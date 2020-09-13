package com.company;

import java.util.Random;
import java.util.Scanner;

public class HomeWork3 {
    private static final char DOT_HUMAN = 'X';
    private static final char DOT_AI = 'O';
    private static final char DOT_EMPTY = '.';
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final Random RANDOM = new Random();
    private static char[][] field;
    private static int fieldSizeX;
    private static int fieldSizeY;
    private static final int FIELD_SIZE = 5;
    private static final int WIN_SIZE = 4;

    //init field
    private static void initField() {
        fieldSizeY = FIELD_SIZE;
        fieldSizeX = FIELD_SIZE;
        field = new char[fieldSizeY][fieldSizeX];
        for (int y = 0; y < fieldSizeY; y++) {
            for (int x = 0; x < fieldSizeX; x++) {
                field[y][x] = DOT_EMPTY;
            }
        }
    }

    // printField
    private static void printField() {
        System.out.print("+");
        for (int i = 0; i < fieldSizeX * 2 + 1; i++)
            System.out.print((i % 2 == 0) ? "-" : i / 2 + 1);
        System.out.println();

        for (int i = 0; i < fieldSizeY; i++) {
            System.out.print(i + 1 + "|");
            for (int j = 0; j < fieldSizeX; j++)
                System.out.print(field[i][j] + "|");
            System.out.println();
        }

        for (int i = 0; i <= fieldSizeX * 2 + 1; i++)
            System.out.print("-");
        System.out.println();
    }

    // humanTurn
    private static void humanTurn() {
        int x;
        int y;
        do {
            System.out.print("Введите координаты хода X и Y (от 1 до 3) через пробел >>> ");
            x = SCANNER.nextInt() - 1;
            y = SCANNER.nextInt() - 1;
        } while (!isCellValid(x, y) || !isCellEmpty(x, y));
        field[y][x] = DOT_HUMAN;
    }

    private static boolean isCellEmpty(int x, int y) {
        return field[y][x] == DOT_EMPTY;
    }

    private static boolean isCellValid(int x, int y) {
        return x >= 0 && x < fieldSizeX && y >= 0 && y < fieldSizeY;
    }

    // aiTurn
    private static void aiTurn() {
        int x;
        int y;
        do {
            int[] coordinates = checkFieldAI();
            x = coordinates[1];
            y = coordinates[0];
        } while (!isCellEmpty(x, y));
        field[y][x] = DOT_AI;
    }

    // проверка длинны при которой есть резон выстраивать победную прямую
    private static boolean aiScanLines(char[] arr, char c){
        int count = 0;
        for (int i=0;i<arr.length;i++){
            if (arr[i] != c)
                count++;
            else
                count =0;
            if (count == WIN_SIZE)
                return true;
        }
        return false;
    }
    // Поиск возможного хода
    private static int aiSearchSection(char[] arr){
        if (aiScanLines(arr, DOT_HUMAN)){
            for (int i=0;i<arr.length;i++){
                if (arr[i] == DOT_AI && i != 0){
                    if (arr[i-1] == DOT_EMPTY)
                        return i-1;
                } else if (arr[i] == DOT_AI && i != arr.length-1){
                    if (arr[i+1] == DOT_EMPTY)
                        return i+1;
                }
            }
        }
        return -1;
    }

    // проверка на последний победный ход
    private static boolean aiScanLinesWin(char[] arr, char c){
        int count = 0;
        for (int i=0;i<arr.length;i++){
            if (arr[i] == c)
                count++;
            else
                count =0;
            if (count == WIN_SIZE-1)
                return true;
        }
        return false;
    }

    // координаты последнего победного хода
    private static int aiLastStepToWin(char[] arr){
        if (aiScanLines(arr, DOT_HUMAN) && aiScanLinesWin(arr, DOT_AI)){
            for (int i=0;i<arr.length;i++){
                if (arr[i] == DOT_AI && i != 0){
                    if (arr[i-1] == DOT_EMPTY)
                        return i-1;
                } else if (i != arr.length-1){
                    if (arr[i+1] == DOT_EMPTY)
                        return i;
                }
            }
        }
        return -1;
    }

    // проверка проигрыша
    private static int aiBlockHuman(char[] arr){
        if (aiScanLines(arr, DOT_AI) && aiScanLinesWin(arr, DOT_HUMAN)){
            for (int i=0;i<arr.length;i++){
                if (arr[i] == DOT_HUMAN && i != 0){
                    if (arr[i-1] == DOT_EMPTY)
                        return i-1;
                } else if (i != arr.length-1){
                    if (arr[i+1] == DOT_EMPTY)
                        return i;
                }
            }
        }
        return -1;
    }

    // checkWin
    private static boolean checkWin(char c) {
        // hor
        if (field[0][0] == c && field[0][1] == c && field[0][2] == c) return true;
        if (field[1][0] == c && field[1][1] == c && field[1][2] == c) return true;
        if (field[2][0] == c && field[2][1] == c && field[2][2] == c) return true;

        // ver
        if (field[0][0] == c && field[1][0] == c && field[2][0] == c) return true;
        if (field[0][1] == c && field[1][1] == c && field[2][1] == c) return true;
        if (field[0][2] == c && field[1][2] == c && field[2][2] == c) return true;

        // dia
        if (field[0][0] == c && field[1][1] == c && field[2][2] == c) return true;
        if (field[0][2] == c && field[1][1] == c && field[2][0] == c) return true;
        return false;
    }

    // Метод для упражнения 2
    private static boolean checkWinMy(char c){
        for (int i = 0;i < FIELD_SIZE;i++){
            int count = 0;
            for (int j = 0;j < FIELD_SIZE;j++){
                if (field[i][j] == c)
                    count++;
                if (count == FIELD_SIZE)
                    return true;
            }
        }
        for (int i = 0;i < FIELD_SIZE;i++){
            int count = 0;
            for (int j = 0;j < FIELD_SIZE;j++){
                if (field[j][i] == c)
                    count++;
                if (count == FIELD_SIZE)
                    return true;
            }
        }
        int countLD = 0;
        for (int i = 0;i < FIELD_SIZE;i++){
            if (field[i][i] == c)
                countLD++;
            if (countLD == FIELD_SIZE)
                return true;
        }
        int countRD = 0;
        for (int i = 0;i < FIELD_SIZE;i++){
            if (field[i][FIELD_SIZE-1-i] == c)
                countRD++;
            if (countRD == FIELD_SIZE)
                return true;
        }
        return false;
    }

    // Метод для упражнения 3
    private static boolean checkWinFiveSize(char c){
        int countLDa = 0;
        int countLDb = 0;
        int countRDa = 0;
        int countRDb = 0;
        int countLD = 0;
        int countRD = 0;

        for (int i = 0;i < FIELD_SIZE;i++){
            int countV = 0;
            int countH = 0;
            // проверка вертикали и горизонтали
            for (int j = 0;j < FIELD_SIZE;j++){
                if (field[i][j] == c)
                    countV++;
                else
                    countV = 0;
                if (field[j][i] == c)
                    countH++;
                else
                    countH = 0;
                if (countV == WIN_SIZE || countH == WIN_SIZE)
                    return true;
            }
            // основные диагонали
            if (field[i][i] == c){
                countLD++;
            }else
                countLD=0;
            if (field[i][field.length-1-i] == c){
                countRD++;
            }else
                countRD=0;
            if (countLD == WIN_SIZE || countRD == WIN_SIZE)
                return true;
        }

        // проверка диагоналей
        for (int i = 0;i < FIELD_SIZE-1;i++){
            countLDa = 0;
            countLDb = 0;
            countRDa = 0;
            countRDb = 0;
            for (int j = 0;j < FIELD_SIZE-1;j++){
                if (i+j <= FIELD_SIZE-1){
                    if (field[j+i][j] == c)
                        countLDa++;
                    if (field[j][j+i] == c)
                        countLDb++;
                    if (field[j+i][FIELD_SIZE-1-j] == c)
                        countRDa++;
                    if (field[j][FIELD_SIZE-1-i-j] == c)
                        countRDb++;
                    if (countLDa == WIN_SIZE || countLDb == WIN_SIZE || countRDa == WIN_SIZE || countRDb == WIN_SIZE)
                        return true;
                }
            }
        }
        return false;
    }

    // разбивка поля на линии и возврат координат в массиве
    private static int[] checkFieldAI(){
        char[] linesLD = new char[FIELD_SIZE];
        char[] linesRD = new char[FIELD_SIZE];

        // прямые проверка победы
        for (int i = 0;i<FIELD_SIZE;i++){
            char[] linesV = new char[FIELD_SIZE];
            char[] linesH = new char[FIELD_SIZE];
            for (int j = 0;j<FIELD_SIZE;j++){
                linesV[j] = field[i][j];
                if (j == FIELD_SIZE-1){
                    if (aiLastStepToWin(linesV) >= 0){
                        return new int[]{aiLastStepToWin(linesV),i};
                    }
                }
                linesH[j] = field[j][i];
                if (j == FIELD_SIZE-1){
                    if (aiLastStepToWin(linesH) >= 0){
                        return new int[]{aiLastStepToWin(linesH),i};
                    }
                }
            }
            // основные диагонали
            linesLD[i] = field[i][i];
            if (i == FIELD_SIZE-1){
                if (aiLastStepToWin(linesLD) >= 0){
                    return new int[]{aiLastStepToWin(linesLD),i};
                }
            }
            linesRD[i] = field[i][field.length-1-i];
            if (i == FIELD_SIZE-1){
                if (aiLastStepToWin(linesRD) >= 0){
                    return new int[]{aiLastStepToWin(linesRD),i};
                }
            }
        }

        // диагонали проверка победы
        for (int i = 0;i < FIELD_SIZE-1;i++){
            char[] linesLDa = new char[FIELD_SIZE];
            char[] linesRDa = new char[FIELD_SIZE];
            char[] linesLDb = new char[FIELD_SIZE];
            char[] linesRDb = new char[FIELD_SIZE];

            for (int j = 0;j<FIELD_SIZE-1;j++){
                if (i+j <= FIELD_SIZE-1){
                    linesLDa[j] = field[j+i][j];
                    if (aiLastStepToWin(linesLDa) >= 0){
                        // поменял чтобы исправить баг
                        return new int[]{aiLastStepToWin(linesLDa),WIN_SIZE-j};
                    }
                    linesLDb[j] = field[j][j+i];
                    if (aiLastStepToWin(linesLDb) >= 0){
                        return new int[]{aiLastStepToWin(linesLDb),j};
                    }
                    linesRDa[j] = field[j+i][FIELD_SIZE-1-j];
                    if (aiLastStepToWin(linesRDa) >= 0){
                        return new int[]{aiLastStepToWin(linesRDa),j};
                    }
                    linesRDb[j] = field[j][FIELD_SIZE-1-i-j];
                    if (aiLastStepToWin(linesRDb) >= 0){
                        return new int[]{aiLastStepToWin(linesRDb),j};
                    }
                }
            }
        }
        // прямые проверка проигрыша
        for (int i = 0;i<FIELD_SIZE;i++){
            char[] linesV = new char[FIELD_SIZE];
            char[] linesH = new char[FIELD_SIZE];
            for (int j = 0;j<FIELD_SIZE;j++){
                linesV[j] = field[i][j];
                if (j == FIELD_SIZE-1){
                    if (aiBlockHuman(linesV) >= 0){
                        return new int[]{i,aiBlockHuman(linesV)};
                    }
                }
                linesH[j] = field[j][i];
                if (j == FIELD_SIZE-1){
                    if (aiBlockHuman(linesH) >= 0){
                        return new int[]{i,aiBlockHuman(linesH)};
                    }
                }
            }
            // основные диагонали
            linesLD[i] = field[i][i];
            if (i == FIELD_SIZE-1){
                if (aiBlockHuman(linesLD) >= 0){
                    return new int[]{i,aiBlockHuman(linesLD)};
                }
            }
            linesRD[i] = field[i][field.length-1-i];
            if (i == FIELD_SIZE-1){
                if (aiBlockHuman(linesRD) >= 0){
                    return new int[]{i,aiBlockHuman(linesRD)};
                }
            }
        }

        // диагонали проверка проигрыша
        for (int i = 0;i < FIELD_SIZE-1;i++){
            char[] linesLDa = new char[FIELD_SIZE];
            char[] linesRDa = new char[FIELD_SIZE];
            char[] linesLDb = new char[FIELD_SIZE];
            char[] linesRDb = new char[FIELD_SIZE];

            for (int j = 0;j<FIELD_SIZE-1;j++){
                if (i+j <= FIELD_SIZE-1){
                    linesLDa[j] = field[j+i][j];
                    if (aiBlockHuman(linesLDa) >= 0){
                        return new int[]{j,aiBlockHuman(linesLDa)};
                    }
                    linesLDb[j] = field[j][j+i];
                    if (aiBlockHuman(linesLDb) >= 0){
                        return new int[]{j,aiBlockHuman(linesLDb)};
                    }
                    linesRDa[j] = field[j+i][FIELD_SIZE-1-j];
                    if (aiBlockHuman(linesRDa) >= 0){
                        return new int[]{j,aiBlockHuman(linesRDa)};
                    }
                    linesRDb[j] = field[j][FIELD_SIZE-1-i-j];
                    if (aiBlockHuman(linesRDb) >= 0){
                        return new int[]{j,aiBlockHuman(linesRDb)};
                    }
                }
            }
        }

        // прямые построение фишек к победе
        for (int i = 0;i<FIELD_SIZE;i++){
            char[] linesV = new char[FIELD_SIZE];
            char[] linesH = new char[FIELD_SIZE];
            for (int j = 0;j<FIELD_SIZE;j++){
                linesV[j] = field[i][j];
                if (j == FIELD_SIZE-1){
                    if (aiSearchSection(linesV) >= 0 ){
                        return new int[]{aiSearchSection(linesV),i};
                    }
                }
                linesH[j] = field[j][i];
                if (j == FIELD_SIZE-1){
                    if (aiSearchSection(linesH) >= 0){
                        return new int[]{aiSearchSection(linesH),i};
                    }
                }
            }
            // основные диагонали
            linesLD[i] = field[i][i];
            if (i == FIELD_SIZE-1){
                if (aiSearchSection(linesLD) >= 0){
                    return new int[]{aiSearchSection(linesLD),i};
                }
            }
            linesRD[i] = field[i][field.length-1-i];
            if (i == FIELD_SIZE-1){
                if (aiSearchSection(linesRD) >= 0){
                    return new int[]{aiSearchSection(linesRD),i};
                }
            }
        }

        // диагонали построение фишек к победе
        for (int i = 0;i < FIELD_SIZE-1;i++){
            char[] linesLDa = new char[FIELD_SIZE];
            char[] linesRDa = new char[FIELD_SIZE];
            char[] linesLDb = new char[FIELD_SIZE];
            char[] linesRDb = new char[FIELD_SIZE];

            for (int j = 0;j<FIELD_SIZE-1;j++){
                if (i+j <= FIELD_SIZE-1){
                    linesLDa[j] = field[j+i][j];
                    if (aiSearchSection(linesLDa) >= 0){
                        return new int[]{aiSearchSection(linesLDa),j};
                    }
                    linesLDb[j] = field[j][j+i];
                    if (aiSearchSection(linesLDb) >= 0){
                        return new int[]{aiSearchSection(linesLDb),j};
                    }
                    linesRDa[j] = field[j+i][FIELD_SIZE-1-j];
                    if (aiSearchSection(linesRDa) >= 0){
                        return new int[]{aiSearchSection(linesRDa),j};
                    }
                    linesRDb[j] = field[j][FIELD_SIZE-1-i-j];
                    if (aiSearchSection(linesRDb) >= 0){
                        return new int[]{aiSearchSection(linesRDb),j};
                    }
                }
            }
        }
        return new int[]{RANDOM.nextInt(fieldSizeX),RANDOM.nextInt(fieldSizeY)};
    }


    //checkDraw
    private static boolean checkDraw() {
        for (int y = 0; y < fieldSizeY; y++) {
            for (int x = 0; x < fieldSizeX; x++) {
                if (isCellEmpty(x, y)) return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        while (true) {
            initField();
            printField();
            while (true) {
                humanTurn();
                printField();
                if (gameChecks(DOT_HUMAN, "Human win")) break;
                aiTurn();
                printField();
                if (gameChecks(DOT_AI, "Computer win")) break;
            }
            System.out.println("Play again?");
            if (!SCANNER.next().equals("Y"))
                break;
        }
    }

    private static boolean gameChecks(char dot, String s) {
        if (checkWinFiveSize(dot)) {
            System.out.println(s);
            return true;
        }
        if (checkDraw()) {
            System.out.println("draw!");
            return true;
        }
        return false;
    }
}
