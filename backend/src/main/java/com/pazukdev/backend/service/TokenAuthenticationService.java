package com.pazukdev.backend.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.pazukdev.backend.entity.VerificationToken;
import com.pazukdev.backend.repository.UserRepository;
import com.pazukdev.backend.repository.VerificationTokenRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.security.SignatureException;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author Siarhei Sviarkaltsau
 */
@Service
@RequiredArgsConstructor
public class TokenAuthenticationService {

    private static final long EXPIRATION_TIME = 2_592_000_000L; // Month
    private static final String SECRET = "D90#11%fhBpP";
    private static final String TOKEN_PREFIX = "Bearer";
    public static final String HEADER_STRING = "Authorization";

    private final UserRepository userRepository;
    private final VerificationTokenRepository tokenRepository;

    private final Cache<String, VerificationToken> cache = CacheBuilder
            .newBuilder()
            .expireAfterWrite(120, TimeUnit.MINUTES)
            .build();

    public void addAuthentication(final HttpServletResponse response, final String alias, final String roles) {
        final Long userID = userRepository.findByName(alias).getId();
        final String JWT = encode(alias, userID.toString(), roles);

        updateToken(JWT);
        response.addHeader(HEADER_STRING, JWT);
    }

    public Authentication getAuthentication(final HttpServletRequest request) throws SignatureException {
        return new UsernamePasswordAuthenticationToken(request.getUserPrincipal(), null, null);
    }

    @Transactional
    public void logout(final String token) {
        tokenRepository.deleteByToken(token);
        cache.invalidate(token);
    }

    @Transactional
    public void updateToken(final String JWT) {
//        final VerificationToken verificationToken = tokenRepository.findByToken(JWT).orElseGet(() -> {
//            final VerificationToken token = new VerificationToken();
//            token.setToken(JWT);
//            return token;
//        });

        VerificationToken verificationToken = tokenRepository.findByToken(JWT);
        if (verificationToken == null) {
            verificationToken = new VerificationToken();
            verificationToken.setToken(JWT);
        }

        verificationToken.setExpiryDate(LocalDate.now().plusMonths(1));
        verificationToken.setName("name");
        tokenRepository.save(verificationToken);
        cache.put(JWT, verificationToken);
    }

    private static String encode(final String... fields) {
        return TOKEN_PREFIX + " " + Jwts.builder()
                .setSubject(String.join(":", fields))
                .setExpiration(new Date(System.currentTimeMillis() + TokenAuthenticationService.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET).compact();
    }

    private static String decode(final String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                .getBody()
                .getSubject();
    }

//    h

    private Optional<VerificationToken> getFromCache(final String token) {
        return Optional.ofNullable(cache.getIfPresent(token));
    }

}
