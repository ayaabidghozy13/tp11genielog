package FFSSM;

import java.time.LocalDate;
import java.time.Month;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ClubTest {

    private DiplomeDeMoniteur moniteurDiplome;
    private Club club;
    private Plongee plongeeConforme;
    private Plongee plongeeNonConforme;
    private final LocalDate AUJOURDHUI = LocalDate.of(2025, Month.JANUARY, 1);
    private final LocalDate ANNEE_PASSEE_VALIDE = AUJOURDHUI.minusMonths(6); 
    private final LocalDate ANNEE_PASSEE_EXPIREE = AUJOURDHUI.minusYears(2); 

    @BeforeEach
    public void setUp() {
        Plongeur moniteur = new Plongeur("1M", "Bond", "James", "HQ", "007", LocalDate.of(1960, Month.JANUARY, 1), GroupeSanguin.OPLUS, 5);
        moniteurDiplome = new DiplomeDeMoniteur(moniteur, 12345);
        Plongeur plongeurAvecLicenceValide = new Plongeur("1P", "Dupond", "Jean", "Maison", "123", LocalDate.of(1980, Month.FEBRUARY, 2), GroupeSanguin.AMOINS, 2);
        Plongeur plongeurSansLicence = new Plongeur("2P", "Durand", "Paul", "Loin", "456", LocalDate.of(1990, Month.MARCH, 3), GroupeSanguin.BMOINS, 1);
        
        club = new Club(moniteurDiplome, "Club des Dauphins");
        Site site = new Site("La Fosse", "Très profond");
        
        // Licences valides pour la conformité
        plongeurAvecLicenceValide.ajouteLicence("L1", ANNEE_PASSEE_VALIDE, club);
        moniteur.ajouteLicence("LM", ANNEE_PASSEE_VALIDE, club);

        // Plongée Conforme
        plongeeConforme = new Plongee(site, moniteurDiplome, AUJOURDHUI, 10, 30);
        plongeeConforme.ajouteParticipant(plongeurAvecLicenceValide);
        
        // Plongée Non Conforme (plongeurSansLicence)
        plongeeNonConforme = new Plongee(site, moniteurDiplome, AUJOURDHUI, 10, 30);
        plongeeNonConforme.ajouteParticipant(plongeurSansLicence); 
    }
    
    @Test
    public void testOrganisePlongee() {
        club.organisePlongee(plongeeConforme);
        club.organisePlongee(plongeeNonConforme);
        
        assertEquals(2, club.getActivites().size(), "Le club devrait avoir enregistré 2 plongées.");
    }

    @Test
    public void testPlongeesNonConformes() {
        // Enregistrement des plongées dans le club
        club.organisePlongee(plongeeConforme); 
        club.organisePlongee(plongeeNonConforme); 
        
        // Ajout d'une autre plongée non conforme (licence expirée)
        Plongeur plongeurExpire = new Plongeur("3P", "Michel", "Pierre", "Près", "789", LocalDate.of(1970, Month.APRIL, 4), GroupeSanguin.ABPLUS, 3);
        plongeurExpire.ajouteLicence("L4", ANNEE_PASSEE_EXPIREE, club);
        Plongee p3NonConforme = new Plongee(new Site("S", "D"), moniteurDiplome, AUJOURDHUI, 10, 30);
        p3NonConforme.ajouteParticipant(plongeurExpire);
        club.organisePlongee(p3NonConforme);
        
        Set<Plongee> nonConformes = club.plongeesNonConformes();
        
        assertEquals(2, nonConformes.size(), "Le club devrait avoir 2 plongées non conformes.");
        assertTrue(nonConformes.contains(plongeeNonConforme), "La première non conforme doit être dans la liste.");
        assertTrue(nonConformes.contains(p3NonConforme), "La deuxième non conforme doit être dans la liste.");
        assertFalse(nonConformes.contains(plongeeConforme), "La plongée conforme ne doit pas être dans la liste.");
    }
}
