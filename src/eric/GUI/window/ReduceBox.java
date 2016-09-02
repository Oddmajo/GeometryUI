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
import javax.swing.JFrame;

/**
 *
 * @author erichake
 */
public class ReduceBox extends windowComponent {

    private String btn="zreducebutton.png";
    private String overbtn="zreducebuttonover.png";
    private Image icon=themes.getImage(btn);

    public void paintComponent(Graphics g) {
        Dimension d=this.getSize();
        g.drawImage(icon, 0, 0, d.width, d.height,
                this);
    }

    public ReduceBox() {
    }

    public void init() {
//        win=StaticTools.getMainWindow(this);
        int x;
        if (themes.MacLF()) {
            x=themes.getBoxesMarginWidth()+themes.getCloseBoxDim().width;
        } else {
            x=pipe_tools.getWindowSize().width-themes.getBoxesMarginWidth()-themes.getCloseBoxDim().width-themes.getGrowBoxDim().width-themes.getReduceBoxDim().width-themes.getTotalRightPanelWidth();
        }
        this.setBounds(x,
                themes.getBoxesMarginHeight(),
                themes.getReduceBoxDim().width,
                themes.getReduceBoxDim().height);
    }

    public void mousePressed(MouseEvent e) {
        if (pipe_tools.getWindow() instanceof MainWindow) {
            MainWindow mw=(MainWindow) pipe_tools.getWindow();
            mw.setExtendedState(JFrame.ICONIFIED);
            mw.setComponents();
        }
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
