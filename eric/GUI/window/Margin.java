/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.GUI.window;

import java.awt.Dimension;
import eric.JEricPanel;

/**
 *
 * @author erichake
 */
public class Margin extends JEricPanel {

    public static final int HORIZONTAL=1, VERTICAL=2;

    public Margin(int length, int orientation) {
        int w, h;
        if (orientation==HORIZONTAL) {
            w=length;
            h=1;
        } else {
            w=1;
            h=length;
        }
        Dimension d=new Dimension(w, h);
        setPreferredSize(d);
        setMinimumSize(d);
        setMaximumSize(d);
        setSize(d);
        setOpaque(false);
        setFocusable(false);
    }
}
