/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rubikcube.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import rubikcube.model.RubikG;
import rubikcube.moves.Move;
import rubikcube.moves.Moves;
import rubikcube.util.AppContext;

/**
 *
 * @author robri
 */
public class RubikL {
    private RubikG rubickG;
    private Contenedor[][][] cubo;
    private ArrayList<Moves> moveLists;

    //recibe un cubo gráfico para hacer las rotaciones
    public RubikL(RubikG rubickG) {
        this.rubickG = rubickG;
        this.moveLists=AppContext.getInstance().getMoveLists();
        //inicializa las listas de movimientos realizados lógicamente
        for(Integer i=0;i<9;i++){
            this.moveLists.add(new Moves());
        }
        cubo = new Contenedor[3][3][3];
        Integer contador = 1;
        for(Integer z=0; z<3; z++){
            for(Integer x=0; x<3; x++){
                for(Integer y=0; y<3; y++){
                    Pieza p;
                    //Centro del cubo
                    if(x==1&&y==1&&z==1){
                        p = new Centro(999);
                    }
                    // Esquinas
                    else if((x!=1) && (z!=1) && (y!=1)){
                        p = new Esquina(contador);
                    }
                    //Centros
                    else if(((x!=1)&&y==1&&z==1) || ((y!=1)&&x==1&&z==1) || ((z!=1)&&x==1&&y==1)){
                        p = new Centro(contador);
                    }
                    //Aristas
                    else {
                        p = new Arista(contador);
                    }
                    p.setPos(x, y, z);
                    cubo[x][y][z] = new Contenedor(p);
                    contador++;
                }
            }
        }
        AppContext.getInstance().set("LatEnLat", true);
    }
    
    public RubikL(RubikL cuboLogico){
        this.rubickG = cuboLogico.getRubickG();
        cubo = new Contenedor[3][3][3];
        for(Integer z=0; z<3; z++){
            for(Integer x=0; x<3; x++){
                for(Integer y=0; y<3; y++){
                    Pieza p = null;
                    switch(cuboLogico.getCubo()[x][y][z].getTipoPieza()){
                        case CENTRO:
                            p = new Centro(cuboLogico.getCubo()[x][y][z].getPieza().getId());
                            break;
                        case ARISTA:
                            p = new Arista(cuboLogico.getCubo()[x][y][z].getPieza().getId());
                            break;
                        case ESQUINA:
                            p = new Esquina(cuboLogico.getCubo()[x][y][z].getPieza().getId());
                            break;
                    }
                    if(p != null){
                        p.setOrientacion(cuboLogico.getCubo()[x][y][z].getPieza().getOrientacion());
                        p.setPos(x, y, z);
                        cubo[x][y][z] = new Contenedor(p);
                        cubo[x][y][z].setId(cuboLogico.getCubo()[x][y][z].getId());
                    }
                }
            }
        }
    }
    
    //usar este metiendo por parámetros el movimiento,porque creo en la parte gráfica generaría doble movimiento 
    /**
     * llama a la rotacíon del cubo gráfico (y esta llama a la rotación del lógico: "orientarse")
     * @param face nombre del movimiento
     */
    public void rotateFaceG(String face){
        if(face.contains("L")||face.contains("R")||face.contains("U")||face.contains("D")||face.contains("F")||face.contains("B"))
           this.rubickG.rotateFace(face); 
    }
    
    //únicamente rota el cubo lógico (usado en cubo gráfico)
    /**
     * llama a la rotación lógica sin llamar a la gráfica, usada desde la parte gráfica solamente (RubikG -> rotateFace -> línea5)
     * nota: el vubo gráfico tuiene un cubo lógico
     * @param face 
     */
    public void movimientoBasico(String face){
        if(face.contains("L")||face.contains("R")||face.contains("U")||face.contains("D")||face.contains("F")||face.contains("B")||face.contains("Y")||face.contains("X")){
            switch(face){
                case "Li": rotateL(true);break;
                case "L": rotateL(false);break;
                case "Ri": rotateR(true);break;
                case "R": rotateR(false);break;
                case "Ui": rotateU(true);break;
                case "U": rotateU(false);break;
                case "Di": rotateD(true);break;
                case "D": rotateD(false);break;
                case "Fi": rotateF(true);break;
                case "F": rotateF(false);break;
                case "Bi": rotateB(true);break;
                case "B": rotateB(false);break;
                case "Yi": rotateY(true);break;
                case "Y": rotateY(false);break;
                case "Xi": rotateX(true);break;
                case "X": rotateX(false);break;
            }
        }
    }
    
    //Movimientos publicos que el usuario puede hacer
    public void rotateL(Boolean i){
        if(i){
            Pieza auxAri, auxEsq;
            auxAri = cubo[0][0][1].getPieza();
            auxEsq = cubo[0][0][0].getPieza();
            cubo[0][0][1].setPieza(cubo[1][0][0].getPieza());
            cubo[0][0][1].getPieza().orientar("Li", 0, 0, 1);
            cubo[0][0][0].setPieza(cubo[2][0][0].getPieza());
            cubo[0][0][0].getPieza().orientar("Li", 0, 0, 0);
            cubo[1][0][0].setPieza(cubo[2][0][1].getPieza());
            cubo[1][0][0].getPieza().orientar("Li", 1, 0, 0);
            cubo[2][0][0].setPieza(cubo[2][0][2].getPieza());
            cubo[2][0][0].getPieza().orientar("Li", 2, 0, 0);
            cubo[2][0][1].setPieza(cubo[1][0][2].getPieza());
            cubo[2][0][1].getPieza().orientar("Li", 2, 0, 1);
            cubo[2][0][2].setPieza(cubo[0][0][2].getPieza());
            cubo[2][0][2].getPieza().orientar("Li", 2, 0, 2);
            cubo[1][0][2].setPieza(auxAri);
            cubo[1][0][2].getPieza().orientar("Li", 1, 0, 2);
            cubo[0][0][2].setPieza(auxEsq);
            cubo[0][0][2].getPieza().orientar("Li", 0, 0, 2);
        }
        else{
            Pieza auxAri, auxEsq;
            auxAri = cubo[0][0][1].getPieza();
            auxEsq = cubo[0][0][0].getPieza();
            cubo[0][0][0].setPieza(cubo[0][0][2].getPieza());
            cubo[0][0][0].getPieza().orientar("L", 0, 0, 0);
            cubo[0][0][1].setPieza(cubo[1][0][2].getPieza());
            cubo[0][0][1].getPieza().orientar("L", 0, 0, 1);
            cubo[0][0][2].setPieza(cubo[2][0][2].getPieza());
            cubo[0][0][2].getPieza().orientar("L", 0, 0, 2);
            cubo[1][0][2].setPieza(cubo[2][0][1].getPieza());
            cubo[1][0][2].getPieza().orientar("L", 1, 0, 2);
            cubo[2][0][2].setPieza(cubo[2][0][0].getPieza());
            cubo[2][0][2].getPieza().orientar("L", 2, 0, 2);
            cubo[2][0][1].setPieza(cubo[1][0][0].getPieza());
            cubo[2][0][1].getPieza().orientar("L", 2, 0, 1);
            cubo[2][0][0].setPieza(auxEsq);
            cubo[2][0][0].getPieza().orientar("L", 2, 0, 0);
            cubo[1][0][0].setPieza(auxAri);
            cubo[1][0][0].getPieza().orientar("L", 1, 0, 0);
        }
    }
    
    public void rotateR(Boolean i){
        if(i){
            Pieza auxAri, auxEsq;
            auxEsq = cubo[0][2][0].getPieza();
            auxAri = cubo[0][2][1].getPieza();
            cubo[0][2][0].setPieza(cubo[0][2][2].getPieza());
            cubo[0][2][0].getPieza().orientar("Ri", 0, 2, 0);
            cubo[0][2][1].setPieza(cubo[1][2][2].getPieza());
            cubo[0][2][1].getPieza().orientar("Ri", 0, 2, 1);
            cubo[0][2][2].setPieza(cubo[2][2][2].getPieza());
            cubo[0][2][2].getPieza().orientar("Ri", 0, 2, 2);
            cubo[1][2][2].setPieza(cubo[2][2][1].getPieza());
            cubo[1][2][2].getPieza().orientar("Ri", 1, 2, 2);
            cubo[2][2][2].setPieza(cubo[2][2][0].getPieza());
            cubo[2][2][2].getPieza().orientar("Ri", 2, 2, 2);
            cubo[2][2][1].setPieza(cubo[1][2][0].getPieza());
            cubo[2][2][1].getPieza().orientar("Ri", 2, 2, 1);
            cubo[2][2][0].setPieza(auxEsq);
            cubo[2][2][0].getPieza().orientar("Ri", 2, 2, 0);
            cubo[1][2][0].setPieza(auxAri);
            cubo[1][2][0].getPieza().orientar("Ri", 1, 2, 0);
        }
        else{
            Pieza auxAri, auxEsq;
            auxAri = cubo[0][2][1].getPieza();
            auxEsq = cubo[0][2][0].getPieza();
            cubo[0][2][1].setPieza(cubo[1][2][0].getPieza());
            cubo[0][2][1].getPieza().orientar("R", 0, 2, 1);
            cubo[0][2][0].setPieza(cubo[2][2][0].getPieza());
            cubo[0][2][0].getPieza().orientar("R", 0, 2, 0);
            cubo[1][2][0].setPieza(cubo[2][2][1].getPieza());
            cubo[1][2][0].getPieza().orientar("R", 1, 2, 0);
            cubo[2][2][0].setPieza(cubo[2][2][2].getPieza());
            cubo[2][2][0].getPieza().orientar("R", 2, 2, 0);
            cubo[2][2][1].setPieza(cubo[1][2][2].getPieza());
            cubo[2][2][1].getPieza().orientar("R", 2, 2, 1);
            cubo[2][2][2].setPieza(cubo[0][2][2].getPieza());
            cubo[2][2][2].getPieza().orientar("R", 2, 2, 2);
            cubo[1][2][2].setPieza(auxAri);
            cubo[1][2][2].getPieza().orientar("R", 1, 2, 2);
            cubo[0][2][2].setPieza(auxEsq);
            cubo[0][2][2].getPieza().orientar("R", 0, 2, 2);
        }
    }
    
    public void rotateU(Boolean i){
        if(i){
            Pieza aux, aux2;
            aux = cubo[0][2][0].getPieza();
            aux2 = cubo[0][2][1].getPieza();
            cubo[0][2][1].setPieza(cubo[0][1][0].getPieza());
            cubo[0][2][1].getPieza().orientar("Ui", 0, 2, 1);
            cubo[0][2][0].setPieza(cubo[0][0][0].getPieza());
            cubo[0][2][0].getPieza().orientar("Ui", 0, 2, 0);
            cubo[0][1][0].setPieza(cubo[0][0][1].getPieza());
            cubo[0][1][0].getPieza().orientar("Ui", 0, 1, 0);
            cubo[0][0][0].setPieza(cubo[0][0][2].getPieza());
            cubo[0][0][0].getPieza().orientar("Ui", 0, 0, 0);
            cubo[0][0][1].setPieza(cubo[0][1][2].getPieza());
            cubo[0][0][1].getPieza().orientar("Ui", 0, 0, 1);
            cubo[0][0][2].setPieza(cubo[0][2][2].getPieza());
            cubo[0][0][2].getPieza().orientar("Ui", 0, 0, 2);
            cubo[0][1][2].setPieza(aux2);
            cubo[0][1][2].getPieza().orientar("Ui", 0, 1, 2);
            cubo[0][2][2].setPieza(aux);
            cubo[0][2][2].getPieza().orientar("Ui", 0, 2, 2);
        }
        else{
            Pieza aux, aux2;
            aux = cubo[0][0][1].getPieza();
            aux2 = cubo[0][0][0].getPieza();
            cubo[0][0][1].setPieza(cubo[0][1][0].getPieza());
            cubo[0][0][1].getPieza().orientar("U", 0, 0, 1);
            cubo[0][0][0].setPieza(cubo[0][2][0].getPieza());
            cubo[0][0][0].getPieza().orientar("U", 0, 0, 0);
            cubo[0][1][0].setPieza(cubo[0][2][1].getPieza());
            cubo[0][1][0].getPieza().orientar("U", 0, 1, 0);
            cubo[0][2][0].setPieza(cubo[0][2][2].getPieza());
            cubo[0][2][0].getPieza().orientar("U", 0, 2, 0);
            cubo[0][2][1].setPieza(cubo[0][1][2].getPieza());
            cubo[0][2][1].getPieza().orientar("U", 0, 2, 1);
            cubo[0][2][2].setPieza(cubo[0][0][2].getPieza());
            cubo[0][2][2].getPieza().orientar("U", 0, 2, 2);
            cubo[0][1][2].setPieza(aux);
            cubo[0][1][2].getPieza().orientar("U", 0, 1, 2);
            cubo[0][0][2].setPieza(aux2);
            cubo[0][0][2].getPieza().orientar("U", 0, 0, 2);
        }
    }
    
    public void rotateD(Boolean i){
        if(i){
            Pieza aux, aux2;
            aux = cubo[2][0][1].getPieza();
            aux2 = cubo[2][0][0].getPieza();
            cubo[2][0][1].setPieza(cubo[2][1][0].getPieza());
            cubo[2][0][1].getPieza().orientar("Di", 2, 0, 1);
            cubo[2][0][0].setPieza(cubo[2][2][0].getPieza());
            cubo[2][0][0].getPieza().orientar("Di", 2, 0, 0);
            cubo[2][1][0].setPieza(cubo[2][2][1].getPieza());
            cubo[2][1][0].getPieza().orientar("Di", 2, 1, 0);
            cubo[2][2][0].setPieza(cubo[2][2][2].getPieza());
            cubo[2][2][0].getPieza().orientar("Di", 2, 2, 0);
            cubo[2][2][1].setPieza(cubo[2][1][2].getPieza());
            cubo[2][2][1].getPieza().orientar("Di", 2, 2, 1);
            cubo[2][2][2].setPieza(cubo[2][0][2].getPieza());
            cubo[2][2][2].getPieza().orientar("Di", 2, 2, 2);
            cubo[2][1][2].setPieza(aux);
            cubo[2][1][2].getPieza().orientar("Di", 2, 1, 2);
            cubo[2][0][2].setPieza(aux2);
            cubo[2][0][2].getPieza().orientar("Di", 2, 0, 2);
        }
        else{
            Pieza aux, aux2;
            aux = cubo[2][2][0].getPieza();
            aux2 = cubo[2][2][1].getPieza();
            cubo[2][2][1].setPieza(cubo[2][1][0].getPieza());
            cubo[2][2][1].getPieza().orientar("D", 2, 2, 1);
            cubo[2][2][0].setPieza(cubo[2][0][0].getPieza());
            cubo[2][2][0].getPieza().orientar("D", 2, 2, 0);
            cubo[2][1][0].setPieza(cubo[2][0][1].getPieza());
            cubo[2][1][0].getPieza().orientar("D", 2, 1, 0);
            cubo[2][0][0].setPieza(cubo[2][0][2].getPieza());
            cubo[2][0][0].getPieza().orientar("D", 2, 0, 0);
            cubo[2][0][1].setPieza(cubo[2][1][2].getPieza());
            cubo[2][0][1].getPieza().orientar("D", 2, 0, 1);
            cubo[2][0][2].setPieza(cubo[2][2][2].getPieza());
            cubo[2][0][2].getPieza().orientar("D", 2, 0, 2);
            cubo[2][1][2].setPieza(aux2);
            cubo[2][1][2].getPieza().orientar("D", 2, 1, 2);
            cubo[2][2][2].setPieza(aux);
            cubo[2][2][2].getPieza().orientar("D", 2, 2, 2);
        }
    }
    
    public void rotateF(Boolean i){
        if(i){
            Pieza auxAri, auxEsq;
            auxEsq = cubo[0][0][0].getPieza();
            auxAri = cubo[0][1][0].getPieza();
            cubo[0][0][0].setPieza(cubo[0][2][0].getPieza());
            cubo[0][0][0].getPieza().orientar("Fi", 0, 0, 0);
            cubo[0][1][0].setPieza(cubo[1][2][0].getPieza());
            cubo[0][1][0].getPieza().orientar("Fi", 0, 1, 0);
            cubo[0][2][0].setPieza(cubo[2][2][0].getPieza());
            cubo[0][2][0].getPieza().orientar("Fi", 0, 2, 0);
            cubo[1][2][0].setPieza(cubo[2][1][0].getPieza());
            cubo[1][2][0].getPieza().orientar("Fi", 1, 2, 0);
            cubo[2][2][0].setPieza(cubo[2][0][0].getPieza());
            cubo[2][2][0].getPieza().orientar("Fi", 2, 2, 0);
            cubo[2][1][0].setPieza(cubo[1][0][0].getPieza());
            cubo[2][1][0].getPieza().orientar("Fi", 2, 1, 0);
            cubo[2][0][0].setPieza(auxEsq);
            cubo[2][0][0].getPieza().orientar("Fi", 2, 0, 0);
            cubo[1][0][0].setPieza(auxAri);
            cubo[1][0][0].getPieza().orientar("Fi", 1, 0, 0);
        }
        else{
            Pieza auxAri, auxEsq;
            auxEsq = cubo[0][0][0].getPieza();
            auxAri = cubo[0][1][0].getPieza();
            cubo[0][0][0].setPieza(cubo[2][0][0].getPieza());
            cubo[0][0][0].getPieza().orientar("F", 0, 0, 0);
            cubo[0][1][0].setPieza(cubo[1][0][0].getPieza());
            cubo[0][1][0].getPieza().orientar("F", 0, 1, 0);
            cubo[2][0][0].setPieza(cubo[2][2][0].getPieza());
            cubo[2][0][0].getPieza().orientar("F", 2, 0, 0);
            cubo[1][0][0].setPieza(cubo[2][1][0].getPieza());
            cubo[1][0][0].getPieza().orientar("F", 1, 0, 0);
            cubo[2][2][0].setPieza(cubo[0][2][0].getPieza());
            cubo[2][2][0].getPieza().orientar("F", 2, 2, 0);
            cubo[2][1][0].setPieza(cubo[1][2][0].getPieza());
            cubo[2][1][0].getPieza().orientar("F", 2, 1, 0);
            cubo[0][2][0].setPieza(auxEsq);
            cubo[0][2][0].getPieza().orientar("F", 0, 2, 0);
            cubo[1][2][0].setPieza(auxAri);
            cubo[1][2][0].getPieza().orientar("F", 1, 2, 0);
        }
    }
    
    public void rotateB(Boolean i){
        if(i){
            Pieza auxAri, auxEsq;
            auxEsq = cubo[0][2][2].getPieza();
            auxAri = cubo[0][1][2].getPieza();
            cubo[0][2][2].setPieza(cubo[0][0][2].getPieza());
            cubo[0][2][2].getPieza().orientar("Bi", 0, 2, 2);
            cubo[0][1][2].setPieza(cubo[1][0][2].getPieza());
            cubo[0][1][2].getPieza().orientar("Bi", 0, 1, 2);
            cubo[0][0][2].setPieza(cubo[2][0][2].getPieza());
            cubo[0][0][2].getPieza().orientar("Bi", 0, 0, 2);
            cubo[1][0][2].setPieza(cubo[2][1][2].getPieza());
            cubo[1][0][2].getPieza().orientar("Bi", 1, 0, 2);
            cubo[2][0][2].setPieza(cubo[2][2][2].getPieza());
            cubo[2][0][2].getPieza().orientar("Bi", 2, 0, 2);
            cubo[2][1][2].setPieza(cubo[1][2][2].getPieza());
            cubo[2][1][2].getPieza().orientar("Bi", 2, 1, 2);
            cubo[2][2][2].setPieza(auxEsq);
            cubo[2][2][2].getPieza().orientar("Bi", 2, 2, 2);
            cubo[1][2][2].setPieza(auxAri);
            cubo[1][2][2].getPieza().orientar("Bi", 1, 2, 2);
        }
        else{
            Pieza auxAri, auxEsq;
            auxEsq = cubo[0][2][2].getPieza();
            auxAri = cubo[0][1][2].getPieza();
            cubo[0][2][2].setPieza(cubo[2][2][2].getPieza());
            cubo[0][2][2].getPieza().orientar("B", 0, 2, 2);
            cubo[0][1][2].setPieza(cubo[1][2][2].getPieza());
            cubo[0][1][2].getPieza().orientar("B", 0, 1, 2);
            cubo[2][2][2].setPieza(cubo[2][0][2].getPieza());
            cubo[2][2][2].getPieza().orientar("B", 2, 2, 2);
            cubo[1][2][2].setPieza(cubo[2][1][2].getPieza());
            cubo[1][2][2].getPieza().orientar("B", 1, 2, 2);
            cubo[2][0][2].setPieza(cubo[0][0][2].getPieza());
            cubo[2][0][2].getPieza().orientar("B", 2, 0, 2);
            cubo[2][1][2].setPieza(cubo[1][0][2].getPieza());
            cubo[2][1][2].getPieza().orientar("B", 2, 1, 2);
            cubo[0][0][2].setPieza(auxEsq);
            cubo[0][0][2].getPieza().orientar("B", 0, 0, 2);
            cubo[1][0][2].setPieza(auxAri);
            cubo[1][0][2].getPieza().orientar("B", 1, 0, 2);
        }
    }
    
    //Rotaciones individuales privadas que no estan disponibles para el usuario
    //Estas rotaciones no solo cambian de posicion las piezas, sino tambien los contenedores
    private void rotarContenedoresU(Boolean i){
        if(i){
            Contenedor aux, aux2;
            aux = cubo[0][2][0];
            aux2 = cubo[0][2][1];
            cubo[0][2][1] = cubo[0][1][0];
            cubo[0][2][1].getPieza().orientar("Ui", 0, 2, 1);
            cubo[0][2][0] = cubo[0][0][0];
            cubo[0][2][0].getPieza().orientar("Ui", 0, 2, 0);
            cubo[0][1][0] = cubo[0][0][1];
            cubo[0][1][0].getPieza().orientar("Ui", 0, 1, 0);
            cubo[0][0][0] = cubo[0][0][2];
            cubo[0][0][0].getPieza().orientar("Ui", 0, 0, 0);
            cubo[0][0][1] = cubo[0][1][2];
            cubo[0][0][1].getPieza().orientar("Ui", 0, 0, 1);
            cubo[0][0][2] = cubo[0][2][2];
            cubo[0][0][2].getPieza().orientar("Ui", 0, 0, 2);
            cubo[0][1][2] = aux2;
            cubo[0][1][2].getPieza().orientar("Ui", 0, 1, 2);
            cubo[0][2][2] = aux;
            cubo[0][2][2].getPieza().orientar("Ui", 0, 2, 2);
        }
        else{
            Contenedor aux, aux2;
            aux = cubo[0][0][1];
            aux2 = cubo[0][0][0];
            cubo[0][0][1] = cubo[0][1][0];
            cubo[0][0][1].getPieza().orientar("U", 0, 0, 1);
            cubo[0][0][0] = cubo[0][2][0];
            cubo[0][0][0].getPieza().orientar("U", 0, 0, 0);
            cubo[0][1][0] = cubo[0][2][1];
            cubo[0][1][0].getPieza().orientar("U", 0, 1, 0);
            cubo[0][2][0] = cubo[0][2][2];
            cubo[0][2][0].getPieza().orientar("U", 0, 2, 0);
            cubo[0][2][1] = cubo[0][1][2];
            cubo[0][2][1].getPieza().orientar("U", 0, 2, 1);
            cubo[0][2][2] = cubo[0][0][2];
            cubo[0][2][2].getPieza().orientar("U", 0, 2, 2);
            cubo[0][1][2] = aux;
            cubo[0][1][2].getPieza().orientar("U", 0, 1, 2);
            cubo[0][0][2] = aux2;
            cubo[0][0][2].getPieza().orientar("U", 0, 0, 2);
        }
    }
    
    private void rotarContenedoresD(Boolean i){
        if(i){
            Contenedor aux, aux2;
            aux = cubo[2][0][1];
            aux2 = cubo[2][0][0];
            cubo[2][0][1] = cubo[2][1][0];
            cubo[2][0][1].getPieza().orientar("Di", 2, 0, 1);
            cubo[2][0][0] = cubo[2][2][0];
            cubo[2][0][0].getPieza().orientar("Di", 2, 0, 0);
            cubo[2][1][0] = cubo[2][2][1];
            cubo[2][1][0].getPieza().orientar("Di", 2, 1, 0);
            cubo[2][2][0] = cubo[2][2][2];
            cubo[2][2][0].getPieza().orientar("Di", 2, 2, 0);
            cubo[2][2][1] = cubo[2][1][2];
            cubo[2][2][1].getPieza().orientar("Di", 2, 2, 1);
            cubo[2][2][2] = cubo[2][0][2];
            cubo[2][2][2].getPieza().orientar("Di", 2, 2, 2);
            cubo[2][1][2] = aux;
            cubo[2][1][2].getPieza().orientar("Di", 2, 1, 2);
            cubo[2][0][2] = aux2;
            cubo[2][0][2].getPieza().orientar("Di", 2, 0, 2);
        }
        else{
            Contenedor aux, aux2;
            aux = cubo[2][2][0];
            aux2 = cubo[2][2][1];
            cubo[2][2][1] = cubo[2][1][0];
            cubo[2][2][1].getPieza().orientar("D", 2, 2, 1);
            cubo[2][2][0] = cubo[2][0][0];
            cubo[2][2][0].getPieza().orientar("D", 2, 2, 0);
            cubo[2][1][0] = cubo[2][0][1];
            cubo[2][1][0].getPieza().orientar("D", 2, 1, 0);
            cubo[2][0][0] = cubo[2][0][2];
            cubo[2][0][0].getPieza().orientar("D", 2, 0, 0);
            cubo[2][0][1] = cubo[2][1][2];
            cubo[2][0][1].getPieza().orientar("D", 2, 0, 1);
            cubo[2][0][2] = cubo[2][2][2];
            cubo[2][0][2].getPieza().orientar("D", 2, 0, 2);
            cubo[2][1][2] = aux2;
            cubo[2][1][2].getPieza().orientar("D", 2, 1, 2);
            cubo[2][2][2] = aux;
            cubo[2][2][2].getPieza().orientar("D", 2, 2, 2);
        }
    }
    
    private void rotarContenedoresCentroEjeX(boolean i){
        if(i){
            Contenedor aux, aux2;
            aux = cubo[1][2][0];
            aux2 = cubo[1][2][1];
            cubo[1][2][1] = cubo[1][1][0];
            cubo[1][2][1].getPieza().orientar("CXi", 1, 2, 1);
            cubo[1][2][0] = cubo[1][0][0];
            cubo[1][2][0].getPieza().orientar("CXi", 1, 2, 0);
            cubo[1][1][0] = cubo[1][0][1];
            cubo[1][1][0].getPieza().orientar("CXi", 1, 1, 0);
            cubo[1][0][0] = cubo[1][0][2];
            cubo[1][0][0].getPieza().orientar("CXi", 1, 0, 0);
            cubo[1][0][1] = cubo[1][1][2];
            cubo[1][0][1].getPieza().orientar("CXi", 1, 0, 1);
            cubo[1][0][2] = cubo[1][2][2];
            cubo[1][0][2].getPieza().orientar("CXi", 1, 0, 2);
            cubo[1][1][2] = aux2;
            cubo[1][1][2].getPieza().orientar("CXi", 1, 1, 2);
            cubo[1][2][2] = aux;
            cubo[1][2][2].getPieza().orientar("CXi", 1, 2, 2);
        }
        else{
            Contenedor aux, aux2;
            aux = cubo[1][0][1];
            aux2 = cubo[1][0][0];
            cubo[1][0][1] = cubo[1][1][0];
            cubo[1][0][1].getPieza().orientar("CX", 1, 0, 1);
            cubo[1][0][0] = cubo[1][2][0];
            cubo[1][0][0].getPieza().orientar("CX", 1, 0, 0);
            cubo[1][1][0] = cubo[1][2][1];
            cubo[1][1][0].getPieza().orientar("CX", 1, 1, 0);
            cubo[1][2][0] = cubo[1][2][2];
            cubo[1][2][0].getPieza().orientar("CX", 1, 2, 0);
            cubo[1][2][1] = cubo[1][1][2];
            cubo[1][2][1].getPieza().orientar("CX", 1, 2, 1);
            cubo[1][2][2] = cubo[1][0][2];
            cubo[1][2][2].getPieza().orientar("CX", 1, 2, 2);
            cubo[1][1][2] = aux;
            cubo[1][1][2].getPieza().orientar("CX", 1, 1, 2);
            cubo[1][0][2] = aux2;
            cubo[1][0][2].getPieza().orientar("CX", 1, 0, 2);
        }
        AppContext.getInstance().set("LatEnLat", !((boolean) AppContext.getInstance().get("LatEnLat")));
    }
    
    private void rotarContenedoresL(boolean i){
        if(i){
            Contenedor auxAri, auxEsq;
            auxAri = cubo[0][0][1];
            auxEsq = cubo[0][0][0];
            cubo[0][0][1] = cubo[1][0][0];
            cubo[0][0][0] = cubo[2][0][0];
            cubo[0][0][0].getPieza().orientar("Li", 0, 0, 0);
            cubo[1][0][0] = cubo[2][0][1];
            cubo[2][0][0] = cubo[2][0][2];
            cubo[2][0][0].getPieza().orientar("Li", 2, 0, 0);
            cubo[2][0][1] = cubo[1][0][2];
            cubo[2][0][2] = cubo[0][0][2];
            cubo[2][0][2].getPieza().orientar("Li", 2, 0, 2);
            cubo[1][0][2] = auxAri;
            cubo[0][0][2] = auxEsq;
            cubo[0][0][2].getPieza().orientar("Li", 0, 0, 2);
        }
        else{
            Contenedor auxAri, auxEsq;
            auxAri = cubo[0][0][1];
            auxEsq = cubo[0][0][0];
            cubo[0][0][0] = cubo[0][0][2];
            cubo[0][0][0].getPieza().orientar("L", 0, 0, 0);
            cubo[0][0][1] = cubo[1][0][2];
            cubo[0][0][2] = cubo[2][0][2];
            cubo[0][0][2].getPieza().orientar("L", 0, 0, 2);
            cubo[1][0][2] = cubo[2][0][1];
            cubo[2][0][2] = cubo[2][0][0];
            cubo[2][0][2].getPieza().orientar("L", 2, 0, 2);
            cubo[2][0][1] = cubo[1][0][0];
            cubo[2][0][0] = auxEsq;
            cubo[2][0][0].getPieza().orientar("L", 2, 0, 0);
            cubo[1][0][0] = auxAri;
        }
    }
    
    private void rotarContenedoresR(boolean i){
        if(i){
            Contenedor auxAri, auxEsq;
            auxEsq = cubo[0][2][0];
            auxAri = cubo[0][2][1];
            cubo[0][2][0] = cubo[0][2][2];
            cubo[0][2][0].getPieza().orientar("Ri", 0, 2, 0);
            cubo[0][2][1] = cubo[1][2][2];
            cubo[0][2][2] = cubo[2][2][2];
            cubo[0][2][2].getPieza().orientar("Ri", 0, 2, 2);
            cubo[1][2][2] = cubo[2][2][1];
            cubo[2][2][2] = cubo[2][2][0];
            cubo[2][2][2].getPieza().orientar("Ri", 2, 2, 2);
            cubo[2][2][1] = cubo[1][2][0];
            cubo[2][2][0] = auxEsq;
            cubo[2][2][0].getPieza().orientar("Ri", 2, 2, 0);
            cubo[1][2][0] = auxAri;
        }
        else{
            Contenedor auxAri, auxEsq;
            auxAri = cubo[0][2][1];
            auxEsq = cubo[0][2][0];
            cubo[0][2][1] = cubo[1][2][0];
            cubo[0][2][0] = cubo[2][2][0];
            cubo[0][2][0].getPieza().orientar("R", 0, 2, 0);
            cubo[1][2][0] = cubo[2][2][1];
            cubo[2][2][0] = cubo[2][2][2];
            cubo[2][2][0].getPieza().orientar("R", 2, 2, 0);
            cubo[2][2][1] = cubo[1][2][2];
            cubo[2][2][2] = cubo[0][2][2];
            cubo[2][2][2].getPieza().orientar("R", 2, 2, 2);
            cubo[1][2][2] = auxAri;
            cubo[0][2][2] = auxEsq;
            cubo[0][2][2].getPieza().orientar("R", 0, 2, 2);
        }
    }
    
    private void rotarContenedoresCentroEjeY(boolean i){
        if(i){
            Contenedor auxAri, auxEsq;
            auxAri = cubo[0][1][1];
            auxEsq = cubo[0][1][0];
            cubo[0][1][1] = cubo[1][1][0];
            cubo[0][1][1].getPieza().orientar("CYi", 0, 1, 1);
            cubo[0][1][0] = cubo[2][1][0];
            cubo[0][1][0].getPieza().orientar("CYi", 0, 1, 0);
            cubo[1][1][0] = cubo[2][1][1];
            cubo[1][1][0].getPieza().orientar("CYi", 1, 1, 0);
            cubo[2][1][0] = cubo[2][1][2];
            cubo[2][1][0].getPieza().orientar("CYi", 2, 1, 0);
            cubo[2][1][1] = cubo[1][1][2];
            cubo[2][1][1].getPieza().orientar("CYi", 2, 1, 1);
            cubo[2][1][2] = cubo[0][1][2];
            cubo[2][1][2].getPieza().orientar("CYi", 2, 1, 2);
            cubo[1][1][2] = auxAri;
            cubo[1][1][2].getPieza().orientar("CYi", 1, 1, 2);
            cubo[0][1][2] = auxEsq;
            cubo[0][1][2].getPieza().orientar("CYi", 0, 1, 2);
        }
        else{
            Contenedor auxAri, auxEsq;
            auxAri = cubo[0][1][1];
            auxEsq = cubo[0][1][0];
            cubo[0][1][0] = cubo[0][1][2];
            cubo[0][1][0].getPieza().orientar("CY", 0, 1, 0);
            cubo[0][1][1] = cubo[1][1][2];
            cubo[0][1][1].getPieza().orientar("CY", 0, 1, 1);
            cubo[0][1][2] = cubo[2][1][2];
            cubo[0][1][2].getPieza().orientar("CY", 0, 1, 2);
            cubo[1][1][2] = cubo[2][1][1];
            cubo[1][1][2].getPieza().orientar("CY", 1, 1, 2);
            cubo[2][1][2] = cubo[2][1][0];
            cubo[2][1][2].getPieza().orientar("CY", 2, 1, 2);
            cubo[2][1][1] = cubo[1][1][0];
            cubo[2][1][1].getPieza().orientar("CY", 2, 1, 1);
            cubo[2][1][0] = auxEsq;
            cubo[2][1][0].getPieza().orientar("CY", 2, 1, 0);
            cubo[1][1][0] = auxAri;
            cubo[1][1][0].getPieza().orientar("CY", 1, 1, 0);
        }
    }
    
    private void rotarContenedoresF(boolean i){
        if(i){
            
        } else {
            
        }
    }
    
    private void rotarContenedoresB(boolean i){
        if(i){
            
        } else {
            
        }
    }
    
    private void rotarContenedoresCentroEjeZ(boolean i){
        if(i){
            
        } else {
            
        }
    }
    
    //Rotaciones publicas para mover todo el cubo
    public void rotateY(boolean i){
        rotarContenedoresU(i);
        rotarContenedoresCentroEjeX(i);
        rotarContenedoresD(!i);
    }
    
    public void rotateX(boolean i){
        rotarContenedoresL(!i);
        rotarContenedoresCentroEjeY(!i);
        rotarContenedoresR(i);
    }
    
    public boolean evaluarCuboArmado(){
        boolean estado = true;
        ArrayList<String> list = new ArrayList<>();
        switch(((Centro) encontrarPieza(11)).getPos()){
            case INFERIOR:
                movimientoBasico("X"); movimientoBasico("X");
                Collections.addAll(list, "Xi", "Xi");
                break;
            case IZQUIERDA:
                movimientoBasico("Yi"); movimientoBasico("X");
                Collections.addAll(list, "Xi", "Y");
                break;
            case DERECHA:
                movimientoBasico("Y"); movimientoBasico("X");
                Collections.addAll(list, "Xi", "Yi");
                break;
            case FRONTAL:
                movimientoBasico("X");
                Collections.addAll(list, "Xi");
                break;
            case TRASERA:
                movimientoBasico("Xi");
                Collections.addAll(list, "X");
                break;
        }
        for(Integer z=0; z<3; z++){
            for(Integer x=0; x<3; x++){
                for(Integer y=0; y<3; y++){
                    if(!(cubo[x][y][z].getId().equals(cubo[x][y][z].getPieza().getId())))
                        estado = false;
                    else if(!cubo[x][y][z].getPieza().getOrientacion().equals(1)){
                        estado = false;
                    }
                }
            }
        }
        //System.out.println(estado? "Cubo Armado! c:":"Cubo Desarmado! :c");
        list.stream().forEach(str -> movimientoBasico(str));
        return estado;
    }
    
    public Pieza encontrarPieza(Integer id){
        Pieza p = null;
        for(Integer z=0; z<3; z++){
             for(Integer x=0; x<3; x++){
                 for(Integer y=0; y<3; y++){
                     if(cubo[x][y][z].getPieza().getId().equals(id))
                        p = cubo[x][y][z].getPieza();
                 }
             }
         } 
        return p;
    }

    public RubikG getRubickG() {
        return rubickG;
    }

    public void setRubickG(RubikG rubickG) {
        this.rubickG = rubickG;
    }

    public Contenedor[][][] getCubo() {
        return cubo;
    }

    public void setCubo(Contenedor[][][] cubo) {
        this.cubo = cubo;
    }
    
    public void imprimirSecuencia3D(List<Move> moves){
        //invoca al método que ejecuta la lista de movimientos de la lista acumulada (ArrayList<Moves> de appContextext en la pos 0)
        this.rubickG.doMoveList(moves);
        
        //prueba
        System.out.println("\n--------Listas de movimientos usadas por autoarmado-------------\n");
        Integer cont=0;
        for(Moves ms:this.moveLists){
            if(ms.getNumMoves()!=0){
            System.out.println("\nLista "+cont+"("+ms.getNumMoves()+" movimientos)");
            ms.getMoves().forEach((m) -> {
                System.out.print(m.getFace()+", ");
            });
            }
            cont++;
        }
        
        System.out.println("\nLista general");
        //Integer c=0;
        AppContext.getMoveListGen().getMoves().stream().forEach(m->{
            System.out.print(m.getFace()+",");
        });
        //fin prueba
    }
    
    //Métodos para pruebas logicas
    public void imprimirCubo(){
        for(Integer z=2; z>=0; z--){
            switch(z){
                case 0:
                    System.out.println("Eje Frontal: ");
                    break;
                case 1:
                    System.out.println("Eje Central: ");
                    break;
                case 2:
                    System.out.println("Eje Trasero: ");
                    break;
            }
            System.out.println("\t------------------------------------------------------------");
            for(Integer x=0; x<3; x++){
                String piezas = "\t| ";
                piezas += cubo[x][0][z].getTipoPieza() 
                        + " (C:" + String.valueOf(cubo[x][0][z].getId()) + ")" 
                        + "(ID:" + String.valueOf(cubo[x][0][z].getPieza().getId()) + ")" 
                        + "(O:" + String.valueOf(cubo[x][0][z].getPieza().getOrientacion()) + ")" 
                        + "(POS:" + cubo[x][0][z].getPieza().posToString() + ")" 
                        + " | ";
                piezas += cubo[x][1][z].getTipoPieza() 
                        + " (C:" + String.valueOf(cubo[x][1][z].getId()) + ")" 
                        + "(ID:" + String.valueOf(cubo[x][1][z].getPieza().getId()) + ")" 
                        + "(O:" + String.valueOf(cubo[x][1][z].getPieza().getOrientacion()) + ")" 
                        + "(POS:" + cubo[x][1][z].getPieza().posToString() + ")" 
                        + " | ";
                piezas += cubo[x][2][z].getTipoPieza() 
                        + " (C:" + String.valueOf(cubo[x][2][z].getId()) + ")" 
                        + "(ID:" + String.valueOf(cubo[x][2][z].getPieza().getId()) + ")" 
                        + "(O:" + String.valueOf(cubo[x][2][z].getPieza().getOrientacion()) + ")" 
                        + "(POS:" + cubo[x][2][z].getPieza().posToString() + ")" 
                        + " | ";
                System.out.println(piezas);
                System.out.println("\t------------------------------------------------------------");
            }
        }
        System.out.println("\n");
        evaluarCuboArmado();
    }
}
