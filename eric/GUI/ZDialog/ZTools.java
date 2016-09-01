/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.GUI.ZDialog;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JComponent;
import rene.gui.Global;

/**
 *
 * @author erichake
 */
public class ZTools {
    public static final Color backMainColor=new Color(100, 100, 100, 50);
    public static final Color backTitleColor=new Color(0, 0, 0, 200);
    public static final Color TitleTextColor=new Color(255, 255, 255);
    public static final Color C_TextField=new Color(50, 50, 50);                // Text color of ZTextFields
    public static final Color C_TextField_OFF=new Color(150, 150, 150);        // Disable Text color of ZTextFields
    public static final Color B_TextField=new Color(245, 246, 255);            // ZTextFields background color
    public static final Color Bord_TextField=new Color(50, 50, 50);            // ZTextFields line border color

    public static final Font ZLabelFont=new Font(Global.GlobalFont, 0, 11);
    public static final Font ZCheckBoxFont=new Font(Global.GlobalFont, 0, 11);
    public static final Font ZTextFieldFont=new Font(Global.GlobalFont, 0, 11);
    public static final Font ZDialogTitleFont=new Font(Global.GlobalFont, 1, 12);


    public static void fixsize(JComponent jc, int w, int h) {
        Dimension d=new Dimension(w, h);
        jc.setSize(d);
        jc.setMaximumSize(d);
        jc.setMinimumSize(d);
        jc.setPreferredSize(d);
    }
}
