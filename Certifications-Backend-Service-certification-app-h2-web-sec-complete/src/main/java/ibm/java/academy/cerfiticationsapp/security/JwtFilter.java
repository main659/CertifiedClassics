package ibm.java.academy.cerfiticationsapp.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import ibm.java.academy.cerfiticationsapp.repository.UserJpaRepository;
import ibm.java.academy.cerfiticationsapp.service.SecurityUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Logger;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.provider.authentication.TokenExtractor;

@Component
public class JwtFilter extends AbstractAuthenticationProcessingFilter {

   private final TokenExtractor tokenExtractor;

   @Autowired
   public JwtFilter(TokenExtractor tokenExtractor, RequestMatcher matcher) {
       super(matcher);
       this.tokenExtractor = tokenExtractor;
   }

   @Override
   @Autowired
   public void setAuthenticationManager(AuthenticationManager authenticationManager) {
      super.setAuthenticationManager(authenticationManager);
   }

   @Override
   public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
           ServletException {
              System.out.println("Filtering ...");
       HttpServletRequest request = (HttpServletRequest) req;
       HttpServletResponse response = (HttpServletResponse) res;
       String token = request.getHeader("auth_token");
       System.out.println("auth_token: " + token);
       if(token != null){
         System.out.println("Got to here 2");

           boolean success = true;

           Authentication authResult = null;
           try {
               System.out.println("Got to here 3");
               authResult = this.attemptAuthentication(request, response);
           } catch (InternalAuthenticationServiceException var8) {
               this.logger.error("An internal error occurred while trying to authenticate the user.", var8);
               success = false;
           } catch (AuthenticationException var9) {
               success = false;
           }

           if (success && null != authResult) {
               System.out.println("Got to here 4");
               this.successfulAuthentication(request, response, chain, authResult);
           }
         }

           chain.doFilter(request, response);
   }

   @Autowired
   UserJpaRepository userRepo;

   @Override
   public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
           throws AuthenticationException {
       String token = request.getHeader("auth_token");
       System.out.println(token);
       System.out.println("Got to here 4");
       try{
        String user = JWT.require(Algorithm.HMAC512("secret"))
                    .build()
                    .verify(token)
                    .getSubject();
                    System.out.println("This is the user: " + user);
                
                   // User userObject = userRepo.findByEmail(user);
                    return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(user, ""));
        }catch(JWTDecodeException jde){
            throw new JwtAuthenticationException("Exception: auth_token not propper Jwt token");
        }
   }

   @Override
   protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                           Authentication authResult) throws IOException, ServletException {
       SecurityContext context = SecurityContextHolder.createEmptyContext();
       context.setAuthentication(authResult);
       SecurityContextHolder.setContext(context);      
   }
}
