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
		System.out.println("1- Importer � partir d'un fichier Excel (format CSV)");
		System.out.println("2- Importer � partir du module d'exportation des donn�es");
	}

	public void menuExportation()
	{
		System.out.println("Que souhaitez vous faire :");
		System.out.println("1- Exporter/sauvegarder compl�tement la base de donn�e");
		System.out.println("2- Exporter/sauvegarder uniquement une partie de la base de donn�e");
	}

	public void menuGestion()
	{
		System.out.println("Que souhaitez vous faire :");
		System.out.println("1- G�rer les contacts ");
		System.out.println("2- G�rer les donn�es des utilisateurs");
		System.out.println("3- G�rer les factures ");
		System.out.println("4- G�rer les commandes");
		System.out.println("5- G�rer les stocks ");
		System.out.println("6- G�rer les clients");
		System.out.println("7- G�rer les fournisseurs ");
		System.out.println("8- G�rer les litiges");
		System.out.println("9- G�rer les salari�s ");
		System.out.println("10- G�rer les documentations");


	}
}
