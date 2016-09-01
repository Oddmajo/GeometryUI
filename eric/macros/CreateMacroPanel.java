/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.macros;

import eric.GUI.palette.PaletteManager;
import eric.GUI.pipe_tools;
import eric.GUI.themes;
import eric.JZirkelCanvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import eric.JEricPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.text.JTextComponent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import rene.gui.Global;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.ZirkelFrame;
import rene.zirkel.construction.Construction;
import rene.zirkel.construction.ConstructionException;
import rene.zirkel.macro.Macro;
import rene.zirkel.objects.ConstructionObject;

/**
 *
 * @author erichake
 */
/**************************************************************************************
 *** N'oublions pas que JMacrosList est un JPanel qui contient d'autres
 * JPanels La class Jcreatemacro contient les éléments UI qui gèrent
 * l'enregistrement des macros
 ***************************************************************************************/
public class CreateMacroPanel extends JEricPanel {

    private JButton nextbtn;
    private static int stepnum;
    private boolean visible=false;
    private static stepcomments steps;
    private static MacrosList ML;
    private static CreateMacroDialog CMD;
    private static CreateMacroPanel CMP;

    public CreateMacroPanel(CreateMacroDialog md, MacrosList ml, int w, int h) {
        CMP=this;
        ML=ml;
        CMD=md;
        final ZirkelFrame zf=JZirkelCanvas.getCurrentZF();
        if (zf!=null) {
            this.setLayout(new javax.swing.BoxLayout(this,
                    javax.swing.BoxLayout.X_AXIS));
            this.setAlignmentX(0F);
            setOpaque(false);
            setFocusable(false);
            stepnum=1;
            zf.settool(ZirkelFrame.NParameters);
            this.visible=true;
            PaletteManager.fixsize(this, w, h);
            steps=new stepcomments();
            nextbtn=new JButton(themes.getIcon("Mnext.png"));
            nextbtn.setOpaque(false);
            nextbtn.setContentAreaFilled(false);
            nextbtn.setEnabled(true);
            nextbtn.setBorder(BorderFactory.createEmptyBorder());
            nextbtn.setAlignmentY(0.5F);
            nextbtn.addMouseListener(new MouseAdapter() {

                @Override
                public void mousePressed(final MouseEvent e) {
                    nextStep();
                }
            });
            this.add(margin(10));
            this.add(steps);
            this.add(margin(10));
            this.add(nextbtn);
            ML.validate();
            ML.repaint();
        }
    }

    static public void nextStep() {
        ZirkelFrame zf=JZirkelCanvas.getCurrentZF();
        if (zf==null) {
            return;
        }
        // if parameters are selected :
        if (zf.ZC.getConstruction().Parameters.size()>0) {

            switch (stepnum) {
                case 1:
                    if ((zf.ZC.isDP())&&(!zf.ZC.isEuclidian())) {
                        Construction c=zf.ZC.getConstruction();
                        ConstructionObject hz=c.getHZ();
                        hz.setMainParameter(true);
                        c.insertParameter(hz, 0);
                        hz.setSpecialParameter(true);
                    }
                    steps.mycomment.setText("2/3 - "+Global.Loc("macros.finals"));
                    steps.myparams.setText("");
                    zf.settool(ZirkelFrame.NTargets);
                    break;
                case 2:
                    steps.mycomment.setText("3/3 - "+Global.Loc("macros.name"));
                    steps.myparams.setEditable(true);
                    steps.myparams.setFocusable(true);
                    steps.myparams.setHorizontalAlignment(SwingConstants.CENTER);
                    steps.myparams.setText(Global.Loc("macros.untitledmacro"));
                    steps.myparams.selectAll();
                    steps.myparams.requestFocus();
                    pipe_tools.removeMacroPanelKeyInputs();
                    break;
                case 3:
                    valid();
                    break;
            }
            stepnum++;
        } else {
            zf.settool(ZirkelFrame.NParameters);
            setParametersComments();
        }
    }

    static public void setParametersComments() {
        if (CMD!=null) {
            ZirkelCanvas zc=JZirkelCanvas.getCurrentZC();
            if (zc!=null) {
                String p="";
                Vector v=zc.getConstruction().getParameters();
                for (int i=0; i<v.size(); i++) {
                    ConstructionObject o=(ConstructionObject) v.get(i);
                    p+=","+o.getName();
                }
                if (p.startsWith(",")) {
                    p=p.substring(1);
                }
                if (p.equals("")) {
                    CMP.steps.myparams.setHorizontalAlignment(SwingConstants.CENTER);
                    p=Global.Loc("macros.pleaseselect");
                } else {
                    CMP.steps.myparams.setHorizontalAlignment(SwingConstants.RIGHT);
                }
                CMP.steps.myparams.setText(p);
            }
        }
    }

    static public void setTargetsComments() {
        if (CMD!=null) {
            ZirkelCanvas zc=JZirkelCanvas.getCurrentZC();
            if (zc!=null) {
                String p="";
                Vector v=zc.getConstruction().getTargets();
                for (int i=0; i<v.size(); i++) {
                    ConstructionObject o=(ConstructionObject) v.get(i);
                    p+=","+o.getName();
                }
                if (p.startsWith(",")) {
                    p=p.substring(1);
                }
                CMP.steps.myparams.setHorizontalAlignment(SwingConstants.RIGHT);
                CMP.steps.myparams.setText(p);
            }
        }
    }

    static JEricPanel margin(int w) {
        JEricPanel mypan=new JEricPanel();
        PaletteManager.fixsize(mypan, w, 1);
        mypan.setLayout(new javax.swing.BoxLayout(mypan, javax.swing.BoxLayout.X_AXIS));
        mypan.setAlignmentX(0F);
        mypan.setAlignmentY(0F);
        mypan.setOpaque(false);
        mypan.setFocusable(false);
        return mypan;
    }

    public JLabel getComment() {
        return steps.getComment();
    }

    public static void valid() {
        final TreePath tp=createmacro();
        pipe_tools.actualiseLeftPanels();
        PaletteManager.selectGeomIcon();
        CreateMacroDialog.quit();
    }

    public static TreePath createmacro() {
        JDefaultMutableTreeNode root;
        ZirkelCanvas zc=JZirkelCanvas.getCurrentZC();
        if (zc==null) {
            return null;
        }
        final Vector V=zc.getConstruction().Parameters;
        final String s[]=new String[V.size()];
        for (int i=0; i<V.size(); i++) {
            final ConstructionObject o=(ConstructionObject) V.elementAt(i);
            if (o.isSpecialParameter()) {
                s[i]="="+o.getName();
            }else{
                s[i]=o.getName();
            }
        }

        String txt=CMP.steps.myparams.getText();
        if (txt.equals("")) {
            txt=Global.Loc("macros.untitledmacro");
        }
//        final Macro m=new Macro(zc, Global.Loc("macros.untitledmacro"), "", s);
        final Macro m=new Macro(zc, txt, "", s);

        try {

            zc.defineMacro(zc.getConstruction(), m, (zc.getConstruction().countTargets()>0), true, s, false);
        } catch (final ConstructionException e) {
        }
        m.hideDuplicates(false);
        zc.storeMacro(m, false);

        final JDefaultMutableTreeNode node=new JDefaultMutableTreeNode(JZirkelCanvas.getCurrentZF(), m);

        final TreePath[] paths=ML.getMacrosTree().getSelectionPaths();
        if (((paths)!=null)&&(paths.length>0)) {
            root=(JDefaultMutableTreeNode) paths[0].getLastPathComponent();

            if (root.isLeaf()) {
                // if the first selected node is a leaf :
                final DefaultMutableTreeNode father=(DefaultMutableTreeNode) root.getParent();
                final int i=father.getIndex(root)+1;
                ((DefaultTreeModel) ML.getMacrosTree().getModel()).insertNodeInto(
                        node, father, i);

            } else {
                // if the first selected node is a folder :
                ((DefaultTreeModel) ML.getMacrosTree().getModel()).insertNodeInto(
                        node, root, root.getChildCount());
            }
        } else {
            // There is no selected node :
            ((DefaultTreeModel) ML.getMacrosTree().getModel()).insertNodeInto(node,
                    ML.getTopNode(), ML.getTopNode().getChildCount());
        }

        final TreePath tp=new TreePath(node.getPath());
        node.ActualisePath();
        return tp;
    }

    private class stepcomments extends JEricPanel {

        /**
         *
         */
        private JLabel mycomment=new JLabel();
        private JTextField myparams=new JTextField();

        @Override
        public void paintComponent(final java.awt.Graphics g) {
            super.paintComponent(g);
            final java.awt.Dimension d=this.getSize();
            g.drawImage(themes.getImage("Mcomments.png"), 0, 0, d.width,
                    d.height, this);

        }

        public JLabel getComment() {
            return mycomment;
        }

        private stepcomments() {
            this.setLayout(new javax.swing.BoxLayout(this,
                    javax.swing.BoxLayout.Y_AXIS));
            this.setAlignmentY(0.5F);
            final ImageIcon backIcon=themes.getIcon("Mcomments.png");
            PaletteManager.fixsize(this, backIcon.getIconWidth(), backIcon.getIconHeight());
            this.setOpaque(false);

            mycomment.setText("1/3 - "+Global.Loc("macros.initials"));
            mycomment.setFont(new Font("Verdana", 0, 10));
            PaletteManager.fixsize(mycomment, backIcon.getIconWidth(), backIcon.getIconHeight()/2);
            mycomment.setHorizontalAlignment(SwingConstants.CENTER);
            mycomment.setVerticalAlignment(SwingConstants.CENTER);
            mycomment.setAlignmentX(0.5f);
            mycomment.setAlignmentY(0.5f);
            this.add(mycomment);

            myparams.setText(Global.Loc("macros.pleaseselect"));
            myparams.setFont(new Font("Verdana", 0, 10));
            myparams.setForeground(Color.blue);
            myparams.setOpaque(false);
            myparams.setBorder(null);
            myparams.setFocusable(false);
            PaletteManager.fixsize(myparams, backIcon.getIconWidth()-10, backIcon.getIconHeight()/2);
            myparams.setHorizontalAlignment(SwingConstants.CENTER);
//            myparams.setVerticalAlignment(SwingConstants.CENTER);
            myparams.setAlignmentX(0.5f);
            myparams.setAlignmentY(0.5f);
            myparams.addKeyListener(new KeyAdapter() {

                @Override
                public void keyPressed(final KeyEvent e) {
                    if (e.getKeyCode()==KeyEvent.VK_ENTER) {
                        valid();
                    }
                }
            });
            this.add(myparams);
        }
    }
}
