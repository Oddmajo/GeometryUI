/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.GUI.window;

import eric.GUI.palette.PaletteManager;
import eric.GUI.pipe_tools;
import eric.GUI.windowComponent;
import eric.GUI.themes;
import eric.JGeneralMenuBar;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import rene.gui.Global;

/**
 *
 * @author erichake
 */
public class Open_left_panel_btn extends windowComponent {

    private static Open_left_panel_btn me;
    private Image icon_off=themes.getImage("leftpanel_off.png");
    private Image icon_on=themes.getImage("leftpanel_on.png");
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

    public Open_left_panel_btn() {
        me=this;
        setToolTipText(Global.Loc("menu.display.leftpanel"));
    }

    public static void setSelected(boolean b) {
        me.isselected=b;
        me.repaint();
    }

    public void init() {
        int x=pipe_tools.getWindowSize().width-themes.getTotalRightPanelWidth()-2*themes.getOpenLeftPanelBtnDim().width-themes.getOpenMiddlePanelBtnDim().width-themes.getOpenPanelsBtnsMarginW();
        this.setBounds(x,
                themes.getOpenPanelsBtnsMarginH(),
                themes.getOpenLeftPanelBtnDim().width,
                themes.getOpenLeftPanelBtnDim().height);
    }

    public static void open() {
        if (!LeftPanel.isPanelVisible()) {
            toggle();
        }
    }

    public static void setmode(){
        if (me!=null) {
            me.isselected=LeftPanel.isPanelVisible();
        }
    }

    public static void toggle() {
        LeftPanel.showPanel(!LeftPanel.isPanelVisible());
        if (me!=null) {
            me.isselected=LeftPanel.isPanelVisible();
            me.repaint();
        }
        JGeneralMenuBar.initToggleItems();
        tab_main_panel.initToggleItems();
        if (pipe_tools.isApplet()) {
            pipe_tools.setWindowComponents();
            PaletteManager.init();
        }
    }

    public void mousePressed(MouseEvent e) {
        toggle();
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
