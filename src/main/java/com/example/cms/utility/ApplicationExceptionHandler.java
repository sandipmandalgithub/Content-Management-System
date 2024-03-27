package com.example.cms.utility;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.mapping.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.cms.exception.UserAlreadyExistByEmailException;

import lombok.AllArgsConstructor;

@RestControllerAdvice
@AllArgsConstructor
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler{
	
	private ErrorStructure<String>errorStructure;
	private ErrorStructure<Object>errorStructure2;

	
	
	
	@Override
	protected ResponseEntity<Object>handleMethodArgumentNotValid(MethodArgumentNotValidException ex,HttpHeaders headers,HttpStatusCode status,WebRequest request)
	{
		//List<ObjectError> errors=ex.getAllErrors();
		
		Map<String,String> messages=new HashMap<>();
		ex.getAllErrors().forEach(error->{
			FieldError fieldError=(FieldError)error;
			messages.put(fieldError.getField(),error.getDefaultMessage());
		});
		return ResponseEntity.badRequest().body(
				errorStructure2.setErrorStatusCode(HttpStatus.BAD_REQUEST.value())
				.setErrorMessage("Invalid Input")
				.setRootCause(messages)
				);
				
		
	}
	
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleUserAlreadyExistByEmail(UserAlreadyExistByEmailException ex)
	{
		return ResponseEntity.badRequest().body(errorStructure
				.setErrorStatusCode(HttpStatus.BAD_REQUEST.value())
				.setErrorMessage(ex.getMessage())
				.setRootCause("User Already Exist with this Email"));
		
	}
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>>handleUserNameNotFound(UsernameNotFoundException ex)
	{
		return ResponseEntity.badRequest().body(errorStructure
				.setErrorStatusCode(HttpStatus.BAD_REQUEST.value())
				.setErrorMessage(ex.getMessage())
				.setRootCause("User Already Exist with this Name"));
				
	}


}
