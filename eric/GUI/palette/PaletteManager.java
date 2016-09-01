/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.GUI.palette;

import eric.GUI.window.LeftPanel;
import eric.GUI.window.MenuBar;
import eric.GUI.window.RightPanel;
import eric.JZirkelCanvas;
import eric.macros.MacroTools;
import eric.restrict.RestrictItems;
import eric.restrict.RestrictPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import rene.gui.Global;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.construction.Construction;

/**
 *
 * @author erichake
 */
public class PaletteManager {

    private static String GEOM_GROUP="geom";
    static String POINT_GROUP="point";
    static String ASPECT1_GROUP="aspect1";
    static String ASPECT2_GROUP="aspect2";
    static String GRID1_GROUP="grid1";
    static String GRID2_GROUP="grid2";
    static String PHOTO_GROUP="photo";
    private static RightPanel MainPanel;
    private static JIcon ctrlJSlider, ctrlJPopup, ctrlJCheckBox, ctrlJTextField,
            ctrlJButton;
    private static ArrayList<JIcon> allIcons=new ArrayList<JIcon>();
    private static PaletteZone JPDisk, JPEdit, JPfunc, JPTest,
            JPControls, JPHist, JPSizes, JPColors, JPPrec, JPGrid;
    private static ArrayList<PaletteZone> PaletteWithIconOnly;
    private static PaletteZone_Aspect JPAspect;
    private static PaletteZone_Geom JPGeom;
    private static PaletteZone_3D JP3D;
    private static JColorPanel colorpanel;

    public static void fixsize(JComponent jc, int w, int h) {
        Dimension d=new Dimension(w, h);
        jc.setSize(d);
        jc.setMaximumSize(d);
        jc.setMinimumSize(d);
        jc.setPreferredSize(d);
    }

    public static void construct(RightPanel mainpanel) {
        MainPanel=mainpanel;
        MainPanel.removeAll();
        allIcons.clear();
        constructJPDisk();
        constructJPEdit();
        constructJP3D();
        constructJPGeom();
        constructJPAspect();
        constructJPfunc();
        constructJPTest();
        constructJPControls();
        constructJPGrid();
        constructJPHist();
        constructJPColors();
        constructJPSizes();
        constructJPPrec();
        setDefault();
        PaletteWithIconOnly=new ArrayList<PaletteZone>();
        PaletteWithIconOnly.add(JPDisk);
        PaletteWithIconOnly.add(JPEdit);
        PaletteWithIconOnly.add(JPGeom);
        PaletteWithIconOnly.add(JPfunc);
        PaletteWithIconOnly.add(JPTest);
        PaletteWithIconOnly.add(JPControls);
    }

    public static void init() {
        if (RightPanel.isPanelVisible()) {
            fixRestrictedEnvironment();
            JPDisk.init();
            JPEdit.init();
            JP3D.init();
//            JPDP.init();
            JPGeom.init();
            JPAspect.init();
            JPfunc.init();
            JPTest.init();
            JPControls.init();
            JPGrid.init();
            JPHist.init();
            JPColors.init();
            JPSizes.init();
            JPPrec.init();
            FixPaletteHeight(null);
        }
    }

    private static void setDefault() {
        setSelected_with_clic("type"+Global.getParameter("options.type", 0), true);
        setSelected_with_clic("obtuse", Global.getParameter("options.obtuse", true));
    }
    

    public static boolean isPaletteWithIconOnly(PaletteZone pz) {
        for (int i=0; i<PaletteWithIconOnly.size(); i++) {
            if (pz.equals(PaletteWithIconOnly.get(i))) {
                return true;
            }
        }
        return false;
    }

    private static void constructJPDisk() {
        String icns[]={"new", "load", "save", "copy", "exportpng",
            "exporteps", "exportsvg", "exportpdf"};
        JPDisk=new PaletteZone(Global.Loc("palette.file"));
        JPDisk.createIcons(icns, null);
        MainPanel.add(JPDisk);
        RestrictItems.init_disk_icns(icns);
    }

    private static void constructJPEdit() {
        String icns[]={"move", "select", "edit"};
        JPEdit=new PaletteZone(Global.Loc("palette.edit"));
        JPEdit.createIcons(icns, GEOM_GROUP);
        JPEdit.createIcon("back", null);
        JPEdit.createIcon("delete", GEOM_GROUP);
        JPEdit.createIcon("undo", null);
        JPEdit.createIcon("hide", GEOM_GROUP);
        JPEdit.createToggleIcon("hidden");
        JPEdit.createToggleIcon("grid");
        String icns1[]={"animate"};
        MainPanel.add(JPEdit);
        JPEdit.createIcons(icns1, GEOM_GROUP);
        String icns2[]={"move", "select","edit", "back", "delete", "undo", "hide", "hidden", "grid", "animate"};
        RestrictItems.init_edit_icns(icns2);
    }

    private static void constructJP3D() {
        JP3D=new PaletteZone_3D();
    }

    private static void constructJPAspect() {
        JPAspect=new PaletteZone_Aspect();
        MainPanel.add(JPAspect);
    }

    private static void constructJPGeom() {
        JPGeom=new PaletteZone_Geom();
        MainPanel.add(JPGeom);
    }

    private static void constructJPfunc() {
        final String icns[]={"tracker", "objecttracker", "locus"};
        JPfunc=new PaletteZone(Global.Loc("palette.function"));
        JPfunc.createIcon("bi_function_u", GEOM_GROUP);
        JPfunc.createIcon("function", null);
        JPfunc.createIcon("equationxy", null);
        JPfunc.createIcons(icns, GEOM_GROUP);
        MainPanel.add(JPfunc);
        final String icns2[]={"tracker", "objecttracker", "locus", "bi_function_u", "function", "equationxy"};
        RestrictItems.init_func_icns(icns2);
    }

    private static void constructJPTest() {
        final String icns[]={"bi_t_align", "bi_t_para", "bi_t_perp",
            "bi_t_equi", "bi_t_app", "bi_t_conf"};
        JPTest=new PaletteZone(Global.Loc("palette.test"));
        JPTest.createIcons(icns, GEOM_GROUP);
        MainPanel.add(JPTest);
        RestrictItems.init_test_icns(icns);
    }

    private static void constructJPControls() {
        JPControls=new PaletteZone(Global.Loc("palette.controls"));
        JPControls.createIcon("ctrl_edit", GEOM_GROUP);
        ctrlJSlider=JPControls.createIcon("ctrl_slider", GEOM_GROUP);
        ctrlJPopup=JPControls.createIcon("ctrl_popup", GEOM_GROUP);
        ctrlJCheckBox=JPControls.createIcon("ctrl_chkbox", GEOM_GROUP);
        ctrlJTextField=JPControls.createIcon("ctrl_txtfield", GEOM_GROUP);
        ctrlJButton=JPControls.createIcon("ctrl_button", GEOM_GROUP);
        MainPanel.add(JPControls);
        final String icns[]={"ctrl_edit", "ctrl_slider", "ctrl_popup", "ctrl_chkbox", "ctrl_txtfield", "ctrl_button"};
        RestrictItems.init_control_icns(icns);
    }

    private static void constructJPGrid() {
        final String icns[]={"acolor0", "acolor1", "acolor2", "acolor3", "acolor4", "acolor5"};
        JPGrid=new PaletteZone(Global.Loc("palette.grid"));
        JPGrid.createIcons(icns, GRID1_GROUP);
        JPGrid.createToggleIcon("dottedgrid");
        JPGrid.createToggleIcon("numgrid");
        JPGrid.createSimpleIcon("blank");
        final String icns2[]={"athickness0", "athickness1", "athickness2"};
        JPGrid.createIcons(icns2, GRID2_GROUP);
        MainPanel.add(JPGrid);
    }

    private static void constructJPHist() {
        final String icns[]={"allback", "fastback", "oneback", "oneforward",
            "fastforward", "allforward", "setbreak", "nextbreak"};
        JPHist=new PaletteZone(Global.Loc("palette.history"));
        JPHist.createIcons(icns, null);
        MainPanel.add(JPHist);
    }

    private static void constructJPColors() {
        JPColors=new PaletteZone(Global.Loc("palette.colors"));
        JPColors.addComponent(colorpanel=new JColorPanel());
        JPColors.createToggleIcon("background");
        JPColors.createSimpleIcon("blank");
        JPColors.createSimpleIcon("blank");
        final String icns[]={"imstretch", "imcenter", "imtile"};
        JPColors.createIcons(icns, PHOTO_GROUP);
        MainPanel.add(JPColors);
    }

    private static void constructJPSizes() {
        JPSizes=new PaletteZone(Global.Loc("palette.sizes"));
        JPSizes.addComponent(new JCursor("minpointsize", Global.Loc("palette.sizes.point"), 1, 9, 3));
        JPSizes.addComponent(new JCursor("minlinesize", Global.Loc("palette.sizes.line"), 1, 9, 1));
        JPSizes.addComponent(new JCursor("arrowsize", Global.Loc("palette.sizes.arrow"), 3, 50, 15));
        JPSizes.addComponent(new JCursor("minfontsize", Global.Loc("palette.sizes.font"), 1, 64, 12));
        JPSizes.addComponent(new JCursor("selectionsize", Global.Loc("palette.sizes.selectionsize"), 5, 20, 8));
        JPSizes.addComponent(new JCursor("monkeyspeed", Global.Loc("palette.sizes.monkeyspeed"), 1, 50, 20));
        JPSizes.addComponent(new JCursor("gridopacity", Global.Loc("palette.sizes.gridopacity"), 0, 100, 20));
        MainPanel.add(JPSizes);
    }

    private static void constructJPPrec() {
        JPPrec=new PaletteZone(Global.Loc("palette.prec"));
        JPPrec.addComponent(new JCursor("digits.lengths", Global.Loc("palette.prec.lengths"), 0, 12, 5));
        JPPrec.addComponent(new JCursor("digits.edit", Global.Loc("palette.prec.edit"), 0, 12, 5));
        JPPrec.addComponent(new JCursor("digits.angles", Global.Loc("palette.prec.angles"), 0, 12, 0));
        MainPanel.add(JPPrec);
    }

    public static void refresh() {
        JPSizes.clearContent();
        JPSizes.addComponent(new JCursor("minpointsize", Global.Loc("palette.sizes.point"), 1, 9, Global.getParameter("minpointsize", 3)));
        JPSizes.addComponent(new JCursor("minlinesize", Global.Loc("palette.sizes.line"), 1, 9, Global.getParameter("minlinesize", 1)));
        JPSizes.addComponent(new JCursor("arrowsize", Global.Loc("palette.sizes.arrow"), 3, 50, Global.getParameter("arrowsize", 15)));
        JPSizes.addComponent(new JCursor("minfontsize", Global.Loc("palette.sizes.font"), 1, 64, Global.getParameter("minfontsize", 18)));
        JPSizes.addComponent(new JCursor("selectionsize", Global.Loc("palette.sizes.selectionsize"), 5, 20, Global.getParameter("selectionsize", 8)));
        JPSizes.addComponent(new JCursor("monkeyspeed", Global.Loc("palette.sizes.monkeyspeed"), 1, 50, Global.getParameter("monkeyspeed", 20)));
        JPSizes.addComponent(new JCursor("gridopacity", Global.Loc("palette.sizes.gridopacity"), 0, 100, Global.getParameter("gridopacity", 20)));
        JPSizes.revalidate();
        JPSizes.repaint();
        JPPrec.clearContent();
        JPPrec.addComponent(new JCursor("digits.lengths", Global.Loc("palette.prec.lengths"), 0, 12, Global.getParameter("digits.lengths", 2)));
        JPPrec.addComponent(new JCursor("digits.edit", Global.Loc("palette.prec.edit"), 0, 12, Global.getParameter("digits.edit", 4)));
        JPPrec.addComponent(new JCursor("digits.angles", Global.Loc("palette.prec.angles"), 0, 12, Global.getParameter("digits.angles", 2)));
        JPPrec.revalidate();
        JPPrec.repaint();
        JPAspect.initPointNameBtn();
        colorpanel.refresh();

        setSelected_with_clic("grid", Global.getParameter("axis_show", false));
        setSelected_with_clic("acolor"+Global.getParameter("axis_color", 0), true);
        setSelected_with_clic("athickness"+Global.getParameter("axis_thickness", 0), true);
        setSelected_with_clic("numgrid", Global.getParameter("axis_labels", true));
        setSelected_with_clic("dottedgrid", Global.getParameter("axis_with_grid", true));
    }

    public static void fixRestrictedEnvironment() {
        ZirkelCanvas zc=JZirkelCanvas.getCurrentZC();
        if ((zc!=null)) {
            JPDisk.setVisible(!zc.isHiddenItem(RestrictItems.DISK));
            JPEdit.setVisible(!zc.isHiddenItem(RestrictItems.EDIT));
            JPGeom.setVisible(!zc.isHiddenItem(RestrictItems.GEOM));
            JPAspect.setVisible(!zc.isHiddenItem(RestrictItems.ASPECT));
            JPfunc.setVisible(!zc.isHiddenItem(RestrictItems.FUNC));
            JPTest.setVisible(!zc.isHiddenItem(RestrictItems.TEST));
            JPControls.setVisible(!zc.isHiddenItem(RestrictItems.CTRL));
            JPGrid.setVisible(!zc.isHiddenItem(RestrictItems.GRID));
            JPHist.setVisible(!zc.isHiddenItem(RestrictItems.HIST));
            JPColors.setVisible(!zc.isHiddenItem(RestrictItems.BACK));
            JPSizes.setVisible(!zc.isHiddenItem(RestrictItems.SIZE));
            JPPrec.setVisible(!zc.isHiddenItem(RestrictItems.PREC));
            MenuBar.setMenuVisible(!zc.isHiddenItem(RestrictItems.MENU));
            zc.setLibraryMacrosVisible(!zc.isHiddenItem(RestrictItems.LMCR));
            LeftPanel.setMacroBtnVisible(!zc.isHiddenItem(RestrictItems.MCRP));
            LeftPanel.setHistoryBtnVisible(!zc.isHiddenItem(RestrictItems.HISTP));
            LeftPanel.setHelpBtnVisible(!zc.isHiddenItem(RestrictItems.HLPP));


            for (int i=0; i<allIcons.size(); i++) {
                JIcon ji=allIcons.get(i);
                ji.setVisible(!zc.isHiddenItem(ji.getIconName()));
            }

            zc.reloadCD();
        }

    }

    public static String ToolTip(final String s) {
        String ToolTipText="";
        final String purename=(s.startsWith("bi_"))?s.substring(3):s;
        ToolTipText=Global.Loc("palette.info."+purename);
        if (ToolTipText==null) {
            ToolTipText=Global.Loc("palette.info."+s);
        }
        if (ToolTipText==null) {
            ToolTipText=Global.Loc("iconhelp."+purename);
        }
        if (ToolTipText==null) {
            ToolTipText=Global.Loc("palette.info.DP_"+s);
        }
        return ToolTipText;
    }

    public static void FixPaletteHeight(PaletteZone caller) {
        if (caller!=null) {
            caller.init();
        }
        MainPanel.validate();
        if (MainPanel.getComponentCount()!=0) {
            int i=MainPanel.getComponentCount()-1;
            PaletteZone lastpz=(PaletteZone) MainPanel.getComponent(i);
            while ((i>=0)&&(lastpz.getBounds().y+lastpz.getBounds().height>MainPanel.getBounds().height)) {
                PaletteZone pz=(PaletteZone) MainPanel.getComponent(i);
                if (!pz.equals(caller)) {
                    pz.setHideContent(true);
                    pz.init();
                    MainPanel.validate();
                }
                i--;
            }
        }
        MainPanel.repaint();
    }

    public static void registerIcon(JIcon icn) {
        allIcons.add(icn);
    }

    public static void removeIcon(JIcon icn) {
        allIcons.remove(icn);
    }

    

    public static void setOverBtn(JIcon icn) {
        icn.setOver(true);
        for (int i=0; i<allIcons.size(); i++) {
            if (!allIcons.get(i).equals(icn)) {
                allIcons.get(i).setOver(false);
            }
        }
        MainPanel.repaint();
    }

    public static void setSelectBtn(JIcon icn) {
        if (!icn.isPushButton()) {
            if (icn.isToggleButton()) {
                icn.setSelected(!icn.isSelected());
                icn.repaint();
            } else {
                icn.setSelected(true);
                for (int i=0; i<allIcons.size(); i++) {
                    if (icn.getIconGroup().equals(allIcons.get(i).getIconGroup())&&(!allIcons.get(i).equals(icn))) {
                        allIcons.get(i).setSelected(false);
                    }
                }
                MainPanel.repaint();
            }
        }
    }

    public static void repaint() {
        MainPanel.repaint();
    }

    public static String IconFamily(final String name) {
        String f=",ray,parallel,plumb,bi_med,bi_biss,";
        if (f.indexOf(","+name+",")!=-1) {
            return "line";
        }
        f=",intersection,midpoint,bi_syma,bi_symc,bi_trans,boundedpoint,";
        if (f.indexOf(","+name+",")!=-1) {
            return "point";
        }
        f=",vector,fixedsegment,";
        if (f.indexOf(","+name+",")!=-1) {
            return "segment";
        }
        f=",circle3,fixedcircle,bi_arc,bi_circ,quadric,";
        if (f.indexOf(","+name+",")!=-1) {
            return "circle";
        }
        f=",bi_function_u,expression,text,";
        if (f.indexOf(","+name+",")!=-1) {
            return "text";
        }
        f=",fixedangle,";
        if (f.indexOf(","+name+",")!=-1) {
            return "angle";
        }
        f=",image3,";
        if (f.indexOf(","+name+",")!=-1) {
            return "image";
        }
        return name;
    }

    public static void deselectgeomgroup() {
        for (int i=0; i<allIcons.size(); i++) {
            if (GEOM_GROUP.equals(allIcons.get(i).getIconGroup())) {
                allIcons.get(i).setSelected(false);
                allIcons.get(i).repaint();
            }
        }
        JIconMouseAdapter.deselect();
    }

    public static String getGeomGroup(){
        return GEOM_GROUP;
    }

    public static void remove3DPalette(){
        MainPanel.remove(JP3D);
    }

    public static void add3DPalette(){
        MainPanel.add(JP3D,2);
    }

    public static boolean isGeomGroup(String name) {
        for (int i=0; i<allIcons.size(); i++) {
            JIcon ji=allIcons.get(i);
            if (allIcons.get(i).getIconName().equals(name)) {
                return GEOM_GROUP.equals(ji.getIconGroup());
            }
        }
        return false;
    }

    public static void setGoodProperties(final String iconname) {
        int i=0;
        boolean b=false;
        if (isGeomGroup(iconname)) {
            final String familyIcon=IconFamily(iconname);
            i=Global.getParameter("options."+familyIcon+".color", 0);
            final Color col=Global.getParameter("options."+familyIcon+".pcolor", (Color) null);
            if (col==null) {
                setSelected_with_clic("color"+i, true);
                PaletteZone_Aspect.getColorPicker().setDefaultColor();
            } else {
                PaletteZone_Aspect.getColorPicker().Select();
                PaletteZone_Aspect.getColorPicker().setCurrentColor(col);
            }
            i=Global.getParameter("options."+familyIcon+".colortype", 0);
            setSelected_with_clic("thickness"+i, true);
            b=Global.getParameter("options."+familyIcon+".shownames",
                    false);
            setSelected_with_clic("showname", b);
            b=Global.getParameter("options."+familyIcon+".showvalues",
                    false);
            setSelected_with_clic("showvalue", b);
            b=Global.getParameter("options."+familyIcon+".filled", false);
            setSelected_with_clic("filled", b);
            b=Global.getParameter("options."+familyIcon+".solid", false);
            setSelected_with_clic("solid", b);
            b=Global.getParameter("options."+familyIcon+".bold", false);
            setSelected_with_clic("bold", b);
            b=Global.getParameter("options."+familyIcon+".large", false);
            setSelected_with_clic("large", b);
            b=Global.getParameter("options."+familyIcon+".obtuse", false);
            setSelected_with_clic("obtuse", b);
            if (Global.getParameter("background.usesize", false)) {
                setSelected_with_clic("imstretch", true);
            } else if (Global.getParameter("background.tile", false)) {
                setSelected_with_clic("imtile", true);
            } else if (Global.getParameter("background.center", false)) {
                setSelected_with_clic("imcenter", true);
            }




            JPAspect.setLabel(Global.Loc("palette.aspect.label.text_title")+" "+Global.Loc("palette.aspect.label."+familyIcon)+" :");
            setAspectDisabledState(iconname);
        }
    }

    public static void setAspectDisabledState(final String iconname) {
        final String AspectIcons=" bold large filled"
                +" thickness0 thickness1 thickness2"
                +" color0 color1 color2 color3 color4 color5"
                +" obtuse plines partial solid showvalue showname ";
        setDisabledIcons(AspectIcons, false);

        final String familyIcon=IconFamily(iconname);
        if (familyIcon.equals("point")) {
            setDisabledIcons(" filled obtuse plines partial solid ", true);
        } else if (familyIcon.equals("line")) {
            setDisabledIcons(" filled obtuse partial solid showvalue ", true);
        } else if (familyIcon.equals("segment")) {
            setDisabledIcons(" filled obtuse partial plines solid ", true);
        } else if (familyIcon.equals("angle")) {
            setDisabledIcons(" partial plines ", true);
        } else if (familyIcon.equals("circle")) {
            setDisabledIcons(" obtuse plines ", true);
        } else if (familyIcon.equals("area")) {
            setDisabledIcons(" bold large obtuse plines partial showname ",
                    true);
        } else if (familyIcon.equals("text")) {
            setDisabledIcons(" showvalue filled obtuse plines partial solid ",
                    true);
        } else if (familyIcon.equals("locus")) {
            setDisabledIcons(
                    " bold large obtuse plines partial solid showvalue showname ",
                    true);
        } else if (familyIcon.equals("tracker")) {
            setDisabledIcons(
                    " bold large filled thickness0 thickness1 thickness2 color0 color1 color2 color3 color4 color5 obtuse plines partial solid showvalue showname ",
                    true);
        } else {
            setDisabledIcons(AspectIcons, true);
        }
        if (iconname.equals("quadric")) {
            setDisabledIcons(" partial filled ", true);
        }
        if (iconname.equals("text")) {
            setDisabledIcons(" showname ", true);
        }
    }

    // Disable/Enable Icons list : string with space separator
    public static void setDisabledIcons(String iconname, boolean dis) {
        JIcon myicon;
        for (int i=0; i<allIcons.size(); i++) {
            myicon=(JIcon) allIcons.get(i);
            if (iconname.indexOf(" "+myicon.getIconName()+" ")!=-1) {
                if (myicon.isDisabled()!=dis) {
                    myicon.setDisabled(dis);
                }
                myicon.repaint();
                if (myicon.getIconName().equals("color0")) {
                    PaletteZone_Aspect.getColorPicker().setDisabled(dis);
                    PaletteZone_Aspect.getColorPicker().repaint();
                }
            }
        }
    }



    public static void ClicOn(final String iconname) {
        JIcon myicon;
        for (int i=0; i<allIcons.size(); i++) {
            myicon=(JIcon) allIcons.get(i);
            if (iconname.equals(myicon.getIconName())) {
                myicon.ClicOnMe();
                return;
            }
        }
    }

    public static void setSelected(String iconname, boolean sel) {
        JIcon myicon;
        for (int i=0; i<allIcons.size(); i++) {
            myicon=(JIcon) allIcons.get(i);
            if (myicon.getIconName().equals(iconname)) {
                myicon.setSelected(sel);
                return;
            }
        }
    }

    public static void setSelected_with_clic(String iconname, boolean sel) {
        JIcon myicon;
        for (int i=0; i<allIcons.size(); i++) {
            myicon=(JIcon) allIcons.get(i);
            if (myicon.getIconName().equals(iconname)) {
                if (myicon.isSelected()!=sel) {
                    myicon.ClicOnMe();
                }
                return;
            }
        }
    }

    public static String geomSelectedIcon() {
        for (int i=0; i<allIcons.size(); i++) {
            if (GEOM_GROUP.equals(allIcons.get(i).getIconGroup())) {
                if (allIcons.get(i).isSelected()) {
                    return allIcons.get(i).getIconName();
                }
            }
        }
        return "";
    }

    public static void selectGeomIcon() {
        for (int i=0; i<allIcons.size(); i++) {
            if (GEOM_GROUP.equals(allIcons.get(i).getIconGroup())) {
                if (allIcons.get(i).isSelected()) {
                    allIcons.get(i).ClicOnMe();
                    return;
                }
            }
        }
    }

    public static boolean isSelected(final String iconname) {
        for (int i=0; i<allIcons.size(); i++) {
            if (iconname.equals(allIcons.get(i).getIconName())) {
                return allIcons.get(i).isSelected();
            }
        }
        return false;
    }

   

    public static void initPaletteConsideringMode() {
        JPGeom.initConsideringMode();
        JP3D.initConsideringMode();
        FixPaletteHeight(null);
    }

}
