package cr.ac.itcr.monster.game;

public class Deck {
    private int top, size;
    private Object[] deck;

    //Añadir al tope de la pila
    public void push(Object carta){
        this.deck[top++]=carta;
        ++this.size;
    }
    //Sacar la carta que esté arriba
    public Object draw(){
        --this.size;
        return this.deck[top--];
    }
    //Ver la carta que esté arriba
    public Object peek(){
        return this.deck[top];
    }

    //Refillear con cartas aleatorias (Sería la llamada que hace en el app)
    public void refill(){
        while(size!=20){
            push("Aquí va el Json.Hechizo...");
            ++size;
        }
    }
    //Resetear el deck a 0
    public void Reset(){
        this.deck=null;
    }
}
