/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rubikcube.controller;

import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXListView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.TilePane;
import rubikcube.logic.RankingCard;
import rubikcube.logic.RankingMovimientos;
import rubikcube.logic.RankingTiempo;

/**
 * FXML Controller class
 *
 * @author robri
 */
public class RankingController extends Controller implements Initializable {
    
    @FXML
    private JFXDialogLayout root;

    @FXML
    private TilePane movTP;

    @FXML
    private TilePane timeTP;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @Override
    public void initialize() {
        llenarMovRank();
        llenarTimeRank();
    }
    
    public void llenarMovRank(){
        int pos = 1;
        for(RankingMovimientos.Espacio esp : RankingMovimientos.getInstance().getRanking()){
            RankingCard rc = new RankingCard(pos, esp.user, String.valueOf(esp.mov), " mov");
            movTP.getChildren().add(rc);
            pos++;
        }
        
        //pruebas
        /*for(int i=0; i<8; i++){
            RankingCard rc = new RankingCard(i+1, "User:" + String.valueOf(i), String.valueOf((i+2)*10), " mov");
            movTP.getChildren().add(rc);
        }*/
    }
    
    public void llenarTimeRank(){
        int pos = 1;
        for(RankingTiempo.Espacio esp : RankingTiempo.getInstance().getRanking()){
            RankingCard rc = new RankingCard(pos, esp.user, String.valueOf(esp.tiempo), " seg");
            timeTP.getChildren().add(rc);
            pos++;
        }
        
        //pruebas
        /*for(int i=0; i<8; i++){
            RankingCard rc = new RankingCard(i+1, "User:" + String.valueOf(i), String.valueOf((i+2)*10), " mov");
            timeTP.getChildren().add(rc);
        }*/
    }
    
}
