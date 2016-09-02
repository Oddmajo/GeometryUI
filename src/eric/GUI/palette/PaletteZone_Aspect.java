/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.GUI.palette;

import eric.GUI.themes;
import eric.JColorPicker;
import eric.JZirkelCanvas;
import java.awt.Color;
import java.util.Vector;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import eric.JEricPanel;
import javax.swing.SwingConstants;
import rene.gui.Global;
import rene.zirkel.ZirkelCanvas;

/**
 *
 * @author erichake
 */
public class PaletteZone_Aspect extends PaletteZone {
    private static int content_height;
    private static String[] types={"type0", "type1", "type2", "type3", "type4", "type5"};
    private static String[] colors= { "color0", "color1", "color2", "color3","color4", "color5" };
    private static String[] thickness = { "thickness0", "thickness1", "thickness2" };
    private static int label_lineheight=20;
    private static PaletteZoneLabel pointshape_label,aspect_label;
    private static JEricPanel point_name_panel;
    private static JColorPicker color_picker;
    

    public PaletteZone_Aspect() {
        super(Global.Loc("palette.aspect"));
        pointshape_label=addLabel(Global.Loc("palette.aspect.label.pointshape"));
        point_name_panel=new JEricPanel();
        point_name_panel.setLayout(new BoxLayout(point_name_panel, BoxLayout.X_AXIS));
        point_name_panel.setOpaque(false);
        add7iconsString(types,PaletteManager.POINT_GROUP);
        addComponent(point_name_panel);
        aspect_label=addLabel("");
        Vector<JIcon> V=add7iconsString(colors, PaletteManager.ASPECT1_GROUP);
        color_picker=new JColorPicker(V.get(0).getIconWidth(), 6, 3,V){
			@Override
			public void doChange() {
				JIcon.setObjectColor(getCurrentColor());
			}

			@Override
			public void afterSelect() {
				JIcon.setObjectColor(getCurrentColor());
//				JPM.MW.ZF.setinfo("prop_scolor", false);
			}

			@Override
			public void setPalettes() {
                            ZirkelCanvas zc=JZirkelCanvas.getCurrentZC();
                            if (zc!=null){
                                setUsedColors(zc.getConstruction().getSpecialColors());
                            }
			}
		};
        addComponent(color_picker);
        createIcons(thickness,PaletteManager.ASPECT2_GROUP);
        createToggleIcon("partial");
        createToggleIcon("plines");
        createToggleIcon("showvalue");
        createToggleIcon("showname");
        createToggleIcon("bold");
        createToggleIcon("large");
        createToggleIcon("filled");
        createToggleIcon("obtuse");
        createToggleIcon("solid");

//        setContentHeight(200);
    }

    public static JColorPicker getColorPicker(){
        return color_picker;
    }

    

    public void init() {
        PaletteManager.fixsize(pointshape_label, themes.getRightPanelWidth(), label_lineheight);
        PaletteManager.fixsize(aspect_label, themes.getRightPanelWidth(), label_lineheight);
        initPointNameBtn();
        super.init();
    }

    public void initPointNameBtn() {
        point_name_panel.removeAll();
        JZirkelCanvas JZF=JZirkelCanvas.getCurrentJZF();
        if (JZF!=null) {
            point_name_panel.add(JZF.getPointLabel().getPaletteButton());
            JButton jb=JZF.getPointLabel().getPaletteButton();
            int w=(themes.getPaletteIconPerRow()*themes.getPaletteIconWidth())/7;
            PaletteManager.fixsize(jb, w, w);
            point_name_panel.add(jb);
            point_name_panel.validate();
            point_name_panel.repaint();
        }
    }

    public void setLabel(String s){
        aspect_label.setText(s);
        repaint();
    }

    private PaletteZoneLabel addLabel(final String mytxt) {
        PaletteZoneLabel mylabel=new PaletteZoneLabel(mytxt);
        addComponent(mylabel);
        return mylabel;
    }

    private Vector add7iconsString(String icns[],String group) {
        Vector V=new Vector();
        for (final String element : icns) {
            JIcon ji=new JIcon(element,group , 7);
            addIcon(ji);
            V.add(ji);
        }
        return V;
    }

    public void addColorIcons(final String myname[], String group) {
        add7iconsString(myname, group);
        addComponent(JColorPicker.margin(5));

//        JColorPicker jcp=new JColorPicker(themes.getPaletteIconWidth()-5, 6, 3) {
//
//            /**
//             *
//             */
//            
//
//            @Override
//            public void doChange() {
//                JPM.setObjectColor(getCurrentColor());
//            }
//
//            @Override
//            public void afterSelect() {
//                JPM.setObjectColor(getCurrentColor());
//                JPM.MW.ZF.setinfo("prop_scolor", false);
//            }
//
//            @Override
//            public void setPalettes() {
//                setUsedColors(JPM.MW.ZF.ZC.getConstruction().getSpecialColors());
//            }
//        };
//        myLine.add(JPM.MW.ColorPicker);
//        addNewLine();
    }


}
