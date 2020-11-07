package cr.ac.itcr.monster.game.hand;

import cr.ac.itcr.monster.game.cards.Card;

public class Hand { //hand is indexed starting at 0
    private HandCard head;
    private int size;

    public Hand() {
        this.head = null;
        this.size = 0;
    }

    public int getSize() {
        return size;
    }

    public void addCard(Card card) {
        HandCard newHandCard = new HandCard(card);

        if (this.size == 10) { //max 10 cards in hand
            return;
        } else if (this.size > 10) {
            System.out.println("Error: Maximum hand capacity surpassed"); //esto es solo para que nos demos cuenta si hicimos algo mal
            return;
        }

        if (this.head == null) {
            this.head = newHandCard;
            this.head.setNext(this.head);
            this.head.setPrevious(this.head);
            this.size++;
            return;
        }

        HandCard current = this.head;
        for (int i = 1; i < size; i++) {
            current = current.getNext();
        }

        newHandCard.setPrevious(current);
        current.setNext(newHandCard);
        newHandCard.setNext(this.head);
        this.head.setPrevious(newHandCard);
        this.size++;
    }

    public Card getCard(int position) {
        HandCard current = this.head;
        if (current == null) {
            System.out.println("Error: getCard called on empty hand"); //for possible bugfixing purposes
        }
        for (int i = 1; i < position; i++) { //hand is indexed starting at 1
            current = current.getNext();
        }
        return current.getInfo();
    }

    public Card removeCard(int position) {
        HandCard current = this.head;

        if (current == null) {
            System.out.println("Error: removeCard called on empty hand"); //for possible bugfixing purposes
        }
        if (size == 1) {
            this.head = null;
            size--;
            return current.getInfo();
        }
        if (position==1) { //in case it's the first one
            this.head = current.getNext();
            this.head.setPrevious(current.getPrevious().getPrevious());
            this.head.getPrevious().setNext(this.head);
            this.size--;
            return current.getInfo();
        }

        for (int i = 1; i < position; i++) { //hand is indexed starting at 1
            current = current.getNext();
        }

        //if it's a card in the middle (it's a circular list, all cards are in the middle)
        current.getPrevious().setNext(current.getNext());
        current.getNext().setPrevious(current.getPrevious());
        this.size--;
        return current.getInfo();
    }


}
