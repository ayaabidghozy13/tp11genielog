package FFSSM;

import java.time.LocalDate;
import java.time.Month;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LicenceTest {

    // Objets de base pour les tests
    private Plongeur plongeur;
    private Club club;
    private final LocalDate AUJOURDHUI = LocalDate.of(2025, Month.JANUARY, 1);
    private final LocalDate DATE_EXPIRATION_LICENCE = LocalDate.of(2025, Month.JANUARY, 1);
    private final LocalDate ANNEE_PASSEE_VALIDE = AUJOURDHUI.minusMonths(6); 
    private final LocalDate ANNEE_PASSEE_EXPIREE = AUJOURDHUI.minusYears(2); 
    
    // Pour que le club ait un président (Moniteur)
    private DiplomeDeMoniteur moniteurDiplome;

    @BeforeEach
    public void setUp() {
        Plongeur moniteur = new Plongeur("1M", "Bond", "James", "HQ", "007", LocalDate.of(1960, Month.JANUARY, 1), GroupeSanguin.OPLUS, 5);
        moniteurDiplome = new DiplomeDeMoniteur(moniteur, 12345);
        plongeur = new Plongeur("1P", "Dupond", "Jean", "Maison", "123", LocalDate.of(1980, Month.FEBRUARY, 2), GroupeSanguin.AMOINS, 2);
        club = new Club(moniteurDiplome, "Club des Dauphins");
        
        // Licence valide pour les tests de Plongeur
        plongeur.ajouteLicence("L1", ANNEE_PASSEE_VALIDE, club);
    }

    @Test
    public void testLicenceEstValide_DateDansLaPeriode() {
        assertTrue(plongeur.derniereLicence().estValide(AUJOURDHUI), "La licence en cours devrait être valide.");
    }
    
    @Test
    public void testLicenceEstValide_DateLimiteExclusive() {
        // Licence délivrée le 01/01/2024, expire le 01/01/2025
        Licence l = plongeur.ajouteLicence("L2", DATE_EXPIRATION_LICENCE.minusYears(1), club); 
        
        // Valide la veille
        assertTrue(l.estValide(DATE_EXPIRATION_LICENCE.minusDays(1)), "La licence doit être valide la veille du jour anniversaire.");
        
        // Expirée le jour anniversaire
        assertFalse(l.estValide(DATE_EXPIRATION_LICENCE), "La licence doit être expirée le jour anniversaire.");
    }

    @Test
    public void testLicenceEstExpiree() {
        Licence licenceAncienne = plongeur.ajouteLicence("L3", ANNEE_PASSEE_EXPIREE, club);
        assertFalse(licenceAncienne.estValide(AUJOURDHUI), "Une licence délivrée il y a plus d'un an doit être expirée.");
    }

    @Test
    public void testDerniereLicence() {
        Licence l2 = plongeur.ajouteLicence("L2", AUJOURDHUI.minusDays(1), club);
        assertEquals(l2, plongeur.derniereLicence(), "La dernière licence ajoutée doit être retournée.");
    }
}
