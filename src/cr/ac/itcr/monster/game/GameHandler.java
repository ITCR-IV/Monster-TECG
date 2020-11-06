package cr.ac.itcr.monster.game;

import cr.ac.itcr.monster.game.cards.Esbirro;
import cr.ac.itcr.monster.game.hand.Hand;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;

public class GameHandler {

    private Deck playerDeck;
    private Deck enemyDeck;
    private Hand playerCards;
    private Hand enemyCards;
    private ArrayList<Esbirro> playerMinions;
    private ArrayList<Esbirro> enemyMinions;
    private Player enemy;
    private Player player;
}
