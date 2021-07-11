package controller.tipologia;

import controller.http.CommonValidator;
import controller.http.InvalidRequestException;
import controller.http.RequestValidator;
import controller.http.controller;
import model.ristorante.Ristorante;
import model.ristorante.RistoranteDAO;
import model.tipologia.Tipologia;
import model.tipologia.TipologiaDAO;
import model.utility.Paginator;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "tipologiaServlet", value = "/tipologia/*")
public class tipologiaServlet extends controller {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = getPath(req);
        try {
            switch (path) {
                case "/create": {
                    authorizeUtente(req.getSession(false));
                    validate(CommonValidator.validateFunctionValue(req));
                    int function = Integer.parseInt(req.getParameter("function"));
                    req.setAttribute("function", function);
                    req.getRequestDispatcher(view("tipologia/create-edit-tipologia")).forward(req, resp);
                    break;
                }
                case "/update": {
                    authorizeUtente(req.getSession(false));
                    validate(CommonValidator.validateFunctionValue(req));
                    int function=Integer.parseInt(req.getParameter("function"));
                    validate(tipologiaValidator.validateName(req,"nome"));
                    TipologiaDAO service=new TipologiaDAO();
                    Tipologia t=service.doRetrieveByNome(req.getParameter("nome"));
                    if(t==null)
                        notFound();
                    req.setAttribute("function", function);
                    req.setAttribute("tipologia", t);
                    req.getRequestDispatcher(view("tipologia/create-edit-tipologia")).forward(req, resp);
                    break;
                }
                case "/delete": {
                    authorizeUtente(req.getSession(false));
                    validate(tipologiaValidator.validateName(req, "nome"));
                    TipologiaDAO service = new TipologiaDAO();
                    String nome = req.getParameter("nome");
                    if (service.doDelete(nome)) {
                        resp.sendRedirect("/FoodOut/tipologia/all");//non sicuro
                    }
                    else
                        InternalError();
                    break;
                }
                case "/all": {
                    authorizeUtente(req.getSession(false));
                    TipologiaDAO service = new TipologiaDAO();
                    if (req.getParameter("page") != null) {
                        validate(CommonValidator.validatePage(req));
                    }
                    int intPage = parsePage(req);
                    int totTip = service.countAll();
                    Paginator paginator = new Paginator(intPage, 6);
                    int size = service.countAll();
                    req.setAttribute("pages", paginator.getPages(size));
                    ArrayList<Tipologia> tipologie = service.doRetrieveAll(paginator);
                    req.setAttribute("tipologie", tipologie);
                    req.setAttribute("totTip", totTip);
                    req.getRequestDispatcher(view("tipologia/show-all")).forward(req, resp);
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
            System.out.println(e.getMessage());
            e.handle(req, resp);
        }

    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = getPath(req);
        try {
            switch (path) {
                case "/create-edit": {
                    HttpSession session = req.getSession(false);
                    authorizeUtente(session);
                    req.setAttribute("back",view("tipologia/create-edit-tipologia"));
                    validate(CommonValidator.validateFunctionValue(req));
                    int function=Integer.parseInt(req.getParameter("function"));
                    req.setAttribute("function",function);
                    TipologiaDAO service = new TipologiaDAO();
                    if(function==1){
                        validate(tipologiaValidator.validateName(req, "nomeVecchio"));
                        Tipologia tip=service.doRetrieveByNome(req.getParameter("nomeVecchio"));
                        req.setAttribute("tipologia",tip);
                    }
                    validate(tipologiaValidator.validateForm(req));
                    Tipologia t = new Tipologia();
                    t.setNome(req.getParameter("nome"));
                    t.setDescrizione(req.getParameter("descrizione"));
                    if(function==0) {
                        if (service.doSave(t)) {
                            resp.sendRedirect("/FoodOut/tipologia/all");
                        } else
                            InternalError();
                    }
                    else{
                        String nomeVecchio=req.getParameter("nomeVecchio");
                        if(t.getNome().equals(nomeVecchio)) {
                            if (service.doUpdate(t, nomeVecchio)) {
                                resp.sendRedirect("/FoodOut/tipologia/all");
                            } else
                                InternalError();
                        }
                        else{
                            Tipologia t_check=service.doRetrieveByNome(t.getNome());
                            if(t_check==null){
                                if (service.doUpdate(t, nomeVecchio)) {
                                    resp.sendRedirect("/FoodOut/tipologia/all");
                                } else
                                    InternalError();
                            }
                            else{
                                throw new InvalidRequestException("Database error", List.of("Nome tipologia già presente"), HttpServletResponse.SC_BAD_REQUEST);
                            }
                        }
                    }
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

