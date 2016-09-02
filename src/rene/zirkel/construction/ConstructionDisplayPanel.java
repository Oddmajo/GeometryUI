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
package rene.zirkel.construction;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.PopupMenu;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Vector;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;

import eric.JEricPanel;

import rene.gui.ButtonAction;
import rene.gui.CheckboxMenuItemAction;
import rene.gui.ChoiceAction;
import rene.gui.DoActionListener;
import rene.gui.Global;
import rene.gui.MenuItemAction;
import rene.lister.Lister;
import rene.lister.ListerMouseEvent;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.objects.AngleObject;
import rene.zirkel.objects.ConstructionObject;
import rene.zirkel.objects.ExpressionObject;
import rene.zirkel.objects.FixedAngleObject;
import rene.zirkel.objects.FunctionObject;
import rene.zirkel.objects.PointObject;
import rene.zirkel.objects.PrimitiveCircleObject;
import rene.zirkel.objects.PrimitiveLineObject;

public class ConstructionDisplayPanel extends JEricPanel implements
        DoActionListener, ActionListener, ClipboardOwner {

    private static final long serialVersionUID=1L;
    private static int control_height=25;
    private JEricPanel controls;
    public Lister V;
    public Vector W;
    Construction C;
    ZirkelCanvas ZC;
    JComboBox Ch;
    CheckboxMenuItemAction Visible;
    boolean ShowVisible=true;
    CheckboxMenuItemAction Sort, Description, Size, Formula;
    public static String Choices[]={"all", "points", "lines", "circles",
        "angles", "expressions", "other"};
    public int State=0;
    PopupMenu PM;
    JButton Menu;

    @Override
    public void paintComponent(Graphics g) {
        Dimension d=getSize();
        g.setColor(Color.white);
        g.fillRect(0, 0, d.width, d.height);
        super.paintComponent(g);
    }

    public ConstructionDisplayPanel(final ZirkelCanvas zc) {
        ZC=zc;
        C=ZC.getConstruction();
        V=new Lister();
        V.setMode(true, false, true, true);
        V.addActionListener(this);
        State=Global.getParameter("constructiondisplay.state", 0);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);

        controls=new JEricPanel();
        controls.setLayout(new BoxLayout(controls, BoxLayout.X_AXIS));
        controls.setOpaque(false);
        Ch=new ChoiceAction(this, "choices");
        for (final String choice : Choices) {
            Ch.addItem(Global.name("constructiondisplay."+choice));

        }
        controls.add(Ch);
        Menu=new ButtonAction(this, "?", "Menu");
        controls.add(Menu);
        add(controls);
        setListerState();
        makePopup();
        add(V);
    }

    // Only called by LeftPanelContent init method :
    public void fixPanelSize(int w, int h) {
        fixsize(this, w, h);
        fixsize(V, w, h-control_height);
        fixsize(controls, w, control_height);
        controls.revalidate();
    }

    private static void fixsize(final JComponent cp, final int w, final int h) {
        final Dimension d=new Dimension(w, h);
        cp.setMaximumSize(d);
        cp.setMinimumSize(d);
        cp.setPreferredSize(d);
        cp.setSize(d);
    }

    public void reload() {
        V.clear();
        C=ZC.getConstruction();
        Enumeration e=null;
        if (Global.getParameter("constructiondisplay.sort", true)) {
            e=C.getSortedElements();
        } else {
            e=C.elements();
        }
        W=new Vector();
        outer:
        while (e.hasMoreElements()) {
            final ConstructionObject o=(ConstructionObject) e.nextElement();
//			if ((eric.JMacrosTools.CurrentJZF.restrictedSession)
//					&& (o.isSuperHidden()))
//				continue outer;
            if (ShowVisible&&o.mustHide(ZC)) {
                continue outer;
            }
            if (ZC.job_isTargets()&&o.isSuperHidden()) {
                continue outer;
            }
            if (ZC.isRestricted()&&o.isSuperHidden()) {
                continue outer;
            }
            switch (State) {
                case 0:
                    break;
                case 1:
                    if (!(o instanceof PointObject)) {
                        continue outer;
                    }
                    break;
                case 2:
                    if (!(o instanceof PrimitiveLineObject)
                            ||(o instanceof FixedAngleObject)) {
                        continue outer;
                    }
                    break;
                case 3:
                    if (!(o instanceof PrimitiveCircleObject)) {
                        continue outer;
                    }
                    break;
                case 4:
                    if (!(o instanceof AngleObject||o instanceof FixedAngleObject)) {
                        continue outer;
                    }
                    break;
                case 5:
                    if (!(o instanceof ExpressionObject||o instanceof FunctionObject)) {
                        continue outer;
                    }
                    break;
                case 6:
                    if (o instanceof PointObject
                            ||o instanceof PrimitiveLineObject
                            ||o instanceof PrimitiveCircleObject
                            ||o instanceof AngleObject
                            ||o instanceof ExpressionObject
                            ||o instanceof FunctionObject) {
                        continue outer;
                    }
                    break;
            }
            V.addElement(o);
            W.addElement(o);
        }
        //V.showLast();
        updateDisplay();
    }

    public void updateDisplay() {
        V.updateDisplay();
    }

    @Override
    public void doAction(final String o) {
        if (o.equals("Edit")) {
            final int selected[]=V.getSelectedIndices();
            if (selected.length==0) {
                return;
            }
            if (selected.length==1) {
                ((ConstructionObject) W.elementAt(selected[0])).edit(ZC, true, false);
            } else {
                final Vector v=new Vector();
                for (final int element : selected) {
                    v.addElement(W.elementAt(element));
                }
                ZC.validate();
            }
            ZC.repaint();
        } else if (o.equals("Copy")) {
            try {
                final ByteArrayOutputStream ba=new ByteArrayOutputStream(
                        50000);
                final PrintWriter po=new PrintWriter(new OutputStreamWriter(
                        ba), true);
                V.save(po);
                po.close();
                final String S=ba.toString();
                final Clipboard clip=getToolkit().getSystemClipboard();
                final StringSelection sel=new StringSelection(S);
                clip.setContents(sel, this);
            } catch (final Exception e) {
            }
        } else if (o.equals("Delete")) {
            final int selected[]=V.getSelectedIndices();
            if (selected.length==0) {
                return;
            }
            final Vector v=new Vector();
            for (final int element : selected) {
                v.addElement(W.elementAt(element));
            }
            ZC.delete(v);
            ZC.repaint();
            ZC.reset();
            reload();
        } else if (o.equals("Hide")) {
            final int selected[]=V.getSelectedIndices();
            if (selected.length==0) {
                return;
            }
            for (final int element : selected) {
                final ConstructionObject oc=(ConstructionObject) W.elementAt(element);
                oc.setHidden(!oc.isHidden());
            }
            ZC.repaint();
            updateDisplay();
        } else if (o.equals("SuperHide")) {
            final int selected[]=V.getSelectedIndices();
            if (selected.length==0) {
                return;
            }
            for (final int element : selected) {
                final ConstructionObject oc=(ConstructionObject) W.elementAt(element);
                oc.setSuperHidden(true);
            }
            ZC.repaint();
            updateDisplay();
        } else if (o.equals("HighLight")) {
            final int selected[]=V.getSelectedIndices();
            if (selected.length==0) {
                return;
            }
            for (final int element : selected) {
                final ConstructionObject oc=(ConstructionObject) W.elementAt(element);
                oc.setStrongSelected(true);
            }
            final Graphics g=ZC.getGraphics();
            if (g!=null) {
                ZC.paint(g);
                g.dispose();
                try {
                    Thread.sleep(200);
                } catch (final Exception e) {
                }
            }
            for (final int element : selected) {
                final ConstructionObject oc=(ConstructionObject) W.elementAt(element);
                oc.setStrongSelected(false);
            }
            ZC.repaint();
            if (selected.length==1) {
                final ConstructionObject oc=(ConstructionObject) W.elementAt(selected[0]);
                ZC.setConstructionObject(oc);
            }

        } else if (o.equals("Menu")) {
            displayPopup(V.L, 10, 10);
        }
        ZC.requestFocus();
    }

    public void itemToggleAction(final String o) {
        if (o.equals("Sort")) {
            Sort.setState(!Sort.getState());
            itemAction("Sort", Sort.getState());
        } else if (o.equals("Visible")) {
            Visible.setState(!Visible.getState());
            itemAction("Visible", Visible.getState());
        }
    }

    @Override
    public void itemAction(final String o, final boolean flag) {
        if (o.equals("Sort")) {
            Global.setParameter("constructiondisplay.sort", Sort.getState());
            reload();
        } else if (o.equals("Visible")) {
            ShowVisible=Visible.getState();
            reload();
        } else if (o.equals("Description")) {
            Global.setParameter("constructiondisplay.listerstate",
                    ConstructionObject.DescriptionState);
            setListerState();
            updateDisplay();
        } else if (o.equals("Size")) {
            Global.setParameter("constructiondisplay.listerstate",
                    ConstructionObject.SizeState);
            setListerState();
            updateDisplay();
        } else if (o.equals("Formula")) {
            Global.setParameter("constructiondisplay.listerstate",
                    ConstructionObject.FormulaState);
            setListerState();
            updateDisplay();
        } else if (flag) {
            State=Ch.getSelectedIndex();
            Global.setParameter("constructiondisplay.state", State);
            reload();
        }
    }

    public void setListerState() {
        final int state=Global.getParameter("constructiondisplay.listerstate",
                ConstructionObject.SizeState);
        V.setState(state);
        if (PM!=null) {
            Description.setState(state==ConstructionObject.DescriptionState);
            Size.setState(state==ConstructionObject.SizeState);
            Formula.setState(state==ConstructionObject.FormulaState);
        }
    }

//	@Override
//	public Dimension getPreferredSize() {
//		return new Dimension(Global.getParameter(
//				"options.constructiondisplay.width", 200), 400);
//	}
    /**
     * React on click events for the construction list
     */
    @Override
    public void actionPerformed(final ActionEvent e) {
        if (e.getSource()==V&&(e instanceof ListerMouseEvent)) {
            final ListerMouseEvent em=(ListerMouseEvent) e;
            if (em.rightMouse()) {
                displayPopup(em.getEvent().getComponent(),
                        em.getEvent().getX(), em.getEvent().getY());
            } else {
                if (em.clickCount()>=2) {
                    doAction("Edit");
                } else {
                    doAction("HighLight");
                }
            }
        }
    }

    /**
     * Display the popup menu. Create it, if necessary.
     *
     * @param e
     *            mouse event
     */
    public void displayPopup(final Component c, final int x, final int y) {
        PM.show(c, x, y);
    }

    public void makePopup() {
        PM=new PopupMenu();
        PM.add(new MenuItemAction(this,
                Global.name("constructiondisplay.edit"), "Edit"));
        PM.addSeparator();
        PM.add(new MenuItemAction(this,
                Global.name("constructiondisplay.hide"), "Hide"));
        PM.add(new MenuItemAction(this, Global.name("constructiondisplay.superhide"), "SuperHide"));
        PM.addSeparator();
        PM.add(new MenuItemAction(this, Global.name("constructiondisplay.delete"), "Delete"));
        PM.addSeparator();
        Description=new CheckboxMenuItemAction(this, Global.name("constructiondisplay.description"), "Description");
        Description.setState(Global.getParameter(
                "constructiondisplay.description", false));
        PM.add(Description);
        Size=new CheckboxMenuItemAction(this, Global.name("constructiondisplay.size"), "Size");
        Description.setState(Global.getParameter("constructiondisplay.size",
                true));
        PM.add(Size);
        Formula=new CheckboxMenuItemAction(this, Global.name("constructiondisplay.formula"), "Formula");
        Description.setState(Global.getParameter("constructiondisplay.formula",
                false));
        PM.add(Formula);
        PM.addSeparator();
        PM.add(new MenuItemAction(this,
                Global.name("constructiondisplay.copy"), "Copy"));
        V.L.add(PM);
        PM.addSeparator();
        Visible=new CheckboxMenuItemAction(this, Global.name("constructiondisplay.visible"), "Visible");
        Visible.setState(true);
        PM.add(Visible);
        Sort=new CheckboxMenuItemAction(this, Global.name("constructiondisplay.sorted"), "Sort");
        Sort.setState(Global.getParameter("constructiondisplay.sort", true));
        PM.add(Sort);
        setListerState();
    }

    @Override
    public void lostOwnership(final Clipboard clipboard,
            final Transferable contents) {
    }

    public void setListingBackground(final Color c) {
        V.setListingBackground(c);
    }
}
