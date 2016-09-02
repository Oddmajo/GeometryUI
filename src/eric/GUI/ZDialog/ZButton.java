/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.GUI.ZDialog;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import rene.gui.Global;

/**
 *
 * @author erichake
 */
public class ZButton extends JButton {

    @Override
    public void paint(Graphics g) {
        super.paintComponent(g);
    }

    public ZButton(String lbl) {
        super(lbl);
        setOpaque(false);
        setFont(new Font(Global.GlobalFont, 0, 11));
        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                action();
            }
        });
    }

    public void action() {
    }
    
    public void pressed(final ZButton button) {
        class pressed implements Runnable {
            
            @Override
            public void run() {
                button.setEnabled(false);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {}
                button.setEnabled(true);
            }
        }
        new Thread(new pressed()).start();
    }
}
