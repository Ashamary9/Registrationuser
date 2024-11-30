package com.registration.service;

import java.util.List;

import com.registration.domain.User;
import com.registration.exception.EmailExistException;
import com.registration.exception.UserNotFoundException;
import com.registration.exception.UsernameExistException;

public interface UserService {

	
	  User register(String firstName, String lastName, String username, String email) throws UserNotFoundException, UsernameExistException, EmailExistException;

	    List<User> getUsers();

	    User findUserByUsername(String userName);

	    User findUserByEmail(String email);
	
	
}
