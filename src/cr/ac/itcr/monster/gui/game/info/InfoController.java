package cr.ac.itcr.monster.gui.game.info;

import cr.ac.itcr.monster.communication.Host;
import cr.ac.itcr.monster.game.cards.Card;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Clase encargada de controlar la información mostrada en pantalla de la carta
 * Es más que nada la parte lógica de la ventana de información
 */

public class InfoController {
    @FXML
    private Label nameLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label manaLabel;
    @FXML
    private Label typeLabel;
    @FXML
    private Button cancelButton;

    /**
     * Maneja el botón que devuelve el jugador a la pantalla de juego después de vver la información de la carta
     * @param actionEvent
     */
    @FXML
    private void cancelButtonAction(ActionEvent actionEvent){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Hace el display de toda la información de una carta en pantalla
     * @param card
     */
    public void setup(Card card) {
        nameLabel.setText(card.getNombre());
        typeLabel.setText(card.getType());
        manaLabel.setText(String.valueOf(card.getCoste()));

        if (card.getType().equals("Esbirro")) {
            descriptionLabel.setText(card.getNombre()+" con " + card.getAtaque() + " de ataque y "+ card.getVida() +" de vida." );
        } else {
            descriptionLabel.setText(card.getDescripcion());
        }

    }
}
