package com.example.cms.service;

import org.springframework.http.ResponseEntity;

import com.example.cms.dto.UserRequest;
import com.example.cms.dto.UserResponse;
import com.example.cms.entity.User;
import com.example.cms.utility.ResponseStructure;

public interface UserService {
	public ResponseEntity<ResponseStructure<UserResponse>>registerUser(UserRequest userRequest);

	public ResponseEntity<ResponseStructure<UserResponse>>softDeleteuser(int userId);
	
}
