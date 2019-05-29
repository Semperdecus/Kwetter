/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.Date;
import javax.xml.bind.DatatypeConverter;
import models.Account;
import io.jsonwebtoken.*;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.Key;
import java.util.Properties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gherkin.deps.com.google.gson.JsonObject;
import java.io.IOException;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author teren
 */
public class JwtUtil {

    /**
     * Expiration time of JWT token in milliseconds 604 800 000 = 1 week
     */
    private final long EXPIRATIONTIME = 604800000;

    public JwtUtil() {
    }

    public String makeAccountJwtToken(String id, String issuer, Account account) throws IOException {
        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(apiKey());
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder().setId(id)
                .setIssuedAt(now)
                .setSubject(createJSONAccount(account))
                .setIssuer(issuer)
                .signWith(signatureAlgorithm, signingKey);

        Date exp;

        //if it has been specified, let's add the expiration
        if (EXPIRATIONTIME >= 0) {
            long expMillis = nowMillis + EXPIRATIONTIME;
            exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        JsonObject token = new JsonObject();
        token.addProperty("token", builder.compact());
        token.addProperty("expiresIn", exp.toString());

        //Builds the JWT and serializes it to a compact, URL-safe string
        return token.toString();
    }

    public String apiKey() throws IOException {
        String apiKey = null;
        InputStream inputStream = null;

        try {
            Properties prop = new Properties();
            String propFileName = "config.properties";

            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }

            Date time = new Date(System.currentTimeMillis());

            // get the property value and print it out
            apiKey = prop.getProperty("apiKey");
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            inputStream.close();
        }

        return apiKey;

    }

    public String createJSONAccount(Account account) {
        String jsonInString = "";

        ObjectMapper mapper = new ObjectMapper();
        try {
            // Convert object to JSON string
            jsonInString = mapper.writeValueAsString(account);
            // System.out.println(jsonInString);

            // Convert object to JSON string and pretty print
            //jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(account);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return jsonInString;
    }

    public boolean validateJwt(String jwsString) throws IOException {
        String[] splited = jwsString.split(" ");
        int length = splited.length;
       
        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        //We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(apiKey());
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        Jws<Claims> jws;

        try {
            jws = Jwts.parser() 
                    .setSigningKey(signingKey) 
                    .parseClaimsJws(splited[length -1]);

            return true;
        } catch (JwtException ex) { 
            ex.printStackTrace();
            // we *cannot* use the JWT as intended by its creator
        }
        
        return false;
    }
}
