package controller.ordineItem;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import controller.http.CommonValidator;
import controller.http.InvalidRequestException;
import controller.http.controller;
import controller.menu.menuValidator;
import controller.prodotto.prodottoValidator;
import model.menu.Menu;
import model.menu.MenuDAO;
import model.ordine.Ordine;
import model.ordine.OrdineItem;
import model.prodotto.Prodotto;
import model.prodotto.ProdottoDAO;
import model.ristorante.Ristorante;
import model.ristorante.RistoranteDAO;
import model.utente.Utente;
import model.utente.UtenteDAO;
import model.utility.Paginator;
import model.utility.UtenteSession;

@WebServlet(name="ordineItemServlet", value="/ordineItem/*")
public class ordineItemServlet extends controller {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path=getPath(req);
        try {
            switch (path) {
                case "/insert-amount-prodotto": {//inserisci la quantità del prodotto o menu, visualizza per i prodotti i menu dove si trova, mentre per i menu i nomi dei prodtti da cui è composto
                    validate(CommonValidator.validateId(req));
                    validate(prodottoValidator.validateIdRis(req));
                    int id=Integer.parseInt(req.getParameter("id"));
                    int idRis=Integer.parseInt(req.getParameter("idRis"));
                    ProdottoDAO service=new ProdottoDAO();
                    Prodotto p=service.doRetrievebyId(id);
                    if(p==null)
                        notFound();
                    req.setAttribute("prodotto", p);
                    req.setAttribute("idRis", idRis);
                    req.getRequestDispatcher(view("prodotto/amount")).forward(req, resp);
                    break;
                }
                case "/remove-prodotto-item": {
                    HttpSession session=req.getSession(false);
                    validate(CommonValidator.validateId(req));
                    int id=Integer.parseInt(req.getParameter("id"));
                    ProdottoDAO service=new ProdottoDAO();
                    Prodotto p=service.doRetrievebyId(id);
                    Ristorante r=p.getRistorante();

                    synchronized (session) {
                        if(session.getAttribute("cart")!=null){
                            Ordine cart=(Ordine) session.getAttribute("cart");
                                if (!cart.removeOrdineItem(id, "Prodotto"))
                                    InternalError();

                                float totale=0;
                                for(OrdineItem oi: cart.getOrdineItems()){
                                    for(int i=1; i<=oi.getQuantita(); i++){
                                        totale+=oi.getOff().getPrezzo() - (oi.getOff().getPrezzo()*oi.getOff().getSconto())/100;
                                    }
                                }
                                cart.setTotale(totale);
                            }
                    }

                    resp.sendRedirect("/FoodOut/ristorante/show-menu?id=" + r.getCodice());
                    break;
                }
                case "/remove-menu-item": {
                    HttpSession session=req.getSession(false);
                    validate(CommonValidator.validateId(req));
                    int id=Integer.parseInt(req.getParameter("id"));
                    MenuDAO serviceMenu=new MenuDAO();
                    Menu m=serviceMenu.doRetrieveById(id);
                    ProdottoDAO serviceProd=new ProdottoDAO();
                    Prodotto p=serviceProd.doRetrievebyId(m.getProdotti().get(0).getCodice());

                    synchronized (session){
                        if(session.getAttribute("cart")!=null){
                            Ordine cart=(Ordine) session.getAttribute("cart");
                            if(!cart.removeOrdineItem(id, "Menu"))
                                InternalError();
                            float totale=0;
                            for(OrdineItem oi: cart.getOrdineItems()){
                                for(int i=1; i<=oi.getQuantita(); i++){
                                    totale+=oi.getOff().getPrezzo() - (oi.getOff().getPrezzo()*oi.getOff().getSconto())/100;
                                }
                            }
                            cart.setTotale(totale);
                        }
                    }

                    resp.sendRedirect("/FoodOut/ristorante/show-menu?id=" + p.getRistorante().getCodice());
                    break;
                }
                default:
                    notFound();
            }
        }
        catch (SQLException e) {
            log(e.getMessage());
            System.out.println(e.getMessage());
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
                case "/add-prodotto-item": { //aggiungi l'ordine item all'ordine
                    HttpSession session=req.getSession(true);
                    req.setAttribute("back",view("ristorante/menu"));
                    validate(CommonValidator.validateId(req));
                    validate(prodottoValidator.validateIdRis(req));
                    int id=Integer.parseInt(req.getParameter("id"));
                    int idRis=Integer.parseInt(req.getParameter("idRis"));
                    //setup alert
                    RistoranteDAO ristoranteDAO=new RistoranteDAO();
                    MenuDAO menuDAO=new MenuDAO();
                    ProdottoDAO serviceProd=new ProdottoDAO();
                    UtenteDAO utenteDAO=new UtenteDAO();
                    Ristorante r=ristoranteDAO.doRetrieveById(idRis,true);
                    UtenteSession us = (UtenteSession) session.getAttribute("utenteSession");
                    Utente u = new Utente();
                    boolean pref = false;
                    if (us != null) {
                        u.setCodice(us.getId());
                        int count = utenteDAO.countRistPref(u);
                        ArrayList<Ristorante> prefs = utenteDAO.doRetrievebyUtentePref(us.getId(), new Paginator(1, count));
                        if (prefs != null) {
                            for (Ristorante rp : prefs) {
                                if (rp.getCodice() == r.getCodice()) {
                                    pref = true;
                                    break;
                                }
                            }
                        }
                    }
                    r.setProdotti(serviceProd.doRetrieveByRistorante(r.getCodice()));
                    req.setAttribute("menus", menuDAO.doRetrieveByRistorante(r.getCodice()));
                    req.setAttribute("countMenuValidi", ristoranteDAO.countMenuValidita(r.getCodice(), true));
                    req.setAttribute("ristorante", r);
                    req.setAttribute("isOpen",r.isOpen(LocalDateTime.now()));
                    req.setAttribute("pref", pref);
                    req.setAttribute("countProdValidi", ristoranteDAO.countProdottiValidita(r.getCodice(), true));
                    validate(ordineItemValidator.validateForm(req));

                    int q=Integer.parseInt(req.getParameter("quantita"));
                    Prodotto p=serviceProd.doRetrievebyId(id);
                    if(p==null)
                        notFound();
                    if(p.getRistorante().getCodice()!=idRis)
                        notAllowed();
                    synchronized (session){
                        if(session.getAttribute("cart")==null){
                            Ordine cart=new Ordine();
                            cart.setRistorante(p.getRistorante());
                            session.setAttribute("cart", cart);
                        }

                        Ordine cart=(Ordine) session.getAttribute("cart");
                        boolean isPresent=false;
                        for(OrdineItem oc: cart.getOrdineItems()){
                            if(oc.getOff().getCodice()==p.getCodice() && oc.getOff().getClass().getName().contains("Prodotto")){
                                oc.setQuantita(oc.getQuantita()+q);
                                isPresent=true;
                                break;
                            }
                        }
                        if(!isPresent) {
                            OrdineItem oi = new OrdineItem();
                            oi.setOff(p);
                            oi.setQuantita(q);
                            cart.getOrdineItems().add(oi);
                        }

                        float totale=cart.getTotale();
                        for(int i=1; i<=q; i++){
                            totale+=p.getPrezzo() - (p.getPrezzo()*p.getSconto())/100;
                        }

                        cart.setTotale(totale);
                        session.setAttribute("cart", cart);
                    }

                    Ordine oo=(Ordine) session.getAttribute("cart");
                    resp.sendRedirect("/FoodOut/ristorante/show-menu?id=" + p.getRistorante().getCodice());
                    break;
                }

                case "/add-menu-item": { //aggiungi l'ordine item all'ordine
                    HttpSession session=req.getSession(true);
                    req.setAttribute("back",view("ristorante/menu"));
                    validate(CommonValidator.validateId(req));
                    validate(prodottoValidator.validateIdRis(req));
                    int id=Integer.parseInt(req.getParameter("id"));
                    int idRis=Integer.parseInt(req.getParameter("idRis"));
                    //setup alert
                    RistoranteDAO ristoranteDAO=new RistoranteDAO();
                    MenuDAO menuDAO=new MenuDAO();
                    ProdottoDAO serviceProd=new ProdottoDAO();
                    UtenteDAO utenteDAO=new UtenteDAO();
                    Ristorante r=ristoranteDAO.doRetrieveById(idRis,true);
                    UtenteSession us = (UtenteSession) session.getAttribute("utenteSession");
                    Utente u = new Utente();
                    boolean pref = false;
                    if (us != null) {
                        u.setCodice(us.getId());
                        int count = utenteDAO.countRistPref(u);
                        ArrayList<Ristorante> prefs = utenteDAO.doRetrievebyUtentePref(us.getId(), new Paginator(1, count));
                        if (prefs != null) {
                            for (Ristorante rp : prefs) {
                                if (rp.getCodice() == r.getCodice()) {
                                    pref = true;
                                    break;
                                }
                            }
                        }
                    }
                    r.setProdotti(serviceProd.doRetrieveByRistorante(r.getCodice()));
                    req.setAttribute("menus", menuDAO.doRetrieveByRistorante(r.getCodice()));
                    req.setAttribute("countMenuValidi", ristoranteDAO.countMenuValidita(r.getCodice(), true));
                    req.setAttribute("ristorante", r);
                    req.setAttribute("isOpen",r.isOpen(LocalDateTime.now()));
                    req.setAttribute("pref", pref);
                    req.setAttribute("countProdValidi", ristoranteDAO.countProdottiValidita(r.getCodice(), true));
                    validate(ordineItemValidator.validateForm(req));

                    int q=Integer.parseInt(req.getParameter("quantita"));
                    Menu m=menuDAO.doRetrieveById(id);
                    if(m==null)
                        notFound();
                    Prodotto p=serviceProd.doRetrievebyId(m.getProdotti().get(0).getCodice());

                    if(p.getRistorante().getCodice()!=idRis)
                        notAllowed();
                    synchronized (session){
                        if(session.getAttribute("cart")==null){
                            Ordine cart=new Ordine();
                            cart.setRistorante(p.getRistorante());
                            session.setAttribute("cart", cart);
                        }

                        Ordine cart=(Ordine) session.getAttribute("cart");
                        boolean isPresent=false;
                        for(OrdineItem oc: cart.getOrdineItems()){
                            if(oc.getOff().getCodice()==m.getCodice() && oc.getOff().getClass().getName().contains("Menu")){
                                oc.setQuantita(oc.getQuantita()+q);
                                isPresent=true;
                                break;
                            }
                        }
                        if(!isPresent) {
                            OrdineItem oi = new OrdineItem();
                            oi.setOff(m);
                            oi.setQuantita(q);
                            cart.getOrdineItems().add(oi);
                        }
                        float totale=cart.getTotale();
                        for(int i=1; i<=q; i++){
                            totale+=m.getPrezzo() - (m.getPrezzo()*m.getSconto())/100;
                        }
                        cart.setTotale(totale);
                        session.setAttribute("cart", cart);
                    }

                    resp.sendRedirect("/FoodOut/ristorante/show-menu?id=" + p.getRistorante().getCodice());
                    break;
                }

                default:
                    notAllowed();
            }
        }
        catch (SQLException e) {
            log(e.getMessage());
            System.out.println(e.getMessage());
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
        catch (InvalidRequestException e) {
            log(e.getMessage());
            System.out.println(e.getMessage());
            e.handle(req,resp);
        }
    }
}
