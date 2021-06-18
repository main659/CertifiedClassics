package ibm.java.academy.cerfiticationsapp.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthenticatedReactiveAuthorizationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import ibm.java.academy.cerfiticationsapp.repository.UserJpaRepository;
import ibm.java.academy.cerfiticationsapp.service.SecurityUserDetailsService;
import lombok.extern.java.Log;

@Configuration
@EnableWebSecurity
@Log
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserJpaRepository userRepo;

    @Bean
    public UserDetailsService securityUserDetailsService(){
        return new SecurityUserDetailsService(userRepo);
    }

    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(securityUserDetailsService()).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().addFilterAt(
            new HeaderUsernamePasswordAuthenticationFilter(authenticationManager()), 
            UsernamePasswordAuthenticationFilter.class)
            .authorizeRequests(
                authorizeRequests -> authorizeRequests
                .antMatchers("/add-user**").permitAll()
                .anyRequest().authenticated()                
            )
            .httpBasic().realmName("Students");
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
      
        /* (non-Javadoc)
         * @see org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter#obtainPassword(javax.servlet.http.HttpServletRequest)
         */
        @Override
        protected String obtainPassword(HttpServletRequest request) {
            return request.getParameter("password");
        }
      
        /* (non-Javadoc)
         * @see org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter#obtainUsername(javax.servlet.http.HttpServletRequest)
         */
        @Override
        protected String obtainUsername(HttpServletRequest request) {
            return request.getParameter("username");
        }

        @Override
        public Authentication attemptAuthentication(HttpServletRequest request,
        HttpServletResponse response) throws AuthenticationException {
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

      }
}
