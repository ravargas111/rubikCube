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
    public static enum Position {SUPERIOR, INFERIOR, FRONTAL, TRASERA, IZQUIERDA, DERECHA};
    
    public Centro(Integer id) {
        super(id, TipoPieza.CENTRO);
    }
    
    public Position getPos(){
        Position pos;
        switch (this.getX()) {
            case 1:
                switch (this.getY()) {
                    case 0:
                        pos = Position.IZQUIERDA;
                        break;
                    case 2:
                        pos = Position.DERECHA;
                        break;
                    default:
                        if(this.getZ().equals(0)){
                            pos = Position.FRONTAL;
                        } else {
                            pos = Position.TRASERA;
                        }   break;
                }   break;
            case 0:
                pos = Position.SUPERIOR;
                break;
            default:
                pos = Position.INFERIOR;
                break;
        }
        return pos;
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
    
    @Override
    public void orientCX(boolean i) {
        //La pieza no cambia de orientacion en este movimiento
    }

    @Override
    public void orientCY(boolean i) {
        //La pieza no cambia de orientacion en este movimiento
    }

    @Override
    public void orientCZ(boolean i) {
        //La pieza no cambia de orientacion en este movimiento
    }
    
}
