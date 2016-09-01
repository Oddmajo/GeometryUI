/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.GUI.window;

import eric.GUI.pipe_tools;
import eric.GUI.windowComponent;
import eric.GUI.themes;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import javax.swing.BoxLayout;
import eric.JEricPanel;

/**
 *
 * @author erichake
 */
public class CanvasPanel extends windowComponent {

        public void init() {
        setBounds(themes.getVerticalBorderWidth()+themes.getLeftPanelWidth()+themes.getVerticalPanelBorderWidth()+themes.getTabLeftBorderWidth(),
                themes.getTitleBarHeight()+themes.getMenuBarHeight()+themes.getCommentsHeight(),
                pipe_tools.getWindowSize().width-themes.getLeftPanelWidth()-themes.getVerticalPanelBorderWidth()-themes.getTotalRightPanelWidth()-2*themes.getVerticalBorderWidth()-themes.getTabLeftBorderWidth()-themes.getTabRightBorderWidth(),
                pipe_tools.getWindowSize().height-themes.getTitleBarHeight()-themes.getMenuBarHeight()-themes.getMainTabPanelHeight()-themes.getCommentsHeight()-themes.getStatusBarHeight());
    }

    public CanvasPanel() {
        super();
        setLayout(new BorderLayout());
//        setOpaque(true);
    }

}
