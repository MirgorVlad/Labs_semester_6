package com.my.parallel_computing.lab2;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;

public class ThreadPool {
    private static final int QUEUE_LIMIT = 15;
    private final PriorityBlockingQueue<Task> taskQueue = new PriorityBlockingQueue<>(QUEUE_LIMIT);
    private final List<Thread> threadList;

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

    public boolean isRunning() {
        return isRunning;
    }

    public boolean empty(){
        return taskQueue.isEmpty();
    }

    public int size(){
        return taskQueue.size();
    }

    public void clear(){
        taskQueue.clear();
    }

    private final class TaskWorker implements Runnable {

        @Override
        public void run() {
            while (isRunning) {
                Task nextTask = taskQueue.poll();
                if (nextTask != null) {
                    nextTask.run();
                }
            }
        }
    }
}
