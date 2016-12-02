/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kurlingstuff;

import java.util.ArrayList;
import java.util.Iterator;
import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;

/**
 *
 * @author TokenAtKenz
 */
public class SavedShot extends Object {
    
    ArrayList otherRocks = null;
    SequentialTransition theShot = null;
    public SavedShot(SequentialTransition shot,ArrayList bagOfRocks){
        super();
        SetShot(shot,bagOfRocks);
    }
    
    private void SetShot(SequentialTransition shot,ArrayList bagOfRocks){
        theShot = new SequentialTransition();
        otherRocks = new ArrayList();
        Iterator rocks = bagOfRocks.iterator();
        while(rocks.hasNext()){
            this.otherRocks.add(rocks.next());
        }
        rocks = shot.getChildren().iterator();
        while(rocks.hasNext()){theShot.getChildren()
                .add((ParallelTransition)rocks.next());}
    }
    
    public void ReplayShot(){
        PaintedRock pRock;
        Iterator rocks = otherRocks.iterator();
        SequentialTransition st = new SequentialTransition();
        TranslateTransition t;
       
        while(rocks.hasNext()){
            pRock = (PaintedRock) rocks.next();
            t = new TranslateTransition(Duration.millis(17),pRock);
            t.setToX(pRock.rock.center[0]);
            t.setToY(pRock.rock.center[1]);
            st.getChildren().add(t);
        }
        st.getChildren().add(theShot);
        st.setOnFinished(e->{System.out.println("done replay");});
        st.play();
    }
    
}
