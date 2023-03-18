package com.my.parallel_computing.lab2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;

public class ThreadPool {
    private static final int QUEUE_LIMIT = 15;
    private final PriorityBlockingQueue<Task> taskQueue = new PriorityBlockingQueue<>(QUEUE_LIMIT);
    List<Thread> threadList;

    private volatile boolean isRunning = true;


    public ThreadPool(int threadCount){
        threadList = new ArrayList<>(threadCount);
        for (int i = 0; i < threadCount; i++) {
            Thread thread = new Thread(new TaskWorker());
            threadList.add(thread);
            thread.start();
        }
    }

    public void execute(Task task) {
        if (isRunning) {
            if(taskQueue.size() < QUEUE_LIMIT) {
                taskQueue.offer(task);
                System.out.println("Task " + task.getNumber() + " added to queue");
            } else {
                System.out.println("Queue is full");
            }
        }
    }

    public void shutdown() {
        isRunning = false;
    }

    public void interrupt(){
        for(Thread thread : threadList){
            thread.interrupt();
        }
    }

    private final class TaskWorker implements Runnable {

        @Override
        public void run() {

            while (isRunning) {

                if(Thread.interrupted()){
                    break;
                }

                Task nextTask = taskQueue.poll();
                if (nextTask != null) {
                    nextTask.run();
                }
            }
        }
    }
}
