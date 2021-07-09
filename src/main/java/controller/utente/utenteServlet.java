package controller.utente;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import controller.http.CommonValidator;
import controller.http.InvalidRequestException;
import controller.http.RequestValidator;
import controller.http.controller;
;
import controller.prodotto.prodottoValidator;
import model.ristorante.Ristorante;
import model.ristorante.RistoranteDAO;
import model.utente.Utente;
import model.utente.UtenteDAO;
import model.utility.Paginator;
import model.utility.UtenteSession;

@WebServlet(name = "utenteServlet", value = "/utente/*")
public class utenteServlet extends controller {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = getPath(req);
        try {
            switch (path) {
                case "/signup":
                    req.getRequestDispatcher(view("site/signup")).forward(req, resp);
                    break;
                case "/login":
                    req.getRequestDispatcher(view("site/login")).forward(req, resp);
                    break;
                case "/logout": {
                    HttpSession session = req.getSession(false);
                    authenticateUtente(session);
                    synchronized (session) {
                        session.invalidate();
                    }
                    resp.sendRedirect("/FoodOut/index.jsp");
                    break;
                }
                case "/update-pw":
                    authenticateUtente(req.getSession(false));
                    req.getRequestDispatcher(view("site/update-pw")).forward(req, resp);
                    break;
                case "/show":{
                    HttpSession ssn = req.getSession(false);
                    authorizeUtente(ssn);
                    UtenteSession us = (UtenteSession) ssn.getAttribute("utenteSession");
                    UtenteDAO service = new UtenteDAO();
                    Utente u = service.doRetrieveById(us.getId());
                    if(u==null) {
                        InternalError();
                    }
                    synchronized (ssn) {
                        ssn.setAttribute("profilo", u);
                    }
                    req.getRequestDispatcher(view("crm/show")).forward(req, resp);
                    break;
                }
                case "/profile": {
                    HttpSession ssn = req.getSession(false);
                    authenticateUtente(ssn);
                    UtenteSession us = (UtenteSession) ssn.getAttribute("utenteSession");
                    UtenteDAO service = new UtenteDAO();
                    Utente u = service.doRetrieveById(us.getId());
                    if (u == null) {
                        InternalError();
                    }
                    synchronized (ssn) {
                        ssn.setAttribute("profilo", u);
                    }
                    req.getRequestDispatcher(view("customer/profile")).forward(req, resp);
                    break;
                }
                case "/ristoranti-pref":{
                    HttpSession session=req.getSession();
                    authenticateUtente(session);
                    UtenteSession us=(UtenteSession) session.getAttribute("utenteSession");
                    UtenteDAO serviceUtente=new UtenteDAO();
                    Utente u=serviceUtente.doRetrieveById(us.getId());
                    if (req.getParameter("page") != null) {
                        validate(CommonValidator.validatePage(req));
                    }
                    int intPage = parsePage(req);
                    Paginator paginator = new Paginator(intPage, 6);
                    int size = serviceUtente.countRistPref(u);
                    req.setAttribute("pages", paginator.getPages(size));
                    ArrayList<Ristorante> ristoranti=serviceUtente.doRetrievebyUtentePref(u.getCodice(),paginator);
                    req.setAttribute("ristoranti",ristoranti);
                    req.getRequestDispatcher(view("customer/rist-pref")).forward(req,resp);
                    break;
                }
                case "/saldo": {
                    HttpSession session=req.getSession(false);
                    validate(CommonValidator.validateFunctionValue(req));
                    authenticateUtente(session);
                    UtenteSession us =(UtenteSession) session.getAttribute("utenteSession");
                    UtenteDAO serviceUtente=new UtenteDAO();
                    Utente u=serviceUtente.doRetrieveById(us.getId());
                    req.setAttribute("profilo",u);
                    req.setAttribute("function",req.getParameter("function"));
                    req.getRequestDispatcher(view("customer/saldo")).forward(req,resp);
                    break;
                }
                case "/delete": {
                    HttpSession ssn = req.getSession();
                    authenticateUtente(ssn);
                    UtenteSession us= (UtenteSession) ssn.getAttribute("utenteSession");
                    UtenteDAO service=new UtenteDAO();
                    if(service.doDelete(us.getId())) {
                        synchronized (ssn) {
                            ssn.invalidate();
                        }
                        resp.sendRedirect("/FoodOut/index.jsp");
                    }
                    else
                        InternalError();
                    break;
                }

                default:
                    notFound();
            }
        } catch (SQLException e) {
            log(e.getMessage());
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        } catch (InvalidRequestException e) {
            log(e.getMessage());
            e.handle(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = getPath(req);
        try {
            switch (path) {
                case "/":
                    break;
                case "/signup": {
                    req.setAttribute("back",view("site/signup"));
                    validate(utenteValidator.validateForm(req));
                    Utente u = new Utente();
                    u.setNome(req.getParameter("nome"));
                    u.setCognome(req.getParameter("cognome"));
                    u.setEmail(req.getParameter("email"));
                    u.setPassword(req.getParameter("pw"));
                    u.setProvincia(req.getParameter("provincia"));
                    u.setCitta(req.getParameter("citta"));
                    u.setVia(req.getParameter("via"));
                    u.setCivico(Integer.parseInt(req.getParameter("civico")));
                    UtenteDAO service = new UtenteDAO();
                    if(service.doSave(u)) {
                        UtenteSession utenteSession = new UtenteSession(u);
                        HttpSession session = req.getSession();
                        synchronized (session) {
                            session.setAttribute("utenteSession", utenteSession);
                        }
                        if (u.getEmail().contains("@foodout.com"))
                            resp.sendRedirect("/FoodOut/utente/show");//cambiare in /utente/show
                        else
                            resp.sendRedirect("/FoodOut/ristorante/zona");//cambiare contenuto pagina
                    }else{
                        InternalError();
                    }
                    break;
                }
                case "/login": {
                    req.setAttribute("back",view("site/login"));
                    validate(utenteValidator.validateLogin(req));
                    String email = req.getParameter("email");
                    String pw = req.getParameter("pw");
                    /*if (email.contains("@foodout.rider.com")) {
                        RiderDAO service = new RiderDAO();
                        Rider rd = service.doRetrievebyEmailAndPassword(email, pw);
                        if (rd == null)
                            notFound();//cambiare con pagina di errore
                        else {
                            RiderSession riderSession = new RiderSession(rd);
                            HttpSession session = req.getSession();
                            synchronized (session) {
                                session.setAttribute("riderSession", riderSession);
                            }
                            resp.sendRedirect("/FoodOut/ristorante/zona");//cambiare in /rider/profile
                        }
                    } else {*/
                        UtenteDAO service = new UtenteDAO();
                        Utente u = service.doRetrieveByEmailAndPassword(email, pw);
                        if (u == null) {
                            notFound();
                        }
                        else {
                            UtenteSession utenteSession = new UtenteSession(u);
                            HttpSession session = req.getSession();
                            synchronized (session) {
                                session.setAttribute("utenteSession", utenteSession);
                            }
                            if (email.contains("@foodout.com"))
                                resp.sendRedirect("/FoodOut/utente/show");
                            else
                                resp.sendRedirect("/FoodOut/ristorante/zona");//cambiare contenuto pagina
                        }
                    //}
                    break;
                }
                case "/update": {
                    //req.setAttribute("back","FoodOut/utente/show");
                    HttpSession session = req.getSession();
                    authenticateUtente(session);
                    validate(utenteValidator.validateUpdate(req));
                    validate(CommonValidator.validateId(req));
                    int codice=Integer.parseInt(req.getParameter("id"));
                    UtenteDAO serviceUtente=new UtenteDAO();
                    if(serviceUtente.doRetrieveById(codice)==null)
                        notFound();
                    Utente u = new Utente();
                    u.setCodice(codice);
                    u.setNome(req.getParameter("nome"));
                    u.setCognome(req.getParameter("cognome"));
                    u.setEmail(req.getParameter("email"));
                    u.setProvincia(req.getParameter("provincia"));
                    u.setCitta(req.getParameter("citta"));
                    u.setVia(req.getParameter("via"));
                    u.setCivico(Integer.parseInt(req.getParameter("civico")));
                    UtenteDAO service = new UtenteDAO();
                    service.doUpdate(u);
                    UtenteSession utenteSession = new UtenteSession(u);
                    synchronized (session) {
                        session.setAttribute("utenteSession", utenteSession);
                    }
                    if (u.getEmail().contains("@foodout.com"))
                        resp.sendRedirect("/FoodOut/utente/show");
                    else
                        resp.sendRedirect("/FoodOut/utente/profile");
                    break;
                }
                case "/update-pw": {
                    HttpSession session = req.getSession();
                    authenticateUtente(session);
                    validate(utenteValidator.validateUpdatePassword(req));
                    String old_pw = req.getParameter("old_pw");
                    String new_pw = req.getParameter("new_pw");
                    String conf_pw = req.getParameter("conf_pw");
                    String email = req.getParameter("email");
                    UtenteDAO service = new UtenteDAO();
                    Utente u = service.doRetrieveByEmailAndPassword(email, old_pw);
                    if (u == null)
                        notFound();

                    if (!new_pw.equals(conf_pw))
                        throw new InvalidRequestException("error", List.of("La nuova password non combacia con la password di conferma"), 404); // da cambiare

                    u.setPassword(new_pw);
                    service.doUpdatePw(u);
                    UtenteSession utenteSession = new UtenteSession(u);
                    synchronized (session) {
                        session.setAttribute("utenteSession", utenteSession);
                    }
                    if (u.getEmail().contains("@foodout.com"))
                        resp.sendRedirect("/FoodOut/utente/show");
                    else
                        resp.sendRedirect("/FoodOut/utente/profile");
                    break;
                }
                case "/deposit": {
                    HttpSession session=req.getSession();
                    authenticateUtente(session);
                    validate(utenteValidator.validateDeposit(req));
                    validate(CommonValidator.validateFunctionValue(req));
                    int function=Integer.parseInt(req.getParameter("function"));
                    float deposito=Float.parseFloat(req.getParameter("deposito"));
                    UtenteSession us=(UtenteSession) session.getAttribute("utenteSession");
                    UtenteDAO serviceDAO=new UtenteDAO();
                    Utente u=serviceDAO.doRetrieveById(us.getId());
                    u.setSaldo(u.getSaldo()+deposito);
                    if(!serviceDAO.doUpdate(u))
                        InternalError();
                    else
                        if(function==1)
                            resp.sendRedirect("/FoodOut/ordine/pagamento");
                        else
                            resp.sendRedirect("/FoodOut/utente/saldo");
                    break;
                }
                default:
                    notAllowed();
            }
        } catch (SQLException e) {
            log(e.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        } catch (InvalidRequestException e) {
            log(e.getMessage());
            System.out.println(e.getErrors());
            e.handle(req, resp);
        }
    }
}
