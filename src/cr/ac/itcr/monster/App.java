package cr.ac.itcr.monster;

import cr.ac.itcr.monster.communication.Client;
import cr.ac.itcr.monster.communication.Host;
import cr.ac.itcr.monster.game.cards.Card;
import cr.ac.itcr.monster.gui.game.GameController;
import cr.ac.itcr.monster.gui.host.HostController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.net.ServerSocket;

public class App extends Application {
    private static Stage stage;
    private static Scene menuScene;
    private static Scene gameScene;
    private static GameController gameController;

    @Override
    public void init() throws Exception {
        //Lo que vaya aquí sucede antes de iniciar la aplicación
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        primaryStage.setTitle("Monster TECG");
        primaryStage.setResizable(false);

        Parent menuRoot = FXMLLoader.load(getClass().getResource("gui/menu/menu.fxml"));

        FXMLLoader gameLoader = new FXMLLoader(getClass().getResource("gui/game/game.fxml"));
        Parent gameRoot = gameLoader.load();
        gameController = gameLoader.getController();

        menuScene = new Scene(menuRoot, 800, 600);//setup the scene
        gameScene = new Scene(gameRoot, 800, 600);

        primaryStage.setScene(menuScene);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        Host.getHost().terminate();
        Client client = Client.getClient();
        if (client != null) {
            client.terminate();
        }
    }

    public static void startGame(String player){

        if (player == "host") {
            stage.setScene(gameScene);
            gameController.setup("host");
        } else if (player == "client") {
            stage.setScene(gameScene);
            gameController.setup("client");
        }

    }

    public static Stage getStage() {
        return stage;
    }

    public static void main(String[] args) {
        Card.loadCards();
        launch(args);
    }
}