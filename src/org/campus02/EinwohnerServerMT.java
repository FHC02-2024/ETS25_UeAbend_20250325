package org.campus02;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class EinwohnerServerMT {

    public static void main(String[] args) {
        // ServerSocket -> port
        try (ServerSocket server = new ServerSocket(1111)) {
            // server soll mehrere Verbindungen annehmen können
            while (true) {
                System.out.println("Server wartet auf Client");
                Socket client = server.accept();
                System.out.println("Client hat sich verbunden");
                EinwohnerHandler eh = new EinwohnerHandler(client);
                new Thread(eh).start();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
