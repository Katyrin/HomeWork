package com.company;

import java.util.Arrays;

public class HomeWork2 {

    // else if(arr[i] == 1) не стал писать так как задание других элементов кроме 0 и 1 не предусматривает
    private static int[] methodOne(int[] arr){
        for (int i = 0;i < arr.length;i++){
            if (arr[i] == 0)
                arr[i] = 1;
            else
                arr[i] = 0;
        }
        return arr;
    }

    private static int[] methodTwo(int[] arr){
        int number = 1;
        for (int i = 0;i < arr.length;i++){
            arr[i] = number;
            number += 3;
        }
        return arr;
    }

    private static int[] methodThree(int[] arr){
        for (int i = 0;i < arr.length;i++){
            if(arr[i] < 6)
                arr[i] = arr[i] * 2;
        }
        return arr;
    }

    private static int methodFourMax(int[] arr){
        int max = arr[0];
        for (int i = 0;i < arr.length;i++){
            if (max < arr[i])
                max = arr[i];
        }
        return max;
    }

    private static int methodFourMin(int[] arr){
        int min = arr[0];
        for (int i = 0;i < arr.length;i++){
            if (min > arr[i])
                min = arr[i];
        }
        return min;
    }

    private static int[][] methodFive(int[][] arr){
        for (int i = 0;i < arr.length;i++){
            arr[i][i] = 1;
        }
        return arr;
    }

    private static boolean methodSix(int[] arr){
        int firstCount = 0;

        for (int i = 0;i < arr.length;i++){
            int secondCount = 0;
            for (int j = i+1; j < arr.length; j++ ){
                secondCount += arr[j];
            }
            firstCount += arr[i];

            if (firstCount == secondCount)
                return true;
        }
        return false;
    }

    private static int[] methodSeven(int[] arr, int a){
        int[] arrOutput = new int[arr.length];
        int count = 0;
        if (a < 0) {
            a = a * -1;
            for (int i = a % arr.length; i < arr.length; i++) {
                arrOutput[count] = arr[i];
                count++;
            }
            for (int i = 0;i < a%arr.length;i++){
                arrOutput[count] = arr[i];
                count++;
            }
        } else {
            for (int i = arr.length-(a % arr.length);i < arr.length;i++){
                arrOutput[count] = arr[i];
                count++;
            }
            for (int i = 0; i < arr.length-(a % arr.length); i++) {
                arrOutput[count] = arr[i];
                count++;
            }
        }
        return arrOutput;
    }

    private static int[] methodEight(int[] arr,int a){
        int arrElement;
        if (a > 0){
            for (int i = 0;i < a;i++){
                arrElement = arr[arr.length-1];
                for (int j = arr.length-2;j > -1;j--){
                    arr[j+1] = arr[j];
                }
                arr[0]=arrElement;
            }
        } else if (a < 0){
            a = a * -1;
            for (int i = 0;i < a;i++){
                arrElement = arr[0];
                for (int j = 0;j < arr.length-1;j++){
                    arr[j] = arr[j+1];
                }
                arr[arr.length-1] = arrElement;
            }
        }
        return arr;
    }



    public static void main(String[] args) {

        int[] arr = {1, 1, 0, 0, 1, 0, 1, 1, 0, 0};
        int[] eightArr = new int[8];
        int[] lessSixArr = {1, 5, 3, 2, 11, 4, 5, 2, 4, 8, 9, 1 };
        int[][] dArr = new int[5][5];
        int[] arr2 = {2, 1, 1, 2, 1};
        int[] arr3 = {1,2,3,4,5,6,7,28};

        System.out.println(Arrays.toString(methodOne(arr)));
        System.out.println(Arrays.toString(methodTwo(eightArr)));
        System.out.println(Arrays.toString(methodThree(lessSixArr)));
        System.out.println(methodFourMax(lessSixArr));
        System.out.println(methodFourMin(lessSixArr));
        System.out.println(Arrays.deepToString(methodFive(dArr)));
        System.out.println(methodSix(arr2));
        System.out.println(methodSix(arr3));
        System.out.println(Arrays.toString(methodSeven(arr3, -2)));
        System.out.println(Arrays.toString(methodSeven(arr3, 2)));

        System.out.println(Arrays.toString(methodEight(arr3,-2)));
        System.out.println(Arrays.toString(methodEight(arr3,3)));

    }
}
