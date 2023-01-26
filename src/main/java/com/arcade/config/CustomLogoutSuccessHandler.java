package com.arcade.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

public class CustomLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler implements LogoutSuccessHandler {

    private final static Logger logger = Logger.getLogger(CustomLogoutSuccessHandler.class.getName());

    @Override
    public void onLogoutSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {
        String userName = authentication.getName();
        String refererUrl = request.getHeader("Referer");
        logger.info("User " + userName + " logout from: " + refererUrl);
        // TODO: maybe add a redirect to the page the user was before
        response.sendRedirect(request.getContextPath() + "/?logout");
    }

}
