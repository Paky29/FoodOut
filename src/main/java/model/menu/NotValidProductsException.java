package model.menu;

import model.utility.Alert;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class NotValidProductsException extends Exception {
    private final List<String> errors;
    private final int errorCode;

    public NotValidProductsException(String message, List<String> errors, int errorCode){
        super(message);
        this.errors=errors;
        this.errorCode=errorCode;
    }

    public void handle(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        switch(errorCode){
            case HttpServletResponse.SC_BAD_REQUEST:
                request.setAttribute("alert", new Alert(errors, "danger"));
                String backPath=(String) request.getAttribute("back");
                response.setStatus(errorCode);
                request.getRequestDispatcher(backPath).forward(request, response);
                break;
            default:
                response.sendError(errorCode, errors.get(0));
        }
    }

    public List<String> getErrors(){return errors;}
}
