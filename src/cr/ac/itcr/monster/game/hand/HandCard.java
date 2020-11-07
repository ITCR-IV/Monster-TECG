package cr.ac.itcr.monster.game.hand;


import cr.ac.itcr.monster.game.cards.Card;

public class HandCard {

    private Card info;

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

    public Card getInfo() {
        return info;
    }
}
