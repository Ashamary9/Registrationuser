package com.registration.domain;



import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrinciple implements UserDetails{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7970698883948966574L;
	
	
	
	private User user;
	
	
	 public UserPrinciple(User user) {
	        this.user = user;
	    }
	

	public UserPrinciple() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	



	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return  Stream.of(this.user.getAuthorities()).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
		
	}

	



	



	@Override
	public String getPassword() {
		
		return this.user.getPassword();
		
	}

	@Override
	public String getUsername() {

		return this.user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {

		return true;
		
	}

	@Override
	public boolean isAccountNonLocked() {

		return this.user.isNotLocked();
		
	}

	@Override
	public boolean isCredentialsNonExpired() {

		return true;
		
	}

	@Override
	public boolean isEnabled() {

		return this.user.isActive();
	}

}
