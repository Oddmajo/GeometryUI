/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.restrict;

import eric.GUI.palette.PaletteManager;
import eric.GUI.themes;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.BorderFactory;
import eric.JEricPanel;

/**
 *
 * @author erichake
 */
public class RestrictPanelFlowIcons extends JEricPanel {

    public RestrictPanelFlowIcons() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
//        setBorder(BorderFactory.createEmptyBorder());
//        setOpaque(false);
        setBackground(new Color(218,228,242));
        setBorder(BorderFactory.createEtchedBorder());
        setAlignmentX(0.0f);
        setAlignmentY(0.0f);
    }

    public void addIcons(RestrictPanelIconsLineTitle title,String[] icns) {
        for (int i=0; i<icns.length; i++) {
            addIcon(title,icns[i]);
        }
        fixsize();
    }

    public void addIcon(RestrictPanelIconsLineTitle title,String icn) {
        RestrictPanelIcon restrictIcon=new RestrictPanelIcon(title, icn);
        add(restrictIcon);
        title.addIcon(restrictIcon);
    }

    public void fixsize() {
        if (getComponentCount()==0) {
            return;
        }
        RestrictPanelIcon ri=(RestrictPanelIcon) getComponent(0);
        int w=ri.getSize().width*themes.getPaletteIconPerRow();
        int h=ri.getSize().height*(1+(getComponentCount()-1)/themes.getPaletteIconPerRow());
        PaletteManager.fixsize(this, w+10, h+10);
    }
}
