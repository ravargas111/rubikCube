/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rubikcube.logic;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JOptionPane;
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
    private RubikL rubikL;
    //Pasos completados
    private boolean paso1 = false; //primera cruz
    private boolean paso2 = false; //primer nivel
    private boolean paso3 = false; //segundo nivel
    private boolean paso4 = false; //segunda cruz
    private boolean paso5 = false; //orientar cruz tercer nivel
    private boolean paso6 = false; //ubicar esquinas tercer nivel
    private boolean paso7 = false; //orientar esquinas tercer nivel
    private boolean paso8 = false; //orientar tercer nivel
    private Integer pasosCompletados=0;
    Moves listaMovEtapa;
    Moves listaMovGeneral;
    
    private LocalTime time=LocalTime.now();
    
    //Strings con algoritmos
    private String algoritmoFinal;
    private String algoritmoAuxiliar;

    //Constructors
    public Algoritmos(RubikL rubikL, RubikG rubikG) {
        this.rubikL = rubikL;
        this.rubikG = rubikG;
        this.listaMovGeneral=AppContext.getInstance().getMoveLists().get(0); 
    }
    
    //Methods
    public void verificarNiveles(){
        //Se verifica cuales niveles ya estan armados
        paso1 = checkPaso1();
        paso2 = checkPaso2();
        paso3 = checkPaso3();
        paso4 = checkPaso4();
        paso5 = checkPaso5();
        paso6 = checkPaso6();
        paso7 = checkPaso7();
    }
    
    public void autoArmado(){
        algoritmoFinal = "";
        algoritmoAuxiliar = "";
        paso1 = checkPaso1();
        if(!paso1){
            algoritmoAuxiliar = "";
            while(!checkPaso1()){
                primeraCruz();
                movimientoUnico("Yi");
            }
            System.out.println("Pasos para el algoritmo 1\n\t" + algoritmoAuxiliar);
            
        }
        paso2 = checkPaso2();
        if(!paso2){
            algoritmoAuxiliar = "";
            while(!checkPaso2()){
                esquinasPrimerNivel();
                movimientoUnico("Yi");
            }
            System.out.println("Pasos para el algoritmo 2\n\t" + algoritmoAuxiliar);
        }
        paso3 = checkPaso3();
        if(!paso3){
            algoritmoAuxiliar = "";
            movimientoUnico("Xi");
            movimientoUnico("Xi");
            boolean ladoDerecho = true;
            while(!checkPaso3()){
                if(ladoDerecho){
                    aristasDerechasSegundoNivel();
                    ladoDerecho = false;
                } else {
                    aristasIzquierdasSegundoNivel();
                    ladoDerecho = true;
                    movimientoUnico("Yi");
                }
            }
            System.out.println("Pasos para el algoritmo 3\n\t" + algoritmoAuxiliar);
        } else {
            movimientoUnico("Xi");
            movimientoUnico("Xi");
        }
        paso4 = checkPaso4();
        if(!paso4){
            algoritmoAuxiliar = "";
            aristasTercerNivel();
            System.out.println("Pasos para el algoritmo 4\n\t" + algoritmoAuxiliar);
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
            System.out.println("Pasos para el algoritmo 6\n\t" + algoritmoAuxiliar);
        }
        paso7 = checkPaso7();
        if(!paso7){
            algoritmoAuxiliar = "";
            orientarEsquinasTercerNivel();
            System.out.println("Pasos para el algoritmo 7\n\t" + algoritmoAuxiliar);
        }
        System.out.println("\n--Algoritmo final acumulado--\n\t" + algoritmoFinal);
        rubikL.imprimirCubo();
        this.rubikL.imprimirSecuencia3D(this.listaMovGeneral.getMoves());
    }
    
    public void primeraCruz(){
        ArrayList<String> list = new ArrayList<>();
        if(!(rubikL.getCubo()[0][1][0].getId().equals(rubikL.getCubo()[0][1][0].getPieza().getId()))){
            Arista arista = (Arista) rubikL.encontrarPieza(rubikL.getCubo()[0][1][0].getId());
            switch((arista.getPos2())){
                case SupDer:
                    Collections.addAll(list, "Ri", "Ri", "Di");
                    secuencia(list);
                    primeraCruz();
                    break;
                case SupTra:
                    Collections.addAll(list, "B", "B", "Di", "Di");
                    secuencia(list);
                    primeraCruz();
                    break;
                case SupIzq:
                    Collections.addAll(list, "L", "L", "D");
                    secuencia(list);
                    primeraCruz();
                    break;
                case CenDelIzq:
                    Collections.addAll(list, "Fi");
                    secuencia(list);
                    primeraCruz();
                    break;
                case CenDelDer:
                    Collections.addAll(list, "F");
                    secuencia(list);
                    primeraCruz();
                    break;
                case CenTraDer:
                    Collections.addAll(list, "Bi", "Di", "Di", "B");
                    secuencia(list);
                    primeraCruz();
                    break;
                case CenTraIzq:
                    Collections.addAll(list, "Li", "D", "L");
                    secuencia(list);
                    primeraCruz();
                    break;
                case InfDer:
                    Collections.addAll(list, "Di");
                    secuencia(list);
                    primeraCruz();
                    break;
                case InfTra:
                    Collections.addAll(list, "Di", "Di");
                    secuencia(list);
                    primeraCruz();
                    break;
                case InfIzq:
                    Collections.addAll(list, "D");
                    secuencia(list);
                    primeraCruz();
                    break;
                case InfDel:
                    switch(((Arista) rubikL.getCubo()[2][1][0].getPieza()).getOrientacion()){
                        case 1:
                            Collections.addAll(list, "F", "F");
                            break;
                        case 2:
                            Collections.addAll(list, "L", "Di", "Li", "F");
                            break;
                    }
                    secuencia(list);
                    break;
            }
        } else if(!rubikL.getCubo()[0][1][0].getPieza().getOrientacion().equals(1)){
             Collections.addAll(list, "F", "F");
             secuencia(list);
             primeraCruz();
        }
    }
    
    private boolean checkPaso1(){
        boolean chk = true;
        this.pasosCompletados=0;
        if(!rubikL.getCubo()[0][1][0].getId().equals(rubikL.getCubo()[0][1][0].getPieza().getId()) 
            || !rubikL.getCubo()[0][1][0].getPieza().getOrientacion().equals(1))
            chk = false;
        if(!rubikL.getCubo()[0][1][2].getId().equals(rubikL.getCubo()[0][1][2].getPieza().getId()) 
            || !rubikL.getCubo()[0][1][2].getPieza().getOrientacion().equals(1))
            chk = false;
        if(!rubikL.getCubo()[0][0][1].getId().equals(rubikL.getCubo()[0][0][1].getPieza().getId()) 
            || !rubikL.getCubo()[0][0][1].getPieza().getOrientacion().equals(1))
            chk = false;
        if(!rubikL.getCubo()[0][2][1].getId().equals(rubikL.getCubo()[0][2][1].getPieza().getId()) 
            || !rubikL.getCubo()[0][2][1].getPieza().getOrientacion().equals(1))
            chk = false;
        if(chk)
           this.pasosCompletados=1; 
        return chk;
    }
    
    private void esquinasPrimerNivel(){
        ArrayList<String> list = new ArrayList<>();
        if(!(rubikL.getCubo()[0][2][0].getId().equals(rubikL.getCubo()[0][2][0].getPieza().getId()))){
            Esquina arista = (Esquina) rubikL.encontrarPieza(rubikL.getCubo()[0][2][0].getId());
            switch((arista.getPos())){
                case SupDerTra:
                    Collections.addAll(list, "Bi", "Di", "B");
                    secuencia(list);
                    esquinasPrimerNivel();
                    break;
                case SupIzqTra:
                    Collections.addAll(list, "B", "D", "D", "Bi");
                    secuencia(list);
                    esquinasPrimerNivel();
                    break;
                case SupIzqDel:
                    Collections.addAll(list, "L", "D", "Li");
                    secuencia(list);
                    esquinasPrimerNivel();
                    break;
                case InfDerTra:
                    Collections.addAll(list, "Di");
                    secuencia(list);
                    esquinasPrimerNivel();
                    break;
                case InfIzqTra:
                    Collections.addAll(list, "D", "D");
                    secuencia(list);
                    esquinasPrimerNivel();
                    break;
                case InfIzqDel:
                    Collections.addAll(list, "D");
                    secuencia(list);
                    esquinasPrimerNivel();
                    break;
                case InfDerDel:
                    switch(((Esquina) rubikL.getCubo()[2][2][0].getPieza()).getOrientacion()){
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
                    secuencia(list);
                    break;
            }
        } else if(!rubikL.getCubo()[0][2][0].getPieza().getOrientacion().equals(1)){
            Collections.addAll(list, "Ri", "Di", "R", "D");
            secuencia(list);
            esquinasPrimerNivel();
        }
    }
    
    private boolean checkPaso2(){
        boolean chk = true;
        if(!rubikL.getCubo()[0][0][0].getId().equals(rubikL.getCubo()[0][0][0].getPieza().getId()) 
            || !rubikL.getCubo()[0][0][0].getPieza().getOrientacion().equals(1))
            chk = false;
        if(!rubikL.getCubo()[0][0][2].getId().equals(rubikL.getCubo()[0][0][2].getPieza().getId()) 
            || !rubikL.getCubo()[0][0][2].getPieza().getOrientacion().equals(1))
            chk = false;
        if(!rubikL.getCubo()[0][2][0].getId().equals(rubikL.getCubo()[0][2][0].getPieza().getId()) 
            || !rubikL.getCubo()[0][2][0].getPieza().getOrientacion().equals(1))
            chk = false;
        if(!rubikL.getCubo()[0][2][2].getId().equals(rubikL.getCubo()[0][2][2].getPieza().getId()) 
            || !rubikL.getCubo()[0][2][2].getPieza().getOrientacion().equals(1))
            chk = false;
        if(chk)
           this.pasosCompletados=2;
        return chk;
    }
    
    private void aristasDerechasSegundoNivel(){
        Boolean latEnLat = ((boolean) AppContext.getInstance().get("LatEnLat"));
        Integer orientFinalNecesaria = latEnLat ? 2:1;
        ArrayList<String> list = new ArrayList<>();
        if(!(rubikL.getCubo()[1][2][0].getId().equals(rubikL.getCubo()[1][2][0].getPieza().getId()))){
            Arista arista = (Arista) rubikL.encontrarPieza(rubikL.getCubo()[1][2][0].getId());
            switch((arista.getPos2())){
                case SupDer:
                    if(arista.getOrientacion().equals(orientFinalNecesaria)){
                        movimientoUnico("Ui");
                        aristasDerechasSegundoNivel();
                    }
                    break;
                case SupIzq:
                    if(arista.getOrientacion().equals(orientFinalNecesaria)){
                        movimientoUnico("U");
                        aristasDerechasSegundoNivel();
                    }
                    break;
                case SupDel:
                    if(arista.getOrientacion().equals(orientFinalNecesaria)){
                        Collections.addAll(list, "U", "U");
                        secuencia(list);
                        aristasDerechasSegundoNivel();
                    }
                    break;
                case CenDelIzq:
                    if(arista.getOrientacion().equals(2)){
                        Collections.addAll(list, "F", "U", "Fi", "Ui", "Li", "Ui", "L", "Ui");
                        secuencia(list);
                    } else {
                        Collections.addAll(list, "Li", "Ui", "L", "U", "F", "U", "Fi");
                        secuencia(list);
                    }
                    aristasDerechasSegundoNivel();
                    break;
                case CenTraIzq:
                    if(arista.getOrientacion().equals(2)){
                        Collections.addAll(list, "Yi", "Li", "Ui", "L", "U", "F", "U", "Fi", "Ui", "Y");
                        secuencia(list);
                    } else {
                        Collections.addAll(list, "Yi", "F", "U", "Fi", "Ui", "Li", "Ui", "L", "Ui", "Ui", "Y");
                        secuencia(list);
                    }
                    aristasDerechasSegundoNivel();
                    break;
                case CenTraDer:
                    if(arista.getOrientacion().equals(2)){
                        Collections.addAll(list, "Y", "R", "U", "Ri", "Ui", "Fi", "Ui", "F", "U", "Yi");
                        secuencia(list);
                    } else {
                        Collections.addAll(list, "Y", "Fi", "Ui", "F", "U", "R", "U", "Ri", "Ui", "Ui", "Yi");
                        secuencia(list);
                    }
                    aristasDerechasSegundoNivel();
                    break;
                case SupTra:
                    //Caso final
                    if(arista.getOrientacion().equals(orientFinalNecesaria)){
                        Collections.addAll(list, "Fi", "Ui", "F", "U", "R", "U", "Ri");
                        secuencia(list);
                    }
                    break;
            }
        } else if(!rubikL.getCubo()[1][2][0].getPieza().getOrientacion().equals(1)){
             Collections.addAll(list, "Fi", "Ui", "F", "U", "R", "U", "Ri", "U");
             secuencia(list);
             aristasDerechasSegundoNivel();
        }
    }
    
    private void aristasIzquierdasSegundoNivel(){
        Boolean latEnLat = ((boolean) AppContext.getInstance().get("LatEnLat"));
        Integer orientFinalNecesaria = latEnLat ? 2:1;
        ArrayList<String> list = new ArrayList<>();
        if(!(rubikL.getCubo()[1][0][0].getId().equals(rubikL.getCubo()[1][0][0].getPieza().getId()))){
            Arista arista = (Arista) rubikL.encontrarPieza(rubikL.getCubo()[1][0][0].getId());
            switch((arista.getPos2())){
                case SupDer:
                    if(arista.getOrientacion().equals(orientFinalNecesaria)){
                        movimientoUnico("Ui");
                        aristasIzquierdasSegundoNivel();
                    }
                    break;
                case SupIzq:
                    if(arista.getOrientacion().equals(orientFinalNecesaria)){
                        movimientoUnico("U");
                        aristasIzquierdasSegundoNivel();
                    }
                    break;
                case SupDel:
                    if(arista.getOrientacion().equals(orientFinalNecesaria)){
                        Collections.addAll(list, "U", "U");
                        secuencia(list);
                        aristasIzquierdasSegundoNivel();
                    }
                    break;
                case CenDelDer:
                    if(arista.getOrientacion().equals(2)){
                        Collections.addAll(list, "Fi", "Ui", "F", "U", "R", "U", "Ri", "U");
                        secuencia(list);
                    } else {
                        Collections.addAll(list, "R", "U", "Ri", "Ui", "Fi", "Ui", "F");
                        secuencia(list);
                    }
                    aristasIzquierdasSegundoNivel();
                    break;
                case CenTraIzq:
                    if(arista.getOrientacion().equals(2)){
                        Collections.addAll(list, "Yi", "Li", "Ui", "L", "U", "F", "U", "Fi", "Ui", "Y");
                        secuencia(list);
                    } else {
                        Collections.addAll(list, "Yi", "F", "U", "Fi", "Ui", "Li", "Ui", "L", "Ui", "Ui", "Y");
                        secuencia(list);
                    }
                    aristasIzquierdasSegundoNivel();
                    break;
                case CenTraDer:
                    if(arista.getOrientacion().equals(2)){
                        Collections.addAll(list, "Y", "R", "U", "Ri", "Ui", "Fi", "Ui", "F", "U", "Yi");
                        secuencia(list);
                    } else {
                        Collections.addAll(list, "Y", "Fi", "Ui", "F", "U", "R", "U", "Ri", "Ui", "Ui", "Yi");
                        secuencia(list);
                    }
                    aristasIzquierdasSegundoNivel();
                    break;
                case SupTra:
                    //Caso final
                    if(arista.getOrientacion().equals(orientFinalNecesaria)){
                        Collections.addAll(list, "F", "U", "Fi", "Ui", "Li", "Ui", "L");
                        secuencia(list);
                    }
                    break;
            }
        } else if(!rubikL.getCubo()[1][0][0].getPieza().getOrientacion().equals(1)){
             Collections.addAll(list, "F", "U", "Fi", "Ui", "Li", "Ui", "L", "Ui");
             secuencia(list);
             aristasIzquierdasSegundoNivel();
        }
    }
    
    private boolean checkPaso3(){
        boolean chk = true;
        if(!rubikL.getCubo()[1][0][0].getId().equals(rubikL.getCubo()[1][0][0].getPieza().getId()) 
            || !rubikL.getCubo()[1][0][0].getPieza().getOrientacion().equals(1))
            chk = false;
        if(!rubikL.getCubo()[1][0][2].getId().equals(rubikL.getCubo()[1][0][2].getPieza().getId()) 
            || !rubikL.getCubo()[1][0][2].getPieza().getOrientacion().equals(1))
            chk = false;
        if(!rubikL.getCubo()[1][2][0].getId().equals(rubikL.getCubo()[1][2][0].getPieza().getId()) 
            || !rubikL.getCubo()[1][2][0].getPieza().getOrientacion().equals(1))
            chk = false;
        if(!rubikL.getCubo()[1][2][2].getId().equals(rubikL.getCubo()[1][2][2].getPieza().getId()) 
            || !rubikL.getCubo()[1][2][2].getPieza().getOrientacion().equals(1))
            chk = false;
        if(chk)
            this.pasosCompletados=3;
        return chk;
    }
    
    public void aristasTercerNivel(){
        ArrayList<String> list = new ArrayList<>();
        boolean ariF = rubikL.getCubo()[0][1][0].getPieza().getOrientacion().equals(1);
        boolean ariI = rubikL.getCubo()[0][0][1].getPieza().getOrientacion().equals(1);
        boolean ariD = rubikL.getCubo()[0][2][1].getPieza().getOrientacion().equals(1);
        boolean ariT = rubikL.getCubo()[0][1][2].getPieza().getOrientacion().equals(1);
        Integer n = 0;
        if(ariF) n++;
        if(ariI) n++;
        if(ariD) n++;
        if(ariT) n++;
        switch(n){
            case 1:
                if(ariF) {
                    movimientoUnico("U");
                    aristasTercerNivel();
                }
                else if(ariD){
                    Collections.addAll(list, "U", "U");
                    secuencia(list);
                    aristasTercerNivel();
                }
                else if(ariT){
                    movimientoUnico("Ui");
                    aristasTercerNivel();
                }
                else if(ariI){
                    Collections.addAll(list, "F", "R", "U", "Ri", "Ui");
                    secuencia(list);
                    list.clear();
                    if(!rubikL.getCubo()[0][1][2].getPieza().getOrientacion().equals(1))
                        Collections.addAll(list, "R", "U", "Ri", "Ui", "Fi");
                    else 
                        Collections.addAll(list, "Fi");
                    secuencia(list);
                }
                break;
            case 2:
                if(ariI && !ariF){
                    Collections.addAll(list, "F", "R", "U", "Ri", "Ui");
                    secuencia(list);
                    list.clear();
                    if(!rubikL.getCubo()[0][1][2].getPieza().getOrientacion().equals(1))
                        Collections.addAll(list, "R", "U", "Ri", "Ui", "Fi");
                    else 
                        Collections.addAll(list, "Fi");
                    secuencia(list);
                } else {
                    movimientoUnico("U");
                    aristasTercerNivel();
                }
                break;
            case 4:
                this.paso4 = true;
                break;
            case 0:
                Collections.addAll(list, "F", "R", "U", "Ri", "Ui");
                secuencia(list);
                list.clear();
                if(!rubikL.getCubo()[0][1][2].getPieza().getOrientacion().equals(1))
                    Collections.addAll(list, "R", "U", "Ri", "Ui", "Fi");
                else 
                    Collections.addAll(list, "Fi");
                secuencia(list);
                aristasTercerNivel();
                break;
        }
    }
    
    private boolean checkPaso4(){
        boolean chk = true;
        if(!rubikL.getCubo()[0][1][0].getPieza().getOrientacion().equals(1))
            chk = false;
        if(!rubikL.getCubo()[0][0][1].getPieza().getOrientacion().equals(1))
            chk = false;
        if(!rubikL.getCubo()[0][2][1].getPieza().getOrientacion().equals(1))
            chk = false;
        if(!rubikL.getCubo()[0][1][2].getPieza().getOrientacion().equals(1))
            chk = false;
        if(chk)
            this.pasosCompletados=4;
        return chk;
    }
    
    private void orientarCruzTercerNivel(){
        ArrayList<String> list = new ArrayList<>();
        boolean ariF = rubikL.getCubo()[0][1][0].getId().equals(rubikL.getCubo()[0][1][0].getPieza().getId());
        boolean ariI = rubikL.getCubo()[0][0][1].getId().equals(rubikL.getCubo()[0][0][1].getPieza().getId());
        boolean ariD = rubikL.getCubo()[0][2][1].getId().equals(rubikL.getCubo()[0][2][1].getPieza().getId());
        boolean ariT = rubikL.getCubo()[0][1][2].getId().equals(rubikL.getCubo()[0][1][2].getPieza().getId());
        Integer n = 0;
        if(ariF) n++;
        if(ariI) n++;
        if(ariD) n++;
        if(ariT) n++;
        switch(n){
            case 1:
                if(ariF) {
                    Collections.addAll(list, "R", "U", "Ri", "U", "R", "U", "U", "Ri");
                    secuencia(list);
                    orientarCruzTercerNivel();
                }
                else if(ariD){
                    movimientoUnico("Y");
                    orientarCruzTercerNivel();
                }
                else if(ariT){
                    Collections.addAll(list, "Y", "Y");
                    secuencia(list);
                    orientarCruzTercerNivel();
                }
                else if(ariI){
                    movimientoUnico("Yi");
                    orientarCruzTercerNivel();
                }
                break;
            case 2:
                if((ariI && ariD) || (ariT && ariF)){
                    Collections.addAll(list, "U", "R", "U", "Ri", "U", "R", "U", "U", "Ri");
                    secuencia(list);
                    orientarCruzTercerNivel();
                } else {
                    movimientoUnico("U");
                    orientarCruzTercerNivel();
                }
                break;
            case 4:
                this.paso5 = true;
                break;
            case 0:
                movimientoUnico("U");
                orientarCruzTercerNivel();
                break;
        }
    }
    
    private boolean checkPaso5(){
        boolean chk = true;
        if(!rubikL.getCubo()[0][1][0].getId().equals(rubikL.getCubo()[0][1][0].getPieza().getId()))
            chk = false;
        if(!rubikL.getCubo()[0][0][1].getId().equals(rubikL.getCubo()[0][0][1].getPieza().getId()))
            chk = false;
        if(!rubikL.getCubo()[0][2][1].getId().equals(rubikL.getCubo()[0][2][1].getPieza().getId()))
            chk = false;
        if(!rubikL.getCubo()[0][1][2].getId().equals(rubikL.getCubo()[0][1][2].getPieza().getId()))
            chk = false;
        if(chk)
           this.pasosCompletados=5;
        return chk;
    }
    
    private void ubicarEsquinasTercerNivel(){
        ArrayList<String> list = new ArrayList<>();
        boolean delIzq = rubikL.getCubo()[0][0][0].getId().equals(rubikL.getCubo()[0][0][0].getPieza().getId());
        boolean delDer = rubikL.getCubo()[0][2][0].getId().equals(rubikL.getCubo()[0][2][0].getPieza().getId());
        boolean traIzq = rubikL.getCubo()[0][0][2].getId().equals(rubikL.getCubo()[0][0][2].getPieza().getId());
        boolean traDer = rubikL.getCubo()[0][2][2].getId().equals(rubikL.getCubo()[0][2][2].getPieza().getId());
        Integer n = 0;
        if(delIzq) n++;
        if(delDer) n++;
        if(traIzq) n++;
        if(traDer) n++;
        if(n.equals(0)){
            Collections.addAll(list, "U", "R", "Ui", "Li", "U", "Ri", "Ui", "L");
            secuencia(list);
            ubicarEsquinasTercerNivel();
        } else if(n<4){
            if(delDer){
                Collections.addAll(list, "U", "R", "Ui", "Li", "U", "Ri", "Ui", "L");
                secuencia(list);
                ubicarEsquinasTercerNivel();
            } else if(delIzq){
                movimientoUnico("Yi");
                ubicarEsquinasTercerNivel();
            } else if(traDer){
                movimientoUnico("Y");
                ubicarEsquinasTercerNivel();
            } else {
                Collections.addAll(list, "Y", "Y");
                secuencia(list);
                ubicarEsquinasTercerNivel();
            }
        } else {
           paso6 = true; 
        }
    }
    
    private boolean checkPaso6(){
        boolean chk = true;
        if(!rubikL.getCubo()[0][0][0].getId().equals(rubikL.getCubo()[0][0][0].getPieza().getId()))
            chk = false;
        if(!rubikL.getCubo()[0][0][2].getId().equals(rubikL.getCubo()[0][0][2].getPieza().getId()))
            chk = false;
        if(!rubikL.getCubo()[0][2][0].getId().equals(rubikL.getCubo()[0][2][0].getPieza().getId()))
            chk = false;
        if(!rubikL.getCubo()[0][2][2].getId().equals(rubikL.getCubo()[0][2][2].getPieza().getId()))
            chk = false;
        if(chk)
           this.pasosCompletados=6;
        return chk;
    }
    
    private void orientarEsquinasTercerNivel(){
        ArrayList<String> list = new ArrayList<>();
        Integer n = 0;
        if(rubikL.getCubo()[0][0][0].getPieza().getOrientacion().equals(2)) n++;
        if(rubikL.getCubo()[0][0][2].getPieza().getOrientacion().equals(2)) n++;
        if(rubikL.getCubo()[0][2][0].getPieza().getOrientacion().equals(2)) n++;
        if(rubikL.getCubo()[0][2][2].getPieza().getOrientacion().equals(2)) n++;
        if(n<4){
            n = 0;
            Esquina auxEsq = (Esquina) rubikL.getCubo()[0][2][0].getPieza();
            while(!checkPaso7() && n<16){
                list.clear();
                if(rubikL.getCubo()[0][2][0].getPieza().getId().equals(auxEsq.getId())
                && rubikL.getCubo()[0][2][0].getPieza().getOrientacion().equals(2)){
                    Collections.addAll(list, "Ui");
                    secuencia(list);
                    auxEsq = (Esquina) rubikL.getCubo()[0][2][0].getPieza();
                } else {
                    Collections.addAll(list, "Ri", "Di", "R", "D");
                    secuencia(list);
                }
                n++;
            }
        } else {
            paso7 = true;
        }
    }
    
    private boolean checkPaso7(){
        boolean chk = true;
        if(!rubikL.getCubo()[0][0][0].getPieza().getOrientacion().equals(2)
            || !rubikL.getCubo()[0][0][0].getId().equals(rubikL.getCubo()[0][0][0].getPieza().getId()))
            chk = false;
        if(!rubikL.getCubo()[0][0][2].getPieza().getOrientacion().equals(2)
            || !rubikL.getCubo()[0][0][2].getId().equals(rubikL.getCubo()[0][0][2].getPieza().getId()))
            chk = false;
        if(!rubikL.getCubo()[0][2][0].getPieza().getOrientacion().equals(2)
            || !rubikL.getCubo()[0][2][0].getId().equals(rubikL.getCubo()[0][2][0].getPieza().getId()))
            chk = false;
        if(!rubikL.getCubo()[0][2][2].getPieza().getOrientacion().equals(2)
            || !rubikL.getCubo()[0][2][2].getId().equals(rubikL.getCubo()[0][2][2].getPieza().getId()))
            chk = false;
        if(chk)
           this.pasosCompletados=7;
        return chk;
    }
    
    public void secuencia(ArrayList<String> list){
        
        list.stream().forEach(str -> {
            //moves.addMove(new Move(str, LocalTime.now().minusNanos(time.toNanoOfDay()).toNanoOfDay()));
            this.asignarMovimientos(str);
            rubikL.movimientoBasico(str);
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
    
    public void movimientoUnico(String mov){
        rubikL.movimientoBasico(mov);
        this.asignarMovimientos(mov);
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
        return rubikL;
    }

    public void setRubikL(RubikL rubikL) {
        this.rubikL = rubikL;
    }

    public boolean isPaso1() {
        return paso1;
    }

    public void setPaso1(boolean paso1) {
        this.paso1 = paso1;
    }

    public boolean isPaso2() {
        return paso2;
    }

    public void setPaso2(boolean paso2) {
        this.paso2 = paso2;
    }

    public boolean isPaso3() {
        return paso3;
    }

    public void setPaso3(boolean paso3) {
        this.paso3 = paso3;
    }

    public boolean isPaso4() {
        return paso4;
    }

    public void setPaso4(boolean paso4) {
        this.paso4 = paso4;
    }

    public boolean isPaso5() {
        return paso5;
    }

    public void setPaso5(boolean paso5) {
        this.paso5 = paso5;
    }

    public boolean isPaso6() {
        return paso6;
    }

    public void setPaso6(boolean paso6) {
        this.paso6 = paso6;
    }

    public boolean isPaso7() {
        return paso7;
    }

    public void setPaso7(boolean paso7) {
        this.paso7 = paso7;
    }

    public boolean isPaso8() {
        return paso8;
    }

    public void setPaso8(boolean paso8) {
        this.paso8 = paso8;
    }

    public RubikG getRubikG() {
        return rubikG;
    }

    public void setRubikG(RubikG rubikG) {
        this.rubikG = rubikG;
    }
    
    public void asignarMovimientos(String mov){
        //selecciona la etapa para separar por listas (pasos completados se asigna al checkear etapas)
        this.listaMovEtapa=AppContext.getInstance().getMoveLists().get(this.pasosCompletados+1);
        //asigna a la lista por etapas y a la general
        this.listaMovEtapa.addMove(new Move(mov, LocalTime.now().minusNanos(time.toNanoOfDay()).toNanoOfDay()));
        this.listaMovGeneral.addMove(new Move(mov, LocalTime.now().minusNanos(time.toNanoOfDay()).toNanoOfDay()));
    }
    
}
