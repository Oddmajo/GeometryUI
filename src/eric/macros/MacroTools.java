/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.macros;

import eric.GUI.pipe_tools;
import eric.GUI.themes;
import eric.GUI.windowComponent;
import eric.JZirkelCanvas;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Vector;
import rene.gui.Global;
import rene.util.xml.XmlReader;
import rene.util.xml.XmlTag;
import rene.util.xml.XmlTagPI;
import rene.util.xml.XmlTree;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.ZirkelFrame;
import rene.zirkel.construction.ConstructionException;
import rene.zirkel.construction.Count;
import rene.zirkel.macro.Macro;
import rene.zirkel.macro.MacroItem;
import rene.zirkel.macro.MacroRunner;
import rene.zirkel.objects.ConstructionObject;
import rene.zirkel.objects.TextObject;

/**
 *
 * @author erichake
 */
public class MacroTools {

    static public final int LIBRARY_MACRO=0;
    static public final int FILE_MACRO=2;
    static private Vector librarymacros=new Vector();
    static public Vector builtinmacros=new Vector();
    static public String MacrosLibraryFileName="";
    static public String MacrosBackupLibraryFileName="";

    public static void createLocalDirectory() {
        // Setting (if necessary) home directory name and home library macros
        // file name :

        final String mypath=Global.AppPath();

        // Place the help files in the local directory :
        if ((Global.isNewVersion())||(!(new File(Global.getHomeDirectory()+"docs").exists()))) {
            try {
                Global.copyFiles(new File(mypath+"docs"), new File(
                        Global.getHomeDirectory()+"docs"));
            } catch (final IOException ex) {
                System.out.println("bug : createLocalDirectory()");
            }
        }

        // Place the javascript files in the local directory :
        if ((Global.isNewVersion())||(!(new File(Global.getHomeDirectory()+"scripts").exists()))) {
            try {
                Global.copyFiles(new File(mypath+"scripts"), new File(
                        Global.getHomeDirectory()+"scripts"));
            } catch (final IOException ex) {
                System.out.println("bug : createLocalDirectory()");
            }
        }

        String Filename="library.mcr";
        if (new File(mypath+Global.name("language", "")+"library.mcr").exists()) {
            Filename=Global.name("language", "")+"library.mcr";
        } else if (new File(Global.getHomeDirectory()+Global.name("language", "")+"library.mcr").exists()) {
            Filename=Global.name("language", "")+"library.mcr";
        }

        MacrosLibraryFileName=Global.getHomeDirectory()+Filename;
        // is there a library in home folder ?
        if (new File(MacrosLibraryFileName).exists()) {
            // Is it a new version at this startup ?
            if (Global.isNewVersion()) {
                MacrosBackupLibraryFileName=Global.getHomeDirectory()+"library_backup.mcr";
                Global.copyFile(MacrosLibraryFileName, MacrosBackupLibraryFileName);
                Global.copyFile(mypath+Filename, MacrosLibraryFileName);
            }
        } else {
            new File(Global.getHomeDirectory()).mkdirs();
            Global.copyFile(mypath+Filename, MacrosLibraryFileName);
        }

        Global.makeWindowConfigFolderInvisible();
    }

    public static Vector getBuiltinMacros() {
        return builtinmacros;
    }

    public static Vector getLibraryMacros() {
        return librarymacros;
    }

    public static void clearLibraryMacros() {
        librarymacros.removeAllElements();
    }

    public static void addToLibraryMacros(MacroItem mi) {
        librarymacros.add(mi);
    }

    public static void updateLibraryMacros() {
        ZirkelCanvas zc=JZirkelCanvas.getCurrentZC();
        if ((!(zc==null))) {
            librarymacros.clear();
            final Vector V=zc.getMacros();
            for (int i=0; i<V.size(); i++) {
                final MacroItem mi=(MacroItem) V.get(i);
                if (mi.M.isProtected()) {
                    if (!(mi.M.Name.startsWith("@builtin@"))) {
                        librarymacros.add(V.get(i));
                    }
                }
            }
        }
    }

    public static boolean isDPMacro(String genericName){
        for (int i=0; i<builtinmacros.size(); i++) {
            Macro m=((MacroItem) builtinmacros.elementAt(i)).M;
            if (m.getName().equals("@builtin@/DP_"+genericName)) {
                return true;
            }
        }
        return false;
    }

    public static void runDPMacro(String genericName){
        runBuiltinMacro("@builtin@/DP_"+genericName);
    }

    public static void runBuiltinMacro(final String macroname) {
        Vector<?> mc;
        Macro m;
        TextObject t;
        ZirkelFrame ZF=JZirkelCanvas.getCurrentZF();
        if (ZF==null) {
            return;
        }
        mc=builtinmacros;
        for (int i=0; i<mc.size(); i++) {
            m=((MacroItem) mc.elementAt(i)).M;
            if (m.getName().equals(macroname)) {
                if (m.getName().equals("@builtin@/DP_line")) {
                    m.Prompts[1]=Global.Loc("macro.DP_line.1");
                    m.Prompts[2]=Global.Loc("macro.DP_line.2");
                    m.createDPObjects=ConstructionObject.DP_LINE;
                }
                else if (m.getName().equals("@builtin@/DP_midpoint")) {
                    m.Prompts[1]=Global.Loc("macro.DP_midpoint.1");
                    m.Prompts[2]=Global.Loc("macro.DP_midpoint.2");
                } 
                else if (m.getName().equals("@builtin@/DP_bi_syma")) {
                    m.Prompts[1]=Global.Loc("macro.DP_bi_syma.1");
                    m.Prompts[2]=Global.Loc("macro.DP_bi_syma.2");
                }
                else if (m.getName().equals("@builtin@/DP_bi_symc")) {
                    m.Prompts[1]=Global.Loc("macro.DP_bi_symc.1");
                    m.Prompts[2]=Global.Loc("macro.DP_bi_symc.2");
                } else if (m.getName().equals("@builtin@/DP_plumb")) {
                    m.Prompts[1]=Global.Loc("macro.DP_plumb.1");
                    m.Prompts[2]=Global.Loc("macro.DP_plumb.2");
                    m.createDPObjects=ConstructionObject.DP_LINE;
                } else if (m.getName().equals("@builtin@/DP_bi_med")) {
                    m.Prompts[1]=Global.Loc("macro.DP_bi_med.1");
                    m.Prompts[2]=Global.Loc("macro.DP_bi_med.2");
                    m.createDPObjects=ConstructionObject.DP_LINE;
                } else if (m.getName().equals("@builtin@/DP_bi_biss")) {
                    m.Prompts[1]=Global.Loc("macro.DP_bi_biss.1");
                    m.Prompts[2]=Global.Loc("macro.DP_bi_biss.2");
                    m.Prompts[3]=Global.Loc("macro.DP_bi_biss.3");
                    m.createDPObjects=ConstructionObject.DP_LINE;
                } else if (m.getName().equals("@builtin@/DP_angle")) {
                    m.Prompts[1]=Global.Loc("macro.DP_angle.1");
                    m.Prompts[2]=Global.Loc("macro.DP_angle.2");
                    m.Prompts[3]=Global.Loc("macro.DP_angle.3");
                } else if (m.getName().equals("@builtin@/DP_segment")) {
                    m.Prompts[1]=Global.Loc("macro.DP_segment.1");
                    m.Prompts[2]=Global.Loc("macro.DP_segment.2");
                    m.createDPObjects=ConstructionObject.DP_SEGMENT;
                }else if (m.getName().equals("@builtin@/DP_ray")) {
                    m.Prompts[1]=Global.Loc("macro.DP_ray.1");
                    m.Prompts[2]=Global.Loc("macro.DP_ray.2");
                    m.createDPObjects=ConstructionObject.DP_LINE;
                } else if (m.getName().equals("@builtin@/DP_circle")) {
                    m.Prompts[1]=Global.Loc("macro.DP_circle.1");
                    m.Prompts[2]=Global.Loc("macro.DP_circle.2");
                    m.createDPObjects=ConstructionObject.DP_CIRCLE;
                } else if (m.getName().equals("@builtin@/DP_bi_distance")) {
                    m.Prompts[1]=Global.Loc("macro.DP_bi_distance.1");
                    m.Prompts[2]=Global.Loc("macro.DP_bi_distance.2");
                    m.Prompts[3]=Global.Loc("macro.DP_bi_distance.3");
                } else if (m.getName().equals("@builtin@/DP_bi_perp_common")) {
                    m.Prompts[1]=Global.Loc("macro.DP_bi_perp_common.1");
                    m.Prompts[2]=Global.Loc("macro.DP_bi_perp_common.2");
                    m.createDPObjects=ConstructionObject.DP_LINE;
                } else if (m.getName().equals("@builtin@/DP_bi_pinceau1")) {
                    m.Prompts[1]=Global.Loc("macro.DP_bi_pinceau1.1");
                    m.Prompts[2]=Global.Loc("macro.DP_bi_pinceau1.2");
                    m.Prompts[3]=Global.Loc("macro.DP_bi_pinceau1.3");
                    m.createDPObjects=ConstructionObject.DP_LINE;
                } else if (m.getName().equals("@builtin@/DP_bi_pinceau3")) {
                    m.Prompts[1]=Global.Loc("macro.DP_bi_pinceau3.1");
                    m.Prompts[2]=Global.Loc("macro.DP_bi_pinceau3.2");
                    m.Prompts[3]=Global.Loc("macro.DP_bi_pinceau3.3");
                    m.createDPObjects=ConstructionObject.DP_LINE;
                } else if (m.getName().equals("@builtin@/DP_bi_pinceauinter")) {
                    m.Prompts[1]=Global.Loc("macro.DP_bi_pinceauinter.1");
                    m.Prompts[2]=Global.Loc("macro.DP_bi_pinceauinter.2");
                    m.Prompts[3]=Global.Loc("macro.DP_bi_pinceauinter.3");
                    m.Prompts[4]=Global.Loc("macro.DP_bi_pinceauinter.4");
                    m.createDPObjects=ConstructionObject.DP_LINE;
                } else if (m.getName().equals("@builtin@/DP_bi_pinceauhauteur")) {
                    m.Prompts[1]=Global.Loc("macro.DP_bi_pinceauhauteur.1");
                    m.Prompts[2]=Global.Loc("macro.DP_bi_pinceauhauteur.2");
                    m.Prompts[3]=Global.Loc("macro.DP_bi_pinceauhauteur.3");
                    m.createDPObjects=ConstructionObject.DP_LINE;
                } else if (m.getName().equals("@builtin@/DP_bi_pinceaucycle")) {
                    m.Prompts[1]=Global.Loc("macro.DP_bi_pinceaucycle.1");
                    m.Prompts[2]=Global.Loc("macro.DP_bi_pinceaucycle.2");
                    m.Prompts[3]=Global.Loc("macro.DP_bi_pinceaucycle.3");
                    m.createDPObjects=ConstructionObject.DP_CIRCLE;
                } else if (m.getName().equals("@builtin@/DP_bi_pinceaubiss")) {
                    m.Prompts[1]=Global.Loc("macro.DP_bi_pinceaubiss.1");
                    m.Prompts[2]=Global.Loc("macro.DP_bi_pinceaubiss.2");
                    m.Prompts[3]=Global.Loc("macro.DP_bi_pinceaubiss.3");
                    m.Prompts[4]=Global.Loc("macro.DP_bi_pinceaubiss.4");
                    m.Prompts[5]=Global.Loc("macro.DP_bi_pinceaubiss.5");
                    m.Prompts[6]=Global.Loc("macro.DP_bi_pinceaubiss.6");
                    m.createDPObjects=ConstructionObject.DP_LINE;
                } else if (m.getName().equals("@builtin@/DP_bi_lineIP")) {
                    m.Prompts[1]=Global.Loc("macro.DP_bi_lineIP.1");
                    m.Prompts[2]=Global.Loc("macro.DP_bi_lineIP.2");
                    m.createDPObjects=ConstructionObject.DP_LINE;
                } else if (m.getName().equals("@builtin@/DP_bi_horocycle")) {
                    m.Prompts[1]=Global.Loc("macro.DP_bi_horocycle.1");
                    m.Prompts[2]=Global.Loc("macro.DP_bi_horocycle.2");
                    m.createDPObjects=ConstructionObject.DP_CIRCLE;
                } else if (m.getName().equals("@builtin@/DP_bi_equidistante")) {
                    m.Prompts[1]=Global.Loc("macro.DP_bi_equidistante.1");
                    m.Prompts[2]=Global.Loc("macro.DP_bi_equidistante.2");
                    m.createDPObjects=ConstructionObject.DP_CIRCLE;
                } else if (m.getName().equals("@builtin@/DP_fixedangle")) {
                    m.Prompts[1]=Global.Loc("macro.DP_fixedangle.1");
                    m.Prompts[2]=Global.Loc("macro.DP_fixedangle.2");
                    m.Prompts[3]=Global.Loc("macro.DP_fixedangle.3");
                } else if (m.getName().equals("@builtin@/DP_bi_circ")) {
                    m.Prompts[1]=Global.Loc("macro.DP_bi_circ.1");
                    m.Prompts[2]=Global.Loc("macro.DP_bi_circ.2");
                    m.Prompts[3]=Global.Loc("macro.DP_bi_circ.3");
                    m.createDPObjects=ConstructionObject.DP_CIRCLE;
                }

                
                else if (m.getName().equals("@builtin@/syma")) {
                    m.Prompts[0]=Global.Loc("macro.bi_syma.0");
                    m.Prompts[1]=Global.Loc("macro.bi_syma.1");
                } else if (m.getName().equals("@builtin@/symc")) {
                    m.Prompts[0]=Global.Loc("macro.bi_symc.0");
                    m.Prompts[1]=Global.Loc("macro.bi_symc.1");
                } else if (m.getName().equals("@builtin@/trans")) {
                    m.Prompts[0]=Global.Loc("macro.bi_trans.0");
                    m.Prompts[1]=Global.Loc("macro.bi_trans.1");
                    m.Prompts[2]=Global.Loc("macro.bi_trans.2");
                } else if (m.getName().equals("@builtin@/med")) {
                    m.Prompts[0]=Global.Loc("macro.bi_med.0");
                    m.Prompts[1]=Global.Loc("macro.bi_med.1");
                } else if (m.getName().equals("@builtin@/biss")) {
                    m.Prompts[0]=Global.Loc("macro.bi_biss.0");
                    m.Prompts[1]=Global.Loc("macro.bi_biss.1");
                    m.Prompts[2]=Global.Loc("macro.bi_biss.2");
                } else if (m.getName().equals("@builtin@/circ")) {
                    m.Prompts[0]=Global.Loc("macro.bi_circ.0");
                    m.Prompts[1]=Global.Loc("macro.bi_circ.1");
                    m.Prompts[2]=Global.Loc("macro.bi_circ.2");
                } else if (m.getName().equals("@builtin@/arc")) {
                    m.Prompts[0]=Global.Loc("macro.bi_circ.0");
                    m.Prompts[1]=Global.Loc("macro.bi_circ.1");
                    m.Prompts[2]=Global.Loc("macro.bi_circ.2");
                } else if (m.getName().equals("@builtin@/function_u")) {
                    m.Prompts[0]=Global.Loc("macro.bi_expression.0");
                } else if (m.getName().equals("@builtin@/t_align")) {
                    m.Prompts[0]=Global.Loc("macro.bi_circ.0");
                    m.Prompts[1]=Global.Loc("macro.bi_circ.1");
                    m.Prompts[2]=Global.Loc("macro.bi_circ.2");
                    t=(TextObject) m.getTargets().get(
                            m.getTargets().size()-1);
                    t.setLines(Global.Loc("macro.bi_t_align.text1"));
                    t=(TextObject) m.getTargets().get(
                            m.getTargets().size()-2);
                    t.setLines(Global.Loc("macro.bi_t_align.text0"));
                } else if (m.getName().equals("@builtin@/t_para")) {
                    m.Prompts[0]=Global.Loc("macro.bi_t_para.0");
                    m.Prompts[1]=Global.Loc("macro.bi_t_para.1");
                    t=(TextObject) m.getTargets().get(
                            m.getTargets().size()-1);
                    t.setLines(Global.Loc("macro.bi_t_para.text0"));
                    t=(TextObject) m.getTargets().get(
                            m.getTargets().size()-2);
                    t.setLines(Global.Loc("macro.bi_t_para.text1"));
                } else if (m.getName().equals("@builtin@/t_perp")) {
                    m.Prompts[0]=Global.Loc("macro.bi_t_para.0");
                    m.Prompts[1]=Global.Loc("macro.bi_t_para.1");
                    t=(TextObject) m.getTargets().get(
                            m.getTargets().size()-2);
                    t.setLines(Global.Loc("macro.bi_t_perp.text1"));
                    t=(TextObject) m.getTargets().get(
                            m.getTargets().size()-1);
                    t.setLines(Global.Loc("macro.bi_t_perp.text0"));
                } else if (m.getName().equals("@builtin@/t_equi")) {
                    m.Prompts[0]=Global.Loc("macro.bi_t_equi.0");
                    m.Prompts[1]=Global.Loc("macro.bi_t_equi.1");
                    m.Prompts[2]=Global.Loc("macro.bi_t_equi.2");
                    t=(TextObject) m.getTargets().get(
                            m.getTargets().size()-1);
                    t.setLines(Global.Loc("macro.bi_t_equi.text0"));
                    t=(TextObject) m.getTargets().get(
                            m.getTargets().size()-2);
                    t.setLines(Global.Loc("macro.bi_t_equi.text1"));
                } else if (m.getName().equals("@builtin@/t_app")) {
                    m.Prompts[0]=Global.Loc("macro.bi_t_app.0");
                    m.Prompts[1]=Global.Loc("macro.bi_t_app.1");
                    t=(TextObject) m.getTargets().get(
                            m.getTargets().size()-1);
                    t.setLines(Global.Loc("macro.bi_t_app.text1"));
                    t=(TextObject) m.getTargets().get(
                            m.getTargets().size()-2);
                    t.setLines(Global.Loc("macro.bi_t_app.text0"));
                } else if (m.getName().equals("@builtin@/t_conf")) {
                    m.Prompts[0]=Global.Loc("macro.bi_t_conf.0");
                    m.Prompts[1]=Global.Loc("macro.bi_t_conf.1");
                    t=(TextObject) m.getTargets().get(
                            m.getTargets().size()-1);
                    t.setLines(Global.Loc("macro.bi_t_conf.text1"));
                    t=(TextObject) m.getTargets().get(
                            m.getTargets().size()-2);
                    t.setLines(Global.Loc("macro.bi_t_conf.text0"));
                } else if (m.getName().equals("@builtin@/3Dcoords")) {
                    m.Prompts[4]=Global.Loc("macro.bi_3Dcoords.0");
                } else if (m.getName().equals("@builtin@/3Dcube")) {
                    m.Prompts[4]=Global.Loc("macro.bi_3Dcube.0");
                } else if (m.getName().equals("@builtin@/3Darete")) {
                    m.Prompts[0]=Global.Loc("macro.bi_3Darete.0");
                    m.Prompts[1]=Global.Loc("macro.bi_3Darete.1");
                    m.Prompts[2]=Global.Loc("macro.bi_3Darete.2");
                    m.Prompts[3]=Global.Loc("macro.bi_3Darete.3");
                } else if (m.getName().equals("@builtin@/3Dtetra")) {
                    m.Prompts[4]=Global.Loc("macro.bi_3Dtetra.0");
                } else if (m.getName().equals("@builtin@/3Docta")) {
                    m.Prompts[4]=Global.Loc("macro.bi_3Docta.0");
                } else if (m.getName().equals("@builtin@/3Disoc")) {
                    m.Prompts[4]=Global.Loc("macro.bi_3Disoc.0");
                } else if (m.getName().equals("@builtin@/3Ddode")) {
                    m.Prompts[4]=Global.Loc("macro.bi_3Ddode.0");
                } else if (m.getName().equals("@builtin@/3Dsymp")) {
                	m.Prompts[4]=Global.Loc("macro.bi_3Dsymp.0");
                	m.Prompts[5]=Global.Loc("macro.bi_3Dsymp.1");
                	m.Prompts[6]=Global.Loc("macro.bi_3Dsymp.2");
                	m.Prompts[7]=Global.Loc("macro.bi_3Dsymp.3");
            	} else if (m.getName().equals("@builtin@/3Dproj")) {
                	m.Prompts[4]=Global.Loc("macro.bi_3Dproj.0");
                	m.Prompts[5]=Global.Loc("macro.bi_3Dproj.1");
                	m.Prompts[6]=Global.Loc("macro.bi_3Dproj.2");
                	m.Prompts[7]=Global.Loc("macro.bi_3Dproj.3");
            	} else if (m.getName().equals("@builtin@/3Dsymc")) {
            		m.Prompts[4]=Global.Loc("macro.bi_3Dsymc.0");
            		m.Prompts[5]=Global.Loc("macro.bi_3Dsymc.1");
            	} else if (m.getName().equals("@builtin@/3Dtrans")) {
            		m.Prompts[4]=Global.Loc("macro.bi_3Dtrans.0");
            		m.Prompts[5]=Global.Loc("macro.bi_3Dtrans.1");
            		m.Prompts[6]=Global.Loc("macro.bi_3Dtrans.2");
            	} else if (m.getName().equals("@builtin@/3Dcircle1")) {
            		m.Prompts[4]=Global.Loc("macro.bi_3Dcircle1.0");
            		m.Prompts[5]=Global.Loc("macro.bi_3Dcircle1.1");
            	} else if (m.getName().equals("@builtin@/3Dcircle2")) {
            		m.Prompts[4]=Global.Loc("macro.bi_3Dcircle2.0");
            		m.Prompts[5]=Global.Loc("macro.bi_3Dcircle2.1");
            		m.Prompts[6]=Global.Loc("macro.bi_3Dcircle2.2");
            	} else if (m.getName().equals("@builtin@/3Dcircle3pts")) {
            		m.Prompts[4]=Global.Loc("macro.bi_3Dcircle3pts.0");
            		m.Prompts[5]=Global.Loc("macro.bi_3Dcircle3pts.1");
            		m.Prompts[6]=Global.Loc("macro.bi_3Dcircle3pts.2");
            	} else if (m.getName().equals("@builtin@/3Dplandroite")) {
            		m.Prompts[4]=Global.Loc("macro.bi_3Dplandroite.0");
            		m.Prompts[5]=Global.Loc("macro.bi_3Dplandroite.1");
            		m.Prompts[6]=Global.Loc("macro.bi_3Dplandroite.2");
            		m.Prompts[7]=Global.Loc("macro.bi_3Dplandroite.3");
            	} else if (m.getName().equals("@builtin@/3Dplanplan")) {
            		m.Prompts[4]=Global.Loc("macro.bi_3Dplanplan.0");
            		m.Prompts[5]=Global.Loc("macro.bi_3Dplanplan.1");
            		m.Prompts[6]=Global.Loc("macro.bi_3Dplanplan.2");
            		m.Prompts[7]=Global.Loc("macro.bi_3Dplanplan.3");
            		m.Prompts[8]=Global.Loc("macro.bi_3Dplanplan.4");
            		m.Prompts[9]=Global.Loc("macro.bi_3Dplanplan.5");
            	} else if (m.getName().equals("@builtin@/3Dsphererayon")) {
            		m.Prompts[4]=Global.Loc("macro.bi_3Dsphererayon.0");
            	} else if (m.getName().equals("@builtin@/3Dspherepoint")) {
            		m.Prompts[4]=Global.Loc("macro.bi_3Dspherepoint.0");
            		m.Prompts[5]=Global.Loc("macro.bi_3Dspherepoint.1");
            	} else if (m.getName().equals("@builtin@/3Dspheredroite")) {
            		m.Prompts[4]=Global.Loc("macro.bi_3Dspheredroite.0");
            		m.Prompts[5]=Global.Loc("macro.bi_3Dspheredroite.1");
            	} else if (m.getName().equals("@builtin@/3Dsphereplan")) {
            		m.Prompts[4]=Global.Loc("macro.bi_3Dsphereplan.0");
            		m.Prompts[5]=Global.Loc("macro.bi_3Dsphereplan.1");
            		m.Prompts[6]=Global.Loc("macro.bi_3Dsphereplan.2");
            		m.Prompts[7]=Global.Loc("macro.bi_3Dsphereplan.3");
            	} else if (m.getName().equals("@builtin@/3Dspheresphere")) {
            		m.Prompts[4]=Global.Loc("macro.bi_3Dspheresphere.0");
            		m.Prompts[5]=Global.Loc("macro.bi_3Dspheresphere.1");
            	}
                ZF.runMacro(m);
            }
        }
    }

    private static void LoadMacros(final InputStream in, final Vector Macros)
            throws Exception {
        Macro m;
        try {
            final XmlReader xml=new XmlReader();
            xml.init(in);
            XmlTree tree=xml.scan();
            if (tree==null) {
                throw new ConstructionException("XML file not recognized");
            }
            Enumeration e=tree.getContent();
            while (e.hasMoreElements()) {
                tree=(XmlTree) e.nextElement();
                if (tree.getTag() instanceof XmlTagPI) {
                    continue;
                }
                if (!tree.getTag().name().equals("CaR")) {
                    throw new ConstructionException("CaR tag not found");
                } else {
                    break;
                }
            }
            e=tree.getContent();
            while (e.hasMoreElements()) {
                tree=(XmlTree) e.nextElement();
                final XmlTag tag=tree.getTag();
                if (tag.name().equals("Macro")) {
                    try {

                        Count.setAllAlternate(true);
                        m=new Macro(null, tree);

                        int i=0;
                        for (i=0; i<Macros.size(); i++) {

                            if (((MacroItem) Macros.elementAt(i)).M.getName().equals(m.getName())) {
                                break;
                            }
                        }
                        if (i>=Macros.size()) {
                            m.setProtected(true);
                            final MacroItem mi=new MacroItem(m, null);
                            Macros.addElement(mi);

                        }
                    } catch (final ConstructionException ex) {
                        Count.setAllAlternate(false);
                        throw ex;
                    }
                    Count.setAllAlternate(false);

                } else {
                    throw new ConstructionException("Construction not found");
                }
            }
        } catch (final Exception e) {
            throw e;
        }
    }

    public static void LoadDefaultMacrosAtStartup() {
        // Loading builtin macros (for some icons in palette, like symetry)
        try {
            final InputStream o=MacroTools.class.getResourceAsStream("/builtin.mcr");
            LoadMacros(o, builtinmacros);
            o.close();
        } catch (final Exception e) {
            System.out.println("builtinmacros bug");
        }
        if ((!themes.isApplet())&&(new File(MacrosLibraryFileName).exists())) {
            try {
                final InputStream o=new FileInputStream(MacrosLibraryFileName);
                LoadMacros(o, librarymacros);
                o.close();
                if (!MacrosBackupLibraryFileName.equals("")) {

                    final InputStream o2=new FileInputStream(
                            MacrosBackupLibraryFileName);
                    LoadMacros(o2, librarymacros);
                    o2.close();
                    final File f=new File(MacrosBackupLibraryFileName);
                    f.delete();
                }
                return;
            } catch (final Exception e) {
                System.out.println("librarymacros bug");
            }
        }
        try {
            final InputStream o=MacroTools.class.getResourceAsStream("/default.mcr");
            LoadMacros(o, librarymacros);
            o.close();
            return;
        } catch (final Exception e) {
            System.out.println("default macros bug");
        }
    }

    public static void saveLibraryToDisk() {
        if (JZirkelCanvas.getCurrentZF()!=null) {
            ZirkelFrame ZF=new ZirkelFrame(pipe_tools.isApplet());
            ZF.dosave(MacrosLibraryFileName, false, true, true, false, librarymacros);
        }
    }


    /* If user changes macro type in the tree (e.g. "add to library") from one figure,
     * library macros vector is changed, so we must transmit all changes to
     * others figures :
     */
    public static void populateMacrosTypeChanges() {
        int max=JZirkelCanvas.getZCsSize();
        for (int i=0; i<max; i++) {
            ZirkelCanvas zc=JZirkelCanvas.getZC(i);
            setDefaultMacros(zc);
        }
    }

    // Called by the JZirkelCanvas constructor :
    public static void setDefaultMacros(ZirkelCanvas zc) {
        if (builtinmacros.size()==0) {
            LoadDefaultMacrosAtStartup();
        }
        if (!(zc==null)) {
            int i=0;
            final Vector F=new Vector();
            final Vector V=zc.getMacros();
            for (i=0; i<V.size(); i++) {
                final MacroItem mi=(MacroItem) V.get(i);
                if (!(mi.M.isProtected())) {
                    F.add(V.get(i));
                }
            }
            V.clear();

            for (i=0; i<librarymacros.size(); i++) {
                zc.appendMacro(((MacroItem) librarymacros.get(i)).M);
            }

            for (i=0; i<F.size(); i++) {
                zc.appendMacro(((MacroItem) F.get(i)).M);
            }
        }
    }
}
