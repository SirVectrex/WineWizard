package com.winewizard.winewizard.config;

import com.winewizard.winewizard.service.impl.MyUserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
public class SecurityConfig {	
		
	private MyUserDetailsServiceImpl userDetailsService;
	
	public SecurityConfig(MyUserDetailsServiceImpl myUserDetailsServiceImpl) {
		this.userDetailsService= myUserDetailsServiceImpl;
	}
	
	private static final String[] AUTH_WHITE_LIST = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/logout",
            "/h2-console/**",
            "/console/**"
            
    };
	
	// My API starts from /api so this pattern is ok for me
    private static final String API_URL_PATTERN = "/api/**";
	
			
	@Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    		
			  
    
    @Bean
    public SecurityFilterChain getSecurityFilterChain(HttpSecurity http,
                                                      HandlerMappingIntrospector introspector) throws Exception {


        http.csrf().disable();
        
        http.csrf(csrfConfigurer ->
                csrfConfigurer.ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**")));

        http.headers(headersConfigurer ->
                headersConfigurer.frameOptions(FrameOptionsConfig::sameOrigin));

        http.authorizeHttpRequests(auth ->
                auth
                		.requestMatchers(new AntPathRequestMatcher("/resources/**")).permitAll()		
                		.requestMatchers(new AntPathRequestMatcher("/webjars/**")).permitAll()
                		.requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
                		.requestMatchers(new AntPathRequestMatcher("/login")).permitAll()
                		.requestMatchers(new AntPathRequestMatcher("/logout")).permitAll());
        
                		
        http.authorizeHttpRequests()

        .requestMatchers(new AntPathRequestMatcher("/**")).hasAnyAuthority("ADMIN_STATUS")
        .requestMatchers(new AntPathRequestMatcher("/wines/add")).hasAuthority("ADMIN_STATUS")
        .requestMatchers(new AntPathRequestMatcher("/changeLanguage")).hasAuthority("ADMIN_STATUS")
        .requestMatchers(new AntPathRequestMatcher("/winery/**")).hasAuthority ("ADMIN_STATUS");
        //andere URLs....
        http.headers(headers -> headers.frameOptions(FrameOptionsConfig::disable));                
        
        http.formLogin(Customizer.withDefaults());
        http.httpBasic(Customizer.withDefaults());
        
        return http.build();
    }
     

	@Bean
    public AuthenticationManager authenticationManager(
    		AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
