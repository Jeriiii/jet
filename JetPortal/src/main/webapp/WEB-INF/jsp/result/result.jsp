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
	<c:if test="true">
	    <script type="text/javascript">
		$(function(){
		    var results = $('#results');//element do ktereho se bude zapisovat
		    var file = '${pageContext.request.contextPath}/results/test2.txt';//url zdrojoveho souboru
		    
		    function refreshResults(){
			results.load(file);
		    }
		    refreshResults();
		    setInterval(function() {
			refreshResults();
		    }, 2000);
		});
	    </script>
	</c:if>
    </jsp:attribute>
    
    <jsp:attribute name="menu">
      
    </jsp:attribute>
    
    <jsp:body>
	<h1>Výsledky</h1>
	<c:choose>
	    <c:when test="false">
		<c:if test="${not empty item.email}">
		    <h4>Tyto výsledky byly odesálny na email: ${item.email}</h4>
		</c:if>
		<section id="results" class="well">
			${item.result}
		</section>
	    </c:when>
	    <c:otherwise>
		<section id="results" class="well">
		</section>
	    </c:otherwise>
	</c:choose>
    </jsp:body>
</t:layout>
