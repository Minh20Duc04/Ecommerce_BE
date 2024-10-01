package com.Ecommerce_BE.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor

public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final CustomUserDetailsService customUserDetailsService;

    @Override // chuyen doi request thanh 1 token, sau do tim xem co nguoi dung nao so huu token do khong, neu co thi load nguoi do len
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getTokenFromRequest(request);
        if(token != null)
        {
            String userName = jwtUtils.getUserNameFromToken(token);

            UserDetails userDetails = customUserDetailsService.loadUserByUsername(userName);

            if(StringUtils.hasText(userName) && jwtUtils.isTokenValid(token, userDetails))
            {
                log.info("VALID JWT FOR {}", userName);

                //khi da xac thuc duoc nguoi dung, authenticationToken se chua thong tin nguoi dung de Spring Security quan ly
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }

    //lay token tu request
    private String getTokenFromRequest(HttpServletRequest request)
    {
        String token = request.getHeader("Authorization");
        if(StringUtils.hasText(token) && StringUtils.startsWithIgnoreCase(token,"Bearer"))
        {
         return token.substring(7);
        }
        return null;
    }

}
