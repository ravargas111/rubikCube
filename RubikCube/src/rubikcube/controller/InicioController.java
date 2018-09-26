/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rubikcube.controller;

import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import rubikcube.logic.Jugador;
import rubikcube.util.AppContext;
import rubikcube.util.FlowController;
import rubikcube.util.Mensaje;

/**
 * FXML Controller class
 *
 * @author robri
 */
public class InicioController extends Controller implements Initializable {

    @FXML
    private JFXTextField txtNombre;
    @FXML
    private ImageView imgCubo;
    @FXML
    private StackPane root;
    @FXML
    private FontAwesomeIconView mostrarRanking;
    @FXML
    private StackPane spInfo;
    @FXML
    private StackPane dialogPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @Override
    public void initialize() {
        ArrayList<Jugador> lista= new ArrayList<>();
        AppContext.setJugadores(lista);
    }

    @FXML
    private void selModo(ActionEvent event) {
        if(!this.txtNombre.getText().isEmpty()){
            String nombre = this.txtNombre.getText();
            //No se si utiliza esto para otras cosas pero para guardar/cargar partidas lo voy a hacer diferente
//            ArrayList<Jugador> jugadores= AppContext.getJugadores();
//            Boolean existe=jugadores.stream().anyMatch(x->x.getNombre().equalsIgnoreCase(nombre));
//            if(!existe){
//                Jugador nuevo= new Jugador(nombre);
//                jugadores.add(nuevo);
//                AppContext.setJugadorActual(nuevo);
//            }
//            else{
//                
//            }
//            Voy a manejar un txt para cada jugador, con el mismo nombre del jugador
//            Simplemente en el appContext se guarda el nombre del usuario directamente
//            Cuando el usuario intente cargar una partida, se busca un txt con el nombre del usuario 
//            Si el txt existe es porque el jugador tiene partida guardada, sino no y ya
            AppContext.getInstance().set("user", nombre);
            FlowController.getInstance().goView("ModosJuego");
        }
        else{
            Mensaje msj=new Mensaje();
            msj.show(Alert.AlertType.WARNING, "Campo incompleto", "Necesita ingresar nombre");
        }
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    @FXML
    private void showRanking(MouseEvent event) {
        FlowController.getInstance().goViewOnDialog("Ranking", dialogPane);
    }

    @FXML
    private void irAyuda(MouseEvent event) {
        FlowController.getInstance().goViewOnDialog("Ayuda", this.spInfo);
    }
    
}
