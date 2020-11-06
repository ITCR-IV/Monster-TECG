package cr.ac.itcr.monster.game.hand;


import cr.ac.itcr.monster.game.cards.Card;

public class HandCard {

    private Object info;

    private HandCard next;
    private HandCard previous;

    public HandCard(Card info) {
        this.info = info;
    }

    public HandCard getNext() {
        return next;
    }

    public void setNext(HandCard next) {
        this.next = next;
    }

    public HandCard getPrevious() {
        return previous;
    }

    public void setPrevious(HandCard previous) {
        this.previous = previous;
    }

    public Object getInfo() {
        return info;
    }
}
