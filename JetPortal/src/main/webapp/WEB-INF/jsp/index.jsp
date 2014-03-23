<%-- 
    Document   : hello
    Created on : 6.3.2014, 13:13:13
    Author     : JK
--%>


<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
    <jsp:attribute name="head">
	
    </jsp:attribute>
    
    <jsp:attribute name="foot">
      
    </jsp:attribute>
    
        <jsp:attribute name="menu">
      
    </jsp:attribute>
    
    <jsp:body>
	<p>Vítejte v systému pro validaci souborů pom.xml. Pokračujte na <a href="${pageContext.request.contextPath}/hello">nahrání souboru</a>.</p>
    </jsp:body>
</t:layout>