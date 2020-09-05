package com.company;

public class Main {

    private static double methodOne(int a, int b, int c, int d){
        return a * (b + ((double)c / d));
    }

    private static boolean methodTwo(int a, int b){
        int c = a + b;
        if (c>=10 && c<=20){
            return true;
        }else
            return false;
    }

    private static String methodThree(int a){
        if (a >= 0){
            return "Число положительное";
        }else
            return "Число отрицательное";
    }

    private static String methodFour(String name){
        return "Привет " + name;
    }

    private static String methodFive(int year){
        if (year%4==0) {
            if (year%100==0) {
                if (year%400 == 0)
                    return year + " год считается високосным";
                return year + " год НЕ считается високосным";
            }
            return year + " год считается високосным";
        } else
            return year + " год НЕ считается високосным";
    }

    public static void main(String[] args) {
        System.out.println(methodOne(4,7,34,5));
        System.out.println(methodTwo(5,14));
        System.out.println(methodTwo(5,24));
        System.out.println(methodThree(0));
        System.out.println(methodThree(-5));
        System.out.println(methodFour("Roman"));
        System.out.println(methodFive(900));
    }
}
