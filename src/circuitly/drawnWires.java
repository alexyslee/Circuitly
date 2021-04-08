package circuitly;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import circuitly.CircuitTest;

public class drawnWires {
    public int holesClicked; 
    
    
    private double[] first = new double[2];
    private double[] second = new double[2];
    public double[] lineArray = new double[4];
 
//  setting the color of the hole clicked as well as getting coordinates    
    public void clickedHole(Rectangle changed, int timesClicked){
        holesClicked = timesClicked;
        changed.setFill(Color.BLUE);
        
        if(timesClicked == 1){
            first[0] = changed.getX();
            first[1] = changed.getY();
        }
        
        else{
            second[0] = changed.getX();
            second[1] = changed.getY();
        }
    }
    
    public void createLineArray(double[] first, double[] second){
        lineArray[0] = first[0];
        lineArray[1] = second[0];
        lineArray[2] = first[1];
        lineArray[3] = second[1];
    }
    
    public double[] returnFirst(){
        return first;
    }
    
    public double[] returnSecond(){
        return second;
    }
    
    public double[] getLineArray(){
        return lineArray;
    }
 
    
    
    
        
}
    
