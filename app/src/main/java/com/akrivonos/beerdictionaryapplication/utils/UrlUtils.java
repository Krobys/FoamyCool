package com.akrivonos.beerdictionaryapplication.utils;

public class UrlUtils {
    public static String makeUrlIconFix(String urlIcon) {
        StringBuilder stringBuilder = new StringBuilder(urlIcon);
        int pointer = 0;
        while (pointer < stringBuilder.length()) {
            if (stringBuilder.charAt(pointer) == '\\') {
                stringBuilder.deleteCharAt(pointer);
            }
            pointer++;
        }
        return stringBuilder.toString();
    }
}
