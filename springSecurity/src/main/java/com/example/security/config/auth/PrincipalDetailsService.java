package com.example.security.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.security.domain.User;
import com.example.security.repository.userRepository;

@Service
public class PrincipalDetailsService implements UserDetailsService{

	@Autowired
	private userRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user= userRepository.findByUsername(username);
		if(user!=null) {
			return new PrincipalDetails(user);
		}
		return null;
	}

}
