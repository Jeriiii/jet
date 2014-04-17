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
	    .runinfo{ height: 40px; }
	    #done{ display: none; }
	</style>
    </jsp:attribute>
    
    <jsp:attribute name="foot">
	<c:if test="true">
	    <script type="text/javascript">
		var refreshTime = 1000; //ms
		//var finishPath = "${pageContext.request.contextPath}${finishPath}";
		//var workingPath = "${pageContext.request.contextPath}${workingPath}";
		var finishPath = "/results/finish-${filename}";
		var workingPath = "/results/working-${filename}";
		$(function(){
		    var results = $('#results');//element do ktereho se bude zapisovat
		    function updateGraphics(){
			$('#loading').css('display', 'none');
			$('#done').css('display', 'block');
		    }
	
		    function loadFinish(){
			results.load(finishPath, function( response, status, xhr ) {
			    if(status == 'error'){//file not found
				setTimeout(function (){
				    refreshResults();
				}, refreshTime);
			    }else{//nacteny kompletni vysledky
				updateGraphics();//zobrazeni se aktualizuje
			    }
			});
		    }
	
		    function refreshResults(){
			results.load(workingPath, function( response, status, xhr ) {
			    if(status == 'error'){//file not found
				loadFinish();
			    }else{
				setTimeout(function (){
				    refreshResults();
				}, refreshTime);
			    }
			});
		    }
		    refreshResults();
		    
		});
	    </script>
	</c:if>
    </jsp:attribute>
    
    <jsp:attribute name="menu">
      
    </jsp:attribute>
    
    <jsp:body>
	<h1>Results</h1>
	<div class="runinfo">
	    <img id="loading" src="${pageContext.request.contextPath}/resources/img/ajax-loader.gif"/>
	    <h4 id="done">Validation completed</h4>
	</div>
	
	<c:choose>
	    <c:when test="false">
		<c:if test="${not empty item.email}">
		    <h4>These results were sent to the following email address: ${item.email}</h4>
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
