
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="newline" value="<%= \"\n\" %>" />

<p id="update">
    <c:if test="${not empty fincontent}">
	${fn:replace(fincontent, newline, "<br />")}
	<script>
	    isFinished();
	</script>

    </c:if>
    <c:if test="${empty fincontent}">
	${fn:replace(content, newline, "<br />")}
	<script>
	    updateContent();
	    isWorking();
	</script>
    </c:if>
</p>

