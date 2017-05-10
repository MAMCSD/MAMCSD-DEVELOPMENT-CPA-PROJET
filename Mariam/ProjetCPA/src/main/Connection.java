package main;

import java.util.Scanner;
import java.sql.ResultSet;
import java.sql.SQLException;
import gestionBdd.LectureTableFactures;

import authentification.Recuperation;

public class Connection {
	
	private Scanner scanner = new Scanner(System.in);

	public static void main(java.lang.String[] args) {

		String id;
		String mdp;
		
		//  exemple id="HJP"  mdp="16H11JP75" sont correct;
				
		try {
			ConnectionMySQL.init();			
		} catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
			System.err.println("La librairie n'est pas disponible");
			System.exit(5);
		}
		LectureTableFactures lec = new LectureTableFactures();
		lec.AfficherTableFactures();
		Connection connect =new Connection();
		Recuperation atp = new Recuperation();
		ConnectionMySQL connecteur = new ConnectionMySQL("127.0.0.1", "root"); // création de la connexion avec la base de donnée
		try {
			connecteur.connect();
			
			id=connect.getValeur("entrer votre identifiant :");
			mdp=connect.getValeur("entrer votre mot de passe  :");
			
					
			// le select nous permet re récuperer les informations du salarié
			ResultSet resultSet = connecteur.execute("Select salaries.Nom,salaries.Prenom,salaries.Fonction, salaries.Identifiant, authentification.Mot_de_passe  from salaries inner JOIN authentification ON salaries.Identifiant= authentification.Identifiant where salaries.Identifiant=\""+id+"\" AND authentification.Mot_de_passe=\""+mdp+"\"");

			atp.verificationLogin(resultSet);

			//Bonne pratique: fermer votre résultat
			resultSet.close();

			//Bonne pratique: fermer le connecteur
			connecteur.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public String getValeur(String message){
		System.out.println(message);
		return  scanner.next();
} 
}