package org.laptanovich.webproject.pool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static final Logger logger = LogManager.getLogger();
    private static final String DB_PROPERTIES = "database.properties";
    private static ConnectionPool instance;
    private static final Lock lock = new ReentrantLock();
    private final BlockingDeque<Connection> freeConnections;
    private final BlockingDeque<Connection> usedConnections;

    private ConnectionPool() {
        Properties properties = new Properties();
        try (InputStream inputStream = ConnectionPool.class.getClassLoader().getResourceAsStream(DB_PROPERTIES)) {
            if (inputStream == null) {
                logger.error("Database properties file not found: " + DB_PROPERTIES);
                throw new ExceptionInInitializerError("Database properties file not found: " + DB_PROPERTIES);
            }
            properties.load(inputStream);
            String driver = properties.getProperty("db.driver");
            String url = properties.getProperty("db.url");
            String user = properties.getProperty("db.user");
            String password = properties.getProperty("db.password");
            int poolSize = Integer.parseInt(properties.getProperty("db.poolsize", "8"));
            try {
                Class.forName(driver);
            } catch (ClassNotFoundException e) {
                logger.error("Database driver not found: " + driver, e);
                throw new ExceptionInInitializerError(e);
            }
            freeConnections = new LinkedBlockingDeque<>(poolSize);
            usedConnections = new LinkedBlockingDeque<>(poolSize);
            for (int i = 0; i < poolSize; i++) {
                Connection connection = DriverManager.getConnection(url, user, password);
                freeConnections.add(connection);
            }
            logger.info("Connection pool initialized with {} connections", poolSize);
        } catch (Exception e) {
            logger.error("Error initializing connection pool", e);
            throw new ExceptionInInitializerError(e);
        }
    }

    public static ConnectionPool getInstance() {
        lock.lock();
        try {
            if (instance == null) {
                instance = new ConnectionPool();
            }
            return instance;
        } finally {
            lock.unlock();
        }
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = freeConnections.take();
            usedConnections.put(connection);
        } catch (InterruptedException e) {
            logger.error("Thread interrupted while waiting for connection", e);
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    public void releaseConnection(Connection connection) {
        try {
            usedConnections.remove(connection);
            freeConnections.put(connection);
        } catch (InterruptedException e) {
            logger.error("Thread interrupted while releasing connection", e);
            Thread.currentThread().interrupt();
        }
    }

    private void deregisterDrivers() {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
                logger.info("Deregistered JDBC driver: " + driver);
            } catch (SQLException e) {
                logger.error("Error deregistering JDBC driver: " + driver, e);
            }
        }
    }

    public void destroyPool() {
        for (int i = 0; i < 8; i++) {
            try {
                Connection connection = freeConnections.take();
                connection.close();
            } catch (SQLException | InterruptedException e) {
                logger.error("Error closing connection", e);
            }
        }
        for (Connection connection : usedConnections) {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("Error closing used connection", e);
            }
        }
        deregisterDrivers();
    }
}
