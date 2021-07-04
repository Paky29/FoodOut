package controller.prodotto;

import controller.http.CommonValidator;
import controller.http.InvalidRequestException;
import controller.http.RequestValidator;
import controller.http.controller;
import model.prodotto.Prodotto;
import model.prodotto.ProdottoDAO;
import model.ristorante.Ristorante;
import model.ristorante.RistoranteDAO;
import model.tipologia.Tipologia;

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
import java.nio.file.Paths;
import java.sql.SQLException;

@WebServlet(name="prodottoServlet", value="/prodotto/*")
@MultipartConfig
public class prodottoServlet extends controller{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path=getPath(req);
        try {
            switch (path) {
                case "/update" : {//validare id ristorante
                    validate(CommonValidator.validateId(req));
                    int codice = Integer.parseInt(req.getParameter("id"));
                    int codiceRis = Integer.parseInt(req.getParameter("idRis"));
                    RistoranteDAO serviceRis = new RistoranteDAO();
                    ProdottoDAO serviceProd = new ProdottoDAO();
                    req.setAttribute("ristorante", serviceRis.doRetrieveById(codiceRis));
                    req.setAttribute("prodotto", serviceProd.doRetrievebyId(codice));
                    req.getRequestDispatcher(view("prodotto/update-prod")).forward(req, resp);
                    break;
                }
                case "/delete":
                    break;
            }
        }
        catch (SQLException e) {
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
        try{
        switch(path){
            case "/create":
                HttpSession session=req.getSession();
                authorizeUtente(session);
                validate(prodottoValidator.validateForm(req));
                validate(CommonValidator.validateId(req));
                Prodotto pr=new Prodotto();
                pr.setNome(req.getParameter("nome"));
                pr.setPrezzo(Float.parseFloat(req.getParameter("prezzo")));
                pr.setSconto(Integer.parseInt(req.getParameter("sconto")));
                Tipologia t=new Tipologia();
                t.setNome(req.getParameter("tipologia"));
                pr.setTipologia(t);
                pr.setInfo(req.getParameter("info"));
                pr.setIngredienti(req.getParameter("ingredienti"));
                pr.setValido(true);
                Part filePart = req.getPart("urlImmagine");
                String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                pr.setUrlImmagine(fileName);
                Ristorante r=new Ristorante();
                r.setCodice(Integer.parseInt(req.getParameter("id")));
                pr.setRistorante(r);
                ProdottoDAO service = new ProdottoDAO();
                if (service.doSave(pr)) {
                    if(req.getParameter("button").equals("again"))
                        resp.sendRedirect("/FoodOut/ristorante/add-prodmenu?id=" + r.getCodice());
                    else
                        resp.sendRedirect("/FoodOut/ristorante/all");
                    String uploadRoot = getUploadPath();
                    try (InputStream fileStream = filePart.getInputStream()) {
                        File file = new File(uploadRoot + fileName);
                        Files.copy(fileStream, file.toPath());
                    }
                }
                else
                    InternalError();

                    break;
            case "/update"://modifica prodotto
                break;
            case "/delete":
                break;
            }
        } catch (SQLException e) {
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
