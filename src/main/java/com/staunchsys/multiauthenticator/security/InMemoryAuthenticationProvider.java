package com.staunchsys.multiauthenticator.security;

import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class InMemoryAuthenticationProvider implements AuthenticationProvider {

	@Value("#{${user.credential}}")
	private Map<String, String> credential;

	@Override
	public Authentication authenticate(Authentication auth) throws AuthenticationException {
		String username = null;
		String password = null;

		for (Entry<String, String> entry : credential.entrySet()) {
			if (auth.getName().equals(entry.getKey())) {
				username = entry.getKey();
				password = entry.getValue();
			}
		}

		if (username != null && auth.getName().equals(username) && auth.getCredentials().toString().equals(password)) {
			return new UsernamePasswordAuthenticationToken(username, password, Collections.emptyList());
		} else {
			throw new BadCredentialsException("Authentication fail");
		}
	}

	@Override
	public boolean supports(Class<?> auth) {
		return auth.equals(UsernamePasswordAuthenticationToken.class);
	}

}
