/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.GUI.window;

import eric.GUI.pipe_tools;
import eric.GUI.themes;
import eric.GUI.windowComponent;
import eric.JZirkelCanvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.construction.ConstructionDisplayPanel;

/**
 *
 * @author erichake
 */
public class LeftPanel_history_btn extends windowComponent implements LeftPanel_btn {

    private boolean over=false;
    private boolean selected=false;
    private static int X=(themes.getIcon("leftpanel_on_btn.gif").getIconWidth()-themes.getIcon("left_history.png").getIconWidth())/2;
    private static int Y=2;

    @Override
    public void paintComponent(Graphics g) {
        Dimension d=getSize();
        if (selected) {
            g.drawImage(LeftPanel.getOnBtn(), 0, 0, d.width, d.height,
                    this);
        } else {
            g.drawImage(LeftPanel.getOffBtn(), 0, 0, d.width, d.height,
                    this);
        }
        g.drawImage(themes.getImage("left_history.png"), X, Y, this);
    }

    public void init() {
        setBounds(LeftPanel.x(this), LeftPanel.y(), LeftPanel.getBtnDim().width, LeftPanel.getBtnDim().height);
    }

    public LeftPanel_history_btn() {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        LeftPanel.select(this);
    }

    public void select(boolean b) {
        selected=b;
    }

    public boolean isPanelSelected() {
        return selected;
    }
}


