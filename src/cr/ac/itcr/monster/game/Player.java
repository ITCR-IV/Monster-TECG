package cr.ac.itcr.monster.game;

public class Player {
    int vida = 1000;
    int mana = 200;

    public double getVida() {
        return vida;
    }

    public double pierdeVida(int vida) {
        this.vida -= vida;
        if (this.vida < 0) {
            this.vida = 0;
        }
        return this.vida;
    }

    public double recuperaVida(int vida) {
        this.vida += vida;
        if (this.vida > 1000) {
            this.vida = 1000;
        }
        return this.vida;
    }

    public double getMana() {
        return mana;
    }

    public double pierdeMana(int mana) {
        this.mana -= mana;
        if (this.mana<0) {
            this.mana = 0;
        }
        return this.mana;
    }

    public double recuperaMana(int mana) {
        this.mana += mana;
        if (this.mana > 1000) {
            this.mana = 1000;
        }
        return this.mana;
    }
}
