package cr.ac.itcr.monster.game;

import cr.ac.itcr.monster.communication.Client;
import cr.ac.itcr.monster.communication.Host;
import cr.ac.itcr.monster.communication.Messenger;
import cr.ac.itcr.monster.game.cards.Card;
import cr.ac.itcr.monster.game.cards.Esbirro;
import cr.ac.itcr.monster.game.hand.Hand;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;

public class GameHandler {
    private Messenger messenger;

    private Deck playerDeck;
    private Hand playerCards;
    private Hand enemyCards;
    private ArrayList<Esbirro> playerMinions;
    private ArrayList<Esbirro> enemyMinions;
    private Player enemy;
    private Player player;

    public GameHandler(String playerType) {
        playerDeck = new Deck();
        playerCards = new Hand();
        enemyCards = new Hand();
        if (playerType.equals("host")) {
            this.messenger = Host.getHost();
        } else if (playerType.equals("client")) {
            this.messenger = Client.getClient();
        }
    }

    public Card drawCard() {
        Card drawnCard = playerDeck.draw();
        playerCards.addCard(drawnCard);
        messenger.sendMsg("ACTION-draw-"+drawnCard.getNombre());
        return drawnCard;
    }
}
