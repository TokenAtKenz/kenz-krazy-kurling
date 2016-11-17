/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kurlingstuff;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import kurlingstuff.KurlParams.KeyValuePair;

/**
 * All Rights Reserved
 * @author TokenAtKenz
 */
public class PaintedRockGroup extends Group{
    private final int x=0,y=1;
    private final double[] rockBox; 
    private int rocksOOB = 0;
    private final double[] btnLoc;
    private final double aFoot;
    private final double ringRadius;
/**
 * Constructor builds a group of PaintedRocks
 * @param params    KurlParams
 */
    public PaintedRockGroup(KurlParams params){
        super();
        rockBox = (double[])params.get("rockBox");
        btnLoc = (double[])params.get("btnLoc");
        aFoot = (int)params.get("footSize");
        ringRadius = 6*aFoot+(double)params.get("radius");
        makeRocks(params);
        resetTheRocks(params);
    }
    /**
     * this returns 2 KeyValuePairs. One with the color String and the 
     * winning score. The other with the other color String and Zero.
     * 
     * @return KurlParams
     * 
     */   
    public KurlParams scanToBtn(){
        Iterator r = getChildren().iterator();
        int zero = 0;   
        KurlParams ret = new KurlParams();
        ret.set("red",zero);
        ret.set("yellow",zero);
        PaintedRock pRock;
        int theScore = 0;
        double d;
        KurlParams rocksInTheRings = new KurlParams(); 
        while(r.hasNext()){
            pRock = (PaintedRock) r.next();
            d = Math.hypot(pRock.rock.center[x]-btnLoc[x],
                              pRock.rock.center[y]-btnLoc[y]);
            if(d <= ringRadius){
            rocksInTheRings.set(pRock.getId(),d);  }  
        }
        Collections.sort(rocksInTheRings);
        KeyValuePair kvp;
        String scoreRock = "noScore";
        Iterator rs;
        rs = rocksInTheRings.iterator();
        if(rs.hasNext()){
            kvp = (KeyValuePair)rs.next();
            if(kvp.key.contains("red")){theScore++;scoreRock = "red";}
            if(kvp.key.contains("yellow")){theScore++;scoreRock = "yellow";}
            }
        boolean ok = true;
        while(rs.hasNext()){
            kvp = (KeyValuePair)rs.next();
            if(!kvp.key.contains(scoreRock)){ok = false;}
            if(ok && kvp.key.contains(scoreRock)){
              theScore++;
            }
        }
      ret.set(scoreRock,theScore);
      return ret;
    }
    /**
     * piles the rocks on the button at the start of a load
     * @param params KurlParams
     */
    
    private ParallelTransition rollOOB(KurlParams params,Object pRock,Duration t){
        ParallelTransition ret;
        double d= (double)params.get("diameter");
        int st = (int)params.get("stroke");
        int ofs = st+(int)d;
        PaintedRock aRock = (PaintedRock)pRock;
        aRock.rock.center[x] = rockBox[x]+ofs*(rocksOOB%4);
        aRock.rock.center[y] = rockBox[y]-ofs*(rocksOOB/4);
        aRock.rock.curlVector.angle =0;
        rocksOOB++;
        //ret.getChildren().add(new PauseTransition(t));
        ret = parallelSequence(aRock,t);
        return ret;
    }
    /**
     * 
     * @param params 
     */
    private void resetTheRocks(KurlParams params){
        rocksOOB = 0;
        centerTheRocks(params);
        rocksOOB = 0;
    }
    public void doReset(KurlParams params){
        resetTheRocks(params);
    }
    
    private void centerTheRocks(KurlParams params){
        Iterator rocks = getChildren().iterator();
        SequentialTransition s = new SequentialTransition();
        while(rocks.hasNext()){
            rollOOB(params,rocks.next(),Duration.millis(100)).play();
        }
                //s.play();
    }

 /**
 * simple function so I don't have to keep typeing
 * the System.out shit...
 * @param o     Object
 */    
    private void printIt(Object o){
        String localStr = o.toString();
        System.out.println(localStr);
    }
/**
 * This function returns an ArrayList of the base rocks 
 * for rock.move(ArrayList());
 * @return  ArrayList()
 */    
    public ArrayList rocks(){  //list of bare rocks
      ArrayList retRocks = new ArrayList();
      Iterator rockCanvasList = getChildren().iterator();
      PaintedRock pRock;
      while(rockCanvasList.hasNext()){
          pRock = (PaintedRock) rockCanvasList.next();
          retRocks.add(pRock.rock); 
      }
      return retRocks;
    }
/**
 * Basic motion. Returns a parallel transition, where both the forward motion
 * and the rotation motion are sequenced together into a single sequence. The
 * Painted Rock (pRock) contains it's own motion information and the runtime is 
 * the length of time to span doing the transition.
 * @param pRock         PaintedRock
 * @param runTime       Duration       
 * @return              ParallelTransition 
 */
    private ParallelTransition parallelSequence(PaintedRock pRock,Duration runTime){
        ParallelTransition retSeq = new ParallelTransition();
        RotateTransition rotateSeq = new RotateTransition(runTime,pRock);
        TranslateTransition motionSeq = new TranslateTransition(runTime,pRock);
        motionSeq.setToX(pRock.rock.center[x]);   //set from current to current+dx
        motionSeq.setToY(pRock.rock.center[y]);   //set from current to current+dy
        rotateSeq.setFromAngle(pRock.rock.oldAngle);
        rotateSeq.setToAngle(pRock.rock.curlVector.angle);  //set from current to current+da
        retSeq.getChildren().add(motionSeq);
        retSeq.getChildren().add(rotateSeq);
        return retSeq;
    }    
 /**
  * The single shot sequence generator. This is a recursive routine that starts by 
  * making a single rock move. Then it cycles through the list of rocks to see 
  * if any of those moved because of the first move. If any other rocks have
  * moved, the cycle recurses with that rock testing it's move against all the
  * other rocks... and so on and so on until all the rocks have stopped moving
  * and sequence is returned of all the moves;
  * @param params       KurlParams
  * @param pRock        PaintedRock
  * @return             SequentialTransition 
  */   
    public SequentialTransition shotSequence(KurlParams params,PaintedRock pRock){
        SequentialTransition retSeq = new SequentialTransition();
        Duration runTime = Duration.millis(17);
        int rockWalls = (int)params.get("walls");
        pRock.rock.curlDir = (double)params.get("curlDir");
        pRock.rock.rockVector.size = (double)params.get("rockVectorSize");
        pRock.rock.rockVector.angle = (double)params.get("rockVectorAngle");
        pRock.rock.rockVector.setDelta();
        pRock.rock.curlVector.angle = (double)params.get("curlVectorAngle");
        pRock.rock.curlVector.size = (double)params.get("curlVectorSize");
        pRock.rock.curlVector.setDelta();
        pRock.rock.setWalls(rockWalls);
        pRock.rock.setMoveable(true);
        retSeq.getChildren().add(parallelSequence(pRock,runTime));
        pRock.rock.move(rocks());
        //printIt("creating motionRock movie for *["+pRock.getId()+"]*");
        ParallelTransition groupSeq;
        boolean stop = false;
        PaintedRock aRock;
        while(!stop){
            groupSeq = new ParallelTransition();
            Iterator rList = getChildren().iterator();
            while(rList.hasNext()){
                aRock = (PaintedRock)rList.next();
                aRock.rock.setWalls(rockWalls);
                if(!aRock.rock.stopped()){
                    aRock.rock.move(rocks());
                    groupSeq.getChildren().
                            add(parallelSequence(aRock,runTime));
                    if(aRock.rock.outOfBounds){
                        groupSeq.getChildren().
                            add(rollOOB(params,aRock,runTime));}
                    }
                }   
            retSeq.getChildren().add(groupSeq);
            stop = true;
            rList = getChildren().iterator();
            while(rList.hasNext()){
                aRock = (PaintedRock)rList.next();
                if(!aRock.rock.stopped()){stop = false;}
                }   
            }
        
        return retSeq;
    }
/**
 * Place holder for the setup portion of the shot.
 * @param params    KurlParams
 * @param pRock     PaintedRock
 * @return          SequentialTransition 
 */    
    public SequentialTransition setupSequence(KurlParams params,PaintedRock pRock){
        final double startX = (int)params.get("iceWide")
                               +(int)params.get("stroke")
                               +(int)params.get("ofsX");
        final double topX = (int)params.get("ofsX")+
                             (int)params.get("iceWide")/2 -
                             (double)params.get("radius");
        final double topY = (double)params.get("topY")+(int)params.get("ofsY")
                              -(int)params.get("stroke");
        SequentialTransition retSeq = new SequentialTransition();
        pRock.rock.center[x]=topX + ((Math.random()*1-.5)*(14*pRock.rock.radius));
        pRock.rock.center[y] = topY;
        pRock.rock.curlVector.angle = 0;
        pRock.rock.curlVector.setDelta();
        retSeq.getChildren().add(parallelSequence(pRock,Duration.seconds(1)));
        return retSeq;
    }
/**
 * Returns an animated sequence of a single rock leaving the start position
 * and rolling up to just behind the hogline, then over to the center line.
 * @param params    KurlParams 
 * @param pRock     PaintedRock
 * @return          SequentialTransition 
 */
    public SequentialTransition hackSequence(KurlParams params,PaintedRock pRock){
        
        double ang = 360;
        final double startX = (int)params.get("iceWide")
                               +(int)params.get("stroke")
                               +(int)params.get("ofsX")
                               +(int)params.get("padding");
        final double topX = (int)params.get("ofsX")+
                             (int)params.get("iceWide")/2 -
                             (double)params.get("radius");
        final double topY = (double)params.get("topY")+(int)params.get("ofsY")
                              -(int)params.get("stroke");
       
        pRock.rock.outOfBounds = false;
        //printIt("creating hack Load for *["+pRock.getId()+"]*");
        SequentialTransition retSeq = new SequentialTransition();
                    pRock.rock.center[x]=startX;
                    pRock.rock.center[y]=topY;
                    pRock.rock.curlVector.angle =-ang*4;
                    pRock.rock.curlVector.setDelta();
                    retSeq.getChildren().
                      add(parallelSequence(pRock,Duration.seconds(1.0)));
                    pRock.rock.center[x]=topX;
                    pRock.rock.curlVector.angle =0;
                    pRock.rock.curlVector.setDelta();
                    retSeq.getChildren().
                      add(parallelSequence(pRock,Duration.seconds(.3)));
                    
        return retSeq;
    }
/**
 * Opening sequence where all the rocks are rolled to the bottom right hand 
 * corner. Lots of room to change stuff here for interesting reset sequences
 * @param params    KurlParams
 * @return          SequentialTransition
 */    
    public SequentialTransition resetSequence(KurlParams params){
        int numR = (int)params.get("numberOfRocks");
        double diameter = (double)params.get("diameter");
        double stroke = (int)params.get("stroke");
        int padding = (int)params.get("padding");
        Color hammerColor;
        String hammer = (String)params.get("hammer");
        if(hammer.equals("red")){hammerColor = Color.RED;}else{hammerColor = Color.YELLOW;}
        diameter = diameter + stroke;
        SequentialTransition resSeq = new SequentialTransition();
        double dNumR = (numR%2)+(numR/2);
        double sy = dNumR*diameter;
        double tempX = (int)params.get("iceWide")+(int)params.get("ofsX")+stroke+padding;
        double tempY= (int)params.get("iceHigh")+(int)params.get("ofsY")-sy+stroke;
        Duration d = Duration.millis(100);
        rocksOOB = 0;
        Iterator rList = getChildren().iterator();
        while(rList.hasNext()){
            PaintedRock pRock = (PaintedRock) rList.next(); //load the hopper from origin
            ParallelTransition parTemp;
            SequentialTransition seqTemp=new SequentialTransition();
            if(pRock.rock.colour==Color.RED){
                    pRock.rock.center[x]=tempX+diameter;
                    pRock.rock.center[y]=tempY;
                    pRock.rock.curlVector.angle = -720; //2 * 360 degrees rotation
                    pRock.rock.curlVector.setDelta();
                    parTemp = parallelSequence(pRock,d);
                    }
            else{
                pRock.rock.center[x]=tempX;
                pRock.rock.center[y]=tempY;
                pRock.rock.curlVector.angle = 720;
                pRock.rock.curlVector.setDelta();
                parTemp = parallelSequence(pRock,d);
                tempY += diameter;}
            seqTemp.getChildren().add(parTemp);
            resSeq.getChildren().add(seqTemp);
            
        }
       return resSeq;
    }
/**
 * Creates the initial load of rocks. The number of rocks is
 * passed via the KurlParams;
 * @param params    KurlParams;
 */    
    private void makeRocks(KurlParams params){
        int rockNum = 0;
        int numberOfRocks = (int)params.get("numberOfRocks");
        Color col;
        String colStr;
        PaintedRock pRock;
        for(int i=0;i<numberOfRocks;i++){
          if(i%2>0){
              col = Color.YELLOW;
              colStr = "yellow";
          } else {
              col = Color.RED;
              rockNum++;
              colStr = "red";
          }
          params.set("rockNumber",rockNum);
          params.set("colour",col);
          pRock = new PaintedRock(params);
          colStr += " rock "+rockNum;
          //printIt("Loading "+colStr);
          pRock.setId(colStr);
          getChildren().add(pRock);
         }
    } 
}