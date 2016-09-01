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
public class CloseBox extends windowComponent {

    private String btn="zclosebutton.png";
    private String overbtn="zclosebuttonover.png";
    private Image icon=themes.getImage(btn);

    public void paintComponent(Graphics g) {
        Dimension d=this.getSize();
        g.drawImage(icon, 0, 0, d.width, d.height,
                this);
    }

    public CloseBox() {
    }

    public void init() {
//        win=StaticTools.getMainWindow(this);
        int marginL;
        if (themes.MacLF()) {
            marginL=themes.getBoxesMarginWidth();
        } else {
            marginL=pipe_tools.getWindowSize().width-themes.getBoxesMarginWidth()-themes.getCloseBoxDim().width-themes.getTotalRightPanelWidth();
        }
        this.setBounds(marginL,
                themes.getBoxesMarginHeight(),
                themes.getCloseBoxDim().width,
                themes.getCloseBoxDim().height);
    }

    public void mousePressed(MouseEvent e) {
        pipe_tools.quitAll();
    }

    public void mouseEntered(MouseEvent arg0) {
        icon=themes.getImage(overbtn);
        repaint();
    }

    public void mouseExited(MouseEvent arg0) {
        icon=themes.getImage(btn);
        repaint();
    }
}
