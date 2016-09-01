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
package eric.JSprogram;

import eric.GUI.themes;
import eric.JZirkelCanvas;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;

import javax.swing.BorderFactory;
import javax.swing.GrayFilter;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import rene.zirkel.ZirkelCanvas;

public class JSButton extends JButton implements MouseListener {

//    private final ImageIcon myimage;
    int iconsize=24;
    boolean isEntered=false; // Mouseover ?
    boolean isDisabled;
    private String Name;

    // String Shortcut;
    @Override
    public void paintComponent(final java.awt.Graphics g) {
        final java.awt.Dimension d=this.getSize();
        final int w=d.width;
        final int h=d.height;

        if (g==null) {
            return;
        }

        super.paintComponent(g);
        final Graphics2D g2=(Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
                RenderingHints.VALUE_STROKE_PURE);
        if (isDisabled) {
            final ImageFilter filter=new GrayFilter(true, 60);
            final Image disImage=createImage(new FilteredImageSource(themes.getPaletteImage(Name).getSource(), filter));
            final ImageIcon myicn=new ImageIcon(disImage);
            g2.drawImage(myicn.getImage(), 0, 0, w, h, this);
            return;
        }
        //not elegant... just for one icon...
        if(Name.equals("monkey")){
            g2.drawImage(themes.resizeExistingIcon("/eric/GUI/icons/themes/common/monkeybtn_off.png", 22, 15).getImage(), 1, 4, 22, 15, this);
        } else {
            g2.drawImage(themes.getPaletteImage(Name), 0, 0, w, h, this);
        }

        if (isEntered) {
            final AlphaComposite ac=AlphaComposite.getInstance(
                    AlphaComposite.SRC_OVER, 0.1f);
            g2.setComposite(ac);
            g2.setColor(new Color(0, 0, 80));
            final Stroke stroke=new BasicStroke(3f);
            g2.setStroke(stroke);
            g2.drawRect(1, 1, w-2, h-2);

        }
    }

    public JSButton(String name, int size,boolean enabled) {
        isDisabled=!enabled;
        iconsize=size;
        Name=name;
        this.setBorder(BorderFactory.createEmptyBorder());
        fixsize(iconsize);
        this.addMouseListener(this);
        this.setContentAreaFilled(false);
        this.setOpaque(false);
        this.setFocusable(false);
    }



    private void fixsize(final int sze) {
        final Dimension d=new Dimension(sze, sze);
        this.setMaximumSize(d);
        this.setMinimumSize(d);
        this.setPreferredSize(d);
        this.setSize(d);
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(final MouseEvent e) {
        isEntered=true;
        repaint();

    }

    public void mouseExited(final MouseEvent e) {
        isEntered=false;
        repaint();

    }
}


