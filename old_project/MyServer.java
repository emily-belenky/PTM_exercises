package test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer {
    private ServerSocket serverSocket;
    private ClientHandler clientHandler;
    private volatile boolean isRunning;

    public MyServer(int port, ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
        try {
            this.serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(1000);
        } catch (IOException e) {
            throw new RuntimeException("Unable to create server socket on port " + port, e);
        }
    }

    public void start() {
        isRunning = true;
        new Thread(() -> {
            while (isRunning) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    handleClient(clientSocket);
                } catch (IOException e) {
                    if (isRunning) {
                        System.out.println("Exception while accepting client connection: " + e.getMessage());
                    }
                }
            }
        }).start();
    }

    private void handleClient(Socket clientSocket) {
        try {
            clientHandler.handleClient(clientSocket.getInputStream(), clientSocket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Exception while handling client connection: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.out.println("Exception while closing client socket: " + e.getMessage());
            }
        }
    }

    public void close() {
        isRunning = false;
        try {
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("Exception while closing server socket: " + e.getMessage());
        }
    }
}
