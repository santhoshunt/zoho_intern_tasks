package servletclass;

import java.net.http.HttpClient.Redirect;
import java.security.Key;
import io.jsonwebtoken.*;
import java.util.Date;
import javax.crypto.spec.*;
import javax.xml.bind.DatatypeConverter;

public class Test {

    public static void main(String[] args) {

        String id = "1";
        String issuer = "santhosh";
        String subject = "this is an encrypted message";

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        // We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter
                .parseBase64Binary("com.org.username.password.issanthoshsandy7237istheuaerameoftherandomeuser");
        Key signingKey = new SecretKeySpec(apiKeySecretBytes,
                signatureAlgorithm.getJcaName());

        // Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder().setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(issuer)
                .signWith(signatureAlgorithm, signingKey);

        long expMillis = nowMillis + 120000;
        Date exp = new Date(expMillis);
        builder.setExpiration(exp);
        System.out.println(builder.compact());

        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter
                        .parseBase64Binary("com.org.username.password.issanthoshsandy7237istheuaerameoftherandomeuser"))
                .parseClaimsJws(builder.compact()).getBody();
        System.out.println("Decoded: " + claims.get("iss"));
    }
}
