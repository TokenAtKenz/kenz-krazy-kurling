/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kurlingstuff;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *
 * @author TokenAtKenz
 */
public class PaintedArrow extends Canvas {
    final public int DownARROW = 0;
    final public int LeftARROW = 1;
    final public int RightARROW = 2;
    /**
     * Paints the arrows under the scroll bars.
     * @param params    KurlParams all the good stuff
     * @param w             width of the arrow
     * @param h             height of the arrow
     * @param direction     direction of the arrow 0,1 or 2. 
     */
    public PaintedArrow(KurlParams params,int w,int h,int direction){
        super(w,h);
        int t = DownARROW;
        switch(direction){
            case DownARROW : drawVArrow(params,w,h);break;
            case LeftARROW : drawLArrow(params,w,h);break;
            case RightARROW : drawRArrow(params,w,h);break;
        }
       
    }
    private void drawVArrow(KurlParams params, int w, int h) {
         GraphicsContext g = getGraphicsContext2D();
        int padding = (int)params.get("padding");
        int aFoot = (int)params.get("footSize");
        int stroke = (int)params.get("stroke");
        Color fillColor = (Color)params.get("dnColor");
        final int pSize = 7;
        double[] xPts = new double[pSize];
        double[] yPts = new double[pSize];
        double x1 = stroke;
        double y1 = stroke;
        xPts[0]=w/2; 
        yPts[0]=0;
        xPts[1]= w-padding;
        yPts[1]= h-2*aFoot;
        xPts[2]=w; 
        yPts[2]=h-2*aFoot;
        xPts[3]=w/2; 
        yPts[3]=h;
        xPts[4]=0; 
        yPts[4]=h-2*aFoot;
        xPts[5]=padding;
        yPts[5]=h-2*aFoot;
        xPts[6]=xPts[0]; 
        yPts[6]=yPts[0];
        g.setFill(fillColor);
        g.fillPolygon(xPts, yPts,pSize);
    }

    private void drawLArrow(KurlParams params, int w, int h) {
        int padding = (int)params.get("padding");
        int stroke = (int)params.get("stroke");
        Color fillColor = (Color)params.get("hzColor");
        int pSize = 7;
        double[] xp = new double[pSize];
        double[] yp = new double[pSize];
        xp[0] = w-padding; yp[0]=h/2;
        xp[1] = w - (w*.75); yp[1]=h-padding;
        xp[2] = xp[1]; yp[2]=h-stroke;
        xp[3] = 2*padding; yp[3]=yp[0];
        xp[4] = xp[1]; yp[4] = stroke;
        xp[5] = xp[1]; yp[5] = padding;
        xp[6] = xp[0]; yp[6] = yp[0];
        GraphicsContext g = getGraphicsContext2D();
        g.setFill(fillColor);
        g.fillPolygon(xp, yp,pSize);
    }

    private void drawRArrow(KurlParams params, int w, int h) {
        int padding = (int)params.get("padding");
        int stroke = (int)params.get("stroke");
        Color fillColor = (Color)params.get("hzColor");
        int pSize = 7;
        double[] xp = new double[pSize];
        double[] yp = new double[pSize];
        xp[0] = padding; yp[0]=h/2;
        xp[1] = w*.75; yp[1]=h-padding;
        xp[2] = xp[1]; yp[2]=h-stroke;
        xp[3] = w-2*padding; yp[3]=yp[0];
        xp[4] = xp[1]; yp[4] = stroke;
        xp[5] = xp[1]; yp[5] = padding;
        xp[6] = xp[0]; yp[6] = yp[0];
        GraphicsContext g = getGraphicsContext2D();
        g.setFill(fillColor);
        g.fillPolygon(xp, yp,pSize);
    }
}
