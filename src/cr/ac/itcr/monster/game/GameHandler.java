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
    private Esbirro[] playerMinions = new Esbirro[5];
    private Esbirro[] enemyMinions = new Esbirro[5];
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

    public Card getPlayerCard(int pos) {
        return playerCards.getCard(pos);
    }

    public Card drawCard() {
        Card drawnCard = playerDeck.draw();
        playerCards.addCard(drawnCard);
        messenger.sendMsg("ACTION-draw-"+drawnCard.getNombre());
        return drawnCard;
    }

    public void enemyDraw(String cardName) {
        Card drawnCard = Card.getCardByName(cardName);
        enemyCards.addCard(drawnCard);
    }

    public int addMinion(Card card, String playerType) {
        int i=0;
        if (playerType=="player") {
            while (playerMinions[i]!=null) {
                i++;
                if (i>=5) {
                    return i;
                }
            }
            playerMinions[i] = new Esbirro(card.getVida(), card.getAtaque());
            messenger.sendMsg("ESBIRRO-"+card.getNombre());
        } else if (playerType == "enemy") {
            while (enemyMinions[i]!=null) {
                i++;
                if (i>=5) {
                    return i;
                }
            }
            enemyMinions[i] = new Esbirro(card.getVida(), card.getAtaque());
        }
        else{
            System.out.println("Se llamó addMinion en GameHandler sin dar un playerType válido");
            return 6;
        }
        return i;
    }

    public void removeCard(int index,String player) {
        if (index<=10) {
            if (player.equals("player")) {
                Card card = playerCards.removeCard(index);
                playerDeck.getDiscardPile().add(card);
            } else if (player.equals("enemy")) {
                enemyCards.removeCard(index);
            } else{
                System.out.println("Called removeCard in GameHandler with wrong player type specification");
            }
        }
    }
}
