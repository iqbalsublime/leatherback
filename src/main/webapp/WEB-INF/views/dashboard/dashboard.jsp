<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String message = (String) request.getAttribute("message");
%>
<html ng-app="leatherback">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
 <link href="http://netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/styles/vendor/bootstrap-theme.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/styles/vendor/ng-bs-animated-button.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/styles/vendor/angular-block-ui.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/styles/app.css" rel="stylesheet">
</head>

<body> 
   <!-- Fixed navbar -->
    <nav class="navbar navbar-default navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">{{ 'COMPANY_NAME' | translate }}</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav">
            <li><a href="#/">{{ 'PRESCRIPTION_MANAGE' | translate }}</a></li>
            <li><a href="#/report">{{ 'REPORT_OUTPUT' | translate }}</a></li>
            <li><a href="#/users">{{ 'AUTHORISATION_MANAGE' | translate }}</a></li>
          </ul>
          <ul class="nav navbar-nav navbar-right">
            <li><a href="#/password">{{ 'CHANGE_PASSWORD' | translate }}</a></li>
            <li><a href="logout">{{ 'LOG_OUT' | translate }}</a></li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>

	<div class="container" ng-view>

    </div>
    
    <script src="${pageContext.request.contextPath}/resources/scripts/vendor/jquery-2.1.3.js"></script>
    <script src="${pageContext.request.contextPath}/resources/scripts/vendor/bootstrap.js"></script>
    <script src="${pageContext.request.contextPath}/resources/scripts/vendor/angular.js"></script>
    <script src="${pageContext.request.contextPath}/resources/scripts/vendor/angular-route.js"></script>
    <script src="${pageContext.request.contextPath}/resources/scripts/vendor/angular-resource.js"></script>
    <script src="${pageContext.request.contextPath}/resources/scripts/vendor/angular-sanitize.js"></script>
    <script src="${pageContext.request.contextPath}/resources/scripts/vendor/angular-loader.js"></script>
    <script src="${pageContext.request.contextPath}/resources/scripts/vendor/angular-translate.js"></script>
    <script src="${pageContext.request.contextPath}/resources/scripts/vendor/ui-bootstrap-tpls-0.12.0.js"></script>
    <script src="${pageContext.request.contextPath}/resources/scripts/vendor/ui-utils.js"></script>
    <script src="${pageContext.request.contextPath}/resources/scripts/vendor/i18n/angular-locale_zh-tw.js"></script>
    <script src="${pageContext.request.contextPath}/resources/scripts/vendor/ng-bs-animated-button.js"></script>
    <script src="${pageContext.request.contextPath}/resources/scripts/vendor/angular-block-ui.js"></script>
    
    <script src="${pageContext.request.contextPath}/resources/scripts/app.js"></script>
    <script src="${pageContext.request.contextPath}/resources/scripts/app-route.js"></script>
    <script src="${pageContext.request.contextPath}/resources/scripts/app-directive.js"></script>
    <script src="${pageContext.request.contextPath}/resources/scripts/app-service.js"></script>
    <script src="${pageContext.request.contextPath}/resources/scripts/app-factory.js"></script>
    <script src="${pageContext.request.contextPath}/resources/scripts/app-controller.js"></script>
    <script src="${pageContext.request.contextPath}/resources/scripts/app-translation.js"></script>
</body>
</html>