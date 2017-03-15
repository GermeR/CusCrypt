package web.struct;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.sql.SQLException;
import java.util.Base64;

public class CryptIt {
	public final static int KEY_SIZE = 2048; // [512..2048]

	private RSAPublicKey publicKey;
	private RSAPrivateKey privateKey;

	public CryptIt() {
		publicKey = (RSAPublicKey) LectureEcriture.getPublicKey();
		privateKey = (RSAPrivateKey) LectureEcriture.getPrivateKey();
	}

	private void setPublicKey(byte[] publicKeyData) {
		try {
			X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyData);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			publicKey = (RSAPublicKey) keyFactory.generatePublic(publicKeySpec);
			LectureEcriture.setPublicKey(publicKey);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void setPrivateKey(byte[] privateKeyData) {
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
			publicKey = (RSAPublicKey) kp.getPublic();
			privateKey = (RSAPrivateKey) kp.getPrivate();
			LectureEcriture.write((RSAPublicKey) kp.getPublic(), (RSAPrivateKey) kp.getPrivate());
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
		MyBDD con = MyBDD.getInstance();
		CryptIt rsa = new CryptIt();
		con.reEnCrypt(rsa.getPrivateKey(),rsa.getPrivateKey(),rsa.getPublicKey());
	
		
		/*
		try {
			con.addUser("login", null, null, "azerty", "azerty");
			con.authorize("login", "azerty");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		byte[] cipher = rsa.crypt("azerty");
		//System.out.println("cipher :" + new String(cipher));
		String billCipher = new String(Base64.getEncoder().encode(cipher));
		//System.out.println(billCipher);
		byte[] bill = Base64.getDecoder().decode(billCipher);
		//System.out.println("bill :" + new String(bill));
		System.out.println("ld :" + rsa.decryptInString(bill));

		System.out.println("egal ? "+"azerty".equals(rsa.decryptInString(bill)));*/
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