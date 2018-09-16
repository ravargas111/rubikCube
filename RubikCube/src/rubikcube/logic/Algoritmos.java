/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rubikcube.logic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import rubikcube.model.RubikG;

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

    //Constructors
    public Algoritmos(RubikL rubikL, RubikG rubikG) {
        this.rubikL = rubikL;
        this.rubikG = rubikG;
    }
    
    //Methods
    public void verificarNiveles(){
        //Se verifica cuales niveles ya estan armados
    }
    
    public void autoArmado(){
        if(!paso1) primeraCruz();
        if(!paso2) ;
        if(!paso3) ;
        if(!paso4) ;
        if(!paso5) ;
        if(!paso6) ;
        if(!paso7) ;
        if(!paso8) ;
    }
    
    public void primeraCruz(){
        System.out.println("intentando armar la primera arista");
        if(!(rubikL.getCubo()[0][1][0].getId().equals(rubikL.getCubo()[0][1][0].getPieza().getId()))){
            System.out.println("la arista no esta donde deberia");
            Arista arista = (Arista) rubikL.encontrarPieza(rubikL.getCubo()[0][1][0].getId());
            ArrayList<String> list = new ArrayList<>();
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
                    Collections.addAll(list, "Bi", "Di", "Di");
                    secuencia(list);
                    primeraCruz();
                    break;
                case CenTraIzq:
                    Collections.addAll(list, "Li", "D");
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
                    System.out.println("La arista esta en la parte inferior delantera");
                    switch(((Arista) rubikL.getCubo()[0][1][0].getPieza()).getOrientacion()){
                        case 1:
                            Collections.addAll(list, "F");
                            break;
                        case 2:
                            Collections.addAll(list, "L", "Di", "Li", "F");
                            break;
                    }
                    secuencia(list);
                    break;
            }
        }
    }
    
    public void secuencia(ArrayList<String> list){
        System.out.println("se intenta aplicar la secuencia");
        list.stream().forEach(mov -> rubikG.rotateFace(mov));
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
    
}
