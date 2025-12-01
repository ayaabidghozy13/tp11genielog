/**
 * @(#) Plongee.java
 */
package FFSSM;

import java.time.LocalDate;

public class Plongee {

	public Site lieu;

	public DiplomeDeMoniteur chefDePalanquee;

	public LocalDate date;

	public int profondeur;

	public int duree;

	public Plongee(Site lieu, DiplomeDeMoniteur chefDePalanquee, LocalDate date, int profondeur, int duree) {
		this.lieu = lieu;
		this.chefDePalanquee = chefDePalanquee;
		this.date = date;
		this.profondeur = profondeur;
		this.duree = duree;
	}

	public void ajouteParticipant(Plongeur participant) {
		// TODO: Implémenter cette méthode
		throw new UnsupportedOperationException("Pas encore implémenté");
	}

	/**
	 * Détermine si la plongée est conforme. 
	 * Une plongée est conforme si tous les plongeurs de la palanquée ont une
	 * licence valide à la date de la plongée
	 * @return vrai si la plongée est conforme
	 */
	public boolean estConforme() {
		// TODO: Implémenter cette méthode
		throw new UnsupportedOperationException("Pas encore implémenté");
	}

}
