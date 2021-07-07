package controller.ordineItem;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import controller.http.InvalidRequestException;
import controller.http.controller;

@WebServlet(name="ordineItemServlet", value="/ordineItem/*")
public class ordineItemServlet extends controller {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path=getPath(req);
        try {
            switch (path) {
                case "/insert-amount"://inserisci la quantità del prodotto o menu, visualizza per i prodotti i menu dove si trova, mentre per i menu i nomi dei prodtti da cui è composto
                    break;
                default:
                    notFound();
            }
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
                case "/add-item":
                    break;//aggiungi l'ordine item all'ordine
                case "/remove-item":
                    break;//rimuovi l'ordine item dall'ordine
                default:
                    notAllowed();
            }
        }catch (InvalidRequestException e) {
            log(e.getMessage());
            System.out.println(e.getMessage());
            e.handle(req,resp);
        }
    }
}
