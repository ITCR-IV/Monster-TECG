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

/**
 * Aplicación principal es quien llama a todos los metodos o clases necesarios para el funcionamiento de esta
 */
public class App extends Application {
    private static Stage stage;
    private static Scene menuScene;
    private static Scene gameScene;
    private static GameController gameController;

    /**
     * Lo que secedería antes de iincializarse en caso de que se requieran cosas
     * @throws Exception
     */
    @Override
    public void init() throws Exception {
        //Lo que vaya aquí sucede antes de iniciar la aplicación
    }

    /**
     * Inicialización de la aplicación utilizando los métodos y las clases necesarias
     * @param primaryStage
     * @throws Exception
     */
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

    /**
     *Una vez que Finaliza la ejecución de la aplicación sucede lo que esté dentro de esta función
     * @throws Exception
     */
    @Override
    public void stop() throws Exception {
        Host.getHost().terminate();
        Client client = Client.getClient();
        if (client != null) {
            client.terminate();
        }
    }

    /**
     * Genera un gamecontroller para el host y uno para el cliente
     * @param player
     */
    public static void startGame(String player){

        if (player == "host") {
            stage.setScene(gameScene);
            gameController.setup("host");
        } else if (player == "client") {
            stage.setScene(gameScene);
            gameController.setup("client");
        }

    }

    /**
     * Finaliza el juego cuando se llegue a la condición de finalización
     */
    public static void endGame(){
        Host.getHost().terminate();
        Client client = Client.getClient();
        if (client != null) {
            client.terminate();
        }
        stage.setScene(menuScene);
    }

    /**
     * Permite accesar al menú principal desde casi cualquier parte del código
     * @return
     */
    public static Stage getStage() {
        return stage;
    }

    /**
     * Main method
     * @param args
     */
    public static void main(String[] args) {
        Card.loadCards();
        launch(args);
    }
}