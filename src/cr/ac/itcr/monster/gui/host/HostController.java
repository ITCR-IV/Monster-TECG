package cr.ac.itcr.monster.menu;

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
    public void info(){
        IPField.setText("DirIP");
    }

    //Vuelve al menú
    public void cancelButtonAction(ActionEvent actionEvent){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
