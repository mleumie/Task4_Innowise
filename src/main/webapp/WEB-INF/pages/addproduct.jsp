<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Product</title>
</head>
<body>
<h2>Add Product</h2>

<% if (request.getAttribute("errorMessage") != null) { %>
<p style="color:red;"><%= request.getAttribute("errorMessage") %></p>
<% } %>

<form action="${pageContext.request.contextPath}/controller" method="post">
    <input type="hidden" name="command" value="add_product"/>

    <label>Name:</label><br/>
    <input type="text" name="name" required/><br/><br/>

    <label>Price:</label><br/>
    <input type="number" name="price" step="0.01" min="0" required/><br/><br/>

    <label>Description:</label><br/>
    <textarea name="description" rows="4" cols="40"></textarea><br/><br/>

    <button type="submit">Save</button>
</form>

<br/>
<a href="${pageContext.request.contextPath}/controller?command=view_items">Back to catalog</a>
</body>
</html>