package rubikcube;

import javafx.application.Application;
import javafx.stage.Stage;
import rubikcube.util.FlowController;

/**
 *
 * @author jpereda, April 2014 - @JPeredaDnr
 */
public class RubikCube extends Application {
      
    @Override
    public void start(Stage stage) {
        FlowController.getInstance().InitializeFlow(stage, null);
        stage.setTitle("Cubo Rubik");
        FlowController.getInstance().goMain();
        FlowController.getInstance().goView("Inicio");
        }
    
        public static void main(String[] args) {
        launch(args);
    }
}
