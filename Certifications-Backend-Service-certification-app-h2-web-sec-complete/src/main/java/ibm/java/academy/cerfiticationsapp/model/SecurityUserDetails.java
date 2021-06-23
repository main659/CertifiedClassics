package ibm.java.academy.cerfiticationsapp.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class SecurityUserDetails implements UserDetails {
    private  User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        final Set<GrantedAuthority> _grntdAuths = new HashSet<GrantedAuthority>();
        List<Role> _roles = null;

        if (user!=null) {
            _roles = user.getRoles();
        }

        if (_roles!=null) {
            for (Role _role : _roles) {
                    _grntdAuths.add(_role);
            }
        }

        return _grntdAuths;
    }



    @Override
    public String getPassword() {
        return user.getPassword();
    }



    @Override
    public String getUsername() {
        if (this.user == null) {
            return null;
        }
        return user.getEmail();
    }



    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return false;
    }



    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return false;
    }



    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return false;
    }



    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return false;
    }



    @Override
    public String toString() {
            return "CustomUserDetails [user=" + user + "]";
    }
}
