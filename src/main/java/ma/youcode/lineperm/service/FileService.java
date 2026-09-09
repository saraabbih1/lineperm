package ma.youcode.lineperm.service;

import ma.youcode.lineperm.model.Fichier;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileService {
    private final Map<String,Fichier>fichiers = new HashMap<>();

    public void charger() {

        fichiers.clear();

        try {

            Path path = Path.of("src/main/resources/files.txt");

            if (!Files.exists(path)) {
                return;
            }

            List<String> lines = Files.readAllLines(path);

            for (String line : lines) {

                String[] parts = line.split(";");

                String nom = parts[0];
                String proprietaire = parts[1];

                Fichier fichier = new Fichier(
                        nom,
                        proprietaire,
                        "rwd",
                        "r --"
                );

                fichiers.put(nom, fichier);
            }

        } catch (IOException e) {

            System.out.println("Erreur lors de la lecture du fichier.");
        }
    }
}