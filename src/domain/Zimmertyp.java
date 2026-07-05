package domain;

public class Zimmertyp {
	private int id;
	private int betten;
	private Double preisProNacht;
	private String bezeichnung;
	
	// Getters & Setters
	
	// id
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	// betten
	public int getBetten() {
		return this.betten;
	}
	
	public void setBetten(int betten) {
		this.betten = betten;
	}
	
	// preisProNacht
	public Double getPreisProNacht() {
		return this.preisProNacht;
	}
	
	public void setPreisProNacht(Double preisProNacht) {
		this.preisProNacht = preisProNacht;
	}
	
	// bezeichnung
	public String getBezeichnung() {
		return this.bezeichnung;
	}
	
	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}
	
	
	// Konstruktor
	
	public Zimmertyp(int betten, Double preisProNacht, String bezeichnung) {
		this.id = 0;
		this.betten = betten;
		this.preisProNacht = preisProNacht;
		this.bezeichnung = bezeichnung;
	}
}
