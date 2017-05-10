package exportation;

import java.io.*;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import main.ConnectionMySQL;

/**
 * 
 * @author Stéphane
 *  Cette classe va nous permettre de faire l'exportation de la table des factures.
 */

public class LectureTableFactures {
	

	private StringBuffer buffer = new StringBuffer(128);

	public void afficherDonnees(ResultSet resultats) throws SQLException{
		
		/**
		 * Dans cette méthode nous faisons la lecture des informations récupérées dans la base de données et nous formons notre chaine de sortie à l'aide d'un buffer.
		 */
		System.out.println("Parcours des donnees retournees");
		ResultSetMetaData rsmd = resultats.getMetaData();
		int nbCols = rsmd.getColumnCount();
		boolean contientDAutresDonnees = resultats.next();

		for (int i = 1; i <= nbCols; i++) {
			if(i > 1) {
				System.out.print( " | ");
				buffer.append(" ; ");
			}
			System.out.print(rsmd.getColumnLabel(i));
			buffer.append(rsmd.getColumnLabel(i));
			
		}
		System.out.println();
		buffer.append(" \r\n ");
		while (contientDAutresDonnees) {
			for (int i = 1; i <= nbCols; i++){
				if(i > 1) {
					System.out.print( " | ");
					buffer.append(" ; ");
				}
				System.out.print(resultats.getString(i));
				buffer.append(resultats.getString(i));
			}
			System.out.println();
			buffer.append(" \r\n ");
			contientDAutresDonnees = resultats.next();
		}
		
		System.out.println();
		System.out.println("essai buffer");
		System.out.println(buffer);
		System.out.println();
		
		resultats.close();
	}
	public void AfficherTableFactures() {

		/**
		 * Dans cette méthode nous gérons la connexion avec la base de données et nous lançons la requête pour récupérer les informations et ensuite celles-ci sont transformées.
		 */
		ConnectionMySQL connecteur = new ConnectionMySQL("127.0.0.1", "root");
		try {
			connecteur.connect();

			ResultSet resultSet = connecteur.execute("SELECT * FROM factures");

			afficherDonnees(resultSet);
			exportation();

			//Bonne pratique: fermer votre résultat
			resultSet.close();

			//Bonne pratique: fermer le connecteur
			connecteur.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			ConnectionMySQL.init();			
		} catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
			System.err.println("La librairie n'est pas disponible");
			System.exit(5);
		}

	}
	public void exportation ()  {

		/**
		 * Dans cette méthode nous gérons l'aspect écriture dans un fichier de sortie à l'aide d'un bufferedWriter.
		 */
		//lecture et copie des données

			BufferedWriter writer = null;
			
			try{
				
				writer = new BufferedWriter(new FileWriter("factures.csv"));

				writer.write(buffer.toString());


				writer.flush(); //vider le buffer

			} catch (IOException e) {
				e.printStackTrace();
			}
			finally
			{
				//fermeture de out
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		

	}

}
