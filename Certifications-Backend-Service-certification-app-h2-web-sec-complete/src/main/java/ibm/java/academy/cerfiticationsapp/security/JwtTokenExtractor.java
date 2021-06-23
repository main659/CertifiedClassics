package ibm.java.academy.cerfiticationsapp.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.TokenExtractor;
//import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
//import org.springframework.stereotype.Service;

@Controller
public class JwtTokenExtractor implements TokenExtractor{

    @Override
    public Authentication extract(HttpServletRequest request) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
