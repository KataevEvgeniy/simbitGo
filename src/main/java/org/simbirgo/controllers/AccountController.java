package org.simbirgo.controllers;


import org.postgresql.util.PSQLException;
import org.simbirgo.entities.BlacklistEntity;
import org.simbirgo.entities.UserEntity;
import org.simbirgo.repositories.BlacklistEntityRepository;
import org.simbirgo.repositories.UserEntityRepository;
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
    UserEntityRepository repository;
    BlacklistEntityRepository blacklistEntityRepository;

    @Autowired
    AccountController(JwtService jwtService, UserEntityRepository repository, BlacklistEntityRepository blacklistEntityRepository) {
        this.jwtService = jwtService;
        this.repository = repository;
        this.blacklistEntityRepository = blacklistEntityRepository;
    }


    @PostMapping("/SignIn")
    public ResponseEntity<?> signIn(@RequestBody UserEntity user) {
        try {
            UserEntity userInDb = repository.findUserEntityByUsername(user.getUsername());
            if (Objects.equals(userInDb.getPassword(),user.getPassword())) {
                String jws = jwtService.generateJwtToken(userInDb.getIdUser());

                HttpHeaders headers = new HttpHeaders();
                headers.add("Authorization", jws);
                return new ResponseEntity<>("Success", headers, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("user not found", HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/Me")
    public ResponseEntity<?> me(HttpServletRequest request) {
        String jws = request.getHeader("Authorization");

        try {
            Long userId = jwtService.getUserId(jws);
            UserEntity user = repository.findById(userId).get();

            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("invalid data", HttpStatus.BAD_REQUEST);
        }
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

    @PutMapping("/Update")
    public ResponseEntity<?> update(HttpServletRequest request, @RequestBody UserEntity user) {
        String jws = request.getHeader("Authorization");
        try {
            if (!repository.existsByUsername(user.getUsername())) {
                Long userId = jwtService.getUserId(jws);
                UserEntity userInDb = repository.findById(userId).get();
                userInDb.setPassword(user.getPassword());
                userInDb.setUsername(user.getUsername());
                repository.save(userInDb);
                return new ResponseEntity<>("user updated", HttpStatus.OK);
            }
            return new ResponseEntity<>("username already exist", HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>("invalid data", HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/SignOut")
    public ResponseEntity<?> signOut(HttpServletRequest request) {
        String jws = request.getHeader("Authorization");
        if (blacklistEntityRepository.existsByToken(jws)) {
            return new ResponseEntity<>("token already in blacklist", HttpStatus.OK);
        }
        blacklistEntityRepository.save(new BlacklistEntity(jws, 0));
        return new ResponseEntity<>("sign out", HttpStatus.OK);
    }
}
