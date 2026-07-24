package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import domain.Gast;

public class GastDAO {
	
	public int insertGast(Gast gast) {
		int id = -1;
		String insertQuery = "INSERT INTO gaeste (vorname, nachname, wohnort, email, tel_nummer) "
				   + "VALUES (?, ?, ?, ?, ?);";
		
		try (Connection conn = PostgresConnector.getConn();
				var prepStatement = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
					
			prepStatement.setString(1, gast.getVorname());
			prepStatement.setString(2, gast.getNachname());
			prepStatement.setString(3, gast.getWohnort());
			prepStatement.setString(4, gast.getEmail());
			prepStatement.setString(5, gast.getTelNummer());
			
			prepStatement.execute();
			
			try (ResultSet generatedKeys = prepStatement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					id = generatedKeys.getInt(1);				
				}
			}
		
		} catch (SQLException e) {
            throw new RuntimeException("Gast konnte nicht eingetragen werden: " + e.getMessage());
        }
		
		return id;
	}
	
	public int updateGast(Gast gast) {
		int rowsAffected = -1;
		String updateQuery = "UPDATE gaeste SET vorname = ?, nachname = ?, wohnort = ?, email = ?, tel_nummer = ? WHERE id = ?";
		
		try (Connection conn = PostgresConnector.getConn();
				var prepStatement = conn.prepareStatement(updateQuery);) {
			
			prepStatement.setString(1, gast.getVorname());
			prepStatement.setString(2, gast.getNachname());
			prepStatement.setString(3, gast.getWohnort());
			prepStatement.setString(4, gast.getEmail());
			prepStatement.setString(5, gast.getTelNummer());
			prepStatement.setInt(6, gast.getId());
			
			rowsAffected = prepStatement.executeUpdate();
			return rowsAffected;
						
		} catch (SQLException e) {
			throw new RuntimeException("Gast konnte nicht aktualisiert werden: " + e.getMessage());
		}		
	}
	
	public int deleteGast(Gast gast) {
		int rowsAffected = -1;
		String deleteQuery = "DELETE FROM gaeste WHERE id = ?";
		
		try (Connection conn = PostgresConnector.getConn();
				var prepStatement = conn.prepareStatement(deleteQuery);) {
			
			prepStatement.setInt(1, gast.getId());
			
			rowsAffected = prepStatement.executeUpdate();
			return rowsAffected;			
		} catch (SQLException e) {
			throw new RuntimeException("Gast konnte nicht gelöscht werden: " + e.getMessage());
		}
	}
	
	public Gast findGastByID(int id) {
		String selectQuery = "SELECT id, vorname, nachname, wohnort, email, tel_nummer FROM gaeste WHERE id = ?";
		
		try (Connection conn = PostgresConnector.getConn();
				var prepStatement = conn.prepareStatement(selectQuery)) {
			
			prepStatement.setInt(1, id);
			var resultSet = prepStatement.executeQuery();
			
			if (resultSet.next()) {
				String vorname = resultSet.getString("vorname");
				String nachname = resultSet.getString("nachname");
				String wohnort = resultSet.getString("wohnort");
				String email = resultSet.getString("email");
				String telNummer = resultSet.getString("tel_nummer");
				
				Gast gast = new Gast(id, vorname, nachname, wohnort, email, telNummer);
				return gast;
			} else {
				return null;
			}
			
			
		} catch (SQLException e) {
			throw new RuntimeException("Gast konnte nicht gefunden werden: " + e.getMessage());
		}
	}
	
}