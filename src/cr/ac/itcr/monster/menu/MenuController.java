package cr.ac.itcr.monster.menu;

import cr.ac.itcr.monster.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MenuController {
    @FXML
    public void host_game(ActionEvent actionEvent) throws Exception {
        Stage stage = new Stage();
        stage.initOwner(Main.getStage());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setTitle("Host window");

        Parent root = FXMLLoader.load(getClass().getResource("hostWindow.fxml"));

        //setup scene
        Scene scene = new Scene(root, 250, 150);//setup the scene
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void join_game(ActionEvent actionEvent) throws Exception {
        Stage stage = new Stage();
        stage.initOwner(Main.getStage());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setTitle("Join window");

        Parent root = FXMLLoader.load(getClass().getResource("joinWindow.fxml"));



        //setup scene
        Scene scene = new Scene(root, 250, 150);//setup the scene
        stage.setScene(scene);
        stage.show();
    }
}
