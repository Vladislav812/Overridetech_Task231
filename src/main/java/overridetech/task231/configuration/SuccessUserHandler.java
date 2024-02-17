package overridetech.task231.configuration;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import overridetech.task231.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Component
public class SuccessUserHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        if (roles.contains("ROLE_admin")) {
            response.sendRedirect("/admin/users");
        } else if (roles.contains("ROLE_user")) {
//            Long id = ((User) authentication.getPrincipal()).getId();
//            response.sendRedirect("/user/" + id);
            response.sendRedirect("/user");
        } else {
            response.sendRedirect("/login");
        }
    }
}
