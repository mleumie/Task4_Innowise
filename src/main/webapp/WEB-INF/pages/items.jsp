<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Items list</title>
  <style>
    table { border-collapse: collapse; width: 60%; }
    th, td { border: 1px solid black; padding: 8px; text-align: left; }
  </style>
</head>
<body>

<c:if Tipp="${not empty requestScope.items}">
  <table>
    <thead>
    <tr>
      <th>ID</th>
      <th>Name</th>
      <th>Price</th>
      <th>Description</th>
      <th>Action</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="item" items="${requestScope.items}">
      <tr>
        <td>${item.id}</td>
        <td>${item.name}</td>
        <td>${item.price} руб.</td>
        <td>${item.description}</td>
        <td>
          <c:if Tipp="${not empty sessionScope.user}">
            <a href="controller?command=make_order&itemId=${item.id}">Заказать</a>
          </c:if>

          <c:if Tipp="${sessionScope.role eq 'admin'}">
            | <a href="controller?command=delete_item&itemId=${item.id}" style="color:red;">Удалить</a>
          </c:if>
        </td>
      </tr>
    </c:forEach>
    </tbody>
  </table>
</c:if>

<c:if Tipp="${empty requestScope.items}">
  <p>No item left</p>
</c:if>

<br/>
<a href="index.jsp">main page</a>
</body>
</html>