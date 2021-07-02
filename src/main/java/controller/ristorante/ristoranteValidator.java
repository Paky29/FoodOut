package controller.ristorante;

import controller.http.RequestValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

public class ristoranteValidator {
    static RequestValidator validateForm(HttpServletRequest request){
        RequestValidator validator=new RequestValidator(request);
        System.out.println(request.getParameter("provincia"));
        validator.assertMatch("nome", Pattern.compile("^[a-zA-Zàèìòù',. ]{1,30}$"), "nome compreso tra 1 e 30 caratteri");
        validator.assertMatch("provincia", Pattern.compile("^(\\D|\\s|'){1,30}$"), "provincia compreso tra 1 e 30 caratteri");
        validator.assertMatch("citta", Pattern.compile("^(\\w|\\s|[èàòùìÀÒÈÙÌ]|\'){1,30}$"), "citta compreso tra 1 e 30 caratteri");
        validator.assertMatch("via", Pattern.compile("^(\\w|\\s|[èàòùìÀÒÈÙÌ]|\'|\\.){1,50}$"), "via compreso tra 1 e 50 caratteri");
        validator.assertInt("civico","civico deve essere un intero");
        validator.assertMatch("info", Pattern.compile("^(\\w|\\s|[èàòùìÀÒÈÙÌ]|\'|,|\\.){1,200}$"), "info compreso tra 1 e 200 caratteri");
        validator.assertPositiveDouble("spesaMinima","spesaMinima deve essere un numero con la virgola");
        validator.assertPositiveDouble("tassoConsegna","tassoConsegna deve essere un numero con la virgola");
        return validator;
    }
}
