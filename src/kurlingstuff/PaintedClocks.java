/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kurlingstuff;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author TokenAtKenz
 */
public class PaintedClocks extends Canvas{
    public PaintedClocks(KurlParams params,int w,int h){
        super(w,h);
        paintThem(params,w,h);
    }
    private void paintThem(KurlParams params,int w,int h){
        int stroke = (int)params.get("stroke");
        int aFoot = (int)params.get("footSize");
        int padding = (int)params.get("padding");
        
        GraphicsContext g = getGraphicsContext2D();
        int yLoc = 0;
        int h2 = (h/2) - (padding/2);
        g.setStroke(Color.LIME);
        g.strokeRoundRect(0,yLoc,w,h2,15,15);
        g.setFill(Color.YELLOW);
        g.fillRoundRect(stroke,yLoc+stroke,w-2*stroke,h2-2*stroke,15,15);
        g.setTextAlign(TextAlignment.CENTER);
        g.setStroke(Color.BLACK);
        String s = "00::00";
        g.setFont(new Font("Courier",aFoot*.8));
        g.strokeText(s,w/2,h2 - h2/3);  
        
        
        yLoc = yLoc+h2+padding/2;
       
        g.setStroke(Color.LIME);
        g.strokeRoundRect(0,yLoc,w,h2,15,15);
        g.setFill(Color.RED);
        g.fillRoundRect(stroke,yLoc+stroke,w-2*stroke,h2-2*stroke,15,15);
        g.setTextAlign(TextAlignment.CENTER);
        g.setStroke(Color.WHITE);
        s = "00::00";
        g.strokeText(s,w/2,yLoc+(h2 - h2/3));  
    }
    
}
