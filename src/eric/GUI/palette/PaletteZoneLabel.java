/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.GUI.palette;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import rene.gui.Global;

/**
 *
 * @author erichake
 */
public class PaletteZoneLabel extends JLabel {

    public PaletteZoneLabel(String txt) {
        super(txt);
        setOpaque(false);
        setFont(new java.awt.Font(Global.GlobalFont, 1, 11));
        setForeground(new Color(100, 100, 100));
        setHorizontalAlignment(SwingConstants.CENTER);
    }

    public void paintComponent(Graphics g) {
        Dimension d=getSize();
        Graphics2D g2=(Graphics2D) g;
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.05f));
        g2.setColor(new Color(0, 0, 255));
        g2.fillRect(3, 3, d.width-6, d.height-6);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.15f));
        g2.drawRect(3, 3, d.width-7, d.height-7);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        super.paintComponent(g);
    }
}
