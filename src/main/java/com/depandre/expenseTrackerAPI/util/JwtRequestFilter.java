package com.depandre.expenseTrackerAPI.util;


import com.depandre.expenseTrackerAPI.security.CustomUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = null;
        String userEmail = null;
        /* Now the requestTokenHeader should not be null and start from Bearer */
        if(requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")){
            /* Get the actual token pass substring as 6 --> Bearer + 1 space */
            jwtToken = requestTokenHeader.substring(6);
            try{
                userEmail = jwtTokenUtil.getUserNameFromToken(jwtToken);
            }
            catch (IllegalArgumentException e){
                throw new RuntimeException("Unable to get jwt Token");
            } catch (ExpiredJwtException e){
                throw new RuntimeException("Jwt Token has expired");
            }
        }
        /* Once we get the userEmail we need to validate it
        1. check userEmail is not null
        2. In securityContext it should not be present
         */
        if(userEmail != null && (SecurityContextHolder.getContext().getAuthentication() == null)){
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(userEmail);
            if(jwtTokenUtil.validateToken(jwtToken, userDetails)){
                // Set the Authentication
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // Save in Security Context
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
