/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.GUI.window;

import eric.FileTools;
import eric.GUI.pipe_tools;
import eric.GUI.themes;
import eric.JZirkelCanvas;
import eric.Media;
import java.awt.Image;
import java.awt.Point;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JApplet;
import javax.swing.SwingUtilities;
import rene.zirkel.ZirkelCanvas;

/**
 *
 * @author erichake
 */
public class MainApplet extends JApplet implements MainContainer {

    private ContentPane CONTENT;
    private String Source=null;

    public MainApplet() {
    }

    @Override
    public void init() {
        try {
            pipe_tools.init(this);
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    createGUI();
                    openFILES();
                }
            });
        } catch (Exception ex) {
        }

//        JSObject window=JSObject.getWindow(this);
//        String[] message={"An alert message"};
//        window.call("alert", message);

    }

    @Override
    public void start() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                if (Source!=null) {
                    FileTools.setWorkBookSource(Source);
                }else{
                    pipe_tools.AppletLoadedMessage_To_HTML();
                }
            }
        });
    }

    public void createGUI() {
        tab_main_panel.removeAllBtns(CONTENT);
        Media.clearMedias();
        setLayout(null);
        themes.init();
        CONTENT = new ContentPane();
        setContentPane(CONTENT);
    }

    public void openFILES() {
        try {
            Source = getParameter("source");
            final String filename = getParameter("file");
            URL url = new URL(getCodeBase(), filename);
            if (url.getFile().toLowerCase().endsWith(".zirs")) {
                URL myURL = new URL(getCodeBase(), filename);
                FileTools.openWorkBook(url.getFile(), myURL.openStream());
            } else if (url.getFile().toLowerCase().endsWith(".zir")) {
                URL myURL = new URL(getCodeBase(), filename);
                FileTools.openFile(url.getFile(), myURL.openStream(), 0);

            }
        } catch (Exception ex) {
            setComponents();
            
//            try {
//                String src = getParameter("source");
//                src = src.trim();
//                FileTools.setWorkBookSource(src);
//            } catch (Exception ex2) {
//                setComponents();
//            }
        }
    }

    public void setComponents() {
        CONTENT.setComponents();
    }

    public ContentPane getContent() {
        return CONTENT;
    }

    public Point getMouseLoc() {
        return CONTENT.getMousePosition();
    }

    public Image getImage(String s) {
        Image myimage;
        myimage = getImage(themes.class.getResource(themes.getCommonThemePath() + s));
        if (myimage == null) {
            myimage = getImage(themes.class.getResource(themes.getCurrentThemePath() + s));
        }
        if (myimage == null) {
            myimage = getImage(themes.class.getResource(themes.getCommonThemePath() + "null.gif"));
        }
        return myimage;
    }

    public Image getPaletteImage(String s) {
        Image myimage;
        try {
            myimage = getImage(themes.class.getResource(themes.getPalettePath() + s + ".png"));
        } catch (final Exception e) {
            try {
                myimage = getImage(themes.class.getResource(themes.getPalettePath() + s + ".gif"));
            } catch (final Exception ex) {
                try {
                    myimage = getImage(themes.class.getResource(themes.getBarPath() + s + ".png"));
                } catch (final Exception ex2) {
                    try {
                        myimage = getImage(themes.class.getResource(themes.getBarPath() + s + ".gif"));
                    } catch (final Exception ex3) {
                        myimage = getImage(themes.class.getResource(themes.getCommonThemePath() + "null.gif"));
                    }
                }
            }
        }
        return myimage;
    }

    public boolean isApplet() {
        return true;
    }

    @Override
    public void setLocation(int x, int y) {
    }

    @Override
    public Point getLocation() {
        return new Point(0, 0);
    }

    @Override
    public void setSize(int w, int h) {
    }

    /* these methods are made for communication between the applet
     * and the web page.
     */
    public String get() {
        return FileTools.getWorkBookSource();
    }

    public void set(String s) {
        createGUI();
        s = s.trim();
        FileTools.setWorkBookSource(s);
    }

    public void JSsend(final String s) {
        ZirkelCanvas zc = JZirkelCanvas.getCurrentZC();
        zc.JSsend(s);
        zc.repaint();
    }

    public String JSreceive(final String s) {
        ZirkelCanvas zc = JZirkelCanvas.getCurrentZC();
        return zc.JSreceive(s);
    }
}
