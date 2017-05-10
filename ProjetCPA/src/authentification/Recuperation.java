package authentification;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;



public class Recuperation {

	/**
	 * 
	 * @author Stéphane
	 * Cette classe va nous permettre de faire la récupération des logins de connexion des utilisateurs ainsi que des droits des utilisateurs.
	 * L'attribut etatConnexion est initialisé à false pour signaler que la connexion n'est pas établie.
	 */

	String nom;
	String prenom;
	String fonction;
	boolean etatConnexion= false; // etatConnexion= false equivaut à connexion non établie


	public boolean verificationLogin(ResultSet resultats) throws SQLException{
		/**
		 * Cette méthode nous permet de parcourir les données contenues dans le retour de la requête sql et d'extraire les résultats qu'on souhaite.
		 */
		
		//System.out.println("Parcours des donnees retournees");
		ResultSetMetaData rsmd = resultats.getMetaData();
		int nbCols = rsmd.getColumnCount();
		boolean contientDAutresDonnees = resultats.next();

		if(contientDAutresDonnees==true)
		{
			etatConnexion= true;
			while (contientDAutresDonnees) {
				for (int i = 1; i <= nbCols; i++){
					if(i > 1) {
						//System.out.print( " | ");
					}
					//	System.out.print(resultats.getString(i));

					if ( i==1)
					{
						nom=resultats.getString(i);
					}
					if ( i==2)
					{
						prenom=resultats.getString(i);
					}
					if ( i==3)
					{
						fonction=resultats.getString(i);
					}
				}

				System.out.println();
				contientDAutresDonnees = resultats.next();
				System.out.println( "Bienvenue " + nom +" "+ prenom );

			}
		}
		else
		{
			etatConnexion= false;
			//System.out.println("L'identifiant ou le mot de passe est incorrect");// a activé si on prend la methode console

		}

		resultats.close();
		return etatConnexion;
	}

	public boolean droitUtilisateur() {
		
		/**
		 * Cette méthode retourne les droits de l'utilisateur, dans notre cas la magasiniere est admin tandis que tous les autres sont des utilisateurs normaux.
		 */
		boolean admin=false;

		//System.out.println(fonction);

		if (fonction.equals("magasiniere"))
		
		{
			admin=true;
		}

		return admin;
	}
}


