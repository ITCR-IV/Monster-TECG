package cr.ac.itcr.monster.gui.join;

import cr.ac.itcr.monster.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class JoinWindow {

    public JoinWindow() throws IOException {
        Stage stage = new Stage();
        stage.initOwner(App.getStage());
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
