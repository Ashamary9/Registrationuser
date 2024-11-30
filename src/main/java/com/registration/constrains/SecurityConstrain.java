package com.registration.constrains;



public class SecurityConstrain {

	
	public static final long expirationTime=86400000;  //1 day expressed in milliseconds
	public static final String tokenPrefix="Bearer";
	public static final String jwtTokenHeader="Jwt-Token";
	public static final String tokenCannotVerified="This token cannot be verified";
	public static final String arraysLLC="Get Arrays,LLC";
	public static final String arraysAdministration="User Management portal";
	public static final String authorities="Authorities";
	public static final String forbiddenMessage="You need to login this page";
	public static final String accessDenied="You don't have permission to access this page";
	public static final String optionsHttpMethod="Options";
	//public static final String[] publicUrl= {"/users/login","/users/register","/users/resetPassword/**","/users/image/**"};
	public static final String[] publicUrl= {"**"};

	
}
