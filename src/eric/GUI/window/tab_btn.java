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
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

/**
 *
 * @author erichake
 */
public class tab_btn extends windowComponent implements Comparable<tab_btn>, PopupMenuListener {

    private tab_canvas_panel panel;
    private Point origin;
    private Point current;
    private Point btnloc;
    private boolean active=true;
    private boolean over=false;
    private tab_close_btn CloseBtn;
    private tab_btn_label Label;
    private String Tooltip="";
    private static Image on_btn_right=themes.getImage("tab_btn_on_right_border.gif");
    private static Image on_btn_left=themes.getImage("tab_btn_on_left_border.gif");
    private static Image on_btn=themes.getImage("tab_btn_on.gif");
    private static Image off_btn_right=themes.getImage("tab_btn_off_right_border.gif");
    private static Image off_btn_left=themes.getImage("tab_btn_off_left_border.gif");
    private static Image off_btn=themes.getImage("tab_btn_off.gif");
    private static Image off_over_btn_right=themes.getImage("tab_btn_off_over_right_border.gif");
    private static Image off_over_btn_left=themes.getImage("tab_btn_off_over_left_border.gif");
    private static Image off_over_btn=themes.getImage("tab_btn_off_over.gif");
    private static Image file_icon=themes.getImage("tab_file_icon.png");
    private boolean Changed=false;

    public void paintComponent(Graphics g) {

        Dimension d=getSize();
        final Graphics2D g2=windowComponent.getGraphics2D(g);



        g2.drawImage(getBackgroundImage(), 0, 0, d.width, d.height,
                this);
        g2.drawImage(getLeftBorder(), 0, 0, themes.getTabCornerWidth(), d.height,
                this);
        g2.drawImage(getRightBorder(), d.width-themes.getTabCornerWidth(), 0, themes.getTabCornerWidth(), d.height,
                this);

        if (!JZirkelCanvas.isWorkBook()) {
            g2.drawImage(file_icon, 4, 2, this);
        }
//        g2.setColor(Color.darkGray);
//        g2.drawLine(0, d.height-1, d.width-1, d.height-1);
//        g2.drawLine(d.width-1, d.height-1, d.width-1, 0);
//        if (!active) {
//            g2.drawLine(d.width-1, 0, 0, 0);
//        }

    }

    public tab_btn(String label, String tooltip) {
        super();
        label=pipe_tools.processTabName(label);
        Label=new tab_btn_label(label);
        Tooltip=tooltip;
        setLayout(null);
        CloseBtn=new tab_close_btn();
        panel=new tab_canvas_panel(this);
        add(CloseBtn);
        add(Label);
//        setToolTipText(tooltip);
        setOpaque(true);
    }

    public tab_btn(String label) {
        this(label, label);
    }

    public void setChanged(boolean b) {
        Changed=b;
        Label.setChanged(b);
        repaint();
    }

    public void setActive(boolean act) {
        active=act;
        if (active) {
            ContentPane.setCurrentPanel(panel);
        }
    }

    public boolean getActive() {
        return active;
    }

    public void setOver(boolean ov) {
        over=ov;
    }

    public tab_close_btn getTabCloseBtn() {
        return CloseBtn;
    }

    public tab_btn_label getTabLabel() {
        return Label;
    }

    public tab_canvas_panel getPanel() {
        return panel;
    }

    public void setTabName(String name, String tooltip) {
        name=pipe_tools.processTabName(name);
        Label.setLabelText(name);
        setToolTip(tooltip);
    }

    public void setToolTip(String tooltip){
	Tooltip = tooltip;
	setToolTipText(Tooltip);
    }

    public String getTabName() {
        return Label.getLabelText();
    }

    public void editName() {
        if (pipe_tools.isTabEditAccepted()) {
            Label.edit();
        }
    }

    public void init() {
        setToolTipText(Tooltip);
        getTabCloseBtn().init();
        getTabLabel().init();
        getPanel().init();
    }

    private Image getRightBorder(){
        if (active) {
            return tab_btn.on_btn_right;
        }
        if (over) {
            return tab_btn.off_over_btn_right;
        }
        return tab_btn.off_btn_right;
    }

    private Image getLeftBorder(){
        if (active) {
            return tab_btn.on_btn_left;
        }
        if (over) {
            return tab_btn.off_over_btn_left;
        }
        return tab_btn.off_btn_left;
    }


    private Image getBackgroundImage() {
        if (active) {
            return tab_btn.on_btn;
        }
        if (over) {
            return tab_btn.off_over_btn;
        }
        return tab_btn.off_btn;
    }

    @Override
    public Point getToolTipLocation(MouseEvent event) {
        return new Point(0, getSize().height+2);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        origin=pipe_tools.getWindow().getMouseLoc();
        btnloc=getLocation();
        tab_main_panel.setActiveBtn(this);
        repaint();
//        pipe_tools.onTabActivate();
        // Right-clic :
        if (e.getModifiers()==Event.META_MASK) {
            JPopupMenu popup=tab_main_panel.getCtrlPopup();
            popup.removePopupMenuListener(this);
            popup.addPopupMenuListener(this);
            popup.show(this, (getSize().width-popup.getPreferredSize().width)/2, -2-popup.getPreferredSize().height);
            repaint();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

        tab_main_panel.setOverBtn(this);
        repaint();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Check whether the mouse cursor is still over the panel

//        if (getMousePosition(true)!=null) {
//            return;
//        }
        over=false;
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        tab_main_panel.reorderBTNS(null);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        getParent().setComponentZOrder(this, 0);
        current=pipe_tools.getWindow().getMouseLoc();
        if (current==null) {
            return;
        }
        setLocation(btnloc.x+current.x-origin.x, btnloc.y);
        tab_main_panel.reorderBTNS(this);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        tab_main_panel.setOverBtn(this);
        repaint();

    }

    public int compareTo(tab_btn o) {
        int middle=(2*getBounds().x+getBounds().width)/2;
        if ((middle>o.getBounds().x)) {
            return 1;

        }
        if ((middle<o.getBounds().x)) {
            return -1;

        }
        return 0;
    }

    public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
        removeMouseListener(this);
    }

    public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
        repaint();
        final tab_btn btn=this;
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                addMouseListener(btn);
            }
        });
    }

    public void popupMenuCanceled(PopupMenuEvent e) {
    }
}
