package com.company.homeWork2_6;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
    public static void main(String[] args) {
        try(ServerSocket server = new ServerSocket(8189)) {
            System.out.println("Server created");
            Socket s = server.accept();
            System.out.println("Client connected");
            DataInputStream in = new DataInputStream(s.getInputStream());
            DataOutputStream out = new DataOutputStream(s.getOutputStream());
            System.out.println("ready for exchange");
            String msg = in.readUTF();
            System.out.println("server received " + msg);
            out.writeUTF("echo: " + msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
