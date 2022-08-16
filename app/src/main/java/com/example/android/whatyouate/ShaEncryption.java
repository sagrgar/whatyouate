package com.example.android.whatyouate;

import java.security.MessageDigest;

public class ShaEncryption {

    public static byte[] encryptSHA(byte[] data, String shaN) throws Exception {

        MessageDigest sha = MessageDigest.getInstance(shaN);
        sha.update(data);
        return sha.digest();

    }
}
