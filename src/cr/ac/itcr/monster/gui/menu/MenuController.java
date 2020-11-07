package cr.ac.itcr.monster.gui.menu;

import cr.ac.itcr.monster.gui.host.HostWindow;
import cr.ac.itcr.monster.gui.join.JoinWindow;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * Menú con botones para conectarse o hostear una partida
 */
public class MenuController {

    /**
     * Hostear a una partida
     * @param actionEvent
     * @throws Exception
     */
    @FXML
    public void host_game(ActionEvent actionEvent) throws Exception {
        HostWindow window = new HostWindow();
    }

    /**
     * Conectarse a una partida
     * @param actionEvent
     * @throws Exception
     */
    @FXML
    public void join_game(ActionEvent actionEvent) throws Exception {
        JoinWindow window = new JoinWindow();
    }
}
