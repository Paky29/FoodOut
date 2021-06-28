package controller.ristorante;

import controller.http.InvalidRequestException;
import controller.http.controller;
import model.ristorante.Ristorante;
import model.ristorante.RistoranteDAO;
import model.utility.Paginator;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;


@WebServlet (name="ristoranteServlet", value="/ristorante/*")
@MultipartConfig
public class ristoranteServlet extends controller {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path=getPath(req);
        try {
            switch (path) {
                case "/":
                    break;
                case "/all": {
                    authorizeUtente(req.getSession());
                    RistoranteDAO service=new RistoranteDAO();
                    int intPage=parsePage(req);
                    Paginator paginator=new Paginator(intPage,2);
                    System.out.println(intPage);
                    int size=service.countAll();
                    System.out.println(size);
                    req.setAttribute("pages",paginator.getPages(size));
                    ArrayList<Ristorante> ristoranti = service.doRetrieveAll(paginator);
                    System.out.println("ok");
                    req.setAttribute("ristoranti", ristoranti);
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
                    int id=Integer.parseInt(req.getParameter("id"));
                    RistoranteDAO ristoranteDAO=new RistoranteDAO();
                    Ristorante r=ristoranteDAO.doRetrieveById(id);
                    req.setAttribute("ristorante", r);
                    req.setAttribute("ristoranteUrl", "Foodout/covers/" + r.getUrlImmagine());
                    req.getRequestDispatcher(view("ristorante/info-admin")).forward(req, resp);
                    break;
                }
                case "/show-recensioni":
                    req.getRequestDispatcher(view("ristorante/recensioni")).forward(req, resp);
                    break;
                case "/add":
                    break;
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
        String path=getPath(req);
        switch(path){
            case "/":
                break;
            case "/add-pref":
                break;
            case "/update":
                break;
            case "/add":
                break;
            case "/delete":
                break;
            default:
                resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED,"Operazione non consentita");
        }
    }
}
