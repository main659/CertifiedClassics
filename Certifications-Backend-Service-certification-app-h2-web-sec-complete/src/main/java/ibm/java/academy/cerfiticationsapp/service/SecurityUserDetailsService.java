package ibm.java.academy.cerfiticationsapp.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ibm.java.academy.cerfiticationsapp.model.Role;
import ibm.java.academy.cerfiticationsapp.model.User;
import ibm.java.academy.cerfiticationsapp.repository.UserJpaRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SecurityUserDetailsService implements UserDetailsService{
    @Autowired
    private UserJpaRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByName(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not present"));

        return new org.springframework.security.core.userdetails.User(user.getName(),
           user.getPassword(),
           mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(List<Role> roles) {
        return List.of(() -> "USER");
  }
}
