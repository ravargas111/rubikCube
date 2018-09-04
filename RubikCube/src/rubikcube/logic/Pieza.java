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
public class Pieza {
    
    public static enum TipoPieza {CENTRO, ESQUINA, ARISTA};
    
    private Integer id;
    private final String[] subCaras = new String[6];
    private TipoPieza tipoPieza;
    
    public Pieza(Integer id, TipoPieza tp){
        this.id = id;
        this.tipoPieza = tp;
        for(int i = 0; i < 6; i ++){
            this.subCaras[i] = null;
        }
    }

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
    
}
