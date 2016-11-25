/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kurlingstuff;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 *above
 * @author TokenAtKenz
 * 
 * 
 */
public class PaintedBroom extends Canvas{
    private PaintedRock theRock = null;
    private final double centerX;
    private final double xDiff;
    /**
     * Creates the broom and sets up the mouse drag properties. This is a 
     * simple Canvas type of object with a local paintBroom function.
     * @param params KurlParams: the systemwide parameters package
     * 
     * 
     */        
    public PaintedBroom(KurlParams params){
        super((double)params.get("diameter")+2*(int)params.get("stroke"),
                4*(double)params.get("diameter"));
       centerX = (int)params.get("ofsX")+
                             (int)params.get("iceWide")/2 -
                             (double)params.get("radius");
        xDiff = (int)params.get("iceWide") * .12;
        paintBroom(params);
        
        this.setOnMouseDragged(e->{
            updateLocation(e,theRock);
            
           });
    }
    public void setRock(PaintedRock pRock){
        theRock = pRock;
           if(theRock!= null){
            System.out.println("setRock sets "+theRock.getId());
        } else {
           System.out.println("setRock sets "+theRock); 
        }
    }
    public void clearRock(){
        theRock = null;
    }
    private void updateLocation(MouseEvent e,PaintedRock pRock){
        this.setLayoutX(e.getSceneX());
        this.setLayoutY(e.getSceneY());
        double newX;
        
       
        int x=0,y=1;
            if(pRock != null){
                
                //System.out.println(".....");
                //System.out.println("xDiff "+xDiff);
                //System.out.println("rock center[x]"+pRock.rock.center[x]);
                //System.out.println("rock min "+pRock.rock.min[x]);
                //System.out.println("rock max "+pRock.rock.max[x]);
                //System.out.println("broom e.getSceneX() "+e.getSceneX());
                //newX = pRock.rock.center[x];
                if(e.getSceneX() < centerX - xDiff){
                  newX = centerX - xDiff;
                } else { if(e.getSceneX()> centerX + xDiff){
                           newX = centerX+xDiff;
                        } else {newX = e.getSceneX();}
                }
                
                pRock.moveRock(newX,pRock.rock.center[y]);
            }
    }
    
    private void paintBroom(KurlParams param){
        GraphicsContext g = getGraphicsContext2D();
        double radius = (double) param.get("radius");
        double dia = radius*2;
        int strk = (int)param.get("stroke");
        double cx = (getWidth()-1)/2;
        double cy = radius+strk;
       g.setFill(Color.PINK);
        g.setStroke(Color.BLACK);
        g.setLineWidth(strk);
        g.fillOval(cx-radius, cy-radius,dia,dia/3);
        g.strokeOval(cx-radius, cy-radius,dia+strk,dia/3+strk);
        g.setFill(Color.CHOCOLATE);
        g.fillRoundRect(cx-radius/4+1, cy-radius/2, radius/2, radius*6,10,10);
        g.setLineWidth(strk/2);
        cx = cx-radius-strk;
        cy = cy-radius-2*strk;
        while(cx < (radius*2)+(strk*2)){
            g.strokeLine(cx, cy, cx, cy+radius/2);
            cx += 2;
        }
        //g.strokeRect(0,0,getWidth()-1,getHeight()-1);
        
        
        
    }

    
}
