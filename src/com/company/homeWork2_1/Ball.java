package com.company.homeWork2_1;

import java.awt.*;

public class Ball extends Sprite {
    private final Color color;
    private float vX;
    private float vY;

    Ball(){
        halfHeight = 20 + (float)(Math.random() * 50f);
        halfWidth = halfHeight;
        color = new Color((int)(Math.random() * 255),
                (int)(Math.random() * 255),
                (int)(Math.random() * 255));
        vX = (float)(100f + (Math.random() * 200f));
        vY = (float)(100f + (Math.random() * 200f));
    }
    @Override
    void update(GameCanvas canvas,float deltaTime){
        x += vX * deltaTime;
        y += vY * deltaTime;
        if (getLeft() < canvas.getLeft()){
            setLeft(canvas.getLeft());
            vX = -vX;
        }
        if (getRight() > canvas.getRight()){
            setRight(canvas.getRight());
            vX = -vX;
        }
        if (getTop() < canvas.getTop()){
            setTop(canvas.getTop());
            vY = -vY;
        }
        if (getBottom() > canvas.getBottom()){
            setBottom(canvas.getBottom());
            vY = -vY;
        }
    }

    @Override
    void render(GameCanvas canvas,Graphics g){
        g.setColor(color);
        g.fillOval((int)getLeft(),(int)getTop(),(int)getWidth(),(int)getHeight());
    }
}
