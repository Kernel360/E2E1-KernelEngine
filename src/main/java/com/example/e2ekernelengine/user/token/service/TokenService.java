package com.example.e2ekernelengine.user.token.service;

import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.e2ekernelengine.user.db.entity.User;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TokenService {
	@Value("${token.secret.key}")
	private String secretKey;
	@Value("${token.access-token.plus-minute}")
	private Long accessTokenPlusMinute;

	public String getUserEmail(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(getByteSecretKey(secretKey))
				.build()
				.parseClaimsJws(token)
				.getBody()
				.get("userEmail", String.class);

	}

	public String createToken(User user) {
		Date now = new Date();
		return Jwts.builder()
				.setHeaderParam(Header.TYPE, Header.JWT_TYPE)
				.claim("userEmail", user.getUserEmail())
				.setIssuedAt(now)
				.setExpiration(getExpiredAt())
				.signWith(getByteSecretKey(secretKey), SignatureAlgorithm.HS256)
				.compact();
	}

	public Date getExpiredAt() {
		Date now = new Date();
		long expirationTimeInMilliseconds = accessTokenPlusMinute * 60 * 1000;
		Date expiredAt = new Date(now.getTime() + expirationTimeInMilliseconds);

		return expiredAt;
	}

	public SecretKey getByteSecretKey(String secretKey) {
		byte[] keyBytes = Base64.getDecoder().decode(secretKey);
		SecretKey byteSecretKey = Keys.hmacShaKeyFor(keyBytes);
		return byteSecretKey;
	}
}
