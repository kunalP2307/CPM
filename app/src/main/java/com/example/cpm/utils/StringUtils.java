package com.example.cpm.utils;

public class StringUtils {

    public static String getUserIdOfEmail(String email) {
        return email.replace(".", "");
    }
}
