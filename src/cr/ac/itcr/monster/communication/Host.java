package cr.ac.itcr.monster.communication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Host implements Runnable {

    private static volatile Host instance;

    private String client;
    private ServerSocket ss;
    private boolean flag =true;

    private Host(){
        //singleton!! :D
        Thread t = new Thread(this); //thread so that it's permanently checking for sockets
        t.start();
    }

    public static Host getInstance() {
        if (instance!=  null){
            return instance;
        }
        synchronized (Host.class) {
            instance = new Host();
            return instance;
        }
    }


    @Override
    public void run() {
        try (ServerSocket server = new ServerSocket(0)) {  //this try automatically closes the server socket when done
            this.ss = server;
            while (flag) {
                Socket s = ss.accept(); //waits for client socket to connect
                try {
                    DataInputStream dis = new DataInputStream(s.getInputStream()); // input stream
                    String incomingMsg = dis.readUTF(); // reads the incoming msg

                    DataOutputStream dos = new DataOutputStream(s.getOutputStream()); // output stream to reply to socket
                    dos.writeUTF("holaa"); //envía un mensaje de vuelta

                } catch (IOException e) {
                    e.printStackTrace();
                }
                s.close(); //closes the socket
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getPort() {
        return ss.getLocalPort();
    }
}
