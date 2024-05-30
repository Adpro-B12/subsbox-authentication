package id.ac.ui.cs.advprog.authenticationuserprofile.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;

import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;

    @Mock
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtService = new JwtService();
        jwtService.SECRET_KEY = "secretsecretsecretsecretsecretsecretsecretsecret";
    }

    @Test
    void testExtractUsername() {
        String token = generateTestToken("username");
        String username = jwtService.extractUsername(token);
        assertEquals("username", username);
    }

    @Test
    void testExtractId() {
        String token = generateTestTokenWithId("testId");
        String id = jwtService.extractId(token);
        assertEquals("testId", id);
    }

    @Test
    void testGenerateToken() {
        UserDetails userDetails = new User("username", "password", new ArrayList<>());
        String token = jwtService.generateToken(userDetails);
        assertNotNull(token);
    }

    @Test
    void testGenerateTokenWithExtraClaims() {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("key", "value");
        UserDetails userDetails = new User("username", "password", new ArrayList<>());
        String token = jwtService.generateToken(extraClaims, userDetails);
        assertNotNull(token);
    }

    @Test
    void testIsTokenValid() {
        UserDetails userDetails = new User("username", "password", new ArrayList<>());
        String token = jwtService.generateToken(userDetails);
        assertTrue(jwtService.isTokenValid(token, userDetails));
    }

    @Test
    void testExtractExpiration() {
        String token = generateTestToken("username");
        Date expiration = jwtService.extractExpiration(token);
        assertNotNull(expiration);
    }

    @Test
    void testExtractClaim() {
        String token = generateTestToken("username");
        Function<Claims, String> claimResolver = Claims::getSubject;
        String claim = jwtService.extractClaim(token, claimResolver);
        assertEquals("username", claim);
    }

    @Test
    void testExtractAllClaims() {
        String token = generateTestToken("username");
        Claims claims = jwtService.extractAllClaims(token);
        assertNotNull(claims);
    }

    private String generateTestToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(jwtService.getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private String generateTestTokenWithId(String id) {
        return Jwts.builder()
                .setId(id)
                .setSubject("username")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(jwtService.getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private String generateExpiredToken() {
        return Jwts.builder()
                .setSubject("username")
                .setIssuedAt(new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24))
                .setExpiration(new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 12))
                .signWith(jwtService.getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}
