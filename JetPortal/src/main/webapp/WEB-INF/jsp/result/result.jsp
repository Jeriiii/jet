<%--
    Document   : result
    Created on : 28.3.2014, 18:29:46
    Author     : jan kotalik
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
			#temp{ display: none; }
		</style>
    </jsp:attribute>

    <jsp:attribute name="foot">
		<script type="text/javascript">
			//this script implements ajax request and response operations
			var refreshTime = 100; //ms - time to wait every refresh try (can be 0 with long polling)
			var temp = $('#temp');//temp element - for work with arriwing data
			var results = $('#results table'); //element for data writing
			var endsymbol = '${endsymbol}';
			var nodata = '${timeoutsymbol}';

			//called when job is done
			function isFinished() {
				$('#loading').css('display', 'none');
				$('#done').css('display', 'block');
			}
			//called when results arrives and job (validation) is still not finished
			function isWorking() {
				setTimeout(function() {
					refreshResults();
				}, refreshTime);
			}
			//ajax request, response is more results
			function refreshResults() {
				temp.load("${pageContext.request.contextPath}/result/update?id=${fileid}&ticket=${ticket}", function(response, status, xhr) {
							if (status == 'error') {
								console.log('Ajax error');
								return;
							}
							if (response == nodata) {
								isWorking();
							} else {
								if (response.indexOf(endsymbol) != -1) {
									updateContent();
									isFinished();
								} else {
									updateContent();
									isWorking();
								}
							}
						});
					}

					//ajax request - is validation completed? Entire content if so, error if not.
					function tryToGetComplete() {// !!! UNUSED IN THIS VERSION (uncomment when you use it for something...)
						temp.load("${pageContext.request.contextPath}/result/finished?id=${fileid}&ticket=${ticket}", function(response, status, xhr) {
									if (status == 'error') {
										setTimeout(function() {
											refreshResults();
										}, refreshTime);
									} else {
										updateFinishedContent();
									}
								});
							}
							//updates content when response arrives
							function updateContent() {
								results.append(temp.html().replace(endsymbol, ''));
								scrollToBottomOfData();
							}
							//updates content when entire finished document arrives
							function updateFinishedContent() {// !!! UNUSED IN THIS VERSION (uncomment when you use it for something...)
								results.html(temp.html().replace(endsymbol, ''));
								isFinished();
							}
							//scrolls with window to the bottom
							function scrollToBottomOfData() {
								$("html, body").scrollTop($(document).height());
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
		<div id="temp"></div>
		<c:choose>
			<c:when test="${not empty fincontent}">
				<section id="results" class="well">
					<table class="table">
						<c:out escapeXml="false" value="${fincontent}"/>
					</table>
				</section>
			</c:when>
			<c:otherwise>
				<section id="results" class="well">
					<table class="table">

					</table>
				</section>
			</c:otherwise>
		</c:choose>
    </jsp:body>
</t:layout>
