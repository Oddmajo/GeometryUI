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
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;

/**
 *
 * @author erichake
 */
public class VerticalRightBorder extends windowComponent {
    private Point origin;
    private Point current;
    private Point winloc;

    public void paintComponent(Graphics g) {
        Dimension d=getSize();
        g.drawImage(themes.getImage("verticalseparator.png"), 0, 0, d.width, d.height,
                this);
    }

    public void init() {
//        win=StaticTools.getMainWindow(this);
        setBounds(pipe_tools.getWindowSize().width-themes.getVerticalBorderWidth()-themes.getTotalRightPanelWidth(),
                themes.getTitleBarHeight()+themes.getMenuBarHeight(),
                themes.getVerticalBorderWidth(),
                pipe_tools.getWindowSize().height-themes.getTitleBarHeight()-themes.getMenuBarHeight()-themes.getMainTabPanelHeight());
    }

    public VerticalRightBorder() {
    }

    public void mousePressed(MouseEvent e) {
        origin=MouseInfo.getPointerInfo().getLocation();
        winloc=pipe_tools.getWindowLocation();
    }

    public void mouseDragged(MouseEvent arg0) {
        current=MouseInfo.getPointerInfo().getLocation();
        pipe_tools.setWindowLocation(winloc.x+current.x-origin.x, winloc.y+current.y-origin.y);
        pipe_tools.setWindowComponents();
    }
}
