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
    private DataOutputStream dos;
    private boolean flag =true;

    private Host(){
        //singleton!! :D
        Thread t = new Thread(this); //thread so that it's permanently checking for sockets
        t.start();
        try {
            Thread.sleep(100); //esto es para darle chance al server socket de crearse antes de que lo usen para cualquier cosa
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static synchronized Host getHost() {
        if (instance!=  null){
            return instance;
        }
        instance = new Host();
        return instance;
    }

    public void terminate(){
        this.flag = false;
        try {
            ss.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        instance = null;
    }

    private void handleMsg(String incomingMsg) throws IOException {
        String[] parts = incomingMsg.split("-", 2);

        String type = parts[0];
        String info = parts[1];

        switch (type) {
            case "establish connection":
                this.client = info;
                dos.writeUTF("connection succesful-name host");
                System.out.println(client);
                break;
            case "closing connection":
                this.terminate();
                break;
        }
    }


    @Override
    public void run() {
        try (ServerSocket server = new ServerSocket(0)) {  //this try automatically closes the server socket when done
            this.ss = server;
            Socket s = ss.accept(); //waits for client socket to connect
            DataInputStream dis = new DataInputStream(s.getInputStream()); // input stream
            this.dos = new DataOutputStream(s.getOutputStream()); // output stream to reply to socket
            while (flag) {
                dis.readUTF();
                String incomingMsg = dis.readUTF();

                handleMsg(incomingMsg);
            }
            s.close(); //closes the socket
        } catch (SocketException e) {
            if (!flag) {
                System.out.println("Interrupted server socket accept");
            } else {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getPort() {
        return ss.getLocalPort();
    }
}
