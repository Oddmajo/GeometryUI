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
package eric.bar;

import eric.JZirkelCanvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import eric.JEricPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import eric.controls.JCanvasButton;
import eric.controls.JCanvasPanel;
import eric.controls.JCanvasPopup;
import eric.controls.JCanvasSlider;
import eric.controls.JCanvasTxtfield;
import eric.textfieldpopup.JTextFieldPopup;

/**
 *
 * @author erichake
 */
public class JControlProperties extends JProperties {

    /**
     *
     */

    JCanvasPanel JCP;
    myJSliderShowTicks jsShowTicks;
    myJSliderSnapTicks jsSnapTicks;
    myJSliderMin jsMin;
    myJSliderMax jsMax;
    myJSliderTicks jsTicks;
    myJComment jsComment;
    myJSliderUnit jsUnit;
    myJSliderShowComment jsShowcom;
    myJSliderShowVal jsShowval;
    myJSliderShowUnit jsShowunit;
    myJPopupItems jsPopupitems;

    public JControlProperties(final int w, final int h) {
        super(w, h);
        jsShowTicks=new myJSliderShowTicks(Loc("ctrlshowticks"), true, 200,
                TextFieldHeight);
        jsSnapTicks=new myJSliderSnapTicks(Loc("ctrlsnap"), true, 200,
                TextFieldHeight);
        jsMin=new myJSliderMin("min:", "", 50, 150, TextFieldHeight);
        jsMax=new myJSliderMax("max:", "", 50, 150, TextFieldHeight);
        jsTicks=new myJSliderTicks(Loc("ctrltickspacing"), "", 100, 200,
                TextFieldHeight);
        jsComment=new myJComment(Loc("expl"), "", 100, 250, TextFieldHeight);
        jsUnit=new myJSliderUnit(Loc("unit"), "", 100, 250, TextFieldHeight);
        jsShowcom=new myJSliderShowComment("", true, 18, TextFieldHeight);
        jsShowval=new myJSliderShowVal(Loc("ctrlshowvalue"), true, 200,
                TextFieldHeight);
        jsShowunit=new myJSliderShowUnit("", true, 18, TextFieldHeight);
        jsPopupitems=new myJPopupItems("", 250, TextFieldHeight*3+2);
    }

    public void setObject(final JCanvasPanel jcp) {
        if (JZirkelCanvas.getCurrentZF()==null) {
            return;
        }
        ZF=JZirkelCanvas.getCurrentZF();
        ZC=JZirkelCanvas.getCurrentZC();
        JCP=jcp;
        //O=JCP.O;
	setObject(JCP.O);

        Cn=ZC.getConstruction();
        this.clearAll();
        addName();
        addCommonProps();
        addJSliderProps();
        addJPopupProps();
        addConditionals();
        selectTab(1);
        this.revalidate();
        this.repaint();
    }

    private void addName() {
        final JEricPanel rubname=new myRub();
        name.init();
        rubname.add(name);
        addMain(margin(5));
        addMain(rubname);
        addMain(margin(5));
        final JEricPanel rub=new myRub();
        addMain(rub);
    }

    private void addCommonProps() {
        jsComment.init();
        jsUnit.init();
        jsShowcom.init();
        jsShowval.init();
        jsShowunit.init();
        final ContentLine l1=new ContentLine();
        l1.add(jsShowcom);
        l1.add(jsComment);
        final ContentLine l2=new ContentLine();
        l2.add(jsShowunit);
        l2.add(jsUnit);
        final JEricPanel rub4=new myRub();
        rub4.add(margintop(2));
        rub4.add(l1);
        if ((JCP instanceof JCanvasTxtfield)||(JCP instanceof JCanvasButton)) {
            rub4.add(margintop(jsShowval.H*2+2));
        } else {
            rub4.add(margintop(1));
            rub4.add(l2);
            rub4.add(margintop(1));
            rub4.add(jsShowval);
        }
        addToNum(rub4);
        addToNum(new myRubSep());
    }

    private void addJSliderProps() {
        if (!(JCP instanceof JCanvasSlider)) {
            return;
        }

        jsShowTicks.init();
        jsSnapTicks.init();
        jsMin.init();
        jsMax.init();
        jsTicks.init();
        final JEricPanel rub1=new myRub();
        rub1.add(margintop(2));
        rub1.add(jsTicks);
        rub1.add(margintop(1));
        rub1.add(jsSnapTicks);
        rub1.add(margintop(1));
        rub1.add(jsShowTicks);

        addToNum(rub1);
        addToNum(new myRubSep());

        final JEricPanel rub2=new myRub();
        rub2.add(margintop(2));
        rub2.add(jsMin);
        rub2.add(margintop(jsMin.H+2));
        rub2.add(jsMax);

        addToNum(rub2);

    }

    private void addJPopupProps() {
        if (!(JCP instanceof JCanvasPopup)) {
            return;
        }
        final JEricPanel rub2=new myRub();
        rub2.add(margintop(2));
        jsPopupitems.init();
        rub2.add(jsPopupitems);
        addToNum(rub2);
    }

    private void addConditionals() {
        final JEricPanel rub=new myRub();
        chidden.init();
        csuperhidden.init();
        rub.add(margintop(1));
        rub.add(chidden);
        rub.add(margintop(2*chidden.H+3));

        addToConditional(rub);

    }

    // class myJSliderName extends myJLine {
    //
    // public myJSliderName(String comment, String txt, int comwidth, int width,
    // int height) {
    // super(comment, txt, comwidth, width, height);
    // this.JTF.setHorizontalAlignment(SwingConstants.CENTER);
    //
    // }
    //
    // public void doAction(Component e) {
    // JTextField jtf = (JTextField) e;
    // if (O.getName().equals(jtf.getText())) {
    // return;
    // }
    // O.setName(jtf.getText());
    // O.setShowName(true);
    // show.forceSelect(2); // Force the ShowName icon to be selected
    // if (ZC != null) {
    // ZC.repaint();
    // }
    // }
    //
    // public void init() {
    // setText(O.getName());
    // }
    // }
    class myJComment extends myJLine {

        /**
         *
         */


        public myJComment(final String comment, final String txt,
                final int comwidth, final int width, final int height) {
            super(comment, txt, comwidth, width, height, true);
            carPopup.setDisabled(","+JTextFieldPopup.LATEXMENU+","+JTextFieldPopup.FUNCTIONMENU+",");
        }

        @Override
        public void doAction(final Component e) {

            JCP.setComment(getText());
        }

        public void init() {
            setText(JCP.getComment());
        }
    }

    class myJSliderUnit extends myJLine {

        /**
         *
         */


        public myJSliderUnit(final String comment, final String txt,
                final int comwidth, final int width, final int height) {
            super(comment, txt, comwidth, width, height, true);
            carPopup.setDisabled(","+JTextFieldPopup.LATEXMENU+","+JTextFieldPopup.FUNCTIONMENU+",");
        }

        @Override
        public void doAction(final Component e) {

            JCP.setUnit(getText());
        }

        public void init() {
            setText(JCP.getUnit());
        }
    }

    class myJSliderShowComment extends myJLine {

        /**
         *
         */


        public myJSliderShowComment(final String comment, final boolean bool,
                final int width, final int height) {
            super(comment, bool, width, height);
        }

        @Override
        public void doAction(final Component e) {
            JCP.setShowComment(!isSelected());

        }

        public void init() {
            setSelected(JCP.getShowComment());
        }
    }

    class myJSliderShowVal extends myJLine {

        /**
         *
         */


        public myJSliderShowVal(final String comment, final boolean bool,
                final int width, final int height) {
            super(comment, bool, width, height);
        }

        @Override
        public void doAction(final Component e) {
            JCP.setShowVal(!isSelected());

        }

        public void init() {
            setSelected(JCP.getShowVal());
        }
    }

    class myJSliderShowUnit extends myJLine {

        /**
         *
         */


        public myJSliderShowUnit(final String comment, final boolean bool,
                final int width, final int height) {
            super(comment, bool, width, height);
        }

        @Override
        public void doAction(final Component e) {
            JCP.setShowUnit(!isSelected());

        }

        public void init() {
            setSelected(JCP.getShowUnit());
        }
    }

    class myJSliderShowTicks extends myJLine {

        /**
         *
         */


        public myJSliderShowTicks(final String comment, final boolean bool,
                final int width, final int height) {
            super(comment, bool, width, height);
        }

        @Override
        public void doAction(final Component e) {
            ((JCanvasSlider) JCP).setShowTicks(!isSelected());

        }

        public void init() {
            setSelected(((JCanvasSlider) JCP).getShowTicks());
        }
    }

    class myJSliderSnapTicks extends myJLine {

        /**
         *
         */


        public myJSliderSnapTicks(final String comment, final boolean bool,
                final int width, final int height) {
            super(comment, bool, width, height);
        }

        @Override
        public void doAction(final Component e) {
            ((JCanvasSlider) JCP).setSnap(!isSelected());

        }

        public void init() {
            setSelected(((JCanvasSlider) JCP).getSnap());
        }
    }

    class myJSliderMin extends myJLine {

        /**
         *
         */

        String current;
        String origin;

        public myJSliderMin(final String comment, final String txt,
                final int comwidth, final int width, final int height) {
            super(comment, txt, comwidth, width, height);
        }

        @Override
        public void doAction(final Component e) {
            if ((current.equals(getText()))||(!(isValidExpression(getText())))) {
                return;
            }

            current=getText();

        }

        @Override
        public void doQuitMe(final Component e) {
            if (!(isValidExpression(getText()))) {
                JOptionPane.showMessageDialog(null, Loc("error"));
                ((JCanvasSlider) JCP).setMin(ValueOf(origin));
                setText(origin);
                JTF.requestFocus();
                return;
            }
            current=getText();
            ((JCanvasSlider) JCP).setMin(ValueOf(current));
            jsMax.setText(((JCanvasSlider) JCP).getMax());
        }

        public void init() {
            current=String.valueOf(((JCanvasSlider) JCP).getMin());
            origin=current;
            setText(current);
            System.out.println(JTF.getText());
        }
    }

    class myJSliderMax extends myJLine {

        /**
         *
         */

        String current;
        String origin;

        public myJSliderMax(final String comment, final String txt,
                final int comwidth, final int width, final int height) {
            super(comment, txt, comwidth, width, height);
        }

        @Override
        public void doAction(final Component e) {
            if ((current.equals(getText()))||(!(isValidExpression(getText())))) {
                return;
            }

            current=getText();

        }

        @Override
        public void doQuitMe(final Component e) {
            if (!(isValidExpression(getText()))) {
                JOptionPane.showMessageDialog(null, Loc("error"));
                ((JCanvasSlider) JCP).setMax(ValueOf(origin));
                setText(origin);
                JTF.requestFocus();
                return;
            }
            current=getText();
            ((JCanvasSlider) JCP).setMax(ValueOf(current));
            jsMin.setText(((JCanvasSlider) JCP).getMin());

        }

        public void init() {
            current=String.valueOf(((JCanvasSlider) JCP).getMax());
            origin=current;
            setText(current);
        }
    }

    class myJSliderTicks extends myJLine {

        /**
         *
         */

        String current, origin;

        public myJSliderTicks(final String comment, final String txt,
                final int comwidth, final int width, final int height) {
            super(comment, txt, comwidth, width, height);
        }

        @Override
        public void doAction(final Component e) {
            if ((current.equals(getText()))||(!(isValidExpression(getText())))) {
                return;
            }

            current=getText();
            ((JCanvasSlider) JCP).setTicks(ValueOf(current));
        }

        @Override
        public void doQuitMe(final Component e) {
            if (!(isValidExpression(getText()))) {
                JOptionPane.showMessageDialog(null, Loc("error"));
                ((JCanvasSlider) JCP).setTicks(ValueOf(origin));
                setText(origin);
                return;
            }
            current=getText();
            ((JCanvasSlider) JCP).setTicks(ValueOf(current));
        }

        public void init() {
            current=String.valueOf(((JCanvasSlider) JCP).getTicks());
            origin=current;
            setText(current);

        }
    }

    class txtfieldTemplate extends myJLine {

        /**
         *
         */


        public txtfieldTemplate(final String comment, final String txt,
                final int comwidth, final int width, final int height) {
            super(comment, txt, comwidth, width, height, true);
        }

        @Override
        public void doAction(final Component e) {
        }

        @Override
        public void doQuitMe(final Component e) {
        }

        public void init() {
        }
    }

    class myJPopupItems extends ContentLine {

        /**
         *
         */

        JButton carBTN=null;
        JTextArea JTX;

        public myJPopupItems(final String mytxt, final int width,
                final int height) {
            super(width, height);
            this.setFocusable(false);
            this.add(margin(5));
            JTX=new JTextArea(mytxt);
            JTX.setFont(F_TextArea);
            JTX.setBackground(new Color(245, 246, 255));
            // JTX.setBorder(BorderFactory.createEtchedBorder());

            JTX.addKeyListener(new KeyAdapter() {

                @Override
                public void keyReleased(final KeyEvent e) {
                    doAction(e.getComponent());
                }
            });
            JTX.addFocusListener(new FocusAdapter() {

                @Override
                public void focusGained(final FocusEvent e) {
                    // JTX.selectAll();
                    carBTN.setEnabled(true);
                }

                @Override
                public void focusLost(final FocusEvent e) {
                    carBTN.setEnabled(false);
                    doQuitMe(e.getComponent());
                }
            });
            JTX.setLineWrap(true);

            //
            final JScrollPane jstxt=new JScrollPane(JTX);
            jstxt.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            jstxt.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

            // jstxt.setViewportView(JTX);
            // fixsize(jstxt,PW-11,h);
            this.add(jstxt);
            final ImageIcon carimg=new ImageIcon(getClass().getResource(
                    "/eric/GUI/icons/bar/carbtn.png"));
            final ImageIcon carimg_dis=new ImageIcon(getClass().getResource(
                    "/eric/GUI/icons/bar/carbtn_dis.png"));
            carBTN=new JButton(carimg);
            carBTN.setDisabledIcon(carimg_dis);
            // carbtn.setRolloverIcon(closeoverimg);
            carBTN.setBorder(BorderFactory.createEmptyBorder());
            carBTN.setOpaque(false);
            carBTN.setContentAreaFilled(false);
            carBTN.setFocusable(false);
            carBTN.addMouseListener(new MouseAdapter() {

                @Override
                public void mousePressed(final MouseEvent e) {
                    doShowPopup(e);
                }
            });
            carBTN.setEnabled(false);

            this.add(margin(2));
            this.add(carBTN);
        }

        public void doShowPopup(final MouseEvent e) {
            if (carBTN.isEnabled()) {
                final JTextFieldPopup mypop=new JTextFieldPopup(JTX);
                mypop.setDisabled(","+JTextFieldPopup.LATEXMENU+","+JTextFieldPopup.FUNCTIONMENU+",");
                mypop.addPopupMenuListener(new PopupMenuListener() {

                    public void popupMenuWillBecomeVisible(
                            final PopupMenuEvent arg0) {
                    }

                    public void popupMenuWillBecomeInvisible(
                            final PopupMenuEvent arg0) {
                        doAction(JTX);
                    }

                    public void popupMenuCanceled(final PopupMenuEvent arg0) {
                    }
                });
                mypop.openMenu(e);
            }
        }

        public void setText(final String txt) {
            JTX.setText(txt);
        }

        public String getText() {
            return JTX.getText();
        }

        public void doAction(final Component cp) {
            if (getObject().getText().equals(getText())) {
                return;
            }
            ((JCanvasPopup) JCP).setItems(getText());
            // O.setLines(getText());
            // O.setText(getText(), true);
            // ZC.recompute();
            // ZC.validate();
            // ZC.repaint();
        }

        public void doQuitMe(final Component cp) {
        }

        public void init() {
            // setText(O.getLines());
            setText(((JCanvasPopup) JCP).getItems());
        }
    }
}
