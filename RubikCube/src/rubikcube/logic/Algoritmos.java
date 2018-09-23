/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rubikcube.logic;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import rubikcube.model.RubikG;
import rubikcube.moves.Move;
import rubikcube.moves.Moves;
import rubikcube.util.AppContext;

/**
 *
 * @author Chris
 */
public class Algoritmos {
    //Attributes
    private RubikG rubikG;
    private RubikL rubikLAuxiliar;
    private RubikL rubikLOriginal;
    
    //Pasos completados
    private boolean paso0 = false; //Pone la cara blanca siempre arriba
    private boolean paso1 = false; //primera cruz
    private boolean paso2 = false; //primer nivel
    private boolean paso3 = false; //segundo nivel
    private boolean paso4 = false; //segunda cruz
    private boolean paso5 = false; //orientar cruz tercer nivel
    private boolean paso6 = false; //ubicar esquinas tercer nivel
    private boolean paso7 = false; //orientar esquinas tercer nivel
    
    //Movimientos
    private Integer pasosCompletados=0;
    Moves listaMovEtapa;
    Moves listaMovGeneral;
    ArrayList<Moves> listasMovsEtapas;
    
    private final LocalTime time=LocalTime.now();
    
    //Strings con algoritmos
    private String algoritmoFinal;
    private String algoritmoAuxiliar;

    //Constructors
    public Algoritmos(RubikL rubikL, RubikG rubikG) {
        this.rubikLOriginal = rubikL;
        this.listasMovsEtapas=AppContext.getInstance().getMoveLists();
        this.rubikG = rubikG;
        this.rubikLAuxiliar = new RubikL(rubikL);
        this.listaMovGeneral=AppContext.getMoveListGen(); 
    }
    
    //Methods
    public void autoArmado(){
        algoritmoFinal = "";
        algoritmoAuxiliar = "";
        this.listaMovGeneral.clear();
        this.listasMovsEtapas.stream().forEach(m->m.clear());
        paso0 = checkPaso0();
        if(!paso0){
            algoritmoAuxiliar = "";
            orientarCubo();
            //System.out.println("Pasos para el algoritmo 0\n\t" + algoritmoAuxiliar);
        }
        paso1 = checkPaso1();
        if(!paso1){
            algoritmoAuxiliar = "";
            while(!checkPaso1()){
                primeraCruz();
                movimientoUnico("Yi",1);
            }
            //System.out.println("Pasos para el algoritmo 1\n\t" + algoritmoAuxiliar);
        }
        paso2 = checkPaso2();
        if(!paso2){
            algoritmoAuxiliar = "";
            while(!checkPaso2()){
                esquinasPrimerNivel();
                movimientoUnico("Yi",2);
            }
            //System.out.println("Pasos para el algoritmo 2\n\t" + algoritmoAuxiliar);
        }
        paso3 = checkPaso3();
        if(!paso3){
            algoritmoAuxiliar = "";
            movimientoUnico("Xi",3);
            movimientoUnico("Xi",3);
            boolean ladoDerecho = true;
            while(!checkPaso3()){
                if(ladoDerecho){
                    aristasDerechasSegundoNivel();
                    ladoDerecho = false;
                } else {
                    aristasIzquierdasSegundoNivel();
                    ladoDerecho = true;
                    movimientoUnico("Yi",3);
                }
            }
            //System.out.println("Pasos para el algoritmo 3\n\t" + algoritmoAuxiliar);
        } else {
            movimientoUnico("Xi",3);
            movimientoUnico("Xi",3);
        }
        paso4 = checkPaso4();
        if(!paso4){
            algoritmoAuxiliar = "";
            aristasTercerNivel();
            //System.out.println("Pasos para el algoritmo 4\n\t" + algoritmoAuxiliar);
        }
        paso5 = checkPaso5();
        if(!paso5){
            algoritmoAuxiliar = "";
            orientarCruzTercerNivel();
            System.out.println("Pasos para el algoritmo 5\n\t" + algoritmoAuxiliar);
        }
        paso6 = checkPaso6();
        if(!paso6){
            algoritmoAuxiliar = "";
            ubicarEsquinasTercerNivel();
            //System.out.println("Pasos para el algoritmo 6\n\t" + algoritmoAuxiliar);
        }
        paso7 = checkPaso7();
        if(!paso7){
            algoritmoAuxiliar = "";
            orientarEsquinasTercerNivel();
            //System.out.println("Pasos para el algoritmo 7\n\t" + algoritmoAuxiliar);
        }
        //System.out.println("\n--Algoritmo final acumulado--\n\t" + algoritmoFinal);
        //rubikLAuxiliar.imprimirCubo();
        //rubikLOriginal.imprimirSecuencia3D(this.listaMovGeneral.getMoves());
    }
    
    
    public void autoarmadoG(){
        autoArmado();
        rubikLOriginal.imprimirSecuencia3D(this.listaMovGeneral.getMoves());
    }
    private void orientarCubo(){
        ArrayList<String> list = new ArrayList<>();
        switch(((Centro) rubikLAuxiliar.encontrarPieza(11)).getPos()){
            case INFERIOR:
                Collections.addAll(list, "Xi", "Xi");
                secuencia(list,0);
                break;
            case IZQUIERDA:
                Collections.addAll(list, "Yi", "X");
                secuencia(list,0);
                break;
            case DERECHA:
                Collections.addAll(list, "Y", "X");
                secuencia(list,0);
                break;
            case FRONTAL:
                movimientoUnico("X",0);
                break;
            case TRASERA:
                movimientoUnico("Xi",0);
                break;
        }
    }
    
    private boolean checkPaso0(){
       return ((Centro) rubikLAuxiliar.encontrarPieza(11)).getPos().equals(Centro.Position.SUPERIOR);
    }
    
    public void primeraCruz(){
        ArrayList<String> list = new ArrayList<>();
        if(!(rubikLAuxiliar.getCubo()[0][1][0].getId().equals(rubikLAuxiliar.getCubo()[0][1][0].getPieza().getId()))){
            Arista arista = (Arista) rubikLAuxiliar.encontrarPieza(rubikLAuxiliar.getCubo()[0][1][0].getId());
            switch((arista.getPos2())){
                case SupDer:
                    Collections.addAll(list, "Ri", "Ri", "Di");
                    secuencia(list,1);
                    primeraCruz();
                    break;
                case SupTra:
                    Collections.addAll(list, "B", "B", "Di", "Di");
                    secuencia(list,1);
                    primeraCruz();
                    break;
                case SupIzq:
                    Collections.addAll(list, "L", "L", "D");
                    secuencia(list,1);
                    primeraCruz();
                    break;
                case CenDelIzq:
                    Collections.addAll(list, "Fi");
                    secuencia(list,1);
                    primeraCruz();
                    break;
                case CenDelDer:
                    Collections.addAll(list, "F");
                    secuencia(list,1);
                    primeraCruz();
                    break;
                case CenTraDer:
                    Collections.addAll(list, "Bi", "Di", "Di", "B");
                    secuencia(list,1);
                    primeraCruz();
                    break;
                case CenTraIzq:
                    Collections.addAll(list, "Li", "D", "L");
                    secuencia(list,1);
                    primeraCruz();
                    break;
                case InfDer:
                    Collections.addAll(list, "Di");
                    secuencia(list,1);
                    primeraCruz();
                    break;
                case InfTra:
                    Collections.addAll(list, "Di", "Di");
                    secuencia(list,1);
                    primeraCruz();
                    break;
                case InfIzq:
                    Collections.addAll(list, "D");
                    secuencia(list,1);
                    primeraCruz();
                    break;
                case InfDel:
                    switch(((Arista) rubikLAuxiliar.getCubo()[2][1][0].getPieza()).getOrientacion()){
                        case 1:
                            Collections.addAll(list, "F", "F");
                            break;
                        case 2:
                            Collections.addAll(list, "L", "Di", "Li", "F");
                            break;
                    }
                    secuencia(list,1);
                    break;
            }
        } else if(!rubikLAuxiliar.getCubo()[0][1][0].getPieza().getOrientacion().equals(1)){
             Collections.addAll(list, "F", "F");
             secuencia(list,1);
             primeraCruz();
        }
    }
    
    private boolean checkPaso1(){
        boolean chk = true;
        this.pasosCompletados=0;
        if(!rubikLAuxiliar.getCubo()[0][1][0].getId().equals(rubikLAuxiliar.getCubo()[0][1][0].getPieza().getId()) 
            || !rubikLAuxiliar.getCubo()[0][1][0].getPieza().getOrientacion().equals(1))
            chk = false;
        if(!rubikLAuxiliar.getCubo()[0][1][2].getId().equals(rubikLAuxiliar.getCubo()[0][1][2].getPieza().getId()) 
            || !rubikLAuxiliar.getCubo()[0][1][2].getPieza().getOrientacion().equals(1))
            chk = false;
        if(!rubikLAuxiliar.getCubo()[0][0][1].getId().equals(rubikLAuxiliar.getCubo()[0][0][1].getPieza().getId()) 
            || !rubikLAuxiliar.getCubo()[0][0][1].getPieza().getOrientacion().equals(1))
            chk = false;
        if(!rubikLAuxiliar.getCubo()[0][2][1].getId().equals(rubikLAuxiliar.getCubo()[0][2][1].getPieza().getId()) 
            || !rubikLAuxiliar.getCubo()[0][2][1].getPieza().getOrientacion().equals(1))
            chk = false;
        if(chk)
           this.pasosCompletados=1; 
        return chk;
    }
    
    private void esquinasPrimerNivel(){
        ArrayList<String> list = new ArrayList<>();
        if(!(rubikLAuxiliar.getCubo()[0][2][0].getId().equals(rubikLAuxiliar.getCubo()[0][2][0].getPieza().getId()))){
            Esquina arista = (Esquina) rubikLAuxiliar.encontrarPieza(rubikLAuxiliar.getCubo()[0][2][0].getId());
            switch((arista.getPos())){
                case SupDerTra:
                    Collections.addAll(list, "Bi", "Di", "B");
                    secuencia(list,2);
                    esquinasPrimerNivel();
                    break;
                case SupIzqTra:
                    Collections.addAll(list, "B", "D", "D", "Bi");
                    secuencia(list,2);
                    esquinasPrimerNivel();
                    break;
                case SupIzqDel:
                    Collections.addAll(list, "L", "D", "Li");
                    secuencia(list,2);
                    esquinasPrimerNivel();
                    break;
                case InfDerTra:
                    Collections.addAll(list, "Di");
                    secuencia(list,2);
                    esquinasPrimerNivel();
                    break;
                case InfIzqTra:
                    Collections.addAll(list, "D", "D");
                    secuencia(list,2);
                    esquinasPrimerNivel();
                    break;
                case InfIzqDel:
                    Collections.addAll(list, "D");
                    secuencia(list,2);
                    esquinasPrimerNivel();
                    break;
                case InfDerDel:
                    switch(((Esquina) rubikLAuxiliar.getCubo()[2][2][0].getPieza()).getOrientacion()){
                        case 1:
                            Collections.addAll(list, "Di", "Ri", "D", "R");
                            break;
                        case 2:
                            Collections.addAll(list, "Ri", "Di", "R");
                            break;
                        case 3:
                            Collections.addAll(list, "Di", "Ri", "D", "D", "R", "Di", "Ri", "D", "R");
                            break;
                    }
                    secuencia(list,2);
                    break;
            }
        } else if(!rubikLAuxiliar.getCubo()[0][2][0].getPieza().getOrientacion().equals(1)){
            Collections.addAll(list, "Ri", "Di", "R", "D");
            secuencia(list,2);
            esquinasPrimerNivel();
        }
    }
    
    private boolean checkPaso2(){
        boolean chk = true;
        if(!rubikLAuxiliar.getCubo()[0][0][0].getId().equals(rubikLAuxiliar.getCubo()[0][0][0].getPieza().getId()) 
            || !rubikLAuxiliar.getCubo()[0][0][0].getPieza().getOrientacion().equals(1))
            chk = false;
        if(!rubikLAuxiliar.getCubo()[0][0][2].getId().equals(rubikLAuxiliar.getCubo()[0][0][2].getPieza().getId()) 
            || !rubikLAuxiliar.getCubo()[0][0][2].getPieza().getOrientacion().equals(1))
            chk = false;
        if(!rubikLAuxiliar.getCubo()[0][2][0].getId().equals(rubikLAuxiliar.getCubo()[0][2][0].getPieza().getId()) 
            || !rubikLAuxiliar.getCubo()[0][2][0].getPieza().getOrientacion().equals(1))
            chk = false;
        if(!rubikLAuxiliar.getCubo()[0][2][2].getId().equals(rubikLAuxiliar.getCubo()[0][2][2].getPieza().getId()) 
            || !rubikLAuxiliar.getCubo()[0][2][2].getPieza().getOrientacion().equals(1))
            chk = false;
        if(chk)
           this.pasosCompletados=2;
        return chk;
    }
    
    private void aristasDerechasSegundoNivel(){
        Boolean latEnLat = ((boolean) AppContext.getInstance().get("LatEnLat"));
        Integer orientFinalNecesaria = latEnLat ? 2:1;
        ArrayList<String> list = new ArrayList<>();
        if(!(rubikLAuxiliar.getCubo()[1][2][0].getId().equals(rubikLAuxiliar.getCubo()[1][2][0].getPieza().getId()))){
            Arista arista = (Arista) rubikLAuxiliar.encontrarPieza(rubikLAuxiliar.getCubo()[1][2][0].getId());
            switch((arista.getPos2())){
                case SupDer:
                    if(arista.getOrientacion().equals(orientFinalNecesaria)){
                        movimientoUnico("Ui",3);
                        aristasDerechasSegundoNivel();
                    }
                    break;
                case SupIzq:
                    if(arista.getOrientacion().equals(orientFinalNecesaria)){
                        movimientoUnico("U",3);
                        aristasDerechasSegundoNivel();
                    }
                    break;
                case SupDel:
                    if(arista.getOrientacion().equals(orientFinalNecesaria)){
                        Collections.addAll(list, "U", "U");
                        secuencia(list,3);
                        aristasDerechasSegundoNivel();
                    }
                    break;
                case CenDelIzq:
                    if(arista.getOrientacion().equals(2)){
                        Collections.addAll(list, "F", "U", "Fi", "Ui", "Li", "Ui", "L", "Ui");
                        secuencia(list,3);
                    } else {
                        Collections.addAll(list, "Li", "Ui", "L", "U", "F", "U", "Fi");
                        secuencia(list,3);
                    }
                    aristasDerechasSegundoNivel();
                    break;
                case CenTraIzq:
                    if(arista.getOrientacion().equals(2)){
                        Collections.addAll(list, "Yi", "Li", "Ui", "L", "U", "F", "U", "Fi", "Ui", "Y");
                        secuencia(list,3);
                    } else {
                        Collections.addAll(list, "Yi", "F", "U", "Fi", "Ui", "Li", "Ui", "L", "Ui", "Ui", "Y");
                        secuencia(list,3);
                    }
                    aristasDerechasSegundoNivel();
                    break;
                case CenTraDer:
                    if(arista.getOrientacion().equals(2)){
                        Collections.addAll(list, "Y", "R", "U", "Ri", "Ui", "Fi", "Ui", "F", "U", "Yi");
                        secuencia(list,3);
                    } else {
                        Collections.addAll(list, "Y", "Fi", "Ui", "F", "U", "R", "U", "Ri", "Ui", "Ui", "Yi");
                        secuencia(list,3);
                    }
                    aristasDerechasSegundoNivel();
                    break;
                case SupTra:
                    //Caso final
                    if(arista.getOrientacion().equals(orientFinalNecesaria)){
                        Collections.addAll(list, "Fi", "Ui", "F", "U", "R", "U", "Ri");
                        secuencia(list,3);
                        rubikLAuxiliar.getCubo()[1][2][0].getPieza().setOrientacion(1);
                    }
                    break;
            }
        } else if(!rubikLAuxiliar.getCubo()[1][2][0].getPieza().getOrientacion().equals(1)){
             Collections.addAll(list, "Fi", "Ui", "F", "U", "R", "U", "Ri", "U");
             secuencia(list,3);
             aristasDerechasSegundoNivel();
        }
    }
    
    private void aristasIzquierdasSegundoNivel(){
        Boolean latEnLat = ((boolean) AppContext.getInstance().get("LatEnLat"));
        Integer orientFinalNecesaria = latEnLat ? 2:1;
        ArrayList<String> list = new ArrayList<>();
        if(!(rubikLAuxiliar.getCubo()[1][0][0].getId().equals(rubikLAuxiliar.getCubo()[1][0][0].getPieza().getId()))){
            Arista arista = (Arista) rubikLAuxiliar.encontrarPieza(rubikLAuxiliar.getCubo()[1][0][0].getId());
            switch((arista.getPos2())){
                case SupDer:
                    if(arista.getOrientacion().equals(orientFinalNecesaria)){
                        movimientoUnico("Ui",3);
                        aristasIzquierdasSegundoNivel();
                    }
                    break;
                case SupIzq:
                    if(arista.getOrientacion().equals(orientFinalNecesaria)){
                        movimientoUnico("U",3);
                        aristasIzquierdasSegundoNivel();
                    }
                    break;
                case SupDel:
                    if(arista.getOrientacion().equals(orientFinalNecesaria)){
                        Collections.addAll(list, "U", "U");
                        secuencia(list,3);
                        aristasIzquierdasSegundoNivel();
                    }
                    break;
                case CenDelDer:
                    if(arista.getOrientacion().equals(2)){
                        Collections.addAll(list, "Fi", "Ui", "F", "U", "R", "U", "Ri", "U");
                        secuencia(list,3);
                    } else {
                        Collections.addAll(list, "R", "U", "Ri", "Ui", "Fi", "Ui", "F");
                        secuencia(list,3);
                    }
                    aristasIzquierdasSegundoNivel();
                    break;
                case CenTraIzq:
                    if(arista.getOrientacion().equals(2)){
                        Collections.addAll(list, "Yi", "Li", "Ui", "L", "U", "F", "U", "Fi", "Ui", "Y");
                        secuencia(list,3);
                    } else {
                        Collections.addAll(list, "Yi", "F", "U", "Fi", "Ui", "Li", "Ui", "L", "Ui", "Ui", "Y");
                        secuencia(list,3);
                    }
                    aristasIzquierdasSegundoNivel();
                    break;
                case CenTraDer:
                    if(arista.getOrientacion().equals(2)){
                        Collections.addAll(list, "Y", "R", "U", "Ri", "Ui", "Fi", "Ui", "F", "U", "Yi");
                        secuencia(list,3);
                    } else {
                        Collections.addAll(list, "Y", "Fi", "Ui", "F", "U", "R", "U", "Ri", "Ui", "Ui", "Yi");
                        secuencia(list,3);
                    }
                    aristasIzquierdasSegundoNivel();
                    break;
                case SupTra:
                    //Caso final
                    if(arista.getOrientacion().equals(orientFinalNecesaria)){
                        Collections.addAll(list, "F", "U", "Fi", "Ui", "Li", "Ui", "L");
                        secuencia(list,3);
                        rubikLAuxiliar.getCubo()[1][0][0].getPieza().setOrientacion(1);
                    }
                    break;
            }
        } else if(!rubikLAuxiliar.getCubo()[1][0][0].getPieza().getOrientacion().equals(1)){
             Collections.addAll(list, "F", "U", "Fi", "Ui", "Li", "Ui", "L", "Ui");
             secuencia(list,3);
             aristasIzquierdasSegundoNivel();
        }
    }
    
    private boolean checkPaso3(){
        boolean chk = true;
        if(!rubikLAuxiliar.getCubo()[1][0][0].getId().equals(rubikLAuxiliar.getCubo()[1][0][0].getPieza().getId()) 
            || !rubikLAuxiliar.getCubo()[1][0][0].getPieza().getOrientacion().equals(1))
            chk = false;
        if(!rubikLAuxiliar.getCubo()[1][0][2].getId().equals(rubikLAuxiliar.getCubo()[1][0][2].getPieza().getId()) 
            || !rubikLAuxiliar.getCubo()[1][0][2].getPieza().getOrientacion().equals(1))
            chk = false;
        if(!rubikLAuxiliar.getCubo()[1][2][0].getId().equals(rubikLAuxiliar.getCubo()[1][2][0].getPieza().getId()) 
            || !rubikLAuxiliar.getCubo()[1][2][0].getPieza().getOrientacion().equals(1))
            chk = false;
        if(!rubikLAuxiliar.getCubo()[1][2][2].getId().equals(rubikLAuxiliar.getCubo()[1][2][2].getPieza().getId()) 
            || !rubikLAuxiliar.getCubo()[1][2][2].getPieza().getOrientacion().equals(1))
            chk = false;
        if(chk)
            this.pasosCompletados=3;
        return chk;
    }
    
    public void aristasTercerNivel(){
        ArrayList<String> list = new ArrayList<>();
        boolean ariF = rubikLAuxiliar.getCubo()[0][1][0].getPieza().getOrientacion().equals(1);
        boolean ariI = rubikLAuxiliar.getCubo()[0][0][1].getPieza().getOrientacion().equals(1);
        boolean ariD = rubikLAuxiliar.getCubo()[0][2][1].getPieza().getOrientacion().equals(1);
        boolean ariT = rubikLAuxiliar.getCubo()[0][1][2].getPieza().getOrientacion().equals(1);
        Integer n = 0;
        if(ariF) n++;
        if(ariI) n++;
        if(ariD) n++;
        if(ariT) n++;
        switch(n){
            case 1:
                if(ariF) {
                    movimientoUnico("U",4);
                    aristasTercerNivel();
                }
                else if(ariD){
                    Collections.addAll(list, "U", "U");
                    secuencia(list,4);
                    aristasTercerNivel();
                }
                else if(ariT){
                    movimientoUnico("Ui",4);
                    aristasTercerNivel();
                }
                else if(ariI){
                    Collections.addAll(list, "F", "R", "U", "Ri", "Ui");
                    secuencia(list,4);
                    list.clear();
                    if(!rubikLAuxiliar.getCubo()[0][1][2].getPieza().getOrientacion().equals(1))
                        Collections.addAll(list, "R", "U", "Ri", "Ui", "Fi");
                    else 
                        Collections.addAll(list, "Fi");
                    secuencia(list,4);
                }
                break;
            case 2:
                if(ariI && !ariF){
                    Collections.addAll(list, "F", "R", "U", "Ri", "Ui");
                    secuencia(list,4);
                    list.clear();
                    if(!rubikLAuxiliar.getCubo()[0][1][2].getPieza().getOrientacion().equals(1))
                        Collections.addAll(list, "R", "U", "Ri", "Ui", "Fi");
                    else 
                        Collections.addAll(list, "Fi");
                    secuencia(list,4);
                } else {
                    movimientoUnico("U",4);
                    aristasTercerNivel();
                }
                break;
            case 4:
                this.paso4 = true;
                break;
            case 0:
                Collections.addAll(list, "F", "R", "U", "Ri", "Ui");
                secuencia(list,4);
                list.clear();
                if(!rubikLAuxiliar.getCubo()[0][1][2].getPieza().getOrientacion().equals(1))
                    Collections.addAll(list, "R", "U", "Ri", "Ui", "Fi");
                else 
                    Collections.addAll(list, "Fi");
                secuencia(list,4);
                aristasTercerNivel();
                break;
        }
    }
    
    private boolean checkPaso4(){
        boolean chk = true;
        if(!rubikLAuxiliar.getCubo()[0][1][0].getPieza().getOrientacion().equals(1))
            chk = false;
        if(!rubikLAuxiliar.getCubo()[0][0][1].getPieza().getOrientacion().equals(1))
            chk = false;
        if(!rubikLAuxiliar.getCubo()[0][2][1].getPieza().getOrientacion().equals(1))
            chk = false;
        if(!rubikLAuxiliar.getCubo()[0][1][2].getPieza().getOrientacion().equals(1))
            chk = false;
        if(chk)
            this.pasosCompletados=4;
        return chk;
    }
    
    private void orientarCruzTercerNivel(){
        ArrayList<String> list = new ArrayList<>();
        boolean ariF = rubikLAuxiliar.getCubo()[0][1][0].getId().equals(rubikLAuxiliar.getCubo()[0][1][0].getPieza().getId());
        boolean ariI = rubikLAuxiliar.getCubo()[0][0][1].getId().equals(rubikLAuxiliar.getCubo()[0][0][1].getPieza().getId());
        boolean ariD = rubikLAuxiliar.getCubo()[0][2][1].getId().equals(rubikLAuxiliar.getCubo()[0][2][1].getPieza().getId());
        boolean ariT = rubikLAuxiliar.getCubo()[0][1][2].getId().equals(rubikLAuxiliar.getCubo()[0][1][2].getPieza().getId());
        Integer n = 0;
        if(ariF) n++;
        if(ariI) n++;
        if(ariD) n++;
        if(ariT) n++;
        switch(n){
            case 1:
                if(ariF) {
                    Collections.addAll(list, "R", "U", "Ri", "U", "R", "U", "U", "Ri");
                    secuencia(list,5);
                    orientarCruzTercerNivel();
                }
                else if(ariD){
                    movimientoUnico("Y",5);
                    orientarCruzTercerNivel();
                }
                else if(ariT){
                    Collections.addAll(list, "Y", "Y");
                    secuencia(list,5);
                    orientarCruzTercerNivel();
                }
                else if(ariI){
                    movimientoUnico("Yi",5);
                    orientarCruzTercerNivel();
                }
                break;
            case 2:
                if((ariI && ariD) || (ariT && ariF)){
                    Collections.addAll(list, "U", "R", "U", "Ri", "U", "R", "U", "U", "Ri");
                    secuencia(list,5);
                    orientarCruzTercerNivel();
                } else {
                    movimientoUnico("U",5);
                    orientarCruzTercerNivel();
                }
                break;
            case 4:
                this.paso5 = true;
                break;
            case 0:
                movimientoUnico("U",5);
                orientarCruzTercerNivel();
                break;
        }
    }
    
    private boolean checkPaso5(){
        boolean chk = true;
        if(!rubikLAuxiliar.getCubo()[0][1][0].getId().equals(rubikLAuxiliar.getCubo()[0][1][0].getPieza().getId()))
            chk = false;
        if(!rubikLAuxiliar.getCubo()[0][0][1].getId().equals(rubikLAuxiliar.getCubo()[0][0][1].getPieza().getId()))
            chk = false;
        if(!rubikLAuxiliar.getCubo()[0][2][1].getId().equals(rubikLAuxiliar.getCubo()[0][2][1].getPieza().getId()))
            chk = false;
        if(!rubikLAuxiliar.getCubo()[0][1][2].getId().equals(rubikLAuxiliar.getCubo()[0][1][2].getPieza().getId()))
            chk = false;
        if(chk)
           this.pasosCompletados=5;
        return chk;
    }
    
    private void ubicarEsquinasTercerNivel(){
        ArrayList<String> list = new ArrayList<>();
        boolean delIzq = rubikLAuxiliar.getCubo()[0][0][0].getId().equals(rubikLAuxiliar.getCubo()[0][0][0].getPieza().getId());
        boolean delDer = rubikLAuxiliar.getCubo()[0][2][0].getId().equals(rubikLAuxiliar.getCubo()[0][2][0].getPieza().getId());
        boolean traIzq = rubikLAuxiliar.getCubo()[0][0][2].getId().equals(rubikLAuxiliar.getCubo()[0][0][2].getPieza().getId());
        boolean traDer = rubikLAuxiliar.getCubo()[0][2][2].getId().equals(rubikLAuxiliar.getCubo()[0][2][2].getPieza().getId());
        Integer n = 0;
        if(delIzq) n++;
        if(delDer) n++;
        if(traIzq) n++;
        if(traDer) n++;
        if(n.equals(0)){
            Collections.addAll(list, "U", "R", "Ui", "Li", "U", "Ri", "Ui", "L");
            secuencia(list,6);
            ubicarEsquinasTercerNivel();
        } else if(n<4){
            if(delDer){
                Collections.addAll(list, "U", "R", "Ui", "Li", "U", "Ri", "Ui", "L");
                secuencia(list,6);
                ubicarEsquinasTercerNivel();
            } else if(delIzq){
                movimientoUnico("Yi",6);
                ubicarEsquinasTercerNivel();
            } else if(traDer){
                movimientoUnico("Y",6);
                ubicarEsquinasTercerNivel();
            } else {
                Collections.addAll(list, "Y", "Y");
                secuencia(list,6);
                ubicarEsquinasTercerNivel();
            }
        } else {
           paso6 = true; 
        }
    }
    
    private boolean checkPaso6(){
        boolean chk = true;
        if(!rubikLAuxiliar.getCubo()[0][0][0].getId().equals(rubikLAuxiliar.getCubo()[0][0][0].getPieza().getId()))
            chk = false;
        if(!rubikLAuxiliar.getCubo()[0][0][2].getId().equals(rubikLAuxiliar.getCubo()[0][0][2].getPieza().getId()))
            chk = false;
        if(!rubikLAuxiliar.getCubo()[0][2][0].getId().equals(rubikLAuxiliar.getCubo()[0][2][0].getPieza().getId()))
            chk = false;
        if(!rubikLAuxiliar.getCubo()[0][2][2].getId().equals(rubikLAuxiliar.getCubo()[0][2][2].getPieza().getId()))
            chk = false;
        if(chk)
           this.pasosCompletados=6;
        return chk;
    }
    
    private void orientarEsquinasTercerNivel(){
        ArrayList<String> list = new ArrayList<>();
        Integer n = 0;
        if(rubikLAuxiliar.getCubo()[0][0][0].getPieza().getOrientacion().equals(2)) n++;
        if(rubikLAuxiliar.getCubo()[0][0][2].getPieza().getOrientacion().equals(2)) n++;
        if(rubikLAuxiliar.getCubo()[0][2][0].getPieza().getOrientacion().equals(2)) n++;
        if(rubikLAuxiliar.getCubo()[0][2][2].getPieza().getOrientacion().equals(2)) n++;
        if(n<4){
            n = 0;
            Esquina auxEsq = (Esquina) rubikLAuxiliar.getCubo()[0][2][0].getPieza();
            while(!checkPaso7() && n<16){
                list.clear();
                if(rubikLAuxiliar.getCubo()[0][2][0].getPieza().getId().equals(auxEsq.getId())
                && rubikLAuxiliar.getCubo()[0][2][0].getPieza().getOrientacion().equals(2)){
                    Collections.addAll(list, "Ui");
                    secuencia(list,7);
                    auxEsq = (Esquina) rubikLAuxiliar.getCubo()[0][2][0].getPieza();
                } else {
                    Collections.addAll(list, "Ri", "Di", "R", "D");
                    secuencia(list,7);
                }
                n++;
            }
        } else {
            paso7 = true;
        }
    }
    
    private boolean checkPaso7(){
        boolean chk = true;
        if(!rubikLAuxiliar.getCubo()[0][0][0].getPieza().getOrientacion().equals(2)
            || !rubikLAuxiliar.getCubo()[0][0][0].getId().equals(rubikLAuxiliar.getCubo()[0][0][0].getPieza().getId()))
            chk = false;
        if(!rubikLAuxiliar.getCubo()[0][0][2].getPieza().getOrientacion().equals(2)
            || !rubikLAuxiliar.getCubo()[0][0][2].getId().equals(rubikLAuxiliar.getCubo()[0][0][2].getPieza().getId()))
            chk = false;
        if(!rubikLAuxiliar.getCubo()[0][2][0].getPieza().getOrientacion().equals(2)
            || !rubikLAuxiliar.getCubo()[0][2][0].getId().equals(rubikLAuxiliar.getCubo()[0][2][0].getPieza().getId()))
            chk = false;
        if(!rubikLAuxiliar.getCubo()[0][2][2].getPieza().getOrientacion().equals(2)
            || !rubikLAuxiliar.getCubo()[0][2][2].getId().equals(rubikLAuxiliar.getCubo()[0][2][2].getPieza().getId()))
            chk = false;
        if(chk)
           this.pasosCompletados=7;
        return chk;
    }
    
    public void secuencia(ArrayList<String> list,Integer etapa){
        
        list.stream().forEach(str -> {
            //moves.addMove(new Move(str, LocalTime.now().minusNanos(time.toNanoOfDay()).toNanoOfDay()));
            this.asignarMovimientos(str,etapa);
            rubikLAuxiliar.movimientoBasico(str);
            if(algoritmoAuxiliar.isEmpty()){
                algoritmoAuxiliar = str;
            } else {
                algoritmoAuxiliar += ", " + str;
            }
            if(algoritmoFinal.isEmpty())
                algoritmoFinal = str;
            else
                algoritmoFinal += ", " + str;
        });
    }
    
    public void movimientoUnico(String mov,Integer etapa){
        rubikLAuxiliar.movimientoBasico(mov);
        this.asignarMovimientos(mov,etapa);
        if(algoritmoAuxiliar.isEmpty())
            algoritmoAuxiliar = mov;
        else
            algoritmoAuxiliar += ", " + mov;
        if(algoritmoFinal.isEmpty())
            algoritmoFinal = mov;
        else
            algoritmoFinal += ", " + mov;
    }

    //Setters and Getters
    public RubikL getRubikL() {
        return rubikLAuxiliar;
    }

    public void setRubikL(RubikL rubikL) {
        this.rubikLAuxiliar = rubikL;
    }

    public RubikG getRubikG() {
        return rubikG;
    }

    public void setRubikG(RubikG rubikG) {
        this.rubikG = rubikG;
    }  

    public RubikL getRubikLAuxiliar() {
        return rubikLAuxiliar;
    }

    public void setRubikLAuxiliar(RubikL rubikLAuxiliar) {
        this.rubikLAuxiliar = rubikLAuxiliar;
    }

    public RubikL getRubikLOriginal() {
        return rubikLOriginal;
    }

    public void setRubikLOriginal(RubikL rubikLOriginal) {
        this.rubikLOriginal = rubikLOriginal;
    }

    public String getAlgoritmoFinal() {
        return algoritmoFinal;
    }

    public void setAlgoritmoFinal(String algoritmoFinal) {
        this.algoritmoFinal = algoritmoFinal;
    }

    public String getAlgoritmoAuxiliar() {
        return algoritmoAuxiliar;
    }

    public void setAlgoritmoAuxiliar(String algoritmoAuxiliar) {
        this.algoritmoAuxiliar = algoritmoAuxiliar;
    }
    
    public void asignarMovimientos(String mov,Integer etapa){
        //selecciona la etapa para separar por listas (pasos completados se asigna al checkear etapas)
        seleccionaEtapa(etapa);
        //asigna a la lista por etapas y a la general
        this.listaMovEtapa.addMove(new Move(mov, LocalTime.now().minusNanos(time.toNanoOfDay()).toNanoOfDay()));
        this.listaMovGeneral.addMove(new Move(mov, LocalTime.now().minusNanos(time.toNanoOfDay()).toNanoOfDay()));
    }
    
    public void seleccionaEtapa(Integer etapa){
        this.listaMovEtapa=this.listasMovsEtapas.get(etapa);
        switch(etapa){
        case 0: this.listaMovEtapa.setPaso("Reacomodo");break;
        case 1: this.listaMovEtapa.setPaso("Cruz");break;
        case 2: this.listaMovEtapa.setPaso("Primer Nivel");break;
        case 3: this.listaMovEtapa.setPaso("Segundo Nivel");break;
        case 4: this.listaMovEtapa.setPaso("Tercer Nivel");break;
        case 5: this.listaMovEtapa.setPaso("Tercer Nivel");break;
        case 6: this.listaMovEtapa.setPaso("Tercer Nivel");break;
        case 7: this.listaMovEtapa.setPaso("Tercer Nivel");break;
        }
    }
    
}
