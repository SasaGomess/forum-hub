package br.com.api.forum_hub.infra.exceptions;

import br.com.api.forum_hub.services.ValidationException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class ControllerException {

    @ExceptionHandler(exception = EntityNotFoundException.class)
    public ResponseEntity<Void> entityNotFoundException(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(exception = ValidationException.class)
    public ResponseEntity<Void> validation(){
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(exception = MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorMessage>> validationException(MethodArgumentNotValidException ex){

        var errors = ex.getFieldErrors();

        var listFieldErrorsResponse = errors.stream()
                .map(e -> new ErrorMessage(e.getDefaultMessage(), e.getField()))
                .toList();
        return ResponseEntity.badRequest().body(listFieldErrorsResponse);
    }

    record ErrorMessage(String message, String fieldError) {
        ErrorMessage(FieldError error) {
            this(error.getDefaultMessage(), error.getField());
        }
    }
}
