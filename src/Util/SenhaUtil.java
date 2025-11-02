package Util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class SenhaUtil {
	 private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	   
	    public static String generateHash(String senha) {
	        return encoder.encode(senha);
	    }

	    public static boolean verifyPassword(String senhaDigitada, String hashSalvo) {
	        return encoder.matches(senhaDigitada, hashSalvo);
	    }

}
 