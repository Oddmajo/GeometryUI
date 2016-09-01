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
package rene.zirkel.construction;

// file: ZirkelCanvas.java
import eric.Progress_Bar;
import java.awt.Color;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Vector;
import rene.gui.Global;
import rene.util.MyVector;
import rene.util.sort.Sorter;
import rene.util.xml.XmlTag;
import rene.util.xml.XmlTree;
import rene.util.xml.XmlWriter;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.ZirkelFrame;
import rene.zirkel.constructors.AngleConstructor;
import rene.zirkel.constructors.AreaConstructor;
import rene.zirkel.constructors.BoundedPointConstructor;
import rene.zirkel.constructors.Circle3Constructor;
import rene.zirkel.constructors.CircleConstructor;
import rene.zirkel.constructors.EquationXYConstructor;
import rene.zirkel.constructors.ExpressionConstructor;
import rene.zirkel.constructors.FunctionConstructor;
import rene.zirkel.constructors.ImageConstructor;
import rene.zirkel.constructors.IntersectionConstructor;
import rene.zirkel.constructors.LineConstructor;
import rene.zirkel.constructors.MidpointConstructor;
import rene.zirkel.constructors.ObjectConstructor;
import rene.zirkel.constructors.ParallelConstructor;
import rene.zirkel.constructors.PlumbConstructor;
import rene.zirkel.constructors.PointConstructor;
import rene.zirkel.constructors.QuadricConstructor;
import rene.zirkel.constructors.RayConstructor;
import rene.zirkel.constructors.SegmentConstructor;
import rene.zirkel.constructors.TextConstructor;
import rene.zirkel.constructors.VectorConstructor;
import rene.zirkel.expression.Translator;
import rene.zirkel.listener.AddEventListener;
import rene.zirkel.objects.AxisObject;
import rene.zirkel.objects.ConstructionObject;
import rene.zirkel.objects.EquationXYObject;
import rene.zirkel.objects.ExpressionObject;
import rene.zirkel.objects.FunctionObject;
import rene.zirkel.objects.IntersectionObject;
import rene.zirkel.objects.JLocusTrackObject;
import rene.zirkel.objects.PointObject;
import rene.zirkel.objects.PrimitiveCircleObject;
import rene.zirkel.objects.TrackObject;
import rene.zirkel.tools.ObjectTracker;

/**
 * Construction holds all construction details, like objects, default values,
 * viewing window etc., but not macros, which are constructions themselves, and
 * stored in ZirkelCanvas. The class has methods to read and write
 * constructions, and to draw constructions to MyGraphics.
 *
 * @author Rene Grothmann
 */
public class Construction {

    public final static int MODE_NORMAL=0,MODE_3D=1,MODE_DP=2;
    private int mode=0;
    public Vector magnet=new Vector();
    public Vector V; // The vector of objects (ConstructionObject's)
    public Vector Parameters; // The vector of parameter object names (String's)
    public Vector Targets; // The vector of target object names (String's)
    public String Comment="", JobComment="";
    public Vector PromptFor=new Vector();
    public Vector Prompts;
    private double X=0, Y=0, W=8, H=8; // H is set by ZirkelCanvas
    public boolean Partial=Global.getParameter("options.partial", false),
            Restricted=Global.getParameter("options.restricted", true),
            PartialLines=Global.getParameter("options.plines", false),
            Vectors=Global.getParameter("options.arrow", false),
            ShowNames=Global.getParameter("options.shownames", false),
            ShowValues=Global.getParameter("options.showvalues", false),
            LongNames=Global.getParameter("options.longnames", false),
            LargeFont=Global.getParameter("options.largefont", false),
            BoldFont=Global.getParameter("options.boldfont", false),
            Hidden=false, Obtuse=Global.getParameter("options.obtuse",
            false),
            Solid=Global.getParameter("options.solid", false);
    public boolean Animate=false, Paint=false; // for loading tracks
    public boolean ShowAll=false; // for macros
    public boolean SuperHide=false; // for macros
    public int DefaultColor=Global.getParameter("options.color", 0),
            DefaultType=Global.getParameter("options.type", 0),
            DefaultColorType=Global.getParameter("options.colortype", 0);
    public boolean Changed=false;
    int Count=0; // Unique number for each object
    public Construction TranslateInto;
    public boolean BlockSimulation=false; // To block two simulations at once!
    public boolean DontAlternateIntersections=false; // To block alternating
    // invalid intersections
    ObjectConstructor ObjectConstructors[]= // Constructors for reading a
            // construction from file
            {new PointConstructor(), new LineConstructor(), new SegmentConstructor(), new VectorConstructor(),
        new RayConstructor(), new CircleConstructor(),
        new IntersectionConstructor(), new ParallelConstructor(),
        new PlumbConstructor(), new Circle3Constructor(),
        new MidpointConstructor(), new AngleConstructor(),
        new BoundedPointConstructor(), new ExpressionConstructor(),
        new AreaConstructor(), new TextConstructor(),
        new QuadricConstructor(), new ImageConstructor(),
        new ObjectTracker(), new FunctionConstructor(),
        new EquationXYConstructor()};
    public AxisObject xAxis=null, yAxis=null;
    private boolean isEuclidian=false;
    private PrimitiveCircleObject HZ=null;
    private double one=1.0;

    public Construction() {
        clear();
        Changed=false;
    }

    public double getOne(){
        return one;
    }
    public void setOne(double un){
        one=un;
    }

    public void setMode(int i) {
        mode=i;
    }

    public int getMode(){
        return mode;
    }

    public boolean is3D() {
        return mode==MODE_3D;
    }

    public boolean isDP() {
        return mode==MODE_DP;
    }

    public boolean isEuclidian() {
        return isEuclidian;
    }

    public void setEuclidian(boolean b) {
        isEuclidian=b;
        PrimitiveCircleObject Hz=(PrimitiveCircleObject) find("Hz");
        PointObject CH=(PointObject) find("CH");
        if ((Hz!=null)&&(CH!=null)) {
            Hz.setHidden(!b);
            CH.setHidden(!b);
        }
    }

    public void set3D(boolean b){
        if (b) {
            mode=MODE_3D;
        }
    }

    public void setDP(boolean b){
        if (b) {
            mode=MODE_DP;
        }
    }

    public void deleteAxisObjects() {
        if ((xAxis!=null)&&(yAxis!=null)) {
            if ((!haveChildren(xAxis))&&(!haveChildren(yAxis))) {
                clearConstructables();
                xAxis.setFlag(true);
                determineChildren();
                delete(false);
                clearConstructables();
                yAxis.setFlag(true);
                determineChildren();
                delete(false);
                xAxis=null;
                yAxis=null;
            }
        }
    }

    public void createAxisObjects() {
        if ((xAxis==null)&&(yAxis==null)) {
            xAxis=new AxisObject(this, true);
            yAxis=new AxisObject(this, false);

            add(yAxis);
            add(xAxis);

            reorderAxisObjects();
            updateCircleDep();
        }
    }
    private AddEventListener AEL=null;

    public void addAddEventListener(final AddEventListener ael) {
        AEL=ael;
    }

    public void removeAddEventListener(final AddEventListener ael) {
        AEL=null;
    }

    /**
     * Add an object o to the construction.
     *
     * @param o
     */
    public void add(final ConstructionObject o) {
        if (!o.isGotNCount()) {
            o.setNCount(Count++);
        } // give count
        else {
            o.setGotNCount(false);
        }
        V.addElement(o);
        if (!Undo.isEmpty()) {
            Undo.removeAllElements();
        } // clear undo
        o.setConstruction(this);
        if (AEL!=null) {
            AEL.added(this, o);
        } // note add listener
        haveChanged(); // set changed flag
    }

    /**
     * Add an object o, but do not note add listener. Add listener is used to
     * check, if an assignment is finished.
     *
     * @param o
     */
    public void addNoCheck(final ConstructionObject o) // add a new object
    {
        if (!o.isGotNCount()) {
            o.setNCount(Count++);
        } // give count
        else {
            o.setGotNCount(false);
        }
        V.addElement(o);
        if (!Undo.isEmpty()) {
            Undo.removeAllElements();
        }
        o.setConstruction(this);
        haveChanged();
    }

    public void added(final ConstructionObject o) {
        if (AEL!=null) {
            AEL.added(this, o);
        }
    }

    /**
     * Delete all objects.
     */
    public synchronized void clear() {
        V=new Vector();
        if (!Undo.isEmpty()) {
            Undo.removeAllElements();
        }
        Comment="";
        JobComment="";
        clearParameters();
        clearTargets();
        Prompts=new Vector();
        X=0;
        Y=0;
        W=8;
        Changed=false;
        Count=0;
    }
    Vector Undo=new Vector();

    /**
     * Clear last element.
     */
    public void back() {
        final ConstructionObject o=lastButN(0);
        if (o==null) {
            return;
        }
        o.setInConstruction(false);
        Undo.addElement(o);
        if (V.size()>0) {
            V.removeElementAt(V.size()-1);
        }
        updateCircleDep();
        clearParameters();
        clearTargets();
        haveChanged();
    }

    /**
     * Delete the object and all children. The constructable elements must have
     * been marked before.
     */
    public void delete(final boolean clearUndo) {
        if (clearUndo&&!Undo.isEmpty()) {
            Undo.removeAllElements();
        }
        for (int i=V.size()-1; i>=0; i--) {
            final ConstructionObject o=(ConstructionObject) V.elementAt(i);
            if (o.isFlag()&&!o.isJobTarget()) {
                o.setInConstruction(false);
                Undo.addElement(o);
                V.removeElementAt(i);
            }
        }
        updateCircleDep();
        clearParameters();
        clearTargets();
        haveChanged();
    }

    public void delete() {
        delete(true);
    }

    /**
     * Restore all elements from Undo
     */
    public void undo() {
        if (Undo.isEmpty()) {
            return;
        }
        final ConstructionObject o[]=new ConstructionObject[Undo.size()];
        Undo.copyInto(o);
        for (int i=o.length-1; i>=0; i--) {
            V.addElement(o[i]);
            o[i].setConstruction(this);
        }
        Undo.removeAllElements();
        haveChanged();
    }

    public Enumeration elements() // enumerate all objects
    {
        return V.elements();
    }

    public Enumeration getSortedElements() {
        final ConstructionObject o[]=new ConstructionObject[V.size()];
        V.copyInto(o);
        for (int i=0; i<o.length; i++) {
            o[i].Value=(o[i].getNCount());
        }
        Sorter.sort(o);
        final Vector v=new Vector();
        for (final ConstructionObject element : o) {
            v.addElement(element);
        }
        return v.elements();
    }

    //lastButN(0) is the last object
    //lastButN(1) is the lastButOne object
    public ConstructionObject lastButN(int N){
        if(V.size()>N){
            return (ConstructionObject) V.elementAt(V.size()-(N+1));
        } else {
            return null;
        }
    }

    public ConstructionObject lastByNumber() {
        ConstructionObject o=null;
        int maxCount=-1;
        final Enumeration e=elements();
        while (e.hasMoreElements()) {
            final ConstructionObject co=(ConstructionObject) e.nextElement();
            if (co.getNCount()>maxCount) {
                maxCount=co.getNCount();
                o=co;
            }
        }
        return o;
    }

    public void clearAfter(final ConstructionObject after) {
        while (true) {
            final ConstructionObject o=lastButN(0);
            if (o==null||o==after) {
                break;
            }
            o.setInConstruction(false);
            V.removeElementAt(V.size()-1);
            haveChanged();
        }
        updateCircleDep();
        clearParameters();
        clearTargets();
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String s) {
        if (s.length()<=2) {
            s="";
        }
        Comment=s;
    }

    public double getX() {
        return X;
    }

    public double getY() {
        return Y;
    }

    public double getW() {
        return W;
    }

    public double getH() {
        return H;
    }

    public void setH(final double h) {
        H=h;
    }

    public void setXYW(final double x, final double y, final double w) {
//        System.out.println("X="+X+" Y="+Y+" W="+W);
        X=x;
        Y=y;
        W=w;
    }

    public void save(final XmlWriter xml) {
//        setOriginalOrder(true);

        final Enumeration e=elements();
        while (e.hasMoreElements()) {
            ((ConstructionObject) e.nextElement()).save(xml);
        }
        Changed=false;
    }
    public String TrackP=null, TrackPM=null, TrackO=null,
            AnimateP=null;
    public Vector TrackPO;
    public Vector AnimateV=null;
    public int Omit=0;
    public boolean AnimateNegative=false;
    public boolean AnimateOriginal=false;
    public String AnimateDelay=null;
    public String Icons="";
    public boolean AnimateBreakpoints=false;
    public long AnimateTime=1000;
    public boolean AnimateLoop=false;
    public boolean ResizeBackground=false;
    public String BackgroundFile=null;

    public synchronized void load(XmlTree tree, final ZirkelCanvas zc)
            throws ConstructionException, InterruptedException {
        //new Vector();
        final Enumeration root=tree.getContent();
        TrackP=null;
        TrackPO=new Vector();
        AnimateP=null;
        AnimateNegative=false;
        AnimateOriginal=false;
        AnimateBreakpoints=false;
        AnimateLoop=false;
        AnimateTime=1000;
        Icons="";
        BackgroundFile=null;
        ResizeBackground=false;
        zc.clearDrawings();
        while (root.hasMoreElements()) {
            tree=(XmlTree) root.nextElement();
            final XmlTag tag=tree.getTag();

            if (tag.name().equals("Comment")) {
                try {
                    setComment(tree.parseComment());
                } catch (final Exception e) {
                    throw new ConstructionException("Illegal Comment");
                }
            } else if (tag.name().equals("Assignment")) {
                try {
                    setJobComment(tree.parseComment());
                } catch (final Exception e) {
                    throw new ConstructionException("Illegal Assignment");
                }
            } else if (tag.name().equals("Track")) {

                if (!tag.hasParam("track")) {
                    throw new ConstructionException(Global.name("exception.track"));
                }
                TrackP=tag.getValue("track");
                TrackPO=new Vector();
                int i=0;
                while (tag.hasParam("track"+i)) {
                    TrackPO.addElement(tag.getValue("track"+i));
                    i++;
                }
                if (tag.hasParam("move")) {
                    TrackPM=tag.getValue("move");
                } else {
                    TrackPM=null;
                }
                if (tag.hasParam("on")) {
                    TrackO=tag.getValue("on");

                } else {
                    TrackO=null;
                }
                Animate=false;
                Paint=true;
                if (tag.hasParam("animate")) {
                    if (tag.getValue("animate").equals("nopaint")) {
                        Paint=false;
                    }
                    Animate=true;
                }
                Omit=0;
                if (tag.hasParam("omit")) {
                    try {
                        Omit=Integer.parseInt(tag.getValue("omit"));
                    } catch (final Exception e) {
                    }
                }
            } else if (tag.name().equals("Animate")) {

                if (!tag.hasParam("animate")) {
                    throw new ConstructionException(Global.name("exception.animate"));
                }
                zc.addAnimation(tag.getValue("animate"));

                if (tag.hasParam("negative")) {
                    zc.setAnimationNegative(tag.getValue("animate"), tag.getValue("negative").equals("true"));
                }
                if (tag.hasParam("delay")) {
                    zc.setAnimationDelay(Double.valueOf(tag.getValue("delay")));
                }
                if (tag.hasParam("stopped")) {
                    if (tag.getValue("stopped").equals("true")) {
                        zc.getAnimations().stopAnimation();
                    }
                }
            } else if (tag.name().equals("RestrictedSession")) {
                if (tag.hasParam("HiddenIcons")) {
                    zc.setHiddenItems(tag.getValue("HiddenIcons"));
                }
            } else if (tag.name().equals("Exercise")) {

                if (tag.hasParam("message_ok")) {
                    zc.job_setMessageOk(tag.getValue("message_ok"));
                }
                if (tag.hasParam("message_failed")) {
                    zc.job_setMessageFailed(tag.getValue("message_failed"));
                }
                if (tag.hasParam("hidefinals")) {
                    zc.job_setHideFinals("true".equals(tag.getValue("hidefinals")));
                }
                if (tag.hasParam("staticjob")) {
                    zc.job_setStaticJob("true".equals(tag.getValue("staticjob")));
                }
                if (tag.hasParam("targets")) {
                    zc.job_setTargetNames(tag.getValue("targets"));
                }
                if (tag.hasParam("backup")) {
                    zc.job_setBackup(tag.getValue("backup"));
                }
            } else if (tag.name().equals("AnimateBreakpoints")) {
                AnimateBreakpoints=true;
                try {
                    if (tag.hasParam("time")) {
                        AnimateTime=new Long(tag.getValue("time")).longValue();
                    }
                    if (tag.hasParam("loop")) {
                        AnimateLoop=true;
                    }
                } catch (final Exception e) {
                    throw new ConstructionException("exception.animate");
                }
            } else if (tag.name().equals("Window")) {
                try {
                    if (tag.hasParam("x")) {
                        X=new Double(tag.getValue("x")).doubleValue();
                    }
                    if (tag.hasParam("y")) {
                        Y=new Double(tag.getValue("y")).doubleValue();
                    }
                    if (tag.hasParam("w")) {
                        W=new Double(tag.getValue("w")).doubleValue();
                    }
//                    Global.setParameter("showgrid",tag.hasTrueParam("showgrid"));
                    zc.setAxis_show(tag.hasTrueParam("showgrid"));
                } catch (final Exception e) {
                    throw new ConstructionException("Illegal Window Parameters");
                }
            } else if (tag.name().equals("Grid")) {
                try {
                    zc.setAxis_show(true);
                    if (tag.hasParam("color")) {
                        final int n=new Double(tag.getValue("color")).intValue();
//                        final int n=new Integer(tag.getValue("color")).intValue();
                        if (n>=0&&n<ZirkelFrame.Colors.length) {
                            zc.setAxis_color(n);
                        }
                    }
                    if (tag.hasParam("thickness")) {
                        final int n=new Double(tag.getValue("thickness")).intValue();
//                        final int n=new Integer(tag.getValue("thickness")).intValue();
                        if (n>=0&&n<4) {
                            zc.setAxis_thickness(n);
                        }
                    }
                    zc.setAxis_labels(!tag.hasTrueParam("nolabels"));
                    zc.setAxis_with_grid(!tag.hasTrueParam("axesonly"));
                    Global.setParameter("grid.large",tag.hasTrueParam("large"));
                    Global.setParameter("grid.bold",tag.hasTrueParam("bold"));
                } catch (final Exception e) {
                    throw new ConstructionException("Illegal Grid Parameters");
                }
            } else if (tag.name().equals("Background")) {
                try {
                    if (tag.hasTrueParam("resize")) {
                        ResizeBackground=true;
                    }

                    Global.setParameter("background.usesize", tag.hasTrueParam("usesize"));
                    Global.setParameter("background.tile", tag.hasTrueParam("tile"));
                    Global.setParameter("background.center", tag.hasTrueParam("center"));




//                                        final String filename = JZirkelCanvas.getFilePath(zc)+tag.getValue("file");
//                                        Media.createMedia(filename);

                    BackgroundFile=tag.getValue("file");
//                                        System.out.println("BackgroundFile="+Media.getMedias().size());
                    if (BackgroundFile==null) {
                        throw new ConstructionException(
                                "Illegal Background Parameters");
                    }
                } catch (final Exception e) {
                    throw new ConstructionException(
                            "Illegal Background Parameters");
                }
            } else if (tag.name().equals("Draw")) {
                zc.loadDrawings(tree);
            } else if (tag.name().equals("Objects")) { // System.out.println("reading objects");
                readConstruction(tree, true);
                // System.out.println("finished reading objects");
                updateCount();
                computeNeedsOrdering();
                // System.out.println("needs ordering: "+NeedsOrdering);
                doOrder();
                // System.out.println("finished reading objects");
                break;
            } else if (tag.name().equals("Restrict")) {
                if (!tag.hasParam("icons")) {
                    throw new ConstructionException("Illegal Window Parameters");
                }
                Icons=tag.getValue("icons");
            } else {
                zc.XmlTagReader(tag);
                // collect possible ctrl tags for later :
                zc.JCM.collectXmlTag(tag);
                // CTRLtags.add(tag);
            }
        }

        setCurrentMagnetObjects();
        zc.job_setTargets();
        zc.setBackground(Global.getParameter("colorbackground",
                new java.awt.Color(230, 230, 230)));
        zc.updateDigits();
        zc.resetGraphics();
        zc.repaint();
    }

    public void translateOffsets(final ZirkelCanvas zc) {
        final Enumeration e=V.elements();
        while (e.hasMoreElements()) {
            ((ConstructionObject) e.nextElement()).translateOffset(zc);
        }
    }

    public synchronized void readConstruction(XmlTree tree, boolean showProgressBar)
            throws ConstructionException {
//		 System.out.println("start reading tree");
        int max=Collections.list(tree.getContent()).size();
        if ((showProgressBar)&&(max>100)) {
            Progress_Bar.create(Global.Loc("progressbar.loadmessage"), 0, max);
        }
        Enumeration e=tree.getContent();
        while (e.hasMoreElements()) {
            Progress_Bar.nextValue();
            tree=(XmlTree) e.nextElement();
            int i;
            final int n=ObjectConstructors.length;
            for (i=0; i<n; i++) {
                try {
                    if (ObjectConstructors[i].construct(tree, this)) {
                        break;
                    }
                } catch (final ConstructionException ex) {
                    if (tree.getTag().hasParam("name")) {
                        final String name=tree.getTag().getValue("name");
                        throw new ConstructionException(ex.getDescription()
                                +" (in "+name+")");
                    } else {
                        throw ex;
                    }
                }
            }
            if (i>=n) {
                throw new ConstructionException(tree.getTag().name()
                        +" unknown!");
            }
        }
        Progress_Bar.close();
        // System.out.println("end reading tree");
        e=V.elements();
        while (e.hasMoreElements()) {
            ((ConstructionObject) e.nextElement()).laterBind(this);
        }
        // System.out.println("finished later bind");
        dovalidate();
        updateCircleDep();
        Changed=false;
        // System.out.println("finished circle dep");
    }

    /**
     *
     * @param name
     * @return Object with that name or null.
     */
    public ConstructionObject find(final String name) {
        final Enumeration e=V.elements();
        while (e.hasMoreElements()) {
            final ConstructionObject c=(ConstructionObject) e.nextElement();
            if (c.getName().equals(name)) {
                return c;
            }
        }
        return null;
    }

    public PrimitiveCircleObject getHZ(){
        if (!isDP()) {
            return null;
        }
        if (HZ==null) {
            HZ=(PrimitiveCircleObject) find("Hz");
        }
        return HZ;
    }

    /**
     * Find an object with a name, defined before object until.
     *
     * @param name
     * @param until
     * @return Object with that name or null
     */
    public ConstructionObject find(final String name,
            final ConstructionObject until) {
        final Enumeration e=V.elements();
        while (e.hasMoreElements()) {
            final ConstructionObject c=(ConstructionObject) e.nextElement();
            if (c==until) {
                break;
            }
            if (c.getName().equals(name)) {
                return c;
            }
        }
        return null;
    }

    /**
     * Find an object with that name before and inclusive object until.
     *
     * @param name
     * @param until
     * @return Object with that name or null
     */
    public ConstructionObject findInclusive(final String name,
            final ConstructionObject until) {
        final Enumeration e=V.elements();
        while (e.hasMoreElements()) {
            final ConstructionObject c=(ConstructionObject) e.nextElement();
            if (c.getName().equals(name)) {
                return c;
            }
            if (c==until) {
                break;
            }
        }
        return null;
    }

    /**
     * See, if the first object is prior to the second.
     */
    public boolean before(final ConstructionObject first,
            final ConstructionObject second) {
        final Enumeration e=V.elements();
        while (e.hasMoreElements()) {
            final ConstructionObject c=(ConstructionObject) e.nextElement();
            if (c==first) {
                return true;
            }
            if (c==second) {
                break;
            }
        }
        return false;
    }

    /**
     * See, if one Object on directly depends on another o.
     */
    public boolean dependsDirectlyOn(final ConstructionObject o,
            final ConstructionObject on) {
        final Enumeration e=o.depending();
        while (e.hasMoreElements()) {
            if (on==e.nextElement()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Unmark recursion flag of all objects.
     */
    public void clearRekFlags() {
        final Enumeration e=elements();
        while (e.hasMoreElements()) {
            final ConstructionObject o=(ConstructionObject) e.nextElement();
            o.setRekFlag(false);
        }
    }

    /**
     * Check of object o depends on object on.
     *
     * @param o
     * @param on
     * @return true or false
     */
    public boolean dependsOn(final ConstructionObject o,
            final ConstructionObject on) {
        clearRekFlags(); // used as markers
        final boolean res=dependsOnRek(o, on);
        return res;
    }

    /**
     * Rekursive check, if o depends on object on. Clear constructables before,
     * since they are marked as already checked!
     *
     * @param o
     * @param on
     * @return true or false
     */
    public boolean dependsOnRek(final ConstructionObject o,
            final ConstructionObject on) { // System.out.println(o.getName()+" depends on "+on.getName()+"?");
        o.setRekFlag(true);
        if (o==on) {
            return true;
        }
        final ConstructionObject o1[]=o.getDepArray();
        for (final ConstructionObject element : o1) { // System.out.println(o.getName()+" - check: "+o1[i].getName());
            if (element==o||element.isRekFlag()) {
                continue;
            }
            // System.out.println("flag not set.");
            if (dependsOnRek(element, on)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Reorder the construction completely, so that no forwared references
     * occur. This is a simple algorithm using tail chain length in dircted
     * graphs. The construction must not contain circles, however.
     *
     */
    public void reorderConstruction() { // Get the objects into a faster array
        final ConstructionObject o[]=new ConstructionObject[V.size()];
        boundMagnetObjects();
        V.copyInto(o);
        final int n=o.length;
        if (n==0) {
            return;
        }
        // Compute tail chain length for all objects recursively
        for (int i=0; i<n; i++) {
            o[i].Scratch=0;
            o[i].Flag=false;
        }
        for (int i=0; i<n; i++) {
            countTail(((ConstructionObject) o[i]));
            /*
             * // Test print:
             * System.out.println(((ConstructionObject)o[i]).getName()+" "+
             * ((ConstructionObject)o[i]).Scratch); Enumeration
             * e=((ConstructionObject)o[i]).depending(); while
             * (e.hasMoreElements()) {
             * System.out.println("- "+((ConstructionObject
             * )e.nextElement()).getName()); }
             */
        }
        // Sort the array using bubble sort (least distroying sort)
        if (n<500) {
            for (int i=1; i<n; i++) {
                final int k=o[i].Scratch;
                int j=i;
                while (j>0&&(o[j-1]).Scratch>k) {
                    j--;
                }
                if (j<i) {
                    final ConstructionObject oh=o[i];
                    for (int h=i; h>j; h--) {
                        o[h]=o[h-1];
                    }
                    o[j]=oh;
                }
            }
        } else // Use quick sort
        {
            for (int i=0; i<o.length; i++) {
                o[i].Value=o[i].Scratch;
            }
            Sorter.sort(o);
        }
        // Copy back all objects into the construction
        V=new Vector();
        for (int i=0; i<n; i++) {
            V.addElement(o[i]);
        }
        reorderAxisObjects();
        unboundMagnetObjects();
    }

    /**
     * Recursive help routine for the reordering. Returns the maximal tail
     * length of the object o. It is assumed that marked objects already know
     * their correct tail length.
     *
     * @param o
     * @return tail length
     */
    public int countTail(final ConstructionObject o) {
        if (o.Flag) {
            return o.Scratch;
        }
        o.Flag=true;
        int max=0;
        final ConstructionObject oc[]=o.getDepArray();
        if (oc.length==0) {
            o.Scratch=0;
        } else {
            for (final ConstructionObject element : oc) {
                if (element==o) {
                    continue;
                }
                final int k=countTail(element);
                if (k>max) {
                    max=k;
                }
            }
            o.Scratch=max+1;
        }
        return o.Scratch;
    }
    boolean NeedsOrdering=false;

    /**
     * Set the reordering flag.
     */
    public void needsOrdering() {
        NeedsOrdering=true;
    }

    /**
     * If the construction needs reording, order it!
     */
    public void doOrder() {
        if (!NeedsOrdering) {
            return;
        }
        reorderConstruction();
        NeedsOrdering=false;
    }

    /**
     * Walk through the construction and see, if any element contains a forward
     * dependency.
     */
    public void computeNeedsOrdering() { // none checked so far
        Enumeration e=V.elements();
        while (e.hasMoreElements()) {
            ((ConstructionObject) e.nextElement()).Flag=false;
        }
        // check all
        e=V.elements();
        while (e.hasMoreElements()) {
            final Enumeration ec=((ConstructionObject) e.nextElement()).depending();
            while (ec.hasMoreElements()) {
                if (!((ConstructionObject) ec.nextElement()).Flag) {
                    NeedsOrdering=true;
                    return;
                }
            }
        }
        NeedsOrdering=false;
    }

    /**
     *
     * @param o
     * @return Index of the object o in the vector.
     */
    public int indexOf(final ConstructionObject o) {
        return V.indexOf(o);
    }

    /**
     *
     * @param o
     * @return Last object, o depends on or nil
     */
    public ConstructionObject lastDep(final ConstructionObject o) {
        int res=-1;
        ConstructionObject ores=null;
        final Enumeration e=o.depending();
        while (e.hasMoreElements()) {
            final ConstructionObject u=(ConstructionObject) e.nextElement();
            final int i=indexOf(u);
            if (i>res) {
                res=i;
                ores=u;
            }
        }
        return ores;
    }

    public void setCurrentMagnetObjects() {
        final Enumeration e=V.elements();
        while (e.hasMoreElements()) {
            final ConstructionObject o=(ConstructionObject) e.nextElement();
            if (o instanceof PointObject) {
                final PointObject pt=(PointObject) o;
                pt.setCurrentMagnetObject();
            }
        }
    }

    public void boundMagnetObjects() {
        final Enumeration e=V.elements();
        while (e.hasMoreElements()) {
            final ConstructionObject o=(ConstructionObject) e.nextElement();
            if (o instanceof PointObject) {
                final PointObject pt=(PointObject) o;
                if (pt.getCurrentMagnetObject()!=null) {
                    pt.VirtualBound=pt.getBound();
                    pt.setBound(pt.getCurrentMagnetObject());
                }
            }
        }
    }

    public void unboundMagnetObjects() {
        final Enumeration e=V.elements();
        while (e.hasMoreElements()) {
            final ConstructionObject o=(ConstructionObject) e.nextElement();
            if (o instanceof PointObject) {
                final PointObject pt=(PointObject) o;
                if (pt.getCurrentMagnetObject()!=null) {
                    pt.setBound(pt.VirtualBound);
                }
            }
        }
    }

    public void reorderAxisObjects() {
        if ((xAxis!=null)&&(yAxis!=null)) {
            final Enumeration e=V.elements();
            int shft=2;
            while (e.hasMoreElements()) {
                final ConstructionObject o=(ConstructionObject) e.nextElement();
                if (o instanceof AxisObject) {
                    shft--;
                    o.setNCount(shft);

                } else {
                    o.setNCount(o.getNCount()+shft);
                }
            }
        }
    }

    /**
     * Set the object o1 right after o2, if possible. Never put an element to a
     * later position!
     */
    public boolean reorder(final ConstructionObject o1,
            final ConstructionObject o2) { /*
         * // old routine changing internal
         * order int k1=indexOf(o1),k2=-1;
         * if (o2!=null) k2=indexOf(o2); if
         * (k1<=k2+1) return false;
         * ConstructionObject o=lastDep(o1);
         * if (o!=null && indexOf(o)>k2)
         * return false;
         * V.removeElement(o1);
         * V.insertElementAt(o1,k2+1);
         */
        // new routine changing generation numbers
        int n=-1;
        if (o2!=null) {
            n=o2.getNCount();
        }
        final Enumeration e=V.elements();
        while (e.hasMoreElements()) {
            final ConstructionObject o=(ConstructionObject) e.nextElement();
            if (o.getNCount()>n) {
                o.setNCount(o.getNCount()+1);
            }
        }
        o1.setNCount(n+1);
        haveChanged();
        return true;
    }

    /**
     * Update the texts of all objects that contain oldname, but not of the
     * object o itself.
     *
     * @param o
     * @param oldname
     */
    public void updateTexts(final ConstructionObject o, final String oldname) {
        final Enumeration e=V.elements();
        while (e.hasMoreElements()) {
            final ConstructionObject u=(ConstructionObject) e.nextElement();
            if (dependsDirectlyOn(u, o)) {
                u.updateText();
            }
        }
    }

    public String getJobComment() {
        return JobComment;
    }

    public void setJobComment(final String s) {
        JobComment=s;
    }

    public void updateCircleDep() {
        Enumeration e=V.elements();
        while (e.hasMoreElements()) {
            ((ConstructionObject) e.nextElement()).clearCircleDep();
        }
        e=V.elements();
        while (e.hasMoreElements()) {
            ((ConstructionObject) e.nextElement()).updateCircleDep();
        }
    }

    public Vector getParameters() {
        return Parameters;
    }

    public int countParameters() {
        if (Parameters==null) {
            return 0;
        }
        return Parameters.size();
    }

    public Vector getTargets() {
        return Targets;
    }

    public int countTargets() {
        if (Targets==null) {
            return 0;
        }
        return Targets.size();
    }

    public void insertParameter(final ConstructionObject o,int index){
        Parameters.insertElementAt(o, index);
    }

    public void addParameter(final ConstructionObject o) {
        Parameters.addElement(o);
    }

    public void removeParameter(final ConstructionObject o) {
        Parameters.removeElement(o);
    }

    public void addTarget(final ConstructionObject o) {
        Targets.addElement(o);
    }

    public void removeTarget(final ConstructionObject o) {
        Targets.removeElement(o);
    }

    public void clearParameters() {
        final Enumeration e=elements();
        while (e.hasMoreElements()) {
            final ConstructionObject o=(ConstructionObject) e.nextElement();
            o.clearParameter();
            o.setSpecialParameter(false);
        }
        Parameters=new Vector();
    }

    public void clearTargets() {
        final Enumeration e=elements();
        while (e.hasMoreElements()) {
            final ConstructionObject o=(ConstructionObject) e.nextElement();
            o.setTarget(false);
        }
        Targets=new Vector();
    }

    public void testParameter(final ConstructionObject o)
            throws ConstructionException {
        throw new ConstructionException(Global.name("exception.null"));
    }
    Interpreter Int=new Interpreter(this);

    /**
     * This is to interpret a single line of input in descriptive mode or a
     * single line read from a construction description in a file.
     */
    public void interpret(final ZirkelCanvas zc, final String s,
            final String comment) throws ConstructionException {
        Int.interpret(zc, s, comment);
    }
    /*
    public void interpret(final ZirkelCanvas zc, final String s)
            throws ConstructionException {
        Int.interpret(zc, s, "");
    }
    */
    boolean IntersectionBecameInvalid;

    public void dovalidate() {
        doOrder(); // will do something only if necessary
        while (true) {
            final Enumeration e=elements();
            boolean stop=true;
            IntersectionBecameInvalid=false;
            while (e.hasMoreElements()) {
                final ConstructionObject o=(ConstructionObject) e.nextElement();
                final boolean valid=o.valid();
                o.validate();
                if (o instanceof IntersectionObject&&valid&&!o.valid()) {
                    IntersectionBecameInvalid=true;
                }
                if (o.changedBy()>1e-6) {
                    stop=false;
                }
            }
            if (stop) {
                break;
            }
        }
    }

    /**
     * @return During last dovalidate an intersection became invalid.
     */
    public boolean intersectionBecameInvalid() {
        return IntersectionBecameInvalid;
    }

    public void dovalidateDebug() {
        doOrder(); // will do something only if necessary
        System.out.println("--- Time validate() ---");
        while (true) {
            final Enumeration e=elements();
            boolean stop=true;
            while (e.hasMoreElements()) {
                final ConstructionObject o=(ConstructionObject) e.nextElement();
                long time=System.currentTimeMillis();
                for (int i=0; i<100; i++) {
                    o.validate();
                }
                time=System.currentTimeMillis()-time;
                if (time>0) {
                    System.out.println(o.getName()+" - "+(time/100.0)
                            +" msec");
                }
                if (o.changedBy()>1e-6) {
                    stop=false;
                }
            }
            if (stop) {
                break;
            }
        }
    }

    /**
     * Validate only o and those objects it depends on. This is a recursive
     * function. To avoid problems with cycles we set flags.
     *
     * @param o
     */
    public void validate(final ConstructionObject o,
            final ConstructionObject avoid) {
        if (o.RekValidating) {
            return;
        } // should not happen!
        o.RekValidating=true;
        if (o.VRek==null) {
            o.VRek=new MyVector();
        } else {
            o.VRek.removeAllElements();
        }
        Enumeration e=elements();
        while (e.hasMoreElements()) {
            ((ConstructionObject) e.nextElement()).setRekFlag(false);
        }
        recursiveValidate(o, avoid);
        e=elements();
        while (e.hasMoreElements()) {
            final ConstructionObject oc=(ConstructionObject) e.nextElement();
            if (oc.isRekFlag()) {
                o.VRek.addElement(oc);
            }
        }
        // System.out.println("---");
        e=o.VRek.elements();
        while (e.hasMoreElements()) {
            final ConstructionObject oc=(ConstructionObject) e.nextElement();
            oc.validate();
            // System.out.println("Validating "+oc.getName());
        }
        o.RekValidating=false;
    }

    public void recursiveValidate(final ConstructionObject o,
            final ConstructionObject avoid) {

        if (o.isRekFlag()||o==avoid) {
            return;
        }
        o.setRekFlag(true);
        final ConstructionObject d[]=o.getDepArray();
        for (final ConstructionObject element : d) {
            recursiveValidate(element, avoid);
        }
    }

    private boolean isNewColor(final Vector v, final Color col) {
        final Enumeration e=v.elements();
        while (e.hasMoreElements()) {
            final eric.JColors jc=(eric.JColors) e.nextElement();
            if (jc.C.equals(col)) {
                return false;
            }
        }
        return true;
    }

    public Vector getSpecialColors() {
        final Vector vec=new Vector();
        final Enumeration e=elements();
        while (e.hasMoreElements()) {
            final ConstructionObject o=(ConstructionObject) e.nextElement();
            final Color c=o.getSpecialColor();

            if ((c!=null)&&isNewColor(vec, c)) {
                vec.add(new eric.JColors(c, o.getName()));
            }
        }
        return vec;
    }

    public void computeHeavyObjects(final ZirkelCanvas zc, boolean forceCompute) {
        final Enumeration e=elements();
        while (e.hasMoreElements()) {
            final ConstructionObject o=(ConstructionObject) e.nextElement();
            if (o instanceof JLocusTrackObject) {
                final JLocusTrackObject jl=(JLocusTrackObject) o;
                if (jl.needsToRecompute()||forceCompute) {
                    jl.compute(zc);
                }
            } else if (o instanceof TrackObject) {
                ((TrackObject) o).compute(zc);
            } else if (o instanceof FunctionObject) {
                final FunctionObject f=(FunctionObject) o;
                if (f.needsToRecompute()||forceCompute) {
                    f.compute();
                }
            } else if (o instanceof EquationXYObject) {
                ((EquationXYObject) o).compute();
            }
        }
        Global.doClearList();
    }

    public void clearTranslations() {
        final Enumeration e=elements();
        while (e.hasMoreElements()) {
            final ConstructionObject o=(ConstructionObject) e.nextElement();
            o.setTranslation(null);
        }
    }

    /**
     * Walk through the construction and mark all items, which are constructable
     * from the set parameter items. This is used to determine the possible
     * targets.
     *
     */
    public void determineConstructables() {
        final Enumeration e=elements();
        while (e.hasMoreElements()) {
            final ConstructionObject o=(ConstructionObject) e.nextElement();

            if (o.isParameter()) {
//                System.out.println("parameter:"+o.getName());
                o.setFlag(true);
            } else {
//                System.out.println("not parameter:"+o.getName());
                final Enumeration ee=o.depending();
                boolean constructable=(o instanceof ExpressionObject||o instanceof FunctionObject);
                while (ee.hasMoreElements()) {
                    final ConstructionObject oo=(ConstructionObject) ee.nextElement();
                    if (o!=oo) {
                        if (oo.isFlag()) {
                            constructable=true;
                        } else {
                            constructable=false;
                            break;
                        }
                    }
                }
                o.setFlag(constructable);
//                 if (constructable) System.out.println(o.getName());
            }
        }
    }

    /**
     * Recursively go throught the objects, which o needs, and mark them as
     * constructable, until a parameter object has been reached. The object
     * flags are used and must be cleared before.
     *
     * @see clearConstructables
     * @param o
     * @return Object is constructable.
     */
    public boolean determineConstructables(final ConstructionObject o) {
        if (o.isFlag()) {
            return true;
        }
        final ConstructionObject dep[]=o.getDepArray();
        boolean constructable=(o instanceof ExpressionObject||o instanceof FunctionObject);
        for (int i=0; i<dep.length; i++) {
            if (dep[i]==null) {
                return false;
            }
            if (dep[i]==o) {
                continue;
            }
            if (!determineConstructables(dep[i])) {
                return false;
            } else {
                constructable=true;
            }
        }
        o.setFlag(constructable);
        return true;
    }

    /**
     * Unmark constructable flag of all objects.
     *
     */
    public void clearConstructables() {
        final Enumeration e=elements();
        while (e.hasMoreElements()) {
            final ConstructionObject o=(ConstructionObject) e.nextElement();
            o.setFlag(false);
        }
    }

    public boolean haveChildren(final ConstructionObject on) {
        clearConstructables();
        on.setFlag(true);
        final Enumeration e=elements();
        while (e.hasMoreElements()) {
            final ConstructionObject o=(ConstructionObject) e.nextElement();
            if (!o.isFlag()) {
                final Enumeration ee=o.depending();
                while (ee.hasMoreElements()) {
                    final ConstructionObject oo=(ConstructionObject) ee.nextElement();
                    if (oo.isFlag()) {

                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Walk through the objects and determine all children of any object, which
     * is marked constructable. Mark those children as constructable too.
     */
    public void determineChildren() {
        final Enumeration e=elements();
        while (e.hasMoreElements()) {
            final ConstructionObject o=(ConstructionObject) e.nextElement();
            if (!o.isFlag()) {
                final Enumeration ee=o.depending();
                while (ee.hasMoreElements()) {
                    final ConstructionObject oo=(ConstructionObject) ee.nextElement();
                    if (oo.isFlag()) {
                        o.setFlag(true);
                        break;
                    }
                }
            }
        }

    }

    /**
     * Mark all parameter objects as constructables.
     *
     */
    public void setParameterAsConstructables() {
        final Enumeration e=elements();
        while (e.hasMoreElements()) {
            final ConstructionObject o=(ConstructionObject) e.nextElement();
            if (o.isParameter()||o.isMainParameter()) {
                o.setFlag(true);
            }
        }
    }

    public String[] getPromptFor() {
        final String s[]=new String[PromptFor.size()];
        PromptFor.copyInto(s);
        return s;
    }
    boolean ShouldSwitch=false, NoteSwitch=false;

    public boolean shouldSwitch() {
        return ShouldSwitch;
    }

    public boolean noteSwitch() {
        return NoteSwitch;
    }

    public void shouldSwitch(final boolean flag, final boolean note) {
        ShouldSwitch=flag;
        NoteSwitch=note;
    }

    public void shouldSwitch(final boolean flag) {
        ShouldSwitch=flag;
        NoteSwitch=true;
    }

    public void switchBack() {
        final Enumeration e=elements();
        while (e.hasMoreElements()) {
            final ConstructionObject o=(ConstructionObject) e.nextElement();
            if (o instanceof IntersectionObject) {
                ((IntersectionObject) o).switchBack();
            }
        }
    }

    public void clearSwitches() {
        final Enumeration e=elements();
        while (e.hasMoreElements()) {
            final ConstructionObject o=(ConstructionObject) e.nextElement();
            if (o instanceof IntersectionObject) {
                ((IntersectionObject) o).switchBack();
            }
        }
    }

    public boolean haveSwitched() {
        final Enumeration e=elements();
        while (e.hasMoreElements()) {
            final ConstructionObject o=(ConstructionObject) e.nextElement();
            if (o instanceof IntersectionObject) {
                if (((IntersectionObject) o).isSwitched()) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean changed() {
        return Changed;
    }
    private ChangedListener CL=null;

    public void addChangedListener(ChangedListener cl) {
        CL=cl;
    }

    public void haveChanged() {
        changed(true);
        if (CL!=null) {
            CL.notifyChanged();
        }
    }

    public void changed(final boolean flag) {
        Changed=flag;
    }
    public MyVector TranslatorList=new MyVector();

    public void addTranslator(final Translator t) {
        TranslatorList.addElement(t);
    }

    public void runTranslators(final Construction from) {
        final Enumeration e=TranslatorList.elements();
        while (e.hasMoreElements()) {
            final Translator t=(Translator) e.nextElement();
            t.laterTranslate(from);
        }
    }

    public void clearTranslators() {
        TranslatorList.removeAllElements();
    }

    /**
     * Set the count the maximal count number of all objects plus 1. (After
     * loading e.g.)
     */
    public void updateCount() {
        int max=0;
        final Enumeration e=V.elements();
        while (e.hasMoreElements()) {
            final int n=((ConstructionObject) e.nextElement()).getNCount();
            if (n>max) {
                max=n;
            }
        }
        Count=max+1;
    }
    Vector VOld=null;

    /**
     * Reset the constuction to the original order, saving the current one, or
     * use the current one again.
     *
     * @param original
     *            or current
     */
    public void setOriginalOrder(final boolean flag) {
        if (V==null) {
            return;
        }
        if (flag) {
            final ConstructionObject o[]=new ConstructionObject[V.size()];
            V.copyInto(o);
            for (int i=0; i<o.length; i++) {
                o[i].Value=(o[i].getNCount());
            }
            Sorter.sort(o);
            final Vector W=new Vector();
            for (final ConstructionObject element : o) {
                W.addElement(element);
            }
            VOld=V;
            V=W;
        } else if (VOld!=null) {
            V=VOld;
            VOld=null;
        }
    }

    public Construction getTranslation() {
        return TranslateInto;
    }

    public void setTranslation(final Construction C) {
        TranslateInto=C;
    }
    public boolean Loading=false;

    public boolean loading() {
        return Loading;
    }
    Vector Errors=new Vector();

    public void addError(final String s) {
        Errors.addElement(s);
    }

    public Enumeration getErrors() {
        return Errors.elements();
    }

    public void clearErrors() {
        Errors.removeAllElements();
    }

    public void dontAlternate(final boolean flag) {
        DontAlternateIntersections=flag;
    }

    public boolean canAlternate() {
        return !DontAlternateIntersections;
    }
    double Pixel=100; // Pixel per unit

    /**
     * Called by the ZirkelCanvas to set the pixel per units. This is used by
     * the pixel() function in Expression.
     *
     * @param pixel
     */
    public void setPixel(final double pixel) {
        Pixel=pixel;
    }

    public double getPixel() {
        return Pixel;
    }
}