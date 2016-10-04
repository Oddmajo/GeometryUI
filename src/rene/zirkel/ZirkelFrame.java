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
package rene.zirkel;

// file: ZirkelFrame.java
import eric.GUI.pipe_tools;
import java.awt.CheckboxMenuItem;
import java.awt.Color;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Vector;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import eric.JEricPanel;

import rene.dialogs.MyFileDialog;
import rene.dialogs.Question;
import rene.dialogs.Warning;
import rene.gui.Global;
import rene.util.FileName;
import rene.util.MyVector;
import rene.util.parser.StringParser;
import rene.zirkel.construction.Construction;
import rene.zirkel.construction.ConstructionException;
import rene.zirkel.construction.Count;
import rene.zirkel.constructors.AngleConstructor;
import rene.zirkel.constructors.AreaConstructor;
import rene.zirkel.constructors.BoundedPointConstructor;
import rene.zirkel.constructors.Circle3Constructor;
import rene.zirkel.constructors.CircleConstructor;
import rene.zirkel.constructors.ExpressionConstructor;
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
import rene.zirkel.listener.DoneListener;
import rene.zirkel.listener.StatusListener;
import rene.zirkel.macro.Macro;
import rene.zirkel.macro.MacroRunner;
import rene.zirkel.objects.ConstructionObject;
import rene.zirkel.objects.ExpressionObject;
import rene.zirkel.objects.PointObject;
import rene.zirkel.objects.PrimitiveCircleObject;
import rene.zirkel.objects.PrimitiveLineObject;
import rene.zirkel.objects.SegmentObject;
import rene.zirkel.tools.AnimatorTool;
import rene.zirkel.tools.BreakpointAnimator;
import rene.zirkel.tools.DeleteTool;
import rene.zirkel.tools.DrawerTool;
import rene.zirkel.tools.EditTool;
import rene.zirkel.tools.HiderTool;
import rene.zirkel.tools.MoverTool;
import rene.zirkel.tools.ObjectTracker;
import rene.zirkel.tools.RenamerTool;
import rene.zirkel.tools.ReorderTool;
import rene.zirkel.tools.SaveJob;
import rene.zirkel.tools.SetParameterTool;
import rene.zirkel.tools.SetTargetsTool;
import rene.zirkel.tools.Tracker;
import rene.zirkel.tools.ZoomerTool;
import rene.zirkel.tools.JLocusObjectTracker;
import eric.GUI.window.tab_bottom;
import eric.JZirkelCanvas;
import eric.Media;
import java.awt.Frame;
import rene.zirkel.constructors.VectorConstructor;


public class ZirkelFrame implements StatusListener,
        DoneListener, ZirkelCanvasInterface {

    /**
     *
     */

    public ZirkelCanvas ZC;
    public static Frame FRM;
    public String Filename="";
    public String Background="";
    // color menu items:
    static Color DefaultColors[]={Color.black,
        Color.green.darker().darker(), Color.blue.darker(),
        new Color(150, 100, 0), Color.cyan.darker().darker(),
        new Color(180, 0, 0)};
    public static Color Colors[]=DefaultColors;
    public static Color LightColors[];
    public static Color BrighterLightColors[];
    public static Color BrighterColors[];
    public static Color SelectColor=Global.getParameter("colorselect",
            Color.red);
    public static Color IndicateColor=Global.getParameter("colorselect",
            Color.ORANGE);
    public static Color TargetColor=Global.getParameter("colortarget",
            Color.pink);
    public static String ColorStrings[]={"black", "green", "blue", "brown",
        "cyan", "red"};
    public static String PointTypes[]={"square", "diamond", "circle", "dot",
        "cross", "dcross"};
    public static String ColorTypes[]={"normal", "thick", "thin"};
    public static int ColorTypeKeys[]={KeyEvent.VK_5, KeyEvent.VK_6,
        KeyEvent.VK_7};
    CheckboxMenuItem ColorTypeMenuItems[]=new CheckboxMenuItem[ColorTypes.length];
    public static int ColorKeys[]={KeyEvent.VK_1, KeyEvent.VK_2,
        KeyEvent.VK_3, KeyEvent.VK_4};
    CheckboxMenuItem ColorMenuItems[]=new CheckboxMenuItem[ColorStrings.length];
    CheckboxMenuItem ShowColorMenuItems[]=new CheckboxMenuItem[ColorStrings.length];
    static int PointKeys[]={KeyEvent.VK_5, KeyEvent.VK_6, KeyEvent.VK_7,
        KeyEvent.VK_8, KeyEvent.VK_9, KeyEvent.VK_0};
    CheckboxMenuItem PointMenuItems[]=new CheckboxMenuItem[PointTypes.length];
    final public static String Separators[]= // names
            {"point", "boundedpoint", "intersection", "!line", "ray", "segment",
        "fixedsegment", "!circle", "circle3", "fixedcircle", "!parallel",
        "plumb", "midpoint", "!angle", "fixedangle", "!move", "tracker",
        "objecttracker", "animate", "!expression", "area", "quadric",
        "image", "text", "!hide", "locus", "runmacro", "edit", "parameter",
        "targets", "definejob", "delete", "reorder", "draw", "rename",
        "zoom", "animatebreak"};
    final public static String MenuTitles[]={"points", "lines", "circles",
        "complex", "angles", "move", "decorative"};
    final public static int IconNumber=27;
    final public static int NEdit=IconNumber, NParameters=IconNumber+1,
            NTargets=IconNumber+2, NDefineJob=IconNumber+3,
            NDelete=IconNumber+4, NReorder=IconNumber+5,
            NDraw=IconNumber+6, NRename=IconNumber+7,
            NZoom=IconNumber+8, NAnimateBreak=IconNumber+9,
            NLocus=IconNumber+10;
    final public static int NAnimator=18, NObjectTracker=17, NTracker=16,
            NMover=15;
    final public static int NMacro=IconNumber-1;
    final public static String ObjectStrings[]= // names
            {"point", "boundedpoint", "intersection", "line", "ray", "segment",
        "fixedsegment", "circle", "circle3", "fixedcircle", "parallel",
        "plumb", "midpoint", "angle", "fixedangle", "move", "tracker",
        "objecttracker", "animate", "expression", "area", "quadric",
        "image", "text", "hide", "locus", "runmacro", "edit", "parameter",
        "targets", "definejob", "delete", "reorder", "draw", "rename",
        "zoom", "animatebreak", "vector"};
    public static ObjectConstructor ObjectConstructors[]= // constructors
            {new PointConstructor(), new BoundedPointConstructor(),
        new IntersectionConstructor(), new LineConstructor(),
        new RayConstructor(), new SegmentConstructor(),
        new SegmentConstructor(true), new CircleConstructor(),
        new Circle3Constructor(), new CircleConstructor(true),
        new ParallelConstructor(), new PlumbConstructor(),
        new MidpointConstructor(), new AngleConstructor(),
        new AngleConstructor(true), new MoverTool(), new Tracker(),
        new ObjectTracker(), new AnimatorTool(),
        new ExpressionConstructor(), new AreaConstructor(),
        new QuadricConstructor(), new ImageConstructor(),
        new TextConstructor(), new HiderTool(), new JLocusObjectTracker(),
        new MacroRunner(), new EditTool(), new SetParameterTool(),
        new SetTargetsTool(), new SaveJob(), new DeleteTool(),
        new ReorderTool(), new DrawerTool(), new RenamerTool(),
        new ZoomerTool(), new BreakpointAnimator(), new VectorConstructor()};
    CheckboxMenuItem ObjectMenuItems[]=new CheckboxMenuItem[ObjectConstructors.length]; // menu
    // checkbos
    // items
    static char ObjectKeys[];
    public int CurrentTool=0; // current tool
    public boolean IsApplet;
    JEricPanel North, Center, MainPanel;
    JEricPanel StatusPanel, InputPanel;
    JEricPanel CenterPanel;
    boolean Init=false;
    boolean SawPreviewWarning=false;
    MyFileDialog FileLoad, FileSave, PicSave, HTMLSave, BackgroundLoad,
            ImageLoad, TemplateLoad;

    // The file dialogs
    // public LogoWindow Logo;
    public ZirkelFrame(final boolean applet) {
        IsApplet=applet;
        FRM=pipe_tools.getFrame();
        // the canvas
        ZC=new ZirkelCanvas();

        ZC.setBackground(Global.getParameter("colorbackground", new Color(245,245,245)));

        ZC.addMouseListener(ZC);
        ZC.addMouseMotionListener(ZC);
        ZC.setZirkelCanvasListener(this);


        ZC.setTool(ObjectConstructors[CurrentTool]);

        ZC.addStatusListener(this);
        ZC.showStatus();



        // init various things
        initLightColors();


        // initialize choices
        settool(0);
        setcolor(Global.getParameter("options.color", 0));
        settype(Global.getParameter("options.type", 2));
        setcolortype(Global.getParameter("options.colortype", 0));
        showcolor(0);

        setRestricted(Global.getParameter("options.restricted", true));
        setPartial(Global.getParameter("options.partial", false));
        setPartialLines(Global.getParameter("options.plines", false));
        setVectors(Global.getParameter("options.arrow", false));
        setShowNames(Global.getParameter("options.shownames", false));
        setShowValues(Global.getParameter("options.showvalues", false));
        setLongNames(Global.getParameter("options.longnames", false));
        setLargeFont(Global.getParameter("options.largefont", false));
        setBoldFont(Global.getParameter("options.boldfont", false));
        setObtuse(Global.getParameter("options.obtuse", false));
        setSolid(Global.getParameter("options.solid", false));

        initFileDialogs();

    }


    final static public String DefaultIcons=" new load save back undo delete color type thickness"+" hidden showcolor macro grid comment replay"+" point line segment ray circle fixedcircle"+" parallel plumb circle3 midpoint angle fixedangle"+" move tracker objecttracker hide expression area text quadric"+" runmacro edit animate "+" info zoom draw function rename ";
    final static public String DefaultRestrictedIcons=" back undo color"+" hidden showcolor macro grid comment"+" point line segment ray circle"+" parallel plumb circle3 midpoint angle fixedangle"+" move tracker objecttracker hide area text quadric"+" runmacro zoom info "+" ";



    public boolean icon(final String s) {
        return Global.getParameter("icons", "none").indexOf(" "+s+" ")>=0;
    }

    public boolean enabled(final String s) {
        return !Global.getParameter("restricted", false)||icon(s);
    }

    public void initFileDialogs() {

            FileLoad=new MyFileDialog(FRM, Global.name("filedialog.open"),
                    false);
            FileLoad.setPattern("*.zir *.job *.zirz *.jobz");
            FileSave=new MyFileDialog(FRM, Global.name("filedialog.saveas"),
                    true);
            FileSave.setPattern("*.zir *.job *.zirz *.jobz");
            PicSave=new MyFileDialog(FRM, Global.name("filedialog.saveas"),
                    true);
            PicSave.setPattern("*");
            HTMLSave=new MyFileDialog(FRM, Global.name("filedialog.htmlsave"), true);
            HTMLSave.setPattern("*.html *.htm");
            BackgroundLoad=new MyFileDialog(FRM, Global.name("filedialog.backgroundload"), false);
            BackgroundLoad.setPattern("*.gif *.jpg");
            ImageLoad=new MyFileDialog(FRM, Global.name("filedialog.imageload"), false);
            ImageLoad.setPattern("*.gif *.jpg");
            TemplateLoad=new MyFileDialog(FRM, Global.name("templateload.open"), false);
            TemplateLoad.setPattern("*.template");
    }

    public static void initLightColors(Color back) {
        final int n=DefaultColors.length;
        Colors=new Color[DefaultColors.length];
        for (int i=0; i<n; i++) {
            if (Global.haveParameter("color"+i)) {
                Colors[i]=Global.getParameter("color"+i, Color.black);
            } else {
                Colors[i]=DefaultColors[i];
            }
        }
        LightColors=new Color[n];
        BrighterLightColors=new Color[n];
        BrighterColors=new Color[n];
        if (back==null) {
            back=Color.gray.brighter();
        }
        final int red=back.getRed(), green=back.getGreen(), blue=back.getBlue();
        final double lambda=0.4;
        for (int i=0; i<n; i++) {
            final int r=(int) (red*(1-lambda)+Colors[i].getRed()*lambda);
            final int g=(int) (green*(1-lambda)+Colors[i].getGreen()*lambda);
            final int b=(int) (blue*(1-lambda)+Colors[i].getBlue()*lambda);
            LightColors[i]=new Color(r, g, b);
            if (i==0) {
                BrighterColors[i]=Color.gray;
            } else {
                BrighterColors[i]=Colors[i].brighter();
            }
            BrighterLightColors[i]=LightColors[i].brighter();
        }
    }

    public void initLightColors() {
        initLightColors(Color.white);
    }





    public void clear(final boolean defaults) {
        ZC.clear();
        Count.resetAll();
        clearsettings(defaults);
        ZC.clearDrawings();
        ZC.repaint();
    }

    public void clearsettings(final boolean defaults) {
        if (defaults) {
            settool(0);
            setcolor(0);
            setcolortype(0);
            settype(2);
            showcolor(0);
            setRestricted(true);
            setPartial(false);
            setPartialLines(false);
            setVectors(false);
            setShowNames(false);
            setShowValues(false);
            setLongNames(false);
            setLargeFont(false);
            setBoldFont(false);
            setObtuse(false);
            setSolid(false);
            setVisual(true);
        } else {
            settool(0);
            setcolor(Global.getParameter("options.color", 0));
            setcolortype(Global.getParameter("options.colortype", 0));
            settype(Global.getParameter("options.type", 2));
            showcolor(0);
            setRestricted(Global.getParameter("options.restricted", true));
            setPartial(Global.getParameter("options.partial", false));
            setPartialLines(Global.getParameter("options.plines", false));
            setVectors(Global.getParameter("options.arrow", false));
            setShowNames(Global.getParameter("options.shownames", false));
            setShowValues(Global.getParameter("options.showvalues", false));
            setLongNames(Global.getParameter("options.longnames", false));
            setLargeFont(Global.getParameter("options.largefont", false));
            setBoldFont(Global.getParameter("options.boldfont", false));
            setObtuse(Global.getParameter("options.obtuse", false));
            setSolid(Global.getParameter("options.solid", false));
            setVisual(Global.getParameter("options.visual", true));
        }
    }

    public void clearsettings() {
        clearsettings(false);
    }

    public void loadsettings() {
        setcolor(ZC.getDefaultColor());
        settype(ZC.getDefaultType());
        setcolortype(ZC.getDefaultColorType());
        setPartial(ZC.getPartial());
        setPartialLines(ZC.getPartialLines());
        setVectors(ZC.getVectors());
        setShowNames(ZC.getConstruction().ShowNames);
        setShowValues(ZC.getConstruction().ShowValues);
    }


    public void itemAction(final String o, boolean flag) // interpret checkbox
    // changes
    {
//        for (int i=0; i<ObjectMenuItems.length; i++) {
//            if (o.equals(ObjectStrings[i])) {
//                if (i==NMacro) {
//                    setinfo("runmacro");
//                    runMacro(false);
//                } else {
//                    setinfo(ObjectStrings[i]);
//                    settool(i);
//                }
//                return;
//            }
//        }
//        for (int i=0; i<ColorMenuItems.length; i++) {
//            if (o.equals("cs-"+ColorStrings[i])) {
//                setcolor(i);
//                setinfo("defaults");
//                return;
//            }
//        }
//        for (int i=0; i<ShowColorMenuItems.length; i++) {
//            if (o.equals("scs-"+ColorStrings[i])) {
//                showcolor(i);
//                setinfo("show");
//                return;
//            }
//        }
//        for (int i=0; i<PointMenuItems.length; i++) {
//            if (o.equals("pt-"+PointTypes[i])) {
//                settype(i);
//                setinfo("defaults");
//                return;
//            }
//        }
//        for (int i=0; i<ColorTypeMenuItems.length; i++) {
//            if (o.equals("ct-"+ColorTypes[i])) {
//                setcolortype(i);
//                setinfo("defaults");
//                return;
//            }
//        }
//        if (o.equals("menu.options.hidden")) {
//            sethidden(flag);
//            ZC.reloadCD();
//            setinfo("hide");
//        } else if (o.equals("menu.file.includemacros")) {
//            Global.setParameter("save.includemacros", flag);
//            setinfo("save");
//        } else if (o.equals("menu.file.alwaysclearmacros")) {
//            Global.setParameter("load.clearmacros", flag);
//            setinfo("save");
//        } else if (o.equals("menu.options.visual")) {
//            setVisual(flag);
//            setShowNames(!flag);
//            setinfo("visual");
//        } else if (o.equals("menu.options.printscalepreview")) {
//            if (flag) {
//                final ExportScaler d=new ExportScaler(this, true);
//                if (d.isAborted()) {
//                    flag=false;
//                }
//            }
//            Global.setParameter("printscalepreview", flag);
//            ZC.newImage();
//            setinfo("print");
//        } else if (o.equals("menu.file.compress")) {
//            Global.setParameter("save.compress", flag);
//            setinfo("save");
//        } else if (o.equals("menu.options.partial")) {
//            setPartial(flag);
//            setinfo("defaults");
//        } else if (o.equals("menu.options.restricted")) {
//            setRestricted(flag);
//            setinfo("defaults");
//        } else if (o.equals("menu.options.plines")) {
//            setPartialLines(flag);
//            setinfo("defaults");
//        } else if (o.equals("menu.options.arrow")) {
//            setVectors(flag);
//            setinfo("defaults");
//        } else if (o.equals("menu.options.longnames")) {
//            setLongNames(flag);
//            setinfo("defaults");
//        } else if (o.equals("menu.options.largefont")) {
//            setLargeFont(flag);
//            setinfo("defaults");
//        } else if (o.equals("menu.options.boldfont")) {
//            setBoldFont(flag);
//            setinfo("defaults");
//        } else if (o.equals("menu.options.shownames")) {
//            setShowNames(flag);
//            setinfo("defaults");
//        } else if (o.equals("menu.options.obtuse")) {
//            setObtuse(flag);
//            setinfo("defaults");
//        } else if (o.equals("menu.options.solid")) {
//            setSolid(flag);
//            setinfo("defaults");
//        } else if (o.equals("menu.options.showvalues")) {
//            setShowValues(flag);
//            setinfo("defaults");
//        } else if (o.equals("menu.options.grid")) {
//            toggleGrid();
//            setinfo("grid");
//        } else if (o.equals("menu.options.leftsnap")) {
//            Global.setParameter("grid.leftsnap", flag);
//            setinfo("grid");
//            ZC.repaint();
//        } else if (o.equals("menu.background.tile")) {
//            Global.setParameter("background.tile", flag);
//            ZC.repaint();
//            setinfo("background");
//        } else if (o.equals("menu.background.usesize")) {
//            Global.setParameter("background.usesize", flag);
//            resize();
//            setinfo("background");
//        } else if (o.equals("menu.background.usewidth")) {
//            Global.setParameter("background.usewidth", flag);
//            resize();
//            setinfo("background");
//        } else if (o.equals("menu.background.center")) {
//            Global.setParameter("background.center", flag);
//            ZC.repaint();
//            setinfo("background");
//        } else if (o.equals("menu.background.usesize")) {
//            Global.setParameter("background.usesize", flag);
//            if (flag) {
//                resize();
//            }
//            setinfo("background");
//        } else if (o.equals("menu.special.testjob")) {
//            testjob(flag);
//            if (flag&&!ZC.getConstruction().getComment().equals("")) {
//                showcomment();
//            }
//            setinfo("assignments");
//        } else if (o.equals("menu.settings.constructiondisplay")) {
//            Global.setParameter("options.showdisplay", flag);
//            showConstructionDisplay(flag);
//            setinfo("constructiondisplay");
//        } else if (o.equals("menu.settings.font.bold")) {
//            Global.setParameter("font.bold", flag);
//            ZC.resetGraphics();
//            setinfo("fonts");
//        } else if (o.equals("menu.settings.font.large")) {
//            Global.setParameter("font.large", flag);
//            ZC.resetGraphics();
//            setinfo("fonts");
//        } else if (o.equals("menu.settings.iconbartop")) {
//            Global.setParameter("options.iconbartop", flag);
//            remove(MainPanel);
//            makeMainPanel();
//            add("Center", MainPanel);
//            validate();
//            setinfo("iconbar");
//        } else if (o.equals("menu.settings.restricted")) {
//            Global.setParameter("restricted", flag);
//            warning(Global.name("warning.reset"));
//            setinfo("restricted");
//        } else if (o.equals("menu.settings.beginner")) {
//            Global.setParameter("beginner", flag);
//            if (flag) {
//                Global.setParameter("options.indicate", true);
//                Global.setParameter("options.indicate.simple", true);
//                Global.setParameter("options.pointon", true);
//                Global.setParameter("options.intersection", true);
//                Global.setParameter("options.choice", true);
//                Global.setParameter("showtips", true);
//                Global.setParameter("restrictedicons", DefaultRestrictedIcons);
//                Global.setParameter("saveicons", Global.getParameter("icons",
//                        DefaultIcons));
//                Global.setParameter("icons", DefaultRestrictedIcons);
//            } else {
//                Global.setParameter("options.indicate", true);
//                Global.setParameter("options.indicate.simple", false);
//                Global.setParameter("options.pointon", false);
//                Global.setParameter("options.intersection", false);
//                Global.setParameter("icons", Global.getParameter("saveicons",
//                        DefaultIcons));
//            }
//            setinfo("beginner");
//        } else if (o.equals("menu.special.macrobar")) {
//            Global.setParameter("macrobar", flag);
//            setinfo("macrobar");
//        } else if (o.equals("menu.special.defaultmacrosinbar")) {
//            Global.setParameter("defaultmacrosinbar", flag);
//            setinfo("macrobar");
//            updateMacroBar();
//        } else if (o.equals("menu.file.print.isoscale")) {
//            Global.setParameter("print.isoscale", flag);
//            setinfo("background");
//        } else if (o.equals("menu.special.restricticons")) {
//            restrictIcons(flag);
//            setinfo("restricted");
//        }
    }



    public void settool(String o) {
        ZC.pause(true);
        ZC.requestFocus();
        for (int i=0; i<ObjectStrings.length; i++) {
            if (o.equals(ObjectStrings[i])) {
                settool(i);
            }
        }
        ZC.pause(false);
        ZC.requestFocus();
    }

    /**
     * Choose the tool i and set the icons and menu entries.
     */
    public void settool(final int i) {
        CurrentTool=i;
        /*
         * The if condition is necessary to make the macros run
         * when selected form the menu bar because
         * ZC is NOT the CurrentZC ???
         */
        if(JZirkelCanvas.getCurrentZC()!=null) {
            JZirkelCanvas.getCurrentZC().setTool(ObjectConstructors[i]);
        } else {
            ZC.setTool(ObjectConstructors[i]);
        }
        ObjectConstructors[i].resetFirstTime(ZC);
    }

    public int getCurrentTool(){
        return CurrentTool;
    }

    public void sethidden (boolean flag)
	{
        ZC.setShowHidden(flag);
	}

    public void setcolor(final int c) {
        ZC.setDefaultColor(c);
        Global.setParameter("options.color", c);
    }

    public void settype(final int c) {
        ZC.setDefaultType(c);
        Global.setParameter("options.type", c);
    }

    public void setcolortype(int c) {
        ZC.setDefaultColorType(c);
        Global.setParameter("options.colortype", c);
    }

    public void showcolor(final int c) {
        ZC.setShowColor(c);
    }

    public void showStatus(final String s) {
        if (tab_bottom.getStatus().equals(s)) {
            return;
        }
        try {
//            Status.setText(JZirkelCanvas.FilteredStatus(s));
            tab_bottom.showStatus(JZirkelCanvas.FilteredStatus(s));
        } catch (final Exception e) {
        }

    }

    public void save() {
        if (!haveFile()) {
            saveas();
        } else {
            dosave(Filename, true, Global.getParameter("save.includemacros",
                    false), false, true, ZC.getMacros());
        }
    }

    public static boolean isCompressed(final String filename) {
        return FileName.extension(filename).endsWith("z");
    }

    public boolean dosave(final String Filename, final boolean construction,
            final boolean macros, final boolean protectedmacros, boolean scripts, final Vector v) {
        if (Global.getParameter("options.backups", true)&&exists(Filename)) {
            final File F=new File(Filename);
            final File Back=new File(Filename+".bak");
            try {
                if (Back.exists()) {
                    Back.delete();
                }
                F.renameTo(Back);
            } catch (final Exception e) {
                final Warning w=new Warning(FRM, Global.name("warning.save.backup"), FileName.chop(32, e.toString(), 64), Global.name("warning"), true);
                w.center(FRM);
                w.setVisible(true);
                return false;
            }
        }
        try {
            OutputStream o=new FileOutputStream(Filename);
            if (isCompressed(Filename)) {
                o=new GZIPOutputStream(o, 10000);
            }
            ZC.getConstruction().BackgroundFile=Background;
            ZC.getConstruction().ResizeBackground=Global.getParameter(
                    "background.usesize", false);
            ZC.save(o, construction, macros, protectedmacros, scripts, v, "");
            o.close();
            if (construction) {
                FRM.setTitle(Global.name("program.name")+" : "+FileName.chop(Filename));
            }
        } catch (final FileNotFoundException ef) {
            return false;
        } catch (final Exception e) {
            final Warning w=new Warning(FRM, Global.name("warning.save"),
                    FileName.chop(32, e.toString(), 64),
                    Global.name("warning"), true);
            w.center(FRM);
            w.setVisible(true);
            return false;
        }
        return true;
    }

    public boolean saveas(final String pattern, final String ext) {
        FileSave.center(FRM);
        if (haveFile()) {
            FileSave.setDirectory(FileName.path(Filename));
            FileSave.setFilePath(FileName.filename(Filename));
        }
        FileSave.setPattern(Global.getParameter("pattern", pattern));
        FileSave.update(!haveFile());
        FileSave.setVisible(true);
        if (FileSave.isAborted()) {
            return false;
        }
        String filename=FileSave.getFilePath();
        if (FileName.extension(filename).equals("")) {
            filename=filename+ext;
        }
        if (Global.getParameter("options.filedialog", true)&&exists(filename)) {
            final Question d=new Question(FRM, FileName.filename(filename)+" : "+Global.name("file.exists.overwrite"), Global.name("file.exists.title"), this, false, true);
            d.center(FRM);
            d.setVisible(true);
            if (!d.yes()) {
                return false;
            }
        }
        Filename=filename;
        return dosave(Filename, true, Global.getParameter("save.includemacros",
                false), false, true, ZC.getMacros());
    }

    public boolean saveas() {
        return saveas("*.zir *.zirz *.job *.jobz", Global.getParameter(
                "save.compress", false)?".zirz":".zir");
    }

    public boolean exists(final String filename) {
        final File f=new File(filename);
        return f.exists();
    }

    public boolean savefile() {
        if (!haveFile()) {
            return saveas();
        } else {
            return dosave(Filename, true, Global.getParameter(
                    "save.includemacros", false), false, true, ZC.getMacros());
        }
    }

    public void saveMacros() {
        final Vector v=ZC.chooseMacros();
        if (v==null||v.size()==0) {
            return;
        }
        FileSave.center(FRM);
        FileSave.setPattern(Global.getParameter("pattern.macro", "*.mcr *mcrz"));
        FileSave.update();
        FileSave.setVisible(true);
        if (FileSave.isAborted()) {
            return;
        }
        String Filename=FileSave.getFilePath();
        if (FileName.extension(Filename).equals("")) {
            Filename=Filename+(Global.getParameter("save.compress", false)?".mcrz"
                    :".mcr");
        }
        if (Global.getParameter("options.filedialog", true)&&exists(Filename)) {
            final Question d=new Question(FRM, FileName.filename(Filename)+" : "+Global.name("file.exists.overwrite"), Global.name("file.exists.title"), this, false, true);
            d.center(FRM);
            d.setVisible(true);
            if (!d.yes()) {
                return;
            }
        }
        dosave(Filename, false, true, true, false, v);
    }

    public void deleteMacros() {
        final Vector v=ZC.chooseMacros();
        if (v==null||v.size()==0) {
            return;
        }
        ZC.deleteMacros(v);
    }

    public void renameMacro() {

    }

    public void clearMacros() {

    }

    public void clearNonprotectedMacros() {

    }



    public void doload(final String name, final InputStream in) { // System.out.println("load "+name);
        try {

            Filename=name;
            InputStream o=null;
            if (in==null) {
                o=new FileInputStream(name);
                if (isCompressed(name)) {
                    o=new GZIPInputStream(o);
                }
            } else {
                o=in;
            }
            clear(false);
            ZC.startWaiting();
            ZC.load(o);
            ZC.endWaiting();
            o.close();
            FRM.setTitle(Global.name("program.name")+" : "+FileName.chop(name));
            FRM.setEnabled(true);
            if (!ZC.getConstruction().getComment().equals("")) {
                showcomment();
            }

        } catch (final Exception e) {
            final Warning w=new Warning(FRM, Global.name("warning.load"),
                    FileName.chop(32, e.toString(), 64),
                    Global.name("warning"), true);
            w.center(FRM);
            w.setVisible(true);
            ZC.endWaiting();
//             e.printStackTrace();
            return;
        }
        // System.out.println("finished loading "+name);
        // System.out.println("finished setting icons");
        if (ZC.getConstruction().BackgroundFile!=null) {
//        if ((in==null)&&(ZC.getConstruction().BackgroundFile!=null)) {
//             System.out.println("setting background:"+ZC.getConstruction().BackgroundFile);

            doloadBackground(JZirkelCanvas.getFilePath(ZC.getConstruction())+ZC.getConstruction().BackgroundFile);


//            final String backgroundfile=ZC.getConstruction().BackgroundFile;
//            String file=backgroundfile;
//            if (FileName.path(backgroundfile).equals("")) {
//                file=FileName.path(name)+File.separator+backgroundfile;
//            }
//            Global.setParameter("background.usesize",
//                    ZC.getConstruction().ResizeBackground);
//            doloadBackground(file);



        }
        final Construction C=ZC.getConstruction();
        if (C.TrackP!=null) { // System.out.println("setting track");
            try {
                final ConstructionObject P=C.find(C.TrackP);
                if (P==null||!((P instanceof PointObject)||(P instanceof PrimitiveLineObject))) {
                    throw new ConstructionException("");
                }
                PointObject PM=null;
                if (C.find(C.TrackPM)!=null) {
                    PM=(PointObject) C.find(C.TrackPM);
                }
                final ConstructionObject po[]=new ConstructionObject[C.TrackPO.size()];
                for (int i=0; i<po.length; i++) {
                    final ConstructionObject o=C.find((String) C.TrackPO.elementAt(i));
                    if (o==null||!((o instanceof PointObject)||(o instanceof PrimitiveLineObject))) {
                        throw new ConstructionException("");
                    }
                    po[i]=o;
                }
                if (C.TrackO!=null) {
                    final ConstructionObject O=C.find(C.TrackO);

                    if (P==null||(PM==null&&!(O instanceof ExpressionObject))||O==null) {
                        throw new ConstructionException("");
                    }
                    settool(NObjectTracker);
                    final ObjectTracker TR=new ObjectTracker(P, PM, O, ZC,
                            C.Animate, C.Paint, po);
                    if (C.Omit>0) {
                        TR.setOmit(C.Omit);
                    }
                    ZC.setTool(TR);
                    ZC.validate();
                    ZC.repaint();
                } else {
                    if (P==null) {
                        throw new ConstructionException("");
                    }
                    settool(NTracker);
                    ZC.setTool(new Tracker(P, po));

                    if (PM!=null) {
                        PM.setSelected(true);
                    }
                    ZC.validate();
                    ZC.repaint();
                }
            } catch (final Exception e) {
                warning(Global.name("exception.track"));
            }
        } else if (C.AnimateP!=null) { // System.out.println("setting animation");
            try {
                final PointObject P=(PointObject) C.find(C.AnimateP);
                if (P==null) {
                    throw new ConstructionException("");
                }
                final Enumeration e=C.AnimateV.elements();
                while (e.hasMoreElements()) {
                    final String s=(String) e.nextElement();
                    final ConstructionObject o=C.find(s);
                    if (o==null||!(o instanceof SegmentObject||o instanceof PrimitiveCircleObject||o instanceof PointObject)) {
                        throw new ConstructionException("");
                    }
                }
                settool(NAnimator);
                ZC.setTool(new AnimatorTool(P, C.AnimateV, ZC,
                        C.AnimateNegative, C.AnimateOriginal, C.AnimateDelay));
            } catch (final Exception e) {
                warning(Global.name("exception.animate"));
            }
        } else if (C.AnimateBreakpoints) { // System.out.println("setting animation with brakpoints");
            final BreakpointAnimator bp=new BreakpointAnimator();
            bp.setLoop(C.AnimateLoop);
            bp.setSpeed(C.AnimateTime);
            ZC.setTool(bp);
            bp.reset(ZC);
        } else { // System.out.println("setting mover");
            settool(NMover);
        }
        // System.out.println("finished loading");
    }

    public void loadMacros() {
        FileLoad.setPattern("*.mcr *.mcrz");
        FileLoad.center(FRM);
        FileLoad.update();
        FileLoad.setVisible(true);
        if (FileLoad.isAborted()) {
            return;
        }
        final String Filename=FileLoad.getFilePath();
        try {
            InputStream o=new FileInputStream(Filename);
            if (isCompressed(Filename)) {
                o=new GZIPInputStream(o);
            }
            ZC.load(o, false, true);
            o.close();
        } catch (final Exception e) {
            final Warning w=new Warning(FRM, Global.name("warning.loadmacros"), FileName.chop(32,
                    e.toString(), 64), Global.name("warning"), true);
            w.center(FRM);
            w.setVisible(true);
        }
    }

    public void loadJob() {
        FileLoad.setPattern("*.job *.jobz");
        FileLoad.center(FRM);
        FileLoad.update();
        FileLoad.setVisible(true);
        if (FileLoad.isAborted()) {
            return;
        }
        Filename=FileLoad.getFilePath();
        try {
            InputStream o=new FileInputStream(Filename);
            if (isCompressed(Filename)) {
                o=new GZIPInputStream(o);
            }
            clear(false);
            ZC.load(o);
            o.close();
            FRM.setTitle(Global.name("program.name")+" : "+FileName.chop(Filename));
            final String icons=ZC.getConstruction().Icons;
            if (!icons.equals("")) {
                Global.setParameter("restrictedicons", icons);
                showDefaultIcons(false);
            }
            if (!ZC.getConstruction().getComment().equals("")) {
                showcomment();
            }
        } catch (final Exception e) {
            final Warning w=new Warning(FRM, Global.name("warning.load"),
                    FileName.chop(32, e.toString(), 64),
                    Global.name("warning"), true);
            w.center(FRM);
            w.setVisible(true);
        }
    }



    public void showcomment() {

    }

    public void showjobcomment() {

    }

    public void showconstruction() {

    }


    public boolean close() {
        if (eric.GUI.pipe_tools.isApplet()) {
            return true;
        }
        if (ZC.changed()) {
            final Question q=new Question(FRM, Global.name("savequestion.qsave"), Global.name("savequestion.title"), true);
            q.center(FRM);
            q.setVisible(true);
            if (q.yes()) {
                return savefile();
            }
            return q.getResult()!=Question.ABORT;
        }
        return true;
    }


    public void doclose() {

    }



    /*
     * (non-Javadoc)
     *
     * @see rene.zirkel.DoneListener#notifyDone() Display a message for the
     * user.
     */
    public void notifyDone() {
        try {
            Thread.sleep(500);
        } catch (final Exception e) {
        }
        warning(Global.name("done"));
    }




    public void doexport(final boolean solution) {

    }

    public void doexporttemplate(
            final String template) {


    }
    MyVector TB=new MyVector();
    int TBN=0;

    public void printCheck(final PrintWriter out, final String s) {
        if (TBN==0) {
            out.println(s);
        } else {
            out.print(s);
        }
    }

    public String readTemplateLine(final BufferedReader in) throws IOException {
        if (TB.size()>0) // Still strings in the buffer
        {
            final String h=(String) TB.elementAt(TBN);
            TBN++;
            if (TBN>=TB.size()) {
                TBN=0;
                TB.removeAllElements();
            }
            return h;
        }
        final String h=in.readLine();
        if (h==null) {
            return h;
        }
        if (h.indexOf('#')<0) {
            return h;
        }
        bufferTemplate(h);
        if (TB.size()>0) {
            return readTemplateLine(in);
        } else {
            return h;
        }
    }

    public void bufferTemplate(final String s) {
        final int n=s.indexOf('#');
        if (n<0) {
            TB.addElement(s);
            return;
        }
        final String h=s.substring(n);
        if (h.startsWith("#title")) {
            bufferTemplate(s, n, h, "#title");
        } else if (h.startsWith("#parameter")) {
            bufferTemplate(s, n, h, "#parameter");
        } else if (h.startsWith("#color")) {
            bufferTemplate(s, n, h, "#color");
        } else if (h.startsWith("#font")) {
            bufferTemplate(s, n, h, "#font");
        } else if (h.startsWith("#codebase")) {
            bufferTemplate(s, n, h, "#codebase");
        } else if (h.startsWith("#comment")) {
            for (int i=0; i<10; i++) {
                final String t="#comment"+i;
                if (h.startsWith(t)) {
                    bufferTemplate(s, n, h, t);
                    return;
                }
            }
            bufferTemplate(s, n, h, "#comment");
        } else if (h.startsWith("#text")) {
            for (int i=0; i<10; i++) {
                final String t="#text"+i;
                if (h.startsWith(t)) {
                    bufferTemplate(s, n, h, t);
                    return;
                }
            }
            bufferTemplate(s, n, h, "#text");
        }
    }

    public void bufferTemplate(final String s, final int n, String h,
            final String ph) {
        if (n>0) {
            TB.addElement(s.substring(0, n));
        }
        TB.addElement(ph);
        h=h.substring(ph.length());
        if (!h.equals("")) {
            bufferTemplate(h);
        }
    }

    public void printParagraphs(final PrintWriter out, String s,
            final int linelength) {
        final StringParser p=new StringParser(s);
        final Vector v=p.wrapwords(linelength);
        for (int i=0; i<v.size(); i++) {
            out.println("<P>");
            s=(String) v.elementAt(i);
            final StringParser q=new StringParser(s);
            final Vector w=q.wraplines(linelength);
            for (int j=0; j<w.size(); j++) {
                if (j>0) {
                    out.println();
                }
                s=(String) w.elementAt(j);
                out.print(s);
            }
            out.println("</P>");
        }
    }

    public int getDigits(final double x) {
        return (int) (Math.log(x)/Math.log(10)+0.5);
    }

    public void setPartial(final boolean flag) {
        Global.setParameter("options.partial", flag);
        ZC.setPartial(flag);
    }

    public void setRestricted(final boolean flag) {
        Global.setParameter("options.restricted", flag);
        ZC.setRestricted(flag);
    }

    public void setPartialLines(final boolean flag) {
        Global.setParameter("options.plines", flag);
        ZC.setPartialLines(flag);
    }

    public void setVectors(final boolean flag) {
        Global.setParameter("options.arrow", flag);
        ZC.setVectors(flag);
    }

    public void setLongNames(final boolean flag) {
        Global.setParameter("options.longnames", flag);
        ZC.setLongNames(flag);
    }

    public void setBoldFont(final boolean flag) {
        Global.setParameter("options.boldfont", flag);
        ZC.setBoldFont(flag);
    }

    public void setLargeFont(final boolean flag) {
        Global.setParameter("options.largefont", flag);
        ZC.setLargeFont(flag);
    }

    public void setObtuse(final boolean flag) {
        Global.setParameter("options.obtuse", flag);
        ZC.setObtuse(flag);
    }

    public void setSolid(final boolean flag) {
        Global.setParameter("options.solid", flag);
        ZC.setSolid(flag);
    }

    public void setShowNames(final boolean flag) {
        Global.setParameter("options.shownames", flag);
        ZC.setShowNames(flag);
    }

    public void setShowValues(final boolean flag) {
        Global.setParameter("options.showvalue", flag);
        ZC.setShowValues(flag);
    }

    void definemacro() {
        ZC.defineMacro();
        settool(NParameters);
        ZC.getTool().reset(ZC); //previously ZC.getOC().reset(ZC);
    }
    String OldMacro=null;

    public void runMacro(final boolean shift) {
        Macro m=ZC.chooseMacro(OldMacro);
        if (!shift||m==null) {
            m=ZC.chooseMacro();
        }
        if (m==null) {
            settool(CurrentTool);
            return;
        }
        runMacro(m);
    }

    public void runMacro(final Macro m) {
        ((MacroRunner) ObjectConstructors[NMacro]).setMacro(m, ZC);
        settool(NMacro);
        OldMacro=m.getName();
    }

    public void replayChosen() {
    }

    public void setDigits() {
        ZC.updateDigits();
        ZC.repaint();
    }

    public void setLanguage() {

    }

    public void savePNG() {

    }

    /**
     * Copy graphics to clipboard (in print preview format).
     */
    public void copyPNG() {

    }

    public void saveFIG() {

    }

    public void saveSVG() {

    }

    public void savePDF() {

    }

    public void saveEPS() {

    }

    public void setVisual(final boolean flag) {

    }

    public void replay() {
        ZC.OC.invalidate(ZC);
        ZC.getConstruction().setOriginalOrder(true);
        ZC.getConstruction().setOriginalOrder(false);
        ZC.validate();
        ZC.repaint();
    }



    public int resizeCol;
    boolean resizeFlag=false;





    public void reset() {
        ZC.reset();
        if (CurrentTool==NTargets) {
            settool(NParameters);
        }
    }







    public String loadImage() {
        ImageLoad.center(FRM);
        ImageLoad.update();
        ImageLoad.setVisible(true);
        if (ImageLoad.isAborted()) {
            return "";
        }
        return ImageLoad.getFilePath();
    }

    public Image doLoadImage(String filename) {
        Image img=tryToLoadImage(filename);
        if (img==null){
            img=tryToLoadImage(FileName.path(Filename)+System.getProperty("file.separator")+filename);
        };
        return img;
    }

    public Image tryToLoadImage(String filename){
        try {
            final Image i=FRM.getToolkit().getImage(filename);
            final MediaTracker mt=new MediaTracker(FRM);
            mt.addImage(i, 0);
            mt.waitForID(0);
            if (mt.checkID(0)&&!mt.isErrorAny()) {
                return i;
            } else {
                throw new Exception(Global.name("error.image"));
            }
        } catch (final Exception e) {
            return null;
        }
    }

    /**
     * Load a background picture, and resize if wanted
     * UNIQUEMENT APPELE PAR LE CLIC SUR L'ICONE DE LA PALETTE :
     */
    public void loadBackground() {
        BackgroundLoad.center(FRM);
        BackgroundLoad.update();
        BackgroundLoad.setVisible(true);
        if (BackgroundLoad.isAborted()) {
            return;
        }
        final String filename=BackgroundLoad.getFilePath();
        Media.createMedia(filename);
        ZC.setBackground(Media.getImage(FileName.filename(filename)));
        Background=FileName.filename(filename);
        ZC.getConstruction().BackgroundFile=Background;
    }

    public void doloadBackground(final String filename) {
        Media.createMedia(filename);
        ZC.setBackground(Media.getImage(filename));
                ZC.repaint();
        Background=FileName.filename(filename);
        ZC.getConstruction().BackgroundFile=Background;
    }

    public void resize() {
        FRM.pack();
    }

    public void track() {
        if (ZC.getTool() instanceof ObjectTracker&&((ObjectTracker) ZC.getTool()).isComplete()) {
            final Question q=new Question(FRM, Global.name("trackquestion.keep"), Global.name("trackquestion.title"), true);
            q.center(FRM);
            q.setVisible(true);
            if (q.yes()) {
                ((ObjectTracker) ZC.getTool()).keep(ZC);
            }
        }
    }

    public void restrictIcons(final boolean flag) {
        if (flag) {
            showDefaultIcons(false);
        } else {
            showDefaultIcons(true);
        }
    }






    public void setinfo(final String s, final boolean WithTxtFocus) {
        JZirkelCanvas.setinfo(s, WithTxtFocus);
    }

    public void setinfo(final String s) {
        setinfo(s, true);
    }

    /**
     * Load default macros from ".default.mcr", if that file exists. Else load
     * from resources, if such a file exists in the resources.
     */
    public void loadBuiltInMacros() {
        try {
            final InputStream o=getClass().getResourceAsStream("/builtin.mcr");
            ZC.ProtectMacros=true;
            ZC.load(o, false, true);
            ZC.ProtectMacros=false;
            o.close();
        } catch (final Exception e) {
        }
    }

    public void loadDefaultMacros() {
    }

    public void showDefaultIcons(final boolean flag) {
        if (!flag) {
            ZC.clearProtectedMacros();
        } else {
            loadDefaultMacros();
        }
    }

    /**
     * Search for a help topic. This help should be replaced by the context
     * help.
     *
     * @param subject
     *            file name
     * @return
     */
    public boolean haveHelp(final String subject) {
        final String lang=Global.name("language", "");
        try {
            final BufferedReader in=new BufferedReader(new InputStreamReader(
                    getClass().getResourceAsStream(
                    "/rene/zirkel/docs/"+lang+subject)));
            in.close();
        } catch (final Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Opened or saved a file (or new construction).
     *
     * @return state
     */
    public boolean haveFile() {
        return !Filename.equals("");
    }

    public void newfile(final boolean defaults) {
        if (ZC.changed()) {
            final Question q=new Question(FRM, Global.name("savequestion.qsave"), Global.name("savequestion.title"), true);
            q.center(FRM);
            q.setVisible(true);
            if (q.yes()&&!savefile()) {
                return;
            }
            if (q.isAborted()) {
                return;
            }
        }
        clear(defaults);
        Filename="";
        clearNonprotectedMacros();
        FRM.setTitle(Global.name("program.name"));
    }

    public void warning(final String s) {
        final Warning w=new Warning(FRM, s, "", true);
        w.center(FRM);
        w.setVisible(true);
    }

    /**
     * Display or hide the permanent construction display
     *
     * @param flag
     */
    public void showConstructionDisplay(final boolean flag) {
        Global.setParameter("options.showdisplay", flag);
    }

    /**
     * Set the show hidden state
     *
     * @param flag
     */
    public void editGrid() {
    }
}
