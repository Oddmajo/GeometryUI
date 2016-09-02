/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.GUI.palette;

import eric.JZirkelCanvas;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import rene.gui.Global;
import rene.zirkel.ZirkelCanvas;

/**
 *
 * @author erichake
 */
public class PaletteZoneCheckBox extends JCheckBox implements ItemListener{

    public PaletteZoneCheckBox(String txt) {
        super(txt);
        setOpaque(false);
        setFocusable(false);
        setFont(new java.awt.Font(Global.GlobalFont, 1, 11));
        setForeground(new Color(100, 100, 100));
        setHorizontalAlignment(SwingConstants.CENTER);
        putClientProperty("JComponent.sizeVariant", "mini");
        addItemListener(this);
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

    public void itemStateChanged(ItemEvent e) {
        ZirkelCanvas zc=JZirkelCanvas.getCurrentZC();
        if (zc!=null){
            zc.setEuclidian(isSelected());
            zc.repaint();
        }

        PaletteManager.initPaletteConsideringMode();
        PaletteManager.init();
        PaletteManager.selectGeomIcon();
    }


}
