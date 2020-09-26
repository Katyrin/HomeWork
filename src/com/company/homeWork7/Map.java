package com.company.homeWork7;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Map extends JPanel {
    public static final int MODE_HVH = 0;
    public static final int MODE_HVA = 1;
    int filedSizeX = 3;
    int filedSizeY = 3;
    Integer height;
    JButton[][] btnTableCell;
    Map(){
        setBackground(Color.BLUE);
        initMap();

    }
    public void initMap(){
        setLayout(new GridLayout(filedSizeX,filedSizeY));
        btnTableCell = new JButton[filedSizeY][filedSizeX];
        for (int i = 0; i < btnTableCell.length; i++){
            for (int j = 0; j < btnTableCell.length; j++){
                btnTableCell[i][j] = new JButton();
                int finalI = i;
                int finalJ = j;
                // метод setMargin() у кнопки почемуто не сработал и я решил установить размер
                // текста кнопки через HTML в ActionListener
                //btnTableCell[i][j].setMargin(new Insets(0, 0, 0, 0));

                btnTableCell[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        height = 120/btnTableCell.length;
                        System.out.println(height);
                        btnTableCell[finalI][finalJ].setText("<html><body><b style=\"font-size: "+ height.toString()
                                + "px\">X</b></body></html>");


                    }
                });
                add(btnTableCell[i][j]);

            }
        }
    }
    void startNewGame(int gameMode,int filedSizeX,int filedSizeY,int winLength){
        this.filedSizeX = filedSizeX;
        this.filedSizeY = filedSizeY;
        removeAll();
        repaint();
        //System.out.printf("mode:%d, size:%d, win:%d\n", gameMode, filedSizeX, winLength);
        initMap();
        revalidate();
    }
}
