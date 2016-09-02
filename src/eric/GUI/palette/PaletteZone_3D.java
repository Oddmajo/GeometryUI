/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.GUI.palette;

import eric.JZirkelCanvas;
import rene.gui.Global;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.construction.Construction;

/**
 *
 * @author erichake
 */
public class PaletteZone_3D extends PaletteZone {

    private String icns[]={"bi_3Dcoords", "boundedpoint", "bi_3Darete", "bi_3Dtetra", "bi_3Dcube", "bi_3Ddode"};

    public PaletteZone_3D() {
        super(Global.Loc("palette.3D"));
        createIcons(icns, PaletteManager.getGeomGroup());
    }

    public void initConsideringMode() {
        ZirkelCanvas zc=JZirkelCanvas.getCurrentZC();
        if (zc==null) {
            return;
        }
        PaletteManager.remove3DPalette();
        if (zc.getMode()==Construction.MODE_3D) {
            PaletteManager.add3DPalette();
            setHideContent(false);
            init();
        }
    }
}
