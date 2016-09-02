/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.GUI.window;

import eric.GUI.palette.PaletteManager;
import eric.GUI.pipe_tools;
import eric.GUI.windowComponent;
import eric.GUI.themes;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import rene.gui.Global;

/**
 *
 * @author erichake
 */
public class tab_bottom extends windowComponent {

    private Point winloc;
    private static JLabel status=new JLabel();
    private int margin=10;

    public void paintComponent(Graphics g) {
        Dimension d=getSize();
        g.drawImage(themes.getImage("tab_bottom.gif"), 0, 0, d.width, d.height, this);
//                g.setColor(Color.black);
//        g.drawRect(0, Themes.getTabTopBorderHeight(), d.width-1, d.height-1-Themes.getTabTopBorderHeight());
        super.paintComponent(g);
    }

    public void init() {
//        win=StaticTools.getMainWindow(this);
        setBounds(themes.getVerticalBorderWidth()+themes.getLeftPanelWidth()+themes.getVerticalPanelBorderWidth(),
                pipe_tools.getWindowSize().height-themes.getMainTabPanelHeight()-themes.getStatusBarHeight(),
                pipe_tools.getWindowSize().width-2*themes.getVerticalBorderWidth()-themes.getLeftPanelWidth()-themes.getVerticalPanelBorderWidth()-themes.getTotalRightPanelWidth(),
                themes.getStatusBarHeight());
        PaletteManager.fixsize(status, getBounds().width, getBounds().height);
    }

    public tab_bottom() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setOpaque(false);
        status.setForeground(new Color(70, 70, 70));
        status.setFont(new Font(Global.GlobalFont, 0, 13));
        status.setHorizontalAlignment(JLabel.CENTER);
        status.setVerticalAlignment(JLabel.CENTER);
        add(status);
    }

    public static void showStatus(String txt) {
        status.setText(txt);
    }

    public static String getStatus() {
        return status.getText();
    }
}
