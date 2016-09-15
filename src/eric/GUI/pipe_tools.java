/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.GUI;

import eric.FileTools;
import eric.GUI.palette.PaletteManager;
import eric.GUI.window.*;
import eric.JEricPanel;
import eric.JGeneralMenuBar;
import eric.JZirkelCanvas;
import eric.bar.JPropertiesBar;
import eric.macros.CreateMacroDialog;
import eric.macros.CreateMacroPanel;
import eric.monkey.monkey;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import rene.gui.Global;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.ZirkelFrame;
import rene.zirkel.macro.Macro;
import ui.netscape.javascript.JSObject;

/**
 * This is a static class provide to be a bridge between the GUI
 * packages and the others. Other "engine" packages should always refer to this
 * one whenever they need to interact with the GUI. The GUI also must go through
 * this class to interact with the engine.
 * @author erichake
 */
public class pipe_tools {

    private static MainContainer owner;

    /**
     * Called by MainWindow or MainApplet at the begining of
     * initialization process (constructor or init)
     * @param mc : the main container -> JFrame or JApplet
     */
    public static void init(MainContainer mc) {
        owner=mc;
        themes.setTheme(Global.getParameter("LookAndFeel", themes.GRAY));
    }

    public static boolean isApplet() {
        return owner.isApplet();
    }

    public static MainContainer getWindow() {
        return owner;
    }

    public static Frame getFrame() {
        MainContainer mc=owner;
        if (mc instanceof MainWindow) {
            return (Frame) mc;
        } else {
            return new Frame();
        }
    }

    public static ContentPane getContent() {
        return owner.getContent();
    }

    public static void setWindowComponents() {
        owner.setComponents();
    }

    public static Point getWindowLocation() {
        return owner.getLocation();
    }

    public static void setWindowLocation() {
        setWindowLocation(owner.getLocation().x, owner.getLocation().y);
    }

    public static void setWindowLocation(int x, int y) {
        if (y<Global.getScreenY()+JPropertiesBar.getBarHeight()) {
            y=Global.getScreenY()+JPropertiesBar.getBarHeight();
        }
        owner.setLocation(x, y);
    }

    public static Dimension getWindowSize() {
        return owner.getSize();
    }

    public static void setWindowSize(int w, int h) {
        owner.setSize(w, h);
    }

    public static void setAndCheckWindowSize(int w, int h) {
        if ((!isApplet())&&(JZirkelCanvas.isWorkBook())) {
            if (w>Global.getScreenW()) {
                w=Global.getScreenW();
            }
            if (h>Global.getScreenH()) {
                h=Global.getScreenH();
            }
            int x=(Global.getScreenW()-w)/2;
            int y=(Global.getScreenH()-h)/2;
            setWindowLocation(x, y);
            owner.setSize(w, h);
        }
    }

    public static void setWindowBounds() {
        setWindowBounds(getFrame().getBounds());
    }

    // r est le bounds désiré de la fenêtre :
    public static void setWindowBounds(Rectangle r) {
        if (getWindow() instanceof MainWindow) {

            if (r.y<Global.getScreenY()+JPropertiesBar.getBarHeight()) {
                r.y=Global.getScreenY()+JPropertiesBar.getBarHeight();
            }
            if (r.y+r.height>Global.getScreenY()+Global.getScreenH()) {
                r.height=Global.getScreenY()+Global.getScreenH()-r.y;
            }
            getFrame().setBounds(r);

            // I know, this is weird, but it work's (for palette display) :
            owner.setComponents();
            owner.setComponents();
        }
    }
    static private KeyStroke enter_key=KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);

    public static void setStandardKeyInputs() {
        KeyStroke key=KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, InputEvent.SHIFT_DOWN_MASK);
        owner.getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(key, "objectTracker");
        owner.getRootPane().getActionMap().put("objectTracker", new AbstractAction() {

	    @Override
            public void actionPerformed(final ActionEvent arg0) {
                ZirkelFrame zf=JZirkelCanvas.getCurrentZF();
                zf.track();
            }
        });

        key=KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
        owner.getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(key, "escape");
        owner.getRootPane().getActionMap().put("escape", new AbstractAction() {

	    @Override
            public void actionPerformed(final ActionEvent arg0) {
                PaletteManager.setSelected_with_clic("move", true);
                CreateMacroDialog.quit();
            }
        });


    }

    public static void setMacroPanelKeyInputs() {
        owner.getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(enter_key, "macro_enter");
        owner.getRootPane().getActionMap().put("macro_enter", new AbstractAction() {

	    @Override
            public void actionPerformed(final ActionEvent arg0) {
                CreateMacroPanel.nextStep();
            }
        });
    }

    public static void removeMacroPanelKeyInputs() {
        owner.getRootPane().getActionMap().remove(enter_key);
    }

    public static void toFront() {
        if (!isApplet()) {
            MainWindow mw=(MainWindow) owner;
            mw.toFront();
        }
    }

    public static void showRestrictedEnvironmentManager() {
        ZirkelCanvas zc=JZirkelCanvas.getCurrentZC();
        if (zc!=null) {
            zc.getNewRestrictedDialog();
        }
    }

    public static void showExerciseManager() {
        ZirkelCanvas zc=JZirkelCanvas.getCurrentZC();
        if (zc!=null) {
            zc.job_showDialog();
        }
    }

    public static void AppletLoadedMessage_To_HTML() {
        if (isApplet()) {
            MainApplet ma=(MainApplet) owner;
            JSObject window=JSObject.getWindow(ma);
            String[] args={};
            window.call("carmetal_applet_loaded", args);
        }
    }

    public static boolean Exercise_To_HTML(boolean success, String message) {
        Object jsmethod=null;
        if (isApplet()) {
            try {
                MainApplet ma=(MainApplet) owner;
                JSObject window=JSObject.getWindow(ma);
                String[] args={""+success, message};
                jsmethod=window.call("carmetal_job", args);
            } catch (Exception e) {
                return false;
            }
        }
        return (jsmethod!=null);
    }

    public static void Magnet_To_HTML(String Pt, String Obj) {
        if (isApplet()) {
            MainApplet ma=(MainApplet) owner;
            JSObject window=JSObject.getWindow(ma);
            String[] args={Pt, Obj};
            window.call("carmetal_magnet", args);
        }
    }

    public static JEricPanel getCanvasPanel() {
        return ContentPane.getCanvasPanel();
    }

    public static void duplicateTab() {
        try {
            String s=FileTools.getCurrentFileSource();
            tab_main_panel.addBtnAndSelect(tab_main_panel.getActiveBtn().getTabName()+" copy");
            FileTools.setCurrentFileSource(s);
        } catch (Exception ex) {
        }

    }

    public static void TabHaveChanged(boolean b) {
        if (JZirkelCanvas.isWorkBook()) {
            tab_main_panel.allTabsHaveChanged(b);
        } else {
            tab_main_panel.currentTabHaveChanged(b);
        }
    }

    public static void actualiseMacroPanel() {
        LeftPanel_content.setContent(JZirkelCanvas.getNewMacroPanel());
        JZirkelCanvas.ActualiseMacroPanel();
    }

    public static void actualiseLeftPanels() {
        JZirkelCanvas.removeLeftPanelContent();
        if (LeftPanel.isHistoryPanelVisible()) {
            LeftPanel_content.setContent(JZirkelCanvas.getNewCDPPanel());
        } else if (LeftPanel.isHelpPanelVisible()) {
            LeftPanel_content.setContent(JZirkelCanvas.getNewInfoPanel());
        } else if (LeftPanel.isMacroPanelVisible()) {
            actualiseMacroPanel();
        } else if(LeftPanel.isScriptsPanelVisible()){
	    LeftPanel_content.setContent(JZirkelCanvas.getNewScriptsLeftPanel());
	}

    }

    public static void setTitle(String s) {
        getContent().setTitle(s);
    }

    public static JEricPanel getMenuBar() {
        return new JGeneralMenuBar();
    }

    public static boolean isTabEditAccepted() {
        return (JZirkelCanvas.isWorkBook());
    }

    public static void quitAll() {
        JZirkelCanvas.quitAll();
    }

    public static void closeCurrent() {
        JZirkelCanvas.closeCurrent();
    }

    public static void onTabActivate() {
        JZirkelCanvas JZF=JZirkelCanvas.getCurrentJZF();
        if (JZF!=null) {
            JZF.onTabActivate();
            SwingUtilities.invokeLater(new Runnable() {

		@Override
                public void run() {
                    PaletteManager.selectGeomIcon();
                    PaletteManager.initPaletteConsideringMode();
                    PaletteManager.init();
                    actualiseLeftPanels();
                }
            });
	    if(JGeneralMenuBar.get_scp() != null){
		JZF.getZC().add(JGeneralMenuBar.get_scp());
		JGeneralMenuBar.get_scp().refresh();
		JZF.getZC().repaint();
	    }
        }
    }

    public static String processTabName(String name) {
        if (name.endsWith(".zir")) {
            name=name.substring(0, name.length()-4);
        } else if (name.endsWith(".zirz")) {
            name=name.substring(0, name.length()-5);
        }
        name=tab_main_panel.uniqueName(name);
        return name;
    }

    public static void setMacroHelp(Macro m) {
    }

    public static void setComments(String s) {
        ZirkelCanvas zc=JZirkelCanvas.getCurrentZC();
        if (zc!=null) {
            zc.setJobComment(s);
        }
    }

    public static boolean isComments() {
        ZirkelCanvas zc=JZirkelCanvas.getCurrentZC();
        if (zc==null) {
            return false;
        }
        return (!"".equals(zc.getJobComment()));
    }

    public static void saveFigure() {
        FileTools.saveFileAs();
    }
    private static monkey monkey=null;

    public static void monkeyStart() {
        ZirkelCanvas zc=JZirkelCanvas.getCurrentZC();
        if (zc!=null) {
            monkey=new monkey(zc);
            monkey.start();
        }
    }

    public static void monkeyStop() {
        if (monkey!=null) {
            monkey.stop();
            monkey=null;
        }
    }
}
