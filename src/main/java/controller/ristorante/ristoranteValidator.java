package controller.ristorante;

import controller.http.RequestValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

public class ristoranteValidator {
    static RequestValidator validateForm(HttpServletRequest request){
        RequestValidator validator=new RequestValidator(request);
        validator.assertMatch("nome", Pattern.compile("^\\w{1,30}$"), "nome compreso tra 1 e 30 caratteri");
        validator.assertMatch("provincia", Pattern.compile("^\\w{1,30}$"), "provincia compreso tra 1 e 30 caratteri");
        validator.assertMatch("citta", Pattern.compile("^\\w{1,30}$"), "citta compreso tra 1 e 30 caratteri");
        validator.assertMatch("via", Pattern.compile("^\\w{1,50}$"), "via compreso tra 1 e 50 caratteri");
        validator.assertInt("civico","civico deve essere un intero");
        validator.assertMatch("info", Pattern.compile("^\\w{1,100}$"), "info compreso tra 1 e 100 caratteri");
        validator.assertDouble("spesaMinima","spesaMinima deve essere un numero con la virgola");
        validator.assertDouble("tassoConsegna","tassoConsegna deve essere un numero con la virgola");
        return validator;
    }
}
