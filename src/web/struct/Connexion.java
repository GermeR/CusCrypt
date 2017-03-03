package web.struct;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Gere les connexions et interactions avec la base de donnée
 * 
 * @author Robin Germe
 * 
 */
public class Connexion {
	private String DBPath = "";
	private Connection con = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	private static Connexion conn = new Connexion("data/Database.db");

	public static final Connexion getInstance() {
		return conn;
	}

	private Connexion(String dBPath) {
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
	
	public void truc() throws SQLException {
		this.connect();
		CryptIt rsa = new CryptIt();
		rsa.generateKeyPair();
		delete("login");
		addUser("login", null, null, "azerty", "aderty");
		this.connect();
		String sql = "select * from users ;";
		try {
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				System.out.println(rs.getString(4));
				System.out.println("decrypt :"+rsa.decryptInString(rs.getString(4).getBytes()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.close();
	}

	public boolean addUser(String login, String nom, String prenom, String pass, String repass) throws SQLException {
		this.connect();
		CryptIt rsa = new CryptIt();
		
		String sql = "SELECT * FROM users WHERE login='" + login + "';";
		rs = stmt.executeQuery(sql);
		if (!login.equals("") && !pass.equals("") && pass.equals(repass)) {
			if (!rs.next()) {
				stmt.execute("insert into users(login,nom,prenom,pass) values('" + login + "','" + nom + "','" + prenom
						+ "','" + rsa.crypt(pass.getBytes()) + "');");
				System.out.println("bonjour le monde"+rsa.crypt(pass.getBytes()));
				this.close();
				return true;
			}
		}
		this.close();
		return false;
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
			System.out.println("Connexion a " + DBPath + " avec succès");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("Erreur de connection");
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Erreur de connection");
			return false;
		}
		System.out.println("STATEMENT : " + stmt.toString());
		System.out.println("CONNEXION : " + con.toString());
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
		String query = "Delete from users where login = '"+n+"';";
				try {
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close();
	}
}