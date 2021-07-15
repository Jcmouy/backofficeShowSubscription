package com.uydevs.backoffice.security;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.webtoken.JsonWebToken.Payload;

@Component
public class GoogleTokenVerifier {

	private static final HttpTransport transport = new NetHttpTransport();
	private static final JsonFactory jsonFactory = new JacksonFactory();
	@Value("${security.google.client.id}")
	private String clientId;

	public Payload verify(String idTokenString) throws GeneralSecurityException, IOException, SecurityException {
		return this.verifyToken(idTokenString);
	}

	private Payload verifyToken(String idTokenString) throws GeneralSecurityException, IOException, SecurityException {
		final GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
				.setIssuers(Arrays.asList("https://accounts.google.com", "accounts.google.com"))
				.setAudience(Collections.singletonList(clientId)).build();

		System.out.println("validating:" + idTokenString);

		GoogleIdToken idToken = null;
		try {
			idToken = verifier.verify(idTokenString);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			// means token was not valid and idToken
			// will be null
		}

		if (idToken == null) {
			throw new SecurityException("idToken is invalid");
		}

		return idToken.getPayload();
	}
}