package cr.ac.itcr.monster.game.history;

import cr.ac.itcr.monster.App;
import cr.ac.itcr.monster.game.cards.Card;
import cr.ac.itcr.monster.game.cards.Esbirro;
import cr.ac.itcr.monster.game.hand.HandCard;
import cr.ac.itcr.monster.gui.game.GameController;
import cr.ac.itcr.monster.gui.host.HostWindow;
import javafx.application.Platform;

/**
 * Clase que crea la lista de histrial para poder navegar a través de las jugadas
 */
public class History {
    private static volatile GameController instance;
    public static synchronized GameController getInstance() {
        return instance;
    }

    private Event head;
    private int size;

    /**
     * Builder de la lista doblemente enlazada
     */
    public History() {
        this.head = null;
        this.size = 0;
    }
/**
 * Getter del tamaño de la lista doblemente enlazada
 */
    public int getSize() {
        return size;
    }

    /**
     * Añadir un evento a la lista doblemente enlazada
     * @param data
     */
    public void addEvent(String data) {
        String info = parseData(data);
        Event newEvent = new Event(info);

        if (this.head == null) {
            this.head = newEvent;
            this.size++;
            return;
        }

        Event current = this.head;
        while (current.getNext() != null) {
            current = current.getNext();
        }

        newEvent.setPrev(current);
        current.setNext(newEvent);
        this.size++;

    }

    /**
     * Recibe el String de datos,lo interpreta y lo convierte en una descripción de jugada.
     * @param data
     * @return
     */
    private String parseData(String data) {
        String[] parts = data.split("-", 3);

        String type = parts[0];
        String info = parts[1];

        switch (type) {
            case "ATAQUE":
                Card card = Card.getCardByName(parts[2]);
                switch (info) {
                    case "enemigo":
                        return ("Fui atacado por " +card.getAtaque()+" puntos de vida por el "+card.getNombre()+" enemigo");
                    case "amigo":
                        return ("Ataqué al enemigo con mi " +card.getNombre()+" y le quité "+card.getAtaque()+" puntos de vida");
                    default:
                        if (Integer.parseInt(info)>5) {
                            Esbirro esbirro = GameController.getInstance().getGameHandler().getPlayerMinion(Integer.parseInt(info));
                            return ("Mi "+esbirro.getNombre()+ " atacó al " + parts[2] + "enemigo por " + esbirro.getAtaque() +
                            "puntos de daño");
                        }else{
                            Esbirro esbirro1 = GameController.getInstance().getGameHandler().getEnemyMinion(Integer.parseInt(info));
                            return ("El "+esbirro1.getNombre()+ " enemigo atacó a mi" + parts[2] + "por " + Card.getCardByName(info).getAtaque()
                            + " puntos de daño");                        }
                }
        }
        return "placeholder";
    }
}
