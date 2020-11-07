package cr.ac.itcr.monster.gui.game.info;

import cr.ac.itcr.monster.App;
import cr.ac.itcr.monster.game.cards.Card;
import cr.ac.itcr.monster.gui.host.HostController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Clase encargada de la ventana independiente de información de carta
 * Es más que quién maneja el GUI de la ventana de información
 */
public class InfoWindow {

    public InfoWindow(Card card) {
        Stage stage = new Stage();
        stage.initOwner(App.getStage());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setTitle("Información");

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("infoWindow.fxml")); //Group node that is root
            Parent root = loader.load();
            InfoController controller = loader.getController();
            controller.setup(card);

            //setup scene
            Scene scene = new Scene(root, 300, 200);//setup the scene
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
