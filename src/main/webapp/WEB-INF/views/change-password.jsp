<%--
  Created by IntelliJ IDEA.
  User: decipher19
  Date: 27/3/17
  Time: 12:18 PM
  To change this template use File | Settings | File Templates.
--%>

<%@include file="libs.jsp"%>
<html>
<head>
    <title>UserManagement</title>
    <%--<h2><spring:message code="lebel.title"/></h2>--%>
</head>
<body>
<h1>${message}</h1><br/>
<springform:form action="changeUserPassword" method="post" >
    <table>
        <tr>
            <td>CURRENT PASSWORD : </td>
            <td><input type="password" name="currentPassword" id="currentPassword"/></td>
        </tr>
        <tr>
            <td>NEW PASSWORD : </td>
            <td><input type="password" name="newPassword" id="newPassword"/></td>
        </tr>
        <tr>
            <td><input type="hidden" name="emailId" id="emailId" value="${emailId}"/></td>
        </tr>
        <tr>
            <td colspan="3"><input type="submit" value="Submit" /></td>
        </tr>
    </table>
</springform:form>
</body>
</html>