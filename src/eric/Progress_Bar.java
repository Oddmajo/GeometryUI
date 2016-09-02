/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * JProgressBar.java
 *
 * Created on 27 déc. 2009, 23:53:56
 */
package eric;

import eric.GUI.pipe_tools;
import eric.GUI.themes;
import eric.GUI.windowComponent;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JLabel;
import eric.JEricPanel;
import javax.swing.JProgressBar;
import rene.gui.Global;

/**
 *
 * @author erichake
 */
public class Progress_Bar extends javax.swing.JFrame {

    private static JProgressBar progressbar=null;
    private static Progress_Bar me=null;
    private static int value=0;

//    public void paint(Graphics g) {
//
//
//        super.paint(g);
//    }
    public static void setValue(int i) {
        if (progressbar!=null) {
            progressbar.setValue(i);
            me.paint(me.getGraphics());
        }
    }

    public static void nextValue() {
        setValue(value++);
    }

    public static void create(String message, int min, int max) {
        close();
        me=new Progress_Bar(message, min, max);
        me.setLocationRelativeTo(pipe_tools.getCanvasPanel());
        me.setVisible(true);
        me.paint(me.getGraphics());
    }

    public static void close() {
        progressbar=null;
        if (me!=null) {
            me.setVisible(false);
            me.dispose();
            me=null;
        }
    }

    /** Creates new form JProgressBar */
    public Progress_Bar(String message, int min, int max) {
        value=min;
//        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        if (!pipe_tools.isApplet()) setAlwaysOnTop(true);
        setBackground(new java.awt.Color(255, 255, 255));
        setBounds(new java.awt.Rectangle(0, 0, 400, 64));
        setFocusCycleRoot(false);
        setFocusTraversalPolicyProvider(true);
        setFocusable(false);
        setFocusableWindowState(false);
        setMinimumSize(new java.awt.Dimension(400, 64));
        setUndecorated(true);
        JEricPanel content=new JEricPanel() {

            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Dimension d=getSize();
                g.drawImage(themes.getImage("progressback.png"), 0, 0, d.width, d.height, this);
            }
        };
        content.setLayout(null);
        content.setOpaque(false);

        progressbar=new JProgressBar(min, max);
        progressbar.setPreferredSize(new java.awt.Dimension(150, 20));
        content.add(progressbar);
        progressbar.setBounds(10, 40, 380, 10);
        JLabel jLabel1=new javax.swing.JLabel();
        jLabel1.setText(message);
        content.add(jLabel1);
        jLabel1.setBounds(10, 10, 380, 16);

        setContentPane(content);
        pack();

    }
}
