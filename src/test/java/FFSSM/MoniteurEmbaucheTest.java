package FFSSM;

import java.time.LocalDate;
import java.time.Month;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MoniteurEmbaucheTest {
    
    private DiplomeDeMoniteur moniteurDiplome;
    private Club club;
    private final LocalDate AUJOURDHUI = LocalDate.of(2025, Month.JANUARY, 1);

    @BeforeEach
    public void setUp() {
        Plongeur moniteur = new Plongeur("1M", "Bond", "James", "HQ", "007", LocalDate.of(1960, Month.JANUARY, 1), GroupeSanguin.OPLUS, 5);
        moniteurDiplome = new DiplomeDeMoniteur(moniteur, 12345);
        club = new Club(moniteurDiplome, "Club des Dauphins");
    }

    @Test
    public void testEmployeurActuel_PasDembauche() {
        assertNull(moniteurDiplome.employeurActuel(), "Sans embauche, l'employeur actuel doit être null.");
    }

    @Test
    public void testNouvelleEmbauche_EstEnCours() {
        moniteurDiplome.nouvelleEmbauche(club, AUJOURDHUI);
        
        assertEquals(club, moniteurDiplome.employeurActuel(), "Le club devrait être l'employeur actuel.");
        assertFalse(moniteurDiplome.emplois().get(0).estTerminee(), "L'embauche doit être en cours.");
    }

    @Test
    public void testNouvelleEmbauche_TermineLaPrecedente() {
        Club clubA = new Club(moniteurDiplome, "Club A");
        Club clubB = new Club(moniteurDiplome, "Club B");
        LocalDate debutClubB = AUJOURDHUI.plusMonths(1);

        moniteurDiplome.nouvelleEmbauche(clubA, AUJOURDHUI);
        moniteurDiplome.nouvelleEmbauche(clubB, debutClubB); // Changement d'employeur
        
        Embauche premiereEmbauche = moniteurDiplome.emplois().get(0);
        
        // L'ancienne embauche doit être terminée la veille du début de la nouvelle
        assertTrue(premiereEmbauche.estTerminee(), "L'ancienne embauche doit être terminée.");
        assertEquals(debutClubB.minusDays(1), premiereEmbauche.getFin(), "La fin doit être la veille de la nouvelle embauche.");
        
        // L'employeur actuel est le nouveau club
        assertEquals(clubB, moniteurDiplome.employeurActuel(), "Le nouvel employeur devrait être Club B.");
    }
    
    @Test
    public void testEmployeurActuel_ApresFinContrat() {
        moniteurDiplome.nouvelleEmbauche(club, AUJOURDHUI.minusMonths(2));
        moniteurDiplome.emplois().get(0).terminer(AUJOURDHUI.minusDays(1));
        
        assertNull(moniteurDiplome.employeurActuel(), "Après la fin du contrat, l'employeur actuel doit être null.");
    }
}
