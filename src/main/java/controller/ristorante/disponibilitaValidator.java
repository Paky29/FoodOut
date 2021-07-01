package controller.ristorante;

import controller.http.RequestValidator;
import model.disponibilita.Disponibilita;
import model.disponibilita.DisponibilitaDAO;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalTime;

public class disponibilitaValidator {
    static RequestValidator validateForm(HttpServletRequest request) {
        RequestValidator validator = new RequestValidator(request);
        DisponibilitaDAO dispon=new DisponibilitaDAO();
        for (int i = 0; i < Disponibilita.giorni.length; i++)
            if (request.getParameter("chiuso" + i) == null) {
                String apertura=request.getParameter("apertura" + i);
                String chiusura=request.getParameter("chiusura" + i);
                boolean a=validator.required(apertura);
                boolean c=validator.required(chiusura);
                validator.gatherError(a && c, "Richiesti sia orario apertura sia orario chiusura per " + Disponibilita.giorni[i]);
                if((apertura!=null && !apertura.isBlank()) && (chiusura!=null && !chiusura.isBlank())) {
                    LocalTime ap = LocalTime.parse(apertura);
                    LocalTime ch = LocalTime.parse(chiusura);
                    boolean vincolo = ap.isBefore(ch);
                    validator.gatherError(vincolo, "Orario apertura deve essere precedente ad orario chiusura" + Disponibilita.giorni[i]);
                }
            }

        return validator;
    }
}
