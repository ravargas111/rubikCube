/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rubikcube.controller;

import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTreeTableView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeTableColumn;
import rubikcube.logic.RankingMovimientos.Espacio;

/**
 * FXML Controller class
 *
 * @author robri
 */
public class RankingController extends Controller implements Initializable {
    @FXML
    private JFXDialogLayout root;

    @FXML
    private JFXTreeTableView<?> tablaRank;

    @FXML
    private TreeTableColumn<Espacio, String> colNombre;

    @FXML
    private TreeTableColumn<Espacio, String> colMov;

    @FXML
    private TreeTableColumn<Espacio, String> colTiempo;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @Override
    public void initialize() {
    }
    
    public void llenarRankMov(){
        
    }
    
    
    public void llenarRankTiempo(){
        
    }
}
