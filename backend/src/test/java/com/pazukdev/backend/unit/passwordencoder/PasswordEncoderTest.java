package com.pazukdev.backend.unit.passwordencoder;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Siarhei Sviarkaltsau
 */
public class PasswordEncoderTest {

    @Test
    public void test() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        final String password = "123";
        final String encodedPassword = "$2a$10$/b4KPbMoIOKinYMvbSnGKugQAc7n8VBIzwcKLK4bY6yVPfwlHstVK";

        Assert.assertTrue(passwordEncoder.matches(password, encodedPassword));
    }

}
