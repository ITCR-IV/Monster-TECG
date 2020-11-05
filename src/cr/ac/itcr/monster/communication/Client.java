package cr.ac.itcr.monster.communication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client implements Runnable {
    private static volatile Client instance;

    private String host;
    private boolean flag =true;
    private DataOutputStream dos;

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
        String[] parts = incomingMsg.split("-", 2);

        String type = parts[0];
        String info = parts[1];

        switch (type) {
            case "connection succesful":
                this.host = info;
                System.out.println(host);
                break;
            case "closing connection":
                this.terminate();
                break;
        }

    }


    @Override
    public void run() {
        try (Socket s = new Socket(IP, PORT)) {

            this.dos = new DataOutputStream(s.getOutputStream()); //open data stream
            DataInputStream dis = new DataInputStream(s.getInputStream());

            dos.writeUTF("establish connection-name client");

            while(flag){
                String incomingMsg = dis.readUTF();

                handleMsg(incomingMsg);
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
