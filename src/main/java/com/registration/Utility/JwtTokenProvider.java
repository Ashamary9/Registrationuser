package com.registration.Utility;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.registration.constrains.SecurityConstrain;
import com.registration.domain.UserPrinciple;

import jakarta.servlet.http.HttpServletRequest;


@Component
public class JwtTokenProvider {

	
	@Value("${jwt.secret}")
	private String secret;
	
	
	   public String generateJwtToken(UserPrinciple userPrinciple) {
	        String[] claims = getClaimsFromUser(userPrinciple);
	        return JWT.create().withIssuer(SecurityConstrain.arraysLLC).withAudience(SecurityConstrain.arraysAdministration)
	                .withIssuedAt(new Date()).withSubject(userPrinciple.getUsername())
	                .withArrayClaim(SecurityConstrain.authorities, claims).withExpiresAt(new Date(System.currentTimeMillis() +SecurityConstrain.expirationTime))
	                .sign(Algorithm.HMAC512(secret.getBytes()));
	    }
	   
	   
       public List<GrantedAuthority> getAuthorities(String token) {
	        String[] claims = getClaimsFromToken(token);
	        return Stream.of(claims).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	    }

       
       public Authentication getAuthentication(String username, List<GrantedAuthority> authorities, HttpServletRequest request) {
           UsernamePasswordAuthenticationToken userPasswordAuthToken = new
                   UsernamePasswordAuthenticationToken(username, null, authorities);
           userPasswordAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
           return userPasswordAuthToken;
       }
       

	   public boolean isTokenValid(String username, String token) {
	        JWTVerifier verifier = getJWTVerifier();
	        return StringUtils.isNotEmpty(username) && !isTokenExpired(verifier, token);
	        
	      }
	   
	   
	  
	   public String getSubject(String token) {
	        JWTVerifier verifier = getJWTVerifier();
	        return verifier.verify(token).getSubject();
	    }

	    private boolean isTokenExpired(JWTVerifier verifier, String token) {
	        Date expiration = verifier.verify(token).getExpiresAt();
	        return expiration.before(new Date());
	    }

	
	    private String[] getClaimsFromToken(String token) {

		 JWTVerifier verifier = getJWTVerifier();
	        return verifier.verify(token).getClaim(SecurityConstrain.authorities).asArray(String.class);
		
	    }

        private JWTVerifier getJWTVerifier() {
		
		 JWTVerifier verifier;
	        try {
	            Algorithm algorithm = Algorithm.HMAC512(secret);
	            verifier = JWT.require(algorithm).withIssuer(SecurityConstrain.arraysLLC).build();
	        }catch (JWTVerificationException exception) {
	            throw new JWTVerificationException(SecurityConstrain.tokenCannotVerified);
	        }
	        return verifier;
		
		}

	
	private String[] getClaimsFromUser(UserPrinciple user) {

		 List<String> authorities = new ArrayList<>();
	        for (GrantedAuthority grantedAuthority : user.getAuthorities()){
	            authorities.add(grantedAuthority.getAuthority());
	        }
	        return authorities.toArray(new String[0]);
	    }
		
		
	}

	

