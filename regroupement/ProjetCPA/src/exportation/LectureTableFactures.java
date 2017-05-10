package exportation;

import java.io.*;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import main.ConnectionMySQL;

/**
 * 
 * @author St�phane
 *  Cette classe va nous permettre de faire l'exportation de la table des factures.
 */

public class LectureTableFactures {
	

	private StringBuffer buffer = new StringBuffer(128);

	public void afficherDonnees(ResultSet resultats) throws SQLException{
		
		/**
		 * Dans cette m�thode nous faisons la lecture des informations r�cup�r�es dans la base de donn�es et nous formons notre chaine de sortie � l'aide d'un buffer.
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
		 * Dans cette m�thode nous g�rons la connexion avec la base de donn�es et nous lan�ons la requ�te pour r�cup�rer les informations et ensuite celles-ci sont transform�es.
		 */
		ConnectionMySQL connecteur = new ConnectionMySQL("127.0.0.1", "root");
		try {
			connecteur.connect();

			ResultSet resultSet = connecteur.execute("SELECT * FROM factures");

			afficherDonnees(resultSet);
			exportation();

			//Bonne pratique: fermer votre r�sultat
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
		 * Dans cette m�thode nous g�rons l'aspect �criture dans un fichier de sortie � l'aide d'un bufferedWriter.
		 */
		//lecture et copie des donn�es

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
