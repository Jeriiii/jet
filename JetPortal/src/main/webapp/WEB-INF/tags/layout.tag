<%@tag description="Overall Page template" pageEncoding="UTF-8"%>
<%@attribute name="menu" fragment="true" %>
<%@attribute name="header" fragment="true" %>
<%@attribute name="head" fragment="true" %>
<%@attribute name="foot" fragment="true" %>
<!DOCTYPE html>
<html>
    <head>
	<title>POM Validator</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

	<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
	<!--[if lt IE 9]>
	    <script src="html5shiv.js"></script>
	<![endif]-->
	<link rel="stylesheet" href="${contextPath}/resources/css/layout.css" type="text/css" />
	<link rel="stylesheet" href="${contextPath}/resources/css/print.css" type="text/css"  media="print"/>
	<link href='http://fonts.googleapis.com/css?family=Jockey+One' rel='stylesheet' type='text/css'>
	<link rel="shortcut icon" href="${contextPath}/resources/img/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" href="${contextPath}/resources/bootstrap/css/bootstrap-theme.min.css">
	<link rel="stylesheet" href="${contextPath}/resources/bootstrap/css/bootstrap.min.css">
	<jsp:invoke fragment="head"/>
    </head>

    <body>
	<header>
	    <nav class="navbar navbar-default" role="navigation">
		<!-- Brand and toggle get grouped for better mobile display -->
		<div class="container">
		    <div class="navbar-header">
			    <a class="navbar-brand" href="${pageContext.request.contextPath}">POM Validator</a>
		    </div>
		    <ul class="nav navbar-nav">
			<li>
			  <a href="${contextPath}/form-upload-file">Testovat POM</a>
			</li>
			<li>
			  <a href="${contextPath}/form-upload-file">Výsledky</a>
			</li>
		    </ul>
		</div>
	    </nav>
	    <jsp:invoke fragment="header"/>
	    <div class="clear"></div>
	</header>
	<div id="page" class="container">
		<jsp:doBody/>
	    <div class="clear"></div>
	</div>
	<script src="${pageContext.request.contextPath}/resources/js/jquery-2.1.0.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.min.js"></script>
	<jsp:invoke fragment="foot"/>
    </body>
</html>