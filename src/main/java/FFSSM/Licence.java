package FFSSM;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Licence {

    public Plongeur possesseur;
    public String numero;
    public LocalDate delivrance;
    public Club club;

    public Licence(Plongeur possesseur, String numero, LocalDate delivrance, Club club) {
        this.possesseur = possesseur;
        this.numero = numero;
        this.delivrance = delivrance;
        this.club = club;
    }

    /**
     * Est-ce que la licence est valide à la date indiquée ?
     * Une licence est valide pendant un an à compter de sa date de délivrance
     * @param d la date à tester
     * @return vrai si valide à la date d
     **/
    public boolean estValide(LocalDate d) {
        // La date d'expiration est 1 an après la date de délivrance
        LocalDate expiration = delivrance.plusYears(1);

        // Valide si la date d est après ou égale à la délivrance ET avant la date d'expiration (le jour anniversaire)
        return !d.isBefore(delivrance) && d.isBefore(expiration);
    }
}