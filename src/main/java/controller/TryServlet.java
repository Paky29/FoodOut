package controller;


import model.prodotto.Prodotto;
import model.prodotto.ProdottoDAO;
import model.ristorante.Ristorante;
import model.ristorante.RistoranteDAO;
import model.tipologia.Tipologia;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/Tryservlet")
public class TryServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        RistoranteDAO service=new RistoranteDAO();
        int id=1;
        Ristorante r= null;
        try {
            r = service.doRetrieveById(id);
            ProdottoDAO service1=new ProdottoDAO();
            r.setProdotti(service1.doRetrieveByRistorante(id));
            System.out.println(r.getCodice());
            System.out.println(r.getNome());
            System.out.println(r.getProvincia());
            System.out.println(r.getCitta());
            System.out.println(r.getCivico());
            System.out.println(r.getSpesaMinima());
            System.out.println(r.getTassoConsegna());
            System.out.println(r.getGiorni().get(0).getGiorno()+" "+r.getGiorni().get(0).getOraApertura());
            System.out.println(r.getProdotti().get(0).getNome()+" "+r.getProdotti().get(0).getTipologia().getNome());
            System.out.println(r.getProdotti().get(1).getNome()+" "+r.getProdotti().get(1).getTipologia().getNome());
            for(Tipologia t: r.getTipologie()){
                System.out.println("Tipologia: " + t.getNome());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
