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
package eric.controls;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import rene.util.xml.XmlWriter;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.objects.ExpressionObject;

/**
 * 
 * @author erichake
 */
public class JCanvasButton extends JCanvasPanel {

    /**
     *
     */
    private static final long serialVersionUID=1L;
    MyJButton JCB;

    public JCanvasButton(final ZirkelCanvas zc, final ExpressionObject o) {
        super(zc, o);
        JSL=new MyJButton();
        JCB=(MyJButton) JSL;
        JCB.addMouseListener(this);
        JCB.addMouseMotionListener(this);
        final JCanvasButton btn=this;
        JCB.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(final MouseEvent e) {
                if (!isEditMode()&&!isTargetMode()) {
                    ZC.runControlScripts(btn);
                }
            }

            @Override
            public void mousePressed(final MouseEvent e) {
                if (isEditMode()||isTargetMode()) {
                    JCB.setEnabled(false);
                }
            }

            @Override
            public void mouseReleased(final MouseEvent e) {
                if (!hidden()) {
                JCB.setEnabled(true);
                if (getVal()==0) {
                    setVal(1);
                } else {
                    setVal(0);
                }
            }
            }
        });
        showval=false;
        showunit=false;
        showcom=false;
        setComment("ok");
        setVal(0);
        this.add(JCPlabel);
        this.add(JCB);
        this.add(JCPresize);
        zc.add(this);
    }

    @Override
    public void setComment(final String s) {
        lbl_com=s;
        JCPlabel.setText(goodLabel());
        if (showcom) {
            JCB.setText("");
        } else {
            JCB.setText(s);
        }
        setDims();
    }

    @Override
    public void setShowComment(final boolean b) {
        showcom=b;
        JCPlabel.setText(goodLabel());
        if (showcom) {
            JCB.setText("");
        } else {
            JCB.setText(lbl_com);
        }
        setDims();
    }

    @Override
    public double getVal() {
        double s;
        try {
            s=O.getValue();
        } catch (final Exception ex) {
            s=0;
        }
        return s;
    }

    class MyJButton extends JButton {

        /**
         *
         */
        private static final long serialVersionUID=1L;

        MyJButton() {
            super();
            setFocusable(false);
            setOpaque(false);
        }
    }

    @Override
    public void PrintXmlTags(final XmlWriter xml) {
        xml.startTagStart("CTRLbutton");

        super.PrintXmlTags(xml);
        xml.finishTagNewLine();

    }
}
