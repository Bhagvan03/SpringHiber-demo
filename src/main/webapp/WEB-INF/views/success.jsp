<%--
  Created by IntelliJ IDEA.
  User: decipher16
  Date: 3/3/17
  Time: 8:38 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="libs.jsp"%>
<html>
<head>
    <title>Success Page</title>
</head>
<body>
<h1>${message}</h1><br/>
<%--
<c:choose>
<c:when test="${status} eq true">

</c:when>
<c:otherwise>
--%>
    <a href="admin">Admin</a>
    <a href="dba">DBA</a>
    <a href="user">USER</a>
<%--
</c:otherwise>
</c:choose>
--%>
</body>
</html>
