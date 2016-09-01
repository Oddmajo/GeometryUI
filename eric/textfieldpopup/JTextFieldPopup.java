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

import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;

import rene.gui.Global;

/**
 * 
 * @author erichake
 */
public class JTextFieldPopup extends JPopupMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JComponent JTF;
	// Numéro du menu :
	static public int SPECIALCARMENU = 0, FUNCTIONMENU = 1, LATEXMENU = 2;
	myJMenuItem m1, m2, m3;

	public JTextFieldPopup(final JComponent jtf) {
		JTF = jtf;
		this.setFocusable(false);

		m1 = new myJMenuItem(Global.Loc("props.popup.special"),
				new JSpecialCarsPanel(this, JTF));
		m2 = new myJMenuItem(Global.Loc("props.popup.functions"),
				new JFunctionsPanel(this, JTF));
		m3 = new myJMenuItem(Global.Loc("props.popup.latex"), new JTexPanel(
				this, JTF));
		this.add(m1);
		this.add(m2);
		this.add(m3);
	}

	public void setDisabled(final String dis) {
		if (dis.contains("," + SPECIALCARMENU + ","))
			this.getComponent(SPECIALCARMENU).setEnabled(false);
		if (dis.contains("," + FUNCTIONMENU + ","))
			this.getComponent(FUNCTIONMENU).setEnabled(false);
		if (dis.contains("," + LATEXMENU + ","))
			this.getComponent(LATEXMENU).setEnabled(false);
	}

	public void openMenu(final MouseEvent e) {
		this.show(e.getComponent(), e.getX(), e.getY());
	}

	class myJMenuItem extends JMenu {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		JPopupMenu jp;

		public myJMenuItem(final String name, final JMenuPanel jmp) {
			super(name);
			this.setFocusable(false);
			this.add(jmp);
		}

	}

}
