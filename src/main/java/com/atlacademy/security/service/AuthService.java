package com.atlacademy.security.service;

import com.atlacademy.security.dto.TokenDto;
import com.atlacademy.security.model.UserPrincipal;
import com.atlacademy.security.request.LoginRequest;
import com.atlacademy.security.request.RegisterRequest;

public interface AuthService {

	TokenDto login(LoginRequest loginRequest);

	TokenDto refreshTokens(UserPrincipal userPrincipal);

	void register(RegisterRequest registerRequest);

}
