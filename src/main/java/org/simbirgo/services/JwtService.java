package org.simbirgo.services;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.simbirgo.repositories.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public class JwtService {

    String secretKey = "secretKeyjfdfgnfjdknfhfbfbfbfjds";

    UserEntityRepository repository;

    @Autowired
    JwtService(UserEntityRepository repository){
        this.repository = repository;
    }


    public String generateJwtToken(String username) {
        byte[] keyBytes = secretKey.getBytes();
        SecretKey key = Keys.hmacShaKeyFor(keyBytes);

        return Jwts.builder()
                .subject("user")
                .subject("simbit_go_api")
                .claim("username", username)
                .signWith(key)
                .compact();
    }

    @Nullable
    public String getUsername(String jws) {
        byte[] keyBytes = secretKey.getBytes();
        try {
            SecretKey key = Keys.hmacShaKeyFor(keyBytes);
            return Jwts.parser().verifyWith(key).build().parseSignedClaims(jws).getPayload().get("username").toString();
        } catch (SignatureException e) {
            return null;
        }
    }

    public boolean isJwtValid(String jws){
        String username = getUsername(jws);
        if (username == null) {
            return false;
        }

        if(repository.existsByUsername(username)){
            return true;
        }

        return false;
    }


}
