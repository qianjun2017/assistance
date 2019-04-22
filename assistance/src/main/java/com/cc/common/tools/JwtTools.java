/**
 * 
 */
package com.cc.common.tools;

import java.util.Date;
import java.util.HashMap;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import com.cc.common.utils.UUIDUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

/**
 * @author Administrator
 *
 */
public class JwtTools {
	
	private static String JWTKEY = "!@$!AZ#SR$E%C^MUD%B(*$6sFRs";
	
	public static long JWTTTLMILLIS = 3600000;

	private static SecretKey generalKey(){
		byte[] encodedKey = Base64.decodeBase64(JWTKEY);
		SecretKey secretKey = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
		return secretKey;
	}
	
	private static Claims parseJWT(String token) {
		SecretKey secretKey = generalKey();
		Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
		return claims;
	}
	
	/**
	 * 创建TOKEN
	 * @param customerBean
	 * @param ttlMillis
	 * @return
	 */
	public static String createToken(Object object, long ttlMillis){
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		long nowMillis = System.currentTimeMillis();
		Date now = DateTools.now();
		SecretKey secretKey = generalKey();
		JwtBuilder builder = Jwts.builder()
				.setClaims(JsonTools.toObject(JsonTools.toJsonString(object), HashMap.class))
				.setId(UUIDUtils.getUuid())
				.setIssuedAt(now)
				.signWith(signatureAlgorithm, secretKey);
		if (ttlMillis >= 0) {
			long expMillis = nowMillis + ttlMillis;
			Date expDate = new Date(expMillis);
			builder.setExpiration(expDate);
		}
		return builder.compact();
	}
	
	/**
	 * 验证TOKEN
	 * @param token
	 * @param clazz
	 * @return
	 */
	public static <T> T validToken(String token, Class<T> clazz) {
		try {
			Claims claims = parseJWT(token);
			if (claims!=null) {
				return JsonTools.toObject(JsonTools.toJsonString(claims), clazz);
			}
		} catch (ExpiredJwtException e) {
			e.printStackTrace();
		} catch (UnsupportedJwtException e) {
			e.printStackTrace();
		} catch (MalformedJwtException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return null;
	}
}
