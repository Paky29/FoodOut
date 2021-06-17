package controller.http;

import model.utility.RiderSession;
import model.utility.UtenteSession;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public interface ErrorHandler {

    default void authenticateUtente(HttpSession session) throws InvalidRequestException{
        if(session == null || session.getAttribute("utenteSession")==null){
            throw new InvalidRequestException("Errore autenticazione", List.of("Non sei autenticata"), HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    default void authenticateRider(HttpSession session) throws InvalidRequestException{
        if(session == null || session.getAttribute("riderSession")==null){
            throw new InvalidRequestException("Errore autenticazione", List.of("Non sei autenticata"), HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    default void authorizeUtente(HttpSession session) throws InvalidRequestException{
        authenticateUtente(session);
        UtenteSession utenteSession=(UtenteSession) session.getAttribute("utenteSession");
        if(!utenteSession.isAdmin()){
            throw new InvalidRequestException("Errore autorizzazione", List.of("Azione non consentita"), HttpServletResponse.SC_FORBIDDEN);
        }
    }

    default void InternalError() throws InvalidRequestException{
        List<String> errors=List.of("Un errore imprevisto è accaduto","Riprova più tardi");
        throw new InvalidRequestException("Errore interno", errors, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

    default void notFound() throws InvalidRequestException{
        throw new InvalidRequestException("Erroe interno", List.of("Risorsa non trovata"),HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

    default void notAllowed() throws InvalidRequestException{
        throw new InvalidRequestException("Operazione non consentita", List.of("Operazione non permessa"),HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }
}
