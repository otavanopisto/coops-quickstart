package fi.otavanopisto.coops.quickstart;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;

import fi.otavanopisto.coops.quickstart.dao.FileDAO;
import fi.otavanopisto.coops.quickstart.model.File;

@WebServlet (urlPatterns = "/files")
@Transactional
public class FilesServlet extends HttpServlet {

  private static final long serialVersionUID = -2809810656298696877L;
  
  @Inject
  private FileDAO fileDAO;

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String action = request.getParameter("a");
    if (StringUtils.isBlank(action)) {
      response.sendError(HttpServletResponse.SC_BAD_REQUEST);
      return;
    }
    
    switch (action) {
      case "new":
        String contentType = request.getParameter("contentType");
        if ("text/html;editor=CKEditor".equals(contentType)) {
          File file = fileDAO.create(0l, contentType, null);
          response.sendRedirect(request.getContextPath() + "/html.jsp?fileId=" + file.getId());
          return;
        } else {
          response.sendError(HttpServletResponse.SC_BAD_REQUEST);
          return;
        }
      
      default:
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        return;
    }
    
  };
  
}
