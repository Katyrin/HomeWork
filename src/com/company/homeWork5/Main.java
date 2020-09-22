package com.company.homeWork5;

public class Main {
    static int jump = 1;
    static int swim = 7;
    static int run = 200;
    public static void main(String[] args) {
        Cat[] cats = {new Cat(),new Cat(),new Cat(),new Cat(),new Cat()};
        Dog[] dogs = {new Dog(),new Dog(),new Dog(),new Dog(),new Dog()};

        for (int i = 0;i<cats.length;i++){
            System.out.println(cats[i].toString() + (cats[i].jump(jump)?"":"не ")+"перепрыгнул препятствие высотой "+jump);
            System.out.println(cats[i].toString() + (cats[i].run(run)?"":"не ")+"пробежал дистанцию длинной "+run);
        }

        System.out.println();

        for (int i = 0;i<dogs.length;i++){
            System.out.println(dogs[i].toString() + (dogs[i].jump(jump)?"":"не ")+"перепрыгнула препятствие высотой "+jump);
            System.out.println(dogs[i].toString() + (dogs[i].run(run)?"":"не ")+"пробежала дистанцию длинной "+run);
            System.out.println(dogs[i].toString() + (dogs[i].swim(swim)?"":"не ")+"проплыла дистанцию длинной "+swim);
        }
    }
}
