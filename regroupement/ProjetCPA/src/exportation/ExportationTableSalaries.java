package exportation;

import java.sql.ResultSet;
import java.sql.SQLException;

import main.ConnectionMySQL;

/**
 * 
 * @author Stéphane
 * Cette classe permet de gérer l'exportation de la table des salariés à l'aide d'une commande sql.
 * 
 */
public class ExportationTableSalaries {
	
	public void exportationListeSalaries()
	{
		try {
			ConnectionMySQL.init();			
		} catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
			System.err.println("La librairie n'est pas disponible");
			System.exit(5);
		}

		ConnectionMySQL connecteur = new ConnectionMySQL("127.0.0.1", "root"); // création de la connexion avec la base de donnée
		try {
			connecteur.connect();


			// le select nous permet de faire l'exportation des informations contenues dans la table des salariés et crée un fichier csv localisé en  C:\xampp\mysql\data\projet_autoconcept
			ResultSet resultSet = connecteur.execute("SELECT * INTO OUTFILE 'salaries.csv' FIELDS terminated BY ';' LINES terminated BY '\r\n' FROM (SELECT 'ID_Salarie','Nom','Prenom','Date_de_naissance','Sexe','Anciennete','Nom_de_service','Fonction','Identifiant' UNION SELECT * FROM salaries) tmp");


			//Bonne pratique: fermer votre résultat
			resultSet.close();

			//Bonne pratique: fermer le connecteur
			connecteur.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}     
	}

}
