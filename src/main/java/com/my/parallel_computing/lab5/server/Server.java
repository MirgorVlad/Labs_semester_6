package com.my.parallel_computing.lab5.server;

import com.my.parallel_computing.lab1.Matrix;
import com.my.parallel_computing.lab1.Parallel;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.my.parallel_computing.lab1.Main.LAB1;
import static java.lang.Thread.sleep;

public class Server {
    //public static AtomicBoolean executorAvailable = new AtomicBoolean(true);
    //public static TaskProcessingThread taskProcessingThread = new TaskProcessingThread();
    public static CompletableFuture<Matrix> future;
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(5056);
        //taskProcessingThread.start();
        while (true)
        {
            Socket s = null;
            try
            {
                // socket object to receive incoming client requests
                s = ss.accept();

                System.out.println("A new client is connected : " + s);

                // obtaining input and out streams
                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());

                System.out.println("Assigning new thread for this client");

                // create a new thread object
                Thread t = new ClientThread(s, dis, dos);

                // Invoking the start() method
                t.start();

            }
            catch (Exception e){
                s.close();
                e.printStackTrace();
            }
        }
    }
    public static synchronized Matrix calculate(Matrix matrix, int countThread) {
        try {
            Matrix result = Parallel.doAction(matrix, countThread, LAB1);
            return result;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

// ClientHandler class
class ClientThread extends Thread {
    private final DataInputStream dis;
    private final DataOutputStream dos;
    private final Socket s;
    private boolean finished;

    // Constructor
    public ClientThread(Socket s, DataInputStream dis, DataOutputStream dos) {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
    }

    @Override
    public void  run()
    {
        String received;
        int[][] matrix = null;
        int threadCount = 1;
        int[] result = null;
        Matrix matrixResult = null;


        while (true)
        {
            try {

                dos.writeUTF( "Enter command: ");

                received = dis.readUTF();

                if(received.contains("configuration")){
                    matrixResult = null;
                    ObjectInputStream objIn = new ObjectInputStream(dis);

                    try {
                        matrix = (int[][]) objIn.readObject();
                        threadCount = dis.readInt();
                    } catch (IOException ex){
                        dos.write(0);
                    }

                    System.out.println("Matrix: " + Arrays.deepToString(matrix));
                    System.out.println("Threads: " + threadCount);
                    dos.write(1);

                }
                else if(received.equals("execute")){
                    if(matrix == null){
                        dos.write(0);
                    } else {
                        dos.write(1);

                        matrixResult = new Matrix(matrix);

                        Matrix finalMatrixResult = matrixResult;
                        int finalThreadCount = threadCount;

                        Server.future = CompletableFuture.supplyAsync(() ->
                                Server.calculate(finalMatrixResult, finalThreadCount)
                        );
                    }

                }
                else if(received.equals("result")){
                    if(!Server.future.isDone()){
                        dos.write(0);
                    }
                    else {
                        dos.write(1);
                        result = matrixResult.getResultArray();
                        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
                        ObjectOutputStream objOut = new ObjectOutputStream(byteOut);
                        objOut.writeObject(result);
                        byte[] matrixBytes = byteOut.toByteArray();
                        dos.write(matrixBytes);
                        //dos.writeUTF("Result has sent");
                    }
                }
                else if(received.equals("exit")) {
                    dos.writeUTF("Close connection");
                    this.s.close();
                    break;
                } else{
                    dos.writeUTF("Unknown command");
                }

                // creating Date object

            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }

        try
        {
            // closing resources
            this.dis.close();
            this.dos.close();

        }catch(IOException e){
            e.printStackTrace();
        }
    }
}

//class TaskProcessingThread{
//
//    private final BlockingQueue<Data> taskQueue = new LinkedBlockingQueue<>();
//
//
//    public TaskProcessingThread(){}
//
//    public void addTask(Data data){
//        taskQueue.add(data);
//    }
//
//
//    @Override
//    public synchronized void run() {
//        try {
//            while (true) {
//                Data data = taskQueue.take();
//                Parallel.doAction(data.getMatrix(), data.getThreadCount(), LAB1);
//            }
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//}