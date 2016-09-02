/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.GUI.window;

import eric.GUI.themes;
import eric.GUI.windowComponent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

/**
 *
 * @author erichake
 */
public class LeftPanel_close_btn extends windowComponent {

    private boolean over=false;
    private static int W=themes.getIcon("tab_close.png").getIconWidth();
    private static int H=themes.getIcon("tab_close.png").getIconHeight();
    private static int marginW=5;
    private static int marginH=8;

    @Override
    public void paintComponent(Graphics g) {
        Dimension d=getSize();
        if (over) {
            g.drawImage(themes.getImage("tab_close_over.png"), 0, 0, d.width, d.height,
                    this);
        } else {
            g.drawImage(themes.getImage("tab_close.png"), 0, 0, d.width, d.height,
                    this);
        }
    }

    public void init() {
        setBounds(LeftPanel.getPanelWidth()-W-marginW, marginH, W, H);
    }

    public LeftPanel_close_btn() {
    }

    @Override
    public void mousePressed(MouseEvent e) {
//        LeftPanel.showPanel(false);
//        Open_left_panel_btn.setSelected(false);
//        over=false;
        Open_left_panel_btn.toggle();
    }

    public void mouseEntered(MouseEvent e) {
        over=true;
        repaint();
    }

    public void mouseExited(MouseEvent e) {
        over=false;
        repaint();
    }
}


