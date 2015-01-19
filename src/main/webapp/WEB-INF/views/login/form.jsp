<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="${pageContext.request.contextPath}/resources/styles/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/styles/bootstrap-responsive.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/styles/font-awesome.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/styles/google-fonts.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/resources/styles/style.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/resources/styles/signin.css" rel="stylesheet" type="text/css">
</head>

<body>
	<div class="navbar navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container">
				<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</a>
				<a class="brand" href="/">
					EVER POLYMER CO., LTD.				
				</a>
			</div> <!-- /container -->
		</div> <!-- /navbar-inner -->
	</div> <!-- /navbar -->
	<div class="account-container">
		<div class="content clearfix">
			<form action="/login" method="post">
				<h1>Member Login</h1>		
				<div class="login-fields">
					<p>Please provide your details</p>
					<div class="field">
						<label for="username">Username</label>
						<input type="text" id="username" name="username" value="" placeholder="Username" class="login username-field" />
					</div> <!-- /field -->
					<div class="field">
						<label for="password">Password:</label>
						<input type="password" id="password" name="password" value="" placeholder="Password" class="login password-field"/>
					</div> <!-- /password -->
				</div> <!-- /login-fields -->
				<div class="login-actions">
					<span class="login-checkbox">
						<input id="Field" name="Field" type="checkbox" class="field login-checkbox" value="First Choice" tabindex="4" />
						<label class="choice" for="Field">Keep me signed in</label>
					</span>
					<button class="button btn btn-success btn-large">Sign In</button>
				</div> <!-- .actions -->
			</form>
		</div> <!-- /content -->
	</div>

    <script src="${pageContext.request.contextPath}/resources/scripts/jquery.js"></script>
    <script src="${pageContext.request.contextPath}/resources/scripts/bootstrap.js"></script>
    <script src="${pageContext.request.contextPath}/resources/scripts/signin.js"></script>
</body>
</html>