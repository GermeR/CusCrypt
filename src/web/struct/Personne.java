package web.struct;

import java.util.Date;

public class Personne {
	
	private String login;
	private String prenom;
	private String nom;
	private String mail;
	private Date naiss;
	
	public Personne() {
	}
	
	public Personne(String login, String mail, Date naiss, String nom, String prenom) {
		super();
		this.login = login;
		this.prenom = prenom;
		this.nom = nom;
		this.mail = mail;
		this.naiss = naiss;
	}
	
	public Personne(String nom, String prenom, String login) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.login = login;
	}

	public String toStringTD() {
		return "<TR><TD>"+login+" : </TD><TD>"+nom+"</TD><TD>"+prenom+"</TD></TR>";
	}
	
	@Override
	public String toString() {
		return "Personne [nom=" + nom + ", prenom=" + prenom + ", login=" + login + "]";
	}

	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public Date getNaiss() {
		return naiss;
	}
	public void setNaiss(Date naiss) {
		this.naiss = naiss;
	}
}