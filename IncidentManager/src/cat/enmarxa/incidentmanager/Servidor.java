package cat.enmarxa.incidentmanager;

import java.io.*;
import java.net.*;
import java.sql.*;

public class Servidor {

    public static void main(String[] args) {
        int port = 12345;

        try (ServerSocket servidor = new ServerSocket(port)) {
            System.out.println("Servidor escoltant pel port " + port);

            while (true) {
                // Acceptar connexions dels clients
                Socket socketCliente = servidor.accept();
                System.out.println("Client connectat");

                // Crear fil per acceptar múltiples clients
                new FilClient(socketCliente).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Classe interna que manega les connexions dels clients en un fil
    private static class FilClient extends Thread {
        private Socket socketClient;

        public FilClient(Socket socketClient) {
            this.socketClient = socketClient;
        }

        public void run() {
            try (BufferedReader entrada = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
                 PrintWriter sortida = new PrintWriter(socketClient.getOutputStream(), true)) {

                // Rebre les dades del client
                String descripcion = entrada.readLine();
                String prioridad = entrada.readLine();

            } catch (IOException | SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    socketClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}