package com.desired.offermachi.customer.constant;

public class Util {
    public static boolean isEmptyString(String text) {
        return (text == null || text.trim().equals("null") || text.trim()
                .length() <= 0);
    }
}
