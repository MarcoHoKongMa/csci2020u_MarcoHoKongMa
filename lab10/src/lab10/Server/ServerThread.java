package lab10.Server;

import java.io.*;
import java.net.Socket;

public class ServerThread extends Thread {
    private Socket socket = null;
    private BufferedReader serverInput = null;

    // Constructor
    public ServerThread(Socket socket) {
        try {
            this.socket = socket;
            this.serverInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        String message = null;

        while(true) {
            try {
                message = serverInput.readLine();

                if(message == null) {
                    break;
                }
                ServerWindow.messages.appendText(message + "\n\n");
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
}
