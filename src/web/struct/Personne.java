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
		return "<TR><TD>"+login+" : </TD><TD>"+nom+"</TD><TD>"+prenom+"</TD></TR>";
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

	@Override
	public String toString() {
		return "Personne [nom=" + nom + ", prenom=" + prenom + ", login=" + login + "]";
	}
}