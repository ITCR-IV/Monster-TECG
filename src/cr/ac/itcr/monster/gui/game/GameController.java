package cr.ac.itcr.monster.gui.game;

import cr.ac.itcr.monster.App;
import cr.ac.itcr.monster.communication.Client;
import cr.ac.itcr.monster.communication.Host;
import cr.ac.itcr.monster.game.GameHandler;
import cr.ac.itcr.monster.game.cards.Card;
import cr.ac.itcr.monster.game.cards.Esbirro;
import cr.ac.itcr.monster.gui.game.info.InfoWindow;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Clase encargada de manejar más que nada el gui del juego
 */
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
    private Button playCardButton,infoButton;
    public Button endTurnButton;

    //logical variables
    private GameHandler gameHandler;
    public GameHandler getGameHandler() {
        return gameHandler;
    }

    private static boolean turn;
    private String playerType;
    private int deckSize=20;
    private int enemyDeckSize=20;
    private int friendlyMinionsSize;
    private int enemyMinionsSize;
    private int handSize;
    private int enemyHandSize;
    private int cardSelection;
    private boolean targeting;
    private boolean freeze =false;

    /**
     * Hace el set up inicial del juego, define quien va a mover la primera jugada, da las características necesarias
     * del GUI y da al jugador las cartas iniciales
     * @param player
     */
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
            playCardButton.setDisable(true);
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
            stackPane.setOnMouseClicked(event -> targetEnemyMinion(finalI));
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

    /**
     * Cambia el turno de un jugador y hace los cambios que esto conlleve
     * @param actionEvent
     */
    @FXML
    public void switchTurn(ActionEvent actionEvent) {
        this.turn = !turn;
            if (turn) { //empezar turno
                gameHandler.resetMinionsCD();
                endTurnButton.setDisable(false);
                playCardButton.setDisable(false);
                drawCard();
                recoverMana(250,"player");
            } else { //terminar turno
                if (freeze) {
                    Freeze(false);
                }
                endTurnButton.setDisable(true);
                playCardButton.setDisable(true);
                recoverMana(250,"enemy");
                if (playerType.equals("host")) {
                    Host.getHost().sendMsg("ACTION-switch turn");
                } else if (playerType.equals("client")) {
                    Client.getClient().sendMsg("ACTION-switch turn");
                }
            }
        resetCardSelection();
    }

    /**
     * Muestra la recuperación de man´´a en la pantalla y ordena al gameHandler a hacer lo mismo
     * @param recoveredMana
     * @param playerType
     */
    public void recoverMana(int recoveredMana,String playerType) {
        if (playerType.equals("player")) {
            ProgressBar manaBar = (ProgressBar) player.getChildren().get(1);
            Text manaText = (Text) player.getChildren().get(3);
            double mana = gameHandler.getPlayer().recuperaMana(recoveredMana);
            manaBar.setProgress(mana/1000);
            manaText.setText((int) mana+"/1000");
        } else if (playerType.equals("enemy")) {
            ProgressBar manaBar = (ProgressBar) enemy.getChildren().get(1);
            Text manaText = (Text) enemy.getChildren().get(3);
            double mana = gameHandler.getEnemy().recuperaMana(recoveredMana);
            manaBar.setProgress(mana/1000);
            manaText.setText((int) mana+"/1000");
        } else {
            System.out.println("Llamada con playerType incorrecto a recoverMana en GameController");
        }
    }

    /**
     * Registra el gasto de maná principalmente cuando se utiliza una carta
     * @param spentMana
     * @param playerType
     */
    public void spendMana(int spentMana, String playerType) {
        if (playerType.equals("player")) {
            ProgressBar manaBar = (ProgressBar) player.getChildren().get(1);
            Text manaText = (Text) player.getChildren().get(3);
            double mana = gameHandler.getPlayer().pierdeMana(spentMana);
            manaBar.setProgress(mana/1000);
            manaText.setText((int) mana+"/1000");
        } else if (playerType.equals("enemy")) {
            ProgressBar manaBar = (ProgressBar) enemy.getChildren().get(1);
            Text manaText = (Text) enemy.getChildren().get(3);
            double mana = gameHandler.getEnemy().pierdeMana(spentMana);
            manaBar.setProgress(mana/1000);
            manaText.setText((int) mana+"/1000");
        } else {
            System.out.println("Llamada con playerType incorrecto a spendMana en GameController");
        }
    }

    /**
     * Reestablece la vida de un jugador y obliga al handler a hacer lo mismo
     * @param lifeHealed
     * @param playerType
     */
    public void heal(int lifeHealed, String playerType) {
        if (playerType.equals("player")) {
            ProgressBar lifeBar = (ProgressBar) player.getChildren().get(0);
            Text lifeText = (Text) player.getChildren().get(2);
            double life = gameHandler.getPlayer().recuperaVida(lifeHealed);

            lifeBar.setProgress(life/1000);
            lifeText.setText((int) life+"/1000");
        } else if (playerType.equals("enemy")) {
            ProgressBar lifeBar = (ProgressBar) enemy.getChildren().get(0);
            Text lifeText = (Text) enemy.getChildren().get(2);
            double life = gameHandler.getEnemy().recuperaVida(lifeHealed);
            lifeBar.setProgress(life/1000);
            lifeText.setText((int) life+"/1000");
        } else {
            System.out.println("Llamada con playerType incorrecto a heal en GameController");
        }
    }

    /**
     * Mata automáticamente a un minion de un jugador en base a su indice
     * y fuerza al game Handler a registrarlo
     * @param minionIndex
     * @param playerType
     */
    public void killMinion(int minionIndex, String playerType) {
        if (playerType.equals("player")) {
            for (Node item: playerMinions.get(minionIndex-1).getChildren()) {
                Shape shape = (Shape) item;
                shape.setStroke(Color.rgb(159, 159, 159));
                shape.setFill(Color.rgb(159, 159, 159));
            }
            gameHandler.removeFriendlyMinion(minionIndex);
        } else if (playerType.equals("enemy")) {
            for (Node item: enemyMinions.get(minionIndex-1).getChildren()) {
                Shape shape = (Shape) item;
                shape.setStroke(Color.rgb(159, 159, 159));
                shape.setFill(Color.rgb(159, 159, 159));
            }
            gameHandler.removeEnemyMinion(minionIndex);
        }else {
            System.out.println("Llamada con playerType incorrecto a killMinion en GameController");
        }
    }

    /**
     * Hace daño a un Minion en base a su índice y fuerza al
     * game Handler a registrar el daño sufrido por el minión
     * @param damageDealt
     * @param minionIndex
     * @param playerType
     */
    public void damageMinion(int damageDealt, int minionIndex, String playerType) {
        if (playerType.equals("player")) {
            Text lifeText = (Text) playerMinions.get(minionIndex - 1).getChildren().get(3);
            int totalLife = gameHandler.getPlayerMinion(minionIndex+10).getVidaTotal();
            double life = gameHandler.getPlayerMinion(minionIndex+10).damage(damageDealt);

            lifeText.setText((int) life+"/"+totalLife);

            if (life == 0) {
                killMinion(minionIndex,playerType);
            }
        } else if (playerType.equals("enemy")) {
            Text lifeText = (Text) enemyMinions.get(minionIndex - 1).getChildren().get(3);
            int totalLife = gameHandler.getEnemyMinion(minionIndex).getVidaTotal();
            double life = gameHandler.getEnemyMinion(minionIndex).damage(damageDealt);
            lifeText.setText((int) life+"/"+totalLife);
            if (life == 0) {
                killMinion(minionIndex,playerType);
            }
        } else {
            System.out.println("Llamada con playerType incorrecto a damageMinion en GameController");
        }
    }

    /**
     * Registra el daó recibido por alguno de los jugadores y hace que el game Handler registre igualmente los daós
     * @param damageDealt
     * @param playerType
     */
    public void takeDamage(int damageDealt, String playerType) {
        if (playerType.equals("player")) {
            ProgressBar lifeBar = (ProgressBar) player.getChildren().get(0);
            Text lifeText = (Text) player.getChildren().get(2);
            double life = gameHandler.getPlayer().pierdeVida(damageDealt);

            lifeBar.setProgress(life/1000);
            lifeText.setText((int) life+"/1000");
            if (life == 0) {
                App.endGame();
            }
        } else if (playerType.equals("enemy")) {
            ProgressBar lifeBar = (ProgressBar) enemy.getChildren().get(0);
            Text lifeText = (Text) enemy.getChildren().get(2);
            double life = gameHandler.getEnemy().pierdeVida(damageDealt);
            lifeBar.setProgress(life/1000);
            lifeText.setText((int) life+"/1000");
            if (life == 0) {
                App.endGame();
            }
        } else {
            System.out.println("Llamada con playerType incorrecto a takeDamage en GameController");
        }
    }

    /**
     * Perminte sacar una carta del deck y la pone a disposición del jugador
     */
    public void drawCard() {
        if (handSize >= 10) {
            return;
        }
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

    /**
     * Registra la carta que el enemigo sacó y obliga al game Handler a registrarlo
     * @param cardName
     */
    public void enemyDraw(String cardName) {
        if (enemyHandSize >= 10) {
            return;
        }
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

    /**
     * Selecciona una carta enbase a su índice
     * @param index
     */
    public void selectCard(int index) {
        Rectangle rect;
        System.out.println(index);
        if (index <= 10) { //cartas de mano
            if (index > handSize) {
                return;
            }
            rect = (Rectangle) playerCards.get(index-1).getChildren().get(0);
        } else{ //minions
            if (index-10 > friendlyMinionsSize) {
                return;
            }
            if (gameHandler.getPlayerMinion(index) == null) {
                return;
            }
            if (gameHandler.getPlayerMinion(index).isCD()) {
                return;
            }
            rect = (Rectangle) playerMinions.get(index-11).getChildren().get(0);
        }
        rect.setStroke(Color.DEEPSKYBLUE);

        resetCardSelection();
        this.cardSelection = index;
        System.out.println(index);
    }

    /**
     * Deselecciona la carta previamente seleccionada por el jugador
     */
    public void resetCardSelection() {
        Rectangle rect;
        if (cardSelection > 0) {
            if (this.cardSelection <= 10) {
                rect = (Rectangle) playerCards.get(cardSelection - 1).getChildren().get(0);
            } else {
                rect = (Rectangle) playerMinions.get(cardSelection - 11).getChildren().get(0);
            }
            if (cardSelection != handSize + 1) { //para eliminar caso donde uno usa la última carta
                rect.setStroke(Color.BLACK);
            }
        }
        this.cardSelection = 0;
        this.targeting = false;
    }

    /**
     * Si el target es un minion hace los daños o cambios que este debe de sufrir
     * @param index
     */
    public void targetEnemyMinion(int index) {
        System.out.println("estripado el enemigo"+ index);
        if (!targeting) {
            return;
        }
        if (cardSelection <= 10) {
            Card card = gameHandler.getPlayerCard(cardSelection);
            if (card.getCoste() > gameHandler.getPlayer().getMana()) {
                return;
            }
            String type = card.getType();
            switch (type) {
                case "Hechizo":
                    switch (card.getNombre()) {
                        case "Bola de Fuego":
                            damageMinion(card.getAtaque(),index,"enemy");
                            gameHandler.getMessenger().sendMsg("HECHIZO-BoladeFuego-"+Integer.toString(index));
                            break;
                        case "Congelar":
                            break;
                        case "Curar":
                            break;
                        case "Poder Supremo":
                            break;
                        case "Robar Carta":
                            break;
                        case "Recuperar Cartas":
                            break;
                        case "Asesinar":
                            killMinion(index,"enemy");
                            gameHandler.getMessenger().sendMsg("HECHIZO-Asesinar-"+Integer.toString(index));
                            break;
                        case "Destrucción":
                            break;
                        case "Refill":
                            break;
                        case "Conversión":
                            break;

                    }
                    break;
            }
        } else { //este else siginifica que se ataca con un esbirro
            Esbirro esbirro = gameHandler.getPlayerMinion(cardSelection);
            damageMinion(esbirro.getAtaque(),index,"enemy");
            gameHandler.getPlayerMinion(cardSelection).setCD(true);
            gameHandler.minionAttackMinion(esbirro,index);
        }
        resetCardSelection();
    }

    /**
     * En caso de que el objetivo a hacer daño sea el jugador, hace que este reciba el daño pertinente
     * @param mouseEvent
     */
    public void targetEnemy(MouseEvent mouseEvent) {
        System.out.println("clicked on enemy");
        if (!targeting) {
            return;
        }
        if (cardSelection <= 10) {
            Card card = gameHandler.getPlayerCard(cardSelection);
            if (card.getCoste() > gameHandler.getPlayer().getMana()) {
                return;
            }
            String type = card.getType();
            switch (type) {
                case "Hechizo":
                    switch (card.getNombre()) {
                        case "Bola de Fuego":
                            takeDamage(card.getAtaque(),"enemy");
                            gameHandler.getMessenger().sendMsg("HECHIZO-Bola de Fuego-enemigo");
                            break;
                        case "Congelar":
                            break;
                        case "Curar":
                            break;
                        case "Poder Supremo":
                            break;
                        case "Robar Carta":
                            break;
                        case "Recuperar Cartas":
                            break;
                        case "Asesinar":
                            break;
                        case "Destrucción":
                            break;
                        case "Refill":
                            break;
                        case "Conversión":
                            break;

                    }
                    break;
            }
        } else { //este else siginifica que se ataca con un esbirro
            Esbirro esbirro = gameHandler.getPlayerMinion(cardSelection);
            takeDamage(esbirro.getAtaque(),"enemy");
            gameHandler.getPlayerMinion(cardSelection).setCD(true);
            gameHandler.minionAttackEnemy(esbirro);
        }
        resetCardSelection();
    }

    /**
     * Enseña la información de una carta determinada
     * @param actionEvent
     */
    public void displayInfo(ActionEvent actionEvent) {
        if (cardSelection>0 && cardSelection<=10) {
            InfoWindow info = new InfoWindow(gameHandler.getPlayerCard(cardSelection));
        }
    }

    /**
     * Añade un minion al array de minions de un jugador
     * @param card
     * @param player
     */
    public void addMinion(Card card,String player) {
        int position = 6;
        position = gameHandler.addMinion(card,player);

        if (position<5) {
            StackPane guiMinion = null;
            if (player.equals("player")) {
                guiMinion = playerMinions.get(position);
                friendlyMinionsSize++;
            } else if (player.equals("enemy")) {
                guiMinion = enemyMinions.get(position);
                removeEnemyCard();
                enemyMinionsSize++;
            }

            ObservableList<Node> elements = guiMinion.getChildren();
            for (Node item: elements) {
                Shape shape = (Shape) item;
                shape.setStroke(Color.BLACK);
                shape.setFill(Color.BLACK);
            }
            Rectangle rect = (Rectangle) elements.get(0);
            Text name = (Text) elements.get(1);
            Text attack = (Text) elements.get(2);
            Text life = (Text) elements.get(3);
            rect.setFill(Color.rgb(255, 241, 221));
            name.setText(card.getNombre().replaceAll(" ","\n"));
            attack.setText(String.valueOf(card.getAtaque()));
            attack.setFill(Color.rgb(29, 22, 232));
            life.setText(card.getVida()+"/"+card.getVida());
            life.setFill(Color.rgb(221, 53, 53));
        }
    }

    /**
     * Se activa cuando se juegan las cartas y detecta que carta se jugó
     * @param actionEvent
     */
    public void playCard(ActionEvent actionEvent) {
        if (cardSelection == 0) {
            return;
        }
        if (cardSelection <= 10) {
            Card card = gameHandler.getPlayerCard(cardSelection);
            if (card.getCoste() > gameHandler.getPlayer().getMana()) {
                return;
            }
            String type = card.getType();
            switch (type) {
                case "Esbirro":
                    addMinion(card, "player");
                    spendMana(card.getCoste(), "player");
                    break;
                case "Hechizo":
                        switch (card.getNombre()) {
                            case "Bola de Fuego":
                                break;
                            case "Congelar":
                                gameHandler.getMessenger().sendMsg("HECHIZO-freeze");
                                break;
                            case "Curar":
                                int curar=(int) Math.random()*501;
                                heal(curar,"player");
                                gameHandler.getMessenger().sendMsg("HECHIZO-Curar-"+Integer.toString(curar));
                                break;
                            case "Poder Supremo":
                                break;
                            case "Robar Carta":
                                int robar=(int) (Math.random()*getInstance().enemyDeckSize);
                                gameHandler.getPlayerCards().addCard(gameHandler.getEnemyCards().removeCard(++robar));
                                int robar2=(int) (Math.random()*getInstance().enemyDeckSize);
                                gameHandler.getPlayerCards().addCard(gameHandler.getEnemyCards().removeCard(++robar2));
                                break;

                            case "Recuperar Cartas":
                                break;
                            case "Asesinar":
                                break;
                            case "Destrucción":
                                break;
                            case "Refill":
                                drawCard();
                                drawCard();
                                drawCard();
                                break;
                            case "Conversión":
                                break;

                        }
            }
        } else { //este else siginifica que se juega un esbirro
            if (this.freeze) {
                return;
            }
            Rectangle rect = (Rectangle) playerMinions.get(cardSelection-11).getChildren().get(0);
            rect.setStroke(Color.CRIMSON);
            targeting = true;
        }
        removeCard(cardSelection);
        if (!targeting) {
            resetCardSelection();
        }

    }

    /**
     * Su principal objetivo es cambiar la variable congelar cuando se usó la carta de hechizos que tiene este efecto
     * @param freeze
     */
    public void Freeze (boolean freeze){
        if (freeze){
            this.freeze =true;
            for (int i = 1; i <= 5; i++) {
                if (gameHandler.getPlayerMinion(i + 10) != null) {
                    Rectangle rect = (Rectangle) playerMinions.get(i - 1).getChildren().get(0);
                    rect.setFill(Color.LIGHTSKYBLUE);
                }
            }
        }else{
            for (int i = 1; i <= 5; i++) {
                if (gameHandler.getPlayerMinion(i + 10) != null) {
                    Rectangle rect = (Rectangle) playerMinions.get(i - 1).getChildren().get(0);
                    rect.setFill(Color.rgb(255, 241, 221));
                }
            }
            this.freeze =false;
        }
    }

    /**
     * Elimina una carta en base a su índice
     * @param index
     */
    public void removeCard(int index) {

        if (index <= 10) {
            for (int i = index; i < handSize; i++) {

                ObservableList<Node> children = playerCards.get(i).getChildren();
                StackPane stackPane = playerCards.get(i - 1);
                stackPane.getChildren().setAll(children);
                FXMLLoader cardLoader = new FXMLLoader(getClass().getResource("childrenCard.fxml"));
                try {
                    StackPane clone = cardLoader.load();
                    children.addAll(clone.getChildren());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            for (Node item : playerCards.get(handSize-1).getChildren()) {
                Shape shape = (Shape) item;
                shape.setStroke(Color.rgb(244, 244, 244));
                shape.setFill(Color.rgb(244, 244, 244));
            }
                handSize--;
        } else {
        }
        gameHandler.removeCard(index,"player");
    }

    /**
     * Elimina una Carta del enemigo en base a su indice
     */
    public void removeEnemyCard() {
        for (Node item : enemyCards.get(enemyHandSize-1).getChildren()) {
            Shape shape = (Shape) item;
            shape.setStroke(Color.rgb(244, 244, 244));
            shape.setFill(Color.rgb(244, 244, 244));
        }
        Card card = gameHandler.removeCard(enemyHandSize,"enemy");
        spendMana(card.getCoste(),"enemy");
        enemyHandSize--;
    }
}
