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
package eric.GUI.palette;

import eric.GUI.themes;
import eric.JZirkelCanvas;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Stroke;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import java.awt.image.BufferedImage;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import eric.JEricPanel;
import javax.swing.SwingConstants;

import rene.gui.Global;
import ui.net.java.dev.colorchooser.ColorChooser;
import ui.net.java.dev.colorchooser.Palette;

/**
 * 
 * @author erichake
 */
public class JColorPanel extends JEricPanel implements MouseListener,
        MouseMotionListener {

    /**
     *
     */
    
    private final ColorChooser cchooser;
    private Palette Pal;
    int PaletteType;
    int xx, yy;
    colorline mycolorpickerline;
    JComboBox JCB;
    JLabel comment;
    JColorPanel me;

    /**
     *
     * @param g
     */
    @Override
    public void paintComponent(final java.awt.Graphics g) {
        super.paintComponent(g);
        final java.awt.Dimension d=this.getSize();
        g.drawImage(themes.getImage("palbackground.gif"), 0, 0, d.width,
                d.height, this);
    }

    /**
     * Creates a new instance of JColorPanel
     *
     * @param zf
     * @param jpm
     */
    public JColorPanel() {
        me=this;
        xx=-1;
        yy=-1;
        PaletteType=Global.getParameter("colorbackgroundPal", 1);
        cchooser=new ColorChooser();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setAlignmentX(0);
        this.setOpaque(false);
        this.add(margintop(5));
        JCB=new JComboBox();
        JCB.setMaximumRowCount(5);
        JCB.setOpaque(false);
        JCB.setFocusable(false);
        JCB.setEditable(false);
        JCB.setAlignmentX(0);
        JCB.setFont(new Font("System", 0, 11));
        JCB.addItem(Global.Loc("palette.colors.saturated1"));
        JCB.addItem(Global.Loc("palette.colors.desaturated1"));
        JCB.addItem(Global.Loc("palette.colors.saturated2"));
        JCB.addItem(Global.Loc("palette.colors.desaturated2"));
        JCB.addItem(Global.Loc("palette.colors.constants"));
        JCB.setSelectedIndex(PaletteType);
        JCB.addItemListener(new ItemAdapter());
        final JEricPanel JCBpanel=new JEricPanel();
        JCBpanel.setLayout(new BoxLayout(JCBpanel, BoxLayout.X_AXIS));
        JCBpanel.setAlignmentX(0);
        JCBpanel.setOpaque(false);
        fixsize(JCBpanel, themes.getRightPanelWidth(), 24);
        JCBpanel.add(margin(5));
        fixsize(JCB, themes.getRightPanelWidth()-10, 22);
        JCBpanel.add(JCB);
        this.add(JCBpanel);

        mycolorpickerline=new colorline(PaletteType);
        this.add(mycolorpickerline);

        comment=new JLabel("coucou");
        comment.setOpaque(false);
        comment.setAlignmentX(0);
        comment.setFont(new Font("System", 0, 9));
        fixsize(comment, themes.getRightPanelWidth(), 14);
        comment.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(comment);
    }

    public void refresh(){
        JCB.setSelectedIndex(Global.getParameter("colorbackgroundPal", 1));
    }

    class ItemAdapter implements ItemListener {

        public void itemStateChanged(final ItemEvent evt) {
            if (evt.getStateChange()==ItemEvent.SELECTED) {
                me.remove(mycolorpickerline);
                mycolorpickerline=new colorline(JCB.getSelectedIndex());
                
                Global.setParameter("colorbackgroundPal", JCB.getSelectedIndex());
                me.add(mycolorpickerline, 2);
                int x=Global.getParameter("colorbackgroundx", 74);
                int y=Global.getParameter("colorbackgroundy", 12);
                x=x*Pal.getSize().width/mycolorpickerline.mycolors.getSize().width;
                y=y*Pal.getSize().height/mycolorpickerline.mycolors.getSize().height;
                final Color mycolor=Pal.getColorAt(x, y);
                if (mycolor!=null) {
                    Global.setParameter("colorbackground", mycolor);
                }
                me.repaint();
                PaletteManager.init();
            }
        }
    }

    public JDialog GetDialog(final Component c) {
        if (c instanceof JDialog||null==c) {
            return c==null?null:(JDialog) c;
        }
        return GetDialog(c.getParent());
    }

    class colorline extends JEricPanel {

        /**
         *
         */
        
        JEricPanel mymargin;
        onlycolors mycolors;

        colorline(final int ptype) {
            PaletteType=ptype;
            Pal=cchooser.getPalettes()[ptype];
            this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            this.setAlignmentX(0);
            mymargin=margin(0);
            if (themes.getRightPanelWidth()>Pal.getSize().width) {
                fixsize(mymargin, (themes.getRightPanelWidth()-Pal.getSize().width)/2, 1);
            }
            this.add(mymargin);
            mycolors=new onlycolors(ptype);
            this.add(mycolors);
            this.setOpaque(false);
        }
        // public void fixmargin(){
        // if (PW>Pal.getSize().width){
        // fixsize(mymargin,(PW-Pal.getSize().width)/2,1);
        // }
        // }
        // public void setPalette(int i){
        // mycolors.changePal(i);
        // fixmargin();
        // }
    }

    private class onlycolors extends JEricPanel {

        /**
         *
         */
        
        Image bimage;

        @Override
        public void paintComponent(final java.awt.Graphics g) {
            super.paintComponent(g);
            final Dimension d=this.getSize();
            final int w=d.width;
            final int h=d.height;
            g.drawImage(bimage, 0, 0, w, h, this);
            final Color mycolor=Global.getParameter("colorbackground",
                    new Color(231, 238, 255));
            cchooser.setColor(mycolor);
            if (JZirkelCanvas.getCurrentZC()!=null) {
                JZirkelCanvas.getCurrentZC().setBackground(mycolor);
                JZirkelCanvas.getCurrentZC().repaint();
            }


            int x=Global.getParameter("colorbackgroundx", 74);
            int y=Global.getParameter("colorbackgroundy", 12);

            if (PaletteType==4) {
                x=((int) (x/12))*12+6;
                y=((int) (y/12))*12+6;
            }

            final Graphics2D g2=(Graphics2D) g;
            // AlphaComposite ac =
            // AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.5f);
            // g2.setComposite(ac);
            g2.setColor(new Color(0, 0, 0));
            Stroke stroke=new BasicStroke(1f);
            g2.setStroke(stroke);
            g2.drawRect(x-4, y-4, 8, 8);
            stroke=new BasicStroke(1f);
            g2.setStroke(stroke);
            g2.setColor(new Color(255, 255, 255));
            g2.drawRect(x-3, y-3, 6, 6);
            // g.drawRect(x-5,y-5,10,10);
            comment.setText(Pal.getNameAt(x, y));
        }

        onlycolors(final int ptype) {
            final int w=(themes.getRightPanelWidth()<Pal.getSize().width)?themes.getRightPanelWidth():Pal.getSize().width;
            fixsize(this, w, Pal.getSize().height);
            bimage=new BufferedImage(Pal.getSize().width,Pal.getSize().height, BufferedImage.TYPE_INT_RGB);
            Pal.paintTo(bimage.getGraphics());
            this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            this.setAlignmentX(0);
            this.setOpaque(false);
            this.addMouseMotionListener(me);
            this.addMouseListener(me);
        }
        // public void changePal(int i){
        //
        // Pal=cchooser.getPalettes()[i];
        // int w=(PW<Pal.getSize().width)?PW:Pal.getSize().width;
        // fixsize(this,w,Pal.getSize().height);
        // bimage=JPM.MW.createImage(Pal.getSize().width,Pal.getSize().height);
        // Pal.paintTo(bimage.getGraphics());
        // }
    }

    private void fixsize(final JComponent cp, final int w, final int h) {
        final Dimension d=new Dimension(w, h);
        cp.setMaximumSize(d);
        cp.setMinimumSize(d);
        cp.setPreferredSize(d);
        cp.setSize(d);
    }

    private JEricPanel margin(final int w) {
        final JEricPanel mypan=new JEricPanel();
        fixsize(mypan, w, 1);
        mypan.setOpaque(false);
        mypan.setFocusable(false);
        return mypan;
    }

    private JEricPanel margintop(final int h) {
        final JEricPanel mypan=new JEricPanel();
        fixsize(mypan, 1, h);
        mypan.setOpaque(false);
        mypan.setFocusable(false);
        return mypan;
    }

    public void mouseClicked(final MouseEvent e) {
    }

    public void mousePressed(final MouseEvent e) {
        int x=e.getX();
        int y=e.getY();
        if (e.isShiftDown()) {
            if (yy==-1) {
                yy=y;
            }
            y=yy;
        }
        if (e.isAltDown()) {
            if (xx==-1) {
                xx=x;
            }
            x=xx;
        }
        if (x>mycolorpickerline.mycolors.getSize().width) {
            x=mycolorpickerline.mycolors.getSize().width;
        }
        if (x<0) {
            x=0;
        }
        if (y>mycolorpickerline.mycolors.getSize().height) {
            y=mycolorpickerline.mycolors.getSize().height;
        }
        if (y<0) {
            y=0;
        }

        Global.setParameter("colorbackgroundx", x);
        Global.setParameter("colorbackgroundy", y);

        x=x*Pal.getSize().width/mycolorpickerline.mycolors.getSize().width;
        y=y*Pal.getSize().height/mycolorpickerline.mycolors.getSize().height;

        final Color mycolor=Pal.getColorAt(x, y);
        if (mycolor!=null) {
            Global.setParameter("colorbackground", mycolor);
            JZirkelCanvas.getCurrentLocalPreferences();
            mycolorpickerline.mycolors.repaint();
        }
    }

    public void mouseReleased(final MouseEvent e) {
        xx=-1;
        yy=-1;
    }

    public void mouseEntered(final MouseEvent e) {
    }

    public void mouseExited(final MouseEvent e) {
    }

    public void mouseDragged(final MouseEvent e) {
        mousePressed(e);
    }

    public void mouseMoved(final MouseEvent e) {
    }
}
