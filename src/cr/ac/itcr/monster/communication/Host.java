package cr.ac.itcr.monster.communication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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

    public static synchronized Host getInstance() {
        if (instance!=  null){
            return instance;
        }
        instance = new Host();
        return instance;
    }

    public void terminate() throws IOException {
        this.flag = false;
        ss.close();
    }

    private void handleMsg(String incomingMsg) {
        String[] parts = incomingMsg.split("-", 3);
        String address = parts[0];
        if (client != null) {
            if (client != address) {
                System.out.println("Intentó conectarse cliente erróneo");
                return;
            }
        } else {
            client = address;
        }

        String type = parts[1];

        switch (type) {
            case "establish connection":
                break;
            case "closing connection":
                this.client = null;
                break;
        }

        String info = parts[2];
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
                    handleMsg(incomingMsg);

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
