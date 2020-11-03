package cr.ac.itcr.monster;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void init() throws Exception {
        //Lo que vaya aquí sucede antes de iniciar la aplicación
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
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
}