package cr.ac.itcr.monster.game.cards;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Paths;

public class Card {
    private String nombre;
    private String type;
    private int vida;
    private int ataque;
    private int coste;
    private String descripcion;

    public static Card[] allCards = new Card[40];

    public Card(String nombre, String type, int vida, int ataque, int coste, String descripcion) {
        this.nombre = nombre;
        this.type = type;
        this.vida = vida;
        this.ataque = ataque;
        this.coste = coste;
        this.descripcion = descripcion;
    }

    public static void loadCards() {
        try {
            ObjectMapper mapper = new ObjectMapper(); //create mapper

            allCards = mapper.readValue(Paths.get("JSON_cartas.json").toFile(),Card[].class); //convert JSON array to

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Card[] getAllCards() {
        return allCards;
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
