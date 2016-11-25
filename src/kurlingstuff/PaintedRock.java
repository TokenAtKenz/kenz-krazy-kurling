/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kurlingstuff;

import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 *All Rights Reserved
 * @author TokenAtKenz
 */

public class PaintedRock extends Canvas {

    private final int strk;
    
    private final int x=0,y=1;
    private final Color black=Color.BLACK;
    private final Color grey=Color.GREY;
    private final Color red=Color.RED;
    private final Color yellow=Color.YELLOW;
    public double[] pCenter=new double[2];
    public Rock rock;
    private final double diameter;
    private final boolean hset = false;
    private final boolean sset = false;
    private KurlParams params;
    private SequentialTransition lastShot = null;
    //private final Rectangle2D hackRect;
    
    /**
     * This is a canvas object with a rock painted on it. It is used
     * for all the graphic animations. the parameter rock, is a Rock Type
     * and is used for all the mathematics of the physics.
     * 
     * 
     * @param params KurlParams
     */
    public PaintedRock(KurlParams params){    
        super((double)params.get("diameter")+2*(int)params.get("stroke"),
                (double)params.get("diameter")+2*(int)params.get("stroke"));
        strk = (int)params.get("stroke");
        diameter = (double)params.get("diameter")+2*strk;
        pCenter[x]=diameter/2;
        pCenter[y]=diameter/2;
        rock = newRock(params);
        paintRock();
    }
   @Override
   public String toString(){
       return getId();
   }
   /**
    * used by paintedBroom to move the rock. Movement is limited to make
    * guards viable. 
    * 
    * @param mv  double adjust horizontal by moving to X = mv
    * 
    */
   public void moveRock(double mvX,double mvY){  //used by painted broom to set the rock
       Duration d = Duration.millis(17);
       rock.center[x] = mvX;
       rock.center[y] = mvY;
       TranslateTransition t = new TranslateTransition(d, PaintedRock.this);
       t.setToX(rock.center[x]);
       t.setToY(rock.center[y]);
       t.play(); 
      }
      
   
   
    public boolean replayAvail(){
        return hset && sset;
    }
    
    private Rock newRock(KurlParams param){
        param.set("rockAngle",(double)0.0);
        param.set("curlAngle",(double)0.0);
        param.set("rockSpeed",(double)0.0);
        param.set("curlSpeed",(double)0.0);
        param.set("pRockCenter",pCenter);
        this.params = param;
        return new Rock(param);
        
    }
    private void paintRock(){
        GraphicsContext g = getGraphicsContext2D();
        double radius = (double) this.params.get("radius");
        double dia = radius*2;
        double[] temp=(double[]) this.params.get("pRockCenter");
        double cx = temp[x];
        double cy = temp[y];
        g.setFill(grey);
        g.setStroke(black);
        g.setLineWidth(strk);
        g.fillOval(cx-radius, cy-radius,dia,dia);
        g.setLineWidth(strk/2);
        g.strokeOval(cx-(radius-strk),cy-(radius-strk),
                     dia-2*strk,dia-2*strk);
        g.setFill(rock.colour);
        double csz = dia*.65; 
        g.fillOval(cx-(csz/2),cy-(csz/2), csz,csz);
        g.setStroke(black);
        g.setLineWidth(strk/2);
        g.strokeOval(cx-radius,cy-radius,dia,dia);
        
        temp[x]=cx; temp[y]=cy-radius/2;
        g.setLineWidth(radius*.4);
        if(rock.colour == red){g.setStroke(yellow);}
        else{g.setStroke(red);}
        g.strokeOval(cx-strk/2,cy-strk/2,strk,strk);
        //if(rock.colour == yellow){}
        g.setStroke(Color.CADETBLUE);
        g.strokeLine(cx, cy,temp[x],temp[y]);
        g.setLineWidth(g.getLineWidth()*.4);
        g.setStroke(black);
        g.strokeLine(cx, cy,temp[x],temp[y]);
        
    }

    
}




