package cr.ac.itcr.monster.game.cards;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;

public class Card {
    private String nombre;
    private String type;
    private int vida;
    private int ataque;
    private int coste;
    private String descripcion;

    public static Card[] allCards = new Card[40];
    public static HashMap<String, Card> allCardsDict = new HashMap<String, Card>();
    public static Card[] hechizos = new Card[10];
    public static Card[] esbirros = new Card[20];
    public static Card[] secretos = new Card[10];

    public Card() {

    }

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


            String basePath = new File("").getAbsolutePath();
            File json = Paths.get(basePath,"resources","JSON_Cartas.json").toFile();
            System.out.println("Attempting to read from file in: "+json.getCanonicalPath());

            allCards = mapper.readValue(json,Card[].class); //convert JSON array to
            hechizos = Arrays.copyOfRange(allCards, 0, 10);
            esbirros = Arrays.copyOfRange(allCards, 10, 30);
            secretos = Arrays.copyOfRange(allCards, 30, 40);

            for (Card card : allCards) {
                allCardsDict.put(card.getNombre(), card);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Card[] getAllCards() {
        return allCards;
    }

    public static Card getCardByName(String name) {
        return allCardsDict.get(name);
    }

    public static Card[] getHechizos() {
        return hechizos;
    }

    public static Card[] getEsbirros() {
        return esbirros;
    }

    public static Card[] getSecretos() {
        return secretos;
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
