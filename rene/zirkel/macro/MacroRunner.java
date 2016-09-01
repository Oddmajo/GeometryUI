/*

Copyright 2006 Rene Grothmann, modified by Eric Hakenholz

This file is part of C.a.R. software.

C.a.R. is a free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, version 3 of the License.

C.a.R. is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.

 */
package rene.zirkel.macro;

/**
 * This is an ObjectConstructor, which can run a macro.
 */
import eric.JSelectPopup;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.Vector;
import rene.gui.Global;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.construction.Construction;
import rene.zirkel.construction.ConstructionException;
import rene.zirkel.construction.DepList;
import rene.zirkel.construction.Selector;
import rene.zirkel.constructors.ObjectConstructor;
import rene.zirkel.objects.AngleObject;
import rene.zirkel.objects.AreaObject;
import rene.zirkel.objects.CircleObject;
import rene.zirkel.objects.ConstructionObject;
import rene.zirkel.objects.ExpressionObject;
import rene.zirkel.objects.FixedAngleObject;
import rene.zirkel.objects.FixedCircleObject;
import rene.zirkel.objects.FunctionObject;
import rene.zirkel.objects.JLocusTrackObject;
import rene.zirkel.objects.LineObject;
import rene.zirkel.objects.PointObject;
import rene.zirkel.objects.PointonObject;
import rene.zirkel.objects.PrimitiveCircleObject;
import rene.zirkel.objects.PrimitiveLineObject;
import rene.zirkel.objects.QuadricObject;
import rene.zirkel.objects.RayObject;
import rene.zirkel.objects.SegmentObject;
import rene.zirkel.objects.TwoPointLineObject;
import rene.zirkel.objects.UserFunctionObject;
import rene.zirkel.objects.VectorObject;

public class MacroRunner extends ObjectConstructor implements Selector {

    String S[];
    int Param;
    Macro M;
    ArrayList OCs=new ArrayList();
    ArrayList PROMPTs=new ArrayList();
    ZirkelCanvas ZC;
    ConstructionObject Params[]; // array of parameters.
    boolean NewPoint[]; // are the parameters new points?
    boolean Fixed[];
    double LastX=0, LastY=0;
    boolean ShiftDown=false;
    static ConstructionObject previewPoint=null;
    static boolean keepLine=true;
    static boolean keepCircle=true;

    /**
     * Must be called, when this constructor is started.
     *
     * @param m
     *            The macro to be run
     */
    public void setMacro(final Macro m, final ZirkelCanvas zc) {
        S=m.getPrompts();
        Param=0;
        M=m;
        Params=new ConstructionObject[S.length];
        Fixed=new boolean[S.length];
        NewPoint=new boolean[S.length];
        for (int i=0; i<S.length; i++) {
            Fixed[i]=false;
        }
    }

    /**
     * At each mouse press, one parameter is chosen. The valid objects are
     * determined by the type of the parameter stored in the macro and retrieved
     * by getParams(). Once all parameters are entered, the macro is run.
     */
    @Override
    public void mousePressed(final MouseEvent e, final ZirkelCanvas zc) {

        if (!zc.Visual) {
            return;
        }

        ShiftDown=e.isShiftDown();

        if (!e.isAltDown()&&isMultipleFinalAccepted()) {
            final ConstructionObject o=zc.selectMultipleFinal(e.getX(), e.getY(), false);
            if (o!=null) {
                LaunchMultipleFinalMacro(e, zc);
                reset(zc);
                return;
            }
        }

        ConstructionObject o;
        final ConstructionObject p[]=M.getParams();
        if (p[Param] instanceof PointObject) {
            o=zc.selectCreatePoint(e.getX(), e.getY(), e.isAltDown());
        } else {
            o=zc.selectWithSelector(e.getX(), e.getY(), this);
        }
        setConstructionObject(o, zc);
    }

    @Override
    public void setConstructionObject(ConstructionObject obj, ZirkelCanvas zc) {
        // Il y a eu ambiguité et la méthode est forcément appelée par le
        // popupmenu de sélection :
        if ((JSelectPopup.isCallerObject()) && (obj instanceof PointonObject) && (M.getParams()[Param] instanceof PointObject)) {
            int x=JSelectPopup.getMouseX();
            int y=JSelectPopup.getMouseY();
            PointObject o=new PointObject(zc.getConstruction(), zc.x(x), zc.y(y), obj);
            o.setUseAlpha(true);
            zc.addObject(o);
            zc.validate();
            o.setDefaults();
            zc.repaint();
            o.edit(zc, false, false);
            obj=o;
        }
        if (obj!=null) {
            final int ip=Param;
            if (!setNextParameter(obj, zc, ShiftDown)) {
                return;
            }
            NewPoint[ip]=(obj instanceof PointObject&&zc.isNewPoint());
            if (Param>=S.length) {
                doMacro(zc);
                reset(zc);
            } else {
                getFixed(zc);
            }
        }
    }

    @Override
    public boolean isAdmissible(final ZirkelCanvas zc,
            final ConstructionObject o) {
        final ConstructionObject p[]=M.getParams();
        if (p[Param] instanceof PointObject) {
            return (o instanceof PointObject);
        } else if (p[Param] instanceof FixedAngleObject) {
            return (o instanceof FixedAngleObject);
        } else if (p[Param] instanceof RayObject) {
            return (o instanceof RayObject);
        } else if (p[Param] instanceof TwoPointLineObject) {
            return (o instanceof TwoPointLineObject);
        } else if (p[Param] instanceof SegmentObject) {
            return (p[Param] instanceof SegmentObject);
        } else if (p[Param] instanceof PrimitiveLineObject) {
            return (o instanceof PrimitiveLineObject);
        } else if (p[Param] instanceof PrimitiveCircleObject) {
            return (o instanceof PrimitiveCircleObject);
        } else if (p[Param] instanceof FunctionObject) {
            return (o instanceof FunctionObject);
        } else if (p[Param] instanceof UserFunctionObject) {
            return (o instanceof UserFunctionObject);
        } else if (p[Param] instanceof AngleObject) {
            return (o instanceof AngleObject);
        } else if (p[Param] instanceof QuadricObject) {
            return (o instanceof QuadricObject);
        } else if (p[Param] instanceof ExpressionObject) {
            return (o instanceof ExpressionObject||o instanceof AngleObject
                    ||o instanceof FixedAngleObject||o instanceof AreaObject);
        } else if (p[Param] instanceof AreaObject) {
            return (o instanceof AreaObject);
        } else {
            return false;
        }
    }

    private void checkIfKeepLine(final ZirkelCanvas zc) {
        final Construction c=zc.getConstruction();

        PointObject P1org=new PointObject(c, -3, 4);
        PointObject P2org=new PointObject(c, -1, 1);
        PointObject P3org=new PointObject(c, 1, -2);

        P1org.setSuperHidden(true);
        P2org.setSuperHidden(true);
        P3org.setSuperHidden(true);

        Params[Params.length-1]=P1org;
        c.add(P1org);
        runMacroPreview(zc, false);
        final PointObject p1=(PointObject) previewPoint;

        Params[Params.length-1]=P2org;
        c.add(P2org);
        runMacroPreview(zc, false);
        final PointObject p2=(PointObject) previewPoint;

        Params[Params.length-1]=P3org;
        c.add(P3org);
        runMacroPreview(zc, false);
        final PointObject p3=(PointObject) previewPoint;

        final double x1=p2.getX()-p1.getX();
        final double y1=p2.getY()-p1.getY();
        final double x2=p3.getX()-p1.getX();
        final double y2=p3.getY()-p1.getY();
        keepLine=(Math.abs(x1*y2-x2*y1)<1e-11);
    }

    private void checkIfKeepCircle(final ZirkelCanvas zc) {
        final Construction c=zc.getConstruction();

        PointObject P0org=new PointObject(c, 1, 1);
        PointObject P1org=new PointObject(c, 2, -1);
        PointObject P2org=new PointObject(c, 3, 2);
        PointObject P3org=new PointObject(c, 0, 3);
        PointObject P4org=new PointObject(c, -1, 2);

        P0org.setSuperHidden(true);
        P1org.setSuperHidden(true);
        P2org.setSuperHidden(true);
        P3org.setSuperHidden(true);
        P4org.setSuperHidden(true);


        Params[Params.length-1]=P0org;
        c.add(P0org);
        runMacroPreview(zc, false);
        final PointObject p0=(PointObject) previewPoint;

        Params[Params.length-1]=P1org;
        c.add(P1org);
        runMacroPreview(zc, false);
        final PointObject p1=(PointObject) previewPoint;

        Params[Params.length-1]=P2org;
        c.add(P2org);
        runMacroPreview(zc, false);
        final PointObject p2=(PointObject) previewPoint;

        Params[Params.length-1]=P3org;
        c.add(P3org);
        runMacroPreview(zc, false);
        final PointObject p3=(PointObject) previewPoint;

        Params[Params.length-1]=P4org;
        c.add(P4org);
        runMacroPreview(zc, false);
        final PointObject p4=(PointObject) previewPoint;


        final double x1=Math.sqrt((p1.getX()-p0.getX())
                *(p1.getX()-p0.getX())+(p1.getY()-p0.getY())
                *(p1.getY()-p0.getY()));
        final double x2=Math.sqrt((p2.getX()-p0.getX())
                *(p2.getX()-p0.getX())+(p2.getY()-p0.getY())
                *(p2.getY()-p0.getY()));
        final double x3=Math.sqrt((p3.getX()-p0.getX())
                *(p3.getX()-p0.getX())+(p3.getY()-p0.getY())
                *(p3.getY()-p0.getY()));
        final double x4=Math.sqrt((p4.getX()-p0.getX())
                *(p4.getX()-p0.getX())+(p4.getY()-p0.getY())
                *(p4.getY()-p0.getY()));

        boolean b=Math.abs(x1-x2)<1e-11;
        b=b&&Math.abs(x1-x3)<1e-11;
        b=b&&Math.abs(x1-x4)<1e-11;

        keepCircle=b;
    }

    public boolean isMultipleFinalAccepted() {
        final ConstructionObject p[]=M.getParams();
        if (Param<p.length-1) {
            return false;
        }
        if (!(p[p.length-1] instanceof PointObject)) {
            return false;
        }
        final Vector t=M.getTargets();
        if (t.size()!=1) {
            return false;
        }
        final ConstructionObject o=(ConstructionObject) t.get(0);
        if (!(o instanceof PointObject)) {
            return false;
        }
        return true;
    }

    public void LaunchLocus(final ZirkelCanvas zc, final Construction c,
            final ConstructionObject o) {
        final ConstructionObject po[]=new ConstructionObject[0];
        final int pn=0;

        // Créer un point qui représentera le dernier paramètre
        // de la macro. Ce point est ajouté à la construction :
        final PointObject pm=new PointObject(c, 0, 0);
        pm.setKeep(false);
        pm.setTarget(false);
        pm.setBound(o);
        pm.setSuperHidden(true);
        c.add(pm);
        o.setTranslation(pm);
        pm.validate();
        c.added(pm);

        // Lancer la macro et récupérer le point créé, qui sera rendu
        // super caché :
        Params[Params.length-1]=pm;
        doMacro(zc);
        final PointObject p=(PointObject) previewPoint;
        p.setKeep(false);
        p.setTarget(false);
        p.setSuperHidden(true);
        o.setTranslation(p);
        p.validate();
        c.added(p);

        // Créer le lieu du point p lorsque pm varie sur son ConstructionObject o :
        final JLocusTrackObject jl=new JLocusTrackObject(c, p, po, pn, o, pm);

        // Ajouter ce lieu à la construction :
        jl.setKeep(false);
        jl.setTarget(false);

        jl.setDefaults();
        if (!(o instanceof JLocusTrackObject)) {
            jl.applyDefaults(o);
        }
        c.addNoCheck(jl);
        o.setTranslation(jl);
        jl.validate();
        c.added(jl);
    }

    public void LaunchLocusPreview(final ZirkelCanvas zc, final Construction c,
            final ConstructionObject o) {
        final ConstructionObject po[]=new ConstructionObject[0];
        final int pn=0;

        // Créer un point qui représentera le dernier paramètre
        // de la macro. Ce point est ajouté à la construction :
        final PointObject pm=new PointObject(c, 0, 0);
        c.add(pm);
        pm.setSuperHidden(true);
        Params[Params.length-1]=pm;

        // Lancer la macro et récupérer le point créé, qui sera rendu
        // super caché :
        runMacroPreview(zc, false);
        final PointObject p=(PointObject) previewPoint;
        p.setSuperHidden(true);

        // Créer le lieu du point p lorsque pm varie sur son ConstructionObject o :
        final JLocusTrackObject jl=new JLocusTrackObject(c, p, po, pn, o, pm);

        // Ajouter ce lieu à la construction :
        jl.setKeep(false);
        jl.setTarget(false);
        jl.setSelectable(false);
        jl.setIndicated(true);
        c.addNoCheck(jl);
        o.setTranslation(jl);
        jl.validate();
        c.added(jl);
    }

    public boolean isLineObject(final ConstructionObject o) {
        boolean b=o instanceof PrimitiveLineObject;
        b=b||(o instanceof AreaObject);
        return b;
    }

    public boolean isCircleObject(final ConstructionObject o) {
        final boolean b=o instanceof PrimitiveCircleObject;
        return b;
    }

    public boolean isArcObject(final ConstructionObject c) {
        if (c instanceof PrimitiveCircleObject) {
            final PrimitiveCircleObject cc=(PrimitiveCircleObject) c;
            return cc.hasRange();
        }
        return false;
    }

    public void LaunchMultipleFinalMacro(final MouseEvent e,
            final ZirkelCanvas zc) {
        final ConstructionObject o=zc.selectMultipleFinal(e.getX(), e.getY(),
                false);
        final Construction c=zc.getConstruction();
        if (o!=null) {
            final int myX=e.getX();
            final int myY=e.getY();
            LastX=zc.x(myX);
            LastY=zc.y(myY);

            if ((!keepLine)&&(isLineObject(o))) {
                LaunchLocus(zc, c, o);
                return;
            }
            if ((!keepCircle)&&(isCircleObject(o))) {
                LaunchLocus(zc, c, o);
                return;
            }
            if (isArcObject(o)) {
                LaunchLocus(zc, c, o);
                return;
            }

            if (o instanceof TwoPointLineObject) {
                final TwoPointLineObject tplo=(TwoPointLineObject) o;
                final TwoPointLineObject oc=(TwoPointLineObject) tplo.copy(
                        LastX, LastY);
                Params[Params.length-1]=tplo.getP1();
                doMacro(zc);
                final PointObject p1=(PointObject) previewPoint;
                Params[Params.length-1]=tplo.getP2();
                doMacro(zc);
                final PointObject p2=(PointObject) previewPoint;
                oc.setP1P2(p1, p2);
                finaliseMacro(c, o, oc);
            } else if (o instanceof SegmentObject) {
                final SegmentObject tplo=(SegmentObject) o;
                tplo.setArrow(((SegmentObject) o).Arrow);
                Params[Params.length-1]=tplo.getP1();
                runMacroPreview(zc, false);
                final PointObject p1=(PointObject) previewPoint;
                final PointObject p0=tplo.getP1();
                Params[Params.length-1]=new PointObject(c, p0.getX()
                        +tplo.getDX(), p0.getY()+tplo.getDY());
                runMacroPreview(zc, false);
                final PointObject p2=(PointObject) previewPoint;
                final LineObject oc=new LineObject(c, p1, p2);
                finaliseMacroPreview(c, o, oc);
            } else if (o instanceof PrimitiveLineObject) {
                final PrimitiveLineObject tplo=(PrimitiveLineObject) o;
                Params[Params.length-1]=tplo.getP1();
                doMacro(zc);
                final PointObject p1=(PointObject) previewPoint;
                final PointObject p3=tplo.getP1();
                final String Pnm=p3.getName();
                final String Lnm=tplo.getName();
                final PointObject p4=new PointObject(c, p3.getX()
                        +tplo.getDX(), p3.getY()+tplo.getDY());
                p4.setFixed("x("+Pnm+")+x("+Lnm+")", "y("+Pnm
                        +")+y("+Lnm+")");
                p4.setSuperHidden(true);
                zc.addObject(p4);
                Params[Params.length-1]=p4;
                doMacro(zc);
                final PointObject p2=(PointObject) previewPoint;
                p1.setSuperHidden(true);
                p2.setSuperHidden(true);
                final LineObject oc=new LineObject(c, p1, p2);
                finaliseMacro(c, o, oc);
            } else if (o instanceof CircleObject) {
                final CircleObject tplo=(CircleObject) o;
                final CircleObject oc=(CircleObject) tplo.copy(LastX, LastY);
                Params[Params.length-1]=tplo.getP1();
                doMacro(zc);
                final PointObject p1=(PointObject) previewPoint;
                Params[Params.length-1]=tplo.getP2();
                doMacro(zc);
                final PointObject p2=(PointObject) previewPoint;
                p2.setSuperHidden(true);
                oc.setP1P2(p1, p2);
                finaliseMacro(c, o, oc);
            } else if (o instanceof PrimitiveCircleObject) {
                final PrimitiveCircleObject tplo=(PrimitiveCircleObject) o;
                Params[Params.length-1]=tplo.getP1();
                doMacro(zc);
                final PointObject p1=(PointObject) previewPoint;
                final PointObject p4=new PointObject(c, 0, 0);
                p4.setBound(tplo);
                p4.setSuperHidden(true);
                zc.addObject(p4);
                Params[Params.length-1]=p4;
                doMacro(zc);
                final PointObject p2=(PointObject) previewPoint;
                p2.setSuperHidden(true);
                final CircleObject oc=new CircleObject(c, p1, p2);
                finaliseMacro(c, o, oc);
            } else if (o instanceof AreaObject) {
                final AreaObject ao=(AreaObject) o;
                final AreaObject oc=(AreaObject) ao.copy(LastX, LastY);
                oc.V.removeAllElements();
                final Enumeration en=ao.V.elements();
                while (en.hasMoreElements()) {
                    final PointObject pt=(PointObject) en.nextElement();
                    Params[Param]=pt;
                    doMacro(zc);
                    oc.V.add((PointObject) previewPoint);
                }
                finaliseMacro(c, o, oc);
            } else {
                LaunchLocus(zc, c, o);
            }
        }
    }

    public void finaliseMacro(final Construction c, final ConstructionObject o,
            final ConstructionObject oc) {
        oc.setKeep(false); // necessary for jobs
        oc.setTarget(false); // necessary for descriptive constructions
        oc.setDefaults();
        c.addNoCheck(oc);
        o.setTranslation(oc);
        oc.validate();
        c.added(oc);
    }

    public void LaunchMultipleFinalMacroPreview(final MouseEvent e,
            final ZirkelCanvas zc) {
        final ConstructionObject o=zc.selectMultipleFinal(e.getX(), e.getY(),
                false);
        final Construction c=zc.getConstruction();
        if (o!=null) {

            final int myX=e.getX();
            final int myY=e.getY();
            zc.prepareForPreview(e);
            checkIfKeepLine(zc);
            checkIfKeepCircle(zc);
            LastX=zc.x(myX);
            LastY=zc.y(myY);


            if ((!keepLine)&&(isLineObject(o))) {
                LaunchLocusPreview(zc, c, o);
                return;
            }
            if ((!keepCircle)&&(isCircleObject(o))) {
                LaunchLocusPreview(zc, c, o);
                return;
            }
            if (isArcObject(o)) {
                LaunchLocusPreview(zc, c, o);
                return;
            }

//                        if (true) {
//				LaunchLocusPreview(zc, c, o);
//				return;
//			}



            if (o instanceof TwoPointLineObject) {
                final TwoPointLineObject tplo=(TwoPointLineObject) o;
                final TwoPointLineObject oc=(TwoPointLineObject) tplo.copy(
                        LastX, LastY);
                Params[Params.length-1]=tplo.getP1();
                runMacroPreview(zc, true);
                final PointObject p1=(PointObject) previewPoint;
                Params[Params.length-1]=tplo.getP2();
                runMacroPreview(zc, true);
                final PointObject p2=(PointObject) previewPoint;
                oc.setP1P2(p1, p2);
                finaliseMacroPreview(c, o, oc);
            } else if (o instanceof SegmentObject) {
                final SegmentObject tplo=(SegmentObject) o;
                tplo.setArrow(((SegmentObject) o).Arrow);
                Params[Params.length-1]=tplo.getP1();
                runMacroPreview(zc, false);
                final PointObject p1=(PointObject) previewPoint;
                final PointObject p0=tplo.getP1();
                Params[Params.length-1]=new PointObject(c, p0.getX()
                        +tplo.getDX(), p0.getY()+tplo.getDY());
                runMacroPreview(zc, false);
                final PointObject p2=(PointObject) previewPoint;
                final LineObject oc=new LineObject(c, p1, p2);
                finaliseMacroPreview(c, o, oc);
            } else if (o instanceof PrimitiveLineObject) {
                final PrimitiveLineObject tplo=(PrimitiveLineObject) o;
                Params[Params.length-1]=tplo.getP1();
                runMacroPreview(zc, false);
                final PointObject p1=(PointObject) previewPoint;
                final PointObject p0=tplo.getP1();
                Params[Params.length-1]=new PointObject(c, p0.getX()
                        +tplo.getDX(), p0.getY()+tplo.getDY());
                runMacroPreview(zc, false);
                final PointObject p2=(PointObject) previewPoint;
                final LineObject oc=new LineObject(c, p1, p2);
                finaliseMacroPreview(c, o, oc);
            } else if (o instanceof CircleObject) {
                final CircleObject tplo=(CircleObject) o;
                final CircleObject oc=(CircleObject) tplo.copy(LastX, LastY);
                Params[Params.length-1]=tplo.getP1();
                runMacroPreview(zc, true);
                final PointObject p1=(PointObject) previewPoint;
                Params[Params.length-1]=tplo.getP2();
                runMacroPreview(zc, false);
                final PointObject p2=(PointObject) previewPoint;
                oc.setP1P2(p1, p2);
                finaliseMacroPreview(c, o, oc);
            } else if (o instanceof PrimitiveCircleObject) {
                final PrimitiveCircleObject tplo=(PrimitiveCircleObject) o;
                Params[Params.length-1]=tplo.getP1();
                runMacroPreview(zc, true);
                final PointObject p1=(PointObject) previewPoint;
                final PointObject p4=new PointObject(c, tplo.getP1().getX()
                        +tplo.getR(), tplo.getP1().getY());
                p4.setSuperHidden(true);
                c.add(p4);
                Params[Params.length-1]=p4;
                runMacroPreview(zc, false);
                final PointObject p2=(PointObject) previewPoint;
                final CircleObject oc=new CircleObject(c, p1, p2);
                finaliseMacroPreview(c, o, oc);
            } else if (o instanceof AreaObject) {
                final AreaObject ao=(AreaObject) o;
                final AreaObject oc=(AreaObject) ao.copy(LastX, LastY);
                oc.V.removeAllElements();
                final Enumeration en=ao.V.elements();
                while (en.hasMoreElements()) {
                    final PointObject pt=(PointObject) en.nextElement();
                    Params[Params.length-1]=pt;
                    runMacroPreview(zc, true);
                    oc.V.add((PointObject) previewPoint);
                }
                finaliseMacroPreview(c, o, oc);
            } else {
                LaunchLocusPreview(zc, c, o);
            }
        }
    }

    public void finaliseMacroPreview(final Construction c,
            final ConstructionObject o, final ConstructionObject oc) {
        oc.setKeep(false); // necessary for jobs
        oc.setTarget(false); // necessary for descriptive constructions
        oc.setSelectable(false);
        oc.setIndicated(true);
        c.addNoCheck(oc);
        o.setTranslation(oc);
        oc.validate();
        c.added(oc);
        if (o instanceof SegmentObject) {
            c.Vectors=((SegmentObject) o).Arrow;
        }
    }
    private boolean lastIsMultipleObject=false;

    @Override
    public void mouseMoved(final MouseEvent e, final ZirkelCanvas zc,
            final boolean simple) {
        final ConstructionObject p[]=M.getParams();
        zc.clearIndicated();
        zc.clearPreview();
        zc.repaint();
        if (!e.isAltDown()&&isMultipleFinalAccepted()) {
            final ConstructionObject o=zc.selectMultipleFinal(e.getX(), e.getY(), false);
            if (o!=null) {

                final int myX=e.getX();
                final int myY=e.getY();
                zc.prepareForPreview(e);
                LastX=zc.x(myX);
                LastY=zc.y(myY);

                LaunchMultipleFinalMacroPreview(e, zc);
                zc.indicateMultipleFinal(e.getX(), e.getY());

                return;
            }
        }

        if (!(p[Param] instanceof PointObject)&&Param==p.length-1) {
            // zc.clearPreview();
            // zc.repaint();
            final ConstructionObject o=zc.selectWithSelector(e.getX(), e.getY(), this, false);
            if (o!=null) {

                final int myX=e.getX();
                final int myY=e.getY();
                zc.prepareForPreview(e);
                Params[Param]=o;
                LastX=zc.x(myX);
                LastY=zc.y(myY);
                runMacroPreview(zc, true);

                zc.indicateWithSelector(myX, myY, this);
                return;
            }
        }

        PointObject lastp=null;

        if (p[Param] instanceof PointObject) {
            lastp=zc.indicateCreatePoint(e.getX(), e.getY(), true);
        } else {
            zc.indicateWithSelector(e.getX(), e.getY(), this);
        }

        if (!simple&&waitForLastPoint()) {
            zc.prepareForPreview(e);
            if (lastp==null) {
                finishConstruction(e, zc);
            } else {
                int x=(int) zc.col(lastp.getX());
                int y=(int) zc.row(lastp.getY());
                finishConstruction(x, y, e, zc);
            }
        }
    }

    @Override
    public boolean waitForLastPoint() {
        if (M.countPrompts()>0) {
            return false;
        }
        final ConstructionObject p[]=M.getParams();
        return (p[Param] instanceof PointObject)&&Param==p.length-1;
    }

    @Override
    public void finishConstruction(final MouseEvent e, final ZirkelCanvas zc) {
        final ConstructionObject p[]=M.getParams();
        ConstructionObject o;
        if (p[Param] instanceof PointObject) {
            o=zc.selectCreatePoint(e.getX(), e.getY(), e.isAltDown());
        } else {
            return;
        }
        NewPoint[Param]=true;
        Params[Param]=o;
        runMacroPreview(zc, true);
    }

    public void finishConstruction(int x, int y, final MouseEvent e, final ZirkelCanvas zc) {
        final ConstructionObject p[]=M.getParams();
        ConstructionObject o;
        if (p[Param] instanceof PointObject) {
            o=zc.selectCreatePoint(x, y, e.isAltDown());
        } else {
            return;
        }
        NewPoint[Param]=true;
        Params[Param]=o;
        runMacroPreview(zc, true);
    }

    @Override
    public void reset(final ZirkelCanvas zc) {
        if (zc.Visual) {
            super.reset(zc);
            Param=0;
            if (M!=null&&M.hasFixed()) {
                getFixed(zc);
            }
            showStatus(zc);
        } else if (M!=null) // show the input pattern
        {
            final StringBuffer b=new StringBuffer();
            b.append('=');
            final String name=M.getName();
            if (name.indexOf("(")>0) {
                b.append("\""+M.getName()+"\"");
            } else {
                b.append(M.getName());
            }
            b.append('(');
            for (int i=0; i<M.getParams().length-1; i++) {
                b.append(',');
            }
            b.append(')');
            zc.setPrompt(b.toString());
        }
    }

    public void getFixed(final ZirkelCanvas zc) {
        if (M==null||!zc.Visual) {
            return;
        }
        final boolean start=(Param==0);



        while ((M.isFixed(Param)||M.getPrompts()[Param].startsWith("="))
                &&Param<(start?S.length-1:S.length)) {
            String name;
            if (M.isFixed(Param)) {
                name=M.getLast(Param);
            } else {
                name=M.getPrompts()[Param].substring(1);
            }
            if (name.equals("")) {
                M.setFixed(Param, false);
                break;
            }
            final ConstructionObject o=zc.getConstruction().find(name);
            if (o==null) {
                M.setFixed(Param, false);
                break;
            }
            if (!setNextParameter(o, zc, false)) {
                return;
            }
            if (Param>=S.length) {
                doMacro(zc);
                reset(zc);
                break;
            }
        }
        showStatus(zc);
    }

    public void returnPressed(final ZirkelCanvas zc) {
        if (M==null||!zc.Visual) {
            return;
        }
        final String name=M.getLast(Param);
        if (name.equals("")) {
            return;
        }
        final ConstructionObject o=zc.getConstruction().find(name);
        if (!setNextParameter(o, zc, false)) {
            return;
        }
        if (Param>=S.length) {
            doMacro(zc);
            reset(zc);
        } else {
            getFixed(zc);
        }
    }

    public boolean setNextParameter(final ConstructionObject o,
            final ZirkelCanvas zc, final boolean fix) {
        if (!isAdmissible(zc, o)) {
            return false;
        }
        Params[Param]=o;
        o.setSelected(true);
        if (fix) {
            Fixed[Param]=true;
        }
        zc.getConstruction().addParameter(o);
        zc.repaint();
        Param++;
        return true;
    }

    public void doMacro(final ZirkelCanvas zc) {
        final String value[]=new String[0];
        runMacro(zc, value);
    }
    static DepList DL=new DepList();

    @Override
    public void showStatus(final ZirkelCanvas zc) {
        if (M!=null) {
            final ConstructionObject p[]=M.getParams();
            String type="???";
            // Determine the expected type and display in status line
	    if (p[Param] instanceof FunctionObject) {
                type=Global.name("name.Function");
            } else if (p[Param] instanceof PointObject) {
                type=Global.name("name.Point");
            } else if (p[Param] instanceof FixedAngleObject) {
                type=Global.name("name.FixedAngle");
            } else if(p[Param] instanceof VectorObject) {
		type=Global.name("name.Vector");
	    } else if (p[Param] instanceof SegmentObject) {
                type=Global.name("name.Segment");
            } else if (p[Param] instanceof LineObject) {
                type=Global.name("name.TwoPointLine");
            } else if (p[Param] instanceof RayObject) {
                type=Global.name("name.Ray");
            } else if (p[Param] instanceof PrimitiveLineObject) {
                type=Global.name("name.Line");
            } else if (p[Param] instanceof PrimitiveCircleObject) {
                type=Global.name("name.Circle");
            } else if (p[Param] instanceof ExpressionObject) {
                type=Global.name("name.Expression");
            } else if (p[Param] instanceof AreaObject) {
                type=Global.name("name.Polygon");
            } else if (p[Param] instanceof AngleObject) {
                type=Global.name("name.Angle");
            } else if (p[Param] instanceof QuadricObject) {
                type=Global.name("name.Quadric");
            }
            final String s=M.getLast(Param);
            String prompt;
            int inc=zc.isDP()?0:1;
            if (s.equals("")) {
                prompt=ConstructionObject.text4(Global.name("message.runmacro"), M.getName(), ""
                        +(Param+inc), type, S[Param]);
            } else {
                prompt=ConstructionObject.text4(Global.name("message.runmacro"), M.getName(), ""
                        +(Param+inc), type, S[Param])
                        +" ";
            }
            zc.showStatus(prompt);
        }
    }

    //Only for JSFunctions/ExecuteMacro
    public static ArrayList<String> TargetsNameList = new ArrayList<String>();

    /**
     * Run a macro. The macro parameters have already been determined. This is a
     * rather complicated function.
     */
    public void runMacro(final ZirkelCanvas zc, final Construction c,
            final String value[]) {
        TargetsNameList.clear();;
        OCs.clear();
        PROMPTs.clear();

        M.setTranslation(c);
        final ConstructionObject LastBefore=c.lastButN(0);
        final int N=Params.length;
        // First clear all parameter flags. This makes it possible to
        // check for proper translation of secondary parameters later.
        // Secondary parameters without a translation will be
        // constructed.
        Enumeration e=M.elements();
        while (e.hasMoreElements()) {
            final ConstructionObject o=(ConstructionObject) e.nextElement();
            o.clearParameter();
            o.setTranslation(null);
        }
        M.clearTranslations();
        c.clearTranslators();
        final ConstructionObject p[]=M.getParams();
        // For all macro parameters, determine the translation to the
        // real construction, and do the same for the secondary
        // parameters, which belong to the parameter. The secondary
        // parameters are stored in the macro at its definition, as
        // the primary ones. Also the parameters in the macros are marked
        // as such to make sure and prevent construction.
        M.initLast(); // Macros remember the parameters for next call
        for (int i=0; i<N; i++) {
            M.setLast(Params[i].getName(), i);
            p[i].setTranslation(Params[i]);
            p[i].setMainParameter(true);
            if (NewPoint[i]&&p[i].isHidden()) {
                Params[i].setHidden(true);
            }
            if (Params[i] instanceof PointObject&&p[i] instanceof PointObject
                    &&NewPoint[i]) {
                final PointObject pa=(PointObject) Params[i], pp=(PointObject) p[i];
                pa.setIncrement(pp.getIncrement());
                if (pp.getBound()!=null) {
                    pa.setBound(pp.getBound());
                    pa.setInside(pp.isInside());
                    pa.translate();
                }
            }
            // translate parameters that depend on themself only
            if (Params[i] instanceof PointObject&&p[i] instanceof PointObject
                    &&((PointObject) p[i]).dependsOnItselfOnly()) {
                final PointObject P=(PointObject) Params[i];
                // Do not transfer self reference to objects that depend on
                // something!
                // This might crash the construction.
                if (!P.depending().hasMoreElements()) {
                    P.setConstruction(M);
                    P.setFixed(((PointObject) p[i]).getEX(),
                            ((PointObject) p[i]).getEY());
                    P.translateConditionals();
                    P.translate();
                    P.setConstruction(c);
                }
            }
            if (p[i].isMainParameter()) { // System.out.println("Main Parameter "+p[i].getName());
                e=p[i].secondaryParams();
                // Copy the list of secondary parameters in the macro,
                // which depend from p[i] to DL.
                DL.reset();
                while (e.hasMoreElements()) {
                    final ConstructionObject o=(ConstructionObject) e.nextElement();
                    // System.out.println("Secondary Parameter "+o.getName()+" of "+p[i].getName());
                    DL.add(o);
                    o.setParameter(true);
                }
                e=DL.elements();
                // Get a list of actual secondary params in the
                // construction. Then translate the scecondary params
                // in the macro definition to the true construction objects.
                final Enumeration ep=Params[i].secondaryParams();
                while (ep.hasMoreElements()&&e.hasMoreElements()) {
                    final ConstructionObject o=(ConstructionObject) e.nextElement();
                    final ConstructionObject op=(ConstructionObject) ep.nextElement();
                    if (o.getTranslation()!=op&&o.getTranslation()!=null) {
                        zc.warning(Global.name("macro.usage"));
                        return;
                    }
                    o.setTranslation(op);
                }
            }
        }
        // Now we generate the objects.
        e=M.elements();
        while (e.hasMoreElements()) {
            final ConstructionObject o=(ConstructionObject) e.nextElement();
            // System.out.println(o.getName()+" "+o.isParameter());
            if (!o.isParameter()) { // else do not construct!
                // Copy the object and add to construction. Then
                // translate the dependencies properly
                final ConstructionObject oc=(ConstructionObject) o.copy(
                        LastX, LastY);
                if (oc!=null) {
                    previewPoint=oc;
                    oc.setKeep(false); // necessary for jobs
                    oc.setTarget(false); // necessary for descriptive constructions
                    c.addNoCheck(oc);
                    o.setTranslation(oc);
                    oc.validate();
                    c.added(oc);
		    zc.update_distant(oc, 1);
                    // For the target objects, use default values instead
                    // of values stored in the macro (point style etc.)
                    if (o.isTarget()) {
                        if (!oc.isDPObject()) {
                            oc.setDPMode(M.createDPObjects);
                        }
                        oc.setTargetDefaults();
                        TargetsNameList.add(oc.getName());
                    }
                    // else if (o.isHidden()) {
                    // if (o.isSuperHidden()) oc.setSuperHidden(true);
                    // else oc.setHidden(true);
                    // }

                    if ((oc instanceof FixedCircleObject
                            ||oc instanceof FixedAngleObject||oc instanceof ExpressionObject)
                            &&M.promptFor(o.getName())) {
                        c.updateCircleDep();
                        c.dovalidate();
                        zc.repaint();
                        final int index=M.getPromptFor(o.getName());
                        if (index>=value.length||value[index].equals("")) {
                            OCs.add(oc);
                            PROMPTs.add(M.getPromptName(o.getName()));
                        }
                    }
                }

            }
        }
        // Now fix the objects, which depend on later objects
        e=M.elements();
        while (e.hasMoreElements()) {
            final ConstructionObject o=(ConstructionObject) e.nextElement();
            if (!o.isParameter()) {
                o.laterTranslate(M);
            }
        }
        c.updateCircleDep();
        c.runTranslators(M);
        c.dovalidate();
        zc.repaint();
        int fixed=0;
        for (final boolean element : Fixed) {
            if (element) {
                fixed++;
            }
        }
        if (fixed>0&&fixed<Fixed.length&&!M.hasFixed()) {
            String name=M.getName()+" -";
            for (int i=0; i<Fixed.length; i++) {
                if (Fixed[i]) {
                    name=name+" "+M.LastParams[i];
                }
            }
            M=zc.copyMacro(M, name, Fixed);
            for (int i=0; i<Fixed.length; i++) {
                Fixed[i]=false;
            }
            reset(zc);
        }
        if (LastBefore!=null&&M.hideDuplicates()) {
            zc.hideDuplicates(LastBefore);
        }
        ZC=zc;
        final Thread thread=new Thread(new Runnable() {

            @Override
            public void run() {
                for (int i=0; i<OCs.size(); i++) {
                    final String myName=(String) PROMPTs.get(i);
                    final ConstructionObject myCO=(ConstructionObject) OCs.get(i);
                    new eric.JMacroPrompt(ZC.getFrame(), ZC, myName, myCO);
                }
            }
        });
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.start();
    }

    public void runMacro(final ZirkelCanvas zc, final String value[]) {
        runMacro(zc, zc.getConstruction(), value);
    }

    public void runMacroPreview(final ZirkelCanvas zc, final boolean visible) {
        final Construction c=zc.getConstruction();
        M.setTranslation(c);
        final int N=Params.length;
        // First clear all parameter flags. This makes it possible to
        // check for proper translation of secondary parameters later.
        // Secondary parameters without a translation will be
        // constructed.
        Enumeration e=M.elements();
        while (e.hasMoreElements()) {
            final ConstructionObject o=(ConstructionObject) e.nextElement();
            o.clearParameter();
            o.setTranslation(null);
        }
        M.clearTranslations();
        c.clearTranslators();
        final ConstructionObject p[]=M.getParams();
        // For all macro parameters, determine the translation to the
        // real construction, and do the same for the secondary
        // parameters, which belong to the parameter. The secondary
        // parameters are stored in the macro at its definition, as
        // the primary ones. Also the parameters in the macros are marked
        // as such to make sure and prevent construction.
        for (int i=0; i<N; i++) {
            if (Params[i]==null) {
                return;
            }
            M.setLast(Params[i].getName(), i);
            p[i].setTranslation(Params[i]);
            p[i].setMainParameter(true);
            if (NewPoint[i]&&p[i].isHidden()) {
                Params[i].setHidden(true);
            }
            if (p[i].isMainParameter()) {
                e=p[i].secondaryParams();
                // Copy the list of secondary parameters in the macro,
                // which depend from p[i] to DL.
                DL.reset();
                while (e.hasMoreElements()) {
                    final ConstructionObject o=(ConstructionObject) e.nextElement();
                    DL.add(o);
                    o.setParameter(true);
                }
                e=DL.elements();
                // Get a list of actual secondary params in the
                // construction. Then translate the scecondary params
                // in the macro definition to the true construction objects.
                final Enumeration ep=Params[i].secondaryParams();
                while (ep.hasMoreElements()&&e.hasMoreElements()) {
                    final ConstructionObject o=(ConstructionObject) e.nextElement();
                    final ConstructionObject op=(ConstructionObject) ep.nextElement();
                    if (o.getTranslation()!=op&&o.getTranslation()!=null) { // zc.warning(Global.name("macro.usage"));
                        return;
                    }
                    if (o!=null) {
                        o.setTranslation(op);
                    }
                }
            }
        }
        // Now we generate the objects.
        e=M.elements();
        while (e.hasMoreElements()) {
            final ConstructionObject o=(ConstructionObject) e.nextElement();
            if (!o.isParameter()) // else do not construct!
            { // Copy the object and add to construction. Then
                // translate the dependencies properly
                final ConstructionObject oc=(ConstructionObject) o.copy(
                        LastX, LastY);
                if (oc!=null) {
                    previewPoint=oc;
                    oc.setKeep(false); // necessary for jobs
                    oc.setTarget(false); // necessary for descriptive constructions
                    oc.setSelectable(false);
                    if (visible) {
                        oc.setIndicated(true);
                    } else {
                        oc.setSuperHidden(true);
                    }
                    c.addNoCheck(oc);
                    o.setTranslation(oc);
                    oc.validate();
                    c.added(oc);
                    // For the target objects, use default values instead
                    // of values stored in the macro (point style etc.)
                    if (o.isTarget()) {
                        if (!oc.isDPObject()) {
                            oc.setDPMode(M.createDPObjects);
                        }
                        oc.setTargetDefaults();
                    }
                    if (o.isHidden()) {
                        oc.setHidden(true);
                        // For black objects, use the default color.
                    }
//                    if (oc.getColorIndex()==0) {
//                        oc.setColor(c.DefaultColor);
//                    }
                }

            }
        }
        // All objects have the chance to translate anything
        // (used by start and end points of arcs)
        e=M.elements();
        while (e.hasMoreElements()) {
            final ConstructionObject o=(ConstructionObject) e.nextElement();
            if (!o.isParameter()) {
                o.laterTranslate(M);
            }
        }
        c.updateCircleDep();
        // Run the translations of forward references of type @...
        c.runTranslators(M);
        c.dovalidate();
        // c.computeHeavyObjects(zc);
        zc.repaint();
    }

    /**
     * Run a macro in javascript mode. This needs a previous setMacro() call!
     */
    public void run(final ZirkelCanvas zc, final Construction c) throws ConstructionException {
        final ConstructionObject p[]=M.getParams();




        final String value[]=new String[M.countPrompts()];
        for (int i=0; i<M.countPrompts(); i++) {
            value[i]="";
        }
        for (int i=0; i<p.length; i++) {
            final ConstructionObject o=M.getParams()[i];
            if (o==null) {
                throw new ConstructionException(Global.name("exception.notfound"));
            }
            Params[i]=o;
        }
        runMacro(zc, c, value);
//		final StringTokenizer t = new StringTokenizer(name, ",");
//		final Enumeration e = M.getTargets().elements();
//		while (e.hasMoreElements() && t.hasMoreTokens()) {
//			final ConstructionObject o = (ConstructionObject) e.nextElement();
//			o.getTranslation().setName(t.nextToken().trim());
//		}
        zc.repaint();
    }

    /**
     * Run a macro in nonvisual mode. This needs a previous setMacro() call!
     */
    public void run(final ZirkelCanvas zc, final Construction c,
            final String name, final String params[], final int nparams)
            throws ConstructionException {
        final ConstructionObject p[]=M.getParams();
        if (nparams!=p.length+M.countPrompts()) {
            throw new ConstructionException(Global.name("exception.nparams"));
        }
        final String value[]=new String[M.countPrompts()];
        for (int i=0; i<M.countPrompts(); i++) {
            value[i]=params[p.length+i];
        }
        for (int i=0; i<p.length; i++) {
            final ConstructionObject o=c.find(params[i]);
            if (o==null) {
                throw new ConstructionException(Global.name("exception.notfound"));
            }
            if (p[Param] instanceof PointObject) {
                if (!(o instanceof PointObject)) {
                    throw new ConstructionException(Global.name("exception.type"));
                } else if (p[Param] instanceof SegmentObject) {
                    if (!(o instanceof SegmentObject)) {
                        throw new ConstructionException(Global.name("exception.type"));
                    } else if (p[Param] instanceof LineObject) {
                        if (!(o instanceof LineObject)) {
                            throw new ConstructionException(Global.name("exception.type"));
                        } else if (p[Param] instanceof RayObject) {
                            if (!(o instanceof RayObject)) {
                                throw new ConstructionException(Global.name("exception.type"));
                            } else if (p[Param] instanceof PrimitiveLineObject) {
                                if (!(o instanceof PrimitiveLineObject)) {
                                    throw new ConstructionException(Global.name("exception.type"));
                                } else if (p[Param] instanceof PrimitiveCircleObject) {
                                    if (!(o instanceof PrimitiveCircleObject)) {
                                        throw new ConstructionException(Global.name("exception.type"));
                                    } else {
                                        throw new ConstructionException(Global.name("exception.type"));
                                    }
                                }
                            }
                        }
                    }
                }
            }
            Params[i]=o;
        }
        runMacro(zc, c, value);
        final StringTokenizer t=new StringTokenizer(name, ",");
        final Enumeration e=M.getTargets().elements();
        while (e.hasMoreElements()&&t.hasMoreTokens()) {
            final ConstructionObject o=(ConstructionObject) e.nextElement();
            o.getTranslation().setName(t.nextToken().trim());
        }
        zc.repaint();
    }
}
