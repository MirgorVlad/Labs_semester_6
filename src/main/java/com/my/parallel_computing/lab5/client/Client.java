package com.my.parallel_computing.lab5.client;

import java.io.*;
import java.net.UnknownHostException;
import java.net.Socket;
import java.util.Arrays;
import java.util.Random;


//Set configuration
//Send data
//Start execution
//Get result

public class Client
{
    // initialize socket and input output streams
    private Socket socket            = null;
    private DataOutputStream out     = null;
    private DataInputStream in       = null;
    private BufferedReader reader    = null;

    // constructor to put ip address and port
    public Client(String address, int port) {
        int[] result = null;
        // establish a connection
        try
        {
            socket = new Socket(address, port);
            // takes input from terminal
            reader = new BufferedReader(new InputStreamReader(System.in));

            // sends output to the socket
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());

            // print the initial data
            System.out.println(in.readUTF());
        }
        catch(Exception e) {
            System.out.println(e);
        }


        // string to read message from input
        String line = "";

        // keep reading until "Exit" is input
        while (!line.equals("exit")) {
            try
            {
                line = reader.readLine();
                out.writeUTF(line);

                if(line.contains("configuration")){
                    int threadCount = Integer.parseInt(line.split(":")[1]);
                    int dim = Integer.parseInt(line.split(":")[2]);

                    ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
                    ObjectOutputStream objOut = new ObjectOutputStream(byteOut);
                    objOut.writeObject(generateArray(dim));
                    byte[] matrixBytes = byteOut.toByteArray();
                    out.write(matrixBytes);
                    out.writeInt(threadCount);
                    out.flush();

                    byte resultStatus = in.readByte();
                    System.out.println("status: " + resultStatus);
                    if(resultStatus == 1) {
                        System.out.println("Data has been sent");
                    } else {
                        System.out.println("Data doesn't send");
                    }

                }
                if(line.equals("result")){
                    byte resultStatus = in.readByte();
                    System.out.println("status: " + resultStatus);
                    if(resultStatus == 1) {
                        ObjectInputStream objIn = new ObjectInputStream(in);
                        result = (int[]) objIn.readObject();
                        System.out.println("Result: " + Arrays.toString(result));
                    }else if(resultStatus == -1){
                        System.out.println("Please send configuration first");
                    }
                    else {
                        System.out.println("Executin...");
                    }
                }
                if(line.equals("execute")){
                    byte resultStatus = in.readByte();
                    System.out.println("status: " + resultStatus);
                    if(resultStatus == 1) {
                        System.out.println("Execution has been started");
                    } else {
                        System.out.println("Please send configuration first");
                    }
                }

                //System.out.println(in.readUTF());
                System.out.println(in.readUTF());
            }
            catch(Exception i)
            {
                System.out.println(i);
            }
        }

        // close the connection
        try
        {
            reader.close();
            out.close();
            socket.close();
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
    }

    public static void main(String args[])
    {
        Client client = new Client("127.0.0.1", 5056);
    }

    static int[][] generateArray(int length){
        Random random = new Random();
        int[][] arr = new int[length][length];

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                arr[i][j] = random.nextInt(1000);
            }
        }
        return arr;
    }
}
