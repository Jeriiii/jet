<%-- 
    Document   : result
    Created on : 28.3.2014, 18:29:46
    Author     : josefhula
--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="newline" value="<%= \"\n\" %>" />


<t:layout>
    <jsp:attribute name="head">
	<style>
	    #uploadform{ width: 400px;}
	    .runinfo{ height: 40px; }
	    #done{ display: none; }
	</style>
    </jsp:attribute>
    
    <jsp:attribute name="foot">
	<script type="text/javascript">
	    var refreshTime = 1000; //ms
	    var results = $('#results');//element do ktereho se bude zapisovat
	    
	    //called when job is done
	    function isFinished(){
		$('#loading').css('display', 'none');
		$('#done').css('display', 'block');
	    }
	    //called when results arrives and job is still not finished
	    function isWorking(){
		setTimeout(function (){
		    refreshResults();
		}, refreshTime);
	    }
	    
	    function refreshResults(){
		results.load("${pageContext.request.contextPath}/result/update?id=${fileid}", function( response, status, xhr ) {
		    
		});
	    }
	    //when ajax response arrives
	    function updateContent(){
		
	    }
	    
	    function test(){
		alert('It works!');
	    }
	</script>
	
	<c:if test="${not empty fincontent}">
	    <script>
		isFinished();
	    </script>
	    
	</c:if>
	<c:if test="${empty fincontent}">
	    <script>
		isWorking();
	    </script>
	</c:if>
<!--	    <script type="text/javascript">
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
	    </script>-->
    </jsp:attribute>
    
    <jsp:attribute name="menu">
      
    </jsp:attribute>
    
    <jsp:body>
	<h1>Results</h1>
	<div class="runinfo">
	    <img id="loading" src="${pageContext.request.contextPath}/resources/img/ajax-loader.gif"/>
	    <h4 id="done">Validation completed</h4>
	</div>
	<c:if test="${not empty email}">
	    <h4>These results were sent to the following email address: ${email}</h4>
	</c:if>
	<c:choose>
	    <c:when test="${not empty fincontent}">
		<section id="results" class="well">
		    ${fn:replace(fincontent, newline, "<br />")}
		</section>
	    </c:when>
	    <c:otherwise>
		<section id="results" class="well">
		</section>
	    </c:otherwise>
	</c:choose>
    </jsp:body>
</t:layout>
