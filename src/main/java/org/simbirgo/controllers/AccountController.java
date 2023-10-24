package org.simbirgo.controllers;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.postgresql.util.PSQLException;
import org.simbirgo.entities.BlacklistEntity;
import org.simbirgo.entities.UserEntity;
import org.simbirgo.repositories.BlacklistEntityRepository;
import org.simbirgo.repositories.UserEntityRepository;
import org.simbirgo.services.AccountService;
import org.simbirgo.services.BlackListService;
import org.simbirgo.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Controller
@RequestMapping("/Account")
public class AccountController {


    JwtService jwtService;
    AccountService accountService;
    BlackListService blackListService;

    @Autowired
    AccountController(JwtService jwtService, AccountService accountService,BlackListService blackListService) {
        this.jwtService = jwtService;
        this.accountService = accountService;
        this.blackListService = blackListService;
    }


    @PostMapping("/SignIn")
    public ResponseEntity<?> signIn(@RequestBody UserEntity user) {
        UserEntity userInDb = accountService.findUserByUsername(user.getUsername());
        if (Objects.equals(userInDb.getPassword(), user.getPassword())) {
            String jws = jwtService.generateJwtToken(userInDb.getIdUser());

            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", jws);
            return new ResponseEntity<>("Success", headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("password isn't valid",HttpStatus.UNAUTHORIZED);
        }
    }


    @GetMapping("/Me")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "Authorization")})
    public ResponseEntity<?> me(HttpServletRequest request) {
        String jws = request.getHeader("Authorization");
        Long userId = jwtService.getUserId(jws);
        UserEntity user = accountService.findUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/SignUp")
    public ResponseEntity<?> signUp(@RequestBody UserEntity user) {
        UserEntity entity = new UserEntity();
        entity.setAdmin(false);
        entity.setUsername(user.getUsername());
        entity.setPassword(user.getPassword());
        accountService.createUser(entity);
        return new ResponseEntity<>("user created", HttpStatus.CREATED);
    }

    @PutMapping("/Update")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "Authorization")})
    public ResponseEntity<?> update(HttpServletRequest request, @RequestBody UserEntity user) {
        String jws = request.getHeader("Authorization");
        Long userId = jwtService.getUserId(jws);
        UserEntity userInDb = accountService.findUserById(userId);
        userInDb.setPassword(user.getPassword());
        userInDb.setUsername(user.getUsername());
        accountService.updateById(userInDb, userId);
        return new ResponseEntity<>("user updated", HttpStatus.OK);
    }


    @PostMapping("/SignOut")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "Authorization")})
    public ResponseEntity<?> signOut(HttpServletRequest request) {
        String jws = request.getHeader("Authorization");
        blackListService.addToBlackList(jws);
        return new ResponseEntity<>("sign out", HttpStatus.OK);
    }
}
