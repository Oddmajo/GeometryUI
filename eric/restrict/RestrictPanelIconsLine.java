/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.restrict;

import eric.GUI.palette.PaletteManager;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import eric.JEricPanel;

/**
 *
 * @author erichake
 */
public class RestrictPanelIconsLine extends JEricPanel {
    private int margin=65;

    public RestrictPanelIconsLine(RestrictPanelIconsLineTitle title,String[] icns) {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setAlignmentX(0.0f);
        setAlignmentY(0.0f);
        add(margin(margin));
        setOpaque(false);
        RestrictPanelFlowIcons line=new RestrictPanelFlowIcons();
        line.addIcons(title, icns);
        add(line);
    }

    static JEricPanel margin(int w) {
        JEricPanel mypan=new JEricPanel();
        PaletteManager.fixsize(mypan, w, 1);
        mypan.setLayout(new javax.swing.BoxLayout(mypan, javax.swing.BoxLayout.X_AXIS));
        mypan.setAlignmentX(0.5F);
        mypan.setAlignmentY(0.5F);
        mypan.setOpaque(false);
        mypan.setFocusable(false);
        return mypan;
    }
}
