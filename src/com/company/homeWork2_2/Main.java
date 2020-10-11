package com.company.homeWork2_2;

import java.io.IOException;
import java.util.Arrays;

public class Main {
    private static final String STR = "10 3 1 2\n2 3 2 2\n5 6 7 1\n300 3 1 0";

    private static String[][] arrayString(String str) throws NonSquareArrayException {
        String[] arrStr1 = str.split("\n");
        String[][] arrStr2 = new String[arrStr1.length][];
        for (int i = 0;i < arrStr1.length;i++){
            arrStr2[i] = arrStr1[i].split(" ");
        }
        if (arrStr2.length != 4 || arrStr2[0].length !=4 || arrStr2[1].length !=4 || arrStr2[2].length !=4 || arrStr2[3].length !=4){
            throw new NonSquareArrayException();
        }
        return arrStr2;
    }

    private static int methodTwo(String[][] arrayString) throws NonNumberException {
        int count = 0;
        for (int i = 0;i < arrayString.length;i++){
            for (int j = 0;j < arrayString[i].length;j++){
                if (!arrayString[i][j].matches("[-+]?\\d+")){
                    throw new NonNumberException(arrayString[i][j]);
                }
                count += Integer.parseInt(arrayString[i][j]);
            }
        }
        return count/2;
    }

    public static void main(String[] args) {
        try {
            System.out.println(Arrays.deepToString(arrayString(STR)));
            System.out.println(methodTwo(arrayString(STR)));
        }catch (NonSquareArrayException | NonNumberException e){
            e.printStackTrace();
        }

    }
}
