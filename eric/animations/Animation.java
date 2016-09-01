/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.animations;

import rene.zirkel.ZirkelCanvas;
import rene.zirkel.objects.ConstructionObject;
import rene.zirkel.objects.ExpressionObject;
import rene.zirkel.objects.PointObject;
import rene.zirkel.objects.PrimitiveCircleObject;
import rene.zirkel.objects.SegmentObject;

/**
 *
 * @author erichake
 */
public class Animation {

    private ZirkelCanvas ZC;
    private ConstructionObject O=null;
    private String ObjectName;
    private AnimationPanel PANEL;
    private boolean Negative=false;
    private Thread TH=null;
    private boolean thread_alive=true;
    private double min, max, positiveincrement, parts=100;//For slider Expressions

    public Animation(ZirkelCanvas zc, AnimationPanel panel, ConstructionObject o) {
        ZC=zc;
        O=o;
        PANEL=panel;
        ObjectName=O.getName();
    }

    public Animation(ZirkelCanvas zc, AnimationPanel panel, String objectname) {
        ZC=zc;
        ObjectName=objectname;
        PANEL=panel;
    }

    public boolean getNegative() {
        return Negative;
    }

    public void setNegative(boolean neg) {
        Negative=neg;
    }

    public String getObjectName() {
        return ObjectName;
    }

    public ConstructionObject getObject() {
        if (O==null) {
            O=ZC.getConstruction().find(ObjectName);
        }
        return O;
    }

    public void killThread() {
        thread_alive=false;
    }

    public void run() {
        if (TH!=null) {
            return;
        }
        O=getObject();
        if (O==null) {
            return;
        }
        if (O instanceof PointObject) {
            runPoint();
        } else if (O instanceof ExpressionObject) {
            runExpression();
        }


    }

    public void runPoint() {
        TH=new Thread() {

            public void run() {
                boolean back=false;
                double x=0;



                final ZirkelCanvas canvas=ZC;
                PointObject P=(PointObject) O;
                double x0=P.getX();
                double y0=P.getY();

                if (P.getBound() instanceof SegmentObject) {
                    SegmentObject s=(SegmentObject) P.getBound();
                    x=Math.sqrt((x0-s.getP1().getX())*(x0-s.getP1().getX())+(y0-s.getP1().getY())*(y0-s.getP1().getY()));
                }else if (P.getBound() instanceof PrimitiveCircleObject) {
                    PrimitiveCircleObject c=(PrimitiveCircleObject) P.getBound();
                    x=Math.acos((x0-c.getX())/c.getR())*Math.signum(y0-c.getY());
                }

                if (P.getBound()==null) {
                    return;
                }
                if (!(P.getBound() instanceof SegmentObject)&&!(P.getBound() instanceof PrimitiveCircleObject)) {
                    return;
                }
                long time=System.currentTimeMillis();
                while ((!PANEL.isStopped())&&(thread_alive)) {

                    try {
                        final long t=System.currentTimeMillis();
                        double d=PANEL.getDelay();
                        int h=(int) (t-time);
                        if (h<0) {
                            h=0;
                        }
                        if (h>d) {
                            h=(int) d;
                        }
                        Thread.sleep((int) (d-h));
                        time=System.currentTimeMillis();
                    } catch (final Exception ex) {
                    }
                    if (P.getBound() instanceof SegmentObject) {
                        final SegmentObject s=(SegmentObject) P.getBound();
                        synchronized (canvas) {
                            if (back) {
                                O.move(s.getP1().getX()+(s.getLength()-x)
                                        *s.getDX(), s.getP1().getY()
                                        +(s.getLength()-x)*s.getDY());
                            } else {
                                O.move(s.getP1().getX()+x*s.getDX(), s.getP1().getY()
                                        +x*s.getDY());
                            }
                            ZC.dovalidate();
                            ZC.repaint();
                            x+=ZC.dx(2);
                            if (x>s.getLength()) {
                                back=!back;
                                x=0;
                            }
                        }
                    } else if (P.getBound() instanceof PrimitiveCircleObject) {
                        final PrimitiveCircleObject c=(PrimitiveCircleObject) P.getBound();
                        synchronized (canvas) {
                            O.move(c.getP1().getX()+Math.cos(x)
                                    *c.getR(), c.getP1().getY()
                                    +Math.sin(x)*c.getR());
                            ZC.dovalidate();
                            ZC.repaint();
                            if (Negative) {
                                x-=ZC.dx(2)/c.getR();
                            } else {
                                x+=ZC.dx(2)/c.getR();
                            }
                        }
                    }
                }
                TH=null;
            }
        };
        TH.setPriority(Thread.MIN_PRIORITY);
        TH.start();
    }

    public void initSlidersParameters(ExpressionObject E) {
        min=E.getMinValue();
        max=E.getMaxValue();
        positiveincrement=(max-min)/parts;
    }

    public void runExpression() {
        TH=new Thread() {

            public void run() {

                final ZirkelCanvas canvas=ZC;
                ExpressionObject E=(ExpressionObject) O;
                double x;
                try {
                    x = E.getValue();
                } catch (Exception ex) {
                    x=0;
                }
                double increment=1;


                long time=System.currentTimeMillis();
                while ((!PANEL.isStopped())&&(thread_alive)) {

                    try {
                        final long t=System.currentTimeMillis();
                        double d=PANEL.getDelay();
                        int h=(int) (t-time);
                        if (h<0) {
                            h=0;
                        }
                        if (h>d) {
                            h=(int) d;
                        }
                        Thread.sleep((int) (d-h));
                        time=System.currentTimeMillis();
                    } catch (final Exception ex) {
                    }
                    try {
                        synchronized (canvas) {
                            if (E.isSlider()) {
                                initSlidersParameters(E);
                                if (Negative) {
                                    if (x>max) {
                                        x=min;
                                    } else {
                                        x+=positiveincrement;
                                    }
                                } else {
                                    if (x>max) {
                                        x=max;
                                        increment=-positiveincrement;
                                    } else if (x<min) {
                                        x=min;
                                        increment=positiveincrement;
                                    } else {
                                        x+=Math.signum(increment)*positiveincrement;
                                    }
                                }
                            } else {
                                x+=Negative?-increment:increment;
                            }
                            E.setExpression(""+x, E.getConstruction());
//                            E.setSlider(false);
                            ZC.dovalidate();
                            ZC.repaint();
                        }
                    } catch (Exception ex) {
                    }
                }
                TH=null;
            }
        };
        TH.setPriority(Thread.MIN_PRIORITY);
        TH.start();
    }
}
