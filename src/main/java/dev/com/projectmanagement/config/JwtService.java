package dev.com.projectmanagement.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

//@Service
public class JwtService {

    private static final String SECRET_KEY = "/Ply6YpEUxPFC6N32D9YVp+L7KVBMZL3pQxA7U5CP/3UPwU2WfXF0ABYt6l5KKwFV0amR3Vdfjxv1K8fFigDv5PQIH2tvhNrqNsJl+qnauXHomHmEA6F7KXb0beLveqCmMzo6vFgDDypi5aVTEMUe6TwkmmboNGod2k9lEfGa7m+4bUjpllG5ACv0+OKN5LK+L0ORKNuF0ER3Mn/R+qGfJtg+LVZyS+yozp/9sPK/X/7ocVdRbKBJkOqDJ+JSyL+1+7mAyex1cpIvIPkMD7mgSYzsCejimbG4PJjqV5TUxkjBxClRilgAQZ2svCUx/w/HYe4pf0q+3fFbmLWmSzaT4D3uRuaTspiLL7KMAggZIA=\n";

    public String extractUsername(String token) {

        return extractClaim(token, Claims::getSubject);
    }

    public<T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
