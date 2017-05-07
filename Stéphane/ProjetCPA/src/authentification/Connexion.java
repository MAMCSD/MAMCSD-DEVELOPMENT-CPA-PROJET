package authentification;

import java.util.Scanner;

import main.ConnectionMySQL;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Connexion {

	private Scanner scanner = new Scanner(System.in);



	public boolean connexion() 
	{
		String id;
		String mdp;
		int demandeConnexion =0;
		boolean etatConnexion=false;

		//  exemple id="HJP"  mdp="16H11JP75" sont correct;


		while (demandeConnexion !=3 && etatConnexion!=true)
		{


			try {
				ConnectionMySQL.init();			
			} catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
				System.err.println("La librairie n'est pas disponible");
				System.exit(5);
			}

			Recuperation atp = new Recuperation();
			ConnectionMySQL connecteur = new ConnectionMySQL("127.0.0.1", "root"); // création de la connexion avec la base de donnée
			try {
				connecteur.connect();

				id=getValeur("entrer votre identifiant :");
				mdp=getValeur("entrer votre mot de passe  :");


				// le select nous permet re récuperer les informations du salarié
				ResultSet resultSet = connecteur.execute("Select salaries.Nom,salaries.Prenom,salaries.Fonction, salaries.Identifiant, authentification.Mot_de_passe  from salaries inner JOIN authentification ON salaries.Identifiant= authentification.Identifiant where salaries.Identifiant=\""+id+"\" AND authentification.Mot_de_passe=\""+mdp+"\"");


				etatConnexion=atp.verificationLogin(resultSet);

				//Bonne pratique: fermer votre résultat
				resultSet.close();

				//Bonne pratique: fermer le connecteur
				connecteur.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			demandeConnexion++;

		}

		return etatConnexion;
	}


	public String getValeur(String message){
		System.out.println(message);
		return  scanner.next();
	} 
}
