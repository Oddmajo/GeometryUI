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
 
 
 package eric.macros;

import eric.GUI.palette.PaletteManager;
import eric.GUI.pipe_tools;
import javax.swing.JMenuItem;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import rene.gui.MyMenuItem;
import rene.zirkel.ZirkelFrame;
import rene.zirkel.macro.Macro;

public class JDefaultMutableTreeNode extends DefaultMutableTreeNode {
	/**
	 * 
	 */

	MyMenuItem PMmenuitem;
	JMenuItem MainMenuItem;
	ZirkelFrame ZF;
	String name = "";
	Macro m;
	int macrotype;//        0: library macro       2 : file macro

	public JDefaultMutableTreeNode(final String s) {
		super(s);
		macrotype = MacroTools.FILE_MACRO;

	}

	public JDefaultMutableTreeNode(final ZirkelFrame zf, final Macro mcr) {
		super();
		ZF = zf;
		macrotype = (mcr.isProtected()) ? MacroTools.LIBRARY_MACRO : MacroTools.FILE_MACRO;
		name = mcr.getName();
		m = mcr;
		final String[] mytab = mcr.getName().split(("/"));
		MainMenuItem = new JMenuItem(mytab[mytab.length - 1]);
		MainMenuItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(final java.awt.event.ActionEvent evt) {
				menuaction();
			}
		});
		MainMenuItem.setFont(new java.awt.Font("System", 0, 13));
		PMmenuitem = new MyMenuItem(mytab[mytab.length - 1]);
		PMmenuitem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(final java.awt.event.ActionEvent evt) {
				menuaction();

			}
		});
		this.setUserObject(mytab[mytab.length - 1]);

	}

	public void menuaction() {
		PaletteManager.deselectgeomgroup();
		pipe_tools.setMacroHelp(m);
		ZF.runMacro(m);
	}

	public void runZmacro() {
		ZF.runMacro(m);
	}

	public void setType(final int newtype) {
                macrotype=newtype;
                m.setProtected(macrotype==MacroTools.LIBRARY_MACRO);
	}

	public void ActualisePath() {
		if (this.isLeaf()) {
			final TreeNode[] mypath = this.getPath();
			name = "";
			for (int i = 1; i < mypath.length - 1; i++) {
				name += mypath[i].toString() + "/";
			}
			name += mypath[mypath.length - 1].toString();
			ZF.ZC.renameMacro(m, name);
			PMmenuitem.setLabel(mypath[mypath.length - 1].toString());
			MainMenuItem.setText(mypath[mypath.length - 1].toString());
		}
	}
        
}
