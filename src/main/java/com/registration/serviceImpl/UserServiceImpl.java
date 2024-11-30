package com.registration.serviceImpl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.registration.domain.User;
import com.registration.domain.UserPrinciple;
import com.registration.enumeration.Role;
import com.registration.exception.EmailExistException;
import com.registration.exception.UserNotFoundException;
import com.registration.exception.UsernameExistException;
import com.registration.repository.UserRepository;
import com.registration.service.UserService;





@Service
public class UserServiceImpl implements UserService,UserDetailsService{

	
	
	private static final String USERNAME_ALREADY_EXISTS = null;


	private static final String EMPTY = null;


	private  Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	
	@Autowired
    private UserRepository userRepository;
	
	@Autowired
    private BCryptPasswordEncoder passwordEncoder;

	
	
	   @Override
	    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	        User user = userRepository.findUserByUsername(username);
	        if (user == null) {
	            LOGGER.error("NO USER FOUND BY USERNAME"+ username);
	            throw new UsernameNotFoundException("NO USER FOUND BY USERNAME" + username);
	        } else {
	            user.setLastLoginDateDisplay(user.getLastLoginDate());
	            user.setLastLoginDate(new Date());
	            userRepository.save(user);
	            UserPrinciple userPrincipal = new UserPrinciple(user);
	            LOGGER.info("FOUND USER BY USERNAME" + username);
	            return userPrincipal;
	        }
	    }


	@Override
	public User register(String firstName, String lastName, String username, String email)
			throws UserNotFoundException, UsernameExistException, EmailExistException {
		
		  validateNewUsernameAndEmail(EMPTY, username, email);
	        User user = new User();
	        user.setUserId(user.generateUserId());
	        String password = generatePassword();
	        String encodedPassword = encodePassword(password);
	        user.setFirstName(firstName);
	        user.setLastName(lastName);
	        user.setUsername(username);
	        user.setEmail(email);
	        user.setJoinDate(new Date());
	        user.setPassword(encodedPassword);
	        user.setActive(true);
	        user.setNotLocked(true);
	        user.setRoles(Role.ROLE_USER.name());
	        user.setAuthorities(Role.ROLE_USER.getAuthorities());
	        user.setProfileImageURL(getTemporaryProfileImageUrl());
	        userRepository.save(user);
	        LOGGER.info("New user password: " + password);
	        return user;
		
		
		
	}





	private String getTemporaryProfileImageUrl() {

        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/users/image/profile/temp").toUriString();

		
	}


	private String encodePassword(String password) {
		
        return passwordEncoder.encode(password);

	}


	private String generatePassword() {
		
        return RandomStringUtils.randomAlphanumeric(10);

	}


	@Override
	public List<User> getUsers() {
		// TODO Auto-generated method stub
		return null;
	}


	
	

    private User validateNewUsernameAndEmail(String currentUsername, String newUsername, String newEmail) throws UserNotFoundException, UsernameExistException, EmailExistException {
       // User userByUsername = findUserByUsername(newUsername);
       // User userByEmail = findUserByEmail(newEmail);
        if(StringUtils.isNotBlank(currentUsername)) {
            User currentUser = findUserByUsername(currentUsername);
            if(currentUser == null) {
                throw new UserNotFoundException("NO_USER_FOUND_BY_USERNAME" + currentUsername);
            }
         User userByUsername = findUserByUsername(newUsername);
            
            if (currentUser != null ) {

                throw new UsernameExistException("USERNAME_ALREADY_EXISTS");
            }
            
            
            User userByEmail = findUserByEmail(newEmail);

            if(userByEmail != null ) {
                throw new EmailExistException("EMAIL_ALREADY_EXISTS");
            }
            return currentUser;
        } else {
        	
            User userByUsername = findUserByUsername(newUsername);

            if(userByUsername != null) {
                throw new UsernameExistException("USERNAME_ALREADY_EXISTS");
            }
            
            User userByEmail = findUserByEmail(newEmail);

            if(userByEmail != null) {
                throw new EmailExistException("EMAIL_ALREADY_EXISTS");
            }
            return null;
        }
    }


	@Override
	public User findUserByUsername(String username) {
	
        return userRepository.findUserByUsername(username);

		
	}


	@Override
	public User findUserByEmail(String email) {
	
        return userRepository.findUserByEmail(email);

		
	}
	
	
	
}
