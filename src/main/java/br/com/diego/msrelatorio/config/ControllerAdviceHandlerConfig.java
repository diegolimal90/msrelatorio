package br.com.diego.msrelatorio.config;

import br.com.diego.msrelatorio.config.exception.BadRequestException;
import br.com.diego.msrelatorio.config.exception.DataBaseException;
import br.com.diego.msrelatorio.config.exception.entity.ExceptionEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerAdviceHandlerConfig extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionEntity> notFound(BadRequestException e, WebRequest  request) {
        ExceptionEntity err = new ExceptionEntity(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(DataBaseException.class)
    public ResponseEntity<ExceptionEntity> notFound(DataBaseException e, WebRequest request) {
        ExceptionEntity err = new ExceptionEntity(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
    }
}