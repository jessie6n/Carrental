package com.demo.util;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	// 使用安全的 HS256 Key（至少 256-bit）
	private final Key key = Keys
			.hmacShaKeyFor("MySuperSecureSecretKeyForJWT_1234567890!".getBytes(StandardCharsets.UTF_8));

	// 生成 Token
	public String generateToken(String username) {
		return Jwts.builder().setSubject(username).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30)) // 30 分鐘
				.signWith(key, SignatureAlgorithm.HS256) // 使用安全 key
				.compact();
	}

	// 從 Token 解析使用者名稱
	public String getUsernameFromToken(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
	}

	// 驗證 Token 是否有效
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}