package cn.ydcat.utils.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.TextCodec;

import java.util.Date;
import java.util.Map;

/**
 * jwt工具类
 * @author YD-Cat
 * @date 2020年09月23日
 */
public class JwtUtils {
    // 密钥
    private static final String SECRET = "123456";
    // 加密算法
    private static final SignatureAlgorithm ALGORITHM = SignatureAlgorithm.HS256;
    // token存活时间
    private static long EXP_TIME = 30*24*60*60*1000L;

    /**
     * 构建token
     * @param map 私有声明
     * @return token
     */
    public static String createToken(Map<String, Object> map){
        Date nowDate = new Date(); // 签发时间
        Date expDate = new Date(nowDate.getTime()+EXP_TIME); // 过期时间
        JwtBuilder builder = Jwts.builder(); // 生成器
        builder.setHeaderParam("typ", "JWT"); // 头部
        if(map != null){
            builder.setClaims(map); // 设置私有声明
        }
        builder.setIssuedAt(nowDate); // 设置签发时间
        builder.setExpiration(expDate); // 设置过期时间
        builder.signWith(ALGORITHM, getSecret()); //设置签名秘钥
        return builder.compact();
    }

    /**
     * 获取全部信息
     * @param token token
     * @return jwt全部信息
     * @throws ExpiredJwtException token过期
     * @throws SignatureException 解密失败
     */
    public static Claims getClaims(String token) throws ExpiredJwtException, SignatureException{
        return Jwts.parser()
                .setSigningKey(getSecret())
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 获取token中某个字段的信息
     * @param token token
     * @param key 字段
     * @return 字段信息
     */
    public static Object getInfo(String token, String key){
        return JwtUtils.getClaims(token).get(key);
    }

    /**
     * 获取密钥
     * @return 密钥
     */
    private static byte[] getSecret() {
        return TextCodec.BASE64.encode(SECRET).getBytes();
    }
}
