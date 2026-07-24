package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.HashMap;

import domain.Buchung;
import domain.Buchung.Status;
import domain.Gast;
import domain.Zimmer;


public class BuchungDAO {
	
	private GastDAO gastDAO;
	private ZimmerDAO zimmerDAO;
	
	// Konstruktor mit GastDAO und ZimmerDAO
	
	public BuchungDAO(GastDAO gastDAO, ZimmerDAO zimmerDAO) {
		this.gastDAO = gastDAO;
		this.zimmerDAO = zimmerDAO;
	}
	
	public int insertBuchung(Buchung buchung) {
		int id = -1;
		String insertQuery = "INSERT INTO buchungen (summe, check_in_datum, check_out_datum, status_id, gast_id, zimmer_id, zimmertyp_id) "
				   + "VALUES (?, ?, ?, ?, ?, ?, ?);";
		
		try (Connection conn = PostgresConnector.getConn();
				var prepStatement = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
			
			prepStatement.setDouble(1, buchung.getSumme());
			prepStatement.setObject(2, buchung.getCheckInDatum()); // setObject statt setDate, sonst Datentypen inkompatibel
			prepStatement.setObject(3, buchung.getCheckOutDatum());
			prepStatement.setInt(4, findStatusIDbyDescription(buchung.getStatus().toString()));
			prepStatement.setInt(5, buchung.getGast().getId());
			prepStatement.setInt(6, buchung.getZimmer().getId());
			prepStatement.setInt(7, zimmerDAO.findZimmertypIDByDescription(buchung.getZimmerTyp()));
			
			prepStatement.execute();
			
			try (ResultSet generatedKeys = prepStatement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					id = generatedKeys.getInt(1);				
				}
			}
			
			return id;
		
		} catch (SQLException e) {
            throw new RuntimeException("Buchung konnte nicht eingetragen werden: " + e.getMessage());
        }
	}
	

	
	public int updateBuchung(Buchung buchung) {
		int rowsAffected = -1;
		String updateQuery = "UPDATE buchungen SET summe = ?, check_in_datum = ?, check_out_datum = ?, status_id = ?, gast_id = ?, zimmer_id = ?, zimmertyp_id = ? WHERE id = ?";
		
		try (Connection conn = PostgresConnector.getConn();
				var prepStatement = conn.prepareStatement(updateQuery)) {
			
			prepStatement.setDouble(1, buchung.getSumme());
			prepStatement.setObject(2, buchung.getCheckInDatum());
			prepStatement.setObject(3, buchung.getCheckOutDatum());
			prepStatement.setInt(4, findStatusIDbyDescription(buchung.getStatus().toString()));
			prepStatement.setInt(5, buchung.getGast().getId());
			prepStatement.setInt(6, buchung.getZimmer().getId());
			prepStatement.setInt(7, zimmerDAO.findZimmertypIDByDescription(buchung.getZimmerTyp()));
			prepStatement.setInt(8, buchung.getId());
			
			rowsAffected = prepStatement.executeUpdate();
			return rowsAffected;
			
		}  catch (SQLException e) {
			throw new RuntimeException("Zimmer konnte nicht aktualisiert werden: " + e.getMessage());
		}
	}
	
	
	public int deleteBuchung(Buchung buchung) {
		int rowsAffected = -1;
		String deleteQuery = "DELETE FROM buchungen WHERE id = ?";
		
		try (Connection conn = PostgresConnector.getConn();
				var prepStatement = conn.prepareStatement(deleteQuery)) {
					
			prepStatement.setInt(1, buchung.getId());
			
			rowsAffected = prepStatement.executeUpdate();
			return rowsAffected;
			
		} catch (SQLException e) {
			throw new RuntimeException("Zimmer konnte nicht gelöscht werden: " + e.getMessage());
		}
	}
	
	
	public Buchung findBuchungByID(int id) {
		String selectQuery = "SELECT b.id, b.summe, b.check_in_datum, b.check_out_datum, bs.bezeichnung AS status, b.gast_id, b.zimmer_id, b.zimmertyp_id "
				+ "FROM buchungen b "
				+ "JOIN buchung_status bs ON b.status_id = bs.id"
				+ "WHERE b.id = ?";
		
		try (Connection conn = PostgresConnector.getConn();
				var prepStatement = conn.prepareStatement(selectQuery)) {
			
			prepStatement.setInt(1, id);
			var resultSet = prepStatement.executeQuery();
			
			if (resultSet.next()) {
				Double summe = resultSet.getDouble("summe");
				LocalDate checkInDatum = resultSet.getObject("check_in_datum", LocalDate.class);
				LocalDate checkOutDatum = resultSet.getObject("check_out_datum", LocalDate.class);
				Status status = Buchung.Status.valueOf(resultSet.getString("status"));
				Gast gast = gastDAO.findGastByID(resultSet.getInt("gast_id"));
				Zimmer zimmer = zimmerDAO.findZimmerbyID(resultSet.getInt("zimmer_id"));
				String zimmerTyp = (zimmerDAO.findZimmertypByID(resultSet.getInt("zimmertyp_id"))).getBezeichnung();
				var extras = findExtrasForBuchungID(id);
				
				// TODO: Buchung konstruieren und zurueckgeben
				Buchung buchung = new Buchung(id, gast, zimmer, zimmerTyp, summe, checkInDatum, checkOutDatum, extras, status);
				return buchung;
				
			} else {
				return null;
			}
			
		
		} catch (SQLException e) {
			throw new RuntimeException("Zimmer mit ID " + id + " konnte nicht gefunden werden: " + e.getMessage());
		}

	}
	
	
	// Helfermethoden
	
	public Integer findStatusIDbyDescription(String bezeichnung) {
		String selectQuery = "SELECT id FROM buchung_status WHERE bezeichnung = ?";
		
		try (Connection conn = PostgresConnector.getConn();
				var prepStatement = conn.prepareStatement(selectQuery)) {
			
			prepStatement.setString(1, bezeichnung);
			var resultSet = prepStatement.executeQuery();
			
			if (resultSet.next()) {
				int id = resultSet.getInt("id");				
				return id;
			} else {
				return null;
			}
			
		} catch (SQLException e) {
			throw new RuntimeException("ID für Buchungsstatus " + bezeichnung + " konnte nicht gefunden werden: " + e.getMessage());
		}
	}
	
	
	public HashMap<String, Double> findExtrasForBuchungID(int id) {
		String selectQuery = "SELECT summe, beschreibung FROM buchung_extras WHERE buchung_id = ?";
		HashMap<String, Double> extras = new HashMap<>(); 
		
		try (Connection conn = PostgresConnector.getConn();
				var prepStatement = conn.prepareStatement(selectQuery)) {
			
			prepStatement.setInt(1, id);
			var resultSet = prepStatement.executeQuery();
			
			while (resultSet.next()) {
				extras.put(resultSet.getString("beschreibung"), resultSet.getDouble("summe"));
			}
			
			return extras;
		
		} catch (SQLException e) {
			throw new RuntimeException("Extras für Buchung mit ID " + id + " konnte nicht gefunden werden"+ e.getMessage());
		}
	}

}
