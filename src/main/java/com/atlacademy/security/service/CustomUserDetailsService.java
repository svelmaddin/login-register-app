package com.atlacademy.security.service;

import com.atlacademy.security.model.Role;
import com.atlacademy.security.model.RoleName;
import com.atlacademy.security.model.UserPrincipal;
import com.atlacademy.security.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		var user = userRepository.findByUsername(username).get();
		var userPrincipal = new UserPrincipal(
				user.getId(),
				user.getName(),
				user.getUsername(),
				user.getEmail(),
				user.getRoles().stream().map(Role::getName).collect(Collectors.toList()),
				user.getLocked()
		);
		userPrincipal.setPassword(user.getPassword());

		return userPrincipal;
	}

}
