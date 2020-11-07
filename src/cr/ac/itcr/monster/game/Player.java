package cr.ac.itcr.monster.game;

public class Player {
    int vida = 1000;
    int mana = 200;

    public int getVida() {
        return vida;
    }

    public void pierdeVida(int vida) {
        this.vida -= vida;
    }

    public void recuperaVida(int vida) {
        this.vida += vida;
    }

    public int getMana() {
        return mana;
    }

    public void pierdeMana(int mana) {
        this.mana -= mana;
    }

    public void recuperaMana(int mana) {
        this.mana += mana;
    }
}
