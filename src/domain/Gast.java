package domain;

public class Gast {
	private int id;
	private String vorname;
	private String nachname;
	private Zimmer zimmer;
	
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
	
	// zimmer
	public Zimmer getZimmer() {
		return this.zimmer;
	}
	
	public void SetZimmer(Zimmer zimmer) {
		this.zimmer = zimmer;
	}
	
	// Konstruktor mit bekanntem Zimmer
	
	public Gast(String vorname, String nachname, Zimmer zimmer) {
		this.id = 0;
		this.vorname = vorname;
		this.nachname = nachname;
		this.zimmer = zimmer;
	}
	
	// Konstruktor ohne Zimmer
	
	public Gast(String vorname, String nachname) {
		this.id = 0;
		this.vorname = vorname;
		this.nachname = nachname;
		this.zimmer = null;
	}
	
	@Override
	public String toString() {
		return "ID: " + this.id + "\n" +
			   "Vorname: " + this.vorname + "\n" +
			   "Nachname: " + this.nachname + "\n" +
			   "Zimmer: " + this.zimmer;
		}
}
