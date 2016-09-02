/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * NewJZirkelFrame.java
 *
 * Created on 26 oct. 2009, 08:41:46
 */
package eric.GUI.window;

import eric.GUI.pipe_tools;
import eric.GUI.themes;
import eric.OS;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import rene.gui.Global;

/**
 *
 * @author erichake
 */
public class MainWindow extends JFrame implements MainContainer, WindowListener {

    private int WIN_w=1280, WIN_h=800;
    private ContentPane CONTENT=null;

    @Override
    public void repaint() {
    }

    /**
     *
     */
    public MainWindow() {
        super();
        pipe_tools.init(this);
        addWindowListener(this);
        setUndecorated(true);
        setLayout(null);
        themes.init();
        CONTENT=new ContentPane();
        setContentPane(CONTENT);

        if (OS.isUnix()) {
            // A cause du "MToolkit" du lanceur desktop sur Linux, on ouvre
            // la fenêtre en plein écran au démarrage :
            setBounds(Global.getScreenX(), Global.getScreenY(), Global.getScreenW(), Global.getScreenH());
            setSize(Global.getScreenW(), Global.getScreenH());
        } else {
            if (WIN_w>Global.getScreenW()) {
                WIN_w=Global.getScreenW();
            }
            if (WIN_h>Global.getScreenH()) {
                WIN_h=Global.getScreenH();
            }
            int x=(Global.getScreenW()-WIN_w)/2;
            int y=(Global.getScreenH()-WIN_h)/2;
            setBounds(Global.getScreenX()+x, Global.getScreenY()+y, WIN_w, WIN_h);
            setSize(WIN_w, WIN_h);
        }

        setComponents();
        setVisible(true);
    }

    /**
     *
     */
    public void setComponents() {
        CONTENT.setComponents();
    }

    public ContentPane getContent() {
        return CONTENT;
    }

    public Point getMouseLoc() {
        return MouseInfo.getPointerInfo().getLocation();
    }

    public Image getImage(String s) {
        ImageIcon myicon;
        try {
            myicon=new ImageIcon(themes.class.getResource(themes.getCommonThemePath()+s));
        } catch (final Exception e) {
            try {
                myicon=new ImageIcon(themes.class.getResource(themes.getCurrentThemePath()+s));
            } catch (final Exception ex) {
                myicon=new ImageIcon(themes.class.getResource(themes.getCommonThemePath()+"null.gif"));
            }
        }
        return myicon.getImage();
    }

    public Image getPaletteImage(String s) {
        ImageIcon myicon;
        try {
            myicon=new ImageIcon(themes.class.getResource(themes.getPalettePath()+s+".png"));
        } catch (final Exception e) {
            try {
                myicon=new ImageIcon(themes.class.getResource(themes.getPalettePath()+s+".gif"));
            } catch (final Exception ex) {
                try {
                    myicon=new ImageIcon(themes.class.getResource(themes.getBarPath()+s+".png"));
                } catch (final Exception ex2) {
                    try {
                        myicon=new ImageIcon(themes.class.getResource(themes.getBarPath()+s+".gif"));
                    } catch (final Exception ex3) {
                        myicon=new ImageIcon(themes.class.getResource(themes.getCommonThemePath()+"null.gif"));
                    }
                }
            }
        }
        return myicon.getImage();
    }

    public boolean isApplet() {
        return false;
    }

    public void windowOpened(WindowEvent e) {
    }

    public void windowClosing(WindowEvent e) {
    }

    public void windowClosed(WindowEvent e) {
    }

    public void windowIconified(WindowEvent e) {
    }

    public void windowDeiconified(WindowEvent e) {
    }

    public void windowActivated(WindowEvent e) {
    }

    public void windowDeactivated(WindowEvent e) {
        tab_main_panel.hidePopups();
    }
}
