package com.my.parallel_computing.lab2;

import java.util.Random;

public class Task implements Runnable, Comparable<Task>{
    private static final int MIN_TIME = 5;
    private static final int MAX_TIME = 10;
    private final int  timeOfTask;
    private int number;

    Task(int number){
        this.number = number;
        Random random = new Random();
        timeOfTask = random.nextInt((MAX_TIME-MIN_TIME) + 1) + MIN_TIME;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " started execution of task " + number);
        try {
            Thread.sleep(timeOfTask * 1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Thread.currentThread().getName() + " finished execution of task " + number);
    }

    @Override
    public int compareTo(Task o) {
        return this.timeOfTask - o.timeOfTask;
    }

    public int getNumber() {
        return number;
    }

    public int getTimeOfTask() {
        return timeOfTask;
    }
}
