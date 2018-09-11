package com.barton.sbc.utils;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.barton.sbc.domain.entity.auth.AuthUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * jwt 工具类
 */
@Component
public class JwtTokenUtil implements Serializable {
    /**
     * 密钥
     */
    private final static String secret = "aaaaaaaa";
    /**
     * 多长时间刷新token
     */
    private final static int refreshTime = 3;

    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    private String generateToken(Map<String, Object> claims) {
        Date expirationDate = new Date(System.currentTimeMillis() + 2592000L * 1000);//一个月
        return Jwts.builder().setClaims(claims).setExpiration(expirationDate).signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    private static Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    /**
     * 生成令牌
     *
     * @param userDetails 用户
     * @return 令牌
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>(2);
        claims.put("sub", userDetails.getUsername());
        claims.put("created", new Date());
        return generateToken(claims);
    }

    /**
     * 从令牌中获取用户名
     *
     * @param token 令牌
     * @return 用户名
     */
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

    /**
     * 判断令牌是否过期
     *
     * @param token 令牌
     * @return 是否过期
     */
    public static Boolean isTokenExpired(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 刷新令牌
     *
     * @param token 原令牌
     * @return 新令牌
     */
    public String refreshToken(String token) {
        String refreshedToken;
        try {
            Claims claims = getClaimsFromToken(token);
            claims.put("created", new Date());
            refreshedToken = generateToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    /**
     * 验证令牌
     *
     * @param token       令牌
     * @param userDetails 用户
     * @return 是否有效
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        AuthUser user = (AuthUser) userDetails;
        String username = getUsernameFromToken(token);
        return (username.equals(user.getUsername()) && !isTokenExpired(token));
    }

    /**
     * 验证令牌
     * @param token        令牌
     * @param userDetails  用户
     * @return 0:未验证通过  1:验证通过，需要重新刷新token  2:验证通过，不需要重新刷新token
     */
    public static int validateTokenDetail(String token, UserDetails userDetails) {
        AuthUser user = (AuthUser) userDetails;
        Claims claims = getClaimsFromToken(token);
        try{
            String username = claims.getSubject();
            if(username.equals(user.getUsername()) && !isTokenExpired(token)){
                Date created = DateUtil.date((Long) claims.get("created"));
                Long hour = DateUtil.between(created,new Date(), DateUnit.HOUR);
                if(hour >= refreshTime){
                    return 1;//验证通过，需要重新刷新token
                }else{
                    return 2;//验证通过，不需要重新刷新token
                }
            }else{
                return 0;//未验证通过
            }
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }

    }

    public static void main(String [] args){
        //System.out.println(validateToken1("eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE1MzkyNDE2NjAsInN1YiI6ImFhYSIsImNyZWF0ZWQiOjE1MzY2NDk2NjAzMzF9.exwuInlXQ602AghKm3JEy6Jyz2soDIwcHqRlNfwuN9ET2_Fjw7aDje0GrNcjLJyUtP_kmC0_LVu1CXNbWVUIOA",null));
        //System.out.println(validateToken1("eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE1MzkyNDU0NTUsInN1YiI6ImFhYSIsImNyZWF0ZWQiOjE1MzY2NTM0NTU1NDN9.ubhAHA2kCGyMDUEFG9gCOPC9ikGgwamPXa_F85pBjmOpAvOBTAtVBI3GNZZXEkjcTYkUwGYDMnoR7pL15kyrHg",null));
    }
}
