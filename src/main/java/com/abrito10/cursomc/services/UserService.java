package com.abrito10.cursomc.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.abrito10.cursomc.security.UserSS;

public class UserService {
	
	public static UserSS authenticade() {
		try {
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();			
		}
		catch (Exception e) {
			return null;
		}
	}
}
