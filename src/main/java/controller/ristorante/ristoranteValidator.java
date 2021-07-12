package controller.ristorante;

import controller.http.RequestValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.regex.Pattern;

public class ristoranteValidator {
    static RequestValidator validateForm(HttpServletRequest request){
        RequestValidator validator=new RequestValidator(request);
        validator.assertMatch("nome", Pattern.compile("^([a-zA-Z]|\\s|[è,à,ò,ù,ì,À, Ò, È, Ù, Ì]|'){1,30}$"), "nome compreso tra 1 e 30 caratteri");
        validator.assertMatch("provincia", Pattern.compile("^([a-zA-Z]|\\s|[è,à,ò,ù,ì,À, Ò, È, Ù, Ì]|'){1,30}$"), "provincia compreso tra 1 e 30 caratteri");
        validator.assertMatch("citta", Pattern.compile("^([a-zA-Z]|\\s|[è,à,ò,ù,ì,À, Ò, È, Ù, Ì]|'){1,30}$"), "citta compreso tra 1 e 30 caratteri");
        validator.assertMatch("via", Pattern.compile("^(\\w|\\s|[è,à,ò,ù,ì,À, Ò, È, Ù, Ì]|'|\\.){1,50}$"), "via compreso tra 1 e 50 caratteri");
        validator.assertInt("civico","civico deve essere un intero");
        validator.assertMatch("info", Pattern.compile("^(\\w|\\s|[è,à,ò,ù,ì,À, Ò, È, Ù, Ì]|'|\\.){1,200}$"), "info compreso tra 1 e 200 caratteri");
        validator.assertPositiveDouble("spesaMinima","spesaMinima deve essere un numero con la virgola");
        validator.assertPositiveDouble("tassoConsegna","tassoConsegna deve essere un numero con la virgola");
        return validator;
    }

    static RequestValidator validateImmagine(HttpServletRequest request, String fileName){
        RequestValidator validator=new RequestValidator(request);
        boolean control=!fileName.isBlank();
        validator.gatherError(control,"inserire un'immagine");
        return validator;

    }

    static RequestValidator validateCitta(HttpServletRequest request) {
        RequestValidator validator=new RequestValidator(request);
        validator.assertMatch("citta",Pattern.compile("^([a-zA-Z]|\\s|[è,à,ò,ù,ì,À, Ò, È, Ù, Ì]|'){1,30}$"),"la citta deve essere inserita");
        return validator;
    }

    static public RequestValidator validateNome(HttpServletRequest request) {
        RequestValidator validator=new RequestValidator(request);
        validator.assertMatch("nome",Pattern.compile("^(\\w|\\s|[è,à,ò,ù,ì,À, Ò, È, Ù, Ì]|'|\\.){1,30}$"),"il nome deve essere inserito");
        return validator;
    }

    static public RequestValidator validateFilters(HttpServletRequest request) {
        String[] tips=request.getParameterValues("tipologia");
        RequestValidator validator=new RequestValidator(request);
        Pattern tipPattern = Pattern.compile("^([a-zA-Z]|\\s|[è,à,ò,ù,ì,À, Ò, È, Ù, Ì]|'){1,30}$");
        boolean allValid = Arrays.stream(tips).allMatch(tip -> tipPattern.matcher(tip).matches());
        validator.gatherError(allValid, "Presente tipologia non valida");
        return validator;
    }

    static public RequestValidator validateFilter(HttpServletRequest request) {
        RequestValidator validator=new RequestValidator(request);
        validator.assertMatch("tipologia",Pattern.compile("^([a-zA-Z]|\\s|[è,à,ò,ù,ì,À, Ò, È, Ù, Ì]|'){1,30}$"),"Presente tipologia non valida");
        return validator;
    }

}
