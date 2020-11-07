package cr.ac.itcr.monster.game;

import cr.ac.itcr.monster.communication.Client;
import cr.ac.itcr.monster.communication.Host;
import cr.ac.itcr.monster.communication.Messenger;
import cr.ac.itcr.monster.game.cards.Card;
import cr.ac.itcr.monster.game.cards.Esbirro;
import cr.ac.itcr.monster.game.hand.Hand;

/**
 * Clase que controla toda la logística detrás del Juego, maneja los decks y las
 * cartas y posteriormente informa al usuario enemigo y al Game Controller
 */
public class GameHandler {
    private Messenger messenger;

    private Deck playerDeck;
    private Hand playerCards;
    private Hand enemyCards;
    private Esbirro[] playerMinions = new Esbirro[5];
    private Esbirro[] enemyMinions = new Esbirro[5];
    private Player enemy;
    private Player player;

    /**
     * Builder del Game Handler
     * @param playerType
     */
    public GameHandler(String playerType) {
        playerDeck = new Deck();
        playerCards = new Hand();
        enemyCards = new Hand();
        enemy = new Player();
        player = new Player();
        if (playerType.equals("host")) {
            this.messenger = Host.getHost();
        } else if (playerType.equals("client")) {
            this.messenger = Client.getClient();
        }
    }

    /**
     * Devuelve la carta de un jugador en una posición determinada
     * @param pos
     * @return Carta del jugador en pos
     */
    public Card getPlayerCard(int pos) {
        return playerCards.getCard(pos);
    }

    /**
     * Saca una Carta del deck personal
     *
     * @return Carta sacada del deck
     */
    public Card drawCard() {
        Card drawnCard = playerDeck.draw();
        playerCards.addCard(drawnCard);
        messenger.sendMsg("ACTION-draw-"+drawnCard.getNombre());
        return drawnCard;
    }

    /**
     * Obtiene un registro de qué carta sacó el enemigo
     * @param cardName
     */
    public void enemyDraw(String cardName) {
        Card drawnCard = Card.getCardByName(cardName);
        enemyCards.addCard(drawnCard);
    }

    /**
     * Añade un minion al array y mantiene el conteo de estos
     * @param card
     * @param playerType
     * @return posición del minion agregado
     */
    public int addMinion(Card card, String playerType) {
        int i=0;
        if (playerType=="player") {
            while (playerMinions[i]!=null) {
                i++;
                if (i>=5) {
                    return i;
                }
            }
            playerMinions[i] = new Esbirro(card.getVida(), card.getAtaque(), card.getNombre());
            messenger.sendMsg("ESBIRRO-"+card.getNombre());
        } else if (playerType == "enemy") {
            while (enemyMinions[i]!=null) {
                i++;
                if (i>=5) {
                    return i;
                }
            }
            enemyMinions[i] = new Esbirro(card.getVida(), card.getAtaque(), card.getNombre());
        }
        else{
            System.out.println("Se llamó addMinion en GameHandler sin dar un playerType válido");
            return 6;
        }
        return i;
    }

    /**
     * Elimina la carta del hand de un jugador en base a su posición
     * @param index
     * @param player
     * @return la carta eliminada
     */
    public Card removeCard(int index,String player) {
        if (index<=10) {
            if (player.equals("player")) {
                Card card = playerCards.removeCard(index);
                playerDeck.getDiscardPile().add(card);
                return card;
            } else if (player.equals("enemy")) {
                Card card = enemyCards.removeCard(index);
                return card;
            } else{
                System.out.println("Called removeCard in GameHandler with wrong player type specification");
            }
        }
        return null;
    }

    /**
     * Getter del Jugador
     * @return Jugador
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * El getter del enemigo
     * @return el enemigo
     */
    public Player getEnemy() {
        return enemy;
    }

    /**
     * Getter de un minión en vase a su indice de carta seleccionada
     * @param cardSelection
     * @return Minion elegido
     */
    public Esbirro getPlayerMinion(int cardSelection) {
        return playerMinions[cardSelection - 11];
    }

    /**
     * Getter de un minion enemigo en base a una carta seleccionada
     * @param cardSelection
     * @return Minion enemigo elegido
     */
    public Esbirro getEnemyMinion(int cardSelection) {
        return enemyMinions[cardSelection - 1];
    }

    /**
     * Reseteo del cooldown del minion después de ser usado en un turno
     */
    public void resetMinionsCD() {
        for (Esbirro minion:playerMinions) {
            if (minion != null) {
                minion.setCD(false);
            }
        }
    }

    /**
     * Getter de la mano del enemigo
     * @return Mano del enemigo
     */
    public Hand getEnemyCards() {
        return enemyCards;
    }

    /**
     * Getter de la mano del jugador
     * @return mano del jugador
     */
    public Hand getPlayerCards() {
        return playerCards;
    }

    /**
     * Elimina un minion del jugador en base a su índice
     * @param minionIndex
     */
    public void removeFriendlyMinion(int minionIndex) {
        playerMinions[minionIndex - 1] = null;
    }

    /**
     * Elimina un minion enemigo en base a su índice
     * @param minionIndex
     */
    public void removeEnemyMinion(int minionIndex) {
        enemyMinions[minionIndex - 1] = null;

    }

    /**
     * Getter del messenger para enviar mensajes desde diferentes partes del código
     */
    public Messenger getMessenger() {
        return messenger;
    }

    /**
     * Getter del Deck del jugador
     * @return Deck del jugador
     */
    public Deck getPlayerDeck() {
        return playerDeck;
    }

    public void minionAttackEnemy(Esbirro esbirro) {
        messenger.sendMsg("ATAQUE-enemigo-"+esbirro.getNombre());
    }

    public void minionAttackMinion(Esbirro esbirro, int index) {
        messenger.sendMsg("ATAQUE-"+index+"-"+esbirro.getNombre());
    }
}
