package com.company.homeWork4;

import java.util.Random;
import java.util.Scanner;

public class Main {

    // 7 упражнение
    public static int averageSalary(Stuff[] stuffs){
        int avg = 0;
        for (int i = 0;i < stuffs.length;i++){
            avg += stuffs[i].getSalary();
        }
        return avg/stuffs.length;
    }

    public static void main(String[] args) {

        // 4 упражнение
        Stuff e0 = new Stuff("Иванов Иван Иванович", 50000,20);
        System.out.println("Сотрудник: "+e0.getFullName()+" Возраст: "+e0.getAge());

        // 5 упражнение
        Stuff[] stuffs = new Stuff[5];
        Random random = new Random();
        Scanner in = new Scanner(System.in);
        for (int i = 0;i<stuffs.length;i++){
            System.out.println("Введите ФИО сотрудника № "+(i+1));
            stuffs[i] = new Stuff(in.nextLine(),random.nextInt(200000),random.nextInt(65));
        }
        for (int i = 0;i<stuffs.length;i++){
            if (stuffs[i].getAge() > 40){
                System.out.println("Сотрудник: "+stuffs[i].getFullName()+" Возраст: "+stuffs[i].getAge()
                        +" Зарплата: "+stuffs[i].getSalary());
            }
        }

        System.out.println();

        // проверка 6 задания
        for (int i = 0;i<stuffs.length;i++){
            if (stuffs[i].getAge() > 40){
                stuffs[i].increasedSalary();
                System.out.println("Сотрудник: "+stuffs[i].getFullName()+" Возраст: "+stuffs[i].getAge()
                        +" Зарплата: "+stuffs[i].getSalary());
            }
        }

        System.out.println();
        System.out.println("Средняя зарплата: " + averageSalary(stuffs));

        System.out.println();

        // проверка 8 упражнения
        for (int i = 0;i<stuffs.length;i++){
            System.out.println("id: "+stuffs[i].getId()+" Сотрудник: "+stuffs[i].getFullName()+
                    " Возраст: "+stuffs[i].getAge() +" Зарплата: "+stuffs[i].getSalary());
        }

        System.out.println("id: "+e0.getId()+" Сотрудник: "+e0.getFullName()+
                " Возраст: "+e0.getAge() +" Зарплата: "+e0.getSalary());


    }
}
