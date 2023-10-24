package org.simbirgo.configs;

import org.simbirgo.entities.UserEntity;
import org.simbirgo.repositories.UserEntityRepository;
import org.simbirgo.services.AccountService;
import org.simbirgo.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    private final JwtService jwtService;


    private final AccountService accountService;

    @Autowired
    JwtAuthenticationFilter(JwtService jwtService,AccountService accountService) {
            this.jwtService = jwtService;
            this.accountService = accountService;
    }

    private final String[] matchAdminPaths = {"/api/.*"};
    private final String[] matchUserPaths = {"/api/Account/.*","/api/Transport.*","/api/Payment/.*","/api/Rent/.*"};
    private final String[] matchAnyPaths = {"/api/Account/SignIn","/api/Account/SignUp","/api/Transport/[0-9]*","/api/Rent/Transport","/api/swagger-ui/.*","/api/v3/api-docs","/api/swagger-resources.*"};

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
            UserEntity user = accountService.findUserById(userId);
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
