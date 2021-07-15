package com.uydevs.backoffice.web.rest;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.uydevs.backoffice.domain.User;
import com.uydevs.backoffice.security.GoogleTokenVerifier;
import com.uydevs.backoffice.security.jwt.JWTFilter;
import com.uydevs.backoffice.security.jwt.TokenProvider;
import com.uydevs.backoffice.service.UserService;
import com.uydevs.backoffice.web.rest.vm.LoginVM;

/**
 * Controller to authenticate users.
 */
@RestController
@RequestMapping("/api")
public class UserJWTController {

	private final TokenProvider tokenProvider;
	private GoogleTokenVerifier googleTokenVerifier;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	@Autowired
	private UserService userService;

	public UserJWTController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder,
			GoogleTokenVerifier googleTokenVerifier) {
		this.tokenProvider = tokenProvider;
		this.authenticationManagerBuilder = authenticationManagerBuilder;
		this.googleTokenVerifier = googleTokenVerifier;
	}

	@PostMapping("/authenticate")
	public ResponseEntity<JWTToken> authorize(@Valid @RequestBody LoginVM loginVM) {
		Authentication authentication = autenticarEnTodosLosProveedores(loginVM);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		boolean rememberMe = (loginVM.isRememberMe() == null) ? false : loginVM.isRememberMe();
		String jwt = tokenProvider.createToken(authentication, rememberMe);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
		return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
	}

	private Authentication autenticarEnTodosLosProveedores(LoginVM loginVM) throws AuthenticationException {
		Authentication authentication;
		if (loginVM.getUsername().startsWith("gsignin_")) {
			try {
				googleTokenVerifier.verify(loginVM.getPassword());
				authentication = new UsernamePasswordAuthenticationToken(loginVM.getUsername(), loginVM.getPassword(),
						getAutorities(loginVM.getUsername()));
				// TODO se deberia agregar para terminar el ciclo la validacion del usuario
				// recibido en el token con el que se recibio de flutter (campo id)
			} catch (SecurityException | GeneralSecurityException | IOException e) {
				e.printStackTrace();
				throw new BadCredentialsException(e.getMessage());
			}
		} else {
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
					loginVM.getUsername(), loginVM.getPassword());
			authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		}
		return authentication;
	}

	private Collection<? extends GrantedAuthority> getAutorities(String username) {
		Optional<User> optUser = userService.getUserWithAuthoritiesByLogin(username);
		return optUser.get().getAuthorities().stream().map(authority -> new SimpleGrantedAuthority(authority.getName()))
				.collect(Collectors.toList());
	}

	/**
	 * Object to return as body in JWT Authentication.
	 */
	static class JWTToken {

		private String idToken;

		JWTToken(String idToken) {
			this.idToken = idToken;
		}

		@JsonProperty("id_token")
		String getIdToken() {
			return idToken;
		}

		void setIdToken(String idToken) {
			this.idToken = idToken;
		}
	}
}
