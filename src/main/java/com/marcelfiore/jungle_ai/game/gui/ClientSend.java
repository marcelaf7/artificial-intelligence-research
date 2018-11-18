package com.marcelfiore.jungle_ai.game.gui;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientSend extends Thread{

    private Scanner scanner;
    private PrintWriter out;

    public ClientSend(Socket socket) throws Exception{
        this.scanner = new Scanner(System.in);
        this.out = new PrintWriter(socket.getOutputStream(),true);
    }

    public void send(){
        System.out.println("ClientSend thread started");
        String msg = null;
        while(true){
            msg = scanner.nextLine();
            out.println(msg);
            out.flush();
            System.out.println("Message sent: " + msg);
        }
    }

    public void sendLogin(String data){
        out.println("login " + data);
        out.flush();
        System.out.println("Data sent");
    }

    public void run(){
        send();
    }
}
