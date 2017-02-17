package web.struct;

public class Personne {

	private String nom = "";
	private String prenom = "";
	private String login = "";
	
	public Personne() {
	}

	public Personne(String nom, String prenom, String login) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.login = login;
	}

	public String toStringTD() {
		return "";//TODO
	}
	
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
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
}