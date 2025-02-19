package com.registration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.registration.Utility.JwtTokenProvider;
import com.registration.constrains.SecurityConstrain;
import com.registration.domain.User;
import com.registration.domain.UserPrinciple;
import com.registration.exception.EmailExistException;
import com.registration.exception.ExceptionHandling;
import com.registration.exception.UserNotFoundException;
import com.registration.exception.UsernameExistException;
import com.registration.service.UserService;

@RestController
@RequestMapping(path={"/users"})
public class UserController extends ExceptionHandling{

	    @Autowired
	    private AuthenticationManager authenticationManager;
	   
	    @Autowired
	    private UserService userService;
	    
	    @Autowired
	    private JwtTokenProvider jwtTokenProvider;
	
	
	
	    @PostMapping("/register")
	    public ResponseEntity<User> register(@RequestBody User user) throws UserNotFoundException, UsernameExistException, EmailExistException {
	        User newUser = userService.register(user.getFirstName(), user.getLastName(), user.getUsername(), user.getEmail());
	        return new ResponseEntity<>(newUser, HttpStatus.OK);
	        
	    }
	    
	    
	    @PostMapping("/login")
	    public ResponseEntity<User> login(@RequestBody User user) {
	        authenticate(user.getUsername(), user.getPassword());
	        User loginUser = userService.findUserByUsername(user.getUsername());
	        UserPrinciple userPrincipal = new UserPrinciple(loginUser);
	        HttpHeaders jwtHeader = getJwtHeader(userPrincipal);
	        return new ResponseEntity<>(loginUser, jwtHeader, HttpStatus.OK);
	    }

	    

	    private HttpHeaders getJwtHeader(UserPrinciple user) {
	        HttpHeaders headers = new HttpHeaders();
	        headers.add(SecurityConstrain.jwtTokenHeader, jwtTokenProvider.generateJwtToken(user));
	        return headers;
	    }

	    private void authenticate(String username, String password) {
	        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
	    }

	
}
