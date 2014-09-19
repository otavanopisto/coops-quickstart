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
      <h3>Thanks</h3>
      
      <p>This project is made in collaboration with <a href="https://github.com/otavanopisto/">Otavan Opisto</a> and <a href="https://github.com/foyt/">Foyt</a></p>
      
      <h3>Used components and libraries</h3>
      
      <ul>
        <li>Parts of this application are taken from CoOps NodeJs demo (<a href="https://github.com/foyt/coops-demo/">https://github.com/foyt/coops-demo/</a>)</li>
        <li>CoOps plugins for CKEditor (<a href="http://ckeditor.com/addon/coops">http://ckeditor.com/addon/coops</a>)</li>
        <li>DiffXmlJs is used in CKEditor dmp plugin(<a href="https://github.com/foyt/diffxml-js">https://github.com/foyt/diffxml-js</a>)</li>
        <li>JQuery is used for client side scripting(<a href="http://jquery.com/">http://jquery.com/</a>)</li>
        <li>Bootstrap is used as UI library (<a href="http://getbootstrap.com/">http://getbootstrap.com/</a>)</li>
        <li>Wildfly is used as application container (<a href="http://www.wildfly.org/">http://www.wildfly.org/</a>)</li>
        <li>H2 is used as database server (<a href="http://www.h2database.com/">http://www.h2database.com/</a>)</li>
      </ul>

      <footer>
        <p>&copy; Otavan Opisto 2014 <a href="https://www.otavanopisto.fi">Otavan Opisto</a></p>
      </footer>
    </div>

  </body>
</html>