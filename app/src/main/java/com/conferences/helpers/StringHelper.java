package com.conferences.helpers;

public class StringHelper {
    public static boolean isNullOrEmpty(String s) {
        return s == null || s.length() == 0;
    }

    public static boolean isNullOrWhitespace(String s) {
        return isNullOrEmpty(s) ? true : isNullOrEmpty(s.trim());
    }
}
