/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.GUI.palette;

import eric.GUI.themes;
import eric.GUI.windowComponent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import rene.gui.Global;

/**
 *
 * @author erichake
 */
public class PaletteZoneTitle extends windowComponent {

    private static Image offimage=themes.getImage("PaletteTitleBarN.png");
    private static Image onimage=themes.getImage("PaletteTitleBarH.png");
    private static Image rightTriangle=themes.getPaletteImage("PaletteTriangleDroite");
    private static Image bottomTriangle=themes.getPaletteImage("PaletteTriangleBas");
    private static boolean active=false;
    private JLabel title=new JLabel();

    @Override
    public void paintComponent(Graphics g) {
        Dimension d=getSize();
        
        if (active) {
            g.drawImage(onimage, 0, 0, d.width, d.height, this);
        } else {
            g.drawImage(offimage, 0, 0, d.width, d.height, this);
        }
    }

    public void init() {
        PaletteManager.fixsize(this, themes.getRightPanelWidth(), themes.getPaletteZoneTitleHeight());
    }

    public PaletteZoneTitle(String name) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setAlignmentX(0.0f);
        title.setText(name);
        title.setIcon(new ImageIcon(bottomTriangle));
        setHide(Global.getParameter("hidepalette." + name, true));
        title.setIconTextGap(7);
        title.setFont(new java.awt.Font(Global.GlobalFont, 0, 11));
        title.setHorizontalAlignment(SwingConstants.LEFT);
        PaletteManager.fixsize(title, themes.getRightPanelWidth()-themes.getPaletteZoneTitleHeight(), themes.getPaletteZoneTitleHeight());
        add(title);
    }

    public void setHide(boolean b){
        if (b) title.setIcon(new ImageIcon(rightTriangle));
        else title.setIcon(new ImageIcon(bottomTriangle));
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        active=true;
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        active=false;
        repaint();
    }


}
