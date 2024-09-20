package org.mufasadev.ecommerce.project.exceptions;

import org.mufasadev.ecommerce.project.payload.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class MyGlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> myMethodArgumentsNotValid(MethodArgumentNotValidException ex){
        Map<String,String> response = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error)->{
            String fieldName = ((FieldError)error).getField();
            String errorMessage = error.getDefaultMessage();
            response.put(fieldName,errorMessage);
        });

        return new ResponseEntity<Map<String,String>>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIResponse> myResourceNotFound(ResourceNotFoundException ex){
        String message = ex.getMessage();
        APIResponse response = new APIResponse(message,false);
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(APIException.class)
    public ResponseEntity<APIResponse> myAPIException(APIException ex){
        String message = ex.getMessage();
        APIResponse apiResponse = new APIResponse(message,false);
        return new ResponseEntity<>(apiResponse,HttpStatus.BAD_REQUEST);
    }
}

