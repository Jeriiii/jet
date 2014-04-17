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
	
    </jsp:attribute>
    
    <jsp:attribute name="foot">
	<div class="jumbotron">
	    <div class="container">
	      <h1>Welcome</h1>
              <p>Welcome on portal for POM.xml files validation.</p>
	      <p><a href="${contextPath}/upload/form-upload-file" class="btn btn-primary btn-lg" role="button">Validate file</a></p>
	    </div>
	</div>
    </jsp:attribute>
    
        <jsp:attribute name="menu">
      
    </jsp:attribute>
    
    <jsp:body>
	  
	
    </jsp:body>
</t:layout>