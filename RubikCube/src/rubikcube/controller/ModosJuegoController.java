/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rubikcube.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import rubikcube.util.AppContext;
import rubikcube.util.FlowController;

/**
 * FXML Controller class
 *
 * @author robri
 */
public class ModosJuegoController extends Controller implements Initializable {

    @FXML
    private StackPane root;
    @FXML
    private StackPane spM1;
    @FXML
    private StackPane spM2;
    @FXML
    private StackPane spM3;
    @FXML
    private StackPane spM4;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @Override
    public void initialize() {
    }

    @FXML
    private void irOrdenado(MouseEvent event) {
        AppContext.getInstance().setModoJuego(1);
        FlowController.getInstance().goView("Main");
    }

    @FXML
    private void irDesordenado(MouseEvent event) {
        AppContext.getInstance().setModoJuego(2);
        FlowController.getInstance().goView("Main");
    }

    @FXML
    private void irAsistido(MouseEvent event) {
        AppContext.getInstance().setModoJuego(3);
        FlowController.getInstance().goView("Main");
    }

    @FXML
    private void irCargado(MouseEvent event) {
        AppContext.getInstance().setModoJuego(4);
        FlowController.getInstance().goView("Main");
    }
    
}
