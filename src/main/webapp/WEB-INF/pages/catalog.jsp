<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Catalog</title>
</head>
<body>
<h2>Catalog</h2>

<c:if test="${not empty requestScope.items}">
    <table border="1" cellpadding="6" cellspacing="0">
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Price</th>
            <th>Description</th>
            <th>Actions</th>
        </tr>
        <c:forEach var="item" items="${requestScope.items}">
            <tr>
                <td>${item.id}</td>
                <td>${item.name}</td>
                <td>${item.price}</td>
                <td>${item.description}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/controller?command=make_order&item_id=${item.id}">Order</a>
                    <c:if test="${sessionScope.role eq 'ADMIN'}">
                        | <a href="${pageContext.request.contextPath}/controller?command=delete_item&item_id=${item.id}">Delete</a>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
    </table>
</c:if>

<c:if test="${empty requestScope.items}">
    <p>No items found.</p>
</c:if>

<br/>
<a href="${pageContext.request.contextPath}/controller?command=view_orders">My orders</a> |
<a href="${pageContext.request.contextPath}/controller?command=open_settings">Settings</a> |
<c:if test="${sessionScope.role eq 'ADMIN'}">
    <a href="${pageContext.request.contextPath}/controller?command=open_add_product">Add product</a> |
</c:if>
<a href="${pageContext.request.contextPath}/controller?command=logout">Logout</a>
</body>
</html>