package controller.rider;

import controller.http.RequestValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

public class riderValidator {
    static RequestValidator validateForm(HttpServletRequest request) {
        RequestValidator validator = new RequestValidator(request);
        validator.checkLength("email", 50, "email deve essere al massimo di 50 caratteri");
        validator.assertMatch("email", Pattern.compile("^(([\\w\\.\\-]+)@foodout\\.rider\\.it)$"), "email dev'essere del formato *@foodout.rider.it");
        validator.assertMatch("pw", Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&\\-\\_])[A-Za-z\\d@$!%*?&\\-\\_]{8,}$"), "password deve essere di minimo otto caratteri con almeno una lettere maiuscola, una lettera minuscola, un numero e un carattere speciale");
        validator.assertMatch("veicolo", Pattern.compile("^\\w{1,20}$"), "veicolo compreso tra 1 e 20 caratteri");
        validator.assertMatch("citta", Pattern.compile("^(\\w|\\s){1,20}$"), "citta compreso tra 1 e 20 caratteri");
        return validator;
    }
}
