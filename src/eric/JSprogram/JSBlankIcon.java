/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package eric.JSprogram;

import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JButton;

/**
 *
 * @author erichake
 */
public class JSBlankIcon extends JButton{
public JSBlankIcon(int size) {
        this.setBorder(BorderFactory.createEmptyBorder());
        fixsize(size);
        this.setContentAreaFilled(false);
        this.setOpaque(false);
    }

private void fixsize(final int sze) {
        final Dimension d=new Dimension(sze, sze);
        this.setMaximumSize(d);
        this.setMinimumSize(d);
        this.setPreferredSize(d);
        this.setSize(d);
    }
}
