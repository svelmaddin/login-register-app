package com.atlacademy.security.config;

import com.atlacademy.security.model.Role;
import com.atlacademy.security.model.RoleName;
import com.atlacademy.security.model.UserPrincipal;
import com.atlacademy.security.repository.UserRepository;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtTokenProvider tokenProvider;
	private final UserRepository userRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain) throws ServletException, IOException {
		try {
			String jwt = getJwtFromRequest(request);
			if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
				Long userId = tokenProvider.getUserIdFromJWT(jwt);
				var userOptional = userRepository.findById(userId);
				if (userOptional.isPresent()) {
					var user = userOptional.get();
					UserPrincipal userPrincipal = new UserPrincipal(
							userId,
							user.getName(),
							user.getUsername(),
							user.getEmail(),
							user.getRoles().stream().map(Role::getName).collect(Collectors.toList()),
							user.getLocked()
					);
					UsernamePasswordAuthenticationToken authentication = new
							UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());
					authentication.setDetails(new
							WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}
		} catch (Exception ex) {
			log.error("Could not set user authentication in security context", ex);
		}
		filterChain.doFilter(request, response);
	}

	private String getJwtFromRequest(HttpServletRequest request)
	{
		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) &&
				bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}

}
