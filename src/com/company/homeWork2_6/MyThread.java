package com.company.homeWork2_6;

public class MyThread extends Thread {
    public MyThread(String name){
        super(name);
        start();
    }

    @Override
    public void run(){
        System.out.println("MyThread started");
        for (long i = 0; i < 10_000_000_000L; i++){
            long a = i * 432;
        }
        System.out.println("MyThread stopped");
    }
}
