package cr.ac.itcr.monster.game.cards;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Es el objeto que rebibe el archivo JSON representa a una carta y todos sus parámetros
 */
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

     /**
      *En caso de que se quiera crear una carta vacía
      */
    public Card() {

    }

    /**
     * Card Builder
     * @param nombre
     * @param type
     * @param vida
     * @param ataque
     * @param coste
     * @param descripcion
     */
    public Card(String nombre, String type, int vida, int ataque, int coste, String descripcion) {
        this.nombre = nombre;
        this.type = type;
        this.vida = vida;
        this.ataque = ataque;
        this.coste = coste;
        this.descripcion = descripcion;
    }

    /**
     * Obtiene las cartas del archivo JSON y las procesa dentro de Arrays
     */
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

    /**
     * getter de todas las cartas
     * @return Array de todas las cartas
     */
    public static Card[] getAllCards() {
        return allCards;
    }

    /**
     * Getter de una carta utilizando el nombre como parámetro de búsqueda
     * @param name
     * @return Carta con el nombre establecido
     */
    public static Card getCardByName(String name) {
        return allCardsDict.get(name);
    }

    /**
     * Getter Array hechizo
     * @return Array con todos los hechizos
     */
    public static Card[] getHechizos() {
        return hechizos;
    }

    /**
     * Getter Array de Esbirros
     * @return Array con todos los esbirros
     */
    public static Card[] getEsbirros() {
        return esbirros;
    }

    /**
     * Getter de todos los secretos
     * @return Array con todos los secretos
     */
    public static Card[] getSecretos() {
        return secretos;
    }

    /**
     * Getter del nombre de una carta
     * @return Nombre de la carta
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Getter del tipo de una carta
     * @return Tipo de la carta
     */
    public String getType() {
        return type;
    }

    /**
     * Getter de la vida de una carta
     * @return vida de la carta
     */
    public int getVida() {
        return vida;
    }

    /**
     * Getter dekl ataque de una carta
     * @return ataque de la carta
     */
    public int getAtaque() {
        return ataque;
    }

    /**
     * getter del coste de lmana de una carta
     * @return coste de mana de la carta
     */
    public int getCoste() {
        return coste;
    }

    /**
     * Getter de la descripción de una carta
     * @return Descripción de la carta
     */
    public String getDescripcion() {
        return descripcion;
    }
}
