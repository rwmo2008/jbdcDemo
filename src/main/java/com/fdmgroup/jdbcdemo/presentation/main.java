package com.fdmgroup.jdbcdemo.presentation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.fdmgroup.jdbcdemo.model.NBAPlayer;

import java.sql.ResultSet;

public class main {

	public static void main(String[] args) throws SQLException {
		DriverManager.registerDriver(new org.h2.Driver());
		try (Connection connection = DriverManager
				.getConnection("jdbc:h2:file:C:/rwmo2/Desktop/nope/H2;AUTO_SERVER=TRUE", "sa", "test");
				Statement stmt = connection.createStatement();
				PreparedStatement deleteStmt = connection
						.prepareStatement("DELETE FROM nba_players WHERE first_name LIKE ? AND last_name LIKE ?");
				PreparedStatement insertStmt = connection
						.prepareStatement("INSERT INTO nba_players(id, first_name, last_name, career_points)"
								+ "VALUES(nextval('nba_players_id_seq'), ?, ?, ?)");

		) {
			// DDL statements
			String ddl = "DROP TABLE IF EXISTS nba_players";
			stmt.execute(ddl);
			ddl = "CREATE TABLE nba_players (" + "id NUMBER(4) CONSTRAINT nba_players_pk PRIMARY KEY,"
					+ " first_name VARCHAR2(15)," + " last_name VARCHAR2(15)," + " career_points NUMBER(5)" + ");";
			stmt.execute(ddl);

			ddl = "DROP SEQUENCE IF EXISTS nba_players_id_seq";
			stmt.execute(ddl);
			ddl = "CREATE SEQUENCE nba_players_id_seq INCREMENT BY 1 START WITH 101";
			stmt.execute(ddl);

			// DML statements
			String dml = "INSERT INTO nba_players(id, first_name, last_name, career_points)"
					+ " VALUES(nextval('nba_players_id_seq'), 'Curly', 'Neal', 22)";
			stmt.executeUpdate(dml);

			// Query(SELECT) statements
			String query = "SELECT id, first_name, last_name, career_points FROM nba_players;";
			ResultSet rs = stmt.executeQuery(query);

			/*
			 * while(rs.next()) { int playerId = rs.getInt("id"); String firstName =
			 * rs.getString("first_name"); String lastName = rs.getString("last_name"); int
			 * careerPoints = rs.getInt("career_points"); System.out.println("Player id: "+
			 * playerId + " First name: "+ firstName + " Last name: " + lastName +
			 * " Career Points: " + careerPoints); }
			 */

			while (rs.next()) {
				NBAPlayer nbaPlayer = new NBAPlayer();
				System.out.println(nbaPlayer);
				nbaPlayer.setPlayerId(rs.getInt(1));
				nbaPlayer.setFirstName(rs.getString(2));
				nbaPlayer.setLastName(rs.getString(3));
				nbaPlayer.setCareerPoints(rs.getInt(4));
				System.out.println(nbaPlayer);
			}

			//batching
			deleteStmt.setString(1, "Curly");
			deleteStmt.setString(2, "Neal");
			deleteStmt.executeUpdate();

			connection.setAutoCommit(false);

			insertStmt.setString(1, "Michael");
			insertStmt.setString(2, "Jordan");
			insertStmt.setInt(3, 32_292);
			insertStmt.addBatch();

			insertStmt.setString(1, "Lebron");
			insertStmt.setString(2, "James");
			insertStmt.setInt(3, 35_367);
			insertStmt.addBatch();

			insertStmt.setString(1, "Kobe");
			insertStmt.setString(2, "Bryant");
			insertStmt.setInt(3, 33_643);
			insertStmt.addBatch();

			insertStmt.setString(1, "Karl");
			insertStmt.setString(2, "Malone");
			insertStmt.setInt(3, 36_298);
			insertStmt.addBatch();

			insertStmt.setString(1, "Lewis");
			insertStmt.setString(2, "Alcindor");
			insertStmt.setInt(3, 0);
			insertStmt.addBatch();

			insertStmt.executeBatch();
			connection.commit();
			connection.setAutoCommit(true);

			/**
			 * 
			 * 
			 * 
			 * In-class Exercise
			 * 
			 * 
			 * 
			 */

			// (1) Using a PreparedStatement (e.g. updateStmt), update player
			// Lewis Alcindor to: Kareem Abudul-Jabbar, 38_387
			PreparedStatement updateStmt = connection.prepareStatement("UPDATE nba_Players SET first_name = ?, last_name = ?, career_points = ? WHERE first_name LIKE ? AND last_name LIKE ? ");
			updateStmt.setString(1, "Kareem");
			updateStmt.setString(2, "Abdul-Jabbar");
			updateStmt.setInt(3, 38387);
			updateStmt.setString(4, "Lewis");
			updateStmt.setString(5,  "Alcindor");
			
			
			
			
			// (2) Implement findAllNbaPlayers
			List<NBAPlayer> players = findAllPlayers(connection);
			
			// (3) Display on the console each player sorted by career_points
			// in descending order
			System.out.println("Players sorted by career points in descending order");
			players.sort((player1, player2) -> player1.getCareerPoints() - player2.getCareerPoints());
			players.forEach(player -> System.out.println(player));
		}
		
		/*
		 * CRUD
		 *  public void create (NBAPlayer player){
		 *  
		 *  }
		 *  
		 *  public NBAPlayer read (int id){
		 *  
		 *  }
		 *  public void update (int id){}
		 *  
		 *  public void update(NBAPlayer player){
		 *  
		 *   }
		 *  
		 *  public void delete(int id){
		 *  	
		 *  }
		 *  
		 *  public List<NBAPlayer> readAll(){
		 *  
		 *  }
		 * */

	}
	private static List<NBAPlayer> findAllPlayers (Connection conn){
		List<NBAPlayer> players = new ArrayList<>();
		try {
			Statement stmt = conn.createStatement();
			String query = "SELECT id, first_name, last_name, career_points FROM nba_players";
			ResultSet rs = stmt.executeQuery(query);
			
			while (rs.next()) {
				NBAPlayer player = new NBAPlayer();
				player.setPlayerId(rs.getInt(1));
				player.setFirstName(rs.getString(2));
				player.setLastName(rs.getString(3));
				player.setCareerPoints(rs.getInt(4));
				players.add(player);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return players;
		
	}

}
