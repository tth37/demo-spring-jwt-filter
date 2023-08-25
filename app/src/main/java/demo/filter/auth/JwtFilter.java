package demo.filter.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        System.out.println("Header: " + request.getHeader("Authorization"));
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