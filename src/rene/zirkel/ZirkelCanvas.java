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
t
 */
package rene.zirkel;

import eric.GUI.ZDialog.ZDialog;
import eric.GUI.palette.JIconMouseAdapter;
import eric.GUI.palette.PaletteManager;
import eric.GUI.pipe_tools;
import eric.JGeneralMenuBar;
import eric.JSprogram.ScriptItem;
import eric.JSprogram.ScriptItemsArray;
import eric.JSprogram.ScriptPanel;
import eric.JSprogram.ScriptThread;
import eric.JZirkelCanvas;
import eric.animations.AnimationPanel;
import eric.bar.JProperties;
import eric.bar.JPropertiesBar;
import eric.controls.JCanvasPanel;
import eric.controls.JControlsManager;
import eric.jobs.JobManager;
import eric.macros.MacroTools;
import eric.restrict.RestrictContainer;
import eric.restrict.RestrictItems;
import java.awt.BasicStroke;
import java.awt.CheckboxMenuItem;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import static java.lang.Double.NaN;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Vector;
import javax.swing.SwingUtilities;

import rene.dialogs.Warning;
import rene.gui.Global;
import rene.gui.MyCheckboxMenuItem;
import rene.gui.MyMenuItem;
import rene.util.FileName;
import rene.util.MyVector;
import rene.util.parser.StringParser;
import rene.util.sort.Sorter;
import rene.util.xml.XmlReader;
import rene.util.xml.XmlTag;
import rene.util.xml.XmlTagPI;
import rene.util.xml.XmlTagText;
import rene.util.xml.XmlTree;
import rene.util.xml.XmlWriter;
import rene.zirkel.construction.ChangedListener;
import rene.zirkel.construction.Construction;
import rene.zirkel.construction.ConstructionDisplayPanel;
import rene.zirkel.construction.ConstructionException;
import rene.zirkel.construction.Count;
import rene.zirkel.construction.Interpreter;
import rene.zirkel.construction.Selector;
import rene.zirkel.constructors.IntersectionConstructor;
import rene.zirkel.constructors.ObjectConstructor;
import rene.zirkel.expression.Expression;
import rene.zirkel.expression.InvalidException;
import rene.zirkel.graphics.Drawing;
import rene.zirkel.graphics.LatexOutput;
import rene.zirkel.graphics.MainGraphics;
import rene.zirkel.graphics.MyGraphics;
import rene.zirkel.graphics.TrackPainter;
import rene.zirkel.listener.AddEventListener;
import rene.zirkel.listener.DoneListener;
import rene.zirkel.listener.StatusListener;
import rene.zirkel.macro.Macro;
import rene.zirkel.macro.MacroBar;
import rene.zirkel.macro.MacroContextualPopupMenu;
import rene.zirkel.macro.MacroItem;
import rene.zirkel.macro.MacroMenu;
import rene.zirkel.macro.MacroRunner;
import rene.zirkel.objects.AngleObject;
import rene.zirkel.objects.AreaObject;
import rene.zirkel.objects.AxisObject;
import rene.zirkel.objects.ConstructionObject;
import rene.zirkel.objects.EquationXYObject;
import rene.zirkel.objects.ExpressionObject;
import rene.zirkel.objects.FixedAngleObject;
import rene.zirkel.objects.FixedCircleObject;
import rene.zirkel.objects.FunctionObject;
import rene.zirkel.objects.IntersectionObject;
import rene.zirkel.objects.JLocusTrackObject;
import rene.zirkel.objects.MoveableObject;
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
import rene.zirkel.structures.CoordinatesXY;
import rene.zirkel.structures.MagnetObj;
import rene.zirkel.tools.BinderTool;
import rene.zirkel.tools.BreakpointAnimator;
import rene.zirkel.tools.EditTool;
import rene.zirkel.tools.JSmacroTool;
import rene.zirkel.tools.LabelMover;
import rene.zirkel.tools.MagnetTool;
import rene.zirkel.tools.MetaMover;
import rene.zirkel.tools.MoverTool;
import rene.zirkel.tools.ObjectTracker;
import rene.zirkel.tools.SelectTool;
import rene.zirkel.tools.SetAwayTool;
import rene.zirkel.tools.SetCurveCenterTool;
import rene.zirkel.tools.SetFixedAngle;
import rene.zirkel.tools.SetFixedCircle;
import rene.zirkel.tools.SetRangeTool;
import rene.zirkel.tools.SetTargetsTool;
import rene.zirkel.tools.Tracker;
import rene.zirkel.tools.UniversalTracker;
import rene.zirkel.tools.ZoomerTool;
import rene.zirkel.tools.nullTool;
import ui.de.erichseifert.vectorgraphics2d.VectorGraphics2D;

/**
 * @author Rene Main class, doing most of the work in the C.a.R. application.
 *         This canvas handles mouse and keyboard input, displays the
 *         construction and has tools to save and load constructions.
 */
// with addons by Dibs (patdeb) for 3D

public class ZirkelCanvas extends javax.swing.JPanel implements MouseListener,
        MouseMotionListener, ItemListener, AddEventListener, ActionListener,
        ChangedListener, MouseWheelListener // the canvas to do the construction
{

    private double minfontsize, minpointsize, minlinesize, arrowsize, selectionsize, monkeyspeed, gridopacity,
            digits_lengths, digits_edit, digits_angles, colorbackgroundx, colorbackgroundy,
            colorbackgroundPal, axis_color, axis_thickness;
    private boolean axis_show, axis_labels, axis_with_grid;
    private String colorbackground;
    private static JIconMouseAdapter JIMA=new JIconMouseAdapter();
    ObjectConstructor OC=new MoverTool();
    // the current object constructor
    Construction C=new Construction(), COriginal;
    // the construction (collection of Constructor-Objects)
    StatusListener SL=null;
    // for displaying a status message
    public Image I=null;
    MyGraphics IG;
    public UniversalTracker UniversalTrack=null;
    Image Background=null;
    FontMetrics FM;
//    public double PointSize=4.0; // Size of a point
//    double MinPointSize=4.0; // Default minimal point size
//    public double FontSize=12.0; // Size of font
//    double MinFontSize=12.0; // Default minimal font size
    public int IW=0, IH=0; // Image and its dimensions
    public double Xmin, DX, Ymin, DY;
    boolean ShowHidden=false; // show hidden objects
//    Frame F=new Frame();
    boolean ReadOnly;
    boolean AllowRightMouse=true;
    public MacroContextualPopupMenu PM;
    CheckboxMenuItem CheckboxHidden;
    MenuItem Replay, Empty;
    boolean Job=false; // this is a job (save as such)
    String Last=""; // Last displayed job object
    Vector Targets=new Vector(); // Target names
    ConstructionObject TargetO[], TargetS[]; // Target objects and solutions
    int ShowColor=0; // 0=all, 1=green etc. (black displays always)
    static public double // factors for rounding
            EditFactor=1000000.0, LengthsFactor=100.0, AnglesFactor=1.0;
    public boolean Visual=Global.getParameter("options.visual", true);
    boolean All;
    boolean Interactive=true;
    public JControlsManager JCM;
    private ScriptPanel Scripts;
    private AnimationPanel Animations;
    private JobManager Exercise;
    private RestrictContainer RestrictDialog=null; //GUI for Restricted icons editing
    private RestrictItems Restrict_items; // Restricted icons and palette zones
    private int OwnerWindowWidth, OwnerWindowHeight;
    private boolean islibrarymacrovisible=true;
//    public JUndo JU=new JUndo();
//    private dock DOCK;
    private boolean paintCalled=false;
    private Rectangle SelectionRectangle=null;
    private Color SelectionRectangleBackground=new Color(159, 166, 255, 30);
    private Color SelectionRectangleBorder=new Color(159, 166, 255);
    private int SelectionX, SelectionY;
    private ArrayList<ConstructionObject> MultipleSelection=new ArrayList();
    private Rectangle CopyRectangle=null;
    private Color CopyRectangleBackground=new Color(128, 177, 225, 100);
//    private Color CopyRectangleBackground=new Color(150, 150, 150, 150);
    private Color CopyRectangleBorder=new Color(0, 65, 129);
    private int CopyX, CopyY;
//    private CopyHandler copyE=null, copyW=null, copyN=null, copyS=null;
    private Color DPBackground=new Color(250, 250, 250);
    private Color DPBorder=new Color(163, 132, 0);
    private float DPStroke=3f;
//    private boolean
    private boolean DontPaint=false;
    private boolean firstLoad=true;
    private ui.pm.Client.ClientNetworkTools cnt = null;

    public boolean isEuclidian() {
        return C.isEuclidian();
    }

    public void setEuclidian(boolean b) {
        C.setEuclidian(b);
    }

    public boolean getDontPaint() {
        return DontPaint;
    }

    public void setDontPaint(boolean b) {
        DontPaint=b;
    }

    /**
     * Initiate an empty construction. The display may have a popup menu (only
     * for readonly displays).
     *
     * @param readonly
     *            User cannot change the construction.
     * @param replay
     *            Replay option in popup menu.
     * @param hidden
     *            Show hidden option in popup menu.
     */
    public ZirkelCanvas(final boolean readonly, final boolean replay,
            final boolean hidden) {
        ReadOnly=readonly;
        AllowRightMouse=!readonly;

        // setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
        setLayout(null);

        UniversalTrack=new UniversalTracker(this);
        JCM=new JControlsManager(this);

        if (Global.getParameter("options.nopopupmenu", false)) {
            PM=null;
        } else if (ReadOnly) {
            PM=new MacroContextualPopupMenu();
            CheckboxHidden=new MyCheckboxMenuItem(Global.name("popup.hidden"));
            CheckboxHidden.addItemListener(this);
            if (hidden) {
                PM.add(CheckboxHidden);
            }
            Replay=new MyMenuItem(Global.name("popup.replay"));
            Replay.addActionListener(this);
            if (replay) {
                PM.add(Replay);
            }
            if (hidden||replay) {
                add(PM);
            } else {
                PM=null;
            }
        } else {
            PM=new MacroContextualPopupMenu();
            Empty=new MyMenuItem(Global.name("popup.empty"));
            add(PM);
        }

        C.addChangedListener(this);
        clear();
        updateDigits();
        C.addAddEventListener(this);
        addMouseWheelListener(this);
        addMouseMotionListener(JIMA);

        /* Preferences which must be apply to
         * Every new Canvas :
         */
        Global.setParameter("axis_show", false);
        //Global.setParameter("colorbackground", getColor("245,245,245"));
        Global.setParameter("colorbackgroundx", 139);
        Global.setParameter("colorbackgroundy", 9);
        Global.setParameter("colorbackgroundPal", 4);

        axis_show=false;
        //colorbackground="245,245,245";
        colorbackgroundx=139;
        colorbackgroundy=9;
        colorbackgroundPal=4;

        Scripts=new ScriptPanel(this);
        Animations=new AnimationPanel(this);
        Exercise=new JobManager(this);
        Restrict_items=new RestrictItems(this);
//        add(Animations);
//        add(Scripts);
//        add(RestrictDialog);
    }

    public ZirkelCanvas(final boolean readonly) {
        this(readonly, true, true);
    }

    public ZirkelCanvas() {
        this(false, true, true);
    }

    public double getOne() {
        return C.getOne();
    }

    public void scaleLocalPreferences(double scale) {
        minfontsize=minfontsize*scale;
        minpointsize=minpointsize*scale;
        minlinesize=minlinesize*scale;
        arrowsize=arrowsize*scale;
        gridopacity=gridopacity*scale;
        C.setOne(scale);
    }

    public void getLocalPreferences() {
        minfontsize=Global.getParameter("minfontsize", 12);
        minpointsize=Global.getParameter("minpointsize", 3);
        minlinesize=Global.getParameter("minlinesize", 1);
        arrowsize=Global.getParameter("arrowsize", 10);
        selectionsize=Global.getParameter("selectionsize", 8);
        monkeyspeed=Global.getParameter("monkeyspeed", 20);
        gridopacity=Global.getParameter("gridopacity", 20);
        digits_lengths=Global.getParameter("digits.lengths", 2);
        digits_edit=Global.getParameter("digits.edit", 4);
        digits_angles=Global.getParameter("digits.angles", 2);
        colorbackground=Global.getParameter("colorbackground", "245,245,245");
        colorbackgroundx=Global.getParameter("colorbackgroundx", 139);
        colorbackgroundy=Global.getParameter("colorbackgroundy", 9);
        colorbackgroundPal=Global.getParameter("colorbackgroundPal", 4);
        axis_color=Global.getParameter("axis_color", 0);
        axis_thickness=Global.getParameter("axis_thickness", 0);
        axis_show=Global.getParameter("axis_show", false);
        axis_labels=Global.getParameter("axis_labels", true);
        axis_with_grid=Global.getParameter("axis_with_grid", true);
        C.setOne(1.0);
    }

    public void setLocalPreferences() {
        Global.setParameter("minfontsize", minfontsize);
        Global.setParameter("minpointsize", minpointsize);
        Global.setParameter("minlinesize", minlinesize);
        Global.setParameter("arrowsize", arrowsize);
        Global.setParameter("selectionsize", selectionsize);
        Global.setParameter("gridopacity", gridopacity);
        Global.setParameter("monkeyspeed", monkeyspeed);
        Global.setParameter("digits.lengths", digits_lengths);
        Global.setParameter("digits.edit", digits_edit);
        Global.setParameter("digits.angles", digits_angles);
        Global.setParameter("colorbackground", colorbackground);
        Global.setParameter("colorbackgroundx", colorbackgroundx);
        Global.setParameter("colorbackgroundy", colorbackgroundy);
        Global.setParameter("colorbackgroundPal", colorbackgroundPal);
        Global.setParameter("axis_color", axis_color);
        Global.setParameter("axis_thickness", axis_thickness);
        Global.setParameter("axis_show", axis_show);
        Global.setParameter("axis_labels", axis_labels);
        Global.setParameter("axis_with_grid", axis_with_grid);
    }

    public void XmlTagWriter(final XmlWriter xml) {
        xml.startTagStart("Windowdim");
        xml.printArg("w", ""+pipe_tools.getWindowSize().width);
        xml.printArg("h", ""+pipe_tools.getWindowSize().height);
        xml.finishTagNewLine();
        xml.startTagStart("Preferences");
        xml.printArg("minfontsize", ""+(int) minfontsize);
        xml.printArg("minpointsize", ""+(int) minpointsize);
        xml.printArg("minlinesize", ""+(int) minlinesize);
        xml.printArg("arrowsize", ""+(int) arrowsize);
        xml.printArg("selectionsize", ""+(int) selectionsize);
        xml.printArg("monkeyspeed", ""+(int) monkeyspeed);
        xml.printArg("gridopacity", ""+(int) gridopacity);
        xml.printArg("digits.lengths", ""+(int) digits_lengths);
        xml.printArg("digits.edit", ""+(int) digits_edit);
        xml.printArg("digits.angles", ""+(int) digits_angles);
        xml.printArg("colorbackground", ""+ colorbackground);
        xml.printArg("colorbackgroundx", ""+(int) colorbackgroundx);
        xml.printArg("colorbackgroundy", ""+(int) colorbackgroundy);
        xml.printArg("colorbackgroundPal", ""+(int) colorbackgroundPal);
        xml.printArg("fig3D", String.valueOf(is3D()));
        xml.printArg("figDP", String.valueOf(isDP()));
        xml.finishTagNewLine();
    }

    public void XmlTagReader(final XmlTag tag) {
        if ((!(pipe_tools.isApplet()))&&(tag.name().equals("Windowdim"))&&(tag.hasParam("w"))&&(tag.hasParam("h"))) {
            OwnerWindowWidth=Integer.parseInt(tag.getValue("w"));
            OwnerWindowHeight=Integer.parseInt(tag.getValue("h"));
        } else if (tag.name().equals("Preferences")) {
            if ((tag.hasParam("minfontsize"))) {
                Global.setParameter("minfontsize", tag.getValue("minfontsize"));
            }
            if ((tag.hasParam("minpointsize"))) {
                Global.setParameter("minpointsize", tag.getValue("minpointsize"));
            }
            if ((tag.hasParam("minlinesize"))) {
                Global.setParameter("minlinesize", tag.getValue("minlinesize"));
            }
            if ((tag.hasParam("arrowsize"))) {
                Global.setParameter("arrowsize", tag.getValue("arrowsize"));
            }
            if ((tag.hasParam("selectionsize"))) {
                Global.setParameter("selectionsize", tag.getValue("selectionsize"));
            }
            if ((tag.hasParam("monkeyspeed"))) {
                Global.setParameter("monkeyspeed", tag.getValue("monkeyspeed"));
            }
            if ((tag.hasParam("gridopacity"))) {
                Global.setParameter("gridopacity", tag.getValue("gridopacity"));
            }
            if ((tag.hasParam("digits.lengths"))) {
                Global.setParameter("digits.lengths", tag.getValue("digits.lengths"));
            }
            if ((tag.hasParam("digits.edit"))) {
                Global.setParameter("digits.edit", tag.getValue("digits.edit"));
            }
            if ((tag.hasParam("digits.angles"))) {
                Global.setParameter("digits.angles", tag.getValue("digits.angles"));
            }
            if ((tag.hasParam("colorbackground"))) {
                Global.setParameter("colorbackground", getColor(tag.getValue("colorbackground")));
            }
            if ((tag.hasParam("colorbackgroundx"))) {
                Global.setParameter("colorbackgroundx", tag.getValue("colorbackgroundx"));
            }
            if ((tag.hasParam("colorbackgroundy"))) {
                Global.setParameter("colorbackgroundy", tag.getValue("colorbackgroundy"));
            }
            if ((tag.hasParam("colorbackgroundPal"))) {
                Global.setParameter("colorbackgroundPal", tag.getValue("colorbackgroundPal"));
            }
            if ((tag.hasParam("fig3D"))) {
                set3D(Boolean.valueOf(tag.getValue("fig3D")).booleanValue());
            }
            if ((tag.hasParam("figDP"))) {
                setDP(Boolean.valueOf(tag.getValue("figDP")).booleanValue());
            }
            getLocalPreferences();
        }
    }

    // get a Color object from a string like "231,145,122"
    private Color getColor(final String s) {
        final StringParser p=new StringParser(s);
        p.replace(',', ' ');
        int red, green, blue;
        red=p.parseint();
        green=p.parseint();
        blue=p.parseint();
        return new Color(red, green, blue);
    }

    public void setMode(int i) {
        C.setMode(i);
    }

    public int getMode() {
        return C.getMode();
    }

    public boolean is3D() {
        return C.is3D();
    }

    public boolean isDP() {
        return C.isDP();
    }

    public void set3D(boolean b) {
        C.set3D(b);
    }

    public void setDP(boolean b) {
        C.setDP(b);
    }

    public void deleteAxisObjects() {
        C.deleteAxisObjects();
        reloadCD();
        repaintCD();
    }

    public void createAxisObjects() {
        C.createAxisObjects();
    }

    private void setShowHideParameters(final String s, final boolean b) {
        Global.setParameter("options.point."+s, b);
        Global.setParameter("options.segment."+s, b);
        Global.setParameter("options.line."+s, b);
        Global.setParameter("options.circle."+s, b);
        Global.setParameter("options.angle."+s, b);
        Global.setParameter("options.text."+s, b);
        Global.setParameter("options.locus."+s, b);
        Global.setParameter("options."+s, b);
        // setShowNames(true);
    }

    public void JSsend(final String s) {
        if (s.equals("shownames")) {
            setShowHideParameters("shownames", true);
        } else if (s.equals("hidenames")) {
            setShowHideParameters("shownames", false);
        } else if (s.equals("showvalues")) {
            setShowHideParameters("showvalues", true);
        } else if (s.equals("hidevalues")) {
            setShowHideParameters("showvalues", false);
        } else {
            try {
                C.interpret(this, s, "");
            } catch (final Exception ex) {
            }
        }
    }

    public String JSreceive(final String s) {
        String r="ERROR...";
        try {
            double rep;
            final Expression exp=new Expression(s, C, null);
            rep=exp.getValue();
            r=String.valueOf(rep);
        } catch (final ConstructionException ex) {
            ex.printStackTrace();
        }
        return r;
    }
    public Dimension UseSize=null;

    @Override
    public Dimension getMinimumSize() {
        if (Background==null||!Global.getParameter("background.usesize", false)) {
            if (UseSize!=null) {
                return UseSize;
            } else {
                return new Dimension(600, 600);
            }
        } else {
            final int iw=Background.getWidth(this);
            if (iw<10) {
                return new Dimension(600, 600);
            }
            final int ih=Background.getHeight(this);
            if (Global.getParameter("background.usewidth", false)) {
                final int w=getSize().width, h=(int) ((double) ih/iw*w+0.5);
                return new Dimension(w, h);
            } else {
                return new Dimension(iw, ih);
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return getMinimumSize();
    }

    public final void updateDigits() {
        EditFactor=Math.pow(10, Global.getParameter("digits.edit", 5));
        LengthsFactor=Math.pow(10, Global.getParameter("digits.lengths", 5));
        AnglesFactor=Math.pow(10, Global.getParameter("digits.angles", 0));
    }

    @Override
    public void itemStateChanged(final ItemEvent e) {
        if (e.getSource()==CheckboxHidden) {
            ShowHidden=CheckboxHidden.getState();
            repaint();
        }
    }
    ZirkelCanvasInterface ZCI;

    public void setZirkelCanvasListener(final ZirkelCanvasInterface zci) {
        ZCI=zci;
    }

    public String loadImage() {
        return ZCI.loadImage();
    }

    public Image doLoadImage(final String filename) {
        return ZCI.doLoadImage(filename);
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        if (!Interactive) {
            return;
        }
        if (e.getSource()==Replay) {
            if (ZCI!=null) {
                ZCI.replayChosen();
            }
        } else {
            final Enumeration en=Macros.elements();
            while (en.hasMoreElements()) {
                final MacroItem m=(MacroItem) en.nextElement();
                if (m.I==e.getSource()) {
                    if (ZCI!=null) {
                        ZCI.runMacro(m.M);
                    }
                    break;
                }
            }
        }
    }

    // Some transfer functions to compute screen coordinates etc.
    public double col(final double x) {
        return (x-Xmin)/DX*IW;
    }

    public double row(final double y) {
        return IH-(y-Ymin)/DY*IH;
    }

    public int width() {
        return IW;
    }

    public int height() {
        return IH;
    }

    public double x(final int c) {
        return Xmin+DX*c/IW;
    }

    public double y(final int r) {
        return Ymin+DY*(IH-r)/IH;
    }

    public double dx(final int c) {
        return DX*c/IW;
    }

    public double dy(final int r) {
        return DY*r/IH;
    }

    public double dx(final double c) {
        return DX*c/IW;
    }

    public double dy(final double r) {
        return DY*r/IH;
    }

    public double maxX() {
        return Xmin+DX;
    }

    public double minX() {
        return Xmin;
    }

    public double maxY() {
        return Ymin+DY;
    }

    public double minY() {
        return Ymin;
    }

    public boolean isInside(final double x, final double y) {
        return x>=Xmin&&x<Xmin+DX&&y>=Ymin&&y<Ymin+DY;
    }

    public double dCenter(final double x, final double y) {
        final double dx=x-(Xmin+DX/2), dy=y-(Ymin+DY/2);
        return Math.sqrt(dx*dx+dy*dy)/Math.max(DX/2, DY/2);
    }

    public void recompute() {
        if (IH<IW) {
            Xmin=C.getX()-C.getW();
            DX=C.getW()*2;
            DY=DX/IW*IH;
            Ymin=C.getY()-DY/2;
        } else {
            Ymin=C.getY()-C.getW();
            DY=C.getW()*2;
            DX=DY/IH*IW;
            Xmin=C.getX()-DY/2;
        }
        C.setH(DY);
        if (DX>0) {
            C.setPixel(getSize().width/DX);
        }
    }
    DoneListener DL;

    public void setDoneListener(final DoneListener dl) {
        DL=dl;
    }

    /**
     * Add an item to the construction and re-paint the construction.
     */
    public void addObject(final ConstructionObject o) { // called by the ObjectConstructor
	if(!Preview && !(o instanceof AreaObject))
	{
	    update_distant(o, 1);
	}
        C.add(o);
        if (Preview) {
            o.setIndicated(true);
            o.setSelectable(false); 
        }
        C.updateCircleDep();
    }

    /**
     * A construction added an item. Check, if it solves a problem and notify
     * the DoneListener, if so.
     */
    @Override
    public void added(final Construction c, final ConstructionObject o) {

        if (isPreview()) {
            return;
        }
        if (C.loading()) {
            return;
        }

        if (o instanceof UserFunctionObject) {
            JPropertiesBar.EditObject(o);
        }

    }
    int Moved=0;
    boolean Dragging=false, RightClicked=false;
    boolean Control=false; // Control-Taste bei letztem Mausdruck aktiv?
    public boolean SmartBoardPreview=false;
    private boolean MouseAllowed=true;

    public void setMouseAllowed(boolean ma) {
        MouseAllowed=ma;
    }

    // mouse events:
    @Override
    public synchronized void mousePressed(final MouseEvent e) {
        if (!Interactive) {
            return;
        }
        if (!MouseAllowed) {
            return;
        }
        if (JControlsManager.createControl(this, e)) {
            return;
        }

        SmartBoardPreview=false;
        clearIndicated();
        clearPreview();
        repaint();
        requestFocus();
        Dragging=false;
        RightClicked=false;
        Moved=0;
        if (e.isMetaDown()&&AllowRightMouse) // right mouse button!
        {
            if (!ReadOnly) {
                ConstructionObject o=selectLabel(e.getX(), e.getY());
                if (o!=null) {
                    Dragging=true;
                    setTool(new LabelMover(OC, this, e.getX(), e.getY(), o, e.isShiftDown()));
                    return;
                }
                if (e.isShiftDown()&&e.isControlDown()) // hiding shortcut
                {
                    o=selectObject(e.getX(), e.getY());
                    if (o==null) {
                        return;
                    }
                    o.toggleHidden();
                    C.updateCircleDep();
                    repaint();
                    return;
                }
                if (e.isControlDown()) // edit conditionals
                {
                    o=selectObject(e.getX(), e.getY());
                    if (o==null) {
                        return;
                    }
                    JPropertiesBar.EditObject(o, true, false);
                    JPropertiesBar.SelectPropertiesTab(2);
                    // new EditConditionals(getFrame(), o);
                    validate();
                    repaint();
                    return;
                }
            }
            final ConstructionObject p=selectImmediateMoveableObject(
                    e.getX(), e.getY());
            RightClicked=true;
            if (p!=null) {
                Dragging=true;
                setTool(new MetaMover(OC, this, p, e));
                return;
            } else if (!Global.getParameter("options.nomousezoom", false)) // Drag
            // mit
            // rechter
            // Maustaste
            {
                if (selectObjects(e.getX(), e.getY()).size()==0) {
                    setTool(new ZoomerTool(OC, e, this));
                }
                return;
            }
        } else // left mouse button!
        {
            if (!SmartBoardPreview&&Global.getParameter("smartboard", false)&&OC.useSmartBoard()) {
                OC.mouseMoved(e, this, Global.getParameter(
                        "options.indicate.simple", false));
                SmartBoardPreview=true;
                return;
            } else {
                Control=e.isControlDown();
                OC.mousePressed(e, this); // pass to ObjectConstructor
                Control=false;
            }
        }
    }

    @Override
    public synchronized void mouseReleased(final MouseEvent e) {
        if (!Interactive) {
            return;
        }
        if (DT!=null) {
            DT.waitReady();
        }
        if (RightClicked) {
            //RightClicked=false; // déplacé plus bas ligne 910, ainsi, lorsqu'on appelle select(), il vaut toujours true
            OC.mouseReleased(e, this);
            if (Moved<=2&&AllowRightMouse&&!ReadOnly) {
                final MyVector v=selectObjects(e.getX(), e.getY());
                if (v.size()>0) {
                    final ConstructionObject o=select(v, e.getX(), e.getY());
                    if (o!=null) {
                        new EditTool().mousePressed(e, o, this);
                        return;
                    } else {
                        repaintCD();
                    }
                    return;
                }
            }
            if (Moved<=2&&PM!=null&&!Global.getParameter("restricted", false)) {
                int n=2;
                if (ReadOnly||!Global.getParameter("options.doubleclick", false)) {
                    n=1;
                }
                if (e.getClickCount()>=n&&(ReadOnly||!Macros.isEmpty())) {
                    PM.show(e.getComponent(), e.getX(), e.getY());
                }
            }
            repaintCD();
	    RightClicked=false;
            return;
        }
        if (SmartBoardPreview&&Global.getParameter("smartboard", false)) {
            Control=e.isControlDown();
            clearIndicated();
            clearPreview();
            repaint();
            Dragging=false;
            OC.mousePressed(e, this);
            SmartBoardPreview=false;
            mouseReleased(e);
            return;
        }
        if (!Dragging) {
            OC.mouseReleased(e, this);
            Dragging=false;
            repaintCD();
            return;
        }
        if (Moved<=1) {
            if (OC instanceof LabelMover) {
                ((LabelMover) OC).resetPoint();
                OC.mouseReleased(e, this);
            } else if (OC instanceof MetaMover) {
                OC.mouseReleased(e, this);
                if (!ReadOnly) {
                    new EditTool().mousePressed(e, this);
                }
            }
        } else {
            OC.mouseReleased(e, this);
        }
        repaintCD();
        Dragging=false;
    }

    @Override
    public synchronized void mouseClicked(final MouseEvent e) {
    }

    @Override
    public synchronized void mouseExited(final MouseEvent e) {
        if (!Interactive) {
            return;
        }
        clearIndicated();
        clearPreview();
        repaint();
        SmartBoardPreview=false;
        RightClicked=false;
        repaintCD();
    }

    @Override
    public void mouseEntered(final MouseEvent e) {
    }

    @Override
    public synchronized void mouseMoved(final MouseEvent e) {
        if (!Interactive||!Global.getParameter("options.indicate", true)) {
            return;
        }
        if (Global.getParameter("smartboard", false)) {
            return;
        }
        Count.setAllAlternate(true);
        OC.mouseMoved(e, this, Global.getParameter("options.indicate.simple",
                false));
        Count.setAllAlternate(false);
        repaintCD();
    }
    DragThread DT=null;

    @Override
    public synchronized void mouseDragged(final MouseEvent e) {
        if (!Interactive) {
            return;
        }
        if (DT==null) {
            DT=new DragThread(this);
        }
        if (SmartBoardPreview&&Global.getParameter("smartboard", false)) {
            OC.mouseMoved(e, this, Global.getParameter(
                    "options.indicate.simple", false));
        } else {
            DT.mouseDragged(e);
            Moved++;
        }
        repaintCD();
    }

    public synchronized void doMouseDragged(final MouseEvent e) {
        OC.mouseDragged(e, this);
    }
    ConstructionObject LastPaint=null;

    public void newImage() {
        I=null;
        repaint();
    }
    boolean Frozen=false;

    public void setFrozen(final boolean f) {
        Frozen=f;
    }

    public boolean getFrozen() {
        return Frozen;
    }
    final double PointSizeFactor=240.0;
    int CC=0;

    // public void repaint(){
    // // System.out.print("a ");
    // // super.repaint();
    // StackTraceElement[] trace = new Throwable().getStackTrace();
    // if (!trace[1].equals("javax.swing.JComponent")) super.repaint();
    //
    // // for (int i=0;i<trace.length;i++){
    // // System.out.println(trace[i].getClassName());
    // //// if (trace[i].getClassName().startsWith("rene.zirkel.tools")){
    // //// String s=trace[i].getClassName();
    // //// CallerObject=s.split("\\.")[3];
    // //// break;
    // //// }
    // // };
    // // System.out.println("*******************");
    // }
    // public void paintComponent(Graphics g){
    // System.out.print("a ");
    // }
    // repaint events
    public boolean isInMultipleSelection(ConstructionObject o) {
        for (int i=0; i<MultipleSelection.size(); i++) {
            if (o==MultipleSelection.get(i)) {
                return true;
            }
        }
        return false;
    }

    public void validCurrentMultipleSelection() {
        for (int i=0; i<MultipleSelection.size(); i++) {
            MultipleSelection.get(i).setInMultipleSelection(true);
        }
        clearSelectionRectangle();
        repaint();
        editMultipleSelection();
    }

    public void clearMultipleSelection() {
        MultipleSelection.clear();
        final Enumeration e=C.elements();
        while (e.hasMoreElements()) {
            final ConstructionObject o=(ConstructionObject) e.nextElement();
            o.setSelected(false);
            o.setInMultipleSelection(false);
        }
    }

    public void addMultipleSelection(ConstructionObject o) {
        if (!isInMultipleSelection(o)) {
            MultipleSelection.add(o);
            o.setSelected(true);
        } else {
            MultipleSelection.remove(o);
            o.setSelected(false);
        }
    }

    public void selectInRect(int x, int y, int w, int h) {
        Rectangle r=new Rectangle(x, y, w, h);
        final Enumeration e=C.elements();
        while (e.hasMoreElements()) {
            final ConstructionObject o=(ConstructionObject) e.nextElement();
            if ((!o.isHidden())||(ShowHidden)) {
                if ((o.isInRect(r, this))&&(!isInMultipleSelection(o))) {
                    MultipleSelection.add(o);
                    o.setSelected(true);
                } else if ((!o.isInMultipleSelection())&&(!o.isInRect(r, this))&&(isInMultipleSelection(o))) {
                    MultipleSelection.remove(o);
                    o.setSelected(false);
                }
            }
        }
    }

    public void clearSelectionRectangle() {
        SelectionRectangle=null;
    }

    public void startSelectionRectangle(int x, int y) {
        SelectionRectangle=null;
        SelectionRectangle=new Rectangle(x, y, -1, -1);
        SelectionX=x;
        SelectionY=y;
    }

    public void actualiseSelectionRectangle(int x, int y) {
        if (SelectionRectangle!=null) {
            int x0=Math.min(SelectionX, x);
            int y0=Math.min(SelectionY, y);
            int w=Math.abs(x-SelectionX);
            int h=Math.abs(y-SelectionY);
            SelectionRectangle=null;
            SelectionRectangle=new Rectangle(x0, y0, w, h);
            repaint();
            selectInRect(x0, y0, w, h);
        }
    }

    public synchronized void paintSelectionRectangle(final MyGraphics IG) {
        if ((IG!=null)&&(SelectionRectangle!=null)) {
            Graphics2D g2d=(Graphics2D) IG.getGraphics();
            Rectangle r=SelectionRectangle;
            g2d.setColor(SelectionRectangleBackground);
            g2d.fillRect(r.x, r.y, r.width, r.height);
            g2d.setStroke(new BasicStroke(2f));
            g2d.setColor(SelectionRectangleBorder);
            g2d.drawRect(r.x, r.y, r.width, r.height);
        }
    }
    private boolean ShowCopyRectangle=false;

    public void keepCopyRectangleInside() {
        if (CopyRectangle!=null) {
            Rectangle r=new Rectangle(0, 0, IW, IH);
            CopyRectangle=CopyRectangle.intersection(r);
            if (CopyRectangle.width<40) {
                CopyRectangle.width=40;
            }
            if (CopyRectangle.height<40) {
                CopyRectangle.height=40;
            }
        }
    }

    public void showCopyRectangle() {
        if (CopyRectangle==null) {
            int w=IW*3/4;
            int h=IH*3/4;
            int x=(IW-w)/2;
            int y=(IH-h)/2;
            CopyRectangle=new Rectangle(x, y, w, h);
        } else {
            keepCopyRectangleInside();
        }

        ShowCopyRectangle=true;
        repaint();
    }

    public void hideCopyRectangle() {
        ShowCopyRectangle=false;
    }

    public Rectangle getCopyRectangle() {
        return CopyRectangle;
    }

    public void startCopyRectangle(int x, int y) {
        CopyRectangle=null;
        CopyRectangle=new Rectangle(x, y, 0, 0);
        CopyX=x;
        CopyY=y;
    }

    public void translateCopyRectangle(int x, int y) {
        if (CopyRectangle!=null) {
            CopyRectangle.translate(x, y);
            repaint();
        }
    }

    public void growCopyRectangle(int dw, int dh) {
        if (CopyRectangle!=null) {
            CopyRectangle.width+=dw;
            CopyRectangle.height+=dh;
            repaint();
        }
    }

    public void actualiseCopyRectangle(MouseEvent e) {
        int x=e.getX(), y=e.getY();

        if (CopyRectangle!=null) {
            int x0=Math.min(CopyX, x);
            int y0=Math.min(CopyY, y);
            int w=Math.abs(x-CopyX);
            int h=Math.abs(y-CopyY);
            if (e.isAltDown()) {
                w-=w%10;
                h-=h%10;
            }
            if (e.isShiftDown()) {
                h=w;
            }
            CopyRectangle=null;
            CopyRectangle=new Rectangle(x0, y0, w, h);
            repaint();
        }
    }

    public synchronized void paintCopyRectangle(final MyGraphics IG) {
        if ((ShowCopyRectangle)&&(IG!=null)) {
            Graphics2D g2d=(Graphics2D) IG.getGraphics();
            Rectangle r=CopyRectangle;
            g2d.setColor(CopyRectangleBackground);
            Area outside=new Area(new Rectangle2D.Double(0, 0, IW, IH));
            outside.subtract(new Area(r));
            g2d.setClip(outside);
            g2d.fillRect(0, 0, IW, IH);
            g2d.setClip(new Area(new Rectangle2D.Double(0, 0, IW, IH)));

            g2d.setStroke(new BasicStroke(1f));
            g2d.setColor(CopyRectangleBorder);

            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_OFF);
            g2d.drawRect(r.x, r.y, r.width, r.height);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            int w=3;
            int h=20;
            int rnd=6;

            g2d.fillRoundRect(r.width+r.x-w, r.y+r.height/2-h, 2*w, 2*h, rnd, rnd);
            g2d.fillRoundRect(r.width/2+r.x-h, r.y+r.height-w, 2*h, 2*w, rnd, rnd);
            g2d.fillRoundRect(r.x-w, r.y+r.height/2-h, 2*w, 2*h, rnd, rnd);
            g2d.fillRoundRect(r.width/2+r.x-h, r.y-w, 2*h, 2*w, rnd, rnd);
            w--;
            Path2D corner=new Path2D.Double();
            corner.moveTo(r.width+r.x-w, r.y+r.height-h);
            corner.lineTo(r.width+r.x-w, r.y+r.height-w);
            corner.lineTo(r.width+r.x-h, r.y+r.height-w);
            corner.lineTo(r.width+r.x-h, r.y+r.height+w);
            corner.lineTo(r.width+r.x+w, r.y+r.height+w);
            corner.lineTo(r.width+r.x+w, r.y+r.height-h);
            corner.closePath();
            g2d.fill(corner);
            w++;
            g2d.setColor(Color.white);
            g2d.drawRoundRect(r.width+r.x-w, r.y+r.height/2-h, 2*w, 2*h, rnd, rnd);
            g2d.drawRoundRect(r.width/2+r.x-h, r.y+r.height-w, 2*h, 2*w, rnd, rnd);
            g2d.drawRoundRect(r.x-w, r.y+r.height/2-h, 2*w, 2*h, rnd, rnd);
            g2d.drawRoundRect(r.width/2+r.x-h, r.y-w, 2*h, 2*w, rnd, rnd);
            g2d.draw(corner);
        }
    }

    public MyGraphics getMyGraphics() {
        return IG;
    }

    public synchronized void paintDisquePoincare(final MyGraphics IG) {
        if (isDP()) {

            Graphics2D g2d=(Graphics2D) IG.getGraphics();
            g2d.setColor(DPBackground);
            if (isEuclidian()) {
                g2d.fillRect(0, 0, getWidth(), getHeight());
            } else {
                int x1=(int) col(-4);
                int y1=(int) row(4);
                int w=(int) (2*(col(0)-x1));
                g2d.fillOval(x1, y1, w, w);
                if (!(g2d instanceof VectorGraphics2D)) {
                    g2d.setColor(Color.lightGray);
                    String s=Global.Loc("canvas.DP.message1");
                    Font ft=new Font(Global.GlobalFont, 0, 12);
                    g2d.setFont(ft);
                    FontMetrics fm=getFontMetrics(ft);
                    g2d.drawString(s, (IW-fm.stringWidth(s))/2, fm.getHeight());
                    s=Global.Loc("canvas.DP.message2");
                    g2d.drawString(s, (IW-fm.stringWidth(s))/2, 2*fm.getHeight());
                }
                g2d.setColor(DPBorder);
                g2d.setStroke(new BasicStroke(DPStroke*(float) C.getOne()));
                g2d.drawOval(x1, y1, w, w);
            }
        }
    }

    @Override
    public void setBounds(Rectangle r) {
//        double dwPercent=(1.0*r.width)/(1.0*getBounds().width);
//        double dwUnit=DX*(r.width-getBounds().width)/IW;

        super.setBounds(r);
////        double dwUnit=DX*dwPixel/IW;
//        if (!Double.isNaN(DX)) {
////            System.out.println(dwPixel);
//            C.setXYW(C.getX()+dwUnit/2, C.getY(), C.getW()*dwPercent);
//        }
////System.out.println("X="+C.getX()+ " W="+C.getW());
//System.out.println("X-W="+(C.getX()-C.getW()));
////        System.out.println("setBounds : "+dw+" - "+(DX*dw/IW));
    }

    @Override
    public synchronized void paint(final Graphics g) {

        if (g==null) {
            return;
        }
        if (DontPaint) {
            return;
        }
        if (Frozen) {
            g.drawImage(I, 0, 0, this);
            return;
        }

        final int w=getSize().width, h=getSize().height;
        if (I==null||IW!=w||IH!=h) {

            if (w==0||h==0) {
                return;
            }
            IW=w;
            IH=h;
            I=createImage(IW, IH);
            IG=new MainGraphics((Graphics2D) I.getGraphics(), this);
            IG.setSize(IW, IH);



//            MinPointSize=Global.getParameter("minpointsize", 3);
            // if (PointSize<MinPointSize) PointSize=MinPointSize;
//            MinFontSize=Global.getParameter("minfontsize", 12);
            // if (FontSize<MinFontSize) FontSize=MinFontSize;

//            PointSize=MinPointSize+0.4;
//            FontSize=MinFontSize;



            UniversalTrack.createTrackImage();
            recompute();
            C.dovalidate();
        }

        IG.clearRect(0, 0, IW, IH, getBackground());

        if (Background!=null) {
            final int bw=Background.getWidth(this), bh=Background.getHeight(this);
            if (bw==IW&&bh==IH) {
                IG.drawImage(Background, 0, 0, this);
            } else if (Global.getParameter("background.tile", true)&&bw<IW&&bh<IH) {
                for (int i=(IW%bw)/2-bw; i<IW; i+=bw) {
                    for (int j=(IH%bh)/2-bh; j<IH; j+=bh) {
                        IG.drawImage(Background, i, j, this);
                    }
                }
            } else if (Global.getParameter("background.center", true)) {
                IG.drawImage(Background, (IW-bw)/2, (IH-bh)/2, this);
            } else {
                IG.drawImage(Background, 0, 0, IW, IH, this);
            }
        }


        dopaint(IG);


//        JCM.paintControls();

        paintCalled=true;
        paintChildren(I.getGraphics());
        paintCalled=false;

//        DOCK.paintDock(I.getGraphics());
        g.drawImage(I, 0, 0, this);


        JIconMouseAdapter.paintTool();


    }

    public synchronized void resetGraphics() {
        I=null;
        repaint();

    }

    public void forceComputeHeavyObjects() {
        C.computeHeavyObjects(this, true);
    }
    MyVector Breaks=new MyVector();

    public void updateBreakHide() {
        Breaks.removeAllElements();
        Enumeration e=C.elements();
        while (e.hasMoreElements()) {
            final ConstructionObject o=(ConstructionObject) e.nextElement();
            if (o==LastPaint||o==C.lastButN(0)) {
                break;
            }
            if (o.isBreak()) {
                Breaks.addElement(o);
            }
        }
        e=C.elements();
        ConstructionObject NextBreak=null;
        final Enumeration eb=Breaks.elements();
        if (eb.hasMoreElements()) {
            NextBreak=(ConstructionObject) eb.nextElement();
        }
        while (e.hasMoreElements()) {
            final ConstructionObject o=(ConstructionObject) e.nextElement();
            if (NextBreak!=null&&NextBreak.isHideBreak()) {
                o.setBreakHide(true);
            } else {
                o.setBreakHide(false);
            }
            if (o==NextBreak) {
                if (eb.hasMoreElements()) {
                    NextBreak=(ConstructionObject) eb.nextElement();
                } else {
                    NextBreak=null;
                }
            }
            if (o==LastPaint) {
                break;
            }
        }
    }

    public void dopaint(final MyGraphics IG) {

        paintDisquePoincare(IG);
        C.computeHeavyObjects(this, false);
        UniversalTrack.draw();

        if (getAxis_show()) {
            if (Global.getParameter("axis_with_grid", true)) {
                paintGrid(IG);
            } else {
                paintAxes(IG);
            }
        }
//        long time=System.currentTimeMillis();
        updateBreakHide();
        // count z-buffer elements and mark all as non-painted
        Enumeration e=C.elements();
        int n=0;
        while (e.hasMoreElements()) {
            final ConstructionObject o=(ConstructionObject) e.nextElement();
            o.HasZ=false;
            o.IsDrawn=false;
            try {
                if (!o.selected()) {
                    o.Value=-o.getZ();
                    o.HasZ=true;
                    n++;
                }
            } catch (final Exception ex) {
            }
            if (o==LastPaint) {
                break;
            }
        }
        // paint background objects
        e=C.elements();
        while (e.hasMoreElements()) {
            final ConstructionObject o=(ConstructionObject) e.nextElement();
            if (o.isBack()&&!o.HasZ) {
                o.paint(IG, this);
                o.IsDrawn=true;
            }
            if (o==LastPaint) {
                break;
            }
        }
        // paint objects with z-buffer value in their order
        if (n>0) {
            final ConstructionObject os[]=new ConstructionObject[n];
            e=C.elements();
            n=0;
            while (e.hasMoreElements()) {
                final ConstructionObject o=(ConstructionObject) e.nextElement();
                if (o.HasZ) {
                    os[n++]=o;
                }
            }
            Sorter.sort(os);
            for (int i=0; i<n; i++) {
                os[i].paint(IG, this);
                os[i].IsDrawn=true;
            }
        }
        // paint non-selected objects
        e=C.elements();
        final Vector Pts=new Vector();
        while (e.hasMoreElements()) {
            final ConstructionObject o=(ConstructionObject) e.nextElement();
            if (!o.selected()&&!o.IsDrawn) {
                if (o instanceof rene.zirkel.objects.PointObject) {
                    Pts.add(o);
                } else {
                    o.paint(IG, this);
                    o.IsDrawn=true;
                }
            }
            if (o==LastPaint) {
                break;
            }
        }
        // paint selected objects
        e=C.elements();
        while (e.hasMoreElements()) {
            final ConstructionObject o=(ConstructionObject) e.nextElement();
            if (o.selected()&&!o.IsDrawn) {
                if (o instanceof rene.zirkel.objects.PointObject) {
                    Pts.add(o);
                } else {
                    o.paint(IG, this);
                    o.IsDrawn=true;
                }
            }
            if (o==LastPaint) {
                break;
            }
        }
        e=Pts.elements();
        while (e.hasMoreElements()) {
            final ConstructionObject o=(ConstructionObject) e.nextElement();
            o.paint(IG, this);
            o.IsDrawn=true;
        }

        if (LastPaint==null) {
            paintTrack(IG);
        }
        if (Interactive&&IndicatePoint!=null) {
            IndicatePoint.paint(IG, this);
        }
//        time=System.currentTimeMillis()-time;
//        if (time>250&&PolygonDrawer.step<50) {
//            PolygonDrawer.step=PolygonDrawer.step*2;
//        }
//        if (time<100&&PolygonDrawer.step>4) {
//            PolygonDrawer.step=PolygonDrawer.step/2;
//        }
        paintDrawings(IG);
        paintSelectionRectangle(IG);
        paintCopyRectangle(IG);
//         JMacrosTools.CurrentJZF.JCM.paintControls(IG.getGraphics());
    }
    double xg[]=new double[64], yg[]=new double[64];

    /**
     * Paint axes and grid.
     *
     * @param IG
     */
    void paintGrid(final MyGraphics IG) {

        int GridThickness=Global.getParameter("axis_thickness", 0);
        int GridColor=Global.getParameter("axis_color", 0);
        boolean GridLabels=Global.getParameter("axis_labels", false);
        boolean GridBold=Global.getParameter("grid.bold", false);
        boolean GridLarge=Global.getParameter("grid.large", false);

        if (GridThickness==ConstructionObject.THIN) {
            IG.setColor(ZirkelFrame.LightColors[GridColor]);
        } else {
            IG.setColor(ZirkelFrame.Colors[GridColor]);
        }
        IG.setFont(GridLarge, GridBold);
        final double gridsize=getGridSize();
//        int dd=IH/200;
//        if (dd<1) {
//            dd=1;
//        }

        double dd=3*C.getOne();


        double x1=Math.floor((C.getX()-C.getW())/gridsize-1)*gridsize;
//        System.out.println("x1="+x1);
        int xi=0;
        while (x1<(C.getX()+C.getW())&&xi<64) {
            xg[xi++]=col(x1);
            x1+=gridsize;
        }
        double y1=Math.floor((C.getY()-C.getH())/gridsize-1)*gridsize;
//System.out.println("y1="+y1);
        int yi=0;
        while (y1<(C.getY()+C.getH())&&yi<64) {
            yg[yi++]=row(y1);
            y1+=gridsize;
        }

        for (int i=0; i<xi; i++) {
            IG.drawAxisLine(xg[i], 0, xg[i], IH, (float) (0.01f*gridopacity));
        }
        for (int j=0; j<yi; j++) {
            IG.drawAxisLine(0, yg[j], IW, yg[j], (float) (0.01f*gridopacity));
        }

        // for (int i=0; i<xi; i++) {
        // for (int j=0; j<yi; j++) {
        // IG.drawLine(xg[i],yg[j]-dd,xg[i],yg[j]+dd);
        // IG.drawLine(xg[i]-dd,yg[j],xg[i]+dd,yg[j]);
        // }
        // }

        if (GridThickness!=ConstructionObject.INVISIBLE) {
            if (0<C.getX()+C.getW()&&0>C.getX()-C.getW()) {
                double col=col(0);
                if (GridThickness!=ConstructionObject.THICK) {
                    IG.drawLine(col, 0, col, IH);
                } else {
                    IG.drawThickLine(col, 0, col, IH);
                }
            }
            if (0<C.getY()+C.getW()&&0>C.getY()-C.getW()) {
                double r=row(0);
                if (GridThickness!=ConstructionObject.THICK) {
                    IG.drawLine(0, r, IW, r);
                } else {
                    IG.drawThickLine(0, r, IW, r);
                }
            }
        }
        dd=dd*2;
        double labelsize=Math.pow(10, Math.floor(Math.log(C.getW()*2)/Math.log(10)))/10;
        while (C.getW()*2/labelsize>=10) {
            labelsize*=10;
        }
        if (C.getW()*2/labelsize<5) {
            labelsize/=2;
        }
        final FontMetrics fm=IG.getFontMetrics();
        final int FH=fm.getHeight();
        x1=Math.floor((C.getX()-C.getW())/labelsize-1)*labelsize;
        double lh=row(0);
        double bottomshift=FH+2*C.getOne();
        if (lh<0||lh>IH) {
            lh=IH;
            bottomshift=-7*C.getOne();
        }

        while (x1<(C.getX()+C.getW())) {
            double col=col(x1);
            final String s=format(x1);
            if (s.length()>0) {

                if (GridLabels) {
                    IG.drawString(s, col-fm.stringWidth(s)/2, lh+bottomshift);
                }
                IG.drawLine(col, lh-dd, col, lh+dd);
            }
            x1+=labelsize;
        }

        boolean left=true;
        double lw=col(0);
        if (lw<0||lw>IW-20) {
            lw=0;
            left=false;
        }

        y1=Math.floor((C.getY()-C.getW())/labelsize-1)*labelsize;
        while (y1<(C.getY()+C.getW())) {
            double r=row(y1);
            final String s=format(y1);
            if (s.length()>0) {
                double leftshift=(left)?-fm.stringWidth(s)-10*C.getOne():8*C.getOne();
                if (GridLabels) {
                    IG.drawString(s, lw+leftshift, r+(FH/2-2)*C.getOne());
                }
                IG.drawLine(lw-dd, r, lw+dd, r);
            }
            y1+=labelsize;
        }
    }

    public boolean getAxis_show() {
        return Global.getParameter("axis_show", false);
    }

    public void setAxis_show(boolean b) {
        axis_show=b;
        Global.setParameter("axis_show", b);
    }

    public void setAxis_labels(boolean b) {
        axis_labels=b;
        Global.setParameter("axis_labels", b);
    }

    public void setAxis_with_grid(boolean b) {
        axis_with_grid=b;
        Global.setParameter("axis_with_grid", b);
    }

    public void setAxis_color(int i) {
        axis_color=i;
        Global.setParameter("axis_color", i);
    }

    public void setAxis_thickness(int i) {
        axis_thickness=i;
        Global.setParameter("axis_thickness", i);
    }

    /**
     * Paint only the coordinate axes (no grid)
     *
     * @param IG
     */
    void paintAxes(final MyGraphics IG) {
//        System.out.println("paintAxes");
//        IG.setAntialiasing(false);

        int GridThickness=Global.getParameter("axis_thickness", 0);
        int GridColor=Global.getParameter("axis_color", 0);
        boolean GridLabels=Global.getParameter("axis_labels", false);
        boolean GridBold=Global.getParameter("grid.bold", false);
        boolean GridLarge=Global.getParameter("grid.large", false);

        if (GridThickness==ConstructionObject.THIN) {
            IG.setColor(ZirkelFrame.LightColors[GridColor]);
        } else {
            IG.setColor(ZirkelFrame.Colors[GridColor]);
        }
        IG.setFont(GridLarge, GridBold);
        final double gridsize=getGridSize();
        double x1=Math.floor((C.getX()-C.getW())/gridsize-1)*gridsize;
        double r0=row(0);
//        int dd=IH/200;
//        if (dd<1) {
//            dd=1;
//        }

        double dd=3*C.getOne();


        while (x1<(C.getX()+C.getW())) {
            double col=col(x1);
            IG.drawLine(col, r0-dd, col, r0+dd);
            x1+=gridsize;
        }
        double y1=Math.floor((C.getY()-C.getW())/gridsize-1)*gridsize;
        double c0=col(0);
        while (y1<(C.getY()+C.getW())) {
            double r=row(y1);
            IG.drawLine(c0-dd, r, c0+dd, r);
            y1+=gridsize;
        }




        if (GridThickness!=ConstructionObject.INVISIBLE) {
            if (0<C.getX()+C.getW()&&0>C.getX()-C.getW()) {
                double col=col(0);
                if (GridThickness!=ConstructionObject.THICK) {
                    IG.drawLine(col, 0, col, IH);
                } else {
                    IG.drawThickLine(col, 0, col, IH);
                }
            }
            if (0<C.getY()+C.getW()&&0>C.getY()-C.getW()) {
                double r=row(0);
                if (GridThickness!=ConstructionObject.THICK) {
                    IG.drawLine(0, r, IW, r);
                } else {
                    IG.drawThickLine(0, r, IW, r);
                }
            }
        }



        dd=dd*2;
        double labelsize=Math.pow(10, Math.floor(Math.log(C.getW()*2)/Math.log(10)))/10;
        while (C.getW()*2/labelsize>=10) {
            labelsize*=10;
        }
        if (C.getW()*2/labelsize<5) {
            labelsize/=2;
        }
        final FontMetrics fm=IG.getFontMetrics();
        final int FH=fm.getHeight();

        x1=Math.floor((C.getX()-C.getW())/labelsize-1)*labelsize;
        double lh=row(0);
        double bottomshift=FH+2*C.getOne();
        if (lh<0||lh>IH) {
            lh=IH;
            bottomshift=-7*C.getOne();
        }

        while (x1<(C.getX()+C.getW())) {
            double col=col(x1);
            final String s=format(x1);
            if (s.length()>0) {
                if (GridLabels) {
                    IG.drawString(s, col-fm.stringWidth(s)/2, lh+bottomshift);
                }
                IG.drawLine(col, lh-dd, col, lh+dd);
            }
            x1+=labelsize;
        }

        boolean left=true;
        double lw=col(0);
        if (lw<0||lw>IW-20) {
            lw=0;
            left=false;
        }

        y1=Math.floor((C.getY()-C.getW())/labelsize-1)*labelsize;
        while (y1<(C.getY()+C.getW())) {
            double r=row(y1);
            final String s=format(y1);
            if (s.length()>0) {
                double leftshift=(left)?-fm.stringWidth(s)-10*C.getOne():8*C.getOne();
                if (GridLabels) {
                    IG.drawString(s, lw+leftshift, r+FH/2-2*C.getOne());
                }
                IG.drawLine(lw-dd, r, lw+dd, r);
            }
            y1+=labelsize;
        }

//        IG.setAntialiasing(true);
    }

    public double pointSize() {
        return minpointsize;
    }

    public double fontSize() {
        return minfontsize;
    }

    public double lineSize() {
        return minlinesize;
    }
//    public double SelectionPointFactor=Global.getParameter("selectionsize",
//            1.5);
    public double SelectionPointFactor=3;

//    public double selectionSize() {
//        return SelectionPointFactor*PointSize;
//    }
    public double selectionSize() {
        return selectionsize;
    }

    public double monkeySpeed() {
        return monkeyspeed;
    }
    static char c[]=new char[20];
    int nc;

    public String format(double x) { // double xx=x;
        nc=0;
        boolean minus=false;
        if (x<-1e-12) {
            minus=true;
            x=-x;
        }
        x+=1e-12;
        final double h=x-Math.floor(x);
        if (rekformat(h, 8)) {
            c[nc++]='.';
        }
        while (x>=1) {
            final double s=Math.floor(x/10);
            c[nc++]=(char) ('0'+(int) (x-s*10));
            x=s;
        }
        if (nc>0&&minus) {
            c[nc++]='-';
        }
        // revert c:
        for (int i=0; i<nc/2; i++) {
            final char hc=c[nc-1-i];
            c[nc-1-i]=c[i];
            c[i]=hc;
        }
        // System.out.println(xx+" -> "+new String(c,0,nc));
        return new String(c, 0, nc);
    }

    boolean rekformat(double h, final int k) {
        h=h*10;
        final double x=Math.floor(h);
        if (k==0) {
            final int i=(int) x;
            if (i>0) {
                c[nc++]=(char) ('0'+i);
                return true;
            } else {
                return false;
            }
        } else {
            final int i=(int) x;
            if (rekformat(h-x, k-1)||i>0) {
                c[nc++]=(char) ('0'+i);
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public void update(final Graphics g) {
        paint(g);
    }

    public void paintUntil(final ConstructionObject o) {
        if (LastPaint==o) {
            return;
        }
        LastPaint=o;
        repaint();
    }

    // validate all elements
    @Override
    public void validate() {
        dovalidate();
        if (OC instanceof TrackPainter) {
            ((TrackPainter) OC).validate(this);
        }
    }

    /**
     * Synchronized update of the construction to avoid a repaint during update.
     */
    synchronized public void dovalidate() {
        C.dovalidate();
    }
    // selection routines:
    // This vector is used by ALL selection and indication routines for
    // performance reasons.
    MyVector V=new MyVector();

    public void sort(final MyVector V) {
        if (V.size()<2) {
            return;
        }
        Sorter.QuickSort(V.getArray(), 0, V.size()-1);
    }

    /**
     * Slow function to resort a vector by the number of each element in the
     * construction.
     */
    public void sortRow(final MyVector V) {
        final ConstructionObject o[]=new ConstructionObject[V.size()];
        V.copyInto(o);
        V.removeAllElements();
        final Enumeration e=C.elements();
        while (e.hasMoreElements()) {
            final ConstructionObject oo=(ConstructionObject) e.nextElement();
            for (final ConstructionObject element : o) {
                if (element==oo) {
                    V.addElement(oo);
                    break;
                }
            }
        }
    }

    /**
     * select a circle or a line.
     **/
    public ConstructionObject selectCircleLine(final int x, final int y,
            final boolean multiple) // select a circle or a line
    {
        final MyVector v=selectCircleLineObjects(x, y, multiple, false);
        return select(v);
    }

    public ConstructionObject selectCircleLine(final int x, final int y) // select
    // a
    // circle
    // or
    // a
    // line
    {
        return selectCircleLine(x, y, true);
    }

    /**
     * select a circle or a line.
     **/
    public ConstructionObject selectPointonObject(final int x, final int y,
            final boolean multiple) // select a circle or a line
    {
        final MyVector v=selectPointonObjects(x, y, multiple, false);
        return select(v);
    }

    public ConstructionObject selectPointonObject(final int x, final int y) // select
    // a
    // circle
    // or
    // a
    // line
    {
        return selectPointonObject(x, y, true);
    }

    /**
     * Select all possible circles or lines at x,y. If a matching non-filled
     * object is found above a filled object, the latter is never selected.
     *
     * @param multiple
     *            determines, if it is possible to select selected objects.
     * @param testlocal
     *            determines, if objects that look locally like already selected
     *            objects should be omitted.
     */
    public MyVector selectCircleLineObjects(final int x, final int y,
            final boolean multiple, final boolean testlocal) // select a circle
    // or a line
    {
        final Enumeration e=C.elements();
        V.removeAllElements();
        while (e.hasMoreElements()) {
            ConstructionObject o=(ConstructionObject) e.nextElement();
            if (o.isSelectable()&&(o instanceof PrimitiveLineObject||o instanceof PrimitiveCircleObject)&&o.nearto(x, y, true, this)&&(multiple||!o.selected())) {
                if (testlocal) {
                    final Enumeration ev=V.elements();
                    while (ev.hasMoreElements()) {
                        final ConstructionObject ov=(ConstructionObject) ev.nextElement();
                        if (o.locallyLike(ov)) {
                            o=null;
                            break;
                        }
                    }
                }
                if (o!=null) {
                    V.addElement(o);
                }
            }
        }
        return V;
    }

    public MyVector selectCircleLineObjects(final int x, final int y,
            final boolean multiple) {
        return selectCircleLineObjects(x, y, multiple, false);
    }

    public MyVector selectCircleLineObjects(final int x, final int y) {
        return selectCircleLineObjects(x, y, true, false);
    }

    /**
     * Select all possible object at x,y that can carry a point.
     *
     * @param multiple
     *            determines, if it is possible to select selected objects.
     * @param testlocal
     *            determines, if objects that look locally like already selected
     *            objects should be omitted.
     */
    public MyVector selectPointonObjects(final int x, final int y,
            final boolean multiple, final boolean testlocal) // select a circle
    // or a line
    {

        final Enumeration e=C.elements();
        V.removeAllElements();
        while (e.hasMoreElements()) {
            ConstructionObject o=(ConstructionObject) e.nextElement();
            if (o.isSelectable()&&(o instanceof PointonObject)&&o.nearto(x, y, true, this)&&(multiple||!o.selected())) {
                if (testlocal) {
                    final Enumeration ev=V.elements();
                    while (ev.hasMoreElements()) {
                        final ConstructionObject ov=(ConstructionObject) ev.nextElement();
                        if (o.locallyLike(ov)) {
                            o=null;
                            break;
                        }
                    }
                }
                if (o!=null) {
                    V.addElement(o);
                }
            }
        }
        return V;
    }

    public MyVector selectPointonObjects(final int x, final int y,
            final boolean multiple) {
        return selectPointonObjects(x, y, multiple, false);
    }

    public MyVector selectPointonObjects(final int x, final int y) {
        return selectPointonObjects(x, y, true, false);
    }

    /**
     * Select all selectable objects near to x,y.
     *
     * @param multiple
     *            allows multiple selections.
     **/
    public MyVector selectObjects(final int x, final int y,
            final boolean multiple) {
        final Enumeration e=C.elements();
        V.removeAllElements();
        while (e.hasMoreElements()) {
            final ConstructionObject o=(ConstructionObject) e.nextElement();
            if (o.isSelectable()&&o.nearto(x, y, this)&&(multiple||!o.selected())) {
                V.addElement(o);
            }
        }
        return V;
    }

    public MyVector selectObjects(final int x, final int y) {
        return selectObjects(x, y, true);
    }

    /**
     * Select a single object near x,y.
     **/
    public ConstructionObject selectObject(final int x, final int y,
            final boolean multiple) {
        final MyVector v=selectObjects(x, y, multiple);
        return select(v, x, y);
    }

    public ConstructionObject selectObject(final int x, final int y) {
        return selectObject(x, y, true);
    }

    /**
     * select all constructable objects near x,y.
     **/
    public MyVector selectConstructableObjects(final int x, final int y,
            final boolean multiple) // select an enumeration of objects
    {
        final Enumeration e=C.elements();
        V.removeAllElements();
        while (e.hasMoreElements()) {
            final ConstructionObject o=(ConstructionObject) e.nextElement();
            if (o.valid()&&o.isFlag()&&o.nearto(x, y, this)&&(multiple||!o.selected())) {
                V.addElement(o);
            }
        }
        return V;
    }

    public MyVector selectConstructableObjects(final int x, final int y) {
        return selectConstructableObjects(x, y, true);
    }

    /**
     * select a single constructable object near x,y.
     **/
    public ConstructionObject selectConstructableObject(final int x, final int y) {
        final MyVector v=selectConstructableObjects(x, y, true);
        return select(v, x, y); // user determines
    }

    /**
     * Select a single line, segment, ray, perp., paral. or fixed angle near
     * x,y.
     **/
    public PrimitiveLineObject selectLine(final int x, final int y, final boolean multiple) {
        selectLineObjects(x, y, multiple);
        return (PrimitiveLineObject) select(V);
    }

    public void selectLineObjects(final int x, final int y, final boolean multiple) {
        final Enumeration e=C.elements();
        V.removeAllElements();
        while (e.hasMoreElements()) {
            final ConstructionObject o=(ConstructionObject) e.nextElement();
            if (o.isSelectable()&&(o instanceof PrimitiveLineObject)&&o.nearto(x, y, this)&&(multiple||!o.selected())) {
                V.addElement(o);
            }
        }
    }

    public PrimitiveLineObject selectLine(final int x, final int y) {
        return selectLine(x, y, true);
    }

    /**
     * Select a point or a line (for the object tracker).
     */
    public void selectPointsOrLines(final int x, final int y,
            final boolean multiple) {
        final Enumeration e=C.elements();
        V.removeAllElements();
        while (e.hasMoreElements()) {
            final ConstructionObject o=(ConstructionObject) e.nextElement();
            if (o.isSelectable()&&(o instanceof PrimitiveLineObject||o instanceof PointObject)&&o.nearto(x, y, this)&&(multiple||!o.selected())) {
                V.addElement(o);
            }
        }
    }

    /**
     * Select a single line, segment, or ray near x,y
     **/
    public TwoPointLineObject selectTwoPointLine(final int x, final int y,
            final boolean multiple) {
        selectTwoPointLines(x, y, multiple);
        return (TwoPointLineObject) select(V);
    }

    public void selectTwoPointLines(final int x, final int y,
            final boolean multiple) {
        final Enumeration e=C.elements();
        V.removeAllElements();
        while (e.hasMoreElements()) {
            final ConstructionObject o=(ConstructionObject) e.nextElement();
            if (o.isSelectable()&&(o instanceof TwoPointLineObject)&&o.nearto(x, y, this)&&(multiple||!o.selected())) {
                V.addElement(o);
            }
        }
    }

    public TwoPointLineObject selectTwoPointLine(final int x, final int y) {
        return selectTwoPointLine(x, y, true);
    }

    /**
     * Select a single segment near x,y
     **/
    public SegmentObject selectSegment(final int x, final int y,
            final boolean multiple) {
        selectSegments(x, y, multiple);
        return (SegmentObject) select(V);
    }

    public void selectSegments(final int x, final int y, final boolean multiple) {
        final Enumeration e=C.elements();
        V.removeAllElements();
        while (e.hasMoreElements()) {
            final ConstructionObject o=(ConstructionObject) e.nextElement();
            if (o.isSelectable()&&(o instanceof SegmentObject)&&o.nearto(x, y, this)&&(multiple||!o.selected())) {
                V.addElement(o);
            }
        }
    }

    public SegmentObject selectSegment(final int x, final int y) {
        return selectSegment(x, y, true);
    }

    public boolean isMultipleAcceptedObject(final ConstructionObject o) {
        boolean b=o instanceof AreaObject;
        b=b||o instanceof PrimitiveLineObject;
        b=b||o instanceof PrimitiveCircleObject;
        b=b||o instanceof QuadricObject;
        b=b||o instanceof JLocusTrackObject;
        return b;
    }

    public void selectMultipleFinals(final int x, final int y,
            final boolean multiple) {
        final Enumeration e=C.elements();
        V.removeAllElements();
        while (e.hasMoreElements()) {
            final ConstructionObject o=(ConstructionObject) e.nextElement();
            if (o.isSelectable()&&(o instanceof PointObject)&&o.nearto(x, y, this)&&(multiple||!o.selected())) {
                V.removeAllElements();
                return;
            }
            if (o.isSelectable()&&(isMultipleAcceptedObject(o))&&o.nearto(x, y, this)&&(multiple||!o.selected())) {
                V.addElement(o);
            }
        }
        if (V.size()>1) {
            V.removeAllElements();
        }
    }

    public ConstructionObject selectMultipleFinal(final int x, final int y,
            final boolean multiple) {
        selectMultipleFinals(x, y, multiple);
        return (ConstructionObject) select(V);
    }

    /**
     * Select a single ray near x,y
     **/
    public RayObject selectRay(final int x, final int y, final boolean multiple) {
        selectRays(x, y, multiple);
        return (RayObject) select(V);
    }

    public void selectRays(final int x, final int y, final boolean multiple) {
        final Enumeration e=C.elements();
        V.removeAllElements();
        while (e.hasMoreElements()) {
            final ConstructionObject o=(ConstructionObject) e.nextElement();
            if (o.isSelectable()&&(o instanceof RayObject)&&o.nearto(x, y, this)&&(multiple||!o.selected())) {
                V.addElement(o);
            }
        }
    }

    public RayObject selectRay(final int x, final int y) {
        return selectRay(x, y, true);
    }

    /**
     * Select a circle near x,y. A non-filled object is preferred before a
     * filled object.
     **/
    public PrimitiveCircleObject selectCircle(final int x, final int y,
            final boolean multiple) {
        selectCircles(x, y, multiple);
        return (PrimitiveCircleObject) select(V);
    }

    public void selectCircles(final int x, final int y, final boolean multiple) {
        Enumeration e=C.elements();
        V.removeAllElements();
        boolean haveNotFilled=false;
        while (e.hasMoreElements()) {
            final ConstructionObject o=(ConstructionObject) e.nextElement();
            if (o.isSelectable()&&(o instanceof PrimitiveCircleObject)&&o.nearto(x, y, this)&&(multiple||!o.selected())) {
                V.addElement(o);
                if (!o.isFilledForSelect()) {
                    haveNotFilled=true;
                }
            }
        }
        if (haveNotFilled) {
            e=V.elements();
            while (e.hasMoreElements()) {
                final ConstructionObject o=(ConstructionObject) e.nextElement();
                if (o.isFilledForSelect()) {
                    V.removeElement(o);
                }
            }
        }
    }

    public PrimitiveCircleObject selectCircle(final int x, final int y) {
        return selectCircle(x, y, true);
    }

    public ConstructionObject selectAnimationObject(final int x, final int y) {
        final Enumeration e=C.elements();
        V.removeAllElements();
        while (e.hasMoreElements()) {
            final ConstructionObject o=(ConstructionObject) e.nextElement();
            if (o.isSelectable()&&o.nearto(x, y, this)&&(o instanceof PointObject)) {
                PointObject pt=(PointObject) o;
                if (pt.isPointOn()) {
                    V.addElement(o);
                }
            } else if (o.isSelectable()&&o.nearto(x, y, this)&&(o instanceof ExpressionObject)) {
                V.addElement(o);
            }
        }
        return select(V);
    }

    /**
     * Select a point near x,y.
     **/
    public PointObject selectPoint(final int x, final int y,
            final boolean multiple) {
        selectPointObjects(x, y, multiple);
        return (PointObject) select(V);
    }

    public void selectPointObjects(final int x, final int y,
            final boolean multiple) {
        final Enumeration e=C.elements();
        V.removeAllElements();
        while (e.hasMoreElements()) {
            final ConstructionObject o=(ConstructionObject) e.nextElement();
            if (o.isSelectable()&&o instanceof PointObject&&o.nearto(x, y, this)&&(multiple||!o.selected())) {
                V.addElement(o);
            }
        }
    }

    public PointObject selectPoint(final int x, final int y) {
        return selectPoint(x, y, true);
    }

    public PointObject selectPoint(final int x, final int y,
            final boolean multiple, final ConstructionObject until) {
        selectPointObjects(x, y, multiple, until);
        return (PointObject) select(V);
    }

    public void selectPointObjects(final int x, final int y,
            final boolean multiple, final ConstructionObject until) {
        final Enumeration e=C.elements();
        V.removeAllElements();
        while (e.hasMoreElements()) {
            final ConstructionObject o=(ConstructionObject) e.nextElement();
            if (o==until) {
                break;
            }
            if (o.isSelectable()&&o instanceof PointObject&&o.nearto(x, y, this)&&(multiple||!o.selected())) {
                V.addElement(o);
            }
        }
    }

    public PointObject selectPoint(final int x, final int y,
            final ConstructionObject until) {
        return selectPoint(x, y, true, until);
    }
    boolean NewPoint=false;

    public boolean isNewPoint() {
        return NewPoint;
    }

    /**
     * Select a point, and create a new point, if necessary. Even creates an
     * intersection, or a point bound to an object, if possible. If enabled, the
     * user is asked for confirmation in these cases. The variable NewPoint is
     * set to true, if the point was indeed created.
     *
     * @param multiple
     *            determines, if multiple selections are possible.
     * @param any
     *            determines, if the first point should be used.
     **/
    public PointObject selectCreatePoint(final int x, final int y,
            final boolean multiple, final boolean any, boolean altdown) {

        if ((isDP())&&(!isEuclidian())&&(!PaletteManager.isSelected("bi_distance"))) {
//        if ((isDP())) {
            if (C!=null) {
                double xx=x(x), yy=y(y);
                PrimitiveCircleObject Hz=(PrimitiveCircleObject) C.find("Hz");
                if (Math.sqrt(xx*xx+yy*yy)>Hz.getR()) {
                    return null;
                }
            }
        }
        NewPoint=false;
        if (Preview&&!is3D()) { // modified by Dibs for 3D 	
        	final PointObject p=new PointObject(C, x(x), y(y));
        	addObject(p);
        	p.setSuperHidden(true);
        	PreviewObject=p;
        	return p;
        }
        if (Preview&&(PaletteManager.isSelected("segment3D")||PaletteManager.isSelected("line3D")||PaletteManager.isSelected("ray3D")||PaletteManager.isSelected("midpoint3D")||PaletteManager.isSelected("vector3D"))) {
        	final PointObject p=new PointObject(C, x(x), y(y));
                p.setIs3D(true);
                p.setXYZ(NaN,NaN,NaN);
        	addObject(p);
        	p.setSuperHidden(true);
        	PreviewObject=p;
        	return p;
        }
        // User selects a known point:
        final Enumeration e=C.elements();
        V.removeAllElements();
        while (e.hasMoreElements()) {
            final ConstructionObject o=(ConstructionObject) e.nextElement();
            if (o.isSelectable()&&o instanceof PointObject&&o.nearto(x, y, this)&&(multiple||!o.selected())) {
                V.addElement(o);
            }
            sort(V);
        }
        if (V.size()>0) {
            if (any) {
                return (PointObject) (V.elementAt(0));
            }
            final ConstructionObject o=select(V, Control||!Global.getParameter("options.indicate", true));
            if (o!=null) {
                return (PointObject) o;
            }
            return null;
        }
        if (Preview) return null;
        // User creates a new point:
        ConstructionObject oc=tryCreateIntersection(x, y, true);
        if (oc!=null) {
            final PointObject o=(PointObject) oc;
            o.edit(this, false, false);


            // o.setDefaults();
            // if (o.showName()) renameABC(o,false,false);
            return o;
        }
        if (!IntersectionYes) {
            return null;
        }
        final MyVector w=selectPointonObjects(x, y, true, false);
        filter(w, x, y, true);
        if (w.size()>0) {
            oc=select(w, !Control);
            if (oc==null) {
                return null;
            }
            final PointObject o=new PointObject(C, x(x), y(y), oc);
            if (is3D()&&o instanceof PointObject&&((PointObject) o).getBound()!=null) { // Dibs : 2D -> 3D
            	validate();
            	if (((PointObject) o).getBound() instanceof TwoPointLineObject) {
        		TwoPointLineObject ligne= (TwoPointLineObject) ((PointObject) o).getBound();
    			if (((PointObject) ligne.getP1()).is3D()&&((PointObject) ligne.getP2()).is3D()) {
    				try {
    					double x1=((PointObject) ligne.getP1()).getX3D();
    					double y1=((PointObject) ligne.getP1()).getY3D();
        				double z1=((PointObject) ligne.getP1()).getZ3D();
        				double x2=((PointObject) ligne.getP2()).getX3D();
        				double y2=((PointObject) ligne.getP2()).getY3D();
        				double z2=((PointObject) ligne.getP2 ()).getZ3D();
        				double x_O=getConstruction().find("O").getX();
        				double x_X=getConstruction().find("X").getX();
        				double x_Y=getConstruction().find("Y").getX();
        				double x_Z=getConstruction().find("Z").getX();
        				double alpha1=(o.getX()-x_O-x1*(x_X-x_O)-y1*(x_Y-x_O)-z1*(x_Z-x_O))/((x2-x1)*(x_X-x_O)+(y2-y1)*(x_Y-x_O)+(z2-z1)*(x_Z-x_O));
        				if (x1==x2&&y1==y2) {
        					double y_O=getConstruction().find("O").getY();
            				double y_X=getConstruction().find("X").getY();
            				double y_Y=getConstruction().find("Y").getY();
            				double y_Z=getConstruction().find("Z").getY();
        					alpha1=(getY()-y_O-x1*(y_X-y_O)-y1*(y_Y-y_O)-z1*(y_Z-y_O))/((x2-x1)*(y_X-y_O)+(y2-y1)*(y_Y-y_O)+(z2-z1)*(y_Z-y_O));
        				}
        				((PointObject) o).setX3D(x1+alpha1*(x2-x1));
        				((PointObject) o).setY3D(y1+alpha1*(y2-y1));
        				((PointObject) o).setZ3D(z1+alpha1*(z2-z1));
        				((PointObject) o).setIs3D(true);
        				o.validate();
    	            	} catch (final Exception eBary) {
    	            		}
    					}
    			}
            	else if (((PointObject) o).getBound() instanceof QuadricObject) {
            		QuadricObject quadrique= (QuadricObject) ((PointObject) o).getBound();
            		if (quadrique.getP()[0].is3D()&&quadrique.getP()[1].is3D()&&quadrique.getP()[2].is3D()&&quadrique.getP()[3].is3D()&&quadrique.getP()[4].is3D()) {
            			try {
        					double x0=quadrique.getP()[0].getX3D();
            				double y0=quadrique.getP()[0].getY3D();
            				double z0=quadrique.getP()[0].getZ3D();
            				double x1=quadrique.getP()[1].getX3D();
            				double y1=quadrique.getP()[1].getY3D();
            				double z1=quadrique.getP()[1].getZ3D();
            				double x2=quadrique.getP()[2].getX3D();
            				double y2=quadrique.getP()[2].getY3D();
            				double z2=quadrique.getP()[2].getZ3D();
            				double x_O=getConstruction().find("O").getX();
            				double x_X=getConstruction().find("X").getX();
            				double x_Y=getConstruction().find("Y").getX();
            				double x_Z=getConstruction().find("Z").getX();
            				double y_O=getConstruction().find("O").getY();
            				double y_X=getConstruction().find("X").getY();
            				double y_Y=getConstruction().find("Y").getY();
            				double y_Z=getConstruction().find("Z").getY();
            				double coeffa=(x1-x0)*(x_X-x_O)+(y1-y0)*(x_Y-x_O)+(z1-z0)*(x_Z-x_O);
            				double coeffb=(x2-x0)*(x_X-x_O)+(y2-y0)*(x_Y-x_O)+(z2-z0)*(x_Z-x_O);
            				double coeffc=(x1-x0)*(y_X-y_O)+(y1-y0)*(y_Y-y_O)+(z1-z0)*(y_Z-y_O);
            				double coeffd=(x2-x0)*(y_X-y_O)+(y2-y0)*(y_Y-y_O)+(z2-z0)*(y_Z-y_O);
            				double coeffe=getX()-x_O-x0*(x_X-x_O)-y0*(x_Y-x_O)-z0*(x_Z-x_O);
            				double coefff=getY()-y_O-x0*(y_X-y_O)-y0*(y_Y-y_O)-z0*(y_Z-y_O);
            				double alpha1=(coeffe*coeffd-coefff*coeffb)/(coeffa*coeffd-coeffb*coeffc);
            				double beta1=(coeffa*coefff-coeffc*coeffe)/(coeffa*coeffd-coeffb*coeffc);
            				((PointObject) o).setX3D(x0+alpha1*(x1-x0)+beta1*(x2-x0));
            				((PointObject) o).setY3D(y0+alpha1*(y1-y0)+beta1*(y2-y0));
            				((PointObject) o).setZ3D(z0+alpha1*(z1-z0)+beta1*(z2-z0));
            				((PointObject) o).setIs3D(true);
            				o.validate();
        	            	} catch (final Exception eBary) {
        	            }
            			
            		}
            	}
            }
            o.edit(this, false, false);
            // if (ShowGrid&&!Global.getParameter("grid.leftsnap",
            // false)) {
            // o.snap(this);
            // }
            o.setUseAlpha(true);
            addObject(o);
            validate();
            o.setDefaults();
            NewPoint=true;
            repaint(); //fix a bug when we put a point on an object which is above an other
            return o;
        }
        final PointObject p=new PointObject(C, x(x), y(y));
        boolean macroIs3D=false;   // Dibs : si la macro de menu est dans le dossier 3D, elle créera un point 3D à la volée
        if (OC instanceof MacroRunner) {
        	try {
        		macroIs3D=((MacroRunner) OC).getM().getName().substring(0,3).equals("3D/");
        	}
        	catch (final Exception f) {}
        }
        if (PaletteManager.isSelected("bi_3Dcoords")||PaletteManager.isSelected("vector3D")||PaletteManager.isSelected("midpoint3D")||PaletteManager.isSelected("bi_3Dsymc")||PaletteManager.isSelected("bi_3Dproj")||PaletteManager.isSelected("bi_3Dsymp")||PaletteManager.isSelected("bi_3Dtrans")||PaletteManager.isSelected("area3D")||PaletteManager.isSelected("segment3D")||PaletteManager.isSelected("line3D")||PaletteManager.isSelected("ray3D")||PaletteManager.isSelected("bi_3Dsphererayon")
        	||PaletteManager.isSelected("bi_3Dspherepoint")||PaletteManager.isSelected("bi_3Dplandroite")||PaletteManager.isSelected("bi_3Dplanplan")||PaletteManager.isSelected("bi_3Dspheredroite")||PaletteManager.isSelected("bi_3Dsphereplan")||PaletteManager.isSelected("bi_3Dcube")||PaletteManager.isSelected("bi_3Dtetra")||PaletteManager.isSelected("bi_3Docta")||PaletteManager.isSelected("bi_3Disoc")||PaletteManager.isSelected("bi_3Ddode")||PaletteManager.isSelected("bi_3Dcircle1")||PaletteManager.isSelected("bi_3Dcircle2")||PaletteManager.isSelected("bi_3Dcircle3pts")||PaletteManager.isSelected("angle3D")
        	||macroIs3D) {// création d'un point 3D.
        	p.setIs3D(is3D());
        	try {
        		double xO=C.find("O").getX(), yO = C.find("O").getY();
        		double x3DO=getConstruction().find("O").getX();
        		double y3DO=getConstruction().find("O").getY();
        		double xx3D = Math.sin(Math.toRadians(getConstruction().find("E10").getValue()))*(p.getX()-xO)-Math.sin(Math.toRadians(getConstruction().find("E11").getValue()))*Math.cos(Math.toRadians(getConstruction().find("E10").getValue()))*(p.getY()-yO);
    			double yy3D = Math.cos(Math.toRadians(getConstruction().find("E10").getValue()))*(p.getX()-xO)+Math.sin(Math.toRadians(getConstruction().find("E11").getValue()))*Math.sin(Math.toRadians(getConstruction().find("E10").getValue()))*(p.getY()-yO);
    			double zz3D = Math.cos(Math.toRadians(getConstruction().find("E11").getValue()))*(p.getY()-yO);
                        if (Math.abs(xx3D)<1e-16) {
                            xx3D=0;
                        }
                        if (Math.abs(yy3D)<1e-16) {
                            yy3D=0;
                        }
                        if (Math.abs(zz3D)<1e-16) {
                            zz3D=0;
                        }
    			p.setX3D(xx3D);
    			p.setY3D(yy3D);
    			p.setZ3D(zz3D);
    			p.setFixed("x(O)+("+xx3D+")*(x(X)-x(O))+("+yy3D+")*(x(Y)-x(O))+("+zz3D+")*(x(Z)-x(O))", "y(O)+("+xx3D+")*(y(X)-y(O))+("+yy3D+")*(y(Y)-y(O))+("+zz3D+")*(y(Z)-y(O))");
        	}
        	catch (final Exception f) {}
        	
        }
        if (altdown&&getAxis_show()) {
            p.setHalfIncrement(this);
        }

//        if (getAxis_show()) {
//            p.snap(this);
//        }



        addObject(p);
        p.setDefaults();

        if ((isDP())&&(!isEuclidian())&&(!PaletteManager.isSelected("bi_distance"))) {

//        if (isDP()) {
            if (C!=null) {
                PrimitiveCircleObject HzBack=(PrimitiveCircleObject) C.find("HzBack");
                p.setBound(HzBack);
                p.setInside(true);
            }
        }
        // rename Point, if showname
        // if (p.showName()) renameABC(p,false,false);
        NewPoint=true;

        if (!PaletteManager.isSelected("bi_function_u")) {
            p.edit(this, false, false);
        }
        return p;
    }
    boolean IntersectionYes=false;

    public ConstructionObject tryCreateIntersection(final int x, final int y,
            final boolean ask) {
        final MyVector w=selectPointonObjects(x, y, true, true);
        sort(w);
        IntersectionYes=true;
        if (w.size()<2) {
            return null;
        }
        final ConstructionObject P1=(ConstructionObject) w.elementAt(0);
        final ConstructionObject P2=(ConstructionObject) w.elementAt(1);
        if (!(P1 instanceof PointonObject&&P2 instanceof PointonObject)) {
            return null;
        }
        if (!((PointonObject) P1).canInteresectWith(P2)||!((PointonObject) P2).canInteresectWith(P1)) {
            return null;
        }
        final IntersectionObject o[]=IntersectionConstructor.construct(P1,
                P2, C);
        if (o.length==1&&!o[0].valid()) {
            return null;
        }
        if (o.length==2&&!o[0].valid()&&!o[1].valid()) {
            return null;
        }
        if (o.length==4&&!o[0].valid()&&!o[1].valid()&&!o[2].valid()&&!o[3].valid()) {
            return null;
        }

        if (IntersectionYes) {
            IntersectionObject oc=o[0];
            if (o.length==2) {
                final double d0=o[0].valid()?o[0].distanceTo(x, y, this):Double.MAX_VALUE;
                final double d1=o[1].valid()?o[1].distanceTo(x, y, this):Double.MAX_VALUE;
                if (d0<d1) {
                    oc=o[0];
                } else {
                    oc=o[1];
                }
            }
            if (o.length==4) {
                final double d0=o[0].valid()?o[0].distanceTo(x, y, this):Double.MAX_VALUE;
                final double d1=o[1].valid()?o[1].distanceTo(x, y, this):Double.MAX_VALUE;
                final double d2=o[2].valid()?o[2].distanceTo(x, y, this):Double.MAX_VALUE;
                final double d3=o[3].valid()?o[3].distanceTo(x, y, this):Double.MAX_VALUE;
                double min=Math.min(d0, d1);
                min=Math.min(min, d2);
                min=Math.min(min, d3);
                if (d0==min) {
                    oc=o[0];
                } else if (d1==min) {
                    oc=o[1];
                } else if (d2==min) {
                    oc=o[2];
                } else if (d3==min) {
                    oc=o[3];
                }
            }
            if ((P1 instanceof TwoPointLineObject&&((TwoPointLineObject) P1).getP1().is3D())||(P1 instanceof TwoPointLineObject&&((TwoPointLineObject) P1).getP2().is3D())||(P2 instanceof TwoPointLineObject&&((TwoPointLineObject) P2).getP1().is3D())||(P2 instanceof TwoPointLineObject&&((TwoPointLineObject) P2).getP2().is3D())) {
            	try {	// Dibs intersection 3D
                	double a1=((TwoPointLineObject) P1).getP1().getX3D();
                	double b1=((TwoPointLineObject) P1).getP1().getY3D();
                	double c1=((TwoPointLineObject) P1).getP1().getZ3D();
                	double a2=((TwoPointLineObject) P1).getP2().getX3D();
                	double b2=((TwoPointLineObject) P1).getP2().getY3D();
                	double c2=((TwoPointLineObject) P1).getP2().getZ3D();
                	double a3=((TwoPointLineObject) P2).getP1().getX3D();
                	double b3=((TwoPointLineObject) P2).getP1().getY3D();
                	double c3=((TwoPointLineObject) P2).getP1().getZ3D();
                	double a4=((TwoPointLineObject) P2).getP2().getX3D();
                	double b4=((TwoPointLineObject) P2).getP2().getY3D();
                	double c4=((TwoPointLineObject) P2).getP2().getZ3D();
                	double det =(a2-a1)*(b3-b4)+(b2-b1)*(a4-a3);
                	double dets=(b3-b1)*(a4-a3)-(a3-a1)*(b4-b3);
                	double dett=(a2-a1)*(b3-b1)-(b2-b1)*(a3-a1);
                	double s=0.0;
                	double t=0.0;
                	if (Math.abs(det)<1e-12) {
                		det =(b2-b1)*(c3-c4)+(c2-c1)*(b4-b3);
                		dets=(c3-c1)*(b4-b3)-(b3-b1)*(c4-c3);
                		dett=(b2-b1)*(c3-c1)-(c2-c1)*(b3-b1);
                	}
                	s=dets/det;
            		t=dett/det;
                	//System.out.println(Math.abs((c2-c1)*s-(c4-c3)*t-c3+c1));
                	if (Math.abs((c2-c1)*s-(c4-c3)*t-c3+c1)<1e-12) {
                		oc.setX3D(a1+s*(a2-a1));
                		oc.setY3D(b1+s*(b2-b1));
                		oc.setZ3D(c1+s*(c2-c1));
        				oc.setIs3D(true);
                	}
                	else return null;
            	} catch (final Exception ex) {
            		return null;
                }	
            }
            addObject(oc);
            oc.autoAway();
            oc.validate(x(x), y(y));
            validate();
            oc.setDefaults();
            oc.setRestricted(getRestricted());
            NewPoint=true;
            return oc;

        } else {
            return null;
        }
    }

    public PointObject selectCreatePoint(final int x, final int y, boolean altdown) {
        return selectCreatePoint(x, y, true, false, altdown);
    }

    /**
     * Select a moveable point at x,y. Ask user, if necessary.
     **/
    public ConstructionObject selectMoveablePoint(final int x, final int y) {
        final ConstructionObject s=findSelectedObject();
        if (s instanceof PointObject&&((MoveableObject) s).moveable()&&s.nearto(x, y, this)) {
            return s;
        }
        V.removeAllElements();
        final Enumeration e=C.elements();
        while (e.hasMoreElements()) {
            final ConstructionObject o=(ConstructionObject) e.nextElement();
            if (o.isSelectable()&&(o instanceof PointObject)&&((MoveableObject) o).moveable()&&o.nearto(x, y, this)) {
                V.addElement(o);
            }
        }
        if (V.size()==1) {
            return (ConstructionObject) V.elementAt(0);
        }
        final ConstructionObject o=select(V);
        if (o!=null) {
            o.setSelected(true);
        }
        return null;
    }

    /**
     * Select a point with a selector, provided by the calling constructor.
     **/
    public ConstructionObject selectWithSelector(final int x, final int y,
            final Selector sel, final ConstructionObject until,
            final boolean choice) {
        V.removeAllElements();
        final Enumeration e=C.elements();
        while (e.hasMoreElements()) {
            final ConstructionObject o=(ConstructionObject) e.nextElement();
            if (o==until) {
                break;
            }
            if (o.isSelectable()&&sel.isAdmissible(this, o)&&o.nearto(x, y, this)) {

                V.addElement(o);
            }
        }
        if (V.size()==1) {
            return (ConstructionObject) V.elementAt(0);
        }
        if (!choice) {
            if (V.size()>0) {
                return (ConstructionObject) V.elementAt(0);
            } else {
                return null;
            }
        }
        final ConstructionObject o=select(V, x, y);
        return o;
    }

    public ConstructionObject selectWithSelector(final int x, final int y,
            final Selector sel, final ConstructionObject until) {
        return selectWithSelector(x, y, sel, until, true);
    }

    public ConstructionObject selectWithSelector(final int x, final int y,
            final Selector sel, final boolean choice) {
        return selectWithSelector(x, y, sel, null, choice);
    }

    public ConstructionObject selectWithSelector(final int x, final int y,
            final Selector sel) {
        return selectWithSelector(x, y, sel, null, true);
    }

    /**
     * Select a moveable point at x,y. Don't ask user, take first.
     **/
    public ConstructionObject selectImmediateMoveablePoint(final int x,
            final int y) {
        final Enumeration e=C.elements();
        V.removeAllElements();
        while (e.hasMoreElements()) {
            final ConstructionObject o=(ConstructionObject) e.nextElement();
            if (o.isSelectable()&&o instanceof PointObject&&((MoveableObject) o).moveable()&&o.nearto(x, y, this)) {
                V.addElement(o);
            }
        }
        if (V.size()==0) {
            return null;
        }
        return (ConstructionObject) V.elementAt(0);
    }

    /**
     * Select a moveable object at x,y.
     **/
    public ConstructionObject selectMoveableObject(final int x, final int y) {
        final ConstructionObject s=findSelectedObject();
        if (s instanceof MoveableObject&&((MoveableObject) s).moveable()&&s.nearto(x, y, this)) {
            return s;
        }
        selectMoveableObjects(x, y);
        if (V.size()==1) {
            return (ConstructionObject) V.elementAt(0);
        }
        final ConstructionObject o=select(V);
        if (o!=null) {
            if (!Global.getParameter("options.choice", true)) {
                return o;
            }
            o.setSelected(true);
        }
        return null;
    }

    public void selectMoveableObjects(final int x, final int y,
            final boolean control) {
        V.removeAllElements();
        final ConstructionObject s=findSelectedObject();
        if (s instanceof MoveableObject&&((MoveableObject) s).moveable()&&s.nearto(x, y, this)) {
            V.addElement(s);
            return;
        } else if (control&&s instanceof FixedCircleObject&&s.nearto(x, y, this)&&((FixedCircleObject) s).fixedByNumber()) {
            V.addElement(s);
            return;
        } else if (control&&s instanceof FixedAngleObject&&s.nearto(x, y, this)&&((FixedAngleObject) s).fixedByNumber()) {
            V.addElement(s);
            return;
        }
        final Enumeration e=C.elements();
        while (e.hasMoreElements()) {
            final ConstructionObject o=(ConstructionObject) e.nextElement();
            if (!control&&o.isSelectable()&&o instanceof MoveableObject&&((MoveableObject) o).moveable()&&o.nearto(x, y, this)) {
                V.addElement(o);
            } else if (control&&o instanceof FixedCircleObject&&o.nearto(x, y, this)&&((FixedCircleObject) o).fixedByNumber()) {
                V.addElement(o);
            } else if (control&&o instanceof FixedAngleObject&&o.nearto(x, y, this)&&((FixedAngleObject) o).fixedByNumber()) {
                V.addElement(o);
            }
        }
        filter(V, x, y);
    }

    public void selectMoveableObjects(final int x, final int y) {
        selectMoveableObjects(x, y, false);
    }

    /**
     * Select a moveable object at x,y. Don't ask user, take first.
     **/
    public ConstructionObject selectImmediateMoveableObject(final int x,
            final int y) {
        final Enumeration e=C.elements();
        V.removeAllElements();
        while (e.hasMoreElements()) {
            final ConstructionObject o=(ConstructionObject) e.nextElement();
            if (o.isSelectable()&&o instanceof MoveableObject&&((MoveableObject) o).moveable()&&o.nearto(x, y, this)) {
                V.addElement(o);
            }
        }
        filter(V, x, y);
        if (V.size()==0) {
            return null;
        }
        return (ConstructionObject) V.elementAt(0);
    }

    /**
     * try to determine the unique objects that are near coordinates x,y and
     * delete all others from the vector v.
     **/
    public void filter(final MyVector v, final int x, final int y,
            final boolean choice) {
        boolean HasPoints=false, HasNotFilled=false;
        Enumeration e=v.elements();
        while (e.hasMoreElements()) {
            final ConstructionObject o=(ConstructionObject) e.nextElement();
            if (o instanceof PointObject) {
                HasPoints=true;
            }
            if (!o.isFilledForSelect()) {
                HasNotFilled=true;
            }
        }
        if (HasPoints) {
            e=v.elements();
            while (e.hasMoreElements()) {
                final ConstructionObject o=(ConstructionObject) e.nextElement();
                if (!o.onlynearto(x, y, this)) {
                    v.removeElement(o);
                }
            }
        } else if (HasNotFilled) {
            e=v.elements();
            while (e.hasMoreElements()) {
                final ConstructionObject o=(ConstructionObject) e.nextElement();
                if (o.isFilledForSelect()) {
                    v.removeElement(o);
                }
            }
        }
        sort(v);
        if (!choice) {
            v.truncate(1);
        }
    }

    public void filter(final MyVector v, final int x, final int y) {
        filter(v, x, y, Global.getParameter("options.choice", true)||Control);
    }

    /**
     * user must select an object in the selection dialog, unless the selection
     * is unique anyway, or the filter determines that the selection is unique.
     **/
    public ConstructionObject select(final MyVector v, final int x,
            final int y, final boolean choice) {
        if (v.size()==0) {
            return null;
        }
        if (v.size()==1) {
            return (ConstructionObject) v.elementAt(0);
        }
        filter(v, x, y);
        if (v.size()==1) {
            return (ConstructionObject) v.elementAt(0);
        }
        if (!choice) {
            return (ConstructionObject) v.elementAt(0);
        }
        sortRow(V);
	new eric.JSelectPopup(this, v, RightClicked);
        RightClicked=false;
        //final eric.JSelectPopup d=new eric.JSelectPopup(this, v); //remonté d'une ligne et ajouté le booléen RightClicked
        return null;
    }

    public ConstructionObject select(final MyVector v, final int x, final int y) {
        return select(v, x, y, Global.getParameter("options.choice", true)||Control);
    }

    /**
     * user must select an object in the selection dialog, unless the selection
     * is unique anyway.
     **/
    public ConstructionObject select(final MyVector v, final boolean choice) {
        if (v.size()==0) {
            return null;
        }
        if (v.size()==1) {
            return (ConstructionObject) v.elementAt(0);
        }
        // if (!choice) return (ConstructionObject)v.elementAt(0);
        sortRow(V);
	new eric.JSelectPopup(this, v, RightClicked);
        RightClicked=false;
        //final eric.JSelectPopup d=new eric.JSelectPopup(this, v);
        return null;
    }

    public ConstructionObject select(final MyVector v) {
        return select(v, Global.getParameter("options.choice", true)||Control);
    }

    /**
     * select the label of a point, i.e. a point, which is set by the user
     *
     * @return the first label found
     **/
    public ConstructionObject selectLabel(final int x, final int y) {
        final Enumeration e=C.elements();
        while (e.hasMoreElements()) {
            final ConstructionObject o=(ConstructionObject) e.nextElement();
            if (o.isSelectable()&&o.textcontains(x, y, this)) {
                return o;
            }
        }
        return null;
    }

    public ConstructionObject findSelectedObject() {
        final Enumeration e=C.elements();
        while (e.hasMoreElements()) {
            final ConstructionObject o=(ConstructionObject) e.nextElement();
            if (o.selected()) {
                return o;
            }
        }
        return null;
    }
    // Indication functions
    MyVector Indicated=new MyVector();
    PointObject IndicatePoint=null;

    public void indicate(final MyVector v, final boolean showname) {
        if (v.size()==1) {
            if (showname) {
                setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
            } else if (v.elementAt(0) instanceof PointObject) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        } else {
            setCursor(Cursor.getDefaultCursor());
        }
        if (Indicated.equalsIdentical(v)) {
            return;
        }
        Enumeration e=Indicated.elements();
        while (e.hasMoreElements()) {
            ((ConstructionObject) e.nextElement()).setIndicated(false);
        }
        Indicated.removeAllElements();
        e=v.elements();
        while (e.hasMoreElements()) {
            final ConstructionObject o=(ConstructionObject) e.nextElement();
            Indicated.addElement(o);
            o.setIndicated(true, showname);
        }
        repaint();

    }

    public void indicate(final MyVector v) {
        indicate(v, false);
    }

    public void clearIndicated() {
        IndicatePoint=null;
        if (Indicated.size()==0) {
            return;
        }
        final Enumeration e=Indicated.elements();
        while (e.hasMoreElements()) {
            ((ConstructionObject) e.nextElement()).setIndicated(false);
        }
        Indicated.removeAllElements();
        setCursor(Cursor.getDefaultCursor());
        repaint();
    }
    boolean Preview=false;
    ConstructionObject LastNonPreview=null;
    MoveableObject PreviewObject=null;

    public void movePreview(double x, double y) {
        if (PreviewObject!=null) {
            PreviewObject.move(x, y);
            validate();
            repaint();
        }
    }

    public void movePreview(final MouseEvent e) {
        if (PreviewObject!=null) {
            PreviewObject.move(x(e.getX()), y(e.getY()));
            validate();
            repaint();
        }
    }

    public void prepareForPreview(final MouseEvent e) {
        LastNonPreview=C.lastButN(0);
        Preview=true;
    }

    public boolean isPreview() {
        return Preview;
    }

    public void clearPreview() {
        if (!Preview) {
            return;
        }
        C.clearAfter(LastNonPreview);
        LastNonPreview=null;
        PreviewObject=null;
        Preview=false;
        Count.fixAll(false);
    }

    public void setPreviewObject(final MoveableObject o) {
        PreviewObject=o;
    }

    public MoveableObject getPreviewObject() {
        return PreviewObject;
    }

    public ConstructionObject indicateTryCreateIntersection(final int x,
            final int y, final boolean ask) {

        final MyVector w=selectPointonObjects(x, y, true, true);

        sort(w);
        IntersectionYes=true;
        if (w.size()<2) {
            return null;
        }

        final IntersectionObject o[]=IntersectionConstructor.construct(
                (ConstructionObject) w.elementAt(0), (ConstructionObject) w.elementAt(1), C);

//        System.out.println("indicateTryCreateIntersection : "+o.length);

        if (o.length==1&&!o[0].valid()) {
            return null;
        }
        if (o.length==2) {
            if (!o[1].valid()) {
                if (!o[0].valid()) {
                    return null;
                }
            } else {
//                System.out.println("indicateTryCreateIntersection");
                final IntersectionObject h=o[0];
                o[0]=o[1];
                o[1]=h;
            }
        }
        IntersectionObject oc=o[0];
        if (o.length==2&&o[1].valid()) {
            final double d0=o[0].distanceTo(x, y, this), d1=o[1].distanceTo(x, y, this);
            if (d1<d0) {
                oc=o[1];
            }
        }
        if (o.length==4) {
            final double d0=o[0].valid()?o[0].distanceTo(x, y, this):Double.MAX_VALUE;
            final double d1=o[1].valid()?o[1].distanceTo(x, y, this):Double.MAX_VALUE;
            final double d2=o[2].valid()?o[2].distanceTo(x, y, this):Double.MAX_VALUE;
            final double d3=o[3].valid()?o[3].distanceTo(x, y, this):Double.MAX_VALUE;
            double min=Math.min(d0, d1);
            min=Math.min(min, d2);
            min=Math.min(min, d3);
            if (d0==min) {
                oc=o[0];
            } else if (d1==min) {
                oc=o[1];
            } else if (d2==min) {
                oc=o[2];
            } else if (d3==min) {
                oc=o[3];
            }
        }
        // oc.setDefaults();
        oc.setIndicated(true);
        oc.setType(PointObject.CIRCLE);
        oc.setColorType(ConstructionObject.THICK);
        IndicatePoint=oc;
        indicate(w);
        oc.validate(x(x), y(y));
        return oc;

    }

//    public void indicateCreatePoint(final int x, final int y,
//            final boolean multiple) {
//
//        final Enumeration e=C.elements();
//        V.removeAllElements();
//        while (e.hasMoreElements()) {
//            final ConstructionObject o=(ConstructionObject) e.nextElement();
//            if (o.isSelectable()&&o instanceof PointObject&&o.nearto(x, y, this)&&(multiple||!o.selected())) {
//                V.addElement(o);
//            }
//            sort(V);
//        }
//        if (V.size()>0) {
//            IndicatePoint=null;
//            filter(V, x, y, false);
//            indicate(V);
//            return;
//        }
//        PointObject oc=(PointObject) indicateTryCreateIntersection(x, y, true);
//        if (oc!=null) {
//            return;
//        }
//        final MyVector w=selectPointonObjects(x, y, true, false);
//        filter(w, x, y, true);
//
//        if (w.size()>=1) {
//            if (!w.equalsIdentical(Indicated)) {
//
//                oc=new PointObject(C, x(x), y(y), (ConstructionObject) w.elementAt(0));
//
//                if (getAxis_show()&&!Global.getParameter("grid.leftsnap", false)) {
//                    oc.snap(this);
//                }
//                oc.setUseAlpha(true);
//                oc.validate();
//                oc.setIndicated(true);
//                oc.setType(PointObject.CIRCLE);
//                oc.setColorType(ConstructionObject.THICK);
//                IndicatePoint=oc;
//                indicate(w);
//            } else if (IndicatePoint!=null) {
//                IndicatePoint.setType(PointObject.CIRCLE);
//                IndicatePoint.setColorType(ConstructionObject.THICK);
//                IndicatePoint.move(x(x), y(y));
//                IndicatePoint.project((ConstructionObject) w.elementAt(0));
//                repaint();
//            }
//        } else {
//            clearIndicated();
//        }
//    }
    public PointObject indicateCreatePoint(final int x, final int y,
            final boolean multiple) {

        final Enumeration e=C.elements();
        V.removeAllElements();
        while (e.hasMoreElements()) {
            final ConstructionObject o=(ConstructionObject) e.nextElement();
            if (o.isSelectable()&&o instanceof PointObject&&o.nearto(x, y, this)&&(multiple||!o.selected())) {
                V.addElement(o);
            }
            sort(V);
        }
        if (V.size()>0) {
            IndicatePoint=null;
            filter(V, x, y, false);
            indicate(V);
            return (PointObject) V.elementAt(0);
        }
        PointObject oc=(PointObject) indicateTryCreateIntersection(x, y, true);
        if (oc!=null) {
            return oc;
        }
        final MyVector w=selectPointonObjects(x, y, true, false);
        filter(w, x, y, true);

        if (w.size()>0) {
            if (!w.equalsIdentical(Indicated)) {

                oc=new PointObject(C, x(x), y(y), (ConstructionObject) w.elementAt(0));

                if (getAxis_show()&&!Global.getParameter("grid.leftsnap", false)) {
                    oc.snap(this);
                }
                oc.setUseAlpha(true);
                oc.validate();
                oc.setIndicated(true);
                oc.setType(PointObject.CIRCLE);
                oc.setColorType(ConstructionObject.THICK);
                IndicatePoint=oc;
                indicate(w);
                return IndicatePoint;
            } else if (IndicatePoint!=null) {
                IndicatePoint.setType(PointObject.CIRCLE);
                IndicatePoint.setColorType(ConstructionObject.THICK);
                IndicatePoint.move(x(x), y(y));
                IndicatePoint.project((ConstructionObject) w.elementAt(0));
                repaint();
                return IndicatePoint;
            }
        } else {
            clearIndicated();
        }
        return null;
    }

    public void indicateCircleLineObjects(final int x, final int y) {
        final MyVector w=selectCircleLineObjects(x, y);
        filter(V, x, y);
        indicate(w);
    }

    public void indicatePointonObjects(final int x, final int y) {

        final MyVector w=selectPointonObjects(x, y);
        filter(V, x, y);
        indicate(w);
    }

    public void indicateIntersectedObjects(final int x, final int y) {
        final MyVector w=selectPointonObjects(x, y);
        if (!w.equalsIdentical(Indicated)&&w.size()>=2) {
            final IntersectionObject o[]=IntersectionConstructor.construct(
                    (ConstructionObject) w.elementAt(0), (ConstructionObject) w.elementAt(1), C);
            IntersectionObject oc=o[0];
            if (o.length==2) {
                final double d0=o[0].distanceTo(x, y, this), d1=o[1].distanceTo(x, y, this);
                if (d1<d0) {
                    oc=o[1];
                }
                oc.autoAway();
            }
            if (o.length==4) {
                final double d0=o[0].valid()?o[0].distanceTo(x, y, this):Double.MAX_VALUE;
                final double d1=o[1].valid()?o[1].distanceTo(x, y, this):Double.MAX_VALUE;
                final double d2=o[2].valid()?o[2].distanceTo(x, y, this):Double.MAX_VALUE;
                final double d3=o[3].valid()?o[3].distanceTo(x, y, this):Double.MAX_VALUE;
                double min=Math.min(d0, d1);
                min=Math.min(min, d2);
                min=Math.min(min, d3);
                if (d0==min) {
                    oc=o[0];
                } else if (d1==min) {
                    oc=o[1];
                } else if (d2==min) {
                    oc=o[2];
                } else if (d3==min) {
                    oc=o[3];
                }
                oc.autoAway();
            }
            oc.validate();
            oc.setDefaults();
            oc.setIndicated(true);
            oc.setColorType(ConstructionObject.THICK);
            oc.setType(PointObject.CIRCLE);
            IndicatePoint=oc;
        } else {
            IndicatePoint=null;
        }
        indicate(w);
    }

    public void indicateLineObjects(final int x, final int y) {
        selectLineObjects(x, y, true);
        filter(V, x, y);
        indicate(V);
    }

    public void indicateAnimationObjects(final int x, final int y) {
        selectAnimationObject(x, y);
        filter(V, x, y);
        indicate(V);
    }

    public void indicatePointObjects(final int x, final int y) {
        selectPointObjects(x, y, true);
        filter(V, x, y);
        indicate(V);
    }

    public void indicatePointObjects(final int x, final int y,
            final ConstructionObject until) {
        selectPointObjects(x, y, true, until);
        filter(V, x, y);
        indicate(V);
    }

    public void indicatePointsOrLines(final int x, final int y) {
        selectPointsOrLines(x, y, true);
        filter(V, x, y);
        indicate(V);
    }

    public void indicateSegments(final int x, final int y) {
        selectSegments(x, y, true);
        filter(V, x, y);
        indicate(V);
    }

    public void indicateMultipleFinal(final int x, final int y) {
        // selectMultipleFinals(x, y, true);
        filter(V, x, y);
        indicate(V);
    }

    public void indicateRays(final int x, final int y) {
        selectRays(x, y, true);
        filter(V, x, y);
        indicate(V);
    }

    public void indicateTwoPointLines(final int x, final int y) {
        selectTwoPointLines(x, y, true);
        filter(V, x, y);
        indicate(V);
    }

    public void indicateCircles(final int x, final int y) {
        selectCircles(x, y, true);
        filter(V, x, y);
        indicate(V);
    }

    public void indicateMoveableObjects(final int x, final int y,
            final boolean control) {
        selectMoveableObjects(x, y, control);
        filter(V, x, y);
        indicate(V);
    }

    public void indicateMoveableObjects(final int x, final int y) {
        indicateMoveableObjects(x, y, false);
    }

    public void indicateWithSelector(final int x, final int y,
            final Selector sel) {
        selectWithSelector(x, y, sel, false);
        filter(V, x, y);
        indicate(V);
    }

    public void indicateWithSelector(final int x, final int y,
            final Selector sel, final ConstructionObject until) {
        selectWithSelector(x, y, sel, until, false);
        filter(V, x, y);
        indicate(V);
    }

    public void indicateConstructableObjects(final int x, final int y) {
        selectConstructableObjects(x, y);
        filter(V, x, y);
        indicate(V);
    }

    public void indicateObjects(final int x, final int y, final boolean showname) {
        selectObjects(x, y);
        filter(V, x, y);
        indicate(V, showname);
    }

    public void indicateObjects(final int x, final int y) {
        indicateObjects(x, y, false);
    }

    public void setTool(final ObjectConstructor oc) {
	if (!(oc instanceof ZoomerTool)||!(OC instanceof SelectTool)) {
            if (OC!=null) {
		/* do NOT invalidate JSmacroTool when user want to move an object
		** or the construction by right clic
		** invalidation of JSmacroTool occurs only when user clic on an icon in PaletteManager
		** or on a submenu, in JGeneralMenuBar
		 */
                if (!(OC instanceof JSmacroTool)) {
                    OC.invalidate(this);
                }
            }
        }

//not working when multiple choice

//	if (!(oc instanceof ZoomerTool)||!(OC instanceof SelectTool)) {
//            if (OC!=null) {
//                //do NOT invalidate JSmacroTool when user want to move an object or the construction
//                if (!(OC instanceof JSmacroTool)) {
//                    OC.invalidate(this);
//                } else if (!(oc instanceof ZoomerTool||oc instanceof MoverTool) || ((JSmacroTool) OC).getPreviousTool() instanceof MoverTool) {
//                    ((JSmacroTool) OC).invalidate_and_saveoc(this, oc);
//                }
//            }
//        }

// note PM : la suite est à décommenter
// si jamais le code précédent ne répond pas aux exigences
// (les lignes précédentes sont à supprimer)

//        if (!(oc instanceof ZoomerTool)||!(OC instanceof SelectTool)) {
//            if (OC!=null) {
//                //do NOT invalidate JSmacroTool when user want to move an object or the construction
////	    if(!((oc instanceof ZoomerTool || oc instanceof MoverTool) && OC instanceof JSmacroTool))
//		OC.invalidate(this);
//
//                if (OC instanceof JSmacroTool) {
//                    ((JSmacroTool) OC).invalidate_and_saveoc(this, oc);
//                }else{
//                    OC.invalidate(this);
//                }
//                // note Eric : La modif ci-dessous provoquait une boucle sans fin dans le thread
//                // du script lorsque l'utilisateur prenait ZoomerTool ou MoverTool. InteractiveInput
//                // n'est pas un outil comme les autres : on tue le thread si l'utilisateur
//                // ne fait pas ce que le script veut qu'il fasse (montrer l'objet).
//                //
////                if (!(OC instanceof JSmacroTool)) {
////                    OC.invalidate(this);
////                } else if (!(oc instanceof ZoomerTool||oc instanceof MoverTool)) {
////                    ((JSmacroTool) OC).invalidate_and_saveoc(this, oc);
////                }
//            }
//        }


        OC=oc;
        OC.showStatus(this);
        clearIndicated();
        clearPreview();
    }

    public void setSuddenTool(final ObjectConstructor oc) // called from
    // ZirkelFrame
    {
        OC=oc;
    }

    public void reset() {
        clearPreview();
        clearIndicated();
        OC.reset(this);
    }

    public void clearSelected() // called from ObjectConstructor
    {
        final Enumeration e=C.elements();
        while (e.hasMoreElements()) {
            final ConstructionObject o=(ConstructionObject) e.nextElement();
            o.setSelected(false);
        }
        repaint();
    }

    public final synchronized void clear() // delete complete construction
    {
        if (OC!=null) {
            OC.invalidate(this);
        }
        C.clear();
        if (JCM!=null) {
            JCM.removeAllControls();
        }
        if (Animations!=null) {
            removeAllAnimations();
        }
        recompute();
        setDefaultColor(0);
        setDefaultColorType(0);
        //setDefaultType(2); //after restoring construction throw JS, point type is always "circle"(2) !
        reloadCD();
    }

    /**
     * Delete last construction step done by user (highest number) and all
     * non-visible steps before it.
     */
    public synchronized void back() {
        reset();
        ConstructionObject O=C.lastByNumber();
        if (O==null) {
            return;
        }
        if (O.isKeep()) {
            return;
        }
        delete(O);
//        System.out.println("entrée");
//        while (true) {
//            O=C.lastByNumber();
//            if (O==null) {
//                break;
//            } else if (!O.mustHide(this)||O.isHideBreak()||O.isKeep()||O.isOwnedByControl()) {
//                break;
//            }
//            delete(O);
//        }
//        System.out.println("sortie");
        validate();
    }

    public synchronized void undo() {
        reset();
        C.undo();
    }

    public void delete(final ConstructionObject o) {

        if (C.lastButN(0)==null) {
            return;
        }
        if (o.isKeep()) {
            return;
        }
        JControlsManager.removeOwnerControl(this, o);

        if (!(o instanceof AxisObject)) {
            C.clearConstructables();
            o.setFlag(true);
            C.determineChildren();
            C.delete(false);
	    update_distant(o, 0);
            reloadCD();
            repaintCD();
        }
    }

    /**
     * Delete a vector of construction objects.
     *
     * @param v
     */
    public void delete(final Vector v) {
        if (C.lastButN(0)==null) {
            return;
        }
        C.clearConstructables();
        final Enumeration e=v.elements();
        while (e.hasMoreElements()) {
            final ConstructionObject o=(ConstructionObject) e.nextElement();
            if (o.isKeep()) {
                return;
            }
            JControlsManager.removeOwnerControl(this, o);
            o.setFlag(true);
        }
        C.determineChildren();
        C.delete(false);
    }

    public boolean depends(final ConstructionObject o,
            final ConstructionObject on) {
        C.clearConstructables();
        on.setFlag(true);
        C.determineChildren();
        return o.isFlag();
    }

    public void addStatusListener(final StatusListener sl) {
        SL=sl;
    }

    public void showStatus(final String s) {
        if (SL!=null) {
            SL.showStatus(s);
        }
    }

    public void showStatus() {
        if (OC!=null) {
            OC.showStatus(this);
        }
    }

    public boolean showHidden() {
        return ShowHidden;
    }

    public void setDefaultColor(final int c) {
        C.DefaultColor=c;
    }

    public int getDefaultColor() {
        return C.DefaultColor;
    }

    public void setDefaultType(final int c) {
        C.DefaultType=c;
    }

    public int getDefaultType() {
        return C.DefaultType;
    }

    public void setPartial(final boolean flag) {
        C.Partial=flag;
    }

    public boolean getPartial() {
        return C.Partial;
    }

    public void setRestricted(final boolean flag) {
        C.Restricted=flag;
    }

    public boolean getRestricted() {
        return C.Restricted;
    }

    public void setPartialLines(final boolean flag) {
        C.PartialLines=flag;
    }

    public boolean getPartialLines() {
        return C.PartialLines;
    }

    public void setVectors(final boolean flag) {
        C.Vectors=flag;
    }

    public boolean getVectors() {
        return C.Vectors;
    }

    public void setLongNames(final boolean flag) {
        C.LongNames=flag;
    }

    public boolean getLongNames() {
        return C.LongNames;
    }

    public void setLargeFont(final boolean flag) {
        C.LargeFont=flag;
    }

    public boolean getLargeFont() {
        return C.LargeFont;
    }

    public void setBoldFont(final boolean flag) {
        C.BoldFont=flag;
    }

    public boolean getBoldFont() {
        return C.BoldFont;
    }

    public void setObtuse(final boolean flag) {
        C.Obtuse=flag;
    }

    public boolean getObtuse() {
        return C.Obtuse;
    }

    public void setSolid(final boolean flag) {
        C.Solid=flag;
    }

    public boolean getSolid() {
        return C.Solid;
    }

    public void setShowNames(final boolean flag) {
        C.ShowNames=flag;
    }

    public boolean getShowNames() {
        return C.ShowNames;
    }

    public void setShowValues(final boolean flag) {
        C.ShowValues=flag;
    }

    public boolean getShowValues() {
        return C.ShowValues;
    }

    public void setDefaultColorType(final int c) {
        C.DefaultColorType=c;
    }

    public int getDefaultColorType() {
        return C.DefaultColorType;
    }

    public void setShowHidden(final boolean flag) {
        ShowHidden=flag;
        repaint();
    }

    public boolean getShowHidden() {
        return ShowHidden;
    }

    public void setHidden(final boolean flag) {
        C.Hidden=flag;
    }

    /**
     * With this it is possible to hide all non-constructable items. This
     * function is called from any object to see if it has to hide.
     */
    public boolean hides(final ConstructionObject o) {
        if (OC instanceof SetTargetsTool) {
            return !o.isFlag();
        } else {
            return false;
        }
    }

    // Get xml file source :
    public String getFileSource() throws Exception {
        final ByteArrayOutputStream out=new ByteArrayOutputStream();
        save(out, true, true, false, true, getMacros(), "");
        return out.toString("utf-8");
    }

    public void setFileSource(String s) throws Exception {
        final ByteArrayOutputStream bout=new ByteArrayOutputStream();
        final PrintWriter out=new PrintWriter(new OutputStreamWriter(
                bout, "utf-8"));
        out.print(s);
        out.close();
        final byte b[]=bout.toByteArray();
        final InputStream in=new ByteArrayInputStream(b);
        clear();
        removeAllScripts();
        Count.resetAll();
        clearDrawings();
        load(in, true, true);
        validate();
        repaint();
        SwingUtilities.invokeLater(new Runnable() {

	    @Override
            public void run() {
                JCM.readXmlTags();
            }
        });
    }

    /**
     * Save the construction in this canvas in XML form to the specified output
     * stream. This function will create the complete XML file, including
     * headers.
     */
    public void save(OutputStream o, boolean construction,
            boolean macros, boolean protectedmacros, boolean script,
            Vector Macros, String Restrict) throws IOException {
        final boolean utf=Global.getParameter("options.utf", true);
        XmlWriter xml;
        if (utf) {
            xml=new XmlWriter(new PrintWriter(new OutputStreamWriter(o,
                    "UTF8"), true));
            xml.printEncoding(utf?"utf-8":"iso-8859-1");
        } else {
            xml=new XmlWriter(
                    new PrintWriter(new OutputStreamWriter(o), true));
            xml.printXml();
        }
        // xml.printXls("zirkel.xsl");
        // xml.printDoctype("CaR","zirkel.dtd");
        xml.startTagNewLine("CaR");
        if (macros) {
            // Sorter.sort(Macros);
            final Enumeration e=Macros.elements();
            while (e.hasMoreElements()) {
                final Macro m=((MacroItem) e.nextElement()).M;
                if (protectedmacros||!m.isProtected()) {
                    m.saveMacro(xml);
                }
            }
        }
        if (script) {
            for (ScriptItem myscript : Scripts.getScripts()) {
                myscript.saveScript(xml);
            }
        }

        if (construction) {
            xml.startTagStart("Construction");
            xml.startTagEndNewLine();
            xml.startTagStart("Window");
            xml.printArg("x", ""+C.getX());
            xml.printArg("y", ""+C.getY());
            xml.printArg("w", ""+C.getW());
            if (axis_show) {
                xml.printArg("showgrid", "true");
            }
            xml.finishTagNewLine();

            XmlTagWriter(xml);

            eric.controls.JControlsManager.PrintXmlTags(this, xml);



            if (axis_show) {
                xml.startTagStart("Grid");
                if (!axis_with_grid) {
                    xml.printArg("axesonly", "true");
                }
                xml.printArg("color", ""+(int) axis_color);
                xml.printArg("thickness", ""+(int) axis_thickness);
                if (!axis_labels) {
                    xml.printArg("nolabels", "true");
                } else {
                    if (Global.getParameter("grid.large", false)) {
                        xml.printArg("large", "true");
                    }
                    if (Global.getParameter("grid.bold", false)) {
                        xml.printArg("bold", "true");
                    }
                }
                xml.finishTagNewLine();
            }




            if (getConstruction().BackgroundFile!=null&&!getConstruction().BackgroundFile.equals("")) {
                xml.startTagStart("Background");
                xml.printArg("file", getConstruction().BackgroundFile);
                if (getConstruction().ResizeBackground) {
                    xml.printArg("resize", "true");
                }

                xml.printArg("usesize", Global.getParameter("background.usesize", "false"));
                xml.printArg("tile", Global.getParameter("background.tile", "false"));
                xml.printArg("center", Global.getParameter("background.center", "false"));

                xml.finishTagNewLine();
            }



            if (!C.getComment().equals("")) {
                xml.startTagNewLine("Comment");
                xml.printParagraphs(C.getComment(), 60);
                xml.endTagNewLine("Comment");
            }
            if (!C.getJobComment().equals("")) {
                xml.startTagNewLine("Assignment");
                xml.printParagraphs(C.getJobComment(), 60);
                xml.endTagNewLine("Assignment");
            }
            if (!Restrict.equals("")) {
                xml.finishTag("Restrict", "icons", Restrict);
            }

            Animations.printArgs(xml);
            Restrict_items.printArgs(xml);
            Exercise.printArgs(xml);

            if (OC instanceof ObjectTracker) {
                ((ObjectTracker) OC).save(xml);
            } else if (OC instanceof Tracker) {
                ((Tracker) OC).save(xml);
            } //            else if (OC instanceof AnimatorTool) {
            //                ((AnimatorTool) OC).save(xml);
            //            }
            else if (OC instanceof BreakpointAnimator) {
                ((BreakpointAnimator) OC).save(xml);
            }
            saveDrawings(xml);
            xml.startTagNewLine("Objects");
            C.save(xml);
            xml.endTagNewLine("Objects");
            xml.endTagNewLine("Construction");
        }
        xml.endTagNewLine("CaR");
    }

    public void load(final InputStream in, final boolean construction,
            final boolean macros) throws Exception { // System.out.println("read file");
        try {



            C.magnet.removeAllElements();
            if (construction) {
                C.clear();
                All=false;
                paint(getGraphics());
            } else {
                All=true;
            }
            final XmlReader xml=new XmlReader();
            xml.init(in);
            XmlTree tree=xml.scan();
            if (tree==null) {
                throw new ConstructionException("XML file not recognized");
            }
            Enumeration e=tree.getContent();
            while (e.hasMoreElements()) {
                tree=(XmlTree) e.nextElement();
                if (tree.getTag() instanceof XmlTagPI) {
                    continue;
                }
                if (!tree.getTag().name().equals("CaR")) {
                    throw new ConstructionException("CaR tag not found");
                } else {
                    break;
                }
            }
            e=tree.getContent();
            while (e.hasMoreElements()) {
                tree=(XmlTree) e.nextElement();
                final XmlTag tag=tree.getTag();
                if (tag.name().equals("Macro")) {
                    if (macros) {
                        try {
                            Count.setAllAlternate(true);
                            final Macro m=new Macro(this, tree);
                            int i;
                            for (i=0; i<Macros.size(); i++) {
                                if (((MacroItem) Macros.elementAt(i)).M.getName().equals(m.getName())) {
                                    break;
                                }
                            }
                            if (i>=Macros.size()) {
                                appendMacro(m);
                            }
                        } catch (final ConstructionException ex) {
                            Count.setAllAlternate(false);
                            throw ex;
                        }
                        Count.setAllAlternate(false);
                    }
                } else if (tag.name().equals("Script")) {
                    Scripts.addScript(tree);
                } else if (tag.name().equals("Construction")) {
                    if (construction) {
                        boolean job=false;
                        if (tag.hasParam("job")) {
                            job=true;
                            Last=tag.getValue("last");
                            if (Last==null) {
                                throw new ConstructionException(Global.name("exception.job"));
                            }
                            final String Target=tag.getValue("target");
                            if (Target==null) {
                                Targets=new Vector();
                                int i=1;
                                while (true) {
                                    final String s=tag.getValue("target"+i);
                                    i++;
                                    if (s==null) {
                                        break;
                                    }
                                    Targets.addElement(s);
                                }
                            } else {
                                Targets=new Vector();
                                Targets.addElement(Target);
                            }
                            if (Targets.isEmpty()) {
                                throw new ConstructionException(Global.name("exception.job"));
                            }
                        }
                        C.load(tree, this);
                        if (job) {
                            if (C.find(Last)==null) {
                                throw new ConstructionException(Global.name("exception.job"));
                            }
                            final Enumeration et=Targets.elements();
                            while (et.hasMoreElements()) {
                                final String s=(String) et.nextElement();
                                if (C.find(s)==null&&(!s.startsWith("~")||C.find(s.substring(1))==null)) {
                                    throw new ConstructionException(Global.name("exception.job"));
                                }
                            }
                            Job=true;
                        }
                        break;
                    }
                } else {
                    throw new ConstructionException("Construction not found");
                }
            }
            recompute();
            C.translateOffsets(this);
            resetSum();
            validate();
            repaint();
        } catch (final Exception e) {
            throw e;
        }
        reloadCD();
        repaint();
        // Give the magnet object list to the point
        // see PointConstructor.construct :
        Enumeration e=C.magnet.elements();
        while (e.hasMoreElements()) {
            final PointObject p=(PointObject) e.nextElement();
            p.setMagnetObjects((String) e.nextElement());
        }

        Scripts.fixMouseTargets();
        Animations.run();

        // System.out.println("finished reading file");
    }

    public void resetSum() {
        final Enumeration e=C.elements();
        while (e.hasMoreElements()) {
            final ConstructionObject o=(ConstructionObject) e.nextElement();
            if (o instanceof ExpressionObject) {
                ((ExpressionObject) o).reset();
            }
        }
    }

    public void warning(final String s, final String help) {
        final Warning w=new Warning(getFrame(), s, Global.name("warning"), true, help);
        w.center(getFrame());
        w.setVisible(true);
    }

    public void warning(final String s) {
        warning(s, "");
    }

    public void load(final InputStream in) throws Exception {
        try {
            C.Loading=true;
            load(in, true, true);
            C.Loading=false;
        } catch (final Exception e) {
            C.Loading=false;
            throw e;
        }
    }

    public String getComment() {
        return C.getComment();
    }

    public void setComment(final String s) {
        C.setComment(s);
    }

    public String getJobComment() {
        return C.getJobComment();
    }

    public void setJobComment(final String s) {
        C.setJobComment(s);
    }

    /**
     * This can be used to set a frame window for the error dialogs that the
     * canvas my display.
     * @param f
     */
    public void setFrame(final Frame f) {
    }

    public Frame getFrame() {
        return pipe_tools.getFrame();
    }

    /**
     * Maginify the view by the specified factor.
     */
    public void magnify(final double f) {
        ZoomerTool.initNonDraggableObjects(C);
        C.setXYW(C.getX(), C.getY(), C.getW()*f);
        ZoomerTool.zoomNonDraggableObjectsBy(C, f);
        recompute();
        validate();
        repaint();
    }

    /**
     * Shift the view with these deltas.
     */
    public void shift(final double dx, final double dy) {
        ZoomerTool.initNonDraggableObjects(C);
        C.setXYW(C.getX()+dx*C.getW(), C.getY()+dy*C.getW(), C.getW());
        ZoomerTool.shiftNonDraggableObjectsBy(C, dx*C.getW(), dy*C.getW());
        recompute();
        validate();
        repaint();
    }

    /**
     * Tracker routines: Call the OC (must be a TrackPainter) to paint the
     * object track.
     */
    public void paintTrack(final MyGraphics g) {
        if (!(OC instanceof TrackPainter)) {
            return;
        }
        ((TrackPainter) OC).paint(g, this);
    }

    /**
     * Run through the construction to update all object texts. This should be
     * called, whenever the name of an item was changed. It will recreate only
     * those texts, which contain the old name.
     */
    public void updateTexts(final ConstructionObject o, final String oldname) {
        C.updateTexts(o, oldname);
    }

    public Construction getConstruction() {
        return C;
    }

    public boolean isEmpty() {
        return ((C.lastButN(0)==null)&&(!isScript()));
    }

    /* Job part for CaRMetal 3.5 and later :
     *
     */
    public String job_getMessageOk() {
        return Exercise.getMessage_ok();
    }

    public void job_setMessageOk(String m) {
        Exercise.setMessage_ok(m);
    }

    public String job_getMessageFailed() {
        return Exercise.getMessage_failed();
    }

    public void job_setMessageFailed(String m) {
        Exercise.setMessage_failed(m);
    }

    public void job_setHideFinals(boolean b) {
        Exercise.setHidefinals(b);
    }

    public void job_setStaticJob(boolean b) {
        Exercise.setStaticJob(b);
    }

    public boolean job_isStaticJob() {
        return Exercise.isStaticJob();
    }

    public void job_setBackup(String s) {
        Exercise.setBackup(s);
    }

    public void job_addTarget(ConstructionObject target) {
        Exercise.addTarget(target);
    }

    public void job_removeTarget(ConstructionObject target) {
        Exercise.removeTarget(target);
    }

    /*Two following methods only for loading process :
     *
     */
    public void job_setTargetNames(String t) {
        Exercise.setTargetNames(t);
    }

    public void job_setTargets() {
        Exercise.setTargets();
    }

    public boolean job_isTargets() {
        return (Exercise.getTargets().size()>0);
    }

    public ArrayList<ConstructionObject> job_getTargets() {
        return Exercise.getTargets();
    }

    public void job_showDialog() {
        Exercise.showControlDialog();
    }

    public void job_setTargetsField() {
        Exercise.setTargetsField();
    }

    public void initJobCreationDialog() {
        if (Exercise!=null) {
            Exercise.init();
        }
    }

    // The following functions change the default values of some objects.
    public void setShowColor(final int i) {
        ShowColor=i;
        repaint();
    }

    public int getShowColor() {
        return ShowColor;
    }
//    public ObjectConstructor getOC() {
//        return OC;
//    }
    // Macros:
    private Vector Macros=new Vector();

    public Vector getMacros() {
        return Macros;
    }

    public boolean haveMacros() {
        return Macros.size()>0;
    }

    public boolean haveNonprotectedMacros() {
        final Enumeration e=Macros.elements();
        while (e.hasMoreElements()) {
            final MacroItem m=(MacroItem) e.nextElement();
            if (!m.M.isProtected()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Define a macro. There must be parameters (but not necessarily targets).
     * The function will display the macro dialog. It will create a new macro,
     * when the dialog was not aborted. The macro is kept in the Macros vector
     * by name.
     */
    boolean defineMacro() {

        return true;
    }

    /**
     * Copy a macro with fixed parameters from another macro.
     */
    public Macro copyMacro(final Macro m, final String name,
            final boolean fixed[]) {
        try {
            final Macro macro=(Macro) (m.clone());
            macro.Name=name;
            final boolean f[]=new boolean[fixed.length];
            for (int i=0; i<f.length; i++) {
                f[i]=fixed[i];
            }
            macro.Fixed=f;
            storeMacro(macro, true);
            return macro;
        } catch (final Exception e) {
            return m;
        }
    }

    /**
     * Define a macro based on a construction in c and the targets and
     * parameters in this construction. Store the macro in m.
     *
     * @param
     */
    public void defineMacro(final Construction c, final Macro m,
            final boolean targetsonly, final boolean superhide,
            final String prompt[], final boolean hideduplicates)
            throws ConstructionException {
        final Vector P=c.getParameters(), T=c.getTargets();
        c.setTranslation(m); // for construction expressions only (windoww etc.)
        c.clearTranslations();
        if (T.isEmpty()) // got no targets
        {
            c.determineConstructables();
        } else // got targets
        {
            c.clearConstructables();
            c.setParameterAsConstructables();
            for (int i=0; i<T.size(); i++) {
                c.determineConstructables((ConstructionObject) T.elementAt(i));
            }
        }
        // Make sure the counter for the macro object names starts
        // fresh (P1, P2, ...)
        Count.setAllAlternate(true);
        // Walk through the construction and copy all marked objects
        // to the macro definition.
        m.clearTranslators();
        Enumeration e=c.elements();
        while (e.hasMoreElements()) {
            final ConstructionObject o=(ConstructionObject) e.nextElement();
            if (o instanceof TwoPointLineObject && !(o instanceof VectorObject) && canConvert(c, (TwoPointLineObject) o) && o.isMainParameter()) {
                ((TwoPointLineObject) o).getP1().setFlag(false);
                ((TwoPointLineObject) o).getP2().setFlag(false);
            }
        }
        e=c.elements();
        while (e.hasMoreElements()) {
            ConstructionObject oc;
            final ConstructionObject o=(ConstructionObject) e.nextElement();
	    //System.out.println(o.getName()+" ; flag = "+o.isFlag());
	    //System.out.println(o.getName()+" ; param = "+o.isParameter());
	    //System.out.println(o.getName()+" ; mainparam = "+o.isMainParameter());
	    //System.out.println(o.getName()+" ; needed = "+needed(c,o,null));
	    //System.out.println(o.getName()+" ; "+!( o.isParameter() && !o.isMainParameter() && !needed(c, o, null)));
            if (o.isFlag()&&!( o.isParameter() && !o.isMainParameter() && !needed(c, o, null)) ) {
                // Now copy to the macro, but make sure that parameters
                // are simplified to their object type. E.g., an
                // objectpoint becomes a point, if it is a parameter.
                if (o instanceof PointObject&&o.isParameter()) {
                    final PointObject p=(PointObject) o;
                    if (p.isSpecialParameter()&&p.dependsOnParametersOnly()) {
                        oc=(ConstructionObject) p.copy(0, 0);
                    } else {
                        oc=new PointObject(c, p.getX(), p.getY());
                    }
                } else if (o instanceof FunctionObject&&o.isParameter()) {
                    final FunctionObject fo=new FunctionObject(c);
                    fo.setExpressions("x", "0", "0");
                    fo.setRange("-10", "10", "0.1");
                    oc=fo;
                } else if (o instanceof UserFunctionObject&&o.isParameter()) {
                    final UserFunctionObject fo=new UserFunctionObject(c);
                    fo.setExpressions("x", "0");
                    oc=fo;
                } else if (o instanceof ExpressionObject&&o.isParameter()) {
                    oc=new ExpressionObject(c, 0, 0);
                    ((ExpressionObject) oc).setExpression(o.getValue()+"", o.getConstruction());
                    ((ExpressionObject) oc).setCurrentValue(o.getValue());
                } else if (o instanceof TwoPointLineObject && !(o instanceof VectorObject) && canConvert(c, (TwoPointLineObject) o) && o.isParameter()) {
                    oc=new PrimitiveLineObject(c);
                } else if (o instanceof PrimitiveLineObject && !(o instanceof TwoPointLineObject) && !(o instanceof FixedAngleObject) && o.isParameter()) {
                    oc=new PrimitiveLineObject(c);
                } else if (o instanceof PrimitiveCircleObject&&o.isParameter()) {
                    oc=new PrimitiveCircleObject(c, ((PrimitiveCircleObject) o).getP1());
                    oc.translateConditionals();
                    oc.translate();
                } else if (o instanceof AreaObject&&o.isParameter()) {
                    oc=new AreaObject(c, new Vector());
                    oc.translateConditionals();
                    oc.translate();
                } else {
                    oc=(ConstructionObject) o.copy(0, 0);
                }

                if (oc!=null) {
                    m.add(oc);
                    if (o.isMainParameter()) {
                        oc.setName(o.getName());
                    }
                    if (targetsonly && !o.isTarget() && !o.isParameter()) {
                        if (superhide) {
                            oc.setSuperHidden(true);
                        } else {
                            oc.setHidden(true);
                        }
                    }
                    if (o.isParameter()&&o.isHidden()) {
                        oc.setHidden(true);
                    }
                    // All parameters in the constructions translate to
                    // the paramters in the macro definition.
                    o.setTranslation(oc);
                }
            } else {
                o.setTranslation(null);
            }
        }

        e=c.elements();
        while (e.hasMoreElements()) {
            final ConstructionObject o=(ConstructionObject) e.nextElement();
            if (o.isFlag()&&!o.isParameter()) {
                o.laterTranslate(c);
            }
        }
        Count.setAllAlternate(false);
        // translate the @... forward declarations in FindObjectExpression.
        c.clearErrors();
        m.runTranslators(c);
        // see if any errors occured (@.. to nonexisting object, generated
        // by the FindObjectExpression on translation.
        e=c.getErrors();
        if (e.hasMoreElements()) {
            warning((String) e.nextElement(), "macroerror");
        }
        // Now set the paramter array and make sure it is translated
        // to the objects in the macro definition.
        final ConstructionObject Params[]=new ConstructionObject[P.size()];
        for (int i=0; i<P.size(); i++) {
            Params[i]=((ConstructionObject) P.elementAt(i)).getTranslation();
        }

        m.setParams(Params);
        final String p[]=new String[prompt.length];
        for (int j=0; j<prompt.length; j++) {
            final ConstructionObject o=c.find(prompt[j]);
            if (o==null||o.getTranslation()==null||!(o instanceof FixedCircleObject||o instanceof FixedAngleObject||o instanceof ExpressionObject)) {
                throw new ConstructionException(Global.name("exception.prompt"));
            }
            for (int i=0; i<P.size(); i++) {
                final ConstructionObject op=(ConstructionObject) P.elementAt(i);
                if (op==o) {
                    throw new ConstructionException(Global.name("exception.prompt.parameter"));
                }
            }
            p[j]=o.getTranslation().getName();
        }
        m.setPromptFor(p);
        for (int i=0; i<prompt.length; i++) {
            m.setPromptName(i, prompt[i]);
        }
        m.hideDuplicates(hideduplicates);
    }

    /**
     * See, if this secondary parameter "o" is needed in the construction "c" by
     * either a constructable object, or a parameter different from "besides".
     */
    public boolean needed(final Construction c, final ConstructionObject o,
            final ConstructionObject besides) {
        final Enumeration e=c.elements();
        while (e.hasMoreElements()) {
            final ConstructionObject u=(ConstructionObject) e.nextElement();
            if (!u.isFlag()||u==besides) {
                continue;
            }
            if (c.dependsDirectlyOn(u, o)) {
                return true;
            }
        }
        return false;
    }

    /**
     * See, if this two point line can be converted to a primitive line.
     */
    public boolean canConvert(final Construction c, final TwoPointLineObject o) {
        final PointObject p1=o.getP1(), p2=o.getP2();
        if (p1.isMainParameter()||p2.isMainParameter()) {
            return false;
        }
        return !(needed(c, p1, o)||needed(c, p2, o));
    }

    /*
     * Méthodes permettant de gérer les scripts
     */

    public void runOnLoadScripts() {
        if (firstLoad) {
            firstLoad=false;
            SwingUtilities.invokeLater(new Runnable() {

		@Override
                public void run() {
                    Scripts.runOnLoadScripts();
                }
            });
        }
    }

    public void stopAllScripts() {
	//Scripts.stopAllScripts();
	for(ScriptItem item : Scripts.getScripts()){
	    item.stopme();
	}
	for(ScriptItem item : JZirkelCanvas.getScriptsLeftPanel().getScripts()){
	    item.stopme();
	}
    }

    public void restartAllScripts() {
        //Scripts.restartAllScripts();
	for(ScriptItem item : Scripts.getScripts()){
	    item.restartme();
	}
	for(ScriptItem item : JZirkelCanvas.getScriptsLeftPanel().getScripts()){
	    item.restartme();
	}
    }

    public boolean isThereAnyScriptRunning() {
	//return Scripts.isThereAnyScriptRunning();
	for (ScriptItem item : Scripts.getScripts()) {
            if (item.isRunning()) {
                return true;
            }
        }
	for (ScriptItem item : JZirkelCanvas.getScriptsLeftPanel().getScripts()) {
            if (item.isRunning()) {
                return true;
            }
        }
	return false;
    }

    public boolean isThereAnyStoppedScripts() {
        for (ScriptItem item : Scripts.getScripts()) {
            if (item.isStopped()) {
                return true;
            }
        }
	for (ScriptItem item : JZirkelCanvas.getScriptsLeftPanel().getScripts()) {
            if (item.isStopped()) {
                return true;
            }
        }
        return false;
    }

    public void killAllScripts(){
	for (ScriptItem item : Scripts.getScripts()) {
            item.killme();
        }
	for (ScriptItem item : JZirkelCanvas.getScriptsLeftPanel().getScripts()) {
            item.killme();
        }
    }

    public void openScriptFile(String filename, boolean open) {
        Scripts.openScriptFile(filename, open);
    }

    //pas d'occ
    public void newScript() {
        Scripts.newScript();
    }
    //pas d'occ
    public void saveScript(String s, String script) {
        Scripts.saveScript(s, script);
    }

    public boolean isScript() {
        return (Scripts.getScripts().size()>0);
    }

    public ScriptItemsArray getScripts() {
        return Scripts.getScripts();
    }

    public ScriptPanel getScriptsPanel() {
	return Scripts;
    }
    //pas d'occ
    public void removeScript(String s) {
        Scripts.removeScript(s);
    }

    public void removeAllScripts() {
        Scripts.removeAllScripts();
    }

    public void runControlScripts(JCanvasPanel jp) {
        if (Scripts!=null) {
            Scripts.runControlScripts(jp);
        }
    }

//    public void pauseControlScripts(ConstructionObject o){
//        if (Scripts!=null) {
//            Scripts.pauseControlScripts(o);
//        }
//    }
    public void prepareDragActionScripts(ConstructionObject o) {
        if (Scripts!=null) {
            Scripts.prepareDragActionScript(o);
        }
    }

    public void runDragAction() {
        if (Scripts!=null) {
            Scripts.runDragAction();
        }
    }
    public void runUpAction(ConstructionObject o){
	if(Scripts!=null){
	    Scripts.runUpAction(o);
	}
    }

    public void stopDragAction() {
        if (Scripts!=null) {
            Scripts.stopDragAction();
        }
    }

    public AnimationPanel getAnimations() {
        return Animations;
    }

    public void removeAllAnimations() {
        remove(Animations);
        Animations=null;
        Animations=new AnimationPanel(this);
    }

    public boolean isAnimated(ConstructionObject o) {
        return Animations.isAnimated(o);
    }

    public void removeAnimation(ConstructionObject o) {
        Animations.removeAnimation(o);
    }

    public void addAnimation(ConstructionObject o) {
        Animations.addAnimation(o);
    }

    public void addAnimation(String objectname) {
        Animations.addAnimation(objectname);
    }

    public void selectAnimationPoints() {
        Animations.setObjectSelected(true);
    }

    public void setAnimationNegative(String objectname, boolean negative) {
        Animations.setAnimationNegative(objectname, negative);
    }

    public void setAnimationDelay(double delay) {
        Animations.setDelay(delay);
    }

    public RestrictContainer getRestrict() {
        return RestrictDialog;
    }

    public void initRestrictDialog() {
        if (RestrictDialog!=null) {
            RestrictDialog.init();
        }
    }

    public void getNewRestrictedDialog() {
        closeRestrictDialog();
        RestrictDialog=new RestrictContainer(this);
        add(RestrictDialog);
        RestrictDialog.init();
        RestrictDialog.revalidate();
        RestrictDialog.repaint();
        repaint();
    }

    public void closeRestrictDialog() {
        if (RestrictDialog!=null) {
            remove(RestrictDialog);
            repaint();
            RestrictDialog=null;
        }
    }

    public void addHiddenItem(String s) {
        Restrict_items.add(s);
    }

    public void removeHiddenItem(String s) {
        Restrict_items.remove(s);
    }

    public boolean isHiddenItem(String s) {
        return Restrict_items.isHidden(s);
    }

    public boolean isRestricted() {
        return Restrict_items.isRestricted();
    }

    public ArrayList<String> getHiddenItems() {
        return Restrict_items.get();
    }

    public void setHiddenItems(String items) {
        Restrict_items.set(items);
    }

    public void setHiddenItems(ArrayList<String> items) {
        Restrict_items.set(items);
    }

    public void initRestrictedHiddenItemsFromFactorySettings() {
        Restrict_items.initRestrictedHiddenItemsFromFactorySettings();
    }

    public void initStandardRestrictedHiddenItems() {
        Restrict_items.initRestrictedHiddenItems();
    }

    public void setStandardRestrictedItems() {
        Restrict_items.setStandardRestrictedItems();
    }

    public void setLibraryMacrosVisible(boolean b) {
        if (b!=islibrarymacrovisible) {
            islibrarymacrovisible=b;
            JZirkelCanvas.ActualiseMacroPanel();
        }
    }

    public boolean isLibraryMacrosVisible() {
        return islibrarymacrovisible;
    }

    /**
     * Define a macro from the information stored in the construction c, and
     * store it to the macros in this ZirkelCanvas object.
     */
    public void defineMacro(final String name, final String comment,
            final Construction c) throws ConstructionException {
        final Vector T=c.getTargets();
        final String Prompts[]=new String[c.Prompts.size()];
        for (int i=0; i<Prompts.length; i++) {
            Prompts[i]=(String) c.Prompts.elementAt(i);
        }
        final Macro m=new Macro(this, name, comment, Prompts);
        defineMacro(c, m, T.size()>0&&!c.ShowAll, c.SuperHide, c.getPromptFor(), true);
        storeMacro(m, true);
    }

    /*
     * Store the macro in the macro list (or replace the old macro with the same
     * name
     *
     * @param all Replace the macro without asking.
     */
    public void storeMacro(final Macro m, final boolean all) {
        int i;
        for (i=0; i<Macros.size(); i++) {
            if (((MacroItem) Macros.elementAt(i)).M.getName().equals(
                    m.getName())) {
                All=replaceMacro(m, i, all); // ask user if All=false
                break;
            }
        }
        if (i>=Macros.size()) {
            appendMacro(m);
        }
    }
    public boolean ProtectMacros=false;
    public MacroMenu MM=null;

    public void appendMacro(final Macro m) {
        if (!ReadOnly) {
            if (ProtectMacros) {
                m.setProtected(true);
            }
            if (MM==null) {
                MM=new MacroMenu(PM, "", null);
            }
            final MacroItem mi=MM.add(m, m.getName());
            if (mi.I!=null) {
                mi.I.addActionListener(this);
            }
            Macros.addElement(mi);
        } else {
            if (MM==null) {
                MM=new MacroMenu(null, "", null);
            }
            final MacroItem mi=MM.add(m, m.getName());
            if (mi.I!=null) {
                mi.I.addActionListener(this);
            }
            Macros.addElement(mi);
        }
    }

    /**
     * Replace the macro item number i with m.
     *
     * @return User wants to replace all subsequent macros.
     */
    public boolean replaceMacro(final Macro m, final int i, final boolean all) {
        return true;
    }
    public String MacroCurrentComment;

    /**
     * The user has to choose from a list of macros (for running).
     */
    public Macro chooseMacro() {
        return null;
    }

    /**
     * The user can choose from a list of macros (for saving).
     *
     * @return A vector of selected Macros.
     */
    public Vector chooseMacros() {
        return null;
    }

    /**
     * Run a macro by name.
     */
    public Macro chooseMacro(final String name) {
        Macro m=null;
        Enumeration e=Macros.elements();
        while (e.hasMoreElements()) {
            m=((MacroItem) e.nextElement()).M;
            if (name.equals(m.getName())) {
                return m;
            }
        }
        e=MacroTools.getBuiltinMacros().elements();
        while (e.hasMoreElements()) {
            m=((MacroItem) e.nextElement()).M;
            if (name.equals(m.getName())) {
                return m;
            }
        }
        return null;
    }

    public void deleteMacros(final Vector v) {
        final Enumeration e=v.elements();
        while (e.hasMoreElements()) {
            final MacroItem m=(MacroItem) e.nextElement();
            deleteMacro(m);
        }
    }

    public void deleteMacro(final MacroItem m) {
        Macros.removeElement(m);
        if (m.I!=null) {
            m.I.removeActionListener(this);
            MM.remove(m);
        }
    }

    public void clearMacros() {
        final Enumeration e=Macros.elements();
        while (e.hasMoreElements()) {
            final MacroItem m=(MacroItem) e.nextElement();
            if (m.I!=null) {
                m.I.removeActionListener(this);
                MM.remove(m);
            }
        }
        Macros.removeAllElements();
    }

    public void clearNonprotectedMacros() {
        final Vector Vec=new Vector();
        final Enumeration e=Macros.elements();
        while (e.hasMoreElements()) {
            final MacroItem m=(MacroItem) e.nextElement();
            if (!m.M.isProtected()) {
                Vec.addElement(m);
            }
        }
        deleteMacros(Vec);
    }

    public void clearProtectedMacros() {
        final Vector Vec=new Vector();
        final Enumeration e=Macros.elements();
        while (e.hasMoreElements()) {
            final MacroItem m=(MacroItem) e.nextElement();
            if (m.M.isProtected()) {
                Vec.addElement(m);
            }
        }
        deleteMacros(Vec);
    }

    public void protectMacros() {
        final Enumeration e=Macros.elements();
        while (e.hasMoreElements()) {
            final MacroItem m=(MacroItem) e.nextElement();
            m.M.setProtected(true);
        }
    }

    public void renameMacro(final Macro macro, final String name) {
        final Enumeration e=Macros.elements();
        while (e.hasMoreElements()) {
            final MacroItem m=(MacroItem) e.nextElement();
            if (m.I!=null&&m.M==macro) {
                deleteMacro(m);
                break;
            }
        }
        macro.setName(name);
        appendMacro(macro);
    }
    MacroBar MBar;

    public void setMacroBar(final MacroBar m) {
        MBar=m;
    }

    public void updateMacroBar() {
        // if (MBar!=null) {
        // MBar.update(Macros);
        // }
    }
    // For the prompt in the status line:
    TextField TF;

    public void setTextField(final TextField t) {
        TF=t;
    }

    public void setPrompt(final String s) {
        if (TF!=null) {
            TF.setText(s);
        }
    }

    // Loading:
    public void loadRun(final InputStream is) {
        final BufferedReader in=new BufferedReader(new InputStreamReader(is));
        String s="", comment="";
        while (true) {
            try {
                s=in.readLine();
                if (s==null) {
                    break;
                }
                int n;
                if ((n=s.indexOf("//"))>=0) {
                    comment=s.substring(n+2).trim();
                    s=s.substring(0, n);
                } else {
                    comment="";
                }
                s=s.trim();
                int k=0;
                if ((k=Interpreter.startTest("macro", s))>=0) {
                    loadMacro(in, s.substring(k).trim());
                } else if (!s.equals("")) {
                    C.interpret(this, s, comment);
                }
            } catch (final ConstructionException e) {
                warning(e.getDescription()+" --- "+s);
                break;
            } catch (final Exception e) {
                warning(e.toString()+" --- "+s);
                e.printStackTrace();
                break;
            }
        }
        C.updateCircleDep();
    }

    public void loadMacro(final BufferedReader in, final String name)
            throws ConstructionException {
        final Construction construction=new Construction();
        construction.clear();
        String s="", comment="", macrocomment="";
        boolean inComment=true, newLine=true;
        while (true) {
            try {
                s=in.readLine();
                if (s==null) {
                    throw new ConstructionException(Global.name("exception.macroend"));
                }
                s=s.trim();
                final int n=s.indexOf("//");
                if (inComment&&n==0) {
                    final String h=s.substring(n+2).trim();
                    if (newLine) {
                        macrocomment=macrocomment+h;
                        newLine=false;
                    } else {
                        if (h.equals("")) {
                            macrocomment=macrocomment+"\n";
                            newLine=true;
                        } else {
                            macrocomment=macrocomment+" "+h;
                            newLine=false;
                        }
                    }
                    continue;
                }
                inComment=false;
                if (n>=0) {
                    comment=s.substring(n+2).trim();
                    s=s.substring(0, n);
                } else {
                    comment="";
                }
                s=s.trim();
                if (s.equals(Global.name("end"))) {
                    break;
                }
                if (s.toLowerCase().equals("end")) {
                    break;
                }
                if (!s.equals("")) {
                    construction.interpret(this, s, comment);
                }
            } catch (final InvalidException e) {
            } catch (final ConstructionException e) {
                throw new ConstructionException(e.getDescription()+" --- "+s);
            } catch (final IOException e) {
                warning(e.toString());
                return;
            }
        }
        defineMacro(name, macrocomment, construction);
    }

    public double getGridSize() {
        double gridsize=Math.pow(10, Math.floor(Math.log(C.getW()*2)/Math.log(10)))/10;
        if (C.getW()*2/gridsize>=30) {
            gridsize*=5;
        }
        if (C.getW()*2/gridsize<10) {
            gridsize/=2;
        }
        return gridsize;
    }

    public LatexOutput createBB(final String filename, final int w,
            final int h, final double dpi) {
        try {
            String path="";
            if (Global.getParameter("options.fullpath", true)) {
                path=FileName.pathAndSeparator(filename);
            }
            PrintWriter out=new PrintWriter(new FileOutputStream(path+FileName.purefilename(filename)+".bb"));
            out.println("%%BoundingBox: 0 0 "+w+" "+h);
            out.close();
            out=new PrintWriter(new FileOutputStream(path+FileName.purefilename(filename)+".ztx"));
            final LatexOutput lout=new LatexOutput(out);
            lout.open(w, h, dpi, path+FileName.filename(filename));
            return lout;
        } catch (final Exception e) {
            warning(e.toString());
        }
        return null;
    }

    /**
     * Return pressed.
     */
    public void returnPressed() {
        if (OC instanceof MacroRunner) {
            ((MacroRunner) OC).returnPressed(this);
        }
    }

    public void setMagnetTool(final PointObject p) {
        setTool(new MagnetTool(this, p, OC));
        p.setStrongSelected(true);
        final Enumeration e=p.getMagnetObjects().elements();
        while (e.hasMoreElements()) {
            final MagnetObj mo=(MagnetObj) e.nextElement();
            mo.setSelected(true);
        }
        repaint();
    }

    public void setJSTool(ScriptThread th, String msg, String type) {
        if (OC!=null) {
            OC.invalidate(this);
        }
        OC=new JSmacroTool(this, th, msg, type, OC);
        OC.showStatus(this);
        clearIndicated();
        clearPreview();
    }

    public void setNullTool() {
        if (OC!=null) {
            OC.invalidate(this);
        }
        OC=new nullTool(this, OC);
        OC.showStatus(this);
        clearIndicated();
        clearPreview();
    }

//    private SelectTool SelectionTool=new SelectTool(this);
    public void setSelectTool() {
        if (OC!=null) {
            OC.invalidate(this);
        }
        OC=new SelectTool(this);
        OC.showStatus(this);
        clearIndicated();
        clearPreview();
    }

    public void bind(final PointObject p) {
        setTool(new BinderTool(this, p, OC));
    }

    public void setAway(final IntersectionObject p, final boolean away) {
        setTool(new SetAwayTool(this, p, away, OC));
    }

    public void setCurveCenter(final FunctionObject p) {
        setTool(new SetCurveCenterTool(this, p, OC));
    }

    public void range(final PrimitiveCircleObject c) {
        setTool(new SetRangeTool(this, c, OC));
    }

    public void set(final FixedAngleObject a) {
        setTool(new SetFixedAngle(this, a, OC));
    }

    public void set(final FixedCircleObject c) {
        setTool(new SetFixedCircle(this, c, OC));
    }

    public boolean enabled(final String function) {
        if (ZCI!=null) {
            return ZCI.enabled(function);
        } else {
            return true;
        }
    }

    public void pause(final boolean flag) {
        OC.pause(flag);
    }

    public void setReadOnly(final boolean flag) {
        ReadOnly=flag;
    }

    public void allowRightMouse(final boolean flag) {
        AllowRightMouse=flag;
    }

    public boolean changed() {
        return C.changed();
    }
    Image OldBackground=null;

    /**
     * Create a background image for the Movertool, consisting of the current
     * construction. This is called when moving with the control key is called.
     *
     * @param flag
     */
    public void grab(final boolean flag) {
        if (flag) {
            OldBackground=Background;
            Background=createImage(IW, IH);
            final Graphics G=Background.getGraphics();
            G.drawImage(I, 0, 0, this);
        } else {
            Background=OldBackground;
            OldBackground=null;
        }
        repaint();
    }

    public void setBackground(final Image i) {
        Background=i;
        repaint();
    }

    public void setInteractive(final boolean flag) {
        Interactive=flag;
    }

// Utilisé par le JPopup de selection des objets (ambiguité) et
// pour l'application d'outils à la liste de construction (ConstructionDisplayPanel)
    public void setConstructionObject(ConstructionObject o) {
        if (OC!=null) {
            NewPoint=false;
            OC.setConstructionObject(o, this);
        }
    }

    public ObjectConstructor getTool() {
        return OC;
    }
    MyVector Drawings=new MyVector();

    public synchronized void addDrawing(final Drawing d) {
        Drawings.addElement(d);
    }

    public synchronized void clearDrawings() {
        Drawings.removeAllElements();
        repaint();
    }

    public synchronized void paintDrawings(final MyGraphics g) {
        final Enumeration e=Drawings.elements();
        while (e.hasMoreElements()) {
            final Drawing d=(Drawing) e.nextElement();
            final Enumeration ec=d.elements();
            if (ec.hasMoreElements()) {
                g.setColor(ZirkelFrame.Colors[d.getColor()]);
                CoordinatesXY xy=(CoordinatesXY) ec.nextElement();
                int col=(int) col(xy.X), r=(int) row(xy.Y);
                while (ec.hasMoreElements()) {
                    xy=(CoordinatesXY) ec.nextElement();
                    final int c1=(int) col(xy.X), r1=(int) row(xy.Y);
                    g.drawLine(col, r, c1, r1);
                    col=c1;
                    r=r1;
                }
            }
        }
    }

    public void saveDrawings(final XmlWriter xml) {
        final Enumeration e=Drawings.elements();
        while (e.hasMoreElements()) {
            final Drawing d=(Drawing) e.nextElement();
            final Enumeration ec=d.elements();
            if (ec.hasMoreElements()) {
                xml.startTagNewLine("Draw", "color", ""+d.getColor());
                while (ec.hasMoreElements()) {
                    final CoordinatesXY xy=(CoordinatesXY) ec.nextElement();
                    xml.startTagStart("Point");
                    xml.printArg("x", ""+xy.X);
                    xml.printArg("y", ""+xy.Y);
                    xml.finishTagNewLine();
                }
                xml.endTagNewLine("Draw");
            }
        }
    }

    public void loadDrawings(final XmlTree tree) throws ConstructionException {
        XmlTag tag=tree.getTag();
        if (!tag.name().equals("Draw")) {
            return;
        }
        final Drawing d=new Drawing();
        try {
            if (tag.hasParam("color")) {
                d.setColor(Integer.parseInt(tag.getValue("color")));
            }
        } catch (final Exception e) {
            throw new ConstructionException("Illegal Draw Parameter");
        }
        final Enumeration e=tree.getContent();
        while (e.hasMoreElements()) {
            final XmlTree t=(XmlTree) e.nextElement();
            tag=t.getTag();
            if (tag.name().equals("Point")) {
                try {
                    final double x=new Double(tag.getValue("x")).doubleValue();
                    final double y=new Double(tag.getValue("y")).doubleValue();
                    d.addXY(x, y);
                } catch (final Exception ex) {
                    throw new ConstructionException("Illegal Draw Parameter");
                }
            }
        }
        Drawings.addElement(d);
    }
    int PointLast, LineLast, AngleLast;

    public void renameABC(final ConstructionObject o, final boolean enforce,
            final boolean reset) {
        if (!enforce) {
            if (o instanceof PointObject) {
                for (int i='A'; i<='Z'; i++) {
                    final ConstructionObject h=C.find(""+(char) i);
                    if (h==null) {
                        o.setName(""+(char) i);
                        o.setShowName(true);
                        repaint();
                        break;
                    }
                }
            } else if (o instanceof AngleObject||o instanceof FixedAngleObject) {
                for (int i='a'; i<='z'; i++) {
                    final ConstructionObject h=C.find("\\"+(char) i);
                    if (h==null) {
                        o.setName("\\"+(char) i);
                        o.setShowName(true);
                        repaint();
                        break;
                    }
                }
            } else if (o instanceof PrimitiveLineObject) {
                for (int i='a'; i<='z'; i++) {
                    final ConstructionObject h=C.find(""+(char) i);
                    if (h==null) {
                        o.setName(""+(char) i);
                        o.setShowName(true);
                        repaint();
                        break;
                    }
                }
            }
        } else {
            if (reset) {
                PointLast=0;
                LineLast=0;
                AngleLast=0;
            }
            if (o instanceof PointObject) {
                final String name=""+(char) ('A'+PointLast);
                final ConstructionObject h=C.find(name);
                if (h!=null&&h!=o) {
                    h.setName("***temp***");
                    final String s=o.getName();
                    o.setName(name);
                    h.setName(s);
                } else {
                    o.setName(name);
                }
                PointLast++;
            } else if (o instanceof AngleObject||o instanceof FixedAngleObject) {
                final String name="\\"+(char) ('a'+AngleLast);
                final ConstructionObject h=C.find(name);
                if (h!=null&&h!=o) {
                    h.setName("***temp***");
                    final String s=o.getName();
                    o.setName(name);
                    h.setName(s);
                } else {
                    o.setName(name);
                }
                AngleLast++;
            } else if (o instanceof PrimitiveLineObject) {
                final String name=""+(char) ('a'+LineLast);
                final ConstructionObject h=C.find(name);
                if (h!=null&&h!=o) {
                    h.setName("***temp***");
                    final String s=o.getName();
                    o.setName(name);
                    h.setName(s);
                } else {
                    o.setName(name);
                }
                LineLast++;
            }
        }
    }

    public void selectAllMoveableVisibleObjects() {
        final Enumeration e=C.elements();
        while (e.hasMoreElements()) {
            final ConstructionObject o=(ConstructionObject) e.nextElement();
            if (o instanceof MoveableObject&&((MoveableObject) o).moveable()&&!o.mustHide(this)) {
                o.setStrongSelected(true);
            }
        }
    }

    public void hideDuplicates(final ConstructionObject from) {
        final Enumeration e=C.elements();
        if (from!=null) {
            while (e.hasMoreElements()) {
                final ConstructionObject o=(ConstructionObject) e.nextElement();
                if (o==from) {
                    break;
                }
            }
        }
        while (e.hasMoreElements()) {
            final ConstructionObject o=(ConstructionObject) e.nextElement();
            if (o.mustHide(this)||o.isKeep()) {
                continue;
            }
            final Enumeration ex=C.elements();
            while (ex.hasMoreElements()) {
                final ConstructionObject o1=(ConstructionObject) ex.nextElement();
                if (o1==o) {
                    break;
                }
                if (o1.mustHide(this)||o1.isKeep()) {
                    continue;
                }
                if (o.equals(o1)) {
                    o.setHidden(true);
                    break;
                }
            }
        }
    }

    public void hideDuplicates() {
        hideDuplicates(null);
    }

    public void createEquationXY() {
        final EquationXYObject f=new EquationXYObject(C,
                "(x^2+y^2)^2-3*(x^2-y^2)");
        // EquationXYObject f=new EquationXYObject(C,"x+y");
        f.setDefaults();
        C.add(f);
        f.edit(this, true, false);
        repaint();
        reloadCD();
    }

    public void createCurve() {
        final FunctionObject f=new FunctionObject(C);
        f.setDefaults();
        C.add(f);
        f.setExpressions("x", "x", "x+1");
        f.edit(this, true, true);
        if (f.EditAborted) {
            delete(f);
        }
        repaint();
        reloadCD();
	update_distant(f, 1);
    }

    public void editMultipleSelection() {
        if (MultipleSelection.size()>0) {
            eric.bar.JPropertiesBar.EditObjects(MultipleSelection);
        }
    }

    public void editLast() {
        if (C.lastButN(0)==null) {
            return;
        }
        C.lastButN(0).edit(this, true, false);
    }

    public void breakpointLast(final boolean flag, final boolean hiding) {
        final ConstructionObject o=C.lastButN(0);
        if (o==null) {
            return;
        }
        if (hiding) {
            o.setHideBreak(flag);
        } else {
            o.setBreak(flag);
        }
    }

    @Override
    public void notifyChanged() {
        if (!C.Loading) {
            reloadCD();
            pipe_tools.TabHaveChanged(true);
        }
    }

    public void startWaiting() {
        Interactive=false;
        showMessage(Global.name("message.saving"));
    }

    public void endWaiting() {
        Interactive=true;
        hideMessage();
    }

    public void showMessage(final String s) {
    }

    public void hideMessage() {
    }
    // HotEqn stuff, requires the HotEqn classes:
//    sHotEqn HE=null;
//
//    public void setHotEqn(final String s) {
//        if (HE==null) {
//            HE=new sHotEqn(this);
//        }
//        HE.setEquation(s);
//    }
//
//    public int paintHotEqn(final int c, final int r, final Graphics g) {
//        if (HE==null) {
//            return 0;
//        }
//        return HE.paint(c, r, g);
//    }
    // Stuff for the permanent construction display
    private ConstructionDisplayPanel CDP=null;

    public void reloadCD() {
        if (CDP!=null&&C!=null) {
            CDP.reload();
        }
    }

    public void repaintCD() {
        if (CDP!=null&&C!=null) {
            CDP.updateDisplay();
        }
    }

    public ConstructionDisplayPanel getNewCDP() {
        CDP=null;
        CDP=new ConstructionDisplayPanel(this);
        reloadCD();
        return CDP;
    }

    public void removeCDP() {
        CDP=null;
    }

    public ConstructionDisplayPanel getCDP() {
        return CDP;
    }

    @Override
    public void mouseWheelMoved(final MouseWheelEvent e) {
        if (Global.getParameter("options.nomousezoom", false)) {
            return;
        }
        if (e.getScrollType()==MouseWheelEvent.WHEEL_BLOCK_SCROLL) {
            if (e.getWheelRotation()<0) {
                magnify(1/Math.sqrt(Math.sqrt(2)));
            } else {
                magnify(Math.sqrt(Math.sqrt(2)));
            }
        } else {
            final int n=e.getScrollAmount();
            if (e.getWheelRotation()<0) {
                magnify(1/Math.pow(2, n/12.));
            } else {
                magnify(Math.pow(2, n/12.));
            }
        }
    }

    public Dimension getOwnerWindowDim() {
        return new Dimension(OwnerWindowWidth, OwnerWindowHeight);
    }

    /**
     * @return the paintCalled
     */
    public boolean isPaintCalled() {
        return paintCalled;
    }

    /**
     * @param paintCalled the paintCalled to set
     */
    public void setPaintCalled(boolean b) {
        this.paintCalled=b;
    }

    /******************
     * PARTIE RESEAU  *
     ******************/

    public void set_cnt(ui.pm.Client.ClientNetworkTools cnt) {
        this.cnt = cnt;
    }

    public ui.pm.Client.ClientNetworkTools get_cnt(){
        return cnt;
    }

    /*
     * Pour que le panneau reste toujours au centre
     * de la fenêtre lors d'un redimensionnement
     */
    public void init_cnt(){
        if(cnt!=null) {
            cnt.init(this.getSize().width, this.getSize().height);
        }
    }
    
    public ZDialog updatable(){
	if(cnt!=null && cnt.get_real_time()){
	    return (ui.pm.Client.ClientNetworkTools) cnt;
	} else if(JGeneralMenuBar.get_scp()!=null && JGeneralMenuBar.get_scp().get_collaboration()){
	    return (ui.pm.Server.ServerControlPanel) JGeneralMenuBar.get_scp();
	}
        return null;
    }

    /**
     * @param i is :
     *	    0 to delete an object
     *	    1 to add
     *	    2 to update object by its parents (points)
     *	    3 to update object itself
     */
    public synchronized void update_distant(final ConstructionObject o, final int i) {
	final ZDialog ZD;
        if((ZD=updatable())==null) {
            return;
        }

	SwingUtilities.invokeLater(new Runnable() {
	    private String msg;
	    private Object obj;

	    @Override
	    public void run() {
	        try {
		    ByteArrayOutputStream bout = new ByteArrayOutputStream();
		    XmlWriter xml = new XmlWriter(new PrintWriter(new OutputStreamWriter(bout), true));
		    //xml.printXml();  //problème dans les unités d'angle
		    xml.printEncoding();
		    xml.startTagNewLine("Objects");

		    if(i==1) {
		        o.save(xml);
		        xml.endTagNewLine("Objects");
		        msg = "<To add>\n"+bout.toString();
		    } else if(i==0) {
		        o.save(xml);
		        xml.endTagNewLine("Objects");
		        msg = "<To delete>\n"+bout.toString();
		    } else if(i==2) {

		        if(o instanceof PointObject){
			    o.save(xml);
			} else {
			    Enumeration e = o.depending();
			    while(e.hasMoreElements()) {
			        if((obj = e.nextElement()) instanceof PointObject) {
				    ((PointObject) obj).save(xml);
				}
			    }
			}

			xml.endTagNewLine("Objects");
			msg = "<To update>\n"+bout.toString();
		    } else if(i==3) {
			o.save(xml);
			xml.endTagNewLine("Objects");
			msg = "<To update>\n"+bout.toString();
		    }
		    ZD.send(msg);
		} catch(Exception ex) {
		    System.err.println("BUG ZC : "+ex);
		}
	    }
	});
    }
    
    public synchronized void update_distant(final String s) {
        final ZDialog ZD;
        if((ZD=updatable())==null) {
            return;
        }
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
	    public void run() {
                ZD.send("<To interpret>\n"+s+"\n");
            }
        });
    }

    public synchronized void update_distant(final String old_name, final String new_name) {
	final ZDialog ZD;
        if((ZD=updatable())==null) {
            return;
        }

	ZD.send("<To change name>\n"+old_name+";"+new_name+"\n"+"");
    }

    public void update_local(String src) {
	try {
	    if(src.startsWith("<To change name>")) {
		src = src.replace("<To change name>\n", "");
		String name[] = src.split(";");
		this.getConstruction().find(name[0]).setName(name[1].replace("\n", ""));
	    } else if (src.startsWith("<To interpret>")) {
                C.interpret(this, src.replace("<To interpret>\n", ""), "");
            } else {
		final ByteArrayOutputStream bout = new ByteArrayOutputStream();
		final PrintWriter out = new PrintWriter(new OutputStreamWriter(bout, "utf-8"));
		out.print(src);
		out.close();
		final byte b[] = bout.toByteArray();
		final InputStream IN = new ByteArrayInputStream(b);

		final XmlReader xml = new XmlReader();
		xml.init(IN);
		XmlTree tree = xml.scan();

		Enumeration root = tree.getContent();
		do {
		    tree = (XmlTree) root.nextElement();
		} while(root.hasMoreElements() && !tree.getTag().name().equals("Objects"));

		if(src.startsWith("<To add>") || src.startsWith("<Global>")) {
		    this.getConstruction().readConstruction(tree, false);
		} else { //delete, update
		    tree = (XmlTree) tree.getContent();
		    while(tree.hasMoreElements()) {
			XmlTree t = (XmlTree) tree.nextElement();
			String name = t.getTag().getValue("name");
			ConstructionObject o = this.getConstruction().find(name);
			if(src.startsWith("<To delete>")){
			    this.delete(o);
			} else { //update
                            XmlTag tag = t.getTag();
                            
			    o.setHidden(tag.hasParam("hidden"));
			    o.setShowName(tag.hasParam("showname"));
			    o.setShowValue(tag.hasParam("showvalue"));
			    o.setTracked(tag.hasParam("tracked"));
			    o.setColor(tag.hasParam("color")?Integer.parseInt(tag.getValue("color")):0);
			    o.setFixed(tag.hasParam("fixed"));
                            
                            //for Text
                            Enumeration e = t.getContent();
                            while(e.hasMoreElements()) {
                                XmlTree xt = (XmlTree) e.nextElement();
                                if(xt.getTag() instanceof XmlTagText) {
                                    o.setLines(((XmlTagText) xt.getTag()).getContent());
                                    o.setText(((XmlTagText) xt.getTag()).getContent(), true);
                                }
                            }
                            
                            // Conditionals
                            o.clearConditionals();
                            int i = 0;
                            while(tag.hasParam("ctag"+i) && tag.hasParam("cexpr"+i)) {
                                o.addConditional(tag.getValue("ctag"+i), new Expression(tag.getValue("cexpr"+i), C, o));
                                i++;
                            }
                            
			    if(o instanceof FunctionObject){
				((FunctionObject) o).setExpressions(tag.getValue("var"), tag.getValue("x"), tag.getValue("y"));
			    } else {
				if(o.fixed()) {
					o.setFixed(tag.getValue("x"), tag.getValue("y"));
				    } else {
					o.move(Double.valueOf(tag.getValue("x")), Double.valueOf(tag.getValue("y")));
				    }
			    }
			    this.dovalidate();
			}
		    }
		}
	    }
	    this.repaint();
	} catch (Exception e){}
    }
}
