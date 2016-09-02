/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.JSprogram;

import eric.GUI.themes;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.net.URL;
import javax.swing.ImageIcon;
import eric.JEricPanel;

/**
 *
 * @author erichake
 */
public class myJVerticalSeparatorPanel extends JEricPanel {

    private ImageIcon myimage;

    @Override
    public void paintComponent(Graphics g) {
        final java.awt.Dimension d=this.getSize();
        final int w=d.width;
        final int h=d.height;

        final Graphics2D g2=(Graphics2D) g;

//        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
//                RenderingHints.VALUE_ANTIALIAS_ON);
//        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
//                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
//        g2.setRenderingHint(RenderingHints.KEY_RENDERING,
//                RenderingHints.VALUE_RENDER_QUALITY);
//        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
//                RenderingHints.VALUE_INTERPOLATION_BICUBIC);
//        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
//                RenderingHints.VALUE_STROKE_PURE);


        g2.drawImage(myimage.getImage(), 0, 0, w, h, this);
    }

    public myJVerticalSeparatorPanel() {
        super();
        myimage=themes.getIcon("verticalseparator.png");
    }
}
