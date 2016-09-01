/* 
 
Copyright 2006 Rene Grothmann, modified by Eric Hakenholz

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
 
 
 package rene.gui;

import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JComboBox;

class ChoiceTranslator implements ItemListener {
	DoActionListener C;
	String S;
	public JComboBox Ch;

	public ChoiceTranslator(final JComboBox ch, final DoActionListener c,
			final String s) {
		C = c;
		S = s;
		Ch = ch;
	}

	public void itemStateChanged(final ItemEvent e) {
		C.itemAction(S, e.getStateChange() == ItemEvent.SELECTED);
	}
}

/**
 * This is a choice item, which sets a specified font and translates events into
 * strings, which are passed to the doAction method of the DoActionListener.
 * 
 * @see jagoclient.gui.CloseFrame#doAction
 * @see jagoclient.gui.CloseDialog#doAction
 */

public class ChoiceAction extends JComboBox {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	public ChoiceAction(final DoActionListener c, final String s) {
		addItemListener(new ChoiceTranslator(this, c, s));
		if (Global.NormalFont != null)
			setFont(Global.NormalFont);
                setBackground(Color.white);
	}
}
