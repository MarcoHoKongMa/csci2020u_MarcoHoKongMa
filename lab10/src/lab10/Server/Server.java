package lab10.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        int numClients = 0;
        int serverPort = 1024;
        int maxClients = 50;
        ServerThread[] threads;
        ServerSocket serverSocket;
        Socket clientSocket;

        WindowThread messagesHistory = new WindowThread();
        messagesHistory.start();

        try {
            serverSocket = new ServerSocket(serverPort);
            threads = new ServerThread[maxClients];
            while(true) {
                clientSocket = serverSocket.accept();
                threads[numClients] = new ServerThread(clientSocket);
                threads[numClients].start();
                numClients++;
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
