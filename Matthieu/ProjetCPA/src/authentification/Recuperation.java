package authentification;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class Recuperation {

	private String nom;
	private String prenom;
	private String fonction;
	boolean etatConnexion= false; // etatConnexion= false equivaut à connexion non établie


	public boolean verificationLogin(ResultSet resultats) throws SQLException{
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
		boolean admin=false;
		
		System.out.println(fonction);
			

		if (fonction == "magasiniere") 
		{
			System.out.println("toto");
			admin=true;
		}

		return admin;
	}
}


