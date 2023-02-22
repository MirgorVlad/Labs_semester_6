package com.my.networks.lab1.tcp;

import com.my.networks.lab1.SocketInfo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
public class Server {
    private static final int N = 2;
    private static final int M = 3;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(SocketInfo.DEFAULT_PORT_VALUE);
             Socket socket = serverSocket.accept()) {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new
                    DataOutputStream(socket.getOutputStream());
            int got = 0;
            while (got < N) {
                final float gotNumber = dataInputStream.readFloat();
                System.out.println("Server got number: " + gotNumber);
                got++;
            }
            for (int i = 0; i < M; i++) {
                final char randomChar = SocketInfo.generateRandomChar();
                dataOutputStream.writeChar(randomChar);
                System.out.println("Server sent: " + randomChar);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
