/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.monkey;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.objects.ConstructionObject;
import rene.zirkel.objects.PointObject;

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

    public void start() {
        if (shakeThread!=null) return;
        inprogress=true;
        shakeThread=new Thread() {

            public void run() {
                while (inprogress) {
                    long t0=System.currentTimeMillis();
                    move1step();
                    long t1=System.currentTimeMillis();
                    try {
                        long time=waitTime-t1+t0;
                        if (time>0) {
                            Thread.sleep(time);
                        }
                    } catch (Exception ex) {
                    }
                }
                reset();
                shakeThread=null;
            }
        };
        shakeThread.setPriority(Thread.MIN_PRIORITY);
        shakeThread.start();
    }

    public void stop() {
        inprogress=false;
    }
}
