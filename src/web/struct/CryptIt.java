package web.struct;

import java.math.*;
import java.security.*;
import java.security.spec.*;
import java.sql.SQLException;
import java.security.interfaces.*;

public class CryptIt {
	public final static int KEY_SIZE = 2048; // [512..2048]

	private RSAPublicKey publicKey;
	private RSAPrivateKey privateKey;

	public CryptIt() {
		publicKey = (RSAPublicKey) LectureEcriture.getPublicKey();
		privateKey = (RSAPrivateKey) LectureEcriture.getPrivateKey();
	}

	public void setPublicKey(byte[] publicKeyData) {
		try {
			X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyData);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			publicKey = (RSAPublicKey) keyFactory.generatePublic(publicKeySpec);
			LectureEcriture.setPublicKey(publicKey);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void setPrivateKey(byte[] privateKeyData) {
		try {
			PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyData);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			privateKey = (RSAPrivateKey) keyFactory.generatePrivate(privateKeySpec);
			LectureEcriture.setPrivateKey(privateKey);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void generateKeyPair() {
		try {
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
			keyPairGen.initialize(KEY_SIZE, new SecureRandom());
			KeyPair kp = keyPairGen.generateKeyPair();
			// publicKey = (RSAPublicKey) kp.getPublic();
			// privateKey = (RSAPrivateKey) kp.getPrivate();
			LectureEcriture.write((RSAPublicKey) kp.getPublic(),(RSAPrivateKey) kp.getPrivate());
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public byte[] crypt(byte[] plaintext) {
		return crypt(new BigInteger(bugBigIntegerCorrection(plaintext))).toByteArray();
	}

	public byte[] crypt(String plaintext) {
		return crypt(plaintext.getBytes());
	}

	public byte[] decryptInBytes(byte[] ciphertext) {
		return removeBigIntegerCorrection(decrypt(new BigInteger(ciphertext)).toByteArray());
	}

	public String decryptInString(byte[] ciphertext) {
		return new String(decryptInBytes(ciphertext));
	}

	public static void main(String[] args) {
		Connexion con = Connexion.getInstance();
		try {
			con.truc();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		/*String plaintext = "mot de passe";
		System.out.println("plaintext = " + plaintext);
		CryptIt rsa = new CryptIt();
		
		rsa.setPublicKey(LectureEcriture.getPublicKey().getEncoded());
		rsa.setPrivateKey(LectureEcriture.getPrivateKey().getEncoded());

		rsa.generateKeyPair();

		byte[] ciphertext = rsa.crypt(plaintext);
		System.out.println("ciphertext = " + new BigInteger(ciphertext));


		String plaintext2 = rsa.decryptInString(ciphertext);
		System.out.println("plaintext2 = " + plaintext2);
		if (!plaintext2.equals(plaintext))
			System.out.println("Error: plaintext2 != plaintext");*/
	}

	private BigInteger crypt(BigInteger plaintext) {
		return plaintext.modPow(publicKey.getPublicExponent(), publicKey.getModulus());
	}

	private BigInteger decrypt(BigInteger ciphertext) {
		return ciphertext.modPow(privateKey.getPrivateExponent(), privateKey.getModulus());
	}

	/**
	 * Ajoute un byte de valeur 1 au début du message afin d'éviter que ce
	 * dernier ne corresponde pas à un nombre négatif lorsqu'il sera transformé
	 * en BigInteger.
	 */
	private static byte[] bugBigIntegerCorrection(byte[] input) {
		byte[] result = new byte[input.length + 1];
		result[0] = 1;
		for (int i = 0; i < input.length; i++) {
			result[i + 1] = input[i];
		}
		return result;
	}

	/**
	 * Retire le byte ajouté par la méthode addOneByte.
	 */
	private static byte[] removeBigIntegerCorrection(byte[] input) {
		byte[] result = new byte[input.length - 1];
		for (int i = 0; i < result.length; i++) {
			result[i] = input[i + 1];
		}
		return result;
	}

	public RSAPublicKey getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(RSAPublicKey publicKey) {
		this.publicKey = publicKey;
	}

	public RSAPrivateKey getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(RSAPrivateKey privateKey) {
		this.privateKey = privateKey;
	}
}