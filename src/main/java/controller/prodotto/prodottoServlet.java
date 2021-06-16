package controller.prodotto;

import controller.http.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class prodottoServlet extends controller{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path=getPath(req);
        switch (path) {
            case "/create":
                break;
            case "/update"://mostra form con informazioni modificabili
                break;
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path=getPath(req);
        switch(path){
            case "/create":
                break;
            case "/update"://modifica prodotto
                break;
        }
    }
}
