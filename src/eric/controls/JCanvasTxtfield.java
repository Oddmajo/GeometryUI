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
 
 
 package eric.controls;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTextField;

import rene.util.xml.XmlWriter;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.objects.ExpressionObject;
import rene.gui.Global;

/**
 * 
 * @author erichake
 */
public class JCanvasTxtfield extends JCanvasPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	MyJTextField JCB;
	Color errColor = new Color(201, 68, 27);
	Color goodColor = new Color(50, 50, 50);

	public JCanvasTxtfield(final ZirkelCanvas zc, final ExpressionObject o) {
		super(zc, o);
		JSL = new MyJTextField();
		JCB = (MyJTextField) JSL;
		JCB.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(final KeyEvent e) {
			}

			@Override
			public void keyReleased(final KeyEvent e) {
				setVal(Global.Comma_To_Point(JCB.getText(), O
						.getConstruction(), true));
				if (O.getExp().isValid()) {
					JCB.setForeground(goodColor);
				} else {
					JCB.setForeground(errColor);
				}
			}
		});
		JCB.addFocusListener(new FocusAdapter() {

			@Override
			public void focusGained(final FocusEvent e) {
				JCB.selectAll();
			}

			@Override
			public void focusLost(final FocusEvent e) {
			}
		});
		JCB.addMouseListener(this);
		JCB.addMouseMotionListener(this);
		JCB.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(final MouseEvent e) {
				if (isEditMode()) {
					JCB.setFocusable(false);
					JCB.setEnabled(false);
				}
			}

			@Override
			public void mouseReleased(final MouseEvent e) {
				if (!JCB.isEnabled()&&!hidden()) {
					JCB.setEnabled(true);
					JCB.setFocusable(true);
				}
			}
		});
		showval = false;
		showunit = false;
		showcom = true;
		setComment(O.getName() + " = ");
		setVal(1);
		JCB.setText("1");
		JCB.setForeground(goodColor);
		this.add(JCPlabel);
		this.add(JCB);
		this.add(JCPresize);
		zc.add(this);
	}

	@Override
	public double getVal() {
		return (Double.valueOf(JCB.getText()));
	}

	class MyJTextField extends JTextField {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		JTextField jtf;

		MyJTextField() {
			super();
		}
	}

	@Override
	public void PrintXmlTags(final XmlWriter xml) {
		xml.startTagStart("CTRLtxtfield");
		super.PrintXmlTags(xml);
		xml.printArg("txt", "" + JCB.getText());
		xml.finishTagNewLine();
	}
}
