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
public class PaintedWatermark extends Canvas{
    public PaintedWatermark(KurlParams params,int w,int h){
        super(w,h);
        paintMark(params,w,h);
    }

    private void paintMark(KurlParams params, int w, int h) {
            
            int stroke = (int)params.get("stroke");
            int padding = (int)params.get("padding");
            int aFoot = (int)params.get("footSize");
            GraphicsContext g = getGraphicsContext2D();
            String l1 = "JavaFX Prototype Beta V1.00";
            String l2 = " FOR TESTING AND DEMO ONLY";
            g.setStroke(Color.DARKGRAY);
            g.strokeRect(0,0,w,h);
            g.strokeText(l1,padding ,aFoot - padding,w-2*padding); //bottom left corner
            g.strokeText(l2,padding ,2*aFoot - padding,w-2*padding);
        
            
    }
    
}
