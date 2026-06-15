<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>

<h1>Welcome</h1>

<br/>
<form action="controller" method="POST">
    <input type="hidden" name="command" value="login"/>
    Login: <input type="text" name="login" value=""/>
    <br/>
    Password: <input type="password" name="password" value=""/>
    <br/>
    <input type="submit" name="sub" value="Push"/>
    <br/>
    ${errorMessage}
</form>

<br/>
<a href="controller?command=sign_up">Sign Up</a>

</body>
</html>