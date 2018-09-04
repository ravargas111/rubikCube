/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rubikcube.logic;

import rubikcube.model.RubikG;

/**
 *
 * @author robri
 */
public class RubikL {
    private RubikG rubickG;
    private Contenedor[][][] cubo;

    //recibe un cubo gráfico para hacer las rotaciones
    public RubikL(RubikG rubickG) {
        this.rubickG = rubickG;
        cubo = new Contenedor[3][3][3];
        Integer contador = 1;
        for(Integer z=0; z<3; z++){
            for(Integer x=0; x<3; x++){
                for(Integer y=0; y<3; y++){
                    Pieza p;
                    //Centro del cubo
                    if(x==1&&y==1&&z==1){
                        p = new Pieza(999, Pieza.TipoPieza.CENTRO);
                    }
                    // Esquinas
                    else if((x!=1) && (z!=1) && (y!=1)){
                        p = new Pieza(contador, Pieza.TipoPieza.ESQUINA);
                    }
                    //Centros
                    else if(((x!=1)&&y==1&&z==1) || ((y!=1)&&x==1&&z==1) || ((z!=1)&&x==1&&y==1)){
                        p = new Pieza(contador, Pieza.TipoPieza.CENTRO);
                    }
                    //Aristas
                    else {
                        p = new Pieza(contador, Pieza.TipoPieza.ARISTA);
                    }
                    cubo[x][y][z] = new Contenedor(p);
                    contador++;
                }
            }
        }
    }
    
    //usar este metiendo por parámetros el movimiento,porque creo en la parte gráfica generaría doble movimiento 
    /**
     * llama a la rotacíon del cubo gráfico (y esta llama a la rotación del lógico: "rotateFaceL")
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
    public void rotateFaceL(String face){
        if(face.contains("L")||face.contains("R")||face.contains("U")||face.contains("D")||face.contains("F")||face.contains("B"))
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
           }
    }
    
    public void rotateL(Boolean i){
        if(i){
            
        }
        else{
            
        }
    }
    
    public void rotateR(Boolean i){
        if(i){
            
        }
        else{
            
        }
    }
    
    public void rotateU(Boolean i){
        if(i){
            
        }
        else{
            Pieza aux, aux2;
            aux = cubo[0][2][0].getPieza();
            aux2 = cubo[0][2][1].getPieza();
            cubo[0][2][1].setPieza(cubo[0][1][0].getPieza());
            cubo[0][2][0].setPieza(cubo[0][0][0].getPieza());
            cubo[0][1][0].setPieza(cubo[0][0][1].getPieza());
            cubo[0][0][0].setPieza(cubo[0][0][2].getPieza());
            cubo[0][0][1].setPieza(cubo[0][1][2].getPieza());
            cubo[0][0][2].setPieza(cubo[0][2][2].getPieza());
            cubo[0][1][2].setPieza(aux2);
            cubo[0][2][2].setPieza(aux);
            System.out.println("Movimiento U lógico");
        }
    }
    
    public void rotateD(Boolean i){
        if(i){
            
        }
        else{
            
        }
    }
    
    public void rotateF(Boolean i){
        if(i){
            
        }
        else{
            
            
        }
    }
    
    public void rotateB(Boolean i){
        if(i){
            
        }
        else{
            
        }
    }
    
    public void evaluarCuboArmado(){
        boolean estado = true;
        for(Integer z=0; z<3; z++){
            for(Integer x=0; x<3; x++){
                for(Integer y=0; y<3; y++){
                    if(!cubo[x][y][z].getId().equals(cubo[x][y][z].getPieza().getId()))
                        estado = false;
                }
            }
        }
        System.out.println(estado? "Cubo Armado! c:":"Cubo Desarmado! :c");
    }
    
    //Metodos para pruebas logicas
    public void imprimirCubo(){
        for(Integer z=0; z<3; z++){
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
                        + " (" + String.valueOf(cubo[x][0][z].getId()) + ")" 
                        + "(" + String.valueOf(cubo[x][0][z].getPieza().getId()) + ") " 
                        + " | ";
                piezas += cubo[x][1][z].getTipoPieza() 
                        + " (" + String.valueOf(cubo[x][1][z].getId()) + ")" 
                        + "(" + String.valueOf(cubo[x][1][z].getPieza().getId()) + ") " 
                        + " | ";
                piezas += cubo[x][2][z].getTipoPieza() 
                        + " (" + String.valueOf(cubo[x][2][z].getId()) + ")" 
                        + "(" + String.valueOf(cubo[x][2][z].getPieza().getId()) + ") " 
                        + " | ";
                System.out.println(piezas);
                System.out.println("\t------------------------------------------------------------");
            }
        }
        System.out.println("\n");
        evaluarCuboArmado();
    }
}
