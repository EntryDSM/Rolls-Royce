package kr.hs.entrydsm.rollsroyce.global.security.jwt;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import kr.hs.entrydsm.rollsroyce.global.exception.ExpiredTokenException;
import kr.hs.entrydsm.rollsroyce.global.exception.InvalidTokenException;
import kr.hs.entrydsm.rollsroyce.global.security.auth.AuthDetailsService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

	private final JwtProperties jwtProperties;
	private final AuthDetailsService authDetailsService;

	public String generateAccessToken(Long receiptCode) {
		return Jwts.builder()
				.setSubject(receiptCode.toString())
				.claim("type", "access_token")
				.signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
				.setExpiration(
						new Date(System.currentTimeMillis() + jwtProperties.getAccessExp() * 1000)
				)
				.setIssuedAt(new Date())
				.compact();

	}

	public String generateRefreshToken(Long receiptCode) {
		return Jwts.builder()
				.setSubject(receiptCode.toString())
				.claim("type", "refresh_token")
				.signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
				.setExpiration(
						new Date(System.currentTimeMillis() + jwtProperties.getRefreshExp() * 1000)
				)
				.setIssuedAt(new Date())
				.compact();
	}

	public String resolveToken(HttpServletRequest request) {
		String bearer = request.getHeader(jwtProperties.getHeader());
		if(bearer != null && bearer.startsWith(jwtProperties.getPrefix())
				&& bearer.length() > jwtProperties.getPrefix().length() + 1)
			return bearer.substring(jwtProperties.getPrefix().length() + 1);
		return null;
	}

	public Authentication authentication(String token) {
		Claims body = getTokenBody(token);
		if(!body.getExpiration().after(new Date()))
			return null;
		UserDetails userDetails =
				authDetailsService.loadUserByUsername(body.getSubject());
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	private Claims getTokenBody(String token) {
		try {
			return Jwts.parser().setSigningKey(jwtProperties.getSecretKey())
					.parseClaimsJws(token).getBody();
		} catch (ExpiredJwtException e) {
			throw ExpiredTokenException.EXCEPTION;
		} catch (MalformedJwtException | SignatureException e) {
			throw InvalidTokenException.EXCEPTION;
		}
	}

}
