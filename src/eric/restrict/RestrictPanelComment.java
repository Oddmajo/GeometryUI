/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.restrict;

import eric.GUI.palette.PaletteManager;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import rene.gui.Global;

/**
 *
 * @author erichake
 */
public class RestrictPanelComment extends JLabel {

    public RestrictPanelComment() {
        super("<html><center>"+Global.Loc("restrict.comment")+"</center></html>");
        setFont(new Font(Global.GlobalFont,0,12));
        setForeground(Color.darkGray);
        PaletteManager.fixsize(this, RestrictContainer.getContainerWidth(), 60);
        setHorizontalTextPosition(SwingUtilities.CENTER);
        setHorizontalAlignment(SwingUtilities.CENTER);
        setVerticalAlignment(SwingUtilities.CENTER);
    }
}
