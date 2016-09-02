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
package rene.lister;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.PrintWriter;

import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

public class Lister extends JScrollPane {

    /**
     *
     */
    public ListerPanel L;

    /**
     * Initialize the display of vertical scrollbar
     *
     */
    public Lister() {
        L=new ListerPanel(this);
        setViewportView(L);
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	setBorder(javax.swing.BorderFactory.createEmptyBorder());
        setWheelScrollingEnabled(true);
        getVerticalScrollBar().setUnitIncrement(16);
    }

    /**
     * Return the lister for external use.
     *
     * @return lister panel
     */
    public ListerPanel getLister() {
        return L;
    }

    public void addActionListener(final ActionListener al) {
        L.addActionListener(al);
    }

    public void updateDisplay() {
        L.repaint();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                L.fixsize();
            }
        });
    }

    public void removeActionListener(final ActionListener al) {
        L.removeActionListener(al);
    }

    public void clear() {
        L.clear();
    }

    public void addElement(final Element el) {
        L.add(el);
    }

    /**
     * Get the first selected index.
     *
     * @return index or -1
     */
    public int getSelectedIndex() {
        if (L.Selected.size()>0) {
            return ((Integer) L.Selected.elementAt(0)).intValue();
        } else {
            return -1;
        }
    }

    public String getSelectedItem() {
        final int n=getSelectedIndex();
        if (n<0) {
            return null;
        }
        return L.getElementAt(n).getElementString();
    }

    /**
     * Get a vector of all selected indices.
     *
     * @return vector of indices
     */
    public int[] getSelectedIndices() {
        final int k[]=new int[L.Selected.size()];
        for (int i=0; i<k.length; i++) {
            k[i]=((Integer) L.Selected.elementAt(i)).intValue();
        }
        return k;
    }

    /**
     * Make sure, the lister shows the last element.
     */
    public void showLast() {
        L.showLast();
    }

    /**
     * Set the operations mode.
     *
     * @param multiple
     *            allows multiple clicks
     * @param easymultiple
     *            multiple selection without control
     * @param singleclick
     *            report single click events
     * @param rightmouse
     *            report right mouse clicks
     */
    public void setMode(final boolean multiple, final boolean easymultiple,
            final boolean singleclick, final boolean rightmouse) {
        L.MultipleSelection=multiple;
        L.EasyMultipleSelection=easymultiple;
        L.ReportSingleClick=singleclick;
        L.RightMouseClick=rightmouse;
    }

    /**
     * Print the lines to the printwriter o.
     *
     * @param o
     */
    public void save(final PrintWriter o) {
        L.save(o);
    }

    public void select(final int sel) {
    }

    /**
     * Shortcut to add a string with a specific color.
     *
     * @param name
     * @param col
     */
    public void addElement(final String name, final Color col) {
        addElement(new StringElement(name, col));
    }

    public void addElement(final String name) {
        addElement(new StringElement(name));
    }

    public void setState(final int s) {
        L.setState(s);
    }

    public void setListingBackground(final Color c) {
        L.setListingBackground(c);
    }
}
