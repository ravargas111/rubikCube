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
    
    public static enum Position {SupDel, SupTra, SupIzq, SupDer,
                                 CenDelIzq, CenDelDer, CenTraIzq, CenTraDer,
                                 InfDel, InfTra, InfIzq, InfDer};
    
    public Arista(Integer id) {
        super(id, TipoPieza.ARISTA);
    }
    
    public Position getPos2(){
        //Nivel Superior
        if(this.getX().equals(0) && this.getY().equals(1) && this.getZ().equals(0)) return Position.SupDel;
        else if(this.getX().equals(0) && this.getY().equals(1) && this.getZ().equals(2)) return Position.SupTra;
        else if(this.getX().equals(0) && this.getY().equals(0) && this.getZ().equals(1)) return Position.SupIzq;
        else if(this.getX().equals(0) && this.getY().equals(2) && this.getZ().equals(1)) return Position.SupDer;
        //Nivel Central
        else if(this.getX().equals(1) && this.getY().equals(0) && this.getZ().equals(0)) return Position.CenDelIzq;
        else if(this.getX().equals(1) && this.getY().equals(0) && this.getZ().equals(2)) return Position.CenTraIzq;
        else if(this.getX().equals(1) && this.getY().equals(2) && this.getZ().equals(0)) return Position.CenDelDer;
        else if(this.getX().equals(1) && this.getY().equals(2) && this.getZ().equals(2)) return Position.CenTraDer;
        //Nivel Inferior
        else if(this.getX().equals(2) && this.getY().equals(1) && this.getZ().equals(0)) return Position.InfDel;
        else if(this.getX().equals(2) && this.getY().equals(1) && this.getZ().equals(2)) return Position.InfTra;
        else if(this.getX().equals(2) && this.getY().equals(0) && this.getZ().equals(1)) return Position.InfIzq;
        else if(this.getX().equals(2) && this.getY().equals(2) && this.getZ().equals(1)) return Position.InfDer;
        else return null;
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
