package controller.rider;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="riderServlet", value="/rider/*")
public class riderServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path=(req.getPathInfo()!=null)? req.getPathInfo() : "/";
        switch(path){
            case "/":
                break;
            case "/create":
                break;
            case "/show":
                break;
            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND,"Risorsa non trovata");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path=(req.getPathInfo()!=null)? req.getPathInfo() : "/";
        switch(path){
            case "/":
                break;
            case "/create":
                break;
            case "/update":
                break;
            default:
                resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED,"Operazione non consentita");
        }
    }
}
