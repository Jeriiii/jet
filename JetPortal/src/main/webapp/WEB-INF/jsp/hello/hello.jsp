<%-- 
    Document   : hello
    Created on : 6.3.2014, 13:13:13
    Author     : JK
--%>


<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
    <jsp:attribute name="head">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/forms.css" type="text/css" />      
    </jsp:attribute>
    
    <jsp:attribute name="foot">
      
    </jsp:attribute>
    
        <jsp:attribute name="menu">
      
    </jsp:attribute>
    
    <jsp:body>
	<section class="well">
	    <h1>Hello World!</h1>
	    <h2>${message}</h2>
	</section>
    </jsp:body>
</t:layout>