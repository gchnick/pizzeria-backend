package com.idforideas.pizzeria.security;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static com.idforideas.pizzeria.security.CustomEnvironmentVariables.SECRET;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.idforideas.pizzeria.appuser.AppUser;
import com.idforideas.pizzeria.util.Response;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Nick Galan
 * @version 1.0
 * @since 3/10/2022
 */
@Slf4j
public class CustomAuthenticaionFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    public CustomAuthenticaionFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        log.info("Email is: {} ", email);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authentication) throws IOException, ServletException {
        
        Access access = new Access(authentication, request);

        response.setContentType(APPLICATION_JSON_VALUE);

        new ObjectMapper().writeValue(response.getOutputStream(), 
            Response.builder()
                .timeStamp(now())
                .data(getTokens(access))
                .message("Tokens created")
                .status(OK)
                .statusCode(OK.value())
            .build()
        );
    }

    private String createJWT(Access access, int minExpires) {
        AppUser user = (AppUser) access.authentication().getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256(System.getenv(SECRET).getBytes());
        return JWT.create()
            .withSubject(user.getEmail())
            .withExpiresAt(new Date(System.currentTimeMillis() + min * 60 * 1000))
            .withIssuer(access.request().getRequestURL().toString())
            .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
            .sign(algorithm);
    }

    private Collection<String, String> getTokens(Access access) {
        String accessToken = createJWT(access, 10);
        String refreshToken = createJWT(access, 30);
        Map<String, String> tokens = new HashMap<>();
        tokens.put("token", accessToken);
        tokens.put("refreshToken", refreshToken);
        return tokens;
    }

    private record Access (Authentication authentication, HttpServletRequest request) {}

}
