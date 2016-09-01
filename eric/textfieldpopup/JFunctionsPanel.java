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

import javax.swing.JButton;
import javax.swing.JComponent;
import eric.JEricPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.text.JTextComponent;

/**
 * 
 * @author erichake
 */
public class JFunctionsPanel extends JMenuPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int Linemax = 3;
	String funcs = "&& || ! < > <= >= == ~= $ "
		+ "x() y() d(,) if(,,) a(,,) inside(,) $ "
		+ "sqrt() exp() log() round() ceil() floor() abs() sign() random() min(,) max(,) $ "
		+ "sin() cos() tan() rsin() rcos() rtan() "
		+ "arcsin() arccos() arctan() rarcsin() rarccos() rarctan() "
		+ "deg() rad() sinhyp() coshyp() angle180() angle360() "
                + "Argsinh() Argcosh() Argtanh() tanhyp() atan2(,) ratan2(,) $ "
		+ "integrate(,,) zero(,,) diff(,) min(,,) max(,,) length() $ "
		+ "windoww windowh windowcx windowcy pixel $ "
                + "div(,) mod(,) gcd(,) lcm(,)";

	public JFunctionsPanel(final JPopupMenu men, final JComponent jtf) {
		super(men);
		JTF = jtf;
		iconwidth = 75;
		iconheight = 20;
		final String[] f = funcs.split(" ");
		JEricPanel line = null;
		int j = 0;
		for (final String element : f) {
			if ((j % Linemax) == 0) {
				add(line = getnewline());
			}
			if (element.equals("$")) {
				add(new JSeparator());
				j = 0;
			} else {
				line.add(getJButton(element));
				j++;
			}
		}
	}

	@Override
	public void doAction(final JButton mybtn) {
		final String s = mybtn.getText();
		mybtn.setOpaque(false);
		mybtn.setContentAreaFilled(false);
		if (JTF != null) {
			final JTextComponent jt = (JTextComponent) JTF;
			if ((s.endsWith(")")) && (jt.getSelectedText() != null)) {
				String mytxt = jt.getText()
				.substring(0, jt.getSelectionStart());
				mytxt += s.substring(0, s.length() - 1);
				mytxt += jt.getSelectedText() + ")";
				final int car = mytxt.length();
				mytxt += jt.getText().substring(jt.getSelectionEnd());
				jt.setText(mytxt);
				jt.setCaretPosition(car);
			} else {
				String mytxt = jt.getText()
				.substring(0, jt.getSelectionStart());
				mytxt += (s.endsWith(")")) ? s.substring(0, s.length() - 1) : s;
				final int car = mytxt.length();
				mytxt += jt.getText().substring(jt.getSelectionEnd());
				jt.setText(mytxt);
				jt.setCaretPosition(car);

			}
			MEN.setVisible(false);
		}
	}
}
