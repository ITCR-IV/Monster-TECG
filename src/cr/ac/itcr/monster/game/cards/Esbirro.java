package cr.ac.itcr.monster.game.cards;

public class Esbirro {
    int vida;
    int ataque;
    boolean CD;

    public Esbirro(int vida, int ataque) {
        this.vida = vida;
        this.ataque = ataque;
        this.CD = false;
    }

    public double damage(int damageDealt) {
        this.vida -= damageDealt;
        return this.vida;
    }

    public boolean isCD() {
        return CD;
    }

    public void setCD(boolean CD) {
        this.CD = CD;
    }

    public int getVida() {
        return vida;
    }

    public int getAtaque() {
        return ataque;
    }
}
