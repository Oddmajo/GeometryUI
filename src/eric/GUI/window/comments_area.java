/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.GUI.window;

import eric.GUI.pipe_tools;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicScrollBarUI;
import rene.gui.Global;

/**
 *
 * @author erichake
 */
public class comments_area extends JScrollPane {

    private static int margins=3;
    private static int marginLeft=0, marginTop=0, marginRight=0;
    private static int scrollBarWidth=15;
    private static Font commentFont=new Font("Verdana", 0, 14);
    private static Color commentColor=new Color(20, 20, 20);
    JLabel text_area;
    private JLabel inviteLabel;
    private static MouseAdapter ma=null;
    private String label="";

    public comments_area() {
        super();
        text_area=new JLabel("") {

            @Override
            public Dimension getPreferredSize() {
                Dimension superPreferred=super.getPreferredSize();
                Container p=getParent();
                return new Dimension(p.getBounds().width-2*margins-scrollBarWidth, superPreferred.height);
            }
        };
        setViewportView(text_area);
        addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                edit();
            }
        });
        text_area.setOpaque(false);
        text_area.setVerticalTextPosition(SwingConstants.TOP);
        text_area.setHorizontalTextPosition(SwingConstants.LEFT);
        text_area.setHorizontalAlignment(SwingConstants.LEFT);
        text_area.setVerticalAlignment(SwingConstants.TOP);
        text_area.setFont(commentFont);
        text_area.setForeground(commentColor);
        text_area.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        setOpaque(false);
        this.getViewport().setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(margins, margins, margins, margins));

        JScrollBar vsb=getVerticalScrollBar();
        Dimension newsbsize=new Dimension(scrollBarWidth, 0);
        vsb.setUI(new BasicScrollBarUI());
        vsb.setPreferredSize(newsbsize);
        vsb.setUnitIncrement(16);
        setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);


        inviteLabel=new JLabel(Global.Loc("comment.emptycomment"));
        inviteLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        inviteLabel.setVerticalTextPosition(SwingConstants.CENTER);
        inviteLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        inviteLabel.setHorizontalAlignment(SwingConstants.CENTER);
        inviteLabel.setVerticalAlignment(SwingConstants.CENTER);
        inviteLabel.setOpaque(false);
        inviteLabel.setFont(commentFont);
        inviteLabel.setForeground(commentColor);
        text_area.add(inviteLabel);
        setInviteLabel();
        ma=new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                final Component cp=e.getComponent();
                SwingUtilities.invokeLater(new Runnable() {

                    public void run() {
                        cp.requestFocus();
                    }
                });
            }
        };
    }

    void init() {
        Container p=getParent();
        if (p!=null) {
            setBounds(0, 0, p.getBounds().width, p.getBounds().height);
            inviteLabel.setBounds(0, 0, p.getBounds().width-2*margins-scrollBarWidth, p.getBounds().height-2*margins);
            setLabelText(label);
        }

    }

    public void setLabelText(String s) {
        Container p=getParent();
        if (p!=null) {
            label=s;
            // I really don't know why, but it seems that the width is increased
            // by 30%. E.g. ask for 500px will give a 650px div...
            int pix=10*(p.getBounds().width-2*margins-scrollBarWidth)/13-1;
            text_area.setText("<html><div style=\"width: "+pix+"px; text-align: justify;\">"+s+"</div></html>");
            setInviteLabel();
        }
    }

    public String getLabelText() {
        return label;
    }

    public void setInviteLabel() {
        inviteLabel.setVisible(label.equals(""));
    }

    public void implementMouseAdapter() {
        final ContentPane pane=pipe_tools.getContent();
        implementMouseAdapter(pane);
    }

    public void implementMouseAdapter(Container comp) {
        if (!(comp instanceof JLabel)) {
            comp.addMouseListener(ma);
        }
        for (int i=0; i<comp.getComponentCount(); i++) {
            Container cp=(Container) comp.getComponent(i);
            implementMouseAdapter(cp);
        }
    }

    public void removeMouseAdapter() {
        if (ma!=null) {
            ContentPane pane=pipe_tools.getContent();
            removeMouseAdapter(pane);
        }
    }

    public void removeMouseAdapter(Container comp) {
        if (!(comp instanceof JLabel)) {
            comp.removeMouseListener(ma);
        }
        for (int i=0; i<comp.getComponentCount(); i++) {
            Container cp=(Container) comp.getComponent(i);
            removeMouseAdapter(cp);
        }
    }

    public void edit() {
        final comments com=(comments) getParent();
        final JTextArea jtf=new JTextArea();
        jtf.setOpaque(false);
        jtf.setText(label);
        jtf.setBounds(getBounds());
        jtf.setFont(commentFont);
        jtf.setForeground(commentColor);
        jtf.setLineWrap(true);
        com.removeMouseListener(com);
        implementMouseAdapter();
        jtf.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(final KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_ENTER) {
                    jtf.replaceSelection("<br>");
//                    e.consume();
                } else if (e.getKeyCode()==KeyEvent.VK_ESCAPE) {
                    removeMouseAdapter();
                    com.remove(jtf);
                    com.revalidate();
                    com.repaint();
                }
            }

            public void keyReleased(final KeyEvent e) {
                pipe_tools.setComments(jtf.getText());
                setLabelText(jtf.getText());
            }
        });
        jtf.addFocusListener(new FocusAdapter() {

            @Override
            public void focusLost(final FocusEvent e) {
//                setLabelText(jtf.getText());
                setViewportView(text_area);
                com.remove(jtf);
                com.revalidate();
                com.repaint();
                com.addMouseListener(com);
                removeMouseAdapter();
                revalidate();
                validate();
                repaint();
            }
        });
        setViewportView(jtf);
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                jtf.requestFocus();
            }
        });

    }
}


