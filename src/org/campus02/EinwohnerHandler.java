package org.campus02;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;

public class EinwohnerHandler implements Runnable {

    private Socket client;
    private EinwohnerManager em;

    public EinwohnerHandler(Socket client) {
        this.client = client;
        this.em = new EinwohnerManager();
    }

    public void process() {
        // mit client kommunizieren
        // inputstream zum lesen
        // outputstream zum schreiben
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(client.getInputStream()));
             BufferedWriter bw = new BufferedWriter(
                     new OutputStreamWriter(client.getOutputStream()))
        ) {
            ArrayList<Einwohner> einwohnerList = em.load();
            String input;
            while ((input = br.readLine()) != null) {
                // Szenario: input = exit
                if (input.equalsIgnoreCase("exit")) { //Exit, exIT, exit
                    bw.write("Verbindung wird wie gewünscht beendet");
                    bw.newLine(); // !!!!!
                    bw.flush(); // !!!!
                    break;
                }

                // 1. Fall: GET <<bundesland>> // steiermark, kärnten, tirol
                // 2. Fall: GET <<geburtsjahr>> order by name
                if (input.startsWith("GET ")) {
                    String[] commands = input.split(" ");
                    if (commands.length == 2) {
                        // bundesland wird abgefragt
                        ArrayList<Einwohner> einwohnerPerBundesland = new ArrayList<>();
                        for (Einwohner ew : einwohnerList) {
                            if (ew.getBundesland().equalsIgnoreCase(commands[1])) {
                                einwohnerPerBundesland.add(ew);
                                // bw.write(ew.toString());
                                // bw.newLine();
                            }
                        }
                        bw.write(String.valueOf(einwohnerPerBundesland));
                    } else {
                        // geburtsjahr wird abgefragt
                        ArrayList<Einwohner> list = getEinwohnerPerYear(commands[1], einwohnerList);
                        bw.write(String.valueOf(list));
                    }
                } else {
                    bw.write("unknown command");
                }
                bw.newLine();
                bw.flush();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (DataFileException e) {
            throw new RuntimeException(e);
        }
    }

    private ArrayList<Einwohner> getEinwohnerPerYear(String command, ArrayList<Einwohner> einwohnerList) {
        int geburtsjahr = Integer.parseInt(command);

        ArrayList<Einwohner> einwohnerProGeburtsjahr = new ArrayList<>();
        for (Einwohner ew : einwohnerList) {
            if (ew.getGeburtsjahr() == geburtsjahr) {
                einwohnerProGeburtsjahr.add(ew);
            }
        }
        Collections.sort(einwohnerProGeburtsjahr);
        return einwohnerProGeburtsjahr;
    }

    @Override
    public void run() {
        process();
    }
}
