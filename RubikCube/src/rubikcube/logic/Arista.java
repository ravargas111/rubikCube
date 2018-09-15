/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rubikcube.logic;

/**
 *
 * @author Chris
 */
public class Arista extends Pieza{
    
    public Arista(Integer id) {
        super(id, TipoPieza.ARISTA);
    }

    @Override
    public void orientL(boolean i) {
        if(this.getOrientacion().equals(1)){
            this.setOrientacion(2);
        } else {
            this.setOrientacion(1);
        }
    }

    @Override
    public void orientR(boolean i) {
        if(this.getOrientacion().equals(1)){
            this.setOrientacion(2);
        } else {
            this.setOrientacion(1);
        }
    }

    @Override
    public void orientU(boolean i) {
        //La pieza no cambia de orientacion en este movimiento
    }

    @Override
    public void orientD(boolean i) {
        //La pieza no cambia de orientacion en este movimiento
    }

    @Override
    public void orientF(boolean i) {
        //La pieza no cambia de orientacion en este movimiento
    }

    @Override
    public void orientB(boolean i) {
        //La pieza no cambia de orientacion en este movimiento
    }
    
}
