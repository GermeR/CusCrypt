package web.struct;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

public class LectureEcriture {

	private static String pubKey = "data/pubKey.rsa";
	private static String prvKey = "data/prvKey.rsa";
	private static BufferedReader reader;
	private static BufferedWriter writer;

	public static boolean write(PublicKey rsaPub, PrivateKey rsaPriv) {
		try {
			setPublicKey(rsaPub);
		}catch(Exception e) {
			System.out.println(e);
			return false;
		}
		try {
			setPrivateKey(rsaPriv);
		}catch(Exception e) {
			return false;
		}
		return true;
	}
	
	public static void setPublicKey(PublicKey rsaPub) {
		RSAPublicKeySpec rsaSpec = null;
		try {
			KeyFactory getSpec = KeyFactory.getInstance("RSA");
			rsaSpec = getSpec.getKeySpec(rsaPub, RSAPublicKeySpec.class);
			ObjectOutputStream writer = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(pubKey)));
			writer.writeObject(rsaSpec.getModulus());
			writer.writeObject(rsaSpec.getPublicExponent());
			writer.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static PublicKey getPublicKey() {
		BigInteger mod = null;
		BigInteger exp = null;
		PublicKey rsaPub = null;
		try {
			ObjectInputStream inputStream = new ObjectInputStream(
					new BufferedInputStream(new FileInputStream(pubKey)));
			mod = (BigInteger) inputStream.readObject();
			exp = (BigInteger) inputStream.readObject();

			RSAPublicKeySpec specification = new RSAPublicKeySpec(mod, exp);
			KeyFactory usine = KeyFactory.getInstance("RSA");
			rsaPub = usine.generatePublic(specification);
		} catch (Exception e) {
		}
		return rsaPub;
	}

	public static void setPrivateKey(PrivateKey rsaPriv) {
		RSAPrivateKeySpec rsaSpec = null;
		try {
			KeyFactory getSpec = KeyFactory.getInstance("RSA");
			rsaSpec = getSpec.getKeySpec(rsaPriv, RSAPrivateKeySpec.class);

			ObjectOutputStream writer = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(prvKey)));
			writer.writeObject(rsaSpec.getModulus());
			writer.writeObject(rsaSpec.getPrivateExponent());
			writer.close();
		} catch (Exception e) {
		}
	}

	public static PrivateKey getPrivateKey() {
		BigInteger mod;
		BigInteger exp;
		PrivateKey rsaPriv = null;
		try {
			ObjectInputStream inputStream = new ObjectInputStream(
					new BufferedInputStream(new FileInputStream(prvKey)));
			mod = (BigInteger) inputStream.readObject();
			exp = (BigInteger) inputStream.readObject();

			RSAPrivateKeySpec rsaSpec = new RSAPrivateKeySpec(mod, exp);
			KeyFactory getSpec = KeyFactory.getInstance("RSA");
			rsaPriv = getSpec.generatePrivate(rsaSpec);
		} catch (Exception e) {
		}
		return rsaPriv;
	}

}