/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.GUI.window;

import eric.GUI.pipe_tools;
import eric.GUI.windowComponent;
import eric.GUI.themes;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.Rectangle2D;
import rene.gui.Global;

/**
 *
 * @author erichake
 */
public class TitleBar extends windowComponent {
    private Point origin;
    private Point current;
    private Point winloc;
    private Font TextFont=new Font("System", Font.PLAIN, 13);
    private Color TextColor=Color.DARK_GRAY;
    private String title;

    public void paintComponent(Graphics g) {
        Dimension d=getSize();
        Graphics2D g2d=(Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g2d.drawImage(themes.getImage("titlebar.gif"), 0, 0, d.width, d.height,
                this);
        g2d.setFont(TextFont);
        FontRenderContext frc=g2d.getFontRenderContext();
        Rectangle2D bounds=TextFont.getStringBounds(title, frc);
        LineMetrics metrics=TextFont.getLineMetrics(title, frc);
        float width=(float) bounds.getWidth();     // The width of our text
        float lineheight=metrics.getHeight();      // Total line height
        float ascent=metrics.getAscent();          // Top of text to baseline
        g2d.setColor(TextColor);
        g2d.drawString(title, (pipe_tools.getWindowSize().width-themes.getTotalRightPanelWidth()-width)/2, lineheight+3);
    }

    public void init() {
//        win=StaticTools.getMainWindow(this);
        setBounds(0, 0, pipe_tools.getWindowSize().width-themes.getTotalRightPanelWidth(), themes.getTitleBarHeight());
    }

    public TitleBar() {
        title=Global.Loc("program.name");
    }

    public void mousePressed(MouseEvent e) {
        origin=MouseInfo.getPointerInfo().getLocation();
        winloc=pipe_tools.getWindowLocation();
    }

    public void mouseDragged(MouseEvent arg0) {
        current=MouseInfo.getPointerInfo().getLocation();
        pipe_tools.setWindowLocation(winloc.x+current.x-origin.x, winloc.y+current.y-origin.y);
//        pipe_tools.setWindowComponents();
    }

    void setTitle(String s){
        title=s;
        getParent().repaint();
    }
}
