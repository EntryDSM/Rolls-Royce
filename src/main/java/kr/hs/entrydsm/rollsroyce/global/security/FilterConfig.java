package kr.hs.entrydsm.rollsroyce.global.security;

import kr.hs.entrydsm.rollsroyce.global.error.GlobalExceptionFilter;
import kr.hs.entrydsm.rollsroyce.global.security.jwt.JwtTokenFilter;
import kr.hs.entrydsm.rollsroyce.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class FilterConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

	private final JwtTokenProvider jwtTokenProvider;
	private final GlobalExceptionFilter globalExceptionFilter;

	@Override
	public void configure(HttpSecurity builder) {
		JwtTokenFilter jwtTokenFilter = new JwtTokenFilter(jwtTokenProvider);
		builder.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
		builder.addFilterBefore(globalExceptionFilter, JwtTokenFilter.class);
	}

}
