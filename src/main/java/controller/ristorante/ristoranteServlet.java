package controller.ristorante;

import controller.http.*;
import model.disponibilita.Disponibilita;
import model.disponibilita.DisponibilitaDAO;
import model.ristorante.Ristorante;
import model.ristorante.RistoranteDAO;
import model.utility.Paginator;

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


@WebServlet (name="ristoranteServlet", value="/ristorante/*")
@MultipartConfig
public class ristoranteServlet extends controller implements ErrorHandler {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path=getPath(req);
        System.out.println((path));
        try {
            switch (path) {
                case "/":
                    break;
                case "/all": {
                    authorizeUtente(req.getSession());
                    RistoranteDAO service=new RistoranteDAO();
                    int intPage=parsePage(req);
                    int totRis=service.countAll();
                    Paginator paginator=new Paginator(intPage,2);
                    int size=service.countAll();
                    req.setAttribute("pages",paginator.getPages(size));
                    ArrayList<Ristorante> ristoranti = service.doRetrieveAll(paginator);
                    req.setAttribute("ristoranti", ristoranti);
                    req.setAttribute("totRis", totRis);
                    req.getRequestDispatcher(view("ristorante/show-all")).forward(req, resp);
                    break;
                }
                case "/zona"://controllare se i parametri sono null per capire se è per l'admin o un utente
                    System.out.println("sono qui");
                    //req.getRequestDispatcher(view("ristorante/zona")).forward(req,resp);
                    req.getRequestDispatcher("/index.jsp").forward(req, resp);
                    break;
                case "/show-menu"://possibilità di aggiungere al carrello i prodotti
                    break;
                case "/show-info":// mostrare info statiche
                    break;
                case "/show-menu-admin": {
                    authorizeUtente(req.getSession());
                    req.getRequestDispatcher(view("ristorante/menu-admin")).forward(req, resp);
                    break;
                }
                case "/show-info-admin": {// mostrare all'admin info modificabili
                    authorizeUtente(req.getSession());
                    CommonValidator.validateId(req);
                    int id=Integer.parseInt(req.getParameter("id"));
                    RistoranteDAO ristoranteDAO=new RistoranteDAO();
                    Ristorante r=ristoranteDAO.doRetrieveByIdAdmin(id);
                    req.setAttribute("ristorante", r);
                    //req.setAttribute("ristoranteUrl", "FoodOut/covers/" + r.getUrlImmagine());
                    req.getRequestDispatcher(view("ristorante/info-admin")).forward(req, resp);
                    break;
                }
                case "/show-recensioni":
                    req.getRequestDispatcher(view("ristorante/recensioni")).forward(req, resp);
                    break;
                case "/add":
                    req.getRequestDispatcher(view("ristorante/add-ristorante")).forward(req,resp);
                    break;
                case "/delete": {
                    authorizeUtente(req.getSession());
                    CommonValidator.validateId(req);
                    RistoranteDAO service=new RistoranteDAO();
                    int id=Integer.parseInt(req.getParameter("id"));
                    if(service.doDelete(id)){
                        resp.sendRedirect("/FoodOut/ristorante/all");//non sicuro
                    }
                    else {
                        InternalError();
                    }
                    break;
                }
                default:
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Risorsa non trovata");
            }
        }
        catch (SQLException e) {
            log(e.getMessage());
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
        catch (InvalidRequestException e) {
            log(e.getMessage());
            e.handle(req,resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = getPath(req);
        try {
            switch (path) {
                case "/":
                    break;
                case "/add-pref":
                    break;
                case "/update": {
                    HttpSession session = req.getSession();
                    authorizeUtente(session);
                    ristoranteValidator validator = new ristoranteValidator();
                    validator.validateForm(req);
                    Ristorante r = new Ristorante();
                    r.setCodice(Integer.parseInt(req.getParameter("id")));
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
                    RistoranteDAO service = new RistoranteDAO();
                    if (!fileName.isBlank()) {
                        r.setUrlImmagine(fileName);
                        if (service.doUpdateWithUrl(r)) {
                            resp.sendRedirect("/FoodOut/ristorante/show-info-admin?id=" + r.getCodice());
                            String uploadRoot = getUploadPath();
                            try (InputStream fileStream = filePart.getInputStream()) {
                                File file = new File(uploadRoot + fileName);
                                Files.copy(fileStream, file.toPath());
                            }
                        }
                        else {
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
                    ristoranteValidator validator = new ristoranteValidator();
                    validator.validateForm(req);
                    Ristorante r = new Ristorante();
                    r.setNome(req.getParameter("nome"));
                    r.setProvincia(req.getParameter("provincia"));
                    r.setCitta(req.getParameter("citta"));
                    r.setVia(req.getParameter("via"));
                    r.setCivico(Integer.parseInt(req.getParameter("civico")));
                    r.setSpesaMinima(Float.parseFloat(req.getParameter("spesaMinima")));
                    r.setTassoConsegna(Float.parseFloat(req.getParameter("tassoConsegna")));
                    r.setInfo(req.getParameter("info"));
                    Part filePart = req.getPart("urlImmagine");
                    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                    r.setUrlImmagine(fileName);
                    RistoranteDAO service = new RistoranteDAO();
                    int size = service.countAll();
                    if (service.doSave(r)) {
                        resp.sendRedirect("/FoodOut/ristorante/all");
                        DisponibilitaDAO serviceDisp=new DisponibilitaDAO();
                        for(int i = 0; i< Disponibilita.giorni.length; ++i){
                            Disponibilita d=new Disponibilita();
                            d.setGiorno(Disponibilita.giorni[i]);
                            d.setOraApertura(LocalTime.of(0,0));
                            d.setOraChiusura(LocalTime.of(0,0));
                            serviceDisp.doSave(d,r.getCodice());
                        }
                        String uploadRoot=getUploadPath();
                        try(InputStream fileStream=filePart.getInputStream()){
                            File file=new File(uploadRoot+fileName);
                            Files.copy(fileStream, file.toPath());
                        }
                    }
                    else
                        InternalError();
                    break;
                }
                default:
                    resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Operazione non consentita");
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
}
