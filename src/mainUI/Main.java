package mainUI;



import eric.FileTools;
import eric.GUI.palette.PaletteManager;
import eric.GUI.window.MainApplet;
import eric.JLogoWindow;
import eric.JSprogram.ScriptThread;
import eric.OS;
import eric.macros.MacroTools;
import eric.bar.JPropertiesBar;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Locale;
import java.util.Properties;
import javax.swing.SwingUtilities;
import rene.gui.Global;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author erichake
 */
public class Main extends MainApplet {

    public Main() {
        super();
    }

    public void init() {
        rene.gui.Global.DetectDesktopSize();
        Global.initBundles();

        Global.initProperties();
        eric.JGlobalPreferences.initPreferences();
        ScriptThread.InitContextFactory();
//        Global.FirstRun();
        JPropertiesBar.CreatePropertiesBar();
        super.init();

    }

    public void start() {    
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                PaletteManager.init();
            }
        });
        super.start();
    }

    static public void main(final String[] args) {
        final int PORT=32145;
        int i=0;
        String filename="";


        while (i<args.length) {
            if (args[i].startsWith("-l")&&i<args.length-1) {
                i+=2;
            } else if (args[i].startsWith("-h")&&i<args.length-1) {
                i+=2;
            } else if (args[i].startsWith("-s")) {
                i++;
            } else if (args[i].startsWith("-r")) {
                i++;
            } else if (args[i].startsWith("-d")) {
                i++;
            } else {
                filename+=args[i]+System.getProperty("path.separator");
                i++;
            }
        }

        final String FILES=filename;
//            final eric.JUniqueInstance uniqueInstance=new eric.JUniqueInstance(
//                    PORT, FILES);
//            if (uniqueInstance.launch()) {
//                if (OS.isMac()) {
//                    new eric.JMacOShandler();
//                }
//                mainApplication(args);
//            }

        if (OS.isMac()) {
            // on mac, things are easy :
            new eric.JMacOShandler();
            mainApplication(args);
        }else if (OS.isWindows()) {
//            // on windows, use a socket in order to launch unique instance of app :
//            final eric.JUniqueInstance uniqueInstance=new eric.JUniqueInstance(
//                    PORT, FILES);
//            if (uniqueInstance.launch()) {
//                mainApplication(args);
//            }

            // make things simple without socket, just to see if it's better :
            mainApplication(args);
            
        }else {
            // on linux, make it simple (so, without hability to launch files with double
            // click when app is already open). Linux doesn't like socket things...
            mainApplication(args);
        }


    }

    // zirkel is called as application :
    public static void mainApplication(final String args[]) {
        
        rene.gui.Global.DetectDesktopSize();
        
        int i=0;
        String filename="";
        while (i<args.length) {
            if (args[i].startsWith("-l")&&i<args.length-1) {
                Locale.setDefault(new Locale(args[i+1], ""));
                i+=2;
            } else if (args[i].startsWith("-h")&&i<args.length-1) {
                i+=2;
            } else if (args[i].startsWith("-s")) {
                i++;
            } else if (args[i].startsWith("-r")) {
                i++;
            } else if (args[i].startsWith("-d")) {
                final Properties p=System.getProperties();
                try {
                    final PrintStream out=new PrintStream(
                            new FileOutputStream(p.getProperty("user.home")+p.getProperty("file.separator")+"zirkel.log"));
                    System.setErr(out);
                    System.setOut(out);
                } catch (final Exception e) {
                    System.out.println("Could not open log file!");
                }
                i++;
            } else {
                filename=args[i];
                FileTools.addStartupFile(filename);
                i++;
            }
        }
        Global.renameOldHomeDirectory();
        Global.loadProperties(Global.getHomeDirectory()+"carmetal_config.txt");
        Global.initBundles();
        JLogoWindow.ShowLogoWindow(false);
        Global.setParameter("jsdumb", Global.getParameter("jsdumb", true));
//        JLogoWindow.ShowLogoWindow();
        Global.initProperties();
        MacroTools.createLocalDirectory();
        eric.JGlobalPreferences.initPreferences();
        ScriptThread.InitContextFactory();
        FileTools.FirstRun();
        
    }
}
