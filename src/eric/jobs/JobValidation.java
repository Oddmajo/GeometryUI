/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.jobs;

import eric.GUI.pipe_tools;
import eric.Progress_Bar;
import java.util.ArrayList;
import java.util.Enumeration;
import rene.gui.Global;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.construction.Construction;
import rene.zirkel.objects.ConstructionObject;
import rene.zirkel.objects.PointObject;

/**
 *
 * @author erichake
 */
public class JobValidation {

private final int MAX=1000;
    private ZirkelCanvas ZC;
    private Construction C;
    private ArrayList<PointObject> mobiles=new ArrayList<PointObject>();
    private ArrayList<double[]> mobilesCoords=new ArrayList<double[]>();
    private ArrayList<ConstructionObject> targets, clones;

    public JobValidation(ZirkelCanvas zc) {
        ZC=zc;
        C=ZC.getConstruction();
        initMobiles();
        targets=ZC.job_getTargets();
    }

    public void initMobiles() {
        mobiles.clear();
        mobilesCoords.clear();
        Enumeration e=C.elements();
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

    /**
     * Find every clones of targets object in the whole construction
     * @return true if everything is fine. return false in case of
     * error.
     */
    public boolean initClones() {
        clones=new ArrayList<ConstructionObject>();
        for (int i=0; i<targets.size(); i++) {
            ConstructionObject o=targets.get(i);
            Enumeration e=C.elements();
            while (e.hasMoreElements()) {
                ConstructionObject c=(ConstructionObject) e.nextElement();
                if ((o!=c)&&(!c.isHidden())) {
                    /* Careful ! "equals" is a method of ConstructionObjects classes :
                     * It says if both objects have same type and are at the same places.
                     */
                    if (o.equals(c)) {
                        clones.add(c);
                        break;
                    }
                }
            }
        }
        return (targets.size()==clones.size());
    }

    public void reset() {
        // Reset position for non magnetic mobile objects :
        for (int i=0; i<mobiles.size(); i++) {
            double[] tab=mobilesCoords.get(i);
            mobiles.get(i).move(tab[0], tab[1]);
        }
        ZC.validate();
    }

    public boolean checkHorizontalAligment() {
        double inc=2*C.getW()/(mobiles.size()+2);
        double left=C.getX()-C.getW()+inc;
        for (int i=0; i<mobiles.size(); i++) {
            PointObject pt=mobiles.get(i);
            pt.move(left, 0);
            pt.validate();
            left+=inc;
        }
        ZC.validate();
        return isTargetsEqualClones();
    }

    public boolean check1step() {
        for (int i=0; i<mobiles.size(); i++) {
            PointObject pt=mobiles.get(i);
            pt.alea();
        }
        ZC.validate();
        return isTargetsEqualClones();
    }

    public boolean isTargetsEqualClones() {
        for (int i=0; i<targets.size(); i++) {
            if ((targets.get(i).valid())||(clones.get(i).valid())) {
                if (!targets.get(i).equals(clones.get(i))) {
                    return false;
                }
            }
        }
        return true;
    }

    public void dynamicConstructionFailed() {
        reset();
        String message="<b>"+ZC.job_getMessageFailed();
        message+="</b><br>("+Global.Loc("restrict.failed.initial")+")";
        if (!pipe_tools.Exercise_To_HTML(false, ZC.job_getMessageFailed())) {
            JobMessage.showMessage(message);
        }
    }

    public void dynamicConstructionFailed(int failed) {
        reset();
        double d=100.0*failed/(MAX+1);
        String message="<b>"+ZC.job_getMessageFailed();
        message+="</b><br>("+Global.Loc("restrict.failed.percent")+String.valueOf(Math.round(d))+"%)";
        if (!pipe_tools.Exercise_To_HTML(false, ZC.job_getMessageFailed())) {
            JobMessage.showMessage(message);
        }
    }


    public void staticConstructionFailed() {
        reset();
        String message="<b>"+ZC.job_getMessageFailed()+"</b>";
        if (!pipe_tools.Exercise_To_HTML(false, ZC.job_getMessageFailed())) {
            JobMessage.showMessage(message);
        }
    }

    public void constructionOk() {
        reset();
        if (!pipe_tools.Exercise_To_HTML(true, ZC.job_getMessageOk())) {
            JobMessage.showMessage(ZC.job_getMessageOk());
        }
    }

    public void constructionOkExceptAlignment() {
        reset();
        String message="<b>"+ZC.job_getMessageOk();
        message+="</b><br>("+Global.Loc("job.gui.alignment")+")";
        if (!pipe_tools.Exercise_To_HTML(true, ZC.job_getMessageOk()+" ("+Global.Loc("job.gui.alignment")+")")) {
            JobMessage.showMessage(message);
        }
    }

    public void justSee() {
        ZC.paint(ZC.getGraphics());
        try {
            Thread.sleep(200);
        } catch (InterruptedException ex) {
        }
    }

    public void checkAllsteps() {
        if ((mobiles.size()>0)&&(!ZC.job_isStaticJob())) {
            // if it's a dynamic exercise :
            int failed=0;
            if (!initClones()) {
                dynamicConstructionFailed();
                return;
            }
            int progress=0;
            Progress_Bar.create(Global.Loc("job.gui.progressmessage"), 0, MAX-1);
            for (int i=0; i<MAX; i++) {
                if (!check1step()) {
                    failed++;
                }
                Progress_Bar.nextValue();
            }
            Progress_Bar.close();
            if (failed>0) {
                dynamicConstructionFailed(failed);
                return;
            }
            if (!checkHorizontalAligment()) {
                constructionOkExceptAlignment();
                return;
            }
            constructionOk();
        } else {
            // if it's a static exercise :
            if (!initClones()) {
                staticConstructionFailed();
                return;
            }
            constructionOk();
        }
    }
}
