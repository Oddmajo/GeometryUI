/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package eric.GUI.window;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import javax.swing.JRootPane;

/**
 *
 * @author erichake
 */
public interface MainContainer{
    public void setComponents();
    public ContentPane getContent();
    public Point getMouseLoc();
    public Image getImage(String s);
    public Image getPaletteImage(String s);
    public boolean isApplet();
    public void setLocation(int x, int y);
    public Point getLocation();
    public void setSize(int w,int h);
    public Dimension getSize();
    public JRootPane getRootPane();
//    public Image createImage(int width, int height);
}
