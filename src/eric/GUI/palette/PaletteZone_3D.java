/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.GUI.palette;

import static eric.GUI.palette.PaletteManager.setSelected_with_clic;
import eric.JZirkelCanvas;
import rene.gui.Global;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.construction.Construction;

/**
 *
 * @author erichake
 */
public class PaletteZone_3D extends PaletteZone {

    private String icns[]={"bi_3Dcoords", "inter3D", "boundedpoint", "bi_3Darete", "midpoint3D", "bi_3Dproj", "bi_3Dsymc", "bi_3Dsymp", "bi_3Dtrans", "vector3D", "line3D", "ray3D", "segment3D", "area3D", "bi_3Dsphererayon", "bi_3Dspherepoint", "bi_3Dcircle1", "bi_3Dcircle2", "bi_3Dcircle3pts", "angle3D", "bi_3Dplandroite", "bi_3Dplanplan", "bi_3Dspheredroite", "bi_3Dsphereplan", "bi_3Dspheresphere", "bi_3Dtetra", "bi_3Dcube", "bi_3Docta", "bi_3Disoc", "bi_3Ddode"};

    public PaletteZone_3D() {
        super(Global.Loc("palette.3D"));
        createIcons(icns, PaletteManager.getGeomGroup());
        //setSelectBtn("bi_3Dcoords",true);
    }

    public void initConsideringMode() {
        ZirkelCanvas zc=JZirkelCanvas.getCurrentZC();
        if (zc==null) {
            return;
        }
        PaletteManager.remove3DPalette();
        if (zc.getMode()==Construction.MODE_3D) {
            PaletteManager.add3DPalette();
            PaletteManager.FixPaletteHeight2(this);  // Dibs
            setHideContent(false);
            init();
        }
    }
}
