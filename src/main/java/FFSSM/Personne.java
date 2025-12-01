package FFSSM;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class Personne {

    public String numeroINSEE;

    public String nom;

    public String prenom;

    public String adresse;

    public String telephone;

    public LocalDate naissance;

    // Constructeur complet (pour faciliter l'appel depuis Plongeur)
    public Personne(String numeroINSEE, String nom, String prenom, String adresse, String telephone, LocalDate naissance) {
        this.numeroINSEE = numeroINSEE;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.telephone = telephone;
        this.naissance = naissance;
    }
    
    // Constructeur simple (si nécessaire)
    public Personne(String nom) {
        this.nom = nom;
    }
}