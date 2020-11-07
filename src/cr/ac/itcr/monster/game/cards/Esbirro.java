package cr.ac.itcr.monster.game.cards;

public class Esbirro {
    String nombre;
    int vidaTotal;
    int vida;
    int ataque;
    boolean CD;

    public Esbirro(int vida, int ataque, String nombre) {
        this.vida = vida;
        this.vidaTotal = vida;
        this.ataque = ataque;
        this.CD = false;
    }

    public double damage(int damageDealt) {
        this.vida -= damageDealt;
        if (this.vida < 0) {
            this.vida = 0;
        }
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

    public int getVidaTotal() {
        return vidaTotal;
    }

    public String getNombre() {
        return nombre;
    }
}
