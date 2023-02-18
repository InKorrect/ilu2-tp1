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
			int contientProduits = 0;
			int nbEtalsProduits = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].contientProduit(produit)) {
					contientProduits++;
				}
			}
			Etal[] etalsPleins = new Etal[contientProduits];
			for (int i = 0; i < etalsPleins.length; i++) {
				if (etals[i].contientProduit(produit)) {
					etalsPleins[nbEtalsProduits] = etals[i];
					nbEtalsProduits++;
				}
			}
			return etalsPleins;
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
		Etal[] etalProduit=marcheVillage.trouverEtals(produit);
		System.out.println("Les vendeurs qui proposent des fleurs sont:\n");
		return("- "+etalProduit);
	}
}