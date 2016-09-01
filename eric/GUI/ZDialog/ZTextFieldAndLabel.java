/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.GUI.ZDialog;

import eric.JEricPanel;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;

/**
 *
 * @author erichake
 */
public class ZTextFieldAndLabel extends JEricPanel {

    private ZTextFieldAndLabel me;
    private myTextField field=null;
    private ZLabel label=null;
    private String InitValue="";
    private int labelWidth=100;
    private int labelHeight=20;

    @Override
    public void paint(Graphics g) {
        paintChildren(g);
    }

    public ZTextFieldAndLabel(String lbl, String value, int labelwidth,int labelheight) {
        super();
        me=this;
        labelWidth=labelwidth;
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setOpaque(false);
        if (labelWidth==0) {
            InitValue="<"+lbl+">";
            if ("".equals(value)) {
                add(field=new myTextField(InitValue));
                field.setForeground(ZTools.C_TextField_OFF);
            } else {
                add(field=new myTextField(value));
            }
            field.setHorizontalAlignment(SwingConstants.CENTER);

        } else {
            add(label=new ZLabel(lbl));
            add(field=new myTextField(value));
            ZTools.fixsize(label, labelWidth, labelheight);
        }
    }

    public void actionMouse() {
    }

    public void actionKey(java.awt.event.KeyEvent k){
    }

    public void focusLost(){
    }

    public void focusGained(){
    }

    public String getText() {
        if (InitValue.equals(field.getText())) {
            return "";
        } else {
            return field.getText();
        }
    }

    public void setText(String txt) {
        if ("".equals(txt)) {
            txt=InitValue;
            field.setForeground(ZTools.C_TextField_OFF);
        } else {
            field.setForeground(ZTools.C_TextField);
        }
        field.setText(txt);
    }

    @Override
    public void setForeground(Color c){
        if(field!=null){
            field.setForeground(c);
        }
    }

    public void setEditable(boolean b){
        if (field!=null){
	    field.setEditable(b);
	}
    }

    private class myTextField extends ZTextField {

        public myTextField(String s) {
            super(s);
        }

        @Override
        public void actionMouse() {
            me.actionMouse();
        }

        @Override
        public void actionKey(java.awt.event.KeyEvent k){
            me.actionKey(k);
        }

        @Override
        public void focusOn() {
            if ((field.getText().equals(InitValue))) {
                field.setText("");
                field.setForeground(ZTools.C_TextField);
            }
            field.selectAll();
            me.focusGained();
        }

        @Override
        public void focusOff() {
            if ((field.getText().equals(""))) {
                field.setText(InitValue);
                field.setForeground(ZTools.C_TextField_OFF);
            }
            me.focusLost();
        }
    }
}
