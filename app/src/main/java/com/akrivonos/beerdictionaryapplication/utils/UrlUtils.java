package com.akrivonos.beerdictionaryapplication.utils;

import android.text.TextUtils;

public class UrlUtils {
    public static String makeUrlIconFix(String urlIcon) {
        if (TextUtils.isEmpty(urlIcon)) return "";
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
