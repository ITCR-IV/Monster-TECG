package cr.ac.itcr.monster.game;

import cr.ac.itcr.monster.game.cards.Card;

import java.util.Arrays;
import java.util.Random;

public class Deck {
    private int top = 0;
    private Card[] deck = new Card[20];

    //Añadir al tope de la pila
    public void push(Card carta){
        this.deck[top]=carta;
        top++;
    }
    //Sacar la carta que esté arriba
    public Object draw(){
        if (top > 0) {
            return this.deck[--top];

        }
        return null;
    }

    //Ver la carta que esté arriba
    public Object peek(){
        return this.deck[top-1];
    }

    //Refillear con cartas aleatorias (Sería la llamada que hace en el app)
    public void refill(){
        Random random = new Random(); //instance of random class
            for (int i = 0; i < 7; i++) {
                push(Card.getHechizos()[random.nextInt(10)]);
            }
            for (int i = 0; i < 9; i++) {
                push(Card.getEsbirros()[random.nextInt(20)+10]);
            }
            for (int i = 0; i < 4; i++) {
                push(Card.getSecretos()[random.nextInt(10)+30]);
            }

            for (int i = 0; i < 20; i++) { //shuffling of the deck
                int randomIndexToSwap = random.nextInt(20);
                Card temp = deck[randomIndexToSwap];
                deck[randomIndexToSwap] = deck[i];
                deck[i] = temp;
        }
    }
    //Resetear el deck a 0
    public void Reset(){
        Arrays.fill(this.deck,null);
    }
}
