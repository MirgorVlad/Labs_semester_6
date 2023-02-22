package com.my.networks.lab1.upd;

import com.my.networks.lab1.SocketInfo;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;

public class Client {
    private static final int N = 2;
    private static final int M = 3;

    public static void main(String[] args) throws Exception {
        InetAddress inetAddress = InetAddress.getByName(SocketInfo.DEFAULT_HOST_VALUE);
        try(DatagramSocket datagramSocket = new DatagramSocket()) {
            for(int i = 0; i < N; i++) {
                byte[] messageBuffer = new byte[8];
                ByteBuffer.wrap(messageBuffer).putFloat(SocketInfo.generateRandomFloat());
                DatagramPacket datagramPacket = new DatagramPacket(messageBuffer, 8, inetAddress,
                        SocketInfo.DEFAULT_PORT_VALUE);
                datagramSocket.send(datagramPacket);
                DataInputStream dataInputStream = new DataInputStream(new
                        ByteArrayInputStream(datagramPacket.getData()));
                final float sentNumber = dataInputStream.readFloat();
                System.out.println("Client sent: " + sentNumber);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        try(DatagramSocket datagramSocket = new
                DatagramSocket(SocketInfo.DEFAULT_PORT_VALUE, inetAddress)) {
            int got = 0;
            while(got < M) {
                byte[] messageBuffer = new byte[8];
                DatagramPacket pdatagramPacketcket = new DatagramPacket(messageBuffer, 8);
                datagramSocket.receive(pdatagramPacketcket);
                DataInputStream dataInputStream = new DataInputStream(new
                        ByteArrayInputStream(pdatagramPacketcket.getData()));
                final char gotNumber = dataInputStream.readChar();
                System.out.println("Client got: " + gotNumber);
                got++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}