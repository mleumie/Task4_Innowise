<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>


<% if (request.getAttribute("errorMessage") != null) { %>
<p style="color: red;"><%= request.getAttribute("errorMessage") %></p>
<% } %>

<form action="controller" method="POST">
    <input type="hidden" name="command" value="sign_up" />

    <label>New login:</label><br/>
    <input type="text" name="login" required/><br/><br/>

    <label>New password:</label><br/>
    <input type="password" name="password" required/><br/><br/>

    <button type="submit">Sign up</button>
</form>

<br/>
<a href="index.jsp">main page</a>
</body>
</html>