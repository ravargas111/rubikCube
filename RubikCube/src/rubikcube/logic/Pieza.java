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
public abstract class Pieza {
    
    public static enum TipoPieza {CENTRO, ESQUINA, ARISTA};
    
    private Integer id;
    private final String[] subCaras = new String[6];
    private TipoPieza tipoPieza;
    private Integer orientacion;
    private Integer X;
    private Integer Y;
    private Integer Z;
    
    public Pieza(Integer id, TipoPieza tp){
        this.id = id;
        this.tipoPieza = tp;
        this.orientacion = 1;
        for(int i = 0; i < 6; i ++){
            this.subCaras[i] = null;
        }
    }
    
    public void setPos(int x, int y, int z){
        this.X = x;
        this.Y = y;
        this.Z = z;
    }
    
    public void orientar(String face, int newX, int newY, int newZ){
        switch(face){
            case "Li": orientL(true);break;
            case "L": orientL(false);break;
            case "Ri": orientR(true);break;
            case "R": orientR(false);break;
            case "Ui": orientU(true);break;
            case "U": orientU(false);break;
            case "Di": orientD(true);break;
            case "D": orientD(false);break;
            case "Fi": orientF(true);break;
            case "F": orientF(false);break;
            case "Bi": orientB(true);break;
            case "B": orientB(false);break;
            case "CXi": orientCX(true);break;
            case "CX": orientCX(false);break;
            case "CYi": orientCX(true);break;
            case "CY": orientCX(false);break;
            case "CZi": orientCX(true);break;
            case "CZ": orientCX(false);break;
        }
        setPos(newX, newY, newZ);
    }
    
    public abstract void orientL(boolean i);
    
    public abstract void orientR(boolean i);
    
    public abstract void orientU(boolean i);
    
    public abstract void orientD(boolean i);
    
    public abstract void orientF(boolean i);
    
    public abstract void orientB(boolean i);
    
    public abstract void orientCX(boolean i);
    
    public abstract void orientCY(boolean i);
    
    public abstract void orientCZ(boolean i);

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String[] getSubCaras() {
        return subCaras;
    }

    public TipoPieza getTipoPieza() {
        return tipoPieza;
    }

    public void setTipoPieza(TipoPieza tipoPieza) {
        this.tipoPieza = tipoPieza;
    }

    public Integer getOrientacion() {
        return orientacion;
    }

    public void setOrientacion(Integer orientacion) {
        this.orientacion = orientacion;
    }

    public Integer getX() {
        return X;
    }

    public void setX(Integer X) {
        this.X = X;
    }

    public Integer getY() {
        return Y;
    }

    public void setY(Integer Y) {
        this.Y = Y;
    }

    public Integer getZ() {
        return Z;
    }

    public void setZ(Integer Z) {
        this.Z = Z;
    }
    
    public String posToString(){
        return "X-" + this.X + ", Y-" + this.Y + ", Z-" + this.Z;
    }
    
}
