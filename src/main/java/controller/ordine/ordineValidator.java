package controller.ordine;

import controller.http.RequestValidator;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalTime;
import java.util.Locale;
import java.util.regex.Pattern;

public class ordineValidator {
    static RequestValidator validateForm(HttpServletRequest request){
        RequestValidator validator=new RequestValidator(request);
        if(request.getParameter("nota")!=null)
            validator.assertMatch("nota", Pattern.compile("^(\\w|\\s|[è,à,ò,ù,ì,À, Ò, È, Ù, Ì]|'|,|\\.){1,30}$"), "nota compreso tra 1 e 30 caratteri");
        if(request.getParameter("giudizio")!=null)
            validator.assertMatch("giudizio", Pattern.compile("^(\\w|\\s|[è,à,ò,ù,ì,À, Ò, È, Ù, Ì]|'|,|\\.){1,50}$"), "giudizio compreso tra 1 e 50 caratteri");
        if(request.getParameter("voto")!=null)
        validator.assertMatch("voto",Pattern.compile("^[1-5]$"),"voto deve essere un numero tra 1 e 5");
        return validator;
    }

    static RequestValidator validateFormAdmin(HttpServletRequest request){
        RequestValidator validator=new RequestValidator(request);
        boolean conditionPresent=true;
        boolean conditionBefore=true;
        String oraPartenza=request.getParameter("oraPartenza");
        String oraArrivo=request.getParameter("oraArrivo");
        if(!oraArrivo.isBlank()) {
            if(oraPartenza.isBlank())
                conditionPresent=false;
            else {
                conditionBefore = LocalTime.parse(oraPartenza).isBefore(LocalTime.parse(oraArrivo));
            }
        }
        validator.gatherError(conditionBefore, "oraPartenza dev'essere antecedente a oraArrivo");
        validator.gatherError(conditionPresent, "è necessaria oraPartenza per inserire oraArrivo");

        return validator;
    }
}
