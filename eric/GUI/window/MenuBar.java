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
import javax.swing.BoxLayout;
import javax.swing.JLabel;

/**
 *
 * @author erichake
 */
public class MenuBar extends windowComponent {

    private Point winloc;
    private Point origin;
    private Point current;
    private static MenuBar me;

    public void paintComponent(Graphics g) {
        Dimension d=getSize();
        g.drawImage(themes.getImage("menubar.gif"), 0, 0, d.width, d.height,
                this);
    }

    public void init() {
        setBounds(0, themes.getTitleBarHeight(), pipe_tools.getWindowSize().width-themes.getTotalRightPanelWidth(), themes.getMenuBarHeight());
    }

    public MenuBar() {
        me=this;
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        add(pipe_tools.getMenuBar());
    }

    public static void setMenuVisible(boolean b) {
        try {
            me.getComponent(0).setVisible(b);
        } catch (Exception e) {
        }
    }

    public static boolean isMenuVisible() {
        return me.getComponent(0).isVisible();
    }

    public static void reloadMenuBar() {
        me.removeAll();
        me.add(pipe_tools.getMenuBar());
        me.revalidate();
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
