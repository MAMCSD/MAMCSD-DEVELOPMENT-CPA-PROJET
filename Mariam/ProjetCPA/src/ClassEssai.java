import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import gestionBdd.LectureTableFactures;

import main.ConnectionMySQL;

public class ClassEssai {


	public void afficherDonnees(ResultSet resultats) throws SQLException{
		//System.out.println("Parcours des donnees retournees");
		ResultSetMetaData rsmd = resultats.getMetaData();
		int nbCols = rsmd.getColumnCount();
		boolean contientDAutresDonnees = resultats.next();
		
		System.out.println(contientDAutresDonnees);

		for (int i = 1; i <= nbCols; i++) {
			if(i > 1) {
				System.out.print( " | ");// à commenter pour l'authentification
			}
			System.out.print(rsmd.getColumnLabel(i));  // à commenter pour l'authentification 
		}
		System.out.println();

		while (contientDAutresDonnees) {
			for (int i = 1; i <= nbCols; i++){
				if(i > 1) {
					System.out.print( " | ");
				}
				System.out.print(resultats.getString(i));
			}
			System.out.println();
			contientDAutresDonnees = resultats.next();
		}

		resultats.close();
	}

	/*public static void main(java.lang.String[] args) {
		
		String id="BL";
		String mdp="04B03L70";
		//String id="uhjkjn";
		//String mdp="huijk";
		
		try {
			ConnectionMySQL.init();			
		} catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
			System.err.println("La librairie n'est pas disponible");
			System.exit(5);
		}


		ClassEssai atp = new ClassEssai();
		ConnectionMySQL connecteur = new ConnectionMySQL("127.0.0.1", "root");
		try {
			connecteur.connect();

			ResultSet resultSet = connecteur.execute("Select salaries.Nom, salaries.Identifiant, authentification.Mot_de_passe from salaries inner JOIN authentification ON salaries.Identifiant= authentification.Identifiant where salaries.Identifiant=\""+id+"\" AND authentification.Mot_de_passe=\""+mdp+"\"");

			atp.afficherDonnees(resultSet);
			
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