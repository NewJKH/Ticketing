package com.concert.ticketing.common.security;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtUtil {

	public static final String BEARER_PREFIX = "Bearer ";
	private static final long TOKEN_VALID_TIME = 60 * 60 * 1000L; // 1시간

	@Value("${jwt.secret.key}")
	private String secretKey; // Base64 인코딩된 시크릿 키

	private Key key;

	@PostConstruct
	public void init() {
		byte[] decodedKey = Base64.getDecoder().decode(secretKey);
		this.key = Keys.hmacShaKeyFor(decodedKey);
	}

	// 토큰 생성
	public String generateToken(String email, String name) {
		Date now = new Date();
		String token = Jwts.builder()
			.setSubject(email)
			.claim("name", name)
			.setIssuedAt(now)
			.setExpiration(new Date(now.getTime() + TOKEN_VALID_TIME))
			.signWith(key, SignatureAlgorithm.HS256)
			.compact();

		return BEARER_PREFIX + token;
	}

	// 토큰 유효성 검사
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			log.warn("JWT 검증 실패: {}", e.getMessage());
		}
		return false;
	}

	// 토큰에서 이메일 추출
	public String extractEmail(String token) {
		return Jwts.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(token)
			.getBody()
			.getSubject();
	}

	// 토큰에서 이메일을 꺼내고, 'Bearer ' 접두어 제거 후 이메일 반환
	public String getEmailFromToken(String token) {
		if (token.startsWith(BEARER_PREFIX)) {
			token = token.substring(BEARER_PREFIX.length());
		}
		return extractEmail(token);
	}
}
