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
import java.awt.Image;
import java.awt.event.MouseEvent;

/**
 *
 * @author erichake
 */
public class tab_close_btn extends windowComponent {

    private static int marginW=5, marginH=7;
    private static int W=themes.getIcon("tab_close.png").getIconWidth();
    private static int H=themes.getIcon("tab_close.png").getIconHeight();
    private static Image overIMG=themes.getImage("tab_close_over.png");
    private static Image IMG=themes.getImage("tab_close.png");
    private boolean over=false;

    @Override
    public void paintComponent(Graphics g) {
        if (tab_main_panel.getBTNSsize()==1) return;
        Dimension d=getSize();
        if (tab_main_panel.rightcut((tab_btn) getParent())) {
            return;
        }
        if (over) {
            g.drawImage(themes.getImage("tab_close_over.png"), 0, 0, d.width, d.height,
                    this);
        } else {
            g.drawImage(themes.getImage("tab_close.png"), 0, 0, d.width, d.height,
                    this);
        }
    }

    public void init() {
        setBounds(getParent().getBounds().width-W-marginW,
                marginH,
                W,
                H);
    }

    public tab_close_btn() {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        over=true;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        over=false;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        tab_btn parent=(tab_btn) getParent();
        tab_main_panel.setActiveBtn(parent);
        parent.repaint();
        pipe_tools.closeCurrent();
    }
}
