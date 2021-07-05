package controller.http;

import javax.servlet.http.HttpServletRequest;

public class CommonValidator {

    public static RequestValidator validatePage(HttpServletRequest request){
        RequestValidator validator= new RequestValidator(request);
        validator.assertInt("page", "Il numero di pagina deve essere un intero");
        return validator;
    }

    public static RequestValidator validateId(HttpServletRequest request){
        RequestValidator validator= new RequestValidator(request);
        validator.assertInt("id", "Id deve essere un intero");
        return validator;
    }

    public static RequestValidator validateFunctionValue(HttpServletRequest request){
        RequestValidator validator=new RequestValidator(request);
        String function=request.getParameter("function");
        boolean value= (function.equals("0")|| function.equals("1"));
        validator.gatherError(value, "function deve essere 0 o 1");
        return validator;
    }
}
