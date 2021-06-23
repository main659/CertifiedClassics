package ibm.java.academy.cerfiticationsapp.security;

import javax.servlet.http.HttpServletRequest;

//import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.security.web.util.matcher.RequestMatcher;
//import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
//import org.springframework.stereotype.Service;

@Controller
public class JwtRequestMacher implements RequestMatcher{

    @Override
    public boolean matches(HttpServletRequest request) {
        System.out.print(request.toString());
        return false;
    }
    
}
