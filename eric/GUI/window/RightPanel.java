/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.GUI.window;

import eric.GUI.palette.PaletteManager;
import eric.GUI.pipe_tools;
import eric.GUI.windowComponent;
import eric.GUI.themes;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.BoxLayout;
import rene.gui.Global;

/**
 *
 * @author erichake
 */
public class RightPanel extends windowComponent {

    private static int VerticalSeparator=1;
    private static Image back=themes.getImage("rightback.png");
    private static RightPanel me;
//    private static int actualSize; //width of this panel

    public static boolean isPanelVisible() {
        return (me.getSize().width!=0);
    }

    public static int getPanelWidth() {
        return me.getSize().width;
    }

    public static int getSeparatorWidth() {
        if (isPanelVisible()) {
            return VerticalSeparator;
        } else {
            return 0;
        }
    }

    public static void setPanelWidth(int size) {
        Dimension d=new Dimension(size, pipe_tools.getWindowSize().height);
        me.setPreferredSize(d);
        me.setMaximumSize(d);
        me.setMinimumSize(d);
        me.setSize(d);
    }

    public static void showPanel(boolean vis) {
        int w=(vis)?(themes.getPaletteIconPerRow()*themes.getPaletteIconWidth()):0;

//        if ((w>0)&&(Global.getScreenW()>=pipe_tools.getWindowSize().width+w)) {
//            pipe_tools.setWindowSize(pipe_tools.getWindowSize().width+w+VerticalSeparator, pipe_tools.getWindowSize().height);
//        }
	if(w>0){
	    if(Global.getScreenW()>=pipe_tools.getWindowSize().width+w){
		pipe_tools.setWindowSize(pipe_tools.getWindowSize().width+w+VerticalSeparator, pipe_tools.getWindowSize().height);
	    } else {
		pipe_tools.setWindowSize(Global.getScreenW()+VerticalSeparator, pipe_tools.getWindowSize().height);
	    }
	}
        if (w==0) {
            pipe_tools.setWindowSize(pipe_tools.getWindowSize().width-getPanelWidth()-VerticalSeparator, pipe_tools.getWindowSize().height);
        }
        setPanelWidth(w);
        me.setBounds(pipe_tools.getWindowSize().width-getPanelWidth(), 0, getPanelWidth(), pipe_tools.getWindowSize().height);
	pipe_tools.setWindowComponents();
    }

    @Override
    public void paintComponent(Graphics g) {
        Dimension d=getSize();
        g.drawImage(back, 0, 0, d.width, d.height, this);
    }

    public void init() {
        setBounds(pipe_tools.getWindowSize().width-themes.getRightPanelWidth(),
                0,
                themes.getRightPanelWidth(),
                pipe_tools.getWindowSize().height);
        PaletteManager.init();
    }

    public RightPanel() {
        super();
        me=this;
        setPanelWidth(themes.getPaletteIconPerRow()*themes.getPaletteIconWidth());
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setAlignmentX(0.0f);
        PaletteManager.construct(this);
    }
}
