package cr.ac.itcr.monster.communication;

import cr.ac.itcr.monster.App;
import cr.ac.itcr.monster.gui.game.GameController;
import cr.ac.itcr.monster.gui.join.JoinWindow;
import javafx.application.Platform;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client implements Runnable,Messenger {
    private static volatile Client instance;

    private String host;
    private boolean flag =true;
    private DataOutputStream dos;
    private Socket s;

    private InetAddress IP;
    private int PORT;

    public Client(InetAddress IP, int PORT){

        this.IP=IP;
        this.PORT=PORT;
        instance = this;

        Thread t = new Thread(this); //thread so that it's permanently checking socket
        t.start();
        try {
            Thread.sleep(100); //esto es para idk
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static synchronized Client getClient() {
        return instance;
    }

    public void terminate() {
        try {
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.flag = false;
        instance = null;
    }

    public void sendMsg(String msg) {
        try {
            dos.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleMsg(String incomingMsg) {
        String[] parts = incomingMsg.split("-", 2);

        String type = parts[0];
        String info = parts[1];

        switch (type) {
            case "CONNECTION SUCCESFUL":
                this.host = info;
                System.out.println(host);

                Platform.runLater(() -> {
                    JoinWindow.closeJoinWindow();
                    App.startGame("client");
                });
                break;
            case "CLOSING CONNECTION":
                this.terminate();
                break;
            case "ACTION":
                switch (info){
                    case "switch turn":
                            Platform.runLater(() -> {
                                GameController.getInstance().endTurnButton.setDisable(false);
                                GameController.getInstance().endTurnButton.fire();});
                        break;
                }
                break;
        }

    }


    @Override
    public void run() {
        try (Socket s = new Socket(IP, PORT)) {
            this.s = s;

            this.dos = new DataOutputStream(s.getOutputStream()); //open data stream
            DataInputStream dis = new DataInputStream(s.getInputStream());

            dos.writeUTF("ESTABLISH CONNECTION-name client");

            while (flag) {
                String incomingMsg = dis.readUTF();

                handleMsg(incomingMsg);
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }  catch (IOException e) {
            System.out.println("Failed to connect to given ip/port combination or terminated connection");
            System.out.println(e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
