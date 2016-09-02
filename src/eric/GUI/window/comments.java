/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.GUI.window;

import eric.GUI.pipe_tools;
import eric.GUI.windowComponent;
import eric.GUI.themes;
import eric.JZirkelCanvas;
import eric.OS;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import javax.swing.BorderFactory;

/**
 *
 * @author erichake
 */
public class comments extends windowComponent {

    private comments_area Label;
    private static comments me;
    private static Image bottom=themes.getImage("tab_top.gif");
    private static int H=themes.getIcon("tab_top.gif").getIconHeight();
    private Point2D start=new Point2D.Float(0, 0);
//    private float[] dist = {0.0f, 0.2f, 0.4f, 0.6f, 0.8f, 1.0f};
//    private Color[] colors = {Color.YELLOW, Color.ORANGE, Color.RED, Color.MAGENTA, Color.BLUE, Color.BLACK};
    private float[] dist={0.0f, 1.0f};
//    private Color[] colors={new Color(213, 232, 255), new Color(193, 212, 235)};
    private Color[] colors={new Color(225, 239, 253), new Color(193, 212, 235)};
//    private Color[] colors={new Color(225, 239, 253), new Color(146, 196, 247)};
    private boolean JavaOlderThan6=OS.isJavaOlderThan(6);

    public void paintComponent(Graphics g) {
        Dimension d=getSize();
        Graphics2D g2d=windowComponent.getGraphics2D(g);
        if (JavaOlderThan6) {
            g2d.setColor(colors[1]);
            g2d.fillRect(0, 0, d.width, d.height);
        } else {

            java.awt.LinearGradientPaint paint=new java.awt.LinearGradientPaint(start, new Point2D.Float(0, d.height), dist, colors);
            g2d.setPaint(paint);
            g2d.fillRect(0, 0, d.width, d.height);
        }
        g2d.setColor(Color.black);
        g2d.drawLine(0, d.height,d.width, d.height);
        super.paintComponent(g);
    }

    public void init() {
        setBounds(themes.getVerticalBorderWidth(),
                themes.getTitleBarHeight()+themes.getMenuBarHeight(),
                pipe_tools.getWindowSize().width-2*themes.getVerticalBorderWidth()-themes.getTotalRightPanelWidth(),
                themes.getCommentsHeight());
        Label.init();
    }

    public comments() {
        me=this;
        setLayout(null);
        Label=new comments_area();
        add(Label);
    }

    public static void refresh() {
        me.init();
        me.validate();
        me.repaint();
    }

    public static void setLabelText(String s) {
        me.Label.setLabelText(s);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Label.edit();
    }
}
