<%@ include file="libs.jsp"%>
<html>
<head>
    <title>User List</title>
</head>
<body>
${message}<br/>
<table>
    <c:forEach items="${accountList}" var="username">
        <tr>
            <td>id : ${username.id} </td>
            <td>username : ${username.userName}</td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
