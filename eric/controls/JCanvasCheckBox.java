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

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JCheckBox;

import rene.gui.Global;
import rene.util.xml.XmlWriter;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.objects.ExpressionObject;

/**
 * 
 * @author erichake
 */
public class JCanvasCheckBox extends JCanvasPanel implements ItemListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	MyJCheckBox JCB;

	public JCanvasCheckBox(final ZirkelCanvas zc, final ExpressionObject o) {
		super(zc, o);
		JSL = new MyJCheckBox();
		JCB = (MyJCheckBox) JSL;
		// JCanvasPanel.fixsize(JCB, 50, 22);
		JCB.addMouseListener(this);
		JCB.addMouseMotionListener(this);
		JCB.addItemListener(this);
		JCB.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(final MouseEvent e) {
				if (isEditMode()||isTargetMode()) {
					JCB.setEnabled(false);
				}
				
			}

			@Override
			public void mouseReleased(final MouseEvent e) {
				JCB.setEnabled(true);
			}
		});
		this.add(JCB);
		showval = false;
		showunit = false;
		showcom = true;
		String s = Global.Loc("props.expl");
		s = s.trim();
		s = s.replace(":", "");
		s = s.trim();
		setComment(s);
		setVal(0);
		this.add(JCPlabel);
		this.add(JCPresize);
		zc.add(this);
	}

	@Override
	public double getVal() {
		final double s = JCB.isSelected() ? 1 : 0;
		return s;
	}

        public void setSelected(boolean b){
            JCB.setSelected(b);
        }

	class MyJCheckBox extends JCheckBox {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		MyJCheckBox() {
			super();
			setFocusable(false);
			setOpaque(false);
		}

	}

	public void itemStateChanged(final ItemEvent arg0) {
            if(!hidden()){
		try {
			final int s = JCB.isSelected() ? 1 : 0;
			setVal(s);
		} catch (final Exception ex) {
		}
            }
	}

	@Override
	public void PrintXmlTags(final XmlWriter xml) {
		xml.startTagStart("CTRLcheckbox");
		super.PrintXmlTags(xml);
		xml.finishTagNewLine();

	}
}
