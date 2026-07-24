package service;

import java.time.LocalDate;

import dao.BuchungDAO;
import dao.GastDAO;
import dao.ZimmerDAO;
import domain.Buchung;
import domain.Gast;
import domain.Zimmer;
import domain.Zimmer.Status;
import domain.Zimmertyp;

public class Tester {

	public static void main(String[] args) {
		
	// Gast Tester	
		
//		Gast testGast = new Gast(7 ,"Evil", "Knievel", "Boston, Massachussets", "evil@knievel.com", "+1 2345 00000");
//		GastDAO testGastDAO = new GastDAO();
		
		// Insert Test
//		try {
//			int idReceived = testDAO.insertGast(testGast);
//			System.out.println("Gast eingetragen mit ID: " + idReceived);
//		} catch (RuntimeException e){
//			System.err.println(e.getMessage());
//		}
		
		// Update Test
//		try {
//			int rowsAffected = testDAO.updateGast(testGast);
//			System.out.println("Gast aktualisiert. Bearbeitete Reihen: " + rowsAffected);
//		} catch (RuntimeException e) {
//			System.err.println(e.getMessage());
//		}
		
		// Delete Test
//		try {
//			int rowsAffected = testDAO.deleteGast(testGast);
//			System.out.println("Gast gelöscht. Bearbeitete Reihen: " + rowsAffected);
//		} catch (RuntimeException e) {
//			System.err.println(e.getMessage());
//		}
		
		// Select Test
//		
//		try {
//			System.out.println(testDAO.findGastByID(6));
//		} catch (RuntimeException e) {
//			System.err.println(e.getMessage());
//		}
		
	// Zimmer Tester
		
		GastDAO gastDAO = new GastDAO();
		ZimmerDAO zimmerDAO = new ZimmerDAO(gastDAO);
		
		Gast testGast = gastDAO.findGastByID(1);
		Zimmer testZimmer = zimmerDAO.findZimmerbyID(6);
		
//		testZimmer.setGast(testGast);
//		testZimmer.setBelegt(true);
//		testZimmer.setPax(2);
		
//		zimmerDAO.updateZimmer(testZimmer);
		
		
//		Zimmertyp testTyp = zimmerDAO.findZimmertypByID(3);
		
		
//		Zimmer testZimmer = new Zimmer("A280", testGast, testTyp, 0, false, Status.REINIGEN);
		
		
		
		
		// Insert
//		try {
//			int idReceived = zimmerDAO.insertZimmer(testZimmer);
//			System.out.println("Zimmer eingetragen mit ID: " + idReceived);
//		} catch (RuntimeException e){
//			System.err.println(e.getMessage());
//		}
		
		// Update 
//		try {
//			int rowsAffected = zimmerDAO.updateZimmer(testZimmer);
//			System.out.println("Zimmer aktualisiert. Anzahl bearbeiter Reihen: " + rowsAffected);
//		} catch (RuntimeException e){
//			System.err.println(e.getMessage());
//		}
		
		// Delete
		
//		try {
//			int rowsAffected = zimmerDAO.deleteZimmer(testZimmer);
//			System.out.println("Zimmer gelöscht. Anzahl bearbeiter Reihen: " + rowsAffected);
//		} catch (RuntimeException e){
//			System.err.println(e.getMessage());
//		}

		
		// Buchung Tester
		
		BuchungDAO buchungDAO = new BuchungDAO(gastDAO, zimmerDAO);
		Buchung testBuchung = new Buchung(1, testGast, testZimmer, LocalDate.of(2026, 7, 17), LocalDate.of(2026, 7, 25));
		
		// Insert Test
		
//		try {
//			int idReceived = buchungDAO.insertBuchung(testBuchung);
//			testBuchung.setId(idReceived);
//			System.out.println("Buchung eingetragen mit ID: " + idReceived);
//		} catch (RuntimeException e){
//			System.err.println(e.getMessage());
//		}
		
		// Update Test
		
		try {
			int rowsAffected = buchungDAO.updateBuchung(testBuchung);
			
			System.out.println("Buchung aktualisiert. Anzahl bearbeiter Reihen: " + rowsAffected);
		} catch (RuntimeException e){
			System.err.println(e.getMessage());
		}
	

	}

}
