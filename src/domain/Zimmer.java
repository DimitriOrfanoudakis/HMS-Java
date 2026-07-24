package domain;

public class Zimmer {
	private int id;
	private String nummer;
	private Gast gast;
	private Zimmertyp typ;
	private int pax;
	private boolean belegt;
	public enum Status{
		FERTIG,
		REINIGEN
	};
	private Status status;
	
	// Getters & Setters
	
	// id
	public int getId() {
		return this.id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	// nummer
	public String getNummer() {
		return this.nummer;
	}
	public void setNummer(String nummer) {
		this.nummer = nummer;
	}
	
	// gast
	public Gast getGast() {
		return this.gast;
	}
	public void setGast(Gast gast) {
		this.gast = gast;
	}
	
	// typ
	public Zimmertyp getTyp() {
		return this.typ;
	}
	public void setTyp(Zimmertyp typ) {
		this.typ = typ;
	}
	
	// pax
	public int getPax() {
		return this.pax;
	}
	public void setPax(int pax) {
		this.pax = pax;
	}
	
	// belegt
	public boolean istBelegt() {
		return this.belegt;
	}
	public void setBelegt(boolean belegt) {
		this.belegt = belegt;
	}
	
	// status
	public Status getStatus() {
		return this.status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
	
	// Konstruktor mit ID
	public Zimmer(int id, String nummer, Gast gast, Zimmertyp typ, int pax, boolean belegt, Status status) {
		this.id = id;
		this.nummer = nummer;
		this.gast = gast;
		this.typ = typ;
		this.pax = pax;
		this.belegt = belegt;
		setStatus(status);
	}
		
	// Konstruktor wenn Gast bekannt ist
	public Zimmer(String nummer, Gast gast, Zimmertyp typ, int pax, boolean belegt, Status status) {
		this.id = 0;
		this.nummer = nummer;
		this.gast = gast;
		this.typ = typ;
		this.pax = pax;
		this.belegt = belegt;
		setStatus(status);
		
	}
	
	// Konstruktor ohne Gast
		public Zimmer(String nummer, Zimmertyp typ, int pax, boolean belegt) {
			this.id = 0;
			this.nummer = nummer;
			this.gast = null;
			this.typ = typ;
			this.pax = pax;
			this.belegt = belegt;			
	}
	@Override
	public String toString() {
		return "ID: " + this.id + "\n" +
			   "Zimmernummer: " + this.nummer + "\n" +
			   "Gast: " + this.gast + "\n" +
			   "Zimmertyp: " + this.typ.getBezeichnung() + "\n" +
			   "PAX: " + this.pax + "\n" +
			   "Belegt: " + this.belegt + "\n" +
			   "Status:  " + this.status + "\n";
		}
	
}
