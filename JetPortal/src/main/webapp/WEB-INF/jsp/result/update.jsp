
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="newline" value="<%= \"\n\" %>" />

<p id="update">
    <c:if test="${not empty fincontent}">
	<p id="newcontent">
	    ${fn:replace(fincontent, newline, "<br />")}
	</p>
	<script>
	    updateContent();
	    isFinished();
	</script>
    </c:if>
    <c:if test="${empty fincontent}">
	<p id="newcontent">
	    ${fn:replace(content, newline, "<br />")}
	</p>
	<script>
	    if(lastmod != ${lastmod}){
		lastmod = ${lastmod};
		updateContent();
	    }
	    isWorking();
	    
	</script>
    </c:if>
</p>

