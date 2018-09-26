/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rubikcube.logic;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/**
 *
 * @author Chris
 */
public class RankingCard extends Pane{
    
    private Integer posicion;
    private String nombre;
    private String valor;
    private String unidad;
    
    public RankingCard(Integer posicion, String nombre, String valor, String unidad){
        this.nombre = nombre;
        this.valor = valor;
        this.posicion = posicion;
        this.unidad = unidad;
        initCard();
    }
    
    private void initCard(){
        this.setPrefSize(320, 50);
        this.setStyle("-fx-background-color: red");
        this.getChildren().add(initPosLbl());
        this.getChildren().add(initNameLbl());
        this.getChildren().add(initPointsLbl());
    }
    
    private Label initPosLbl(){
        Label posLbl = new Label();
        posLbl.setPrefSize(50, 50);
        posLbl.setLayoutX(0);
        posLbl.setText("#" + String.valueOf(posicion));
        return posLbl;
    }
    
    private Label initNameLbl(){
        Label nameLbl = new Label();
        nameLbl.setPrefSize(180, 50);
        nameLbl.setLayoutX(50);
        nameLbl.setText(nombre);
        return nameLbl;
    }
    
    private Label initPointsLbl(){
        Label nameLbl = new Label();
        nameLbl.setPrefSize(70, 50);
        nameLbl.setLayoutX(230);
        nameLbl.setText(valor + unidad);
        return nameLbl;
    }

    public Integer getPosicion() {
        return posicion;
    }

    public void setPosicion(Integer posicion) {
        this.posicion = posicion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }
    
}
