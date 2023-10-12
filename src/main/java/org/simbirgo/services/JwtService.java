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


    public String generateJwtToken(Long userId) {
        byte[] keyBytes = secretKey.getBytes();
        SecretKey key = Keys.hmacShaKeyFor(keyBytes);

        return Jwts.builder()
                .subject("user")
                .subject("simbit_go_api")
                .claim("id", userId)
                .signWith(key)
                .compact();
    }

    @Nullable
    public Long getUserId(String jws) {
        byte[] keyBytes = secretKey.getBytes();
        try {
            SecretKey key = Keys.hmacShaKeyFor(keyBytes);
            return Long.parseLong(Jwts.parser().verifyWith(key).build()
                    .parseSignedClaims(jws)
                    .getPayload()
                    .get("id")
                    .toString());
        } catch (SignatureException e) {
            return null;
        }
    }

    public boolean isJwtValid(String jws){
        Long id = getUserId(jws);
        if (id == null) {
            return false;
        }

        if(repository.existsById(id)){
            return true;
        }

        return false;
    }


}
