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
 *
 * @author TokenAtKenz
 */
public class PaintedHammer extends Canvas {
    private Color hammerColor = Color.RED;
    private final int w; 
    private final int h;
    private int hPt;
    
    public PaintedHammer(KurlParams params,int w, int h){
        super(w,h);
        this.w = w;
        hPt = h;
        this.h = h;
        paintHammer(params);
       
    //    this.setOnMouseDragged(e->{
    //        updateLocation(e);
   //             }); 
    }
    private void updateLocation(MouseEvent e){
        this.setLayoutX(e.getSceneX());
        this.setLayoutY(e.getSceneY());
        
    }
    
    public void setHammerColor(KurlParams params,String s){
        switch (s){
        case "red" :hammerColor = Color.RED;hPt = h;break;
        case "black" : hammerColor = Color.BLACK;break;
        case "lime" : hammerColor = Color.LIME;break;
        default: hammerColor = Color.YELLOW; hPt=h/2;
    }
    paintHammer(params);
    }
    
    private void paintHammer(KurlParams params){
      GraphicsContext g = getGraphicsContext2D();
        //g.setStroke(Color.BLACK);
        //g.strokeRect(0, 0, w, h);
        g.setFill(hammerColor);
        int aFoot = (int)params.get("footSize");
        int colHi = hPt;
        int padding = w/10;
        int pSize = 9;
        double[]xp = new double[pSize];
        double[]yp = new double[pSize];
        xp[0] = padding;yp[0] = colHi;
        xp[1] = 0;yp[1] = colHi-padding;
        xp[2] = 3*padding; yp[2] = colHi-5*padding;
        
        xp[3] =padding;yp[3] = colHi-7*padding;
        xp[4] = 5*padding; yp[4] = colHi-7*padding;
        xp[5] = 8*padding; yp[5] = colHi-4*padding;
        xp[6] = 6*padding; yp[6] = colHi-2*padding;
        xp[7] = 5*padding;yp[7] = colHi-4*padding;
        
        xp[8] = padding; yp[8]=colHi;
        g.fillPolygon(xp, yp, pSize);  
    }
    
}
