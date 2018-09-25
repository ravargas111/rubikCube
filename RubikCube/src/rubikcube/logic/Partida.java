/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rubikcube.logic;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author robri
 */
public class Partida implements Serializable{
    public static enum ModoJuego {ORDENADO,DESORDENADO,ASISTIDO,CARGADO};
    private Integer cantMovs;
    private ArrayList<String> listaMovs;
    private String listaMovsScramble;

    public Partida(ModoJuego modo,ArrayList<String> listaMovs) {
        this.listaMovs = listaMovs;
        this.cantMovs = this.listaMovs.size();
    }

    public Integer getCantMovs() {
        return cantMovs;
    }

    public void setCantMovs(Integer cantMovs) {
        this.cantMovs = cantMovs;
    }

    public ArrayList<String> getListaMovs() {
        return listaMovs;
    }

    public void setListaMovs(ArrayList<String> listaMovs) {
        this.listaMovs = listaMovs;
    }

    public String getListaMovsScramble() {
        return listaMovsScramble;
    }

    public void setListaMovsScramble(String listaMovsScramble) {
        this.listaMovsScramble = listaMovsScramble;
    }
    
    public void guardarPartida(){    
        Persistencia.guardarPartida(this);
            
    }
    
    public String getMovimientos(){
        StringBuilder sb=new StringBuilder();
        this.listaMovs.stream().forEach(str -> {sb.append(str).append(" ");});
        return sb.toString().trim();
    }
    
    /*public String getMovimientosScramble(){
        StringBuilder sb=new StringBuilder();
        this.listaMovsScramble.stream().forEach(str -> {sb.append(str).append(" ");});
        return sb.toString().trim();
    }*/
    
}
