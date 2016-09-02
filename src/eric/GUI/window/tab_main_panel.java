/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.GUI.window;

import eric.FileTools;
import eric.GUI.palette.PaletteManager;
import eric.GUI.pipe_tools;
import eric.GUI.windowComponent;
import eric.GUI.themes;
import eric.JZirkelCanvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.Collections;
import eric.JEricPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;
import rene.gui.Global;

/**
 *
 * @author erichake
 */
public class tab_main_panel extends windowComponent implements MouseWheelListener {

    private Color foreColor=new Color(0, 0, 0);
    private static myJMenuItem paletteitem, leftpanelitem, commentitem;

    @Override
    public void paintComponent(Graphics g) {
        Dimension d=getSize();
        g.drawImage(themes.getImage("tabbar.gif"), 0, 0, d.width, d.height,
                this);
    }

    public void init() {
//        win=StaticTools.getMainWindow(this);
        int wdth=pipe_tools.getWindowSize().width-themes.getTotalRightPanelWidth()-themes.getTabControlPanelWidth();
        setBounds(0,
                pipe_tools.getWindowSize().height-themes.getMainTabPanelHeight(),
                wdth,
                themes.getMainTabPanelHeight());
        initBTNS(null);
        showActiveBtn();
    }

    public tab_main_panel() {
        me=this;
        setOpaque(true);
        setLayout(null);
        addMouseWheelListener(this);
        createTabAndCanvas("Figure 1");

//        setFocusable(true);

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (BTNS.size()==0) {
            return;
        }
        int notches=e.getWheelRotation();
        int askedOffset=offset-notches*30;

        if ((notches<0)&&(askedOffset>0)) {
            askedOffset=0;
        } else {
            if ((notches>0)&&(Rect_width*BTNS.size()+askedOffset<=getSize().width)) {
                askedOffset=getSize().width-Rect_width*(BTNS.size());
            }
        }
        offset=askedOffset;
        deOverAll();
        initBTNS(null);
    }
    /***************/
    /* STATIC PART */
    /***************/
    private static int Rect_width=150;
    private static int offset=0;
    private static int leftmargin=20;
    private static tab_main_panel me=null;
    private static ArrayList<tab_btn> BTNS=new ArrayList();
    private static JPopupMenu tab_popup=null, ctrl_popup=null;

    public static tab_main_panel getme() {
        return me;
    }

    public static void removeAllBtns(ContentPane content) {
        if (content!=null) {
            for (int i=0; i<BTNS.size(); i++) {
                content.remove(BTNS.get(i));
            }
        }
        BTNS.clear();
    }


    /**
     * Remove tab and select the next tab or the tab before
     * depending if you removed the last one or not
     * @param btn : the tab_btn btn you want to remove
     */
    public static void removeBtnAndSelect(tab_btn btn) {
        if (BTNS.size()>1) {
            int i=getActiveBtnPos();
            removeBtn(btn);
            if (i<BTNS.size()) {
                setActiveBtn(BTNS.get(i));
            } else {
                setActiveBtn(BTNS.get(i-1));
            }
            showActiveBtn();
            me.revalidate();
            me.repaint();
        }
    }


    /**
     * Remove tab from the JPanel and from the BTNS arrayList
     * @param btn : the tab_btn btn you want to remove
     */
    public static void removeBtn(tab_btn btn) {
        if (BTNS.size()>1) {
            me.remove(btn);
            BTNS.remove(btn);
            initBTNS(null);
        }
    }

    public static void addBtnAndSelect(String label) {
        tab_btn btn=new tab_btn(label);
        int i=getActiveBtnPos()+1;
        if (i==BTNS.size()) {
            me.add(btn);
            BTNS.add(btn);
        } else {
            me.add(btn, i);
            BTNS.add(i, btn);
        }
        initBTNS(null);
        setActiveBtn(btn);
        showActiveBtn();
        btn.editName();
    }

    public static tab_btn addBtn(String name) {
        tab_btn btn=new tab_btn(name, name);
        me.add(btn);
        BTNS.add(btn);
        return btn;
    }

    public static void createTabAndCanvas(String label, String tooltip) {
        tab_btn btn=new tab_btn(label, tooltip);
        me.add(btn);
        BTNS.add(btn);
        setActiveBtn(btn);
        showActiveBtn();
    }

    public static void createTabAndCanvas(String label) {
        createTabAndCanvas(label, label);
    }

    // j is the offset (1 for the next to the right, -1 to the left)
    public static void setNextActiveBtn(int j) {
        int i=getActiveBtnPos();
        try {
            setActiveBtn(BTNS.get(i+j));
            showActiveBtn();
        } catch (Exception e) {
        }
        initNAVbtns();
    }

    public static void setCurrentTabName(String name, String tooltip) {
        getActiveBtn().setTabName(name, tooltip);
    }

    public static void setActiveBtn(int k) {
        setActiveBtn(BTNS.get(k));
    }

    public static void setActiveBtn(final tab_btn btn) {
        JZirkelCanvas.stopAllScripts();
        btn.setActive(true);
        for (int i=0; i<BTNS.size(); i++) {
            if (!BTNS.get(i).equals(btn)) {
                BTNS.get(i).setActive(false);
            }
            BTNS.get(i).repaint();
        }
        initNAVbtns();
        pipe_tools.onTabActivate();
        SwingUtilities.invokeLater(new Runnable() {
	    @Override
            public void run() {
                btn.getPanel().setComments();
                JZirkelCanvas.restartAllScripts();
            }
        });
    }

    public static tab_btn getLastBtn() {
        if (BTNS.size()>0) {
            return BTNS.get(BTNS.size()-1);
        } else {
            return null;
        }
    }

    public static void currentTabHaveChanged(boolean b) {
        getActiveBtn().setChanged(b);
    }

    public static void allTabsHaveChanged(boolean b) {
        for (int i=0; i<BTNS.size(); i++) {
            BTNS.get(i).setChanged(b);
        }
    }

    public static tab_btn getActiveBtn() {
        for (int i=0; i<BTNS.size(); i++) {
            if (BTNS.get(i).getActive()) {
                return BTNS.get(i);
            }
        }
        return null;
    }

    public static tab_canvas_panel getPanel(int i) {
        return BTNS.get(i).getPanel();
    }

    public static tab_canvas_panel getActivePanel() {
        for (int i=0; i<BTNS.size(); i++) {
            if (BTNS.get(i).getActive()) {
                return BTNS.get(i).getPanel();
            }
        }
        return null;
    }

    public static int getActiveBtnPos() {
        for (int i=0; i<BTNS.size(); i++) {
            if (BTNS.get(i).getActive()) {
                return i;
            }
        }
        return -1;
    }

    public static int getBTNSsize() {
        return BTNS.size();
    }

    public static tab_btn getBTN(int i) {
        return BTNS.get(i);
    }

    public static void showActiveBtn() {
        tab_btn btn=getActiveBtn();
        boolean b1=(btn.getBounds().x+btn.getSize().width>me.getSize().width);
        if ((btn.getBounds().x+btn.getSize().width>me.getSize().width)) {
            offset-=btn.getBounds().x+btn.getSize().width-me.getSize().width;
        } else if (btn.getBounds().x<0) {
            offset+=-btn.getBounds().x;
        }
        initBTNS(null);
    }

    public static void setOverBtn(tab_btn btn) {
        btn.setOver(true);
        for (int i=0; i<BTNS.size(); i++) {
            if (!BTNS.get(i).equals(btn)) {
                BTNS.get(i).setOver(false);
            }
            BTNS.get(i).repaint();
        }
    }

    public static void reorderBTNS(tab_btn exceptBtn) {
        Collections.sort(BTNS);
        initBTNS(exceptBtn);
        initNAVbtns();
    }

    public static void deOverAll() {
        for (int i=0; i<BTNS.size(); i++) {
            BTNS.get(i).setOver(false);
        }
    }

    public static void deactiveAll() {
        for (int i=0; i<BTNS.size(); i++) {
            BTNS.get(i).setActive(false);
        }
    }

    public static void initNAVbtns() {
        int i=getActiveBtnPos();
        nav_left.setDisabled(i==0);
        nav_right.setDisabled(i==(BTNS.size()-1));
    }

    public static void initBTNS(tab_btn except) {
        for (int i=0; i<BTNS.size(); i++) {
            tab_btn btn=BTNS.get(i);
            if (!btn.equals(except)) {
                int x=themes.getLeftPanelWidth()+themes.getVerticalPanelBorderWidth()+leftmargin+offset+i*Rect_width;
                int y=0;
                btn.setBounds(x, y, Rect_width-1, themes.getTabBtnHeight());
                btn.init();
            }
        }
    }

    public static boolean rightcut(tab_btn btn) {
        boolean b1=(btn.getBounds().x+btn.getSize().width>me.getSize().width);
        boolean b2=(btn.getBounds().x<me.getSize().width);
        return (b1&&b2);
    }

    public static int visibleWidth(tab_btn btn) {
        if (rightcut(btn)) {
            return ((me.getSize().width-btn.getBounds().x));
        } else {
            return Rect_width;
        }
    }

    public static JPopupMenu getTabPopup() {
        initTabPopupMenu();
        return tab_popup;
    }

    public static void initTabPopupMenu() {
        tab_popup=new JPopupMenu();
        for (int i=0; i<BTNS.size(); i++) {
            final int j=i;
            myJMenuItem item=new myJMenuItem(BTNS.get(i).getTabName()) {

                @Override
		public void action() {
                    setActiveBtn(BTNS.get(j));
                    showActiveBtn();
                }
            };
            if (BTNS.get(i).getActive()) {
                item.setFont(themes.TabSelectedMenusFont);
            }
            tab_popup.add(item);
        }
    }

    public static JPopupMenu getCtrlPopup() {
        initCtrlPopupMenu();
        return ctrl_popup;
    }

    public static void initCtrlPopupMenu() {
        ctrl_popup=new JPopupMenu();
        myJMenuItem item=new myJMenuItem(Global.Loc("tab.popup.rename")) {

            @Override
	    public void action() {
                getActiveBtn().editName();
            }
        };
        if (!JZirkelCanvas.isWorkBook()) {
            item.setEnabled(false);
        }
        ctrl_popup.add(item);
        item=new myJMenuItem(Global.Loc("tab.popup.newfigure")) {

            @Override
	    public void action() {
                newTabBtn();
            }
        };
        ctrl_popup.add(item);
        item=new myJMenuItem(Global.Loc("tab.popup.duplicate")) {

            @Override
	    public void action() {
                pipe_tools.duplicateTab();
            }
        };
        ctrl_popup.add(item);
        item=new myJMenuItem(Global.Loc("tab.popup.savefileonly")) {

            @Override
	    public void action() {
                pipe_tools.saveFigure();
            }
        };
        ctrl_popup.add(item);
        ctrl_popup.add(new JSeparator());

        item=new myJMenuItem(Global.Loc("tab.popup.openworkbook")) {

            @Override
	    public void action() {
                FileTools.openWorkBook();
            }
        };
        ctrl_popup.add(item);
        item=new myJMenuItem(Global.Loc("tab.popup.saveworkbookas")) {

            @Override
	    public void action() {
                FileTools.saveWorkBookAs();
            }
        };
        ctrl_popup.add(item);
        item=new myJMenuItem(Global.Loc("tab.popup.saveworkbook")) {

            @Override
	    public void action() {
                FileTools.saveWorkBook(JZirkelCanvas.getWorkBookFileName());
            }
        };
        ctrl_popup.add(item);
        ctrl_popup.add(new JSeparator());

        leftpanelitem=new myJMenuItem(Global.Loc("menu.display.leftpanel")) {

            @Override
	    public void action() {
                Open_left_panel_btn.toggle();
            }
        };
        ctrl_popup.add(leftpanelitem);
        commentitem=new myJMenuItem(Global.Loc("menu.display.comment")) {

            @Override
	    public void action() {
                Open_middle_panel_btn.toggle();
            }
        };
        ctrl_popup.add(commentitem);
        paletteitem=new myJMenuItem(Global.Loc("menu.display.palette")) {

            @Override
	    public void action() {
                Open_right_panel_btn.toggle();
            }
        };
        ctrl_popup.add(paletteitem);
        ctrl_popup.add(new JSeparator());

        item=new myJMenuItem(Global.Loc("menu.display.restrictedenvironment")) {

	    @Override
	    public void action() {
                pipe_tools.showRestrictedEnvironmentManager();
            }
        };
        ctrl_popup.add(item);
        item=new myJMenuItem(Global.Loc("menu.special.definejob")) {

	    @Override
	    public void action() {
                pipe_tools.showExerciseManager();
            }
        };
        ctrl_popup.add(item);
        initToggleItems();
    }

    public static void hidePopups() {
        if (tab_popup!=null) {
            tab_popup.setVisible(false);
        }
        if (ctrl_popup!=null) {
            ctrl_popup.setVisible(false);
        }
    }

    private static boolean nameExists(String name) {
        for (int i=0; i<BTNS.size(); i++) {
            if (name.equals(BTNS.get(i).getTabName())) {
                return true;
            }
        }
        return false;
    }

    public static String uniqueName(String name) {
        if (nameExists(name) && !getActiveBtn().getTabName().equals(name)) { //bug du nom lors d'enregistrement de .zir
	//if (nameExists(name)) {
            return uniqueNumberedName(name);
        } else {
            return name;
        }
    }

    public static String uniqueNumberedName(String base) {
        int count=1;
//        System.out.print(base+":");
        base=base.replaceAll("[\\s0-9]+$", "");
//        System.out.println(base);
        while (nameExists(base+" "+count)) {
            count++;
        }
        return (base+" "+count);
    }

    public static void newTabBtn() {
        addBtnAndSelect(uniqueNumberedName(Global.Loc("tab.newfigurebasename")));
    }

    public static void initToggleItems() {
        if (commentitem!=null) {
            commentitem.setSelected(Global.getParameter("comment", false));
            leftpanelitem.setSelected(LeftPanel.isPanelVisible());
            paletteitem.setSelected(RightPanel.isPanelVisible());
        }
    }
}
