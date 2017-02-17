package web.struct;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

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
	public void createModeles() {
		String query = "Create Table MODELES (nom text primary key,path text,date text,id text);";//TODO
		try {
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(String n) throws SQLException {
		String query = "Delete from MODELES where nom = '" + n + "'"; //TODO
		int i = 3;
		try {
			i = stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (i == 1) {
			File f = new File("data/" + n);
			f.delete();
		}
	}
}