package com.company.homeWork5;

public class Cat extends Animal{
    private static int count = 0;

    Cat(){
        this.jumpLimit = random.nextFloat()+2;
        this.runLimit = random.nextInt(100)+150;
        id = ++count;
    }

    @Override
    protected boolean run(int meters){
        return meters < runLimit;
    }

    @Override
    protected boolean jump(int meters){
        return meters < jumpLimit;
    }

    @Override
    public String toString(){
        return "Кот номер "+id+" ";
    }
}
