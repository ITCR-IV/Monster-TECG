package cr.ac.itcr.monster.gui.join;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class JoinController {
    @FXML
    private Button cancelButton,connectButton;
    @FXML
    private TextField IPBox,PortBox;

    //Vuelve al menú
    public void cancelButtonAction(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
    public void connectButtonAction(ActionEvent actionEvent){

    }
}