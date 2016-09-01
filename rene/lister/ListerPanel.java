/* 

Copyright 2006 Rene Grothmann, modified by Eric Hakenholz

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
package rene.lister;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Vector;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import eric.JEricPanel;

import rene.gui.Global;
import rene.util.MyVector;

public class ListerPanel extends JEricPanel {

    /**
     *
     */
    private static final long serialVersionUID=1L;
    private final MyVector V; // Vector of listed Elements
    int Top; // Top Element
    Image I; // Buffer Image
    int W, H; // width and height of current image
    int panelHeight;
    Graphics IG; // Graphics for the image
    Font F; // current font
    FontMetrics FM; // current font metrics
    int Leading, Height, Ascent, Descent; // font stuff
    int PageSize; // numbers of lines per page
    int HOffset; // horizontal offset of display
    boolean ShowLast; // Show last on next redraw
    Lister LD;
    String Name;
    public Color ListingBackground=null;
    public boolean MultipleSelection=true; // Allow multiple selection
    public boolean EasyMultipleSelection=false; // Multiple select without
    // right mouse
    public boolean ReportSingleClick=false; // Report single clicks also
    public boolean RightMouseClick=false; // Report right mouse clicks also

    public ListerPanel(final Lister ld, final String name) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        LD=ld;
        Name=name;
        V=new MyVector();
        Top=0;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                clicked(e);
            }
        });
        init();
    }

    public ListerPanel(final Lister ld) {
        this(ld, "");
    }

    /**
     * Paint routine. Simply sets up the buffer image, calls dopaint and paints
     * the image to the screen.
     */
//	@Override
    @Override
    public void paintComponent(final Graphics g) {
        W=getSize().width;
        H=getHeight();
        I=createImage(W, H);
        if (I==null) {
            return;
        }
        IG=I.getGraphics();
        init();
        dopaint(IG);
        g.drawImage(I, 0, 0, W, H, this);
        double vp, vs, hp, hs;
        if (V.size()>1) {
            vp=(double) Top/V.size();
        } else {
            vp=0;
        }
        if (V.size()>2*PageSize) {
            vs=(double) PageSize/V.size();
        } else {
            vs=0.5;
        }
        if (HOffset<10*W) {
            hp=(double) HOffset/(10*W);
        } else {
            hp=0.9;
        }
        hs=0.1;
    }

    /**
     * Initialize the font stuff and set the background of the panel.
     */
    void init() {
        F=new Font("Monospaced", 1, 14);
        setFont(F);
        FM=getFontMetrics(F);
        Leading=FM.getLeading()
                +Global.getParameter("fixedfont.spacing", -1);
        Height=FM.getHeight()+3;
        Ascent=FM.getAscent();
        Descent=FM.getDescent();
        // if (Global.Background!=null) setBackground(Global.Background);
        if (Height+Leading>0) {
            PageSize=H/(Height+Leading);
        } else {
            PageSize=10;
        }
        Top=0;
    }

    /**
     * Paint the current text lines on the image.
     *
     * @param g
     */
    public synchronized void dopaint(final Graphics g) {
        Graphics2D g2=(Graphics2D) g;
        g2.setColor(Color.white);
        g2.fillRect(0, 0, W, H);
        g2.setFont(F);
        g2.setColor(Color.black);
        int h=Leading+Ascent;
        int line=Top;
        if (line<0) {
            return;
        }
        while (line-Top<PageSize&&line<V.size()) {
            final Element el=(Element) V.elementAt(line);
            if (isSelected(line)) {
                g2.setColor(getBackground().darker());
                g2.fillRect(0, h-Ascent, W, Height);
                g2.setColor(Color.black);
            }
            final Color col=el.getElementColor();
            if (col!=null) {
                g2.setColor(col);
            } else {
                g2.setColor(Color.black);
            }
            g2.drawString(el.getElementString(State), 2-HOffset, h+1);
            h+=Leading+Height;
            line++;
        }
    }

    public int getHeight() {
        return (V.size()*(Leading+Height));
    }

    public void fixsize() {
        fixsize(this, W, getHeight());
    }

    private static void fixsize(final JComponent cp, final int w, final int h) {
        final Dimension d=new Dimension(w, h);
        cp.setMaximumSize(d);
        cp.setMinimumSize(d);
        cp.setPreferredSize(d);
        cp.setSize(d);
    }
    int State=0;

    public void setState(final int s) {
        State=s;
    }

    /**
     * Add a new line of type rene.lister.Element
     *
     * @param e
     */
    public synchronized void add(final Element e) {
        V.addElement(e);
    }

    // Used by the mouse wheel or external programs:
    public synchronized void up(final int n) {
        Top+=n;
        if (Top>=V.size()) {
            Top=V.size()-1;
        }
        if (Top<0) {
            Top=0;
        }
        repaint();
    }

    public synchronized void down(final int n) {
        Top-=n;
        if (Top<0) {
            Top=0;
        }
        repaint();
    }

    public synchronized void pageUp() {
        up(PageSize-1);
        repaint();
    }

    public synchronized void pageDown() {
        down(PageSize-1);
        repaint();
    }

    /**
     * Set the vertical position. Used by the scrollbars in the Lister.
     *
     * @param x
     *            percentage of text
     */
    public synchronized void setVerticalPos(final double x) {
        Top=(int) (x*V.size());
        if (Top>=V.size()) {
            Top=V.size()-1;
        }
        repaint();
    }

    /**
     * Set the horizontal offset.
     *
     * @param x
     *            ofset in percent of 10 times the screen width
     */
    public synchronized void setHorizontalPos(final double x) {
        HOffset=(int) (x*10*W);
        repaint();
    }

    /**
     * Delete all items from the panel.
     */
    public synchronized void clear() {
        Selected.removeAllElements();
        V.removeAllElements();
        Top=0;
    }

    /**
     * Make sure, the last element displays.
     */
    public synchronized void showLast() {
        ShowLast=true;
        LD.getVerticalScrollBar().setValue(LD.getVerticalScrollBar().getMaximum());
    }
    // Mouse routines:
    Vector VAL=new Vector(); // Vector of action listener
    MyVector Selected=new MyVector(); // currently selected items

    /**
     * Determine if line sel is selected
     *
     * @param sel
     * @return selected or not
     */
    public synchronized boolean isSelected(final int sel) {
        final Enumeration e=Selected.elements();
        while (e.hasMoreElements()) {
            final int n=((Integer) e.nextElement()).intValue();
            if (n==sel) {
                return true;
            }
        }
        return false;
    }

    /**
     * Toggle the line sel to be selected or not.
     *
     * @param sel
     */
    public synchronized void toggleSelect(final int sel) {
        final Enumeration e=Selected.elements();
        while (e.hasMoreElements()) {
            final Integer i=(Integer) e.nextElement();
            if (i.intValue()==sel) {
                Selected.removeElement(i);
                return;
            }
        }
        Selected.addElement(new Integer(sel));
    }

    /**
     * Expand the selection to include sel and all elements in between.
     *
     * @param sel
     */
    public synchronized void expandSelect(final int sel) { // compute maximal
        // selected index
        // below sel.
        int max=-1;
        Enumeration e=Selected.elements();
        while (e.hasMoreElements()) {
            final int i=((Integer) e.nextElement()).intValue();
            if (i>max&&i<sel) {
                max=i;
            }
        }
        if (max>=0) {
            for (int i=max+1; i<=sel; i++) {
                select(i);
            }
            return;
        }
        int min=V.size();
        e=Selected.elements();
        while (e.hasMoreElements()) {
            final int i=((Integer) e.nextElement()).intValue();
            if (i<min&&i>sel) {
                min=i;
            }
        }
        if (min<V.size()) {
            for (int i=sel; i<=min; i++) {
                select(i);
            }
        }
    }

    /**
     * Selecte an item by number sel.
     *
     * @param sel
     */
    public synchronized void select(final int sel) {
        if (!isSelected(sel)) {
            Selected.addElement(new Integer(sel));
        }
    }

    /**
     * Add an action listener for all actions of this panel.
     *
     * @param al
     */
    public void addActionListener(final ActionListener al) {
        VAL.addElement(al);
    }

    /**
     * Remove an action listener
     *
     * @param al
     */
    public void removeActionListener(final ActionListener al) {
        VAL.removeElement(al);
    }

    /**
     * React on mouse clicks (single or double, or right click). single: select
     * the item (according multiple mode) cause change action. double: select
     * only this item and cause action. right: popup menu, if possible. In any
     * case, report the result to the action listeners.
     *
     * @param e
     */
    public void clicked(final MouseEvent e) {
        final int n=e.getY()/(Leading+Height);
        if (e.isMetaDown()&&RightMouseClick) {
            final Enumeration en=VAL.elements();
            while (en.hasMoreElements()) {
                ((ActionListener) (en.nextElement())).actionPerformed(new ListerMouseEvent(LD, Name, e));
            }
        } else {
            if (Top+n>=V.size()) {
                return;
            }
            final int sel=n+Top;
            if (e.getClickCount()>=2) {
                if (!MultipleSelection) {
                    Selected.removeAllElements();
                }
                select(sel);
            } else if (MultipleSelection
                    &&(e.isControlDown()||EasyMultipleSelection||e.isShiftDown())) {
                if (e.isControlDown()||EasyMultipleSelection) {
                    toggleSelect(sel);
                } else if (e.isShiftDown()) {
                    expandSelect(sel);
                }
            } else {
                Selected.removeAllElements();
                Selected.addElement(new Integer(sel));
            }
            final Graphics g=getGraphics();
            paint(g);
            g.dispose();
            if (e.getClickCount()>=2||ReportSingleClick) {
                final Enumeration en=VAL.elements();
                while (en.hasMoreElements()) {
                    ((ActionListener) (en.nextElement())).actionPerformed(new ListerMouseEvent(LD, Name, e));
                }
            }
        }
    }

//	@Override
//	public Dimension getPreferredSize() {
//		return new Dimension(200, 300);
//	}
    public synchronized Element getElementAt(final int n) {
        return (Element) V.elementAt(n);
    }

    public synchronized void save(final PrintWriter o) {
        final Enumeration e=V.elements();
        while (e.hasMoreElements()) {
            final Element el=(Element) e.nextElement();
            o.println(el.getElementString());
        }
    }

    public void setListingBackground(final Color c) {
        ListingBackground=c;
    }
}
