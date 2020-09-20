package com.company.homeWork5;

import java.util.Random;

public abstract class Animal {

    protected int runLimit;
    protected float jumpLimit;
    protected int id;
    protected Random random = new Random();

    protected abstract boolean run(int meters);

    protected abstract boolean jump(int meters);

    public abstract String toString();
}
