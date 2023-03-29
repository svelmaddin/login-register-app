package com.atlacademy.security.controller;

import com.atlacademy.security.config.CurrentUser;
import com.atlacademy.security.dto.TokenDto;
import com.atlacademy.security.model.UserPrincipal;
import com.atlacademy.security.request.LoginRequest;
import com.atlacademy.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

	private final AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<TokenDto> login(@RequestBody LoginRequest loginRequest) {
		log.info("signing in");
		return ResponseEntity.ok()
				.body(authService.login(loginRequest));
	}

	@PostMapping("/refresh-token")
	public ResponseEntity<TokenDto> refreshToken(@CurrentUser UserPrincipal userPrincipal) {
		return ResponseEntity.ok(authService.refreshTokens(userPrincipal));
	}

}
