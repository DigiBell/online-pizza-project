package com.mycompany.onlinepizzaproject.backend;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Password {
	
	public static String generatePasswordHash(String password) {
		
		byte[] salt = generateSalt();
		byte[] hash = null;
		
		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 1000, 64 * 8);
		
		try {
			
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			hash = factory.generateSecret(spec).getEncoded();
		
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		return toHex(salt) + ":" + toHex(hash);
	}
	
	public static boolean validatePassword(String originalPassword, String storedPassword) {
       
		String[] parts = storedPassword.split(":");
        byte[] salt = fromHex(parts[0]);
        byte[] hash = fromHex(parts[1]);
         
        PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(), salt, 1000, hash.length * 8);
        
        byte[] testHash = null;
		try {
			SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			testHash = skf.generateSecret(spec).getEncoded();
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
         
        int diff = hash.length ^ testHash.length;
        for(int i = 0; i < hash.length && i < testHash.length; i++)
        {
            diff |= hash[i] ^ testHash[i];
        }
        return diff == 0;
    }
	
	private static byte[] generateSalt() {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16];
		random.nextBytes(salt);
		
		return salt;
	}
	
    private static String toHex(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0)
        {
            return String.format("%0"  +paddingLength + "d", 0) + hex;
        }else{
            return hex;
        }
    }
    
    private static byte[] fromHex(String hex) {
        byte[] bytes = new byte[hex.length() / 2];
        for(int i = 0; i<bytes.length ;i++)
        {
            bytes[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }
	
}
