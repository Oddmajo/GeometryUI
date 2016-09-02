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

import eric.GUI.pipe_tools;
import eric.GUI.themes;
import eric.JZirkelCanvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import eric.JEricPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import rene.gui.Global;
import rene.zirkel.ZirkelCanvas;

import rene.zirkel.ZirkelFrame;
import rene.zirkel.macro.Macro;
import rene.zirkel.objects.ConstructionObject;

/* This class represent the macro properties inspector
 */
public class JMacrosInspector extends JDialog implements MouseListener, MouseMotionListener, TopDialog {

    private static Macro M=null;
    private int PW=310; // Palette width
    private int PH=395;
    private static JDefaultMutableTreeNode node;
    private IContent content;
    private MouseEvent pressed;
    private Point location;
    private static JMacrosInspector me;

    public JMacrosInspector(JDefaultMutableTreeNode mymacro) {
        setFocusableWindowState(true);
        setFocusable(false);


        this.setSize(PW, PH);

        setUndecorated(true);

        if (!pipe_tools.isApplet()) {
            setAlwaysOnTop(true);
        } else {
            addWindowListener(new WindowAdapter() {

                public void windowDeactivated(WindowEvent e) {
                    toFront();
                }
            });
        }

        // content is a JPanel which represents the content of the palette
        content=new IContent(this);
        setContentPane(content);

        DialogTitleBar title=new DialogTitleBar(this, PW);
        content.add(title, 0);

        ZirkelCanvas zc=JZirkelCanvas.getCurrentZC();
        if (zc!=null) {
            Point p=zc.getLocationOnScreen();
            setLocation(p.x+10, p.y+(zc.getSize().height-PH)/2);
        }

        setVisible(true);


    }

    public static boolean isDialogVisible() {
        return (me!=null);
    }

    public static void openInspector(JDefaultMutableTreeNode mymacro) {
        if (me==null) {
            me=new JMacrosInspector(mymacro);
        }
        changemacro(mymacro);
    }

    public static void changemacro(JDefaultMutableTreeNode mymacro) {
        if (me!=null) {
            me.setMacro(mymacro);
        }
    }

    // set location of the palette (near the right border of the macro panel) :
    public void setStandardLocation() {
    }

    public void clearPalette() {
        if (M!=null) {
            content.changemacro();
        }
        node=null;
        M=null;
        content.clearfields();
    }

    // method called each time the user ask properties or select another macro
    // in the tree
    public void setMacro(final JDefaultMutableTreeNode mynode) {
        node=mynode;
        M=node.m;
        content.fillfields();
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent me) {
        pressed=me;
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent me) {
        location=getLocation(location);
        int x=location.x-pressed.getX()+me.getX();
        int y=location.y-pressed.getY()+me.getY();
        setLocation(x, y);
        Toolkit.getDefaultToolkit().sync();
    }

    public void mouseMoved(MouseEvent e) {
    }

    public static void quit() {
        if (me!=null) {
            me.exit();
        }
    }

    public void exit() {
        content.changemacro();
        node=null;
        me=null;
        dispose();
    }

    public void fixObject(int i, boolean b) {
        content.fixObject(i, b);
    }

    public void askObject(int i, boolean b) {
        content.askObject(i, b);
    }

    // this embedded class represents the content of the palette :
    public class IContent extends JEricPanel {

        /**
         *
         */
        JMacrosInspector JMI;
        JLabel name;
        JTextArea comment;
        JMacrosProperties props;
        JCheckBox hideDuplicates;

        private JEricPanel margin(final int w) {
            final JEricPanel mypan=new JEricPanel();
            fixsize(mypan, w, 1);
            mypan.setOpaque(false);
            return mypan;
        }

        public IContent(final JMacrosInspector jmi) {
            JMI=jmi;
            setLayout(new javax.swing.BoxLayout(this,
                    javax.swing.BoxLayout.Y_AXIS));
            this.setFocusable(false);
            name=new JLabel();
            comment=new JTextArea();
            props=new JMacrosProperties(JMI);
            hideDuplicates=new JCheckBox(Global.Loc("mi.hideduplicates"));

            newnameline();
            this.add(new mySep(1));
            newcommentline();
            this.add(new mySep(1));
            newproperties();
            this.add(new mySep(1));
            newhideproperties();
            this.add(new mySep(1));
            newcontrolline();

            fixsize(this, PW, PH);
        }

        // set sizes of a palette's JComponent :
        private void fixsize(final JComponent cp, final int w, final int h) {
            final Dimension d=new Dimension(w, h);
            cp.setMaximumSize(d);
            cp.setMinimumSize(d);
            cp.setPreferredSize(d);
            cp.setSize(d);
        }

        // add the "name" topic of the palette :
        public void newnameline() {
            final JEricPanel rub=new myRub();
            final JEricPanel myline1=new ContentLine(25);
            fixsize(name, PW-10, 18);
            myline1.add(margin(5));
            myline1.add(name);
            rub.add(myline1);
            this.add(rub);
        }

        // add the "comment" topic of the palette :
        public void newcommentline() {
            final JEricPanel rub=new myRub();
            final JScrollPane jScroll=new JScrollPane();
            final JEricPanel myline1=new ContentLine(22);
            final JLabel namelabel=new JLabel(Global.Loc("mi.comment"));
            fixsize(namelabel, PW-10, 14);
            myline1.add(margin(5));
            myline1.add(namelabel);
            final JEricPanel myline2=new ContentLine(100);

            comment.setLineWrap(true);
            jScroll.setViewportView(comment);
            jScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            fixsize(jScroll, PW-10, 80);
            myline2.add(margin(5));
            myline2.add(jScroll);
            rub.add(myline1);
            rub.add(myline2);
            this.add(rub);
        }

        // add the "target properties" topic of the palette :
        public void newhideproperties() {
            final JEricPanel rub=new myRub();
            rub.setLayout(new javax.swing.BoxLayout(rub,
                    javax.swing.BoxLayout.Y_AXIS));
            final JEricPanel myline1=new ContentLine(22);
            final JLabel namelabel=new JLabel(Global.Loc("mi.hideproperties"));
            fixsize(namelabel, PW-10, 14);
            myline1.add(margin(5));
            myline1.add(namelabel);
            final JEricPanel mylineC3=new ContentLine(27);

            hideDuplicates.setOpaque(false);
            mylineC3.add(margin(20));
            mylineC3.add(hideDuplicates);
            rub.add(myline1);
            rub.add(mylineC3);
            this.add(rub);
        }

        // add the apply button to the bottom of the palette :
        public void newcontrolline() {
            final JEricPanel rub=new myRub();
            final JEricPanel myline=new ContentLine(25);
            final JButton applybtn=new JButton("Apply");
            applybtn.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(final MouseEvent e) {
                    changemacro();
                    // It's important to refresh the inspector because
                    // parameters position may have change :
                    fillfields();
                }
            });
            // applybtn.setBorder(BorderFactory.createRaisedBevelBorder());
            // applybtn.setBorder(BorderFactory.createEmptyBorder());
            fixsize(applybtn, 90, 18);
            applybtn.setFont(new Font("System", Font.BOLD, 11));
            final JEricPanel sep=new JEricPanel();
            sep.setOpaque(false);
            myline.add(sep);

            // JButton jb=new JButton("Aaaa");
            // fixsize(jb,90,18);

            myline.add(applybtn);
            myline.add(margin(5));
            rub.add(myline);
            this.add(rub);
        }

        // add the parameters properties to the palette :
        public void newproperties() {
            final JEricPanel rub=new myRub();
            final JEricPanel myline1=new ContentLine(22);
            final JLabel namelabel=new JLabel(Global.Loc("mi.properties"));
            myline1.add(margin(5));
            myline1.add(namelabel);
            fixsize(namelabel, PW-10, 14);
            final JEricPanel myline2=new ContentLine(130);
            fixsize(props, PW-10, 100);
            myline2.add(margin(5));
            myline2.add(props);
            rub.add(myline1);
            rub.add(myline2);
            this.add(rub);
        }

        /*************************************************
         * this is the tricky method : it reads the inspector changes and then
         * store the new values in the macro m. A macro contains two types of
         * parameters : 1) normal parameters (the one you shows at the first
         * step of macro's creation ). They are inside the m.Params array for
         * ConstructionObjects and m.Prompts array for prompts 2) numerical
         * input parameters (it's possible to make macros with numerical inputs
         * ). Name of Objects are in the PromptFor array and prompts in the
         * PromptName array
         *************************************************/
        public void changemacro() {
            ConstructionObject[] params;
            final Vector newparams=new Vector();
            final Vector newprompts=new Vector();
            final Vector newpromptFor=new Vector();
            final Vector newpromptName=new Vector();
            props.stopCellEditing();
            if (M==null) {
                return;
            }
            if (isError()) {
                return;
            }
            M.setComment(comment.getText());
            params=M.getParams();



            // read "normal" parameters and store them in :
            // newparams and newprompts if "ask" is not checked (stays "normal")
            // newPromptFor and newPromptName if "ask" is not checked (becomes
            // "numerical input")
            for (int i=0; i<params.length; i++) {
                params[i].setName(props.getOName(i));
                if (props.getOAsk(i)) {
                    newpromptFor.add(props.getOName(i));
                    newpromptName.add(props.getOPrompt(i));
                    params[i].setHidden(true);
                    params[i].clearParameter();
                } else {
                    newparams.add(params[i]);
                    if (props.getOFix(i)) {
                        newprompts.add("="+props.getOName(i));
                    } else {
                        newprompts.add(props.getOPrompt(i));
                    }

                }
            }


            int rang=0;
            // read "numerical input" parameters and store them in :
            // newparams and newprompts if "ask" is not checked (stays "normal")
            // newPromptFor and newPromptName if "ask" is not checked (becomes
            // "numerical input")
            for (int i=params.length; i<props.getRowCount(); i++) {
                ConstructionObject E=null;
                // looking for the expression with the name
                // m.PromptFor[i-params.length]
                for (int j=0; j<M.V.size(); j++) {
                    // sure it's going to find one :
                    if (((ConstructionObject) M.V.get(j)).getName().equals(
                            M.PromptFor[i-params.length])) {
                        E=(ConstructionObject) M.V.get(j);
                        break;
                    }
                }

                E.setName(props.getOName(i));
                if (props.getOAsk(i)) {
                    newpromptFor.add(props.getOName(i));
                    newpromptName.add(props.getOPrompt(i));
                } else {
                    newparams.add(rang, E);
                    if (props.getOFix(i)) {
                        newprompts.add(rang, "="+props.getOName(i));
                    } else {
                        newprompts.add(rang, props.getOPrompt(i));
                    }

                    rang++;
                }
            }

            int ln=newparams.size();
            // Clear and prepare the Params, Prompts and LastParams arrays :
            M.Params=new ConstructionObject[ln];
            M.Prompts=new String[ln];
            M.LastParams=new String[ln];

            // Store the newparams and newprompts in the macro :
            for (int i=0; i<ln; i++) {
                M.Params[i]=(ConstructionObject) newparams.get(i);
                M.Prompts[i]=(String) newprompts.get(i);
                M.LastParams[i]=null;
                M.Params[i].setHidden(false);
                M.Params[i].setMainParameter(true);
                M.Params[i].setParameter(true);
            }

            ln=newpromptFor.size();
            // Clear and prepare the PromptFor, PromptName arrays :
            M.PromptFor=new String[ln];
            M.PromptName=new String[ln];
            // Store the newpromptFor and newpromptName in the macro :
            for (int i=0; i<ln; i++) {
                M.PromptFor[i]=(String) newpromptFor.get(i);
                M.PromptName[i]=(String) newpromptName.get(i);
            }

            // Conform the macro's hideduplicate property to the checkbox value
            // :
            M.hideDuplicates(hideDuplicates.isSelected());
        }

        // this method is called by the JMacroProperties object
        // each time a user check/uncheck a "fix" JCheckBox :
        public void fixObject(final int i, final boolean fix) {
            final String newprompt=(fix)?"":props.getOName(i);
            props.setOPrompt(newprompt, i);
            if (fix) {
                props.setOAsk(false, i);
            }
        }

        // this method is called by the JMacroProperties object
        // each time a user check/uncheck a "ask" JCheckBox :
        public void askObject(final int i, final boolean ask) {
            if ((ask)&&props.getOFix(i)) {
                props.setOFix(false, i);
                fixObject(i, false);
            }
        }

        public void clearfields() {
            name.setText(Global.Loc("mi.name"));
            comment.setText("");
            props.removeAllRows();
            hideDuplicates.setSelected(false);
        }

        public boolean isError() {
            final boolean isErr=false;
            // first see if at least one row contains no selected checkbox :
            final int ln=props.getRowCount();
            boolean err=true;
            for (int i=0; i<ln; i++) {
                err=(err)&&((props.getOFix(i))||(props.getOAsk(i)));
            }
            if (err) {
                JOptionPane.showMessageDialog(null,
                        Global.Loc("mi.error.initial"));
                return true;
            }
            return isErr;
        }

        // read the params, prompts, PromptFor, PromptName arrays of
        // the macro and fill the inspector fields :
        public void fillfields() {
            ConstructionObject[] params;
            String[] prompts;
            if (M==null) {
                return;
            }
            name.setText(Global.Loc("mi.name")+" "
                    +(String) node.getUserObject());
            comment.setText(M.Comment);
            props.removeAllRows();
            params=M.getParams();

            prompts=M.getPrompts();
            String pr="";
            // fill JTable first lines with "normal" parameters :
            for (int i=0; i<params.length; i++) {
                pr="="+params[i].getName();
                String tpe="";
                String classtpes[]=params[i].getClass().getName().split("\\.");

                try {
                    tpe=Global.Loc(params[i].getClass().getName());
                } catch (final Exception e) {
                    tpe=classtpes[classtpes.length-1];
                }
                if (tpe==null) {
                    tpe=classtpes[classtpes.length-1];
                }

                final boolean withask=params[i].getClass().getName().endsWith("ExpressionObject");
                // withask=(withask)||(params[i].getClass().getName().endsWith("FixedAngleObject"));
                // withask=(withask)||(params[i].getClass().getName().endsWith("PrimitiveCircleObject"));
                if (withask) {
                    props.addRow(tpe, params[i].getName(), prompts[i],
                            prompts[i].equals(pr), false);
                } else {
                    props.addRow(tpe,
                            params[i].getName(),
                            prompts[i],
                            prompts[i].equals(pr));
                }
            }

            // fill the rest of JTable with PromptFor Expressions, if any :
            for (int i=0; i<M.PromptFor.length; i++) {
                final String tpe=Global.Loc("rene.zirkel.objects.ExpressionObject");
                props.addRow(tpe, M.PromptFor[i], M.PromptName[i], false, true);
            }

            hideDuplicates.setSelected(M.hideDuplicates());
        }

        class myRub extends JEricPanel {

            /**
             *
             */
            @Override
            public void paintComponent(final java.awt.Graphics g) {
                super.paintComponent(g);
                final java.awt.Dimension d=this.getSize();
                g.drawImage(themes.getImage("palbackground2.gif"), 0, 0,
                        d.width, d.height, this);
            }

            public myRub() {
                this.setLayout(new javax.swing.BoxLayout(this,
                        javax.swing.BoxLayout.Y_AXIS));
                this.setAlignmentX(0F);
            }
        }

        class ContentLine extends JEricPanel {

            /**
             *
             */
            public ContentLine(final int height) {
                this.setLayout(new javax.swing.BoxLayout(this,
                        javax.swing.BoxLayout.X_AXIS));
                this.setAlignmentX(0F);
                this.setMaximumSize(new java.awt.Dimension(PW, height));
                this.setMinimumSize(new java.awt.Dimension(PW, height));
                this.setPreferredSize(new java.awt.Dimension(PW, height));
                this.setSize(PW, height);
                this.setOpaque(false);
            }
        }

        class mySep extends JEricPanel {

            /**
             *
             */
            public mySep(final int height) {
                this.setLayout(new javax.swing.BoxLayout(this,
                        javax.swing.BoxLayout.X_AXIS));
                this.setAlignmentX(0F);
                this.setMaximumSize(new java.awt.Dimension(PW, height));
                this.setMinimumSize(new java.awt.Dimension(PW, height));
                this.setPreferredSize(new java.awt.Dimension(PW, height));
                this.setSize(PW, height);
                this.setBackground(new Color(200, 200, 200));
            }
        }
    }
}
