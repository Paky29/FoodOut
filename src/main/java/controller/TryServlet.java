package controller;


import model.disponibilita.Disponibilita;
import model.menu.Menu;
import model.menu.MenuDAO;
import model.ordine.Ordine;
import model.ordine.OrdineDAO;
import model.ordine.OrdineItem;
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

            RistoranteDAO service=new RistoranteDAO();
            ProdottoDAO service2=new ProdottoDAO();
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
            Prodotto p1=service2.doRetrievebyId(1);
            Prodotto p2=service2.doRetrievebyId(2);
            MenuDAO service5=new MenuDAO();
            Menu m1=service5.doRetrieveById(1);
            Menu m2=service5.doRetrieveById(2);
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
            OrdineItem oi1=new OrdineItem();
            oi1.setOff(p1);
            oi1.setQuantita(2);
            OrdineItem oi2=new OrdineItem();
            oi2.setOff(p2);
            oi2.setQuantita(1);
            OrdineItem oi3=new OrdineItem();
            oi3.setOff(m1);
            oi3.setQuantita(3);
            OrdineItem oi4=new OrdineItem();
            oi4.setOff(m2);
            oi4.setQuantita(1);
            o.getOrdineItems().add(oi1);
            o.getOrdineItems().add(oi2);
            o.getOrdineItems().add(oi3);
            o.getOrdineItems().add(oi4);
            service4.doSave(u);
            o.setUtente(service4.doRetrieveByEmailAndPassword("ciao@foodout.com", "mico"));
            ArrayList<Ordine> ordini=new ArrayList<>();
            service3.doSave(o);

            Utente unico=service4.doRetrieveByEmailAndPassword("ciao@foodout.com", "mico");
            unico.getOrdini().add(service3.doRetrieveById(1));
            System.out.println(unico.getOrdini().get(0).getOrdineItems().get(0).getOff().getNome() +" "+unico.getOrdini().get(0).getOrdineItems().get(0).getQuantita());
            System.out.println(unico.getOrdini().get(0).getOrdineItems().get(1).getOff().getNome());
            System.out.println(unico.getOrdini().get(0).getOrdineItems().get(2).getOff().getNome());
            System.out.println(unico.getOrdini().get(0).getOrdineItems().get(3).getOff().getNome());
            Menu m= (Menu) unico.getOrdini().get(0).getOrdineItems().get(3).getOff();
            System.out.println(m.getProdotti().get(0).getNome());
            System.out.println(m.getProdotti().get(1).getNome());
            request.setAttribute("utente", unico);

            RequestDispatcher dispatcher=request.getRequestDispatcher("/WEB-INF/presentation.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("ok");
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
