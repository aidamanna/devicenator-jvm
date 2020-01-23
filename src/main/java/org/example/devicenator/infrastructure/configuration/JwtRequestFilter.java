package org.example.devicenator.infrastructure.configuration;

import io.jsonwebtoken.ExpiredJwtException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.example.devicenator.application.authenticateuser.JwtUserDetails;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUserDetails jwtUserDetails;
    private final JwtToken jwtToken;
    private final Logger logger;

    @Autowired
    public JwtRequestFilter(JwtToken jwtToken, JwtUserDetails jwtUserDetails,
        Logger logger) {
        this.jwtToken = jwtToken;
        this.jwtUserDetails = jwtUserDetails;
        this.logger = logger;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain chain) throws ServletException, IOException {
        String username = null;
        String token = null;
        String requestTokenHeader = request.getHeader("Authorization");

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            token = requestTokenHeader.substring(7);

            try {
                username = jwtToken.getUsername(token);
            } catch (IllegalArgumentException e) {
                logger.info("Unable to get jwt Ttoken");
            } catch (ExpiredJwtException e) {
                logger.info("Jwt token has expired");
            }
        } else {
            logger.warn("Jwt token is not valid. Token: " + token);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = jwtUserDetails.loadUserByUsername(username);

            if (jwtToken.validate(token, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                    .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext()
                    .setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }
}
