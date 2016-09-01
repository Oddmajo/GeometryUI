/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.restrict;

import eric.GUI.palette.PaletteManager;
import eric.GUI.themes;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import eric.JEricPanel;
import javax.swing.SwingConstants;
import rene.gui.Global;

/**
 *
 * @author erichake
 */
public class RestrictContainerTitle extends JEricPanel {

    @Override
    public void paintComponent(Graphics g){
        Dimension d=getSize();
        g.setColor(new Color(230,230,230));
        g.fillRect(0, 0, d.width, d.height);
        g.setColor(new Color(130,130,130));
        g.drawLine(0, d.height-1, d.width, d.height-1);
    }

    public RestrictContainerTitle() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        JLabel label=new JLabel(Global.Loc("restrict.title"));
//        setBackground(new Color(230,230,230));
        PaletteManager.fixsize(this, RestrictContainer.getContainerWidth(), 25);
        PaletteManager.fixsize(label, RestrictContainer.getContainerWidth(), 25);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setFont(new Font(Global.GlobalFont,0,12));
        add(label);
    }


}
