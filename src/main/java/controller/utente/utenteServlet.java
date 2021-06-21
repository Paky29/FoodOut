package controller.utente;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import controller.http.controller;

@WebServlet(name="utenteServlet", value="/utente/*")
public class utenteServlet extends controller {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path=getPath(req);
        switch(path){
           case "/":
               break;
           case "/signup":
               break;
           case "/login":
               break;
            case "/logout":
                break;
           case "/show":
               break;
           case "/profile":
               break;
           case "/ristoranti-pref":
               break;
           default:
               resp.sendError(HttpServletResponse.SC_NOT_FOUND,"Risorsa non trovata");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path=getPath(req);
        switch(path){
            case "/":
                break;
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
            case "/update-admin":
                break;
            case "/update-cliente":
                break;
            case "/deposit":
                break;
            case "/delete":
                break;
            default:
                resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED,"Operazione non consentita");
        }
    }
}
