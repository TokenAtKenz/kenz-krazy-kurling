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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

/**
 *All Rights Reserved
 * @author TokenAtKenz
 */
public class PaintedIce extends Canvas{
    private final int x=0; private final int y=1;
    private final int endX=2; private final int endY=3;
    
    public final int[] hogLine = new int[4];
    private final int[] teeLine = new int[4];
    private final int[] backLine = new int[4];
    private final int[] centerLine = new int[4];
    private final int[] twelveFoot = new int[4];
   
    public final int[] rinkButton = new int[2];
    
    
    
    private final Color blue=Color.BLUE;
    private final Color red=Color.RED;
    private final Color white=Color.WHITE;
    private final Color black=Color.BLACK;
    private final Color iceColor = white;
    
    public PaintedIce(KurlParams params,Group iceAds){
        super((int)params.get("iceWide")+2*(int)params.get("stroke"),
               (int)params.get("iceHigh")+2*(int)params.get("stroke"));
        params.set("iceMax",iceMax((int)params.get("footSize"),(int)params.get("stroke")));
        params.set("iceMin",iceMin((int)params.get("stroke")));
        fillFinals(params);
        paintRink(params);
        paintAds(params,iceAds);
    }
    
    private void paintAds(KurlParams params,Group iceAds){
        double aFoot = (int)params.get("footSize");
        int iceWide = (int)params.get("iceWide");
        int padding = (int)params.get("padding");
        Iterator ads = iceAds.getChildren().iterator();
        ImageView iv;
        double xofs;
        double startx;
        double yofs;
        double starty;
        double iw,ih;
        while (ads.hasNext()){
            iv = (ImageView)ads.next();
            Image im = iv.getImage();
            xofs = im.getWidth();
            startx = 0;
            yofs = im.getHeight();
            starty = 0;
            switch(iv.getId()){
                case "image1": 
                    iw = 5*aFoot;
                    ih = 8*aFoot;
                    if (xofs < iw){
                        startx = (5*aFoot - xofs)/2;
                        iw = xofs;}
                    if(yofs < ih){
                        starty = (8*aFoot - yofs)/2;
                        ih = yofs;}
                    getGraphicsContext2D().
                    drawImage(im,startx+aFoot,5*aFoot+starty,iw,ih);
                    break;
                case "image2":
                    iw = 5*aFoot;
                    ih = 8*aFoot;
                    if (xofs < iw){
                        startx = (5*aFoot - xofs)/2;
                        iw = xofs;}
                    if(yofs < ih){
                        starty = (8*aFoot - yofs)/2;
                        ih = yofs;}
                    getGraphicsContext2D().
                            drawImage(im,startx+iceWide-(6*aFoot)+padding,
                                        5*aFoot+starty,iw,ih);
                    break;
                case "image3":
                    iw = aFoot;
                    ih = aFoot;
                    if (xofs < iw){
                        startx = (aFoot - xofs)/2;
                        iw = xofs;}
                    if(yofs < ih){
                        starty = (aFoot - yofs)/2;
                        ih = yofs;}
                    getGraphicsContext2D().
                            drawImage(im,(iceWide/2 - aFoot/2)+startx,22.5*aFoot+starty,iw,ih);
                    break;
                default: break;
            }
        }
    }
    private void fillFinals(KurlParams params){
        int strk = (int)params.get("stroke");
        int aFoot = (int)params.get("footSize");
        int rinkWide = (int)params.get("iceWide");
        int rinkHigh = (int)params.get("iceHigh");
        
        hogLine[x]=strk;
        hogLine[y]=2*aFoot+strk;
        hogLine[endX]=strk+rinkWide; 
        hogLine[endY]=hogLine[y];
        
        teeLine[x]=hogLine[x];
        teeLine[y]=hogLine[y]+21*aFoot;
        teeLine[endX]=hogLine[endX];
        teeLine[endY]=teeLine[y];
        
        backLine[x]=teeLine[x];
        backLine[y]=teeLine[y]+6*aFoot;
        backLine[endX]=teeLine[endX];
        backLine[endY]=backLine[y];
        
        centerLine[x]=strk+(int)(7.5*aFoot);
        centerLine[y]=strk; 
        centerLine[endX]=centerLine[x];
        centerLine[endY]=strk+rinkHigh; 
        
        rinkButton[x]=centerLine[x];
        rinkButton[y]=teeLine[y];
       
        twelveFoot[x]=rinkButton[x]-6*aFoot;
        twelveFoot[y]=rinkButton[y]-6*aFoot;
    }
    
    private int[] iceMax(int aFoot,int strk){
        int[] max = new int[2];
        max[x] = 15*aFoot - strk;
        max[y] = 31*aFoot - strk;
        return max;
    }
    private int[] iceMin(int strk){
        int[] min = new int[2];
        min[x] = strk;
        min[y] = strk;
        return min;
    }
    /**
     * paints the rink
     * @param params KurlParams containing all the great parameter stuff
     */
    private void paintRink(KurlParams params) {
        GraphicsContext g = getGraphicsContext2D();
        int x1; int y1; int fts;
        int strk = (int)params.get("stroke");
        int doubleStrk = strk*2;
        int halfStrk = strk/2;
        double rinkWidth = (int)params.get("iceWide");
        double rinkHeight = (int)params.get("iceHigh");
        int aFoot = (int)params.get("footSize");
        double radius = (double)params.get("radius");
        double diameter = 2*radius + doubleStrk;
        g.setStroke(red);
        g.setLineWidth(doubleStrk);
        g.strokeRect(0,
                     0,
                    rinkWidth+doubleStrk,
                    rinkHeight+doubleStrk);
        g.setFill(iceColor);
        g.fillRect(strk,strk,rinkWidth,rinkHeight);
        g.setLineWidth(strk);
        g.setStroke(black);
        g.strokeLine(backLine[x],backLine[y],backLine[endX],backLine[endY]);
        g.setStroke(red);
        x1=twelveFoot[x];
        y1=twelveFoot[y];
        fts = 12*aFoot;
        g.strokeOval(x1,y1,fts,fts);  //12Ft red edge
        g.setFill(blue);
        x1+=halfStrk;
        y1+=halfStrk;
        g.fillOval(x1,y1,fts-strk,fts-strk); //12Ft blue Circle
        g.setStroke(red);
        x1=twelveFoot[x]+2*aFoot;
        y1=twelveFoot[y]+2*aFoot;
        fts=8*aFoot;
        g.strokeOval(x1,y1,fts,fts); //12Ft red Edge
        g.setFill(white);
        x1+=halfStrk;
        y1+=halfStrk;
        g.fillOval(x1,y1,fts-strk,fts-strk); // 8Ft while circle
        g.setStroke(blue);
        x1=twelveFoot[x]+4*aFoot;
        y1=twelveFoot[y]+4*aFoot;
        fts = 4*aFoot;
        g.strokeOval(x1,y1,fts,fts); //4Ft blue edge
        g.setFill(red);
        x1+=halfStrk;
        y1+=halfStrk;
        g.fillOval(x1,y1,fts-strk,fts-strk); //4Ft red circle
        g.setStroke(black);   //draw the lines
        g.strokeLine(hogLine[x],hogLine[y],hogLine[endX],hogLine[endY]);
        g.strokeLine(teeLine[x],teeLine[y],teeLine[endX],teeLine[endY]);
        g.strokeLine(centerLine[x],centerLine[y],centerLine[endX],centerLine[endY]);
       
        g.setLineDashes(radius/3);
        g.setStroke(red);
        g.strokeLine(backLine[x],
                              backLine[y]+diameter,
                              backLine[endX],
                              backLine[endY]+diameter);
        g.setLineDashes(0);
        
        g.setStroke(blue);
        fts = (int)(3*radius) ;
        x1=(int) (rinkButton[x] - fts/2);
        y1=(int) (rinkButton[y] - fts/2);
        g.strokeOval(x1,y1,fts,fts); //4Ft blue edge
        g.setFill(white);
        x1+=halfStrk;
        y1+=halfStrk;
        g.fillOval(x1,y1,fts-strk,fts-strk); //white button
        g.setFill(black);
        x1=rinkButton[x]-strk;
        y1=rinkButton[y]-strk;
        g.fillOval(x1,y1,2*strk,2*strk);  //black button
        g.setLineWidth(strk);
        g.setStroke(blue);
        
        }
 
}
    

  
