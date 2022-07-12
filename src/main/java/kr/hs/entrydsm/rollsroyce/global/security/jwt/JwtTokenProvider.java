package kr.hs.entrydsm.rollsroyce.global.security.jwt;

import io.jsonwebtoken.*;
import kr.hs.entrydsm.rollsroyce.domain.admin.domain.types.Role;
import kr.hs.entrydsm.rollsroyce.domain.refresh_token.domain.RefreshToken;
import kr.hs.entrydsm.rollsroyce.domain.refresh_token.domain.repository.RefreshTokenRepository;
import kr.hs.entrydsm.rollsroyce.global.exception.ExpiredTokenException;
import kr.hs.entrydsm.rollsroyce.global.exception.InvalidTokenException;
import kr.hs.entrydsm.rollsroyce.global.security.auth.AdminDetailsService;
import kr.hs.entrydsm.rollsroyce.global.security.auth.AuthDetailsService;
import kr.hs.entrydsm.rollsroyce.global.utils.EnumUtil;
import kr.hs.entrydsm.rollsroyce.global.utils.token.dto.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

	private final JwtProperties jwtProperties;
	private final AuthDetailsService authDetailsService;
	private final AdminDetailsService adminDetailsService;
	private final RefreshTokenRepository refreshTokenRepository;

	private static final String REFRESH_KEY = "refresh_token";

	public TokenResponse generateToken(String id, String role) {
		String accessToken = generateAccessToken(id, role);
		String refreshToken = generateRefreshToken(id, role);

		refreshTokenRepository.save(RefreshToken.builder()
				.id(id)
				.token(refreshToken)
				.ttl(jwtProperties.getRefreshExp() * 1000)
				.build());

		return new TokenResponse(accessToken, refreshToken);
	}

	private String generateAccessToken(String id, String role) {
		return Jwts.builder()
				.setSubject(id)
				.setHeaderParam("typ", "access_token")
				.claim("role", role)
				.signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
				.setExpiration(
						new Date(System.currentTimeMillis() + jwtProperties.getAccessExp() * 1000)
				)
				.setIssuedAt(new Date())
				.compact();

	}

	private String generateRefreshToken(String id, String role) {
		return Jwts.builder()
				.setSubject(id)
				.setHeaderParam("typ", REFRESH_KEY)
				.claim("role", role)
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
		Claims body = getJws(token).getBody();
		if(!isNotRefreshToken(token))
			throw InvalidTokenException.EXCEPTION;

		UserDetails userDetails = getDetails(body);
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	public boolean isNotRefreshToken(String token) {
		return !REFRESH_KEY.equals(getJws(token).getHeader().get("typ").toString());
	}

	public String getRole(String token) {
		return getJws(token).getBody().get("role").toString();
	}

	private Jws<Claims> getJws(String token) {
		try {
			return Jwts.parser().setSigningKey(jwtProperties.getSecretKey())
					.parseClaimsJws(token);
		} catch (ExpiredJwtException e) {
			throw ExpiredTokenException.EXCEPTION;
		} catch (Exception e) {
			throw InvalidTokenException.EXCEPTION;
		}
	}

	private UserDetails getDetails(Claims body) {
		Role role = EnumUtil.getEnum(Role.class, body.get("role").toString());

		if(Role.ROOT.equals(role) || Role.CONFIRM_APPLICATION.equals(role)) {
			return adminDetailsService
					.loadUserByUsername(body.getSubject());
		} else {
			return authDetailsService
					.loadUserByUsername(body.getSubject());
		}
	}

}
