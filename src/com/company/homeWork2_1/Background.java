package com.company.homeWork2_1;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class Background {
    private int r = 255;
    private int g = 0;
    private int b = 0;

    // простой вариант рандомного смены фона
    private Color changeColor() {
        return new Color((int)(Math.random() * 255),
                (int)(Math.random() * 255),
                (int)(Math.random() * 255));
    }

    public void timer(GameCanvas canvas){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                canvas.setBackground(nextColor());
                canvas.repaint();
            }
        },0,10);
    }

    private void nextRGB() {
        if (r == 255 && g < 255 && b == 0) {
            g++;
        }
        if (g == 255 && r > 0 && b == 0) {
            r--;
        }
        if (g == 255 && b < 255 && r == 0) {
            b++;
        }
        if (b == 255 && g > 0 && r == 0) {
            g--;
        }
        if (b == 255 && r < 255 && g == 0) {
            r++;
        }
        if (r == 255 && b > 0 && g == 0) {
            b--;
        }
    }

    public Color nextColor() {
        nextRGB();
        return makeColor();
    }

    private Color makeColor() {
        return new Color(r, g, b);
    }
}
