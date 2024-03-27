package com.example.cms.serviceImpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.cms.dto.UserRequest;
import com.example.cms.dto.UserResponse;
import com.example.cms.entity.User;
import com.example.cms.exception.UserAlreadyExistByEmailException;
import com.example.cms.repository.UserRepository;
import com.example.cms.service.UserService;
import com.example.cms.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{
	
	public UserRepository repository;
	public ResponseStructure<UserResponse>userStructure;
	public PasswordEncoder passwordEncoder;
	
	
	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> registerUser(UserRequest userRequest) {
		
		
		if(repository.existsByemail(userRequest.getEmail()))
			throw new UserAlreadyExistByEmailException("Failed to register user");
			
			User user=repository.save(mapToUserEntity(userRequest,new User()));
		
		return ResponseEntity.ok(userStructure.setStatuscode(HttpStatus.OK.value())
				.setMessage("User registered successfully")
				.setData(mapToUserResponse(user)));
			
	}
	
	private UserResponse mapToUserResponse(User user) {
		
		return UserResponse.builder()
				.userId(user.getUserId())
				.userName(user.getUsername())
				.email(user.getEmail())
				.createdAt(user.getCreatedAt())
				.lastModifiedAt(user.getLastModifiedAt())
				.build();
		
	
	}

	private User mapToUserEntity(UserRequest userRequest, User user) {
		
		String encode = passwordEncoder.encode(userRequest.getPassword());
		
		user.setUsername(userRequest.getUsername());
		user.setEmail(userRequest.getEmail());
		user.setPassword(encode);
		
		return user;
		
		
	}

	


	
}
