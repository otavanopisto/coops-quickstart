<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>CoOps quickstart</title>
    <script type="text/javascript">
      var CONTEXTPATH = '${pageContext.request.contextPath}';
    </script>
    
    <script type="text/javascript" src="//cdnjs.cloudflare.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
    <script type="text/javascript" src="//cdnjs.cloudflare.com/ajax/libs/ckeditor/4.3.2/ckeditor.js"></script>
    <script type="text/javascript" src="//cdnjs.cloudflare.com/ajax/libs/diff_match_patch/20121119/diff_match_patch.js"></script>
    <script type="text/javascript" src="//cdn.jsdelivr.net/jshash/2.2/md5-min.js"></script> 
    <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/diffxml-js/diffxml-js.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/html.js"></script>
  </head>
  <body>
    <div class="notifications"></div>
    <div class="container">
      <div class="content">
        <div class="editor">
          <div id="title-container">
            <input type="text" name="name" value="Untitled">
          </div>
          <div id="ckcontainer"></div>
        </div>
        <div class="collaborators"></div>
      </div>
    </div>
    <div class="footer"></div>
  </body>
</html>