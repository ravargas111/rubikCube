/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rubikcube.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import java.net.URL;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.function.Predicate;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import rubikcube.logic.Algoritmos;
import rubikcube.logic.MovBtn;
import rubikcube.logic.Persistencia;
import rubikcube.logic.RankingMovimientos;
import rubikcube.logic.RankingTiempo;
import rubikcube.logic.RubikL;
import rubikcube.model.RubikG;
import rubikcube.moves.Move;
import rubikcube.moves.Moves;
import rubikcube.util.AppContext;

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
    private Boolean asistido;
    private LocalTime time=LocalTime.now();
    private Timeline timer;
    private final StringProperty clock = new SimpleStringProperty("00:00");
    private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("mm:ss").withZone(ZoneId.systemDefault());
    private ChangeListener<Number> clockLis;
    private final StringProperty contMovs = new SimpleStringProperty();
    private String sigMov;
    private JFXButton btnHover;
    private Boolean empezado;
    private Moves moves=new Moves();
    private ArrayList<String> hist;
    private ArrayList<String> pasosSiguientes;
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
    
    private IntegerProperty movesCount;
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
    private VBox vbHist;
    private VBox vbPasos;
    @FXML
    private JFXButton bStop1;
    @FXML
    private JFXListView<Label> listaMov;
    @FXML
    private JFXListView<Label> listaPasosSig;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //this.infoSP.setVisible(true);
        this.asistido=false;
        this.pasosSiguientes=new ArrayList<String>();
        this.hist=new ArrayList<String>();
        sigMov="";
        empezado=false;
        movesCount=new SimpleIntegerProperty();
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
        if(rubikG.siguientePermitido(btRot)){
        rubikG.rotateFace(btRot);
        }
        //else
            //caso NO PERMITIDO!
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
            //aquí ejecuta movimintos
            accionesMovimiento();
            //lMov.setText(""+(v1.intValue()+1));
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
                    //preview aquí
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
        
        lMov.textProperty().bind(movesCount.asString());
        
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
    
    @FXML
    public void imprimirCubo(){
        this.rubikL.imprimirCubo();
    }
    
    public void seleccionarModo(){
        Integer modo=AppContext.getModoJuego();
        switch(modo){
            case 1: modoOrdenado();break;
            case 2: modoDesordenado();break;
            case 3: modoAsistido();break;
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
        this.rubikG.doScramble();
        this.asistido=true;
    }
    
    public void modoCargado(){
       // root.getC
    } 
    
    public void reiniciarInfo(){
        empezado=false;
        //this.lMov.setText("0");
        //this.lTime.setText("00:00");
        //this.rubikG.getCount().set(-1);
        this.movesCount.set(0);
        this.listaMov.getItems().clear();
    }
    
    public void llenarAutoarmado(){
        autoArmar();
        AppContext.getInstance().getMoveLists().stream().forEach(ms->{
            this.listaPasosSig.getItems().add(new Label(ms.getPaso()));
            //ArrayList<Move> list=new ArrayList<>();
            //list.addAll();
            ms.getMoves().stream().forEach(m->{
                this.listaPasosSig.getItems().add(new Label(m.getFace()));
                //if(!" ".equals(m.getFace()))
                this.pasosSiguientes.add(m.getFace());
            });
        });
        this.rubikG.setSigMov(this.pasosSiguientes.get(0));
        System.out.print("primer movimiento"+sigMov);
    }
    
    public void accionesMovimiento(){
        if(this.empezado){
                String mov=rubikG.getLastRotation().get();
                this.hist.add(mov);
                mov+=" "+this.lTime.getText();
                Label lbl = new Label(mov);
                //lbl.setGraphic(iv);
                this.listaMov.getItems().add(lbl);
                this.movesCount.set(movesCount.get()+1);
                if(this.asistido){
                    this.pasosSiguientes.remove(0);
                    if(this.pasosSiguientes.size()>0){
                    this.rubikG.setSigMov(this.pasosSiguientes.get(0));
                    }
                }
            }
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
        if(AppContext.getModoJuego().equals(3)){
            llenarAutoarmado();
        }
        if(moves.getNumMoves()>0){
            //timer.stop();
            this.movesCount.set(0);
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
        ArrayList<String> lista = new ArrayList<>();
        Collections.addAll(lista, "L", "R", "Li", "Ri", "L", "R", "Li", "Ri", "L", "R", "Li", "Ri", "L", "R", "Li", "Ri");
        Persistencia.guardarPartida(lista);
        
        //Prueba guardado del ranking de movimientos
        RankingMovimientos.getInstance().setEspacio("Cristhian", 140);
        RankingMovimientos.getInstance().setEspacio("Cristhian", 150);
        RankingMovimientos.getInstance().setEspacio("Cristhian", 145);
        RankingMovimientos.getInstance().setEspacio("Cristhian", 138);
        RankingMovimientos.getInstance().setEspacio("Cristhian", 120);
        RankingMovimientos.getInstance().setEspacio("Cristhian", 175);
        RankingMovimientos.getInstance().setEspacio("Cristhian", 170);
        RankingMovimientos.getInstance().setEspacio("Cristhian", 162);
        RankingMovimientos.getInstance().setEspacio("Cristhian", 135);
        RankingMovimientos.getInstance().setEspacio("Cristhian", 157);
        RankingMovimientos.getInstance().setEspacio("Cristhian", 144);
        RankingMovimientos.getInstance().setEspacio("Cristhian", 188);
        RankingMovimientos.getInstance().setEspacio("Cristhian", 200);
        RankingMovimientos.getInstance().setEspacio("Cristhian", 120);
        RankingMovimientos.getInstance().setEspacio("Cristhian", 159);
        RankingMovimientos.getInstance().setEspacio("Cristhian", 165);
        Persistencia.guardarRankingMovimientos(RankingMovimientos.getInstance());
        
        //Prueba guardado del ranking de tiempos
        RankingTiempo.getInstance().setEspacio("Cristhian", 70);
        RankingTiempo.getInstance().setEspacio("Cristhian", 72);
        RankingTiempo.getInstance().setEspacio("Cristhian", 82);
        RankingTiempo.getInstance().setEspacio("Cristhian", 74);
        RankingTiempo.getInstance().setEspacio("Cristhian", 80);
        RankingTiempo.getInstance().setEspacio("Cristhian", 76);
        RankingTiempo.getInstance().setEspacio("Cristhian", 80);
        RankingTiempo.getInstance().setEspacio("Cristhian", 78);
        RankingTiempo.getInstance().setEspacio("Cristhian", 81);
        RankingTiempo.getInstance().setEspacio("Cristhian", 71);
        RankingTiempo.getInstance().setEspacio("Cristhian", 84);
        RankingTiempo.getInstance().setEspacio("Cristhian", 76);
        RankingTiempo.getInstance().setEspacio("Cristhian", 70);
        RankingTiempo.getInstance().setEspacio("Cristhian", 73);
        RankingTiempo.getInstance().setEspacio("Cristhian", 79);
        Persistencia.guardarRankingTiempos(RankingTiempo.getInstance());
    }
    
    public void cargarCubo(){
        
    }

    @FXML
    private void terminarJuego(ActionEvent event) {
        autoArmar();
    }
    
}
