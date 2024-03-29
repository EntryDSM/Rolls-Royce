package kr.hs.entrydsm.rollsroyce.global.security;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsUtils;

import kr.hs.entrydsm.rollsroyce.global.security.jwt.JwtTokenProvider;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    private static final String USER = "USER";
    private static final String ADMIN_ROOT = "ROOT";
    private static final String ADMIN_CONFIRM_APPLICATION = "CONFIRM_APPLICATION";

    // TODO url을 enum으로 빼서 관리하면 오타를 줄일 수 있을 것 같음.
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .cors()
                .and()
                .formLogin()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests()
                .requestMatchers(CorsUtils::isCorsRequest)
                .permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/**")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/schedule", "/question/all")
                .permitAll()
                .antMatchers(HttpMethod.POST, "/user/**")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/user/password")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/user")
                .permitAll()
                .antMatchers(HttpMethod.PATCH, "/user/**")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/user/verify/**")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/user/verify/info", "/banner", "/notice/all/**", "/notice/**", "/screen")
                .permitAll()
                .antMatchers("/user/auth", "/admin/auth")
                .permitAll()
                .antMatchers("/**/email/verify")
                .permitAll()

                // user
                .antMatchers("/pdf/**")
                .hasRole(USER)
                .antMatchers("/score/**")
                .hasRole(USER)
                .antMatchers("/application/**")
                .hasRole(USER)
                .antMatchers(HttpMethod.GET, "/user/status")
                .hasRole(USER)
                .antMatchers(HttpMethod.DELETE, "/user")
                .hasRole(USER)

                // admin
                .antMatchers(HttpMethod.DELETE, "/admin/data", "/notice", "/admin/question/**")
                .hasRole(ADMIN_ROOT)
                .antMatchers(HttpMethod.GET, "/admin/excel/**")
                .hasRole(ADMIN_ROOT)
                .antMatchers(HttpMethod.PATCH, "/schedule", "/admin/application/**", "/notice/**")
                .hasAnyRole(ADMIN_ROOT)
                .antMatchers("/admin/application-count")
                .hasAnyRole(ADMIN_ROOT, ADMIN_CONFIRM_APPLICATION)
                .antMatchers(HttpMethod.POST, "/admin/auth/check", "/notice/**", "/banner", "/screen/**")
                .hasAnyRole(ADMIN_ROOT, ADMIN_CONFIRM_APPLICATION)
                .antMatchers(HttpMethod.GET, "/admin/applicants", "/admin/applicant/**", "/admin/statics/**")
                .hasAnyRole(ADMIN_ROOT, ADMIN_CONFIRM_APPLICATION)

                // faq
                .antMatchers(HttpMethod.GET, "/faq/**")
                .permitAll()
                .antMatchers(HttpMethod.POST, "/faq")
                .hasRole(ADMIN_ROOT)
                .antMatchers(HttpMethod.DELETE, "/faq/**")
                .hasRole(ADMIN_ROOT)
                .antMatchers(HttpMethod.PATCH, "/faq/**")
                .hasRole(ADMIN_ROOT)

                // reserve
                .antMatchers(HttpMethod.GET, "/reserve")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/question/**")
                .permitAll()
                .antMatchers("/swagger-ui/**", "/docs/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .apply(new FilterConfig(jwtTokenProvider));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
