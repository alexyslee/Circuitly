package circuitly;

import java.util.ArrayList;
import javafx.scene.shape.Line;

public class stateTracker {
    
    ArrayList <stateTracker.columnCreationPower> powerList;
    ArrayList <stateTracker.columnCreationGroupOne> columnStatesGroupOneList;
    ArrayList <stateTracker.columnCreationGroupTwo> columnStatesGroupTwoList;
    ArrayList <stateTracker.columnCreationGround> groundList;
    ArrayList <stateTracker.columnCreationButtons> buttonsList;
    
    ArrayList <Line> wireList;
    
    double[] locationsArray;
    double startX, startY, endX, endY;
    double firstPoint;
    
    int storeFirstColumnState, storeSecondColumnState;
    int firstY, secondY, firstLocation, secondLocation;
    int setState, setFirstGroup, setSecondGroup;
    int setArraySize;
    int z = 1;
    
    Line[] connectedLines = new Line[1000];
    
    public void getNeededInformation(double[] locations, ArrayList <stateTracker.columnCreationPower> power, 
            ArrayList <stateTracker.columnCreationGround> ground, 
            ArrayList <stateTracker.columnCreationGroupOne> groupOne, 
            ArrayList <stateTracker.columnCreationGroupTwo> groupTwo, ArrayList <Line> createWireList, ArrayList <stateTracker.columnCreationButtons> button){
        
        powerList = power;
        columnStatesGroupOneList = groupOne;
        columnStatesGroupTwoList = groupTwo;
        groundList = ground;
        wireList = createWireList;
        buttonsList = button;
    }
    
    
    public void findFirstColumnState(){
        if(startY == 105.0){
            for(int i = 0; i < 65; i++){
                if(startX == powerList.get(i).getX()){
                    firstLocation = i;
                    setFirstGroup = 0;
                    storeFirstColumnState = powerList.get(i).getState();
                }
            }
        }
        
        else if(startY == 120.0){
            for(int i = 0; i < 65; i++){
                if(startX == groundList.get(i).getX()){
                    firstLocation = i;
                    setFirstGroup = 1;
                    storeFirstColumnState = groundList.get(i).getState();
                }
            }
        }
        
        else if(startY >= 155 && startY <= 215){
            for(int i = 0; i < 65; i++){
                if(startX == columnStatesGroupOneList.get(i).getX()){
                    firstLocation = i;
                    setFirstGroup = 2;
                    storeFirstColumnState = columnStatesGroupOneList.get(i).getState();
                }
            }
        }
        
        else if(startY >= 245 && startY <= 305){
            for(int i = 0; i < 65; i++){
                if(startX == columnStatesGroupTwoList.get(i).getX()){
                    firstLocation = i;
                    setFirstGroup = 3;
                    storeFirstColumnState = columnStatesGroupTwoList.get(i).getState();
                }
            }
        }
        
        else if(startY >= 40 && startY <= 75){
            for(int i = 0; i < 8; i++){
                if(startX == buttonsList.get(i).getX()){
                    firstLocation = i;
                    setFirstGroup = 4;
                    storeFirstColumnState = buttonsList.get(i).getState();
                }
            }
        }
            
    }
    public void findSecondColumnState(){
        if(endY == 105.0){
            for(int i = 0; i < 65; i++){
                if(endX == powerList.get(i).getX()){
                    secondLocation = i;
                    setSecondGroup = 0;
                    storeSecondColumnState = powerList.get(i).getState();
                }
            }
        }
        
        else if(endY == 120.0){
            for(int i = 0; i < 65; i++){
                if(endX == groundList.get(i).getX()){
                    secondLocation = i;
                    setSecondGroup = 1;
                    storeSecondColumnState = groundList.get(i).getState();
                }
            }
        }
        
        else if(endY >= 155 && endY <= 215){
            for(int i = 0; i < 65; i++){
                if(endX == columnStatesGroupOneList.get(i).getX()){
                    secondLocation = i;
                    setSecondGroup = 2;
                    storeSecondColumnState = columnStatesGroupOneList.get(i).getState();
                }
            }
        }
        else if(endY >= 245 && endY <= 305){
            for(int i = 0; i < 65; i++){
                if(endX == columnStatesGroupTwoList.get(i).getX()){
                    secondLocation = i;
                    setSecondGroup = 3;
                    storeSecondColumnState = columnStatesGroupTwoList.get(i).getState();
                }
            }
        }
        else if(endY >= 40 && endY <= 75){
            for(int i = 0; i < 8; i++){
                if(endX == buttonsList.get(i).getX()){
                    secondLocation = i;
                    setSecondGroup = 4;
                    storeSecondColumnState = buttonsList.get(i).getState();
                }
            }
        }
        
    }
    public void calculateState(){
       if(storeFirstColumnState == 4 || storeSecondColumnState == 4){
           setState = 4;
       }
       else if(storeFirstColumnState == 3 || storeSecondColumnState == 3){
           setState = 3;
       }
       else if(storeFirstColumnState == 1 || storeSecondColumnState == 1){
           setState = 1;
       }
       else if(storeFirstColumnState == 0 && storeSecondColumnState == 0){
           setState = 0;
       }
       
       setFirstColState();
       setSecondColState();
    }
    
    public void setFirstColState(){
        switch(setFirstGroup){
            case 0:
                powerList.get(firstLocation).setState(setState);
                break;
            case 1:    
                groundList.get(firstLocation).setState(setState);
                break;
            case 2:
                columnStatesGroupOneList.get(firstLocation).setState(setState);
                break;
            case 3:
                columnStatesGroupTwoList.get(firstLocation).setState(setState);
                break;
            case 4:
                buttonsList.get(firstLocation).setState(setState);
                break;
            default:
                System.out.println("Incorrect");
                break;
        }
    }
    public void setSecondColState(){
        switch(setSecondGroup){
            case 0:
                powerList.get(secondLocation).setState(setState);
                break;
            case 1:
                groundList.get(secondLocation).setState(setState);
                break;
            case 2:
                columnStatesGroupOneList.get(secondLocation).setState(setState);
                break;
            case 3:
                columnStatesGroupTwoList.get(secondLocation).setState(setState);
                break;
            case 4:
                buttonsList.get(secondLocation).setState(setState);
                break;
            default:
                System.out.println("Incorrect");
                break;
        }
    }
    
    public void setStateFromWires(){
        for(int i = 0; i < wireList.size(); i++){
            startX = wireList.get(i).getStartX() - 5;
            startY = wireList.get(i).getStartY();
            endX = wireList.get(i).getEndX() - 5;
            endY = wireList.get(i).getEndY();
            
            findFirstColumnState();
            findSecondColumnState();
            calculateState();
        }
    }
    public void setStateFromButton(int states, double x, ArrayList <Line> createWireList, ArrayList <stateTracker.columnCreationButtons> button){
        buttonsList = button; 
        wireList = createWireList;
        int state = states;     
        
        
        for(int i = 0; i < 8; i ++){   
                if(x == button.get(i).getX() + 5){
                    buttonsList.get(i).setState(states);
                    
                    
                    if(createWireList.size() > 0){
                        for(int j = 0; j < createWireList.size(); j++){
                            if(wireList.get(j).getStartX() == x || wireList.get(j).getEndX() == x){
                                connectedLines[0] = wireList.get(j);
                                getConnectedWires(0, state);
                            }
                        }
                    }
                    
                }
            }
    }
    public void getConnectedWires(int n, int state){
        int states = state;
        z = 1;
        if(n < wireList.size()){
            for(int i = 0; i < wireList.size(); i++){

                if(connectedLines[n].getStartX() == wireList.get(i).getStartX() && connectedLines[n].getEndX() == wireList.get(i).getEndX()){

                }
                else if(connectedLines[n].getStartX() == wireList.get(i).getStartX() ^ connectedLines[n].getStartX() == wireList.get(i).getEndX() ^ connectedLines[n].getEndX() == wireList.get(i).getEndX() ^ connectedLines[n].getEndX() == wireList.get(i).getStartX()){
                    connectedLines[z] = wireList.get(i);
                    z++;
                }
            }
            ++n;
            if(connectedLines[n] != null){
                getConnectedWires(n, states);
            }
            else{
                n = wireList.size() + 1;
                getConnectedWires(n,  states);
            }
            
        }

        else{
            setArraySize = 0;
            for(int i = 0; i < connectedLines.length; i++){
                if(connectedLines[i] == null){
                }
                else{
                    setArraySize++;
                }
            }
            
            for(int i = 0; i < setArraySize; i++){
                startX = connectedLines[i].getStartX();
                endX = connectedLines[i].getEndX();
                
                for(int j = 0; j < columnStatesGroupTwoList.size(); j++){
//                    System.out.println(columnStatesGroupTwoList.get(j).getX());
                    if(startX == (columnStatesGroupTwoList.get(j).getX() + 5) || endX == (columnStatesGroupTwoList.get(j).getX() + 5)){
                        columnStatesGroupTwoList.get(j).setState(state);
                    }
                }
            }
            
        }
    }
    
    
    public ArrayList <stateTracker.columnCreationPower> returnUpdatedPowerRow(){
        return powerList;
    }
    public ArrayList <stateTracker.columnCreationGroupOne> returnGroupOne(){
        return columnStatesGroupOneList;
    }
    public ArrayList <stateTracker.columnCreationGroupTwo> returnGroupTwo(){
        return columnStatesGroupTwoList;
    }
    public ArrayList <stateTracker.columnCreationGround> returnGround(){
        return groundList;
    }
    ArrayList <stateTracker.columnCreationButtons> returnButtons(){
        return buttonsList;
    }
    
    public class columnCreationPower{
        double xCoord = 0;
        
        public int setState(int states){
            int state = 1;
            return state;
        }
        
        public int getState(){
            int state = 1;
            return state;
        }
        
        public void setX(int i){
            if(i == 0){
                xCoord = 25;
            }
            else{
                xCoord = 25 + 15 * i;
            }
        }
        public double getX(){
            return xCoord;
        }
        
        public double getY(){
            double yCoordinatesPower = 100;
            return yCoordinatesPower;
        }
    }
    public class columnCreationGround{
        double xCoord = 0;
        
        public int setState(int states){
            int state = 4;
            return state;
        }
        
        public int getState(){
            int state = 4;
            return state;
        }
        
        public void setX(int i){
            if(i == 0){
                xCoord = 25;
            }
            else{
                xCoord = 25 + 15 * i;
            }
        }
        public double getX(){
            return xCoord;
        }
        
        public double getY(){
            double yCoordinatesGround = 120;
            return yCoordinatesGround;
        }
    }
    public class columnCreationGroupOne{
        int state;
        double xCoord = 0;
        
        public int setState(int sentState){
            state = sentState;
            return state;
        }
        public int getState(){
            return state;
        }
        
        public void setX(int i){
            if(i == 0){
                xCoord = 25;
            }
            else{
                xCoord = 25 + 15 * i;
            }
        }
        public double getX(){
            return xCoord;
        }
        
        public double[] getY(){
            double[] yCoordinatesGroupOne = new double[]{150,165,180,195,210};
            return yCoordinatesGroupOne;
        }
        
       }
    public class columnCreationGroupTwo{
        int state;
        double xCoord = 0;
        
        public int setState(int sentState){
            state = sentState;
            return state;
        }
        public int getState(){
            return state;
        }
        
        public void setX(int i){
            if(i == 0){
                xCoord = 25;
            }
            else{
                xCoord = 25 + 15 * i;
            }
        }
        public double getX(){
            return xCoord;
        }
        
        public double[] getY(){
            double[] yCoordinatesGroupOne = new double[]{240,255,270,285,300};
            return yCoordinatesGroupOne;
        }
        
       }
    public class columnCreationButtons{
        int state;
        double xCoord = 0;
        
        public void setState(int states){
            state = states;
        }
        
        public int getState(){
            return state;
        }
        
        public void setX(int i){
            if(i == 0){
                xCoord = 735;
            }
            else{
                xCoord = 735 + 35 * i;
            }
        }
        public double getX(){
            return xCoord;
        }
        
        public double[] getY(){
            double[] yButtonCoordinates = new double[] {35, 70};
            return yButtonCoordinates;
        }
    }    
    
    }

    
    
