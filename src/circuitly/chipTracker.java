package circuitly;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class chipTracker {
    double start, end;
    
    ArrayList <createChip.createAndChip> andChipList;
    ArrayList <createChip.createNandChip> nandChipList;
    
    ArrayList <stateTracker.columnCreationGroupOne> columnStatesGroupOneList;
    ArrayList <stateTracker.columnCreationGroupTwo> columnStatesGroupTwoList;
    
    stateTracker.columnCreationGroupOne[] compareList = new stateTracker.columnCreationGroupOne[7];
    stateTracker.columnCreationGroupTwo[] compareListTwo = new stateTracker.columnCreationGroupTwo[7];
    
    public void getInformation(ArrayList <stateTracker.columnCreationGroupOne> groupOne, ArrayList <stateTracker.columnCreationGroupTwo> groupTwo,
            ArrayList <createChip.createAndChip> andChip, ArrayList <createChip.createNandChip> nandChip){
        
        columnStatesGroupOneList = groupOne;
        columnStatesGroupTwoList = groupTwo;
        andChipList = andChip;
        nandChipList = nandChip;
        
        if(andChipList.size() > 0){
            getAndColumns();
        }
        if(nandChipList.size() > 0){
            getNandColumns();
        }
    }
    
    public void getAndColumns(){
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
                    compareListTwo[0] = columnStatesGroupTwoList.get(j);
                    compareListTwo[1] = columnStatesGroupTwoList.get(j + 1);
                    compareListTwo[2] = columnStatesGroupTwoList.get(j + 2);
                    compareListTwo[3] = columnStatesGroupTwoList.get(j + 3);
                    compareListTwo[4] = columnStatesGroupTwoList.get(j + 4);
                    compareListTwo[5] = columnStatesGroupTwoList.get(j + 5);
                    compareListTwo[6] = columnStatesGroupTwoList.get(j + 6);
                    setAndChips();
                    j = 65;
                }
            }
        }
    }
    public void getNandColumns(){
        for(int i = 0; i < nandChipList.size(); i++){
            int k = 0;
            double tempX = nandChipList.get(i).getX();
            for(int j = 0; j < 65; j++){
                if(columnStatesGroupOneList.get(j).getX() == tempX){
                    compareList[0] = columnStatesGroupOneList.get(j);
                    compareList[1] = columnStatesGroupOneList.get(j + 1);
                    compareList[2] = columnStatesGroupOneList.get(j + 2);
                    compareList[3] = columnStatesGroupOneList.get(j + 3);
                    compareList[4] = columnStatesGroupOneList.get(j + 4);
                    compareList[5] = columnStatesGroupOneList.get(j + 5);
                    compareList[6] = columnStatesGroupOneList.get(j + 6);
                    compareListTwo[0] = columnStatesGroupTwoList.get(j);
                    compareListTwo[1] = columnStatesGroupTwoList.get(j + 1);
                    compareListTwo[2] = columnStatesGroupTwoList.get(j + 2);
                    compareListTwo[3] = columnStatesGroupTwoList.get(j + 3);
                    compareListTwo[4] = columnStatesGroupTwoList.get(j + 4);
                    compareListTwo[5] = columnStatesGroupTwoList.get(j + 5);
                    compareListTwo[6] = columnStatesGroupTwoList.get(j + 6);
                    setNandChips();
                    j = 65;
                }
            }
        }
    }
    
    public void setAndChips(){
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
            
            if(compareListTwo[0].getState() == 1 && compareListTwo[1].getState() == 1){
                compareListTwo[2].setState(1);
            }
            else{
                compareListTwo[2].setState(0);
            }
            
            if(compareListTwo[3].getState() == 1 && compareListTwo[4].getState() == 1){
                compareListTwo[5].setState(1);
            }
            else{
                compareListTwo[5].setState(0);
            }
        }
        
        else{
            compareList[3].setState(0);
            compareList[6].setState(0);
            compareListTwo[2].setState(0);
            compareListTwo[5].setState(0);
        }
        
        for(int i = 0; i < 65; i++ ){
            if(compareList[0].getX() == columnStatesGroupOneList.get(i).getX()){
                columnStatesGroupOneList.get(i + 3).setState(compareList[3].getState());
                columnStatesGroupOneList.get(i + 6).setState(compareList[6].getState()); 
                columnStatesGroupTwoList.get(i + 2).setState(compareListTwo[2].getState());
                columnStatesGroupTwoList.get(i + 5).setState(compareListTwo[5].getState());
            }
        }
    }
    public void setNandChips(){
        if(compareList[0].getState() == 1){
            if(compareList[1].getState() == 1 && compareList[2].getState() == 1){
                compareList[3].setState(6);
            }
            else{
                compareList[3].setState(1);
            }

            if(compareList[4].getState() == 1 && compareList[5].getState() == 1){
                compareList[6].setState(0);
            }
            else{
                compareList[6].setState(1);
            }
            
            if(compareListTwo[0].getState() == 1 && compareListTwo[1].getState() == 1){
                compareListTwo[2].setState(0);
            }
            else{
                compareListTwo[2].setState(1);
            }
            
            if(compareListTwo[3].getState() == 1 && compareListTwo[4].getState() == 1){
                compareListTwo[5].setState(0);
            }
            else{
                compareListTwo[5].setState(1);
            }
        }
        
        else{
            compareList[3].setState(0);
            compareList[6].setState(0);
            compareListTwo[2].setState(0);
            compareListTwo[5].setState(0);
        }
        
        for(int i = 0; i < 65; i++ ){
            if(compareList[0].getX() == columnStatesGroupOneList.get(i).getX()){
                columnStatesGroupOneList.get(i + 3).setState(compareList[3].getState());
                columnStatesGroupOneList.get(i + 6).setState(compareList[6].getState()); 
                columnStatesGroupTwoList.get(i + 2).setState(compareListTwo[2].getState());
                columnStatesGroupTwoList.get(i + 5).setState(compareListTwo[5].getState());
            }
        }
    }
}