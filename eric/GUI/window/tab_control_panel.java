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
import java.awt.Graphics;

/**
 *
 * @author erichake
 */
public class tab_control_panel extends windowComponent {
    private static int MarginLeft=10,MarginTop=8;
    private nav_left NavLeftBtn;
    private nav_right NavRightBtn;
    private nav_menu1 NavMenu1Btn;
    private nav_menu2 NavMenu2Btn;
    private static tab_control_panel myself;

    public void paintComponent(Graphics g) {
        Dimension d=getSize();
        g.drawImage(themes.getImage("tabbar.gif"), 0, 0, d.width, d.height,this);
    }
    
//        		@Override
//		public void paintComponent(final java.awt.Graphics g) {
//		}


    public void init() {
//        win=StaticTools.getMainWindow(this);
        setBounds(pipe_tools.getWindowSize().width-themes.getTabControlPanelWidth()-themes.getTotalRightPanelWidth(),
                pipe_tools.getWindowSize().height-themes.getMainTabPanelHeight(),
                themes.getTabControlPanelWidth(),
                themes.getMainTabPanelHeight());
        NavLeftBtn.init();
        NavRightBtn.init();
        NavMenu1Btn.init();
        NavMenu2Btn.init();
    }

    public tab_control_panel() {
        myself=this;
        setLayout(null);
        NavLeftBtn=new nav_left();
        NavRightBtn=new nav_right();
        NavMenu1Btn=new nav_menu1();
        NavMenu2Btn=new nav_menu2();
        add(NavLeftBtn);
        add(NavRightBtn);
        add(NavMenu1Btn);
        add(NavMenu2Btn);
    }

    public static tab_control_panel getme(){
        return myself;
    }

    public static int getMarginLeft(){
        return MarginLeft;
    }
    public static int getMarginTop(){
        return MarginTop;
    }
}
