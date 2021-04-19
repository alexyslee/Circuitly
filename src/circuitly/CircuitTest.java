package circuitly;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import circuitly.eventHandling;
import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class CircuitTest extends Application{
    public Rectangle[] breadboardHoles = new Rectangle[325];
    private Line currentLine, liveWire;
    public eventHandling handled = new eventHandling();
    public int[] xCoordinatesGroupOne = new int[65];
    public Rectangle chip = new Rectangle();
    public ImageView displayCurrentChip;
    Rectangle setChip;
    public chipCreation chips = new chipCreation();
    public chipCreation texts = new chipCreation();
    
    ArrayList <Rectangle> createdChipList = new ArrayList<>();
    ArrayList <ImageView> chipList = new ArrayList<>();
    
    ArrayList <createChip.createAndChip> andChipList = new ArrayList<>();
    ArrayList <createChip.createNandChip> nandChipList = new ArrayList<>();
    ArrayList <createChip.createOrChip> orChipList = new ArrayList<>();
    ArrayList <createChip.createXorChip> xorChipList = new ArrayList<>();
    ArrayList <createChip.createNorChip> norChipList = new ArrayList<>();
    ArrayList <createChip.createNotChip> notChipList = new ArrayList<>();
    ArrayList <createChip.createAndFourChip> andFourChipList = new ArrayList<>();
    ArrayList <createChip.createNandFourChip> nandFourChipList = new ArrayList<>();
    
    ArrayList <Line> createWireList = new ArrayList<>();
    
    ArrayList <stateTracker.columnCreationGroupOne> columnStatesGroupOne = new ArrayList<>();
    ArrayList <stateTracker.columnCreationGroupTwo> columnStatesGroupTwo = new ArrayList<>();
    ArrayList <stateTracker.columnCreationPower> power = new ArrayList<>();
    ArrayList <stateTracker.columnCreationGround> ground = new ArrayList<>();
    ArrayList <stateTracker.columnCreationButtons> button  = new ArrayList<>();
    
    
    boolean isViewToggled = true;          // false is point view -- true is wired view
    boolean setMode = false;
    boolean movable = false;
    boolean firstClick = true;
    double currentX, currentY;

    double[] toBeCompared = new double[4];
    int addedToArray;
    double startX, startY, endX, endY;
    stateTracker tracking = new stateTracker();
    chipTracker trackingChip = new chipTracker();
    
    
    
    createChip and = new createChip();
    createChip.createAndChip andChipSetup = and.new createAndChip();
    ImageView andChip = andChipSetup.createShape();
    
    createChip nand= new createChip();
    createChip.createNandChip nandChipSetup = nand.new createNandChip();
    ImageView nandChip = nandChipSetup.createShape();
    
    createChip or = new createChip();
    createChip.createOrChip orChipSetup = or.new createOrChip();
    ImageView orChip = orChipSetup.createShape();
    
    createChip xor = new createChip();
    createChip.createXorChip xorChipSetup = xor.new createXorChip();
    ImageView xorChip = xorChipSetup.createShape();
    
    createChip nor = new createChip();
    createChip.createNorChip norChipSetup = nor.new createNorChip();
    ImageView norChip = norChipSetup.createShape();
    
    createChip not = new createChip();
    createChip.createNotChip notChipSetup = not.new createNotChip();
    ImageView notChip = notChipSetup.createShape();
    
    createChip andFour = new createChip();
    createChip.createAndFourChip andFourChipSetup = andFour.new createAndFourChip();
    ImageView andFourChip = andFourChipSetup.createShape();
    
    createChip nandFour = new createChip();
    createChip.createNandFourChip nandFourChipSetup = nandFour.new createNandFourChip();
    ImageView nandFourChip = nandFourChipSetup.createShape();
    
    
    public static void main(String[] args) {
        launch(args);
        
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane pane = new BorderPane();

// intializing the state of the first group of columns
        for(int i = 0; i < 65; i++){
            stateTracker c = new stateTracker();
            stateTracker.columnCreationGroupOne groupOneColumn = c.new columnCreationGroupOne();
            groupOneColumn.setX(i);
            columnStatesGroupOne.add(groupOneColumn);
        }
        
// intializing the state of the second group of columns
        for(int i = 0; i < 65; i++){
            stateTracker c2 = new stateTracker();
            stateTracker.columnCreationGroupTwo groupTwoColumn = c2.new columnCreationGroupTwo();
            groupTwoColumn.setX(i);
            columnStatesGroupTwo.add(groupTwoColumn);
        }

// intialing the state of the power row        
        for(int i = 0; i < 65; i++){
            stateTracker p = new stateTracker();
            stateTracker.columnCreationPower powerColumn = p.new columnCreationPower();
            powerColumn.setX(i);
            power.add(powerColumn);
        }

// intializing the state of the ground row
        for(int i = 0; i < 65; i++){
            stateTracker g = new stateTracker();
            stateTracker.columnCreationGround groundColumn = g.new columnCreationGround();
            groundColumn.setX(i);
            ground.add(groundColumn);
        }
// intializing the state of the button rows
        for(int i = 0; i < 8; i++){
            stateTracker b = new stateTracker();
            stateTracker.columnCreationButtons buttonColumn = b.new columnCreationButtons();
            buttonColumn.setX(i);
            button.add(buttonColumn);
        }
        

// BUTTONS        
        breadboardHolesProperties buttonRow = new breadboardHolesProperties();
        buttonRow.createButtonRow();
        Rectangle[] buttonRowGroup = buttonRow.returnButtonHoles();
        
        for(int z = 0; z < 16; z++){
            pane.getChildren().add(buttonRowGroup[z]);
        }        
         
        for(int l = 0; l < buttonRowGroup.length; l++){
            Rectangle currentSquare = buttonRowGroup[l];
            buttonRowGroup[l].setOnMouseClicked(new EventHandler<MouseEvent>(){
                public void handle(MouseEvent e){
                    if(e.getButton() == MouseButton.PRIMARY){
                        handled.pickingBreadboardHoles(currentSquare, isViewToggled);
                    
// if point view is activated this draws wire by selecting holes only -- needs to be switched where you do not see the wires                 
                        if(e.getButton() == MouseButton.PRIMARY && isViewToggled == false && firstClick == true){
                            liveWire = handled.returnDrawnLiveWire();
                            movable = true;
                            firstClick = false;
                            startX = liveWire.getStartX();
                            startY = liveWire.getStartY();
                            createWireList.add(liveWire);
                            handled.addToListOfWires(liveWire);
                            pane.getChildren().add(liveWire);  
                            
                            toBeCompared[0] = startX;
                            toBeCompared[1] = startY;
                            System.out.println(toBeCompared[1]);

                            pane.setOnMouseMoved(new EventHandler<MouseEvent>(){
                                public void handle(MouseEvent e){
                                if(movable == true){
                                    liveWire.setEndX(e.getSceneX());
                                    liveWire.setEndY(e.getSceneY() - 2);
                                }
                            }});
                        }
                        else if(e.getButton() == MouseButton.PRIMARY && isViewToggled == false && firstClick == false){    
                            movable = false;
                            firstClick = true;
                            endX = handled.returnEndX() + 5;
                            endY = handled.returnEndY() + 5;
                            liveWire.setEndX(endX);
                            liveWire.setEndY(endY);
                            
                            toBeCompared[2] = endX;
                            toBeCompared[3] = endY;
                            
                            System.out.println(toBeCompared[3]);
                            
                            tracking.getNeededInformation(toBeCompared, power, ground, columnStatesGroupOne, columnStatesGroupTwo, createWireList, button);
                            tracking.setStateFromWires();
                            
                            columnStatesGroupOne = tracking.returnGroupOne();
                            columnStatesGroupTwo = tracking.returnGroupTwo();
                            
                            trackingChip.getInformation(columnStatesGroupOne, columnStatesGroupTwo, andChipList, nandChipList, orChipList, xorChipList,
                            norChipList, notChipList, andFourChipList, nandFourChipList);
                            columnStatesGroupOne = trackingChip.columnStatesGroupOneList;
                            columnStatesGroupTwo = trackingChip.columnStatesGroupTwoList;
                            
                            tracking.setStateFromWires();
                            columnStatesGroupOne = trackingChip.columnStatesGroupOneList;
                            columnStatesGroupTwo = trackingChip.columnStatesGroupTwoList;
                            
                           for(int i = 0; i < 65; i++){
                               System.out.println("I[" + (i + 1) + "]: " + columnStatesGroupTwo.get(i).getState());
                           }
                            
                       }
//  if wire view is Toggled this begins to drawn wires in real time            
                        else if(e.getButton() == MouseButton.PRIMARY && isViewToggled == true && firstClick == true){
                            liveWire = handled.returnDrawnLiveWire();
                            movable = true;
                            firstClick = false;
                            startX = liveWire.getStartX();
                            startY = liveWire.getStartY();
                            createWireList.add(liveWire);
                            handled.addToListOfWires(liveWire);
                            pane.getChildren().add(liveWire);  
                            
                            toBeCompared[0] = startX;
                            toBeCompared[1] = startY;
                            System.out.println(toBeCompared[1]);

                            pane.setOnMouseMoved(new EventHandler<MouseEvent>(){
                                public void handle(MouseEvent e){
                                if(movable == true){
                                    liveWire.setEndX(e.getSceneX());
                                    liveWire.setEndY(e.getSceneY() - 2);
                                }
                            }});
                        }


                       else if(e.getButton() == MouseButton.PRIMARY && isViewToggled == true && firstClick == false){    
                            movable = false;
                            firstClick = true;
                            endX = handled.returnEndX() + 5;
                            endY = handled.returnEndY() + 5;
                            liveWire.setEndX(endX);
                            liveWire.setEndY(endY);
                            
                            toBeCompared[2] = endX;
                            toBeCompared[3] = endY;
                            
                            System.out.println(toBeCompared[3]);
                            
                            tracking.getNeededInformation(toBeCompared, power, ground, columnStatesGroupOne, columnStatesGroupTwo, createWireList, button);
                            tracking.setStateFromWires();
                            
                            columnStatesGroupOne = tracking.returnGroupOne();
                            columnStatesGroupTwo = tracking.returnGroupTwo();
                            
                            trackingChip.getInformation(columnStatesGroupOne, columnStatesGroupTwo, andChipList, nandChipList, orChipList, xorChipList,
                            norChipList, notChipList, andFourChipList, nandFourChipList);
                            columnStatesGroupOne = trackingChip.columnStatesGroupOneList;
                            columnStatesGroupTwo = trackingChip.columnStatesGroupTwoList;
                            
                            tracking.setStateFromWires();
                            columnStatesGroupOne = trackingChip.columnStatesGroupOneList;
                            columnStatesGroupTwo = trackingChip.columnStatesGroupTwoList;
                            
                           for(int i = 0; i < 65; i++){
                               System.out.println("I[" + (i + 1) + "]: " + columnStatesGroupTwo.get(i).getState());
                           }
                            
                       }
                    }
                }
            });
        }       

// POWER
        breadboardHolesProperties powerRow = new breadboardHolesProperties();
        powerRow.createPowerRow();
        Rectangle[] powerRowGroup = powerRow.returnPowerRow();
        
        for(int z = 0; z < 65; z++){
            pane.getChildren().add(powerRowGroup[z]);
        }
        
        for(int l = 0; l < powerRowGroup.length; l++){
            Rectangle currentSquare = powerRowGroup[l];
            powerRowGroup[l].setOnMouseClicked(new EventHandler<MouseEvent>(){
                public void handle(MouseEvent e){
                    if(e.getButton() == MouseButton.PRIMARY){
                        handled.pickingBreadboardHoles(currentSquare, isViewToggled);
                    
// if point view is activated this draws wire by selecting holes only -- needs to be switched where you do not see the wires                 
                        if(e.getButton() == MouseButton.PRIMARY && isViewToggled == false && firstClick == true){
                            liveWire = handled.returnDrawnLiveWire();
                            movable = true;
                            firstClick = false;
                            startX = liveWire.getStartX();
                            startY = liveWire.getStartY();
                            createWireList.add(liveWire);
                            handled.addToListOfWires(liveWire);
                            pane.getChildren().add(liveWire);  
                            
                            toBeCompared[0] = startX;
                            toBeCompared[1] = startY;
                            System.out.println(toBeCompared[1]);

                            pane.setOnMouseMoved(new EventHandler<MouseEvent>(){
                                public void handle(MouseEvent e){
                                if(movable == true){
                                    liveWire.setEndX(e.getSceneX());
                                    liveWire.setEndY(e.getSceneY() - 2);
                                }
                            }});
                        }
                        else if(e.getButton() == MouseButton.PRIMARY && isViewToggled == false && firstClick == false){    
                            movable = false;
                            firstClick = true;
                            endX = handled.returnEndX() + 5;
                            endY = handled.returnEndY() + 5;
                            liveWire.setEndX(endX);
                            liveWire.setEndY(endY);
                            
                            toBeCompared[2] = endX;
                            toBeCompared[3] = endY;
                            
                            System.out.println(toBeCompared[3]);
                            
                            tracking.getNeededInformation(toBeCompared, power, ground, columnStatesGroupOne, columnStatesGroupTwo, createWireList, button);
                            tracking.setStateFromWires();
                            
                            columnStatesGroupOne = tracking.returnGroupOne();
                            columnStatesGroupTwo = tracking.returnGroupTwo();
                            
                            trackingChip.getInformation(columnStatesGroupOne, columnStatesGroupTwo, andChipList, nandChipList, orChipList, xorChipList,
                            norChipList, notChipList, andFourChipList, nandFourChipList);
                            columnStatesGroupOne = trackingChip.columnStatesGroupOneList;
                            columnStatesGroupTwo = trackingChip.columnStatesGroupTwoList;
                            
                            tracking.setStateFromWires();
                            columnStatesGroupOne = trackingChip.columnStatesGroupOneList;
                            columnStatesGroupTwo = trackingChip.columnStatesGroupTwoList;
                            
                           for(int i = 0; i < 65; i++){
                               System.out.println("I[" + (i + 1) + "]: " + columnStatesGroupTwo.get(i).getState());
                           }
                            
                       }
//  if wire view is Toggled this begins to drawn wires in real time            
                        else if(e.getButton() == MouseButton.PRIMARY && isViewToggled == true && firstClick == true){
                            liveWire = handled.returnDrawnLiveWire();
                            movable = true;
                            firstClick = false;
                            startX = liveWire.getStartX();
                            startY = liveWire.getStartY();
                            createWireList.add(liveWire);
                            handled.addToListOfWires(liveWire);
                            pane.getChildren().add(liveWire);  
                            
                            toBeCompared[0] = startX;
                            toBeCompared[1] = startY;
                            System.out.println(toBeCompared[1]);

                            pane.setOnMouseMoved(new EventHandler<MouseEvent>(){
                                public void handle(MouseEvent e){
                                if(movable == true){
                                    liveWire.setEndX(e.getSceneX());
                                    liveWire.setEndY(e.getSceneY() - 2);
                                }
                            }});
                        }


                       else if(e.getButton() == MouseButton.PRIMARY && isViewToggled == true && firstClick == false){    
                            movable = false;
                            firstClick = true;
                            endX = handled.returnEndX() + 5;
                            endY = handled.returnEndY() + 5;
                            liveWire.setEndX(endX);
                            liveWire.setEndY(endY);
                            
                            toBeCompared[2] = endX;
                            toBeCompared[3] = endY;
                            
                            System.out.println(toBeCompared[3]);
                            
                            tracking.getNeededInformation(toBeCompared, power, ground, columnStatesGroupOne, columnStatesGroupTwo, createWireList, button);
                            tracking.setStateFromWires();
                            
                            columnStatesGroupOne = tracking.returnGroupOne();
                            columnStatesGroupTwo = tracking.returnGroupTwo();
                            
                            trackingChip.getInformation(columnStatesGroupOne, columnStatesGroupTwo, andChipList, nandChipList, orChipList, xorChipList,
                            norChipList, notChipList, andFourChipList, nandFourChipList);
                            columnStatesGroupOne = trackingChip.columnStatesGroupOneList;
                            columnStatesGroupTwo = trackingChip.columnStatesGroupTwoList;
                            
                            tracking.setStateFromWires();
                            columnStatesGroupOne = trackingChip.columnStatesGroupOneList;
                            columnStatesGroupTwo = trackingChip.columnStatesGroupTwoList;
                            
                           for(int i = 0; i < 65; i++){
                               System.out.println("I[" + (i + 1) + "]: " + columnStatesGroupTwo.get(i).getState());
                           }
                            
                       }
                    }
                }
            });
        }
        
// GROUND
        breadboardHolesProperties groundRow = new breadboardHolesProperties();
        groundRow.createGroundRow();
        Rectangle[] groundRowGroup = groundRow.returnGroundRow();
        
        for(int z = 0; z < 65; z++){
            pane.getChildren().add(groundRowGroup[z]);
        }
        
        for(int l = 0; l < groundRowGroup.length; l++){
            Rectangle currentSquare = groundRowGroup[l];
            groundRowGroup[l].setOnMouseClicked(new EventHandler<MouseEvent>(){
                public void handle(MouseEvent e){
                    if(e.getButton() == MouseButton.PRIMARY){
                        handled.pickingBreadboardHoles(currentSquare, isViewToggled);
                    
// if point view is activated this draws wire by selecting holes only -- needs to be switched where you do not see the wires                 
                        if(e.getButton() == MouseButton.PRIMARY && isViewToggled == false && firstClick == true){
                            liveWire = handled.returnDrawnLiveWire();
                            movable = true;
                            firstClick = false;
                            startX = liveWire.getStartX();
                            startY = liveWire.getStartY();
                            createWireList.add(liveWire);
                            handled.addToListOfWires(liveWire);
                            pane.getChildren().add(liveWire);  
                            
                            toBeCompared[0] = startX;
                            toBeCompared[1] = startY;
                            System.out.println(toBeCompared[1]);

                            pane.setOnMouseMoved(new EventHandler<MouseEvent>(){
                                public void handle(MouseEvent e){
                                if(movable == true){
                                    liveWire.setEndX(e.getSceneX());
                                    liveWire.setEndY(e.getSceneY() - 2);
                                }
                            }});
                        }
                        else if(e.getButton() == MouseButton.PRIMARY && isViewToggled == false && firstClick == false){    
                            movable = false;
                            firstClick = true;
                            endX = handled.returnEndX() + 5;
                            endY = handled.returnEndY() + 5;
                            liveWire.setEndX(endX);
                            liveWire.setEndY(endY);
                            
                            toBeCompared[2] = endX;
                            toBeCompared[3] = endY;
                            
                            System.out.println(toBeCompared[3]);
                            
                            tracking.getNeededInformation(toBeCompared, power, ground, columnStatesGroupOne, columnStatesGroupTwo, createWireList, button);
                            tracking.setStateFromWires();
                            
                            columnStatesGroupOne = tracking.returnGroupOne();
                            columnStatesGroupTwo = tracking.returnGroupTwo();
                            
                            trackingChip.getInformation(columnStatesGroupOne, columnStatesGroupTwo, andChipList, nandChipList, orChipList, xorChipList,
                            norChipList, notChipList, andFourChipList, nandFourChipList);
                            columnStatesGroupOne = trackingChip.columnStatesGroupOneList;
                            columnStatesGroupTwo = trackingChip.columnStatesGroupTwoList;
                            
                            tracking.setStateFromWires();
                            columnStatesGroupOne = trackingChip.columnStatesGroupOneList;
                            columnStatesGroupTwo = trackingChip.columnStatesGroupTwoList;
                            
                           for(int i = 0; i < 65; i++){
                               System.out.println("I[" + (i + 1) + "]: " + columnStatesGroupTwo.get(i).getState());
                           }
                            
                       }
//  if wire view is Toggled this begins to drawn wires in real time            
                        else if(e.getButton() == MouseButton.PRIMARY && isViewToggled == true && firstClick == true){
                            liveWire = handled.returnDrawnLiveWire();
                            movable = true;
                            firstClick = false;
                            startX = liveWire.getStartX();
                            startY = liveWire.getStartY();
                            createWireList.add(liveWire);
                            handled.addToListOfWires(liveWire);
                            pane.getChildren().add(liveWire);  
                            
                            toBeCompared[0] = startX;
                            toBeCompared[1] = startY;
                            System.out.println(toBeCompared[1]);

                            pane.setOnMouseMoved(new EventHandler<MouseEvent>(){
                                public void handle(MouseEvent e){
                                if(movable == true){
                                    liveWire.setEndX(e.getSceneX());
                                    liveWire.setEndY(e.getSceneY() - 2);
                                }
                            }});
                        }


                       else if(e.getButton() == MouseButton.PRIMARY && isViewToggled == true && firstClick == false){    
                            movable = false;
                            firstClick = true;
                            endX = handled.returnEndX() + 5;
                            endY = handled.returnEndY() + 5;
                            liveWire.setEndX(endX);
                            liveWire.setEndY(endY);
                            
                            toBeCompared[2] = endX;
                            toBeCompared[3] = endY;
                            
                            System.out.println(toBeCompared[3]);
                            
                            tracking.getNeededInformation(toBeCompared, power, ground, columnStatesGroupOne, columnStatesGroupTwo, createWireList, button);
                            tracking.setStateFromWires();
                            
                            columnStatesGroupOne = tracking.returnGroupOne();
                            columnStatesGroupTwo = tracking.returnGroupTwo();
                            
                            trackingChip.getInformation(columnStatesGroupOne, columnStatesGroupTwo, andChipList, nandChipList, orChipList, xorChipList,
                            norChipList, notChipList, andFourChipList, nandFourChipList);
                            columnStatesGroupOne = trackingChip.columnStatesGroupOneList;
                            columnStatesGroupTwo = trackingChip.columnStatesGroupTwoList;
                            
                            tracking.setStateFromWires();
                            columnStatesGroupOne = trackingChip.columnStatesGroupOneList;
                            columnStatesGroupTwo = trackingChip.columnStatesGroupTwoList;
                            
                           for(int i = 0; i < 65; i++){
                               System.out.println("I[" + (i + 1) + "]: " + columnStatesGroupTwo.get(i).getState());
                           }
                            
                       }
                    }
                }
            });
        }
        
        
        
// GROUP ONE
        breadboardHolesProperties groupOne = new breadboardHolesProperties();
        groupOne.createBreadboardHolesGroupOne();
        Rectangle[] breadboardHolesGroupOne = groupOne.returnBreadboardHoles();
        
        for(int z = 0; z < 325; z++){
            pane.getChildren().add(breadboardHolesGroupOne[z]);
        }

// whenever a hole is right clicked it should select the hole and change its color      
        for(int l = 0; l < breadboardHolesGroupOne.length; l++){
            Rectangle currentSquare = breadboardHolesGroupOne[l];
            breadboardHolesGroupOne[l].setOnMouseClicked(new EventHandler<MouseEvent>(){
                public void handle(MouseEvent e){
                    if(e.getButton() == MouseButton.PRIMARY){
                        handled.pickingBreadboardHoles(currentSquare, isViewToggled);
                    
// if point view is activated this draws wire by selecting holes only -- needs to be switched where you do not see the wires                 
                        if(e.getButton() == MouseButton.PRIMARY && isViewToggled == false && firstClick == true){
                            liveWire = handled.returnDrawnLiveWire();
                            movable = true;
                            firstClick = false;
                            startX = liveWire.getStartX();
                            startY = liveWire.getStartY();
                            createWireList.add(liveWire);
                            handled.addToListOfWires(liveWire);
                            pane.getChildren().add(liveWire);  
                            
                            toBeCompared[0] = startX;
                            toBeCompared[1] = startY;
                            System.out.println(toBeCompared[1]);

                            pane.setOnMouseMoved(new EventHandler<MouseEvent>(){
                                public void handle(MouseEvent e){
                                if(movable == true){
                                    liveWire.setEndX(e.getSceneX());
                                    liveWire.setEndY(e.getSceneY() - 2);
                                }
                            }});
                        }
                        else if(e.getButton() == MouseButton.PRIMARY && isViewToggled == false && firstClick == false){    
                            movable = false;
                            firstClick = true;
                            endX = handled.returnEndX() + 5;
                            endY = handled.returnEndY() + 5;
                            liveWire.setEndX(endX);
                            liveWire.setEndY(endY);
                            
                            toBeCompared[2] = endX;
                            toBeCompared[3] = endY;
                            
                            System.out.println(toBeCompared[3]);
                            
                            tracking.getNeededInformation(toBeCompared, power, ground, columnStatesGroupOne, columnStatesGroupTwo, createWireList, button);
                            tracking.setStateFromWires();
                            
                            columnStatesGroupOne = tracking.returnGroupOne();
                            columnStatesGroupTwo = tracking.returnGroupTwo();
                            
                            trackingChip.getInformation(columnStatesGroupOne, columnStatesGroupTwo, andChipList, nandChipList, orChipList, xorChipList,
                            norChipList, notChipList, andFourChipList, nandFourChipList);
                            columnStatesGroupOne = trackingChip.columnStatesGroupOneList;
                            columnStatesGroupTwo = trackingChip.columnStatesGroupTwoList;
                            
                            tracking.setStateFromWires();
                            columnStatesGroupOne = trackingChip.columnStatesGroupOneList;
                            columnStatesGroupTwo = trackingChip.columnStatesGroupTwoList;
                            
                           for(int i = 0; i < 65; i++){
                               System.out.println("I[" + (i + 1) + "]: " + columnStatesGroupTwo.get(i).getState());
                           }
                            
                       }
//  if wire view is Toggled this begins to drawn wires in real time            
                        else if(e.getButton() == MouseButton.PRIMARY && isViewToggled == true && firstClick == true){
                            liveWire = handled.returnDrawnLiveWire();
                            movable = true;
                            firstClick = false;
                            startX = liveWire.getStartX();
                            startY = liveWire.getStartY();
                            createWireList.add(liveWire);
                            handled.addToListOfWires(liveWire);
                            pane.getChildren().add(liveWire);  
                            
                            toBeCompared[0] = startX;
                            toBeCompared[1] = startY;
                            System.out.println(toBeCompared[1]);

                            pane.setOnMouseMoved(new EventHandler<MouseEvent>(){
                                public void handle(MouseEvent e){
                                if(movable == true){
                                    liveWire.setEndX(e.getSceneX());
                                    liveWire.setEndY(e.getSceneY() - 2);
                                }
                            }});
                        }


                       else if(e.getButton() == MouseButton.PRIMARY && isViewToggled == true && firstClick == false){    
                            movable = false;
                            firstClick = true;
                            endX = handled.returnEndX() + 5;
                            endY = handled.returnEndY() + 5;
                            liveWire.setEndX(endX);
                            liveWire.setEndY(endY);
                            
                            toBeCompared[2] = endX;
                            toBeCompared[3] = endY;
                            
                            System.out.println(toBeCompared[3]);
                            
                            tracking.getNeededInformation(toBeCompared, power, ground, columnStatesGroupOne, columnStatesGroupTwo, createWireList, button);
                            tracking.setStateFromWires();
                            
                            columnStatesGroupOne = tracking.returnGroupOne();
                            columnStatesGroupTwo = tracking.returnGroupTwo();
                            
                            trackingChip.getInformation(columnStatesGroupOne, columnStatesGroupTwo, andChipList, nandChipList, orChipList, xorChipList,
                            norChipList, notChipList, andFourChipList, nandFourChipList);
                            
                            columnStatesGroupOne = trackingChip.columnStatesGroupOneList;
                            columnStatesGroupTwo = trackingChip.columnStatesGroupTwoList;
                            
                            tracking.setStateFromWires();
                            columnStatesGroupOne = trackingChip.columnStatesGroupOneList;
                            columnStatesGroupTwo = trackingChip.columnStatesGroupTwoList;
                            
                           for(int i = 0; i < 65; i++){
                               System.out.println("I[" + (i + 1) + "]: " + columnStatesGroupTwo.get(i).getState());
                           }
                            
                       }
                    }
                }
            });
        }
        
// GROUP TWO      
        breadboardHolesProperties groupTwo = new breadboardHolesProperties();
        groupTwo.createBreadboardHolesGroupTwo();
        Rectangle[] breadboardHolesGroupTwo = groupTwo.returnBreadboardHoles();
        
        for(int z = 0; z < 325; z++){
            pane.getChildren().add(breadboardHolesGroupTwo[z]);
        }
// whenever a hole is right clicked it should select the hole and change its color      
        for(int l = 0; l < breadboardHolesGroupTwo.length; l++){
            Rectangle currentSquare = breadboardHolesGroupTwo[l];
            breadboardHolesGroupTwo[l].setOnMouseClicked(new EventHandler<MouseEvent>(){
                public void handle(MouseEvent e){
                    if(e.getButton() == MouseButton.PRIMARY){
                        handled.pickingBreadboardHoles(currentSquare, isViewToggled);
                    
// if point view is activated this draws wire by selecting holes only -- needs to be switched where you do not see the wires                 
                        if(e.getButton() == MouseButton.PRIMARY && isViewToggled == false && firstClick == true){
                            liveWire = handled.returnDrawnLiveWire();
                            movable = true;
                            firstClick = false;
                            startX = liveWire.getStartX();
                            startY = liveWire.getStartY();
                            createWireList.add(liveWire);
                            handled.addToListOfWires(liveWire);
                            pane.getChildren().add(liveWire);  
                            
                            toBeCompared[0] = startX;
                            toBeCompared[1] = startY;
                            System.out.println(toBeCompared[1]);

                            pane.setOnMouseMoved(new EventHandler<MouseEvent>(){
                                public void handle(MouseEvent e){
                                if(movable == true){
                                    liveWire.setEndX(e.getSceneX());
                                    liveWire.setEndY(e.getSceneY() - 2);
                                }
                            }});
                        }
                        else if(e.getButton() == MouseButton.PRIMARY && isViewToggled == false && firstClick == false){    
                            movable = false;
                            firstClick = true;
                            endX = handled.returnEndX() + 5;
                            endY = handled.returnEndY() + 5;
                            liveWire.setEndX(endX);
                            liveWire.setEndY(endY);
                            
                            toBeCompared[2] = endX;
                            toBeCompared[3] = endY;
                            
                            System.out.println(toBeCompared[3]);
                            
                            tracking.getNeededInformation(toBeCompared, power, ground, columnStatesGroupOne, columnStatesGroupTwo, createWireList, button);
                            tracking.setStateFromWires();
                            
                            columnStatesGroupOne = tracking.returnGroupOne();
                            columnStatesGroupTwo = tracking.returnGroupTwo();
                            
                            trackingChip.getInformation(columnStatesGroupOne, columnStatesGroupTwo, andChipList, nandChipList, orChipList, xorChipList,
                            norChipList, notChipList, andFourChipList, nandFourChipList);
                            columnStatesGroupOne = trackingChip.columnStatesGroupOneList;
                            columnStatesGroupTwo = trackingChip.columnStatesGroupTwoList;
                            
                            tracking.setStateFromWires();
                            columnStatesGroupOne = trackingChip.columnStatesGroupOneList;
                            columnStatesGroupTwo = trackingChip.columnStatesGroupTwoList;
                            
                            for(int i = 0; i < 65; i++){
                               System.out.println("I[" + (i + 1) + "]: " + columnStatesGroupTwo.get(i).getState());
                            }
                            
                       }
//  if wire view is Toggled this begins to drawn wires in real time            
                        else if(e.getButton() == MouseButton.PRIMARY && isViewToggled == true && firstClick == true){
                            liveWire = handled.returnDrawnLiveWire();
                            movable = true;
                            firstClick = false;
                            startX = liveWire.getStartX();
                            startY = liveWire.getStartY();
                            createWireList.add(liveWire);
                            handled.addToListOfWires(liveWire);
                            pane.getChildren().add(liveWire);  
                            
                            toBeCompared[0] = startX;
                            toBeCompared[1] = startY;
                            System.out.println(toBeCompared[1]);

                            pane.setOnMouseMoved(new EventHandler<MouseEvent>(){
                                public void handle(MouseEvent e){
                                if(movable == true){
                                    liveWire.setEndX(e.getSceneX());
                                    liveWire.setEndY(e.getSceneY() - 2);
                                }
                            }});
                        }


                       else if(e.getButton() == MouseButton.PRIMARY && isViewToggled == true && firstClick == false){    
                            movable = false;
                            firstClick = true;
                            endX = handled.returnEndX() + 5;
                            endY = handled.returnEndY() + 5;
                            liveWire.setEndX(endX);
                            liveWire.setEndY(endY);
                            
                            toBeCompared[2] = endX;
                            toBeCompared[3] = endY;
                            
                            System.out.println(toBeCompared[3]);
                            
                            tracking.getNeededInformation(toBeCompared, power, ground, columnStatesGroupOne, columnStatesGroupTwo, createWireList, button);
                            tracking.setStateFromWires();
                            
                            columnStatesGroupOne = tracking.returnGroupOne();
                            columnStatesGroupTwo = tracking.returnGroupTwo();
                            
                            
                            trackingChip.getInformation(columnStatesGroupOne, columnStatesGroupTwo, andChipList, nandChipList, orChipList, xorChipList,
                            norChipList, notChipList, andFourChipList, nandFourChipList);
                            
                            columnStatesGroupOne = trackingChip.columnStatesGroupOneList;
                            columnStatesGroupTwo = trackingChip.columnStatesGroupTwoList;
                            
                            tracking.setStateFromWires();
                            columnStatesGroupOne = trackingChip.columnStatesGroupOneList;
                            columnStatesGroupTwo = trackingChip.columnStatesGroupTwoList;
                            
                           for(int i = 0; i < 65; i++){
                               System.out.println("I[" + (i + 1) + "]: " + columnStatesGroupTwo.get(i).getState());
                           }
                            
                       }
                    }
                }
            });
        }    
        
        pane.getChildren().addAll(andChip, nandChip, orChip, xorChip, norChip, notChip, andFourChip, nandFourChip);

        Image pointView = new Image("file:images/point.png");
        Image wireView = new Image("file:images/wire.png");
        ImageView view = new ImageView();
        view.setImage(wireView);
        view.setX(25);
        view.setY(10);
        pane.getChildren().add(view);
        view.setOnMouseClicked(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent e){
                if(isViewToggled == false){
                    isViewToggled = true;
                    view.setImage(wireView);
                    
                    for(int i = 0; i < createWireList.size(); i++){
                        createWireList.get(i).setStroke(Color.BLUE);
                    }
                }
                else{
                    isViewToggled = false;
                    view.setImage(pointView);
                    
                    for(int i = 0; i < createWireList.size(); i++){
                        createWireList.get(i).setStroke(Color.rgb(0, 0, 0, 0));
                    }
                    
                }
            } 
        });
        
        Image deleteMode = new Image("file:images/delete.png");
        Image drawMode = new Image("file:images/draw.png");
        ImageView mode = new ImageView();
        mode.setImage(drawMode);
        mode.setX(115);
        mode.setY(10);
        pane.getChildren().add(mode);
        mode.setOnMouseClicked(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent e){
                if(setMode == false){
                    setMode = true;
                    mode.setImage(deleteMode);
                }
                else{
                    setMode = false;
                    mode.setImage(drawMode);
                }
            } 
        });
// OUTPUTS
        for(int i = 0; i < 8; i++){
            outputButtons outs = new outputButtons();
            Circle output = outs.buttonSpecs(i);

            pane.getChildren().add(output);
        }  
        
// BUTTON PRESSES        
        for(int i = 0; i < 8; i++){
            inputButtons buttons = new inputButtons();
            Circle input = buttons.buttonSpecs(i);
            
            input.setOnMouseClicked(new EventHandler<MouseEvent>(){
                public void handle(MouseEvent e){
                    buttons.setState();
                    if(buttons.getState() == 1){
                        input.setFill(Color.BLACK);
                    }
                    else
                        input.setFill(Color.GREY);
                    
                    tracking.getNeededInformation(toBeCompared, power, ground, columnStatesGroupOne, columnStatesGroupTwo, createWireList, button);
                    tracking.setStateFromButton(buttons.getState(), input.getCenterX(), createWireList, button);
                    tracking.setStateFromWires();
                    
                    columnStatesGroupOne = tracking.returnGroupOne();
                    columnStatesGroupTwo = tracking.returnGroupTwo();
                    
                    trackingChip.getInformation(columnStatesGroupOne, columnStatesGroupTwo, andChipList, nandChipList, orChipList, xorChipList,
                        norChipList, notChipList, andFourChipList, nandFourChipList);
                    
                    columnStatesGroupOne = trackingChip.columnStatesGroupOneList;
                    columnStatesGroupTwo = trackingChip.columnStatesGroupTwoList;
                    
                    tracking.setStateFromWires();
                    columnStatesGroupOne = trackingChip.columnStatesGroupOneList;
                    columnStatesGroupTwo = trackingChip.columnStatesGroupTwoList;
                    
                    for(int i = 0; i < 65; i++){
                               System.out.println("I[" + (i + 1) + "]: " + columnStatesGroupTwo.get(i).getState());
                           }
                    
                    
                }
            });
            
            pane.getChildren().add(input);
        }       

// AND        
        andChip.setOnMousePressed(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent e){    
                if(e.getButton() == MouseButton.PRIMARY && setMode == false){
                    createChip chi = new createChip();
                    createChip.createAndChip createAndChip = chi.new createAndChip();
                    
                    displayCurrentChip = createAndChip.createShape();
                    pane.getChildren().add(displayCurrentChip);
                    
                    andChipList.add(createAndChip);
                    chipList.add(displayCurrentChip);
                    int i = chipList.size() - 1;
 
                    
                    chipList.get(i).setOnMouseReleased(new EventHandler<MouseEvent>(){
                       public void handle(MouseEvent e){
                        if(e.getButton() == MouseButton.PRIMARY && setMode == false){   
                           chipList.get(i).setX(handled.lineUpChipX((int)e.getSceneX()));
                           chipList.get(i).setY(handled.lineUpChipY((int)e.getSceneY()));
                           
                           trackingChip.getInformation(columnStatesGroupOne, columnStatesGroupTwo, andChipList, nandChipList, orChipList, xorChipList,
                            norChipList, notChipList, andFourChipList, nandFourChipList);
                            columnStatesGroupOne = trackingChip.columnStatesGroupOneList;
                            columnStatesGroupTwo = trackingChip.columnStatesGroupTwoList;
                           for(int i = 0; i < 65; i++){
                               System.out.println("I[" + (i + 1) + "]: " + columnStatesGroupTwo.get(i).getState());
                           }
                           
                       } 
                       }
                    });
                }
            }
        });
        andChip.setOnMouseDragged(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent e){  
                if(e.getButton() == MouseButton.PRIMARY){
                    displayCurrentChip.setX(e.getSceneX());
                    displayCurrentChip.setY(e.getSceneY());
                }
            }
        });
        andChip.setOnMouseReleased(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent e){
                if(e.getButton() == MouseButton.PRIMARY && setMode == false){
                    displayCurrentChip.setX(handled.lineUpChipX((int)e.getSceneX()));
                    displayCurrentChip.setY(handled.lineUpChipY((int)e.getSceneY())); 
                    
                    trackingChip.getInformation(columnStatesGroupOne, columnStatesGroupTwo, andChipList, nandChipList, orChipList, xorChipList,
                            norChipList, notChipList, andFourChipList, nandFourChipList);
                    columnStatesGroupOne = trackingChip.columnStatesGroupOneList;
                    columnStatesGroupTwo = trackingChip.columnStatesGroupTwoList;
                           for(int i = 0; i < 65; i++){
                               System.out.println("I[" + (i + 1) + "]: " + columnStatesGroupTwo.get(i).getState());
                           }
                } 
                
            }
        });
        
                
// NAND  
        nandChip.setOnMousePressed(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent e){    
                if(e.getButton() == MouseButton.PRIMARY){
                    createChip nand = new createChip();
                    createChip.createNandChip createNandChip = nand.new createNandChip();
                    
                    displayCurrentChip = createNandChip.createShape();
                    pane.getChildren().add(displayCurrentChip);
                    
                    nandChipList.add(createNandChip);
                    chipList.add(displayCurrentChip);
                    int i = chipList.size() - 1;
                    
                    chipList.get(i).setOnMouseClicked(new EventHandler<MouseEvent>(){
                       public void handle(MouseEvent e){
                           if(e.getButton() == MouseButton.SECONDARY){
                              pane.getChildren().remove(chipList.get(i)); 
                              andChipList.remove(i);
                           }
                       } 
                    });
                    
                    chipList.get(i).setOnMouseReleased(new EventHandler<MouseEvent>(){
                       public void handle(MouseEvent e){
                           chipList.get(i).setX(handled.lineUpChipX((int)e.getSceneX()));
                           chipList.get(i).setY(handled.lineUpChipY((int)e.getSceneY()));
                           
                           trackingChip.getInformation(columnStatesGroupOne, columnStatesGroupTwo, andChipList, nandChipList, orChipList, xorChipList,
                            norChipList, notChipList, andFourChipList, nandFourChipList);
                    columnStatesGroupOne = trackingChip.columnStatesGroupOneList;
                    columnStatesGroupTwo = trackingChip.columnStatesGroupTwoList;
                           for(int i = 0; i < 65; i++){
                               System.out.println("I[" + (i + 1) + "]: " + columnStatesGroupTwo.get(i).getState());
                           }
                       } 
                    });
                }
            }
        });
        nandChip.setOnMouseDragged(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent e){  
                if(e.getButton() == MouseButton.PRIMARY){
                    displayCurrentChip.setX(e.getSceneX());
                    displayCurrentChip.setY(e.getSceneY());
                }
            }
        });
        nandChip.setOnMouseReleased(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent e){
                if(e.getButton() == MouseButton.PRIMARY){
                    displayCurrentChip.setX(handled.lineUpChipX((int)e.getSceneX()));
                    displayCurrentChip.setY(handled.lineUpChipY((int)e.getSceneY())); 
                    
                    trackingChip.getInformation(columnStatesGroupOne, columnStatesGroupTwo, andChipList, nandChipList, orChipList, xorChipList,
                            norChipList, notChipList, andFourChipList, nandFourChipList);
                    columnStatesGroupOne = trackingChip.columnStatesGroupOneList;
                    columnStatesGroupTwo = trackingChip.columnStatesGroupTwoList;
                           for(int i = 0; i < 65; i++){
                               System.out.println("I[" + (i + 1) + "]: " + columnStatesGroupTwo.get(i).getState());
                           }
                }
            }
        });
        
// OR        
        orChip.setOnMousePressed(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent e){    
                if(e.getButton() == MouseButton.PRIMARY){
                    createChip or = new createChip();
                    createChip.createOrChip createOrChip = or.new createOrChip();
                    
                    displayCurrentChip = createOrChip.createShape();
                    pane.getChildren().add(displayCurrentChip);
                    
                    orChipList.add(createOrChip);
                    chipList.add(displayCurrentChip);
                    int i = chipList.size() - 1;
                    
                    chipList.get(i).setOnMouseClicked(new EventHandler<MouseEvent>(){
                       public void handle(MouseEvent e){
                           if(e.getButton() == MouseButton.SECONDARY){
                              pane.getChildren().remove(chipList.get(i)); 
                              andChipList.remove(i);
                           }
                       } 
                    });
                    
                    chipList.get(i).setOnMouseReleased(new EventHandler<MouseEvent>(){
                       public void handle(MouseEvent e){
                           chipList.get(i).setX(handled.lineUpChipX((int)e.getSceneX()));
                           chipList.get(i).setY(handled.lineUpChipY((int)e.getSceneY()));
                           
                           trackingChip.getInformation(columnStatesGroupOne, columnStatesGroupTwo, andChipList, nandChipList, orChipList, xorChipList,
                            norChipList, notChipList, andFourChipList, nandFourChipList);
                    columnStatesGroupOne = trackingChip.columnStatesGroupOneList;
                    columnStatesGroupTwo = trackingChip.columnStatesGroupTwoList;
                           for(int i = 0; i < 65; i++){
                               System.out.println("I[" + (i + 1) + "]: " + columnStatesGroupTwo.get(i).getState());
                           }
                       } 
                    });
                }
            }
        });
        orChip.setOnMouseDragged(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent e){  
                if(e.getButton() == MouseButton.PRIMARY){
                    displayCurrentChip.setX(e.getSceneX());
                    displayCurrentChip.setY(e.getSceneY());
                }
            }
        });
        orChip.setOnMouseReleased(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent e){
                if(e.getButton() == MouseButton.PRIMARY){
                    displayCurrentChip.setX(handled.lineUpChipX((int)e.getSceneX()));
                    displayCurrentChip.setY(handled.lineUpChipY((int)e.getSceneY())); 
                    
                    trackingChip.getInformation(columnStatesGroupOne, columnStatesGroupTwo, andChipList, nandChipList, orChipList, xorChipList,
                            norChipList, notChipList, andFourChipList, nandFourChipList);
                    columnStatesGroupOne = trackingChip.columnStatesGroupOneList;
                    columnStatesGroupTwo = trackingChip.columnStatesGroupTwoList;
                           for(int i = 0; i < 65; i++){
                               System.out.println("I[" + (i + 1) + "]: " + columnStatesGroupTwo.get(i).getState());
                           }
                }
            }
        });

// XOR
        xorChip.setOnMousePressed(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent e){    
                if(e.getButton() == MouseButton.PRIMARY){
                    createChip xor = new createChip();
                    createChip.createXorChip createXorChip = xor.new createXorChip();
                    
                    displayCurrentChip = createXorChip.createShape();
                    pane.getChildren().add(displayCurrentChip);
                    
                    xorChipList.add(createXorChip);
                    chipList.add(displayCurrentChip);
                    int i = chipList.size() - 1;
                    
                    chipList.get(i).setOnMouseClicked(new EventHandler<MouseEvent>(){
                       public void handle(MouseEvent e){
                           if(e.getButton() == MouseButton.SECONDARY){
                              pane.getChildren().remove(chipList.get(i)); 
                              andChipList.remove(i);
                           }
                       } 
                    });
                    
                    chipList.get(i).setOnMouseReleased(new EventHandler<MouseEvent>(){
                       public void handle(MouseEvent e){
                           chipList.get(i).setX(handled.lineUpChipX((int)e.getSceneX()));
                           chipList.get(i).setY(handled.lineUpChipY((int)e.getSceneY()));
                           
                          trackingChip.getInformation(columnStatesGroupOne, columnStatesGroupTwo, andChipList, nandChipList, orChipList, xorChipList,
                            norChipList, notChipList, andFourChipList, nandFourChipList);
                    columnStatesGroupOne = trackingChip.columnStatesGroupOneList;
                    columnStatesGroupTwo = trackingChip.columnStatesGroupTwoList;
                           for(int i = 0; i < 65; i++){
                               System.out.println("I[" + (i + 1) + "]: " + columnStatesGroupTwo.get(i).getState());
                           }
                       } 
                    });
                }
            }
        });
        xorChip.setOnMouseDragged(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent e){  
                if(e.getButton() == MouseButton.PRIMARY){
                    displayCurrentChip.setX(e.getSceneX());
                    displayCurrentChip.setY(e.getSceneY());
                }
            }
        });
        xorChip.setOnMouseReleased(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent e){
                if(e.getButton() == MouseButton.PRIMARY){
                    displayCurrentChip.setX(handled.lineUpChipX((int)e.getSceneX()));
                    displayCurrentChip.setY(handled.lineUpChipY((int)e.getSceneY())); 
                    
                    trackingChip.getInformation(columnStatesGroupOne, columnStatesGroupTwo, andChipList, nandChipList, orChipList, xorChipList,
                            norChipList, notChipList, andFourChipList, nandFourChipList);
                    columnStatesGroupOne = trackingChip.columnStatesGroupOneList;
                    columnStatesGroupTwo = trackingChip.columnStatesGroupTwoList;
                           for(int i = 0; i < 65; i++){
                               System.out.println("I[" + (i + 1) + "]: " + columnStatesGroupTwo.get(i).getState());
                           }
                }
            }
        });

// NOR        
        norChip.setOnMousePressed(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent e){    
                if(e.getButton() == MouseButton.PRIMARY){
                    createChip nor = new createChip();
                    createChip.createNorChip createNorChip = nor.new createNorChip();
                    
                    displayCurrentChip = createNorChip.createShape();
                    pane.getChildren().add(displayCurrentChip);
                    
                    norChipList.add(createNorChip);
                    chipList.add(displayCurrentChip);
                    int i = chipList.size() - 1;
                    
                    chipList.get(i).setOnMouseClicked(new EventHandler<MouseEvent>(){
                       public void handle(MouseEvent e){
                           if(e.getButton() == MouseButton.SECONDARY){
                              pane.getChildren().remove(chipList.get(i)); 
                              andChipList.remove(i);
                           }
                       } 
                    });
                    
                    chipList.get(i).setOnMouseReleased(new EventHandler<MouseEvent>(){
                       public void handle(MouseEvent e){
                           chipList.get(i).setX(handled.lineUpChipX((int)e.getSceneX()));
                           chipList.get(i).setY(handled.lineUpChipY((int)e.getSceneY()));
                           
                           trackingChip.getInformation(columnStatesGroupOne, columnStatesGroupTwo, andChipList, nandChipList, orChipList, xorChipList,
                            norChipList, notChipList, andFourChipList, nandFourChipList);
                    columnStatesGroupOne = trackingChip.columnStatesGroupOneList;
                    columnStatesGroupTwo = trackingChip.columnStatesGroupTwoList;
                           for(int i = 0; i < 65; i++){
                               System.out.println("I[" + (i + 1) + "]: " + columnStatesGroupTwo.get(i).getState());
                           }
                       } 
                    });
                }
            }
        });
        norChip.setOnMouseDragged(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent e){  
                if(e.getButton() == MouseButton.PRIMARY){
                    displayCurrentChip.setX(e.getSceneX());
                    displayCurrentChip.setY(e.getSceneY());
                }
            }
        });
        norChip.setOnMouseReleased(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent e){
                if(e.getButton() == MouseButton.PRIMARY){
                    displayCurrentChip.setX(handled.lineUpChipX((int)e.getSceneX()));
                    displayCurrentChip.setY(handled.lineUpChipY((int)e.getSceneY())); 
                    
                    trackingChip.getInformation(columnStatesGroupOne, columnStatesGroupTwo, andChipList, nandChipList, orChipList, xorChipList,
                            norChipList, notChipList, andFourChipList, nandFourChipList);
                    columnStatesGroupOne = trackingChip.columnStatesGroupOneList;
                    columnStatesGroupTwo = trackingChip.columnStatesGroupTwoList;
                           for(int i = 0; i < 65; i++){
                               System.out.println("I[" + (i + 1) + "]: " + columnStatesGroupTwo.get(i).getState());
                           }
                }
            }
        });

// NOT
        notChip.setOnMousePressed(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent e){    
                if(e.getButton() == MouseButton.PRIMARY){
                    createChip not = new createChip();
                    createChip.createNotChip createNotChip = not.new createNotChip();
                    
                    displayCurrentChip = createNotChip.createShape();
                    pane.getChildren().add(displayCurrentChip);
                    
                    notChipList.add(createNotChip);
                    chipList.add(displayCurrentChip);
                    int i = chipList.size() - 1;
                    
                    chipList.get(i).setOnMouseClicked(new EventHandler<MouseEvent>(){
                       public void handle(MouseEvent e){
                           if(e.getButton() == MouseButton.SECONDARY){
                              pane.getChildren().remove(chipList.get(i)); 
                              andChipList.remove(i);
                           }
                       } 
                    });
                    
                    chipList.get(i).setOnMouseReleased(new EventHandler<MouseEvent>(){
                       public void handle(MouseEvent e){
                           chipList.get(i).setX(handled.lineUpChipX((int)e.getSceneX()));
                           chipList.get(i).setY(handled.lineUpChipY((int)e.getSceneY()));
                           
                           trackingChip.getInformation(columnStatesGroupOne, columnStatesGroupTwo, andChipList, nandChipList, orChipList, xorChipList,
                            norChipList, notChipList, andFourChipList, nandFourChipList);
                    columnStatesGroupOne = trackingChip.columnStatesGroupOneList;
                    columnStatesGroupTwo = trackingChip.columnStatesGroupTwoList;
                           for(int i = 0; i < 65; i++){
                               System.out.println("I[" + (i + 1) + "]: " + columnStatesGroupTwo.get(i).getState());
                           }
                       } 
                    });
                }
            }
        });
        notChip.setOnMouseDragged(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent e){  
                if(e.getButton() == MouseButton.PRIMARY){
                    displayCurrentChip.setX(e.getSceneX());
                    displayCurrentChip.setY(e.getSceneY());
                }
            }
        });
        notChip.setOnMouseReleased(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent e){
                if(e.getButton() == MouseButton.PRIMARY){
                    displayCurrentChip.setX(handled.lineUpChipX((int)e.getSceneX()));
                    displayCurrentChip.setY(handled.lineUpChipY((int)e.getSceneY())); 
                    
                    trackingChip.getInformation(columnStatesGroupOne, columnStatesGroupTwo, andChipList, nandChipList, orChipList, xorChipList,
                            norChipList, notChipList, andFourChipList, nandFourChipList);
                    columnStatesGroupOne = trackingChip.columnStatesGroupOneList;
                    columnStatesGroupTwo = trackingChip.columnStatesGroupTwoList;
                           for(int i = 0; i < 65; i++){
                               System.out.println("I[" + (i + 1) + "]: " + columnStatesGroupTwo.get(i).getState());
                           }
                }
            }
        });
        
// NAND FOUR
        nandFourChip.setOnMousePressed(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent e){    
                if(e.getButton() == MouseButton.PRIMARY){
                    createChip nandFour = new createChip();
                    createChip.createNandFourChip createNandFourChip = nandFour.new createNandFourChip();
                    
                    displayCurrentChip = createNandFourChip.createShape();
                    pane.getChildren().add(displayCurrentChip);
                    
                    nandFourChipList.add(createNandFourChip);
                    chipList.add(displayCurrentChip);
                    int i = chipList.size() - 1;
                    
                    chipList.get(i).setOnMouseClicked(new EventHandler<MouseEvent>(){
                       public void handle(MouseEvent e){
                           if(e.getButton() == MouseButton.SECONDARY){
                              pane.getChildren().remove(chipList.get(i)); 
                              andChipList.remove(i);
                           }
                       } 
                    });
                    
                    chipList.get(i).setOnMouseReleased(new EventHandler<MouseEvent>(){
                       public void handle(MouseEvent e){
                           chipList.get(i).setX(handled.lineUpChipX((int)e.getSceneX()));
                           chipList.get(i).setY(handled.lineUpChipY((int)e.getSceneY()));
                           
                           trackingChip.getInformation(columnStatesGroupOne, columnStatesGroupTwo, andChipList, nandChipList, orChipList, xorChipList,
                            norChipList, notChipList, andFourChipList, nandFourChipList);
                    columnStatesGroupOne = trackingChip.columnStatesGroupOneList;
                    columnStatesGroupTwo = trackingChip.columnStatesGroupTwoList;
                           for(int i = 0; i < 65; i++){
                               System.out.println("I[" + (i + 1) + "]: " + columnStatesGroupTwo.get(i).getState());
                           }
                       } 
                    });
                }
            }
        });
        nandFourChip.setOnMouseDragged(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent e){  
                if(e.getButton() == MouseButton.PRIMARY){
                    displayCurrentChip.setX(e.getSceneX());
                    displayCurrentChip.setY(e.getSceneY());
                }
            }
        });
        nandFourChip.setOnMouseReleased(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent e){
                if(e.getButton() == MouseButton.PRIMARY){
                    displayCurrentChip.setX(handled.lineUpChipX((int)e.getSceneX()));
                    displayCurrentChip.setY(handled.lineUpChipY((int)e.getSceneY())); 
                    
                    trackingChip.getInformation(columnStatesGroupOne, columnStatesGroupTwo, andChipList, nandChipList, orChipList, xorChipList,
                            norChipList, notChipList, andFourChipList, nandFourChipList);
                    columnStatesGroupOne = trackingChip.columnStatesGroupOneList;
                    columnStatesGroupTwo = trackingChip.columnStatesGroupTwoList;
                           for(int i = 0; i < 65; i++){
                               System.out.println("I[" + (i + 1) + "]: " + columnStatesGroupTwo.get(i).getState());
                           }
                }
            }
        });
        
// AND FOUR      
        andFourChip.setOnMousePressed(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent e){    
                if(e.getButton() == MouseButton.PRIMARY){
                    createChip andFour = new createChip();
                    createChip.createAndFourChip createAndFourChip = andFour.new createAndFourChip();
                    
                    displayCurrentChip = createAndFourChip.createShape();
                    pane.getChildren().add(displayCurrentChip);
                    
                    andFourChipList.add(createAndFourChip);
                    chipList.add(displayCurrentChip);
                    int i = chipList.size() - 1;
                    
                    chipList.get(i).setOnMouseClicked(new EventHandler<MouseEvent>(){
                       public void handle(MouseEvent e){
                           if(e.getButton() == MouseButton.SECONDARY){
                              pane.getChildren().remove(chipList.get(i)); 
                              andChipList.remove(i);
                           }
                       } 
                    });
                    
                    chipList.get(i).setOnMouseReleased(new EventHandler<MouseEvent>(){
                       public void handle(MouseEvent e){
                           chipList.get(i).setX(handled.lineUpChipX((int)e.getSceneX()));
                           chipList.get(i).setY(handled.lineUpChipY((int)e.getSceneY()));
                           
                           trackingChip.getInformation(columnStatesGroupOne, columnStatesGroupTwo, andChipList, nandChipList, orChipList, xorChipList,
                            norChipList, notChipList, andFourChipList, nandFourChipList);
                    columnStatesGroupOne = trackingChip.columnStatesGroupOneList;
                    columnStatesGroupTwo = trackingChip.columnStatesGroupTwoList;
                           for(int i = 0; i < 65; i++){
                               System.out.println("I[" + (i + 1) + "]: " + columnStatesGroupTwo.get(i).getState());
                           }
                           
                       } 
                    });
                }
            }
        });
        andFourChip.setOnMouseDragged(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent e){  
                if(e.getButton() == MouseButton.PRIMARY){
                    displayCurrentChip.setX(e.getSceneX());
                    displayCurrentChip.setY(e.getSceneY());
                }
            }
        });
        andFourChip.setOnMouseReleased(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent e){
                if(e.getButton() == MouseButton.PRIMARY){
                    displayCurrentChip.setX(handled.lineUpChipX((int)e.getSceneX()));
                    displayCurrentChip.setY(handled.lineUpChipY((int)e.getSceneY())); 
                    
                    trackingChip.getInformation(columnStatesGroupOne, columnStatesGroupTwo, andChipList, nandChipList, orChipList, xorChipList,
                            norChipList, notChipList, andFourChipList, nandFourChipList);
                    columnStatesGroupOne = trackingChip.columnStatesGroupOneList;
                    columnStatesGroupTwo = trackingChip.columnStatesGroupTwoList;
                           for(int i = 0; i < 65; i++){
                               System.out.println("I[" + (i + 1) + "]: " + columnStatesGroupTwo.get(i).getState());
                           }
                }
            }
        });        
        
        
        
        Scene scene = new Scene(pane, 1300, 800, true);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    
}

