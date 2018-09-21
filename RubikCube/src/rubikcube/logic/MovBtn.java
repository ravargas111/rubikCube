/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rubikcube.logic;

import com.jfoenix.controls.JFXButton;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author robri
 */
public class MovBtn extends JFXButton{
    
    public MovBtn() {
    }

    public MovBtn(String text) {
        super(text);
        Image img=new Image("/rubikcube/resources/icons/movimientos/D.png");
        ImageView iv=new ImageView();
        iv.prefHeight(10);
        iv.prefWidth(10);
        iv.setImage(img);
        this.setGraphic(iv);
        
    }

    public MovBtn(String text, Node graphic) {
        super(text, graphic);
    }
     
}
