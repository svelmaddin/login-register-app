package com.atlacademy.security.controller;

import com.atlacademy.security.config.CurrentUser;
import com.atlacademy.security.model.UserPrincipal;
import javax.annotation.security.RolesAllowed;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Secured({"ROLE_MODERATOR", "ROLE_ADMIN"})
	@GetMapping
	public String getAdmin(@CurrentUser UserPrincipal userPrincipal) {
		return userPrincipal.toString();
	}

	@Secured("ROLE_ADMIN")
	@PostMapping
	public String postAdmin(@CurrentUser UserPrincipal userPrincipal) {
		return userPrincipal.toString();
	}

}
