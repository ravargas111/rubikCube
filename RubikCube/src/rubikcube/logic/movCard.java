/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rubikcube.logic;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 *
 * @author robri
 */
public class movCard extends HBox{
    private Image img;
    private ImageView iv;
    private String mov;

    public movCard(String mov) {
        super();
        this.mov = mov;
    }

    public String getMov() {
        return mov;
    }

    public void setMov(String mov) {
        this.mov = mov;
    }
    
    public void init(){
        /*img=new Image("rubikcube/resources/icons/movimientos/"+mov+".png");
        iv=new ImageView(img);
        iv.maxHeight(20);
        iv.maxWidth(20);
        iv.prefHeight(20);
        iv.prefHeight(20);
        this.maxHeight(20);
        this.maxWidth(20);
        this.getChildren().add(iv);*/
        Label lbl=new Label(mov);
        lbl.setId("card");
        this.getChildren().add(lbl);
    }
}
