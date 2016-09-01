/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.GUI.palette;

import eric.GUI.themes;
import eric.JZirkelCanvas;
import eric.restrict.RestrictItems;
import rene.gui.Global;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.construction.Construction;

/**
 *
 * @author erichake
 */
public class PaletteZone_Geom extends PaletteZone {

    private String icns[]={"point", "intersection", "midpoint", "bi_syma",
        "bi_symc", "bi_trans", "line", "ray", "parallel", "plumb",
        "bi_med", "bi_biss", "segment", "fixedsegment", "vector",
        "area", "angle", "fixedangle", "circle", "fixedcircle",
        "circle3", "bi_circ", "bi_arc", "quadric", "text", "expression", "image3"};
    private String DPDisabledIcons=" parallel fixedsegment fixedcircle vector area bi_arc quadric bi_trans ";
    private String DPNewIcons=" bi_distance bi_perp_common bi_lineIP bi_horocycle bi_equidistante blank bi_pinceau1 bi_pinceau3 bi_pinceauinter bi_pinceauhauteur bi_pinceaucycle bi_pinceaubiss ";
    private PaletteZoneCheckBox euclidianBox=new PaletteZoneCheckBox(Global.Loc("palette.construction.euclidian"));
    private static int box_lineheight=25;

    public PaletteZone_Geom() {
        super(Global.Loc("palette.construction"));
        createIcons(icns, PaletteManager.getGeomGroup());
        RestrictItems.init_geom_icns(icns);
    }

    @Override
    public void init() {
        PaletteManager.fixsize(euclidianBox, themes.getRightPanelWidth(), 0);
        super.init();
        ZirkelCanvas zc=JZirkelCanvas.getCurrentZC();
        if ((!zone_content.isHidden())&&(zc!=null)&&(zc.isDP())) {
            PaletteManager.fixsize(euclidianBox, themes.getRightPanelWidth(), box_lineheight);
            int w=zone_content.getWidth();
            int h=zone_content.getHeight()+box_lineheight;
            PaletteManager.fixsize(zone_content, w, h);
        }
    }

    public void initConsideringMode() {
        ZirkelCanvas zc=JZirkelCanvas.getCurrentZC();
        if (zc==null) {
            return;
        }
        removeBlankIcons();
        PaletteManager.setDisabledIcons(DPDisabledIcons, false);
        removeIcons(DPNewIcons);
        zone_content.remove(euclidianBox);

        if (zc.isDP()) {
            zone_content.add(euclidianBox, 0);
            if (!zc.isEuclidian()) {
                PaletteManager.setDisabledIcons(DPDisabledIcons, true);
                removeIcons(" text expression image3 ");
                insertIcons(DPNewIcons, PaletteManager.getGeomGroup());
                insertIcons(" text expression image3 ", PaletteManager.getGeomGroup());
                PaletteManager.setDisabledIcons(" filled ", true);
            }

        }

    }
}
