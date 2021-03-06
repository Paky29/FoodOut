package controller.menu;

import controller.http.CommonValidator;
import controller.http.InvalidRequestException;
import controller.http.RequestValidator;
import controller.http.controller;
import controller.prodotto.prodottoValidator;
import controller.tipologia.tipologiaValidator;
import model.menu.Menu;
import model.menu.MenuDAO;
import model.prodotto.Prodotto;
import model.prodotto.ProdottoDAO;
import model.ristorante.Ristorante;
import model.ristorante.RistoranteDAO;
import model.tipologia.Tipologia;
import model.tipologia.TipologiaDAO;

import javax.management.MalformedObjectNameException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name="menuServlet", value="/menu/*")
public class menuServlet extends controller{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path=getPath(req);
        try {
            switch (path) {
                case "/update": {
                    authorizeUtente(req.getSession(false));
                    validate(CommonValidator.validateId(req));
                    validate(menuValidator.validateIdRis(req));
                    int id=Integer.parseInt(req.getParameter("id"));
                    int idRis=Integer.parseInt(req.getParameter("idRis"));
                    MenuDAO menuDAO=new MenuDAO();
                    RistoranteDAO ristoranteDAO=new RistoranteDAO();
                    ProdottoDAO prodottoDAO=new ProdottoDAO();
                    Menu m=menuDAO.doRetrieveById(id);
                    if(m==null)
                        notFound();
                    else {
                        Ristorante r = ristoranteDAO.doRetrieveById(idRis, true);
                        if(r==null)
                            notFound();
                        r.setProdotti(prodottoDAO.doRetrieveByRistorante(idRis));
                        req.setAttribute("ristorante", r);
                        req.setAttribute("menu", m);
                        req.getRequestDispatcher(view("menu/update-menu")).forward(req, resp);
                    }

                    break;
                }
                default:
                    notFound();
            }
        }
        catch (SQLException e) {
            log(e.getMessage());
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }catch (InvalidRequestException e) {
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
                case "/create": {
                    HttpSession session = req.getSession();
                    authorizeUtente(session);
                    req.setAttribute("back",view("ristorante/add-prodmenu"));
                    validate(CommonValidator.validateId(req));
                    validate(CommonValidator.validateFunctionValue(req));
                    //preparazione alert
                    int function=Integer.parseInt(req.getParameter("function"));
                    int codice=Integer.parseInt(req.getParameter("id"));
                    RistoranteDAO serviceRis = new RistoranteDAO();
                    Ristorante r = serviceRis.doRetrieveById(codice, true);
                    ProdottoDAO serviceProd = new ProdottoDAO();
                    r.setProdotti(serviceProd.doRetrieveByRistorante(codice));
                    req.setAttribute("ristorante",r);
                    req.setAttribute("function",function);
                    TipologiaDAO serviceTip=new TipologiaDAO();
                    ArrayList<Tipologia> tipologie = serviceTip.doRetrieveAll();
                    req.setAttribute("tipologie", tipologie);
                    req.setAttribute("countProdValidi", serviceRis.countProdottiValidita(codice, true));
                    validate(menuValidator.validateForm(req));

                    Menu m = new Menu();
                    m.setNome(req.getParameter("nome"));
                    m.setPrezzo(Float.parseFloat(req.getParameter("prezzo")));
                    m.setSconto(Integer.parseInt(req.getParameter("sconto")));
                    m.setValido(true);
                    String[] prodotti = req.getParameterValues("prodotti");
                    for (int i = 0; i < prodotti.length; ++i) {
                        m.getProdotti().add(serviceProd.doRetrievebyId(Integer.parseInt(prodotti[i])));
                    }
                    MenuDAO serviceMenu = new MenuDAO();
                    if (serviceMenu.doSave(m)) {
                        if (req.getParameter("button").equals("again"))
                            resp.sendRedirect("/FoodOut/ristorante/add-prodmenu?id=" + codice + "&function=" + function);
                        else {
                            if (function == 1)
                                resp.sendRedirect("/FoodOut/ristorante/show-menu-admin?id=" + codice);
                            else
                                resp.sendRedirect("/FoodOut/ristorante/all");
                        }
                    } else
                        InternalError();
                    break;
                }
                case "/update": {
                    authorizeUtente(req.getSession());
                    req.setAttribute("back",view("menu/update-menu"));
                    validate(CommonValidator.validateId(req));
                    validate(menuValidator.validateIdRis(req));
                    //setup alert
                    int id=Integer.parseInt(req.getParameter("id"));
                    int idRis=Integer.parseInt(req.getParameter("idRis"));
                    MenuDAO menuDAO=new MenuDAO();
                    RistoranteDAO ristoranteDAO=new RistoranteDAO();
                    ProdottoDAO prodottoDAO=new ProdottoDAO();
                    Menu m=menuDAO.doRetrieveById(id);
                    if(m==null)
                        notFound();
                    else {
                        Ristorante r = ristoranteDAO.doRetrieveById(idRis, true);
                        if (r == null)
                            notFound();
                        r.setProdotti(prodottoDAO.doRetrieveByRistorante(idRis));
                        req.setAttribute("ristorante", r);
                        req.setAttribute("menu", m);
                    }
                    validate(menuValidator.validateForm(req));

                    m.setNome(req.getParameter("nome"));
                    m.setPrezzo(Float.parseFloat(req.getParameter("prezzo")));
                    m.setSconto(Integer.parseInt(req.getParameter("sconto")));

                    ArrayList<Prodotto> oldProdMenu = m.getProdotti();
                    menuDAO.deleteProducts(id, oldProdMenu);

                    String[] prodotti = req.getParameterValues("prodotti");
                    ArrayList<Prodotto> newProdMenu = new ArrayList<>();
                    for (int i = 0; i < prodotti.length; ++i) {
                        newProdMenu.add(prodottoDAO.doRetrievebyId(Integer.parseInt(prodotti[i])));
                    }
                    menuDAO.addProducts(id, newProdMenu);
                    if (menuDAO.doUpdate(m)) {
                        resp.sendRedirect("/FoodOut/ristorante/show-menu-admin?id=" + idRis);
                    } else
                        InternalError();

                    break;
                }
                case "/update-validita": {
                    authorizeUtente(req.getSession());
                    validate(CommonValidator.validateId(req));
                    int codiceMenu=Integer.parseInt(req.getParameter("id"));
                    validate(menuValidator.validateIdRis(req));
                    int codiceRis=Integer.parseInt(req.getParameter("idRis"));
                    MenuDAO serviceMenu=new MenuDAO();
                    Menu m=serviceMenu.doRetrieveById(codiceMenu);
                    if(m==null)
                        notFound();
                    else
                    {
                        if(!serviceMenu.updateValidita(codiceMenu,!m.isValido()))
                            InternalError();
                        else
                        {
                            RistoranteDAO serviceRis=new RistoranteDAO();
                            if(serviceRis.doRetrieveById(codiceRis,true)==null) {
                                notFound();
                            }
                            else{
                                resp.sendRedirect("/FoodOut/ristorante/show-menu-admin?id="+codiceRis);
                            }
                        }
                    }
                    break;
                }
                default:
                    notAllowed();
            }
        }catch (SQLException e) {
            log(e.getMessage());
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }catch (InvalidRequestException e) {
            log(e.getMessage());
            System.out.println(e.getMessage());
            e.handle(req,resp);
        }
    }
}
