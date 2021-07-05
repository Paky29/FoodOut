package controller.menu;

import controller.http.CommonValidator;
import controller.http.InvalidRequestException;
import controller.http.controller;
import controller.tipologia.tipologiaValidator;
import model.menu.Menu;
import model.menu.MenuDAO;
import model.prodotto.ProdottoDAO;

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
        switch (path) {
            case "/update"://mostra form con informazioni modificabili, bottone modifica prodotti
                break;
            case "/delete":
                break;
            case "/edit-prodotti"://mostra select di prodotti da aggiungere e da togliere
                break;

        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path=getPath(req);
        try {
            switch (path) {
                case "/create":
                    HttpSession session = req.getSession();
                    authorizeUtente(session);
                    validate(menuValidator.validateForm(req));
                    validate(CommonValidator.validateId(req));
                    validate(CommonValidator.validateFunctionValue(req));
                    int id=Integer.parseInt(req.getParameter("id"));
                    int function=Integer.parseInt(req.getParameter("function"));
                    Menu m=new Menu();
                    m.setNome(req.getParameter("nome"));
                    m.setPrezzo(Float.parseFloat(req.getParameter("prezzo")));
                    m.setSconto(Integer.parseInt(req.getParameter("sconto")));
                    m.setValido(true);
                    String[] prodotti=req.getParameterValues("prodotti");
                    ProdottoDAO serviceProd=new ProdottoDAO();
                    for(int i=0;i<prodotti.length;++i){
                        m.getProdotti().add(serviceProd.doRetrievebyId(Integer.parseInt(prodotti[i])));
                    }
                    MenuDAO serviceMenu=new MenuDAO();
                    if(serviceMenu.doSave(m)) {
                        if (req.getParameter("button").equals("again"))
                            resp.sendRedirect("/FoodOut/ristorante/add-prodmenu?id=" + id + "&function=" + function);
                        else {
                            if(function==1)
                                resp.sendRedirect("/FoodOut/ristorante/show-menu-admin?id=" + id);
                            else
                                resp.sendRedirect("/FoodOut/ristorante/all");
                        }
                    }
                    else
                        InternalError();
                    break;
                case "/update"://mostra form con informazioni modificabili, bottone modifica prodotti
                    break;
                case "/delete":
                    break;
                case "/edit-prodotti"://mostra select di prodotti da aggiungere e da togliere
                    break;

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
