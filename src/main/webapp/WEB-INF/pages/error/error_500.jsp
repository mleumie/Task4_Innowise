<%--
  Created by IntelliJ IDEA.
  User: lapta
  Date: 03.06.26
  Time: 13:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>500</title>
</head>
<body>
Request from: ${pageContext.errorData.requestURI} is failed<br/>
Servlet name: ${pageContext.errorData.servletName} <br/>
Status code: ${pageContext.errorData.statusCode} <br/>
Exception: ${pageContext.exception} <br/>
Message from exception: ${pageContext.exception.message} <br/>
</body>
</html>
