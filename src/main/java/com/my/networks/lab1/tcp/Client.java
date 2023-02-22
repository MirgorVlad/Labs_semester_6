package com.my.networks.lab1.tcp;

import com.my.networks.lab1.SocketInfo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Client {
    private static final int N = 2;
    private static final int M = 3;
    public static void main(String[] args) {
        try (Socket socket = new Socket(SocketInfo.DEFAULT_HOST_VALUE,
                SocketInfo.DEFAULT_PORT_VALUE)) {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new
                    DataOutputStream(socket.getOutputStream());
            for (int i = 0; i < N; i++) {
                final float randomFloat = SocketInfo.generateRandomFloat();
                dataOutputStream.writeFloat(randomFloat);
                System.out.println("Client sent: " + randomFloat);
            }
            int got = 0;
            while (got < M) {
                final char gotNumber = dataInputStream.readChar();
                System.out.println("Client got number: " + gotNumber);
                got++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}