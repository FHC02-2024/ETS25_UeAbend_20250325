package org.campus02;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class EinwohnerManager {

    public ArrayList<Einwohner> load() throws DataFileException {

        // 1. Methode zum Laden von Einwohnern zur Verfügung stellen
        // 2. Daten aus dem File lesen
        String filePath = "data/testdata-einwohner.csv";
        ArrayList<Einwohner> einwohnerList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(
                new FileReader(filePath)
        )) {
            String line;
            br.readLine(); // skip 1. row
            while ((line = br.readLine()) != null) {
                //System.out.println(line); // teste file read
                // 3. Einwohner Zeile für Zeile parsen
                // => in ein Einwohner-Objekt umwandeln
                String[] einwohnerData = line.split(";");
                //System.out.println(Arrays.toString(einwohnerData));
                int id = Integer.parseInt(einwohnerData[0]);
                String name = einwohnerData[1];
                String bundesland = einwohnerData[2];
                int geburtsjahr = Integer.parseInt(einwohnerData[3]);

                Einwohner einwohner = new Einwohner(id, name, bundesland, geburtsjahr);
                //System.out.println(einwohner);

                // 4. füge den EinwohnerIn zur Liste hinzu
                einwohnerList.add(einwohner);
            }
        } catch (FileNotFoundException e) {
            throw new DataFileException("File konnte nicht gefunden werden", e);
        } catch (IOException e) {
            throw new DataFileException("Datei konnte nicht gelesen werden", e);
        }

        //Collections.sort(einwohnerList, new EinwohnerComparator());
        einwohnerList.sort(new EinwohnerComparator());
        return einwohnerList;
    }
}
