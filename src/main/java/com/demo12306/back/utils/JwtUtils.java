package com.demo12306.back.utils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;

public class JwtUtils {
    private static final long EXPIRATION_TIME = 864_000_000; // 10 days
    private static final String SECRET_KEY = "secretKey";
    public static String generateToken(Integer status,Integer id,Boolean minister) {
        Date expiryDate = new Date(System.currentTimeMillis() + EXPIRATION_TIME);
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("status",status);
        claims.put("id",id);
        claims.put("minister",minister);
        System.out.println(minister);
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }
    public static Integer getStatusFromToken(String token) {
        Jws<Claims> claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token);
        return Integer.valueOf(claims.getBody().get("status").toString());
    }
    public static Integer getIdFromToken(String token) {
        Jws<Claims> claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token);
        return Integer.valueOf(claims.getBody().get("id").toString());
    }
    public static Boolean getMinisterFromToken(String token) {
        Jws<Claims> claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token);
        System.out.println("Claims: " + claims);
        return Boolean.valueOf(claims.getBody().get("minister").toString());
    }
    public static boolean isTokenValid(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}