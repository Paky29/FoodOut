package controller.utente;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="utenteServlet", value="/utente/*")
public class utenteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       String path=(req.getPathInfo()!=null) ? req.getPathInfo() : "/";
       switch(path){
           case "/":
               break;
           case "/signup":
               break;
           case "/login":
               break;
           case "/show":
               break;
           case "/ristoranti-pref":
               break;
           default:
               resp.sendError(HttpServletResponse.SC_NOT_FOUND,"Risorsa non trovata");
       }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path=(req.getPathInfo()!=null) ? req.getPathInfo() : "/";
        switch(path){
            case "/signup":
                break;
            case "/login":
                /*String email=req.getParameter("email");
                if(email.contains("@foodout.com"))
                else
                    if(email.contains("@foodout.rider.com");
                  else
                 */
                break;
            case "/update":
                break;
            case "/deposit":
                break;
            default:
                resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED,"Operazione non consentita");
        }
    }
}
