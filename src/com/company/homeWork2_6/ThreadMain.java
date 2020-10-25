package com.company.homeWork2_6;

public class ThreadMain {
    private static long a;
    private static long b;
    private static long c;

    private static Object mon = new Object();
    private static Object mon2 = new Object();

    private static void incAllVars(){
        synchronized (mon){
            for (int i = 0; i < 1_000_000 ; i++) {
                a = a + 1;
                b = b + 1;
                c = c + 1;
            }
        }
        synchronized (mon2){
            String vars = String.format("a=%d, b=%d, c=%d", a, b, c);
            System.out.println(vars);
        }
    }

    private synchronized static void incAllVars2(){
        for (int i = 0; i < 1_000_000 ; i++) {
            a = a + 1;
            b = b + 1;
            c = c + 1;
        }
        String vars = String.format("a=%d, b=%d, c=%d", a, b, c);
        System.out.println(vars);
    }

    public static void main(String[] args) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                incAllVars2();
            }
        };
        new Thread(r,"Thread-0").start();
        new Thread(r,"Thread-1").start();
        new Thread(r,"Thread-2").start();
    }

    private static void joinExample() throws InterruptedException {
        MyThread t0 = new MyThread("Thread");
        t0.join();
        System.out.println("Main finished");
    }

    public static void interruptExample(MyThread t0){
        t0.interrupt();
    }

    private static void threadExamples() throws InterruptedException {
        System.out.println("Hello from thread " + Thread.currentThread().getName());
        MyThread t0 = new MyThread("MyThread");
        t0.run();
        Thread.sleep(2000);
        System.out.println("Hello from thread " + Thread.currentThread().getName());
        t0.start();
        Thread.sleep(2000);
        System.out.println("Hello from thread " + Thread.currentThread().getName());

        Runnable r0 = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello from runnable " + Thread.currentThread().getName());
            }
        };

        new Thread(r0).start();
    }

}
