package circuitly;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class chipTracker {
    double start, end;
    
    ArrayList <createChip.createAndChip> andChipList;
    
    ArrayList <stateTracker.columnCreationGroupOne> columnStatesGroupOneList;
    ArrayList <stateTracker.columnCreationGroupTwo> columnStatesGroupTwoList;
    
    stateTracker.columnCreationGroupOne[] compareList = new stateTracker.columnCreationGroupOne[7];
    stateTracker.columnCreationGroupTwo[] compareListTwo = new stateTracker.columnCreationGroupTwo[7];
    
    public void getInformation(ArrayList <stateTracker.columnCreationGroupOne> groupOne, ArrayList <stateTracker.columnCreationGroupTwo> groupTwo,
            ArrayList <createChip.createAndChip> andChip){
        
        columnStatesGroupOneList = groupOne;
        columnStatesGroupTwoList = groupTwo;
        andChipList = andChip;
        
        getColumns();
    }
    
    public void getColumns(){
        for(int i = 0; i < andChipList.size(); i++){
            int k = 0;
            double tempX = andChipList.get(i).getX();
            for(int j = 0; j < 65; j++){
                if(columnStatesGroupOneList.get(j).getX() == tempX){
                    compareList[0] = columnStatesGroupOneList.get(j);
                    compareList[1] = columnStatesGroupOneList.get(j + 1);
                    compareList[2] = columnStatesGroupOneList.get(j + 2);
                    compareList[3] = columnStatesGroupOneList.get(j + 3);
                    compareList[4] = columnStatesGroupOneList.get(j + 4);
                    compareList[5] = columnStatesGroupOneList.get(j + 5);
                    compareList[6] = columnStatesGroupOneList.get(j + 6);
                    setAndChipsTop();
                    j = 65;
                }
            }
        }
    }
    
    public void setAndChipsTop(){
        if(compareList[0].getState() == 1){
            if(compareList[1].getState() == 1 && compareList[2].getState() == 1){
                compareList[3].setState(1);
            }
            else{
                compareList[3].setState(0);
            }

            if(compareList[4].getState() == 1 && compareList[5].getState() == 1){
                compareList[6].setState(1);
            }
            else{
                compareList[6].setState(0);
            }
        }
        else{
            compareList[3].setState(0);
            compareList[6].setState(0);
        }
        
        for(int i = 0; i < 65; i++ ){
            if(compareList[0].getX() == columnStatesGroupOneList.get(i).getX()){
                columnStatesGroupOneList.get(i).setState(compareList[0].getState());
                columnStatesGroupOneList.get(i + 1).setState(compareList[1].getState()); 
                columnStatesGroupOneList.get(i + 2).setState(compareList[2].getState()); 
                columnStatesGroupOneList.get(i + 3).setState(compareList[3].getState());
                columnStatesGroupOneList.get(i + 4).setState(compareList[4].getState()); 
                columnStatesGroupOneList.get(i + 5).setState(compareList[5].getState()); 
                columnStatesGroupOneList.get(i + 6).setState(compareList[6].getState()); 
            }
        }
    }
}