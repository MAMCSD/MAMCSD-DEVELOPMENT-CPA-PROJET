package authentification;

import java.util.Scanner;
import main.ConnectionMySQL;
import java.sql.ResultSet;
import java.sql.SQLException; 

/**
 * 
 * @author St�phane
 *	Cette classe permet de mettre en place la connexion de l'utilisateur en mode console
 */

public class Connexion {

	private Scanner scanner = new Scanner(System.in);


	Recuperation atp = new Recuperation();
	public boolean connexion() 
	{
		/**
		 * Cette m�thode permet de cr�er la connexion de l'utilisateur.
		 * C'est dans celle-ci que nous comparons les logins choisis par l'utilisateur avec les logins pr�sents dans la base de donn�es.
		 *
		 *par exemple pour un utilisateur normal on peut utiliser les logins suivants : id ="HJP" et mdp = " 16H11JP75"
		 *et pour un admin voici les logins : id = "FA" et mdp = " 27F02A84"
		 *
		 */
		String id;
		String mdp;
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

				id=getValeur("entrer votre identifiant :");
				mdp=getValeur("entrer votre mot de passe  :");


				// le select nous permet re r�cuperer les informations du salari�
				ResultSet resultSet = connecteur.execute("Select salaries.Nom,salaries.Prenom,salaries.Fonction from salaries inner JOIN authentification ON salaries.Identifiant= authentification.Identifiant where salaries.Identifiant=\""+id+"\" AND authentification.Mot_de_passe=\""+mdp+"\"");


				etatConnexion=atp.verificationLogin(resultSet);

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
	}

	public boolean retourDroitUtilisateur()
	{
		/**
		 * Cette m�thode permet de retourner les droits de l'utilisateur qui vient de se connecter.
		 */
		boolean admin= false;
		
		admin = atp.droitUtilisateur();
		return admin;
		
	}

	public String getValeur(String message){
		/**
		 * Cette m�thode permet de retourner une valeur (String)  entr�e par l'utilisateur au clavier.
		 */
		System.out.println(message);
		return  scanner.next();
	} 
	public int getChoix(String message)
	{
		/**
		 * Cette m�thode permet de retourner le choix de l'utilisateur lors de sa pr�sence dans les menus.
		 */
		System.out.println(message);
		return  scanner.nextInt();		
	}
}
