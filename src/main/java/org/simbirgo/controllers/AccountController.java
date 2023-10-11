package org.simbirgo.controllers;


import org.simbirgo.entities.UsersEntity;
import org.simbirgo.services.JwtGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Account")
public class AccountController {


    JwtGenerator jwtGenerator;

    @Autowired
    AccountController(JwtGenerator jwtGenerator){
        this.jwtGenerator = jwtGenerator;
    }


    @PostMapping("/SignIn")
    public ResponseEntity<?> signIn(@RequestBody UsersEntity user){

        String jwt = jwtGenerator.generateJwtToken(user.getUsername());

        System.out.println(jwtGenerator.getUsername(jwt));


        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization",jwt);

        return new ResponseEntity<>("Success",headers, HttpStatus.OK);
    }





}
