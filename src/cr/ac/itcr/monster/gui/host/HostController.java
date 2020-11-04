package cr.ac.itcr.monster.gui.host;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class HostController {

    @FXML
    private Button cancelButton;
    @FXML
    private Label IPField;
    @FXML
    public Label PORTField;

    //Se muestra la dir IP a conectar
    @FXML
    public void setup(Stage stage){
        IPField.setText("DirIP");
        PORTField.setText("DirPort");

        stage.setOnCloseRequest(event -> {
            System.out.println("Closing window");
            //poner aquí función para desconectar receiver
        });
    }

    //Vuelve al menú
    @FXML
    private void cancelButtonAction(ActionEvent actionEvent){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

}
