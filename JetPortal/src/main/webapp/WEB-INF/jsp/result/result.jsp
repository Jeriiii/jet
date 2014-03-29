<%-- 
    Document   : result
    Created on : 28.3.2014, 18:29:46
    Author     : josefhula
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>POM Validator result</title>
    </head>
    <body>
        <h1>Výsledek validace</h1>
        <c:if test="${not empty result}"><div>${result}</div></c:if>
    </body>
</html>
