/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.GUI.window;

import eric.GUI.pipe_tools;
import eric.GUI.windowComponent;
import eric.GUI.themes;
import eric.JZirkelCanvas;
import eric.bar.JPropertiesBar;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import rene.gui.Global;

/**
 *
 * @author erichake
 */
public class GrowBox extends windowComponent {

    private String btn="zgrowbutton.png";
    private String overbtn="zgrowbuttonover.png";
    private Image icon=themes.getImage(btn);

    public void paintComponent(Graphics g) {
        Dimension d=this.getSize();
        g.drawImage(icon, 0, 0, d.width, d.height,
                this);
    }

    public GrowBox() {
    }

    public void init() {
//        win=StaticTools.getMainWindow(this);
        int x;
        if (themes.MacLF()) {
            x=themes.getBoxesMarginWidth()+themes.getCloseBoxDim().width+themes.getReduceBoxDim().width;
        } else {
            x=pipe_tools.getWindowSize().width-themes.getBoxesMarginWidth()-themes.getCloseBoxDim().width-themes.getGrowBoxDim().width-themes.getTotalRightPanelWidth();
        }
        this.setBounds(x,
                themes.getBoxesMarginHeight(),
                themes.getGrowBoxDim().width,
                themes.getGrowBoxDim().height);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        pipe_tools.setWindowBounds(Global.getScreen());
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
        icon=themes.getImage(overbtn);
        repaint();
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
        icon=themes.getImage(btn);
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }
}
