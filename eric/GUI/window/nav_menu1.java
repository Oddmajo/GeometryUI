/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.GUI.window;

import eric.GUI.windowComponent;
import eric.GUI.themes;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

/**
 *
 * @author erichake
 */
public class nav_menu1 extends windowComponent implements PopupMenuListener {

    private static int marginW=60;
    private static int W=themes.getIcon("navmenu1.png").getIconWidth();
    private static int H=themes.getIcon("navmenu1.png").getIconHeight();
    private boolean over=false;
    private boolean active=false;
    private static nav_menu1 myself=null;

    @Override
    public void paintComponent(Graphics g) {
        Dimension d=getSize();
        if (active) {
            g.drawImage(themes.getImage("navmenu1pushed.png"), 0, 0, d.width, d.height,
                    this);
        } else {
            g.drawImage(themes.getImage("navmenu1.png"), 0, 0, d.width, d.height,
                    this);
        }

    }

    public void init() {
        setBounds(marginW+tab_control_panel.getMarginLeft(),
                tab_control_panel.getMarginTop(),
                W,
                H);
    }

    public nav_menu1() {
        myself=this;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        JPopupMenu popup=tab_main_panel.getTabPopup();
        popup.removePopupMenuListener(this);
        active=true;
        popup.addPopupMenuListener(this);
        popup.show(this, (W-popup.getPreferredSize().width)/2, -2-popup.getPreferredSize().height);
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
        removeMouseListener(this);
    }

    public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
        active=false;
        repaint();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                addMouseListener(myself);
            }
        });
    }

    public void popupMenuCanceled(PopupMenuEvent e) {
    }
}


