package ibm.java.academy.cerfiticationsapp.security;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import ibm.java.academy.cerfiticationsapp.model.Role;
import ibm.java.academy.cerfiticationsapp.model.User;
import ibm.java.academy.cerfiticationsapp.repository.RoleJpaRepository;
import ibm.java.academy.cerfiticationsapp.repository.UserJpaRepository;

@Service
public class JwtAuthenticationManager implements AuthenticationManager{
    @Autowired
    UserJpaRepository userRepo;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("Authenticating");
        System.out.println(authentication.getPrincipal());
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.addAll(userRepo.findByEmail((String) authentication.getPrincipal()).orElse(new User()).getRoles());
        System.out.println(grantedAuthorities);
        Authentication auth = new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getAuthorities(), grantedAuthorities);
        System.out.println(auth);
        return auth;
    }
    
}
