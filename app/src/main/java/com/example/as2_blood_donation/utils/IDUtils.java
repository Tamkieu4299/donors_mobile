package com.example.as2_blood_donation.utils;

import java.util.UUID;

public class IDUtils {
    public static String generateRandomId() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}
