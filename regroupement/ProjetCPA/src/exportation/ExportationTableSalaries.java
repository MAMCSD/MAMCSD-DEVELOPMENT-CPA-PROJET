package exportation;

import java.sql.ResultSet;
import java.sql.SQLException;

import main.ConnectionMySQL;

/**
 * 
 * @author St�phane
 * Cette classe permet de g�rer l'exportation de la table des salari�s � l'aide d'une commande sql.
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

		ConnectionMySQL connecteur = new ConnectionMySQL("127.0.0.1", "root"); // cr�ation de la connexion avec la base de donn�e
		try {
			connecteur.connect();


			// le select nous permet de faire l'exportation des informations contenues dans la table des salari�s et cr�e un fichier csv localis� en  C:\xampp\mysql\data\projet_autoconcept
			ResultSet resultSet = connecteur.execute("SELECT * INTO OUTFILE 'salaries.csv' FIELDS terminated BY ';' LINES terminated BY '\r\n' FROM (SELECT 'ID_Salarie','Nom','Prenom','Date_de_naissance','Sexe','Anciennete','Nom_de_service','Fonction','Identifiant' UNION SELECT * FROM salaries) tmp");


			//Bonne pratique: fermer votre r�sultat
			resultSet.close();

			//Bonne pratique: fermer le connecteur
			connecteur.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}     
	}

}
