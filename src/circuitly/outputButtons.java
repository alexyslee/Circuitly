package circuitly;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class outputButtons {
    
    public Circle buttonSpecs(int i){
        Circle[] inputs = new Circle[8];
    
        Circle circ = new Circle();
        circ.setRadius(10);
        circ.setCenterX(1275);
        circ.setCenterY(300 + (35 * i));
        circ.setFill(Color.GREY);
        
        return circ;
    }
    
}
