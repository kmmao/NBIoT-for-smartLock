package com.routon.plsy.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * wangxiwei
 */
public class JwtToken {

    public static String secret = "RoutonJinglun";

    /**
     * 创建JWT Token
     * @return
     */
    public static String createToken(){

        String token = null;
        //签发时间
        Date iatDate = new Date();
        //过期时间
        Calendar nowTime = Calendar.getInstance();
        //三分钟之后过期
        nowTime.add(Calendar.MINUTE, 3);
        Date expiresDate = nowTime.getTime();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        try {
            token = JWT.create()
                    .withHeader(map)
                    .withClaim("locale", "HangZhou")
                    .withClaim("customer", "Guangan")
                    .withClaim("project", "NBIoT")
                    .withExpiresAt(expiresDate)
                    .withIssuedAt(iatDate)
                    .sign(Algorithm.HMAC256(secret));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return token;
    }

    /**
     * 验证Token
     */
    public static Map<String, Claim> verifyToken(String token){
        JWTVerifier verifier = null;
        try {
            verifier = JWT.require(Algorithm.HMAC256(secret))
                    .build();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaims();
    }

    public static void main(String[] args){
        //String token = JwtToken.createToken();
        String errortoken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJwcm9qZWN0IjoiTkJJb1QiLCJsb2NhbGUiOiJIYW5nWmhvdSIsImV4cCI6MTU0NDc3MzA1NSwiaWF0IjoxNTQ0NzcyODc1LCJjdXN0b21lciI6Ikd1YW5nYW4ifQ.O6K9P5X2YbhxkyLLRBl9Ti95xboJUgSTpQ3kBz12aEc";
        //System.out.println(token);

        JwtToken.verifyToken(errortoken);
    }
}