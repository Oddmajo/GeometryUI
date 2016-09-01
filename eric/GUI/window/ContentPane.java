/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.GUI.window;

import eric.GUI.palette.PaletteManager;
import eric.GUI.pipe_tools;
import eric.GUI.themes;
import eric.JLogoWindow;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JLabel;
import eric.JEricPanel;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;

/**
 *
 * @author erichake
 */
public class ContentPane extends JEricPanel {
    private static CanvasPanel CANVASPANEL;
    private LeftPanel LEFTPANEL;
    private LeftPanel_content LEFTPANELCONTENT;
    private RightPanel RIGHTPANEL;
    private ResizeBox RESIZE;
    private TitleBar TITLE;
    private MenuBar MENU;
    private VerticalLeftBorder VERTICALLEFTBORDER;
    private VerticalLeftPanelBorder VERTICALLEFTPANELBORDER;
    private VerticalRightBorder VERTICALRIGHTBORDER;
    private CloseBox CLOSEBOX;
    private ReduceBox REDUCEBOX;
    private GrowBox GROWBOX;
    private comments TABTOPBORDER;
    private tab_left TABLEFTBORDER;
    private tab_right TABRIGHTBORDER;
    private tab_bottom TABBOTTOMBORDER;
    private tab_main_panel TABMAINPANEL;
    private tab_control_panel TABCONTROLPANEL;
    private Open_left_panel_btn OPENLEFTPANEL;
    private Open_right_panel_btn OPENRIGHTPANEL;
    private Open_middle_panel_btn OPENMIDDLEPANEL;
    private Monkey_panel_btn MONKEYBTN;




    public void paintComponent(Graphics g) {
        final java.awt.Dimension d=this.getSize();
        g.setColor(Color.lightGray);
        g.fillRect(0, 0, d.width, d.height);
    }

    public ContentPane() {
        setLayout(null);
        VERTICALLEFTPANELBORDER=new VerticalLeftPanelBorder();
        CANVASPANEL=new CanvasPanel();
        LEFTPANEL=new LeftPanel();
        LEFTPANELCONTENT=new LeftPanel_content();
        RIGHTPANEL=new RightPanel();
        TABTOPBORDER=new comments();
        TABLEFTBORDER=new tab_left();
        TABRIGHTBORDER=new tab_right();
        TABBOTTOMBORDER=new tab_bottom();
        TABMAINPANEL=new tab_main_panel();
        TABCONTROLPANEL=new tab_control_panel();

        // resizebox needs to be in front of every component :
        if (!themes.isApplet()) {
            RESIZE=new ResizeBox();
            add(RESIZE);
        }

        add(CANVASPANEL);

        if (!themes.isApplet()) {
            GROWBOX=new GrowBox();
            REDUCEBOX=new ReduceBox();
            CLOSEBOX=new CloseBox();
            TITLE=new TitleBar();
            MENU=new MenuBar();
            VERTICALLEFTBORDER=new VerticalLeftBorder();
            VERTICALRIGHTBORDER=new VerticalRightBorder();
            OPENLEFTPANEL=new Open_left_panel_btn();
            OPENRIGHTPANEL=new Open_right_panel_btn();
            OPENMIDDLEPANEL=new Open_middle_panel_btn();
            MONKEYBTN=new Monkey_panel_btn();
            add(OPENLEFTPANEL);
            add(OPENRIGHTPANEL);
            add(OPENMIDDLEPANEL);
            add(MONKEYBTN);
            add(GROWBOX);
            add(REDUCEBOX);
            add(CLOSEBOX);
            add(TITLE);
            add(MENU);
            add(VERTICALLEFTBORDER);
            add(VERTICALRIGHTBORDER);
        }
        add(VERTICALLEFTPANELBORDER);
        add(TABCONTROLPANEL);
        add(TABMAINPANEL);
        add(TABLEFTBORDER);
        add(TABRIGHTBORDER);
        add(TABTOPBORDER);
        add(TABBOTTOMBORDER);
        add(LEFTPANEL);
        add(LEFTPANELCONTENT);
        add(RIGHTPANEL);

        ToolTipManager.sharedInstance().setInitialDelay(100);
        ToolTipManager.sharedInstance().setDismissDelay(10000);
        UIManager.put("ToolTip.background", new ColorUIResource(213, 227, 253));
        pipe_tools.setStandardKeyInputs();
    }

    public void rebuiltRightPanel(){
        final String gicon=PaletteManager.geomSelectedIcon();
        int i=getComponentZOrder(RIGHTPANEL);
        remove(RIGHTPANEL);
        RIGHTPANEL=null;
        RIGHTPANEL=new RightPanel();
        add(RIGHTPANEL, i);
        // I know, this is weird, but it work's :
        setComponents();
        setComponents();
        PaletteManager.setSelected_with_clic(gicon,true);
        PaletteManager.initPaletteConsideringMode();
    }

    public void setTitle(String s){
        TITLE.setTitle(s);
    }


    public static void setCurrentPanel(tab_canvas_panel canvas){
        CANVASPANEL.removeAll();
        CANVASPANEL.add(canvas);
        CANVASPANEL.revalidate();
        CANVASPANEL.repaint();
    }

    public static JEricPanel getCanvasPanel(){
        return CANVASPANEL;
    }

    public void setComponents() {
        RIGHTPANEL.init();
        CANVASPANEL.init();
        TABTOPBORDER.init();
        TABLEFTBORDER.init();
        TABRIGHTBORDER.init();
        TABBOTTOMBORDER.init();
        LEFTPANEL.init();
        LEFTPANELCONTENT.init();

        TABMAINPANEL.init();
        TABCONTROLPANEL.init();
        VERTICALLEFTPANELBORDER.init();

        if (!themes.isApplet()) {
            REDUCEBOX.init();
            CLOSEBOX.init();
            GROWBOX.init();
            TITLE.init();
            MENU.init();
            VERTICALLEFTBORDER.init();
            VERTICALRIGHTBORDER.init();
            RESIZE.init();
            OPENLEFTPANEL.init();
            OPENRIGHTPANEL.init();
            OPENMIDDLEPANEL.init();
            MONKEYBTN.init();
        }

    }
}
