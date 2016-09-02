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
import java.awt.Image;
import rene.gui.Global;

/**
 *
 * @author erichake
 */
public class LeftPanel extends windowComponent {

    private static LeftPanel me;
    private static int factorySize=250;
    private static int actualSize=0; //width of this panel
    private static final int marginW=5, marginH=4, space=2;
    private static int H=themes.getIcon("leftpanelbackground.gif").getIconHeight();
    private static Image on_btn=themes.getImage("leftpanel_on_btn.gif");
    private static Image off_btn=themes.getImage("leftpanel_off_btn.gif");
    private static Dimension btn_dim=new Dimension(themes.getIcon("leftpanel_on_btn.gif").getIconWidth(), themes.getIcon("leftpanel_on_btn.gif").getIconHeight());

    private static LeftPanel_macros_btn macros_btn=new LeftPanel_macros_btn();
    private static LeftPanel_history_btn history_btn=new LeftPanel_history_btn();
    private static LeftPanel_help_btn help_btn=new LeftPanel_help_btn();
    private static LeftPanel_scripts_btn scripts_btn=new LeftPanel_scripts_btn();

    private static LeftPanel_close_btn close_btn=new LeftPanel_close_btn();

    public void paintComponent(Graphics g) {
        Dimension d=getSize();
        g.setColor(Color.white);
        g.fillRect(0, 0, d.width, d.height);
        g.drawImage(themes.getImage("leftpanelbackground.gif"), 0, 0, d.width, H, this);
//        super.paintComponent(g);
    }

    public static Image getOnBtn() {
        return on_btn;
    }

    public static Image getOffBtn() {
        return off_btn;
    }

    public static Dimension getBtnDim() {
        return btn_dim;
    }

    public static void selectHelp(){
        select(help_btn);
    }

    public static void select(LeftPanel_btn btn) {
        macros_btn.select(macros_btn.equals(btn));
        history_btn.select(history_btn.equals(btn));
        help_btn.select(help_btn.equals(btn));
	scripts_btn.select(scripts_btn.equals(btn));
        me.repaint();
        pipe_tools.actualiseLeftPanels();
    }

    public static int x(LeftPanel_btn btn) {
        int macr=marginW;
        int hist=macr+((macros_btn.isVisible())?space+btn_dim.width:0);
        int help=hist+((history_btn.isVisible())?space+btn_dim.width:0);
	int scripts=help+((scripts_btn.isVisible())?space+btn_dim.width:0);
        if (macros_btn.equals(btn)) {
            return macr;
        } else if (history_btn.equals(btn)) {
            return hist;
        } else if (help_btn.equals(btn)) {
            return help;
        } else if(scripts_btn.equals(btn)){
	    return scripts;
	}
        return 0;
    }

    public static int y() {
        return marginH;
    }

    public static void setPanelWidth(int size) {
        actualSize=size;
    }

    public static int getPanelWidth() {
        return actualSize;
    }

    public static int getPanelHeight() {
        return H;
    }

    public static boolean isPanelVisible() {
        return (actualSize!=0);
    }

    public static void setMacroBtnVisible(boolean b){
        macros_btn.setVisible(b);
        LeftPanel_content.setPanelVisibility();
        me.init();
    }
    public static void setHistoryBtnVisible(boolean b){
        history_btn.setVisible(b);
        LeftPanel_content.setPanelVisibility();
        me.init();
    }
    public static void setHelpBtnVisible(boolean b){
        help_btn.setVisible(b);
        LeftPanel_content.setPanelVisibility();
        me.init();
    }
    public static void setScriptsBtnVisible(boolean b){
	scripts_btn.setVisible(b);
	LeftPanel_content.setPanelVisibility();
	me.init();
    }
    public static boolean isMacroBtnVisible(){
        return macros_btn.isVisible();
    }
    public static boolean isHistoryBtnVisible(){
        return history_btn.isVisible();
    }
    public static boolean isHelpBtnVisible(){
        return help_btn.isVisible();
    }
    public static boolean isScriptsBtnVisible(){
	return scripts_btn.isVisible();
    }

    public static boolean isHistoryPanelVisible() {
        return ((isPanelVisible())&&(history_btn.isPanelSelected()));
    }
    public static boolean isHelpPanelVisible() {
        return ((isPanelVisible())&&(help_btn.isPanelSelected()));
    }
    public static boolean isMacroPanelVisible() {
        return ((isPanelVisible())&&(macros_btn.isPanelSelected()));
    }
    public static boolean isScriptsPanelVisible(){
	return ((isPanelVisible())&&(scripts_btn.isPanelSelected()));
    }

    public static int getFactorySize(){
        return factorySize;
    }

    public static void setFactorySize(int size){
        factorySize=size;
        actualSize=size;
        pipe_tools.setWindowComponents();

    }

    public static void showPanel(boolean vis) {
        int w=(vis)?Global.getParameter("leftpanelwidth", factorySize):0;
        setPanelWidth(w);
        pipe_tools.setWindowComponents();
        pipe_tools.actualiseLeftPanels();
    }

    public void init() {
        setBounds(themes.getVerticalBorderWidth(),
                themes.getTitleBarHeight()+themes.getMenuBarHeight()+themes.getCommentsHeight(),
                actualSize,
                H);
        macros_btn.init();
        history_btn.init();
        help_btn.init();
	scripts_btn.init();
        close_btn.init();
    }

    public LeftPanel() {
        super();
        me=this;
        setOpaque(true);
        setLayout(null);
        add(macros_btn);
        add(history_btn);
        add(help_btn);
	add(scripts_btn);
        add(close_btn);
    }
}