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
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import rubikcube.logic.Partida;
import rubikcube.logic.Persistencia;
import rubikcube.logic.RankingMovimientos;
import rubikcube.logic.RankingTiempo;
import rubikcube.util.AppContext;
import rubikcube.util.FlowController;
import rubikcube.util.Mensaje;

/**
 * FXML Controller class
 *
 * @author robri
 */
public class ModosJuegoController extends Controller implements Initializable {

    @FXML
    private StackPane root;

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
        if(Persistencia.existePartida()){
            //Cardago de lista de movimientos de la partida guardada
            Partida p;
            p = Persistencia.cargarPartida();
            AppContext.setModoJuego(4);
            AppContext.getInstance().set("cargada", p);
            FlowController.getInstance().goView("Main");
        } else {
            Mensaje msj=new Mensaje();
            msj.show(Alert.AlertType.INFORMATION, "Error cargar partida", "No existe ninguna partida guardada a nombre de: " + AppContext.getInstance().get("user"));
            System.out.println("\nNo existe ninguna partida guardada a nombre de: " + AppContext.getInstance().get("user"));
        }
        //Pruebas de carga
        //cargarDatosPersistidos();
    }
    
    private void cargarDatosPersistidos(){
        //Cargado de Ranking de movimientos
        //System.out.println("\nTop 10 mejores soluciones por movimientos: ");
        RankingMovimientos.getInstance().getRanking().stream()
                .forEachOrdered(esp -> System.out.println("| (" + esp.user + ") -> " + esp.mov));
        //Cargado de Ranking de tiempos
        //System.out.println("\nTop 10 mejores soluciones por tiempo: ");
        RankingTiempo.getInstance().getRanking().stream()
                .forEachOrdered(esp -> System.out.println("| (" + esp.user + ") -> " + esp.tiempo + "s"));
    }
    
}
