package villagegaulois;

import personnages.Gaulois;

public class Etal {
	private Gaulois vendeur;
	private String produit;
	private int quantiteDebutMarche;
	private int quantite;
	private boolean etalOccupe = false;

	public boolean isEtalOccupe() {
		return etalOccupe;
	}

	public Gaulois getVendeur() {
		return vendeur;
	}

	public void occuperEtal(Gaulois vendeur, String produit, int quantite) {
		this.vendeur = vendeur;
		this.produit = produit;
		this.quantite = quantite;
		quantiteDebutMarche = quantite;
		etalOccupe = true;
	}

	public String libererEtal() {
			StringBuilder chaine = new StringBuilder();
		try {
			etalOccupe = false;
			System.out.println("Le vendeur " + vendeur.getNom() + " quitte son �tal, ");
			int produitVendu = quantiteDebutMarche - quantite;
			if (produitVendu > 0) {
				chaine.append(
						"il a vendu " + produitVendu + " parmi " + produit + ".\n");
			} else {
				chaine.append("il n'a malheureusement rien vendu.\n");
			}
		
		}
		catch(NullPointerException e) {
			System.out.println("L'etal est d�j� vide");
		}
			return chaine.toString();
	}

	public String afficherEtal() {
		if (etalOccupe) {
			return "L'�tal de " + vendeur.getNom() + " est garni de " + quantite
					+ " " + produit + "\n";
		}
		return "L'�tal est libre";
	}

	public String acheterProduit(int quantiteAcheter, Gaulois acheteur) 
			throws NullPointerException, IllegalArgumentException, IllegalStateException{
	StringBuilder chaine = new StringBuilder();
	try {
		chaine.append(acheteur.getNom() + " veut acheter " + quantiteAcheter
			+ " " + produit + " � " + vendeur.getNom());
	} catch (NullPointerException e) {
		e.printStackTrace();
		return "";
	}
	if (quantiteAcheter < 1) {
		throw new IllegalArgumentException("la quantit� est n�gative");
	}
	if (!etalOccupe) {
		throw new IllegalStateException("L'�tal est inoccup�");
	}
	if (quantite == 0) {
		chaine.append(", malheureusement il n'y en a plus !");
		quantiteAcheter = 0;
	}
	if (quantiteAcheter > quantite) {
		chaine.append(", comme il n'y en a plus que " + quantite + ", "
				+ acheteur.getNom() + " vide l'�tal de "
				+ vendeur.getNom() + ".\n");
		quantiteAcheter = quantite;
		quantite = 0;
	}
	if (quantite != 0) {
		quantite -= quantiteAcheter;
		chaine.append(". " + acheteur.getNom()
				+ ", est ravi de tout trouver sur l'�tal de "
				+ vendeur.getNom() + "\n");
	}
	return chaine.toString();
}

	public boolean contientProduit(String produit) {
		return this.produit.equals(produit);
	}

}
