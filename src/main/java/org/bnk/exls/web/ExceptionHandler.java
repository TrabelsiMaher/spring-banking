package org.bnk.exls.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionHandler  extends ResponseEntityExceptionHandler{
	
	  
	    protected ResponseEntity<Object> handleMethodArgumentNOtValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request){
		
		
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) ->{
			System.out.println("krarez ********************");
			String fieldName = ((FieldError) error).getField();
			String message = error.getDefaultMessage();
			errors.put(fieldName, message);
		});
		return new ResponseEntity<Object>("krarez", HttpStatus.BAD_REQUEST);
		
	}

}
