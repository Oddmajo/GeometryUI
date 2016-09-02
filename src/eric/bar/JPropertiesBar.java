/*

Copyright 2006 Eric Hakenholz

This file is part of C.a.R. software.

C.a.R. is a free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, version 3 of the License.

C.a.R. is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.

 */
package eric.bar;

import eric.GUI.palette.PaletteManager;
import eric.GUI.pipe_tools;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import eric.JEricPanel;

import rene.gui.Global;
import rene.zirkel.objects.ConstructionObject;
import eric.controls.JCanvasPanel;
import eric.macros.CreateMacroDialog;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

/**
 *
 * @author erichake
 */
public class JPropertiesBar extends JFrame implements MouseListener,
        MouseMotionListener {

    static private int Bx=0, By=0, Bwidth=1078, Bheight=60;
    CPane CP;
    TitleBar titlebar;
    static JControlProperties Content;
    private MouseEvent pressed;
    private Point location;
    private static JPropertiesBar JPB=null;

    public JPropertiesBar() {
        JPB=this;
        Bx=Global.getParameter("props.paletteX", 0);
        if (Bx<Global.getScreenX()) {
            Bx=Global.getScreenX();
        }
        By=Global.getScreenY();

        CP=new CPane();
        setContentPane(CP);
        titlebar=new TitleBar(this, 18);
        Content=new JControlProperties(Bwidth-titlebar.getSize().width,
                Bheight);
        Content.addPanel(Global.Loc("props.aspecttab"));
        Content.addPanel(Global.Loc("props.numerictab"));
        Content.addPanel(Global.Loc("props.conditionaltab"));
        Content.selectTab(Global.getParameter("props.selectedtab", 0));



        CP.add(titlebar);
        CP.add(Content);
        setSize(Bwidth+2, Bheight+2);
        setLocation(Bx, By);
        setUndecorated(true);

        // Attention : setFocusable commenté pour la 3.5.5 beta 3, car cela interdit le
        // "echap" défini juste après...
//        setFocusable(true);


        KeyStroke key=KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
        getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(key, "escape");
        getRootPane().getActionMap().put("escape", new AbstractAction() {
	    @Override
            public void actionPerformed(final ActionEvent arg0) {
                PaletteManager.setSelected_with_clic("move", true);
                CreateMacroDialog.quit();
            }
        });


    }

    public static int getBarHeight() {
        if ((JPB!=null)&&(JPB.isVisible())) {
            return Bheight;
        } else {
            return 0;
        }
    }

    public static void clearme() {
        Content.clearme();
    }

    public void refresh() {
        Content.refresh();
    }

    public void showme(final boolean vis) {
        if (vis) {
            setVisible(true);
            pipe_tools.setWindowBounds();
            pipe_tools.toFront();
        } else {
            setVisible(false);
        }
    }

    public static void ShowHideBar(){
        if (JPB!=null){
            JPB.showme(!JPB.isVisible());
        }
    }

    static public boolean isBarVisible() {
        return ((JPB!=null)&&(JPB.isVisible()));
    }

    static public void EditObjects(ArrayList<ConstructionObject> v){
        if ((JPB!=null)&&(v!=null)&&(v.size()>0)) {
            JPB.setObjects(v);
        }
    }

    static public void EditObject(final ConstructionObject o) {
        EditObject(o, true, true);
    }

    static public void EditObject(final ConstructionObject o,
            final boolean forcevisible, final boolean forcefocus) {
        if ((JPB!=null)&&(o!=null)) {

            JPB.setObject(o, forcevisible, forcefocus);
        }
    }

    static public void EditObject(final JCanvasPanel jcp) {
        if ((JPB!=null)&&(jcp.O!=null)) {
            JPB.setObject(jcp);
        }
    }

    static public void SelectPropertiesTab(final int i) {
        JPB.selectTab(i);
    }

    static public void RefreshBar() {
        if (JPB!=null) {
            JPB.refresh();
        }
    }

    // Only called at first launch (applet or application) :
    static public void CreatePropertiesBar() {
        JPB=new JPropertiesBar();
        JPB.showme(false);
    }

   private void setObjects(ArrayList<ConstructionObject> v) {
        showme(true);
        Content.setObjects(v);
    }

    public void setObject(final ConstructionObject O,
            final boolean forcevisible, final boolean forcefocus) {

        if (forcevisible) {
            showme(true);
        }
        if ((!this.isVisible())&&(!forcefocus)) {
            return;
        }

        Content.setObject(O, forcevisible, forcefocus);

    }

    public void selectTab(final int i) {
        Content.selectTab(i);
    }

    public void setObject(final JCanvasPanel jcp) {
        showme(true);
        Content.setObject(jcp);
    }

    private static void fixsize(final JComponent cp, final int w, final int h) {
        final Dimension d=new Dimension(w, h);
        cp.setMaximumSize(d);
        cp.setMinimumSize(d);
        cp.setPreferredSize(d);
        cp.setSize(d);
    }



    private class TitleBar extends JEricPanel {

        ImageIcon closeimg=new ImageIcon(getClass().getResource(
                "gui/Pclose.png"));
        ImageIcon closeoverimg=new ImageIcon(getClass().getResource(
                "gui/Pcloseover.png"));
        ImageIcon myicon=new ImageIcon(getClass().getResource(
                "gui/titlebar.png"));
        JButton closebtn;
        JPropertiesBar Mother;

        @Override
        public void paintComponent(final java.awt.Graphics g) {

            final java.awt.Dimension d=this.getSize();
            g.drawImage(myicon.getImage(), 0, 0, d.width, d.height, this);
            super.paintComponent(g);
        }

        public TitleBar(final JPropertiesBar parent, final int width) {
            Mother=parent;
            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            // this.setAlignmentX(0F);
            this.setOpaque(false);
            fixsize(this, width, Bheight);
            this.addMouseListener(parent);
            this.addMouseMotionListener(parent);
            closebtn=new JButton(closeimg);
            closebtn.setRolloverIcon(closeoverimg);
            closebtn.setBorder(BorderFactory.createEmptyBorder());
            closebtn.setOpaque(false);
            closebtn.setContentAreaFilled(false);
            closebtn.addMouseListener(new MouseAdapter() {

                @Override
                public void mousePressed(final MouseEvent e) {
//                    Mother.setVisible(false);
                    Mother.showme(false);
                }
            });
            this.add(closebtn);

        }
    }

    private class CPane extends JEricPanel {

        public CPane() {
            this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            this.setOpaque(false);
            this.setBorder(BorderFactory.createLineBorder(
                    new Color(80, 80, 80), 1));
        }
    }

    private void setBarLocation(final MouseEvent me) {
        location=getLocation(location);
        int x=location.x-pressed.getX()+me.getX();
        if (x<Global.getScreenX()+20) {
            x=Global.getScreenX();
        } else if (x+Bwidth>Global.getScreenX()+Global.getScreenW()-20) {
            x=Global.getScreenX()+Global.getScreenW()-Bwidth;
        }
        setLocation(x, Global.getScreenY());
        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void mouseDragged(final MouseEvent me) {
        setBarLocation(me);
    }

    @Override
    public void mousePressed(final MouseEvent me) {
        pressed=me;
    }

    @Override
    public void mouseMoved(final MouseEvent e) {
    }

    @Override
    public void mouseReleased(final MouseEvent e) {
        setBarLocation(e);
        Global.setParameter("props.paletteX", getLocation().x);
        Bx=getLocation().x;
        By=getLocation().y;
        pipe_tools.setWindowLocation();
        pipe_tools.toFront();
    }

    @Override
    public void mouseClicked(final MouseEvent e) {
    }

    @Override
    public void mouseEntered(final MouseEvent e) {
    }

    @Override
    public void mouseExited(final MouseEvent e) {
    }
}