package cr.ac.itcr.monster.gui.game;

import com.sun.javafx.fxml.builder.JavaFXSceneBuilder;
import cr.ac.itcr.monster.communication.Client;
import cr.ac.itcr.monster.communication.Host;
import cr.ac.itcr.monster.communication.Messenger;
import cr.ac.itcr.monster.game.GameHandler;
import cr.ac.itcr.monster.game.cards.Card;
import cr.ac.itcr.monster.gui.game.info.InfoWindow;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

import java.awt.*;
import java.util.ArrayList;

public class GameController {

    private static volatile GameController instance;
    public static synchronized GameController getInstance() {
        return instance;
    }

    //GUI references
    @FXML
    private StackPane playerDeck,enemyDeck;
    @FXML
    private ArrayList<StackPane> playerCards,enemyCards;
    @FXML
    private ArrayList<StackPane> playerMinions,enemyMinions;
    @FXML
    private Pane enemy,player;

    //Buttons
    @FXML
    private Button playCardButton, infoButton;
    public Button endTurnButton;

    //logical variables
    private static boolean turn;
    private String playerType;
    private GameHandler gameHandler;
    private int deckSize=20;
    private int enemyDeckSize=20;
    private int friendlyMinionsSize;
    private int enemyMinionsSize;
    private int handSize;
    private int enemyHandSize;
    private int cardSelection;

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
        gameHandler = new GameHandler(playerType);

        int i = 1;
        for (StackPane stackPane : playerCards) {
            int forI = i; //variable must be copied to effectively final variable for it to work
            stackPane.setOnMouseClicked(event -> selectCard(forI));
            i++;
            for (Node item: stackPane.getChildren()) {
                Shape shape = (Shape) item;
                shape.setStroke(Color.rgb(244, 244, 244));
                shape.setFill(Color.rgb(244, 244, 244));
            }
        }
        for (StackPane stackPane : enemyCards) {
            for (Node item: stackPane.getChildren()) {
                Shape shape = (Shape) item;
                shape.setStroke(Color.rgb(244, 244, 244));
                shape.setFill(Color.rgb(244, 244, 244));
            }
        }
        for (StackPane stackPane : playerMinions) {
            int finalI = i;
            stackPane.setOnMouseClicked(event -> selectCard(finalI));
            i++;
            for (Node item: stackPane.getChildren()) {
                Shape shape = (Shape) item;
                shape.setStroke(Color.rgb(159, 159, 159));
                shape.setFill(Color.rgb(159, 159, 159));
            }
        }
        i = 1;
        for (StackPane stackPane : enemyMinions) {
            int finalI = i;
            stackPane.setOnMouseClicked(event -> targetMinon(finalI));
            i++;
            for (Node item: stackPane.getChildren()) {
                Shape shape = (Shape) item;
                shape.setStroke(Color.rgb(159, 159, 159));
                shape.setFill(Color.rgb(159, 159, 159));
            }
        }

        drawCard();
        drawCard();
        drawCard();
        drawCard();
    }

    @FXML
    public void switchTurn(ActionEvent actionEvent) {
        this.turn = !turn;
            if (turn) { //empezar turno
                endTurnButton.setDisable(false);
                drawCard();
            } else { //terminar turno
                endTurnButton.setDisable(true);
                if (playerType.equals("host")) {
                    Host.getHost().sendMsg("ACTION-switch turn");
                } else if (playerType.equals("client")) {
                    Client.getClient().sendMsg("ACTION-switch turn");
                }
            }
        resetCardSelection();
    }

    public void drawCard() {
        Card drawnCard = gameHandler.drawCard();

        StackPane guiCard = playerCards.get(handSize);
        ObservableList<Node> elements = guiCard.getChildren();
        for (Node item: elements) {
            Shape shape = (Shape) item;
            shape.setStroke(Color.BLACK);
            shape.setFill(Color.BLACK);
        }
        Rectangle rect = (Rectangle) elements.get(0);
        Text name = (Text) elements.get(1);
        Text cost = (Text) elements.get(3);
        rect.setFill(Color.NAVAJOWHITE);
        name.setText(drawnCard.getNombre().replaceAll(" ","\n"));
        cost.setText(String.valueOf(drawnCard.getCoste()));

        this.handSize++;
        this.deckSize--;

        Text deckCount = (Text) playerDeck.getChildren().get(1);
        deckCount.setText(deckSize+"/16");
    }

    public void enemyDraw(String cardName) {
        gameHandler.enemyDraw(cardName);

        StackPane guiCard = enemyCards.get(enemyHandSize);
        ObservableList<Node> elements = guiCard.getChildren();
        Rectangle rect = (Rectangle) elements.get(0);
        rect.setStroke(Color.BLACK);
        rect.setFill(Color.NAVAJOWHITE);

        this.enemyHandSize++;
        this.enemyDeckSize--;

        Text deckCount = (Text) enemyDeck.getChildren().get(1);
        deckCount.setText(enemyDeckSize+"/16");
    }

    public void selectCard(int index) {
        Rectangle rect;
        if (index <= 10) { //cartas de mano
            if (index > handSize) {
                return;
            }
            rect = (Rectangle) playerCards.get(index-1).getChildren().get(0);
        } else{ //minions
            if (index-10 > friendlyMinionsSize) {
                return;
            }
            rect = (Rectangle) playerMinions.get(index-11).getChildren().get(0);
        }
        rect.setStroke(Color.DEEPSKYBLUE);

        resetCardSelection();
        this.cardSelection = index;
        System.out.println(index);
    }

    public void resetCardSelection() {
        Rectangle rect;
        if (cardSelection > 0) {
            if (this.cardSelection < 10) {
                rect = (Rectangle) playerCards.get(cardSelection - 1).getChildren().get(0);
            } else {
                rect = (Rectangle) playerMinions.get(cardSelection - 11).getChildren().get(0);
            }
            rect.setStroke(Color.BLACK);
        }
        this.cardSelection = 0;
    }

    public void targetMinon(int index) {
        System.out.println("estripado el enemigo"+ index);
    }

    public void targetEnemy(MouseEvent mouseEvent) {
        System.out.println("clicked on enemy");
    }

    public void displayInfo(ActionEvent actionEvent) {
        if (cardSelection>0 && cardSelection<=10) {
            InfoWindow info = new InfoWindow(gameHandler.getPlayerCard(cardSelection));
        }
    }
}
