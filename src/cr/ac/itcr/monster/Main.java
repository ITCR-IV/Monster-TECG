package cr.ac.itcr.monster;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;

public class Main extends Application {
    private static Window stage;

    @Override
    public void init() throws Exception {
        //Lo que vaya aquí sucede antes de iniciar la aplicación
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        primaryStage.setTitle("Monster TECG");
        primaryStage.setResizable(false);

        Parent root = FXMLLoader.load(getClass().getResource("menu.fxml"));

        Scene scene = new Scene(root, 800, 600);//setup the scene
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        //Lo que vaya aquí sucede al cerrar la aplicación
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static Window getStage() {
        return stage;
    }
}