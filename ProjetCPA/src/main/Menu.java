package main;

/**
 * 
 * @author Stéphane
 * Dans cette classe il sera présent l'ensemble des menus qui seront utilisé dans le programme.
 * Il y aura donc : menuAdmin, menuUser, menuImportation, menuExportation, menuGestion.
 *
 */


public class Menu {

	public void menuAdmin()
	{
		System.out.println("Que souhaitez vous faire :");
		System.out.println("1- Importer des informations");
		System.out.println("2- Exporter des informations");
		System.out.println("3- Gérer la base de donnée");
		System.out.println("4- Modifier des informations");
		System.out.println("5- Changer d'utilisateur");
		System.out.println("6- Quitter le programme");

	}
	
	public void menuUser()
	{
		System.out.println("Que souhaitez vous faire :");
		System.out.println("1- Importer des informations");
		System.out.println("2- Exporter des informations");
		System.out.println("3- Gérer la base de donnée");
		System.out.println("4- Changer d'utilisateur");
		System.out.println("5- Quitter le programme");
	}


	public void menuImportation()
	{
		System.out.println("Que souhaitez vous faire :");
		System.out.println("1- Importer à partir d'un fichier Excel (format CSV)");
		System.out.println("2- Importer à partir du module d'exportation des données");
		System.out.println("3- Retourner au menu principal");
	}

	public void menuExportation()
	{
		System.out.println("Que souhaitez vous faire :");
		System.out.println("1- Exporter/sauvegarder complètement la base de donnée");
		System.out.println("2- Exporter/sauvegarder uniquement une partie de la base de donnée");
		System.out.println("3- Retourner au menu principal");
	}

	public void menuExportationPartieBdd()
	{
		System.out.println("Que souhaitez vous faire :");
		System.out.println("1- Exporter/sauvegarder la table des factures");
		System.out.println("2- Exporter/sauvegarder la table des salariés");
		System.out.println("3- Retourner au menu exportation");
	}
	
	public void menuGestion()
	{
		System.out.println("Que souhaitez vous faire :");
		System.out.println("1- Gérer les contacts ");
		System.out.println("2- Gérer les salariés ");
		System.out.println("3- Gérer les factures ");
		System.out.println("4- Gérer les commandes");
		System.out.println("5- Gérer les stocks ");
		System.out.println("6- Gérer les clients");
		System.out.println("7- Gérer les fournisseurs ");
		System.out.println("8- Gérer les litiges");
		System.out.println("9- Gérer les documentations");
		System.out.println("10- Retourner au menu principal");

	}
}
