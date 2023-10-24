package org.simbirgo.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.simbirgo.repositories.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class JwtService {

    String secretKey = "secretKeyjfdfgnfjdknfhfbfbfbfjds";

    UserEntityRepository userRepository;
    BlackListService blackListService;

    @Autowired
    JwtService(UserEntityRepository userRepository, BlackListService blackListService) {
        this.userRepository = userRepository;
        this.blackListService = blackListService;
    }


    public String generateJwtToken(Long userId) {
        byte[] keyBytes = secretKey.getBytes();
        SecretKey key = Keys.hmacShaKeyFor(keyBytes);
        Calendar expirationDate = Calendar.getInstance();
        expirationDate.add(Calendar.DAY_OF_MONTH, 1);

        return Jwts.builder()
                .subject("user")
                .subject("simbir_go_api")
                .expiration(expirationDate.getTime())
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

    public boolean isJwtValid(String jws) {
        byte[] keyBytes = secretKey.getBytes();
        SecretKey key = Keys.hmacShaKeyFor(keyBytes);
        Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(jws).getPayload();

        Long id = Long.parseLong(claims.get("id").toString());
        Date expDate = claims.getExpiration();

        if (userRepository.existsById(id) && expDate.after(new Date()) && !blackListService.isExist(jws)) {
            return true;
        }

        return false;
    }


}
