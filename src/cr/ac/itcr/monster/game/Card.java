package cr.ac.itcr.monster.game;

public class Card {
    private String nombre;
    private String type;
    private int vida;
    private int ataque;
    private int coste;
    private String descripcion;

    public Card(String nombre, String type, int vida, int ataque, int coste, String descripcion) {
        this.nombre = nombre;
        this.type = type;
        this.vida = vida;
        this.ataque = ataque;
        this.coste = coste;
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getType() {
        return type;
    }

    public int getVida() {
        return vida;
    }

    public int getAtaque() {
        return ataque;
    }

    public int getCoste() {
        return coste;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
