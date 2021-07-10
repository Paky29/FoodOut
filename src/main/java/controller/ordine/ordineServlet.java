package controller.ordine;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import controller.http.CommonValidator;
import controller.http.InvalidRequestException;
import controller.http.controller;
import controller.utente.utenteValidator;
import model.ordine.Ordine;
import model.ordine.OrdineDAO;
import model.utente.Utente;
import model.utente.UtenteDAO;
import model.utility.Paginator;
import model.utility.UtenteSession;

@WebServlet(name="ordineServlet", value="/ordine/*" )
public class ordineServlet extends controller {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path=getPath(req);
        try {
            switch (path) {
                case "/login":
                    req.getRequestDispatcher(view("ordine/login")).forward(req, resp);
                    break;
                case "/signup":
                    req.getRequestDispatcher(view("ordine/signup")).forward(req, resp);
                    break;
                case "/utente-storico":
                    break;
                case "/utente-attesa":
                    break;
                case "/dettagli": {
                    authorizeUtente(req.getSession());
                    OrdineDAO service = new OrdineDAO();
                    validate(CommonValidator.validateId(req));
                    int id = Integer.parseInt(req.getParameter("id"));
                    Ordine o = service.doRetrieveById(id);
                    if (o == null)
                        notFound();
                    else {
                        req.setAttribute("ordine", o);
                        req.getRequestDispatcher(view("ordine/show")).forward(req, resp);
                    }
                    break;
                }
                /*case "/rider"://passare come parametro lo stato della consegna
                    break;*/
                case "/ristorante":
                    break;
                case "/all": {//possibile inviare date come parametri per filtrare gli ordini
                    authorizeUtente(req.getSession());
                    OrdineDAO service = new OrdineDAO();
                    if (req.getParameter("page") != null) {
                        validate(CommonValidator.validatePage(req));
                    }
                    int intPage = parsePage(req);
                    int totOrd = service.countAll();
                    int incasso=0;
                    Paginator paginator = new Paginator(intPage, 6);
                    int size = service.countAll();
                    req.setAttribute("pages", paginator.getPages(size));
                    ArrayList<Ordine> ordini = service.doRetrieveAll(paginator);

                    if(ordini!=null)
                        for(Ordine o: ordini)
                            incasso+=o.getTotale();

                    req.setAttribute("ordini", ordini);
                    req.setAttribute("totOrd", totOrd);
                    req.setAttribute("incasso", incasso);
                    req.getRequestDispatcher(view("ordine/show-all")).forward(req, resp);
                    break;
                }
                case "/pagamento": {
                    HttpSession session = req.getSession(false);
                    authenticateUtente(session);
                    UtenteSession us = (UtenteSession) session.getAttribute("utenteSession");
                    UtenteDAO serviceUtente = new UtenteDAO();
                    Utente u = serviceUtente.doRetrieveById(us.getId());
                    Ordine o = (Ordine) session.getAttribute("cart");
                    if (o == null)
                        notAllowed();
                    boolean saldo;
                    if (u.getSaldo() > o.getTotale())
                        saldo = true;
                    else
                        saldo = false;
                    req.setAttribute("saldo", saldo);
                    req.getRequestDispatcher(view("ordine/pagamento")).forward(req, resp);
                    break;
                }

                case "/add-recensione":{
                    HttpSession session=req.getSession(false);
                    authenticateUtente(session);
                    validate(CommonValidator.validateId(req));
                    int codice=Integer.parseInt(req.getParameter("id"));
                    OrdineDAO serviceOrd=new OrdineDAO();
                    if(serviceOrd.doRetrieveById(codice)==null)
                        notFound();
                    req.setAttribute("id", codice);
                    req.getRequestDispatcher(view("ordine/recensione")).forward(req, resp);
                    break;
                }

                case "/delete":{
                    authorizeUtente(req.getSession());
                    validate(CommonValidator.validateId(req));
                    int codice=Integer.parseInt(req.getParameter("id"));
                    OrdineDAO serviceOrd=new OrdineDAO();
                    if(serviceOrd.doRetrieveById(codice)==null)
                        notFound();
                    if(serviceOrd.doDelete(codice)){
                        resp.sendRedirect("/FoodOut/ordine/all");
                    }
                    else
                        InternalError();
                    break;
                }
                default:
                    notFound();
            }
        }catch (SQLException e) {
            log(e.getMessage());
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
        catch (InvalidRequestException e) {
            log(e.getMessage());
            System.out.println(e.getMessage());
            e.handle(req,resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path=getPath(req);
        try {
            switch (path) {
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
                            resp.sendRedirect("/FoodOut/utente/show");
                        else
                            resp.sendRedirect("/FoodOut/ordine/pagamento");
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
                            resp.sendRedirect("/FoodOut/ordine/pagamento");
                    }
                    break;
                }
                case "/add-recensione": {
                    HttpSession session=req.getSession(false);
                    if(session==null ||  session.getAttribute("utenteSession")==null)
                        System.out.println("utente non trovato");
                    System.out.println(((UtenteSession)session.getAttribute("utenteSession")).getId());
                    authenticateUtente(session);
                    validate(CommonValidator.validateId(req));
                    System.out.println(ordineValidator.validateForm(req));
                    validate(ordineValidator.validateForm(req));
                    int codice=Integer.parseInt(req.getParameter("id"));
                    int voto=Integer.parseInt(req.getParameter("voto"));
                    String giudizio=req.getParameter("giudizio");
                    OrdineDAO serviceOrd=new OrdineDAO();
                    Ordine ord=serviceOrd.doRetrieveById(codice);
                    if(ord==null)
                        notFound();
                    ord.setVoto(voto);
                    ord.setGiudizio(giudizio);
                    if(!serviceOrd.updateVG(ord))
                        InternalError();
                    else
                        resp.sendRedirect("/FoodOut/utente/storico");
                    break;
                }
                case "/pagamento":{
                    HttpSession session=req.getSession(false);
                    authenticateUtente(session);
                    validate(ordineValidator.validatePagamento(req));
                    validate(ordineValidator.validateForm(req));
                    Ordine o=(Ordine) session.getAttribute("cart");
                    if(o==null)
                        notAllowed();
                    UtenteSession us = (UtenteSession) session.getAttribute("utenteSession");
                    UtenteDAO service = new UtenteDAO();
                    Utente u = service.doRetrieveById(us.getId());
                    if (u == null) {
                        InternalError();
                    }
                    String metodo=req.getParameter("metodo");
                    if(metodo.equals("saldo") && u.getSaldo()<o.getTotale())
                        notAllowed();
                    if(metodo.equals("cash"))
                        u.setSaldo(u.getSaldo()-o.getTotale());
                    o.setUtente(u);
                    o.setConsegnato(false);
                    o.setOraArrivo(null);
                    o.setOraPartenza(null);
                    o.setMetodoPagamento(metodo);
                    o.setNota(req.getParameter("nota"));
                    o.setDataOrdine(LocalDate.now());
                    o.setGiudizio(null);
                    o.setVoto(0);
                    OrdineDAO serviceOrdine=new OrdineDAO();
                    if(serviceOrdine.doSave(o))
                        resp.sendRedirect("/FoodOut/ristorante/zona");
                    else
                        InternalError();
                    break;
                }
                case "/update": {
                    authorizeUtente(req.getSession());
                    validate(CommonValidator.validateId(req));
                    validate(ordineValidator.validateFormAdmin(req));
                    OrdineDAO service=new OrdineDAO();
                    int id=Integer.parseInt(req.getParameter("id"));
                    Ordine o=service.doRetrieveById(id);
                    if(o==null)
                        notFound();
                    else {
                        if(!o.isConsegnato()) {
                            String oraPartenza = req.getParameter("oraPartenza");
                            String oraArrivo = req.getParameter("oraArrivo");
                            if (!oraPartenza.isBlank())
                                o.setOraPartenza(LocalTime.parse(oraPartenza));
                            if (!oraArrivo.isBlank()) {
                                o.setConsegnato(true);
                                o.setOraArrivo(LocalTime.parse(oraArrivo));
                            }
                        }
                        if(service.doUpdate(o))
                            resp.sendRedirect("/FoodOut/ordine/dettagli?id=" + o.getCodice());
                    }
                    break;
                }
                default:
                    notAllowed();
            }
        }

        catch (SQLException e) {
            log(e.getMessage());
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }

        catch (InvalidRequestException e) {
            log(e.getMessage());
            System.out.println(e.getErrors());
            e.handle(req,resp);
        }
    }
}
