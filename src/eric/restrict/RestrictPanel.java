/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package eric.restrict;

import eric.GUI.palette.PaletteManager;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import eric.JEricPanel;
import javax.swing.JSeparator;
import rene.gui.Global;

/**
 *
 * @author erichake
 */
public class RestrictPanel extends JEricPanel{
    private static int W=400,H=1200,MARGINH=20,SMALLMARGINH=10;
    private ArrayList<RestrictPanelIconsLineTitle> iconslines=new ArrayList<RestrictPanelIconsLineTitle>();
    
    private RestrictNonPalettePreference menu,macr,hist,help,lib_macros;

public RestrictPanel(){
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    setBorder(BorderFactory.createEmptyBorder());
    setOpaque(false);
    PaletteManager.fixsize(this, W,H );
    
    add(marginH(MARGINH));
    add(new RestrictPanelActiveLine(this, Global.Loc("restrict.activate")));
    add(marginH(MARGINH));
    add(new RestrictSeparator(SMALLMARGINH));
    add(new RestrictPanelComment());
    
    
    init_Disk_zone();
    init_Edit_zone();
    init_Construction_zone();
    init_Func_zone();
    init_Test_zone();
    init_Control_zone();
    init_Aspect_zone();
    init_Grid_zone();
    init_History_zone();
    init_Back_zone();
    init_Size_zone();
    init_Prec_zone();
    add(marginH(MARGINH));
    add(new RestrictSeparator(SMALLMARGINH));
    add(marginH(MARGINH));
    add(menu=new RestrictNonPalettePreference(RestrictItems.MENU,Global.Loc("restrict.menubar")));
    add(marginH(MARGINH));
    add(macr=new RestrictNonPalettePreference(RestrictItems.MCRP,Global.Loc("restrict.macropanel")));
    add(marginH(MARGINH));
    add(hist=new RestrictNonPalettePreference(RestrictItems.HISTP,Global.Loc("restrict.historypanel")));
    add(marginH(MARGINH));
    add(help=new RestrictNonPalettePreference(RestrictItems.HLPP,Global.Loc("restrict.helppanel")));
    add(marginH(MARGINH));
    add(lib_macros=new RestrictNonPalettePreference(RestrictItems.LMCR, Global.Loc("restrict.librarymacros")));
}

public static int getPanelWidth(){
    return W;
}

public void setSelectAll(boolean b){
    for (int i=0;i<iconslines.size();i++){
        iconslines.get(i).setSelected(true,b);
    }
    if (menu!=null) menu.setSelected(b);
    if (macr!=null) macr.setSelected(b);
    if (hist!=null) hist.setSelected(b);
    if (help!=null) help.setSelected(b);
    if (lib_macros!=null) lib_macros.setSelected(b);
}
public void setEnabledAll(boolean b){
    for (int i=0;i<iconslines.size();i++){
        iconslines.get(i).setEnabled(b);
        iconslines.get(i).setEnabledIcons(b);
    }
    if (menu!=null) menu.setEnabled(b);
    if (macr!=null) macr.setEnabled(b);
    if (hist!=null) hist.setEnabled(b);
    if (help!=null) help.setEnabled(b);
    if (lib_macros!=null) lib_macros.setEnabled(b);
}

public void initAllStates(){
    for (int i=0;i<iconslines.size();i++){
        iconslines.get(i).initIconsState();
        iconslines.get(i).initState();
    }
    if (menu!=null) menu.initState();
    if (macr!=null) macr.initState();
    if (hist!=null) hist.initState();
    if (help!=null) help.initState();
    if (lib_macros!=null) lib_macros.initState();
}

 static JEricPanel marginH() {
        return marginH(MARGINH);
    }

 static JEricPanel marginH(int h) {
        JEricPanel mypan=new JEricPanel();
        PaletteManager.fixsize(mypan, 1, h);
        mypan.setLayout(new javax.swing.BoxLayout(mypan, javax.swing.BoxLayout.X_AXIS));
        mypan.setAlignmentX(0F);
        mypan.setAlignmentY(0F);
        mypan.setOpaque(false);
        mypan.setFocusable(false);
        return mypan;
    }


public void init_Disk_zone(){
    RestrictPanelIconsLineTitle title=new RestrictPanelIconsLineTitle(RestrictItems.DISK,Global.Loc("palette.file")+" :");
    RestrictPanelIconsLine line=new RestrictPanelIconsLine(title,RestrictItems.disk_icns);
    add(marginH());
    add(title);
    add(line);
    iconslines.add(title);
}

public void init_Edit_zone(){
    RestrictPanelIconsLineTitle title=new RestrictPanelIconsLineTitle(RestrictItems.EDIT,Global.Loc("palette.edit")+" :");
    RestrictPanelIconsLine line=new RestrictPanelIconsLine(title,RestrictItems.edit_icns);
    add(marginH());
    add(title);
    add(line);
    iconslines.add(title);
}


public void init_Construction_zone(){
    RestrictPanelIconsLineTitle title=new RestrictPanelIconsLineTitle(RestrictItems.GEOM,Global.Loc("palette.construction")+" :");
    RestrictPanelIconsLine line=new RestrictPanelIconsLine(title,RestrictItems.geom_icns);
    add(marginH());
    add(title);
    add(line);
    iconslines.add(title);
}

public void init_Aspect_zone(){
    RestrictPanelIconsLineTitle title=new RestrictPanelIconsLineTitle(RestrictItems.ASPECT,Global.Loc("palette.aspect"));
    add(marginH(SMALLMARGINH));
    add(title);
    iconslines.add(title);
}

public void init_Func_zone(){
    RestrictPanelIconsLineTitle title=new RestrictPanelIconsLineTitle(RestrictItems.FUNC,Global.Loc("palette.function")+" :");
    RestrictPanelIconsLine line=new RestrictPanelIconsLine(title,RestrictItems.func_icns);
    add(marginH());
    add(title);
    add(line);
    iconslines.add(title);
}

public void init_Test_zone(){
    RestrictPanelIconsLineTitle title=new RestrictPanelIconsLineTitle(RestrictItems.TEST,Global.Loc("palette.test")+" :");
    RestrictPanelIconsLine line=new RestrictPanelIconsLine(title,RestrictItems.test_icns);
    add(marginH());
    add(title);
    add(line);
    iconslines.add(title);
}

public void init_Control_zone(){
    RestrictPanelIconsLineTitle title=new RestrictPanelIconsLineTitle(RestrictItems.CTRL,Global.Loc("palette.controls")+" :");
    RestrictPanelIconsLine line=new RestrictPanelIconsLine(title,RestrictItems.control_icns);
    add(marginH());
    add(title);
    add(line);
    iconslines.add(title);
}

public void init_Grid_zone(){
    RestrictPanelIconsLineTitle title=new RestrictPanelIconsLineTitle(RestrictItems.GRID,Global.Loc("palette.grid"));
    add(marginH(SMALLMARGINH));
    add(title);
    iconslines.add(title);
}

public void init_History_zone(){
    RestrictPanelIconsLineTitle title=new RestrictPanelIconsLineTitle(RestrictItems.HIST,Global.Loc("palette.history"));
    add(marginH(SMALLMARGINH));
    add(title);
    iconslines.add(title);
}

public void init_Back_zone(){
    RestrictPanelIconsLineTitle title=new RestrictPanelIconsLineTitle(RestrictItems.BACK,Global.Loc("palette.colors"));
    add(marginH(SMALLMARGINH));
    add(title);
    iconslines.add(title);
}

public void init_Size_zone(){
    RestrictPanelIconsLineTitle title=new RestrictPanelIconsLineTitle(RestrictItems.SIZE,Global.Loc("palette.sizes"));
    add(marginH(SMALLMARGINH));
    add(title);
    iconslines.add(title);
}

public void init_Prec_zone(){
    RestrictPanelIconsLineTitle title=new RestrictPanelIconsLineTitle(RestrictItems.PREC,Global.Loc("palette.prec"));
    add(marginH(SMALLMARGINH));
    add(title);
    iconslines.add(title);
}







}
