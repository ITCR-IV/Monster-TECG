package cr.ac.itcr.monster.game.history;

/**
 * Clase que almacena eventos que eventuakmente son nodos para una lista doblemente enlazada
 */
public class Event {
    private String info;

    private Event next;
    private Event prev;

    /**
     * builder de el nodo evento
     * @param data
     */
    public Event(String data) {
        this.info = data;
    }

    /**
     * Getter de la información pertinente a la Jugada
     * @return
     */
    public String getInfo() {
        return info;
    }

    /**
     * Getter del nodo que tendrá enlazado al siguiente
      * @return
     */
    public Event getNext() {
        return next;
    }

    /**
     * Setter del siguiente nodo
     * @param next
     */
    public void setNext(Event next) {
        this.next = next;
    }

    /**
     * getter del nodo anterior a este
     * @return
     */
    public Event getPrev() {
        return prev;
    }

    /**
     * Setter sel nodo anterior a Este
     * @param prev
     */
    public void setPrev(Event prev) {
        this.prev = prev;
    }
}
