package cr.ac.itcr.monster.connectors;

import cr.ac.itcr.monster.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.ServerSocket;


public class HostController {

    ServerSocket serverSocket = new ServerSocket("" );

    @FXML
    private Button cancelButton;
    @FXML
    private Label IPHost;

    //Se muestra la dir IP a conectar
    public void info(){
        IPHost.setText("DirIP");
    }
    //Vuelve al menú
    public void cancelButtonAction(ActionEvent actionEvent){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
