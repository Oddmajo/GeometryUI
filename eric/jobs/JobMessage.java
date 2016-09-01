/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.jobs;

import eric.JZirkelCanvas;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import eric.JEricPanel;
import rene.zirkel.ZirkelCanvas;

/**
 *
 * @author erichake
 */
public class JobMessage {

    static private int D_WIDTH=300;
    static private int D_HEIGHT=70;

    public static void showMessage(String m) {
        showMessage(m, D_WIDTH, D_HEIGHT);
    }

    public static void showMessage(String m, int w, int h) {
        ZirkelCanvas zc=JZirkelCanvas.getCurrentZC();
        if (zc!=null) {
            JOptionPane.showMessageDialog(zc, getPanel("<html>"+m+"</html>", w, h));
        }
    }


    private static JEricPanel getPanel(String m, int w, int h) {
        JEricPanel jp=new JEricPanel();
        jp.setLayout(new BoxLayout(jp, BoxLayout.X_AXIS));
        jp.setOpaque(false);
        JLabel label=new JLabel(m);
        jp.add(label);
        fixsize(jp, w, h);
        return jp;
    }

    private static void fixsize(JComponent jc, int w, int h) {
        Dimension d=new Dimension(w, h);
        jc.setSize(d);
        jc.setMaximumSize(d);
        jc.setMinimumSize(d);
        jc.setPreferredSize(d);
    }
}
