/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kurlingstuff;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

/**
 *All Rights Reserved
 * @author TokenAtKenz
 */
public class PaintedAd extends ImageView{
    private final int w,h,stroke;
    private Color colour;
    /**
    * paints a rectangle where an ad should go
    * @param w          width
    * @param h          height
    * @param stroke     thickness of the line
    */
    public PaintedAd(int w,int h,int stroke){
        super();
        this.stroke = stroke;
        this.w = w;
        this.h = h;
    }
    /**
     * sets the color of the ad rectangle
     * @param c  Color
     */
    public void setColor(Color c){
        colour = c;
    }
    /**
     * paints a rounded rectangle at the layout
     * @param g GraphicsContext
     */
    public void paintAd(GraphicsContext g){
        g.setStroke(colour);
        g.setLineWidth(stroke);
        g.strokeRoundRect(getLayoutX()-stroke,getLayoutY()-stroke,
                              w+2*stroke,h+2*stroke,15,15);
        
    }
}
