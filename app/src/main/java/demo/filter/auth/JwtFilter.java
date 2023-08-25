package demo.filter.auth;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            String token = request.getHeader("Authorization");
            token = token.replace("Bearer ", "");
            RequestCustomer reqCustomer = jwtUtils.parseJwtToken(token);
            request.setAttribute("reqCustomer", reqCustomer);
        } catch (Exception e) {
            RequestCustomer reqCustomer = new RequestCustomer(0L, null);
            request.setAttribute("reqCustomer", reqCustomer);
        }

        chain.doFilter(request, response);
    }

}