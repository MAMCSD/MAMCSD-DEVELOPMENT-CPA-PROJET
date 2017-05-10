package main;

//import authentification.Connexion;
import authentification.FenetreConnexion;

/**
 * 
 * @author Stéphane
 *
 *Voici la classe main dans laquelle tout le programme sera géré.
 */

public class Main {


	public static void main(java.lang.String[] args) {

		boolean etatConnexion=false;
		boolean admin = false;
		boolean programmeActif=true;

		int choix=0;
		FenetreConnexion fenConnexion = new FenetreConnexion();
		Menu menu=new Menu();
		Action action= new Action();

		while(programmeActif==true)
		{

			boolean loginsUtilisateur=true;
			while (loginsUtilisateur==true)
			{
				//Connexion connect =new Connexion();
				fenConnexion = new FenetreConnexion();	
				menu=new Menu();
				action= new Action();
			
				//etatConnexion=connect.connexion();
				etatConnexion=fenConnexion.fenetreConnexion();


				if ( etatConnexion == true && loginsUtilisateur==true)
				{
					admin = fenConnexion.retourDroitUtilisateur();
					System.out.println();

					if (admin == true && loginsUtilisateur==true)
					{
						while (choix != 6 && loginsUtilisateur!=false)
						{
							menu.menuAdmin( );
							choix=fenConnexion.getChoix(" \n Entrer votre choix: ");
							// la variable programmeActif nous permet de nous re connecter.
							loginsUtilisateur=action.actionAdmin(choix);

						}

					}
					else
					{
						while (choix != 5 && loginsUtilisateur!=false)
						{
							menu.menuUser();
							choix=fenConnexion.getChoix(" \n Entrer votre choix: ");

							// la variable programmeActif nous permet de nous re connecter.
							loginsUtilisateur=action.actionUser(choix);
						}

					}
				}
				else
				{
					System.out.println(" Vous vous êtes trompé d'identifiant ou de mot de passe ou vous ne faites pas parti de notre liste.");
					System.out.println(" Veuillez contacter une personne du service informatique pour plus de renseignements.");

				}	
			}
		}
	}
}
