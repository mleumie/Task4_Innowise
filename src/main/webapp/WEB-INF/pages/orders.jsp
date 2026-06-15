<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Orders</title>
</head>
<body>
<h2>Orders</h2>

<c:if test="${not empty requestScope.orders}">
    <table border="1" cellpadding="6" cellspacing="0">
        <tr>
            <th>Order ID</th>
            <th>User ID</th>
            <th>Item ID</th>
            <th>Status</th>
        </tr>
        <c:forEach var="order" items="${requestScope.orders}">
            <tr>
                <td>${order.id}</td>
                <td>${order.user.id}</td>
                <td>${order.item.id}</td>
                <td>${order.status}</td>
            </tr>
        </c:forEach>
    </table>
</c:if>

<c:if test="${empty requestScope.orders}">
    <p>No orders yet.</p>
</c:if>

<br/>
<a href="${pageContext.request.contextPath}/controller?command=view_items">Back to catalog</a>
</body>
</html>