/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.GUI.window;

import eric.GUI.pipe_tools;
import eric.GUI.windowComponent;
import eric.GUI.themes;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * @author erichake
 */
public class tab_left extends windowComponent {

    private Point winloc;

    public void paintComponent(Graphics g) {
        Dimension d=getSize();
        g.drawImage(themes.getImage("tab_left.gif"), 0, 0, d.width, d.height,
                this);
    }

    public void init() {
//        win=StaticTools.getMainWindow(this);
        setBounds(themes.getVerticalBorderWidth()+themes.getLeftPanelWidth()+themes.getVerticalPanelBorderWidth(),
                themes.getTitleBarHeight()+themes.getMenuBarHeight()+themes.getCommentsHeight()-1,
                themes.getTabLeftBorderWidth(),
                pipe_tools.getWindowSize().height-themes.getTitleBarHeight()-themes.getMenuBarHeight()-themes.getCommentsHeight()-themes.getStatusBarHeight()-themes.getMainTabPanelHeight()+2);
    }

    public tab_left() {
    }
}
