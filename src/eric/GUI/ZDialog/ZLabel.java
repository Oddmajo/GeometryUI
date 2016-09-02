/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.GUI.ZDialog;

import java.awt.Graphics;
import javax.swing.JLabel;

/**
 *
 * @author erichake
 */
public class ZLabel extends JLabel {

    public void paint(Graphics g) {
        super.paintComponent(g);
    }

    public ZLabel(String s) {
        super(s);
        setFont(ZTools.ZLabelFont);
    }
}
