/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.GUI.window;

import eric.GUI.themes;
import eric.GUI.windowComponent;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

/**
 *
 * @author PM
 */
public class LeftPanel_scripts_btn extends windowComponent implements LeftPanel_btn {

    private boolean selected = false;
    private static int X = (themes.getIcon("leftpanel_on_btn.gif").getIconWidth()-17)/2; //17 : voir ligne 34
    private static int Y = 3;

    public LeftPanel_scripts_btn() {
    }

    @Override
    public void paintComponent(Graphics g) {
        Dimension d=getSize();
        if (selected) {
            g.drawImage(LeftPanel.getOnBtn(), 0, 0, d.width, d.height, this);
        } else {
            g.drawImage(LeftPanel.getOffBtn(), 0, 0, d.width, d.height, this);
        }
	g.drawImage(themes.resizeExistingIcon("/eric/GUI/icons/themes/common/scripts.png", 17, 17).getImage(),X,Y,this);
    }

    public void init() {
        setBounds(LeftPanel.x(this),LeftPanel.y(),LeftPanel.getBtnDim().width,LeftPanel.getBtnDim().height);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        LeftPanel.select(this);
    }

    @Override
    public void select(boolean b) {
	selected = b;
    }

    @Override
    public boolean isPanelSelected() {
        return selected;
    }
}