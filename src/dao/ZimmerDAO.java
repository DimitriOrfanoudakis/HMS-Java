package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import domain.Gast;
import domain.Zimmer;
import domain.Zimmertyp;

public class ZimmerDAO {
	
	private GastDAO gastDAO;

   	public int insertZimmer(Zimmer zimmer) {
		int id = -1;
		String insertQuery = "INSERT INTO zimmer (nummer, pax, belegt, status, zimmertyp_id, gast_id) "
				+ "VALUES (?, ?, ?, ?, ?, ?)";
		
		try (Connection conn = PostgresConnector.getConn();
				var prepStatement = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
			
			prepStatement.setString(1, zimmer.getNummer());
			prepStatement.setInt(2, zimmer.getPax());
			prepStatement.setBoolean(3, zimmer.istBelegt());
			prepStatement.setString(4, zimmer.getStatus().name());
			prepStatement.setInt(5, zimmer.getTyp().getId());
			prepStatement.setInt(6, zimmer.getGast().getId());
			
			prepStatement.execute();
			
			try (ResultSet generatedKeys = prepStatement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					id = generatedKeys.getInt(1);				
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Zimmer konnte nicht eingetragen werden: " + e.getMessage());
		}
		
		return id;
	}
	
	public int updateZimmer(Zimmer zimmer) {
		int rowsAffected = -1;
		String updateQuery = "UPDATE zimmer SET nummer = ?, pax = ?, belegt = ?, status = ?, zimmertyp_id = ?, gast_id =? WHERE id = ?";
		
		try (Connection conn = PostgresConnector.getConn();
				var prepStatement = conn.prepareStatement(updateQuery)) {
			
			prepStatement.setString(1, zimmer.getNummer());
			prepStatement.setInt(2, zimmer.getPax());
			prepStatement.setBoolean(3, zimmer.istBelegt());
			prepStatement.setString(4, zimmer.getStatus().name());
			prepStatement.setInt(5, zimmer.getTyp().getId());
			prepStatement.setInt(6, zimmer.getGast().getId());
			prepStatement.setInt(7, zimmer.getId());
			
			rowsAffected = prepStatement.executeUpdate();
			return rowsAffected;
			
		} catch (SQLException e) {
			throw new RuntimeException("Zimmer konnte nicht aktualisiert werden: " + e.getMessage());
		}		
	}
	
	public int deleteZimmer(Zimmer zimmer) {
		int rowsAffected = -1;
		String deleteQuery = "DELETE FROM zimmer WHERE id = ?";
		
		try (Connection conn = PostgresConnector.getConn();
				var prepStatement = conn.prepareStatement(deleteQuery)) {
			
			prepStatement.setInt(1, zimmer.getId());
			
			rowsAffected = prepStatement.executeUpdate();
			return rowsAffected;
			
		} catch (SQLException e) {
			throw new RuntimeException("Zimmer konnte nicht gelöscht werden: " + e.getMessage());
		}
	}
	// find ZIMMER
	public Zimmer findZimmerbyID(int id) {
		String selectQuery = "SELECT id, nummer, pax, belegt, status, zimmertyp_id, gast_id FROM zimmer WHERE id = ?";
		
		try (Connection conn = PostgresConnector.getConn();
				var prepStatement = conn.prepareStatement(selectQuery)) {
			
			prepStatement.setInt(1, id);
			var resultSet = prepStatement.executeQuery();
			
			if (resultSet.next()) {
				String nummer = resultSet.getString("nummer");
				int pax = resultSet.getInt("pax");
				boolean belegt = resultSet.getBoolean("belegt");
				Zimmer.Status status = Zimmer.Status.valueOf(resultSet.getString("status").toUpperCase()); // status in Datenbank als VARCHAR.. String wird zu enum umgewandelt
				Zimmertyp typ = findZimmertypByID(resultSet.getInt("zimmertyp_id"));
				Gast gast = gastDAO.findGastByID(resultSet.getInt("gast_id"));
				
				Zimmer zimmer = new Zimmer(id, nummer, gast, typ, pax, belegt, status);
				return zimmer;
			} else {
				return null;
			}
			
		} catch (SQLException e) {
			throw new RuntimeException("Zimmer mit ID " + id + " konnte nicht gefunden werden: " + e.getMessage());
		}
	}
	
	// Find ZIMMER TYP!
	public Zimmertyp findZimmertypByID(int id) {
		String selectQuery = "SELECT id, betten, preis_pro_nacht, bezeichnung FROM zimmertypen WHERE id = ?";
		
		try (Connection conn = PostgresConnector.getConn();
				var prepStatement = conn.prepareStatement(selectQuery)) {
			
			prepStatement.setInt(1, id);
			var resultSet = prepStatement.executeQuery();
			
			if (resultSet.next()) {
				int betten = resultSet.getInt("betten");
				Double preisProNacht = resultSet.getDouble("preis_pro_nacht");
				String bezeichnung = resultSet.getString("bezeichnung");
				
				Zimmertyp typ = new Zimmertyp(id, betten, preisProNacht, bezeichnung);
				return typ;
			} else {
				return null;
			}
			
		} catch (SQLException e) {
			throw new RuntimeException("Zimmertyp mit ID " + id + " konnte nicht gefunden werden: " + e.getMessage());
		}
	}
	
	
	// Helfermethoden
	
	public Integer findZimmertypIDByDescription(String bezeichnung) {
		String selectQuery = "SELECT id from zimmertypen WHERE bezeichnung = ?";
		
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
			throw new RuntimeException("ID für Zimmertyp " + bezeichnung + " konnte nicht gefunden werden: " + e.getMessage());
		}
	}
	
	// Konstruktor mit GastDAO
	
	 public ZimmerDAO(GastDAO gastDAO) {
	        this.gastDAO = gastDAO;
	    }
	
	
}
