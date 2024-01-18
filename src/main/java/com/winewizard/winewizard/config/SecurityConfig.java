package com.winewizard.winewizard.config;

import com.winewizard.winewizard.service.impl.MyUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
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
            "/console/**",
            "/api/**"

            
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
                		.requestMatchers(new AntPathRequestMatcher("/logout")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/register")).permitAll());

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/**").permitAll()
                .requestMatchers("/images/flaschi_die_flasche.png").permitAll()
        );
                		
        http.authorizeHttpRequests()

        .requestMatchers(new AntPathRequestMatcher("/wines/edit/**")).hasAnyAuthority("WINERY_STATUS")
        .requestMatchers(new AntPathRequestMatcher("/wines/**")).hasAnyAuthority("ADMIN_STATUS", "WINEWIZARD_STATUS", "WINERY_STATUS")
        .requestMatchers(new AntPathRequestMatcher("/rating/myarea/**")).hasAnyAuthority("WINERY_STATUS", "WINEWIZARD_STATUS")
        .requestMatchers(new AntPathRequestMatcher("/rating/**")).hasAnyAuthority("ADMIN_STATUS", "WINEWIZARD_STATUS")
        .requestMatchers(new AntPathRequestMatcher("/admin")).hasAnyAuthority("ADMIN_STATUS")
        .requestMatchers(new AntPathRequestMatcher("/winery/**")).hasAnyAuthority ("ADMIN_STATUS", "WINERY_STATUS")
        .requestMatchers(new AntPathRequestMatcher("/allratings/**")).hasAnyAuthority("ADMIN_STATUS", "WINEWIZARD_STATUS", "WINERY_STATUS")
        .requestMatchers(new AntPathRequestMatcher("/changeLanguage")).hasAnyAuthority("ADMIN_STATUS", "WINEWIZARD_STATUS", "WINERY_STATUS")
        .requestMatchers(new AntPathRequestMatcher("/admin")).hasAnyAuthority("ADMIN_STATUS")
        .requestMatchers(new AntPathRequestMatcher("/feedback/**")).hasAnyAuthority ("ADMIN_STATUS", "WINEWIZARD_STATUS", "WINERY_STATUS")
        // .requestMatchers(new AntPathRequestMatcher("/fragments/**")).hasAnyAuthority ("ADMIN_STATUS", "WINEWIZARD_STATUS", "WINERY_STATUS")
        .requestMatchers(new AntPathRequestMatcher("/sendMail/**")).hasAnyAuthority ("ADMIN_STATUS", "WINEWIZARD_STATUS", "WINERY_STATUS")
        .requestMatchers(new AntPathRequestMatcher("/**")).hasAnyAuthority("ADMIN_STATUS", "WINEWIZARD_STATUS", "WINERY_STATUS");

        //andere URLs....
        http.headers(headers -> headers.frameOptions(FrameOptionsConfig::disable));

        http.formLogin()
                .loginPage("/customlogin")
                .successHandler(myAuthenticationSuccessHandler())
                .permitAll();



        http.logout()
                .logoutUrl("/logout") // Specify the URL for logout
                .logoutSuccessUrl("/customlogin") // Redirect to this URL after logout
                .invalidateHttpSession(true) // Invalidate the HTTP session
                .deleteCookies("JSESSIONID") // Delete cookies
                .permitAll(); // Allow all users to access the logout URL

        http.httpBasic(Customizer.withDefaults());
        
        return http.build();
    }
     

	@Bean
    public AuthenticationManager authenticationManager(
    		AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
        return new MySimpleUrlAuthenticationSuccessHandler();
    }

}
