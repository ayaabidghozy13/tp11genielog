package FFSSM;

import java.time.LocalDate;
import java.time.Month;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlongeeTest {

    private DiplomeDeMoniteur moniteurDiplome;
    private Plongeur moniteur;
    private Plongeur plongeurAvecLicenceValide;
    private Plongeur plongeurSansLicence;
    private Club club;
    private Site site;
    private final LocalDate AUJOURDHUI = LocalDate.of(2025, Month.JANUARY, 1);
    private final LocalDate ANNEE_PASSEE_VALIDE = AUJOURDHUI.minusMonths(6); 
    private final LocalDate ANNEE_PASSEE_EXPIREE = AUJOURDHUI.minusYears(2); 

    @BeforeEach
    public void setUp() {
        moniteur = new Plongeur("1M", "Bond", "James", "HQ", "007", LocalDate.of(1960, Month.JANUARY, 1), GroupeSanguin.OPLUS, 5);
        moniteurDiplome = new DiplomeDeMoniteur(moniteur, 12345);
        plongeurAvecLicenceValide = new Plongeur("1P", "Dupond", "Jean", "Maison", "123", LocalDate.of(1980, Month.FEBRUARY, 2), GroupeSanguin.AMOINS, 2);
        plongeurSansLicence = new Plongeur("2P", "Durand", "Paul", "Loin", "456", LocalDate.of(1990, Month.MARCH, 3), GroupeSanguin.BMOINS, 1);
        club = new Club(moniteurDiplome, "Club des Dauphins");
        site = new Site("La Fosse", "Très profond");
        
        // Licences valides
        plongeurAvecLicenceValide.ajouteLicence("L1", ANNEE_PASSEE_VALIDE, club);
        moniteur.ajouteLicence("LM", ANNEE_PASSEE_VALIDE, club);
    }

    @Test
    public void testPlongeeEstConforme_OK() {
        Plongee p = new Plongee(site, moniteurDiplome, AUJOURDHUI, 10, 30);
        p.ajouteParticipant(plongeurAvecLicenceValide);
        assertTrue(p.estConforme(), "La plongée devrait être conforme si toutes les licences sont valides.");
    }
    
    @Test
    public void testPlongeeEstConforme_ParticipantSansLicence() {
        Plongee p = new Plongee(site, moniteurDiplome, AUJOURDHUI, 10, 30);
        p.ajouteParticipant(plongeurSansLicence);
        assertFalse(p.estConforme(), "La plongée ne devrait pas être conforme avec un participant sans licence.");
    }

    @Test
    public void testPlongeeEstConforme_MoniteurLicenceExpiree() {
        Plongeur moniteurExpire = new Plongeur("9M", "X", "Y", "Z", "0", LocalDate.of(1950, Month.JANUARY, 1), GroupeSanguin.OPLUS, 5);
        moniteurExpire.ajouteLicence("LX", ANNEE_PASSEE_EXPIREE, club);
        DiplomeDeMoniteur moniteurDiplomeExpire = new DiplomeDeMoniteur(moniteurExpire, 999);
        
        Plongee p = new Plongee(site, moniteurDiplomeExpire, AUJOURDHUI, 10, 30);
        p.ajouteParticipant(plongeurAvecLicenceValide);
        
        assertFalse(p.estConforme(), "La plongée ne devrait pas être conforme si le chef de palanquée a une licence expirée.");
    }
}
