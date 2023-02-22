package com.my.networks.lab1.upd;

import com.my.networks.lab1.SocketInfo;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

public class Server {
    private static final int N = 2;
    private static final int M = 3;

    public static void main(String[] args) throws UnknownHostException {
        InetAddress inetAddress = InetAddress.getByName(SocketInfo.DEFAULT_HOST_VALUE);
        try(DatagramSocket datagramSocket = new
                DatagramSocket(SocketInfo.DEFAULT_PORT_VALUE, inetAddress)) {
            int got = 0;
            while (got < N) {
                byte[] messageBuffer = new byte[8];
                DatagramPacket datagramPacket = new DatagramPacket(messageBuffer, 8);
                datagramSocket.receive(datagramPacket);
                DataInputStream dataInputStream = new DataInputStream(new
                        ByteArrayInputStream(datagramPacket.getData()));
                final float receivedNumber = dataInputStream.readFloat();
                System.out.println("Server got: " + receivedNumber);
                got++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try(DatagramSocket datagramSocket = new DatagramSocket()) {
            for(int i = 0; i < M; i++) {
                byte[] messageBuffer = new byte[8];
                ByteBuffer.wrap(messageBuffer).putChar(SocketInfo.generateRandomChar());
                DatagramPacket datagramPacket = new DatagramPacket(messageBuffer, 8, inetAddress,
                        SocketInfo.DEFAULT_PORT_VALUE);
                datagramSocket.send(datagramPacket);
                DataInputStream dataInputStream = new DataInputStream(new
                        ByteArrayInputStream(datagramPacket.getData()));
                final char gotNumber = dataInputStream.readChar();
                System.out.println("Server sent: " + gotNumber);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}