package com.afoxxvi.asteorbar.internal;

import javax.crypto.Cipher;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Scanner;

public class SecurityInfo {
    private static String privateKeyStr;
    private static String publicKeyStr;

    private static PublicKey publicKey;
    private static PrivateKey privateKey;

    private static void generateKeyPairs() throws NoSuchAlgorithmException {
        var generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        var keyPair = generator.generateKeyPair();
        var publicKey = keyPair.getPublic();
        var privateKey = keyPair.getPrivate();
        var publicKetBase64 = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        var privateKetBase64 = Base64.getEncoder().encodeToString(privateKey.getEncoded());
        publicKeyStr = publicKetBase64;
        privateKeyStr = privateKetBase64;
        File file = new File("asteorbar-key.txt");
        try {
            var writer = new java.io.FileWriter(file);
            writer.write(privateKetBase64 + "\n" + publicKetBase64);
            writer.close();
        } catch (Exception ignored) {
        }
    }

    private static void loadKeyPairs() {
        File file = new File("asteorbar-key.txt");
        if (file.exists()) {
            try {
                var scanner = new Scanner(file);
                if (scanner.hasNextLine()) {
                    privateKeyStr = scanner.nextLine();
                }
                if (scanner.hasNextLine()) {
                    publicKeyStr = scanner.nextLine();
                }
                var keyFactory = KeyFactory.getInstance("RSA");
                publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyStr)));
                privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyStr)));
            } catch (Exception ignored) {
            }
        }
    }

    public static byte[] getPublicKey(boolean regen) {
        if (!regen) {
            if (publicKeyStr == null || publicKeyStr.isEmpty()) {
                loadKeyPairs();
            }
            if (publicKeyStr != null && !publicKeyStr.isEmpty()) {
                return publicKeyStr.getBytes(StandardCharsets.UTF_8);
            }
        }
        try {
            generateKeyPairs();
            return publicKeyStr.getBytes(StandardCharsets.UTF_8);
        } catch (Exception ignored) {

        }
        return new byte[0];
    }

    public static byte[] handleChallenge(byte[] data) {
        try {
            if (privateKey == null) {
                loadKeyPairs();
            }
            var cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            var challenge = cipher.doFinal(data);
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            return cipher.doFinal(challenge);
        } catch (Exception ignored) {
        }

        return new byte[0];
    }
}
