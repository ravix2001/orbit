package com.ravi.orbit.filter;

import com.ravi.orbit.exceptions.ExpiredTokenException;
import com.ravi.orbit.exceptions.InvalidTokenException;
import com.ravi.orbit.utils.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

   private final UserDetailsService userDetailsService;

   private final JwtUtil jwtUtil;

   @Override
   protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
       try {
           String authorizationHeader = request.getHeader("Authorization");
           String username = null;
           String jwt = null;

           if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
               jwt = authorizationHeader.substring(7);
               username = jwtUtil.extractUsername(jwt);
           }

           if (username != null) {
               UserDetails userDetails = userDetailsService.loadUserByUsername(username);
               if (jwtUtil.validateToken(jwt)) {
                   UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                   auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                   SecurityContextHolder.getContext().setAuthentication(auth);
               }
           }

           chain.doFilter(request, response);
       } catch (ExpiredTokenException ex) {
           // Log and rethrow the exception to be handled by the global handler
           response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token expired");
       } catch (InvalidTokenException e) {
           response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token invalid");
       } catch (Exception ex) {
           response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An internal error occurred");
       }
   }

}