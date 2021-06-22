package controller.utente;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

import controller.http.InvalidRequestException;
import controller.http.controller;
import model.utente.Utente;
import model.utente.UtenteDAO;
import model.utility.UtenteSession;

@WebServlet(name="utenteServlet", value="/utente/*")
public class utenteServlet extends controller {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path=getPath(req);
        switch(path){
           case "/":
               break;
           case "/signup":
               req.getRequestDispatcher(view("site/signup")).forward(req,resp);
               break;
           case "/login":
               req.getRequestDispatcher(view("site/login")).forward(req,resp);
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
        try {
            switch (path) {
                case "/":
                    break;
                case "/signup":
                    System.out.println("ok1");
                    validate(utenteValidator.validateForm(req));
                    Utente u=new Utente();
                    u.setNome(req.getParameter("nome"));
                    System.out.println(u.getNome());
                    u.setCognome(req.getParameter("cognome"));
                    u.setEmail(req.getParameter("email"));
                    u.setPassword(req.getParameter("pw"));
                    u.setProvincia(req.getParameter("provincia"));
                    u.setCitta(req.getParameter("citta"));
                    u.setVia(req.getParameter("via"));
                    u.setCivico(Integer.parseInt(req.getParameter("civico")));
                    UtenteDAO service=new UtenteDAO();
                    service.doSave(u);
                    System.out.println("ok2");
                    UtenteSession utenteSession=new UtenteSession(u);
                    HttpSession session=req.getSession();
                    System.out.println("Utente session:"+utenteSession.isAdmin());
                    synchronized (session){
                        session.setAttribute("utenteSession",utenteSession);
                    }
                    resp.sendRedirect("/FoodOut/ristorante/zona");
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
                    resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Operazione non consentita");
            }
        } catch (SQLException e) {
            log(e.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
        catch (InvalidRequestException e) {
            log(e.getMessage());
            e.handle(req,resp);
        }
    }
}
