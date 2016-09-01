/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric;

import eric.GUI.palette.PaletteManager;
import eric.GUI.pipe_tools;
import eric.GUI.themes;
import eric.GUI.window.comments;
import eric.GUI.window.tab_main_panel;
import eric.JSprogram.JScriptsLeftPanel;
import eric.macros.MacroTools;
import eric.macros.MacrosList;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.swing.JOptionPane;
import rene.gui.Global;
import rene.util.FileName;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.ZirkelFrame;
import rene.zirkel.construction.Construction;
import rene.zirkel.construction.ConstructionDisplayPanel;
import rene.zirkel.dialogs.Replay;
import rene.zirkel.objects.PointObject;

/**
 *
 * @author erichake
 */
public class JZirkelCanvas extends JEricPanel {

//    private static ArrayList<JZirkelCanvas> allJZFs=new ArrayList<JZirkelCanvas>();
    private ZirkelFrame ZF=null;
    private JPointName PointLabel;
    private Replay Dreplay;
    private static boolean restrictedsession=false;

    // For unused figures in workbooks, store the inputstream in a
    // byte array :
    private byte[] byteinputfile=null;
    private static String workbookFileName=null;
    private static JHelpPanel InfoPanel=new JHelpPanel();
    private static MacrosList MacroPanel=null;
    private static JScriptsLeftPanel ScriptsLeftPanel = new JScriptsLeftPanel();

    @Override
    public void paintComponent(final java.awt.Graphics g) {
    }

    public JZirkelCanvas() {
        super();
        setLayout(new BorderLayout());
        setOpaque(true);
        ZF=new ZirkelFrame(pipe_tools.isApplet());
        ZF.ZC.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(final java.awt.event.MouseEvent evt) {
                if (Global.getParameter("smartboard", false)) {
                    return;
                }
                PointLabel.getBetterName(null, true);
            }

            @Override
            public void mouseReleased(final java.awt.event.MouseEvent evt) {
                if (!Global.getParameter("smartboard", false)) {
                    return;
                }
                PointLabel.getBetterName(null, true);
            }
        });
        add(ZF.ZC);
        newReplay();
        PointLabel=new JPointName(this);
        PointObject.setPointLabel(PointLabel);
        MacroTools.setDefaultMacros(ZF.ZC);
        ZF.ZC.getLocalPreferences();
//        allJZFs.add(this);
    }

    public void init() {
        Rectangle r=new Rectangle(0,
                0,
                pipe_tools.getWindowSize().width-themes.getLeftPanelWidth()-themes.getVerticalPanelBorderWidth()-themes.getTotalRightPanelWidth()-2*themes.getVerticalBorderWidth()-themes.getTabLeftBorderWidth()-themes.getTabRightBorderWidth(),
                pipe_tools.getWindowSize().height-themes.getTitleBarHeight()-themes.getMenuBarHeight()-themes.getMainTabPanelHeight()-themes.getCommentsHeight()-themes.getStatusBarHeight());
        setBounds(r);
        ZF.ZC.setBounds(r);
        ZF.ZC.initRestrictDialog();
        ZF.ZC.initJobCreationDialog();
	ZF.ZC.init_cnt();
    }

    public byte[] getByteArrayInputFile() {
        return byteinputfile;
    }

    public void createByteArrayInputFile(InputStream in) {
        if (byteinputfile==null) {
            byteinputfile=FileTools.copyToByteArray(in);
        }
    }

    public void setInputFile(InputStream in) {
        if (in!=null) {
            createByteArrayInputFile(in);
        }
    }

    public void setInputFile(byte[] in) {
        byteinputfile=in;
    }

    public static void getCurrentLocalPreferences() {
        if (getCurrentZC()!=null) {
            getCurrentZC().getLocalPreferences();
        }
    }



    public void onTabActivate() {
        if (byteinputfile!=null) {
            FileTools.openFile("", new ByteArrayInputStream(byteinputfile), 0);
            byteinputfile=null;
        }
        ZF.ZC.setLocalPreferences();
        PointObject.setPointLabel(PointLabel);
        ZF.ZC.updateDigits();
        ZF.ZC.paint(ZF.ZC.getGraphics());
        PaletteManager.refresh();
    }

    public static boolean isWorkBook() {
        return (workbookFileName!=null);
    }

    public static String getWorkBookFileName() {
        return workbookFileName;
    }

    public static String getFileName(){
        try{
        if (isWorkBook()){
            return getWorkBookFileName();
        }else{
            return getCurrentZF().Filename;
        }
        }catch(Exception e){
            return null;
        }
    }

    public static void setWorkBookFileName(String s, boolean force) {
        if ((force)||(workbookFileName==null)) {
            workbookFileName=s;
            if (!pipe_tools.isApplet()){
            pipe_tools.setTitle(Global.Loc("program.name")+" - "+Global.Loc("workbook.workbookmode")+" : "+FileName.filename(s));
            pipe_tools.TabHaveChanged(false);
            }
        }
    }

    public static boolean isRestrictedSession() {
        return restrictedsession;
    }

    public Replay getReplay() {
        return Dreplay;
    }

    public void disposeReplay() {
        Dreplay.dispose();
    }

    public void newReplay() {
        Dreplay=new Replay(null, ZF.ZC);
    }

    public JPointName getPointLabel() {
        return PointLabel;
    }

    public ZirkelFrame getZF() {
        return ZF;
    }

    public ZirkelCanvas getZC() {
        return ZF.ZC;
    }



    public static String ToolTip(final String s) {
        String ToolTipText="";
        final String purename=(s.startsWith("bi_"))?s.substring(3):s;
        try {
            ToolTipText=Global.Loc("palette.info."+purename);
        } catch (final Exception e1) {
            try {
                ToolTipText=Global.Loc("palette.info."+s);
            } catch (final Exception e2) {
                ToolTipText=rene.gui.Global.name("iconhelp."+purename);
            }
        }

        return ToolTipText;
    }

    public static String FilteredStatus(final String status) {
        String newstatus=status;
        int index;
        if ((index=status.indexOf("@builtin@/DP_line"))>0) {
            newstatus=newstatus.substring(index);
            newstatus=newstatus.replaceAll("\\Q@builtin@/DP_line\\E",
                    Global.Loc("palette.info.DP_line"));
        }





        else if ((index=status.indexOf("@builtin@/DP_midpoint"))>0) {
            newstatus=newstatus.substring(index);
            newstatus=newstatus.replaceAll("\\Q@builtin@/DP_midpoint\\E",
                    Global.Loc("palette.info.DP_midpoint"));
        }

         else if ((index=status.indexOf("@builtin@/DP_bi_syma"))>0) {
            newstatus=newstatus.substring(index);
            newstatus=newstatus.replaceAll("\\Q@builtin@/DP_bi_syma\\E",
                    Global.Loc("palette.info.DP_bi_syma"));
        }

        else if ((index=status.indexOf("@builtin@/DP_bi_symc"))>0) {
            newstatus=newstatus.substring(index);
            newstatus=newstatus.replaceAll("\\Q@builtin@/DP_bi_symc\\E",
                    Global.Loc("palette.info.DP_bi_symc"));
        }

        else if ((index=status.indexOf("@builtin@/DP_plumb"))>0) {
            newstatus=newstatus.substring(index);
            newstatus=newstatus.replaceAll("\\Q@builtin@/DP_plumb\\E",
                    Global.Loc("palette.info.DP_plumb"));
        }

        else if ((index=status.indexOf("@builtin@/DP_bi_med"))>0) {
            newstatus=newstatus.substring(index);
            newstatus=newstatus.replaceAll("\\Q@builtin@/DP_bi_med\\E",
                    Global.Loc("palette.info.DP_bi_med"));
        }

        else if ((index=status.indexOf("@builtin@/DP_bi_biss"))>0) {
            newstatus=newstatus.substring(index);
            newstatus=newstatus.replaceAll("\\Q@builtin@/DP_bi_biss\\E",
                    Global.Loc("palette.info.DP_bi_biss"));
        }

        else if ((index=status.indexOf("@builtin@/DP_segment"))>0) {
            newstatus=newstatus.substring(index);
            newstatus=newstatus.replaceAll("\\Q@builtin@/DP_segment\\E",
                    Global.Loc("palette.info.DP_segment"));
        }

        else if ((index=status.indexOf("@builtin@/DP_ray"))>0) {
            newstatus=newstatus.substring(index);
            newstatus=newstatus.replaceAll("\\Q@builtin@/DP_ray\\E",
                    Global.Loc("palette.info.DP_ray"));
        }

        else if ((index=status.indexOf("@builtin@/DP_angle"))>0) {
            newstatus=newstatus.substring(index);
            newstatus=newstatus.replaceAll("\\Q@builtin@/DP_angle\\E",
                    Global.Loc("palette.info.DP_angle"));
        }

        else if ((index=status.indexOf("@builtin@/DP_circle"))>0) {
            newstatus=newstatus.substring(index);
            newstatus=newstatus.replaceAll("\\Q@builtin@/DP_circle\\E",
                    Global.Loc("palette.info.DP_circle"));
        }

        else if ((index=status.indexOf("@builtin@/DP_bi_distance"))>0) {
            newstatus=newstatus.substring(index);
            newstatus=newstatus.replaceAll("\\Q@builtin@/DP_bi_distance\\E",
                    Global.Loc("palette.info.DP_bi_distance"));
        }

        else if ((index=status.indexOf("@builtin@/DP_bi_lineIP"))>0) {
            newstatus=newstatus.substring(index);
            newstatus=newstatus.replaceAll("\\Q@builtin@/DP_bi_lineIP\\E",
                    Global.Loc("palette.info.DP_bi_lineIP"));
        }

        else if ((index=status.indexOf("@builtin@/DP_bi_perp_common"))>0) {
            newstatus=newstatus.substring(index);
            newstatus=newstatus.replaceAll("\\Q@builtin@/DP_bi_perp_common\\E",
                    Global.Loc("palette.info.DP_bi_perp_common"));
        }

        else if ((index=status.indexOf("@builtin@/DP_bi_pinceau1"))>0) {
            newstatus=newstatus.substring(index);
            newstatus=newstatus.replaceAll("\\Q@builtin@/DP_bi_pinceau1\\E",
                    Global.Loc("palette.info.DP_bi_pinceau1"));
        }

        else if ((index=status.indexOf("@builtin@/DP_bi_pinceau3"))>0) {
            newstatus=newstatus.substring(index);
            newstatus=newstatus.replaceAll("\\Q@builtin@/DP_bi_pinceau3\\E",
                    Global.Loc("palette.info.DP_bi_pinceau3"));
        }

        else if ((index=status.indexOf("@builtin@/DP_bi_pinceauinter"))>0) {
            newstatus=newstatus.substring(index);
            newstatus=newstatus.replaceAll("\\Q@builtin@/DP_bi_pinceauinter\\E",
                    Global.Loc("palette.info.DP_bi_pinceauinter"));
        }

        else if ((index=status.indexOf("@builtin@/DP_bi_pinceauhauteur"))>0) {
            newstatus=newstatus.substring(index);
            newstatus=newstatus.replaceAll("\\Q@builtin@/DP_bi_pinceauhauteur\\E",
                    Global.Loc("palette.info.DP_bi_pinceauhauteur"));
        }

        else if ((index=status.indexOf("@builtin@/DP_bi_pinceaucycle"))>0) {
            newstatus=newstatus.substring(index);
            newstatus=newstatus.replaceAll("\\Q@builtin@/DP_bi_pinceaucycle\\E",
                    Global.Loc("palette.info.DP_bi_pinceaucycle"));
        }

        else if ((index=status.indexOf("@builtin@/DP_bi_pinceaubiss"))>0) {
            newstatus=newstatus.substring(index);
            newstatus=newstatus.replaceAll("\\Q@builtin@/DP_bi_pinceaubiss\\E",
                    Global.Loc("palette.info.DP_bi_pinceaubiss"));
        }

        else if ((index=status.indexOf("@builtin@/DP_bi_equidistante"))>0) {
            newstatus=newstatus.substring(index);
            newstatus=newstatus.replaceAll("\\Q@builtin@/DP_bi_equidistante\\E",
                    Global.Loc("palette.info.DP_bi_equidistante"));
        }

        else if ((index=status.indexOf("@builtin@/DP_bi_horocycle"))>0) {
            newstatus=newstatus.substring(index);
            newstatus=newstatus.replaceAll("\\Q@builtin@/DP_bi_horocycle\\E",
                    Global.Loc("palette.info.DP_bi_horocycle"));
        }

        else if ((index=status.indexOf("@builtin@/DP_fixedangle"))>0) {
            newstatus=newstatus.substring(index);
            newstatus=newstatus.replaceAll("\\Q@builtin@/DP_fixedangle\\E",
                    Global.Loc("palette.info.DP_fixedangle"));
        }

        else if ((index=status.indexOf("@builtin@/DP_bi_circ"))>0) {
            newstatus=newstatus.substring(index);
            newstatus=newstatus.replaceAll("\\Q@builtin@/DP_bi_circ\\E",
                    Global.Loc("palette.info.DP_bi_circ"));
        }



        else if ((index=status.indexOf("@builtin@/syma"))>0) {
            newstatus=newstatus.substring(index);
            newstatus=newstatus.replaceAll("\\Q@builtin@/syma\\E",
                    Global.Loc("palette.info.bi_syma"));
        } else if ((index=status.indexOf("@builtin@/symc"))>0) {
            newstatus=newstatus.substring(index);
            newstatus=newstatus.replaceAll("\\Q@builtin@/symc\\E",
                    Global.Loc("palette.info.bi_symc"));
        } else if ((index=status.indexOf("@builtin@/trans"))>0) {
            newstatus=newstatus.substring(index);
            newstatus=newstatus.replaceAll("\\Q@builtin@/trans\\E",
                    Global.Loc("palette.info.bi_trans"));
        } else if ((index=status.indexOf("@builtin@/med"))>0) {
            newstatus=newstatus.substring(index);
            newstatus=newstatus.replaceAll("\\Q@builtin@/med\\E",
                    Global.Loc("palette.info.bi_med"));
        } else if ((index=status.indexOf("@builtin@/biss"))>0) {
            newstatus=newstatus.substring(index);
            newstatus=newstatus.replaceAll("\\Q@builtin@/biss\\E",
                    Global.Loc("palette.info.bi_biss"));
        } else if ((index=status.indexOf("@builtin@/circ"))>0) {
            newstatus=newstatus.substring(index);
            newstatus=newstatus.replaceAll("\\Q@builtin@/circ\\E",
                    Global.Loc("palette.info.bi_circ"));
        } else if ((index=status.indexOf("@builtin@/arc"))>0) {
            newstatus=newstatus.substring(index);
            newstatus=newstatus.replaceAll("\\Q@builtin@/arc\\E",
                    Global.Loc("palette.info.bi_arc"));
        } else if ((index=status.indexOf("@builtin@/function_u"))>0) {
            newstatus=newstatus.substring(index);
            newstatus=newstatus.replaceAll("\\Q@builtin@/function_u\\E",
                    ToolTip("bi_function_u"));
        } else if ((index=status.indexOf("@builtin@/t_align"))>0) {
            newstatus=newstatus.substring(index);
            newstatus=newstatus.replaceAll("\\Q@builtin@/t_align\\E",
                    Global.Loc("palette.info.bi_t_align"));
        } else if ((index=status.indexOf("@builtin@/t_para"))>0) {
            newstatus=newstatus.substring(index);
            newstatus=newstatus.replaceAll("\\Q@builtin@/t_para\\E",
                    Global.Loc("palette.info.bi_t_para"));
        } else if ((index=status.indexOf("@builtin@/t_perp"))>0) {
            newstatus=newstatus.substring(index);
            newstatus=newstatus.replaceAll("\\Q@builtin@/t_perp\\E",
                    Global.Loc("palette.info.bi_t_perp"));
        } else if ((index=status.indexOf("@builtin@/t_equi"))>0) {
            newstatus=newstatus.substring(index);
            newstatus=newstatus.replaceAll("\\Q@builtin@/t_equi\\E",
                    Global.Loc("palette.info.bi_t_equi"));
        } else if ((index=status.indexOf("@builtin@/t_app"))>0) {
            newstatus=newstatus.substring(index);
            newstatus=newstatus.replaceAll("\\Q@builtin@/t_app\\E",
                    Global.Loc("palette.info.bi_t_app"));
        } else if ((index=status.indexOf("@builtin@/t_conf"))>0) {
            newstatus=newstatus.substring(index);
            newstatus=newstatus.replaceAll("\\Q@builtin@/t_conf\\E",
                    Global.Loc("palette.info.bi_t_conf"));
        } else if ((index=status.indexOf("@builtin@/3Dcoords"))>0) {
            newstatus=newstatus.substring(index);
            newstatus=newstatus.replaceAll("\\Q@builtin@/3Dcoords\\E",
                    Global.Loc("palette.info.bi_3Dcoords"));
        } else if ((index=status.indexOf("@builtin@/3Dcube"))>0) {
            newstatus=newstatus.substring(index);
            newstatus=newstatus.replaceAll("\\Q@builtin@/3Dcube\\E",
                    Global.Loc("palette.info.bi_3Dcube"));
        } else if ((index=status.indexOf("@builtin@/3Darete"))>0) {
            newstatus=newstatus.substring(index);
            newstatus=newstatus.replaceAll("\\Q@builtin@/3Darete\\E",
                    Global.Loc("palette.info.bi_3Darete"));
        } else if ((index=status.indexOf("@builtin@/3Dtetra"))>0) {
            newstatus=newstatus.substring(index);
            newstatus=newstatus.replaceAll("\\Q@builtin@/3Dtetra\\E",
                    Global.Loc("palette.info.bi_3Dtetra"));
        } else if ((index=status.indexOf("@builtin@/3Ddode"))>0) {
            newstatus=newstatus.substring(index);
            newstatus=newstatus.replaceAll("\\Q@builtin@/3Ddode\\E",
                    Global.Loc("palette.info.bi_3Ddode"));
        }

        return newstatus;
    }

    public static boolean workbookHaveChanged() {
        boolean changed=false;
        for (int i=0; i<tab_main_panel.getBTNSsize(); i++) {
            if (tab_main_panel.getBTN(i).getPanel().getZC().changed()) {
                return true;
            }
        }
        return changed;
    }

    private boolean close() {
        return ZF.close();
    }

    public static void setFactoryProperties(){
        Global.setParameter("colorbackground", new Color(245,245,245));
        Global.setParameter("colorbackgroundx", 139);
        Global.setParameter("colorbackgroundy", 9);
        Global.setParameter("colorbackgroundPal", 4);
    }

    // Static part :
    public static boolean closeCurrent() {
        if (getCurrentJZF()==null) {
            return false;
        }
        boolean b=getCurrentJZF().close();
        if (b) {
            if (tab_main_panel.getBTNSsize()==1) {
                setFactoryProperties();
                Global.saveProperties("CaR Properties");
                MacroTools.saveLibraryToDisk();
                Global.exit(0);
            } else {
                tab_main_panel.removeBtnAndSelect(tab_main_panel.getActiveBtn());
            }
        }
        return b;
    }

    public static void quitAll() {
        if(getCurrentZC().get_cnt()!=null) {
            getCurrentZC().get_cnt().doClose();
        }
        if (isWorkBook()) {
            if (workbookHaveChanged()) {
                int rep=JOptionPane.showConfirmDialog(getCurrentJZF(), Global.Loc("workbook.savequestion"));
                if (rep==JOptionPane.CANCEL_OPTION) {
                    return;
                }
                if (rep==JOptionPane.OK_OPTION) {
                    FileTools.saveWorkBook(getWorkBookFileName());
                }
            }

            setFactoryProperties();
            Global.saveProperties("CaR Properties");
            MacroTools.saveLibraryToDisk();
            Global.exit(0);
        } else {
            while (closeCurrent()) {
            }
        }

    }

    public static int getZCsSize(){
        return tab_main_panel.getBTNSsize();
    }

    public static ZirkelCanvas getZC(int i){
        try {
            JZirkelCanvas jzf=(JZirkelCanvas) tab_main_panel.getPanel(i);
            return jzf.getZC();
        } catch (Exception e) {
            return null;
        }
    }

    public static String getFilePath(Construction c){
        for (int i=0;i<getZCsSize();i++){
            JZirkelCanvas jzf=(JZirkelCanvas) tab_main_panel.getPanel(i);
            if (c.equals(jzf.getZC().getConstruction())){
                return FileName.pathAndSeparator(jzf.getZF().Filename);
            }
        }
        return null;
    }



    public static JZirkelCanvas getCurrentJZF() {
        try {
            JZirkelCanvas zf=(JZirkelCanvas) tab_main_panel.getActivePanel();
            return zf;
        } catch (Exception e) {
            return null;
        }
    }

    public static ZirkelFrame getCurrentZF() {
        try {
            return getCurrentJZF().getZF();
        } catch (Exception e) {
            return null;
        }
    }

    public static ZirkelCanvas getCurrentZC() {
        try {
            return getCurrentZF().ZC;
        } catch (Exception e) {
            return null;
        }
    }

    public static void stopAllScripts(){
        ZirkelCanvas zc=getCurrentZC();
        if (zc!=null) {
            zc.stopAllScripts();
        }
    }
    public static void restartAllScripts(){
        ZirkelCanvas zc=getCurrentZC();
        if (zc!=null) {
            zc.restartAllScripts();
        }
    }
//    public static void prepareDragActionScripts(ConstructionObject o){
//        ZirkelCanvas zc=getCurrentZC();
//        if (zc!=null) {
//            zc.prepareDragActionScripts(o);
//        }
//    }
//    public static void runDragAction(){
//        ZirkelCanvas zc=getCurrentZC();
//        if (zc!=null) {
//            zc.runDragAction();
//        }
//    }
//    public static void stopDragAction(){
//        ZirkelCanvas zc=getCurrentZC();
//        if (zc!=null) {
//            zc.stopDragAction();
//        }
//    }




    public static boolean isPaintCalled(){
        ZirkelCanvas zc=getCurrentZC();
        if (zc!=null) {
            return zc.isPaintCalled();
        }else{
            return false;
        }
    }

    public static boolean getCurrent3dMode() {
        try {
            return getCurrentZC().is3D();
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean getCurrentDPMode() {
        try {
            return getCurrentZC().isDP();
        } catch (Exception e) {
            return false;
        }
    }

    public static void repaintZC() {
        ZirkelCanvas zc=getCurrentZC();
        if (zc!=null) {
            zc.repaint();
        }
    }

    public static void setCurrent3dMode(boolean bool) {
        try {
            getCurrentZC().set3D(bool);
        } catch (Exception e) {
        }
    }

    public static void setCurrentDPMode(boolean bool) {
        try {
            getCurrentZC().setDP(bool);
        } catch (Exception e) {
        }
    }

    public static void setinfo(final String s, final boolean WithTxtFocus) {
        JHelpPanel.Subject=s;
        if (InfoPanel!=null) {
            InfoPanel.clearSearchTxtField();
            InfoPanel.fill(WithTxtFocus);
        }
    }

    public static JHelpPanel getNewInfoPanel() {
        InfoPanel=null;
        InfoPanel=new JHelpPanel();
        return InfoPanel;
    }

    public static ConstructionDisplayPanel getNewCDPPanel() {
        ZirkelCanvas zc=getCurrentZC();
        if (zc!=null) {
            return zc.getNewCDP();
        } else {
            return null;
        }
    }

    public static MacrosList getNewMacroPanel() {
        MacroPanel=new MacrosList(getCurrentZF());
        return MacroPanel;
    }

    public static JScriptsLeftPanel getNewScriptsLeftPanel(){
	ScriptsLeftPanel = null;
	ScriptsLeftPanel = new JScriptsLeftPanel();
	return ScriptsLeftPanel;
    }

    public static JScriptsLeftPanel getScriptsLeftPanel(){
	return ScriptsLeftPanel;
    }

    public void setComments() {
//        System.out.println("setcomments :"+getZC().getJobComment());
        String s=getZC().getJobComment();
        if (s==null) s="";
        comments.setLabelText(s);
    }

    public static void ActualiseMacroPanel() {
        if (MacroPanel==null) {
            MacroPanel=getNewMacroPanel();
        }
        MacroPanel.initTreeFromZCMacros();
    }

    public static void removeLeftPanelContent() {
        ZirkelCanvas zc=getCurrentZC();
        if (zc!=null) {
            zc.removeCDP();
            InfoPanel=null;
        }
    }
//    static private Image trash=themes.getPaletteImage("delete");
//    public static void drawToolImage(ZirkelCanvas zc){
//        zc.getGraphics().drawImage(toolimage, zc.getMousePosition().x, zc.getMousePosition().y, zc);
////        Cursor c = Toolkit.getDefaultToolkit().createCustomCursor(trash , new Point(0,0), "img");
////        zc.setCursor(c);
////        System.out.println("d");
////        zc.I.getGraphics().drawImage(trash, zc.getMousePosition().x, zc.getMousePosition().y, zc);
//    }
}
