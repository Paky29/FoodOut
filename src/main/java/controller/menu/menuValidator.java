package controller.menu;

import controller.http.RequestValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

public class menuValidator {
    static RequestValidator validateForm(HttpServletRequest request){
        RequestValidator validator=new RequestValidator(request);
        validator.assertMatch("nome", Pattern.compile("^(\\w|\\s|[è,à,ò,ù,ì,À, Ò, È, Ù, Ì]|'){1,30}$"), "nome compreso tra 1 e 30 caratteri");
        validator.assertPositiveDouble("prezzo","prezzo deve essere un numero con la virgola");
        validator.assertMatch("sconto", Pattern.compile("^((100)|[0-9]?[0-9]?)$"),"sconto deve essere un intero tra 0 e 100");
        String[] prodotti=request.getParameterValues("prodotti");
        boolean size;
        if(prodotti==null)
            size=false;
        else
            size=prodotti.length>=2;
        validator.gatherError(size,"il menu deve contenere almeno due prodotti");
        if(size)
            validator.assertInts("prodotti","errore nei codici dei prodotti");
        return validator;
    }

    public static RequestValidator validateIdRis(HttpServletRequest request){
        RequestValidator validator= new RequestValidator(request);
        validator.assertInt("idRis", "IdRis deve essere un intero");
        return validator;
    }
}

