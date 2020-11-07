package cr.ac.itcr.monster.communication;

import cr.ac.itcr.monster.App;
import cr.ac.itcr.monster.game.cards.Card;
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

/**
 * Clase encargada de la logística de un cliente y su manejo
 *
 */
public class Client implements Runnable,Messenger {
    private static volatile Client instance;

    private String host;
    private boolean flag =true;
    private DataOutputStream dos;
    private Socket s;

    private InetAddress IP;
    private int PORT;


    /**
     * Client Builder
     * @param IP
     * @param PORT
     */
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

    /**
     * Obtener instancia de cliente
     * @return Client
     */
    public static synchronized Client getClient() {
        return instance;
    }

    /**
     * Cuando el cliente se desconecta del host
     */
    public void terminate() {
        try {
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.flag = false;
        instance = null;
    }

    /**
     * Para la comunicación entre el host y el cliente, envía strings
     * @param msg
     */
    public void sendMsg(String msg) {
        try {
            dos.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Recibe un mensaje e interpreta su significado
     * @param incomingMsg
     */
    private void handleMsg(String incomingMsg) {
        String[] parts = incomingMsg.split("-", 3);

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
                    case"freeze":
                        Platform.runLater(()->GameController.getInstance().Freeze(true));
                        break;
                    case "Curar":
                        Platform.runLater(()->GameController.getInstance().heal(Integer.parseInt(parts[2]),"enemy"));
                        break;
                    case "Bola de Fuego":
                        if (parts[2]=="enemigo"){
                            Platform.runLater(()->GameController.getInstance().takeDamage(200,"player"));
                        }else{
                            Platform.runLater(()->GameController.getInstance().damageMinion(200,Integer.parseInt(parts[2]),"player"));
                        }
                        break;
                    case "Asesinar":
                        Platform.runLater(()->GameController.getInstance().killMinion(Integer.parseInt(parts[2]),"player"));
                        break;
                }
                break;
            case "ESBIRRO":
                Platform.runLater(() -> GameController.getInstance().addMinion(Card.getCardByName(parts[1]), "enemy"));
                break;
            case"HECHIZO":
                switch (info){
                    case "freeze":
                        Platform.runLater(()->GameController.getInstance().Freeze(true));
                        break;

                }
                break;
            case "ATAQUE":
                switch (info) {
                    case "enemigo":
                        Platform.runLater(()-> {
                            int damage = Card.getCardByName(parts[2]).getAtaque();
                            GameController.getInstance().takeDamage(damage,"player");
                        });
                        break;
                    default:
                        Platform.runLater(()-> {
                            int damage = Card.getCardByName(parts[2]).getAtaque();
                            GameController.getInstance().damageMinion(damage, Integer.parseInt(info),"player");
                        });
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
