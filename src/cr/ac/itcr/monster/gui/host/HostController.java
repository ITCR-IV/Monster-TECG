package cr.ac.itcr.monster.gui.host;

import cr.ac.itcr.monster.communication.Host;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Es quién se encarga de la logistica por parte de la ventana del Host
 */
public class HostController {

    @FXML
    private Button cancelButton;
    @FXML
    private Label IPField;
    @FXML
    public Label PORTField;

    /**
     * Hace el set up del host muestra las ventana con la información pertinente
     * @param stage
     * @throws UnknownHostException
     */
    //Se muestra la dir IP a conectar
    @FXML
    public void setup(Stage stage) throws UnknownHostException {
        Host host = Host.getHost();

        IPField.setText(String.valueOf(InetAddress.getLocalHost().getHostAddress()));
        PORTField.setText(Integer.toString(host.getPort()));

        stage.setOnCloseRequest(event -> {
            System.out.println("Closing window");
            host.terminate();
        });
    }

    /**
     * Dirige la acción del button para devolverse al menú princippal
     * @param actionEvent
     */
    //Vuelve al menú
    @FXML
    private void cancelButtonAction(ActionEvent actionEvent){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();

        Host host = Host.getHost();

        host.terminate();
    }

}
