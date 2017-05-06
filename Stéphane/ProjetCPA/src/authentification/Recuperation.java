package authentification;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import main.ConnectionMySQL;

public class Recuperation {

	String nom;
	String prenom;
	String fonction;
	boolean etatConnexion= false; // etatConnexion= false equivaut à connexion non établie


	public boolean verificationLogin(ResultSet resultats) throws SQLException{
		//System.out.println("Parcours des donnees retournees");
		ResultSetMetaData rsmd = resultats.getMetaData();
		int nbCols = rsmd.getColumnCount();
		boolean contientDAutresDonnees = resultats.next();

		//System.out.println(contientDAutresDonnees);
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

				//System.out.println();
				//System.out.println(" le nom est "+nom+", le prénom est "+prenom+" la fonction est "+fonction);
				System.out.println();
				contientDAutresDonnees = resultats.next();
				System.out.println( "Bienvenue " + nom +" "+ prenom);

			}
		}
		else
		{
			etatConnexion= false;
			System.out.println("L'identifiant ou le mot de passe est incorrect");
			
		}
		
		resultats.close();
		return etatConnexion;
	}

}
