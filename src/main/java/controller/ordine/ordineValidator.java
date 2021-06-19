package controller.ordine;

import controller.http.RequestValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

public class ordineValidator {
    static RequestValidator validateForm(HttpServletRequest request){
        RequestValidator validator=new RequestValidator(request);
        if(request.getParameter("nota")!=null)
            validator.assertMatch("nota", Pattern.compile("^(\\w|\\s){1,30}$"), "nota compreso tra 1 e 30 caratteri");
        if(request.getParameter("giudizio")!=null)
            validator.assertMatch("giudizio", Pattern.compile("^(\\w|\\s){1,50}$"), "giudizio compreso tra 1 e 50 caratteri");
        if(request.getParameter("voto")!=null)
        validator.assertMatch("voto",Pattern.compile("^[1-5]$"),"voto deve essere un numero tra 1 e 5");
        return validator;
    }
}
