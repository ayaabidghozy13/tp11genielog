/**
 * @(#) Moniteur.java
 */
package FFSSM;

import java.time.LocalDate;
import java.util.List;

public class DiplomeDeMoniteur {

    private final int numeroDiplome;
    private final Plongeur possesseur;

    public DiplomeDeMoniteur(Plongeur possesseur, int numeroDiplome) {
        this.numeroDiplome = numeroDiplome;
        this.possesseur = possesseur;
    }

    /**
     * Si ce moniteur n'a pas d'embauche, ou si sa dernière embauche est terminée,
     * ce moniteur n'a pas d'employeur.
     * @return l'employeur actuel de ce moniteur ou null s'il n'en a pas
     */
    public Club employeurActuel() {
         // TODO: Implémenter cette méthode
        throw new UnsupportedOperationException("Pas encore implémenté");
    }
    
    /**
     * Enregistrer une nouvelle embauche pour cet employeur
     * @param employeur le club employeur
     * @param debutNouvelle la date de début de l'embauche
     */
    public void nouvelleEmbauche(Club employeur, LocalDate debutNouvelle) {   
         // TODO: Implémenter cette méthode
        throw new UnsupportedOperationException("Pas encore implémenté");	    
    }

    public List<Embauche> emplois() {
         // TODO: Implémenter cette méthode
        throw new UnsupportedOperationException("Pas encore implémenté");
    }

}
