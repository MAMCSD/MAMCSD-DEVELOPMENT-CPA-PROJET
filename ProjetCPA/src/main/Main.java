package main;

import authentification.Connexion;
import authentification.FenetreConnexion;
import authentification.Recuperation;

public class Main {
	public static void main(java.lang.String[] args) {

		boolean etatConnexion=false;
		boolean admin = false;
		
		//Connexion connect =new Connexion();
		FenetreConnexion fen = new FenetreConnexion();
		
		Recuperation recup= new Recuperation();
		
		Menu menu=new Menu();
		
		
		//etatConnexion=connect.connexion();
		etatConnexion=fen.fenetreConnexion();
		
		
		admin = recup.droitUtilisateur();


		if ( etatConnexion == true)
		{

			if (admin == true)
			{
				menu.menuAdmin();
			}
			else
			{
				menu.menuUser();
			}
		}
		else
		{
			System.out.println(" Vous vous êtes trompé d'identifiant ou de mot de passe ou vous ne faites pas parti de notre liste.");
			System.out.println(" Veuillez contacter une personne du service informatique pour plus de renseignements.");

		}

	}
}
