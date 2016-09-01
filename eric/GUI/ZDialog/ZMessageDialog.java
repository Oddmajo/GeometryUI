/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.GUI.ZDialog;

import eric.JZirkelCanvas;
import rene.zirkel.ZirkelCanvas;

/**
 *
 * @author erichake
 */
public class ZMessageDialog extends ZDialog {

    public ZMessageDialog() {
        super("essai", 0, 0, 400, 300, true, false);
    }

    public static void showMessage() {
        ZirkelCanvas zc=JZirkelCanvas.getCurrentZC();
        if (zc!=null) {
            ZMessageDialog zmd=new ZMessageDialog();
            zc.add(zmd);
            zmd.init();
        }
    }
}


