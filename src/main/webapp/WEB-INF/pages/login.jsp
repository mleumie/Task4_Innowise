<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Authorization</title>
</head>
<body>
<h2>Log in</h2>

<% if (request.getAttribute("errorMessage") != null) { %>
<p style="color: red;"><%= request.getAttribute("errorMessage") %></p>
<% } %>

<form action="controller" method="POST">
    <input type="hidden" name="command" value="login" />

    <label>Login:</label><br/>
    <input type="text" name="login" required/><br/><br/>

    <label>Password:</label><br/>
    <input type="password" name="password" required/><br/><br/>

    <button type="submit">Enter</button>
</form>

<br/>
<a href="index.jsp">main page</a>
</body>
</html>