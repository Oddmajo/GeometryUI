/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.GUI.window;

import eric.GUI.pipe_tools;
import eric.GUI.themes;
import eric.GUI.windowComponent;
import eric.JHelpPanel;
import eric.JSprogram.JScriptsLeftPanel;
import eric.macros.MacrosList;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import rene.zirkel.construction.ConstructionDisplayPanel;

/**
 *
 * @author erichake
 */
public class LeftPanel_content extends windowComponent {

    private static LeftPanel_content me;
    private static JComponent JP;

    public void init() {
        setBounds(themes.getVerticalBorderWidth(),
                themes.getTitleBarHeight()+themes.getMenuBarHeight()+LeftPanel.getPanelHeight()+themes.getCommentsHeight(),
                themes.getLeftPanelWidth(),
                pipe_tools.getWindowSize().height-themes.getTitleBarHeight()-themes.getMenuBarHeight()-themes.getMainTabPanelHeight()-LeftPanel.getPanelHeight()-themes.getCommentsHeight());
        if (JP instanceof JHelpPanel) {
            JHelpPanel jh=(JHelpPanel) JP;
            jh.fixPanelSize(getSize().width, getSize().height);
        } else if (JP instanceof MacrosList) {
            MacrosList ml=(MacrosList) JP;
            ml.fixPanelSize(getSize().width, getSize().height);
        } else if (JP instanceof ConstructionDisplayPanel) {
            ConstructionDisplayPanel cdp=(ConstructionDisplayPanel) JP;
            cdp.fixPanelSize(getSize().width, getSize().height);
        } else if(JP instanceof JScriptsLeftPanel){
	    JScriptsLeftPanel jsp = (JScriptsLeftPanel) JP;
	    jsp.fixPanelSize(getSize().width, getSize().height);
	}
    }

    public LeftPanel_content() {
        me=this;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    public static void setPanelVisibility(){
         if (JP instanceof MacrosList) {
            JP.setVisible(LeftPanel.isMacroBtnVisible());
        } else if (JP instanceof ConstructionDisplayPanel) {
            JP.setVisible(LeftPanel.isHistoryBtnVisible());
        } else if (JP instanceof JHelpPanel) {
            JP.setVisible(LeftPanel.isHelpBtnVisible());
        } else if (JP instanceof JScriptsLeftPanel) {
            JP.setVisible(LeftPanel.isScriptsBtnVisible());
        }
    }

    public static void setContent(JComponent jp) {
        JP=jp;
        me.removeAll();
        me.add(jp);
        me.init();
        me.revalidate();
        setPanelVisibility();
        me.repaint();
    }

    public static Dimension getPanelSize() {
        return me.getSize();
    }
}


