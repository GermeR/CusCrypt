package web.struct;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;

/**
 * Gere les connexions et interactions avec la base de donnée
 * 
 * @author Robin Germe
 * 
 */
public class MyBDD {
	private String DBPath = "";
	private Connection con = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	private static MyBDD conn = new MyBDD("data/Database.db");

	public static final MyBDD getInstance() {
		return conn;
	}

	private MyBDD(String dBPath) {
		DBPath = dBPath;
	}

	public ArrayList<Personne> lister() {
		this.connect();
		ArrayList<Personne> pers = new ArrayList<>();
		String sql = "select * from users ;";
		try {
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				pers.add(new Personne(rs.getString(2), rs.getString(3), rs.getString(1)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.close();
		return pers;
	}

	public boolean reEnCrypt(RSAPrivateKey pkKeyOld, RSAPrivateKey pkKeyNew, RSAPublicKey pbKeyNew) {
		connect();
		ArrayList<Personne> pers = new ArrayList<Personne>();
		CryptIt rsa = new CryptIt();
		String sql = "select * from users ;";
		String billCipher = "";
		try {
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				billCipher = rs.getString(4);
				byte[] bill = Base64.getDecoder().decode(billCipher);
				String cipher = rsa.decryptInString(bill);
				pers.add(new Personne(rs.getString(1), rs.getString(2), rs.getString(3), cipher));
			}
			for (Personne p : pers) {
				alterUser(p,pbKeyNew);
			}
		} catch (SQLException e) {
			return false;
		}
		close();
		return false;
	}

	private boolean alterUser(Personne pers, RSAPublicKey pbKey) throws SQLException {
		String login = pers.getLogin();
		String pass = pers.getPass();
		this.connect();
		CryptIt rsa = new CryptIt();
		String billCipher = new String(Base64.getEncoder().encode(rsa.crypt(pass)));
		String sql = "";
		if (!login.equals("") && !pass.equals("")) {
			sql = "update users set pass = '" + new String(Base64.getEncoder().encode(rsa.crypt(pass))) + "' where login ='" + login + "';";
			stmt.execute(sql);
			return true;
		}
		return false;
	}

	public boolean addUser(String login, String nom, String prenom, String pass, String repass) throws SQLException {
		this.connect();
		CryptIt rsa = new CryptIt();
		String billCipher = new String(Base64.getEncoder().encode(rsa.crypt(pass)));

		String sql = "SELECT * FROM users WHERE login='" + login + "';";
		rs = stmt.executeQuery(sql);
		if (!login.equals("") && !pass.equals("") && pass.equals(repass)) {
			if (!rs.next()) {
				sql = "insert into users(login,nom,prenom,pass) values('" + login + "','" + nom + "','" + prenom + "','"
						+ billCipher + "');";
				stmt.execute(sql);
				this.close();
				return true;
			}
		}
		this.close();
		return false;
	}

	public Personne get(String login) {
		connect();
		String sql = "select * from users where login='" + login + "';";
		try {
			rs = stmt.executeQuery(sql);
			if (rs.next())
				return new Personne(rs.getString(2), rs.getString(3), rs.getString(1));
		} catch (SQLException e) {
			return null;
		}
		close();
		return null;
	}

	public boolean authorize(String login, String pass) {
		connect();
		if (pass == null || login == null)
			return false;
		CryptIt rsa = new CryptIt();
		String sql = "select pass from users where login ='" + login + "';";
		String billCipher = "";
		try {
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				billCipher = rs.getString(1);
			} else
				return false;
		} catch (SQLException e) {
			return false;
		}
		byte[] bill = Base64.getDecoder().decode(billCipher);
		String cipher = rsa.decryptInString(bill);
		close();
		return pass.equals(cipher);
	}

	/**
	 * ouverture de connexion sql
	 * 
	 * @return boolean
	 */
	public boolean connect() {
		try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection("jdbc:sqlite:" + DBPath);
			stmt = con.createStatement();
			// System.out.println("Connexion a " + DBPath + " avec succès");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("Erreur de connection");
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Erreur de connection");
			return false;
		}
		// System.out.println("STATEMENT : " + stmt.toString());
		// System.out.println("CONNEXION : " + con.toString());
		return true;
	}

	/**
	 * fermeture de connexion sql
	 * 
	 * @return boolean
	 */
	public boolean close() {
		try {
			stmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * creation de la table
	 */
	public void create() {
		this.connect();
		String query = "Create Table users(login text primary key, nom text , prenom text, pass text);";
		try {
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.close();
	}

	public void delete(String n) throws SQLException {
		connect();
		String query = "Delete from users where login = '" + n + "';";
		try {
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close();
	}
}
