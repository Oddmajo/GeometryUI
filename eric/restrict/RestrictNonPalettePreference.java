/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.restrict;

import eric.GUI.palette.PaletteManager;
import eric.GUI.window.MenuBar;
import eric.JZirkelCanvas;
import rene.zirkel.ZirkelCanvas;

/**
 *
 * @author erichake
 */
public class RestrictNonPalettePreference extends RestrictPanelLine {

    String name;

    public RestrictNonPalettePreference(String nme, String label) {
        super(label);
        name=nme;
        initState();
    }

    public void initState() {
        ZirkelCanvas zc=JZirkelCanvas.getCurrentZC();
        if (zc!=null) {
            setSelected(!zc.isHiddenItem(name));
        }
    }

    public void action() {
        ZirkelCanvas zc=JZirkelCanvas.getCurrentZC();
        if (zc!=null) {
            if (isSelected()) {
                zc.removeHiddenItem(name);
            } else {
                zc.addHiddenItem(name);
            }
        }
        PaletteManager.init();
    }
}
