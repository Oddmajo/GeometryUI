/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.GUI.window;

import eric.GUI.windowComponent;
import eric.GUI.themes;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.image.FilteredImageSource;
import javax.swing.GrayFilter;
import javax.swing.ImageIcon;

/**
 *
 * @author erichake
 */
public class nav_right extends windowComponent {

    private static int marginW=24;
    private static int W=themes.getIcon("navright.png").getIconWidth();
    private static int H=themes.getIcon("navright.png").getIconHeight();
    private boolean over=false;
    private boolean active=false;
    private static boolean disable=false;
    private static nav_right myself=null;

    @Override
    public void paintComponent(Graphics g) {
        Dimension d=getSize();
        if (disable) {
            final GrayFilter filter=new GrayFilter(true, 40);
            Image disImage=createImage(new FilteredImageSource(themes.getImage("navright.png").getSource(), filter));
            ImageIcon myicn=new ImageIcon(disImage);
            g.drawImage(myicn.getImage(), 0, 0, d.width, d.height, this);
            return;
        }
        if (active) {
            g.drawImage(themes.getImage("navrightpushed.png"), 0, 0, d.width, d.height,
                    this);
        } else {
            g.drawImage(themes.getImage("navright.png"), 0, 0, d.width, d.height,
                    this);
        }

    }

    public void init() {
        setBounds(marginW+tab_control_panel.getMarginLeft(),
                tab_control_panel.getMarginTop(),
                W,
                H);
    }

    public nav_right() {
        myself=this;
    }

    public static void setDisabled(boolean b) {
        disable=b;
        if (myself!=null) {
            myself.repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        active=true;
        repaint();
        tab_main_panel.setNextActiveBtn(1);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        active=false;
        repaint();
    }
}
