package com.marcelfiore.jungle_ai.game.gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientReceive extends Thread{

    private BufferedReader in;

    public ClientReceive(Socket socket) throws IOException{
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void receive(){
        String msg = null;
        System.out.println("Receive thread started");
        while (true) {
            try {
                //Buffered Reader reads from socket
                while ((msg = in.readLine()) != null) {
                    System.out.println("Message received: " + msg);
                    //Parse input into string array
                    String[] message = parseReceive(msg);
                    respondToInput(message);

                }
            } catch (Exception e) {}

        }
    }

    public String[] parseReceive(String msg) {
        String [] items = msg.split(" ");
        return items;
    }

    public void respondToInput(String [] message){
        //if logging in, do some stuff to send to database to authenticate, etc.
        //TODO
        if(message[0].equals("login")){
            System.out.println("Logging in");
        }
    }

    public void run(){
        receive();
    }
}
