package com.company.homeWork7;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameWindow extends JFrame {
    private static final int WIN_WIDTH = 507;
    private static final int WIN_HEIGHT = 555;
    private static final int WIN_POSX = 400;
    private static final int WIN_POSY = 150;

    private Map map;

    GameWindow(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WIN_WIDTH,WIN_HEIGHT);
        setLocation(WIN_POSX,WIN_POSY);
        setTitle("TicTacToe");
        setResizable(false);
        JButton buttonStart = new JButton("Start");
        JButton buttonStop = new JButton("<html><body><b>Exit</b></body></html>");

        JPanel panelBottom = new JPanel();
        panelBottom.setLayout(new GridLayout(1,2));
        panelBottom.add(buttonStart);
        panelBottom.add(buttonStop);
        map = new Map();

        SettingsWindow settingsWindow = new SettingsWindow(this);

        buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                settingsWindow.setVisible(true);
            }
        });
        buttonStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        add(map,BorderLayout.CENTER);
        add(panelBottom,BorderLayout.SOUTH);
        setVisible(true);

    }

    public void acceptSettings(int gameMode,int filedSizeX,int filedSizeY,int winLength,boolean isPlayWithAI){
        map.startNewGame(gameMode,filedSizeX,filedSizeY,winLength,isPlayWithAI);
    }
}
