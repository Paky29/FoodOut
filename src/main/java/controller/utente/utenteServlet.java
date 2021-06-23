package controller.utente;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import controller.http.InvalidRequestException;
import controller.http.controller;
import model.rider.Rider;
import model.rider.RiderDAO;
import model.tipologia.Tipologia;
import model.tipologia.TipologiaDAO;
import model.utente.Utente;
import model.utente.UtenteDAO;
import model.utility.RiderSession;
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
                case "/signup":{
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
                    UtenteSession utenteSession=new UtenteSession(u);
                    HttpSession session=req.getSession();
                    System.out.println("Utente session:"+utenteSession.isAdmin());
                    TipologiaDAO tipologiaDAO=new TipologiaDAO();
                    ArrayList<Tipologia> tipologie=tipologiaDAO.doRetriveByCitta(u.getCitta());
                    synchronized (session){
                        session.setAttribute("utenteSession",utenteSession);
                        session.setAttribute("tipologie", tipologie);
                    }
                    if(u.getEmail().contains("@foodout.com"))
                        resp.sendRedirect("/FoodOut/utente/show");//cambiare in /utente/show
                    else
                        resp.sendRedirect("/FoodOut/ristorante/zona");//cambiare contenuto pagina
                    break;
                }
                case "/login":{
                String email=req.getParameter("email");
                String pw=req.getParameter("pw");
                if(email.contains("@foodout.rider.com")){
                    RiderDAO service=new RiderDAO();
                    Rider rd=service.doRetrievebyEmailAndPassword(email,pw);
                    if(rd==null)
                        System.out.println("Credenziali non valide");//cambiare con pagina di errore
                    else{
                        RiderSession riderSession=new RiderSession(rd);
                        HttpSession session=req.getSession();
                        synchronized (session){
                            session.setAttribute("riderSession",riderSession);
                        }
                        resp.sendRedirect("/FoodOut/ristorante/zona");//cambiare in /rider/profile
                    }
                }
                else
                {
                    UtenteDAO service=new UtenteDAO();
                    Utente u=service.doRetrieveByEmailAndPassword(email,pw);
                    if(u==null)
                        System.out.println("Credenziali non valide");
                    else{
                        TipologiaDAO tipologiaDAO=new TipologiaDAO();
                        ArrayList<Tipologia> tipologie=tipologiaDAO.doRetriveByCitta(u.getCitta());
                        UtenteSession utenteSession=new UtenteSession(u);
                        HttpSession session=req.getSession();
                        synchronized (session){
                            session.setAttribute("utenteSession",utenteSession);
                            session.setAttribute("tipologie", tipologie);
                        }
                        if(email.contains("@foodout.com"))
                            resp.sendRedirect("/FoodOut/utente/show");//cambiare in /utente/show
                        else
                            resp.sendRedirect("/FoodOut/ristorante/zona");//cambiare contenuto pagina
                    }
                }
                break;
                }
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
