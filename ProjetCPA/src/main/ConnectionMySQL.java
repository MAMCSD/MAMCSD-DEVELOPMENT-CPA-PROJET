package main;
import java.sql.*;
/**
 * 
 * @author Stéphane
 *  Cette classe permet de réaliser la liaison avec la base de donnée
 *
 */

public class ConnectionMySQL {

	private Connection connection = null;
	private String user, host;

	private static boolean driverLoaded = false;

	public static boolean isDriverLoaded(){
		return driverLoaded;
	}

	public static void init() throws ClassNotFoundException, IllegalAccessException, InstantiationException{
		if(!driverLoaded){
			//Chargement du pilote
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			driverLoaded = true;
		}
	}

	public ConnectionMySQL() {
		if(!driverLoaded){
			throw new IllegalStateException("Cannot instantiate if driver is not loaded. Please call "+getClass().getName()+".init() method before invoking this constructor.");
		}
	}

	public ConnectionMySQL(String host, String user) {
		this();
		this.host = "127.0.0.1";
		this.user = "root";

	}

	public void connect() throws SQLException{
		//Connexion a la base de données
		//System.out.println("Connexion à la base de données");

		String dBurl = "jdbc:mysql://"+host+"/auto_concept";
		connection = DriverManager.getConnection(dBurl, user,"admin");


	}
	public ResultSet execute(String requete) throws SQLException{
		//System.out.println("creation et execution de la requête :"+requete);
		Statement stmt = connection.createStatement();
		return stmt.executeQuery(requete);
	}

	public void close() throws SQLException{
		connection.close();
	}

}