/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.GUI.window;

import eric.GUI.pipe_tools;
import eric.GUI.windowComponent;
import eric.GUI.themes;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;

/**
 *
 * @author erichake
 */
public class VerticalLeftPanelBorder extends windowComponent {
    private Point origin;
    private Point current;
    //private boolean mousepressed;

    @Override
    public void paintComponent(Graphics g) {
        Dimension d=getSize();
        g.drawImage(themes.getImage("verticalseparator.png"), 0, 0, d.width, d.height,
                this);
    }

    public void init() {
//	win=StaticTools.getMainWindow(this);
        setBounds(themes.getLeftPanelWidth()+themes.getVerticalBorderWidth(),
                themes.getTitleBarHeight()+themes.getMenuBarHeight()+themes.getCommentsHeight(),
                themes.getVerticalPanelBorderWidth(),
                pipe_tools.getWindowSize().height-themes.getTitleBarHeight()-themes.getMenuBarHeight()-themes.getMainTabPanelHeight()-themes.getCommentsHeight());
    }

    public VerticalLeftPanelBorder() {
    }


     @Override
    public void mouseEntered(MouseEvent e) {
         setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        setCursor(Cursor.getDefaultCursor());
    }

    @Override
    public void mousePressed(MouseEvent e) {
        origin=MouseInfo.getPointerInfo().getLocation();
    }

    @Override
    public void mouseReleased(MouseEvent e){
    }

    @Override
    public void mouseDragged(MouseEvent arg0) {
        current=MouseInfo.getPointerInfo().getLocation();
	int w = LeftPanel.getFactorySize()+current.x-origin.x;
	if(w>=115 && w<=this.getParent().getWidth()/2) {
	    LeftPanel.setFactorySize(w);
	    origin=current;
	}
	//LeftPanel.setFactorySize(LeftPanel.getFactorySize()+current.x-origin.x);
//        LeftPanel.setFactorySize(LeftPanel.getFactorySize()+current.x-origin.x);
//        origin=current;
    }
}
