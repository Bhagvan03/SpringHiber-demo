<%@include file="libs.jsp"%>
<html>
<head>
<title>Login Page</title>
<style>
.error {
	padding: 15px;
	margin-bottom: 20px;
	border: 1px solid transparent;
	border-radius: 4px;
	color: #a94442;
	background-color: #f2dede;
	border-color: #ebccd1;
}

</style>
	<!-- CSS -->
	<link rel="stylesheet" href="<c:url value='/resources/css/alertify/alertify.min.css'/>" />
	<!-- JavaScript -->
	<script type="text/javascript" src="<c:url value='/resources/js/jquery/jquery-3.2.0.min.js'/>" ></script>
	<script type="text/javascript" src="<c:url value="/resources/js/alertify/alertify.js"/> "></script>
</head>
<title>Login Page</title>
<body bgcolor="#fffaf0">
	<h1>Spring Security Login Form </h1>
	${message}
	<div id="login-box">
	<c:if test="${not empty error}">
		<div class="error">'${error}'
			<%--<script>
                $(document).ready(function() {
                    alertify.alert('${error}');
                    return false;
                });
			</script>--%>
		</div>
	</c:if>
	<c:if test="${not empty msg}">
		<div class="msg">${error}
			<%--<script>
                $(document).ready(function() {
					alertify.alert('${error}');
					return false;
				});
			</script>--%>
		</div>
	</c:if>

	<springform:form method="post" action="processLogin">
		<center>
			<table border="1" width="30%" cellpadding="3">
				<thead>
				<tr>
					<th colspan="2">Login Here</th>
				</tr>
				</thead>
				<tbody>
				<tr>
					<td>User Email</td>
					<td><input type="text" name="email" placeholder="Email Id" id="email"/></td>
				</tr>
				<tr>
					<td>Password</td>
					<td><input type="password" name="password" placeholder="Password" id="password"/></td>
				</tr>
				<tr>
					<td colspan="2" align="center"><input type="submit" value="Login" onclick="emptyCridential()"/> <input type="reset" value="Reset" /></td>
				</tr>
				<tr>
					<td colspan="2">Yet Not Registered!! <a href="registration">Register Here</a></td>
				</tr>
				</tbody>
				<input type="hidden" name="${_csrf.parameterName}"
					   value="${_csrf.token}" />
			</table>
		</center>
	</springform:form>
	</div>


<script>
    function emptyCridential() {
        alertify.alert("emptyCridential",false);
        var email=$("#email").value();
        if(email==""){
            alertify.alert('${error}');
		}
        var password=$("#password").value();
        if(password==""){
            alertify.alert('${error}');
        }
        return false;
    }
</script>
</body>
</html>