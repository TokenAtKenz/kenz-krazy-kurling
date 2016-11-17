/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kurlingstuff;

/**
 *PointData 
 * @author TokenAtKenz
 * All Rights Reserved
 * This class is a set of math'ish function that are 
 * used to calculated the different requirements of
 * figuring out how two friggin curling rocks behave when they
 * crash into one anudder. 
 */
public class PointData {
    public final double pi = Math.PI;
    public final double twoPi = Math.PI*2;
    public final double halfPi = Math.PI/2;
    public final int x=0;        
    public final int y=1;      
    public final int radian=0;  
    public final int radial=1;  
    
    public double deltaX;
    public double deltaY;
    public double angle;
    public double size;
    
  public PointData(){
        deltaX = 0;
        deltaY = 0;
        angle = 0;
        size = 0;
    }
  /**
   *returns the degrees from radians.
   * Not very useful, really, but came in handy during
   * the origin of the algorithms.
   * @param r  radians
   * @return   double  
   */
    public double degs(double r){
        return r*180/pi;
    }
    /**
     * fixes the angle as it goes round to keep it 
     * between 0 and 2*pi radians.
     * @param a     angle 
     * @return      double
     */
    public double fixedAngle(double a){
        double ang = a;
        while(ang >= twoPi){ang = ang - twoPi;}
        while(ang < 0){ang = ang + twoPi;}
        return ang;
    }
    public double[] v2d(double ang,double sz){  //Returns a Delta from a Vector
        double[] delta = new double[2];
        ang = fixedAngle(ang);
        delta[x] = Math.cos(ang)*sz;
        delta[y] = Math.sin(ang)*sz*-1;  //-1 for y-axis inversion
        return delta;
    }
    public double d2a(double dx, double dy){ //returns the angle in radians
        Double a = null;
        if (dx == 0){
            if(dy>=0){a=1.5*pi;}else{a=halfPi;}
        } else {
            if(dy == 0){
               if(dx>=0){a=0.0;}else{a=pi;} 
               }
        }
        if(a==null){ 
            a = Math.atan(dy/dx);
            if(dy < 0){if(dx >= 0){a=-a;}else{a = pi-a;}}
            else{if(dx >= 0){a=twoPi-a;}else{a = pi-a;}}
        }
        return (double) a;
    }
    
    private double[] d2v(double dx,double dy){  //returns a vector from the deltas'
        double[] vector = new double[2];
        vector[radian] = d2a(dx,dy);
        vector[radial] = Math.hypot(dx,dy);
        return vector;
    }
    

    public double[] vector(){
        return d2v(deltaX,deltaY);
    }
    
    public double[] delta(){
        return v2d(angle,size);
    }
    public void setDelta(){             //call after changing angle or size
        double[] d = v2d(angle,size);
        deltaX = d[x];
        deltaY = d[y];
    }
    public void setVector(){            //call after changing deltaX or deltaY 
        double[] v = d2v(deltaX,deltaY);
        angle = v[radian]; 
        size = v[radial]; 
    }

}
