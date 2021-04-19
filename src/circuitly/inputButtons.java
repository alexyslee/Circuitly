package circuitly;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class inputButtons {
    Circle[] outputs = new Circle[8];
    boolean buttonStatus = false;
    int state = 0;
    
    public Circle buttonSpecs(int i){
        Circle circ = new Circle();
        circ.setRadius(10);
        circ.setCenterX(740 + (i * 35));
        circ.setCenterY(25);
        circ.setFill(Color.GREY);
        
        return circ;
    }
    
    public int getState(){
        return state;
    }
    
    public int setState(){
        if(state == 0){
            state = 1;
        }
        else
            state = 0;
        
        return state;
    }
}
