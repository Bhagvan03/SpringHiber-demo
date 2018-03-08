<%--
  Created by IntelliJ IDEA.
  User: decipher16
  Date: 3/3/17
  Time: 8:38 PM
  To change this template use File | Settings | File Templates.
--%>
<%@include file="libs.jsp"%>
<html>
<head>
    <title>User Page</title>
</head>
<body>
${message}
<h1>Message : ${message}</h1>

<c:url value="/logout" var="logoutUrl" />
<form action="${logoutUrl}" method="post" id="logoutForm">
    <input type="hidden" name="${_csrf.parameterName}"
           value="${_csrf.token}" />
</form>
<script>
    function formSubmit() {
        document.getElementById("logoutForm").submit();
    }
</script>

<c:if test="${pageContext.request.userPrincipal.name != null}">
    <h2>
        Welcome : ${pageContext.request.userPrincipal.name} | <a
            href="javascript:formSubmit()"> Logout</a>
    </h2>
</c:if>
</body>
</html>
