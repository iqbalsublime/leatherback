<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String message = (String) request.getAttribute("message");
%>
<html ng-app="leatherback">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="${pageContext.request.contextPath}/resources/styles/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/styles/bootstrap-responsive.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/styles/datepicker.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/styles/google-fonts.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/resources/styles/font-awesome.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/styles/angucomplete.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/resources/styles/style.css" rel="stylesheet" type="text/css">
<style>
.app-modal-window .modal-dialog {
	width: 500px;
}
</style>
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
				<a class="brand" href="#/">
					EVER POLYMER CO., LTD.				
				</a>		
				<div class="nav-collapse">
					<ul class="nav pull-right">
						<li class="dropdown">						
							<a href="#">
								Hi Rocky Chen
							</a>
						</li>
						<li class="dropdown">						
							<a href="#">
								<i class="icon-cog"></i>
								Change Password
							</a>
						</li>
						<li class="dropdown">						
							<a href="#">
								<i class="icon-cog"></i>
								Logout
							</a>
						</li>
					</ul>
				</div>
			</div>
		</div>
	</div>
	
	<div class="subnavbar">
		<div class="subnavbar-inner">
			<div class="container">
				<ul class="mainnav">	
					<li>
						<a href="#/">
							<i class="icon-list"></i>
							<span>List all prescriptions</span>
						</a>	    				
					</li>
					<li>
						<a href="#/add">
							<i class="icon-plus"></i>
							<span>Create a prescription</span>
						</a>	    				
					</li>
	                <li>					
						<a href="charts.html">
							<i class="icon-bar-chart"></i>
							<span>Reports</span>
						</a>  									
					</li>
	                <li>					
						<a href="shortcodes.html">
							<i class="icon-user"></i>
							<span>Authorisation</span>
						</a>  									
					</li>
	                <li>														
					</li>
				</ul>
			</div>
		</div>
	</div>

	<div id="main">
		<div class="main-inner">
	    	<div class="container" ng-view>
	    	</div>
	    </div>
    </div>
    
    <script src="${pageContext.request.contextPath}/resources/scripts/jquery.js"></script>
    <script src="${pageContext.request.contextPath}/resources/scripts/bootstrap.js"></script>
    <script src="${pageContext.request.contextPath}/resources/scripts/angular.js"></script>
    <script src="${pageContext.request.contextPath}/resources/scripts/angular-route.js"></script>
    <script src="${pageContext.request.contextPath}/resources/scripts/angular-resource.js"></script>
    <script src="${pageContext.request.contextPath}/resources/scripts/angular-sanitize.js"></script>
    <script src="${pageContext.request.contextPath}/resources/scripts/angular-translate.js"></script>
    <script src="${pageContext.request.contextPath}/resources/scripts/ui-bootstrap-tpls-0.12.0.js"></script>
    <script src="${pageContext.request.contextPath}/resources/scripts/dialogs.js"></script>
    
    <script src="${pageContext.request.contextPath}/resources/scripts/app.js"></script>
    <script src="${pageContext.request.contextPath}/resources/scripts/app-route.js"></script>
    <script src="${pageContext.request.contextPath}/resources/scripts/app-translation.js"></script>
    <script src="${pageContext.request.contextPath}/resources/scripts/app-directive.js"></script>
    <script src="${pageContext.request.contextPath}/resources/scripts/app-service.js"></script>
    <script src="${pageContext.request.contextPath}/resources/scripts/app-factory.js"></script>
    <script src="${pageContext.request.contextPath}/resources/scripts/app-controller.js"></script>
</body>
</html>