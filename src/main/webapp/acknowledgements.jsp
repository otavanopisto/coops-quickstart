<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <jsp:include page="/includes/head.jsp"/>
    <title>CoOps quickstart</title>
  </head>
  <body>
    <jsp:include page="/includes/forkme.jsp"></jsp:include>
    <jsp:include page="/includes/navbar.jsp"></jsp:include>
  
    <div class="container">
      <p>Thanks to</p>
      <ul>
        <li>oo + gh / www</li>
        <li>foyt + gh / www</li>
      </ul>
      
      <p>Tech</p>
      <ul>
        <li>coops-demo</li>
        <li>ckplugins</li>
        <li>diffxmljs</li>
        <li>jquery</li>
        <li>bootstrap</li>
        <li>wildfly</li>
        <li>h2</li>
      </ul>

      <footer>
        <p>&copy; Otavan Opisto 2014 <a href="https://www.otavanopisto.fi">Otavan Opisto</a></p>
      </footer>
    </div>

  </body>
</html>