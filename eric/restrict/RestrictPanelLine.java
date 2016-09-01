/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.restrict;

import eric.GUI.palette.PaletteManager;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import rene.gui.Global;

/**
 *
 * @author erichake
 */
public class RestrictPanelLine extends JCheckBox implements ItemListener {

    public RestrictPanelLine(String label) {
        super(label);
        setAlignmentX(0.0f);
        setAlignmentY(0.0f);
        setFocusable(false);
        setOpaque(false);
        setFont(new Font(Global.GlobalFont,0,12));
        addItemListener(this);
        PaletteManager.fixsize(this, RestrictContainer.getContainerWidth(), 20);
        
    }

    public void action() {
    }

    public void itemStateChanged(ItemEvent e) {
        action();
    }
}
