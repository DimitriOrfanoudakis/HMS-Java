package domain;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Buchung {
	private int id;
	private Gast gast; // Darf bei Erstellung null sein
	private Zimmer zimmer; // Darf bei Erstellung null sein
	private String zimmerTyp;
	private Double summe;
	private Map<String, Double> extras;
	private LocalDate checkInDatum;
	private LocalDate checkOutDatum;
	public enum Status {
		AUSSTEHEND,
		BESTÄTIGT,
		EINGECHECKT,
		AUSGECHECKT,
		STORNIERT,
		NO_SHOW
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
	
	// gast
	public Gast getGast() {
		return this.gast;
	}
	
	public void setGast(Gast gast) {
		this.gast = gast;
	}
	
	// zimmer
	public Zimmer getZimmer() {
		return this.zimmer;
	}
	
	public void setZimmer(Zimmer zimmer) {
		this.zimmer = zimmer;
	}
	
	// summe
	public Double getSumme() {
		return this.summe;
	}
	
	public void setSumme(Double summe) {
		this.summe = summe;
	}
	
	// checkInDatum	
	public LocalDate getCheckInDatum() {
		return this.checkInDatum;
	}
	
	public void setCheckInDatum(LocalDate checkInDatum) {
		this.checkInDatum = checkInDatum;
	}
	
	// checkOutDatum
	public LocalDate getCheckOutDatum() {
		return this.checkOutDatum;
	}
	
	public void setCheckOutDatum(LocalDate checkOutDatum) {
		this.checkOutDatum = checkOutDatum;
	}
	
	// status
	public Status getStatus() {
		return this.status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
	
	// zimmerTyp
	public String getZimmerTyp() {
        return this.zimmerTyp;
    }
	
		
	// Extras bekommen statt Getter/Setter spezielle Methoden, um Dienstleistungen leichter aufbuchen und entfernen zu können
	// und Kapselung zu gewährleisten.
	
	// Es wird nur eine Kopie der Map zurückgegeben
	public Map<String, Double> getExtras() {
		return new HashMap<>(this.extras);
	}
	
	// Einzeln aufbuchen
	public void addExtra(String beschreibung, Double betrag) {
		this.extras.put(beschreibung, betrag);
	}
	
	//  Einzeln entfernen
	public void removeExtra(String beschreibung) {
		this.extras.remove(beschreibung);
	}
	
	// Alle entfernen
	public void clearExtras() {
		this.extras.clear();
	}
	
	// Summme aller Extras zurückgeben
	public Double getExtrasTotal() {
		return this.extras.values().stream().mapToDouble(Double::doubleValue).sum();
	}
	
	// Konstruktor wenn Gast und Zimmer noch nicht bekannt
	
	public Buchung(String zimmerTyp, LocalDate checkInDatum, LocalDate checkOutDatum) {
		this.id = 0;
		this.gast = null; 
		this.zimmer = null; 
		this.zimmerTyp = zimmerTyp;
		this.summe = 0.0;
		this.extras = new HashMap<String, Double>();
		this.checkInDatum = checkInDatum;
		this.checkOutDatum = checkOutDatum;
		this.status = Status.AUSSTEHEND;		
	}
	
	// Konstruktor wenn Gast und Zimmer bekannt sind
	
	public Buchung(Gast gast, Zimmer zimmer, LocalDate checkInDatum, LocalDate checkOutDatum) {
		this.id = 0;
		this.gast = gast; 
		this.zimmer = zimmer; 
		this.zimmerTyp = this.zimmer.getTyp().getBezeichnung();
		this.summe = 0.0;
		this.extras = new HashMap<String, Double>();
		this.checkInDatum = checkInDatum;
		this.checkOutDatum = checkOutDatum;
		this.status = Status.AUSSTEHEND;		
	}
	
	// Konstruktor um ALLE Werte zu setzen
	
		public Buchung(int id, Gast gast, Zimmer zimmer, String zimmerTyp, Double summe, LocalDate checkInDatum, LocalDate checkOutDatum, HashMap<String, Double> extras, Status status) {
			this.id = id;
			this.gast = gast; 
			this.zimmer = zimmer; 
			this.zimmerTyp = zimmerTyp;
			this.summe = summe;
			this.extras = extras;
			this.checkInDatum = checkInDatum;
			this.checkOutDatum = checkOutDatum;
			this.status = status;		
		}
	
	@Override
	public String toString() {
		return "ID: " + this.id + "\n" +
			   "Zimmernummer: " + this.zimmer + "\n" +
			   "Gast: " + this.gast + "\n" +
			   "Zimmertyp: " + this.zimmerTyp + "\n" +
			   "Summe(€): " + this.summe + "\n" +
			   "Datum Checkin: " + this.checkInDatum + "\n" +
			   "Datum Checkout:  " + this.checkOutDatum + "\n" +
			   "Status:  " + this.status;
		}
}
