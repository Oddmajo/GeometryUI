/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.GUI.palette;

import eric.GUI.themes;
import eric.JZirkelCanvas;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import rene.zirkel.ZirkelCanvas;

/**
 *
 * @author erichake
 */
public class JIconMouseAdapter implements MouseMotionListener {

    private static ZirkelCanvas ZC=null;
    private static String geomSelectedIcon="point";
    private static String acceptedIcons=",hide,delete,ctrl_slider,ctrl_txtfield,ctrl_popup,ctrl_chkbox,ctrl_button,";

    public static void setgeomSelectedIcon() {
        ZC=JZirkelCanvas.getCurrentZC();
        if (ZC!=null) {
            geomSelectedIcon=PaletteManager.geomSelectedIcon();
            if ((acceptedIcons.indexOf(geomSelectedIcon)==-1)||(geomSelectedIcon.equals(""))) {
                ZC=null;
            }
        }
    }

    public static void deselect() {
        ZC=null;
    }

    public static void paintTool() {
        if (ZC!=null) {
            final Point pt=ZC.getMousePosition();
            try {
                ZC.getGraphics().drawImage(ZC.I, 0, 0, ZC);
                ZC.getGraphics().drawImage(themes.getPaletteImage(geomSelectedIcon), pt.x+5, pt.y+5, themes.getToolIconSize(), themes.getToolIconSize(), ZC);

            } catch (Exception ex) {
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        paintTool();
    }

    public void mouseDragged(MouseEvent e) {
    }
}
