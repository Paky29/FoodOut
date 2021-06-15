package controller;


import model.menu.MenuDAO;
import model.prodotto.Prodotto;
import model.prodotto.ProdottoDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/Tryservlet")
public class TryServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) {

        try {
            /*UtenteDAO utenteDAO=new UtenteDAO();
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
            //utenteDAO.doSave(u);

            Utente u2=new Utente();
            u2.setEmail("macio@libero.com");
            u2.setPassword("macio");
            u2.setSaldo(500);
            u2.setNome("Carla");
            u2.setCognome("Bruni");
            u2.setCitta("Milano");
            u2.setProvincia("MI");
            u2.setVia("Via Gae Aulenti");
            u2.setCivico(10);
            //utenteDAO.doSave(u2);
           RistoranteDAO ristoranteDAO=new RistoranteDAO();

            ProdottoDAO prodottoDAO=new ProdottoDAO();
            OrdineDAO ordineDAO=new OrdineDAO();
            Prodotto p1=prodottoDAO.doRetrievebyId(1);
            Prodotto p2=prodottoDAO.doRetrievebyId(2);
            Prodotto p3=prodottoDAO.doRetrievebyId(3);
            MenuDAO service5=new MenuDAO();
            Menu m1=service5.doRetrieveById(1);
            Ordine ordine1=new Ordine();
            ordine1.setDataOrdine(LocalDate.of(2000,10,19));
            ordine1.setNota("ciao");
            ordine1.setTotale(50);
            ordine1.setGiudizio("Buono");
            ordine1.setVoto(5);
            ordine1.setOraPartenza(LocalTime.of(10,30,30));
            ordine1.setOraArrivo(LocalTime.of(11,30,30));
            ordine1.setMetodoPagamento("cash");
            ordine1.setRistorante(ristoranteDAO.doRetrieveById(1));
            System.out.println("Info servlet: " + ordine1.getRistorante().getInfo());
            ordine1.setConsegnato(false);
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
            OrdineItem oi5=new OrdineItem();
            oi5.setOff(p3);
            oi5.setQuantita(1);
            ordine1.getOrdineItems().add(oi1);
            ordine1.getOrdineItems().add(oi2);
            ordine1.getOrdineItems().add(oi3);
            ordine1.setUtente(u);
            ordineDAO.doSave(ordine1);

            Ordine o1=new Ordine();
            o1.setDataOrdine(LocalDate.of(2000,10,19));
            o1.setNota("ciao");
            o1.setTotale(50);
            o1.setGiudizio("Buono");
            o1.setVoto(5);
            o1.setOraPartenza(LocalTime.of(10,30,30));
            o1.setOraArrivo(LocalTime.of(11,30,30));
            o1.setMetodoPagamento("cash");
            o1.setRistorante(ristoranteDAO.doRetrieveById(1));
            System.out.println("Info servlet: " + o1.getRistorante().getInfo());
            o1.setConsegnato(false);
            o1.getOrdineItems().add(oi1);
            o1.getOrdineItems().add(oi2);
            o1.getOrdineItems().add(oi3);
            o1.setUtente(u);
            ordineDAO.doSave(o1);




            Ordine o=ordineDAO.doRetrieveById(1);
            o.setVoto(4);
            o.setGiudizio("molto buono");
            ordineDAO.updateVG(o);

            o=ordineDAO.doRetrieveById(2);
            o.setVoto(2);
            o.setGiudizio("na chiavica");
            ordineDAO.updateVG(o);

            Ristorante r_saved=ristoranteDAO.doRetrieveById(1);
            System.out.println("Ristorante 1");
            System.out.println("Nome: " + r_saved.getNome());
            System.out.println("Provincia: " + r_saved.getProvincia());
            System.out.println("Citta: " + r_saved.getCitta());
            System.out.println("Via: " + r_saved.getVia());
            System.out.println("Civico: " + r_saved.getCivico());
            System.out.println("Tasso C: " + r_saved.getTassoConsegna());
            System.out.println("Info: " + r_saved.getInfo());
            System.out.println("Spesa M: " + r_saved.getSpesaMinima());
            System.out.println("Rating: " + r_saved.getRating());
            TipologiaDAO tipologiaDAO=new TipologiaDAO();
            Tipologia t1=tipologiaDAO.doRetrieveByNome("italiano");
            Prodotto p1=new Prodotto();
            p1.setNome("Fiorentina");
            p1.setIngredienti("Carne bovina, olio, sale");
            p1.setInfo("No vegano");
            p1.setPrezzo((float)25.50);
            p1.setSconto(0);
            p1.setValido(true);
            p1.setUrlImmagine("ciao.png");
            p1.setRistorante(r_saved);
            p1.setTipologia(t1);

            ProdottoDAO prodottoDAO=new ProdottoDAO();
            prodottoDAO.doSave(p1);
            ArrayList<Prodotto> prodottos=prodottoDAO.doRetrieveByRistorante(1);
            int i=0;
            for(Prodotto p: prodottos) {
                System.out.println("Prodotto" + i);
                System.out.println("Nome: " + p.getNome());
                System.out.println("Ingredienti: " + p.getIngredienti());
                System.out.println("Prezzo: " + p.getPrezzo());
                System.out.println("Sconto: " + p.getSconto());
                System.out.println("Info: " + p.getInfo());
                System.out.println("Valido: " + p.isValido());
                System.out.println("URL: " + p.getUrlImmagine());
                i++;
            }

            RistoranteDAO ristoranteDAO=new RistoranteDAO();
            ristoranteDAO.savePreferenza(1,1);
            ristoranteDAO.savePreferenza(2,1);
            ristoranteDAO.deletePreferenza(1,1);

            DisponibilitaDAO disponibilitaDAO=new DisponibilitaDAO();
            Disponibilita d=new Disponibilita();
            disponibilitaDAO.doDelete("martedi",1);

            Ristorante r_saved=ristoranteDAO.doRetrieveById(1);
            System.out.println("Nome: " + r_saved.getNome());
            System.out.println("Provincia: " + r_saved.getProvincia());
            System.out.println("Citta: " + r_saved.getCitta());
            System.out.println("Via: " + r_saved.getVia());
            System.out.println("Civico: " + r_saved.getCivico());
            System.out.println("Tasso C: " + r_saved.getTassoConsegna());
            System.out.println("Info: " + r_saved.getInfo());
            System.out.println("Spesa M: " + r_saved.getSpesaMinima());
            System.out.println("Rating: " + r_saved.getRating());
            for(Disponibilita g: r_saved.getGiorni()){
                System.out.println("Giorno: " + g.getGiorno());
                System.out.println("oraA: " + g.getOraApertura());
                System.out.println("oraC: " +g.getOraChiusura());
            }



            ArrayList<Utente> utentes = utenteDAO.doRetrievebyPref(1, new Paginator(1,100));
            for(Utente u1: utentes){
                System.out.println("Utente");
                System.out.println("Nome: " + u1.getNome());
                System.out.println("Cognome: " + u1.getCognome());
                System.out.println("Citta: " + u1.getCitta());
                System.out.println("Via: " + u1.getVia());
                System.out.println("Civico: " + u1.getCivico());
                System.out.println("Email: " + u1.getEmail());
                System.out.println("Saldo: " + u1.getSaldo());
            }

            ProdottoDAO prodottoDAO = new ProdottoDAO();
            TipologiaDAO tipologiaDAO=new TipologiaDAO();
            RistoranteDAO ristoranteDAO=new RistoranteDAO();
            Prodotto prodotto=prodottoDAO.doRetrievebyId(1);
            prodottoDAO.updateValidita(prodotto,false);
            ArrayList<Prodotto> prodottos=prodottoDAO.doRetrieveByRistorante(1);
            for(Prodotto p: prodottos) {
                System.out.println("Nome: " + p.getNome());
                System.out.println("Ingredienti: " + p.getIngredienti());
                System.out.println("Prezzo: " + p.getPrezzo());
                System.out.println("Sconto: " + p.getSconto());
                System.out.println("Info: " + p.getInfo());
                System.out.println("Valido: " + p.isValido());
                System.out.println("URL: " + p.getUrlImmagine());
            }

            Ristorante r=ristoranteDAO.doRetrieveById(1);
            for(Tipologia t: r.getTipologie()){
                System.out.println("Nome: " + t.getNome());
                System.out.println("Descrizione: " + t.getDescrizione());

            }*/

            /*RistoranteDAO ristoranteDAO=new RistoranteDAO();
            UtenteDAO utenteDAO=new UtenteDAO();
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
            utenteDAO.doSave(u);

            ProdottoDAO prodottoDAO=new ProdottoDAO();
            OrdineDAO ordineDAO=new OrdineDAO();
            Prodotto p1=prodottoDAO.doRetrievebyId(1);
            Prodotto p2=prodottoDAO.doRetrievebyId(2);
            Prodotto p3=prodottoDAO.doRetrievebyId(3);
            MenuDAO service5=new MenuDAO();
            Menu m1=service5.doRetrieveById(1);
            Ordine ordine1=new Ordine();
            ordine1.setDataOrdine(LocalDate.of(2000,10,19));
            ordine1.setNota("ciao");
            ordine1.setTotale(50);
            ordine1.setGiudizio("Buono");
            ordine1.setVoto(5);
            ordine1.setOraPartenza(LocalTime.of(10,30,30));
            ordine1.setOraArrivo(LocalTime.of(11,30,30));
            ordine1.setMetodoPagamento("cash");
            ordine1.setRistorante(ristoranteDAO.doRetrieveById(1));
            System.out.println("Info servlet: " + ordine1.getRistorante().getInfo());
            ordine1.setConsegnato(false);
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
            OrdineItem oi5=new OrdineItem();
            oi5.setOff(p3);
            oi5.setQuantita(1);
            ordine1.getOrdineItems().add(oi1);
            ordine1.getOrdineItems().add(oi2);
            ordine1.getOrdineItems().add(oi3);
            ordine1.setUtente(u);


            RiderDAO riderDAO=new RiderDAO();
            Rider r=new Rider();
            r.setEmail("rider@rider.it");
            r.setPassword("rider");
            r.setVeicolo("bici");
            r.setCitta("Napoli");
            riderDAO.doSave(r);

            Rider r2=new Rider();
            r2.setEmail("roder@roder.it");
            r2.setPassword("roder");
            r2.setVeicolo("moto");
            r2.setCitta("Milano");
            riderDAO.doSave(r2);

            ordineDAO.doSave(ordine1);
            ordineDAO.doSave(ordine1);
            ordineDAO.updateRider(1,1);
            ordineDAO.updateRider(2,2);

            TurnoDAO turnoDAO=new TurnoDAO();
            Turno turno=new Turno();
            turno.setGiorno("sabato");
            turno.setOraInizio(LocalTime.of(12,0,0));
            turno.setOraFine(LocalTime.of(12,30,0));
            turnoDAO.doSave(turno,1);

            Turno turno2=new Turno();
            turno2.setGiorno("sabato");
            turno2.setOraInizio(LocalTime.of(11,0,0));
            turno2.setOraFine(LocalTime.of(13,30,0));
            turnoDAO.doSave(turno2,2);

            r.setVeicolo("carrarmato");
            riderDAO.doUpdate(r);

            Rider rider=riderDAO.doRetrievebyId(1);
            System.out.println("Email:" + rider.getEmail());
            System.out.println("Password:" + rider.getPassword());
            System.out.println("Veicolo:" + rider.getVeicolo());
            System.out.println("Citta:" + rider.getCitta());
            for (Turno t : rider.getTurni()) {
                System.out.println("Giorno:" + t.getGiorno());
                System.out.println("OraI:" + t.getOraInizio());
                System.out.println("OraF:" + t.getOraFine());
            }

            for (Ordine o : rider.getOrdini()) {
                System.out.println("Codice:" + o.getCodice());
                System.out.println("Data:" + o.getDataOrdine().toString());
                System.out.println("Totale:" + o.getTotale());
                System.out.println("Nota:" + o.getNota());
            }*/

            ProdottoDAO prodottoDAO=new ProdottoDAO();
            ArrayList<Prodotto> prodottos=new ArrayList<>();
            prodottos.add(prodottoDAO.doRetrievebyId(1));
            MenuDAO menuDAO=new MenuDAO();
            menuDAO.deleteProducts(1,prodottos);
            for(Prodotto p: menuDAO.doRetrieveById(1).getProdotti()){
                System.out.println("Nome: " + p.getNome());
                System.out.println("Ingredienti: " + p.getIngredienti());
                System.out.println("Prezzo: " + p.getPrezzo());
                System.out.println("Sconto: " + p.getSconto());
                System.out.println("Info: " + p.getInfo());
                System.out.println("Valido: " + p.isValido());
                System.out.println("URL: " + p.getUrlImmagine());
            }








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
