package com.vendoria.common.handlers;

import com.vendoria.common.Result;
import com.vendoria.common.ResultWithValue;
import com.vendoria.common.errors.Error;
import com.vendoria.common.errors.ErrorResponseFactory;
import org.springframework.web.servlet.ModelAndView;

public class ErrorHandler {
    public static ModelAndView handleResult(Result result, String successView, String errorView) {
        if (result.isSuccess()) {
            return new ModelAndView(successView);
        }
        return handleError(result.error(), errorView);
    }

    public static <T> ModelAndView handleResultWithValue(
            ResultWithValue<T> result,
            String successView,
            String errorView,
            String modelAttribute
    ) {
        if (result.isSuccess()) {
            ModelAndView modelAndView = new ModelAndView(successView);
            modelAndView.addObject(modelAttribute, result.value());
            return modelAndView;
        }
        return handleError(result.getError(), errorView);
    }

    private static ModelAndView handleError(Error error, String errorView) {
        ModelAndView modelAndView = new ModelAndView(errorView);
        modelAndView.addObject("error", error.getMessage());
        return modelAndView;
    }
}