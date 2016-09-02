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
 
 
 package eric.bar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import eric.JEricPanel;
import javax.swing.SwingConstants;
import rene.gui.Global;


/**
 * 
 * @author erichake
 */
public class JTabPanelTitleBar extends JEricPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ArrayList TabTitles = new ArrayList();
	JTabPanel Mother;

	@Override
	public void paintComponent(final Graphics g) {
		super.paintComponent(g);
		final ImageIcon myicon1 = new ImageIcon(getClass().getResource(
		"gui/titles_back.png"));
		final ImageIcon myicon2 = new ImageIcon(getClass().getResource(
		"gui/title_back_end.png"));
		final java.awt.Dimension d = this.getSize();
		g.drawImage(myicon1.getImage(), 0, 0, d.width, d.height, this);
		g.drawImage(myicon2.getImage(), d.width - d.height, 0, this);
	}

	public JTabPanelTitleBar(final JTabPanel parent) {
		Mother = parent;
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setAlignmentX(0.0f);
		setAlignmentY(0.0f);
		add(margin(Mother.Leftmargin));
		add(margin(Mother.Rightmargin));
	}

	public void addTabTitle(final String name) {
		final int cnt = this.getComponentCount();
		final JTabTitle jtt = new JTabTitle(Mother, name);

		TabTitles.add(jtt);
		this.add(jtt, cnt - 1);
		fixsize(this, getLeftWidth(), Mother.TabHeight);
	}

	public void selectTabTitle(final int n) {
		for (int i = 0; i < TabTitles.size(); i++) {
			((JTabTitle) TabTitles.get(i)).setSelected(i == n);
		}
	}

	public int getLeftWidth() {
		int w = Mother.Leftmargin + Mother.Rightmargin;
		for (int i = 0; i < TabTitles.size(); i++) {
			final JTabTitle jtt = (JTabTitle) TabTitles.get(i);
			w += jtt.getSize().width;
		}
		return w;
	}

	static JEricPanel margin(final int w) {
		final JEricPanel mypan = new JEricPanel();
		fixsize(mypan, w, 1);
		mypan.setOpaque(false);
		mypan.setFocusable(false);
		return mypan;
	}

	static void fixsize(final JComponent cp, final int w, final int h) {
		final Dimension d = new Dimension(w, h);
		cp.setMaximumSize(d);
		cp.setMinimumSize(d);
		cp.setPreferredSize(d);
		cp.setSize(d);
	}

}

class JTabTitle extends JEricPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	boolean isActive = false;
	JTabPanel Mother;
	int Pos;
	JLabel JLB = new JLabel();
	ImageIcon IcON = new ImageIcon(getClass().getResource("gui/tab_on.png"));
	ImageIcon IcOFF = new ImageIcon(getClass().getResource("gui/tab_off.png"));

	@Override
	public void paintComponent(final Graphics g) {
		super.paintComponent(g);
		final ImageIcon icn = (isActive) ? IcON : IcOFF;
		final java.awt.Dimension d = this.getSize();
		g.drawImage(icn.getImage(), 0, 0, d.width, d.height, this);
	}

	public JTabTitle(final JTabPanel parent, final String txt) {
		Mother = parent;
		Pos = Mother.Panes.size();
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setOpaque(false);
		// JTabPanelTitleBar.fixsize(this,Mother.TabWidth,Mother.TabHeight);
		JTabPanelTitleBar.fixsize(this, titleWidth(txt) + 2
				* Mother.TabTitleMargin, Mother.TabHeight);
		JLB.setText(txt);
		JLB.setFont(new Font(Global.GlobalFont, 0, 11));
		JLB.setOpaque(false);
		JLB.setHorizontalAlignment(SwingConstants.CENTER);
		JLB.setVerticalAlignment(SwingConstants.BOTTOM);
		JLB.setForeground(new Color(40, 40, 40));
		JTabPanelTitleBar.fixsize(JLB, titleWidth(txt) + 2
				* Mother.TabTitleMargin, Mother.TabHeight);
		// JTabPanelTitleBar.fixsize(JLB,Mother.TabWidth,Mother.TabHeight);
		JLB.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mousePressed(final java.awt.event.MouseEvent evt) {
				Mother.selectTab(Pos);
				Mother.repaint();
			}
		});
		this.add(JLB);
	}

	private int titleWidth(final String s) {
		// FontMetrics fm = getFontMetrics(getFont());
		final FontMetrics fm = getFontMetrics(new Font(Global.GlobalFont, 0,
				Mother.TabTitleSize));

		return fm.stringWidth(s);
	}

	public void setSelected(final boolean b) {
		isActive = b;
	}
}
