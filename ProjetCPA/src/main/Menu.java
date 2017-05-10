package main;

/**
 * 
 * @author St�phane
 * Dans cette classe il sera pr�sent l'ensemble des menus qui seront utilis� dans le programme.
 * Il y aura donc : menuAdmin, menuUser, menuImportation, menuExportation, menuGestion.
 *
 */


public class Menu {

	public void menuAdmin()
	{
		System.out.println("Que souhaitez vous faire :");
		System.out.println("1- Importer des informations");
		System.out.println("2- Exporter des informations");
		System.out.println("3- G�rer la base de donn�e");
		System.out.println("4- Modifier des informations");
		System.out.println("5- Changer d'utilisateur");
		System.out.println("6- Quitter le programme");

	}
	
	public void menuUser()
	{
		System.out.println("Que souhaitez vous faire :");
		System.out.println("1- Importer des informations");
		System.out.println("2- Exporter des informations");
		System.out.println("3- G�rer la base de donn�e");
		System.out.println("4- Changer d'utilisateur");
		System.out.println("5- Quitter le programme");
	}


	public void menuImportation()
	{
		System.out.println("Que souhaitez vous faire :");
		System.out.println("1- Importer � partir d'un fichier Excel (format CSV)");
		System.out.println("2- Importer � partir du module d'exportation des donn�es");
		System.out.println("3- Retourner au menu principal");
	}

	public void menuExportation()
	{
		System.out.println("Que souhaitez vous faire :");
		System.out.println("1- Exporter/sauvegarder compl�tement la base de donn�e");
		System.out.println("2- Exporter/sauvegarder uniquement une partie de la base de donn�e");
		System.out.println("3- Retourner au menu principal");
	}

	public void menuExportationPartieBdd()
	{
		System.out.println("Que souhaitez vous faire :");
		System.out.println("1- Exporter/sauvegarder la table des factures");
		System.out.println("2- Exporter/sauvegarder la table des salari�s");
		System.out.println("3- Retourner au menu exportation");
	}
	
	public void menuGestion()
	{
		System.out.println("Que souhaitez vous faire :");
		System.out.println("1- G�rer les contacts ");
		System.out.println("2- G�rer les salari�s ");
		System.out.println("3- G�rer les factures ");
		System.out.println("4- G�rer les commandes");
		System.out.println("5- G�rer les stocks ");
		System.out.println("6- G�rer les clients");
		System.out.println("7- G�rer les fournisseurs ");
		System.out.println("8- G�rer les litiges");
		System.out.println("9- G�rer les documentations");
		System.out.println("10- Retourner au menu principal");

	}
}
