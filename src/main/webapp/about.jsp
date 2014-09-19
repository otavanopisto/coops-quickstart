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
      <h3>About this project</h3>
      
      <p>Goal of this project is to create a jump start application that can be used as to quickly get familiar how developers can create a CoOps server</p>
      <p>As part of this goal application is developed in a way that it can be setup with minimal amount of extra configuration and installation</p>
      <h4>Prerequisites</h4>
      <p>Currently application requires that you have Git, Java and Maven installed</p>
      
      <h4>Installing and Running</h4>
      
      <p>Application can be installed by following 3 easy steps:</p>
      <ol>
        <li><code>git clone https://github.com/otavanopisto/coops-quickstart.git</code></li>
        <li><code>cd coops-quickstart</code></li>
        <li><code>mvn clean verify cargo:run</code></li>
      </ol>
      
      <p>After that you should be able to you should be able to see the CoOps quickstart at <a href="http://localhost:8080">http://localhost:8080</a> (it is possible that application takes couple of minutes to start)</p>
      
      <footer>
        <p>&copy; Otavan Opisto 2014 <a href="https://www.otavanopisto.fi">Otavan Opisto</a></p>
      </footer>
    </div>

  </body>
</html>