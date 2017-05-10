package exportation;

import java.io.*;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import authentification.Recuperation;
import main.ConnectionMySQL;

public class LectureTableFactures {

	public void afficherDonnees(ResultSet resultats) throws SQLException{
		System.out.println("Parcours des donnees retournees");
		ResultSetMetaData rsmd = resultats.getMetaData();
		int nbCols = rsmd.getColumnCount();
		boolean contientDAutresDonnees = resultats.next();

		for (int i = 1; i <= nbCols; i++) {
			if(i > 1) {
				System.out.print( " | ");
			}
			System.out.print(rsmd.getColumnLabel(i));
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
		exportation();

		resultats.close();
	}
	public void AfficherTableFactures() {

		ConnectionMySQL connecteur = new ConnectionMySQL("127.0.0.1", "root");
		try {
			connecteur.connect();

			ResultSet resultSet = connecteur.execute("SELECT * FROM factures");

			afficherDonnees(resultSet);

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
	public void exportation () {

		try { 
			ConnectionMySQL connecteur = new ConnectionMySQL("127.0.0.1", "root");
			connecteur.connect();
			ResultSet resultSet = connecteur.execute("SELECT * FROM factures");
			try{ 
				//PrintStream ps = new PrintStream(System.out);
				PrintStream out = new PrintStream(new FileOutputStream("Test.csv"));
				
				//ps.println(resultSet);
			    //ps.print("New Line");

			      // flush the stream
			      //ps.flush();
				while(resultSet.next()) 
				{ //ici j'aimerais pouvoir insérer le résultat de ma requête avec des ; comme délimiteur 
					//Writer.print(resultSet.getInt("ID_Facture"));
					//writer.print(";");
					//writer.print(resultSet.getString("nom"));
					//writer.print(";");
					//writer.println(resultSet.getString("prenom"));
				}

			} catch (Exception e) {
				e.printStackTrace();

			}    

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally{

		}
	}
}
