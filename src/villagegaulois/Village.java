package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marcheVillage;
	
	
	public Village(String nom, int nbVillageoisMaximum,int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marcheVillage = new Marche(nbEtals);
	}

	private static class Marche {
		private Etal[] etals;

		private Marche(int nbEtals) {
			this.etals = new Etal[nbEtals];	
			for(int i=0;i<nbEtals;i++) {
				Etal e=new Etal();
				this.etals[i]=e;	
			}

		}

		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			this.etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}

		private int trouverEtalLibre() {
			int libre= -1;
			for (int i = 0; i < etals.length; i++) {
				if (!(etals[i].isEtalOccupe())&&libre==-1) {
					libre =i;
				}
			}
			return libre;
		}

		private Etal[] trouverEtals(String produit) {
			int nbEtalsLibres = 0;
			for (int i = 0 ; i < etals.length ; i++) {
				if (etals[i] != null && etals[i].isEtalOccupe() && etals[i].contientProduit(produit)) {
					nbEtalsLibres++;
				}
			}
			Etal[] etalsLibres = new Etal[nbEtalsLibres];
			int j = 0;
			for (int i = 0 ; i < etals.length ; i++) {
				if (etals[i] != null && etals[i].isEtalOccupe() && etals[i].contientProduit(produit)) {
					etalsLibres[j++] = etals[i];
				}
			}
			return etalsLibres;
		}

		private Etal trouverVendeur(Gaulois gaulois) {
			int indiceVendeur = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].getVendeur().getNom().equals(gaulois.getNom())) {
					indiceVendeur = i;
				}
			}
			return etals[indiceVendeur];
		}

		private String afficherMarche() {
			int nbEtalsVide = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe()) {
					etals[i].afficherEtal();
				}
			}
			for (int i = 0; i < etals.length; i++) {
				if (!(etals[i].isEtalOccupe())) {
					nbEtalsVide++;
				}
			}
			return "Il reste " + nbEtalsVide + " étals non utilisés dans le marché.\n";
		}
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef " + chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom() + " vivent les lÃ©gendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	public String installerVendeur(Gaulois vendeur, String produit,int nbProduit) {
		System.out.println(vendeur.getNom()+" cherche un endroit pour vendre "+ nbProduit +" "+ produit);
		int indiceEtalLibre=marcheVillage.trouverEtalLibre();
		marcheVillage.utiliserEtal(indiceEtalLibre, vendeur, produit,nbProduit);
		return ("Le vendeur "+vendeur.getNom()+" vends des "+ produit+" a l'etal n°"+ indiceEtalLibre +"\n");
	}
	
	public String rechercherVendeursProduit(String produit) {
		StringBuilder chaine = new StringBuilder();
		chaine.append("Les vendeurs qui proposent des " + produit + " sont :\n");
		Etal[] bonEtals = this.marcheVillage.trouverEtals(produit);
		for (Etal etal : bonEtals)
			chaine.append(" - " + etal.getVendeur().getNom() + "\n");
		return chaine.toString();
	}
	
	public Etal rechercherEtal(Gaulois vendeur) {
		for (Etal etal : marcheVillage.etals) {
			if (etal.getVendeur().getNom().equals(vendeur.getNom()))
				return etal;
		}
		return null;
	}
	
	public String partirVendeur(Gaulois vendeur) {
		Etal etalNul = rechercherEtal(vendeur);
		return etalNul.libererEtal();
	}
	
	public String afficherMarche() {
		return marcheVillage.afficherMarche();
	}
}