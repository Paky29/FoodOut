package controller;


import model.disponibilita.Disponibilita;
import model.ordine.Ordine;
import model.ordine.OrdineDAO;
import model.prodotto.Prodotto;
import model.prodotto.ProdottoDAO;
import model.ristorante.Ristorante;
import model.ristorante.RistoranteDAO;
import model.tipologia.Tipologia;
import model.utente.Utente;
import model.utente.UtenteDAO;
import model.utility.Paginator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

@WebServlet("/Tryservlet")
public class TryServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) {

        try {
            RistoranteDAO service1=new RistoranteDAO();
            ArrayList<Ristorante> r= null;
            r = service1.doRetrievebyScontoDisp("Milano", new Paginator(1,10));
            for(Ristorante ris: r){
                System.out.println(ris.getNome());
                System.out.println(ris.getTassoConsegna());
                for(Tipologia t: ris.getTipologie())
                    System.out.println(t.getNome());
                for(Disponibilita d: ris.getGiorni()){
                    System.out.println(d.getGiorno());
                    System.out.println(d.getOraApertura());
                    System.out.println(d.getOraChiusura());
                }
            }

            RistoranteDAO service=new RistoranteDAO();

            ProdottoDAO service2=new ProdottoDAO();
            Prodotto p= service2.doRetrievebyId( 5);
            Ristorante r1=service.doRetrieveById(3);
            service2.updateValidita(p, false);
            Ristorante r2=service.doRetrieveById(3);
            ArrayList<Tipologia> tips1=r1.getTipologie();
            ArrayList<Tipologia> tips2=r2.getTipologie();
            service2.updateValidita(p, true);
            Ristorante r3=service.doRetrieveById(3);
            ArrayList<Tipologia> tips3=r3.getTipologie();

            UtenteDAO service4=new UtenteDAO();
            Utente u=new Utente();
            u.setEmail("ciao@foodout.com");
            u.setPassword("mico");
            u.setSaldo(1000);
            u.setNome("Mario");
            u.setCognome("Rossi");
            u.setCitta("Napoli");
            u.setProvincia("NA");
            u.setVia("Merg");
            u.setCivico(3);

            OrdineDAO service3=new OrdineDAO();
            Ordine o=new Ordine();
            o.setDataOrdine(LocalDate.of(2000,10,19));
            o.setNota("ciao");
            o.setTotale(50);
            o.setGiudizio("Buono");
            o.setVoto(5);
            o.setOraPartenza(LocalTime.of(10,30,30));
            o.setOraArrivo(LocalTime.of(11,30,30));
            o.setMetodoPagamento("cash");
            o.setRistorante(service.doRetrieveById(2));
            o.setConsegnato(false);
            service4.doSave(u);
            o.setUtente(service4.doRetrieveByEmailAndPassword("ciao@foodout.com", "mico"));
            ArrayList<Ordine> ordini=new ArrayList<>();
            ordini.add(o);
            u.setOrdini(ordini);
            service3.doSave(o);
            service3.doUpdate(o);
            Utente unico=service4.doRetrieveByEmailAndPassword("ciao@foodout.com", "mico");


            request.setAttribute("utente", unico);

            RequestDispatcher dispatcher=request.getRequestDispatcher("/WEB-INF/presentation.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException | IOException | ServletException e) {
            e.printStackTrace();
        }


    }
}
