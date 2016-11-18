/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kurlingstuff;


import java.util.ArrayList;
import java.util.Iterator;
import javafx.scene.paint.Color;

/**
 *All Rights Reserved
 * @author TokenAtKenz
 */
public class Rock{
    public  PointData rockVector = new PointData();
    public  PointData curlVector = new PointData();
    public  PointData hitVector = new PointData();
    public double[] center = {0,0};
    public double[] oldCenter = {0,0};
    public double oldAngle = 0;
    public double curlDir;
    public final double radius;
    public final Color colour;  
    public final double[] max = new double[2];
    public final double[] min = new double[2];
   
    private final double pi = Math.PI;
    private final double halfPi = Math.PI/2;
    private final int x=0;        
    private final int y=1;
    private final double baseFriction;
    private final double curlFriction;
    private final double fricFrac;
    private final double thud;    //%left after a thud
    private final double curlRatioSize;
    private boolean moveable = false;
    private final int TOP;
    private final int BOTTOM;
    private final int SIDES;
    private int walls = 0;
    public boolean outOfBounds = false;
    
    public Rock(KurlParams params){
        TOP =(int) params.get("TOP");
        BOTTOM =(int) params.get("BOTTOM");
        SIDES =(int) params.get("SIDES");
        walls = (int) params.get("walls");
        baseFriction = (double)params.get("baseFriction");//aFoot * 0.00064; //at .02 for my res
        fricFrac = (double)params.get("fricFrac");
        curlFriction = (double)params.get("curlFricFrac") * baseFriction;
        curlRatioSize = (double)params.get("curlRatioSize");
        thud = (double)params.get("thud");
        radius = (double)params.get("radius");
        min[x] = (int)params.get("ofsX");
        min[y] = (int)params.get("ofsY");
        max[x] = (int)params.get("iceWide")-(double)params.get("diameter")+min[x];
        max[y] = (int)params.get("iceHigh")-(double)params.get("diameter")+min[y];
        colour = (Color) params.get("colour");
        center[x] = radius;
        center[y] = radius;
        moveable = false;
        
        
        
        rockVector.angle = (double) params.get("rockAngle");
        rockVector.size = (double) params.get("rockSpeed");
        rockVector.setDelta();
        
        curlDir = Math.signum((double)params.get("curlSpeed"));
        curlVector.angle=(double)params.get("rockAngle");
        curlVector.size = Math.abs((double)params.get("curlSpeed"));
        
        
        curlVector.setDelta();
        
        hitVector.angle = rockVector.angle+pi;
        hitVector.size = rockVector.size;
        hitVector.setDelta();
    }
   
    private double[] addDeltas(double[] d1,double[] d2){
       double size,angle;
       size = Math.hypot(d1[x] + d2[x],d1[y]+d2[y]);
       angle = hitVector.d2a(d1[x] + d2[x],d1[y] + d2[y]);
       return hitVector.v2d(angle,size);
    }
    
    
    public void setMoveable(boolean mv){
       moveable = mv;//okToMove;
    }
    
    public void setWalls(int newWalls){
        walls = newWalls;
    }
    
    public boolean inRings(KurlParams params){
        final int aFoot = (int)params.get("footSize");
        final double[] pin = (double[])params.get("btnLoc");
        return Math.hypot(pin[x]-center[x],pin[y]-center[y]) < (12*aFoot+radius);
    }
    
    public boolean stopped(){
        if(moveable){
          return rockVector.size ==0 && curlVector.size == 0;}
        else {return true;}
    }
    
    public double[] endPt(PointData v,double[] c){
        return addDeltas(c,v.v2d(v.angle,radius));
    }

    public boolean checkBounce(int w){
        return (w&walls)==0;
    }
    
    public void checkWalls(){
        if(center[x]<min[x]||center[x]>max[x]){
         outOfBounds = checkBounce(SIDES);   
         if(center[x]>min[x]){
             center[x] = max[x];
             hitVector.angle = 0;}
         else{center[x] = min[x];hitVector.angle=pi;}
        rockVector.deltaX = -rockVector.deltaX;
        rockVector.setVector();
        }
        if(center[y]>max[y]||center[y]<min[y]){
         if(center[y]>min[y]){
             outOfBounds = checkBounce(BOTTOM);
             center[y] = max[y];
             hitVector.angle = 1.5*pi;}
         else{center[y] = min[y];
              hitVector.angle=halfPi;
              outOfBounds = checkBounce(TOP);}
        rockVector.deltaY = -rockVector.deltaY;
        rockVector.setVector();
        } 
    }
    
    private double getPrctOut(double a1, double a2){
        double ret = Math.abs(a1 - a2);
        while(ret >= pi){ret -= halfPi;}
        ret = (100-((ret/pi)*100))/100;
        return ret;
    }

    public void checkHit(ArrayList bagOfRocks){
        double twoRadius;
        double[] twoDelta = new double[2];
        Iterator rockList = bagOfRocks.iterator();

        while(rockList.hasNext()){
            Rock hitRock = (Rock) rockList.next();
            twoRadius = radius + hitRock.radius;
            twoDelta[x] = hitRock.center[x] - center[x];
            twoDelta[y] = hitRock.center[y] - center[y];
            final double hiPot = Math.hypot(twoDelta[x],twoDelta[y]);
            if(hitRock != this && hiPot < twoRadius){
                
                //set the 2 hitvector delta's and angles
                hitVector.deltaX = twoDelta[x];
                hitVector.deltaY = twoDelta[y];
                hitVector.setVector();
                hitRock.hitVector.deltaX = center[x]-hitRock.center[x];
                hitRock.hitVector.deltaY = center[y]-hitRock.center[y];
                hitRock.hitVector.setVector();

                if(rockVector.size > thud){
                      rockVector.size = rockVector.size * thud;
                }
                if(hitRock.rockVector.size > thud){
                      hitRock.rockVector.size = hitRock.rockVector.size * thud;
                }
                
                double fp1 = getPrctOut(rockVector.angle,hitVector.angle);
                //double fp2 = getPrctOut(hitRock.rockVector.angle,hitRock.hitVector.angle);
                
                
                
                hitVector.size = fp1 * rockVector.size;
                hitVector.setDelta();
                hitRock.hitVector.size = fp1 * rockVector.size;
                hitRock.hitVector.setDelta();
                
                rockVector.deltaX += hitRock.hitVector.deltaX;
                rockVector.deltaY += hitRock.hitVector.deltaY;
                rockVector.setVector();
                
                
                hitRock.rockVector.deltaX += hitVector.deltaX;
                hitRock.rockVector.deltaY += hitVector.deltaY;
                hitRock.rockVector.setVector();
                
               //****curl transfer section
                //final double c1Out = fp1*curlVector.size;
                //final double c1In = curlVector.size-c1Out;
                final double c1Out = (1-fp1)*curlVector.size;
                final double c1In = curlVector.size-c1Out;
                //final double c2Out = fp1*hitRock.curlVector.size;
                //final double c2In = hitRock.curlVector.size-c2Out;
                final double c2Out = (1-fp1)*hitRock.curlVector.size;
                final double c2In = hitRock.curlVector.size - c2Out;
                
                
                //if(c1Out > c2Out){hitRock.curlDir = curlDir;curlDir = -curlDir;}
                //else{curlDir = hitRock.curlDir;hitRock.curlDir = -hitRock.curlDir;}
                hitRock.curlDir = curlDir;
                curlDir = -curlDir;
                curlVector.size = (c1In + c2Out);
                hitRock.curlVector.size = (c1Out + c2In);
                //System.out.println("c1In["+c1In+"] c1Out["+c1Out+"]  c2In["+c2In+"] c2Out["+c2Out+"]");
                curlVector.setDelta();
                hitRock.curlVector.setDelta();

                 hitRock.move(bagOfRocks);

            }
        }
    } 
    
    private double friction(){//dish the ice
        double f = baseFriction;
        double ff = f*fricFrac;
        double l = max[x]/2;
        double n = Math.abs(center[x]-max[x]/2);
        double c = n/l;
        double p = (baseFriction+ff) - c*ff;
        return p;
    }
    private void deltaRock(){
       
       oldAngle = curlVector.angle;
       oldCenter = center;
       
       double friction = friction();
       if(rockVector.size <= friction){
           rockVector.size = 0;}
       else {rockVector.size -= friction;}
       rockVector.setDelta();
       
       curlVector.size -= curlFriction;
       if(curlVector.size <= 0){
           curlVector.size = 0;}
       else{ curlVector.angle += curlVector.size * curlDir;}
       //curlVector.angle = curlVector.fixedAngle(curlVector.angle);
       curlVector.setDelta();
       
           double curlRatio = curlVector.size - rockVector.size;
           if(curlRatio <= 0){curlRatio = curlRatioSize;}//sweeping for .1
           curlRatio = curlRatio * curlRatioSize;     
           if(curlVector.size>0){
           rockVector.angle -= curlRatio*curlDir*friction;}
           //rockVector.angle = rockVector.fixedAngle(rockVector.angle);
           rockVector.setDelta();
    }
    /**
     * 
     * @param bagOfRocks ArrayList(Rock) 
     */
    public void move(ArrayList bagOfRocks){
        if(moveable && !stopped()){
                deltaRock();
                center = addDeltas(center, rockVector.delta());
                checkWalls();
                
                if(outOfBounds){
                    //rockVector.size=0;curlVector.size=0;
                    moveable = false;}
                else{
                    checkHit(bagOfRocks);}
                
            }
        }
    }
    
    
    

