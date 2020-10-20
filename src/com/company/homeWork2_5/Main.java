package com.company.homeWork2_5;

import java.util.Arrays;

public class Main {
    static final int size = 10000000;
    static final int h = size / 2;

    public static void main(String[] args) {
        float[] a = methodOne();
        System.out.println();
        float[] b = methodTwo();
        // сравниваю получившиеся массивы
        System.out.println(Arrays.equals(a, b));


    }

    private static float[] methodOne(){
        float[] arr = new float[size];
        for (int i = 0; i < size; i++) {
            arr[i] = 1;
        }
        long a = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        long b = System.currentTimeMillis();
        System.out.println("Время выполнения метода 1 = " + (b-a));
        return arr;
    }

    private static float[] methodTwo(){
        float[] arr = new float[size];
        for (int i = 0; i < size; i++) {
            arr[i] = 1;
        }
        float[] a1 = new float[h];
        float[] a2 = new float[h];

        long a = System.currentTimeMillis();
        System.arraycopy(arr, 0, a1, 0, h);
        System.arraycopy(arr, h, a2, 0, h);

        Thread thread1 = new Thread(() -> firstHalf(a1));
        thread1.start();
        Thread thread2 = new Thread(() -> secondHalf(a2));
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.arraycopy(a1, 0, arr, 0, h);
        System.arraycopy(a2, 0, arr, h, h);
        long b = System.currentTimeMillis();
        System.out.println("Время выполнения метода 2 = " + (b-a));
        return arr;
    }

    private static void firstHalf(float[] fa){
        for (int i = 0; i < h; i++) {
            fa[i] = (float)(fa[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
    }
    private static void secondHalf(float[] fa){
        for (int i = 0; i < h; i++) {
            fa[i] = (float)(fa[i] * Math.sin(0.2f + (i + h) / 5) * Math.cos(0.2f + (i + h) / 5) * Math.cos(0.4f + (i + h) / 2));
        }
    }
}
