/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.macros;

import eric.FileTools;
import eric.GUI.palette.PaletteManager;
import eric.JZirkelCanvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Vector;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.plaf.SeparatorUI;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import rene.gui.Global;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.macro.Macro;
import rene.zirkel.macro.MacroItem;

/**
 *
 * @author erichake
 */
public class NodePopupMenu extends JPopupMenu {

    private final CTree macrostree;
    private JDefaultMutableTreeNode SelectedNode;
    private TreePath[] SelectedPath;
    private JMenuItem runitem, renitem, delitem, tolibitem, tofileitem, saveitem, updtitem, propitem, dupitem;

    public NodePopupMenu(CTree mytree) {
        macrostree=mytree;
        dupitem=new JMenuItem(Global.Loc("macros.popup.duplicate"));
        dupitem.addActionListener(new ActionListener() {

            public void actionPerformed(final ActionEvent event) {
                duplicatenodes();
            }
        });

        propitem=new JMenuItem(Global.Loc("macros.popup.properties"));
        propitem.addActionListener(new ActionListener() {

            public void actionPerformed(final ActionEvent event) {
                showproperties();
            }
        });

        runitem=new JMenuItem(Global.Loc("macros.popup.run"));
        runitem.addActionListener(new ActionListener() {

            public void actionPerformed(final ActionEvent event) {
//				runmacro();
            }
        });

        renitem=new JMenuItem(Global.Loc("macros.popup.rename"));
        renitem.addActionListener(new ActionListener() {

            public void actionPerformed(final ActionEvent event) {
                renamenode();
            }
        });

        delitem=new JMenuItem(Global.Loc("macros.popup.delete"));
        delitem.addActionListener(new ActionListener() {

            public void actionPerformed(final ActionEvent event) {
                deletenodes();
            }
        });

        tolibitem=new JMenuItem(Global.Loc("macros.popup.addtolibrary"));
        tolibitem.addActionListener(new ActionListener() {

            public void actionPerformed(final ActionEvent event) {
                changeMacroType(0);
            }
        });

        tofileitem=new JMenuItem(Global.Loc("macros.popup.removefromlibrary"));
        tofileitem.addActionListener(new ActionListener() {

            public void actionPerformed(final ActionEvent event) {
                changeMacroType(2);
            }
        });



        saveitem=new JMenuItem(Global.Loc("macros.popup.saveas"));
        saveitem.addActionListener(new ActionListener() {

            public void actionPerformed(final ActionEvent event) {
                saveMacros();
            }
        });
    }

    public void handleMouseClick(final MouseEvent e) {
        final TreePath path=macrostree.getPathForLocation(e.getX(), e.getY());
        if (path!=null) {
            SelectedNode=(JDefaultMutableTreeNode) path.getLastPathComponent();
            SelectedPath=macrostree.getSelectionPaths();
            if ((SelectedPath.length==1)&&(SelectedNode.isLeaf())) {
                if (JMacrosInspector.isDialogVisible()) {
                    JMacrosInspector.changemacro(SelectedNode);
                } else {
                    PaletteManager.deselectgeomgroup();
                    SelectedNode.runZmacro();
                }
            }
        }
    }

    void actualiseproperties() {
//        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void showproperties() {
        JMacrosInspector.openInspector(SelectedNode);
//                new JMacrosInspector(SelectedNode);
    }

    public void handlePopup(final MouseEvent e) {
        if (e.isPopupTrigger()) {
            final TreePath path=macrostree.getPathForLocation(e.getX(), e.getY());

            if (path!=null) {
                macrostree.addSelectionPath(path);
                SelectedNode=(JDefaultMutableTreeNode) path.getLastPathComponent();
                SelectedPath=macrostree.getSelectionPaths();
                // initSelectedPath();

                this.removeAll();

                if (SelectedPath.length>1) {

                    this.add(delitem);
                    if (!JZirkelCanvas.isRestrictedSession()) {
                        this.add(createSeparator());
                        this.add(tolibitem);
                        this.add(tofileitem);
                        this.add(createSeparator());
                        this.add(saveitem);
                    }
                } else {
                    if (SelectedNode.isLeaf()) {
                        this.add(renitem);
                        if (!JZirkelCanvas.isRestrictedSession()) {
                            this.add(delitem);
                            this.add(dupitem);
                        }
                        this.add(createSeparator());
                        if (!JZirkelCanvas.isRestrictedSession()) {
                            this.add(tolibitem);
                            this.add(tofileitem);
                            this.add(createSeparator());
                            this.add(saveitem);
                            this.add(createSeparator());
                        }
                        this.add(runitem);
                        this.add(createSeparator());
                        this.add(propitem);
                    } else {
                        this.add(renitem);
                        this.add(delitem);
                        this.add(createSeparator());
                        if (!JZirkelCanvas.isRestrictedSession()) {
                            this.add(tolibitem);
                            this.add(tofileitem);
                            this.add(createSeparator());
                            this.add(saveitem);
                        }
                    }
                }

                this.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }

    public void addfolder() {
        JDefaultMutableTreeNode root;

        final JDefaultMutableTreeNode node=new JDefaultMutableTreeNode(Global.Loc("macros.untitledfolder"));
        node.add(new JDefaultMutableTreeNode(Global.Loc("macros.emptynode")));

        final TreePath[] paths=macrostree.getSelectionPaths();
        if ((paths)!=null) {
            root=(JDefaultMutableTreeNode) paths[0].getLastPathComponent();

            if (root.isLeaf()) {
                // if the first selected node is a leaf :
                final DefaultMutableTreeNode father=(DefaultMutableTreeNode) root.getParent();
                final int i=father.getIndex(root)+1;

                ((DefaultTreeModel) macrostree.getModel()).insertNodeInto(node,
                        father, i);

            } else {
                // if the first selected node is a folder :
                ((DefaultTreeModel) macrostree.getModel()).insertNodeInto(node,
                        root, root.getChildCount());
            }
        } else {
            // There is no selected node :
            ((DefaultTreeModel) macrostree.getModel()).insertNodeInto(node,
                    macrostree.JML.getTopNode(),
                    macrostree.JML.getTopNode().getChildCount());
        }

        // Transformation d'un noeud en TreePath :
        final TreePath tp=new TreePath(node.getPath());
        macrostree.setEditable(true);
        macrostree.startEditingAtPath(tp);

    }

    private void updateMacrosVector(JDefaultMutableTreeNode node) {
        if (node.m==null) {
            return;
        }
        String name=node.m.Name;
        if (node.m.isProtected()) {
            int ZCsSize=JZirkelCanvas.getZCsSize();
            // delete the macro from all the ZirkelCanvas (in different tabs) :
            for (int size=0; size<ZCsSize; size++) {
                Vector V=JZirkelCanvas.getZC(size).getMacros();
                for (int i=0; i<V.size(); i++) {
                    MacroItem mi=(MacroItem) V.get(i);
                    if (name.equals(mi.M.Name)) {
                        V.remove(mi);
                        continue;
                    }
                }
            }
            // delete the macro from the library :
            Vector W=MacroTools.getLibraryMacros();
            for (int i=0; i<W.size(); i++) {
                MacroItem mi=(MacroItem) W.get(i);
                if (name.equals(mi.M.Name)) {
                    W.remove(mi);
                    continue;
                }
            }
        } else {
            // remove macro from the current ZirkelCanvas :
            Vector V=JZirkelCanvas.getCurrentZC().getMacros();
            for (int i=0; i<V.size(); i++) {
                MacroItem mi=(MacroItem) V.get(i);
                if (name.equals(mi.M.Name)) {
                    V.remove(mi);
                    return;
                }
            }
        }
    }

    private void parse_deletenodes(JDefaultMutableTreeNode node) {
        if (!(node.isLeaf())) {
            for (int i=0; i<node.getChildCount(); i++) {
                parse_deletenodes((JDefaultMutableTreeNode) node.getChildAt(i));
            }
        } else {
            updateMacrosVector(node);
        }
    }

    public void deletenodes() {

        final TreePath[] paths=macrostree.getSelectionPaths();
        if ((paths)!=null) {
            final Object[] options={"Ok", "Cancel"};
            final int rep=JOptionPane.showOptionDialog(null, Global.Loc("macros.question.delete"), "Warning",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                    null, options, options[0]);
            if (rep==0) {
                for (final TreePath path : paths) {
                    final JDefaultMutableTreeNode node=(JDefaultMutableTreeNode) path.getLastPathComponent();
                    parse_deletenodes(node);

                    JDefaultMutableTreeNode father=(JDefaultMutableTreeNode) node.getParent();
                    ((DefaultTreeModel) macrostree.getModel()).removeNodeFromParent(node);
                    while (father.getChildCount()==0) {
                        final JDefaultMutableTreeNode grandfather=(JDefaultMutableTreeNode) father.getParent();
                        ((DefaultTreeModel) macrostree.getModel()).removeNodeFromParent(father);
                        father=grandfather;
                    }
                }
            }
        }
    }

    public static String uniqueMacroName(String base) {
        ZirkelCanvas zc=JZirkelCanvas.getCurrentZC();
        if (zc==null) {
            return base;
        }
        base=base.replaceAll("[\\s0-9]+$", "");
        Vector V=zc.getMacros();
        int num=0;
        loop:
        for (int i=0; i<V.size(); i++) {
            MacroItem mi=(MacroItem) V.get(i);
            if (base.equals(mi.M.Name)) {
                num++;
                base=base.replaceAll("[\\s0-9]+$", "")+" "+num;
                continue loop;
            }
        }
        return base;
    }

    public void duplicatenodes() {
        try {
            ZirkelCanvas zc=JZirkelCanvas.getCurrentZC();
            // this is a very dirty way to clone a macro :
            MacroItem mi=new MacroItem((Macro) SelectedNode.m.clone(), null);
            final String[] mytab=mi.M.Name.split("/");
            mi.M.setName(uniqueMacroName(mytab[mytab.length-1]));
            final Vector ZFMacros=new Vector();
            ZFMacros.add(mi);

            final ByteArrayOutputStream out=new ByteArrayOutputStream();
            zc.save(out, false, true, true, false, ZFMacros, "");
            zc.load(new ByteArrayInputStream(out.toByteArray()), false, true);
            JZirkelCanvas.ActualiseMacroPanel();
        } catch (Exception e) {
        }
    }

    public void renamenode() {
        final TreePath[] paths=macrostree.getSelectionPaths();
        if ((paths)!=null) {
            macrostree.setEditable(true);
            macrostree.startEditingAtPath(paths[0]);
        }
    }

    private void changeMacroType(final int newtype) {
        for (final TreePath element : SelectedPath) {
            SelectedNode=(JDefaultMutableTreeNode) element.getLastPathComponent();
            parse_changeMacroType(SelectedNode, newtype);
        }
        macrostree.repaint();
        MacroTools.updateLibraryMacros();
        MacroTools.populateMacrosTypeChanges();
    }

    private void parse_changeMacroType(final JDefaultMutableTreeNode node, final int newtype) {
        if (node.isLeaf()) {
            node.setType(newtype);
        } else {
            for (int i=0; i<node.getChildCount(); i++) {
                parse_changeMacroType((JDefaultMutableTreeNode) node.getChildAt(i), newtype);
            }
        }
    }

    private void saveMacros() {
        Vector ZFMacros;

        ZFMacros=new Vector();
        for (final TreePath element : SelectedPath) {
            SelectedNode=(JDefaultMutableTreeNode) element.getLastPathComponent();
            parse_saveMacros(SelectedNode, ZFMacros);
        }

        String filename=FileTools.getSaveFile(false);
        if (filename!=null) {
            final String ext=(filename.endsWith(".mcr"))?"":".mcr";
            JZirkelCanvas.getCurrentZF().dosave(filename+ext, false, true, true, false, ZFMacros);
        }
    }

    private void parse_saveMacros(final JDefaultMutableTreeNode node,
            final Vector ZFMacros) {
        if (node.isLeaf()) {
            final MacroItem mi=new MacroItem(node.m, null);
            ZFMacros.add(mi);
        } else {
            for (int i=0; i<node.getChildCount(); i++) {
                parse_saveMacros((JDefaultMutableTreeNode) node.getChildAt(i),
                        ZFMacros);
            }
        }
    }

    private static final JSeparator createSeparator() {
        final JSeparator jsep=new JSeparator(JSeparator.HORIZONTAL);
        final Dimension d=new Dimension(200, 12);
        jsep.setMaximumSize(d);
        jsep.setMinimumSize(d);
        jsep.setPreferredSize(d);
        jsep.setSize(d);
        jsep.setUI(new MiddleSeparatorUI());
        return jsep;
    }

    private static final class MiddleSeparatorUI extends SeparatorUI {

        @Override
        public void paint(final Graphics g, final JComponent c) {
            final Dimension s=c.getSize();
            final int middleHeight=(s.height-1)/2;

            g.setColor(Color.lightGray);
            g.drawLine(0, middleHeight, s.width, middleHeight);

            g.setColor(Color.white);
            g.drawLine(0, middleHeight+1, s.width, middleHeight+1);
        }
    }
}
