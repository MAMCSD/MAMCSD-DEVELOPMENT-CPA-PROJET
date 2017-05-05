package authentification;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import main.ConnectionMySQL;

public class Recuperation {

	String nom;
	String prenom;
	String fonction;


	public void verificationLogin(ResultSet resultats) throws SQLException{
		//System.out.println("Parcours des donnees retournees");
		ResultSetMetaData rsmd = resultats.getMetaData();
		int nbCols = rsmd.getColumnCount();
		boolean contientDAutresDonnees = resultats.next();

		//System.out.println(contientDAutresDonnees);
		if(contientDAutresDonnees==true)
		{

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
			System.out.println("L'identifiant ou le mot de passe est incorrect");
		}
		
		resultats.close();
	}



	/*public static void main(java.lang.String[] args) {

		String id="HJP";
		String mdp="16H11JP75";
		//String id="uhjkjn";
		//String mdp="huijk";
		try {
			ConnectionMySQL.init();			
		} catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
			System.err.println("La librairie n'est pas disponible");
			System.exit(5);
		}


		Recuperation atp = new Recuperation();
		ConnectionMySQL connecteur = new ConnectionMySQL("127.0.0.1", "root");
		try {
			connecteur.connect();

			ResultSet resultSet = connecteur.execute("Select salaries.Nom,salaries.Prenom,salaries.Fonction, salaries.Identifiant, authentification.Mot_de_passe  from salaries inner JOIN authentification ON salaries.Identifiant= authentification.Identifiant where salaries.Identifiant=\""+id+"\" AND authentification.Mot_de_passe=\""+mdp+"\"");

			atp.verificationLogin(resultSet);

			//Bonne pratique: fermer votre résultat
			resultSet.close();

			//Bonne pratique: fermer le connecteur
			connecteur.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}*/
}
