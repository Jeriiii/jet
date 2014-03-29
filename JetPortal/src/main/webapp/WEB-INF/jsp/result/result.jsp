<%-- 
    Document   : result
    Created on : 28.3.2014, 18:29:46
    Author     : josefhula
--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


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
			<c:if test="${not empty resultNumber}"><div>${resultNumber}</div></c:if>
			<c:if test="${not empty result}"><div>${result}</div></c:if>
		</section>
    </jsp:body>
</t:layout>
