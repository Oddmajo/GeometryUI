/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.GUI.window;

import eric.GUI.pipe_tools;
import eric.GUI.windowComponent;
import eric.GUI.themes;
import eric.JZirkelCanvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;

/**
 *
 * @author erichake
 */
public class ResizeBox extends windowComponent {
    private Point origin;
    private Point current;
    private int winWidth;
    private int winHeight;

    public void paintComponent(Graphics g) {
        Dimension d=this.getSize();
        g.drawImage(themes.getImage("zoombox.png"), 0, 0, d.width, d.height,
                    this);
    }

    public ResizeBox() {

    }

    public void init(){
        this.setBounds(pipe_tools.getWindowSize().width-themes.getResizeBoxWidth()-themes.getTotalRightPanelWidth(),
                pipe_tools.getWindowSize().height-themes.getResizeBoxHeight(),
                themes.getResizeBoxWidth(),
                themes.getResizeBoxHeight());
    }

    public void mouseEntered(MouseEvent e) {
        repaint();
    }



    @Override
    public void mousePressed(MouseEvent e) {
        origin=MouseInfo.getPointerInfo().getLocation();
        winWidth=pipe_tools.getWindowSize().width;
        winHeight=pipe_tools.getWindowSize().height;
    }


    @Override
    public void mouseDragged(MouseEvent arg0) {
        current=MouseInfo.getPointerInfo().getLocation();
        int w=winWidth+current.x-origin.x;
        int h=winHeight+current.y-origin.y;
        Toolkit.getDefaultToolkit().sync();
        pipe_tools.setWindowSize(Math.max(w,210), Math.max(h,70));
        pipe_tools.setWindowComponents();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

}
