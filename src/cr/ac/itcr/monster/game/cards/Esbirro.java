package cr.ac.itcr.monster.game.cards;

/**
 * Clase que modela al esbirro como un objeto diferente de las cartas
 */
public class Esbirro {
    int vidaTotal;
    int vida;
    int ataque;
    boolean CD;

    /**
     * Builder del esbirro
     * @param vida
     * @param ataque
     */
    public Esbirro(int vida, int ataque) {
        this.vida = vida;
        this.vidaTotal = vida;
        this.ataque = ataque;
        this.CD = false;
    }

    /**
     * Módulo de recibinmiento de daño por parte del esbirro
     * @param damageDealt
     * @return vida restante
     */
    public double damage(int damageDealt) {
        this.vida -= damageDealt;
        if (this.vida < 0) {
            this.vida = 0;
        }
        return this.vida;
    }

    /**
     * Es el cooldown de un esbirro ya que atacan una vez por turno
     * @return cooldown del esbirro
     */
    public boolean isCD() {
        return CD;
    }

    /**
     * Para cambiar el cooldown a los parametros pertinentes
     * @param CD
     */
    public void setCD(boolean CD) {
        this.CD = CD;
    }

    /**
     * Getter de la vida de un esbirro
     * @returnvida del esbirro
     */
    public int getVida() {
        return vida;
    }

    /**
     * Getter del Ataque de un esbirro
     * @return Ataque de un esbirro
     */
    public int getAtaque() {
        return ataque;
    }

    /**
     * Getter de ña vida total del esbirro
     * @return Vida total del esbirro
     */
    public int getVidaTotal() {
        return vidaTotal;
    }
}
