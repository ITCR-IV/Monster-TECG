package cr.ac.itcr.monster.game;


public class Card {

    private Object info;

    private Card next;
    private Card previous;

    public Card(Object info) {
        this.info = info;
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

    public Object getInfo() {
        return info;
    }
}
