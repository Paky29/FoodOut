package controller.ristorante;

import controller.http.RequestValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

public class disponibilitaValidator {
    static RequestValidator validateForm(HttpServletRequest request){
        RequestValidator validator=new RequestValidator(request);
        return validator;
    }
}
