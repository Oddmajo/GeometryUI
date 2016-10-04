/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric;

import eric.GUI.palette.PaletteManager;
import eric.GUI.pipe_tools;
import eric.GUI.themes;
import eric.GUI.window.LeftPanel;
import eric.GUI.window.MainWindow;
import eric.GUI.window.Open_left_panel_btn;
import eric.GUI.window.Open_middle_panel_btn;
import eric.GUI.window.Open_right_panel_btn;
import eric.GUI.window.RightPanel;
import eric.GUI.window.tab_btn;
import eric.GUI.window.tab_main_panel;
import eric.bar.JPropertiesBar;
import eric.jobs.Base64Coder;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.geom.AffineTransform;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import rene.dialogs.Warning;
import rene.gui.Global;
import rene.util.FileName;
import rene.util.ImageSelection;
import rene.util.PngEncoder;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.ZirkelFrame;
import rene.zirkel.construction.Construction;
import rene.zirkel.graphics.MainGraphics;
import rene.zirkel.tools.SelectTool;
import ui.de.erichseifert.vectorgraphics2d.EPSGraphics2D;
import ui.de.erichseifert.vectorgraphics2d.PDFGraphics2D;
import ui.de.erichseifert.vectorgraphics2d.SVGGraphics2D;

/**
 *
 * @author erichake
 */
public class FileTools {

    static private int dlogW=500;
    static private int dlogH=400;
    static private ArrayList<String> StartupFiles=new ArrayList<String>();
    static private boolean isStartup=true;

    public static void addStartupFile(String filename) {
        StartupFiles.add(filename);
    }

    public static boolean isStartup() {
        return isStartup;
    }

    public static String getCurrentFileSource() throws Exception {
        return JZirkelCanvas.getCurrentZC().getFileSource();
    }

    public static void setCurrentFileSource(String s) throws Exception {
        JZirkelCanvas.getCurrentZC().setFileSource(s);
    }

    /*********************************************
     * Simple file part : open and save zir files
     *********************************************/
    
    public static void copyAppletTag() {
        String tag="<APPLET CODEBASE=\"http://db-maths.nuxit.net/\" ARCHIVE=\"CaRMetal.jar\" CODE=\"Main.class\" WIDTH=\"@width@\" HEIGHT=\"@height@\" ALIGN=\"CENTER\" MAYSCRIPT=\"true\">";
        tag+="\n<PARAM NAME=\"source\" VALUE=\"@file_content@\">";
        tag+="\n</APPLET>";
        tag=tag.replace("@width@", ""+(pipe_tools.getWindowSize().width-2*themes.getVerticalBorderWidth()));
        tag=tag.replace("@height@", ""+(pipe_tools.getWindowSize().height-themes.getMenuBarHeight()-themes.getTitleBarHeight()));
        tag=tag.replace("@file_content@", getWorkBookSource());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection myclip = new StringSelection(tag);
        clipboard.setContents(myclip, null);
    }
    
    
    public static void HTMLWorkBookExport() {
        String str;
        String content="";

        if (!JZirkelCanvas.isWorkBook()) {
            saveWorkBookAs();
        }
        ;

        if (JZirkelCanvas.isWorkBook()) {
            saveWorkBook(JZirkelCanvas.getWorkBookFileName());
            String targetpath=FileName.path(JZirkelCanvas.getWorkBookFileName());
            String sep=System.getProperty("file.separator");
            BufferedReader in;
            try {
                InputStream input=FileTools.class.getResourceAsStream("/eric/docs/applet.html");
                in=new BufferedReader(new InputStreamReader(input));
                while ((str=in.readLine())!=null) {
                    content+=str+"\n";
                }
                in.close();
            } catch (final Exception e) {
                return;
            }
            content=content.replace("@width@", ""+(pipe_tools.getWindowSize().width-2*themes.getVerticalBorderWidth()));
            content=content.replace("@height@", ""+(pipe_tools.getWindowSize().height-themes.getMenuBarHeight()-themes.getTitleBarHeight()));
            content=content.replace("@file@", FileName.filename(JZirkelCanvas.getWorkBookFileName()));
            content=content.replace("@workbook@", FileName.filename(JZirkelCanvas.getWorkBookFileName()));
            try {
                File aFile=new File(targetpath+sep+"index.html");
                Writer output=new BufferedWriter(new FileWriter(aFile));
                output.write(content);
                output.close();
            } catch (Exception e) {
            }
        }
    }
    
    public static void HTMLWorkBookExtExport() {
        String str;
        String content="";

        if (!JZirkelCanvas.isWorkBook()) {
            saveWorkBookAs();
        }
        ;

        if (JZirkelCanvas.isWorkBook()) {
//            saveWorkBook(JZirkelCanvas.getWorkBookFileName());
            String targetpath=FileName.path(JZirkelCanvas.getWorkBookFileName());
            String sep=System.getProperty("file.separator");
            BufferedReader in;
            try {
                InputStream input=FileTools.class.getResourceAsStream("/eric/docs/applet_ext.html");
                in=new BufferedReader(new InputStreamReader(input));
                while ((str=in.readLine())!=null) {
                    content+=str+"\n";
                }
                in.close();
            } catch (final Exception e) {
                return;
            }
            content=content.replace("@width@", ""+(pipe_tools.getWindowSize().width-2*themes.getVerticalBorderWidth()));
            content=content.replace("@height@", ""+(pipe_tools.getWindowSize().height-themes.getMenuBarHeight()-themes.getTitleBarHeight()));
            content=content.replace("@file_content@", getWorkBookSource());
            try {
                File aFile=new File(targetpath+sep+"index.html");
                Writer output=new BufferedWriter(new FileWriter(aFile));
                output.write(content);
                output.close();
            } catch (Exception e) {
            }
        }
    }

    public static void SaveJarAndLaunchBrowser() {

        JZirkelCanvas jzc=JZirkelCanvas.getCurrentJZF();
        if (jzc==null) {
            return;
        }
        if (JZirkelCanvas.getFileName()==null) {
            return;
        }
        String targetpath=FileName.path(JZirkelCanvas.getFileName());
        String targetfile="index.html";

        final String sep=System.getProperty("file.separator");
        final String mypath=Global.AppPath();
        if (new File(mypath+"CaRMetal.jar").exists()) {
            try {
                final InputStream in=new FileInputStream(mypath+"CaRMetal.jar");
                final OutputStream out=new FileOutputStream(targetpath+sep+"CaRMetal.jar");
                final byte[] buf=new byte[1024];
                int len;
                while ((len=in.read(buf))>0) {
                    out.write(buf, 0, len);
                    out.flush();
                }
                out.close();
                in.close();

                JBrowserLauncher.openURL(targetpath+sep+targetfile);

            } catch (final Exception ex) {
            }
        }
    }

    public static String getSwingOpenFile() {
        String name=null;
        final JFileChooser jfc=new JFileChooser(Global.getOpenSaveDirectory());
        jfc.setDialogType(javax.swing.JFileChooser.OPEN_DIALOG);
        jfc.setAcceptAllFileFilterUsed(false);
        JFileFilter filter=new JFileFilter(Global.Loc("filedialog.js"), ".js");
        jfc.addChoosableFileFilter(filter);
        jfc.setFileFilter(filter);
        filter=new JFileFilter(Global.Loc("filedialog.job"), ".job");
        jfc.addChoosableFileFilter(filter);
        filter=new JFileFilter(Global.Loc("filedialog.macrofilefilter"), ".mcr");
        jfc.addChoosableFileFilter(filter);
        filter=new JFileFilter(Global.Loc("filedialog.compressedfilefilter"), ".zirz");
        jfc.addChoosableFileFilter(filter);
        filter=new JFileFilter(Global.Loc("filedialog.workbook"), ".zirs");
        jfc.addChoosableFileFilter(filter);
        filter=new JFileFilter(Global.Loc("filedialog.filefilter"), ".zir");
        jfc.addChoosableFileFilter(filter);
        filter=new JFileFilter(Global.Loc("filedialog.allfiles"), ".zirs,.zir,.zirz,.mcr,.job,.js");
        jfc.addChoosableFileFilter(filter);
        final int rep=jfc.showOpenDialog(pipe_tools.getFrame());
        if (rep==JFileChooser.APPROVE_OPTION) {
            name=jfc.getSelectedFile().getAbsolutePath();
            Global.setOpenSaveDirectory(FileName.path(name));
        }
        return name;
    }

    public static String getAWTOpenFile() {
        String name="";
        FileDialog fd=new FileDialog(pipe_tools.getFrame(),
                Global.Loc("filedialog.open"),
                FileDialog.LOAD);
        fd.setFilenameFilter(new FilenameFilter() {

            public boolean accept(File dir, String name) {
                return (name.endsWith(".js")||name.endsWith(".mcr")||name.endsWith(".zir")||name.endsWith(".zirs")||name.endsWith(".zirz")||name.endsWith(".job")||name.endsWith(".jobz"));
            }
        });
        fd.setDirectory(Global.getOpenSaveDirectory());
        fd.setSize(dlogW, dlogH);
        fd.pack();
        fd.setVisible(true);
        if (fd.getFile()!=null) {
            Global.setOpenSaveDirectory(fd.getDirectory());
        }
        return canonicFileName(fd);
    }

    public static String getOpenFile() {
        if (OS.isUnix()) {
            return getSwingOpenFile();
        } else {
            return getAWTOpenFile();
        }
    }

    public static String getSwingSaveFile(boolean export) {
        String name=null;
        final JFileChooser jfc=new JFileChooser(Global.getOpenSaveDirectory());
        jfc.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);
        jfc.setAcceptAllFileFilterUsed(false);
        JFileFilter filter;
        if (export) {
            filter=new JFileFilter(Global.Loc("filedialog.allexportfiles"), ".pdf,.eps,.svg,.png");
        } else {
            filter=new JFileFilter(Global.Loc("filedialog.allfiles"), ".zirs,.zir,.zirz,.mcr,.job");
        }

        jfc.addChoosableFileFilter(filter);
        jfc.setFileFilter(filter);
        final int rep=jfc.showSaveDialog(pipe_tools.getFrame());
        System.out.println(jfc.getFileFilter().toString());
        if (rep==JFileChooser.APPROVE_OPTION) {
            name=jfc.getSelectedFile().getAbsolutePath();
            Global.setOpenSaveDirectory(FileName.path(name));
        }
        return name;
    }

    public static String getAWTSaveFile(boolean export) {
        String name="";
        FileDialog fd=new FileDialog(pipe_tools.getFrame(),
                Global.Loc("filedialog.save"),
                FileDialog.SAVE);
        if (export) {
            fd.setFilenameFilter(new FilenameFilter() {

                public boolean accept(File dir, String name) {
                    return (name.endsWith(".pdf")||name.endsWith(".eps")||name.endsWith(".svg")||name.endsWith(".png"));
                }
            });
        } else {
            fd.setFilenameFilter(new FilenameFilter() {

                public boolean accept(File dir, String name) {
                    return (name.endsWith(".js")||name.endsWith(".mcr")||name.endsWith(".zir")||name.endsWith(".zirs")||name.endsWith(".zirz")||name.endsWith(".job")||name.endsWith(".jobz"));
                }
            });
        }

        fd.setDirectory(Global.getOpenSaveDirectory());
        fd.setSize(dlogW, dlogH);
        fd.pack();
        fd.setVisible(true);
        if (fd.getFile()!=null) {
            Global.setOpenSaveDirectory(fd.getDirectory());
        }
        return canonicFileName(fd);
    }

    public static String getSaveFile(boolean export) {
        if (OS.isUnix()) {
            return getSwingSaveFile(export);
        } else {
            return getAWTSaveFile(export);
        }
    }

    public static String canonicFileName(FileDialog fd) {
        if (fd.getFile()==null) {
            return null;
        }
        String path=(fd.getDirectory().endsWith(System.getProperty("file.separator")))?fd.getDirectory():fd.getDirectory()+System.getProperty("file.separator");
        path+=fd.getFile();
        return path;
    }

    public static boolean validPreferencesFileName(String name) {
        return name.endsWith("preferences.txt");
    }

    public static boolean validZirkelFileName(String name) {
        String nme=name.toLowerCase();
        return (nme.endsWith(".zir")||nme.endsWith(".zirz")||nme.endsWith(".job")||nme.endsWith(".jobz"));
    }

    public static boolean validImageFileName(String name) {
        String nme=name.toLowerCase();
        return (nme.endsWith(".tif")||nme.endsWith(".tiff")||nme.endsWith(".gif")||nme.endsWith(".png")||nme.endsWith(".jpg")||nme.endsWith(".jpeg"));
    }

    public static void openFile() {
        final String filename=getOpenFile();
        if (filename!=null) {
            if (filename.endsWith(".zirs")) {
                openWorkBook(filename);
            } else if (filename.endsWith(".mcr")) {
                openMacro(filename);
            } else if (filename.endsWith(".zir")) {
                openFile(filename, null, 0);
            } else {
                openScript(filename);
            }
        }
    }

    public static void doLoad(final String filename, final InputStream in, final int mode) {
        JZirkelCanvas JZF=JZirkelCanvas.getCurrentJZF();
        ZirkelFrame ZF=JZirkelCanvas.getCurrentZF();
        final ZirkelCanvas ZC=JZirkelCanvas.getCurrentZC();
        if (ZC!=null) {
            ZC.setMode(mode);
            ZF.setinfo("save");
            ZC.getConstruction().BackgroundFile=null;
            ZF.Background="";
            ZC.grab(false);
            rene.zirkel.construction.Count.resetAll();
            ZF.doload(filename, in);
            PaletteManager.initPaletteConsideringMode();
//            PaletteManager.fixDPpalette();
            ZC.getLocalPreferences();
            rene.zirkel.construction.Count.resetAll();

            pipe_tools.setWindowComponents();
            SwingUtilities.invokeLater(new Runnable() {

                public void run() {
                    ZC.JCM.readXmlTags();
                    PaletteManager.refresh();
                    PaletteManager.setSelected_with_clic("move", true);
                    ZC.runOnLoadScripts();
                }
            });
        }
    }
    
    public static void doLoadNew3D(final String filename, final InputStream in, final int mode) {
        JZirkelCanvas JZF=JZirkelCanvas.getCurrentJZF();
        ZirkelFrame ZF=JZirkelCanvas.getCurrentZF();
        final ZirkelCanvas ZC=JZirkelCanvas.getCurrentZC();
        if (ZC!=null) {
            ZC.setMode(mode);
            ZF.setinfo("save");
            ZC.getConstruction().BackgroundFile=null;
            ZF.Background="";
            ZC.grab(false);
            rene.zirkel.construction.Count.resetAll();
            ZF.doload(filename, in);
            PaletteManager.initPaletteConsideringMode();
//            PaletteManager.fixDPpalette();
            ZC.getLocalPreferences();
            rene.zirkel.construction.Count.resetAll();

            pipe_tools.setWindowComponents();
            SwingUtilities.invokeLater(new Runnable() {

                public void run() {
                    ZC.JCM.readXmlTags();
                    PaletteManager.refresh();
                    PaletteManager.setSelected_with_clic("bi_3Dcoords", true);
                    ZC.runOnLoadScripts();
                }
            });
        }
    }

    public static void openMacro(final String filename) {
        try {

            FileInputStream o=new FileInputStream(filename);
            JZirkelCanvas.getCurrentZC().load(o, false, true);
            o.close();
        } catch (Exception ex) {
        }
        JZirkelCanvas.ActualiseMacroPanel();
    }

    private static void openScript(String filename) {
        ZirkelCanvas ZC=JZirkelCanvas.getCurrentZC();
        if (ZC==null) {
            return;
        }
        if (filename==null) {
            return;
        }
        ZC.openScriptFile(filename, true);
    }

    public static void openFile(final String filename, final InputStream in, final int mode) {
        ZirkelCanvas ZC=JZirkelCanvas.getCurrentZC();
        if (ZC==null) {
            return;
        }
        if (filename==null) {
            return;
        }
        if ((filename.endsWith(".zir"))||(filename.endsWith(".zirz"))) {
            if (ZC.isEmpty()) {
                tab_main_panel.setCurrentTabName(FileName.filename(filename), filename);

            } else {
                tab_main_panel.createTabAndCanvas(FileName.filename(filename), filename);
            }
        }
        doLoad(filename, in, mode);
        JZirkelCanvas.getCurrentJZF().setComments();
        JZirkelCanvas.ActualiseMacroPanel();

    }

    public static void New3DWindow() {
    	InputStream o;
    	String Filename;
    	if (System.getProperty("user.language").equals("fr")) {
    		o=FileTools.class.getResourceAsStream("/base3D-fr.zir");
    		Filename="base3D-fr.zir";
    	}
    	else if (System.getProperty("user.language").equals("es")) {
    		o=FileTools.class.getResourceAsStream("/base3D-es.zir");
    		Filename="base3D-es.zir";
    	}
    	else {
    		o=FileTools.class.getResourceAsStream("/base3D.zir");
    		Filename="base3D.zir";
    	}
        final ZirkelCanvas zc=JZirkelCanvas.getCurrentZC();
        if (!zc.isEmpty()) {
            tab_main_panel.newTabBtn();
        }
        doLoadNew3D(Filename, o, Construction.MODE_3D);
        ZirkelFrame zf=JZirkelCanvas.getCurrentZF();
        if (zf!=null) {
            zf.Filename="";
        }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (zc!=null) {
                    zc.JCM.fix3Dcomments();
                }
            }
        });
    }

    public static void NewDPWindow() {
        final InputStream o=FileTools.class.getResourceAsStream("/baseDP.zir");
        final String Filename="baseDP.zir";
        final ZirkelCanvas zc=JZirkelCanvas.getCurrentZC();
        if (!zc.isEmpty()) {
            tab_main_panel.newTabBtn();

        }
        doLoad(Filename, o, Construction.MODE_DP);
        ZirkelFrame zf=JZirkelCanvas.getCurrentZF();
        if (zf!=null) {
            zf.Filename="";
        }
//        SwingUtilities.invokeLater(new Runnable() {
//
//            public void run() {
////                if (zc!=null) {
////                    zc.JCM.fixDPcomments();
////                }
//            }
//        });
    }

    public static void saveFileAs() {
        //if (Media.getMedias().size()>0) {
	if (Media.checkMedia()>0) {
            JOptionPane.showMessageDialog(JZirkelCanvas.getCurrentZC(), Global.Loc("media.saveas"));
            saveWorkBookAs();
        } else {
            String filename=getSaveFile(false);
            if (filename!=null) {
                saveFile(filename, false, ".zir", false);
            }
        }
    }

    public static void saveFile() {
        if (JZirkelCanvas.isWorkBook()) {
            saveWorkBook(JZirkelCanvas.getWorkBookFileName());
        //} else if (Media.getMedias().size()>0) {
	} else if (Media.checkMedia()>0) {
            JOptionPane.showMessageDialog(JZirkelCanvas.getCurrentZC(), Global.Loc("media.saveas"));
            saveWorkBookAs();
        } else {
            if (JZirkelCanvas.getCurrentZC()==null) {
                return;
            }
            ZirkelFrame ZF=JZirkelCanvas.getCurrentZF();
            ZirkelCanvas ZC=JZirkelCanvas.getCurrentZC();
            String filename=(ZF.haveFile())?ZF.Filename:getSaveFile(false);
            if (filename!=null) {
                saveFile(filename, false, ".zir", !ZF.haveFile());
            }
        }
    }

    private static boolean ICanSave(final String Fname, final boolean ask) {
        if (!ask) {
            return true;
        }
        if (!new File(Fname).exists()) {
            return true;
        }
        return (JOptionPane.showConfirmDialog(null, Global.Loc("filedialog.savemessage1")+Fname+Global.Loc("filedialog.savemessage2"), "",
                JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION);
    }

    private static void doSave(ZirkelCanvas zc, String Filename, OutputStream o) {
        try {
            if (o==null) {
                o=new FileOutputStream(Filename);
                if (ZirkelFrame.isCompressed(Filename)) {
                    o=new GZIPOutputStream(o, 10000);
                }
            }
            zc.save(o, true, true, false, true, zc.getMacros(), "");
            o.close();
        } catch (Exception e) {
        }
    }

    private static void saveFile(String Filename, final boolean restrict, final String ext, final boolean ask) {
        if (JZirkelCanvas.getCurrentZC()==null) {
            return;
        }
        if (Filename==null) {
            return;
        }
        ZirkelFrame ZF=JZirkelCanvas.getCurrentZF();
        ZirkelCanvas ZC=JZirkelCanvas.getCurrentZC();
        if ((!Filename.endsWith(".zirz"))&&(!Filename.endsWith(".zir"))) {
            Filename+=ext;
        }
        if (ICanSave(Filename, ask)) {
//            SetMacrosProtectionForSaveProcess(macrostree.JML.MacroTreeTopNode);
            OutputStream o;
            try {
                ZF.ZC.getConstruction().BackgroundFile=ZF.Background;
                ZF.ZC.getConstruction().ResizeBackground=Global.getParameter(
                        "background.usesize", false);
                doSave(ZF.ZC, Filename, null);
                ZF.Filename=Filename;
		if(!JZirkelCanvas.isWorkBook())
		    tab_main_panel.setCurrentTabName(FileName.filename(Filename), Filename);
            } catch (final Exception e) {
                final Warning w=new Warning(pipe_tools.getFrame(), Global.Loc("warning.save"),
                        FileName.chop(32, e.toString(), 64), Global.Loc("warning"), true);
                w.center(null);
                w.setVisible(true);
            }
//            ResetMacrosProtection(macrostree.JML.MacroTreeTopNode);
        }
        pipe_tools.TabHaveChanged(false);
    }

    /*********************************************
     * Workbooks part : open and save zirs files
     *********************************************/
    public static String getConstruction(ZirkelCanvas zc) {
        try {
            final ByteArrayOutputStream out=new ByteArrayOutputStream();
            doSave(zc, null, out);
            return out.toString("utf-8");
        } catch (Exception ex) {
            return "Error";
        }
    }

    public static void saveWorkBookAs() {
        final String filename=getSaveFile(false);
        if (filename!=null) {
            saveWorkBook(filename);
        }
    }

    public static String getWorkBookSource() {
        String backup="";
        try {
            ByteArrayOutputStream bout=new ByteArrayOutputStream();
            // save the workbook into a byteArray stream :
            saveWorkBook(bout);
            // then save it into base64 format :
            backup=new String(Base64Coder.encode(bout.toByteArray()));

            // Make the base64 encoding url safe :
            backup=backup.replace("+", "-");
            backup=backup.replace("/", "_");
            // remove base64 paddings :
            backup=backup.replace("=", "");

        } catch (Exception ex) {
        }
        return backup;
    }

    public static void setWorkBookSource(String s) {
        try {
            // base64 encoding is url safe. This restore the
            // "official" syntax of the string :
            s=s.replace("-", "+");
            s=s.replace("_", "/");
            // restore base64 paddings :
            int reste=s.length()%4;
            if (reste==2) {
                s+="==";
            } else if (reste==3) {
                s+="=";
            }

            byte[] b=Base64Coder.decode(s);
            ByteArrayInputStream bin=new ByteArrayInputStream(b);
            openWorkBook(null, bin);
        } catch (Exception ex) {
        }
    }

    public static void saveWorkBook(String filename) {
        if (filename==null) {
            return;
        }
        if (!filename.endsWith(".zirs")) {
            filename+=".zirs";
        }
        try {
            saveWorkBook(new FileOutputStream(filename));
            // set workbook mode :
            JZirkelCanvas.setWorkBookFileName(filename, true);
        } catch (Exception ex) {
        }
    }

    public static void saveWorkBook(OutputStream output) {
        try {
            // Create the ZIP file

            ZipOutputStream out=new ZipOutputStream(output);

            // Save the workbook :

            Progress_Bar.create(Global.Loc("progressbar.workbooksavemessage"), 0, tab_main_panel.getBTNSsize()-1);

            for (int i=0; i<tab_main_panel.getBTNSsize(); i++) {
                tab_btn btn=tab_main_panel.getBTN(i);
		btn.setToolTip(btn.getTabName()+".zir");
                ZirkelCanvas ZC=btn.getPanel().getZC();
                out.putNextEntry(new ZipEntry(btn.getTabName()+".zir"));


                if (btn.getPanel().getByteArrayInputFile()==null) {
                    ByteArrayOutputStream file=new ByteArrayOutputStream();
                    doSave(ZC, null, file);
                    out.write(file.toByteArray());
                } else {
                    out.write(btn.getPanel().getByteArrayInputFile());
                }
                out.flush();
                out.closeEntry();
                Progress_Bar.nextValue();
            }
            for (int i=0; i<Media.getMedias().size(); i++) {
                out.putNextEntry(new ZipEntry(Media.getMedias().get(i).getImageFileName()));
                out.write(Media.getMedias().get(i).getImageBytes());
            }
            out.putNextEntry(new ZipEntry("preferences.txt"));
            out.write(createPreferencesFile());

            Progress_Bar.close();

            out.close();
        } catch (IOException e) {
        }
    }

    public static byte[] createPreferencesFile() {
        String file="** COMMON PART (APPLET and Application) :\n";
        file+="\n";
        file+="showcomments="+Global.getParameter("comment", true)+"\n";
        file+="showpalette="+RightPanel.isPanelVisible()+"\n";
        file+="showleftpanel="+LeftPanel.isPanelVisible()+"\n";
        file+="showtabs="+(tab_main_panel.getBTNSsize()>1)+"\n";
        file+="showstatus="+(tab_main_panel.getBTNSsize()>1)+"\n";
        file+="currenttab="+(tab_main_panel.getActiveBtnPos()+1);
        file+="\n\n\n";
        file+="** APPLICATION SPECIFIC PART :\n";
        file+="\n";
        file+="window_width="+pipe_tools.getWindowSize().width+"\n";
        file+="window_height="+pipe_tools.getWindowSize().height+"\n";
        file+="\n";
        return file.getBytes();
    }

    public static void openWorkBook() {
        final String filename=getOpenFile();
        if (filename!=null) {
            openWorkBook(filename);
        }
    }

    public static void openWorkBook(String filename) {
        try {
            openWorkBook(filename, new FileInputStream(filename));
        } catch (Exception ex) {
        }
    }

    // OpenWork book from Application :
    public static void openWorkBook(String filename, InputStream in) {
        try {

            ZipInputStream zpf=new ZipInputStream(new BufferedInputStream(in));
            String prefsFileContent=null;

            // remember last tab button :
            tab_btn lastbtn=tab_main_panel.getLastBtn();
            // remember last tab position before adding new ones :
            int last_tab_pos=tab_main_panel.getBTNSsize()-1;

            ZipEntry entry;
            while ((entry=zpf.getNextEntry())!=null) {

                int size;
                byte[] buffer=new byte[2048];

                ByteArrayOutputStream bos=new ByteArrayOutputStream();

                while ((size=zpf.read(buffer, 0, buffer.length))!=-1) {
                    bos.write(buffer, 0, size);
                }
                bos.flush();
                bos.close();

                if (validZirkelFileName(entry.getName())) {
                    tab_btn curbtn=tab_main_panel.addBtn(entry.getName());
                    curbtn.getPanel().setInputFile(bos.toByteArray());
                } else if (validImageFileName(entry.getName())) {
                    Media.createMedia(entry.getName(), bos.toByteArray());
                } else if (validPreferencesFileName(entry.getName())) {
                    prefsFileContent=bos.toString("UTF-8");
                }

            }

            // set workbook mode :
            if (filename!=null) {
                JZirkelCanvas.setWorkBookFileName(filename, false);
            }

            if (prefsFileContent!=null) {
                applyWorkBookPreferences(prefsFileContent, last_tab_pos);
            } else {
                tab_main_panel.setActiveBtn(1);
                tab_main_panel.showActiveBtn();
            }

            // if last tab canvas is empty, then remove it :
            if ((lastbtn.getPanel().getZC().isEmpty())&&(lastbtn.getPanel().getByteArrayInputFile()==null)) {
                tab_main_panel.removeBtn(lastbtn);
            }

            // I know, this is weird, but it work's (for palette display) :
            pipe_tools.setWindowComponents();
            pipe_tools.setWindowComponents();
            Open_left_panel_btn.setmode();
            Open_middle_panel_btn.setmode();
            Open_right_panel_btn.setmode();
            JGeneralMenuBar.initToggleItems();
            tab_main_panel.initToggleItems();
        } catch (Exception e) {
            return;
        }
    }

    /**
     * inside the workbook zirs archive, "preferences.txt" contains properties with
     * following structure : <property>=<value>
     * @param prefs : content of the "preferences.txt" file
     * @param property : the property we want to read
     * @return the value of the property
     */
    public static String getPref(String prefs, String property) {
        Matcher m=Pattern.compile("\\Q"+property+"=\\E(\\w+)\\W", Pattern.MULTILINE).matcher(prefs);
        if (m.find()) {
            return m.group(1);
        }
        return null;
    }

    /**
     * Read and apply workbook preferences : "preferences.txt" inside the "zirs" archive
     * @param prefs : content of the "preferences.txt" file
     * @param last_tab_pos : last tab position before adding the zirs
     */
    public static void applyWorkBookPreferences(final String prefs, int last_tab_pos) {

        int tabnum=last_tab_pos;
        try {
            tabnum+=Integer.parseInt(getPref(prefs, "currenttab"));
        } catch (Exception e) {
            tabnum=last_tab_pos;
        }

        tab_main_panel.setActiveBtn(tabnum);
        tab_main_panel.showActiveBtn();

        Global.setParameter("comment", "true".equals(getPref(prefs, "showcomments")));
        RightPanel.showPanel("true".equals(getPref(prefs, "showpalette")));
        LeftPanel.showPanel("true".equals(getPref(prefs, "showleftpanel")));
        if (pipe_tools.isApplet()) {
            themes.setShowTabs("true".equals(getPref(prefs, "showtabs")));
            themes.setShowStatus("true".equals(getPref(prefs, "showstatus")));
        } else {
            themes.setShowTabs(true);
            themes.setShowStatus(true);
        }
        try {
            int w=Integer.parseInt(getPref(prefs, "window_width"));
            int h=Integer.parseInt(getPref(prefs, "window_height"));
            pipe_tools.setAndCheckWindowSize(w, h);
        } catch (Exception e) {
        }
    }

    public static byte[] copyToByteArray(InputStream in) {
        int BUFFER_SIZE=4096;
        ByteArrayOutputStream out=new ByteArrayOutputStream(BUFFER_SIZE);
        try {
            int byteCount=0;
            byte[] buffer=new byte[BUFFER_SIZE];
            int bytesRead=-1;
            while ((bytesRead=in.read(buffer))!=-1) {
                out.write(buffer, 0, bytesRead);
                byteCount+=bytesRead;
            }
            out.flush();
            in.close();
            out.close();
        } catch (IOException e) {
        }
        return out.toByteArray();
    }

    public static void open(String filename) {
        if ((filename.endsWith(".mcr"))) {
            openMacro(filename);
        } else if ((filename.endsWith(".zirs"))) {
            openWorkBook(filename);
        } else {
            openFile(filename, null, 0);
        }
    }

    public static void OpenStartupFiles() {
        for (int i=0; i<StartupFiles.size(); i++) {
            final String filename=(String) StartupFiles.get(i);
            open(filename);
        }
        StartupFiles.clear();
    }

    public static void FirstRun() {
        if (isStartup) {
            JPropertiesBar.CreatePropertiesBar();
            MainWindow mw=new MainWindow();

            PaletteManager.init();
            PaletteManager.setSelected_with_clic("point", true);
            JLogoWindow.DisposeLogoWindow();
            JZirkelCanvas.ActualiseMacroPanel();
        }
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                OpenStartupFiles();
            }
        });

        isStartup=false;
    }
    /******************************************************
     ****************** EXPORT PART ***********************
     ******************************************************/
    // Export graphique (vers PNG, PDF ou EPS ou SVG) :
    final public static int PNG=0, EPS=1, PDF=2, SVG=3;

    static String fileExtension(String filename, String ext) {
        String myext=null;
        myext=(filename.toLowerCase().endsWith(ext))?"":ext;
        return myext;
    }

    public static void exportGraphicFile(int FileType) {
        String filename=getSaveFile(true);
        if (filename!=null) {
            exportGraphicFile(FileType, filename);
        }
    }

    public static void exportGraphicFile(int FileType, String filename) {

        ZirkelCanvas ZC=JZirkelCanvas.getCurrentZC();
        if (ZC==null) {
            return;
        }

        Construction c=ZC.getConstruction();
        int oldW=ZC.IW, oldH=ZC.IH;

        ZC.startWaiting();

        float Scale=1.0f;
        Image Ipng=null; // Seulement pour l'export PNG

        // cruel dilemne : pour les png et le copier traditionnel, on peut :
        //     1- imposer de conserver l'échelle et alors les images passent moins bien sur un site
        //     2- donner la priorité au pixel et alors bien sûr l'echelle n'est plus respectée dans
        //        un traitement de texte par exemple.
//        if (true) {
        if (FileType!=PNG) {
            double scale11;
            if ((FileType==SVG)||(FileType==PDF)) {
                //Echelle 1unité:1cm pour les svg et pdf :
                scale11=10.0/ZC.getConstruction().getPixel();
            } else {
                //Echelle 1unité:1cm pour les fichiers png et eps :
                scale11=720.0/(25.4*ZC.getConstruction().getPixel());
            }

            // Faire en sorte que l'espacement entre deux lignes verticales
            // consécutives de la grille corresponde à 1cm à l'impression :
            Scale=(float) (scale11/ZC.getGridSize());

            // Petit réajustement de l'echelle au rapport pixels/pixels.
            Scale=1.0f*Math.round(Scale*oldW)/oldW;
        }



        // On calcule les (X,Y,W,H) du rectangle de sélection. Ici,
        // il s'agit de la fenêtre entière :
        double RX=0;
        double RY=0;
        double RW=Math.round(Scale*oldW);
        double RH=Math.round(Scale*oldH);

        // Si l'outil de sélection est actif, on réajuste les (X,Y,W,H)
        // pour qu'ils correspondent au rectangle de sélection courant :
        if (ZC.getTool() instanceof SelectTool) {
            ZC.hideCopyRectangle();
            Rectangle r=ZC.getCopyRectangle();
            RX=1.0*r.x*RW/oldW;
            RY=1.0*r.y*RH/oldH;
            RW=1.0*r.width*RW/oldW;
            RH=1.0*r.height*RH/oldH;
        }

        ZC.scaleLocalPreferences(Scale);

        ZC.IW=Math.round(Scale*oldW);
        ZC.IH=Math.round(Scale*oldH);

        Graphics2D G2D=null;
        switch (FileType) {
            case EPS:
                G2D=new EPSGraphics2D(0, 0, RW, RH);
                filename=(filename!=null)?filename+fileExtension(filename, ".eps"):null;
                break;
            case PDF:
                G2D=new PDFGraphics2D(0, 0, RW, RH);
                filename=(filename!=null)?filename+fileExtension(filename, ".pdf"):null;
                break;
            case SVG:
                G2D=new SVGGraphics2D(0, 0, RW, RH);
                filename=(filename!=null)?filename+fileExtension(filename, ".svg"):null;
                break;
            case PNG:
                Ipng=ZC.createImage((int)RW, (int)RH);
                G2D=(Graphics2D) Ipng.getGraphics();
                filename=(filename!=null)?filename+fileExtension(filename, ".png"):null;
                break;
        }

        MainGraphics IG=new MainGraphics(G2D, ZC);
        IG.setSize(ZC.IW, ZC.IH);

        if (FileType!=SVG){
            IG.clearRect(0, 0, ZC.IW, ZC.IH, ZC.getBackground());
        }
        ZC.recompute();
        ZC.validate();
        ZC.forceComputeHeavyObjects();

        // Avant de peindre, on réalise la translation qui amène en
        // (0,0) le coin supérieur gauche du rectangle de sélection :
        G2D.translate(-RX, -RY);

        // On peint :
        ZC.dopaint(IG);
        ZC.endWaiting();


        if (filename!=null) {
            // Il s'agit d'une exportation (fichier) :
            if (FileType==PNG) {
                try {
                    final BufferedOutputStream out=new BufferedOutputStream(new FileOutputStream(filename));
                    final PngEncoder png=new PngEncoder(Ipng, PngEncoder.NO_ALPHA, 0, 9);
                    png.setDPI(72);
                    out.write(png.pngEncode());
                    out.close();
                } catch (final Exception e) {
                    // warning(e.toString());
                }
            } else {
                String CharsetName=(FileType==PDF)?"ISO-8859-1":"UTF-8";
                try {
                    Writer out=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), CharsetName));
                    out.write(G2D.toString());
                    out.close();
                } catch (IOException e) {
                    System.out.println("bug !!!");
                }
            }
        } else {
            // Il s'agit d'un "copier vers" :
            Transferable pdft=null;
            switch (FileType) {
                case EPS:
                    pdft=new CopyTransferable(G2D.toString(), "application/postscript", "UTF-8");
                    break;
                case PDF:
                    pdft=new CopyTransferable(G2D.toString(), "application/pdf", "ISO-8859-1");
                    break;
                case SVG:
                    pdft=new CopyTransferable(G2D.toString(), "image/svg+xml", "UTF-8");
                    break;
                case PNG:
                    pdft=new ImageSelection(Ipng);
                    break;
            }
            if (pdft!=null) {
                Clipboard clip=Toolkit.getDefaultToolkit().getSystemClipboard();
                clip.setContents(pdft, null);
            }
        }
        ZC.getLocalPreferences();
        ZC.IW=oldW;
        ZC.IH=oldH;
        if (ZC.getTool() instanceof SelectTool) {
            ZC.showCopyRectangle();
        }
    }

}

class CopyTransferable implements Transferable {

    public final DataFlavor MY_FLAVOUR;
    private final DataFlavor[] flavors;
    private final String filecontent;
    private final String Charset;

    public CopyTransferable(String f, String filetype, String charset) {
        filecontent=f;
        Charset=charset;
        MY_FLAVOUR=new DataFlavor(filetype, null);
        flavors=new DataFlavor[]{MY_FLAVOUR};
    }

    public Object getTransferData(DataFlavor flavor)
            throws UnsupportedFlavorException, IOException {
        if (MY_FLAVOUR.equals(flavor)) {
            return new ByteArrayInputStream(filecontent.getBytes(Charset));
        } else {
            throw new UnsupportedFlavorException(flavor);
        }
    }

    public DataFlavor[] getTransferDataFlavors() {
        return flavors;
    }

    public boolean isDataFlavorSupported(DataFlavor flavor) {
        DataFlavor[] flavors=getTransferDataFlavors();
        for (int i=0; i<flavors.length; i++) {
            if (flavors[i].equals(flavor)) {
                return true;
            }
        }
        return false;
    }
}
