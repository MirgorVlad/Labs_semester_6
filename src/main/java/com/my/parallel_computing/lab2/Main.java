package com.my.parallel_computing.lab2;

public class Main {
    public static final int THREAD_COUNT = 4;

    public static void main(String[] args) throws InterruptedException {

        ThreadPool threadPool = new ThreadPool(THREAD_COUNT);

        for (int i = 0; i < 100; i++){

            threadPool.execute(new Task(i));
            Thread.sleep(1000);
        }

        threadPool.shutdown();
    }

    //Max time, when queue is full: 4.53s; min: 1.26;
    //Rejected tasks: 28/100
}
