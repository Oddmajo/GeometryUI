/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.GUI.window;

import eric.GUI.pipe_tools;
import eric.GUI.themes;
import eric.JZirkelCanvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 *
 * @author erichake
 */
public class tab_btn_label extends JLabel {

    private static final int fileMarginLeft=20, workbookMarginLeft=5;
    private static int marginLeft=fileMarginLeft, marginTop=2, marginRight=15;
    private MouseAdapter ma=null;
    private boolean over=false;
    private String label="";

    public void init() {
        int w=tab_main_panel.visibleWidth((tab_btn) getParent());
        setBounds(marginLeft,
                marginTop,
                w-marginLeft-marginRight,
                getParent().getSize().height-2*marginTop);
        String lbl=(tab_main_panel.rightcut((tab_btn) getParent()))?label+"...":label;
        setText(lbl);
    }

    public void setLabelText(String s) {
        super.setText(label=s);
    }

    public String getLabelText() {
        return label;
    }

    public tab_btn_label(String lbl) {
        super(lbl);
        label=lbl;
        setFont(themes.TabFont);
        setVerticalTextPosition(SwingConstants.TOP);
        setBorder(null);
        setOpaque(false);
    }

    public void setChanged(boolean b) {
        Color col=Color.black;
        if (b) {
            col=themes.TabChangedColor;
        }
        marginLeft=(JZirkelCanvas.isWorkBook())?workbookMarginLeft:fileMarginLeft;
        setForeground(col);
        init();
        repaint();
    }

    public void implementMouseAdapter() {
        tab_btn btn=(tab_btn) getParent();
        tab_main_panel mp=tab_main_panel.getme();
        tab_control_panel tcp=tab_control_panel.getme();
        final ContentPane pane=pipe_tools.getContent();
        ma=new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                pane.requestFocus();
            }
        };
        for (int i=0; i<pane.getComponentCount(); i++) {
            Component cp=pane.getComponent(i);
            cp.addMouseListener(ma);
        }
        for (int i=0; i<mp.getComponentCount(); i++) {
            Component cp=mp.getComponent(i);
            cp.addMouseListener(ma);
        }
        for (int i=0; i<tcp.getComponentCount(); i++) {
            Component cp=tcp.getComponent(i);
            cp.addMouseListener(ma);
        }

        pane.addMouseListener(ma);
    }

    public void removeMouseAdapter() {
        if (ma!=null) {
            tab_btn btn=(tab_btn) getParent();
            tab_main_panel mp=tab_main_panel.getme();
            tab_control_panel tcp=tab_control_panel.getme();
            ContentPane pane=pipe_tools.getContent();
            for (int i=0; i<pane.getComponentCount(); i++) {
                Component cp=pane.getComponent(i);
                cp.removeMouseListener(ma);
            }
            for (int i=0; i<mp.getComponentCount(); i++) {
                Component cp=mp.getComponent(i);
                cp.removeMouseListener(ma);
            }
            for (int i=0; i<tcp.getComponentCount(); i++) {
                Component cp=tcp.getComponent(i);
                cp.removeMouseListener(ma);
            }
            pane.removeMouseListener(ma);
            ma=null;
        }
    }

    public void edit() {
        final tab_btn btn=(tab_btn) getParent();
        final tab_btn_label myself=this;
        final JTextField jtf=new JTextField(label);
        jtf.setBounds(getBounds());
        jtf.setFont(themes.TabFont);
        setVisible(false);
        implementMouseAdapter();
        jtf.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(final KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_ENTER) {
                    myself.setVisible(true);
                    btn.remove(jtf);
                    btn.revalidate();
                    btn.repaint();
                    removeMouseAdapter();
                } else if (e.getKeyCode()==KeyEvent.VK_ESCAPE) {
                    myself.setVisible(true);
                    jtf.setText(label);
                    btn.remove(jtf);
                    btn.revalidate();
                    btn.repaint();
                    removeMouseAdapter();
                }
            }
        });
        jtf.addFocusListener(new FocusAdapter() {

            @Override
            public void focusLost(final FocusEvent e) {
                myself.setLabelText(pipe_tools.processTabName(jtf.getText()));
		tab_main_panel.getActiveBtn().setToolTip(label+".zir");
                removeMouseAdapter();
                myself.setVisible(true);
                btn.remove(jtf);
                btn.revalidate();
                btn.repaint();
                removeMouseAdapter();
                tab_main_panel.initBTNS(null);
            }
        });
        btn.add(jtf);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                jtf.selectAll();
                jtf.requestFocus();
            }
        });

    }
}
