package circuitly;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class breadboardHolesProperties {
    private int timesClicked;
    private double[] first = new double[2];
    private double[] second = new double[2];
    Rectangle[] breadboardHoles = new Rectangle[325];
    Rectangle[] breadboardPowerHoles = new Rectangle[65];
    Rectangle[] breadboardGroundHoles = new Rectangle[65];
    Rectangle[] breadboardButtonHoles = new Rectangle[16];
    Rectangle[] breadboardButtonHoles2 = new Rectangle[16];
    
    private Rectangle breadboardSqaures(int x, int y){
        Rectangle rec = new Rectangle();
        rec.setX(x);
        rec.setY(y);
        rec.setHeight(10);
        rec.setWidth(10);
        rec.setFill(Color.LIGHTGREY);
        return rec;
    }
    
    private Rectangle breadboardPowerSqaures(int x){
        Rectangle rec = new Rectangle();
        rec.setX(x);
        rec.setY(100);
        rec.setHeight(10);
        rec.setWidth(10);
        rec.setFill(Color.LIGHTPINK);
        return rec;
    }
    
    private Rectangle breadboardGroundSqaures(int x){
        Rectangle rec = new Rectangle();
        rec.setX(x);
        rec.setY(115);
        rec.setHeight(10);
        rec.setWidth(10);
        rec.setFill(Color.LIGHTSTEELBLUE);
        return rec;
    }    
    
    public Rectangle[] returnBreadboardHoles(){
        return breadboardHoles;
    }
    
    public Rectangle[] returnButtonHoles(){
        return breadboardButtonHoles;
    }
    
    public Rectangle[] returnButtonHoles2(){
        return breadboardButtonHoles2;
    }
    
    
    public Rectangle[] returnPowerRow(){
        return breadboardPowerHoles;
    }
    
    public Rectangle[] returnGroundRow(){
        return breadboardGroundHoles;
    }
    
    public void createButtonRow(){
        int l = 0;
        
        while(l < 16){
            for (int i = 0; i < 8; i++) {
                for(int j = 0; j < 2; j++){
                    breadboardButtonHoles[l] = breadboardSqaures(735 + i * 35, 45 + j * 15);
                    l++;
                } // end of for
            } // end of for
        } //end of while
    }
    
    public void createPowerRow(){
        for (int i = 0; i < 65; i++) {
                    breadboardPowerHoles[i] = breadboardPowerSqaures(25 + i * 15);
            } // end of for
    }
    public void createGroundRow(){
        for (int i = 0; i < 65; i++) {
                    breadboardGroundHoles[i] = breadboardGroundSqaures(25 + i * 15);
            } // end of for
    }
    public void createBreadboardHolesGroupOne(){
        int l = 0;
        
        while(l < 325){
            for (int i = 0; i < 65; i++) {
                for(int j = 0; j < 5; j++){
                    breadboardHoles[l] = breadboardSqaures(25 + i * 15, 150 + j * 15);
                    l++;
                } // end of for
            } // end of for
        } //end of while
    }
    public void createBreadboardHolesGroupTwo(){
        int l = 0;
        
        while(l < 325){
            for (int i = 0; i < 65; i++) {
                for(int j = 0; j < 5; j++){
                    breadboardHoles[l] = breadboardSqaures(25 + i * 15, 240 + j * 15);
                    l++;
                } // end of for
            } // end of for
        } //end of while
    }
    
    public void createButtonRow2(){
        int l = 0;
        
        while(l < 16){
            for (int i = 0; i < 8; i++) {
                for(int j = 0; j < 2; j++){
                    breadboardButtonHoles2[l] = breadboardSqaures(730 + i * 35, 550 + j * 15);
                    l++;
                } // end of for
            } // end of for
        } //end of while
    }
    
    
    
    
}
