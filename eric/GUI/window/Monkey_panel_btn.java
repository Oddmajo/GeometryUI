/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.GUI.window;

import eric.GUI.pipe_tools;
import eric.GUI.windowComponent;
import eric.GUI.themes;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import rene.gui.Global;

/**
 *
 * @author erichake
 */
public class Monkey_panel_btn extends windowComponent {

    private static Monkey_panel_btn me;
    private Image icon_off=themes.getImage("monkeybtn_off.png");
    private Image icon_on=themes.getImage("monkeybtn_on.png");
    private boolean isselected=false;
    private boolean isover=false;

    public void paintComponent(Graphics g) {
        Dimension d=this.getSize();
        final int w=d.width;
        final int h=d.height;

        final Graphics2D g2=windowComponent.getGraphics2D(g);
        if (isselected) {
            g2.drawImage(icon_on, 0, 0, w, h, this);
        } else {
            g2.drawImage(icon_off, 0, 0, w, h, this);
        }
        if (isover) {
            final AlphaComposite ac=AlphaComposite.getInstance(
                    AlphaComposite.SRC_OVER, 0.3f);
            g2.setComposite(ac);
            g2.setColor(new Color(0, 0, 80));
            g2.fillRect(7, 5, 17, 11);
        }
    }

    public Monkey_panel_btn() {
        me=this;
        setToolTipText(Global.Loc("monkey.btn.tooltip"));
    }

    public static void setSelected(boolean b) {
        me.isselected=b;
        me.repaint();
    }

    public void init() {
        int x=pipe_tools.getWindowSize().width-themes.getTotalRightPanelWidth()-3*themes.getOpenLeftPanelBtnDim().width-themes.getOpenMiddlePanelBtnDim().width-3*themes.getOpenPanelsBtnsMarginW();
        this.setBounds(x,
                themes.getOpenPanelsBtnsMarginH(),
                themes.getOpenLeftPanelBtnDim().width,
                themes.getOpenLeftPanelBtnDim().height);
    }



    public void mousePressed(MouseEvent e) {
        isselected=true;
        repaint();
        pipe_tools.monkeyStart();
    }

    public void mouseReleased(MouseEvent e) {
        isselected=false;
        repaint();
        pipe_tools.monkeyStop();
    }

    public void mouseEntered(MouseEvent arg0) {
        isover=true;
        repaint();
    }

    public void mouseExited(MouseEvent arg0) {
        isover=false;
        repaint();
    }
}
