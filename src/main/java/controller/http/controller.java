package controller.http;

import model.utility.UtenteSession;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class controller extends HttpServlet implements ErrorHandler {
    protected String getPath(HttpServletRequest req){
       return req.getPathInfo() != null ? req.getPathInfo() : "/";
    }

    protected String view(String viewPath){
        String basePath=getServletContext().getInitParameter("basePath");
        String engine=getServletContext().getInitParameter("engine");
        return basePath + viewPath + engine;
    }

    protected String back(HttpServletRequest req){
        return req.getServletPath() + req.getPathInfo();
    }

    protected void validate(RequestValidator validator) throws InvalidRequestException {
        if(validator.hasErrors()){
            throw new InvalidRequestException("Validation error", validator.getErrors(), HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    protected String getUploadPath(){
        return System.getenv("CATALINA_HOME") + File.separator + "uploads" + File.separator;
    }

    protected int parsePage(HttpServletRequest request){return (request.getParameter("page")==null) ? 1 : Integer.parseInt(request.getParameter("page"));}

    protected UtenteSession getUtenteSession(HttpSession session){
        return (UtenteSession) session.getAttribute("utenteSession");
    }
}
