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

import eric.GUI.palette.PaletteManager;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import eric.JEricPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import rene.gui.Global;

/**
 * 
 * @author erichake
 */
class JCharacterPalette extends JDialog implements MouseListener {

    /**
     *
     */
    
    static int GENERICCODE=10;
    JEricPanel Content, TabZone, ButtonZone;
    JButton LBL;
    JZirkelCanvas JZF;
    int Linemax=10;
    int Btnsize=18;
    int Tabbtnsize=30;

    public JCharacterPalette(final JZirkelCanvas jzf, final JButton lbl) {
        // super(jzf.JPM.MainPalette,true);
        super();
        JZF=jzf;

        LBL=lbl;
        JTabBtn.Btns.clear();
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Caracter Palette");
        this.setUndecorated(true);
        Content=new JContent(Tabbtnsize);
        // Content.setLayout(new BoxLayout(Content,BoxLayout.X_AXIS));
        // Content.setBackground(new Color(50,50,50));
        // Content.setBorder(BorderFactory.createLineBorder(new
        // Color(0,0,0),1));
        this.setContentPane(Content);
        this.setAlwaysOnTop(true);
        this.toFront();
        ButtonZone=getnewcol();
        TabZone=getnewcol();
        TabZone.setOpaque(false);
        initJTab();
        initJButtons();
        Content.add(TabZone);
        Content.add(ButtonZone);
        this.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mousePressed(final java.awt.event.MouseEvent evt) {
                JZF.getPointLabel().setEnabledJLabel(true);
                setVisible(false);
                dispose();
            }
        });
        this.addWindowListener(new java.awt.event.WindowAdapter() {

            @Override
            public void windowDeactivated(final java.awt.event.WindowEvent evt) {
                JZF.getPointLabel().setEnabledJLabel(true);
                setVisible(false);
                dispose();
            }
        });

        this.pack();
        // JPointLabel.fixsize(Content,Content.getSize().width+1,Content.getSize().height+1);
        // JPointLabel.fixsize(titles,this.getSize().width,20);
        // this.pack();
        final int x=LBL.getLocationOnScreen().x+LBL.getSize().width-this.getSize().width;
        final int y=LBL.getLocationOnScreen().y+LBL.getSize().height;
        this.setLocation(x, y);

        JZF.getPointLabel().setEnabledJLabel(false);

        this.setVisible(true);

    }

    private JEricPanel initJTab() {
        JEricPanel col=null;
        JButton mybtn=null;

        col=new JEricPanel();
        col.setLayout(new BoxLayout(col, BoxLayout.Y_AXIS));
        col.setAlignmentY(0f);
        mybtn=new JTabBtn(this, 0, "D");
        JPointName.fixsize(mybtn, Tabbtnsize, Tabbtnsize);
        col.add(mybtn);
        mybtn=new JTabBtn(this, 1, "d");
        JPointName.fixsize(mybtn, Tabbtnsize, Tabbtnsize);
        col.add(mybtn);
        mybtn=new JTabBtn(this, 2, "\u0394");
        JPointName.fixsize(mybtn, Tabbtnsize, Tabbtnsize);
        col.add(mybtn);
        mybtn=new JTabBtn(this, 3, "\u03B4");
        JPointName.fixsize(mybtn, Tabbtnsize, Tabbtnsize);
        col.add(mybtn);
        mybtn=new JTabBtn(this, GENERICCODE, "P1");
        JPointName.fixsize(mybtn, Tabbtnsize, Tabbtnsize);
        col.add(mybtn);
        col.setOpaque(false);
        JTabBtn.setSelectedJTitle(JZF.getPointLabel().getCurrentLetterSetCode());
        TabZone.add(col);
        // JPointLabel.fixsize(TabZone,Tabwidth,50);
        return col;
    }

    private JButton getJButton(final String s) {
        final JButton mybtn=new JButton(s);
        mybtn.setBorder(BorderFactory.createEmptyBorder());
        mybtn.setBorderPainted(false);
        mybtn.setFocusPainted(false);
        mybtn.setFocusable(false);
        mybtn.setBackground(new Color(228, 222, 255));
        mybtn.setOpaque(false);
        mybtn.setContentAreaFilled(false);
        if (JZF.getPointLabel().isLetterAccepted(mybtn.getText())) {
            mybtn.addMouseListener(this);
        } else {
            mybtn.setEnabled(false);
            mybtn.addMouseListener(new java.awt.event.MouseAdapter() {

                @Override
                public void mousePressed(final java.awt.event.MouseEvent evt) {
                    JZF.getPointLabel().setEnabledJLabel(true);
                    setVisible(false);
                    dispose();
                }
            });
        }
        mybtn.setFont(new java.awt.Font(Global.GlobalFont, 1, 11));
        mybtn.setForeground(new Color(20, 20, 20));
        JPointName.fixsize(mybtn, 18, 18);
        return mybtn;
    }

    private static JEricPanel getnewline() {
        final JEricPanel line=new JEricPanel();
        line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));
        line.setAlignmentX(0f);
        line.setOpaque(false);
        return line;
    }

    private static JEricPanel getnewcol() {
        final JEricPanel col=new JEricPanel();
        col.setLayout(new BoxLayout(col, BoxLayout.Y_AXIS));
        col.setAlignmentY(0f);
        col.setOpaque(true);
        col.setBackground(new Color(250, 250, 250));
        return col;
    }

    void initJButtons() {
        JEricPanel line=null;

        final String letters=JZF.getPointLabel().getCurrentLetterSet();
        ButtonZone.removeAll();
        for (int i=0; i<letters.length(); i++) {
            if ((i%Linemax)==0) {
                ButtonZone.add(line=getnewline());
            }
            line.add(getJButton(letters.substring(i, i+1)));
        }
        ButtonZone.add(new JSeparator());
        for (int i=0; i<letters.length(); i++) {
            if ((i%Linemax)==0) {
                ButtonZone.add(line=getnewline());
            }
            line.add(getJButton(letters.substring(i, i+1)+"'"));
        }
        ButtonZone.add(new JSeparator());
        for (int i=0; i<letters.length(); i++) {
            if ((i%Linemax)==0) {
                ButtonZone.add(line=getnewline());
            }
            line.add(getJButton(letters.substring(i, i+1)+"''"));
        }
    }

    public void mouseClicked(final MouseEvent e) {
    }

    public void mouseReleased(final MouseEvent e) {
        if (e.getSource()!=null) {
            if (!Global.getParameter("options.point.shownames", false)) {
                Global.setParameter("options.point.shownames", true);
                final String iconname=PaletteManager.geomSelectedIcon();
                if (iconname.equals("point")) {
                    PaletteManager.setSelected_with_clic("showname", true);
                }
            }

            final JButton btn=(JButton) e.getSource();
            final String s=btn.getText();
            if (JZF.getPointLabel().isLetterAllowed(s)) {
                JZF.getPointLabel().setSuffixChar("");
                JZF.getPointLabel().setStartLetter(s);
            } else if (s.endsWith("''")) {
                JZF.getPointLabel().setSuffixChar("''");
                JZF.getPointLabel().setStartLetter(s.substring(0, 1));
            } else if (s.endsWith("'")) {
                JZF.getPointLabel().setSuffixChar("'");
                JZF.getPointLabel().setStartLetter(s.substring(0, 1));
            }
            JZF.getPointLabel().setEnabledJLabel(true);
            setVisible(false);
            dispose();

        }
    }

    public void mousePressed(final MouseEvent e) {
    }

    public void mouseEntered(final MouseEvent e) {
        if (e.getSource()!=null) {
            final JButton btn=(JButton) e.getSource();
            btn.setBackground(new Color(236, 236, 249));
            btn.setOpaque(true);
            btn.setContentAreaFilled(true);
        }
    }

    public void mouseExited(final MouseEvent e) {
        if (e.getSource()!=null) {
            final JButton btn=(JButton) e.getSource();
            btn.setOpaque(false);
            btn.setContentAreaFilled(false);
        }
    }
}

class JTabBtn extends JButton {

    /**
     *
     */
    
    public static ArrayList Btns=new ArrayList<JTabBtn>();
    int SetCode=0;
    JCharacterPalette JCP;

    public JTabBtn(final JCharacterPalette jcp, final int code, final String s) {
        SetCode=code;
        JCP=jcp;
        setBorder(BorderFactory.createEmptyBorder());
        setBorderPainted(false);
        setFocusPainted(false);
        setFocusable(false);
        // setBackground(new Color(240,240,240));
        setOpaque(false);
        setContentAreaFilled(false);
        setHorizontalAlignment(SwingConstants.CENTER);
        setFont(new java.awt.Font(Global.GlobalFont, 1, 13));
        addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseReleased(final java.awt.event.MouseEvent evt) {
                if (SetCode==JCharacterPalette.GENERICCODE) {
                    Global.setParameter("options.point.shownames", false);
                    JCP.JZF.getPointLabel().getBetterName(null, true);
                    JCP.dispose();
                    return;
                }
                JCP.JZF.getPointLabel().setLetterSet(SetCode);
                JCP.initJButtons();
                disableAllJTitles();
                setEnabled(true);
                JCP.pack();
                JCP.repaint();
            }
        });
        setText(s);
        Btns.add(this);
    }

    static void disableAllJTitles() {
        for (int i=0; i<Btns.size(); i++) {
            final JButton btn=(JButton) Btns.get(i);
            btn.setEnabled(false);
        }
    }

    static void setSelectedJTitle(final int code) {
        disableAllJTitles();
        if (code<Btns.size()) {
            final JButton btn=(JButton) Btns.get(code);
            btn.setEnabled(true);
        }
    }
}

class JContent extends JEricPanel {

    /**
     *
     */
    
    int TS=0;

    @Override
    public void paintComponent(final java.awt.Graphics g) {
        super.paintComponent(g);
        final java.awt.Dimension d=this.getSize();
        final Graphics2D g2=(Graphics2D) g;
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                0.05f));
        g2.setColor(new Color(0, 0, 255));
        g2.fillRect(5, 5, TS-8, d.height-11);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                0.15f));
        g2.drawRect(5, 5, TS-8, d.height-11);
        g2.setComposite(AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, 1f));
    }

    public JContent(final int tabsize) {
        TS=tabsize;
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBackground(new Color(250, 250, 250));
        setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 1));
    }
}
