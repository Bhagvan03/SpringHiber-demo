<%--
  Created by IntelliJ IDEA.
  User: decipher16
  Date: 4/3/17
  Time: 12:02 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="libs.jsp"%>
<html>
<head>
    <title>Remove Account</title>
</head>
<body>
<h1>${message}</h1<br/>
<springform:form action="removeAccount" method="post">
        User Email Id : <input type="text" name="emailId" /><br/>
    <input type="submit" value="Submit" />
</springform:form>
</body>
</html>
