/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rubikcube.logic;

/**
 *
 * @author robri
 */
public class Partida {
    private Integer puntos;
    private Double tiempo;
    private Integer movimientos;

    public Partida(Integer puntos, Double tiempo, Integer movimientos) {
        this.puntos = puntos;
        this.tiempo = tiempo;
        this.movimientos = movimientos;
    }

    public Partida() {
    }

    public Integer getPuntos() {
        return puntos;
    }

    public void setPuntos(Integer puntos) {
        this.puntos = puntos;
    }

    public Double getTiempo() {
        return tiempo;
    }

    public void setTiempo(Double tiempo) {
        this.tiempo = tiempo;
    }

    public Integer getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(Integer movimientos) {
        this.movimientos = movimientos;
    }
    
    
}
