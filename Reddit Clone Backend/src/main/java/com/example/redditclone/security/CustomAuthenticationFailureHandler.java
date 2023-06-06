package com.example.redditclone.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private LoginAttemptService loginAttemptService;

    @Autowired
    CustomAuthenticationFailureHandler(HttpServletRequest request, HttpServletResponse response, LoginAttemptService loginAttemptService) {
        this.loginAttemptService = loginAttemptService;
        this.request = request;
        this.response = response;
    }

    @Override
    public void onAuthenticationFailure(final HttpServletRequest request,
                                        final HttpServletResponse response,
                                        final AuthenticationException exception) throws IOException, ServletException {

        super.onAuthenticationFailure(request, response, exception);

        if (loginAttemptService.isBlocked()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This address is blocked");
        }
    }
}
