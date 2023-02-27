package com.restapi.project.configuration;

public class SecurityConstants {
    public static final String SIGNING_KEY = "SecretKey";
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "auth";
}
