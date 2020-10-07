package com.company.homeWork2_1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

public class MainCircles extends JFrame {
    private static final int POS_X = 400;
    private static final int POS_Y = 200;
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;
    static int count = 10;

    Sprite[] sprites = new Sprite[count];

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainCircles();
            }
        });
    }

    private MainCircles(){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(POS_X,POS_Y,WINDOW_WIDTH,WINDOW_HEIGHT);
        GameCanvas canvas = new GameCanvas(this);
        add(canvas);
        initApplication();
        setTitle("Circles");
        setVisible(true);

        // обработчик нажатия кнопок мыши
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getButton() == MouseEvent.BUTTON1){
                    increaseSize();
                }
                if (e.getButton() == MouseEvent.BUTTON3){
                    if (sprites.length > 0){
                        decreaseSize();
                    }
                }
            }
        });

    }
    // метод добавления
    private void increaseSize(){
        Sprite[] newSprites = Arrays.copyOf(sprites,sprites.length + 1);
        newSprites[newSprites.length-1] = new Ball();
        sprites = newSprites;
    }
    // метод удаления
    private void decreaseSize(){
        Sprite[] newSprites = Arrays.copyOf(sprites,sprites.length - 1);
        sprites = newSprites;
    }
    private void initApplication(){
        for (int i = 0;i < sprites.length;i++){
            sprites[i] = new Ball();
        }
    }
    public void onDrawFrame(GameCanvas canvas, Graphics g,float deltaTime){
        update(canvas,deltaTime);
        render(canvas,g);
    }
    private void update(GameCanvas canvas, float deltaTime){
        for (int i = 0;i < sprites.length;i++){
            sprites[i].update(canvas,deltaTime);
        }
    }
    private void render(GameCanvas canvas, Graphics g){
        for (int i = 0;i < sprites.length;i++){
            sprites[i].render(canvas,g);
        }
    }
}
