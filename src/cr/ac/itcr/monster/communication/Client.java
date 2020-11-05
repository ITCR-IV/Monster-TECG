package cr.ac.itcr.monster.communication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.Queue;

public class Client implements Runnable {
    private static volatile Client instance;

    private String host;
    private boolean flag =true;
    private Queue<String> eventQueue;

    private InetAddress IP;
    private int PORT;

    public Client(InetAddress IP, int PORT){

        this.IP=IP;
        this.PORT=PORT;

        Thread t = new Thread(this); //thread so that it's permanently checking socket
        t.start();
        try {
            Thread.sleep(100); //esto es para idk
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void terminate() {
        this.flag = false;
        instance = null;
    }

    private void handleMsg(String incomingMsg) {
        String[] parts = incomingMsg.split("-", 3);
        String address = parts[0];

        String type = parts[1];

        switch (type) {
            case "connection succesful":
                break;
            case "closing connection":
                this.terminate();
                break;
        }

        String info = parts[2];
    }

    public void connect(String IP,String PORT){

    }

    @Override
    public void run() {
        try (Socket s = new Socket(IP, PORT)) {

            DataOutputStream dos = new DataOutputStream(s.getOutputStream()); //open data stream
            DataInputStream dis = new DataInputStream(s.getInputStream());

            dos.writeUTF("establish connection-");

            while(flag){

            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Failed to connect to given ip/port combination");
            System.out.println(e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }





}
