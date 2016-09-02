/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package eric.GUI.ZDialog;

import eric.GUI.themes;
import eric.JZirkelCanvas;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import rene.zirkel.ZirkelCanvas;

/**
 *
 * @author erichake
 */
public class ZCheckBox extends JLabel {

        private boolean selected;

        @Override
        public void paint(Graphics g) {

                super.paintComponent(g);
                if (selected) {
                    g.drawImage(themes.getImage("chkboxON.gif"), 0, 2, this);
                } else {
                    g.drawImage(themes.getImage("chkboxOFF.gif"), 0, 2, this);
                }

        }

        public ZCheckBox(String lbl, boolean value) {
            super(lbl);
            selected=value;
            setFont(ZTools.ZCheckBoxFont);
            setFocusable(false);
            setIcon(themes.getIcon("pixel"));
            setIconTextGap(20);
            setVerticalTextPosition(SwingConstants.CENTER);
            setVerticalAlignment(SwingConstants.CENTER);
            addMouseListener(new MouseAdapter() {

                @Override
                public void mousePressed(MouseEvent e) {
                    setSelected(!selected);
                    action();
                }
            });
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean b) {
            selected=b;
            ZirkelCanvas zc=JZirkelCanvas.getCurrentZC();
            if (zc!=null) {
                zc.repaint();
            }
        }

        public void action() {
        }
    }