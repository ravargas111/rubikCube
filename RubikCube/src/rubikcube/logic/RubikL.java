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

    //recibe un cubo gráfico para hacer las rotaciones
    public RubikL(RubikG rubickG) {
        this.rubickG = rubickG;
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
    
}
