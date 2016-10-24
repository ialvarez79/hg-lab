import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.KeyGenerator;

public class AesGen {

	public static void main(String[] args) throws NoSuchAlgorithmException {
		KeyGenerator keyGen = KeyGenerator.getInstance("AES");
		keyGen.init(256); // Use 128 for 16bit key.
		String key = Base64.getEncoder().encodeToString(keyGen.generateKey().getEncoded());
		System.out.println(key); // Prints AES key in Base64 format.
	}

}
