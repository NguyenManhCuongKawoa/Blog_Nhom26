package nhom26.config;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

//import nhom26.google.CustomOAuth2User;
//import nhom26.google.CustomOAuth2UserService;
import nhom26.service.UserGoogleService;


@Configuration
@EnableWebSecurity
public class WebConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private DataSource dataSource;

    @Value("${spring.admin.username}")
    private String adminUsername;

    @Value("${spring.admin.password}")
    private String adminPassword;

    @Value("${spring.queries.users-query}")
    private String usersQuery;

    @Value("${spring.queries.roles-query}")
    private String rolesQuery;

//    @Autowired
//    private CustomOAuth2UserService oauthUserService;
    
    @Autowired 
    private UserGoogleService userGoogleService;

    /**
     * HTTPSecurity configurer
     * - roles ADMIN allow to access /admin/**
     * - roles USER allow to access /user/** and /newPost/**
     * - anybody can visit /, /home, /registration, /error, /blog/**, /post/**
     * - every other page needs authentication
     * - custom 403 access denied handler
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/css/**", "/js/**").permitAll()
                .antMatchers("/home", "/registration", "/error", "/blog/**", "/post/**", "/login", "/oauth/**").permitAll()
                .antMatchers("/newPost/**", "/commentPost/**", "/createComment/**").hasAnyRole("USER")
                .antMatchers("/admin").hasAnyRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").defaultSuccessUrl("/home").permitAll()
                .and().oauth2Login()
                	.loginPage("/login")
                .successHandler(new AuthenticationSuccessHandler() {
                		 
                        @Override
                        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                Authentication authentication) throws IOException, ServletException {
                            
                            DefaultOidcUser oauthUser = (DefaultOidcUser) authentication.getPrincipal();
                            String email = oauthUser.getAttribute("email");
                            String name = oauthUser.getAttribute("name");
                            userGoogleService.processOAuthPostLogin(email, name);
                            
                            Authentication auth = new Authentication() {
								
								@Override
								public String getName() {
									return email;
								}
								
								@Override
								public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
									
								}
								
								@Override
								public boolean isAuthenticated() {
									return true;
								}
								
								@Override
								public Object getPrincipal() {
									return authentication.getPrincipal();
								}
								
								@Override
								public Object getDetails() {
									return null;
								}
								
								@Override
								public Object getCredentials() {
									return authentication.getCredentials();
								}
								
								@Override
								public Collection<? extends GrantedAuthority> getAuthorities() {
									// TODO Auto-generated method stub
									return authentication.getAuthorities();
								}
							};
                            
                            
                            
                            SecurityContextHolder.getContext().setAuthentication(auth);
                 
                            response.sendRedirect("/home");
                        }
                })
                .and()
                .logout()
                .permitAll().and().requestCache().disable();
    }
    
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/webjars/**");
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {

        // Database authentication
        auth.
                jdbcAuthentication()
                .usersByUsernameQuery(usersQuery)
                .authoritiesByUsernameQuery(rolesQuery)
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder());

        // In memory authentication
        auth.inMemoryAuthentication()
                .withUser(adminUsername).password(passwordEncoder().encode(adminPassword)).roles("ADMIN");
    }
    
    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return new MyAccessDeniedHandler();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
