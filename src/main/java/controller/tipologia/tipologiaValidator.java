package controller.tipologia;

import controller.http.RequestValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

public class tipologiaValidator {
    static RequestValidator validateForm(HttpServletRequest request){
        RequestValidator validator=new RequestValidator(request);
        validator.assertMatch("nome", Pattern.compile("^[a-zA-Zàèìòù',. ]{1,30}$"), "nome compreso tra 1 e 30 caratteri");
        validator.assertMatch("descrizione", Pattern.compile("^(\\D|\\s|'){1,100}$"), "descrizione compreso tra 1 e 100 caratteri");
        boolean check=request.getParameter("nome").equalsIgnoreCase("Menu");
        validator.gatherError(!check,"La tipologia non può essere 'menu'");
        return validator;
    }

    static RequestValidator validateName(HttpServletRequest request, String value){
        RequestValidator validator=new RequestValidator(request);
        validator.assertMatch(value, Pattern.compile("^[a-zA-Zàèìòù',. ]{1,30}$"), "nome compreso tra 1 e 30 caratteri");
        boolean check=request.getParameter(value).equalsIgnoreCase("Menu");
        validator.gatherError(!check,"la tipologia non può essere 'menu'");
        return validator;
    }

}
