/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kurlingfx2;

import java.util.Iterator;
import javafx.animation.SequentialTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Orientation;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
/**
 * my 'kurling' stuff library
 */
import kurlingstuff.PaintedIce;
import kurlingstuff.PaintedRock;
import kurlingstuff.KurlParams;
import kurlingstuff.PaintedBroom;
import kurlingstuff.PaintedRockGroup;
import kurlingstuff.PaintedArrow;
import kurlingstuff.PaintedClocks;
import kurlingstuff.PaintedHammer;
import kurlingstuff.PaintedLogo;
import kurlingstuff.PaintedPlayers;
import kurlingstuff.PaintedScrollBar;
import kurlingstuff.PaintedWatermark;
import kurlingstuff.ScoreBox;

/**
 *
 * @author TokenAtKenz
 * All Rights Reserved
 */
public class KurlingFX2 extends Application {
    private final int x=0,y=1;
    private final int TOP = 1;
    private final int BOTTOM = 2;
    private final int SIDES = 4;
    private int walls;
    private final int ofsX,mainScreenOffsetX;
    private final int ofsY,mainScreenOffsetY;
    private final int aFoot;
    private final double diameter;
    private final int stroke;
    private final double radius;
    private final int numRocks;
    private final int padding;
    private final int iceWide;
    private final int iceHigh;
    private final int frameLength;
    private final double buttonHeight;
    private int vButtonHeight;
    private double buttonCount=0;
    private double paddingCount = 0;
    private final boolean webStart;
    private KurlParams appParams;
    private boolean active = false;
    private boolean okToShoot = false;
    private String hammer;
    private String notHammer;
    private PaintedBroom broom;
    private PaintedRock theRock;
    private SequentialTransition demoReel = new SequentialTransition();
    private SequentialTransition shotReel = new SequentialTransition();
    private SequentialTransition hackReel = new SequentialTransition();
    private Group scrollBars;
    private ScoreBox scoreBoard;
    private Group bumpers;
    private Group theButtons;
    private int rocksInPlay = 0;
    private final int initialRockSpeed=3;
    private final int initialCurlSpeed=0;
    private int currentEnd = 1;
    private int redScore = 0;
    private int yellowScore = 0;
    private final String ip = "69.7.252.41";
    /**
     *Initialize all the parameter
     */
    public KurlingFX2(){    //Constructor
        super();
        webStart =//true;
                  false;
        if(webStart){aFoot = 800/34;}//600/27;}
        else{aFoot = getFootSize(34,26);}
        ofsX=6;
        ofsY=1;
        mainScreenOffsetX = aFoot*25;
        mainScreenOffsetY = aFoot/2;
        diameter =  3.25*aFoot/Math.PI;
        buttonHeight = aFoot;
        padding = aFoot/4;
        radius = diameter/2;
        stroke = aFoot/12;
        numRocks = 16;
        iceHigh = 31*aFoot;
        iceWide = 14*aFoot;
        frameLength = 17;
        walls = TOP + BOTTOM + SIDES;
    }
    /**
     * gets the relative size of a "foot" based on a rink size
     * of 31 feet by 14 feet. Returns a "foot" size that is used
     * to scale the rink, the rocks, the buttons, etc. pretty much
     * the entire program is scaled to the variable "aFoot".
     * 
     * @param footsHigh 
     * @param footsWide 
     * @return 
     */
    private int getFootSize(int footsHigh,int footsWide){
        Rectangle2D main = Screen.getPrimary().getVisualBounds();
        int tf = (int)main.getMaxY() / footsHigh;
        if(main.getMaxX() < footsWide*tf){
            return (int)main.getMaxX()/footsHigh;
        }
        else{return tf;}
    }
    private KurlParams makeDefaultParams(Stage stage){
       KurlParams ret = new KurlParams();
       //stage.setMinHeight(stage.getHeight());
            hammer = "red";
            ret.set("hammer",hammer);
            notHammer = "yellow";
            ret.set("notHammer",notHammer);
            ret.set("footSize",aFoot);
            ret.set("stroke",stroke);
            ret.set("padding",padding);
            ret.set("diameter",diameter);
            ret.set("radius",radius);
            ret.set("iceWide",iceWide);
            ret.set("iceHigh",iceHigh);
            ret.set("numberOfRocks",numRocks);
            ret.set("frameLenth",frameLength);
            ret.set("ofsX",ofsX*aFoot+padding);
            ret.set("ofsY",ofsY*aFoot+padding);
            ret.set("baseFriction",(double)0.007);//(aFoot * 0.001));
            ret.set("curlFricFrac",(double)0.70); //curl friction % of friction
            ret.set("fricFrac",(double)0.30); //% friction variance to the edges
            ret.set("curlRatioSize",(double)0.15); //curl factor on forward motion
            ret.set("thud",(double).78);
            ret.set("gameType","SHOOT");
            ret.set("topX",iceWide/2+stroke-radius);
            ret.set("topY", 2*aFoot+stroke-3*radius);
            
           //walls = TOP | BOTTOM | SIDES; //walls up
            walls = 0;
            ret.set("walls",walls);
            double[] btnLoc = {(7+ofsX)*aFoot+padding-radius,
                                  (23+ofsY)*aFoot+padding-radius};
            ret.set("btnLoc",btnLoc);
            double[] rockLoc = new double[2];
            rockLoc[x] = padding+padding;
            rockLoc[y] = (31+ofsY)*aFoot+padding-diameter;
            ret.set("rockBox",rockLoc);
            ret.set("walls",walls);
            ret.set("TOP",TOP);
            ret.set("BOTTOM",BOTTOM);
            ret.set("SIDES",SIDES);
            ret.set("image1","http://"+ip+"/KurlingFX/Ptolemy1.png");
            ret.set("image2","http://"+ip+"/KurlingFX/Ptolemy2.png");
            ret.set("image3","http://"+ip+"/KurlingFX/laserPig.png");
            ret.set("player1","Token AtKenz");
            ret.set("player2","Kurlin AtKenz");
            //ret.set("sceneColor",Color.MIDNIGHTBLUE);
            ret.set("maxSpeed",(double)8500);
            ret.set("maxCurl",(double)8500);
            ret.set("sceneColor",Color.STEELBLUE);
            ret.set("dnColor",Color.ROSYBROWN);
            ret.set("hzColor",Color.ROSYBROWN);
       return ret;
    }
    
    private Button mkButton(String s,double x,double y,double w,double h){
            Button b = new Button(s);
            b.setLayoutX(x);b.setLayoutY(y);
            b.setMinHeight(h);b.setMinWidth(w);
            b.setFont(new Font("Arial Bold",aFoot*.5));
            return b;
    }
    private Button demoButton(PaintedRockGroup rocks,
                                      double x,double y,double w,double h){
        Button ret = mkButton("Watch Demo",x,y,w,h);
        ret.setId("Watch Demo");
        ret.setOnAction((ActionEvent e) -> {
            if(!active){autoGame(rocks);}
        });
        return ret;
        
    }
    private void shootTheRock(PaintedRockGroup rocks){
            if(active && okToShoot){
                setParamsFromScrollBars();
                if(theRock!=null){
                    if(appParams.get("curlDir")==null){
                        appParams.set("curlDir",Math.signum(Math.random()-0.5));
                    }
                    
                    double n = 5;
                    if(appParams.get("rockVectorSize")==null){
                        appParams.set("rockVectorSize", n);} 
                    
                    double xDiff = broom.getLayoutX()-theRock.rock.center[0];
                    double yDiff = broom.getLayoutY()-theRock.rock.center[1];
                    double rva = theRock.rock.rockVector.d2a(xDiff,yDiff);
                    appParams.set("rockVectorAngle",rva);
                    
                    if(appParams.get("curlVectorAngle")==null){
                        appParams.set("curlVectorAngle",rva); }
                    
                    if(appParams.get("curlVectorSize")==null){
                    appParams.set("curlVectorSize", (double)3);}
                   
                    takeTheShot(rocks,theRock);}
                else{active=false;}
        }
    }
    private Button exitButton(String s,double x,double y,double w,double h){
         Button ret = mkButton(s,x,y,w,h);
         ret.setId(s);
         ret.setFont(new Font("Arial Bold",aFoot*.6));
         ret.setOnAction(e->System.exit(0));
         return ret;
    }
    private Button exitButton2(String s,double x,double y,double w,double h){
         Button ret = mkButton(s,x,y,w,h);
         ret.setId(s);
         ret.setFont(new Font("Arial Bold",aFoot*.525));
         ret.setOnAction(e->System.exit(0));
         return ret;
    }
    private Button aboutButton(String s,double x,double y,double w,double h){
         Button ret = mkButton(s,x,y,w,h);
         ret.setId(s);
         ret.setFont(new Font("Arial Bold",aFoot*.525));
         
         ret.setOnAction((ActionEvent e) -> {
             Alert alert = new Alert(AlertType.INFORMATION);
             alert.setTitle("Kenz Krazy Kurling FX");
             alert.setHeaderText(null);
             Text t = new Text();
             t.setFont(new Font(20));
             
             t.setWrappingWidth(alert.getWidth() - 4* aFoot);
             t.setTextAlignment(TextAlignment.CENTER);
             t.setText("KurlingFX.in.JavaFX  Verision.1.0    "+
                         "      copyright.c.2016.BETA-VERSION       "+
                         "          all.rights.reserved             "+
                         "       for.testing.and.demo.only.         ");
             alert.setContentText(t.getText());
             alert.showAndWait();
         });
         return ret;
    } 
    /**
     * returns ON if the value is False, and OFF if the value is true.
     * the purpose is to make the action buttons say what they will do, not 
     * what the variable is currently set at. Keeps the ! out of it.
     * @param b     boolean
     * @return      String  (false=ON,true=OFF)
     */
    private String onOff(boolean b){
        if(b){return "OFF";}
        else {return "ON";}
    }
    private Button sideButton(String s,double x,double y,double w,double h){
         Button ret = mkButton(s,x,y,w,h);
         ret.setId(s);
         ret.setOnAction(e->{
            walls = walls^SIDES;
            changeBumper(walls);
            String ts = onOff((walls&SIDES) == SIDES);
            if(ts.equals("ON")){
                ret.setText("Put Sides UP");
            } else{ret.setText("Put Sides DOWN");}
         });
         return ret;
    }
    private Button backButton(String s,double x,double y,double w,double h){
         Button ret = mkButton(s,x,y,w,h);
         ret.setId(s);
         ret.setOnAction(e->{
             int w2 = BOTTOM+TOP;
            walls = walls^(w2);
            
            changeBumper(walls);
            String ts = onOff((walls&w2) == w2);
            if(ts.equals("ON")){
                ret.setText("Put Ends UP");
            } else{ret.setText("Put Ends DOWN");}
         });
         return ret;
    }
    private Button resetButton(Button pb,PaintedRockGroup pg,double x,double y,double w,double h){
         String s = "RESET";
         Button ret = mkButton(s,x,y,w,h);
         ret.setId(s);
         
         ret.setFont(new Font("Arial Bold",aFoot*.6));
         ret.setOnAction((ActionEvent e) -> {
             hackReel.stop();
             shotReel.stop();
             demoReel.stop();
             active = false;
             pg.doReset(appParams);
             scoreBoard.clearTheBoard();
             pb.setText("NEW GAME");
             resetBroom();
         });
         return ret;
    }
    
    private Group getButtons(PaintedRockGroup rocks){
        double locX = padding;
        double yOrig = (ofsY*aFoot)+padding+stroke;
        double w = aFoot * ofsX - padding - aFoot;
        double h = buttonHeight;
        double locY = yOrig;
        Group ret = new Group();
        Button playButton;
        ret.setId("buttonGroup");
        playButton = getPlayButton(rocks,locX,locY,w,h*3);
        ret.getChildren().add(playButton);
        locY += (padding*2+h*3);buttonCount+=3;paddingCount+=3;
        ret.getChildren().add(sideButton("Put Sides UP",locX,locY,w,h));
        locY += (padding+h);buttonCount++;paddingCount++;
        ret.getChildren().add(backButton("Put Ends UP",locX,locY,w,h));
        locY += (padding+h);buttonCount++;paddingCount++;
        ret.getChildren().add(demoButton(rocks,locX,locY,w,h));
        locY += (padding*2+h);buttonCount++;paddingCount+=2;
        ret.getChildren().add(resetButton(playButton,rocks,locX,locY,w,h*2+padding));
        locY += (padding*3+h*2);buttonCount+=2;paddingCount+=3;
        ret.getChildren().add(exitButton("EXIT",locX,locY,w,h*2));
        buttonCount+=2;
        ret.getChildren().add(aboutButton("?",
                 iceWide +aFoot*ofsX+2*diameter+4*padding
                -(aFoot*2+padding),padding,aFoot-stroke,aFoot-2*stroke));
        ret.getChildren().add(exitButton2("X",
                iceWide +aFoot*ofsX+2*diameter+4*padding
                -(aFoot+padding),padding,aFoot-stroke,aFoot-2*stroke));
        vButtonHeight = (int)(buttonHeight*buttonCount+padding*paddingCount);
        
        return ret;
    }
    
    private ScrollBar mkScrollBar(double x,double y,Orientation o,
                                       double max,double min,
                                       double minH,double minW,
                                       String id){
        ScrollBar ret = new PaintedScrollBar();
        ret.setLayoutX(x);ret.setLayoutY(y);
        ret.setOrientation(o);
        ret.setMax(max); ret.setMin(min);
        ret.setMinHeight(minH);ret.setMinWidth(minW);
        ret.setUnitIncrement(1000);
        ret.setBlockIncrement(1000);
        ret.setBackground(Background.EMPTY);
        //ret.setStyle("speedbar");
        //ret.setVisibleAmount(100);
        ret.setId(id);
       return ret;
    }
    private Group getScrollBars(){
        Group ret = new Group();
        ret.setId("ScrollBarGroup");
        double locX = ofsX * aFoot + padding - aFoot-stroke;
        double locY = ofsY * aFoot + padding+stroke;
        Orientation o = Orientation.VERTICAL;  
        double max = (double)appParams.get("maxSpeed");
        double min = 0;
        double minW = aFoot;
        double minH = buttonHeight*buttonCount+padding*paddingCount+2*aFoot+padding;
        ret.getChildren().add(mkScrollBar(locX,locY,o,max,min,minH,minW,"SpeedBar"));
       
        locX = ofsX * aFoot + padding+stroke;
        locY = padding;
        o = Orientation.HORIZONTAL;
        max = (double)appParams.get("maxCurl");min = -max;
        minH = aFoot; minW = aFoot*14;
        ScrollBar sb = mkScrollBar(locX,locY,o,max,min,minH,minW,"CurlBar");
        sb.setVisible(true);
        ret.getChildren().add(sb);
        
        return ret;
    }
    /**
     * sets the position of the scrollbar indicators
     * 
     * @param v     the vertical position to set the scrollbar
     * @param h     the horizontal position to set the scrollbar
     */
    public void setScrollBars(double v,double h){
        Iterator sbz = scrollBars.getChildren().iterator();
        ScrollBar sb;
        while(sbz.hasNext()){
            sb = (ScrollBar)sbz.next();
            switch(sb.getId()){
                case "SpeedBar":
                    sb.setValue(v*1000);
                    appParams.set("rockVectorSize",v);
                    break;
                case "CurlBar":
                    sb.setValue(-h*1000);
                    double n = Math.signum(h);
                    if(n==0){n=1;}
                    appParams.set("curlVectorSize",Math.abs(h));
                    appParams.set("curlDir",n);
                    break;
                default: break;
            }
        }
    }

    /**
     *
     */
    public void setParamsFromScrollBars(){
        Iterator sbz = scrollBars.getChildren().iterator();
        ScrollBar sb;
        double n;
        while(sbz.hasNext()){
            sb = (ScrollBar)sbz.next();
            if(sb.getId().equals("SpeedBar")){
                n = sb.getValue()*.001;
                appParams.set("rockVectorSize", n);
            }
            else {
                
              n = Math.signum(sb.getValue());
              if(n==0){n=1;}
              appParams.set("curlDir",-n);
              n = Math.abs(sb.getValue()*.001);
              appParams.set("curlVectorSize",n);
            }
        }
    }
    
    private ImageView mkUrlAd(String s,String id){
        ImageView iv = new ImageView(s);
        iv.autosize();
        iv.setId(id);
        return iv;
    }
    
    private Group getAds(){
        Group ret = new Group();
        ret.setId("getAdsGroup");
        int w = 4*aFoot; int h=6*aFoot;
        ret.getChildren().add(mkUrlAd((String)appParams.get("image1"),"image1"));
        ret.getChildren().add(mkUrlAd((String)appParams.get("image2"),"image2"));
        ret.getChildren().add(mkUrlAd((String)appParams.get("image3"),"image3"));
        return ret;
    }
    private void changeBumper(int w){
        Iterator lines = bumpers.getChildren().iterator();
        Line l;
        int num = TOP;
        while(lines.hasNext()){
           l = (Line)lines.next();
           l.setVisible((num&w)==num);
           if(num == TOP){num = BOTTOM;}else{num=SIDES;}
           }
        appParams.set("walls", walls);
    }
    private Group bumperLines(){
        Group bl = new Group();
        bl.setId("bumperLineGroup");
        int t=0;int b=1;int l=2;int r = 3;
        int strk = (int)appParams.get("stroke");
        double s2 = strk*3;
        int w = (int)appParams.get("iceWide")+strk;
        int h = (int)appParams.get("iceHigh")+strk;
        Line line = new Line(stroke,stroke,w,stroke);
        line.setStrokeWidth(s2);
        line.setStroke(Color.MEDIUMSPRINGGREEN);
        line.setId("topLine");
         bl.getChildren().add(line);
        line = new Line(stroke,h,w,h);
        line.setStrokeWidth(s2);
        line.setStroke(Color.MEDIUMSPRINGGREEN);
        line.setId("bottomLine");
        bl.getChildren().add(line);
        line = new Line(stroke,stroke,stroke,h);
        line.setStrokeWidth(s2);
        line.setStroke(Color.MEDIUMSPRINGGREEN);
        line.setId("leftLine");
        bl.getChildren().add(line);
        line = new Line(w,stroke,w,h);
        line.setStrokeWidth(s2);
        line.setStroke(Color.MEDIUMSPRINGGREEN);
        line.setId("rightLine");
        bl.getChildren().add(line);
        return bl;
    }
    private void doScore(PaintedRockGroup pg){
        int n = (int)Math.abs(Math.random()*30+1);
        String bStr;  
        String nStr;
        KurlParams p = pg.scanToBtn();
        System.out.println("red "+(int)p.get("red"));
        bStr = "red box "+currentEnd;
        nStr = "" + (int)p.get("red");
        redScore += (int)p.get("red");
        scoreBoard.setText(bStr,nStr,Color.WHITE);
        System.out.println("yel "+(int)p.get("yellow"));
        bStr = "yellow box "+currentEnd;
        nStr = "" + (int)p.get("yellow");
        yellowScore += (int)p.get("yellow");
        scoreBoard.setText(bStr,nStr,Color.BLACK);
        bStr = "end box "+currentEnd;
        nStr = ""+currentEnd;
        scoreBoard.setText(bStr,nStr,Color.BLUE);
        bStr = ""+redScore;
        nStr = ""+yellowScore;
        scoreBoard.setText("red total",bStr,Color.WHITE);
        scoreBoard.setText("yellow total",nStr,Color.BLACK);
        scoreBoard.setText("text total","Totals",Color.BLUE);
        currentEnd ++;
        int red = (int)p.get("red");
        int yellow = (int)p.get("yellow");
        if(red > yellow){
            hammer = "yellow";notHammer = "red"; 
            }
        if(yellow > red){
            hammer = "red";notHammer = "yellow"; 
            }
        ((PaintedHammer)appParams.get("hammerLogo")).setHammerColor(appParams,"black");
        ((PaintedHammer)appParams.get("hammerLogo")).setHammerColor(appParams,hammer);
        //if(currentEnd == 12){currentEnd = 1;yellowScore=0;redScore=0;}
    }
    private Button getPlayButton(PaintedRockGroup pg,double x,double y,double w,double h){
        final String startNewGame = "NEW GAME";
        final String startNextEnd = "Next End";
        final String scoreTheEnd = "Score The End";
        final String shootTheRock = "SHOOT";
        Button btn = mkButton(startNewGame,x,y,w,h);
        btn.setFont(new Font("Arial Bold",aFoot*.6));
        btn.setOnAction(e->{
               String btnStr,retStr;
               btnStr = (String)btn.getText(); 
               System.out.println(btnStr);
               retStr = btnStr;
               switch(btnStr){
                   case startNewGame:
                        //System.out.println(btnStr+" case");
                        hackReel.stop();
                        shotReel.stop();
                        demoReel.stop();
                        active = false;
                        pg.doReset(appParams);
                        resetBroom();
                        scoreBoard.clearTheBoard();
                        currentEnd = 1;
                        yellowScore = 0;
                        redScore = 0;
                        playNewEnd(pg);
                        btn.setText(shootTheRock);
                        break;
                   case startNextEnd:
                       //System.out.println(btnStr+" case");
                       if(currentEnd < 12){ 
                       hackReel.stop();
                        shotReel.stop();
                        demoReel.stop();
                        active = false;
                        pg.doReset(appParams);
                        resetBroom();
                        playNewEnd(pg);
                        btn.setText(shootTheRock);
                        break;}
                   case scoreTheEnd:
                       //System.out.println(btnStr+" case");
                       btn.setText(startNextEnd);
                       doScore(pg);
                       if(currentEnd > 10){
                         currentEnd = 11;  
                         if(redScore != yellowScore){
                           btn.setText(startNewGame);   
                         }  
                       }
                       break;
                   case shootTheRock:
                         shootTheRock(pg);
                         if(rocksInPlay == numRocks){
                         btn.setText(scoreTheEnd);}
                         break;
              }});
        return btn;
    }
    @Override
    public void start(Stage stage) {
        appParams = makeDefaultParams(stage);
        //checkIni();
        
        Group root = new Group();
        root.setId("root");
        
        PaintedIce ice = new PaintedIce(appParams,getAds());
            ice.setId("ice");
            ice.setLayoutX(ofsX*aFoot+padding);
            ice.setLayoutY(ofsY*aFoot+padding);
        root.getChildren().add(ice);
        
        PaintedWatermark pw = new PaintedWatermark(appParams,5*aFoot,2*aFoot);
            pw.setId("leftWatermark");
            pw.setLayoutX(ofsX*aFoot + aFoot);
            pw.setLayoutY(ofsY*aFoot + padding);
        root.getChildren().add(pw);
        
        pw = new PaintedWatermark(appParams,5*aFoot,2*aFoot);
            pw.setId("rightWatermark");
            pw.setLayoutX(ofsX*aFoot + 8*aFoot);
            pw.setLayoutY(ofsY*aFoot + padding);
        root.getChildren().add(pw);
        
        bumpers = bumperLines();
            bumpers.setId("bumpers");
            bumpers.setLayoutX(ofsX*aFoot+padding);
            bumpers.setLayoutY(ofsY*aFoot+padding);
        changeBumper(walls);    
        root.getChildren().add(bumpers);
        
        PaintedRockGroup bagOfPaintedRocks = new PaintedRockGroup(appParams);
            bagOfPaintedRocks.setId("bagOfPaintedRocks");
        root.getChildren().add(bagOfPaintedRocks);
        
        theButtons = getButtons(bagOfPaintedRocks);
            theButtons.setId("theButtons");
        root.getChildren().add(theButtons);
        
        PaintedArrow downArrow = new PaintedArrow(appParams,aFoot,
                                          vButtonHeight+2*aFoot-2*stroke,0);
            downArrow.setId("downArrow");
            downArrow.setLayoutX(ofsX*aFoot+padding-aFoot-stroke);
            downArrow.setLayoutY(ofsY*aFoot+padding+1);
        root.getChildren().add(downArrow);
        
        PaintedArrow leftArrow = new PaintedArrow(appParams,7*aFoot,aFoot,1);
            leftArrow.setId("leftArrow");
            leftArrow.setLayoutX(ofsX*aFoot+padding);
            leftArrow.setLayoutY(ofsY*aFoot+padding-aFoot);
        root.getChildren().add(leftArrow);
        
        PaintedArrow rightArrow = new PaintedArrow(appParams,7*aFoot,aFoot,2);
            rightArrow.setId("rightArrow");
            rightArrow.setLayoutX((7+ofsX)*aFoot+padding+2*stroke);
            rightArrow.setLayoutY(ofsY*aFoot+padding-aFoot);
        root.getChildren().add(rightArrow);
        
        scrollBars = getScrollBars();
            scrollBars.setId("scrollBars");
            setScrollBars(initialRockSpeed,initialCurlSpeed);
        root.getChildren().add(scrollBars);
        
        scoreBoard = new ScoreBox(appParams);
            currentEnd = 1;
            yellowScore = 0;
            redScore = 0;
            scoreBoard.setId("scoreBoard");
            scoreBoard.setLayoutX(padding);
            scoreBoard.setLayoutY(16*aFoot+padding);
        root.getChildren().add(scoreBoard); 
        
        PaintedLogo paintedLogo = new PaintedLogo(appParams,
                                             6*aFoot-padding+stroke,aFoot-padding);
          paintedLogo.setId("paintedLogo");
          paintedLogo.setLayoutX(padding);
          paintedLogo.setLayoutY(padding);
          root.getChildren().add(paintedLogo);
        
        Group clockGroup = new Group();
            clockGroup.setId("clockGroup");
            clockGroup.getChildren().
                add(new PaintedClocks(appParams,3*aFoot-3*padding,
                                             2*aFoot+3*padding));
            clockGroup.setLayoutX(3*aFoot+2*padding);
            clockGroup.setLayoutY(16*aFoot+padding);
        root.getChildren().add(clockGroup);
        
        Group playerGroup = new Group();
            playerGroup.setId("playerGroup");
            playerGroup.getChildren().
                add(new PaintedPlayers(appParams,ofsX*aFoot-(padding+stroke)-aFoot,2*aFoot));
            playerGroup.setLayoutX(padding);
            playerGroup.setLayoutY(14*aFoot-padding+stroke);
        root.getChildren().add(playerGroup);
        
        
        PaintedHammer pHammer = new PaintedHammer(appParams,aFoot,2*aFoot);
            pHammer.setId("paintedHammer");
            pHammer.setLayoutX(4*aFoot);
            pHammer.setLayoutY(playerGroup.getLayoutY()-padding+2*stroke);
            appParams.set("hammerLogo",pHammer);
        root.getChildren().add((PaintedHammer)appParams.get("hammerLogo"));
            pHammer.setHammerColor(appParams,notHammer);
            pHammer.setHammerColor(appParams,"black");
            pHammer.setHammerColor(appParams,hammer);
        
        broom = new PaintedBroom(appParams);
            broom.setId("broom");
            resetBroom();
        root.getChildren().add(broom);
        
        Pane bp = new Pane();
        bp.getChildren().add(root);
        bp.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,null,null)));
        bp.setEffect(new DropShadow());
        
        Scene scene = new Scene(bp);
        scene.setFill((Color)appParams.get("sceneColor"));
       
        stage.setTitle("Kenz Krazy Kurling ver 1.0");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setX(mainScreenOffsetX);
        stage.setY(mainScreenOffsetY);
        stage.setWidth(iceWide +aFoot*ofsX+2*diameter+4*padding);
        stage.setHeight(iceHigh+aFoot*(ofsY)+4*stroke+2*padding);
       
        
        stage.show();    
    }  
//END OF START
    
     /**
     * sets the broom into position and turns it on
     */ 
    public void resetBroom(){
        broom.setLayoutX(ofsX*aFoot-diameter);
        broom.setLayoutY(ofsY*aFoot+iceHigh-diameter*4);
        broom.setVisible(true);
        broom.clearRock();
     }

    /**
     *
     */
    public void setRandomParams(){
        double rs = Math.random()*
                     ((double)appParams.get("maxSpeed")*0.50) +
                      (double)appParams.get("maxSpeed")*0.25;
        double rc = Math.random()*
                     ((double)appParams.get("maxCurl")*0.50) +
                      (double)appParams.get("maxCurl")*0.25;
        double ra = Math.random() * (Math.PI * 0.5) + 
                      ((Math.PI*.25)+Math.PI);
        double curlDir = Math.signum(Math.random()*1-0.5);
        if(curlDir ==0){curlDir = 1;}
        //System.out.println("rs["+rs+"  rc["+rc+"]  ra["+ra+"]  cd["+curlDir);
        appParams.set("curlDir",curlDir);
        appParams.set("rockVectorSize", rs *.001);
        appParams.set("curlVectorSize", rc * .001);
        appParams.set("rockVectorAngle", ra);
        appParams.set("curlVectorAngle",ra);
    } 

    /**
     *
     */
    public void initializeRockParams(){
        double curlDir = 1;
        appParams.set("curlDir",curlDir);
        appParams.set("rockVectorSize", initialRockSpeed);
        appParams.set("curlVectorSize",initialCurlSpeed);
        setScrollBars(initialRockSpeed,initialCurlSpeed);
        double n=Math.PI * 1.5;
        appParams.set("rockVectorAngle", n);
        n=0;
        appParams.set("curlVectorAngle",n);
    }  
    /**
     * updates theRock by calling nextRock() 
     * and then rolls theRock up to the hog line.
     * @param pg KurlingStuff.PaintedRockGroup
     */ 
    public void loadHack(PaintedRockGroup pg){
         System.out.println("rocks in play "+rocksInPlay);
         int r = rocksInPlay/2 + 1;
         int valX = rocksInPlay % 2;
         if(valX==0){
             theRock = getRock(pg,notHammer+" rock "+r);
             System.out.println("gettin nothammer "+notHammer+" rock "+r);
         } else{
             theRock = getRock(pg,hammer+" rock "+r);
              System.out.println("gettin hammer "+hammer+" rock "+r);
         }
         rocksInPlay++;
         if(theRock != null){
            setScrollBars(initialRockSpeed,initialCurlSpeed);
            hackReel = pg.hackSequence(appParams,theRock);
            hackReel.setOnFinished(e->{//resetBroom();
                                resetBroom();
                                broom.setRock(theRock);
                                okToShoot=true;
                                });
            hackReel.play();
         }
     }
    /**
      * shoots theRock
      * @param pg       KurlingStuff.PaintedRockGroup
      * @param pRock    KurlingStuff.PaintedRock
      */
     public void takeTheShot(PaintedRockGroup pg,PaintedRock pRock){
         if(pRock != null){
            okToShoot=false;broom.clearRock();resetBroom();
            shotReel = pg.shotSequence(appParams,pRock);
            shotReel.setOnFinished(e->{
                                   
                                   loadHack(pg);});
            shotReel.play();
         }else{active=false;}
     }
    /**
     * returns the rock in the group by it's getId name. Kinda the global next()
     * @param pg    KurlingStuff.PaintedRockGroup
     * @param s     KurlingStuff.Object name
     * @return      KurlingStuff.PaintedRock
     */
     public PaintedRock getRock(PaintedRockGroup pg,Object s){
         PaintedRock aRock = null;
         //System.out.println(" getRock "+s);
         Iterator r = pg.getChildren().iterator();
         boolean found = false;
         while(!found && r.hasNext()){
             PaintedRock pRock = (PaintedRock)r.next();
             if(pRock.getId().equals(s)){
                 found = true;
                 aRock = pRock;
             } 
         }
         return aRock;
     }
     /**
      * Starts up a new curling end with 'numRocks' rocks
      * @param pg   KurlingStuff.PaintedRockGroup
      */
     public void playNewEnd(PaintedRockGroup pg){
         active = true;
         rocksInPlay=0;
         SequentialTransition resetSeq = pg.resetSequence(appParams);
         resetSeq.setOnFinished(e->{
                okToShoot=false;
                resetBroom();
                loadHack(pg);
         });
         resetSeq.play();
        }
    /**
     * sets off an automatic curling end that cannot be interrupted
     * @param pg    KurlingStuff.PaintedRockGroup
     */
    public void autoGame(PaintedRockGroup pg){
         active = true;
         broom.setVisible(false);
         initializeRockParams();
         SequentialTransition resetSeq = pg.resetSequence(appParams);
         resetSeq.setOnFinished(e->{
             demoReel = new SequentialTransition();
             SequentialTransition t;
             SequentialTransition ss;
             Iterator rocks = pg.getChildren().iterator();
             while(rocks.hasNext()){
                setRandomParams();
                PaintedRock pRock = (PaintedRock)rocks.next();
                t = new SequentialTransition();
                ss=pg.hackSequence(appParams, pRock);
                t.getChildren().add(ss);
                t.getChildren().add(pg.setupSequence(appParams, pRock));
                t.getChildren().add(pg.shotSequence(appParams,pRock));
                
                demoReel.getChildren().add(t);
                }
                demoReel.setOnFinished(ee->active = false);
                
                 //ProgressBar pb = parallel sequence the length of s.play;
                
                demoReel.play();
         });
        resetSeq.play();
        System.out.println("AutoGame Sequence Creation Complete");
      }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Working Directory = " +
              System.getProperty("user.dir"));
        launch(args);
    }
}
