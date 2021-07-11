package controller.ordineItem;

import controller.http.RequestValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

public class ordineItemValidator {
    static RequestValidator validateForm(HttpServletRequest request){
        RequestValidator validator=new RequestValidator(request);
        validator.assertMatch("quantita", Pattern.compile("^([1-9]|[1-9]\\d)$"),"quantita deve essere un numero intero tra 1 e 99");
        return validator;
    }
}