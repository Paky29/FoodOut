package controller.prodotto;

import controller.http.CommonValidator;
import controller.http.InvalidRequestException;
import controller.http.RequestValidator;
import controller.http.controller;
import controller.tipologia.tipologiaValidator;
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
                case "/update" : {
                    validate(CommonValidator.validateId(req));
                    validate(prodottoValidator.validateIdRis(req));
                    int codice = Integer.parseInt(req.getParameter("id"));
                    int codiceRis = Integer.parseInt(req.getParameter("idRis"));
                    RistoranteDAO serviceRis = new RistoranteDAO();
                    ProdottoDAO serviceProd = new ProdottoDAO();
                    req.setAttribute("ristorante", serviceRis.doRetrieveById(codiceRis,true));
                    req.setAttribute("prodotto", serviceProd.doRetrievebyId(codice));
                    req.getRequestDispatcher(view("prodotto/update-prod")).forward(req, resp);
                    break;
                }
                default:notFound();
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
        switch(path) {
            case "/create": {
                HttpSession session = req.getSession();
                authorizeUtente(session);

                validate(prodottoValidator.validateForm(req));
                validate(CommonValidator.validateId(req));
                validate(CommonValidator.validateFunctionValue(req));

                int function=Integer.parseInt(req.getParameter("function"));
                Prodotto pr = new Prodotto();
                pr.setNome(req.getParameter("nome"));
                pr.setPrezzo(Float.parseFloat(req.getParameter("prezzo")));
                pr.setSconto(Integer.parseInt(req.getParameter("sconto")));
                Tipologia t = new Tipologia();
                t.setNome(req.getParameter("tipologia"));
                pr.setTipologia(t);
                pr.setInfo(req.getParameter("info"));
                pr.setIngredienti(req.getParameter("ingredienti"));
                pr.setValido(true);
                Part filePart = req.getPart("urlImmagine");
                String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                if (fileName.isBlank())
                    pr.setUrlImmagine(null);
                else
                    pr.setUrlImmagine(fileName);
                Ristorante r = new Ristorante();
                r.setCodice(Integer.parseInt(req.getParameter("id")));
                pr.setRistorante(r);
                ProdottoDAO service = new ProdottoDAO();
                if (service.doSave(pr)) {
                    if (req.getParameter("button").equals("again"))
                        resp.sendRedirect("/FoodOut/ristorante/add-prodmenu?id=" + r.getCodice() + "&function=" + function);
                    else {
                        if(function==1) {
                            resp.sendRedirect("/FoodOut/ristorante/show-menu-admin?id=" + r.getCodice());
                        }
                        else
                            resp.sendRedirect("/FoodOut/ristorante/all");
                    }

                    if (!fileName.isBlank()) {
                        String uploadRoot = getUploadPath();
                        try (InputStream fileStream = filePart.getInputStream()) {
                            File file = new File(uploadRoot + fileName);
                            Files.copy(fileStream, file.toPath());
                        }
                    }
                } else
                    InternalError();
                break;
        }
            case "/update": {
                authorizeUtente(req.getSession());
                validate(prodottoValidator.validateForm(req));
                validate(CommonValidator.validateId(req));
                int codiceProd=Integer.parseInt(req.getParameter("id"));
                validate(prodottoValidator.validateIdRis(req));
                int codiceRis=Integer.parseInt(req.getParameter("idRis"));
                ProdottoDAO service=new ProdottoDAO();
                if(service.doRetrievebyId(codiceProd)!=null) {
                    Prodotto pr = new Prodotto();
                    pr.setCodice(codiceProd);
                    pr.setNome(req.getParameter("nome"));
                    pr.setPrezzo(Float.parseFloat(req.getParameter("prezzo")));
                    pr.setSconto(Integer.parseInt(req.getParameter("sconto")));
                    Tipologia t = new Tipologia();
                    t.setNome(req.getParameter("tipologia"));
                    pr.setTipologia(t);
                    pr.setInfo(req.getParameter("info"));
                    pr.setIngredienti(req.getParameter("ingredienti"));
                    Part filePart = req.getPart("urlImmagine");
                    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                    if (!fileName.isBlank()) {
                        pr.setUrlImmagine(fileName);
                        if(service.doUpdateWithUrl(pr)){
                            resp.sendRedirect("/FoodOut/ristorante/show-menu-admin?id="+codiceRis);
                            String uploadRoot = getUploadPath();
                            try (InputStream fileStream = filePart.getInputStream()) {
                                File file = new File(uploadRoot + fileName);
                                Files.copy(fileStream, file.toPath());
                            }
                        }
                        else {
                            System.out.println("errore");
                            InternalError();
                        }
                    }
                    else {
                        if (service.doUpdate(pr))
                            resp.sendRedirect("/FoodOut/ristorante/show-menu-admin?id=" + codiceRis);
                        else
                            InternalError();
                    }
                }
                else
                    notFound();
                break;
            }
            case "/update-validita": {
                authorizeUtente(req.getSession());
                RequestValidator rv=CommonValidator.validateId(req);
                System.out.println(req.getParameter("id"));
                for(String s: rv.getErrors())
                    System.out.println(s);
                validate(CommonValidator.validateId(req));
                ProdottoDAO service=new ProdottoDAO();
                Prodotto p=service.doRetrievebyId(Integer.parseInt(req.getParameter("id")));
                if(p!=null) {
                    if(service.updateValidita(p, !p.isValido()))
                        resp.sendRedirect("/FoodOut/ristorante/show-menu-admin?id="+p.getRistorante().getCodice());
                    else
                        InternalError();
                }
                else
                    notFound();
                break;
                }
            default:notFound();
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
