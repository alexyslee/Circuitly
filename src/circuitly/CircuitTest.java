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
    
    ArrayList <Line> createWireList = new ArrayList<>();
    
    ArrayList <stateTracker.columnCreationGroupOne> columnStatesGroupOne = new ArrayList<>();
    ArrayList <stateTracker.columnCreationGroupTwo> columnStatesGroupTwo = new ArrayList<>();
    ArrayList <stateTracker.columnCreationPower> power = new ArrayList<>();
    ArrayList <stateTracker.columnCreationGround> ground = new ArrayList<>();
    
    
    boolean isViewToggled = false;          // false is point view -- true is wired view
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
        
        

// creating row of power
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
                            
                            tracking.getNeededInformation(toBeCompared, power, ground, columnStatesGroupOne, columnStatesGroupTwo);
                            tracking.calculateState();
                            
                            power = tracking.returnUpdatedPowerRow();
                            columnStatesGroupOne = tracking.returnGroupOne();
                            ground = tracking.returnGround();
                            
                            trackingChip.getInformation(columnStatesGroupOne, columnStatesGroupTwo, andChipList, nandChipList);
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
                            
                            tracking.getNeededInformation(toBeCompared, power, ground, columnStatesGroupOne, columnStatesGroupTwo);
                            tracking.calculateState();
                            
                            power = tracking.returnUpdatedPowerRow();
                            columnStatesGroupOne = tracking.returnGroupOne();
                            ground = tracking.returnGround();
                            
                            trackingChip.getInformation(columnStatesGroupOne, columnStatesGroupTwo, andChipList, nandChipList);
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
        
// creating row of ground
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
                            
                            tracking.getNeededInformation(toBeCompared, power, ground, columnStatesGroupOne, columnStatesGroupTwo);
                            tracking.calculateState();
                            
                            power = tracking.returnUpdatedPowerRow();
                            columnStatesGroupOne = tracking.returnGroupOne();
                            ground = tracking.returnGround();
                            
                            trackingChip.getInformation(columnStatesGroupOne, columnStatesGroupTwo, andChipList, nandChipList);
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
                            
                            tracking.getNeededInformation(toBeCompared, power, ground, columnStatesGroupOne, columnStatesGroupTwo);
                            tracking.calculateState();
                            
                            power = tracking.returnUpdatedPowerRow();
                            columnStatesGroupOne = tracking.returnGroupOne();
                            ground = tracking.returnGround();
                            
                            trackingChip.getInformation(columnStatesGroupOne, columnStatesGroupTwo, andChipList, nandChipList);
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
        
        
        
// creating the first set of breadboard holes
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
                            
                            tracking.getNeededInformation(toBeCompared, power, ground, columnStatesGroupOne, columnStatesGroupTwo);
                            tracking.calculateState();
                            
                            power = tracking.returnUpdatedPowerRow();
                            columnStatesGroupOne = tracking.returnGroupOne();
                            ground = tracking.returnGround();
                            
                            trackingChip.getInformation(columnStatesGroupOne, columnStatesGroupTwo, andChipList, nandChipList);
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
                            
                            tracking.getNeededInformation(toBeCompared, power, ground, columnStatesGroupOne, columnStatesGroupTwo);
                            tracking.calculateState();
                            
                            power = tracking.returnUpdatedPowerRow();
                            columnStatesGroupOne = tracking.returnGroupOne();
                            ground = tracking.returnGround();
                            
                            trackingChip.getInformation(columnStatesGroupOne, columnStatesGroupTwo, andChipList, nandChipList);
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
        
// creating second set of breadbaord holes        
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
                            
                            tracking.getNeededInformation(toBeCompared, power, ground, columnStatesGroupOne, columnStatesGroupTwo);
                            tracking.calculateState();
                            
                            power = tracking.returnUpdatedPowerRow();
                            columnStatesGroupOne = tracking.returnGroupOne();
                            ground = tracking.returnGround();
                            
                            trackingChip.getInformation(columnStatesGroupOne, columnStatesGroupTwo, andChipList, nandChipList);
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
                            
                            tracking.getNeededInformation(toBeCompared, power, ground, columnStatesGroupOne, columnStatesGroupTwo);
                            tracking.calculateState();
                            
                            power = tracking.returnUpdatedPowerRow();
                            columnStatesGroupOne = tracking.returnGroupOne();
                            ground = tracking.returnGround();
                            
                            trackingChip.getInformation(columnStatesGroupOne, columnStatesGroupTwo, andChipList, nandChipList);
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
        
        pane.getChildren().addAll(andChip, nandChip, orChip);

        Image pointView = new Image("file:images/point.png");
        Image wireView = new Image("file:images/wire.png");
        ImageView view = new ImageView();
        view.setImage(pointView);
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
                    isViewToggled = false;
                    mode.setImage(drawMode);
                }
            } 
        });
        
        
//        for(int i = 0; i < 8; i++){
//            inputButtons buttons = new inputButtons();
//            Circle input = buttons.buttonSpecs(i);
//            
//            input.setOnMouseClicked(new EventHandler<MouseEvent>(){
//                public void handle(MouseEvent e){  
//                    if(buttons.state == 0){
//                        input.setFill(Color.RED);
//                        buttons.setState();
//                    }
//                    else{
//                        input.setFill(Color.GREY);
//                        buttons.setState();
//                    }
//                }
//            });
//            
//            pane.getChildren().add(input);
//        }       

// AND        
        andChip.setOnMousePressed(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent e){    
                if(e.getButton() == MouseButton.PRIMARY){
                    createChip chi = new createChip();
                    createChip.createAndChip createAndChip = chi.new createAndChip();
                    
                    displayCurrentChip = createAndChip.createShape();
                    pane.getChildren().add(displayCurrentChip);
                    
                    andChipList.add(createAndChip);
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
                           
                           trackingChip.getInformation(columnStatesGroupOne, columnStatesGroupTwo, andChipList, nandChipList);
                           columnStatesGroupOne = trackingChip.columnStatesGroupOneList;
                           for(int i = 0; i < 65; i++){
                               System.out.println("I[" + (i + 1) + "]: " + columnStatesGroupOne.get(i).getState());
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
                if(e.getButton() == MouseButton.PRIMARY){
                    displayCurrentChip.setX(handled.lineUpChipX((int)e.getSceneX()));
                    displayCurrentChip.setY(handled.lineUpChipY((int)e.getSceneY())); 
                    
                    trackingChip.getInformation(columnStatesGroupOne, columnStatesGroupTwo, andChipList, nandChipList);
                    columnStatesGroupOne = trackingChip.columnStatesGroupOneList;
                           for(int i = 0; i < 65; i++){
                               System.out.println("I[" + (i + 1) + "]: " + columnStatesGroupOne.get(i).getState());
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
                           
                           trackingChip.getInformation(columnStatesGroupOne, columnStatesGroupTwo, andChipList, nandChipList);
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
                    
                    trackingChip.getInformation(columnStatesGroupOne, columnStatesGroupTwo, andChipList, nandChipList);
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
                           
                           trackingChip.getInformation(columnStatesGroupOne, columnStatesGroupTwo, andChipList, nandChipList);
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
                    
                    trackingChip.getInformation(columnStatesGroupOne, columnStatesGroupTwo, andChipList, nandChipList);
                    columnStatesGroupOne = trackingChip.columnStatesGroupOneList;
                    columnStatesGroupTwo = trackingChip.columnStatesGroupTwoList;
                           for(int i = 0; i < 65; i++){
                               System.out.println("I[" + (i + 1) + "]: " + columnStatesGroupTwo.get(i).getState());
                           }
                }
            }
        });        
        
        Scene scene = new Scene(pane, 1024, 800, true);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    
}

