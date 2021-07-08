package controller.ordineItem;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

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

@WebServlet(name="ordineItemServlet", value="/ordineItem/*")
public class ordineItemServlet extends controller {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path=getPath(req);
        try {
            switch (path) {
                case "/insert-amount"://inserisci la quantità del prodotto o menu, visualizza per i prodotti i menu dove si trova, mentre per i menu i nomi dei prodtti da cui è composto
                    break;
                case "/remove-prodotto-item": {
                    HttpSession session=req.getSession(false);
                    validate(CommonValidator.validateId(req));
                    int id=Integer.parseInt(req.getParameter("id"));
                    ProdottoDAO service=new ProdottoDAO();
                    Prodotto p=service.doRetrievebyId(id);
                    Ristorante r=p.getRistorante();

                    synchronized (session){
                        if(session.getAttribute("cart")!=null){
                            Ordine cart=(Ordine) session.getAttribute("cart");
                            if(!cart.removeOrdineItem(id, "Prodotto"))
                                InternalError();
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
                            if(!cart.removeOrdineItem(id, "Prodotto"))
                                InternalError();
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
                    validate(CommonValidator.validateId(req));
                    validate(ordineItemValidator.validateForm(req));
                    validate(prodottoValidator.validateIdRis(req));
                    int id=Integer.parseInt(req.getParameter("id"));
                    int q=Integer.parseInt(req.getParameter("quantita"));
                    int idRis=Integer.parseInt(req.getParameter("idRis"));
                    ProdottoDAO serviceProd=new ProdottoDAO();
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
                        OrdineItem oi=new OrdineItem();
                        oi.setOff(p);
                        oi.setQuantita(q);
                        cart.getOrdineItems().add(oi);
                        session.setAttribute("cart", cart);
                    }

                    resp.sendRedirect("/FoodOut/ristorante/show-menu?id=" + p.getRistorante().getCodice());
                    break;
                }

                case "/add-menu-item": { //aggiungi l'ordine item all'ordine
                    HttpSession session=req.getSession(true);
                    validate(CommonValidator.validateId(req));
                    validate(ordineItemValidator.validateForm(req));
                    validate(menuValidator.validateIdRis(req));
                    int id=Integer.parseInt(req.getParameter("id"));
                    int q=Integer.parseInt(req.getParameter("quantita"));
                    int idRis=Integer.parseInt(req.getParameter("idRis"));
                    ProdottoDAO serviceProd=new ProdottoDAO();
                    MenuDAO serviceMenu=new MenuDAO();
                    Menu m=serviceMenu.doRetrieveById(id);
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
                        OrdineItem oi=new OrdineItem();
                        oi.setOff(m);
                        oi.setQuantita(q);
                        cart.getOrdineItems().add(oi);
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
