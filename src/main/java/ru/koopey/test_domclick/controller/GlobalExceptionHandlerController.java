package ru.koopey.test_domclick.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.koopey.test_domclick.exeptions.AccountNotFoundException;
import ru.koopey.test_domclick.exeptions.OperationException;

@ControllerAdvice
public class GlobalExceptionHandlerController {
    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandlerController.class);

    @ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(OperationException.class)
    public void operationExceptionHandler(OperationException e) {
        logger.error("Catch account operation exception", e);
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(AccountNotFoundException.class)
    public void accountNotFoundExceptionHandler(AccountNotFoundException e) {
        logger.error("Catch account not found exception", e);
    }
}
