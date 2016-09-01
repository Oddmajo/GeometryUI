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
public class JTabPanel extends JEricPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JEricPanel LeftPanel = new JEricPanel();
	JEricPanel SouthPanel = new JEricPanel();
	JEricPanel RightPanel = new JEricPanel();
	ArrayList Panes = new ArrayList();
	JTabPanelTitleBar JTitle;
	int Leftmargin = 5;// margin before the tabs
	int Rightmargin = 35;// margin after the tabs
	int TabHeight = 18; // tab title line height
	int TabTitleSize = 11; // Tab title font size
	int TabTitleMargin = 5; // space before and after the Tab Title

	@Override
	public void paintComponent(final Graphics g) {
		super.paintComponent(g);
		final ImageIcon myicon = new ImageIcon(getClass().getResource(
		"gui/panel_back.png"));
		final java.awt.Dimension d = this.getSize();
		g.drawImage(myicon.getImage(), 0, 0, d.width, d.height, this);
	}

	public JTabPanel(final int w, final int h) {
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		LeftPanel.setLayout(new BoxLayout(LeftPanel, BoxLayout.Y_AXIS));
		RightPanel.setLayout(new BoxLayout(RightPanel, BoxLayout.Y_AXIS));
		SouthPanel.setLayout(new BoxLayout(SouthPanel, BoxLayout.X_AXIS));
		JTitle = new JTabPanelTitleBar(this);
		LeftPanel.add(JTitle);
		LeftPanel.add(SouthPanel);
		add(LeftPanel);
		add(RightPanel);
		RightPanel.setOpaque(false);
		SouthPanel.setOpaque(false);
		LeftPanel.setOpaque(false);
		SouthPanel.setAlignmentX(0.0f);
		SouthPanel.setAlignmentY(0.0f);
		fixsize(this, w, h);
		// this.setBorder(BorderFactory.createLineBorder(new
		// Color(82,82,82),1));
	}

	public void add(final JComponent cp, final int i) {
		final JEricPanel mypane = (JEricPanel) Panes.get(i);
		mypane.add(cp);
	}

	public void addMain(final JComponent cp) {
		SouthPanel.add(cp);
	}

	public void setMainCenteredContent(final JComponent cp) {
		final JEricPanel myjp1 = new JEricPanel();
		myjp1.setOpaque(false);
		final JEricPanel myjp2 = new JEricPanel();
		myjp2.setOpaque(false);
		SouthPanel.add(myjp1);
		SouthPanel.add(cp);
		SouthPanel.add(myjp2);
	}

	public void clearAll() {
		SouthPanel.removeAll();
		SouthPanel.revalidate();
		for (int i = 0; i < Panes.size(); i++) {
			final JEricPanel mypane = (JEricPanel) Panes.get(i);
			mypane.removeAll();
			mypane.revalidate();
		}
	}

	public void addPanel(final String name) {
		JTitle.addTabTitle(name);
		final JEricPanel mypane = new JEricPanel();
		mypane.setLayout(new BoxLayout(mypane, BoxLayout.X_AXIS));
		mypane.setOpaque(false);
		String str = Global.Loc("props.help");
		str = str.replace("<br>", " ");

		Panes.add(mypane);
		JTitle.revalidate();

		fixsize(LeftPanel, JTitle.getSize().width, this.getSize().height);
		fixsize(SouthPanel, JTitle.getSize().width, this.getSize().height
				- TabHeight);
		fixsize(RightPanel, this.getSize().width - LeftPanel.getSize().width,
				this.getSize().height);

		final JLabel hlp = new JLabel(str);
		hlp.setOpaque(false);
		hlp.setFont(new Font("System", 0, 12));
		hlp.setHorizontalAlignment(SwingConstants.CENTER);
		hlp.setVerticalAlignment(SwingConstants.CENTER);
		fixsize(hlp, 600, RightPanel.getSize().height);
		hlp.setForeground(new Color(100, 100, 100));
		mypane.add(hlp);
		mypane.revalidate();
	}


	public void selectTab(final int i) {
		JTitle.selectTabTitle(i);
		RightPanel.removeAll();
		final JEricPanel mypane = (JEricPanel) Panes.get(i);
		fixsize(mypane, RightPanel.getSize().width, RightPanel.getSize().height);
		RightPanel.add(mypane);
		RightPanel.revalidate();
		RightPanel.repaint();
		JTitle.repaint();
		Global.setParameter("props.selectedtab", i);
	}

	private void fixsize(final JComponent cp, final int w, final int h) {
		final Dimension d = new Dimension(w, h);
		cp.setMaximumSize(d);
		cp.setMinimumSize(d);
		cp.setPreferredSize(d);
		cp.setSize(d);
	}
}
