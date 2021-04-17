package circuitly;

import java.util.ArrayList;

public class stateTracker {
    double[] xLocation;           // these are going to be X values where the wire will be found
    double[] yLocation;           // same thing but Y values
    
    ArrayList <stateTracker.columnCreationPower> powerList;
    ArrayList <stateTracker.columnCreationGroupOne> columnStatesGroupOneList;
    ArrayList <stateTracker.columnCreationGroupTwo> columnStatesGroupTwoList;
    ArrayList <stateTracker.columnCreationGround> groundList;
    
    double[] locationsArray;
    double startX, startY, endX, endY;
    double firstPoint;
    
    int storeFirstColumnState, storeSecondColumnState;
    int firstY, secondY, firstLocation, secondLocation;
    int setState, setFirstGroup, setSecondGroup;
    
    public void getNeededInformation(double[] locations, ArrayList <stateTracker.columnCreationPower> power, 
            ArrayList <stateTracker.columnCreationGround> ground, 
            ArrayList <stateTracker.columnCreationGroupOne> groupOne, 
            ArrayList <stateTracker.columnCreationGroupTwo> groupTwo){
        
        startX = locations[0] - 5;
        startY = locations[1];
        endX = locations[2] - 5;
        endY = locations[3];
        
        powerList = power;
        columnStatesGroupOneList = groupOne;
        columnStatesGroupTwoList = groupTwo;
        groundList = ground;
        
        findFirstColumnState();
        findSecondColumnState();
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
                System.out.println("First Col State: "+ powerList.get(firstLocation).getState());
                break;
            case 1:    
                groundList.get(firstLocation).setState(setState);
                System.out.println("First Col State: "+ groundList.get(firstLocation).getState());
                break;
            case 2:
                columnStatesGroupOneList.get(firstLocation).setState(setState);
                System.out.println("First Col State: "+ columnStatesGroupOneList.get(firstLocation).getState());
                break;
            case 3:
                columnStatesGroupTwoList.get(firstLocation).setState(setState);
                System.out.println("First Col State: "+ columnStatesGroupTwoList.get(firstLocation).getState());
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
                System.out.println("Second Col State: "+ powerList.get(secondLocation).getState());
                break;
            case 1:
                groundList.get(secondLocation).setState(setState);
                System.out.println("Second Col State: "+ groundList.get(secondLocation).getState());
                break;
            case 2:
                columnStatesGroupOneList.get(secondLocation).setState(setState);
                System.out.println("Second Col State: "+ columnStatesGroupOneList.get(secondLocation).getState());
                break;
            case 3:
                columnStatesGroupTwoList.get(secondLocation).setState(setState);
                System.out.println("Second Col State: "+ columnStatesGroupTwoList.get(secondLocation).getState());
                break;
            default:
                System.out.println("Incorrect");
                break;
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
    
    }

    
    
