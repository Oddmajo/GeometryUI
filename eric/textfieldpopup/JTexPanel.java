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
 
 
 package eric.textfieldpopup;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import eric.JEricPanel;
import javax.swing.JPopupMenu;
import javax.swing.text.JTextComponent;

import atp.sHotEqn;
import java.awt.Graphics2D;
import rene.gui.Global;

/**
 * 
 * @author erichake
 */
public class JTexPanel extends JMenuPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int Linemax = 4;
	String funcs = "\\frac{a}{b} \\sqrt{a} \\sqrt[n]{a} \\vec{u} \\widehat{ABC} \\hat{a} a_1 a^2 "
		+ "\\sum_{i=0}^{n} \\prod_{i=0}^{n} \\int_{a}^{b} \\oint_{a}^{b} \\bar{z} \\fbox{box}";

	// +"\\left|\\begin{array}{cc}a_{11}&a_{12}\\\\\\\\a_{21}&a_{22}\\end{array}\\right| "
	// +
	// "\\left(\\begin{array}{cc}a_{11}&a_{12}\\\\\\\\a_{21}&a_{22}\\end{array}\\right) ";

	public JTexPanel(final JPopupMenu men, final JComponent jtf) {
		super(men);
		JTF = jtf;
		iconwidth = 50;
		iconheight = 50;
		final String[] f = funcs.split(" ");
		JEricPanel line = null;
		for (int i = 0; i < f.length; i++) {
			if ((i % Linemax) == 0) {
				add(line = getnewline());
			}
			line.add(getJButton(f[i]));
		}
	}

	@Override
	public void doAction(final JButton mybtn) {
		String s = ((myJButton) mybtn).EQ;
		final JTextComponent jt = (JTextComponent) JTF;
		mybtn.setOpaque(false);
		mybtn.setContentAreaFilled(false);
		String mytxt = jt.getText().substring(0, jt.getSelectionStart());
		final int nbDollars = mytxt.split("\\$").length - 1;
		if (nbDollars % 2 == 0) {
			s = "$" + s + "$";
		}
		if (jt.getSelectedText() != null) {
			final Pattern p = Pattern.compile("\\{([^\\}]*)\\}",
					Pattern.CASE_INSENSITIVE);
			final Matcher m = p.matcher(s);
			if (m.find()) {
				s = m.replaceFirst("{" + jt.getSelectedText() + "}");
			}
		}
		mytxt += s;
		final int car = mytxt.length();
		mytxt += jt.getText().substring(jt.getSelectionEnd());
		jt.setText(mytxt);
		jt.setCaretPosition(car);
		MEN.setVisible(false);

	}

	@Override
	public JButton getJButton(final String s) {
		final myJButton mybtn = new myJButton(s);
		mybtn.setBorder(BorderFactory.createEmptyBorder());
		mybtn.setBorderPainted(false);
		mybtn.setFocusPainted(false);
		mybtn.setFocusable(false);
		mybtn.setBackground(new Color(228, 222, 255));
		mybtn.setOpaque(false);
		mybtn.setContentAreaFilled(false);
		mybtn.addMouseListener(this);
		mybtn.setFont(new java.awt.Font(Global.GlobalFont, 1, 14));
		mybtn.setForeground(new Color(20, 20, 20));
		fixsize(mybtn, iconwidth, iconheight);
		return mybtn;
	}

	class myJButton extends JButton {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		String EQ = null;
		sHotEqn HE = null;

		@Override
		public void paintComponent(final Graphics g) {
			super.paintComponent(g);
                        Graphics2D g2d=(Graphics2D) g;
			final Dimension d = this.getSize();
			final Dimension eq = HE.getSizeof(EQ, g2d);
			HE.paint((d.width - eq.width) / 2, (d.height - eq.height) / 2, g2d);
		}

		public myJButton(final String s) {
			super();
			EQ = s;
			HE = new sHotEqn(this);
			HE.setHAlign("center");
			HE.setEquation(s);

		}
	}
}
