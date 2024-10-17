package cat.enmarxa.incidentmanager;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ServeiActius {

    private Socket socket;
    private ObjectOutputStream output;
    private ObjectInputStream input;

    public ServeiActius(String serverAddress, int serverPort) throws IOException {
        socket = new Socket(serverAddress, serverPort);
        output = new ObjectOutputStream(socket.getOutputStream());
        input = new ObjectInputStream(socket.getInputStream());
    }

    // Mètode per llistar els actius
    @SuppressWarnings("unchecked")
    public List<Actiu> llistarActius() {
        try {
            // Enviar la sol·licitud per llistar els actius
            output.writeObject("LLISTAR_ACTIUS");
            output.flush();

            // Esperar la resposta del servidor
            List<Actiu> actius = (List<Actiu>) input.readObject();
            return actius;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null; // Retornar null si hi ha cap error
        }
    }

    // Mètode per crear un nou actiu
    public boolean crearActiu(Actiu nouActiu) {
        try {
            // Enviar la sol·licitud per crear un nou actiu al servidor
            output.writeObject("CREAR_ACTIU");
            output.writeObject(nouActiu); // Suposem que Actiu és un objecte que es pot serialitzar
            output.flush();

            // Esperar la resposta del servidor
            String resposta = (String) input.readObject();
            return resposta.equals("ACTIU_CREADO");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return false; // Retornar false si hi ha un error
        }
    }

    // Mètode per tancar connexions
    public void tancarConnexio() {
        try {
            if (input != null) input.close();
            if (output != null) output.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
