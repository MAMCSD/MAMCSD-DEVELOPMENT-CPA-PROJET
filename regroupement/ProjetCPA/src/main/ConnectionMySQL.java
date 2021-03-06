package main;
import java.sql.*;
/**
 * 
 * @author St�phane
 *  Cette classe permet de r�aliser la liaison avec la base de donn�e
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
		this.host = host;
		this.user = user;

	}

	public void connect() throws SQLException{
		
		/**
		 * Cette m�thode nous permet de faire la connexion avec la base de donn�es nomm�e "projet_autoconcept"
		 */
		
		//Connexion a la base de donn�es
		//System.out.println("Connexion � la base de donn�es");


		String dBurl = "jdbc:mysql://"+host+"/projet_autoconcept";
		connection = DriverManager.getConnection(dBurl, user,"");


	}
	public ResultSet execute(String requete) throws SQLException{
		
		/**
		 * Cette m�thode nous permet d'ex�cuter des requ�tes SQL dans la base de donn�es.
		 */
		
		//System.out.println("creation et execution de la requ�te :"+requete);
		
		Statement stmt = connection.createStatement();
		return stmt.executeQuery(requete);
	}

	public void close() throws SQLException{
		/**
		 * Cette m�thode nous permet de fermer la connexion avec la base de donn�es.
		 */
		connection.close();
	}

}