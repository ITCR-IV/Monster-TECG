package cr.ac.itcr.monster.gui.game;

import com.sun.javafx.fxml.builder.JavaFXSceneBuilder;
import cr.ac.itcr.monster.communication.Client;
import cr.ac.itcr.monster.communication.Host;
import cr.ac.itcr.monster.game.GameHandler;
import cr.ac.itcr.monster.game.cards.Card;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

import java.awt.*;
import java.util.ArrayList;

public class GameController {

    private static volatile GameController instance;
    public static synchronized GameController getInstance() {
        return instance;
    }

    //GUI references
    @FXML
    private StackPane playerDeck;
    @FXML
    private StackPane enemyDeck;
    @FXML
    private ArrayList<StackPane> playerCards;
    @FXML
    private ArrayList<StackPane> enemyCards;
    @FXML
    private ArrayList<StackPane> playerMinions;
    @FXML
    private ArrayList<StackPane> enemyMinions;
    @FXML
    private Pane enemy;
    @FXML
    private Pane player;

    //Buttons
    @FXML
    private Button playCardButton;
    @FXML
    private Button infoButton;
    @FXML
    public Button endTurnButton;

    //logical variables
    private static boolean turn;
    private String playerType;
    private GameHandler gameHandler;

    public void setup(String player) {
        instance = this;
        if (player.equals("host")) {
            this.playerType = "host";
            this.turn = true;
            System.out.println("It's your turn!!");
        } else if (player.equals("client")) {
            this.playerType = "client";
            this.turn = false;
            System.out.println("It's not your turn :(");
            endTurnButton.setDisable(true);
        }
        Card.loadCards();

        for (StackPane i : playerCards) {
            for (Node item: i.getChildren()) {
                Shape shape = (Shape) item;
                shape.setStroke(Color.rgb(244, 244, 244));
                shape.setFill(Color.rgb(244, 244, 244));
            }
        }
        for (StackPane i : enemyCards) {
            for (Node item: i.getChildren()) {
                Shape shape = (Shape) item;
                shape.setStroke(Color.rgb(244, 244, 244));
                shape.setFill(Color.rgb(244, 244, 244));
            }
        }

    }

    @FXML
    public void switchTurn(ActionEvent actionEvent) {
        this.turn = !turn;
            if (turn) {
                System.out.println("It's your turn again!");
                endTurnButton.setDisable(false);
            } else {
                System.out.println("It's no longer your turn :/");
                endTurnButton.setDisable(true);
                if (playerType.equals("host")) {
                    Host.getHost().sendMsg("ACTION-switch turn");
                } else if (playerType.equals("client")) {
                    Client.getClient().sendMsg("ACTION-switch turn");
                }
            }
    }
}
