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
public class Contenedor {
    private Integer id;
    private Pieza pieza;
    private Pieza.TipoPieza tipoPieza;
    
    public Contenedor(Pieza p){
        if(p!=null){
            this.id = p.getId();
            this.pieza = p;
            tipoPieza = p.getTipoPieza();
        } else {
            this.id = null;
            this.pieza = null;
            tipoPieza = null;
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Pieza getPieza() {
        return pieza;
    }

    public void setPieza(Pieza pieza) {
        this.pieza = pieza;
    }

    public Pieza.TipoPieza getTipoPieza() {
        return tipoPieza;
    }

    public void setTipoPieza(Pieza.TipoPieza tipoPieza) {
        this.tipoPieza = tipoPieza;
    }
    
    
}
