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

import eric.GUI.themes;
import eric.JGeneralMenuBar;
import eric.JZirkelCanvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import eric.JEricPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import rene.gui.Global;

import rene.gui.MyMenu;
import rene.zirkel.ZirkelFrame;
import rene.zirkel.macro.Macro;
import rene.zirkel.macro.MacroItem;

// Toute ce qui touche à l'arbre de macros et à sa gestion :
public class MacrosList extends JEricPanel {

    private final int CONTROLHEIGHT=25;
    private final ImageIcon JTreefoldclosed;
    private final ImageIcon JTreefoldopened;
    private final ImageIcon[] JTreeleaf;
    private CTree MacrosTree;
    private JDefaultMutableTreeNode MacroTreeTopNode=new JDefaultMutableTreeNode("Macros");
    private ZirkelFrame ZF;
    private JScrollPane jscrolls;
    private JControls controls;
//    private  Jcreatemacro createmacropanel;

    public MacrosList(ZirkelFrame zf) {
        ZF=zf;
        JTreefoldclosed=themes.getIcon("JTreefoldclosed.gif");
        JTreefoldopened=themes.getIcon("JTreefoldopened.gif");
        JTreeleaf=new ImageIcon[4];
        JTreeleaf[0]=themes.getIcon("JTreeleaf_0.gif");
        JTreeleaf[1]=themes.getIcon("JTreeleaf_1.gif");
        JTreeleaf[2]=themes.getIcon("JTreeleaf_2.gif");
        JTreeleaf[3]=themes.getIcon("JTreeleaf_3.gif");
        this.setLayout(new javax.swing.BoxLayout(this,
                javax.swing.BoxLayout.Y_AXIS));

        // uncomment this line to obtain gray conection lines between leaves :
        // UIManager.put("Tree.hash",new ColorUIResource(Color.lightGray));

        MacroTreeTopNode=new JDefaultMutableTreeNode("Macros");
        MacrosTree=new CTree(this) {

            /**
             *
             */
            @Override
            public void paint(final Graphics g) {
                final ImageIcon backimage=themes.getIcon("macrospanelback.gif");
                g.drawImage(backimage.getImage(), 0, 0, this.getSize().width,
                        backimage.getIconHeight(), this);
                super.paint(g);
            }
        };
        MacrosTree.setFocusable(false);
        MacrosTree.setModel(new MyTreeModel(MacroTreeTopNode));
        MacrosTree.getSelectionModel().setSelectionMode(
                TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
        final MyCellRenderer renderer=new MyCellRenderer();
        MacrosTree.setCellRenderer(renderer);
        MacrosTree.setCellEditor(new MyDefaultCellEditor());
        MacrosTree.setOpaque(false);
        MacrosTree.setFont(new Font(Global.GlobalFont, 0, 12));
        MacrosTree.setForeground(new Color(70, 70, 70));
        MacrosTree.setDragEnabled(false);
        MacrosTree.setEditable(false);

        jscrolls=new JScrollPane(MacrosTree);
        jscrolls.setAlignmentX(0F);
        jscrolls.setBorder(BorderFactory.createEmptyBorder());
        this.add(jscrolls);

        controls=new JControls(this);
        this.add(controls);
//        createmacropanel=new Jcreatemacro(this);
//        this.add(createmacropanel);
    }

    public JDefaultMutableTreeNode getTopNode() {
        return MacroTreeTopNode;
    }

    public CTree getMacrosTree() {
        return MacrosTree;
    }

    // Utilise le Vector de macros pour initialiser l'arbre :
    public void initTreeFromZCMacros() {

        Vector mc;
//		JMacrosTools.setDefaultMacros();
        MacroTreeTopNode.removeAllChildren();
//        removeAll();
//        MacroTreeTopNode=new JDefaultMutableTreeNode("Macros");

        mc=ZF.ZC.getMacros();
        for (int i=0; i<mc.size(); i++) {
//            if (((MacroItem) mc.elementAt(i)).M!=null) {
            Macro m=((MacroItem) mc.elementAt(i)).M;
            if ((ZF.ZC.isLibraryMacrosVisible())||(!m.isProtected())) {
                AddMacroToTree(m);
            }
//            AddMacroToTree(((MacroItem) mc.elementAt(i)).M);
//            }
        }

        MacrosTree.setModel(new MyTreeModel(MacroTreeTopNode));
        MacrosTree.getSelectionModel().setSelectionMode(
                TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
        final MyCellRenderer renderer=new MyCellRenderer();
        MacrosTree.setCellRenderer(renderer);
        MacrosTree.setCellEditor(new MyDefaultCellEditor());

        ActualiseMacroPopupMenu();
    }


    // Only called by LeftPanelContent init method :
    public void fixPanelSize(int w, int h) {
        fixsize(this, w, h);
        fixsize(jscrolls, w, h-CONTROLHEIGHT);
	jscrolls.revalidate();
        //fixsize(MacrosTree,w,h-CONTROLHEIGHT);
        fixsize(controls,w,CONTROLHEIGHT);
	controls.revalidate();
    }

    private static void fixsize(final JComponent cp, final int w, final int h) {
        final Dimension d=new Dimension(w, h);
        cp.setMaximumSize(d);
        cp.setMinimumSize(d);
        cp.setPreferredSize(d);
        cp.setSize(d);
    }

    class MyDefaultCellEditor extends DefaultCellEditor {

        /**
         *
         */
        JTextField jtf;

        public MyDefaultCellEditor() {
            super(new JTextField());
            jtf=(JTextField) this.getComponent();
            jtf.setFocusTraversalKeysEnabled(false);
            jtf.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            jtf.addKeyListener(new KeyAdapter() {

                @Override
                public void keyTyped(final KeyEvent e) {
                    adjust(e.getKeyChar());
                }

                @Override
                public void keyPressed(final KeyEvent e) {
                    if ((e.getKeyCode()==KeyEvent.VK_ESCAPE)
                            ||(e.getKeyCode()==KeyEvent.VK_TAB)) {
                        fireEditingStopped();
                    }
                }
            });
        }

        private void adjust(final char ad) {
            final FontMetrics fm=getFontMetrics(jtf.getFont());
            jtf.setSize(fm.stringWidth(jtf.getText()+ad)+5, jtf.getHeight());
        }

        @Override
        protected void fireEditingCanceled() {
            super.fireEditingStopped();
        }

        @Override
        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }

        @Override
        public Object getCellEditorValue() {
            return super.getCellEditorValue();
        }

        @Override
        public Component getTableCellEditorComponent(final JTable table,
                final Object value, final boolean isSelected, final int row,
                final int column) {
            return super.getTableCellEditorComponent(table, value, isSelected,
                    row, column);

        }
    }

    public void AddMacroToTree(final Macro m) {
        if (m.getName().startsWith("@builtin@")) {
            return;
        }
        String[] mypath;
        mypath=m.getName().split("/");
        JDefaultMutableTreeNode mother=MacroTreeTopNode;
        for (int i=0; i<mypath.length-1; i++) {
            mother=getFolder(mother, mypath[i]);
        }
        final JDefaultMutableTreeNode node=new JDefaultMutableTreeNode(ZF, m);
        mother.add(node);
    }

    private JDefaultMutableTreeNode getFolder(
            final JDefaultMutableTreeNode father, final String name) {
        for (int i=0; i<father.getChildCount(); i++) {
            if (name.equals((String) ((JDefaultMutableTreeNode) father.getChildAt(i)).getUserObject())) {
                return ((JDefaultMutableTreeNode) father.getChildAt(i));
            }
        }
        final JDefaultMutableTreeNode node=new JDefaultMutableTreeNode(name);
        father.add(node);
        return node;
    }

    // Actualisation du PopupMenu de macro, du menu principal "Macros", et du vector librarymacros
    // Appelée à chaque modification de l'arbre (Drag and Drop, Rename, ...)
    // appelée aussi à la fin de initMacrosTreeFromPopup :
    public void ActualiseMacroPopupMenu() {

        final MyMenu pm=new MyMenu("root");
        final JMenu jm=new JMenu("root");

        if (MacroTreeTopNode.getChildCount()>0) {
            JPopupMenu.setDefaultLightWeightPopupEnabled(false);

            ParseMacroTree(pm, jm, MacroTreeTopNode, "root");

            ZF.ZC.PM.removeAll();
            JGeneralMenuBar.s_InitMacrosMenu();

            final JMenu jmroot=(JMenu) jm.getItem(0);
            final MyMenu pmroot=(MyMenu) pm.getItem(0);
            while (pmroot.getItemCount()>0) {
                ZF.ZC.PM.add(pmroot.getItem(0));
                JGeneralMenuBar.addMacrosMenu(jmroot.getItem(0));


//                if (ZF.ZC.isLibraryMacrosVisible()) {
//                    ZF.ZC.PM.add(pmroot.getItem(0));
//                    JGeneralMenuBar.addMacrosMenu(jmroot.getItem(0));
//                } else {
//                    pmroot.remove(0);
//                    jmroot.remove(0);
//
//                }
            }

        } else {
            ZF.ZC.PM.removeAll();
            JGeneralMenuBar.s_InitMacrosMenu();
        }
        MacroTools.updateLibraryMacros();
    }

    // Procédure recursive appelée uniquement par ActualiseMacroPopupMenu.
    // Parcours de l'arbre de macros :
    private void ParseMacroTree(final MyMenu PMmenu, final JMenu JMmenu,
            final JDefaultMutableTreeNode node, final String path) {
        final String mypath=path;
        if (!(node.isLeaf())) {
            final MyMenu mymenu=new MyMenu((String) node.getUserObject());
            final JMenu myjmenu=new JMenu((String) node.getUserObject());
            myjmenu.setFont(new java.awt.Font("System", 0, 13));
            for (int i=0; i<node.getChildCount(); i++) {
                ParseMacroTree(mymenu, myjmenu, (JDefaultMutableTreeNode) node.getChildAt(i), mypath+"/"+mymenu.getLabel());
            }
            PMmenu.add(mymenu);
            JMmenu.add(myjmenu);
        } else {
            final String myname=(String) node.getUserObject();
            if (!(myname.startsWith("-- "))) {
                node.ActualisePath();
                PMmenu.add(node.PMmenuitem);
                JMmenu.add(node.MainMenuItem);
            } else {
                if (node.getParent().getChildCount()>1) {
                    ((DefaultTreeModel) MacrosTree.getModel()).removeNodeFromParent(node);
                }
            }

        }

    }

    // Les noeuds de l'arbre sont considérés comme des JLabels
    // Cette classe se charge de leurs look :
    class MyCellRenderer extends JLabel implements TreeCellRenderer {

        /**
         *
         */
        public MyCellRenderer() {
            setOpaque(false);
            setBackground(null);
        }

        public Component getTreeCellRendererComponent(final JTree tree,
                final Object value, final boolean sel, final boolean expanded,
                final boolean leaf, final int row, final boolean hasFocus) {
            final String stringValue=tree.convertValueToText(value, sel,
                    expanded, leaf, row, hasFocus);

            setText(stringValue);
            setEnabled(tree.isEnabled());
            setFont(tree.getFont());
            setForeground(Color.black);
            setOpaque(sel);

            // Couleur de sélection :
            setBackground(Color.lightGray);
            final JDefaultMutableTreeNode mynode=(JDefaultMutableTreeNode) value;
            if (leaf) {
                setIcon((stringValue.startsWith(("-- ")))?null
                        :JTreeleaf[mynode.macrotype]);
                if (mynode.macrotype==0) {
                    setForeground(new Color(68, 84, 131));
                }
            } else {
                setIcon((expanded)?JTreefoldopened:JTreefoldclosed);
            }


            return this;
        }
    }

    // Le modèle sur lequel est basé l'arbre
    // Se charge de l'édition des noeuds et contient les TreeModelListeners :
    class MyTreeModel extends DefaultTreeModel implements TreeModelListener {

        /**
         *
         */
        public MyTreeModel(final TreeNode node) {
            super(node);
            this.addTreeModelListener(this);

        }

        @Override
        public void valueForPathChanged(final TreePath path,
                final Object newValue) {
            final JDefaultMutableTreeNode tn=(JDefaultMutableTreeNode) path.getLastPathComponent();
            super.valueForPathChanged(path, newValue);
            tn.ActualisePath();
        }

        public void treeNodesChanged(final TreeModelEvent e) {
            SwingUtilities.invokeLater(new Runnable() {

                public void run() {
                    ActualiseMacroPopupMenu();
                }
            });
        }

        public void treeNodesInserted(final TreeModelEvent e) {
            // System.out.println("treeNodesInserted");
            SwingUtilities.invokeLater(new Runnable() {

                public void run() {
                    ActualiseMacroPopupMenu();
                }
            });
        }

        public void treeNodesRemoved(final TreeModelEvent e) {
            SwingUtilities.invokeLater(new Runnable() {

                public void run() {
                    ActualiseMacroPopupMenu();
                }
            });
        }

        public void treeStructureChanged(final TreeModelEvent e) {
            // System.out.println("treeStructureChanged");
            SwingUtilities.invokeLater(new Runnable() {

                public void run() {
                    ActualiseMacroPopupMenu();
                }
            });
        }
    }

    /**************************************************************************************
     *** N'oublions pas que JMacrosList est un JPanel qui contient d'autres
     * JPanels La class Jcontrols contient les boutons d'édition de l'arbre et
     * leurs listeners
     ***************************************************************************************/
    class JControls extends JEricPanel {

        /**
         *
         */
        private JButton addbtn;
        private JButton delbtn;
        private JButton renbtn;
        private JButton createbtn;
        private String message="";
        private MacrosList MI;

        @Override
        public void paintComponent(final java.awt.Graphics g) {
            super.paintComponent(g);
            final java.awt.Dimension d=this.getSize();
            g.drawImage(themes.getImage("tab_bottom.gif"), 0, 0, d.width,
                    d.height, this);
        }

        public void setButtonsIcons() {
            addbtn.setIcon(themes.getIcon("addmacrofolder.png"));
            addbtn.setRolloverIcon(themes.getIcon("addmacrofoldersel.png"));
            delbtn.setIcon(themes.getIcon("delmacro.png"));
            delbtn.setToolTipText(Global.Loc("macros.deleteselected"));
            renbtn.setIcon(themes.getIcon("renamemacro.png"));
            renbtn.setRolloverIcon(themes.getIcon("renamemacrosel.png"));
            createbtn.setIcon(themes.getIcon("createmacro.png"));
            createbtn.setRolloverIcon(themes.getIcon("createmacroover.png"));

        }

        public JControls(MacrosList mi) {
            MI=mi;
            this.setLayout(new javax.swing.BoxLayout(this,
                    javax.swing.BoxLayout.X_AXIS));
            this.setAlignmentX(0F);
            addbtn=new JButton();
            addbtn.setToolTipText(Global.Loc("macros.addfolder"));
            addbtn.setOpaque(false);
            addbtn.setContentAreaFilled(false);
            addbtn.setBorder(BorderFactory.createEmptyBorder());
            addbtn.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(final MouseEvent e) {
                    MacrosTree.nodepopup.addfolder();
                }
            });

            delbtn=new JButton();
            delbtn.setOpaque(false);
            delbtn.setContentAreaFilled(false);
            delbtn.setBorder(BorderFactory.createEmptyBorder());
            delbtn.setRolloverIcon(themes.getIcon("delmacrosel.png"));
            delbtn.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(final MouseEvent e) {
//                    MacrosTree.nodepopup.deletenodes();
                    System.out.println("size="+JZirkelCanvas.getCurrentZC().getMacros().size());
                }
            });

            renbtn=new JButton();
            renbtn.setToolTipText(Global.Loc("macros.renamemacro"));
            renbtn.setOpaque(false);
            renbtn.setContentAreaFilled(false);
            renbtn.setBorder(BorderFactory.createEmptyBorder());
            renbtn.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(final MouseEvent e) {
                    MacrosTree.nodepopup.renamenode();
                }
            });

            createbtn=new JButton();
            createbtn.setToolTipText(Global.Loc("macros.recordmacro"));
            createbtn.setSelectedIcon(themes.getIcon("createmacrosel.png"));
            createbtn.setBorder(BorderFactory.createEmptyBorder());
            createbtn.setOpaque(false);
            createbtn.setContentAreaFilled(false);
            createbtn.setSelected(false);

            setButtonsIcons();

            createbtn.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseEntered(final MouseEvent e) {
//                    if (createbtn.isSelected()) {
//                        message=createmacropanel.getComment().getText();
//                        createmacropanel.getComment().setText(Global.Loc("macros.cancel"));
//                    }
                }

                @Override
                public void mouseExited(final MouseEvent e) {
//                    if (createbtn.isSelected()) {
//                        if (message!="") {
//                            createmacropanel.getComment().setText(message);
//                        }
//                    }
                }

                @Override
                public void mousePressed(final MouseEvent e) {
                    if (createbtn.isSelected()) {
                        createbtn.setSelected(false);
//                        createmacropanel.disappeargently();
//                        PaletteManager.setSelected("point", true);
                    } else {
                        createbtn.setSelected(true);
                        new CreateMacroDialog(MI);
//                        createmacropanel.appeargently();
//                        PaletteManager.deselectgeomgroup();
                    }

                }
            });

            final JEricPanel spacer=new JEricPanel();
            spacer.setOpaque(false);

            this.add(addbtn);
            this.add(delbtn);
            this.add(renbtn);
            this.add(spacer);
            this.add(createbtn);
        }
    }
}
