package cr.ac.itcr.monster.gui.host;

import cr.ac.itcr.monster.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class HostWindow {

    public HostWindow() throws IOException {
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
}
