package com.company.homeWork5;

public class Dog extends Animal{

    protected int swimLimit;
    private static int count = 0;

    Dog(){
        this.jumpLimit = random.nextFloat();
        this.runLimit = random.nextInt(200)+400;
        this.swimLimit = random.nextInt(10)+10;
        id = ++count;
    }

    protected boolean swim(int meters){
        return meters < swimLimit;
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
        return "Собака номер "+id+" ";
    }
}
