package cr.ac.itcr.monster.game;

/**
 * Clase que representa un jugador y sus cualidades principales
 */
public class Player {
    int vida = 1000;
    int mana = 200;

    /**
     * Getter de la vida del jugador
     * @return Vida del jugador
     */
    public double getVida() {
        return vida;
    }

    /**
     * Recibimiento de daño por parte de un jugador
     * @param vida
     * @returnVida después del ataque
     */
    public double pierdeVida(int vida) {
        this.vida -= vida;
        if (this.vida < 0) {
            this.vida = 0;
        }
        return this.vida;
    }

    /**
     * Recuperacion de vida por parte del jugador
     * @param vida
     * @returnVida después de ser regenerada
     */
    public double recuperaVida(int vida) {
        this.vida += vida;
        if (this.vida > 1000) {
            this.vida = 1000;
        }
        return this.vida;
    }

    /**
     * Getter del Mana de cada jugador
     * @return
     */
    public double getMana() {
        return mana;
    }

    /**
     * Pérdida de mana por parte del jugador
     * @param mana
     * @return Mana después de la pérdida
     */
    public double pierdeMana(int mana) {
        this.mana -= mana;
        if (this.mana<0) {
            this.mana = 0;
        }
        return this.mana;
    }

    /**
     * Recuperación del maná por parte del jugador
     * @param mana
     * @returnMana después de su recuperación
     */
    public double recuperaMana(int mana) {
        this.mana += mana;
        if (this.mana > 1000) {
            this.mana = 1000;
        }
        return this.mana;
    }
}
