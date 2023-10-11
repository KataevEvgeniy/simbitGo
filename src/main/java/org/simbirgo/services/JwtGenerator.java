package org.simbirgo.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public class JwtGenerator {

    String secretKey = "secretKeyjfdfgnfjdknfhfbfbfbfjds";


    public String generateJwtToken(String username) {

        byte[] keyBytes = secretKey.getBytes();

        SecretKey key = Keys.hmacShaKeyFor(keyBytes);


        String jwt = Jwts.builder()
                .subject("user")
                .subject("simbit_go_api")
                .claim("username", username)
                .signWith(key)
                .compact();
        return jwt;

    }


    public Jwt<?,?> getUsername(String jwt){
        byte[] keyBytes = secretKey.getBytes();

        SecretKey key = Keys.hmacShaKeyFor(keyBytes);


        Jwt<?,?> token =  Jwts.parser().verifyWith(key).build().parse(jwt);


        return token;
    }

}
