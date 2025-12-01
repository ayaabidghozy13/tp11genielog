package FFSSM;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import lombok.Getter;

@Getter
public class Plongeur extends Personne {

    // Association *licencesDelivrees* (1..*)
    private final List<Licence> licencesDelivrees = new LinkedList<>();
    private final GroupeSanguin groupe;
    private int niveau;

    public Plongeur(String numeroINSEE, String nom, String prenom, String adresse, String telephone, LocalDate naissance, GroupeSanguin groupe, int niveau) {
        super(numeroINSEE, nom, prenom, adresse, telephone, naissance);
        this.groupe = groupe;
        this.niveau = niveau;
    }

    /**
     * Ajoute une licence à ce plongeur.
     * @param numero Le numéro de la licence
     * @param delivrance La date de délivrance
     * @param club Le club émetteur
     * @return La nouvelle Licence
     */
    public Licence ajouteLicence(String numero, LocalDate delivrance, Club club) {
        Licence nouvelleLicence = new Licence(this, numero, delivrance, club);
        licencesDelivrees.add(nouvelleLicence);
        return nouvelleLicence;
    }

    /**
     * Retourne la dernière licence (la plus récente) du plongeur.
     * @return La dernière Licence ou null si aucune licence
     */
    public Licence derniereLicence() {
        if (licencesDelivrees.isEmpty()) {
            return null;
        }
        // La dernière ajoutée est la plus récente
        return licencesDelivrees.get(licencesDelivrees.size() - 1);
    }
}