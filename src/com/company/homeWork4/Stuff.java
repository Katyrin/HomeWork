package com.company.homeWork4;

public class Stuff {
    private String fullName;
    private int salary;
    private int age;
    private static int count = 0;
    private int id;

    Stuff(String fullName, int salary, int age){
        this.fullName = fullName;
        this.salary = salary;
        this.age = age;
        id = ++count;
    }

    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public int getSalary() {
        return salary;
    }

    public int getAge() {
        return age;
    }

    public void increasedSalary(){
        if (age>45){
            salary += 5000;
        }
    }


}
