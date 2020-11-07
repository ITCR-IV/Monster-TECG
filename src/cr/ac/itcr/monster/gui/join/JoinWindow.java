package cr.ac.itcr.monster.gui.join;

import cr.ac.itcr.monster.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Encargado de montare el GUI pertinente de Join
 */
public class JoinWindow {
    private static Stage stage;

    /**
     * Builder, construye y guarda en la variable pertinente la ventana de Join con
     * toda la información necesaria
     * @throws IOException
     */
    public JoinWindow() throws IOException {
        stage = new Stage();
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

    /**
     * En casi de que se cierre la ventana maneja el cierre de esta
     */
    public static void closeJoinWindow() {
        stage.close();
    }
}
