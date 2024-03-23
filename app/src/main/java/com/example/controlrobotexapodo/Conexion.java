package com.example.controlrobotexapodo;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.net.Socket;

public class Conexion {

    private Socket socket;

    /**
     * Constructor vacio para la clase
     * @author: Uriel Gomez
     * @version: 23/03/2024
     */
    public Conexion() {
        // Constructor vacío
    }

    /**
     * Establecer la conexion con el servidor
     * @param ip Dirección ip del servidor
     * @param puerto Puerto en el que está escuchando el servidor
     * @throws IOException
     * @author: Uriel Gomez
     * @version: 23/03/2024
     * */
    public void conectarConServidor(String ip, int puerto) throws IOException {
        socket = new Socket(ip, puerto);
    }

    /**
     * Enviar dato del cliente al servidor
     * @param comando Comando a enviar al servidor
     * @throws  IOException
     * @author: Uriel Gomez
     * @version: 23/03/2024*/
    public void enviarComando(@NonNull String comando) throws IOException {
        socket.getOutputStream().write(comando.getBytes());
    }

    /**
     * Recibir una respuesta enviada por el servidor
     * @throws  IOException
     * @author: Uriel Gomez
     * @version: 23/03/2024*/
    public String recibirRespuesta() throws IOException {
        byte[] buffer = new byte[1024];
        int bytesRead = socket.getInputStream().read(buffer);
        return new String(buffer, 0, bytesRead);
    }

    /**
     * Cerrar la conexion del cliente con el servidor
     * @throws IOException
     * @author: Uriel Gomez
     * @version: 23/03/2024
     */
    public void cerrarConexion() throws IOException {
        if (socket != null) {
            socket.close();
        }
    }

    /**
     * Getter para obtener el socket con el que se está trabajando
     * @return socket Socket de conexion
     * @author: Uriel Gomez
     * @version: 23/03/2024*/
    public Socket getSocket() {
        return socket;
    }
}
