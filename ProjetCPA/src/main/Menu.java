package main;

public class Menu {

	public void menuAdmin()
	{
		System.out.println("Que souhaitez vous faire :");
		System.out.println("1- Importer des informations");
		System.out.println("2- Exporter des informations");
		System.out.println("3- Modifier des informations");


	}
	public void menuUser()
	{
		System.out.println("Que souhaitez vous faire :");
		System.out.println("1- Importer des informations");
		System.out.println("2- Exporter des informations");
	}


	public void menuImportation()
	{
		System.out.println("Que souhaitez vous faire :");
		System.out.println("1- Importer à partir d'un fichier Excel (format CSV)");
		System.out.println("2- Importer à partir du module d'exportation des données");
	}

	public void menuExportation()
	{
		System.out.println("Que souhaitez vous faire :");
		System.out.println("1- Exporter/sauvegarder complètement la base de donnée");
		System.out.println("2- Exporter/sauvegarder uniquement une partie de la base de donnée");
	}

	public void menuGestion()
	{
		System.out.println("Que souhaitez vous faire :");
		System.out.println("1- Gérer les contacts ");
		System.out.println("2- Gérer les données des utilisateurs");
		System.out.println("3- Gérer les factures ");
		System.out.println("4- Gérer les commandes");
		System.out.println("5- Gérer les stocks ");
		System.out.println("6- Gérer les clients");
		System.out.println("7- Gérer les fournisseurs ");
		System.out.println("8- Gérer les litiges");
		System.out.println("9- Gérer les salariés ");
		System.out.println("10- Gérer les documentations");


	}
}
