package com.Ecommerce_BE.Security;

import com.Ecommerce_BE.Model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

@Service
@Slf4j
public class JwtUtils {

    private static final long EXPIRATION_TIME_IN_MILLISECOND = 1000L * 60L *60L *24L * 30L * 6L; //han su dung token la 6 thang

    private SecretKey key;

    @Value("${secretJwtString}")
    private String secretJwtString;

    //tu dong goi khi khoi tao class nay
    @PostConstruct
    private void init()
    {
        byte[] keyBytes = secretJwtString.getBytes(StandardCharsets.UTF_8);
        this.key = new SecretKeySpec(keyBytes, "HmacSHA256");
    }

    public String generateToken(User user)
    {
        String userName = user.getEmail();
        return generateToken(userName);
    }

    public String generateToken(String userName) {
        return Jwts.builder()
                .subject(userName)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_IN_MILLISECOND))
                .signWith(key)
                .compact();
    }

    public String getUserNameFromToken(String token)
    {
        return extractClaims(token, Claims::getSubject);
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction)
    {
        return claimsTFunction.apply(Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload());
    }

    public boolean isTokenValid(String token, UserDetails userDetails)
    {
        final String userName = getUserNameFromToken(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token)
    {
        return extractClaims(token, Claims::getExpiration).before(new Date());
    }


}
