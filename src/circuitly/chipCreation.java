package circuitly;

import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class chipCreation {
    chipTracker trackingChip = new chipTracker();
    eventHandling handled = new eventHandling();
    ArrayList <Rectangle> createdChipList = new ArrayList<>();
    boolean isLeftClick = false;
    
    public Rectangle createChip(){
        Rectangle rec = new Rectangle();
        rec.setX(500);
        rec.setY(500);
        rec.setHeight(40);
        rec.setWidth(100);
        rec.setFill(Color.BLACK);
        
        
        rec.setOnMousePressed(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent e){    
                createChip();
                
            }
        });
        
        rec.setOnMouseDragged(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent e){    
                rec.setX(e.getSceneX());
                rec.setY(e.getSceneY());
            }
        });
        
        rec.setOnMouseReleased(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent e){
                rec.setX(handled.lineUpChipX((int)e.getSceneX()));
                rec.setY(handled.lineUpChipY((int)e.getSceneY()));
            }
        });
        
        
        
        return rec;
    }
    
    public Text createChipText(){
        Text text = new Text();
        text.setX(500);
        text.setY(500);
        text.setFont(new Font(20));
        text.setText("testing");
        
        text.setOnMousePressed(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent e){    
                createChipText();
                
            }
        });
        
        text.setOnMouseDragged(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent e){    
                text.setX(e.getSceneX());
                text.setY(e.getSceneY());
            }
        });
        
        text.setOnMouseReleased(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent e){
                text.setX(handled.lineUpChipX((int)e.getSceneX()));
                text.setY(handled.lineUpChipY((int)e.getSceneY()));
            }
        });
        
        return text;
    }
    
    public Rectangle createSetChip(){
        Rectangle rec = new Rectangle();
        rec.setX(500);
        rec.setY(500);
        rec.setHeight(40);
        rec.setWidth(100);
        rec.setFill(Color.GREY);
        return rec;
    }
    
}
