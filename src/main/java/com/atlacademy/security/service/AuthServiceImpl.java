package com.atlacademy.security.service;

import com.atlacademy.security.config.JwtTokenProvider;
import com.atlacademy.security.dto.TokenDto;
import com.atlacademy.security.model.TokenType;
import com.atlacademy.security.model.UserPrincipal;
import com.atlacademy.security.request.LoginRequest;
import com.atlacademy.security.request.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final AuthenticationManager authenticationManager;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider tokenProvider;

	@Override
	public TokenDto login(LoginRequest loginRequest) {
		Authentication authentication =
				authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(
								loginRequest.getUsername(),
								loginRequest.getPassword()
						)
				);

		SecurityContextHolder.getContext().setAuthentication(authentication);

		return TokenDto.builder()
				.accessToken(tokenProvider.generateToken((UserPrincipal) authentication.getPrincipal(), TokenType.ACCESS_TOKEN))
				.refreshToken(tokenProvider.generateToken((UserPrincipal) authentication.getPrincipal(), TokenType.REFRESH_TOKEN))
				.build();
	}

	@Override
	public TokenDto refreshTokens(UserPrincipal userPrincipal) {
		return TokenDto.builder()
				.accessToken(tokenProvider.generateToken(userPrincipal, TokenType.ACCESS_TOKEN))
				.refreshToken(tokenProvider.generateToken(userPrincipal, TokenType.REFRESH_TOKEN))
				.build();
	}

	@Override
	public void register(RegisterRequest registerRequest) {
//		passwordEncoder.encode(registerRequest.getPassword);
	}

}
