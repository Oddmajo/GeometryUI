/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.restrict;

import eric.GUI.palette.PaletteManager;
import eric.GUI.themes;
import eric.GUI.windowComponent;
import eric.JZirkelCanvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import javax.swing.GrayFilter;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import rene.zirkel.ZirkelCanvas;

/**
 *
 * @author erichake
 */
public class RestrictPanelIcon extends JCheckBox implements MouseListener, ItemListener {

    private Image image;
    private String name;
    private RestrictPanelIconsLineTitle title;
    private int W=60, H=32, MARGINW=18;
    private static boolean select=false;

    @Override
    public void paintComponent(Graphics g) {

        final Graphics2D g2=windowComponent.getGraphics2D(g);
        if (isEnabled()) {
            g2.drawImage(image, MARGINW, 0, this);
        } else {
            final ImageFilter filter=new GrayFilter(true, 50);
            final Image disImage=createImage(new FilteredImageSource(image.getSource(), filter));
            final ImageIcon myicn=new ImageIcon(disImage);
            g2.drawImage(myicn.getImage(), MARGINW, 0, this);
        }
        super.paintComponent(g);
    }

    public RestrictPanelIcon(RestrictPanelIconsLineTitle ttle, String nme) {
        name=nme;
        title=ttle;
        PaletteManager.fixsize(this, W, H);
        image=themes.getPaletteImage(nme);
        putClientProperty("JComponent.sizeVariant", "mini");
        setFocusable(false);
        setOpaque(false);
        setVerticalAlignment(SwingConstants.BOTTOM);
        addMouseListener(this);
        addItemListener(this);
        initState();
    }

    public void initState() {
        ZirkelCanvas zc=JZirkelCanvas.getCurrentZC();
        if (zc!=null) {
            setSelected(!zc.isHiddenItem(name));
        }
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        if (e.getButton()==MouseEvent.BUTTON3) {
            select=!isSelected();
            setSelected(select);
        }
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
        if (e.getButton()==MouseEvent.BUTTON3) {
            setSelected(select);
        }
    }

    public void mouseExited(MouseEvent e) {
    }

    public void itemStateChanged(ItemEvent e) {
        ZirkelCanvas zc=JZirkelCanvas.getCurrentZC();
        if (zc!=null) {
            if (isSelected()) {
                zc.removeHiddenItem(name);
                if (!title.isSelected()) {
                    title.setSelected(false, true);
                }
            } else {
                zc.addHiddenItem(name);
                title.uncheckIfAlone();
            }
        }

        PaletteManager.init();
    }
}
