/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kurlingstuff;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author TokenAtKenz
 */
public class PaintedLogo extends Canvas{
    
public PaintedLogo(KurlParams params,int w,int h){
    super(w,h);
    paintLogo(params,w,h);
}
    
private void paintLogo(KurlParams params, int w,int h){
        int stroke = (int)params.get("stroke");
        int aFoot = (int) params.get("footSize");
        GraphicsContext g = getGraphicsContext2D();
        g.setStroke(Color.WHITE);
        g.strokeRoundRect(0,0,w,h,15,15);
        g.setFill(Color.BLACK);
        g.fillRect(stroke,stroke,w-2*stroke,h-2*stroke);
        g.setTextAlign(TextAlignment.CENTER);
        g.setStroke(Color.LIME); 
        g.strokeText("Kenz Krazy Kurling FX",w/2,h-3*stroke,w-4*stroke); 
        }    
} 


