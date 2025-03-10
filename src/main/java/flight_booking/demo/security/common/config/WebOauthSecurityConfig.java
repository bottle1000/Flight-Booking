package flight_booking.demo.security.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import flight_booking.demo.domain.user.repository.UserRepository;
import flight_booking.demo.security.common.filter.TokenAuthenticationFilter;
import flight_booking.demo.security.jwt.TokenProvider;
import flight_booking.demo.security.oauth.OAuth2AuthorizationRequestBasedOnCookieRepository;
import flight_booking.demo.security.oauth.OAuth2SuccessHandler;
import flight_booking.demo.security.oauth.OAuth2UserCustomService;
import flight_booking.demo.security.token.repository.RefreshTokenRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableMethodSecurity // Role 검증을 위한 어노테이션
@Configuration
public class WebOauthSecurityConfig {

	private final OAuth2UserCustomService oAuth2UserCustomService;
	private final TokenProvider tokenProvider;
	private final UserRepository userRepository;
	private final RefreshTokenRepository refreshTokenRepository;

	@Bean
	public WebSecurityCustomizer configure() {
		return (web) -> web.ignoring()
			.requestMatchers(
				new AntPathRequestMatcher("/img/**"),
				new AntPathRequestMatcher("/css/**"),
				new AntPathRequestMatcher("/js/**")
			);
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// CSRF 비활성화
		http.csrf(csrf -> csrf.disable());

		// 기본 인증 방식 비활성화
		http.httpBasic(httpBasic -> httpBasic.disable());
		http.formLogin(formLogin -> formLogin.disable());
		http.logout(logout -> logout.disable());

		// Stateless 방식 적용 (세션 사용 안함)
		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		// JWT 인증 필터 추가
		http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

		// 접근 권한 설정
		http.authorizeHttpRequests(auth -> auth
			.requestMatchers("/actuator/prometheus").permitAll() // 이 경로는 인증 없이 접근 허용
			.requestMatchers("/**").permitAll()  // 그 외 모든 요청 허용
			.anyRequest().authenticated()  // 나머지 모든 요청은 인증된 사용자만 접근 가능
		);

		// OAuth2 로그인 설정
		http.oauth2Login(oauth2 -> oauth2
			.loginPage("/login")
			.authorizationEndpoint(auth -> auth
				.authorizationRequestRepository(oAuth2AuthorizationRequestBasedOnCookieRepository())
			)
			.successHandler(oAuth2SuccessHandler())
			.userInfoEndpoint(userInfo -> userInfo.userService(oAuth2UserCustomService))
		);

		// 로그아웃 설정
		http.logout(logout -> logout.logoutSuccessUrl("/login"));

		// 인증되지 않은 경우 JSON 응답 반환
		http.exceptionHandling(exceptions -> exceptions
			.authenticationEntryPoint((request, response, authException) -> {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.setContentType("application/json");
				response.getWriter().write("{\"error\": \"Unauthorized\"}");
			})
		);

		return http.build();
	}

	@Bean
	public OAuth2SuccessHandler oAuth2SuccessHandler() {
		return new OAuth2SuccessHandler(tokenProvider, refreshTokenRepository,
			oAuth2AuthorizationRequestBasedOnCookieRepository(),
			userRepository
		);
	}


	public TokenAuthenticationFilter tokenAuthenticationFilter() {
		return new TokenAuthenticationFilter(tokenProvider, refreshTokenRepository, userRepository);
	}

	@Bean
	public OAuth2AuthorizationRequestBasedOnCookieRepository oAuth2AuthorizationRequestBasedOnCookieRepository() {
		return new OAuth2AuthorizationRequestBasedOnCookieRepository();
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
