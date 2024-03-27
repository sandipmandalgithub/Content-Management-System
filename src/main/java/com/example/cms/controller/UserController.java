package com.example.cms.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.cms.dto.UserRequest;
import com.example.cms.dto.UserResponse;
import com.example.cms.service.UserService;
import com.example.cms.utility.ErrorStructure;
import com.example.cms.utility.ResponseStructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class UserController {
	public UserService service;
	
//*************************************************USER SAVE OPERATION****************************************************************************************************************************	
	@Operation(description = "The End Point Is For User Registeration",responses= {
			@ApiResponse(responseCode = "200", description = "Registered Successfully"),
			@ApiResponse(responseCode = "404",description = "Failed To Registerd",
			              content = {
			            		  		@Content(schema=@Schema(implementation =ErrorStructure.class ))
									}
						)			
	})	
	@PostMapping("/users/register")
	public ResponseEntity<ResponseStructure<UserResponse>> registerUser(@RequestBody @Valid UserRequest userRequest )
	{
		System.out.println(userRequest.getEmail());
		System.out.println(userRequest.getPassword());
		System.out.println(userRequest.getUsername());
		
		return service.registerUser(userRequest);
	}

//**********************************************FIND UNIQUE USER OPERATION******************************************************************************************************
	
		
	
	
	
	
	
	
	
	
	
//***********************************************SOFT DELETE USER OPERATION**********************************************************
	
	@Operation(description = "The End Point is Used To DELETE the USER Based on ID")
	
	@DeleteMapping("/users/{userId}")
	private ResponseEntity<ResponseStructure<UserResponse>>softDeleteUser(@PathVariable int userId)
	{
		return service.softDeleteuser(userId);
	}
	
	
//***************************************************************************************************************************************	
	
	@GetMapping("/test")
	public String test()
	{
		return "Hello from CMS";
	}
	
	
	
	

}
