package FFSSM;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class DiplomeDeMoniteur {

    private final int numeroDiplome;
    private final Plongeur possesseur;

    private final List<Embauche> emplois = new ArrayList<>();

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
        if (emplois.isEmpty()) {
            return null;
        }

        Embauche derniereEmbauche = emplois.get(emplois.size() - 1);

        if (derniereEmbauche.estTerminee()) {
            return null;
        } else {
            return derniereEmbauche.getEmployeur();
        }
    }

    /**
     * Enregistrer une nouvelle embauche pour cet employeur
     * @param employeur le club employeur
     * @param debutNouvelle la date de début de l'embauche
     */
    public void nouvelleEmbauche(Club employeur, LocalDate debutNouvelle) {
        Club employeurPrecedent = employeurActuel();
        if (employeurPrecedent != null) {
            Embauche derniereEmbauche = emplois.get(emplois.size() - 1);
            derniereEmbauche.terminer(debutNouvelle.minusDays(1));
        }

        Embauche nouvelle = new Embauche(debutNouvelle, this, employeur);
        emplois.add(nouvelle);
    }

    public List<Embauche> emplois() {
        return emplois;
    }



}
