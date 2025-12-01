package FFSSM;

import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class Club {

    @Getter @Setter
    public DiplomeDeMoniteur president;

    @Getter @Setter
    public String nom;

    @Getter @Setter
    public String adresse;

    @Getter @Setter
    public String telephone;

    // Association *activites* vers Plongee
    private final List<Plongee> activites = new ArrayList<>();

    public Club(DiplomeDeMoniteur president, String nom) {
        this.president = president;
        this.nom = nom;
    }

    /**
     * Calcule l'ensemble des plongées non conformes organisées par ce club.
     * @return l'ensemble des plongées non conformes
     */
    public Set<Plongee> plongeesNonConformes() {
        Set<Plongee> nonConformes = new HashSet<>();
        for (Plongee p : activites) {
            if (!p.estConforme()) {
                nonConformes.add(p);
            }
        }
        return nonConformes;
    }

    /**
     * Enregistre une nouvelle plongée organisée par ce club
     * @param p la nouvelle plongée
     */
    public void organisePlongee(Plongee p) {
        activites.add(p);
    }
    
    public List<Plongee> getActivites() {
        return activites;
    }


    @Override
    public String toString() {
        return "Club{" + "président=" + president.getPossesseur().getPrenom() + ", nom=" + nom + ", adresse=" + adresse + ", telephone=" + telephone + '}';
    }
}

