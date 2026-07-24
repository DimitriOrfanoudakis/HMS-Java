package domain;

public class Gast {
	private int id;
	private String vorname;
	private String nachname;
	private String wohnort;
	private String email;
	private String telNummer;
	
	
	// Getters & Setters
	
	// id
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	// vorname
	public String getVorname() {
		return this.vorname;
	}
	
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}
	
	// nachname
	public String getNachname() {
		return this.nachname;
	}
	
	public void setNachname(String nachname) {
		this.nachname = nachname;
	}
	
	// wohnort
	
	public String getWohnort() {
		return this.wohnort;
	}
	
	public void setWohnort(String wohnort) {
		this.wohnort = wohnort;
	}
	
	// email
	
	public String getEmail() {
		return this.email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	// telNummer
	
	public String getTelNummer() {
		return this.telNummer;
	}
	
	public void setTelNummer(String telNummer) {
		this.telNummer = telNummer;
	}
	
	
	// Konstruktor ohne ID
	
	public Gast(String vorname, String nachname, String wohnort, String email, String telNummer) {
		this.id = 0;
		this.vorname = vorname;
		this.nachname = nachname;
		this.wohnort = wohnort;
		this.email = email;
		this.telNummer = telNummer;
	}
	
	// Konstruktor mit ID
	
		public Gast(int id, String vorname, String nachname, String wohnort, String email, String telNummer) {
			this.id = id;
			this.vorname = vorname;
			this.nachname = nachname;
			this.wohnort = wohnort;
			this.email = email;
			this.telNummer = telNummer;
		}
	
	@Override
	public String toString() {
		return "ID: " + this.id + "\n" +
			   "Vorname: " + this.vorname + "\n" +
			   "Nachname: " + this.nachname + "\n" +
			   "Wohnort: " + this.wohnort + "\n" +
			   "Email: " + this.email + "\n" +
			   "Telefonnummer: " + this.telNummer + "\n";
		}
}
