package circuitly;

/**
 * This updates the button state and also updates the LED
 */

import java.util.ArrayList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class outputButtons {
    double[] xAxis = new double[]{735, 770, 805, 840, 875, 910, 945, 980};
    Circle[] updated = new Circle[8];
    public Circle buttonSpecs(int i){
        Circle circ = new Circle();
        circ.setRadius(10);
        circ.setCenterX(735 + (i * 35));
        circ.setCenterY(600);
        circ.setFill(Color.DARKRED);
        
        return circ;
    }

// find wire connected to the button    
    public void getMatchingWire(ArrayList <stateTracker.columnCreationPower> power, 
            ArrayList <stateTracker.columnCreationGround> ground, 
            ArrayList <stateTracker.columnCreationGroupOne> groupOne, 
            ArrayList <stateTracker.columnCreationGroupTwo> groupTwo, ArrayList <Line> createWireList, ArrayList <stateTracker.columnCreationButtons> button,
            ArrayList <stateTracker.columnCreationOutputs> outs, Circle[] outputLed){
        
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < createWireList.size(); j++){
                if((createWireList.get(j).getStartX()) == xAxis[i] || (createWireList.get(j).getEndX()) == xAxis[i]){
                    double startY = createWireList.get(j).getStartY();
                    double startX = createWireList.get(j).getStartX();
                    double endY = createWireList.get(j).getEndY();
                    double endX = createWireList.get(j).getEndX();
                    double location = xAxis[i];
                    
                    if(startY == 105|| endY == 105){
                        for(int l = 0; l < 65; l++){
                            if(startX ==  (power.get(l).getX() + 5) || endX ==  (power.get(l).getX() + 5)){
                                int stateToUpdate = power.get(l).getState();
                                for(int k = 0; k < 8; k++){
                                    double checks = outs.get(k).getX() + 5;
                                    if(checks == location){
                                        updateLed(stateToUpdate, outs.get(k).getX(), outputLed);
                                    }
                                }
                                l = 65;
                            }
                        }
                        
                        
                    }
                    else if(startY == 120|| endY == 120){
                        for(int l = 0; l < 65; l++){
                            if(startX ==  (ground.get(l).getX() + 5) || endX ==  (ground.get(l).getX() + 5)){
                                int stateToUpdate = ground.get(l).getState();
                                for(int k = 0; k < 8; k++){
                                    double checks = outs.get(k).getX() + 5;
                                    if(checks == location){
                                        updateLed(stateToUpdate, outs.get(k).getX(), outputLed);
                                    }
                                }
                                l = 65;
                            }
                        }
                    }
                    else if((startY >= 155 && startY <= 215) || (endY >= 155 && endY <= 215)){
                        for(int l = 0; l < 65; l++){
                            if(startX ==  (groupOne.get(l).getX() + 5) || endX ==  (groupOne.get(l).getX() + 5)){
                                int stateToUpdate = groupOne.get(l).getState();
                                for(int k = 0; k < 8; k++){
                                    double checks = outs.get(k).getX() + 5;
                                    if(checks == location){
                                        updateLed(stateToUpdate, outs.get(k).getX(), outputLed);
                                    }
                                }
                                l = 65;
                            }
                        }
                    }
                    else if((startY >= 245 && startY <= 305) || (endY >= 245 && endY <= 305)){
                        for(int l = 0; l < 65; l++){
                            if(startX ==  (groupTwo.get(l).getX() + 5) || endX ==  (groupTwo.get(l).getX() + 5)){
                                int stateToUpdate = groupTwo.get(l).getState();
                                for(int k = 0; k < 8; k++){
                                    double checks = outs.get(k).getX() + 5;
                                    if(checks == location){
                                        updateLed(stateToUpdate, outs.get(k).getX(), outputLed);
                                    }
                                }
                                l = 65;
                            }
                        }
                    }
                    else if((startY >= 40 && startY <= 74) || (endY >= 40 && endY <= 74)){
                        for(int l = 0; l < 8; l++){
                            if(startX == (button.get(l).getX() + 5) || endX ==  (button.get(l).getX() + 5)){
                                int stateToUpdate = button.get(l).getState();
                                System.out.println("State: " + stateToUpdate);
                                for(int k = 0; k < 8; k++){
                                    double checks = outs.get(k).getX() + 5;
                                    if(checks == location){
                                        updateLed(stateToUpdate, outs.get(k).getX(), outputLed);
                                    }
                                }
                                l = 8;
                            }
                        }
                    }
                    else{
                        System.out.println("BLAH");
                    }
                }
            }
        }
        
    }

// set LEDs to the correct state    
    public void updateLed(int state, double location, Circle[] outputLed){
        updated = outputLed;
        double locationNew = location + 5;
        for(int i = 0; i < outputLed.length; i++){
            if(locationNew == updated[i].getCenterX()){
                if(state == 1){
                    outputLed[i].setFill(Color.RED);
                }
                else{
                    outputLed[i].setFill(Color.DARKRED);
                }
            }
        }
    }
// return LED state    
    public Circle[] returnUpdatedLed(){
        return updated;
    }
// updates with the chip   
    public Circle[] updateWithChip(ArrayList <stateTracker.columnCreationOutputs> outs, Circle[] outputLed){
        updated = outputLed;
        
        for(int i = 0; i < 8; i++){
            double location = xAxis[i];
                if((location + 5) == updated[i].getCenterX()){
            }
        }
    return updated;
    }
}
