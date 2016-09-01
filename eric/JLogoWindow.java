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
package eric;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import javax.swing.SwingUtilities;
import rene.gui.Global;

public class JLogoWindow extends JFrame {

    private static int W=300, H=150;
    private static myBar Bar=null;
    private static int BW=280, BH=7, BY=110;// ProgressBar dimensions
    private static volatile int BStep=0;
    private static final double BStepMax=1996;
    /**
     *
     */
    private static final long serialVersionUID=1L;
    private final ImageIcon backimage=new ImageIcon(getClass().getResource("/rene/zirkel/logowindow.jpg"));

    public JLogoWindow(final boolean aboutBox) {
        final Dimension dim=Toolkit.getDefaultToolkit().getScreenSize();
        setSize(W, H);
        this.setLocation((dim.width-W)/2, (dim.height-H)/2);
        this.setUndecorated(true);
        setAlwaysOnTop(true);
        setPreferredSize(new java.awt.Dimension(W, H));
        setResizable(false);
        setContentPane(new myPanel());
        if (!aboutBox) {
            Bar=new myBar();
            getContentPane().add(Bar);
        }
    }
    static public JLogoWindow JLW;

    static public void progress(String message) {
        
//        System.out.print(BStep+": "+message);
//
//        int p=(int) Math.round((BW/BStepMax)*BStep);
//        System.out.println(" : "+p);
        if (Bar!=null) {
            BStep++;
            Bar.repaint();
        }
    }

    static public void ShowLogoWindow(final boolean aboutBox) {
        Thread logothread=new Thread() {

            @Override
            public void run() {
                JLW=new JLogoWindow(aboutBox);
                if (aboutBox) {
                    JLW.addWindowListener(new WindowAdapter() {

                        @Override
                        public void windowDeactivated(WindowEvent e) {
                            DisposeLogoWindow();
                        }
                    });
                    JLW.addMouseListener(new MouseAdapter() {

                        @Override
                        public void mousePressed(MouseEvent e) {
                            DisposeLogoWindow();
                        }
                    });
                }
                JLW.setVisible(true);


                // DrawString est extrêmement lent sur certaines machines virtuelles...
                // Cela oblige à certaines contorsions si on veut que le dialogue
                // apparaisse immédiatement avec l'image de fond, en attendant le texte...
                JLW.repaint();
                SwingUtilities.invokeLater(new Runnable() {

                    public void run() {
                        JLW.repaint();
                    }
                });
            }
        };
        logothread.setPriority(Thread.MAX_PRIORITY);
        logothread.start();

    }

    static public void DisposeLogoWindow() {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                if (JLW!=null) {
                    JLW.dispose();
                    JLW=null;
                    Bar=null;
                    BStep=0;
                }
            }
        });

    }

    class myBar extends javax.swing.JPanel {

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2=(Graphics2D) g;
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

            Dimension d=getSize();
            g2.setColor(new Color(255, 255, 255, 230));
            int p=(int) Math.round(((BW-4)/BStepMax)*BStep);
            g2.fillRect(2, 2, p, BH-4);
            g2.setColor(Color.white);
            g2.drawRect(0, 0, BW, BH);
        }

        public myBar() {
            setBounds((W-BW)/2, BY, BW, BH);
            setOpaque(false);
        }
    }

    class myPanel extends javax.swing.JPanel {

        private boolean firstPaint=true;

        public myPanel() {
            super();
            setLayout(null);
        }

        @Override
        public void paintComponent(Graphics g) {
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


            final java.awt.Dimension d=this.getSize();
            g2.drawImage(backimage.getImage(), 0, 0, d.width, d.height, this);

            if (firstPaint) {
                firstPaint=false;
            } else {
                paintStrings(g2);
            }

        }

        public void paintStrings(final Graphics2D g2) {
            try {
                g2.setFont(new Font("Dialog", Font.ITALIC, 13));
                FontMetrics fm=g2.getFontMetrics();
                String s=Global.Loc("splash.message");
                g2.setColor(new Color(191, 215, 255));
                int w=fm.stringWidth(s);
                g2.drawString(s, (W-w)/2, 60);

                g2.setFont(new Font("Dialog", Font.PLAIN, 12));
                fm=g2.getFontMetrics();
                s=Global.name("version")+" "+Global.name("program.version");
                g2.setColor(Color.white);
                w=fm.stringWidth(s);
                g2.drawString(s, (W-w)/2, 85);

                s="Java : "+System.getProperty("java.version");
                g2.setColor(Color.white);
                w=fm.stringWidth(s);
                g2.drawString(s, (W-w)/2, 100);
            } catch (Exception e) {
            }
        }
    }
}
