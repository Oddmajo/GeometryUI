/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.GUI.palette;

import eric.FileTools;
import eric.GUI.pipe_tools;
import eric.GUI.themes;
import eric.GUI.window.LeftPanel;
import eric.GUI.window.tab_main_panel;
import java.awt.image.ImageFilter;
import eric.GUI.windowComponent;
import eric.JZirkelCanvas;
import eric.macros.CreateMacroDialog;
import eric.macros.MacroTools;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.image.FilteredImageSource;
import javax.swing.GrayFilter;
import javax.swing.ImageIcon;
import rene.gui.Global;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.ZirkelFrame;
import rene.zirkel.constructors.AreaConstructor;
import rene.zirkel.constructors.QuadricConstructor;
import rene.zirkel.tools.JLocusObjectTracker;
import rene.zirkel.tools.JSmacroTool;
import rene.zirkel.tools.ObjectTracker;

/**
 *
 * @author erichake
 */
public class JIcon extends windowComponent {
    // icon_group==null means simple push button
    // icon_group==icon_name name means simple toggle button

    private String icon_name="";
    private String icon_group="";
    private boolean isSelected; // icon state
    private boolean isDisabled=false; // icon disabled ?
    private boolean isEntered=false; // Mouseover ?
    private Image image=null;
    private int IconsPerRow=-1; // Number of icons per row.
    // If -1 then this is the standard number defined
    // by themes.palette_icon_per_row

    @Override
    public void paintComponent(final java.awt.Graphics g) {
        super.paintComponent(g);
        final java.awt.Dimension d=this.getSize();
        final int w=d.width;
        final int h=d.height;
        final Graphics2D g2=windowComponent.getGraphics2D(g);
        if (isDisabled) {
            final ImageFilter filter=new GrayFilter(true, 60);
            final Image disImage=createImage(new FilteredImageSource(image.getSource(), filter));
            final ImageIcon myicn=new ImageIcon(disImage);
            g2.drawImage(myicn.getImage(), 0, 0, w, h, this);
            return;
        }
        g2.drawImage(image, 0, 0, w, h, this);
        if (isSelected) {
            final AlphaComposite ac=AlphaComposite.getInstance(
                    AlphaComposite.SRC_OVER, 0.2f);
            g2.setComposite(ac);
            g2.setColor(new Color(0, 0, 100));
            // g2.fillRoundRect(1,1,w-1,h-1,14,14);
            g2.fillRect(1, 1, w-1, h-1);
        }
        if (isEntered) {
            final AlphaComposite ac=AlphaComposite.getInstance(
                    AlphaComposite.SRC_OVER, 0.1f);
            g2.setComposite(ac);
            g2.setColor(new Color(0, 0, 80));
            final Stroke stroke=new BasicStroke(3f);
            g2.setStroke(stroke);
            g2.drawRect(2, 2, w-4, h-4);
        }
    }

    public JIcon(String name, String group) {
        super();
        icon_name=name;
        icon_group=group;
        image=themes.getPaletteImage(name);
        PaletteManager.registerIcon(this);
        String ttp=PaletteManager.ToolTip(name);
        if (ttp!=null) {
            setToolTipText("<html>"+ttp.replace("+", "<br>")+"</html>");
        }
        setOpaque(false);
    }

    public JIcon(String name, String group, int iconperrow) {
        this(name, group);
        IconsPerRow=iconperrow;
    }

    public String getIconGroup() {
        return icon_group;
    }

    public String getIconName() {
        return icon_name;
    }

    public boolean isToggleButton() {
        return (icon_name.equals(icon_group));
    }

    public boolean isPushButton() {
        return (icon_group==null);
    }

    public void setOver(boolean b) {
        isEntered=b;
    }

    public void setSelected(boolean b) {
        isSelected=b;
    }

    public void setDisabled(boolean b) {
        isDisabled=b;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public boolean isDisabled() {
        return isDisabled;
    }

    public void init() {
        int w=getIconWidth();
        setPreferredSize(new Dimension(w, w));
    }

    public int getIconWidth() {
        if (IconsPerRow==-1) {
            return themes.getPaletteIconWidth();
        } else {
            int w=(themes.getPaletteIconPerRow()*themes.getPaletteIconWidth())/IconsPerRow;
            return w;
        }
    }

    @Override
    public Point getToolTipLocation(MouseEvent event) {
        return new Point(getSize().width/2, getSize().height+4);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        ClicOnMe();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        PaletteManager.setOverBtn(this);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        isEntered=false;
        PaletteManager.repaint();
    }
    private static ZirkelFrame ZF;
    private static ZirkelCanvas ZC;
    private static JZirkelCanvas JZF;
    private static String moveonreselect=",delete,hide,rename,edit,zoom,animate,";

    private static void TasksBeforeClick(JIcon me) {
        ZF.CurrentTool=0;
        if (PaletteManager.isGeomGroup(me.getIconName())) {
            AreaConstructor.deletePreview(ZF.ZC);
            QuadricConstructor.deletePreview(ZF.ZC);
        }
        CreateMacroDialog.quit();
        ZF.ZC.JCM.hideHandles(null);
    }

    public void ClicOnMe() {
        if ((moveonreselect.indexOf(","+icon_name+",")!=-1)&&(isSelected)) {
            PaletteManager.setSelected_with_clic("move", true);
            return;
        }
        PaletteManager.setSelectBtn(this);
        ZF=JZirkelCanvas.getCurrentZF();
        JZF=JZirkelCanvas.getCurrentJZF();
        ZC=JZirkelCanvas.getCurrentZC();
        if (ZC==null) {
            return;
        }
	if(ZC.getTool() instanceof JSmacroTool){
	    ((JSmacroTool) ZC.getTool()).invalidate(ZC);
	}

        PaletteManager.setGoodProperties(icon_name);
        TasksBeforeClick(this);
        action(this, icon_name);
    }

    private static boolean checkReplay(String o) {
        if (o.equals("oneforward")) {
            JZF.getReplay().iconPressed(o);
        } else if (o.equals("oneback")) {
            JZF.getReplay().iconPressed(o);
        } else if (o.equals("fastforward")) {
            JZF.getReplay().iconPressed(o);
        } else if (o.equals("fastback")) {
            JZF.getReplay().iconPressed(o);
        } else if (o.equals("allforward")) {
            JZF.getReplay().iconPressed(o);
        } else if (o.equals("setbreak")) {
            JZF.getReplay().iconPressed(o);
        } else if (o.equals("nextbreak")) {
            JZF.getReplay().iconPressed(o);
        } else {
            JZF.getReplay().doclose();
            return false;
        }
        return true;
    }

    private static void action(JIcon me, String o) {

        if (checkReplay(o)) {
            return;
        }
        if (me.isDisabled) {
            return;
        }
        ZC.pause(true);
        ZC.requestFocus();
        ZF.setinfo(o);

        if ((ZC.isDP())&&(!ZC.isEuclidian())&&(MacroTools.isDPMacro(o))) {
            MacroTools.runDPMacro(o);
        } else if (o.equals("select")) {
            ZF.ZC.setSelectTool();
        } else if (o.equals("load")) {
            ZF.setinfo("save");
            FileTools.openFile();
        } else if (o.equals("save")) {
            ZF.setinfo("save");
            FileTools.saveFile();
        } else if (o.equals("new")) {
            tab_main_panel.newTabBtn();
            pipe_tools.actualiseMacroPanel();
        } else if (o.equals("newmacro")) {
            new CreateMacroDialog(JZirkelCanvas.getNewMacroPanel());
            JZirkelCanvas.ActualiseMacroPanel();
        } else if (o.equals("manage_macros")) {
            LeftPanel.showPanel(me.isSelected);
        } else if (o.startsWith("type")) {
            ZF.settype(Integer.parseInt(o.substring(4)));
            ZF.setinfo("zone_aspect");
        } else if (o.startsWith("thickness")) {
            setObjectColorType(Integer.parseInt(o.substring(9)));
            ZF.setinfo("zone_aspect");
        } else if (o.startsWith("filled")) {
            setObjectFilled(me.isSelected);
        } else if (o.startsWith("color")) {
            ZF.setinfo("zone_aspect");
            setObjectColor(Integer.parseInt(o.substring(5)));
        } else if (o.startsWith("acolor")) {
            final int chx=Integer.parseInt(o.substring(6));
            ZF.ZC.setAxis_color(chx);
            ZF.ZC.repaint();
        } else if (o.startsWith("athickness")) {
            final int chx=Integer.parseInt(o.substring(10));
            ZF.ZC.setAxis_thickness(chx);
            ZF.ZC.repaint();
        } else if (o.equals("numgrid")) {
            ZF.ZC.setAxis_labels(me.isSelected);
            ZF.ZC.repaint();
        } else if (o.equals("dottedgrid")) {
            ZF.ZC.setAxis_with_grid(me.isSelected);
            ZF.ZC.repaint();
        } else if (o.equals("vector")) {
            //ZF.setVectors(true);
            ZF.settool("vector");
        } else if (o.equals("segment")) {
            //ZF.setVectors(false);
            ZF.settool("segment");
        } else if (o.equals("fixedsegment")) {

            ZF.setVectors(false);
            ZF.settool("fixedsegment");
        } else if (o.equals("image3")) {

            ZF.settool("image");

//            if (!ZF.haveFile()) {
//                JOptionPane.showMessageDialog(null, Global.Loc("palette.image.fileerror"));
////                        PaletteManager.MW.savefile();
//            }
//
//            if (ZF.haveFile()) {
//                ZF.settool("image");
//            } else {
//                PaletteManager.setSelected("point", true);
//            }
        } else if (o.equals("background")) {
            ZF.setinfo("background");
            if (me.isSelected) {
                Global.setParameter("background.usesize",
                            false);
                    Global.setParameter("background.tile",
                            false);
                    Global.setParameter("background.center",
                            true);
                    ZF.loadBackground();
                    PaletteManager.setSelected_with_clic("imcenter", true);
            } else {
                ZF.ZC.grab(false);
            }
        } else if (o.equals("imcenter")) {
            rene.gui.Global.setParameter("background.usesize", false);
            rene.gui.Global.setParameter("background.tile", false);
            rene.gui.Global.setParameter("background.center", true);
            ZF.setinfo("background");
            ZF.ZC.repaint();

        } else if (o.equals("imtile")) {
            rene.gui.Global.setParameter("background.usesize", false);
            rene.gui.Global.setParameter("background.tile", true);
            rene.gui.Global.setParameter("background.center", false);
            ZF.setinfo("background");
            ZF.ZC.repaint();
        } else if (o.equals("imstretch")) {
            rene.gui.Global.setParameter("background.usesize", true);
            rene.gui.Global.setParameter("background.tile", false);
            rene.gui.Global.setParameter("background.center", false);
            ZF.setinfo("background");
            ZF.ZC.repaint();
//                } else if (name.equals("loadmacros")) {
//                    JMacrosTools.OpenMacro("");
//                } else if (name.equals("new")) {
//                    JMacrosTools.NewWindow();
//                } else if (name.equals("load")) {
//                    JMacrosTools.OpenFile();
//                } else if (name.equals("save")) {
//                    PaletteManager.MW.savefile();
        } else if (o.equals("allback")) {
            // ZF.settool(ZF.NParameters);
            JZF.disposeReplay();
            JZF.newReplay();
            JZF.getReplay().iconPressed(o);
        } else if (o.equals("partial")) {
            ZF.ZC.setPartial(me.isSelected);
        } else if (o.equals("plines")) {
            ZF.ZC.setPartialLines(me.isSelected);
        } else if (o.equals("showvalue")) {
            setObjectShowValue(me.isSelected);
        } else if (o.equals("hidden")) {
            ZF.sethidden(me.isSelected);
            ZC.reloadCD();
            ZF.setinfo("hidden");
        } else if (o.equals("showname")) {
            setObjectShowName(me.isSelected);
        } else if (o.equals("bold")) {
            setObjectBold(me.isSelected);
        } else if (o.equals("large")) {
            setObjectLarge(me.isSelected);
        } else if (o.equals("longnames")) {
            ZF.setLongNames(me.isSelected);
            ZF.setinfo("defaults");
        } else if (o.equals("obtuse")) {
            setObjectObtuse(me.isSelected);
        } else if (o.equals("solid")) {
            setObjectSolid(me.isSelected);
        } else if (o.equals("grid")) {
            ZF.ZC.setAxis_show(me.isSelected);
            if (me.isSelected) {
                ZF.ZC.createAxisObjects();
            } else {
                ZF.ZC.deleteAxisObjects();
            }
            ZF.ZC.repaint();
            ZF.setinfo("grid");
        } else if (o.equals("objecttracker")) {

            ZirkelFrame.ObjectConstructors[ZirkelFrame.NObjectTracker]=new ObjectTracker();
            ZF.settool("objecttracker");
        } else if (o.equals("locus")) {

            ZirkelFrame.ObjectConstructors[ZirkelFrame.NObjectTracker]=new JLocusObjectTracker();
            ZF.settool("objecttracker");
        } else if (o.equals("function")) {
            ZF.ZC.createCurve();
            ZF.setinfo("function");
        } else if (o.equals("equationxy")) {
            ZF.ZC.createEquationXY();
//                } else if (name.equals("manage_macros")) {
//                    PaletteManager.MW.ZContent.ShowLeftPanel(2);
//                } else if (name.equals("help_panel")) {
//                    PaletteManager.MW.ZContent.ShowLeftPanel(3);
//                } else if (name.equals("newmacro")) {
//                    PaletteManager.MW.ZContent.ShowMacroPanel();
//                    PaletteManager.MW.ZContent.macros.myJML.MacrosTree.JML.controls.createbtn.setSelected(true);
//                    PaletteManager.MW.ZContent.macros.myJML.MacrosTree.JML.createmacropanel.appeargently();
//                    PaletteManager.deselectgeomgroup();
//                } else if (name.equals("history_panel")) {
//                    PaletteManager.MW.ZContent.ShowLeftPanel(1);
//                    // } else if (name.equals("properties_panel")){
//                    // JGlobals.JPB.showme(me.isSelected);
                } else if (o.equals("copy")) {
                    FileTools.exportGraphicFile(FileTools.PNG, null);
                } else if (o.equals("exportpng")) {
                    FileTools.exportGraphicFile(FileTools.PNG);
                } else if (o.equals("exporteps")) {
                    FileTools.exportGraphicFile(FileTools.EPS);
                } else if (o.equals("exportsvg")) {
                    FileTools.exportGraphicFile(FileTools.SVG);
                } else if (o.equals("exportpdf")) {
                    FileTools.exportGraphicFile(FileTools.PDF);
        } else if (o.startsWith("bi_")) {
            if (o.equals("bi_function_u")) {
                Global.setParameter("options.point.shownames", false);
                JZF.getPointLabel().getBetterName(null, true);
            }

            MacroTools.runBuiltinMacro("@builtin@/"+o.substring(3));
        } else if (o.equals("back")) {
            ZF.ZC.back();
            ZF.settool(o);
        } else if (o.equals("undo")) {
            ZF.ZC.undo();
            ZF.settool(o);
        } else if (o.equals("boundedpoint")) {

            ZF.settool("boundedpoint");
        } else if (o.equals("ctrl_slider")) {
            ZF.ZC.setNullTool();
        } else if (o.equals("ctrl_popup")) {
            ZF.ZC.setNullTool();
        } else if (o.equals("ctrl_chkbox")) {
            ZF.ZC.setNullTool();
        } else if (o.equals("ctrl_button")) {
            ZF.ZC.setNullTool();
        } else if (o.equals("ctrl_edit")) {
            ZF.ZC.setNullTool();
        } else if (o.equals("ctrl_txtfield")) {
            ZF.ZC.setNullTool();
        } else {
            ZF.settool(o);
        }

        JIconMouseAdapter.setgeomSelectedIcon();
        ZC.pause(false);
        ZC.requestFocus();

    }

    private static boolean isIconWithProperties(final String name) {
        final String acceptedIcons=",expression,locus,bi_function_u,text,area,ray,segment,"+"line,point,parallel,plumb,intersection,midpoint,bi_syma,"+"bi_symc,bi_trans,bi_med,bi_biss,vector,fixedsegment,circle,"+"circle3,fixedcircle,bi_arc,bi_circ,angle,fixedangle,quadric,"+"boundedpoint,";
        return (acceptedIcons.indexOf(","+name+",")!=-1);
    }

    public static void setObjectColor(final int i) {
        String iconname=PaletteManager.geomSelectedIcon();
        if (isIconWithProperties(iconname)) {
            iconname=PaletteManager.IconFamily(iconname);
            Global.setParameter("options."+iconname+".pcolor", (Color) null);
            Global.setParameter("options."+iconname+".color", i);
            PaletteZone_Aspect.getColorPicker().setSelected(false);
            PaletteZone_Aspect.getColorPicker().setDefaultColor();
        } else {
            ZF.setcolor(i);
        }
    }

    public static void setObjectColor(final Color c) {
        String iconname=PaletteManager.geomSelectedIcon();
        if (isIconWithProperties(iconname)) {
            iconname=PaletteManager.IconFamily(iconname);
            Global.setParameter("options."+iconname+".pcolor", c);
        }

    }

    private static void setObjectColorType(final int i) {
        String iconname=PaletteManager.geomSelectedIcon();
        if (isIconWithProperties(iconname)) {
            iconname=PaletteManager.IconFamily(iconname);
            Global.setParameter("options."+iconname+".colortype", i);
        } else {
            ZF.setcolortype(i);
        }
    }

    private static void setObjectShowName(final boolean bool) {
        String iconname=PaletteManager.geomSelectedIcon();
        if (isIconWithProperties(iconname)) {
            iconname=PaletteManager.IconFamily(iconname);
            Global.setParameter("options."+iconname+".shownames", bool);
        } else {
            ZF.setShowNames(bool);
        }
        ZF.setinfo("defaults");
        JZF.getPointLabel().getBetterName(null, true);
    }

    private static void setObjectShowValue(final boolean bool) {
        String iconname=PaletteManager.geomSelectedIcon();
        if (isIconWithProperties(iconname)) {
            iconname=PaletteManager.IconFamily(iconname);
            Global.setParameter("options."+iconname+".showvalues", bool);
        } else {
            ZF.ZC.setShowValues(bool);
        }
    }

    private static void setObjectFilled(final boolean bool) {
        String iconname=PaletteManager.geomSelectedIcon();
        if (isIconWithProperties(iconname)) {
            iconname=PaletteManager.IconFamily(iconname);
            Global.setParameter("options."+iconname+".filled", bool);
        } else {
            // ZF.ZC.setShowValues(bool);
        }
    }

    private static void setObjectSolid(final boolean bool) {
        String iconname=PaletteManager.geomSelectedIcon();
        if (isIconWithProperties(iconname)) {
            iconname=PaletteManager.IconFamily(iconname);
            Global.setParameter("options."+iconname+".solid", bool);
        } else {
            // ZF.ZC.setShowValues(bool);
        }
    }

    private static void setObjectLarge(final boolean bool) {
        String iconname=PaletteManager.geomSelectedIcon();
        if (isIconWithProperties(iconname)) {
            iconname=PaletteManager.IconFamily(iconname);
            Global.setParameter("options."+iconname+".large", bool);
        } else {
            ZF.ZC.setLargeFont(bool);
        }

    }

    private static void setObjectBold(final boolean bool) {
        String iconname=PaletteManager.geomSelectedIcon();
        if (isIconWithProperties(iconname)) {
            iconname=PaletteManager.IconFamily(iconname);
            Global.setParameter("options."+iconname+".bold", bool);
        } else {
            ZF.ZC.setBoldFont(bool);
        }
    }

    private static void setObjectObtuse(final boolean bool) {
        String iconname=PaletteManager.geomSelectedIcon();
        if (isIconWithProperties(iconname)) {
            iconname=PaletteManager.IconFamily(iconname);
            Global.setParameter("options."+iconname+".obtuse", bool);
        } else {
            ZF.ZC.setObtuse(bool);
        }
    }
}
