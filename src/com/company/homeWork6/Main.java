package com.company.homeWork6;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;
import java.util.Scanner;

public class Main {
    private static void crateFileTXT(String text,String fileName){
        try {
            FileOutputStream fos = new FileOutputStream(fileName,true);
            fos.write(text.getBytes());
            fos.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void mergeText(String fileName1, String fileName2){
        StringBuilder sb = new StringBuilder();
        try {
            FileOutputStream fos = new FileOutputStream("mergeText.txt",true);
            FileInputStream fis1 = new FileInputStream(fileName1),fis2 = new FileInputStream(fileName2);
            int b;
            while ((b = fis1.read()) != -1){
                sb.append((char) b);
            }
            fis1.close();
            sb.append("\n");
            while ((b = fis2.read()) != -1){
                sb.append((char)b);
            }
            fis2.close();
            fos.write(sb.toString().getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean searchWordInFile(String fileName, String word){

        try {
            Scanner scanner = new Scanner(new FileInputStream(fileName));
            while (scanner.hasNext()) {
                if (word.equals(scanner.next()))
                    return true;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean searchWordInFolder(String pathName, String word){
        File folder = new File(pathName);
        String[] files = folder.list();
        if (files != null){
            for (int i = 0;i < files.length;i++){
                if (new File(files[i]).isFile()){
                    if (searchWordInFile(files[i],word))
                        return true;
                }
            }
        }
        return  false;
    }

    public static void main(String[] args) {
        String text1 = "Writes {@code b.length} bytes from the specified byte array";
        String text2 = "Writes the specified byte to this file output stream.";
        String fileName1 = "file1.txt";
        String fileName2 = "file2.txt";
        String pathName = "/Users/roman/Desktop/GeekBrains/Java Core Базовый уровень/HomeWork1";
        crateFileTXT(text1,fileName1);
        crateFileTXT(text2,fileName2);
        mergeText(fileName1,fileName2);
        System.out.println(searchWordInFile(fileName1, "kgugkhgcxdg"));
        System.out.println(searchWordInFile(fileName1, "bytes"));
        System.out.println(searchWordInFolder(pathName,"bytes"));
        System.out.println(searchWordInFolder(pathName,"djsvad,jvn,sdnv,mdn"));
    }
}
