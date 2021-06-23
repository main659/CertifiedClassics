package ibm.java.academy.cerfiticationsapp.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class JwtAuthenticationManager implements AuthenticationManager{

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("Authenticating");
        System.out.println(authentication.toString());
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        Authentication auth = new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), null, grantedAuthorities);
        return auth;
    }
    
}
