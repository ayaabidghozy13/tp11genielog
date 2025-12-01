package FFSSM;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import lombok.Getter;

@Getter
public class Plongee {

    private final Site lieu;
    private final DiplomeDeMoniteur chefDePalanquee;
    private final LocalDate date;
    private final int profondeur;
    private final int duree;

    // Association *participants* vers Plongeur (palanquée) (1..*)
    private final Set<Plongeur> participants = new HashSet<>();

    public Plongee(Site lieu, DiplomeDeMoniteur chefDePalanquee, LocalDate date, int profondeur, int duree) {
        this.lieu = lieu;
        this.chefDePalanquee = chefDePalanquee;
        this.date = date;
        this.profondeur = profondeur;
        this.duree = duree;
    }

    public void ajouteParticipant(Plongeur participant) {
        participants.add(participant);
    }

    /**
     * Détermine si la plongée est conforme.
     * Une plongée est conforme si tous les plongeurs de la palanquée (y compris le chef)
     * ont une licence valide à la date de la plongée
     * @return vrai si la plongée est conforme
     */
    public boolean estConforme() {
        Plongeur chef = chefDePalanquee.getPossesseur();
        Licence licenceChef = chef.derniereLicence();

        if (licenceChef == null || !licenceChef.estValide(this.date)) {
            return false;
        }

        for (Plongeur p : participants) {
            Licence licenceParticipant = p.derniereLicence();
            if (licenceParticipant == null || !licenceParticipant.estValide(this.date)) {
                return false;
            }
        }

        return true;
    }
}