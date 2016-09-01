/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.GUI.ZDialog;

import java.awt.Dimension;
import java.awt.Graphics;
import eric.JEricPanel;

/**
 *
 * @author erichake
 */
public class ZSep extends JEricPanel {

    int percent=100;

    public void paint(Graphics g) {

        Dimension d=getSize();
        int margin=d.width*(100-percent)/200;
        g.setColor(ZTools.backTitleColor);
        g.fillRect(margin, 0, d.width-2*margin, d.height);

//        Dimension d=getSize();
//        g.setColor(ZTools.B_TextField);
//        g.fillRect(0, 0, d.width, d.height);
//        paintChildren(g);
//        g.setColor(ZTools.Bord_TextField);
//        g.drawRect(0, 0, d.width, d.height);
//        if (withback) {
//            Dimension d=getSize();
//                    g.setColor(ZTools.backTitleColor);
//        g.fillRect(0, 0, d.width, d.height);
//        paintChildren(g);
//    }
//        super.paintComponent(g);
    }

    public ZSep(int prop) {
        super();
        percent=prop;
    }
}
