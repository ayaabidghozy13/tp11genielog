package FFSSM;

import lombok.Data;
import lombok.NonNull;

// Lombok : génère getter / setter / constructeur, toString...
@Data
public class Site {
    @NonNull
    private String nom;

    @NonNull
    private String details;

    // Le main n'est pas nécessaire pour l'implémentation
}