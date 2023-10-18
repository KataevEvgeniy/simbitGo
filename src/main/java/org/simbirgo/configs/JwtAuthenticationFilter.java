package org.simbirgo.configs;

import org.simbirgo.entities.UserEntity;
import org.simbirgo.repositories.UserEntityRepository;
import org.simbirgo.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    private final UserEntityRepository userRepository;

    @Autowired
    JwtAuthenticationFilter(JwtService jwtService,UserDetailsService userDetailsService,UserEntityRepository userRepository){
            this.jwtService = jwtService;
            this.userDetailsService = userDetailsService;
            this.userRepository = userRepository;
    }

    private final String[] matchAdminPaths = {"/Admin/.*","/Account/.*"};
    private final String[] matchUserPaths = {"/Account/.*","/Payment/.*"};
    private final String[] matchAnyPaths = {"/Account/SignIn","/Account/SignUp","/Transport/{id}","/Rent/Transport"};

    private final String ROLE_ADMIN = "ROLE_ADMIN";
    private final String ROLE_USER = "ROLE_USER";



    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String token = httpServletRequest.getHeader("Authorization");
        String path = httpServletRequest.getRequestURI();

        if(token == null && isPathAllowed(matchAnyPaths,path)){
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }

        if(token != null && jwtService.isJwtValid(token)){
            Long userId = jwtService.getUserId(token);
            UserEntity user = userRepository.findById(userId).get();
            String role = defineRole(user);

            if(Objects.equals(role, ROLE_USER) && isPathAllowed(matchUserPaths,path)){
                filterChain.doFilter(httpServletRequest,httpServletResponse);
                return;
            }

            if(Objects.equals(role, ROLE_ADMIN) && isPathAllowed(matchAdminPaths,path)){
                filterChain.doFilter(httpServletRequest,httpServletResponse);
                return;
            }
        }
        httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN,"Access denied");
    }

    public boolean isPathAllowed(String[] matchExprs,String path){
        for (String matchExpr : matchExprs) {
            if (path.matches(matchExpr)) {
                return true;
            }
        }
        return false;
    }


    public String defineRole(UserEntity user){
        if(user.isAdmin()){
            return ROLE_ADMIN;
        }
        else {
            return ROLE_USER;
        }
    }



}