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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.plaf.ComboBoxUI;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalComboBoxUI;

import rene.gui.Global;
import rene.util.xml.XmlWriter;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.objects.ExpressionObject;

/**
 * 
 * @author erichake
 */
public class JCanvasPopup extends JCanvasPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	MyJComboBox JCB;

	public JCanvasPopup(final ZirkelCanvas zc, final ExpressionObject o) {
		super(zc, o);
		JSL = new MyJComboBox();
		JCB = (MyJComboBox) JSL;
		JCB.setUI((ComboBoxUI) MyComboBoxUI.createUI(JCB));
		// JCanvasPanel.fixsize(JCB, 100, 18);
		// addMouseEvents();
		JCB.addMouseListener(this);
		JCB.addMouseMotionListener(this);
		JCB.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(final MouseEvent e) {
				if (isEditMode()) {
					JCB.hidePopup();
				}
				

			}
		});
		addMouseEvents();
		JCB.addActionListener(this);
		showval = false;
		showunit = false;
		showcom = true;
		setComment(Global.Loc("props.expl") + " ");
		setVal(1);
		this.add(JCPlabel);
		this.add(JCB);
		this.add(JCPresize);
		zc.add(this);
	}

	static class MyComboBoxUI extends MetalComboBoxUI {

		public static ComponentUI createUI(final JComponent c) {
			return new MyComboBoxUI();
		}
	}

	public void addMouseEvents() {
		for (int i = 0; i < JCB.getComponentCount(); i++) {
			JCB.getComponent(i).addMouseListener(new MouseAdapter() {

				@Override
				public void mousePressed(final MouseEvent e) {
					if (isEditMode()) {
						JCB.hidePopup();
					}
					

				}
			});
			JCB.getComponent(i).addMouseListener(this);
			JCB.getComponent(i).addMouseMotionListener(this);
		}
	}

	public String getItems() {
		String s = "";
		for (int i = 0; i < JCB.getItemCount() - 1; i++) {
			s += JCB.getItemAt(i) + "\n";
		}
		s += JCB.getItemAt(JCB.getItemCount() - 1);
		return s;
	}

	public void setItems(final String s) {
		JCB.removeAllItems();
		final String[] itms = s.split("\n");
		for (final String itm : itms) {
			JCB.addItem(itm);
		}
		setDims();
	}

	@Override
	public double getVal() {
		return (JCB.getSelectedIndex() + 1);
	}

	class MyJComboBox extends JComboBox {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		MyJComboBox() {
			super();
			setFocusable(false);

			setModel(new DefaultComboBoxModel(new String[] { "Item 1",
					"Item 2", "Item 3" }));
		}
	}

	public void actionPerformed(final ActionEvent e) {
		if (e.getSource() == JCB) {
			final int selectedIndex = JCB.getSelectedIndex() + 1;

			try {
				setVal(selectedIndex);
			} catch (final Exception ex) {
			}

		}
	}

	@Override
	public void PrintXmlTags(final XmlWriter xml) {
		xml.startTagStart("CTRLpopup");
		super.PrintXmlTags(xml);
		final String s = getItems().replace("\n", "@@@");
		xml.printArg("Items", "" + s);
		xml.finishTagNewLine();

	}
}
