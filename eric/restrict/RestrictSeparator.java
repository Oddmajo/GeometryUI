/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package eric.restrict;

import eric.GUI.palette.PaletteManager;
import javax.swing.BoxLayout;
import eric.JEricPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

/**
 *
 * @author erichake
 */
public class RestrictSeparator extends JEricPanel{
public RestrictSeparator(int h){
    super();
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    setAlignmentX(0.0f);
    setAlignmentY(0.0f);
    setOpaque(false);
//    setOrientation(SwingConstants.HORIZONTAL);
    PaletteManager.fixsize(this, RestrictContainer.getContainerWidth(), h);
    JSeparator sep=new JSeparator();
    PaletteManager.fixsize(sep, RestrictContainer.getContainerWidth()-100, h);
    add(sep);
}
}
