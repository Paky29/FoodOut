package controller.http;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.File;

public class controller extends HttpServlet {
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

    /*protected void validate(RequestValidator validator) throws InvalidRequestException {
        if(validator.hasErrors()){
            throw new InvalidRequestException("Validation error", validator.getErrors());
        }
    }*/

    protected String getUploadPath(){
        return System.getenv("CATALINA_HOME" + File.separator + "uploads" + File.separator);
    }
}
