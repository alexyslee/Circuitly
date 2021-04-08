package circuitly;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class inputButtons {
    Circle[] inputs = new Circle[8];
    boolean buttonStatus = false;
    int state;
    
    public Circle buttonSpecs(int i){
        Circle circ = new Circle();
        circ.setRadius(8);
        circ.setCenterX(500 + (i * 35));
        circ.setCenterY(20);
        circ.setFill(Color.GREY);
        
        return circ;
    }
    
    public int getState(){
        return state;
    }
    
    public void setState(){
        if(state == 0){
            state = 5;
        }
        else
            state = 0;
    }
}
