package main;

import java.util.Scanner;

/**
 * 
 * @author Stéphane.
 * 
 *	Dans cette classe nous allons gérer l'ensemble des actions qui auront lieu suite au choix des utilisateurs par rapport aux menus.
 *  Un ensemble de constantes sera défini afin de gérer tous les choix possibles.
 */

public class Action {

	private Scanner scanner = new Scanner(System.in);

	public static final int CHOIX_IMPORTER_ADMIN= 1;
	public static final int CHOIX_EXPORTER_ADMIN= 2;
	public static final int CHOIX_GESTION_ADMIN= 3;
	public static final int CHOIX_MODIF_INFOS_ADMIN= 4;
	public static final int CHOIX_CHANGER_UTILISATEUR_ADMIN= 5;
	public static final int CHOIX_QUITTER_PROG_ADMIN= 6;

	public static final int CHOIX_IMPORTER_USER= 1;
	public static final int CHOIX_EXPORTER_USER= 2;
	public static final int CHOIX_GESTION_USER= 3;
	public static final int CHOIX_CHANGER_UTILISATEUR_USER= 4;
	public static final int CHOIX_QUITTER_PROG_USER= 5;
	
	public static final int CHOIX_IMPORTER_FICHIER_EXCEL= 1;
	public static final int CHOIX_IMPORTER_DEPUIS_MODULE_EXPORT= 2;
	public static final int CHOIX_RETOUR_MENU_PRINCIPAL_DEPUIS_IMPORT= 3;
	
	public static final int CHOIX_EXPORTER_BASE_COMPLETE= 1;
	public static final int CHOIX_EXPORTER_PARTIE_BASE= 2;
	public static final int CHOIX_RETOUR_MENU_PRINCIPAL_DEPUIS_EXPORT= 3;
	
	public static final int CHOIX_GERER_CONTACTS= 1;
	public static final int CHOIX_GERER_SALARIES= 2;
	public static final int CHOIX_GERER_FACTURES= 3;
	public static final int CHOIX_GERER_COMMANDES= 4;
	public static final int CHOIX_GERER_STOCKS= 5;
	public static final int CHOIX_GERER_CLIENTS= 6;
	public static final int CHOIX_GERER_FOURNISSEURS= 7;
	public static final int CHOIX_GERER_LITIGES= 8;
	public static final int CHOIX_GERER_DOCUMENTATIONS= 9;
	public static final int CHOIX_RETOUR_MENU_PRINCIPAL_DEPUIS_GESTION= 10;
	

	
	public boolean actionAdmin(int choix)
	{
		/**
		 * Cette méthode correspond aux actions pouvant être faite par l'admin.
		 * Il pourra par exemple importer ou exporter des données , modifier des données, changer d'utilisateur ou encore quitter le programme.
		 * 
		 */
		Menu menuSecondaireAdmin = new Menu();
		int choixMenuSecondaireAdmin=0;
		boolean loginsUtilisateur=true;

		switch(choix)
		{
			case CHOIX_IMPORTER_ADMIN:
			{
				// le 3 correspond au retour au menu principal
				while (choixMenuSecondaireAdmin!=3)
				{
					menuSecondaireAdmin.menuImportation();
					choixMenuSecondaireAdmin=getChoix(" \n Entrer votre choix: ");
					actionMenuImportation(choixMenuSecondaireAdmin);
				}
			}
			break;	
			case CHOIX_EXPORTER_ADMIN:
			{
				// le 3 correspond au retour au menu principal
				while(choixMenuSecondaireAdmin!=3)
				{
					menuSecondaireAdmin.menuExportation();
					choixMenuSecondaireAdmin=getChoix(" \n Entrer votre choix: ");
					actionMenuExportation(choixMenuSecondaireAdmin);
				}
			}
			break;
			case CHOIX_GESTION_ADMIN:
			{
				// le 10 correspond au retour au menu principal
				while (choixMenuSecondaireAdmin!=10)
				{
					menuSecondaireAdmin.menuGestion();
					choixMenuSecondaireAdmin=getChoix(" \n Entrer votre choix: ");
					actionMenuGestion(choixMenuSecondaireAdmin);
				}
			}
			break;
			case CHOIX_MODIF_INFOS_ADMIN:
			{
				System.out.println(" \n Modification en cours");
			}
			break;
			case CHOIX_CHANGER_UTILISATEUR_ADMIN:
			{
				System.out.println(" \n Changement d'utilisateur \n");
				loginsUtilisateur=false;
			}
			break;
			case CHOIX_QUITTER_PROG_ADMIN:
			{
				System.out.println(" \n Vous venez de quitter le programme \n");
				System.exit(0);
			}
			break;
			default:
			{
				System.out.println(" \n Choix impossible \n");
			}

		}
		
		return loginsUtilisateur;
	}

	public boolean actionUser(int choix)
	{
		/**
		 * Cette méthode correspond aux actions pouvant être faite par les utilisateurs .
		 * Ils pourront par exemple importer ou exporter des données , changer d'utilisateur ou encore quitter le programme.
		 * 
		 */
		Menu menuSecondaireUser = new Menu();
		int choixMenuSecondaireUser=0;
		boolean  loginsUtilisateur=true;
		switch(choix)
		{
			case CHOIX_IMPORTER_USER:
			{
				// le 3 correspond au retour au menu principal
				while(choixMenuSecondaireUser!=3)
				{
					menuSecondaireUser.menuImportation();
					choixMenuSecondaireUser=getChoix(" \n Entrer votre choix: ");
					actionMenuImportation(choixMenuSecondaireUser);
				}
	
			}
			break;	
			case CHOIX_EXPORTER_USER:
			{
				// le 3 correspond au retour au menu principal
				while(choixMenuSecondaireUser!=3)
				{
					menuSecondaireUser.menuExportation();
					choixMenuSecondaireUser=getChoix(" \n Entrer votre choix: ");
					actionMenuExportation(choixMenuSecondaireUser);
				}
	
			}
			break;
			case CHOIX_GESTION_USER:
			{
				// le 10 correspond au retour au menu principal
				while(choixMenuSecondaireUser!=10)
				{
					menuSecondaireUser.menuGestion();
					choixMenuSecondaireUser=getChoix(" \n Entrer votre choix: ");
					actionMenuGestion(choixMenuSecondaireUser);
				}
	
			}
			break;
			case CHOIX_CHANGER_UTILISATEUR_USER:
			{
				System.out.println(" \n Changement d'utilisateur \n");
				loginsUtilisateur=false;
			}
			break;
			case CHOIX_QUITTER_PROG_USER:
			{
				System.out.println(" \n Vous venez de quitter le programme \n");
				System.exit(0);
			}
			break;
			default:
			{
				System.out.println(" \n Choix impossible \n");
			}

		}
		
		return loginsUtilisateur;
	}
	
	public void actionMenuImportation(int choix)
	{
		/**
		 * Cette méthode correspond aux actions pouvant être faite par les utilisateurs lorsqu'ils ont choisi le mode d'importation.
		 * Ils pourront par exemple importer un fichier Excel, importer depuis le module d'exportation ou encore retourner au menu principal.
		 * 
		 */
		switch(choix)
		{	
			case CHOIX_IMPORTER_FICHIER_EXCEL:
			{

				System.out.println(" \n Importation depuis un fichier Excel \n");

			}
			break;
			case CHOIX_IMPORTER_DEPUIS_MODULE_EXPORT:
			{

				System.out.println(" \n Importation depuis module d'exportation \n");
			}
			break;
			case CHOIX_RETOUR_MENU_PRINCIPAL_DEPUIS_IMPORT:
			{

				System.out.println(" \n Retour au menu principal \n");
			}
			break;
			default:
			{

				System.out.println(" \n Choix impossible \n");
			}
		}
	}

	public void actionMenuExportation(int choix)
	{
		/**
		 * Cette méthode correspond aux actions pouvant être faite par les utilisateurs lorsqu'ils ont choisi le mode d'exportation.
		 * Ils pourront par exemple exporter entièrement la base de données, exporter une partie de la base de données ou encore retourner au menu principal.
		 * 
		 */
		switch(choix)
		{	
			case CHOIX_EXPORTER_BASE_COMPLETE:
			{
				System.out.println(" \n Exportation de la base complète \n");

			}
			break;
			case CHOIX_EXPORTER_PARTIE_BASE:
			{
				System.out.println(" \n Exportation d'une partie de la base \n");
			}
			break;
			case CHOIX_RETOUR_MENU_PRINCIPAL_DEPUIS_EXPORT:
			{
				System.out.println(" \n Retour au menu principal \n");
			}
			break;
			default:
			{
				System.out.println(" \n Choix impossible \n");
			}
		}
	}
	
	public void actionMenuGestion(int choix)
	{
		/**
  		 * Cette méthode correspond aux actions pouvant être faite par les utilisateurs lorsqu'ils ont choisi le mode de gestion.
		 * Ils pourront effectuer plusieurs actions sur les diverses tables de la base de données  ou encore retourner au menu principal.
		 * 
		 */
		switch(choix)
		{
			case  CHOIX_GERER_CONTACTS:
			{
				System.out.println(" \n Gestion des contacts \n");
			}
			break;
			case CHOIX_GERER_SALARIES:
			{
				System.out.println(" \n Gestion des salariés \n");
			}
			break;
			case CHOIX_GERER_FACTURES:
			{
				System.out.println(" \n Gestion des factures \n");
			}
			break;
			case  CHOIX_GERER_COMMANDES:
			{
				System.out.println(" \n Gestion des commandes \n");
			}
			break;
			case  CHOIX_GERER_STOCKS:
			{
				System.out.println(" \n Gestion des stocks \n");
			}
			break;
			case CHOIX_GERER_CLIENTS:
			{
				System.out.println(" \n Gestion des clients \n");
			}
			break;
			case CHOIX_GERER_FOURNISSEURS:
			{
				System.out.println(" \n Gestion des fournisseurs \n");
			}
			break;
			case CHOIX_GERER_LITIGES:
			{
				System.out.println(" \n Gestion des litiges \n");
			}
			break;
			case  CHOIX_GERER_DOCUMENTATIONS:		
			{
				System.out.println(" \n Gestion des documentations \n");
			}
			break;
			case  CHOIX_RETOUR_MENU_PRINCIPAL_DEPUIS_GESTION:
			{
				System.out.println(" \n Retour au menu principal \n");
			}
			break;
		
			default:
			{
				System.out.println(" \n Choix impossible \n");
			}
		}
	}
	public int getChoix(String message)
	{	
		/**
		 * Cette méthode permet de retourner le choix de l'utilisateur lors de sa présence dans les menus.
		 */
		System.out.println(message);
		return  scanner.nextInt();		
	}
}
