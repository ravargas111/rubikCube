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
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.function.Predicate;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import rubikcube.logic.Algoritmos;
import rubikcube.logic.Partida;
import rubikcube.logic.Partida.ModoJuego;
import rubikcube.logic.Persistencia;
import rubikcube.logic.RankingMovimientos;
import rubikcube.logic.RankingTiempo;
import rubikcube.logic.RubikL;
import rubikcube.model.RubikG;
import rubikcube.moves.Move;
import rubikcube.moves.Moves;
import rubikcube.util.AppContext;
import rubikcube.util.FlowController;
import rubikcube.util.Mensaje;

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
    private String histScramble;
    private Moves historialMovimientos;
    private ArrayList<String> pasosSiguientes;
    private BooleanProperty empezadoP;
    private Partida partidaActual;
    private Integer modoJuego;
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
    @FXML
    private Label lblEtapa;
    @FXML
    private Tab tabPasos;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //this.infoSP.setVisible(true);
        //this.partidaActual=new Partida();
        this.modoJuego=AppContext.getModoJuego();
        this.historialMovimientos=new Moves();
        this.empezadoP=new SimpleBooleanProperty(false);
        this.asistido=false;
        this.pasosSiguientes=new ArrayList<>();
        this.histScramble=new String();
        this.hist=new ArrayList<>();
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
        //this.empezadoP.setValue(true);
        this.partidaActual=(Partida) AppContext.getInstance().get("cargada");
         seleccionarModo();
    }
  
    public void secuenciaCuboBtn(){
        String response;
            if(moves.getNumMoves()>0){
                rubikG.doReset();
            } 
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
    
    private static Predicate<Label> coincideTexto(String face){
        return n -> n.getText().equals(face);
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
        //movimiento permitido
        if(rubikG.siguientePermitido(btRot)&&this.empezado){
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
        Integer modo=AppContext.getModoJuego();
        if(moves.getNumMoves()>0){
            timer.stop();
            moves.getMoves().clear();
            rubikG.doReset();
            rubikG.setEmpezado(false);
            switch(this.modoJuego){
                case 1:
                    rubikG.doReset();
                    break;
                case 2:
                    this.rubikG.doReset();
                    this.rubikG.doSequence((String)AppContext.getInstance().get("scramble"));
                    break;
                case 3:
                    this.rubikG.doReset();
                    this.rubikG.doSequence((String)AppContext.getInstance().get("scramble"));
                    
                    break;
                case 4:
                    this.rubikG.doReset();
                    cargarCubo();
                    break;
            }                
        }
        else{
            Mensaje msj = new Mensaje();
            msj.show(Alert.AlertType.INFORMATION, "No se puede reiniciar", "necesario realizar movimientos");
        }
    }

    public void mezclarCubo(){
        if(moves.getNumMoves()>0){
                rubikG.doReset();
                doScramble();
                this.partidaActual.setListaMovsScramble((String) AppContext.getInstance().get("scramble"));
            } else {
                doScramble();
            }
    }
    
    private void doScramble(){
        root.getChildren().stream().filter(withToolbars()).forEach(setDisable(true));
        rubikG.doScramble();
        accionesScramble();
        rubikG.isOnScrambling().addListener((ov,v,v1)->{
            if(v && !v1){
                root.getChildren().stream().filter(withToolbars()).forEach(setDisable(false));
                moves=new Moves();
                time=LocalTime.now();
                //timer.playFromStart();
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
        });
        
        rubikG.getLastRotation().addListener((ov,v,v1)->{
            if(!rubikG.isOnReplaying().get() && !v1.isEmpty()){
                   moves.addMove(new Move(v1, LocalTime.now().minusNanos(time.toNanoOfDay()).toNanoOfDay()));
                   accionesMovimiento();
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
        
        this.empezadoP.addListener((b0,b1,b2)->{
            //si el estado cambia a empezado
            if(b2){
                //System.out.println("empezado");
                this.bStart.setDisable(true);
                this.bReplay.setDisable(false);
                this.bReset.setDisable(false);
                this.bStart.setDisable(false);
                this.bStop.setDisable(false);
                this.bGuardar.setDisable(false);
                this.bStop1.setDisable(false);
            }
            //si el estado cambia a terminado
            if(b1){
                //System.out.println("terminado");
                desactivaBotones();
            }
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
        //this.bStart.setDisable(false);
        desactivaBotones();
        Integer modo=AppContext.getModoJuego();
        switch(modo){
            case 1: modoOrdenado();break;
            case 2: modoDesordenado();break;
            case 3: modoAsistido();break;
            case 4: modoCargado();break;
            default: break;
        }
    }

    public void modoOrdenado(){
        this.partidaActual=new Partida(ModoJuego.ORDENADO,this.hist);
    }
    
    public void modoDesordenado(){
        this.partidaActual=new Partida(ModoJuego.DESORDENADO,this.hist);
        doScramble();
    }
    
    public void modoAsistido(){
        this.tabPasos.setDisable(false);
        this.partidaActual=new Partida(ModoJuego.ASISTIDO,this.hist);
        this.rubikG.doScramble();
        this.asistido=true;
    }
    
    public void modoCargado(){
       // root.getC
       //this.rubikG.doSequence(this.partidaActual.getListaMovsScramble());
       //this.partidaActual=new Partida(ModoJuego.CARGADO,this.hist);
       cargarCubo();
    } 
    
    public void reiniciarInfo(){
        empezado=false;
        this.movesCount.set(0);
        this.listaMov.getItems().clear();
        this.listaPasosSig.getItems().clear();
        this.pasosSiguientes.clear();
        if(asistido)//todo
            this.rubikG.doScramble();
    }
    
    public void llenarAutoarmado(){
        autoArmar();
        AppContext.getInstance().getMoveLists().stream().forEach(ms->{
            if(!" ".equals(ms.getPaso())){
            this.listaPasosSig.getItems().add(new Label(ms.getPaso()));
            }
            ms.getMoves().stream().forEach(m->{
                this.listaPasosSig.getItems().add(new Label(m.getFace()));
                //if(!" ".equals(m.getFace()))
                this.pasosSiguientes.add(m.getFace());
            });
        });
        this.rubikG.setSigMov(this.pasosSiguientes.get(0));
    }
    
    public void accionesMovimiento(){
        if(this.empezado){
                //maneja historial
                String movStr=rubikG.getLastRotation().get();
                //Move movimiento = new Move(movStr,this.lTime.getText());
                //this.historialMovimientos.addMove(movimiento);
                this.hist.add(movStr);
                //movStr+=" "+movimiento.getTiempoMov();//agrega tiempo
                Label lbl = new Label(movStr);
                this.listaMov.getItems().add(lbl);
                this.movesCount.set(movesCount.get()+1);
                
                //maneja lista de pasos a seguir en caso asistido
                if(this.asistido){
                    this.pasosSiguientes.remove(0);
                    if(this.pasosSiguientes.size()>0){
                    this.rubikG.setSigMov(this.pasosSiguientes.get(0));
                    refrescarListaPasos(1);
                    }
                }
                revisarArmadoL();
            }
        else{
            //String listaScramble = (String)AppContext.getInstance().get("scramble");
            //listaScramble+=" "+this.rubikG.getLastRotation().get();
            //System.out.println("nuevo scramble: "+listaScramble);
        }
    }
    
    public void revisarArmadoL(){
        if(this.rubikL.evaluarCuboArmado()){
           Mensaje msj=new Mensaje();
            msj.show(Alert.AlertType.INFORMATION, "Fin de partida", "El cubo ha sido armado"); 
        }
    }
    
    public void accionesScramble(){
        this.empezadoP.set(false);
        this.partidaActual.setListaMovsScramble((String) AppContext.getInstance().get("scramble")); 
        this.empezadoP.set(true);
        
    }
    
    public void refrescarListaPasos(Integer i){
        Integer tamSig=this.listaPasosSig.getItems().get(i+1).getText().length();
        this.listaPasosSig.getItems().remove(1);
        if(tamSig>2)
           this.listaPasosSig.getItems().remove(0);
        this.lblEtapa.setText(this.listaPasosSig.getItems().get(0).getText());
        //}
        //else 
           //this.listaPasosSig.getItems().remove(i+1); 
    }
    
    public void desactivaBotones(){
        this.bStart.setDisable(false);
        this.bReplay.setDisable(true);
        this.bReset.setDisable(true);
        this.bStop.setDisable(true);
        this.bGuardar.setDisable(true);
        this.bStop1.setDisable(true);
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
        this.empezadoP.setValue(false);
        reiniciarInfo();
    }

    @FXML
    private void iniciarJuego(ActionEvent event) {
        switch(this.modoJuego){
            case 1:
                btnIniciaOrdenado();
                break;
            case 2:
                btnIniciaDesordenado();
                break;
            case 3:
                btnIniciaAsistido();
                break;
            case 4:
                btnIniciaCargado();
                break;
        }

        
        
    }
    
    public void btnIniciaOrdenado(){
        if(moves.getNumMoves()>0)
        btnInicia();
        else{
            Mensaje msj=new Mensaje();
            msj.show(Alert.AlertType.INFORMATION, "Movimientos requeridos", "Desarme el cubo"); 
        }
    }
    
    public void btnIniciaDesordenado(){
        btnInicia();
    }
    
    public void btnIniciaAsistido(){
        llenarAutoarmado();
        btnInicia();
    }
    
    public void btnIniciaCargado(){
        btnInicia();
    }

    public void btnInicia(){
        this.rubikG.setEmpezado(true);
        this.movesCount.set(0);
        moves=new Moves();
        time=LocalTime.now();
        timer.playFromStart();
        this.empezadoP.setValue(true);
        empezado=true;
    }
    
    @FXML
    public void guardarCubo(){
        //Partida p= new Partida(this.hist, Integer.valueOf(this.lMov.getText()));
        this.partidaActual.setTime(time);
        this.partidaActual.guardarPartida();
        //ArrayList<String> lista = new ArrayList<>();
        //lista.addAll(this.hist);
        //Persistencia.guardarPartida(this.hist);
        RankingMovimientos.getInstance().setEspacio((String) AppContext.getInstance().get("user"), Integer.valueOf(this.lMov.getText()));
        Persistencia.guardarRankingMovimientos(RankingMovimientos.getInstance());
        Integer segundos = time.getSecond();
        segundos += time.getMinute()*60;
        RankingTiempo.getInstance().setEspacio((String) AppContext.getInstance().get("user"), segundos);
        Persistencia.guardarRankingTiempos(RankingTiempo.getInstance());
        //ArrayList<Move> listaM = new ArrayList<>();
        //listaM.addAll(this.historialMovimientos.getMoves());
        //Persistencia.guardarPartidaM(listaM);
    }
    
    public void cargarCubo(){
        //Partida p = (Partida) AppContext.getInstance().get("cargada");
        this.rubikG.doSequence(this.partidaActual.getListaMovsScramble());
        this.empezado=true;
        this.rubikG.doSequence(this.partidaActual.getMovimientos());
        this.empezado = false;
        this.time=this.partidaActual.getTime();
        //this.lMov.setText(this.partidaActual.getCantMovs().toString());
        //if(this.empezado)
           // reiniciarCubo();
        //this.empezado=true;
        //StringBuilder sb=(StringBuilder) AppContext.getInstance().get("cargada");
        

    }

    @FXML
    private void terminarJuego(ActionEvent event) {
        //autoArmar();
        FlowController.getInstance().goView("Inicio");
    }

    @FXML
    private void verAyuda(MouseEvent event) {
        FlowController.getInstance().goViewOnDialog("Ayuda", infoSP);
        this.infoSP.setVisible(true);
    }
    
}
