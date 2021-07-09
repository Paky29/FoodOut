package controller.ristorante;

import controller.http.*;
import controller.prodotto.prodottoValidator;
import controller.tipologia.tipologiaValidator;
import model.disponibilita.Disponibilita;
import model.disponibilita.DisponibilitaDAO;
import model.menu.MenuDAO;
import model.ordine.Ordine;
import model.ordine.OrdineDAO;
import model.ordine.OrdineItem;
import model.prodotto.ProdottoDAO;
import model.ristorante.Ristorante;
import model.ristorante.RistoranteDAO;
import model.tipologia.Tipologia;
import model.tipologia.TipologiaDAO;
import model.utente.Utente;
import model.utente.UtenteDAO;
import model.utility.Paginator;
import model.utility.UtenteSession;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;


@WebServlet(name = "ristoranteServlet", value = "/ristorante/*")
@MultipartConfig
public class ristoranteServlet extends controller implements ErrorHandler{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = getPath(req);
        System.out.println((path));
        try {
            switch (path) {
                case "/all": {
                    authorizeUtente(req.getSession());
                    RistoranteDAO service = new RistoranteDAO();
                    if (req.getParameter("page") != null) {
                        validate(CommonValidator.validatePage(req));
                    }
                    int intPage = parsePage(req);
                    int totRis = service.countAll();
                    Paginator paginator = new Paginator(intPage, 6);
                    int size = service.countAll();
                    req.setAttribute("pages", paginator.getPages(size));
                    ArrayList<Ristorante> ristoranti = service.doRetrieveAll(paginator);
                    req.setAttribute("ristoranti", ristoranti);
                    req.setAttribute("totRis", totRis);
                    req.getRequestDispatcher(view("ristorante/show-all")).forward(req, resp);
                    break;
                }
                case "/zona": {//controllare se i parametri sono null per capire se è per l'admin o un utente
                    //req.getRequestDispatcher(view("ristorante/zona")).forward(req,resp);
                    RistoranteDAO serviceRis = new RistoranteDAO();
                    TipologiaDAO serviceTip = new TipologiaDAO();
                    UtenteDAO serviceUtente = new UtenteDAO();
                    String citta = null;
                    HttpSession session = req.getSession(true);
                    UtenteSession us = (UtenteSession) session.getAttribute("utenteSession");
                    if (us != null) {
                        Utente u = serviceUtente.doRetrieveById(us.getId());
                        citta = u.getCitta();
                        System.out.println("cittaUtente:" + citta);
                    } else {
                        if(session.getAttribute("citta")==null) {
                            citta = req.getParameter("citta");
                            session.setAttribute("citta", citta);
                            System.out.println("cittaGuest:" + citta);
                        }
                        else {
                            citta = (String) session.getAttribute("citta");
                            System.out.println("cittaGuestSecondaVolta:" + citta);
                        }

                    }
                    if (req.getParameter("page") != null) {
                        validate(CommonValidator.validatePage(req));
                    }
                    int intPage = parsePage(req);
                    Paginator paginator = new Paginator(intPage, 6);
                    int size = serviceRis.countCitta(citta);
                    req.setAttribute("pages", paginator.getPages(size));

                    Ordine cart = (Ordine) session.getAttribute("cart");
                    if (cart != null) {
                        session.removeAttribute("cart");
                    }
                    System.out.println("citta" + citta);
                    ArrayList<Ristorante> ristoranti = serviceRis.doRetrieveByCitta(citta, paginator, false);
                    ArrayList<Tipologia> tipologie = serviceTip.doRetriveByCitta(citta);
                    req.setAttribute("ristoranti", ristoranti);
                    req.setAttribute("tipologie", tipologie);
                    req.getRequestDispatcher(view("ristorante/show-zona")).forward(req, resp);
                    break;
                }
                case "/show-menu": {//possibilità di aggiungere al carrello i prodotti
                    validate(CommonValidator.validateId(req));
                    int id = Integer.parseInt(req.getParameter("id"));
                    UtenteDAO utenteDAO = new UtenteDAO();
                    RistoranteDAO ristoranteDAO = new RistoranteDAO();
                    Ristorante r = ristoranteDAO.doRetrieveById(id, true);
                    if (r == null)
                        notFound();
                    ProdottoDAO prodottoDAO = new ProdottoDAO();
                    OrdineDAO ordineDAO = new OrdineDAO();
                    MenuDAO menuDAO = new MenuDAO();
                    HttpSession session = req.getSession(true);
                    synchronized (session) {
                        if (session.getAttribute("cart") == null) {
                            Ordine cart = new Ordine();
                            cart.setRistorante(r);
                            session.setAttribute("cart", cart);
                        }
                    }
                    UtenteSession us = (UtenteSession) session.getAttribute("utenteSession");
                    Utente u = new Utente();
                    boolean pref = false;
                    if (us != null) {
                        u.setCodice(us.getId());
                        int count = utenteDAO.countRistPref(u);
                        ArrayList<Ristorante> prefs = utenteDAO.doRetrievebyUtentePref(us.getId(), new Paginator(1, count));
                        if (prefs != null) {
                            for (Ristorante rp : prefs) {
                                System.out.println(rp.getNome());
                                if (rp.getCodice() == r.getCodice()) {
                                    pref = true;
                                    break;
                                }
                            }
                        }
                    }
                    r.setProdotti(prodottoDAO.doRetrieveByRistorante(r.getCodice()));
                    req.setAttribute("menus", menuDAO.doRetrieveByRistorante(r.getCodice()));
                    req.setAttribute("countMenuValidi", ristoranteDAO.countMenuValidita(r.getCodice(), true));
                    req.setAttribute("ristorante", r);
                    System.out.println("pref:" + pref);
                    req.setAttribute("pref", pref);
                    req.setAttribute("countProdValidi", ristoranteDAO.countProdottiValidita(r.getCodice(), true));
                    req.getRequestDispatcher(view("ristorante/menu")).forward(req, resp);

                    break;
                }
                case "/show-info": {
                    authenticateUtente(req.getSession(false));
                    validate(CommonValidator.validateId(req));
                    int id = Integer.parseInt(req.getParameter("id"));
                    RistoranteDAO ristoranteDAO = new RistoranteDAO();
                    Ristorante r = ristoranteDAO.doRetrieveById(id, true);
                    if (r == null)
                        notFound();
                    req.setAttribute("ristorante", r);
                    req.getRequestDispatcher(view("ristorante/info")).forward(req, resp);
                }
                break;
                case "/show-menu-admin": {
                    authorizeUtente(req.getSession());
                    validate(CommonValidator.validateId(req));
                    int id = Integer.parseInt(req.getParameter("id"));
                    RistoranteDAO ristoranteDAO = new RistoranteDAO();
                    Ristorante r = ristoranteDAO.doRetrieveById(id, true);
                    if (r == null)
                        notFound();
                    ProdottoDAO prodottoDAO = new ProdottoDAO();
                    MenuDAO menuDAO = new MenuDAO();
                    r.setProdotti(prodottoDAO.doRetrieveByRistorante(r.getCodice()));
                    req.setAttribute("menus", menuDAO.doRetrieveByRistorante(r.getCodice()));
                    req.setAttribute("ristorante", r);
                    req.setAttribute("countValidi", ristoranteDAO.countProdottiValidita(r.getCodice(), true));
                    req.setAttribute("countNonValidi", ristoranteDAO.countProdottiValidita(r.getCodice(), false));
                    req.getRequestDispatcher(view("ristorante/menu-admin")).forward(req, resp);
                    break;
                }
                case "/add-prodmenu": {
                    authorizeUtente(req.getSession());
                    validate(CommonValidator.validateId(req));
                    validate(CommonValidator.validateFunctionValue(req));
                    int codiceRis = Integer.parseInt(req.getParameter("id"));
                    int function = Integer.parseInt(req.getParameter("function"));
                    RistoranteDAO serviceRis = new RistoranteDAO();
                    Ristorante r = serviceRis.doRetrieveById(codiceRis, true);
                    if (r == null)
                        notFound();
                    TipologiaDAO serviceTip = new TipologiaDAO();
                    ArrayList<Tipologia> tipologie = serviceTip.doRetrieveAll();
                    req.setAttribute("tipologie", tipologie);
                    ProdottoDAO serviceProd = new ProdottoDAO();
                    r.setProdotti(serviceProd.doRetrieveByRistorante(codiceRis));
                    req.setAttribute("ristorante", r);
                    req.setAttribute("function", function);
                    req.setAttribute("countProdValidi", serviceRis.countProdottiValidita(r.getCodice(), true));
                    req.getRequestDispatcher(view("ristorante/add-prodmenu")).forward(req, resp);
                    break;
                }
                case "/show-info-admin": {// mostrare all'admin info modificabili
                    authorizeUtente(req.getSession());
                    validate(CommonValidator.validateId(req));
                    int id = Integer.parseInt(req.getParameter("id"));
                    RistoranteDAO ristoranteDAO = new RistoranteDAO();
                    Ristorante r = ristoranteDAO.doRetrieveById(id, true);
                    if (r == null)
                        notFound();
                    req.setAttribute("ristorante", r);
                    req.getRequestDispatcher(view("ristorante/info-admin")).forward(req, resp);
                    break;
                }
                case "/show-recensioni":
                    req.getRequestDispatcher(view("ristorante/recensioni")).forward(req, resp);
                    break;
                case "/add":
                    authorizeUtente(req.getSession());
                    req.getRequestDispatcher(view("ristorante/add-ristorante")).forward(req, resp);
                    break;
                case "/add-disponibilita": {
                    authorizeUtente(req.getSession());
                    validate(CommonValidator.validateId(req));
                    req.setAttribute("id", req.getParameter("id"));
                    req.getRequestDispatcher(view("ristorante/add-disponibilita")).forward(req, resp);
                    break;
                }
                case "/update-disponibilita": {
                    authorizeUtente(req.getSession());
                    validate(CommonValidator.validateId(req));
                    RistoranteDAO service = new RistoranteDAO();
                    Ristorante r = service.doRetrieveById(Integer.parseInt(req.getParameter("id")), true);
                    if (r == null)
                        notFound();
                    req.setAttribute("ristorante", r);
                    req.getRequestDispatcher(view("ristorante/update-disponibilita")).forward(req, resp);
                    break;
                }
                case "/update-validita": {
                    authorizeUtente(req.getSession());
                    validate(CommonValidator.validateId(req));
                    RistoranteDAO service = new RistoranteDAO();
                    int id = Integer.parseInt(req.getParameter("id"));
                    Ristorante r = service.doRetrieveById(id, true);
                    if (r == null)
                        notFound();
                    else {
                        if (service.updateValidita(id, !r.isValido())) {
                            resp.sendRedirect("/FoodOut/ristorante/all");
                        } else {
                            InternalError();
                        }
                    }
                    break;
                }

                case "/add-pref": {
                    HttpSession ssn = req.getSession();
                    authenticateUtente(ssn);
                    System.out.println(CommonValidator.validateId(req));
                    validate(CommonValidator.validateId(req));
                    System.out.println(prodottoValidator.validateIdRis(req));
                    validate(prodottoValidator.validateIdRis(req));
                    int idUtente = Integer.parseInt(req.getParameter("id"));
                    int idRis = Integer.parseInt(req.getParameter("idRis"));

                    RistoranteDAO service = new RistoranteDAO();
                    if (!service.savePreferenza(idRis, idUtente))
                        InternalError();
                    else
                        resp.sendRedirect("/FoodOut/ristorante/show-menu?id=" + idRis);
                    break;
                }

                case "/remove-pref": {
                    HttpSession ssn = req.getSession();
                    authenticateUtente(ssn);
                    System.out.println(CommonValidator.validateId(req));
                    validate(CommonValidator.validateId(req));
                    System.out.println(prodottoValidator.validateIdRis(req));
                    validate(prodottoValidator.validateIdRis(req));
                    int idUtente = Integer.parseInt(req.getParameter("id"));
                    int idRis = Integer.parseInt(req.getParameter("idRis"));

                    RistoranteDAO service = new RistoranteDAO();
                    if (!service.deletePreferenza(idRis, idUtente))
                        InternalError();
                    else
                        resp.sendRedirect("/FoodOut/ristorante/show-menu?id=" + idRis);
                    break;
                }
                default:
                    notFound();
            }
        } catch (SQLException e) {
            log(e.getMessage());
            System.out.println(e.getMessage());
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        } catch (InvalidRequestException e) {
            log(e.getMessage());
            System.out.println(e.getMessage());
            e.handle(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = getPath(req);
        try {
            switch (path) {
                case "/update": {
                    HttpSession session = req.getSession();
                    authorizeUtente(session);
                    validate(CommonValidator.validateId(req));
                    validate(ristoranteValidator.validateForm(req));
                    int codRis = Integer.parseInt(req.getParameter("id"));
                    RistoranteDAO service = new RistoranteDAO();
                    if (service.doRetrieveById(codRis, true) == null)
                        notFound();
                    Ristorante r = new Ristorante();
                    r.setCodice(codRis);
                    r.setNome(req.getParameter("nome"));
                    r.setProvincia(req.getParameter("provincia"));
                    r.setCitta(req.getParameter("citta"));
                    r.setVia(req.getParameter("via"));
                    r.setCivico(Integer.parseInt(req.getParameter("civico")));
                    r.setSpesaMinima(Float.parseFloat(req.getParameter("spesaMinima")));
                    r.setTassoConsegna(Float.parseFloat(req.getParameter("tassoConsegna")));
                    r.setInfo(req.getParameter("info"));
                    r.setRating(Integer.parseInt(req.getParameter("rating")));
                    Part filePart = req.getPart("urlImmagine");
                    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                    if (!fileName.isBlank()) {
                        r.setUrlImmagine(fileName);
                        if (service.doUpdateWithUrl(r)) {
                            resp.sendRedirect("/FoodOut/ristorante/show-info-admin?id=" + r.getCodice());
                            String uploadRoot = getUploadPath();
                            try (InputStream fileStream = filePart.getInputStream()) {
                                File file = new File(uploadRoot + fileName);
                                Files.copy(fileStream, file.toPath());
                            }
                        } else {
                            InternalError();
                        }

                    } else {
                        if (service.doUpdate(r)) {
                            resp.sendRedirect("/FoodOut/ristorante/show-info-admin?id=" + r.getCodice());
                        } else {
                            InternalError();
                        }
                    }
                    break;
                }
                case "/add": {
                    HttpSession session = req.getSession();
                    authorizeUtente(session);
                    System.out.println(req.getParameter("nome"));
                    validate(ristoranteValidator.validateForm(req));
                    Ristorante r = new Ristorante();
                    r.setNome(req.getParameter("nome"));
                    r.setProvincia(req.getParameter("provincia"));
                    r.setCitta(req.getParameter("citta"));
                    r.setVia(req.getParameter("via"));
                    r.setCivico(Integer.parseInt(req.getParameter("civico")));
                    r.setSpesaMinima(Float.parseFloat(req.getParameter("spesaMinima")));
                    r.setTassoConsegna(Float.parseFloat(req.getParameter("tassoConsegna")));
                    r.setInfo(req.getParameter("info"));
                    r.setValido(true);
                    Part filePart = req.getPart("urlImmagine");
                    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                    validate(ristoranteValidator.validateImmagine(req, fileName));
                    r.setUrlImmagine(fileName);
                    RistoranteDAO service = new RistoranteDAO();
                    if (service.doSave(r)) {
                        resp.sendRedirect("/FoodOut/ristorante/add-disponibilita?id=" + r.getCodice());
                        String uploadRoot = getUploadPath();
                        try (InputStream fileStream = filePart.getInputStream()) {
                            File file = new File(uploadRoot + fileName);
                            Files.copy(fileStream, file.toPath());
                        }

                        DisponibilitaDAO serviceDisp = new DisponibilitaDAO();
                        for (int i = 0; i < Disponibilita.giorni.length; ++i) {
                            Disponibilita d = new Disponibilita();
                            d.setGiorno(Disponibilita.giorni[i]);
                            d.setOraApertura(LocalTime.of(0, 0));
                            d.setOraChiusura(LocalTime.of(0, 0));
                            serviceDisp.doSave(d, r.getCodice());
                        }
                    } else
                        InternalError();
                    break;
                }
                case "/disponibilita": {
                    req.setAttribute("back",view("ristorante/add-disponibilita"));
                    HttpSession session = req.getSession();
                    authorizeUtente(session);
                    validate(disponibilitaValidator.validateForm(req));
                    validate(CommonValidator.validateId(req));
                    int codice = Integer.parseInt(req.getParameter("id"));
                    RistoranteDAO serviceRis = new RistoranteDAO();
                    if (serviceRis.doRetrieveById(codice, true) == null)
                        notFound();
                    DisponibilitaDAO serviceTip = new DisponibilitaDAO();
                    for (int i = 0; i < Disponibilita.giorni.length; ++i) {
                        Disponibilita d = new Disponibilita();
                        d.setGiorno(Disponibilita.giorni[i]);
                        if (req.getParameter("chiuso" + i) == null) {
                            d.setOraApertura(LocalTime.parse(req.getParameter("apertura" + i)));
                            d.setOraChiusura(LocalTime.parse(req.getParameter("chiusura" + i)));
                        } else {
                            d.setOraApertura(LocalTime.of(0, 0));
                            d.setOraChiusura(LocalTime.of(0, 0));
                        }
                        serviceTip.doUpdate(d, codice);
                    }

                    if (req.getParameter("button").equals("add")) {
                        resp.sendRedirect("/FoodOut/ristorante/add-prodmenu?id=" + codice + "&function=0");
                    } else
                        resp.sendRedirect("/FoodOut/ristorante/show-info-admin?id=" + codice);
                    break;
                }
                default:
                    notAllowed();
            }
        } catch (SQLException e) {
            log(e.getMessage());
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        } catch (InvalidRequestException e) {
            log(e.getMessage());
            System.out.println(e.getMessage());
            e.handle(req, resp);
        }
    }
}
