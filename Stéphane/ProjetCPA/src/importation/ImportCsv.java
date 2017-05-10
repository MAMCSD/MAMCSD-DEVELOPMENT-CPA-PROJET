package importation;

import java.io.*;
import java.util.Scanner;
import java.sql.*;
import main.ConnectionMySQL;

/**
 * Classe qui va demander à l'utilisateur le chemin d'un fichier à importer, lui afficher le contenu du fichier
 * ainsi que la liste des tables de la BDD.
 * L'utilisateur entre (dans l'ideal il lui sera propose une liste avec des numero pour eviter les erreurs de saisie) le nom de la table qu'il desire mettre a jour
 * Pour finir la classe met à jour la BDD avec les parametres entrés.
*/
public class ImportCsv {
	
	Scanner scan=new Scanner(System.in);	
	String chemin;
	
	
	
	public File ouvertureFichier(){
		/**
	 * Methode qui demande le chemin et le nom du fichier à importer
	 *
	 */
		File fichier;
		//String chemin;
		
		System.out.println("\n---------------------------------------------------------");
		System.out.println("Veuilles entrer le chemin et le nom du fichier à importer");
		System.out.println("---------------------------------------------------------");
		chemin=scan.nextLine();
		fichier=new File(chemin);
		
		
		
		
		return fichier;
	}
	
	
	public String lectureFichier(File fichier){
		
	/**
	 * Methode qui lit le fichier choisi par l'utilisateur, lui affiche son contenu en brut et l'insere dans une variable si besoin d'un traitement ulterieur.
	 * 
	 */
		int i;
		//int j=0;
		String contenuFichier="";
		
		try{
			
			FileReader fr=new FileReader (fichier);
			
			try{
				while((i=fr.read())!=-1){ 
					
					contenuFichier+=((char)i);
					//j++;
				}
				//System.out.println("Contenu"+contenuFichier);
			}
			
			finally{
				fr.close();
			}
		}
		catch(FileNotFoundException ef){
			System.out.println("fichier introuvable");
		}
		catch(IOException e){
			System.out.println(e+"erreur lors de la lecture du fichier");
		}
	
		return contenuFichier;
	}
		
//		public void decoupageCsv(String contenu){
//			
//			System.out.println(contenu);
			
//		}

		
		
		public void affichageCsv(File fichier){
		
			/**
		 * Methode qui affiche le contenu d'un fichier csv en mettant en forme les diffrents champs. 
		 * 
		 */
			BufferedReader br=null;
			String ligne="";
			
			try{
				br=new BufferedReader(new FileReader(fichier));
				System.out.println("\n");
				while((ligne=br.readLine()) != null){
					String[] cellules = ligne.split(";");
					int i=0;
					for(i=0;i<cellules.length-1;i++)
					{
						System.out.print(cellules[i]+" | ");
					}
					System.out.print(cellules[i]+"\n");
				}
				System.out.println("\n");
			}
			catch(FileNotFoundException e){
				e.printStackTrace();
			}
			catch (IOException o){
				o.printStackTrace();
			}
			finally{
				if (br !=null){
					try{
						br.close();
					}
					catch(IOException o){
						o.printStackTrace();
					}
				}
				
			}
		}
		
		
		
		public void importCsv(File fichier){
			/**
		 * Methode qui affiche la liste des tables de la BDD et demande à l'utilisateur laquelle il veut mettre à jour.
		 * Met ensuite à jour la table choisie.
		 *
		 */
			String tableChoisie;
			
			try {
				ConnectionMySQL.init();
			} catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Erreur Driver");
			}
			
			ConnectionMySQL connexion= new ConnectionMySQL("127.0.0.1", "root");
			try {
				connexion.connect();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.out.println("Erreur Connexion");
			}
			
			try {
				ResultSet resultset=connexion.execute("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_SCHEMA='projet_autoconcept'");
				afficherDonnees(resultset);
				System.out.println("\n--------------------------------------------------------------------------------");
				System.out.println("Veuillez entrer le nom de la table dans laquelle vous voulez inserer les données");
				System.out.println("--------------------------------------------------------------------------------");
				tableChoisie=scan.nextLine();

				//ResultSetMetaData rsmd = resultset.getMetaData();
//				int nbCols = rsmd.getColumnCount();
//				boolean contientDAutresDonnees = resultset.next();
//				while (contientDAutresDonnees) {
//					for (int i = 1; i <= nbCols; i++){
//						String comparateur= resultset.getString(i);
//						if (comparateur == tableChoisie){
//							
//						}
//					}
//					System.out.println();
//					contientDAutresDonnees = resultset.next();
//				}
				connexion.execute("LOAD DATA INFILE '"+chemin+"' INTO TABLE "+tableChoisie+" FIELDS TERMINATED BY ';' LINES TERMINATED BY '\n' IGNORE 1 LINES");

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Erreur d'execution de la requete");
			}
			

			
			
			System.out.println("Mise à jour terminée");
			
		}
		
		
		
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

			resultats.close();
		}
	
		
	
}