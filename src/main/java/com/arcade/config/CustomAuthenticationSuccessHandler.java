package com.arcade.config;

import com.arcade.entity.user.User;
import com.arcade.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.Logger;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final static Logger logger = Logger.getLogger(CustomAuthenticationSuccessHandler.class.getName());

    private final UserService userService;

    @Autowired
    public CustomAuthenticationSuccessHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {
        String userName = authentication.getName();
        logger.info("User " + userName + " logged in.");

        User user = userService.findByUsername(userName);
        HttpSession session = request.getSession();
        session.setAttribute("user", user);

        DefaultSavedRequest savedRequest = (DefaultSavedRequest) session.getAttribute("SPRING_SECURITY_SAVED_REQUEST");
        if (savedRequest != null) {
            String url = savedRequest.getRedirectUrl();
            response.sendRedirect(url);
        }
        else {
            response.sendRedirect(request.getContextPath() + "/?login");
        }

    }

}
