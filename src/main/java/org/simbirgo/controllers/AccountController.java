package org.simbirgo.controllers;


import org.postgresql.util.PSQLException;
import org.simbirgo.entities.UserEntity;
import org.simbirgo.repositories.UserEntityRepository;
import org.simbirgo.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/Account")
public class AccountController {


    JwtService jwtService;
    UserEntityRepository repository;

    @Autowired
    AccountController(JwtService jwtService, UserEntityRepository repository) {
        this.jwtService = jwtService;
        this.repository = repository;
    }


    @PostMapping("/SignIn")
    public ResponseEntity<?> signIn(@RequestBody UserEntity user) {

        UserEntity userInDb;
        try {

            userInDb = repository.findUsersEntityByUsername(user.getUsername());
            if (userInDb.getPassword().equals(user.getPassword())) {
                String jws = jwtService.generateJwtToken(user.getUsername());


                HttpHeaders headers = new HttpHeaders();
                headers.add("Authorization", jws);

                return new ResponseEntity<>("Success", headers, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("user not found", HttpStatus.NOT_FOUND);
        }

    }


    @GetMapping("/Me")
    public ResponseEntity<?> me(HttpServletRequest request) {
        String jws = request.getHeader("Authorization");


        if (jwtService.isJwtValid(jws)) {
            String username = jwtService.getUsername(jws);
            UserEntity user = repository.findUsersEntityByUsername(username);

            return new ResponseEntity<>(user, HttpStatus.OK);
        }

        return new ResponseEntity<>("invalid token", HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/SignUp")
    public ResponseEntity<?> signUp(@RequestBody UserEntity user) {

        try {
            if (repository.existsByUsername(user.getUsername())) {
                return new ResponseEntity<>("already exist", HttpStatus.CONFLICT);
            }
            repository.save(user);
            return new ResponseEntity<>("user created", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("invalid data", HttpStatus.BAD_REQUEST);
        }
    }


}
