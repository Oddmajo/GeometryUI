/* 

Copyright 2006 Rene Grothmann, modified by Eric Hakenholz

This file is part of C.a.R. software.

C.a.R. is a free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, version 3 of the License.

C.a.R. is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.

 */
package rene.gui;

import eric.GUI.pipe_tools;
import eric.GUI.window.MenuBar;
import eric.GUI.window.tab_btn;
import eric.GUI.window.tab_main_panel;
import eric.JBrowserLauncher;
import eric.JLogoWindow;
import eric.JZirkelCanvas;
import eric.OS;
import eric.bar.JPropertiesBar;
import eric.controls.SliderSnap;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileSystemView;
import rene.dialogs.Warning;
import rene.util.FileName;
import rene.util.parser.StringParser;
import rene.util.xml.XmlTag;
import rene.util.xml.XmlWriter;
import rene.gui.Global;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.ZirkelFrame;
import rene.zirkel.construction.Construction;
import rene.zirkel.expression.ExpressionColor;
import rene.zirkel.objects.ConstructionObject;
import rene.zirkel.objects.DriverObject;
import rene.zirkel.objects.UserFunctionObject;

/**
 * The Global class.
 * <p>
 * This class will load a resource bundle with local support. It will set
 * various things from this resource file.
 */
public class Global {
    // Fonts:

    static public Font NormalFont=null, FixedFont=null, BoldFont=null;
    static final public String CONFIG=".carmetal_config";
    static public String GlobalFont="Dialog";
    private static ResourceBundle ericBundle;
    private static ResourceBundle reneBundle;
    static public String FontURL="";
    static public String ChineseFontURL="http://db-maths.nuxit.net/CaRMetal/fonts/fireflysung.zip";

    static public void makeFonts() {
        NormalFont=createfont("normalfont", "SansSerif", 12, false);
        FixedFont=createfont("fixedfont", "Monospaced", 12, false);
        BoldFont=createfont("fixedfont", "Monospaced", 12, true);
        isNewVersion=true;
    }

    static {
        makeFonts();
    }

    static public Font createfont(final String name, final String def,
            final int defsize, final boolean bold) {
        final String fontname=getParameter(name+".name", def);
        final String mode=getParameter(name+".mode", "plain");
        if (bold||mode.equals("bold")) {
            return new Font(fontname, Font.BOLD, getParameter(name+".size",
                    defsize));
        } else if (mode.equals("italic")) {
            return new Font(fontname, Font.ITALIC, getParameter(name+".size",
                    defsize));
        } else {
            return new Font(fontname, Font.PLAIN, getParameter(name+".size", defsize));
        }
    }



    // For heavy objects dependencies :
    static private Vector ObjectsToClearList=new Vector();

    static public void doClearList() {
        final Enumeration e=ObjectsToClearList.elements();
        while (e.hasMoreElements()) {
            final DriverObject dobj=(DriverObject) e.nextElement();
            if (dobj.somethingChanged()) {
                dobj.clearChanges();
            }
        }
        ObjectsToClearList.clear();
    }

    static public void addClearList(final DriverObject oc) {
        ObjectsToClearList.add(oc);
    }

    public static Enumeration names() {
        if (reneBundle!=null) {
            return reneBundle.getKeys();
        } else {
            return null;
        }
    }

    public static String name(final String tag, final String def) {
        String s;
        if (reneBundle==null) {
            return def;
        }
        try {
            s=reneBundle.getString(tag);
        } catch (final Exception e) {
            s=def;
        }
        return s;
    }

    public static String name(final String tag) {
        return name(tag, tag.substring(tag.lastIndexOf(".")+1));
    }

    static public String Loc(final String s) {
        String rep=null;
        try {
            rep=ericBundle.getString(s);
        } catch (Exception e1) {
            try {
                rep=reneBundle.getString(s);
            } catch (Exception e2) {
            }
        }
        return rep;
    }

    public static void initBundles() {
        String lang=getParameter("language", "");
        String country=getParameter("country", "");
        if (!lang.equals("")) {
            try {
                Locale.setDefault(new Locale(lang, country));
            } catch (final Exception ex) {
                Locale.setDefault(new Locale("en", ""));
                setParameter("language", "en");
                setParameter("country", "");
            }
        }
        ericBundle=ResourceBundle.getBundle("eric/docs/JZirkelProperties");
        reneBundle=ResourceBundle.getBundle("rene/zirkel/docs/ZirkelProperties");
    }
    // Properties:
    public static Properties P=new Properties();
    static String ConfigName;

    public static synchronized Enumeration properties() {
        return P.keys();
    }

    public static synchronized void loadProperties(final InputStream in) {
        try {
            P=new Properties();
            P.load(in);
            in.close();
        } catch (final Exception e) {
            P=new Properties();
        }
    }

    public static synchronized boolean loadPropertiesFromResource(
            final String filename) {
        try {
            final Object G=new Object();
            final InputStream in=G.getClass().getResourceAsStream(filename);
            P=new Properties();
            P.load(in);
            in.close();
        } catch (final Exception e) {
            P=new Properties();
            return false;
        }
        ConfigName=filename;
        return true;
    }

    public static synchronized boolean loadProperties(final String filename) {
        ConfigName=filename;
        try {
            final FileInputStream in=new FileInputStream(filename);
            P=new Properties();
            P.load(in);
            in.close();
        } catch (final Exception e) {
            P=new Properties();
            return false;
        }
        return true;
    }

    public static synchronized void loadProperties(final String dir,
            final String filename) {
        try {
            final Properties p=System.getProperties();
            ConfigName=dir+p.getProperty("file.separator")+filename;
            loadProperties(ConfigName);
        } catch (final Exception e) {
            P=new Properties();
        }
    }

    public static synchronized void loadPropertiesInHome(final String filename) {
        try {
            final Properties p=System.getProperties();
            loadProperties(p.getProperty("user.home"), filename);
        } catch (final Exception e) {
            P=new Properties();
        }
    }

    public static synchronized void clearProperties() {
        P=new Properties();
    }

    public static synchronized void saveProperties(final String text) {
        try {
            final FileOutputStream out=new FileOutputStream(ConfigName);
            P.store(out, text);
            // P.save(out,text);
            out.close();
        } catch (final Exception e) {
        }
    }

    public static void saveProperties(final String text, final String filename) {
        ConfigName=filename;
        saveProperties(text);
    }

    public static synchronized void setParameter(final String key,
            final boolean value) {
        if (P==null) {
            return;
        }
        if (value) {
            P.put(key, "true");
        } else {
            P.put(key, "false");
        }
    }

    public static synchronized boolean getParameter(final String key,
            final boolean def) {
        try {
            final String s=P.getProperty(key);
            if (s.equals("true")) {
                return true;
            } else if (s.equals("false")) {
                return false;
            }
            return def;
        } catch (final Exception e) {
            return def;
        }
    }

    public static synchronized String getParameter(final String key,
            final String def) {
        String res=def;
        try {
            res=P.getProperty(key);
        } catch (final Exception e) {
        }
        if (res!=null) {
            if (res.startsWith("$")) {
                res=res.substring(1);
            }
            return res;
        } else {
            return def;
        }
    }

    public static synchronized void setParameter(final String key, String value) {
        if (P==null) {
            return;
        }
        if (value.length()>0&&Character.isSpaceChar(value.charAt(0))) {
            value="$"+value;
        }
        P.put(key, value);
    }

    public static synchronized int getParameter(final String key, final int def) {
        try {
            return Integer.parseInt(getParameter(key, ""));
        } catch (final Exception e) {
            try {
                final double x=new Double(getParameter(key, "")).doubleValue();
                return (int) x;
            } catch (final Exception ex) {
            }
            return def;
        }
    }

    public static synchronized void setParameter(final String key,
            final int value) {
        setParameter(key, ""+value);
    }

    public static synchronized double getParameter(final String key,
            final double def) {
        try {
            return new Double(getParameter(key, "")).doubleValue();
        } catch (final Exception e) {
            return def;
        }
    }

    public static synchronized void setParameter(final String key,
            final double value) {
        setParameter(key, ""+value);
    }

    static public synchronized Color getParameter(final String key,
            final Color c) {
        final String s=getParameter(key, "");
        if (s.equals("")) {
            return c;
        }
        final StringParser p=new StringParser(s);
        p.replace(',', ' ');
        int red, green, blue;
        red=p.parseint();
        green=p.parseint();
        blue=p.parseint();
        try {
            return new Color(red, green, blue);
        } catch (final RuntimeException e) {
            return c;
        }
    }
    static public synchronized ExpressionColor getParameter(final String key,
            final ExpressionColor c, ConstructionObject o) {
        final String s=getParameter(key, "");
        if (s.equals("")) {
            return c;
        }
        final StringParser p=new StringParser(s);
        p.replace(',', ' ');
        int red, green, blue;
        red=p.parseint();
        green=p.parseint();
        blue=p.parseint();
        try {
            ExpressionColor exp=new ExpressionColor(o.getConstruction(), o);
            exp.setColor(red, green, blue);
            return exp;
        } catch (final RuntimeException e) {
            return c;
        }
    }

    static public synchronized Color getParameter(final String key, int red,
            int green, int blue) {
        final String s=getParameter(key, "");
        if (s.equals("")) {
            return new Color(red, green, blue);
        }
        final StringParser p=new StringParser(s);
        p.replace(',', ' ');
        red=p.parseint();
        green=p.parseint();
        blue=p.parseint();
        try {
            return new Color(red, green, blue);
        } catch (final RuntimeException e) {
            return Color.black;
        }
    }

    public static synchronized void setParameter(final String key, final Color c) {
        if (c!=null) {
            setParameter(key, ""+c.getRed()+","+c.getGreen()+","+c.getBlue());
        } else {
            P.remove((Object) key);
        }
    }

    /**
     * Remove a specific Paramater.
     */
    public static synchronized void removeParameter(final String key) {
        P.remove((Object) key);
    }

    /**
     * Remove all Parameters that start with the string.
     */
    public static synchronized void removeAllParameters(final String start) {
        final Enumeration e=P.keys();
        while (e.hasMoreElements()) {
            final String key=(String) e.nextElement();
            if (key.startsWith(start)) {
                P.remove((Object) key);
            }
        }
    }

    /**
     * Set default values for parameters resetDefaults("default.") is the same
     * as: setParameter("xxx",getParameter("default.xxx","")); if "default.xxx"
     * has a value.
     *
     * @param defaults
     */
    public static synchronized void resetDefaults(final String defaults) {
        final Enumeration e=P.keys();
        while (e.hasMoreElements()) {
            final String key=(String) e.nextElement();
            if (key.startsWith(defaults)) {
                setParameter(key.substring(defaults.length()), getParameter(
                        key, ""));
            }
        }
    }

    public static void resetDefaults() {
        resetDefaults("default.");
    }

    /**
     * @return if I have such a parameter.
     */
    public static synchronized boolean haveParameter(final String key) {
        try {
            final String res=P.getProperty(key);
            if (res==null) {
                return false;
            }
        } catch (final Exception e) {
            return false;
        }
        return true;
    }
    // Warnings
    static Frame F=null;

    public static void warning(final String s) {
        if (F==null) {
            F=new Frame();
        }
        final Warning W=new Warning(F, s, name("warning"), false);
        W.center();
        W.setVisible(true);
        // W.show();
    }

    static public boolean isApplet() {
        return pipe_tools.isApplet();
    }

    // Java Version
    public static double getJavaVersion() {
        final String version=System.getProperty("java.version");
        if (version==null) {
            return 0.0;
        }
        double v=0.0;
        final StringTokenizer t=new StringTokenizer(version, ".");
        if (t.hasMoreTokens()) {
            v=convert(t.nextToken());
        } else {
            return v;
        }
        if (t.hasMoreTokens()) {
            v=v+convert(t.nextToken())/10;
        } else {
            return v;
        }
        if (t.hasMoreTokens()) {
            v=v+convert(t.nextToken())/100;
        }
        return v;
    }

    public static double convert(final String s) {
        try {
            return Integer.parseInt(s);
        } catch (final Exception e) {
            return 0;
        }
    }

    public static synchronized String getUserDir() {
        final String dir=System.getProperty("user.dir");
        return FileName.canonical(dir);
    }
    public static Object ExitBlock=new Object();

    public static synchronized void exit(final int i) {
        synchronized (ExitBlock) {
            System.exit(i);
        }
    }

    static public String getCDPLocaleNumber(final double x, final int prec) {
        String s=String.format("%1."+prec+"f", x);
        // add a space if number is positive :
        if (!s.startsWith("-")) {
            s=" "+s;
        }
        return s;
    }

    static public String getLocaleNumber(final double x, final int prec) {
        String s=String.format("%1."+prec+"f", x);
        // skip the last 0s of the number
        s=s.replaceAll("(,+|\\.+)0+$", "");
        s=s.replaceAll("(,+|\\.+)([0-9]*[1-9]+)0+$", "$1$2");
        return s;
    }

    static public String getLocaleNumber(final double x, final String type) {
        return getLocaleNumber(x, getParameter("digits."+type, 5));
    }

    /**
     * Check if the current Country uses the comma as decimal separator This
     * static method is used for inputs in the properties bar
     *
     * @return
     */
    public static boolean isDecimalWithComma() {
        return String.format("%1.1f", 2.2).contains(",");
    }
    static public ArrayList<String> StartupFiles=new ArrayList();

    public static void openHomeDirectoryInDesktop() {
        if (OS.isUnix()) {
            try {
                Runtime.getRuntime().exec("xdg-open "+getHomeDirectory());
                return;
            } catch (Exception ex) {
            }
        }
        try {
            Desktop.getDesktop().open(new File(getHomeDirectory()));
        } catch (Exception ex) {
        }
    }

    public static String getHomeDirectory() {
        final String SP=System.getProperty("file.separator");
        return FileSystemView.getFileSystemView().getDefaultDirectory()+SP+CONFIG+SP;
    }

    static public void deleteDirectory(File path) {
    if( path.exists() ) {
      File[] files = path.listFiles();
      for(int i=0; i<files.length; i++) {
         if(files[i].isDirectory()) {
           deleteDirectory(files[i]);
           files[i].delete();
         }
         else {
           files[i].delete();
         }
      }
      path.delete();
    }
  }

    // Old versions of carmetal ( <3.5 ) used to create a visible carmetal_config
    // folder in the home directory. This method just rename this folder to an
    // invisible ".carmetal_config".
    public static void renameOldHomeDirectory() {
        final String SP=System.getProperty("file.separator");
        final String oldname=FileSystemView.getFileSystemView().getDefaultDirectory()+SP+"carmetal_config";
        final String newname=FileSystemView.getFileSystemView().getDefaultDirectory()+SP+CONFIG;
        File newDir=new File(newname);
        File oldDir=new File(oldname);
        if (oldDir.exists()) {
            deleteDirectory(newDir);
            oldDir.renameTo(new File(newname));
            makeWindowConfigFolderInvisible();
        }
    }

    // On windows plateform, dot character "." doesn't mean to hide a file
    // we need to set the hidden flag for the carmetal_config folder :
    public static void makeWindowConfigFolderInvisible() {
        if (OS.isWindows()) {
            final String SP=System.getProperty("file.separator");
            final String newname=FileSystemView.getFileSystemView().getDefaultDirectory()+SP+CONFIG;
            String comand="C:\\WINDOWS\\System32\\ATTRIB.EXE +H "+newname;
            try {
                Runtime.getRuntime().exec(comand);
            } catch (IOException ex) {
            }
        }
    }

    static public boolean isLanguage(final String lang, final String country) {
        final String lng=Global.getParameter("language", Locale.getDefault().toString().substring(0, 2));
        final String cnt=Global.getParameter("country", Locale.getDefault().getCountry().toString());
        boolean rep=(lang.equals(lng));
        rep=((rep)&&(country.equals(cnt)));
        return rep;
    }

    static public URL getPath(final String s) {
        URL myPath=null;
        try {
            myPath=JZirkelCanvas.class.getResource("/"+s);
        } catch (final Exception e) {
        }
        return myPath;
    }

    public static void initObjectsProperties() {
        setParameter("options.segment.color", getParameter(
                "options.segment.color", 1));
        setParameter("options.segment.colortype", getParameter(
                "options.segment.colortype", 0));
        setParameter("options.segment.shownames", getParameter(
                "options.segment.shownames", false));
        setParameter("options.segment.showvalues", getParameter(
                "options.segment.showvalues", false));
        setParameter("options.segment.large", getParameter(
                "options.segment.large", false));
        setParameter("options.segment.bold", getParameter(
                "options.segment.bold", false));
        setParameter("options.line.color", getParameter(
                "options.line.color", 3));
        setParameter("options.line.colortype", getParameter(
                "options.line.colortype", 0));
        setParameter("options.line.shownames", getParameter(
                "options.line.shownames", false));
        setParameter("options.line.showvalues", false);
        setParameter("options.line.large", getParameter(
                "options.line.large", false));
        setParameter("options.line.bold", getParameter(
                "options.line.bold", false));
        setParameter("options.point.color", getParameter(
                "options.point.color", 2));
        setParameter("options.point.colortype", getParameter(
                "options.point.colortype", 0));
        setParameter("options.point.shownames", getParameter(
                "options.point.shownames", false));
        setParameter("options.point.large", getParameter(
                "options.point.large", false));
        setParameter("options.point.bold", getParameter(
                "options.point.bold", false));
        setParameter("options.point.showvalues", getParameter(
                "options.point.showvalues", false));
        setParameter("options.circle.color", getParameter(
                "options.circle.color", 4));
        setParameter("options.circle.colortype", getParameter(
                "options.circle.colortype", 0));
        setParameter("options.circle.shownames", getParameter(
                "options.circle.shownames", false));
        setParameter("options.circle.showvalues", getParameter(
                "options.circle.showvalues", false));
        setParameter("options.circle.filled", getParameter(
                "options.circle.filled", false));
        setParameter("options.circle.solid", getParameter(
                "options.circle.solid", false));
        setParameter("options.circle.large", getParameter(
                "options.circle.large", false));
        setParameter("options.circle.bold", getParameter(
                "options.circle.bold", false));
        setParameter("options.angle.color", getParameter(
                "options.angle.color", 1));
        setParameter("options.angle.colortype", getParameter(
                "options.angle.colortype", 0));
        setParameter("options.angle.shownames", getParameter(
                "options.angle.shownames", false));
        setParameter("options.angle.showvalues", getParameter(
                "options.angle.showvalues", true));
        setParameter("options.angle.filled", getParameter(
                "options.angle.filled", true));
        setParameter("options.angle.solid", getParameter(
                "options.angle.solid", false));
        setParameter("options.angle.large", getParameter(
                "options.angle.large", false));
        setParameter("options.angle.bold", getParameter(
                "options.angle.bold", false));
        setParameter("options.angle.obtuse", getParameter(
                "options.angle.obtuse", false));
        setParameter("options.area.color", getParameter(
                "options.area.color", 1));
        setParameter("options.area.colortype", getParameter(
                "options.area.colortype", 2));
        setParameter("options.area.shownames", getParameter(
                "options.area.shownames", false));
        setParameter("options.area.showvalues", getParameter(
                "options.area.showvalues", false));
        setParameter("options.area.filled", getParameter(
                "options.area.filled", true));
        setParameter("options.area.solid", getParameter(
                "options.area.solid", false));
        setParameter("options.text.color", getParameter(
                "options.text.color", 1));
        setParameter("options.text.colortype", getParameter(
                "options.text.colortype", 1));
        setParameter("options.text.shownames", getParameter(
                "options.text.shownames", true));
        setParameter("options.text.showvalues", getParameter(
                "options.text.showvalues", true));
        setParameter("options.locus.color", getParameter(
                "options.locus.color", 1));
        setParameter("options.locus.colortype", getParameter(
                "options.locus.colortype", 0));
        setParameter("options.locus.shownames", getParameter(
                "options.locus.shownames", false));
        setParameter("options.locus.showvalues", getParameter(
                "options.locus.showvalues", false));
    }
    static public boolean isNewVersion=false;

    static public boolean isNewVersion() {
        return isNewVersion;
    }

    public static void initProperties() {
        if (!getParameter("program.version", "").equals(
                name("program.version"))) {
            setParameter("program.newversion", true);
            setParameter("program.version", name("program.version"));
            setParameter("icons", ZirkelFrame.DefaultIcons);
            setParameter("selectionsize", 8);
            isNewVersion=true;
        }
        setParameter("comment", getParameter("comment", false));
        setParameter("jsdumb", getParameter("jsdumb", true));
        setParameter("iconpath", "/rene/zirkel/newicons/");
        setParameter("icontype", "png");
        if (getParameter("options.smallicons", false)) {
            setParameter("iconsize", 24);
        } else {
            setParameter("iconsize", 32);
        }
        setParameter("save.includemacros", true);
        setParameter("load.clearmacros", false);
        setParameter("options.backups", false);
        setParameter("options.visual", true);
        setParameter("options.filedialog", false);
        setParameter("options.restricted", true);
        setParameter("options.smallicons", false);
        setParameter("options.indicate", true);
        setParameter("restricted", false);
        setParameter("showgrid", false);
        setParameter("simplegraphics", false);
        setParameter("quality", true);
        setParameter("export.jar", "CaRMetal.jar");
        setParameter("iconpath", "/eric/GUI/icons/palette/");
        setParameter("background.tile", getParameter(
                "background.tile", false));
        if (!haveParameter("options.germanpoints")&&Locale.getDefault().getLanguage().equals("de")) {
            setParameter("options.germanpoints", true);
        }
        setPaletteZonePref(Loc("palette.file"));
        setPaletteZonePref(Loc("palette.aspect"));
        setPaletteZonePref(Loc("palette.function"));
        setPaletteZonePref(Loc("palette.test"));
        setPaletteZonePref(Loc("palette.controls"));
        setPaletteZonePref(Loc("palette.grid"));
        setPaletteZonePref(Loc("palette.history"));
        setPaletteZonePref(Loc("palette.colors"));
        setPaletteZonePref(Loc("palette.sizes"));
        setPaletteZonePref(Loc("palette.prec"));
        
        SliderSnap.init();
        initObjectsProperties();

    }

    private static void setPaletteZonePref(String name){
        setParameter("hidepalette."+name,getParameter("hidepalette."+name, true));
    }


    static double VARS[]={1, 2, 3, 4};

    public static String Comma_To_Point(final String mynum,
            final Construction C, final boolean check) {
        if (C==null) {
            return mynum;
        }
        String s=mynum.replace(",", ".");
        s=s.replace(";", ",");
        if (check) {
            try {
                final UserFunctionObject V_FONC=new UserFunctionObject(C);
                V_FONC.setExpressions("x y z t", s.replace("invalid", "1"));
                V_FONC.evaluateF(VARS);
            } catch (final Exception ex) {
                return mynum;
            }
        }
        if (isDecimalWithComma()) {
            return s;
        } else {
            return mynum;
        }
    }


    static public String AppPath() {
        String mypath=System.getProperty("java.class.path");

        mypath=mypath.split(System.getProperty("path.separator"))[0];
        final String sep=System.getProperty("file.separator");

        while (!(mypath.endsWith(sep))) {
            mypath=mypath.substring(0, mypath.length()-1);
        }
        return mypath;
    }

    /**
     * This function will copy files or directories from one location to
     * another. note that the source and the destination must be mutually
     * exclusive. This function can not be used to copy a directory to a sub
     * directory of itself. The function will also have problems if the
     * destination files already exist.
     *
     * @param src
     *            -- A File object that represents the source for the copy
     * @param dest
     *            -- A File object that represnts the destination for the copy.
     * @throws IOException
     *             if unable to copy.
     */
    public static void copyFiles(final File src, final File dest)
            throws IOException {
        // Check to ensure that the source is valid...
        if (!src.exists()) {
            throw new IOException("copyFiles: Can not find source: "+src.getAbsolutePath()+".");
        } else if (!src.canRead()) { // check to ensure we have rights to the
            // source...
            throw new IOException("copyFiles: No right to source: "+src.getAbsolutePath()+".");
        }
        // is this a directory copy?
        if (src.isDirectory()) {
            if (!dest.exists()) { // does the destination already exist?
                // if not we need to make it exist if possible (note this is
                // mkdirs not mkdir)
                if (!dest.mkdirs()) {
                    throw new IOException(
                            "copyFiles: Could not create direcotry: "+dest.getAbsolutePath()+".");
                }
            }
            // get a listing of files...
            final String list[]=src.list();
            // copy all the files in the list.
            for (final String element : list) {
                final File dest1=new File(dest, element);
                final File src1=new File(src, element);
                copyFiles(src1, dest1);
            }
        } else {
            // This was not a directory, so lets just copy the file
            FileInputStream fin=null;

            FileOutputStream fout=null;
            final byte[] buffer=new byte[4096]; // Buffer 4K at a time (you
            // can change this).
            int bytesRead;
            try {
                // open the files for input and output
                fin=new FileInputStream(src);
                fout=new FileOutputStream(dest);
                // while bytesRead indicates a successful read, lets write...
                while ((bytesRead=fin.read(buffer))>=0) {
                    fout.write(buffer, 0, bytesRead);
                }
            } catch (final IOException e) { // Error copying file...
                final IOException wrapper=new IOException(
                        "copyFiles: Unable to copy file: "+src.getAbsolutePath()+"to"+dest.getAbsolutePath()+".");
                wrapper.initCause(e);
                wrapper.setStackTrace(e.getStackTrace());
                throw wrapper;
            } finally { // Ensure that the files are closed (if they were open).
                if (fin!=null) {
                    fin.close();
                }
                if (fout!=null) {
                    fin.close();
                }
            }
        }
    }

    public static void copyFile(final String inFile, final String outFile) {
        FileChannel in=null;
        FileChannel out=null;
        try {
            in=new FileInputStream(inFile).getChannel();
            out=new FileOutputStream(outFile).getChannel();

            in.transferTo(0, in.size(), out);
        } catch (final Exception e) {
        } finally {
            if (in!=null) {
                try {
                    in.close();
                } catch (final Exception e) {
                }
            }
            if (out!=null) {
                try {
                    out.close();
                } catch (final Exception e) {
                }
            }
        }
    }

    public static String getOpenSaveDirectory() {
        return getParameter("opensavedirectory", "");
    }

    public static void setOpenSaveDirectory(String s) {
        setParameter("opensavedirectory", s);
    }
    static private Rectangle SCREEN=new Rectangle();

    public static Rectangle getScreen() {
        return (Rectangle) SCREEN.clone();
    }

    public static int getScreenX() {
        return SCREEN.x;
    }

    public static int getScreenY() {
        return SCREEN.y;
    }

    public static int getScreenW() {
        return SCREEN.width;
    }

    public static int getScreenH() {
        return SCREEN.height;
    }

    public static void DetectDesktopSize() {
        if (OS.isUnix()) {
//             Very dirty trick to solve a very dirty bug on linux java :
            final JFrame myframe=new JFrame();
            myframe.setUndecorated(true);
            myframe.pack();
            final int s=JFrame.ICONIFIED;
            myframe.setExtendedState(s);
            myframe.setVisible(true);
            myframe.setExtendedState(JFrame.MAXIMIZED_BOTH);
            while (myframe.getExtendedState()==s) {
            }
            try {
                SwingUtilities.invokeAndWait(new Runnable() {

                    public void run() {
                        SCREEN.x=myframe.getBounds().x+5;
                        SCREEN.y=myframe.getBounds().y+5;
                        SCREEN.width=myframe.getBounds().width-10;
                        SCREEN.height=myframe.getBounds().height-10;
                        if (SCREEN.x<0) {
                            SCREEN.x=0;
                        }
                        myframe.dispose();
                    }
                });
            } catch (Exception ex) {
            }
        } else {
            // this is much better on mac and windows :
            GraphicsEnvironment GE=GraphicsEnvironment.getLocalGraphicsEnvironment();
            SCREEN=GE.getMaximumWindowBounds();
        }
    }

    public static void setLanguage(final String lang, final String country) {
        try {
            changeGlobalFont(lang);
            Global.setParameter("language", lang);
            Global.setParameter("country", country);
            initBundles();
            pipe_tools.getContent().rebuiltRightPanel();
            MenuBar.reloadMenuBar();
//
//            JGlobals.CreatePopertiesBar();
//            this.Content.remove(GeneralMenuBar);
//            GeneralMenuBar=new JGeneralMenuBar(ZF, this);
//            this.Content.add(GeneralMenuBar, 1);
//
//            this.ZContent.refreshlanguage();
//
//            JPM.dispose();
//            JPM=null;
//            JPM=new JPaletteManager(ZF, this, IconSize());
//            GeneralMenuBar.InitObjectsMenu();
//            this.Content.revalidate();
//            this.Content.repaint();
//            this.ResizeAll();
//            // this.pack();
//            ZContent.macros.myJML.initMacrosTree();
//            JPM.MainPalette.setVisible(true);
        } catch (final Exception ex) {
            // There were no unicode font for this language :
            final int rep=JOptionPane.showConfirmDialog(null,
                    "Sorry, but the requested font is not installed"+" for this language.\n"+"This language will not be selected.\n\n"+"Do you want to download the necessary font ?",
                    "Font not installed", JOptionPane.YES_NO_OPTION);
            if (rep==JOptionPane.OK_OPTION) {
                JBrowserLauncher.openURL(FontURL);
            }
        }
    }

    static public void changeGlobalFont(final String lang) throws Exception {
        GlobalFont="Dialog";
        final Font myfont=new Font(GlobalFont, 0, 12);
        if (lang.equals("zh")) {
            final String chinesesample="\u4e00";
            // if the standard font can't display chinese caracters :
            if (!(myfont.canDisplayUpTo(chinesesample)==-1)) {
                // if the chinese unicode font is installed :
                if (isFontInstalled("AR PL New Sung")) {
                    GlobalFont="AR PL New Sung";
                } else {
                    FontURL=ChineseFontURL;
                    throw new Exception();
                }
            }
        }
    }

    static public boolean isFontInstalled(final String myfont) {
        final Font UF=new Font("this is a weird unknown font", 0, 12);
        final Font CF=new Font(myfont, 0, 12);
        final boolean notInstalled=(UF.getFontName().equals(CF.getFontName()));
        return (!notInstalled);
    }
}
