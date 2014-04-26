
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<p id="update">
    <c:if test="${not empty fincontent}">
	${fincontent}
	<script>
	    isFinished();
	</script>

    </c:if>
    <c:if test="${empty fincontent}">
	${content}
	<script>
	    updateContent();
	    isWorking();
	</script>
    </c:if>
</p>

