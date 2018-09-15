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
public class Centro extends Pieza{
    
    public Centro(Integer id) {
        super(id, TipoPieza.CENTRO);
    }

    @Override
    public void orientL(boolean i) {
        //La pieza no cambia de orientacion en este movimiento
    }

    @Override
    public void orientR(boolean i) {
        //La pieza no cambia de orientacion en este movimiento
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
