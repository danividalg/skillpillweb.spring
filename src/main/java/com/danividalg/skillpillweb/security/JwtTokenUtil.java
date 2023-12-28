package com.danividalg.skillpillweb.security;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.danividalg.skillpillweb.security.model.JwkKeys;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;

@Component
public class JwtTokenUtil implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
	
	@Value("${clerk.jwks.json}")
	private String urlJwksJson;
	
	@Value("#{'${origins.valid}'.split(',')}")
	private List<String> origins;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("#{'${clerk.issuers}'.split(',')}")
	private List<String> issuers;
	
	private PublicKey publicKey = null;
	
	@PostConstruct
	private void loadJwkKey() {
		try {
			ResponseEntity<String> jwksJson = restTemplate.getForEntity(urlJwksJson, String.class);
			if (jwksJson.getStatusCode().equals(HttpStatus.OK)) {
				JwkKeys jwk = new ObjectMapper().readValue(jwksJson.getBody(), JwkKeys.class);
				if ((jwk != null) && (jwk.getKeys() != null) && !jwk.getKeys().isEmpty()) {
					String n = jwk.getKeys().get(0).getN();
					String e = jwk.getKeys().get(0).getE();
					final Decoder decoder = Base64.getUrlDecoder();
					BigInteger modulus = new BigInteger(1, decoder.decode(n));
					BigInteger exponent = new BigInteger(1, decoder.decode(e));
					KeyFactory keyFactory = KeyFactory.getInstance("RSA");
					publicKey = keyFactory.generatePublic(new RSAPublicKeySpec(modulus, exponent));
				}
			}
		} catch (final NoSuchAlgorithmException | InvalidKeySpecException | JsonProcessingException e) {
			e.printStackTrace();
		}
	}
	
	// Retrieve Authorized Party (Origin)
	public String getAzpFromToken(String token) {
		return getClaimFromToken(token, String.class, "azp");
	}
	// Retrieve Issuer
	public String getIssFromToken(String token) {
		return getClaimFromToken(token, String.class, "iss");
	}
	// Retrieve Issued At
	public Date getIatFromToken(String token) {
		return getClaimFromToken(token, Date.class, "iat");
	}
	//Retrieve Not Before
	public Date getNbfFromToken(String token) {
		return getClaimFromToken(token, Date.class, "nbf");
	}
	// Retrieve Expiration Date
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}
	// Retrieve Username from jwt token
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, String.class, "user.primary_email_address");
	}
	//Retrieve Public Metadata
	@SuppressWarnings("unchecked")
	public Map<String, String> getPublicMetadataFromToken(String token) {
		return getClaimFromToken(token, Map.class, "user.public_metadata");
	}
	//Retrieve Role
	public String getRoleFromToken(String token) {
		Map<String, String> publicMetadata = getPublicMetadataFromToken(token);
		return publicMetadata.get("role");
	}
	
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}
	
	public <T> T getClaimFromToken(String token, Class<T> classType, String claim) {
		final Claims claims = getAllClaimsFromToken(token);
		return claims.get(claim, classType);
	}
	
	// For retrieveing any information from token we will need the public key
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().verifyWith(publicKey).build().parseSignedClaims(token).getPayload();
	}
	
	// Check if the token has expired
	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		final Date nbf = getNbfFromToken(token);
		final Date iat = getIatFromToken(token);
		final Date now = new Date();
		return (expiration.before(now) || nbf.after(now) || iat.after(now));
	}
	
	private Boolean isOriginValid(String token) {
		final String origin = getAzpFromToken(token);
		return origins.contains(origin);
	}
	
	private Boolean isIssuerValid(String token) {
		final String issuer = getIssFromToken(token);
		return issuers.contains(issuer);
	}
	
	// validate token
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token) && isOriginValid(token)
				&& isIssuerValid(token));
	}
}
