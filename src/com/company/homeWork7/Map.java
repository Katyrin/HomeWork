package com.company.homeWork7;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class Map extends JPanel {
    public static final int MODE_HVH = 0;
    public static final int MODE_HVA = 1;

    private int[][] field;
    private int fieldSizeX;
    private int fieldSizeY;
    private int winLength;
    private int cellWidth;
    private int cellHeight;

    private static final Random RANDOM = new Random();
    private static final int DOT_EMPTY = 0;
    private static final int DOT_HUMAN = 1;
    private static final int DOT_AI = 2;
    private static final int DOT_PADDING = 5;

    private int stateGameOver;
    private static final int STATE_DRAW = 0;
    private static final int STATE_WIN_HUMAN = 1;
    private static final int STATE_WIN_AI =2;
    private boolean isGameOver;
    private boolean initialized;

    private static final String MSG_WIN_HUMAN = "Победил игрок!";
    private static final String MSG_WIN_AI = "Победил компьютер!";
    private static final String MSG_DRAW = "Ничья!";

    private boolean isPlayWithAI;
    private static int humanCount = 0;
    private static int aiCount = 0;

    Map(){
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                if (isPlayWithAI){
                    update(e,DOT_HUMAN,STATE_WIN_HUMAN);
                }else {
                    if (humanCount == aiCount){
                        update(e,DOT_HUMAN,STATE_WIN_HUMAN);
                    }else {
                        update(e,DOT_AI,STATE_WIN_AI);
                    }
                }
            }
        });
        initialized = false;
    }

    private void update(MouseEvent e,int dot,int stateWin) {
        if (dot == DOT_HUMAN)
            humanCount++;
        else
            aiCount++;

        if (isGameOver || !initialized)return;
        int cellX = e.getX()/cellWidth;
        int cellY = e.getY()/cellHeight;
        if (!isValidCell(cellX,cellY) || !isEmptyCell(cellX,cellY))
            return;
        field[cellY][cellX] = dot;
        repaint();
        if (checkEndGame(dot,stateWin))
            return;

        if (isPlayWithAI){
            aiTurn();
            repaint();
            if (checkEndGame(DOT_AI,STATE_WIN_AI))
                return;
        }
    }

    private boolean checkEndGame(int dot, int stateGameOver){
        if (checkWin(dot)){
            this.stateGameOver = stateGameOver;
            isGameOver = true;
            repaint();
            return true;
        }
        if (isMapFull()){
            this.stateGameOver = STATE_DRAW;
            isGameOver = true;
            repaint();
            return true;
        }
        return false;
    }

    void startNewGame(int gameMode,int filedSizeX,int filedSizeY,int winLength,boolean isPlayWithAI){
        this.fieldSizeX = filedSizeX;
        this.fieldSizeY = filedSizeY;
        this.winLength = winLength;
        this.isPlayWithAI = isPlayWithAI;
        field = new int[filedSizeY][filedSizeX];
        initialized = true;
        isGameOver = false;
        humanCount = 0;
        aiCount = 0;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        render(g);
    }

    private void render(Graphics g) {
        if (!initialized)return;
        int width = getWidth();
        int height = getHeight();
        cellWidth = width/fieldSizeX;
        cellHeight = height/fieldSizeY;
        g.setColor(Color.BLACK);
        for (int i = 0;i < fieldSizeY;i++){
            int y = i * cellHeight;
            g.drawLine(0,y,width,y);
        }
        for (int i = 0;i < fieldSizeX;i++){
            int x = i * cellWidth;
            g.drawLine(x,0,x,height);
        }
        for (int y = 0;y < fieldSizeY;y++){
            for (int x = 0;x < fieldSizeX;x++){
                if (isEmptyCell(x,y))
                    continue;
                if (field[y][x] == DOT_HUMAN){
                    g.setColor(new Color(1,1,255));
                    g.fillOval(x * cellWidth + DOT_PADDING,
                            y * cellHeight + DOT_PADDING,
                            cellWidth - DOT_PADDING * 2,
                            cellHeight - DOT_PADDING *2);
                } else if (field[y][x] == DOT_AI){
                    g.setColor(Color.RED);
                    g.fillRect(x * cellWidth + DOT_PADDING,
                            y * cellHeight + DOT_PADDING,
                            cellWidth - DOT_PADDING * 2,
                            cellHeight - DOT_PADDING *2);
                } else
                    throw new RuntimeException(
                            String.format("Can not recognize cell field[%d][%d]: %d",y,x,field[y][x])
                    );
            }
        }
        if (isGameOver){
            showMessageGameOver(g);
        }
    }

    private void showMessageGameOver(Graphics g){
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0,200,getWidth(),70);
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Times new roman",Font.BOLD,48));
        switch (stateGameOver){
            case STATE_DRAW:
                g.drawString(MSG_DRAW,180,getHeight()/2);
                break;
            case STATE_WIN_AI:
                g.drawString(MSG_WIN_AI,20,getHeight()/2);
                break;
            case STATE_WIN_HUMAN:
                g.drawString(MSG_WIN_HUMAN,70,getHeight()/2);
                break;
            default:
                throw new RuntimeException("Unexpected gameOver state: " + stateGameOver);
        }
    }

    // Ход компьютера
    private void aiTurn() {
        if(turnAIWinCell()) return;		// проверим, не выиграет-ли игрок на следующем ходу
        if(turnHumanWinCell()) return;	// проверим, не выиграет-ли комп на следующем ходу
        int x, y;
        do {							// или комп ходит в случайную клетку
            x = RANDOM.nextInt(fieldSizeX);
            y = RANDOM.nextInt(fieldSizeY);
        } while (!isEmptyCell(x, y));
        field[y][x] = DOT_AI;
    }
    // Проверка, может ли выиграть комп
    private boolean turnAIWinCell() {
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX; j++) {
                if (isEmptyCell(j, i)) {				// поставим нолик в каждую клетку поля по очереди
                    field[i][j] = DOT_AI;
                    if (checkWin(DOT_AI)) return true;	// если мы выиграли, вернём истину, оставив нолик в выигрышной позиции
                    field[i][j] = DOT_EMPTY;			// если нет - вернём обратно пустоту в клетку и пойдём дальше
                }
            }
        }
        return false;
    }
    // Проверка, выиграет-ли игрок своим следующим ходом
    private boolean turnHumanWinCell() {
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX; j++) {
                if (isEmptyCell(j, i)) {
                    field[i][j] = DOT_HUMAN;			// поставим крестик в каждую клетку по очереди
                    if (checkWin(DOT_HUMAN)) {			// если игрок победит
                        field[i][j] = DOT_AI;			// поставить на то место нолик
                        return true;
                    }
                    field[i][j] = DOT_EMPTY;			// в противном случае вернуть на место пустоту
                }
            }
        }
        return false;
    }
    // проверка на победу
    private  boolean checkWin(int c) {
        for (int i = 0; i < fieldSizeX; i++) {			// ползём по всему полю
            for (int j = 0; j < fieldSizeY; j++) {
                if (checkLine(i, j, 1, 0, winLength, c)) return true;	// проверим линию по х
                if (checkLine(i, j, 1, 1, winLength, c)) return true;	// проверим по диагонали х у
                if (checkLine(i, j, 0, 1, winLength, c)) return true;	// проверим линию по у
                if (checkLine(i, j, 1, -1, winLength, c)) return true;	// проверим по диагонали х -у
            }
        }
        return false;
    }
    // проверка линии
    private boolean checkLine(int x, int y, int vx, int vy, int len, int c) {
        final int far_x = x + (len - 1) * vx;			// посчитаем конец проверяемой линии
        final int far_y = y + (len - 1) * vy;
        if (!isValidCell(far_x, far_y)) return false;	// проверим не выйдет-ли проверяемая линия за пределы поля
        for (int i = 0; i < len; i++) {					// ползём по проверяемой линии
            if (field[y + i * vy][x + i * vx] != c) return false;	// проверим одинаковые-ли символы в ячейках
        }
        return true;
    }
    // ничья?
    private boolean isMapFull() {
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX; j++) {
                if (field[i][j] == DOT_EMPTY) return false;
            }
        }
        return true;
    }
    // ячейка-то вообще правильная?
    private boolean isValidCell(int x, int y) {
        return x >= 0 && x < fieldSizeX && y >= 0 && y < fieldSizeY;
    }
    // а пустая?
    private boolean isEmptyCell(int x, int y) {
        return field[y][x] == DOT_EMPTY;
    }
}
