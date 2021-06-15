package controller.menu;

import controller.http.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class menu extends controller{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path=getPath(req);
        switch (path) {
            case "/create": //mostra form con informazioni menu e prodotti da selezionare
                break;
            case "/update"://mostra form con informazioni modificabili, bottone modifica prodotti
                break;
            case "/edit-prodotti"://mostra select di prodotti da aggiungere e da togliere
                break;

        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path=getPath(req);
        switch (path) {
            case "/create": //crea il menu
                break;
            case "/update"://mostra form con informazioni modificabili, bottone modifica prodotti
                break;
            case "/edit-prodotti"://mostra select di prodotti da aggiungere e da togliere
                break;

        }
    }
}
