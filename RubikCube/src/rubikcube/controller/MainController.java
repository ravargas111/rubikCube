/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rubikcube.controller;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.function.Predicate;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import rubikcube.logic.Algoritmos;
import rubikcube.logic.MovBtn;
import rubikcube.logic.RubikL;
import rubikcube.model.RubikG;
import rubikcube.moves.Move;
import rubikcube.moves.Moves;
import rubikcube.util.AppContext;
import rubikcube.util.FlowController;

/**
 * FXML Controller class
 *
 * @author robri
 */
public class MainController extends Controller implements Initializable {

    @FXML
    private BorderPane root;
    private RubikG rubikG;
    private RubikL rubikL;
    private Algoritmos algoritmos;
    
    private LocalTime time=LocalTime.now();
    private Timeline timer;
    private final StringProperty clock = new SimpleStringProperty("00:00:00");
    private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm:ss").withZone(ZoneId.systemDefault());
    private ChangeListener<Number> clockLis;
    private final StringProperty contMovs = new SimpleStringProperty();
    
    private JFXButton btnHover;
    private Boolean empezado;
    private Moves moves=new Moves();
    @FXML
    private Label lSolved;
    @FXML
    private Label lSimulated;
    @FXML
    private JFXButton bReset;
    @FXML
    private JFXButton bReplay;
    @FXML
    private Label lMov;
    @FXML
    private Label lTime;

    private Integer movesCount;
    @FXML
    private ToolBar tbMov;
    @FXML
    private JFXButton bStart;
    @FXML
    private HBox hbInfo;
    @FXML
    private JFXButton bGuardar;
    @FXML
    private JFXButton bStop;
    @FXML
    private StackPane infoSP;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //this.infoSP.setVisible(true);
        empezado=false;
        movesCount=0;
        rubikG=new RubikG();
        rubikL=new RubikL(rubikG);
        rubikG.setRubikL(rubikL);//ambos quedan relacionados (rotación del gráfico llama rotación del lógico)
        root.setCenter(rubikG.getSubScene());
        binds();
        initToolbarEvents();
        initListeners();
    }    

    @Override
    public void initialize() {
         seleccionarModo();
    }
  
    public void secuenciaCuboBtn(){
        String response;
            if(moves.getNumMoves()>0){
                /*response = Dialogs.create()
                .owner(getStage())
                .title("Warning Dialog")
                .masthead("Loading a Sequence").lightweight()
                .message("Add a valid sequence of movements:\n(previous movements will be discarded)")
                .showTextInput(moves.getSequence());*/
                
                rubikG.doReset();
                //rubik.doSequence(response.trim());
            } /*else {
                response = Dialogs.create()
                .owner(getStage())
                .title("Information Dialog")
                .masthead("Loading a Sequence").lightweight()
                .message( "Add a valid sequence of movements")
                .showTextInput();
            }
            //System.out.println("r: "+response);
            if(response!=null && !response.isEmpty()){
                rubik.doReset();
                rubik.doSequence(response.trim());
            }*/
    }
    
    // some predicates for readability
    private static Predicate<Node> withToolbars(){
        return n -> (n instanceof ToolBar);
    }
    private static Predicate<Node> withMoveButtons(){
        return n -> (n instanceof JFXButton) && ((JFXButton)n).getText().length()<=2;
    }
    private static Predicate<Node> withButtonTextName(String text){
        return n -> ((JFXButton)n).getText().equals(text);
    }
    private static Predicate<Node> isButtonHovered(){
        return n -> ((JFXButton)n).isHover();
    }
    private static Consumer<Node> setDisable(boolean disable){
        return n -> n.setDisable(disable);
    }
    
    // called on button click
    private void rotateFace(final String btRot){
        root.getChildren().stream()
            .filter(withToolbars())
            .forEach(tb->{
                ((ToolBar)tb).getItems().stream()
                    .filter(withMoveButtons().and(withButtonTextName(btRot)))
                    .findFirst().ifPresent(n->rubikG.isHoveredOnClick().set(((JFXButton)n).isHover()));
            });
        rubikG.rotateFace(btRot);
    }
    
    // called on button hover
    private void updateArrow(String face, boolean hover){
        rubikG.updateArrow(face,hover);
    }
    
    public void reiniciarCubo(){
        if(moves.getNumMoves()>0){
            timer.stop();
            moves.getMoves().clear();
            rubikG.doReset();
        }
    }

    public void mezclarCubo(){
        if(moves.getNumMoves()>0){
               /* Action response = Dialogs.create()
                .owner(getStage())
                .title("Warning Dialog")
                .masthead("Scramble Cube")
                .message( "You will lose all your previous movements. Do you want to continue?")
                .showConfirm();
                if(response==Dialog.Actions.YES){*/
                    rubikG.doReset();
                    doScramble();
                //}
            } else {
                doScramble();
            }
    }
    
    private void doScramble(){
        root.getChildren().stream().filter(withToolbars()).forEach(setDisable(true));
        rubikG.doScramble();
        rubikG.isOnScrambling().addListener((ov,v,v1)->{
            if(v && !v1){
                root.getChildren().stream().filter(withToolbars()).forEach(setDisable(false));
                moves=new Moves();
                time=LocalTime.now();
                timer.playFromStart();
            }
        });
    }
    
    public void repetirSecuencia(){
        timer.stop();
        rubikG.getTimestamp().addListener(clockLis);
        doReplay();
    }
    
    private void doReplay(){
        root.getChildren().stream().filter(withToolbars()).forEach(setDisable(true));
        rubikG.doReplay(moves.getMoves());
        rubikG.isOnReplaying().addListener((ov,v,v1)->{
            if(v && !v1){
                root.getChildren().stream().filter(withToolbars()).forEach(setDisable(false));
            }
        });
    }
    
    public void cargarCubo(){
        
    }
    
    private void initTimer(){
    timer=new Timeline(new KeyFrame(Duration.ZERO, e->{
            clock.set(LocalTime.now().minusNanos(time.toNanoOfDay()).format(fmt));
        }),new KeyFrame(Duration.seconds(1)));
        timer.setCycleCount(Animation.INDEFINITE);
        rubikG.isSolved().addListener((ov,b,b1)->{
            if(b1){
                this.lSolved.setVisible(true);
                timer.stop();
                moves.setTimePlay(LocalTime.now().minusNanos(time.toNanoOfDay()).toNanoOfDay());
                //System.out.println(moves);
            } else {
                this.lSolved.setVisible(false);
            }
        });
    }

    private void initListeners(){
        rubikG.isOnReplaying().addListener((ov,b,b1)->{
            if(b&&!b1){
                rubikG.getTimestamp().removeListener(clockLis);
                if(!rubikG.isSolved().get()){
                    timer.play();
                }
            }
        });
        
        rubikG.getCount().addListener((ov,v,v1)->{
            bReset.setDisable(moves.getNumMoves()==0);
            bReplay.setDisable(moves.getNumMoves()==0);
            if(this.empezado)
            lMov.setText(""+(v1.intValue()+1));
        });
        
        rubikG.getLastRotation().addListener((ov,v,v1)->{
            if(!rubikG.isOnReplaying().get() && !v1.isEmpty()){
                moves.addMove(new Move(v1, LocalTime.now().minusNanos(time.toNanoOfDay()).toNanoOfDay()));
            }
        });
        
        rubikG.isOnRotation().addListener((b0,b1,b2)->{
            if(b2){
                // store the button hovered 
                root.getChildren().stream().filter(withToolbars())
                    .forEach(tb->{
                        ((ToolBar)tb).getItems().stream().filter(withMoveButtons().and(isButtonHovered()))
                            .findFirst().ifPresent(n->btnHover=(JFXButton)n);
                    });
            } else {
                if(rubikG.getPreviewFace().get().isEmpty()){
                    btnHover=null;
                    if(AppContext.getInstance().getModoJuego().equals(3)){
                      System.out.println("aquí hay que comparar");
                      //set mov permitido (hacer en cubo gráfio)
                    }
                } else {
                    // after rotation
                    if(btnHover!=null && !btnHover.isHover()){
                        updateArrow(btnHover.getText(), false);
                    }
                }
            }
        });
        
        // disable rest of buttons to avoid new hover events
        rubikG.isOnPreview().addListener((b0, b1, b2) -> {
            final String face=rubikG.getPreviewFace().get();
            root.getChildren().stream().filter(withToolbars())
                .forEach(tb->{
                    ((ToolBar)tb).getItems().stream().filter(withMoveButtons())
                        .forEach((b)->{
                            b.setDisable(!b2 || face.isEmpty() || face.equals("V")?false:
                                    !face.equals(((JFXButton)b).getText()));
                        });
                });
        });
    }

    private void binds(){
        clockLis=(ov,l,l1)->clock.set(LocalTime.ofNanoOfDay(l1.longValue()).format(fmt));
        
        lSimulated.textProperty().bind(rubikG.getPreviewFace());
        
        lTime.textProperty().bind(clock);
        
        initTimer();
        
        Scene scene=AppContext.getMainScene();
        scene.addEventHandler(MouseEvent.ANY, rubikG.eventHandler);
        scene.cursorProperty().bind(rubikG.getCursor());
    }
    
    private void initToolbarEvents(){
        this.root.getChildren().stream()
            .filter(withToolbars())
            .forEach(tb->{
                ((ToolBar)tb).getItems().stream()
                    .filter(withMoveButtons())
                    .forEach(n->{
                        JFXButton b=(JFXButton)n;
                        b.setOnAction(e->rotateFace(b.getText()));
                        b.hoverProperty().addListener((ov,b0,b1)->updateArrow(b.getText(),b1));
                    });
            });
    }
    
    public void autoArmado(){
        
    }
    
    public void autoArmar(){
        this.algoritmos = new Algoritmos(rubikL, rubikG);
        this.algoritmos.autoArmado();
    }
    
    //Metodos para pruebas logicas
    public void checkSolved(){
        
    }
    
    public void evaluarCubo(){
        this.rubikL.evaluarCuboArmado();
    }
    
    public void printLogicalCube(){
        
    }
    
    public void imprimirCubo(){
        this.rubikL.imprimirCubo();
    }
    
    public void seleccionarModo(){
        Integer modo=AppContext.getModoJuego();
        switch(modo){
            case 1: modoOrdenado();break;
            case 2: modoDesordenado();break;
            case 3: break;
            case 4: break;
            default: break;
        }
    }
    
    public void llenarBotones(){
        
        //String nomBotones[]={"L","Li","R","Ri","U","Ui","D","Di"};
        ArrayList<String> nombres=new ArrayList<>();
        nombres.addAll(Arrays.asList("L","Li","R","Ri","U","Ui","D","Di","Y","Yi","X","Xi"));
        nombres.stream().forEach(e->{
            //JFXButton btn=new JFXButton();
            MovBtn btn=new MovBtn(e);
            //btn.setText(e);
            this.tbMov.getItems().add(btn);
        });
    }

    public void modoOrdenado(){
        //FlowController.getInstance().goViewOnDialog("ListaMovimientos", (StackPane)root.getLeft());
        //this.initTimer();
    }
    
    public void modoDesordenado(){
        this.rubikG.doScramble();
    }
    
    public void modoAsistido(){
        
    }
    
    public void modoCargado(){
       // root.getC
    } 
    
    public void reiniciarInfo(){
        empezado=false;
        //this.lMov.setText("0");
        //this.lTime.setText("00:00");
        this.rubikG.getCount().set(-1);
    }
    
    @FXML
    private void replayCube(ActionEvent event) {
        doReplay();
    }
    
    @FXML
    private void resetCube(ActionEvent event) {
        reiniciarCubo();
        this.bStart.setDisable(false);
        this.bGuardar.setDisable(true);
        reiniciarInfo();
    }

    @FXML
    private void iniciarJuego(ActionEvent event) {
        if(moves.getNumMoves()>0){
            //timer.stop();
            moves=new Moves();
            time=LocalTime.now();
            timer.playFromStart();
            this.bStart.setDisable(true);
            this.bReset.setDisable(false);
            this.bReplay.setDisable(false);
            this.bGuardar.setDisable(false);
            empezado=true;
            //reiniciarInfo();
        }
            
        
    }

    @FXML
    public void guardarCubo(){
        autoArmar();
    }

    @FXML
    private void terminarJuego(ActionEvent event) {
        autoArmar();
    }
    
}
