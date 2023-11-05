package com.example.e2ekernelengine.user.token.service;

import java.sql.Timestamp;
import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.e2ekernelengine.user.db.entity.User;
import com.example.e2ekernelengine.user.db.repository.UserRepository;
import com.example.e2ekernelengine.user.token.db.entity.RefreshToken;
import com.example.e2ekernelengine.user.token.db.repository.RefreshTokenRepository;
import com.example.e2ekernelengine.user.token.exception.TokenException;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TokenService {

	private final UserRepository userRepository;
	private final RefreshTokenRepository refreshTokenRepository;
	@Value("${token.secret.key}")
	private String secretKey;
	@Value("${token.access-token.plus-minute}")
	private Long accessTokenPlusMinute;
	@Value("${token.refresh-token.plus-hour}")
	private Long refreshTokenPlusHour;

	public String getUserEmail(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(getByteSecretKey(secretKey))
				.build()
				.parseClaimsJws(token)
				.getBody()
				.get("userEmail", String.class);

	}

	public String createAccessToken(User user) {
		Date now = new Date();
		return Jwts.builder()
				.setHeaderParam(Header.TYPE, Header.JWT_TYPE)
				.claim("userEmail", user.getUserEmail())
				.setIssuedAt(now)
				.setExpiration(getExpirationTime(true))
				.signWith(getByteSecretKey(secretKey), SignatureAlgorithm.HS256)
				.compact();
	}

	public String createAccessTokenByRefreshToken(String refreshToken) throws Exception {
		try {
			/*String userEmail = getUserEmail(refreshToken);*/
			RefreshToken refreshTokenEntity = refreshTokenRepository.findByRefreshToken(refreshToken)
					.orElseThrow(() -> new TokenException("Refresh token does not exist."));

			if (isTokenExpired(refreshToken)) {
				refreshTokenRepository.deleteByRefreshToken(refreshToken);
				throw new TokenException("Refresh token expired.");
			}

			User user = refreshTokenEntity.getUser();

			return createAccessToken(user);

		} catch (Exception e) {
			throw new TokenException("Invalid refreshToken.");
		}
	}

	public boolean isTokenExpired(String token) {
		Date expirationDate = Jwts.parserBuilder()
				.setSigningKey(getByteSecretKey(secretKey))
				.build()
				.parseClaimsJws(token)
				.getBody()
				.getExpiration();
		return expirationDate.before(new Date());
	}

	public String createAndStoreRefreshToken(User user) {
		String refreshToken = createRefreshToken(user);
		Timestamp expireTimestamp = new Timestamp(getExpirationTime(false).getTime());

		RefreshToken refreshTokenEntity = RefreshToken.builder()
				.user(user)
				.refreshToken(refreshToken)
				.refreshTokenExpiredAt(expireTimestamp)
				.build();

		refreshTokenRepository.save(refreshTokenEntity);
		return refreshToken;
	}

	public String createRefreshToken(User user) {
		Date now = new Date();
		return Jwts.builder()
				.setHeaderParam(Header.TYPE, Header.JWT_TYPE)
				.claim("userEmail", user.getUserEmail())
				.setIssuedAt(now)
				.setExpiration(getExpirationTime(false))
				.signWith(getByteSecretKey(secretKey), SignatureAlgorithm.HS256)
				.compact();
	}

	public Date getExpirationTime(boolean isAccessToken) {
		Date now = new Date();
		long expirationTimeInMilliseconds = accessTokenPlusMinute * 60 * 1000;
		if (!isAccessToken) {
			expirationTimeInMilliseconds = refreshTokenPlusHour * 60 * 60 * 1000;
		}
		Date expirationTime = new Date(now.getTime() + expirationTimeInMilliseconds);

		return expirationTime;
	}

	public SecretKey getByteSecretKey(String secretKey) {
		byte[] keyBytes = Base64.getDecoder().decode(secretKey);
		SecretKey byteSecretKey = Keys.hmacShaKeyFor(keyBytes);
		return byteSecretKey;
	}
}
