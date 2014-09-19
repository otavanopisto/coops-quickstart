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
      <div class="jumbotron">
        <h1>CoOps Quickstart</h1>
        <p>This is a quickstart example implementation of CoOps specification for Java</p>
        <p>Purpose of this project is to give a jumpstart for going-to-be-coops-developers</p>
        <p>See details from project's GitHub project:</p>
        <p>
          <a role="button" href="https://github.com/otavanopisto/coops-quickstart" class="btn btn-lg btn-primary">View on GitHub »</a>
        </p>
      </div>
    </div>
    
    <div class="container">
      <p>
        <i>
          Please note that this quickstart runs on in-memory databaase (h2) so <b>all your work will be lost when the server restarts.</b> 
        </i>
      </p>
      
      <h2>Using this demo</h2>
      <p>This demo allows you to edit html documents with your friends. Just click 'Open New Document', wait for the page to load and send the page link to another user and voi'la, you can start collaborating. Open documents are also listed in the index page of this application</p>
      
      <a href="${pageContext.request.contextPath}/files?a=new&contentType=text/html;editor=CKEditor">Open New Document</a>
      <h3>Open Documents</h3>
      
      <c:choose>
        <c:when test="${fn:length(indexPageBackingBean.openFiles) eq 0}">
          <p>There is none, plase click 'Open New Document' to craete one.</p>
        </c:when>
        <c:otherwise>
          <c:forEach var="openFile" items="${indexPageBackingBean.openFiles}">
            <div>
              <a target="_blank" href="${pageContext.request.contextPath}/html.jsp?fileId=${openFile.id}">${openFile.title}</a>
            </div>
          </c:forEach>
        </c:otherwise>
      </c:choose>

      <hr/>

      <footer>
        <p>&copy; Otavan Opisto 2014 <a href="https://www.otavanopisto.fi">Otavan Opisto</a></p>
      </footer>
    </div>

  </body>
</html>