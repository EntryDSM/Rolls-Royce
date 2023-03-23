package kr.hs.entrydsm.rollsroyce.global.security.jwt;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import kr.hs.entrydsm.rollsroyce.domain.admin.domain.types.Role;
import kr.hs.entrydsm.rollsroyce.domain.refreshtoken.domain.RefreshToken;
import kr.hs.entrydsm.rollsroyce.domain.refreshtoken.domain.repository.RefreshTokenRepository;
import kr.hs.entrydsm.rollsroyce.global.exception.ExpiredTokenException;
import kr.hs.entrydsm.rollsroyce.global.exception.InvalidTokenException;
import kr.hs.entrydsm.rollsroyce.global.security.auth.AdminDetailsService;
import kr.hs.entrydsm.rollsroyce.global.security.auth.AuthDetailsService;
import kr.hs.entrydsm.rollsroyce.global.utils.token.dto.TokenResponse;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;
    private final AuthDetailsService authDetailsService;
    private final AdminDetailsService adminDetailsService;
    private final RefreshTokenRepository refreshTokenRepository;

    private static final String ACCESS_KEY = "access_token";
    private static final String REFRESH_KEY = "refresh_token";

    public TokenResponse generateToken(String id, String role) {
        String accessToken = generateToken(id, role, ACCESS_KEY, jwtProperties.getAccessExp());
        String refreshToken = generateToken(id, role, REFRESH_KEY, jwtProperties.getRefreshExp());

        refreshTokenRepository.save(RefreshToken.builder()
                .id(id)
                .token(refreshToken)
                .ttl(jwtProperties.getRefreshExp() * 1000)
                .build());

        return new TokenResponse(accessToken, refreshToken);
    }

    private String generateToken(String id, String role, String type, Long exp) {
        return Jwts.builder()
                .setSubject(id)
                .setHeaderParam("typ", type)
                .claim("role", role)
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .setExpiration(new Date(System.currentTimeMillis() + exp * 1000))
                .setIssuedAt(new Date())
                .compact();
    }

    public String resolveToken(HttpServletRequest request) {
        String bearer = request.getHeader(jwtProperties.getHeader());
        if (bearer != null
                && bearer.startsWith(jwtProperties.getPrefix())
                && bearer.length() > jwtProperties.getPrefix().length() + 1)
            return bearer.substring(jwtProperties.getPrefix().length() + 1);
        return null;
    }

    public Authentication authentication(String token) {
        Claims body = getJws(token).getBody();
        if (!isNotRefreshToken(token)) throw InvalidTokenException.EXCEPTION;

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
            return Jwts.parser().setSigningKey(jwtProperties.getSecretKey()).parseClaimsJws(token);
        } catch (ExpiredJwtException e) {
            throw ExpiredTokenException.EXCEPTION;
        } catch (Exception e) {
            throw InvalidTokenException.EXCEPTION;
        }
    }

    private UserDetails getDetails(Claims body) {
        if (Role.USER.toString().equals(body.get("role").toString())) {
            return authDetailsService.loadUserByUsername(body.getSubject());
        } else {
            return adminDetailsService.loadUserByUsername(body.getSubject());
        }
    }
}
