package cr.ac.itcr.monster.gui.join;

import cr.ac.itcr.monster.communication.Client;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Clase encargada de manejar logísticamente la ventana del cliente por unirse
 */
public class JoinController {
    @FXML
    private Button cancelButton,connectButton;
    @FXML
    private TextField IPBox,PortBox;

    /**
     * Devuelve al menú principal desde la ventana del Join
     * @param actionEvent
     */
    //Vuelve al menú
    @FXML
    private void cancelButtonAction(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Botón para conectar a un Host una vez se haya escrito la dirección IP y el puerto
     * @param actionEvent
     */
    @FXML
    private void connectButtonAction(ActionEvent actionEvent){

        try {
            Client client = new Client(InetAddress.getByName(IPBox.getText()), Integer.parseInt(PortBox.getText()));
        } catch (UnknownHostException e) {
            System.out.println("Invalid parameters for ip/port fields");
            System.out.println(e.getMessage());
        }
    }
}