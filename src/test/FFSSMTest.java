package FFSSM;

import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FFSSMTest {

    // --- Objets de Test ---
    
    private DiplomeDeMoniteur moniteurDiplome;
    private Plongeur moniteur;
    private Plongeur plongeurAvecLicenceValide;
    private Plongeur plongeurSansLicence;
    private Club club;
    private Site site;
    private Plongee plongeeConforme;
    private Plongee plongeeNonConforme;

    // --- Dates de Référence ---
    
    // Le 1er janvier 2025 est utilisé comme date "aujourd'hui" fixe pour des tests reproductibles
    private final LocalDate AUJOURDHUI = LocalDate.of(2025, Month.JANUARY, 1);
    // Le jour de l'expiration d'une licence délivrée le 01/01/2024
    private final LocalDate DATE_EXPIRATION_LICENCE = LocalDate.of(2025, Month.JANUARY, 1); 
    // La date où la licence est expirée (lendemain de l'expiration)
    private final LocalDate APRES_EXPIRATION = DATE_EXPIRATION_LICENCE.plusDays(1);
    // Délivrée il y a un peu moins d'un an, donc valide
    private final LocalDate ANNEE_PASSEE_VALIDE = AUJOURDHUI.minusMonths(6); 
    // Délivrée il y a plus d'un an, donc expirée
    private final LocalDate ANNEE_PASSEE_EXPIREE = AUJOURDHUI.minusYears(2); 

    @BeforeEach
    public void setUp() {
        // 1. Initialisation des Personnes / Diplôme
        moniteur = new Plongeur("1M", "Bond", "James", "HQ", "007", LocalDate.of(1960, Month.JANUARY, 1), GroupeSanguin.OPLUS, 5);
        moniteurDiplome = new DiplomeDeMoniteur(moniteur, 12345);
        
        plongeurAvecLicenceValide = new Plongeur("1P", "Dupond", "Jean", "Maison", "123", LocalDate.of(1980, Month.FEBRUARY, 2), GroupeSanguin.AMOINS, 2);
        plongeurSansLicence = new Plongeur("2P", "Durand", "Paul", "Loin", "456", LocalDate.of(1990, Month.MARCH, 3), GroupeSanguin.BMOINS, 1);

        // 2. Initialisation du Club
        club = new Club(moniteurDiplome, "Club des Dauphins");
        
        // 3. Initialisation des Licences
        // Licence valide pour le plongeur
        plongeurAvecLicenceValide.ajouteLicence("L1", ANNEE_PASSEE_VALIDE, club);
        // Licence valide pour le moniteur
        moniteur.ajouteLicence("LM", ANNEE_PASSEE_VALIDE, club);

        // 4. Initialisation du Site
        site = new Site("La Fosse", "Très profond");
        
        // 5. Initialisation des Plongées (pour les tests de Club)
        plongeeConforme = new Plongee(site, moniteurDiplome, AUJOURDHUI, 10, 30);
        plongeeConforme.ajouteParticipant(plongeurAvecLicenceValide);
        
        // Plongée non conforme car un participant n'a pas de licence
        plongeeNonConforme = new Plongee(site, moniteurDiplome, AUJOURDHUI, 10, 30);
        plongeeNonConforme.ajouteParticipant(plongeurSansLicence); 
    }

    // --- Tests pour LICENCE ---

    @Test
    public void testLicenceEstValide_DateDansLaPeriode() {
        // La licence délivrée il y a 6 mois devrait être valide aujourd'hui (dans l'année)
        assertTrue(plongeurAvecLicenceValide.derniereLicence().estValide(AUJOURDHUI), "La licence en cours devrait être valide.");
    }
    
    @Test
    public void testLicenceEstValide_DateLimiteExclusive() {
        // On crée une licence qui expire le 31/12/2024. Le 01/01/2025 est la date d'expiration.
        Licence l = plongeurAvecLicenceValide.ajouteLicence("L2", DATE_EXPIRATION_LICENCE.minusYears(1), club); 
        
        // La licence est valide la veille de l'expiration (31/12/2024)
        assertTrue(l.estValide(DATE_EXPIRATION_LICENCE.minusDays(1)), "La licence doit être valide la veille du jour anniversaire.");
        
        // La licence est expirée le jour anniversaire (01/01/2025)
        assertFalse(l.estValide(DATE_EXPIRATION_LICENCE), "La licence doit être expirée le jour anniversaire.");
    }

    @Test
    public void testLicenceEstExpiree() {
        Licence licenceAncienne = plongeurAvecLicenceValide.ajouteLicence("L3", ANNEE_PASSEE_EXPIREE, club);
        assertFalse(licenceAncienne.estValide(AUJOURDHUI), "Une licence délivrée il y a plus d'un an doit être expirée.");
    }
    
    // --- Tests pour PLONGEUR ---
    
    @Test
    public void testDerniereLicence() {
        Licence l2 = plongeurAvecLicenceValide.ajouteLicence("L2", AUJOURDHUI.minusDays(1), club);
        // Vérifie que la dernière licence ajoutée est bien celle retournée
        assertEquals(l2, plongeurAvecLicenceValide.derniereLicence(), "La dernière licence ajoutée doit être retournée.");
    }


    // --- Tests pour DIPLOMEDEMONITEUR / EMBAUCHE ---

    @Test
    public void testEmployeurActuel_PasDembauche() {
        assertNull(moniteurDiplome.employeurActuel(), "Sans embauche, l'employeur actuel doit être null.");
    }

    @Test
    public void testNouvelleEmbauche_EstEnCours() {
        moniteurDiplome.nouvelleEmbauche(club, AUJOURDHUI);
        
        // L'employeur actuel est le club
        assertEquals(club, moniteurDiplome.employeurActuel(), "Le club devrait être l'employeur actuel.");
        
        // L'embauche n'est pas terminée
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
    
    // --- Tests pour PLONGEE ---

    @Test
    public void testPlongeeEstConforme_OK() {
        // Tous les participants (chef et plongeur) ont une licence valide (dans setUp)
        assertTrue(plongeeConforme.estConforme(), "La plongée devrait être conforme si toutes les licences sont valides.");
    }
    
    @Test
    public void testPlongeeEstConforme_ParticipantSansLicence() {
        // Teste la plongée non conforme créée dans setUp (plongeurSansLicence)
        assertFalse(plongeeNonConforme.estConforme(), "La plongée ne devrait pas être conforme avec un participant sans licence.");
    }

    @Test
    public void testPlongeeEstConforme_MoniteurLicenceExpiree() {
        // Le moniteur a une ancienne licence expirée
        Plongeur moniteurExpire = new Plongeur("9M", "X", "Y", "Z", "0", LocalDate.of(1950, Month.JANUARY, 1), GroupeSanguin.OPLUS, 5);
        moniteurExpire.ajouteLicence("LX", ANNEE_PASSEE_EXPIREE, club);
        DiplomeDeMoniteur moniteurDiplomeExpire = new DiplomeDeMoniteur(moniteurExpire, 999);
        
        Plongee p = new Plongee(site, moniteurDiplomeExpire, AUJOURDHUI, 10, 30);
        p.ajouteParticipant(plongeurAvecLicenceValide);
        
        assertFalse(p.estConforme(), "La plongée ne devrait pas être conforme si le chef de palanquée a une licence expirée.");
    }

    // --- Tests pour CLUB ---
    
    @Test
    public void testOrganisePlongee() {
        club.organisePlongee(plongeeConforme);
        club.organisePlongee(plongeeNonConforme);
        
        assertEquals(2, club.getActivites().size(), "Le club devrait avoir enregistré 2 plongées.");
    }

    @Test
    public void testPlongeesNonConformes() {
        // Enregistrement des plongées dans le club
        club.organisePlongee(plongeeConforme); // Conforme
        club.organisePlongee(plongeeNonConforme); // Non conforme (plongeur sans licence)
        
        // Ajout d'une autre plongée non conforme (licence expirée)
        Plongeur plongeurExpire = new Plongeur("3P", "Michel", "Pierre", "Près", "789", LocalDate.of(1970, Month.APRIL, 4), GroupeSanguin.ABPLUS, 3);
        plongeurExpire.ajouteLicence("L4", ANNEE_PASSEE_EXPIREE, club);
        Plongee p3NonConforme = new Plongee(site, moniteurDiplome, AUJOURDHUI, 10, 30);
        p3NonConforme.ajouteParticipant(plongeurExpire);
        club.organisePlongee(p3NonConforme);
        
        Set<Plongee> nonConformes = club.plongeesNonConformes();
        
        assertEquals(2, nonConformes.size(), "Le club devrait avoir 2 plongées non conformes.");
        assertTrue(nonConformes.contains(plongeeNonConforme), "La première non conforme doit être dans la liste.");
        assertTrue(nonConformes.contains(p3NonConforme), "La deuxième non conforme doit être dans la liste.");
        assertFalse(nonConformes.contains(plongeeConforme), "La plongée conforme ne doit pas être dans la liste.");
    }
}



 