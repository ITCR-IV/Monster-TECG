package cr.ac.itcr.monster;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;

public class App extends Application {
    private static Stage stage;
    private static Scene menuScene;
    private static Scene gameScene;

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
        Parent gameRoot =  FXMLLoader.load(getClass().getResource("gui/game/game.fxml"));

        menuScene = new Scene(menuRoot, 800, 600);//setup the scene
        gameScene = new Scene(gameRoot, 800, 600);

        primaryStage.setScene(menuScene);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        //Lo que vaya aquí sucede al cerrar la aplicación
    }

    public static void switchScene(String newScene){

        if (newScene == "game") {
            stage.setScene(gameScene);
        } else if (newScene == "menu") {
            stage.setScene(menuScene);
        }

    }

    public static Stage getStage() {
        return stage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}