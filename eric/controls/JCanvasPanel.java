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
package eric.controls;

import eric.GUI.palette.PaletteManager;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import eric.JEricPanel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import rene.gui.Global;
import rene.util.xml.XmlWriter;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.objects.ExpressionObject;
import eric.JPointName;
import eric.JZirkelCanvas;
import eric.bar.JPropertiesBar;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import javax.swing.GrayFilter;
import rene.zirkel.tools.JSmacroTool;
import rene.zirkel.tools.Scripts_SetMouseDrag;

/**
 * 
 * @author erichake
 */
public class JCanvasPanel extends JEricPanel implements MouseListener,
        MouseMotionListener, ChangeListener {

    /**
     *
     */
    private static final long serialVersionUID=1L;
    ImageIcon ctrlresizeicon=new ImageIcon(getClass().getResource(
            "/eric/GUI/icons/palette/ctrl_resize.png"));
    static int COMFONTSIZE=14;
    static int COMSIZE=50;
    static Color COMCOLOR=new Color(80, 80, 80);
    JComponent JSL;
    String lbl_com="", lbl_val="", lbl_unit="°";
    boolean showcom=true, showval=true, showunit=false;
    JLabel JCPlabel=new JLabel();
    JButton JCPresize=new JButton();
    // int W, H;
    ZirkelCanvas ZC;
    public ExpressionObject O;
    boolean hidden=false;
    private boolean showborder1=false;
    private boolean showborder2=false;
    private boolean showhandle=false;
    private MouseEvent pressed;
    private Point location;
    private final DecimalFormat decFormat;

    public boolean hidden(){
        return hidden;
    }

    public void paint(Graphics g) {
        Graphics2D g2D=null;
        BufferedImage sprite=null;
        if ((!isHidden())||(ZC.getShowHidden())) {
            sprite=new BufferedImage(getSize().width,
                    getSize().height, BufferedImage.TYPE_INT_ARGB);
            g2D=sprite.createGraphics();
//            super.paintChildren(g2D);
            super.paint(g2D);
        }
        if ((isHidden())&&(ZC.getShowHidden())) {
            final ImageFilter filter=new GrayFilter(true, 60);
            final Image disImage=this.createImage(new FilteredImageSource(
                    sprite.getSource(), filter));
            final ImageIcon myicn=new ImageIcon(disImage);
            g2D.drawImage(myicn.getImage(), 0, 0, getSize().width, getSize().height, this);
        }
        if (sprite!=null) {
            ZC.I.getGraphics().drawImage(sprite, getLocation().x,
                    getLocation().y, this);
        }
        // when mouseentered :
        final Dimension d=getSize();
        if (O.selected()) {
            g.setColor(JControlsManager.bordercolor3);
                g.drawRect(0, 0, d.width-8, d.height-1);
        }
        else if(showhandle) {
            final Graphics2D g2d=(Graphics2D) g;

            final Rectangle2D rectangle=new Rectangle2D.Double(0, 0,
                    d.width-8, d.height-1);
            g2d.setColor(new Color(119, 136, 153));
            g2d.setStroke(new BasicStroke(1f, BasicStroke.CAP_SQUARE,
                    BasicStroke.JOIN_BEVEL, 1f, new float[]{2f}, 0f));
            g2d.draw(rectangle);
            g.drawImage(ctrlresizeicon.getImage(), d.width-12,
                    d.height/2-4, this);
        } else {
            if (showborder1) {
                g.setColor(JControlsManager.bordercolor1);
                g.drawRect(0, 0, d.width-8, d.height-1);
            } else if (showborder2) {
                g.setColor(JControlsManager.bordercolor2);
                g.drawRect(0, 0, d.width-8, d.height-1);
            }
        }
    }

    @Override
    public void paintComponent(final Graphics g) {

    }

    // withoutExpr unused, just to make another constructor :
    public JCanvasPanel(final ZirkelCanvas zc, final ExpressionObject o) {
        super();
        ZC=zc;
        O=(o==null)?createExpression():o;
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.setOpaque(false);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        JCPlabel.setHorizontalAlignment(SwingConstants.LEFT);
        JCPlabel.setFont(new Font(Global.GlobalFont, 0, COMFONTSIZE));
        JCPlabel.setForeground(COMCOLOR);

        // JCPresize.setIcon(ctrlresizeicon);
        JCPresize.setOpaque(false);
        JCPresize.setContentAreaFilled(false);
        JCPresize.setBorder(BorderFactory.createEmptyBorder());
        JCPresize.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));

        JCPresize.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(final MouseEvent me) {
                pressed=me;
            }

            @Override
            public void mouseReleased(final MouseEvent me) {
                ZC.JCM.hideBorders((JCanvasPanel) ((JComponent) me.getSource()).getParent());
            }
        });

        JCPresize.addMouseMotionListener(new MouseMotionAdapter() {

            @Override
            public void mouseDragged(final MouseEvent me) {
                final int w=JSL.getSize().width+me.getX();
                setDims(w-pressed.getX(), getSize().height);
                ZC.JCM.analyseResize((JCanvasPanel) ((JComponent) me.getSource()).getParent());
            }
        });

        decFormat=new DecimalFormat();
        final DecimalFormatSymbols dfs=new DecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        decFormat.setDecimalFormatSymbols(dfs);
    }

    private ExpressionObject createExpression() {
        final ExpressionObject o=new ExpressionObject(ZC.getConstruction(),
                0, 0);
        o.setDefaults();
        o.setSuperHidden(true);
        o.setOwnedByControl(true);
        ZC.getConstruction().add(o);

        if (JZirkelCanvas.getCurrentJZF()!=null) {
            final int i=JZirkelCanvas.getCurrentJZF().getPointLabel().getCurrentLetterSetCode();
            final boolean b=Global.getParameter("options.point.shownames",
                    false);
            Global.setParameter("options.point.shownames", true);
            final String s=JZirkelCanvas.getCurrentJZF().getPointLabel().setLetterSet(JPointName.minLettersSetCode);

            Global.setParameter("options.point.shownames", b);
            JZirkelCanvas.getCurrentJZF().getPointLabel().setLetterSet(i);
            o.setName(s);
        }
        return o;
    }

    public int StringwWidth(final String s) {
        // FontMetrics fm = getFontMetrics(getFont());
        final FontMetrics fm=getFontMetrics(new Font(Global.GlobalFont, 0,
                COMFONTSIZE));

        return fm.stringWidth(s);
    }

    // MUST BE OVERRIDE :
    public double getVal() {
        return 0.0;
    }

    public void setOnlyValue(final double x) {
        decFormat.setMaximumFractionDigits(Global.getParameter("digits.edit", 5));
        lbl_val=String.valueOf(decFormat.format(x));
    }

    public void setVal(final double x) {
        setOnlyValue(x);
        setVal(String.valueOf(x));
    }

    public void setVal(final String s) {
        JCPlabel.setText(goodLabel());
        try {
            O.setExpression(s, ZC.getConstruction());
            ZC.recompute();
            setDims();
            // ZC.validate();
            // ZC.repaint();
        } catch (final Exception ex) {
        }
    }

    public void setDims(final int x, final int y, final int w, final int h) {
        final int W=w+StringwWidth(JCPlabel.getText())+15;
        fixsize(JCPlabel, StringwWidth(JCPlabel.getText()), h);
        fixsize(JCPresize, 15, h);
        fixsize(JSL, w, h);
        revalidate();
        setBounds(x, y, W, h);
        ZC.validate();
        ZC.repaint();
    }

    public void setDims(final int w, final int h) {
        final int x=getLocation().x;
        final int y=getLocation().y;
        setDims(x, y, w, h);
    }

    public void setDims() {
        setDims(JSL.getSize().width, JSL.getSize().height);
    }

    public void grow(final int w, final int h) {
        setDims(JSL.getSize().width+w, JSL.getSize().height+h);
    }

    public String getComment() {
        return lbl_com;
    }

    public void setComment(final String s) {
        lbl_com=s;
        JCPlabel.setText(goodLabel());
        setDims();
    }

    public String getUnit() {
        return lbl_unit;
    }

    public void setUnit(final String s) {
        lbl_unit=s;
        JCPlabel.setText(goodLabel());
        setDims();
    }

    public void setShowComment(final boolean b) {
        showcom=b;
        JCPlabel.setText(goodLabel());
        setDims();
    }

    public void setShowVal(final boolean b) {
        showval=b;
        JCPlabel.setText(goodLabel());
        setDims();
    }

    public void setShowUnit(final boolean b) {
        showunit=b;
        JCPlabel.setText(goodLabel());
        setDims();
    }

    public boolean getShowComment() {
        return showcom;
    }

    public boolean getShowVal() {
        return showval;
    }

    public boolean getShowUnit() {
        return showunit;
    }

    public String goodLabel() {
        String lbl="";
        if (showcom) {
            lbl+=lbl_com;
        }
        if (showval) {
            lbl+=lbl_val;
        }
        if (showunit) {
            lbl+=lbl_unit;
        }
        return lbl;
    }

    static public void fixsize(final JComponent cp, final int w, final int h) {
        final Dimension d=new Dimension(w, h);
        cp.setMaximumSize(d);
        cp.setMinimumSize(d);
        cp.setPreferredSize(d);
        cp.setSize(d);
    }

    public boolean isTargetMode(){
        return ((ZC.getTool() instanceof Scripts_SetMouseDrag)&&(this instanceof JCanvasButton));
    }

    public boolean isEditMode() {
        if (JZirkelCanvas.getCurrentJZF()==null) {
            return false;
        }
        boolean bool=(PaletteManager.isSelected("edit"))
                ||(PaletteManager.isSelected("ctrl_edit"));
        bool=(bool||(PaletteManager.isSelected("ctrl_slider")));
        bool=(bool||(PaletteManager.isSelected("ctrl_popup")));
        bool=(bool||(PaletteManager.isSelected("ctrl_chkbox")));
        bool=(bool||(PaletteManager.isSelected("ctrl_txtfield")));
        bool=(bool||(PaletteManager.isSelected("ctrl_button")));
        bool=(bool||(PaletteManager.isSelected("delete")));
        bool=(bool||(PaletteManager.isSelected("hide")));
        return bool;
    }

    public boolean isDeleteMode() {
        if (JZirkelCanvas.getCurrentJZF()==null) {
            return false;
        }
        return (PaletteManager.isSelected("delete"));
    }

    public boolean isHideToolSelected() {
        if (JZirkelCanvas.getCurrentJZF()==null) {
            return false;
        }
        return (PaletteManager.isSelected("hide"));
    }

    public boolean isHidden() {
        return (hidden||O.testConditional("hidden"));
    }

    public void setHidden(final boolean b) {
        hidden=b;
    }

    public void showBorder() {
        showborder2=true;
        repaint();
    }

    public void hideBorder() {
        showborder2=false;
        repaint();
    }

    public void showHandle() {
        showhandle=true;

        setDims();
    }

    public void hideHandle() {
        showhandle=false;
        setDims();
    }

    public void mouseClicked(final MouseEvent arg0) {
    }

    public void mousePressed(final MouseEvent e) {
        pressed=e;
        if (e.isPopupTrigger()) {
            return;
        }
        ZC.JCM.hideHandles(null);
    }

    public void mouseReleased(final MouseEvent e) {
        if (e.isPopupTrigger()) {
            return;
        }
        if ((!ZC.getShowHidden())&&(isHidden())) {

            return;
        }
        if (isHideToolSelected()) {
            setHidden(!isHidden());
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            showborder1=false;
            repaint();
        }
        if (isDeleteMode()) {
            ZC.JCM.removeControl(this);
        }

        ZC.JCM.hideBorders(this);
        if ((isEditMode())&&(!isHidden())) {
            showHandle();
            JPropertiesBar.EditObject(this);
        }
        if (isTargetMode()) {
            Scripts_SetMouseDrag tool=(Scripts_SetMouseDrag) ZC.getTool();
            tool.addFromControl(O, ZC);
            repaint();
        }

    }

    public void mouseEntered(final MouseEvent arg0) {
        if (isHidden()) {
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            showborder1=false;
            // repaint();
            return;
        }
        if (isTargetMode()||isEditMode()) {
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            showborder1=true;
            repaint();
        }
    }

    public void mouseExited(final MouseEvent arg0) {
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        showborder1=false;
        repaint();
    }

    public void mouseDragged(final MouseEvent me) {
        if (!isEditMode()) {
            return;
        }

        location=getLocation(location);
        int x=location.x-pressed.getX()+me.getX();
        int y=location.y-pressed.getY()+me.getY();
        if (x<0) {
            x=0;
        } else if (x+getSize().width>ZC.getSize().width) {
            x=ZC.getSize().width-getSize().width;
        }
        if (y<0) {
            y=0;
        } else if (y+getSize().height>ZC.getSize().height) {
            y=ZC.getSize().height-getSize().height;
        }
        setLocation(x, y);
        Toolkit.getDefaultToolkit().sync();
        ZC.JCM.analyseXY(this);
    }

    public void mouseMoved(final MouseEvent arg0) {
    }

    // Change event from JSlider :
    public void stateChanged(final ChangeEvent arg0) {
    }

    public void PrintXmlTags(final XmlWriter xml) {

        xml.printArg("Ename", ""+O.getName());
        xml.printArg("x", ""+getLocation().x);
        xml.printArg("y", ""+getLocation().y);
        xml.printArg("w", ""+JSL.getSize().width);
        xml.printArg("h", ""+JSL.getSize().height);
        xml.printArg("showC", ""+showcom);
        xml.printArg("showU", ""+showunit);
        xml.printArg("showV", ""+showval);
        xml.printArg("hidden", ""+hidden);
        xml.printArg("C", ""+lbl_com);
        xml.printArg("U", ""+lbl_unit);
        xml.printArg("V", ""+lbl_val);

    }
}
