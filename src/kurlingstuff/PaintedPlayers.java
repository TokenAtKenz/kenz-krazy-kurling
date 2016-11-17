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
public class PaintedPlayers extends Canvas {
    /**
     * black canvas with a Lime border and the player names
     * @param params    KurlParams
     * @param w         width of the player display screen
     * @param h         height of the player display screen
     */
    public PaintedPlayers(KurlParams params,int w,int h){
       super(w,h);
       drawPlayer(params,w,h);
    }
    private void drawPlayer(KurlParams params, int w, int h) {
        int stroke = (int)params.get("stroke");
        int padding = (int)params.get("padding");
        int aFoot = (int)params.get("footSize");
        GraphicsContext g = getGraphicsContext2D();
        g.setStroke(Color.WHITE);
        g.strokeRoundRect(0,0,w,h,15,15);
        g.setFill(Color.BLACK);
        g.fillRect(stroke,stroke,w-2*stroke,h-2*stroke);
        g.setFill(Color.YELLOW);
        int sz = 2*padding;
        int sl = 2*padding - 2*stroke;
        g.fillOval(padding,1.5*padding,sz,sz);
        g.setStroke(Color.BLACK);
        g.strokeOval(padding+stroke,1.5*padding+stroke,sl,sl);
        g.setStroke(Color.LIME);
        String s =  (String)params.get("player1");
        g.strokeText(s,4*padding,3*padding,w-2*aFoot-padding);
        
        g.setFill(Color.RED);
        g.fillOval(padding,h-3.25*padding,2*padding,2*padding);
        g.setStroke(Color.BLACK);
        g.strokeOval(padding+stroke,h-3.25*padding+stroke,sl,sl);
        s = (String)params.get("player2");
        g.setStroke(Color.LIME);
        g.strokeText(s,4*padding,h-1.5*padding,w-2*aFoot-padding);   
    }
    
    
}
    
