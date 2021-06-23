package ibm.java.academy.cerfiticationsapp.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.jaas.JaasAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.RequestParam;

import ibm.java.academy.cerfiticationsapp.repository.UserJpaRepository;
import ibm.java.academy.cerfiticationsapp.service.SecurityUserDetailsService;
import lombok.extern.java.Log;

import com.auth0.jwt.algorithms.Algorithm;

import org.springframework.security.core.userdetails.User;
import java.io.IOException;
import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;

@Configuration
@EnableWebSecurity
@Log
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public final static String AUTHORIZATION_HEADER = "Authorization";

    @Autowired
    UserJpaRepository userRepo;

    @Autowired
    JwtFilter customFilter;

    @Bean
    public UserDetailsService securityUserDetailsService(){
        return new SecurityUserDetailsService(userRepo);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(securityUserDetailsService()).passwordEncoder(passwordEncoder());
        //auth.authenticationProvider(mJwtAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests(
                authorizeRequests -> authorizeRequests
                .antMatchers("/all-users**").authenticated() 
                .antMatchers("/certifications**").authenticated()
                .anyRequest().permitAll() 
            ).addFilterBefore(
                new HeaderUsernamePasswordAuthenticationFilter(authenticationManager()), 
                UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class)
            .httpBasic()
            .and()
            .logout().permitAll()
            .logoutUrl("/logout");

        http.headers().frameOptions().disable();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public class HeaderUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
        private String jwtAudience;
        private String jwtIssuer;
        private String jwtSecret;
        private String jwtType;

        public HeaderUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
          super();
          this.setAuthenticationManager(authenticationManager);
          this.setFilterProcessesUrl("/login**");
          this.setPostOnly(false);
        }
      
        @Override
        protected String obtainPassword(@RequestParam HttpServletRequest request) {
            return request.getParameter("password");
        }
      
        @Override
        protected String obtainUsername(@RequestParam HttpServletRequest request) {
            return request.getParameter("username");
        }

        @Override
        public Authentication attemptAuthentication(HttpServletRequest request,
        HttpServletResponse response) throws AuthenticationException {
            System.out.println("Attempted authentication");
            String username = obtainUsername(request);
            String password = obtainPassword(request);

            if (username == null) {
                username = "";
            }

            if (password == null) {
                password = "";
            }

            username = username.trim();

            System.out.println(username + ":" + password);

            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
            username, password);

            // Allow subclasses to set the "details" property
            setDetails(request, authRequest);

            return this.getAuthenticationManager().authenticate(authRequest);
        }
        
        @Override 
        protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth) throws IOException, ServletException {
            System.out.println(auth.getPrincipal().toString());
            String token = JWT.create()
                .withSubject(((User) auth.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 500000))
                .sign(Algorithm.HMAC512("secret"));
            response.addHeader("access-control-expose-headers", "Set-Cookie, auth_token");
            response.addHeader("access-control-allow-headers", "Content-Type, Custom-Header");
            response.addHeader("auth_token", token);
            response.getWriter().write(token);
            response.getWriter().flush();

            System.out.println("successful login");

            super.successfulAuthentication(request, response, chain, auth);
        }
    }
}
