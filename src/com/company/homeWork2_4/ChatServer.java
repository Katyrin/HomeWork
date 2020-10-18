package com.company.homeWork2_4;

import java.io.FileOutputStream;
import java.io.IOException;

public class ChatServer {
    private boolean isStart = false;
    public void start(int port){
        isStart = true;
        System.out.println("Server started at port: " + port);
    }
    public void stop(){
        isStart = false;
        System.out.println("Server stopped");
    }
    // создаю файл или добавляю текст если он существует
    public void createFileTXT(String text){
        if (isStart){
            try {
                FileOutputStream fos = new FileOutputStream("log",true);
                fos.write(text.getBytes());
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
