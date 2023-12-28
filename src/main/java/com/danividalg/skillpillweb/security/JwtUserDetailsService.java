package com.danividalg.skillpillweb.security;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.logging.log4j.util.Strings;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	@Override
	// Return user without role
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return loadUserByUsername(username, null);
	}
	
	// Return user with username and role specified
	// Can get from a database or elsewhere
	public UserDetails loadUserByUsername(String username, String role) throws UsernameNotFoundException {
		Collection<GrantedAuthority> grantList = new ArrayList<>();
		if (Strings.isNotBlank(role)) {
			grantList.add(new SimpleGrantedAuthority(role));
		}
		return new User(username, "", grantList);
	}
}
