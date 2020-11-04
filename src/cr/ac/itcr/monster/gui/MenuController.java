package cr.ac.itcr.monster.gui;

import cr.ac.itcr.monster.gui.host.HostWindow;
import cr.ac.itcr.monster.gui.join.JoinWindow;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MenuController {
    @FXML
    public void host_game(ActionEvent actionEvent) throws Exception {
        HostWindow window = new HostWindow();
    }

    @FXML
    public void join_game(ActionEvent actionEvent) throws Exception {
        JoinWindow window = new JoinWindow();
    }
}
