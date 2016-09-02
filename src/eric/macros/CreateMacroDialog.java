/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.macros;

import eric.GUI.palette.PaletteManager;
import eric.GUI.pipe_tools;
import eric.GUI.themes;
import eric.JZirkelCanvas;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JMenuItem;
import eric.JEricPanel;
import rene.zirkel.ZirkelCanvas;

/**
 *
 * @author erichake
 */
public class CreateMacroDialog extends JDialog implements MouseListener, MouseMotionListener, TopDialog {

    private static CreateMacroDialog MD;
    private static int content_height=50, content_width=205;
    private static int locmargin=50;
    private CreateMacroPanel CMP;
    private MacrosList MI;
    private MouseEvent pressed;
    private Point location;

    public CreateMacroDialog(MacrosList mi) {
        MD=this;
        MI=mi;
        CreateMacroDialogContentPane c=new CreateMacroDialogContentPane();
        setContentPane(c);
        c.addMouseListener(this);
        c.addMouseMotionListener(this);

        DialogTitleBar title=new DialogTitleBar(this, content_width);
        c.add(title);

        CMP=new CreateMacroPanel(this, MI, content_width, content_height);
        c.add(CMP);


        setUndecorated(true);
        setSize(content_width, content_height+DialogTitleBar.getTitleHeight());

        if (!pipe_tools.isApplet()) {
            setAlwaysOnTop(true);
        } else {
            addWindowListener(new WindowAdapter() {

                public void windowDeactivated(WindowEvent e) {
                    toFront();
                }
            });
        }
        setResizable(false);
        ZirkelCanvas zc=JZirkelCanvas.getCurrentZC();
        if (zc!=null) {
            this.setLocation(zc.getLocationOnScreen().x+locmargin, zc.getLocationOnScreen().y+locmargin);
        }

        pipe_tools.setMacroPanelKeyInputs();

        setVisible(true);
    }

    public static int getContentWidth() {
        return content_width;
    }

    public static void quit() {
        if (MD!=null) {
            MD.exit();
        }
    }

    public void exit() {
        setVisible(false);
        pipe_tools.removeMacroPanelKeyInputs();
        dispose();
        MD=null;
    }

    public class CreateMacroDialogContentPane extends JEricPanel {

        @Override
        public void paintComponent(final java.awt.Graphics g) {
            super.paintComponent(g);
            final java.awt.Dimension d=this.getSize();
            g.drawImage(themes.getImage("MCreateDlogBackground.gif"), 0, 0, d.width,
                    d.height, this);
        }

        public CreateMacroDialogContentPane() {
            super();
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        }
    }

    public class myJMenuItem extends JMenuItem implements ActionListener {

        public void action() {
        }

        public myJMenuItem(String s) {
            super(s);
            addActionListener(this);
            setFont(themes.TabMenusFont);
        }

        public void actionPerformed(ActionEvent e) {
            action();
        }
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mousePressed(MouseEvent me) {
        pressed=me;
    }

    public void mouseDragged(MouseEvent me) {
        location=getLocation(location);
        int x=location.x-pressed.getX()+me.getX();
        int y=location.y-pressed.getY()+me.getY();
        setLocation(x, y);
        Toolkit.getDefaultToolkit().sync();
    }

    public void mouseMoved(MouseEvent e) {
    }
}
