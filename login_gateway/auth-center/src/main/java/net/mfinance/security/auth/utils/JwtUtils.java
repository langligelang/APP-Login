package net.mfinance.security.auth.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import net.mfinance.security.auth.JwtAuthenticationConfig;
import net.mfinance.security.auth.domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

    @Autowired
    JwtAuthenticationConfig config;
    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(config.getSecret().getBytes()).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    public Boolean isTokenExpired(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            //失效
            return true;
        }
    }

    public String refreshToken(String token,String username) {
        String refreshedToken;
        try {
            Claims claims = getClaimsFromToken(token);
            refreshedToken = generateToken(claims,username);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    public String getUsernameFromToken(String token) {
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    private String generateToken(Map<String, Object> claims,String username) {
        Instant now = Instant.now();
        return Jwts.builder().setSubject(username).setClaims(claims).setExpiration(Date.from(now.plusSeconds(config.getExpirationRefresh())))
                .setIssuedAt(Date.from(now)).signWith(SignatureAlgorithm.HS256, config.getSecret()).compact();
    }


    public String generateAccessToken(SysUser sysUser) {
        Instant now = Instant.now();
        String token = Jwts.builder()
                .setSubject(sysUser.getUsername())
                .claim("authorities", sysUser.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(config.getExpiration())))
                .signWith(SignatureAlgorithm.HS256, config.getSecret().getBytes())
                .compact();
        return token;
    }




}
