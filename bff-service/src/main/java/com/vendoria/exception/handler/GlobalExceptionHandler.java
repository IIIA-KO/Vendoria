package com.vendoria.exception.handler;

import com.vendoria.exception.custom.ServiceException;
import com.vendoria.exception.response.ErrorResponse;

import jakarta.servlet.http.HttpServletRequest;

import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;
import org.springframework.http.HttpStatus;

@Log4j2
@ControllerAdvice
public class GlobalExceptionHandler extends DefaultHandlerExceptionResolver {
    @Value("${views.app.error}")
    private String ERROR_PAGE;

    @ExceptionHandler(ServiceException.class)
    public ModelAndView handleServiceException(HttpServletRequest request, ServiceException exception) {
        ErrorResponse errorResponse = exception.getErrorResponse();
        HttpStatus status = HttpStatus.valueOf(exception.getErrorResponse().getStatus());
        log.error(errorResponse);
        return buildModelAndView(status, errorResponse);
    }

    private ModelAndView buildModelAndView(HttpStatus statusCode, ErrorResponse errorResponse) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(ERROR_PAGE);
        modelAndView.addObject("error", errorResponse);
        modelAndView.setStatus(statusCode);
        return modelAndView;
    }
}