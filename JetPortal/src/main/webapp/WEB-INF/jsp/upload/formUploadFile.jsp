<%-- 
    Document   : upload
    Created on : 25.3.2014, 13:13:13
    Author     : Petr Kukrál
--%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script type="text/javascript">
    $(document).ready(function(){
        $('input[type="checkbox"]').click(function(){
            if($(this).attr("name")=="checkEmail"){
                $("#showEmail").toggle();
            }
        });
    });
</script>

<t:layout>
    <jsp:attribute name="head">
	<style>
	    #uploadform{ width: 400px;}
	    
	</style>
    </jsp:attribute>
    
    <jsp:attribute name="foot">
      
    </jsp:attribute>
    
        <jsp:attribute name="menu">
      
    </jsp:attribute>
    
    <jsp:body>
		<section class="jumbotron">
			<h2>Validate POM file</h2>
			<c:if test="${not empty errorFormMessage}"><div class="alert alert-danger">${errorFormMessage}</div></c:if>
			<c:if test="${not empty successFormMessage}"><div class="alert alert-success">${successFormMessage}</div></c:if>

			<form:form method="post" id="uploadform" enctype="multipart/form-data" modelAttribute="uploadedFile" role="form">  
				<form:errors path="file" /> 
				<div class="form-group">
						<label>Choose POM file: </label>  
						<input type="file" name="file" required/>   
				</div> 
                                <div class="form-group">
                                    <label><input type="checkbox" name="checkEmail"> Share result on email</label>
                                </div>
				<div class="form-group" id="showEmail" style=" display: none">
						<input type="email" name="email" class="form-control" size="30" maxlength="50" placeholder="Email address"/>   
				</div> 
				<input type="submit" value="VALIDATE" class="btn btn-primary btn-lg" /> <a class="btn btn-warning btn-lg" href="${pageContext.request.contextPath}/upload/example-file-upload">TRY IT ON EXAMPLE POM</a>
			</form:form> 
		</section>
    </jsp:body>
</t:layout>