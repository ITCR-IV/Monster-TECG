package cr.ac.itcr.monster.game.cards;

public class Esbirro {
    int vida;
    int ataque;

    public Esbirro(int vida, int ataque) {
        this.vida = vida;
        this.ataque = ataque;
    }

    public double damage(int damageDealt) {
        this.vida -= damageDealt;
        return this.vida;
    }

    public int getVida() {
        return vida;
    }

    public int getAtaque() {
        return ataque;
    }
}
