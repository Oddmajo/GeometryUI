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
 
 
 package eric;

import java.awt.Color;
import java.awt.Font;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import rene.gui.Global;
import rene.util.MyVector;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.objects.ConstructionObject;
import rene.zirkel.tools.EditTool;

/**
 * 
 * @author erichake
 */
// This class is only instanciated by JSelectPopup
// It is a "modal" popup for complex tools. Popup menu appear
// in a 1 pixel w/h modal JDialog : it seems it's in ZirkelCanvas, but it's
// not...
public class JSelectPopup extends JPopupMenu implements MouseListener,
PopupMenuListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
        private static int xclic,yclic;
        private static boolean iscaller=false;
	ZirkelCanvas ZC;
	Vector V = new Vector();
	String CallerObject = "RightClick";
	boolean RightClicked;

	public JSelectPopup(final ZirkelCanvas zc,final MyVector v, boolean RightClicked) {
		init(zc, v);
		addPopupMenuListener(this);
                Point loc=MouseInfo.getPointerInfo().getLocation();
                SwingUtilities.convertPointFromScreen(loc, zc);
                xclic=loc.x;
                yclic=loc.y;
		show(zc, loc.x, loc.y);
		this.RightClicked = RightClicked;
	}

	public void init(final ZirkelCanvas zc, final MyVector v) {
		ZC = zc;
		// A bit of a hack : need to know where was the calling method...
		// getStackTrace stores the whole history of caller methods
		final StackTraceElement[] trace = new Throwable().getStackTrace();
		for (final StackTraceElement element : trace) {
			if (element.getClassName().startsWith("rene.zirkel.tools")) {
				final String s = element.getClassName();
				CallerObject = s.split("\\.")[3];
				break;
			}
		}
		
		String aa = "";

		try {
			aa = Global.Loc("selectpopup." + CallerObject);
		} catch (final Exception e) {
		}
		
		JMenuItem m = new JMenuItem(aa + Global.Loc("selectpopup.whatobject"));

		m.setBackground(Color.WHITE);
		m.setForeground(Color.DARK_GRAY);

		m.setFont(new Font("Dialog", 3, 12));
		m.setActionCommand("-1,false");

		m.setEnabled(false);
		m.addMouseListener(this);
		this.add(m);

		for (int i = 0; i < v.size(); i++) {
			final ConstructionObject o = (ConstructionObject) v.elementAt(i);
			V.add(o);
			final String tp = o.getName() + " : " + o.getText().split(" ")[0];
			m = new JMenuItem(tp);
			m.setForeground(o.getColor());
			m.setBackground(new Color(240, 240, 240));
			m.setFont(new Font("Dialog", 1, 12));

			m.setActionCommand(String.valueOf(i) + "," + o.selected());
			m.setRolloverEnabled(true);

			m.addMouseListener(this);
			m.addActionListener(new ActionListener() {
				public void actionPerformed(final ActionEvent event) {
					final JMenuItem jm = (JMenuItem) event.getSource();
					doaction(jm.getActionCommand(), event.getModifiers());
				}
			});
			this.add(m);
		}
		

	}

        public static int getMouseX(){
            return xclic;
        }
        public static int getMouseY(){
            return yclic;
        }
        public static boolean isCallerObject(){
            return iscaller;
        }

	public int row(final String str) {
		return Integer.parseInt(str.split(",")[0]);
	}

	public boolean sel(final String str) {
		final String bl = str.split(",")[1].toLowerCase();
		return (bl.equals("true"));
	}

	public void doaction(final String str, final int modifier) {
		final ConstructionObject o = (ConstructionObject) V.elementAt(row(str));
		o.setSelected(sel(str));
                iscaller=true;
		if(RightClicked){
                    ZC.clearSelected();
                    eric.bar.JPropertiesBar.EditObject(o, true, false);
		} else {
//                    System.out.println(o.getName());
		    ZC.setConstructionObject(o);
		}
                iscaller=false;
	}

	public void mouseClicked(final MouseEvent e) {
	}

	public void mousePressed(final MouseEvent e) {
	}

	public void mouseReleased(final MouseEvent e) {
	}

	public void mouseEntered(final MouseEvent e) {
		final JMenuItem jm = (JMenuItem) e.getSource();
		final int i = row(jm.getActionCommand());
		if ((i > -1) && (!sel(jm.getActionCommand()))) {
			final ConstructionObject o = (ConstructionObject) V.elementAt(i);
			o.setSelected(true);
			ZC.repaint();
		}
	}

	public void mouseExited(final MouseEvent e) {
		final JMenuItem jm = (JMenuItem) e.getSource();
		final int i = row(jm.getActionCommand());
		if ((i > -1) && (!sel(jm.getActionCommand()))) {
			final ConstructionObject o = (ConstructionObject) V.elementAt(i);
			o.setSelected(false);
			ZC.repaint();
		}
	}

	//
	public void popupMenuWillBecomeVisible(final PopupMenuEvent e) {
	}

	public void popupMenuWillBecomeInvisible(final PopupMenuEvent e) {
	}

	public void popupMenuCanceled(final PopupMenuEvent e) {
	}

}
