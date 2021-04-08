package circuitly;

import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class eventHandling {
    private int timesClicked = 0;
    private double startX, startY, endX, endY, currentX, currentY;
    private double[] first = new double[2];
    private double[] second = new double[2];
    private double[] start = new double[2];
    private double[] currentLineArray = new double[4];
    private Line currentLine, liveWire;
    private int[] xCoordinatesGroupOne = new int[65];
    private int[] yCoordinatesGroupOne = new int[]{150,165,180,195,210};
    Rectangle newChip, passedInBHoles;
    int countOfChips;
    ArrayList <Rectangle> createdChipList = new ArrayList<>();
    ArrayList <Line> createdWireList = new ArrayList<>();
    drawnWires drawn = new drawnWires();
    
//  this is making the breadboard holes able to be selected by the wire    
    public void pickingBreadboardHoles(Rectangle breadboardHoles, boolean isViewToggled){
        drawnWires drawn = new drawnWires();
                timesClicked++;
                passedInBHoles = breadboardHoles;
                drawn.clickedHole(breadboardHoles, timesClicked);

                if(timesClicked == 1){
                    first = drawn.returnFirst();
                    
                    if(isViewToggled == true){
                        
                        startX = first[0];
                        startY = first[1];
                        liveWire = drawLine(startX, startY, startX, startY);
                    }
                }
                else if(timesClicked == 2){
                    drawn.clickedHole(breadboardHoles, timesClicked);
                    second = drawn.returnSecond();
                    
                    if(isViewToggled == true){
                        endX = second[0];
                        endY = second[1];
                        timesClicked = 0;
                    }
                }
                if(timesClicked == 2){
                    drawn.createLineArray(first, second);
                    currentLineArray = drawn.getLineArray();
                }
        }

//  this is drawing the wire based on the breadboard hole selected when point view is selected 
    public void drawingWires(){
        while(timesClicked == 2){
            currentLine = drawLine(currentLineArray[0], currentLineArray[2], currentLineArray[1], currentLineArray[3]);
            timesClicked = 0;
        }
    }
    
// this is for moving the endpoint of already drawn wire with Left Click
    public void movingEndpoint(){
    }

// lines up chip with X coordinate of breadboard holes
    public int lineUpChipX(int x){
        createXCoordinates();
        
        if (x % 5 == 0){
        }
        else if (x % 5 < 2.5){
            x = x - x % 5;
        }
        else{
            x = x + (5 - x % 5);
        }
        
        for(int i = 0; i < xCoordinatesGroupOne.length; i++){ 
            if(x == xCoordinatesGroupOne[i]){
                return x;
            }
            else if(x + 5 == xCoordinatesGroupOne[i]){
                x = x - 10;
                return x;
            }
            else if(x - 5 == xCoordinatesGroupOne[i]){
                x = x - 5;
                return x;
            }
        }
        return x;
    }

// lines up chip with Y coordinate of breadboard holes   
    public int lineUpChipY(int y){
        if (y % 5 == 0){
        }
        else if (y % 5 < 2.5){
            y = y - y % 5;
        }
        else{
            y = y + (5 - y % 5);
        }
        
        for(int i = 0; i < yCoordinatesGroupOne.length; i++){ 
            if(y == yCoordinatesGroupOne[i]){
                return y;
            }
            else if(y + 5 == yCoordinatesGroupOne[i]){
                y = y - 10;
                return y;
            }
            else if(y - 5 == yCoordinatesGroupOne[i]){
                y = y - 5;
                return y;
            }
            else if(y - 10 == yCoordinatesGroupOne[i]){
                y = y - 10;
                return y;
            }
        }
        return y;
    }
  
    public double leftClickGetXCoord(Rectangle changed){
        Rectangle newRec = changed;
        double currentX = newRec.getX() + 5;
        return currentX;
    }
    
    public double leftClickGetYCoord(Rectangle changed){
        Rectangle newRec = changed;
        double currentY = newRec.getY() + 5;
        return currentY;
    }

    public void findWireMatch(double x, double y){
        for(int i = 0; i < createdWireList.size(); i++){
            if(x == createdWireList.get(i).getStartX() && y == createdWireList.get(i).getStartY()){
                System.out.println("match in start");
            }
            else if(x == createdWireList.get(i).getEndX() && y == createdWireList.get(i).getEndY()){
                System.out.println("match in end");
            }
        }
    }
    
    
                   
    
    
    
    
    
// These are fucntions important to the events being handled above.
    
// holding arrayList of chips for future reference
   public void addToListOfChips(Rectangle chip){
       createdChipList.add(chip);
   } 
   
   public void addToListOfWires(Line wires){
       createdWireList.add(wires);
   }
    
// creates a line    
    private Line drawLine(double w, double x, double y, double z){
        Line line = new Line();
        line.setStartX(w + 5);
        line.setStartY(x + 5);
        line.setEndX(y + 5);
        line.setEndY(z + 5);
        line.setStroke(Color.LAWNGREEN);
        line.setStrokeWidth(2.5);
        return line;
    }
    
// creates an array of xCoordinates
    private void createXCoordinates(){
        xCoordinatesGroupOne[0] = 25;
        for (int i = 1; i < 65; i++){
            xCoordinatesGroupOne[i] = 15 + xCoordinatesGroupOne[i-1];
        }
    }
            
// returns line to added to pane
    public Line returnLine(){
        return currentLine;
    }
    
    public Line returnDrawnLiveWire(){
        return liveWire;
    }
    
    public double returnEndX(){
        return endX;
    }
    
    public double returnEndY(){
        return endY;
    }
    
    public int resetTimesClicked(){
        return timesClicked = 0;
    }
    public int returnTimesClicked(){
        return timesClicked;
    }
    
    public double returnCurrentX(){
        return currentX;
    }
    
    public double returnCurrentY(){
        return currentY;
    }
}
    