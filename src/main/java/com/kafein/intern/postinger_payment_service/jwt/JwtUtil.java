package com.kafein.intern.postinger_payment_service.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Component
public class JwtUtil {

    public String getToken(HttpServletRequest request){
        String jwt = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("JWT_TOKEN")) {
                    jwt = cookie.getValue();
                    break;
                }
            }
        }return jwt;
    }
    private String secretKey = "XOMmbTa4keacmII06k7toYAJDnRpcgl3+v89wqPci9y1TKbNmO76U7ONYDhCuYio+Q/g2IMAX2eY4MQ0g/I3aQ==";
    public Long extractIdClaim(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
        Long id = claims.get("id", Long.class);
        return id;
    }

}