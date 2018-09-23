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
public class Esquina extends Pieza {
    
    public static enum Position {SupIzqDel, SupDerDel, SupIzqTra, SupDerTra,
                                  InfIzqDel, InfDerDel, InfIzqTra, InfDerTra};
    
    public Esquina(Integer id) {
        super(id, TipoPieza.ESQUINA);
    }
    
    public Position getPos(){
        Position pos = null;
        switch (this.getX()) {
            case 0:
                switch(this.getY()){
                    case 0:
                        switch(this.getZ()){
                            case 0:
                                pos = Position.SupIzqDel;
                                break;
                            case 2:
                                pos = Position.SupIzqTra;
                            break;
                        }
                        break;
                    case 2:
                        switch(this.getZ()){
                            case 0:
                                pos = Position.SupDerDel;
                                break;
                            case 2:
                                pos = Position.SupDerTra;
                            break;
                        }
                        break;
                }
                break;
            case 2:
                switch(this.getY()){
                    case 0:
                        switch(this.getZ()){
                            case 0:
                                pos = Position.InfIzqDel;
                                break;
                            case 2:
                                pos = Position.InfIzqTra;
                            break;
                        }
                        break;
                    case 2:
                        switch(this.getZ()){
                            case 0:
                                pos = Position.InfDerDel;
                                break;
                            case 2:
                                pos = Position.InfDerTra;
                            break;
                        }
                        break;
                }
                break;
        }
        return pos;
    }

    @Override
    public void orientL(boolean i) {
        switch(getPos()){
            case SupIzqDel:
                switch(this.getOrientacion()){
                    case 1:
                        if(i)
                            this.setOrientacion(3);
                        else
                            this.setOrientacion(2);
                        break;
                    case 2:
                        if(i)
                            this.setOrientacion(1);
                        else
                            this.setOrientacion(3);
                        break;
                    case 3:
                        if(i)
                            this.setOrientacion(2);
                        else
                            this.setOrientacion(1);
                        break;
                }
                break;
            case SupIzqTra:
                switch(this.getOrientacion()){
                    case 1:
                        if(i)
                            this.setOrientacion(1);
                        else
                            this.setOrientacion(2);
                        break;
                    case 2:
                        if(i)
                            this.setOrientacion(2);
                        else
                            this.setOrientacion(3);
                        break;
                    case 3:
                        if(i)
                            this.setOrientacion(3);
                        else
                            this.setOrientacion(1);
                        break;
                }
                break;
            case InfIzqDel:
                switch(this.getOrientacion()){
                    case 1:
                        if(i)
                            this.setOrientacion(3);
                        else
                            this.setOrientacion(2);
                        break;
                    case 2:
                        if(i)
                            this.setOrientacion(1);
                        else
                            this.setOrientacion(3);
                        break;
                    case 3:
                        if(i)
                            this.setOrientacion(2);
                        else
                            this.setOrientacion(1);
                        break;
                }
                break;
            case InfIzqTra:
                switch(this.getOrientacion()){
                    case 1:
                        if(i)
                            this.setOrientacion(3);
                        else
                            this.setOrientacion(1);
                        break;
                    case 2:
                        if(i)
                            this.setOrientacion(1);
                        else
                            this.setOrientacion(2);
                        break;
                    case 3:
                        if(i)
                            this.setOrientacion(2);
                        else
                            this.setOrientacion(3);
                        break;
                }
                break;
        }
    }

    @Override
    public void orientR(boolean i) {
        switch(getPos()){
            case SupDerDel:
                switch(this.getOrientacion()){
                    case 1:
                        if(i)
                            this.setOrientacion(1);
                        else
                            this.setOrientacion(2);
                        break;
                    case 2:
                        if(i)
                            this.setOrientacion(2);
                        else
                            this.setOrientacion(3);
                        break;
                    case 3:
                        if(i)
                            this.setOrientacion(3);
                        else
                            this.setOrientacion(1);
                        break;
                }
                break;
            case SupDerTra:
                switch(this.getOrientacion()){
                    case 1:
                        if(i)
                            this.setOrientacion(3);
                        else
                            this.setOrientacion(2);
                        break;
                    case 2:
                        if(i)
                            this.setOrientacion(1);
                        else
                            this.setOrientacion(3);
                        break;
                    case 3:
                        if(i)
                            this.setOrientacion(2);
                        else
                            this.setOrientacion(1);
                        break;
                }
                break;
            case InfDerDel:
                switch(this.getOrientacion()){
                    case 1:
                        if(i)
                            this.setOrientacion(3);
                        else
                            this.setOrientacion(1);
                        break;
                    case 2:
                        if(i)
                            this.setOrientacion(1);
                        else
                            this.setOrientacion(2);
                        break;
                    case 3:
                        if(i)
                            this.setOrientacion(2);
                        else
                            this.setOrientacion(3);
                        break;
                }
                break;
            case InfDerTra:
                switch(this.getOrientacion()){
                    case 1:
                        if(i)
                            this.setOrientacion(3);
                        else
                            this.setOrientacion(2);
                        break;
                    case 2:
                        if(i)
                            this.setOrientacion(1);
                        else
                            this.setOrientacion(3);
                        break;
                    case 3:
                        if(i)
                            this.setOrientacion(2);
                        else
                            this.setOrientacion(1);
                        break;
                }
                break;
        }
    }

    @Override
    public void orientF(boolean i) {
        switch(getPos()){
            case SupIzqDel:
                switch(this.getOrientacion()){
                    case 1:
                        if(i)
                            this.setOrientacion(1);
                        else
                            this.setOrientacion(2);
                        break;
                    case 2:
                        if(i)
                            this.setOrientacion(2);
                        else
                            this.setOrientacion(3);
                        break;
                    case 3:
                        if(i)
                            this.setOrientacion(3);
                        else
                            this.setOrientacion(1);
                        break;
                }
                break;
            case SupDerDel:
                switch(this.getOrientacion()){
                    case 1:
                        if(i)
                            this.setOrientacion(3);
                        else
                            this.setOrientacion(2);
                        break;
                    case 2:
                        if(i)
                            this.setOrientacion(1);
                        else
                            this.setOrientacion(3);
                        break;
                    case 3:
                        if(i)
                            this.setOrientacion(2);
                        else
                            this.setOrientacion(1);
                        break;
                }
                break;
            case InfIzqDel:
                switch(this.getOrientacion()){
                    case 1:
                        if(i)
                            this.setOrientacion(3);
                        else
                            this.setOrientacion(1);
                        break;
                    case 2:
                        if(i)
                            this.setOrientacion(1);
                        else
                            this.setOrientacion(2);
                        break;
                    case 3:
                        if(i)
                            this.setOrientacion(2);
                        else
                            this.setOrientacion(3);
                        break;
                }
                break;
            case InfDerDel:
                switch(this.getOrientacion()){
                    case 1:
                        if(i)
                            this.setOrientacion(3);
                        else
                            this.setOrientacion(2);
                        break;
                    case 2:
                        if(i)
                            this.setOrientacion(1);
                        else
                            this.setOrientacion(3);
                        break;
                    case 3:
                        if(i)
                            this.setOrientacion(2);
                        else
                            this.setOrientacion(1);
                        break;
                }
                break;
        }
    }

    @Override
    public void orientB(boolean i) {
        switch(getPos()){
            case SupIzqTra:
                switch(this.getOrientacion()){
                    case 1:
                        if(i)
                            this.setOrientacion(3);
                        else
                            this.setOrientacion(2);
                        break;
                    case 2:
                        if(i)
                            this.setOrientacion(1);
                        else
                            this.setOrientacion(3);
                        break;
                    case 3:
                        if(i)
                            this.setOrientacion(2);
                        else
                            this.setOrientacion(1);
                        break;
                }
                break;
            case SupDerTra:
                switch(this.getOrientacion()){
                    case 1:
                        if(i)
                            this.setOrientacion(1);
                        else
                            this.setOrientacion(2);
                        break;
                    case 2:
                        if(i)
                            this.setOrientacion(2);
                        else
                            this.setOrientacion(3);
                        break;
                    case 3:
                        if(i)
                            this.setOrientacion(3);
                        else
                            this.setOrientacion(1);
                        break;
                }
                break;
            case InfIzqTra:
                switch(this.getOrientacion()){
                    case 1:
                        if(i)
                            this.setOrientacion(3);
                        else
                            this.setOrientacion(2);
                        break;
                    case 2:
                        if(i)
                            this.setOrientacion(1);
                        else
                            this.setOrientacion(3);
                        break;
                    case 3:
                        if(i)
                            this.setOrientacion(2);
                        else
                            this.setOrientacion(1);
                        break;
                }
                break;
            case InfDerTra:
                switch(this.getOrientacion()){
                    case 1:
                        if(i)
                            this.setOrientacion(3);
                        else
                            this.setOrientacion(1);
                        break;
                    case 2:
                        if(i)
                            this.setOrientacion(1);
                        else
                            this.setOrientacion(2);
                        break;
                    case 3:
                        if(i)
                            this.setOrientacion(2);
                        else
                            this.setOrientacion(3);
                        break;
                }
                break;
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
