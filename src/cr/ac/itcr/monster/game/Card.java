package cr.ac.itcr.monster.game;


public class Card {

    private String id;

    private Card next;
    private Card previous;

    public Card(String id) {
        this.id = id;
    }

    public Card getNext() {
        return next;
    }

    public void setNext(Card next) {
        this.next = next;
    }

    public Card getPrevious() {
        return previous;
    }

    public void setPrevious(Card previous) {
        this.previous = previous;
    }
}
