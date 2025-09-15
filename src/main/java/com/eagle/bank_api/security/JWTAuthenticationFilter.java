package com.eagle.bank_api.security;

import com.eagle.bank_api.error.UnauthorizedException;
import com.eagle.bank_api.user.UserService;
import com.eagle.bank_api.user.entity.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class JWTAuthenticationFilter extends OncePerRequestFilter{

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.equals("/v1/users") || path.equals("/v1/auth/login");
    }

    private JWTService jwtService;
    private UserService userService;

    public JWTAuthenticationFilter(JWTService jwtService, UserService userService){
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = getJwtTokenFromRequest(request);

        if(!StringUtils.hasText(token)){
            filterChain.doFilter(request, response);
            return;
        }

        String email;
        try{
            email = jwtService.extractEmail(token);
        } catch (Exception e) {
            throw new UnauthorizedException("Invalid token");
        }

        User user = userService.findByEmail(email);

        if(user == null) {
            throw new UnauthorizedException("User not found");
        }

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                user.getUserId(),
                null,
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    private String getJwtTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
