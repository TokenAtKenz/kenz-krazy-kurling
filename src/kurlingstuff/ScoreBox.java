/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kurlingstuff;

import java.util.Iterator;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

/**
 * 
 *
 * @author TokenAtKenz
 */
public class ScoreBox extends Group{
   Group scoreGroup;
   final int aFoot;
   final int padding;
   //Group extraGroup;
   //Group totalsGroup;
public ScoreBox(KurlParams params){
    super();
    aFoot = (int) params.get("footSize");
    padding = (int)params.get("padding");
    addChildren();
    //clearTheBoard();
} 

private void addChildren(){
    getChildren().add(getScoreGroup(11,aFoot));
    getChildren().add(getTotalGroup(aFoot,padding)); 
}

public void clearTheBoard(){
    String s;
    for(int i=1;i<12;i++){
         s = "red box "+i;
         setText(s,"",Color.WHITE);
         s = "yellow box "+i;
         setText(s,"",Color.BLACK);
         s = "end box "+i;
         if(i<11){setText(s,(""+i),Color.BLACK);}
         else{setText(s,"X",Color.BLACK);}
    }
    s = "0";
    setText("red total",s,Color.WHITE);
    setText("yellow total",s,Color.BLACK);
    setText("text total","Totals",Color.BLACK);
}

private Group getScoreGroup(int n,int aFoot){
   Group ret = new Group();
   ret.setId("score boxes");
   Group row;
   ABox aBox;
   String s;
   for(int i=0;i<n;i++){
      row = new Group();
      s = "";row.setId("end "+(""+(i+1))+ " row");
      
      aBox = new ABox(Color.WHITE,Color.BLACK,aFoot,aFoot);
      aBox.setId("end box "+(i+1));
      aBox.setLayoutY(i*aFoot);
      row.getChildren().add(aBox);
      
      aBox = new ABox(Color.YELLOW,Color.BLACK,aFoot,aFoot);
      aBox.setId("yellow box "+(i+1));
      aBox.setLayoutX(aFoot);
      aBox.setLayoutY(i*aFoot);
      row.getChildren().add(aBox);
      
      aBox = new ABox(Color.RED,Color.BLACK,aFoot,aFoot);
      aBox.setId("red box "+(i+1));
      aBox.setLayoutX(aFoot*2);
      aBox.setLayoutY(i*aFoot);
      row.getChildren().add(aBox);
      ret.getChildren().add(row);
   }
   return ret;
}    

private Group getTotalGroup(int aFoot,int padding){
    Group ret = new Group();
    ret.setId("totals group");
    double xLoc = 3*aFoot+1.5*padding;
    double yLoc = 9*aFoot;
    Group row = new Group();
    row.setId("totals row");
    ABox aBox = new ABox(Color.WHITE,Color.BLACK,2*aFoot,aFoot);
    aBox.setId("text total");
    aBox.setLayoutX(xLoc);
    aBox.setLayoutY(yLoc);
    row.getChildren().add(aBox);
    ret.getChildren().add(row);
    
    row = new Group();
    row.setId("score row");
    yLoc += aFoot;
    aBox = new ABox(Color.YELLOW,Color.BLACK,aFoot,aFoot);
    aBox.setId("yellow total");
    aBox.setLayoutX(xLoc);
    aBox.setLayoutY(yLoc);
    row.getChildren().add(aBox);
    xLoc += aFoot;
    aBox = new ABox(Color.RED,Color.BLACK,aFoot,aFoot);
    aBox.setId("red total");
    aBox.setLayoutX(xLoc);
    aBox.setLayoutY(yLoc);
    row.getChildren().add(aBox);
    ret.getChildren().add(row);
    
    return ret;
}

public void setText(String box,String s,Color col){
    Iterator list1 = getChildren().iterator();
    ABox theBox;
    Group aGroup,bGroup;
    Iterator gList,gList2;
    boolean done = false;
    while(!done && list1.hasNext()){
        aGroup = (Group)list1.next();
        //System.out.println("   "+aGroup.getId());
        gList = aGroup.getChildren().iterator();
        while(!done && gList.hasNext()){
            bGroup = (Group)gList.next();
            //System.out.println("        "+bGroup.getId());
            gList2 = bGroup.getChildren().iterator();
            while(!done && gList2.hasNext()){
                theBox = (ABox)gList2.next();
                if(theBox.getId().equals(box)){
                    theBox.setText(s,col);}
                //System.out.println("              "+theBox.getId());
            }
        }
    }
}
/**
 * a basic box canvas colored with a border;
 */
private class ABox extends Canvas{
        Color col;
        Color border;
        int w; int h;
        public ABox(Color col,Color border,int w,int h){
            super(w,h);
            this.col = col;
            this.border = border;
            this.w = w;
            this.h = h;
            rePaint();
        }
        private void setText(String s,Color tCol){
            rePaint();
            GraphicsContext g = getGraphicsContext2D();
            g.setTextAlign(TextAlignment.CENTER);
            g.setStroke(tCol);
            g.strokeText(s,w/2,h - h/3);       
        }
        
        private void rePaint(){
            GraphicsContext g = getGraphicsContext2D();
            g.setFill(col);
            g.fillRect(0, 0, w, h);
            g.setStroke(border);
            g.strokeRect(0, 0, w, h);
            
        }
    }
}
