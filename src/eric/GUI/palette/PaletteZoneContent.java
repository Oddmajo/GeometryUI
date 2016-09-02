/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.GUI.palette;

import eric.GUI.themes;
import eric.GUI.windowComponent;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JComponent;

/**
 *
 * @author erichake
 */
public class PaletteZoneContent extends windowComponent {

    private static Image back=themes.getImage("palbackground.gif");
    private boolean visible=true;
    private int height=-1;
    private PaletteZone parent;

    @Override
    public void paintComponent(Graphics g) {
        Dimension d=getSize();
        g.drawImage(back, 0, 0, d.width, d.height, this);
    }

    // For Restricted Palette only :
    public void setVisibleHeight() {
        int nb=0;
        // count visible icons :
        for (int i=0; i<getComponentCount(); i++) {
            JComponent ji=(JComponent) getComponent(i);
            if (ji.isVisible()) {
                nb++;
            }
        }
        int w=themes.getPaletteIconWidth()*themes.getPaletteIconPerRow();
        int h=themes.getPaletteIconWidth()*(1+(nb-1)/themes.getPaletteIconPerRow());
        PaletteManager.fixsize(this, w, h);
    }

    public void init() {
        if ((visible)&&(getComponentCount()>0)) {
            if (PaletteManager.isPaletteWithIconOnly(parent)) {
                setVisibleHeight();
            } else {
                JComponent ji=(JComponent) getComponent(getComponentCount()-1);
                int start=getSize().height;
                int bottom=ji.getBounds().y+ji.getBounds().height;
                PaletteManager.fixsize(this, themes.getRightPanelWidth(), bottom);
            }
        } else {
            PaletteManager.fixsize(this, themes.getRightPanelWidth(), 0);
        }
    }

    public void setHide(boolean b) {
        visible=!b;
    }

    public boolean isHidden(){
        return !visible;
    }

    public PaletteZoneContent(PaletteZone zone) {
        super();
        parent=zone;
        setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        setAlignmentX(0.0f);
    }
}
