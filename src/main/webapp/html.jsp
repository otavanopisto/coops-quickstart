<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <jsp:include page="/includes/head.jsp"/>
    <title>CoOps quickstart</title>

    <script type="text/javascript">
      var CONTEXTPATH = '${pageContext.request.contextPath}';
    </script>
    
    <script type="text/javascript" src="//cdnjs.cloudflare.com/ajax/libs/ckeditor/4.3.2/ckeditor.js"></script>
    <script type="text/javascript" src="//cdnjs.cloudflare.com/ajax/libs/diff_match_patch/20121119/diff_match_patch.js"></script>
    <script type="text/javascript" src="//cdn.jsdelivr.net/jshash/2.2/md5-min.js"></script> 
    <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/diffxml-js/diffxml-js.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/collaborators.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/html.js"></script>
    
    <link rel="StyleSheet" href="${pageContext.request.contextPath}/css/html.css"/>
  </head>
  <body>
    <jsp:include page="/includes/forkme.jsp"></jsp:include>
    <jsp:include page="/includes/navbar.jsp"></jsp:include>
    <jsp:include page="/includes/notifications.jsp"></jsp:include>
    
    <div class="container">
      <div class="content">
        <div class="editor">
          <div id="title-container">
            <input type="text" name="name" value="">
          </div>
          <div id="ckcontainer"></div>
        </div>
        <div class="collaborators"></div>
      </div>
    </div>
    <div class="footer"></div>
  </body>
</html>