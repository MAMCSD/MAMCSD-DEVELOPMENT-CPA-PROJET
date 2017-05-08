package authentification;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import main.ConnectionMySQL;

public class FenetreConnexion {

	Recuperation atp = new Recuperation();
	public boolean fenetreConnexion(){

		int demandeConnexion =0;
		boolean etatConnexion=false;

		//  exemple id="HJP"  mdp="16H11JP75" sont correct et il est un utilisateur normal;
		//  exemple id="FA"  mdp="27F02A84" sont correct et il est un admin;

		while (demandeConnexion !=3 && etatConnexion!=true)
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

				JOptionPane jop = new JOptionPane(), jop2 = new JOptionPane();
				String id = jop.showInputDialog(null, "Entrer votre identifiant !", "Identifiant", JOptionPane.QUESTION_MESSAGE);
				String mdp = jop.showInputDialog(null, "Entrer votre mot de passe !", "Mot de passe !", JOptionPane.QUESTION_MESSAGE);


				// le select nous permet re r�cuperer les informations du salari�
				ResultSet resultSet = connecteur.execute("Select salaries.Nom,salaries.Prenom,salaries.Fonction  from salaries inner JOIN authentification ON salaries.Identifiant= authentification.Identifiant where salaries.Identifiant=\""+id+"\" AND authentification.Mot_de_passe=\""+mdp+"\"");


				etatConnexion=atp.verificationLogin(resultSet);

				if (etatConnexion== true)
				{
					jop2.showMessageDialog(null, "Vous �tes connect�", "Authentification", JOptionPane.INFORMATION_MESSAGE);
				}
				else
				{
					jop2.showMessageDialog(null, "Votre identifiant ou votre mot de passe est incorect ", "Authentification", JOptionPane.ERROR_MESSAGE);
				}

				//Bonne pratique: fermer votre r�sultat
				resultSet.close();

				//Bonne pratique: fermer le connecteur
				connecteur.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			demandeConnexion++;

		}
		return etatConnexion;
		//jop2.showMessageDialog(null, "Votre nom est " + identifiant, "Identit�", JOptionPane.INFORMATION_MESSAGE);
	}

	public boolean retourDroitUtilisateur()
	{
		boolean admin= false;

		admin = atp.droitUtilisateur();
		return admin;

	}

}
