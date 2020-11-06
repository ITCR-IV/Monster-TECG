package cr.ac.itcr.monster.gui.game;

import cr.ac.itcr.monster.communication.Client;
import cr.ac.itcr.monster.communication.Host;
import cr.ac.itcr.monster.game.cards.Card;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class GameController {
    private static boolean turn;
    private String player;
    private static volatile GameController instance;

    @FXML
    public Button endTurnButton;

    public static synchronized GameController getInstance() {
        return instance;
    }


    public void setup(String player) {
        instance = this;
        if (player.equals("host")) {
            this.player = "host";
            this.turn = true;
            System.out.println("It's your turn!!");
        } else if (player.equals("client")) {
            this.player = "client";
            this.turn = false;
            System.out.println("It's not your turn :(");
            endTurnButton.setDisable(true);
        }
        Card.loadCards();
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
                if (player.equals("host")) {
                    Host.getHost().sendMsg("ACTION-switch turn");
                } else if (player.equals("client")) {
                    Client.getClient().sendMsg("ACTION-switch turn");
                }
            }
    }
}
