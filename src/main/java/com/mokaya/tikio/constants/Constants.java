package com.mokaya.tikio.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Constants {
    public static final long EXPIRATION_TIME = 86400000; // 1 day in milliseconds
    public final String secret;

    public Constants(@Value("${jwt.secret}") String secret) {
        this.secret = secret;
    }
}
