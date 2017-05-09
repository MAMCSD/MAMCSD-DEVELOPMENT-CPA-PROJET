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
		
		/**
		 * Cette méthode nous permet de faire la connexion avec la base de données nommée "projet_autoconcept"
		 */
		
		//Connexion a la base de données
		//System.out.println("Connexion à la base de données");

<<<<<<< HEAD

		String dBurl = "jdbc:mysql://"+host+"/projet_autoconcept";
		connection = DriverManager.getConnection(dBurl, user,"");
=======
		String dBurl = "jdbc:mysql://"+host+"/auto_concept";
		connection = DriverManager.getConnection(dBurl, user,"admin");
>>>>>>> ddda9b59ac6430e25f0085a3f33e2a99340aab18


	}
	public ResultSet execute(String requete) throws SQLException{
		
		/**
		 * Cette méthode nous permet d'exécuter des requêtes SQL dans la base de données.
		 */
		
		//System.out.println("creation et execution de la requête :"+requete);
		
		Statement stmt = connection.createStatement();
		return stmt.executeQuery(requete);
	}

	public void close() throws SQLException{
		/**
		 * Cette méthode nous permet de fermer la connexion avec la base de données.
		 */
		connection.close();
	}

}