package com.registration.jwtFilters;

import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.registration.constrains.SecurityConstrain;
import com.registration.domain.HttpResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
public class JwtAccessDeneidHandler implements AccessDeniedHandler{

	
		 @Override
		    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) throws IOException {
		        HttpResponse httpResponse = new HttpResponse(UNAUTHORIZED.value(), UNAUTHORIZED, UNAUTHORIZED.getReasonPhrase().toUpperCase(),SecurityConstrain.accessDenied );
		        response.setContentType(APPLICATION_JSON_VALUE);
		        response.setStatus(UNAUTHORIZED.value());
		        OutputStream outputStream = response.getOutputStream();
		        ObjectMapper mapper = new ObjectMapper();
		        mapper.writeValue(outputStream, httpResponse);
		        outputStream.flush();
		    }
		
		
	}

	
	
	
	
	

