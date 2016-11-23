/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.monkey;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

import channels.fromUI.FromUI;
import eric.JZirkelCanvas;
import mainNonUI.Main;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.ZirkelFrame;
import rene.zirkel.construction.Construction;
import rene.zirkel.objects.ConstructionObject;
import rene.zirkel.objects.PointObject;
import rene.zirkel.objects.PrimitiveLineObject;
import rene.zirkel.objects.SegmentObject;



/**
 *
 * @author erichake
 */
public class monkey {

    private ZirkelCanvas ZC;
    private long waitTime=5;
    private ArrayList<PointObject> mobiles=new ArrayList<PointObject>();
    private ArrayList<double[]> mobilesCoords=new ArrayList<double[]>();
    private boolean inprogress=false;
    private static Thread shakeThread=null;

    public monkey(ZirkelCanvas zc) {
        ZC=zc;
        initMobiles();
    }

    public void initMobiles() {
        mobiles.clear();
        mobilesCoords.clear();
        Enumeration e=ZC.getConstruction().elements();
        while (e.hasMoreElements()) {
            final ConstructionObject c=(ConstructionObject) e.nextElement();
            if (c instanceof PointObject) {
                PointObject pt=(PointObject) c;
                if (pt.moveable()&&pt.insidewindow()) {
                    mobiles.add(pt);
                    double[] tab={pt.getX(), pt.getY()};
                    mobilesCoords.add(tab);
                }
            }
        }
    }

    public void reset() {
        for (int k=25; k>0; k--) {
            long t0=System.currentTimeMillis();
            for (int i=0; i<mobiles.size(); i++) {
                double x0=mobiles.get(i).getX();
                double y0=mobiles.get(i).getY();
                double[] tab=mobilesCoords.get(i);
                double x1=x0+(tab[0]-x0)/k;
                double y1=y0+(tab[1]-y0)/k;
                mobiles.get(i).move(x1, y1);

            }
            ZC.validate();
            ZC.repaint();
            long t1=System.currentTimeMillis();
            try {
                long time=1-t1+t0;
                if (time>0) {
                    Thread.sleep(time);
                }
            } catch (Exception ex) {
            }
        }
    }

    public void move1step() {
        for (int i=0; i<mobiles.size(); i++) {
            PointObject pt=mobiles.get(i);
            pt.shake(ZC);
        }
        ZC.validate();
        ZC.repaint();
    }

    public void start() 
    {
        String[] arguments = new String[] {"123"};
        try
        {
            // 11/21/2016
            //Not sure what ZirkelFrame is for, but it is called whenever a file is saved
            //Might be required later on
//            ZirkelFrame ZF = JZirkelCanvas.getCurrentZF();
            ZirkelCanvas ZC = JZirkelCanvas.getCurrentZC();
            
            FromUI.sendToBackend(ZC);
            
        }
        catch (Exception ex)
        {
            System.out.println("Uh Oh");
        }
//*******************************************************************************************************************************************
// Jay Nash - The following is the original code for the 'monkey' feature within the UI.  It is removed until a custom button can be added 
//          for the 'send to backend' feature
//*******************************************************************************************************************************************
//        if (shakeThread!=null) return;
//        inprogress=true;
//        shakeThread=new Thread() {
//
//            public void run() {
//                while (inprogress) {
//                    long t0=System.currentTimeMillis();
//                    move1step();
//                    long t1=System.currentTimeMillis();
//                    try {
//                        long time=waitTime-t1+t0;
//                        if (time>0) {
//                            Thread.sleep(time);
//                        }
//                    } catch (Exception ex) {
//                    }
//                }
//                reset();
//                shakeThread=null;
//            }
//        };
//        shakeThread.setPriority(Thread.MIN_PRIORITY);
//        shakeThread.start();
    }

    public void stop() {
        inprogress=false;
    }
}
