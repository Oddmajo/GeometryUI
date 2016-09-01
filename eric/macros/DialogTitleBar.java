/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.macros;

import eric.GUI.palette.PaletteManager;
import eric.GUI.themes;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import eric.JEricPanel;
import javax.swing.SwingConstants;
import rene.gui.Global;

/**
 *
 * @author erichake
 */
/**
 * Title bar class
 */
public class DialogTitleBar extends JEricPanel {

    private static int title_height=17;
    private static int close_width=themes.getIcon("tab_close.png").getIconWidth();
    private static int close_height=themes.getIcon("tab_close.png").getIconHeight();
    private static int close_marginRight=3;
    private int title_width;
    private Image img=themes.getImage("PaletteTitleBarN.png");
    private JLabel title=new JLabel();
    private TopDialog DLOG;

    public void paintComponent(Graphics g) {
        if (DLOG instanceof CreateMacroDialog) {
            return;
        }
        Dimension d=getSize();
        g.drawImage(img, 0, 0, d.width, d.height, this);
    }

    public DialogTitleBar(TopDialog d, int w) {
        DLOG=d;
        title_width=w;
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setAlignmentX(0.0f);
        setOpaque(false);
        setFocusable(false);
        PaletteManager.fixsize(this, title_width, title_height);
        title.setText(Global.Loc("macro.creationdlog.title"));
        title.setFont(new java.awt.Font(Global.GlobalFont, 0, 11));
        title.setForeground(new Color(100, 100, 100));
        title.setHorizontalAlignment(SwingConstants.LEFT);
        PaletteManager.fixsize(title, title_width-close_width-close_marginRight, title_height);
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setVerticalAlignment(JLabel.CENTER);
        title.setAlignmentY(0.5f);

        add(title);
        add(new CreateMacroDialogCloseBtn());

        addMouseListener((MouseListener) DLOG);
        addMouseMotionListener((MouseMotionListener) DLOG);
    }

    public static int getTitleHeight() {
        return title_height;
    }

    class CreateMacroDialogCloseBtn extends JEricPanel implements MouseListener {

        private boolean over=false;

        @Override
        public void paintComponent(Graphics g) {
            Dimension d=getSize();
            if (over) {
                g.drawImage(themes.getImage("tab_close_over.png"), 0, 0, d.width, d.height,
                        this);
            } else {
                g.drawImage(themes.getImage("tab_close.png"), 0, 0, d.width, d.height,
                        this);
            }
        }

        public CreateMacroDialogCloseBtn() {
            setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            setAlignmentY(0.5f);
            setOpaque(false);
            PaletteManager.fixsize(this, close_width, close_height);
            addMouseListener(this);
        }

        @Override
        public void mousePressed(MouseEvent e) {
            over=false;
            if (DLOG!=null) {
                DLOG.exit();
            }
        }

        public void mouseEntered(MouseEvent e) {
            over=true;
            repaint();
        }

        public void mouseExited(MouseEvent e) {
            over=false;
            repaint();
        }

        public void mouseClicked(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }
    }
}

    