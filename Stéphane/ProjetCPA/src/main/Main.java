package main;

public class Main {
	public static void main(java.lang.String[] args) {

		boolean etatConnexion=false;
		Connexion connect =new Connexion();
		
		etatConnexion=connect.connexion();
		
		
		if ( etatConnexion == false)
		{
			System.out.println(" Vous vous êtes trompé d'identifiant ou de mot de passe ou vous ne faites pas parti de notre liste.");
			System.out.println(" Veuillez contacter une personne du service informatique pour plus de renseignements.");
			
		}

	}
}
