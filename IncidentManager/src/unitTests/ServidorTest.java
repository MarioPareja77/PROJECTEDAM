package src;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cat.enmarxa.incidentmanager.Servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ServidorTest {

    private static final int TEST_PORT = 12345;
    private ExecutorService executorService;

    @BeforeEach
    void setUp() {
        executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            try {
                Servidor.main(new String[]{}); // Inicia el servidor a un fil diferent
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        
        // Dona temps al servidor a iniciarse
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @AfterEach
    void tearDown() {
        // Tanca el servidor
        Servidor.TancarConnexioServidor();
        executorService.shutdownNow();
    }

    @Test
    void testSuccessfulAuthentication() throws IOException {
        try (Socket clientSocket = new Socket("localhost", TEST_PORT);
             DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());
             DataInputStream input = new DataInputStream(clientSocket.getInputStream())) {

            // Simula les credencials que envia un client
            String email = "mario@acme.cat";
            String password = "12345678";
            output.writeUTF(email);
            output.writeUTF(password);

            // Lectura de la resposta delk servidor
            String response = input.readUTF();

            // Verifica que la resposta del servidor és que el client esta conectat
            assertEquals("Autenticació exitosa", response.substring(0, 20));
        }
    }

    @Test
    void testAccountLocked() throws IOException {
        try (Socket clientSocket = new Socket("localhost", TEST_PORT);
             DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());
             DataInputStream input = new DataInputStream(clientSocket.getInputStream())) {
        	
            // Simula les credencials que envia un client bloquejat
            String email = "mario@acme.cat";
            String password = "12345678";
            output.writeUTF(email);
            output.writeUTF(password);

            // Llegueix la resposta del servidor
            String response = input.readUTF();

            // Verifica que la resposta del servidor es Compte bloquejat.
            assertEquals("Compte bloquejat", response);
        }
    }
}
