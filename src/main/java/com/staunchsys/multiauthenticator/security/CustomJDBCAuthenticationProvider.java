package com.staunchsys.multiauthenticator.security;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.staunchsys.multiauthenticator.entity.User;
import com.staunchsys.multiauthenticator.service.UserService;

@Component
public class CustomJDBCAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private UserService userService;

	@Override
	public Authentication authenticate(Authentication auth) throws AuthenticationException {
		String userName = auth.getName();
		String password = auth.getCredentials().toString();

		User user = userService.findByUserName(userName);
		if (user == null) {
			throw new UsernameNotFoundException("User name not found");
		}

		if (userName.equals(user.getUserName()) && new BCryptPasswordEncoder().matches(password, user.getPassword())) {
			return new UsernamePasswordAuthenticationToken(userName, password, Collections.emptyList());
		} else {
			throw new BadCredentialsException("Authentication fail");
		}
	}

	@Override
	public boolean supports(Class<?> auth) {
		return auth.equals(UsernamePasswordAuthenticationToken.class);
	}

}
