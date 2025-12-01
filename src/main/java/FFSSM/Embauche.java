package FFSSM;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Embauche {

    private final LocalDate debut;
    private LocalDate fin;

    // Ajout des attributs d'association pour Club et DiplomeDeMoniteur
    private final DiplomeDeMoniteur employe;
    private final Club employeur;

    public Embauche(LocalDate debut, DiplomeDeMoniteur employe, Club employeur) {
        this.debut = debut;
        this.employe = employe;
        this.employeur = employeur;
    }

    /**
     * Termine l'embauche à la date spécifiée.
     * @param dateFin La date de fin de l'embauche
     */
    public void terminer(LocalDate dateFin) {
        this.fin = dateFin;
    }

    /**
     * Vérifie si l'embauche est terminée.
     * @return vrai si la date de fin est renseignée
     */
    public boolean estTerminee() {
        return fin != null;
    }
}