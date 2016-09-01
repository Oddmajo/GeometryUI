/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.restrict;

import java.util.ArrayList;
import java.util.Arrays;
import rene.gui.Global;
import rene.util.xml.XmlWriter;
import rene.zirkel.ZirkelCanvas;

/**
 *
 * @author erichake
 */
public class RestrictItems {

    private ZirkelCanvas ZC;
    private ArrayList<String> hiddenItems=new ArrayList<String>();
    public static final String DISK="z_disk";
    public static final String EDIT="z_edit";
    public static final String GEOM="z_geom";
    public static final String ASPECT="z_aspect";
    public static final String FUNC="z_func";
    public static final String TEST="z_test";
    public static final String CTRL="z_ctrl";
    public static final String GRID="z_grid";
    public static final String HIST="z_hist";
    public static final String BACK="z_back";
    public static final String SIZE="z_size";
    public static final String PREC="z_prec";
    public static final String MENU="x_menu";       // for menu bar
    public static final String MCRP="x_macr";       // for macro panel
    public static final String HISTP="x_hist";      // for history panel
    public static final String HLPP="x_help";       // for help panel
    public static final String LMCR="x_lmcr";       // for library macros
    public static String[] geom_icns, disk_icns, edit_icns, func_icns, test_icns, control_icns,
            grid_icns, history_icns, back_icns, size_icns, prec_icns;
    public static String standardRestrictedHiddenItems="save,copy,exportpng,exporteps,exportsvg,exportpdf,edit,animate,grid,z_disk,new,load,bi_trans,intersection,fixedsegment,vector,quadric,image3,z_func,tracker,objecttracker,locus,bi_function_u,function,equationxy,z_test,bi_t_align,bi_t_para,bi_t_perp,bi_t_equi,bi_t_app,bi_t_conf,z_ctrl,ctrl_edit,ctrl_slider,ctrl_popup,ctrl_chkbox,ctrl_txtfield,ctrl_button,z_grid,z_hist,z_back,z_size,z_prec";

    public RestrictItems(ZirkelCanvas zc) {
        ZC=zc;
    }

    public boolean isRestricted() {
        return (hiddenItems.size()>0);
    }

    public boolean isHidden(String s) {
        for (int i=0; i<hiddenItems.size(); i++) {
            if (s.equals(hiddenItems.get(i))) {
                return true;
            }
        }
        return false;
    }

    public void add(String s) {
        if (!isHidden(s)) {
            hiddenItems.add(s);
        }
    }

    public void remove(String s) {
        hiddenItems.remove(s);
    }

    public ArrayList<String> get() {
        return hiddenItems;
    }

    public void set(String items) {
        hiddenItems=new ArrayList<String>(Arrays.asList(items.split(",")));
    }

    public void set(ArrayList<String> items) {
        hiddenItems.clear();
        for (int i=0; i<items.size(); i++) {
            hiddenItems.add(items.get(i));
        }
    }

    public void initRestrictedHiddenItemsFromFactorySettings() {
        if (hiddenItems.size()>0) {
            hiddenItems=new ArrayList<String>(Arrays.asList(standardRestrictedHiddenItems.split(",")));
        }
    }

    public void initRestrictedHiddenItems() {
        if (hiddenItems.size()==0) {
            String items=Global.getParameter("standardRestrictedHiddenItems", standardRestrictedHiddenItems);
            if ("".equals(items)) {
                hiddenItems=new ArrayList<String>();
            } else {
                hiddenItems=new ArrayList<String>(Arrays.asList(items.split(",")));
            }
        }
    }

    public void setStandardRestrictedItems() {
        if (hiddenItems.size()>0) {
            Global.setParameter("standardRestrictedHiddenItems", getHiddenItems());
        }
    }

    public String getHiddenItems() {
        // join hiddenItems ArrayList in a string with comma separator.
        // A shame there is no such a method in java !
        String items=hiddenItems.toString();
        items=items.replace(" ", "");
        items=items.replace("[", "");
        items=items.replace("]", "");
        return items;
    }

    public void printArgs(final XmlWriter xml) {
        if (hiddenItems.size()>0) {
            xml.startTagStart("RestrictedSession");
            xml.printArg("HiddenIcons", getHiddenItems());
            xml.finishTagNewLine();
        }
    }

    /************************************************************
     * Initialisation of static parameters from PaletteManager :
     * **********************************************************/
    public static void init_disk_icns(String icns[]) {
        disk_icns=icns;
    }

    public static void init_edit_icns(String icns[]) {
        edit_icns=icns;
    }

    public static void init_geom_icns(String icns[]) {
        geom_icns=icns;
    }

    public static void init_func_icns(String icns[]) {
        func_icns=icns;
    }

    public static void init_test_icns(String icns[]) {
        test_icns=icns;
    }

    public static void init_control_icns(String icns[]) {
        control_icns=icns;
    }

    public static void init_grid_icns(String icns[]) {
        grid_icns=icns;
    }

    public static void init_history_icns(String icns[]) {
        history_icns=icns;
    }

    public static void init_back_icns(String icns[]) {
        back_icns=icns;
    }

    public static void init_size_icns(String icns[]) {
        size_icns=icns;
    }

    public static void init_prec_icns(String icns[]) {
        prec_icns=icns;
    }
    /********************
     * End Initialisation
     * ******************/
}
