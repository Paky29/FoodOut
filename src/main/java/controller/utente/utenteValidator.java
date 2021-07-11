package controller.utente;

import controller.http.RequestValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

public class utenteValidator {

    public static RequestValidator validateForm(HttpServletRequest request) {
        RequestValidator validator = new RequestValidator(request);
        validator.checkLength("email", 50, "email deve essere al massimo di 50 caratteri");
        validator.assertEmail("email", "email non valida");
        validator.assertMatch("pw", Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&\\-\\_])[A-Za-z\\d@$!%*?&\\-\\_]{8,}$"), "password deve essere di minimo otto caratteri con almeno una lettere maiuscola, una lettera minuscola, un numero e un carattere speciale");
        validator.assertMatch("nome", Pattern.compile("^([a-zA-Z]|\\s|[è,à,ò,ù,ì,À, Ò, È, Ù, Ì]|'){1,30}$"), "nome compreso tra 1 e 30 caratteri");
        validator.assertMatch("cognome", Pattern.compile("^([a-zA-Z]|\\s|[è,à,ò,ù,ì,À, Ò, È, Ù, Ì]|'){1,30}$"), "cognome compreso tra 1 e 30 caratteri");
        validator.assertMatch("provincia", Pattern.compile("^([a-zA-Z]|\\s|[è,à,ò,ù,ì,À, Ò, È, Ù, Ì]|'){1,30}$"), "provincia compreso tra 1 e 30 caratteri");
        validator.assertMatch("citta", Pattern.compile("^([a-zA-Z]|\\s|[è,à,ò,ù,ì,À, Ò, È, Ù, Ì]|'){1,30}$"), "citta compreso tra 1 e 30 caratteri");
        validator.assertMatch("via", Pattern.compile("^(\\w|\\s|[è,à,ò,ù,ì,À, Ò, È, Ù, Ì]|'|\\.){1,50}$"), "via compreso tra 1 e 50 caratteri");
        validator.assertInt("civico", "civico dev'essere un intero");
        return validator;
    }

    static RequestValidator validateUpdate(HttpServletRequest request) {
        RequestValidator validator = new RequestValidator(request);
        validator.checkLength("email", 50, "email deve essere al massimo di 50 caratteri");
        validator.assertEmail("email", "email non valida");
        validator.assertMatch("nome", Pattern.compile("^(\\w|\\s|[è,à,ò,ù,ì,À, Ò, È, Ù, Ì]){1,30}$"), "nome compreso tra 1 e 30 caratteri");
        validator.assertMatch("cognome", Pattern.compile("^(\\w|\\s|[è,à,ò,ù,ì,À, Ò, È, Ù, Ì]|'){1,30}$"), "cognome compreso tra 1 e 30 caratteri");
        validator.assertMatch("provincia", Pattern.compile("^(\\w|\\s|[è,à,ò,ù,ì,À, Ò, È, Ù, Ì]|'){1,30}$"), "provincia compreso tra 1 e 30 caratteri");
        validator.assertMatch("citta", Pattern.compile("^(\\w|\\s|[è,à,ò,ù,ì,À, Ò, È, Ù, Ì]|'){1,30}$"), "citta compreso tra 1 e 30 caratteri");
        validator.assertMatch("via", Pattern.compile("^(\\w|\\s|[è,à,ò,ù,ì,À, Ò, È, Ù, Ì]|'|\\.){1,50}$"), "via compreso tra 1 e 50 caratteri");
        validator.assertInt("civico", "via compreso tra 1 e 50 caratteri");
        return validator;
    }

    static RequestValidator validateUpdatePassword(HttpServletRequest request){
        RequestValidator validator= new RequestValidator(request);
        validator.assertMatch("old_pw", Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&\\-\\_])[A-Za-z\\d@$!%*?&\\-\\_]{8,}$"), "password deve essere di minimo otto caratteri con almeno una lettere maiuscola, una lettera minuscola, un numero e un carattere speciale");
        validator.assertMatch("new_pw", Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&\\-\\_])[A-Za-z\\d@$!%*?&\\-\\_]{8,}$"), "password deve essere di minimo otto caratteri con almeno una lettere maiuscola, una lettera minuscola, un numero e un carattere speciale");
        validator.assertMatch("conf_pw", Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&\\-\\_])[A-Za-z\\d@$!%*?&\\-\\_]{8,}$"), "password deve essere di minimo otto caratteri con almeno una lettere maiuscola, una lettera minuscola, un numero e un carattere speciale");
        return validator;
    }

    public static RequestValidator validateLogin(HttpServletRequest request){
        RequestValidator validator=new RequestValidator(request);
        validator.assertEmail("email", "Formato email non valido");
        validator.assertMatch("pw", Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&\\-\\_])[A-Za-z\\d@$!%*?&\\-\\_]{8,}$"), "Formato password non valido");
        return validator;
    }

    static RequestValidator validateDeposit(HttpServletRequest request){
        RequestValidator validator=new RequestValidator(request);
        validator.assertPositiveDouble("deposito","il deposito deve essere un decimale positivo");
        return validator;
    }

}
