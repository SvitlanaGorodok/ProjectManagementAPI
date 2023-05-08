package api.projectmanagement.controller;

import api.projectmanagement.exception.NoSuchEntityFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NoSuchEntityFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleNoSuchEntityFoundException(NoSuchEntityFoundException exception) {
        ModelAndView model = new ModelAndView("exception");
        model.addObject("msg", exception.getMessage());
        return model;
    }
}
