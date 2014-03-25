<%-- 
    Document   : upload
    Created on : 25.3.2014, 13:13:13
    Author     : Petr Kukrál
--%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>


<t:layout>
    <jsp:attribute name="head">  
    </jsp:attribute>
    
    <jsp:attribute name="foot">
      
    </jsp:attribute>
    
        <jsp:attribute name="menu">
      
    </jsp:attribute>
    
    <jsp:body>
		<section class="jumbotron">
			<h2>Nahrajte POM soubor</h2>
			<c:if test="${not empty errorFormMessage}"><div class="alert alert-danger">${errorFormMessage}</div></c:if>

			<form:form method="post" enctype="multipart/form-data" modelAttribute="uploadedFile" role="form">  
				<form:errors path="file" /> 
				<div class="form-group">
						<label>Vyberte POM soubor: </label>  
						<input type="file" name="file" />   
				</div> 
				<input type="submit" value="Upload" class="btn btn-primary btn-lg" />  
			</form:form> 
		</section>
    </jsp:body>
</t:layout>